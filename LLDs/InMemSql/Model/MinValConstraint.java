package Model;

public class MinValConstraint extends Constraint {
    private Integer minValue, maxVal;

    public MinValConstraint(Integer minValue) {
        this.minValue = minValue;
        this.maxVal = Integer.MAX_VALUE;
    }

    public MinValConstraint(Integer minValue, Integer maxVal) {
        this.minValue = minValue;
        this.maxVal = maxVal;
    }

    public Boolean validate(Object x) {
        if (x instanceof Integer) {
            Integer val = (Integer) x;
            return val >= minValue && val <= maxVal;
        }
        return false;
    }
}