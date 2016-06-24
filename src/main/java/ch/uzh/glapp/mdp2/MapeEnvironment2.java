package ch.uzh.glapp.mdp2;

import burlap.mdp.core.Domain;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.environment.Environment;
import burlap.mdp.singleagent.environment.EnvironmentOutcome;


public class MapeEnvironment2 implements Environment {

	protected final ThreadLocal<Domain> domain = new ThreadLocal<>();
	protected MapeState curState;
	protected MapeState nextState;

	public MapeEnvironment2 (Domain domain) {
		this.domain.set(domain);
	}

	@Override
	public State currentObservation() {
		return null;
	}

	@Override
	public EnvironmentOutcome executeAction(Action action) {
		return null;
	}

	@Override
	public double lastReward() {
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
