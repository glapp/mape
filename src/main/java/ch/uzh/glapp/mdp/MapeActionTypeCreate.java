package ch.uzh.glapp.mdp;

import static ch.uzh.glapp.mdp.MapeWorld.*;

import java.util.ArrayList;
import java.util.List;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.oo.state.generic.DeepOOState;
import burlap.mdp.core.state.State;

public class MapeActionTypeCreate implements ActionType {

	@Override
	public List<Action> allApplicableActions(State state) {
		List<Action> actionList = new ArrayList<Action>();
		
		List<ObjectInstance> cells = ((DeepOOState)state).objectsOfClass(CLASS_CELL);
		for (ObjectInstance cell : cells) {
			// String cellName, String provider, String region, String tier
			
			String cellName = ((MapeCell)cell).name();
//			String currentProvider = ((MapeCell)cell).getProvider();
//			String currentRegion = ((MapeCell)cell).getRegion();
			String currentTier = ((MapeCell)cell).getTier();
		
			for (String newProvider : PROVIDER_LIST) {
				for (String newRegion : REGION_LIST) {
					for (String newTier : TIER_LIST) {
						
						// Only generate action to create new cell at higher tier host. This reduce the applicable actions from 36 to 12
						// given the following conditions:
						// (1) there are 4 available providers, 3 available tiers and 3 available regions to choose from
						// (2) current host is at tier 2
						if (MapeUtils.isNewTierHigher(currentTier, newTier)) {
							actionList.add(new MapeActionCreate(cellName, newProvider, newRegion, newTier));
						}
					}
				}
			}
		}
		
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
//			return new MapeActionCreate(params[0], params[1], params[2], params[3], params[4], params[5]);
			return new MapeActionCreate(params[0], params[1], params[2], params[3]);
		} else {
			return null;
		}
	}

	@Override
	public String typeName() {
		return MapeWorld.ACTION_CREATE;
	}
}
