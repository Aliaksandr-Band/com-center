package alex.band.statemachine.state;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;

import alex.band.statemachine.StateMachineDetails;
import alex.band.statemachine.message.StateMachineMessage;
import alex.band.statemachine.transition.Transition;

/**
 * Реализация {@link State}
 *
 * @author Aliaksandr Bandarchyk
 */
public class StateImpl<S, E> implements State<S, E> {

	private S stateId;
	private Set<StateAction<S, E>> actions = new HashSet<>();
	private Map<E, Set<Transition<S, E>>> transitions = new HashMap<>();
	private Set<E> deferredEvents = new HashSet<>();

	public StateImpl(S stateId) {
		this.stateId = stateId;
	}

	@Override
	public Optional<Transition<S, E>> getSuitableTransition(StateMachineMessage<E> message, StateMachineDetails<S, E> context) {
		if (transitions.get(message.getEvent()) == null || transitions.get(message.getEvent()).isEmpty()) {
			return Optional.absent();
		}

		for (Transition<S, E> transition: transitions.get(message.getEvent())) {
			if (!transition.getGuard().isPresent() || transition.getGuard().get().evaluate(message, context)) {
				return Optional.of(transition);
			}
		}

		return Optional.absent();
	}

	@Override
	public void onEnter(StateMachineDetails<S, E> context) {
		for (StateAction<S, E> action: actions) {
			action.onEnter(context);
		}
	}

	@Override
	public void onExit(StateMachineDetails<S, E> context) {
		for (StateAction<S, E> action: actions) {
			action.onExit(context);
		}
	}

	@Override
	public S getId() {
		return stateId;
	}

	@Override
	public boolean canBeDeferred(StateMachineMessage<E> message) {
		return deferredEvents.contains(message.getEvent());
	}

	@Override
	public String toString() {
		return "StateImpl [stateId=" + stateId + ", deferredEvents=" + deferredEvents + "]";
	}

	public void addActions(Set<StateAction<S, E>> actions) {
		this.actions.addAll(actions);
	}

	public void addAction(StateAction<S, E> action) {
		this.actions.add(action);
	}

	public void addTransition(Transition<S, E> transition) {
		if (!transitions.containsKey(transition.getEvent())) {
			transitions.put(transition.getEvent(), new HashSet<Transition<S, E>>());
		}
		transitions.get(transition.getEvent()).add(transition);
	}

	public void addDeferredEvent(E deferredEvent) {
		this.deferredEvents.add(deferredEvent);
	}

	public void addDeferredEvents(Set<E> deferredEvents) {
		this.deferredEvents.addAll(deferredEvents);
	}

}
