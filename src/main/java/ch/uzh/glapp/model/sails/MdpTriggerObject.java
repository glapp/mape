package ch.uzh.glapp.model.sails;

import ch.uzh.glapp.model.Violation;

import java.util.List;

public class MdpTriggerObject {

	private List<Violation> violationList;
	private double appHealthiness;

	public MdpTriggerObject(List<Violation> violationList, double appHealthiness) {
		this.violationList = violationList;
		this.appHealthiness = appHealthiness;
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
}
