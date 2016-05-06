package ch.uzh.glapp.model;

import java.util.List;

public class Data {
	private String resultType;
	private List<Result> result;

	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public List<Result> getResult() {
		return result;
	}
	public void setResult(List<Result> result) {
		this.result = result;
	}
}