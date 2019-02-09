package alex.band.statemachine.state;

import com.google.common.base.Optional;

import alex.band.statemachine.StateMachine;
import alex.band.statemachine.StateMachineDetails;
import alex.band.statemachine.message.StateMachineMessage;
import alex.band.statemachine.transition.Transition;

/**
 * Управляющее состояние конечного автомата {@link StateMachine}
 *
 * @param <S> - тип идентификатора состояния
 * @param <E> - тип идентификатора события
 *
 * @author Aliaksandr Bandarchyk
 */
public interface State<S, E> {

	/**
	 * Метод возвращает переход {@link Transition} для текущего управляющего состояния
	 * если переданное сообщение {@link StateMachineMessage} поддерживается и логика {@link Guard}, ассоциированного
	 * с переходом возвращает {@code True}.
	 */
	Optional<Transition<S, E>> getSuitableTransition(StateMachineMessage<E> message, StateMachineDetails<S, E> context);

	/**
	 * Запуск действий на входе в управляющее состояние.
	 */
	void onEnter(StateMachineDetails<S, E> context);

	/**
	 * Запуск действий на выходе из управляющего состояния.
	 */
	void onExit(StateMachineDetails<S, E> context);

	/**
	 * Вернет ID управляющего состояния.
	 */
	S getId();

	/**
	 * {@code True} - если пришедшее сообщение {@link StateMachineMessage} может быть отложено в текущем состоянии.
	 */
	boolean canBeDeferred(StateMachineMessage<E> message);

}
