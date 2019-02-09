package alex.band.statemachine.state;

import alex.band.statemachine.StateMachineDetails;

/**
 * Действия, ассоциированные с входом и выходом из управляющего состояния {@link State}
 *
 * @author Aliaksandr Bandarchyk
 */
public interface StateAction<S, E> {

	/**
	 * Действие на входе в состояние.
	 */
	void onEnter(StateMachineDetails<S, E> stateMachineDetails);

	/**
	 * Действие на выходе из состояния.
	 */
	void onExit(StateMachineDetails<S, E> stateMachineDetails);

}
