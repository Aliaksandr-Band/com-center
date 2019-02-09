package alex.band.statemachine.transition;

import alex.band.statemachine.StateMachineDetails;
import alex.band.statemachine.message.StateMachineMessage;

/**
 * Защита перехода {@link Transition} между управляющими состояниями {@link State}.
 *
 * <p>Переход разрешен, если {@link #evaluate(StateMachineMessage, StateMachineDetails)} вернет {@code True}
 *
 * @param <E> - тип идентификатора события
 *
 * @author Aliaksandr Bandarchyk
 */
public interface Guard<S, E> {

	/**
	 * Оценка возможности осуществления перехода.
	 *
	 * <p>{@code True} - переход разрешен, {@code False} - переход запрещен.
	 */
	boolean evaluate(StateMachineMessage<E> message, StateMachineDetails<S, E> context);

}
