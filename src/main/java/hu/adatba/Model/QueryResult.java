package hu.adatba.Model;

public class QueryResult {
    private final String FirstValue;
    private final String SecondValue;

    public QueryResult(String FirstValue, String SecondValue) {
        this.FirstValue = FirstValue;
        this.SecondValue = SecondValue;
    }

    public String getFirstValue() {
        return FirstValue;
    }

    public String getSecondValue() {
        return SecondValue;
    }
}
