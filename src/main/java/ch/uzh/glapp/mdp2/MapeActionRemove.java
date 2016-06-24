package ch.uzh.glapp.mdp2;

import burlap.mdp.core.action.Action;

public class MapeActionRemove implements Action {
	private String cellName;
	
	public MapeActionRemove(String cellName) {
		this.cellName = cellName;
	}

	@Override
	public String actionName() {
		return "Remove";
	}

	@Override
	public Action copy() {
		return new MapeActionRemove(this.cellName);
	}

}
