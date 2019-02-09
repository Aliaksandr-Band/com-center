package alex.band.statemachine;

/**
 * Действие, выполняемое на старте конечного автомата {@link StateMachine}
 *
 * @author Aliaksandr Bandarchyk
 */
public interface StateMachineStartAction<S, E> {

	/**
	 * Выполнение действия на старте конечного автомата.
	 */
	void onStart(StateMachineDetails<S, E> context);

}
