package ch.uzh.glapp.mdp;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.oo.state.generic.DeepOOState;
import burlap.mdp.core.state.State;
import ch.uzh.glapp.MapeUtils;
import ch.uzh.glapp.model.ObjectForMdp;
import ch.uzh.glapp.model.Violation;
import ch.uzh.glapp.model.sails.MdpTriggerObject;
import ch.uzh.glapp.model.sails.hostinfo.Host;
import ch.uzh.glapp.model.sails.hostinfo.Labels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ch.uzh.glapp.mdp.MapeWorld.*;

public class MapeActionTypeHeuristic implements ActionType {

	@Override
	public List<Action> allApplicableActions(State state) {
		
		List<Action> actionList = new ArrayList<Action>();
		
		// get violation details
		MdpTriggerObject mdpTriggerObject = BasicBehaviorMape.getMdpTriggerObject();
		
		ArrayList<Violation> violations = new ArrayList<Violation>(mdpTriggerObject.getViolationList());
			
			// check how many violations
			double worstHealthiness = 0; // the worst healthiness value = the most negative healthiness value
			int worstHealthinessIndex = -1;
			int i = 0;
			for (Violation v : violations) {
				if (v.getWeightedHealthiness() < worstHealthiness) {
					worstHealthiness = v.getWeightedHealthiness();
					worstHealthinessIndex = i;
				}
				i++;
			}
			
//			MapeUtils.printViolation(violations);
			
			String violatedMetric = "";
			// get the name of the violated metric
			// if there are multiple violation, get the one with the worst healthiness value
			Violation worstViolation = violations.get(worstHealthinessIndex);
			violatedMetric = worstViolation.getMetric();
			
			// get the cell ID of the violating cell
			ObjectInstance cell = ((DeepOOState)state).object(worstViolation.getCellId());
			
			// String cellName, String provider, String region, String tier
			String cellName = ((MapeCell)cell).name();
			String currentProvider = ((MapeCell)cell).getProvider();
			String currentRegion = ((MapeCell)cell).getRegion();
			String currentTier = ((MapeCell)cell).getTier();
			int currentNumOfCells = ((MapeCell)cell).getCells();
			
			boolean actionFound = false;
			
			// if the current cell is already at highest tier (tier 3), create another cell on the same machine
			// else, create another cell on any higher tier machine (different provider, region possible)
			if (violatedMetric.equals("container_cpu_usage_seconds_total")) { // if CPU violation => move to bigger machine (higher tier)
				
				if (currentTier.equals(TIER3)) {
					actionList.add(new MapeActionCreate(cellName, currentProvider, currentRegion, currentTier));
					actionFound = true;
				} else {
					for (String newProvider : PROVIDER_LIST) {
						for (String newRegion : REGION_LIST) {
							for (String newTier : TIER_LIST) {
								if (!actionFound && MapeUtils.isNewTierHigher(currentTier, newTier) && MapeUtils.isHostAvailable(newProvider, newRegion, newTier)) {
									actionList.add(new MapeActionMove(cellName, newProvider, newRegion, newTier));
									actionFound = true;
								}
							}
						}
					}
				}
				
//				if (currentTier.equals(TIER1)) {
//					if (MapeUtils.isHostAvailable(currentProvider, currentRegion, TIER2)) {
//						actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER2));
//					} else if (MapeUtils.isHostAvailable(currentProvider, currentRegion, TIER3)) {
//						actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER3));
//					} else {
//						actionList.add(new MapeActionCreate(cellName, currentProvider, currentRegion, currentTier));
//					}
//				} else if (currentTier.equals(TIER2)) {
//					if (MapeUtils.isHostAvailable(currentProvider, currentRegion, TIER3)) {
//						actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER3));
//					} else {
//						actionList.add(new MapeActionCreate(cellName, currentProvider, currentRegion, currentTier));
//					}
//				} else if (currentTier.equals(TIER3)) {
//					actionList.add(new MapeActionCreate(cellName, currentProvider, currentRegion, currentTier));
//				}
			} else if (violatedMetric.equals("memory_utilization")) { // if memory violation => move to bigger machine, other checking to decide if to create another cell
				if (currentTier.equals(TIER1)) {
					if (MapeUtils.isHostAvailable(currentProvider, currentRegion, TIER2)) {
						actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER2));
					} else if (MapeUtils.isHostAvailable(currentProvider, currentRegion, TIER3)) {
						actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER3));
					} else {
						actionList.add(new MapeActionCreate(cellName, currentProvider, currentRegion, currentTier));
					}
				} else if (currentTier.equals(TIER2)) {
					if (MapeUtils.isHostAvailable(currentProvider, currentRegion, TIER3)) {
						actionList.add(new MapeActionMove(cellName, currentProvider, currentRegion, TIER3));
					} else {
						actionList.add(new MapeActionCreate(cellName, currentProvider, currentRegion, currentTier));
					}
				} else if (currentTier.equals(TIER3)) {
					actionList.add(new MapeActionCreate(cellName, currentProvider, currentRegion, currentTier));
				}
			} else if (violatedMetric.equals("cost")) {
				
				// extract cost violations from the violation list
				ArrayList<Violation> tempViolations = new ArrayList<Violation>(violations);
				
				// sort the cost violation list by healthiness value in descending order
				MapeUtils.mergeSort(tempViolations);
				
				// remove any cost violation when the corresponding cell has another violation 
				// go through cost violations 1 by 1 until an action is found
				for (Violation v : tempViolations) {
					System.out.println("MapeActionTypeHeuristic: violation at cell (container ID: " + v.getContainerId() + "), metric: " + v.getMetric() + ", healthiness: " + v.getWeightedHealthiness());
					
					if (!actionFound) {
						ObjectInstance c = ((DeepOOState)state).object(v.getCellId());
						String cName = ((MapeCell)c).name();
						String cProvider = ((MapeCell)c).getProvider();
						String cRegion = ((MapeCell)c).getRegion();
						String cTier = ((MapeCell)c).getTier();
						int cNumOfCells = ((MapeCell)c).getCells();
						
						// if there is cost violation:
						// find the server with lower cost and move there
						String cheapestServer = MapeUtils.findCheapestServer(cProvider, cRegion, cTier);
						if (cheapestServer != null) {
							String[] serverDetails = cheapestServer.split("_");
							actionList.add(new MapeActionMove(cName, serverDetails[1], serverDetails[2], serverDetails[3].substring(4)));
							actionFound = true;
						}
					}
				}
			} else if (violatedMetric.equals("click_count")) {
				if (worstViolation.getAdditionalValue() == Double.POSITIVE_INFINITY) {
					Host targetHost = null;
					if (currentRegion.equals(US)) {
						targetHost = MapeUtils.findHostInRegion(EU);
					} else if (currentRegion.equals(EU)) {
						targetHost = MapeUtils.findHostInRegion(US);
					}
					actionList.add(new MapeActionCreate(cellName, targetHost.getLabels().getProvider(), targetHost.getLabels().getRegion(), targetHost.getLabels().getTier()));
				} else if (worstViolation.getAdditionalValue() > 500) {
					Host targetHost = MapeUtils.findHostInRegion(currentRegion);
					actionList.add(new MapeActionCreate(cellName, targetHost.getLabels().getProvider(), targetHost.getLabels().getRegion(), targetHost.getLabels().getTier()));
				} else if (worstViolation.getAdditionalValue() < 100 && currentNumOfCells > 1) {
					actionList.add(new MapeActionRemove(cellName));
				}
			}
//		}
		
		System.out.println("MapeActionTypeHeuristic: Size of heuristic action list: " + actionList.size());
		
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
