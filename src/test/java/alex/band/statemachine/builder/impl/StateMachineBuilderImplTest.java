package alex.band.statemachine.builder.impl;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import alex.band.statemachine.StateMachine;

@RunWith(MockitoJUnitRunner.class)
public class StateMachineBuilderImplTest {

	private static final String S1 = "S1";
	private static final String S2 = "S2";
	private static final String S3 = "S3";

	private static final String E1 = "E1";
	private static final String E2 = "E2";

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private StateMachineBuilderImpl<String, String> builder;

	@Before
	public void setUp() {
		builder = new StateMachineBuilderImpl<>();
	}

	@Test
	public void configurationWithoutStatesIsNotAllowed() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage(StateMachineBuilderImpl.THERE_ARE_NO_STATES_DEFINED);

		builder.defineInternalTransitionFor(S1).by(E1);
		builder.build();
	}

	@Test
	public void configurationWithoutInitialStateIsNotAllowed() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage(StateMachineBuilderImpl.INITIAL_STATE_IS_NOT_DEFINED);

		builder.defineState(S1);
		builder.defineInternalTransitionFor(S1).by(E1);
		builder.build();
	}

	@Test
	public void configurationWithoutFinalStateIsNotAllowed() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage(StateMachineBuilderImpl.FINAL_STATE_IS_NOT_DEFINED);

		builder.defineState(S1).asInitial();
		builder.defineState(S2);
		builder.defineInternalTransitionFor(S1).by(E1);
		builder.build();
	}

	@Test
	public void transitionWithUnkownSourceStateIsNotAllowed() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage(containsString(withoutPlaceholder(StateMachineBuilderImpl.UNKOWN_SOURCE_STATES_IN_TRANSITIONS)));
		expectedException.expectMessage(containsString(S3));

		builder.defineState(S1).asInitial();
		builder.defineState(S2).asFinal();
		builder.defineInternalTransitionFor(S3).by(E1);
		builder.build();
	}

	@Test
	public void transitionWithUnkownTargetStateIsNotAllowed() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage(containsString(withoutPlaceholder(StateMachineBuilderImpl.UNKOWN_TARGET_STATES_IN_TRANSITIONS)));
		expectedException.expectMessage(containsString(S3));

		builder.defineState(S1).asInitial();
		builder.defineState(S2).asFinal();
		builder.defineExternalTransitionFor(S1).to(S2).by(E1);
		builder.defineExternalTransitionFor(S1).to(S3).by(E2);
		builder.build();
	}

	@Test
	public void externalTransitionsWithNotDefinedTargetStateIsNotAllowed() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage(containsString(withoutPlaceholder(StateMachineBuilderImpl.EXTERNAL_TRANSITION_HAS_NO_TARGET_STATE)));

		builder.defineState(S1).asInitial();
		builder.defineState(S2).asFinal();
		builder.defineExternalTransitionFor(S1).by(E1);
		builder.build();
	}

	@Test
	public void externalTransitionsFromFinalStateIsNotAllowed() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage(containsString(withoutPlaceholder(StateMachineBuilderImpl.ILLEGAL_TRANSITION_FROM_FINAL_STATE)));

		builder.defineState(S1).asInitial();
		builder.defineState(S2).asFinal();
		builder.defineExternalTransitionFor(S1).to(S2).by(E1);
		builder.defineExternalTransitionFor(S2).to(S1).by(E1);
		builder.build();
	}

	@Test
	public void internalTransitionsFromFinalStateIsNotAllowed() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage(containsString(withoutPlaceholder(StateMachineBuilderImpl.ILLEGAL_TRANSITION_FROM_FINAL_STATE)));

		builder.defineState(S1).asInitial();
		builder.defineState(S2).asFinal();
		builder.defineExternalTransitionFor(S1).to(S2).by(E1);
		builder.defineInternalTransitionFor(S2).by(E1);
		builder.build();
	}

	@Test
	public void allStatesExceptInitialShouldHaveInboundTransition() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage(containsString(withoutPlaceholder(StateMachineBuilderImpl.STATES_WITHOUT_INBOUND_TRANSITION)));
		expectedException.expectMessage(containsString(S2));

		builder.defineState(S1).asInitial();
		builder.defineState(S2).asFinal();
		builder.build();
	}


	@Test
	public void allStatesExceptFinalShouldHaveOutboundTransition() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage(containsString(withoutPlaceholder(StateMachineBuilderImpl.STATES_WITHOUT_OUTBOUND_TRANSITION)));
		expectedException.expectMessage(containsString(S2));

		builder.defineState(S1).asInitial();
		builder.defineState(S2);
		builder.defineState(S3).asFinal();
		builder.defineExternalTransitionFor(S1).to(S2).by(E1);
		builder.defineExternalTransitionFor(S1).to(S3).by(E2);
		builder.build();
	}

	@Test
	public void equalStateCannotBeDefinedTwice() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage(containsString(withoutPlaceholder(StateMachineBuilderImpl.STATE_ALREADY_DEFINED)));
		expectedException.expectMessage(containsString(S1));

		builder.defineState(S1);
		builder.defineState(S1);
	}

	@Test
	public void initialStateCannotBeDefinedTwice() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage(containsString(withoutPlaceholder(StateMachineBuilderImpl.INITIAL_STATE_ALREADY_DEFINED)));

		builder.defineState(S1).asInitial();
		builder.defineState(S2).asInitial();
	}

	@Test
	public void finalStateCannotBeDefinedTwice() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage(containsString(withoutPlaceholder(StateMachineBuilderImpl.FINAL_STATE_ALREADY_DEFINED)));

		builder.defineState(S1).asFinal();
		builder.defineState(S2).asFinal();
	}

	@Test
	public void validConfigurationShouldProduceStateMachine() {
		builder.defineState(S1).asInitial();
		builder.defineState(S2);
		builder.defineState(S3).asFinal();
		builder.defineExternalTransitionFor(S1).to(S2).by(E1);
		builder.defineInternalTransitionFor(S2).by(E2);
		builder.defineExternalTransitionFor(S2).to(S3).by(E1);

		StateMachine<String, String> stateMachine = builder.build();
		stateMachine.start();
		assertThat(stateMachine.getCurrentState().getId(), equalTo(S1));

		stateMachine.accept(E1);
		assertThat(stateMachine.getCurrentState().getId(), equalTo(S2));

		stateMachine.accept(E2);
		assertThat(stateMachine.getCurrentState().getId(), equalTo(S2));

		stateMachine.accept(E1);
		assertThat(stateMachine.getCurrentState().getId(), equalTo(S3));
	}

	private String withoutPlaceholder(String str) {
		int placeHolderIndex = str.indexOf("%s");

		if (placeHolderIndex > 0) {
			return str.substring(0, placeHolderIndex - 1);
		}
		return str;
	}

}
