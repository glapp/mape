package ch.uzh.glapp.mdp2;

import burlap.mdp.core.Domain;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.environment.Environment;
import burlap.mdp.singleagent.environment.EnvironmentOutcome;
import ch.uzh.glapp.SailsRetriever;
import ch.uzh.glapp.model.Cell;
import ch.uzh.glapp.model.ObjectForMdp;

import java.util.List;

import static ch.uzh.glapp.mdp2.MapeWorld.*;


public class MapeEnvironment2 implements Environment {

	protected ObjectForMdp objectForMpd;
	protected final ThreadLocal<Domain> domain = new ThreadLocal<>();
	protected MapeState curState;
	protected MapeState nextState;

	public MapeEnvironment2(Domain domain, ObjectForMdp objectForMpd) {
		this.domain.set(domain);
		this.objectForMpd = objectForMpd;
	}

	@Override
	public State currentObservation() {
		curState = new MapeState();
		List<Cell> cells = new SailsRetriever().getCellInfo();
		System.out.println("getCurrentObservation - Size of cells list: "+cells.size());
		int countCell = 0;

		// count cells in organ
		for (Cell cell : cells) {
//			System.out.println(cell.getOrganId().getId() + " -- " + objectForMpd.getOrganId());
			if ((cell.getOrganId().getId()).equals(objectForMpd.getOrganId())){
				countCell++;
			}
		}
		System.out.println("Cells in Organ: " + countCell);

		for (Cell cell : cells) {
//			curState.set(VAR_VIOLATED_POLICY, policy);
			curState.set(VAR_PROVIDER, cell.getHost().getLabels().getProvider());
			curState.set(VAR_REGION, cell.getHost().getLabels().getRegion());
			curState.set(VAR_TIER, cell.getHost().getLabels().getTier());
//			curState.set(VAR_CELLS, cell.getHost().getLabels().adsf);
//			curState.set(VAR_PROXY_PROVIDER, cell.getHost().getLabels().adsf);
//			curState.set(VAR_PROXY_REGION, cell.getHost().getLabels().adsf);
		}

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
