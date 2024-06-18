package Utilities.Filter;

public class Filter {
    public String name;
    public Object value;
    public String predicate;

    public Filter(String name, Object value, String predicate) {
        this.predicate = predicate;
        this.name = name;
        this.value = value;
    }
}