package ch.uzh.glapp.mdp2;

import java.util.List;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.state.State;

public class MapeActionTypeRemove implements ActionType {

	@Override
	public List<Action> allApplicableActions(State arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action associatedAction(String arg0) {
		String[] params = arg0.split(",");
		if (params.length == 6) {
			// 0: cell name
			return new MapeActionRemove(params[0]);
		} else {
			// TODO throw exception
			return null;
		}
	}

	@Override
	public String typeName() {
		return "Remove";
	}

}
