package mapeTest;

import burlap.oomdp.singleagent.GroundedAction;

public class MAPEGroundedAction extends GroundedAction {
	private String cellName;
	private String provider;
	private String tier;
	private String geo;
	
	public MAPEGroundedAction(MAPEAction action, String cellName, String provider, String tier, String geo) {
		super(action);
		this.cellName = cellName;
		this.provider = provider;
		this.tier = tier;
		this.geo = geo;
	}

	public void initParamsWithStringRep(String[] params) {
		if (params.length == 4) {
			this.cellName = params[0]; // 0: cell name
			this.provider = params[1]; // 1: provider
			this.tier = params[2]; // 2: tier
			this.geo = params[3]; // 3: geo
		} else {
			// TODO throw exception
		}
	}

	public String[] getParametersAsString() {
		String[] params = {this.cellName, this.provider, this.tier, this.geo};
		return params;
	}

	@Override
	public GroundedAction copy() {
		return new MAPEGroundedAction((MAPEAction)this.action, this.cellName, this.provider, this.tier, this.geo);
	}
}
