package alex.band.statemachine;

import alex.band.statemachine.context.StateMachineContext;
import alex.band.statemachine.state.State;

/**
 *
 * @param <S> - тип идентификатора состояния
 * @param <E> - тип идентификатора события
 *
 * @author Aliaksandr Bandarchyk
 */
public interface StateMachineDetails<S, E> {

	boolean isRunning();

	State<S, E> getCurrentState();

	StateMachineContext getContext();

}
