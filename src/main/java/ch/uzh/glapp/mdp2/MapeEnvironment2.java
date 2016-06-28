package ch.uzh.glapp.mdp2;

import burlap.mdp.core.Domain;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.oo.state.generic.GenericOOState;
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
	protected GenericOOState curState;
	protected GenericOOState nextState;

	public MapeEnvironment2(Domain domain, ObjectForMdp objectForMpd) {
		this.domain.set(domain);
		this.objectForMpd = objectForMpd;
	}

	@Override
	public State currentObservation() {
		curState = new MapeState();
		List<Cell> cells = new SailsRetriever().getCellInfo();
		System.out.println("getCurrentObservation - Size of cells list (all cells in app): "+cells.size());

		String organId = objectForMpd.getOrganId();
		MapeUtils mapeUtils = new MapeUtils();

		int countCellsInOrgan = mapeUtils.countCellsInOrgan(cells, organId);


		System.out.println("##### " + objectForMpd.getPolicy());

		for (Cell cell : cells) {
			if (objectForMpd.getCellId().equals(cell.getId())) {
				curState.set(VAR_VIOLATED_POLICY, objectForMpd.getPolicy());
				curState.set(VAR_PROVIDER, cell.getHost().getLabels().getProvider());
				curState.set(VAR_REGION, cell.getHost().getLabels().getRegion());
				curState.set(VAR_TIER, cell.getHost().getLabels().getTier());
				curState.set(VAR_CELLS, countCellsInOrgan);
//				curState.set(VAR_PROXY_PROVIDER, cell.getHost().getLabels().adsf);
//				curState.set(VAR_PROXY_REGION, cell.getHost().getLabels().adsf);

//				System.out.println(curState.violated_policy);
//				System.out.println(curState.provider);
//				System.out.println(curState.region);
//				System.out.println(curState.tier);
//				System.out.println(curState.cells);
			}
		}

		return curState;
	}

	@Override
	public EnvironmentOutcome executeAction(Action action) {

		nextState = curState.copy();
		EnvironmentOutcome envOutCome = new EnvironmentOutcome(curState, action, nextState, 0.8, true);
		return envOutCome;
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
