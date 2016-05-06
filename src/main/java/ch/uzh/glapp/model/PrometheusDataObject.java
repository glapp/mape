package ch.uzh.glapp.model;

import java.util.List;

public class PrometheusDataObject {
    private String status;
    private Data data;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }
}

class Data {
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

class Result {
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

class Metric {
    private String group;
    private String instance;
    private String job;

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
}