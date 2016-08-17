package ch.uzh.glapp.model;

public class Violation {

	private String cellId;
	private String containerId;
	private String organId;
	private String appId;
	private String ruleId;
	private String metric;
	private double healthiness; // the healthiness value of the cell that caused violation to a rule

	public Violation(String cellId, String containerId, String organId, String appId, String ruleId, String metric, double healthiness) {
		this.cellId = cellId;
		this.containerId = containerId;
		this.organId = organId;
		this.appId = appId;
		this.ruleId = ruleId;
		this.metric = metric;
		this.healthiness = healthiness;
	}

	public String getCellId() {
		return cellId;
	}

	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
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

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public double getHealthiness() {
		return healthiness;
	}

	public void setHealthiness(double healthiness) {
		this.healthiness = healthiness;
	}
}
