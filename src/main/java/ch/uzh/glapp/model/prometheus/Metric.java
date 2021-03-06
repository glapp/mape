package ch.uzh.glapp.model.prometheus;

public class Metric {
	private String group;
	private String instance;
	private String job;
	private String id;

	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getID() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}