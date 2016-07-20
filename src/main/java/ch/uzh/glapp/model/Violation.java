package ch.uzh.glapp.model;

public class Violation {

	private String cellId;
	private String containerId;
	private String organId;
	private String appId;
	private String ruleId;
	private String metric;

	public Violation(String cellId, String containerId, String organId, String appId, String ruleId, String metric) {
		this.cellId = cellId;
		this.containerId = containerId;
		this.organId = organId;
		this.appId = appId;
		this.ruleId = ruleId;
		this.metric = metric;
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
}