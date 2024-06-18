package Model;
import java.util.List;

public abstract class Expense {

    private String id;
    private double amount;
    private User paidBy;
    private List<Split> splits;
    private ExpenseMetadata expenseMetadata;

    public Expense(User paidBy, double amount, List<Split> splits, ExpenseMetadata expenseMetadata) {
        this.paidBy = paidBy;
        this.amount = amount;
        this.splits = splits;
        this.expenseMetadata = expenseMetadata;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return this.amount;
    }

    public User getPaidBy() {
        return this.paidBy;
    }

    public List<Split> getSplits() {
        return this.splits;
    }

    public ExpenseMetadata getExpenseMetadata() {
        return this.expenseMetadata;
    }

    public abstract boolean validate();
}