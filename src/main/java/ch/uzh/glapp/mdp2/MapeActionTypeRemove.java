package ch.uzh.glapp.mdp2;

import java.util.ArrayList;
import java.util.List;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.oo.state.generic.DeepOOState;
import burlap.mdp.core.state.State;

import static ch.uzh.glapp.mdp2.MapeWorld.*;

public class MapeActionTypeRemove implements ActionType {

	@Override
	public List<Action> allApplicableActions(State state) {
		
		List<Action> actionList = new ArrayList<Action>();
		List<ObjectInstance> cells = ((DeepOOState)state).objectsOfClass(CLASS_CELL);
		
		for (ObjectInstance cell : cells) {
			if((int)(((MapeCell)cell).get(VAR_CELLS)) > 1) {
				actionList.add(new MapeActionRemove(cell.name()));
			}
		}
		return actionList;
	}

	@Override
	public Action associatedAction(String strRep) {
		String[] params = strRep.split(",");
		if (params.length == 1) {
			// 0: cell name
			return new MapeActionRemove(params[0]);
		} else {
			// TODO throw exception
			return null;
		}
	}

	@Override
	public String typeName() {
		return MapeWorld.ACTION_REMOVE;
	}

}
