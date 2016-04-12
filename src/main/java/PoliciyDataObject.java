import java.util.List;

public class PoliciyDataObject {
    private List<Rules> rules;
}

class Rules {
    String application_id;
    String metric;
    String createdAt;
    String updatedAt;
    String operator;
    String value;
    String id;
}