package alex.band.statemachine.message;

import com.google.common.base.Optional;

import alex.band.statemachine.StateMachine;
import alex.band.statemachine.builder.StateMachineBuilder;

/**
 * Сообщение для конечного автомата {@link StateMachine}. Содержит идентификатор события {@link #getEvent()} и полезную нагрузку {@link #getPayload()}.
 *
 * @param <E> - тип идентификатора события
 *
 * @author Aliaksandr Bandarchyk
 */
public interface StateMachineMessage<E> {

	/**
	 * Событие на которое должен реагировать конечный автомат.
	 *
	 * <p>Назначение события задается конфигурацией конкретного конечного автомата при помощи {@link StateMachineBuilder}.
	 */
	E getEvent();

	/**
	 * Полезная нагрузка, которая может присутствовать в сообщении.
	 */
	Optional<Object> getPayload();

}
