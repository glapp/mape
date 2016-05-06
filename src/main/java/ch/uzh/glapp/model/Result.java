package ch.uzh.glapp.model;

import java.util.List;

public class Result {
	private Metric metric;
	private List<List<String>> values;

	public Metric getMetric() {
		return metric;
	}
	public void setMetric(Metric metric) {
		this.metric = metric;
	}
	public List<List<String>> getValues() {
		return values;
	}
	public void setValues(List<List<String>> values) {
		this.values = values;
	}
}