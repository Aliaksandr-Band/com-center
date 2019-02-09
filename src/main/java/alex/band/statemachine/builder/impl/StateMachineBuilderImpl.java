package alex.band.statemachine.builder.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import alex.band.statemachine.StateMachine;
import alex.band.statemachine.StateMachineStartAction;
import alex.band.statemachine.StateMachineStopAction;
import alex.band.statemachine.builder.ExternalTransitionConfigurer;
import alex.band.statemachine.builder.InternalTransitionConfigurer;
import alex.band.statemachine.builder.StartStopActionsConfigurer;
import alex.band.statemachine.builder.StateMachineBuilder;
import alex.band.statemachine.builder.StatesConfigurer;
import alex.band.statemachine.context.StateMachineContextImpl;
import alex.band.statemachine.state.State;
import alex.band.statemachine.state.StateImpl;
import alex.band.statemachine.transition.Transition;

/**
 * Реализация {@link StateMachineBuilder}
 *
 * @author Aliaksandr Bandarchyk
 */
public class StateMachineBuilderImpl<S, E> implements StateMachineBuilder<S, E> {

	static final String FINAL_STATE_ALREADY_DEFINED = "Final State already defined. Defined State %s, new State %s";
	static final String INITIAL_STATE_ALREADY_DEFINED = "Initial State already defined. Defined State %s, new State %s";
	static final String STATE_ALREADY_DEFINED = "State with equal ID already defined: %s";
	static final String STATES_WITHOUT_OUTBOUND_TRANSITION = "There are States which don't have outbound transition: %s";
	static final String STATES_WITHOUT_INBOUND_TRANSITION = "There are States which don't have inbound transition: %s";
	static final String ILLEGAL_TRANSITION_FROM_FINAL_STATE = "Final State should not be used as source of Transition: %s";
	static final String EXTERNAL_TRANSITION_HAS_NO_TARGET_STATE = "External Transition doesn't have target State defined: %s";
	static final String UNKOWN_SOURCE_STATES_IN_TRANSITIONS = "Transitions have unkown source States: %s";
	static final String UNKOWN_TARGET_STATES_IN_TRANSITIONS = "Transitions have unkown target States: %s";
	static final String FINAL_STATE_IS_NOT_DEFINED = "Final State is not defined.";
	static final String INITIAL_STATE_IS_NOT_DEFINED = "Initial State is not defined.";
	static final String THERE_ARE_NO_STATES_DEFINED = "There are no States defined.";

	private State<S, E> initialState;
	private State<S, E> finalState;
	private Map<S, State<S, E>> states = new HashMap<>();
	private Map<S, Set<Transition<S, E>>> transitions = new HashMap<>();
	private Set<StateMachineStartAction<S, E>> startActions = new HashSet<>();
	private Set<StateMachineStopAction<S, E>> stopActions = new HashSet<>();

	@Override
	public StartStopActionsConfigurer defineStartStopActions() {
		StartStopActionsConfigurerImpl  startStopConfigurer = new StartStopActionsConfigurerImpl();
		startActions = startStopConfigurer.getStartActions();
		stopActions = startStopConfigurer.getStopActions();
		return startStopConfigurer;
	}

	@Override
	public StatesConfigurer<S, E> defineState(S stateId) {
		StateImpl<S, E> state = new StateImpl<>(stateId);
		addState(state);

		return new StatesConfigurerImpl<>(this, state);
	}

	@Override
	public void defineStates(Set<S> states) {
		for (S state: states) {
			addState(new StateImpl<S, E>(state));
		}
	}

	@Override
	public ExternalTransitionConfigurer<S, E> defineExternalTransitionFor(S sourceState) {
		ExternalTransitionConfigurerImpl<S, E> transitionConfigurer = new ExternalTransitionConfigurerImpl<>(sourceState, true);
		addTransition(sourceState, transitionConfigurer.getTransition());

		return transitionConfigurer;
	}

	@Override
	public InternalTransitionConfigurer<S, E> defineInternalTransitionFor(S sourceState) {
		InternalTransitionConfigurerImpl<S, E> transitionConfigurer = new InternalTransitionConfigurerImpl<>(sourceState, false);
		addTransition(sourceState, transitionConfigurer.getTransition());

		return transitionConfigurer;
	}

	@Override
	public StateMachine<S, E> build() {
		validateStates();
		validateTransitions();
		validateTopology();
		return createStateMachine();
	}

