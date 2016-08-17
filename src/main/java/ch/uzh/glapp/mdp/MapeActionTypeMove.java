package ch.uzh.glapp.mdp;

import java.util.ArrayList;
import java.util.List;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.oo.state.generic.DeepOOState;
import burlap.mdp.core.state.State;
import ch.uzh.glapp.MapeUtils;

import static ch.uzh.glapp.mdp.MapeWorld.*;

public class MapeActionTypeMove implements ActionType {

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
		
			for (String newProvider : PROVIDER_LIST) {
				for (String newRegion : REGION_LIST) {
					for (String newTier : TIER_LIST) {
						
						// check if a host from the new provider in the new region and new tier is available
						if (MapeUtils.isHostAvailable(newProvider, newRegion, newTier)) {
							
//							System.out.println("Checking provider: " + newProvider + ", region: " + newRegion + ", tier: " + newTier);
						
							// Only generate action to move cell to higher tier host. This reduce the applicable actions from 35 to 12
							// given the following conditions:
							// (1) there are 4 available providers, 3 available tiers and 3 available regions to choose from
							// (2) current host is at tier 2
							if (MapeUtils.isNewTierHigherOrEqual(currentTier, newTier)) {
//							if (!newProvider.equals(currentProvider) || !newRegion.equals(currentRegion) || !newTier.equals(currentTier)) {
								actionList.add(new MapeActionMove(cellName, newProvider, newRegion, newTier));
							} else {
//								System.out.println("invalid action");
							}
						}
					}
				}
			}
		}
		
		System.out.println("Size of move action list: " + actionList.size());
		
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
