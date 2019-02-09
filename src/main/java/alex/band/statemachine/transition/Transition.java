package alex.band.statemachine.transition;

import java.util.Set;

import com.google.common.base.Optional;

import alex.band.statemachine.StateMachine;

/**
 * Описание перехода между управляющими состояниями {@link State} конечного автомата {@link StateMachine}.
 *
 * <p>Интерфейс описывает такие компоненты перехода как:
 * <ul><li>Исходное состояние {@link #getSource()}</li>
 * <li>Целевое состояние {@link #getTarget()}</li>
 * <li>Событие, вызывающее переход {@link #getEvent()}</li>
 * <li>Разрешение на переход {@link #getGuard()}</li>
 * <li>Действия перехода {@link #getActions()}</li>
 * <li>Тип перехода {@link #isExternal()}</li>
 * </ul>
 *
 * @param <S> - тип идентификатора состояния
 * @param <E> - тип идентификатора события
 *
 * @author Aliaksandr Bandarchyk
 */
public interface Transition<S, E> {

	/**
	 * Возвращает исходное состояние перехода.
	 */
	S getSource();

	/**
	 * Возвращает целевое состояние перехода.
	 */
	Optional<S> getTarget();

	/**
	 * Возвращает событие, инициирующее переход.
	 */
	E getEvent();

	/**
	 * Возвращает {@link Guard} защиту (разрешение) перехода.
	 */
	Optional<Guard<S, E>> getGuard();

	/**
	 * Возвращает набор действий {@link TransitionAction}, связанных с переходом.
	 */
	Set<TransitionAction<S, E>> getActions();

	/**
	 * Возвращает тип перехода: (внешний или внутренний)
	 */
	boolean isExternal();

}