	private void validateStates() {
		Preconditions.checkState(!states.isEmpty(), THERE_ARE_NO_STATES_DEFINED);
		Preconditions.checkState(initialState != null, INITIAL_STATE_IS_NOT_DEFINED);
		Preconditions.checkState(finalState != null, FINAL_STATE_IS_NOT_DEFINED);
	}

	private void validateTransitions() {
		Set<S> diff = Sets.difference(transitions.keySet(), states.keySet());
		Preconditions.checkState(diff.isEmpty(), UNKOWN_SOURCE_STATES_IN_TRANSITIONS, diff);

		Set<S> transitionsTargetStates = validateAndGetTargetStatesFromTransitions();
		diff = Sets.difference(transitionsTargetStates, states.keySet());
		Preconditions.checkState(diff.isEmpty(), UNKOWN_TARGET_STATES_IN_TRANSITIONS, diff);
	}

	private Set<S> validateAndGetTargetStatesFromTransitions() {
		Set<S> targetStates = new HashSet<>();
		for (Set<Transition<S, E>> transitionsBySource: transitions.values()) {
			for (Transition<S, E> transition: transitionsBySource) {

				Preconditions.checkState((transition.isExternal() == transition.getTarget().isPresent()),
						EXTERNAL_TRANSITION_HAS_NO_TARGET_STATE, transition);

				Preconditions.checkState(!transition.getSource().equals(finalState.getId()),
						ILLEGAL_TRANSITION_FROM_FINAL_STATE, transition);

				if (transition.getTarget().isPresent()) {
					targetStates.add(transition.getTarget().get());
				}
			}
		}
		return targetStates;
	}

	private void validateTopology() {
		Set<S> enteredStates = Sets.newHashSet(states.keySet());
		Set<S> exitedStates = Sets.newHashSet(states.keySet());
		enteredStates.remove(initialState.getId());
		exitedStates.remove(finalState.getId());

		for (Set<Transition<S, E>> stateTransitions: transitions.values()) {
			for (Transition<S, E> transition: stateTransitions) {
				excludeStatesOfExternalTransition(enteredStates, exitedStates, transition);
			}
		}

		Preconditions.checkState(enteredStates.isEmpty(), STATES_WITHOUT_INBOUND_TRANSITION, enteredStates);
		Preconditions.checkState(exitedStates.isEmpty(), STATES_WITHOUT_OUTBOUND_TRANSITION, exitedStates);
	}

	private void excludeStatesOfExternalTransition(Set<S> enteredStates, Set<S> exitedStates, Transition<S, E> transition) {
		if (transition.isExternal()) {
			exitedStates.remove(transition.getSource());
			if (transition.getTarget().isPresent()) {
				enteredStates.remove(transition.getTarget().get());
			}
		}
	}

	private StateMachine<S, E> createStateMachine() {
		connectTransitionsWithSourceStates();
		StateMachineImpl<S, E> stateMachine = new StateMachineImpl<>();
		stateMachine.setInitialState(initialState);
		stateMachine.setFinalState(finalState);
		stateMachine.setStates(states);
		stateMachine.setStartActions(startActions);
		stateMachine.setStopActions(stopActions);
		stateMachine.setContext(new StateMachineContextImpl());

		return stateMachine;
	}

	private void connectTransitionsWithSourceStates() {
		for (Set<Transition<S, E>> transitionsBySource: transitions.values()) {
			for (Transition<S, E> transition: transitionsBySource) {
				StateImpl<S, E> state = (StateImpl<S, E>) states.get(transition.getSource());
				state.addTransition(transition);
			}
		}
	}

	private void addTransition(S sourceState, Transition<S, E> transition) {
		if (!transitions.containsKey(sourceState)) {
			transitions.put(sourceState, new HashSet<Transition<S, E>>());
		}
		transitions.get(sourceState).add(transition);
	}

	private void addState(State<S, E> state) {
		Preconditions.checkState(!states.containsKey(state.getId()), STATE_ALREADY_DEFINED, state.getId());
		states.put(state.getId(), state);
	}

	void setInitialState(State<S, E> state) {
		Preconditions.checkState(initialState == null, INITIAL_STATE_ALREADY_DEFINED, initialState, state);
		initialState = state;
	}

	void setFinalState(State<S, E> state) {
		Preconditions.checkState(finalState == null, FINAL_STATE_ALREADY_DEFINED, finalState, state);
		finalState = state;
	}

}
