package ch.uzh.glapp.model.prometheus;

import java.util.List;

public class ResultInstantVectors {
	private Metric metric;
	private List<String> value;

	public Metric getMetric() {
		return metric;
	}
	public void setMetric(Metric metric) {
		this.metric = metric;
	}
	public List<String> getValue() {
		return value;
	}
	public void setValue(List<String> value) {
		this.value = value;
	}
}