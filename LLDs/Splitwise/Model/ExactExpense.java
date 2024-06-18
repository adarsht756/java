package Model;
import java.util.List;

public class ExactExpense extends Expense {
        public ExactExpense(double amount, User paidBy, List<Split> splits, ExpenseMetadata expenseMetadata) {
        super(paidBy, amount, splits, expenseMetadata);
    }

    @Override
    public boolean validate() {
        double totalAmount = getAmount();
        double sumSplitAmount = 0;

        for (Split split : getSplits()) {
            if (!(split instanceof ExactSplit)) {
                return false;
            }
            ExactSplit exactSplit = (ExactSplit) split;
            sumSplitAmount += exactSplit.getAmount();
        }

        if (totalAmount != sumSplitAmount) {
            return false;
        }

        return true;
    }
}