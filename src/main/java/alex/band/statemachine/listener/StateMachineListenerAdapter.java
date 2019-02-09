package alex.band.statemachine.listener;

import alex.band.statemachine.StateMachineDetails;
import alex.band.statemachine.message.StateMachineMessage;
import alex.band.statemachine.state.State;

/**
 * Адаптер к {@link StateMachineListener}. Удобно использовать, если нужно реализовать слушателя только для одного события из набора.
 *
 * @author Aliaksandr Bandarchyk
 */
public abstract class StateMachineListenerAdapter<S, E> implements StateMachineListener<S, E> {

	@Override
	public void onStart(StateMachineDetails<S, E> stateMachineDetails) {
		// do nothing by default
	}

	@Override
	public void onStop(StateMachineDetails<S, E> stateMachineDetails) {
		// do nothing by default
	}

	@Override
	public void onStateChanged(StateMachineMessage<E> message, State<S, E> previousState, StateMachineDetails<S, E> stateMachineDetails) {
		// do nothing by default
	}

	@Override
	public void onEventNotAccepted(StateMachineMessage<E> message, StateMachineDetails<S, E> stateMachineDetails) {
		// do nothing by default
	}

}
