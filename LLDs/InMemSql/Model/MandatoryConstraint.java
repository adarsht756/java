package Model;
public class MandatoryConstraint extends Constraint {
    public Boolean validate(Object x) {
        return x != null;
    }
}