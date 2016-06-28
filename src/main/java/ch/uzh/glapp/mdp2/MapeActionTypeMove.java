package ch.uzh.glapp.mdp2;

import java.util.List;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.state.State;

public class MapeActionTypeMove implements ActionType {

	@Override
	public List<Action> allApplicableActions(State arg0) {
		// TODO Auto-generated method stub
		return null;
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
			// TODO throw exception
			return null;
		}
	}

	@Override
	public String typeName() {
		return MapeWorld.ACTION_MOVE;
	}

}
