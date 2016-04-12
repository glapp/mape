import java.util.List;

public class PolicyDataObject {
    private List<Rules> rules;

    public List<Rules> getRules() {
        return rules;
    }
    public void setRules(List<Rules> rules) {
        this.rules = rules;
    }
}

class Rules {
    String application_id;
    String metric;
    String createdAt;
    String updatedAt;
    String operator;
    String value;
    String id;

    public String getApplication_id() {
        return application_id;
    }
    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }
    public String getMetric() {
        return metric;
    }
    public void setMetric(String metric) {
        this.metric = metric;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}