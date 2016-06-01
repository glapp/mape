package ch.uzh.glapp.mdp;

import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.environment.Environment;
import burlap.oomdp.singleagent.environment.EnvironmentOutcome;

public class MAPEenvironment implements Environment {
	@Override
	public State getCurrentObservation() {
		return null;
	}

	@Override
	public EnvironmentOutcome executeAction(GroundedAction groundedAction) {
		return null;
	}

	@Override
	public double getLastReward() {
		return 0;
	}

	@Override
	public boolean isInTerminalState() {
		return false;
	}

	@Override
	public void resetEnvironment() {

	}
}
