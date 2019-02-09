package alex.band.statemachine;

import java.util.HashSet;
import java.util.Set;

import alex.band.statemachine.listener.StateMachineListener;
import alex.band.statemachine.message.StateMachineMessage;
import alex.band.statemachine.message.StateMachineMessageImpl;
import alex.band.statemachine.state.State;

/**
 * Абстрактная реализация {@link StateMachine}, реализующая механизм регистрации/удаления/оповещения слушателей {@link StateMachineListener}.
 *
 * @author Aliaksandr Bandarchyk
 */
public abstract class ListenableStateMachine<S, E> implements StateMachine<S, E> {

	private Set<StateMachineListener<S, E>> listeners = new HashSet<>();


	@Override
	public void start() {
		doStart();

		for (StateMachineListener<S, E> listener: listeners) {
			listener.onStart(this);
		}
	}

	/**
	 * Действия по запуску конечного автомата.
	 */
	protected abstract void doStart();

	@Override
	public void stop() {
		doStop();

		for (StateMachineListener<S, E> listener: listeners) {
			listener.onStop(this);
		}
	}

	/**
	 * Действия по остановке конечного автомата.
	 */
	protected abstract void doStop();

	@Override
	public boolean accept(E event) {
		return accept(new StateMachineMessageImpl<>(event));
	}

	@Override
	public boolean accept(StateMachineMessage<E> message) {
		State<S, E> previousState = getCurrentState();
		boolean messageAccepted = doAccept(message);

		for (StateMachineListener<S, E> listener: listeners) {

			if (messageAccepted) {
				listener.onStateChanged(message, previousState, this);

			} else {
				listener.onEventNotAccepted(message, this);
			}
		}

		return messageAccepted;
	}

	/**
	 * Действия по обработке сообщений конечным автоматом.
	 */
	protected abstract boolean doAccept(StateMachineMessage<E> message);

	@Override
	public void addListener(StateMachineListener<S, E> listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(StateMachineListener<S, E> listener) {
		listeners.remove(listener);
	}

}
