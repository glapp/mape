package ch.uzh.glapp.model;

public class MapeCellObject {

	public MapeCellObject(String provider, String region, String tier, int cells){
		this.provider = provider;
		this.region = region;
		this.tier = tier;
		this.cells = cells;
	}

	private String provider;
	private String region;
	private String tier;
	private int cells;

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

	public int getCells() {
		return cells;
	}

	public void setCells(int cells) {
		this.cells = cells;
	}
}
