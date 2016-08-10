package ch.uzh.glapp.model.sails;

import ch.uzh.glapp.model.Violation;

import java.util.List;

public class MdpTriggerObject {

	private List<Violation> violationList;
	private double appHealthiness;
	private boolean isRuleViolated;

	public MdpTriggerObject(List<Violation> violationList, double appHealthiness, boolean isRuleViolated) {
		this.violationList = violationList;
		this.appHealthiness = appHealthiness;
		this.isRuleViolated = isRuleViolated;
	}

	public List<Violation> getViolationList() {
		return violationList;
	}

	public void setViolationList(List<Violation> violationList) {
		this.violationList = violationList;
	}

	public double getAppHealthiness() {
		return appHealthiness;
	}

	public void setAppHealthiness(double appHealthiness) {
		this.appHealthiness = appHealthiness;
	}

	/**
	 * Check if a rule is violated.
	 * Definition: more than a defined percentage of cells violating a rule
	 * @return true if the rule is violated, false otherwise.
	 */
	public boolean isRuleViolated() {
		return isRuleViolated;
	}

	public void setRuleViolated(boolean isRuleViolated) {
		this.isRuleViolated = isRuleViolated;
	}
}
