package Model;

public class LengthConstraint extends Constraint {
    private int minLength, maxLength;

    public LengthConstraint(int length) {
        this.minLength = length;
        this.maxLength = Integer.MAX_VALUE;
    }

    public LengthConstraint(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public Boolean validate(Object x) {
        if (x instanceof Integer) {
            Integer len = (Integer) x;
            return len >= minLength && len <= maxLength;
        }
        return false;
    }
}