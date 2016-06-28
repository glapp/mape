package ch.uzh.glapp.mdp2;

import burlap.mdp.core.action.Action;

public class MapeActionRemove implements Action {
	private String cellName;
	
	public MapeActionRemove(String cellName) {
		this.cellName = cellName;
	}

	@Override
	public String actionName() {
		return MapeWorld.ACTION_REMOVE + " cell " + cellName;
	}

	@Override
	public Action copy() {
		return new MapeActionRemove(this.cellName);
	}

	public String getCellName() {
		return cellName;
	}

}
