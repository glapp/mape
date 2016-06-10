package ch.uzh.glapp.mdp;

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
	@Override
	public State getCurrentObservation() {

		State s = new MutableState();

		List<Cell> cells = new SailsRetriever().getCellInfo();
		for (Cell cell : cells) {
			s.addObject(new MutableObjectInstance());

			System.out.println(cell.getHost().getLabels().getProvider());

		}
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
