package ch.uzh.glapp.mdp2;

import burlap.mdp.core.action.Action;

public class MapeActionCreate implements Action {
	private String cellName;
	private String provider;
	private String region;
	private String tier;
//	private String proxy_provider;
//	private String proxy_region;
	
	public MapeActionCreate(String cellName, String provider, String region, String tier/*, String proxy_provider, String proxy_region*/) {
		this.cellName = cellName;
		this.provider = provider;
		this.region = region;
		this.tier = tier;
//		this.proxy_provider = proxy_provider;
//		this.proxy_region = proxy_region;
	}

	@Override
	public String actionName() {
		return "Create";
	}

	@Override
	public Action copy() {
		return new MapeActionCreate(this.cellName, this.provider, this.region, this.tier/*, this.proxy_provider, this.proxy_region*/);
	}

}
