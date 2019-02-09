package alex.band.statemachine.builder;

import alex.band.statemachine.StateMachineStartAction;
import alex.band.statemachine.StateMachineStopAction;

/**
 * Конфигуратор стартовых {@link StateMachineStartAction} и терминальных {@link StateMachineStopAction} действий
 * конечного автомата {@link StateMachine}
 *
 * @param <S> - тип идентификатора состояния
 * @param <E> - тип идентификатора события
 *
 * @author Aliaksandr Bandarchyk
 */
public interface StartStopActionsConfigurer<S, E> {

	/**
	 * Задает набор стартовых {@link StateMachineStartAction} действий конечного автомата
	 */
	@SuppressWarnings("unchecked")
	StartStopActionsConfigurer<S, E> onStart(StateMachineStartAction<S, E> ...actions);

	/**
	 * Задает набор финальных {@link StateMachineStopAction} действий конечного автомата
	 */
	@SuppressWarnings("unchecked")
	StartStopActionsConfigurer<S, E> onStop(StateMachineStopAction<S, E> ...actions);

}
