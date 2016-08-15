package ch.uzh.glapp.model.prometheus;

import java.util.List;

public class DataInstantVectors {
	private String resultType;
	private List<ResultInstantVectors> result;

	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public List<ResultInstantVectors> getResult() {
		return result;
	}
	public void setResult(List<ResultInstantVectors> result) {
		this.result = result;
	}
}