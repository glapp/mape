package ch.uzh.glapp.model;


public class ObjectForMdp {

	private String metric;
	private String cellId;
	private String organId;
	private String appId;
	private float healthinessValue;

	public ObjectForMdp(String metric, String cellId, String organId, String appId, float healthinessValue) {
		this.metric = metric;
		this.cellId = cellId;
		this.organId = organId;
		this.appId = appId;
		this.healthinessValue = healthinessValue;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public String getCellId() {
		return cellId;
	}

	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public float getHealthyValue() {
		return healthinessValue;
	}

	public void setHealthyValue(float healthyValue) {
		this.healthinessValue = healthyValue;
	}
}
