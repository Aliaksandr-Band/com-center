package alex.band.statemachine.builder;

import java.util.Set;

import alex.band.statemachine.transition.Guard;
import alex.band.statemachine.transition.TransitionAction;

/**
 * Конфигуратор внутренних переходов {@link Transition} состояния {@link State} конечного автомата {@link StateMachine} S1->S1.
 *
 * @param <S> - тип идентификатора состояния
 * @param <E> - тип идентификатора события
 *
 * @author Aliaksandr Bandarchyk
 */
public interface InternalTransitionConfigurer<S, E> {

	/**
	 * Задает событие, которое инициирует переход
	 */
	InternalTransitionConfigurer<S, E> by(E event);

	/**
	 * Задает {@link Guard} для оценки возможности перехода
	 */
	InternalTransitionConfigurer<S, E> guardedBy(Guard<S, E> guard);

	/**
	 * Задает {@link TransitionAction}, которое будет выполнено во время перехода
	 */
	InternalTransitionConfigurer<S, E> withAction(TransitionAction<S, E> action);

	/**
	 * Задает множество {@link TransitionAction}, которые будут выполнены во время перехода
	 */
	InternalTransitionConfigurer<S, E> withActions(Set<TransitionAction<S, E>> actions);

}
