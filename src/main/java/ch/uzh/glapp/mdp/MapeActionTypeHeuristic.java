package ch.uzh.glapp.mdp;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.oo.state.generic.DeepOOState;
import burlap.mdp.core.state.State;
import ch.uzh.glapp.model.ObjectForMdp;

import java.util.ArrayList;
import java.util.List;

import static ch.uzh.glapp.mdp.MapeWorld.*;

public class MapeActionTypeHeuristic implements ActionType {

	@Override
	public List<Action> allApplicableActions(State state) {
		
		List<Action> actionList = new ArrayList<Action>();

		List<ObjectInstance> cells = ((DeepOOState)state).objectsOfClass(CLASS_CELL);
		for (ObjectInstance cell : cells) {
			// String cellName, String provider, String region, String tier
			
			String cellName = ((MapeCell)cell).name();
			String currentProvider = ((MapeCell)cell).getProvider();
			String currentRegion = ((MapeCell)cell).getRegion();
			String currentTier = ((MapeCell)cell).getTier();
			
			// get violation details
			
			ObjectForMdp objectForMdp = BasicBehaviorMape.getObjectForMdp();
			String violation = objectForMdp.getMetric();
			
			// if CPU violation => move to bigger machine
			if (violation.equals("container_cpu_usage_seconds_total")) {
				if (currentTier.equals(TIER1)) {
					actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER2));
				} else if (currentTier.equals(TIER2)) {
					actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER3));
				} else if (currentTier.equals(TIER3)) {
					actionList.add(new MapeActionCreate(cellName, currentProvider, currentRegion, TIER3));
				}
			} else if (violation.equals("memory_utilization")) {
				if (currentTier.equals(TIER1)) {
					actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER2));
				} else if (currentTier.equals(TIER2)) {
					actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER3));
				} else if (currentTier.equals(TIER3)) {
					actionList.add(new MapeActionCreate(cellName, currentProvider, currentRegion, TIER3));
				}
			}
			
			// if memory violation => move to bigger machine, other checking to decide if to create another cell
			// if network violation(packet dropped), ?
			// if cost, ?
			
		
		}
		
		System.out.println("Size of heuristic action list: " + actionList.size());
		
		return actionList;
	}

	@Override
	public Action associatedAction(String arg0) {
		String[] params = arg0.split(",");
//		if (params.length == 6) {
		if (params.length == 4) { // without proxy information
			// 0: cell name
			// 1: provider
			// 2: region
			// 3: tier
			// 4: proxy_provider
			// 5: proxy_region
//			return new MapeActionMove(params[0], params[1], params[2], params[3], params[4], params[5]);
			return new MapeActionMove(params[0], params[1], params[2], params[3]);
		} else {
			return null;
		}
	}

	@Override
	public String typeName() {
		return ACTION_MOVE;
	}

}
