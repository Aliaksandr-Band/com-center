package alex.band.statemachine;

/**
 * Действие, выполняемое во время остановки конечного автомата {@link StateMachine}
 *
 * @author Aliaksandr Bandarchyk
 */
public interface StateMachineStopAction<S, E> {

	/**
	 * Выполнение действия во время остановки конечного автомата.
	 */
	void onStop(StateMachineDetails<S, E> context);

}
