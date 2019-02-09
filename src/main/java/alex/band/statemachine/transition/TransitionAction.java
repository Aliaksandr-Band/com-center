package alex.band.statemachine.transition;

import alex.band.statemachine.StateMachineDetails;
import alex.band.statemachine.message.StateMachineMessage;

/**
 * Действие, ассоциированное с переходом {@link Transition} между состояниями {@link State}
 *
 * @author Aliaksandr Bandarchyk
 */
public interface TransitionAction<S, E> {

	/**
	 * Выполнение действия, ассоциированного с переходом.
	 */
	void execute(StateMachineMessage<E> message, StateMachineDetails<S, E> context);

}
