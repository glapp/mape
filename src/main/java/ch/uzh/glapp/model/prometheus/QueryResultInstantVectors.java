package ch.uzh.glapp.model.prometheus;

public class QueryResultInstantVectors {
    private String status;
    private DataInstantVectors data;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public DataInstantVectors getData() {
        return data;
    }
    public void setData(DataInstantVectors data) {
        this.data = data;
    }
}
