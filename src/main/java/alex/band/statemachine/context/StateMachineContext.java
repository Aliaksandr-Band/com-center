package alex.band.statemachine.context;

import alex.band.statemachine.StateMachine;
import alex.band.statemachine.StateMachineStartAction;
import alex.band.statemachine.StateMachineStopAction;
import alex.band.statemachine.state.StateAction;
import alex.band.statemachine.transition.TransitionAction;

/**
 * Структура данных для хранения контекстной информации необходимой в процессе работы конечного автомата {@link StateMachine}
 *
 * <p>Данные хранятся в виде пар {@code (key, value)}. Наполнение определяется нуждами конкретной задачи.
 *
 * <p>{@code StateMachineContext} выступает в роли {@code shared} объекта,
 * который доступен всем компонентам, задающим поведение конечного автомата.
 * А именно: {@link StateMachineStartAction}, {@link StateMachineStopAction}, {@link Guard}, {@link StateAction}, {@link TransitionAction}
 *
 * @author Aliaksandr Bandarchyk
 */
public interface StateMachineContext {

	/**
	 * Возвращает значение по ключу либо null
	 */
	Object getValue(String key);

	/**
	 * Задает значение и ключ
	 */
	void setValue(String key, Object value);

	/**
	 * Удаляет значение по ключу.
	 *
	 * @return удаленное значение либо null
	 */
	Object removeValue(String key);

}
