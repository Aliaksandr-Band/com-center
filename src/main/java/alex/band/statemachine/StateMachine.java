package alex.band.statemachine;

import alex.band.statemachine.listener.StateMachineListener;
import alex.band.statemachine.message.StateMachineMessage;

/**
 * Интерфейс, предоставляющий базовый набор методов по работе с конечным автоматом (КА).
 *
 * <p>Содержит методы:
 * <ul><li>Запуска и остановки КА - {@link #start()}, {@link #stop()}.</li>
 * <li>управления жизненным циклом КА посредством событий и сообщений - {@link #accept(Object)}, {@link #accept(StateMachineMessage)}.</li>
 * <li>регистрации слушателей работы КА - {@link #addListener(StateMachineListener)}, {@link #removeListener(StateMachineListener)}.</li></ul>
 *
 * @param <S> - тип идентификатора состояния
 * @param <E> - тип идентификатора события
 *
 * @author Aliaksandr Bandarchyk
 */
public interface StateMachine<S, E> extends StateMachineDetails<S, E> {

	/**
	 * Запуск конечного автомата.
	 */
	void start();

	/**
	 * Остановка конечного автомата.
	 */
	void stop();

	/**
	 * Отправка события на обработку конечным автоматом.
	 */
	boolean accept(E event);

	/**
	 * Отправка сообщения на обработку конечным автоматом.
	 */
	boolean accept(StateMachineMessage<E> message);

	/**
	 * Регистрация слушателя жизненного цикла конечным автоматом.
	 */
	void addListener(StateMachineListener<S, E> listener);

	/**
	 * Удаления слушателя жизненного цикла конечным автоматом.
	 */
	void removeListener(StateMachineListener<S, E> listener);

}
