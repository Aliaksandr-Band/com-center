package alex.band.statemachine.builder;

import java.util.Set;

import alex.band.statemachine.transition.Guard;
import alex.band.statemachine.transition.TransitionAction;

/**
 * Конфигуратор внешних переходов {@link Transition} между состояниями {@link State} конечного автомата {@link StateMachine}. S1->S2.
 *
 * @param <S> - тип идентификатора состояния
 * @param <E> - тип идентификатора события
 *
 * @author Aliaksandr Bandarchyk
 */
public interface ExternalTransitionConfigurer<S, E> {

	/**
	 * Задает целевое состояние перехода
	 */
	ExternalTransitionConfigurer<S, E> to(S state);

	/**
	 * Задает событие, которое инициирует переход
	 */
	ExternalTransitionConfigurer<S, E> by(E event);

	/**
	 * Задает {@link Guard} для оценки возможности перехода
	 */
	ExternalTransitionConfigurer<S, E> guardedBy(Guard<S, E> guard);

	/**
	 * Задает {@link TransitionAction}, которое будет выполнено во время перехода
	 */
	ExternalTransitionConfigurer<S, E> withAction(TransitionAction<S, E> action);

	/**
	 * Задает множество {@link TransitionAction}, которые будут выполнены во время перехода
	 */
	ExternalTransitionConfigurer<S, E> withActions(Set<TransitionAction<S, E>> actions);

}
