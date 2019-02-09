package alex.band.statemachine.builder;

import java.util.Set;

import alex.band.statemachine.StateMachine;
import alex.band.statemachine.state.StateAction;

/**
 * Конфигуратор состояний {@link State} конечного автомата {@link StateMachine}
 *
 * @param <S> - тип идентификатора состояния
 * @param <E> - тип идентификатора события
 *
 * @author Aliaksandr Bandarchyk
 */
public interface StatesConfigurer<S, E> {

	/**
	 * Помечает состояние как стартовое. В конфигурации конечного автомата допустимо только одно стартовое состояние.
	 */
	StatesConfigurer<S, E> asInitial();

	/**
	 * Помечает состояние как финальное (терминальное). В конфигурации конечного автомата допустимо только одно финальное состояние.
	 */
	StatesConfigurer<S, E> asFinal();

	/**
	 * Задает набор действий {@link StateAction} для конфигурируемого состояния
	 */
	StatesConfigurer<S, E> withActions(Set<StateAction<S, E>> actions);

	/**
	 * Задает действие {@link StateAction} для конфигурируемого состояния
	 */
	StatesConfigurer<S, E> withAction(StateAction<S, E> action);

	/**
	 * Задает отложенное событие для конфигурируемого состояния
	 */
	StatesConfigurer<S, E> withDeferredEvent(E deferredEvent);

	/**
	 * Задает набор отложенных событий для конфигурируемого состояния
	 */
	StatesConfigurer<S, E> withDeferredEvents(Set<E> deferredEvents);

}
