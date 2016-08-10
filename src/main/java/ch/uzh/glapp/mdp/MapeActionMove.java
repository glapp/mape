package ch.uzh.glapp.mdp;

import burlap.mdp.core.action.Action;

public class MapeActionMove implements Action {
	private String cellName;
	private String provider;
	private String region;
	private String tier;
//	private String proxy_provider;
//	private String proxy_region;
	
	public MapeActionMove(String cellName, String provider, String region, String tier/*, String proxy_provider, String proxy_region*/) {
		this.cellName = cellName;
		this.provider = provider;
		this.region = region;
		this.tier = tier;
//		this.proxy_provider = proxy_provider;
//		this.proxy_region = proxy_region;
	}

	@Override
	public String actionName() {
		return MapeWorld.ACTION_MOVE + " cell " + cellName + " to " + provider + ", " + region + ", " + tier;
	}

	@Override
	public Action copy() {
		return new MapeActionMove(this.cellName, this.provider, this.region, this.tier/*, this.proxy_provider, this.proxy_region*/);
	}

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}
}
