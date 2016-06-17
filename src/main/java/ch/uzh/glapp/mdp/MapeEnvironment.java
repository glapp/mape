package ch.uzh.glapp.mdp;

import burlap.oomdp.core.Domain;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.states.MutableState;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.environment.Environment;
import burlap.oomdp.singleagent.environment.EnvironmentOutcome;
import ch.uzh.glapp.SailsRetriever;
import ch.uzh.glapp.model.Cell;

import java.util.List;

public class MapeEnvironment implements Environment {

	protected Domain domain;
	protected State curState;

	public MapeEnvironment (Domain domain) {
		this.domain = domain;
	}

	@Override
	public State getCurrentObservation() {

		curState = new MutableState();

		List<Cell> cells = new SailsRetriever().getCellInfo();
		for (Cell cell : cells) {
//			curState.addObject(new MutableObjectInstance());

			System.out.println(cell.getHost().getLabels().getProvider());

		}
		return curState;
	}

	@Override
	public EnvironmentOutcome executeAction(GroundedAction groundedAction) {
		// send action to Sails
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
