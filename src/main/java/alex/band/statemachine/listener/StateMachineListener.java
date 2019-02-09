package alex.band.statemachine.listener;

import alex.band.statemachine.StateMachineDetails;
import alex.band.statemachine.message.StateMachineMessage;
import alex.band.statemachine.state.State;

/**
 * Слушатель жизненного цикла конечного автомата {@link StateMachine}.
 *
 * <p>Предоставляет управление после следующих этапов работы конечного автомата (КА):
 * <ul><li>запуск КА - {@link #onStart(StateMachineDetails)}</li>
 * <li>остановка КА - {@link #onStop(StateMachineDetails)}</li>
 * <li>смена управляющего состояния КА - {@link #onStateChanged(StateMachineMessage, State, StateMachineDetails)}</li>
 * <li>игнорирование пришедшего сообщения - {@link #onEventNotAccepted(StateMachineMessage, StateMachineDetails)}</li></ul>
 *
 * @param <S> - тип идентификатора состояния
 * @param <E> - тип идентификатора события
 *
 * @author Aliaksandr Bandarchyk
 */
public interface StateMachineListener<S, E> {

	/**
	 * Метод вызывается после отработки стартовых действий конечного автомата.
	 *
	 * <p>Слушатель будет иметь возможность анализа состояния конечного автомата через интерфейс {@link StateMachineDetails}
	 */
	void onStart(StateMachineDetails<S, E> stateMachineDetails);

	/**
	 * Метод вызывается после отработки терминальных действий конечного автомата.
	 *
	 * <p>Слушатель будет иметь возможность анализа состояния конечного автомата через интерфейс {@link StateMachineDetails}
	 */
	void onStop(StateMachineDetails<S, E> stateMachineDetails);

	/**
	 * Метод вызывается после смены управляющего состояния конечного автомата.
	 *
	 * <p>Слушатель получить информацию о сообщении {@link StateMachineMessage}, приведшем к смене управляющего состояния,
	 * о предыдущем управляющем состоянии {@link State} и о деталях состояния самого конечного автомата через интерфейс {@link StateMachineDetails}.
	 */
	void onStateChanged(StateMachineMessage<E> message, State<S, E> previousState, StateMachineDetails<S, E> stateMachineDetails);

	/**
	 * Метод вызывается в случае, если пришедшее сообщение {@link StateMachineMessage} не может быть обработано в текущем управляющем состоянии,
	 * при текущих условиях.
	 *
	 * <p>Слушатель получить информацию о сообщении {@link StateMachineMessage}
	 * и о деталях состояния самого конечного автомата через интерфейс {@link StateMachineDetails}.
	 */
	void onEventNotAccepted(StateMachineMessage<E> message, StateMachineDetails<S, E> stateMachineDetails);

}
