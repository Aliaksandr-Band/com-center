package alex.band.statemachine.builder;

import java.util.Set;

import alex.band.statemachine.StateMachine;
import alex.band.statemachine.StateMachineStartAction;
import alex.band.statemachine.StateMachineStopAction;
import alex.band.statemachine.transition.Transition;

/**
 * {@code Builder} для построения {@link StateMachine} заданной конфигурации
 *
 * <p>Позволяет конфигурировать следующие компоненты конечного автомата (КА):
 * <ul><li>Стартовые и терминальные действия КА - {@link #defineStartStopActions()}</li>
 * <li>Управляющие состояния - {@link #defineState(Object)}, {@link #defineStates(Set)}</li>
 * <li>Внешние и внутренние переходы - {@link #defineExternalTransitionFor(Object)}, {@link #defineInternalTransitionFor(Object)}</li></ul>
 *
 * @param <S> - тип идентификатора состояния
 * @param <E> - тип идентификатора события
 *
 * @author Aliaksandr Bandarchyk
 */
public interface StateMachineBuilder<S, E> {

	/**
	 * Конфигурация стартовых {@link StateMachineStartAction} и терминальных {@link StateMachineStopAction}
	 * конечного автомата {@link StateMachine}
	 */
	StartStopActionsConfigurer<S, E> defineStartStopActions();

	/**
	 * Конфигурация нового состояния {@link State} конечного автомата {@link StateMachine}
	 */
	StatesConfigurer<S, E> defineState(S state);

	/**
	 * Создание набора состояний {@link State} конечного автомата {@link StateMachine}
	 */
	void defineStates(Set<S> states);

	/**
	 * Конфигурация внешних переходов {@link Transition} конечного автомата {@link StateMachine}
	 */
	ExternalTransitionConfigurer<S, E> defineExternalTransitionFor(S sourceState);

	/**
	 * Конфигурация внутренних переходов {@link Transition} конечного автомата {@link StateMachine}
	 */
	InternalTransitionConfigurer<S, E>  defineInternalTransitionFor(S sourceState);

	/**
	 * Создание {@link StateMachine} заданной конфигурации
	 */
	StateMachine<S, E> build();

}
