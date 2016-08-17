package ch.uzh.glapp.mdp;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.oo.state.generic.DeepOOState;
import burlap.mdp.core.state.State;
import ch.uzh.glapp.model.ObjectForMdp;
import ch.uzh.glapp.model.Violation;
import ch.uzh.glapp.model.sails.MdpTriggerObject;

import java.util.ArrayList;
import java.util.List;

import static ch.uzh.glapp.mdp.MapeWorld.*;

public class MapeActionTypeHeuristic implements ActionType {

	@Override
	public List<Action> allApplicableActions(State state) {
		
		List<Action> actionList = new ArrayList<Action>();
		
		// get violation details
		MdpTriggerObject mdpTriggerObject = BasicBehaviorMape.getMdpTriggerObject();
			
			// check how many violations
			double worstHealthiness = 0; // the worst healthiness value = the most negative healthiness value
			int worstHealthinessIndex = -1;
			int i = 0;
			for (Violation v : mdpTriggerObject.getViolationList()) {
				if (v.getWeightedHealthiness() < worstHealthiness) {
					worstHealthiness = v.getWeightedHealthiness();
					worstHealthinessIndex = i;
				}
				++i;
			}
			
			
			String violation = "";
//			if (mdpTriggerObject.getViolationList().size() == 1) {
			violation = mdpTriggerObject.getViolationList().get(worstHealthinessIndex).getMetric();
//			}
			
			// get the cell ID of the violating cell
			// if there are multiple violation, get the one with the worst healthiness value
			ObjectInstance cell = ((DeepOOState)state).object(mdpTriggerObject.getViolationList().get(worstHealthinessIndex).getCellId());
			
			// String cellName, String provider, String region, String tier
			String cellName = ((MapeCell)cell).name();
			String currentProvider = ((MapeCell)cell).getProvider();
			String currentRegion = ((MapeCell)cell).getRegion();
			String currentTier = ((MapeCell)cell).getTier();
			int currentNumOfCells = ((MapeCell)cell).getCells();
			
			// default action: create another cell on the machine with same specification (provider, region, tier)
			if (violation.equals("container_cpu_usage_seconds_total")) { // if CPU violation => move to bigger machine (higher tier)
				if (currentTier.equals(TIER1)) {
					if (MdpUtils.isHostAvailable(currentProvider, currentRegion, TIER2)) {
						actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER2));
					} else if (MdpUtils.isHostAvailable(currentProvider, currentRegion, TIER3)) {
						actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER3));
					} else {
						actionList.add(new MapeActionCreate(cellName, currentProvider, currentRegion, currentTier));
					}
				} else if (currentTier.equals(TIER2)) {
					if (MdpUtils.isHostAvailable(currentProvider, currentRegion, TIER3)) {
						actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER3));
					} else {
						actionList.add(new MapeActionCreate(cellName, currentProvider, currentRegion, currentTier));
					}
				} else if (currentTier.equals(TIER3)) {
					actionList.add(new MapeActionCreate(cellName, currentProvider, currentRegion, currentTier));
				}
			} else if (violation.equals("memory_utilization")) { // if memory violation => move to bigger machine, other checking to decide if to create another cell
				if (currentTier.equals(TIER1)) {
					if (MdpUtils.isHostAvailable(currentProvider, currentRegion, TIER2)) {
						actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER2));
					} else if (MdpUtils.isHostAvailable(currentProvider, currentRegion, TIER3)) {
						actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER3));
					} else {
						actionList.add(new MapeActionCreate(cellName, currentProvider, currentRegion, currentTier));
					}
				} else if (currentTier.equals(TIER2)) {
					if (MdpUtils.isHostAvailable(currentProvider, currentRegion, TIER3)) {
						actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER3));
					} else {
						actionList.add(new MapeActionCreate(cellName, currentProvider, currentRegion, currentTier));
					}
				} else if (currentTier.equals(TIER3)) {
					actionList.add(new MapeActionCreate(cellName, currentProvider, currentRegion, currentTier));
				}
			} else if (violation.equals("money_spent")) { // TODO: change the metric name to "cost" once the front end is updated
				// if there is cost violation
				// => if it is possible to move to a smaller machine (lower tier), move to a smaller machine
				// => else if there are more than 2 cells, remove 1 cell
				
				if (currentTier.equals(TIER3)) {
					if (MdpUtils.isHostAvailable(currentProvider, currentRegion, TIER2)) {
						actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER2));
					} else if (MdpUtils.isHostAvailable(currentProvider, currentRegion, TIER1)) {
						actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER1));
					} else if (currentNumOfCells > 1) {
						actionList.add(new MapeActionRemove(cellName));
					}
				} else if (currentTier.equals(TIER2)) {
					if (MdpUtils.isHostAvailable(currentProvider, currentRegion, TIER1)) {
						actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER1));
					} else if (currentNumOfCells > 1) {
						actionList.add(new MapeActionRemove(cellName));
					}
				} else if (currentTier.equals(TIER1)) {
					if (currentNumOfCells > 1) {
						actionList.add(new MapeActionRemove(cellName));
					}
				}
			}
//		}
		
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
