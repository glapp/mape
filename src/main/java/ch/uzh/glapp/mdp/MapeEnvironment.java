package ch.uzh.glapp.mdp;

import burlap.oomdp.core.Domain;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.MutableState;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.environment.Environment;
import burlap.oomdp.singleagent.environment.EnvironmentOutcome;
import ch.uzh.glapp.SailsRetriever;
import ch.uzh.glapp.model.Cell;

import java.util.List;

import static ch.uzh.glapp.mdp.MapeWorldDomain2.GEO;
import static ch.uzh.glapp.mdp.MapeWorldDomain2.TIER;

public class MapeEnvironment implements Environment {

	protected final ThreadLocal<Domain> domain = new ThreadLocal<>();
	protected State curState;
	protected State nextState;

	public MapeEnvironment (Domain domain) {
		this.domain.set(domain);
	}

	@Override
	public State getCurrentObservation() {

		curState = new MutableState();

		List<Cell> cells = new SailsRetriever().getCellInfo();
		System.out.println("getCurrentObservation - Size of cells list: "+cells.size());
		for (Cell cell : cells) {
			ObjectInstance cellObject = new MutableObjectInstance(domain.get().getObjectClass(MapeWorldDomain2.CLASS_CELL),"Organ_" + cell.getOrganId().getId() + "_Cell_" + cell.getId());
			cellObject.setValue(TIER, cell.getHost().getLabels().getTier());
			cellObject.setValue(GEO, cell.getHost().getLabels().getRegion());
			curState.addObject(cellObject);

		}
		return curState;
	}

	@Override
	public EnvironmentOutcome executeAction(GroundedAction groundedAction) {
		GroundedAction groundedActionCopy = groundedAction.copy();
		State curStateCopy = this.curState.copy();

		// send action to Sails
		// wait and get observation from Sails.

		System.out.println("Call from executeAction");
		nextState = getCurrentObservation();

		double rewardReceived = 0; // this shold come from the observation.
		boolean terminated = false; // whether the next state is a termial state or not. Since we run only 1 step this can be always false.

		EnvironmentOutcome outcome = new EnvironmentOutcome(curStateCopy, groundedActionCopy, nextState, rewardReceived, terminated);
		return outcome;
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
