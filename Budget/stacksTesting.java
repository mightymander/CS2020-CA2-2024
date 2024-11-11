package Budget;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class stacksTesting {

    // Create new object class Budget with depth, list of income, list of expenses
    static class Budget {
        String depth;
        List<Entry> income;
        List<Entry> expenses;

        public Budget(String depth) {
            this.depth = depth;
            this.income = new ArrayList<>();
            this.expenses = new ArrayList<>();
        }

        // Add income with description and amount
        public void addIncome(String description, String amount) {
            this.income.add(new Entry(description, amount));
        }

        // Add expense with description and amount
        public void addExpense(String description, String amount) {
            this.expenses.add(new Entry(description, amount));
        }

        // Inner class to store description and amount
        static class Entry {
            String description;
            String amount;

            public Entry(String description, String amount) {
                this.description = description;
                this.amount = amount;
            }

            @Override
            public String toString() {
                return description + ": " + amount;
            }
        }

        @Override
        public String toString() {
            return "depth: " + this.depth + "\nIncome: " + this.income + "\nExpenses: " + this.expenses;
        }
    }

    // Function to add budget to stack
    public static void addBudget(Stack<Budget> stack, List<Budget.Entry> incomeEntries, List<Budget.Entry> expenseEntries) {
        // Create new budget object with depth as the size of the stack
        Budget budget = new Budget(Integer.toString(stack.size() + 1));
        
        // Add income and expense entries to the budget
        for (Budget.Entry income : incomeEntries) {
            budget.addIncome(income.description, income.amount);
        }
        for (Budget.Entry expense : expenseEntries) {
            budget.addExpense(expense.description, expense.amount);
        }
        // Push the budget to the stack
        stack.push(budget);
    }

    public static void main(String[] args) {
        
        // Create lists of income and expense entries
        List<Budget.Entry> incomeValues = Arrays.asList(
            new Budget.Entry("salary", "1000"), 
            new Budget.Entry("freelance", "2000"),
            new Budget.Entry("investment", "3000")
        );

        List<Budget.Entry> expenseValues = Arrays.asList(
            new Budget.Entry("rent", "100"), 
            new Budget.Entry("utilities", "200"),
            new Budget.Entry("subscriptions", "300")
        );

        // Create new stack of Budget objects
        Stack<Budget> allBudgets = new Stack<>();

        // Add a new budget to the stack
        addBudget(allBudgets, incomeValues, expenseValues);


        //update the income and expense values
        incomeValues = Arrays.asList(
            new Budget.Entry("salary", "2000"), 
            new Budget.Entry("freelance", "3000"),
            new Budget.Entry("investment", "4000")
        );
        expenseValues = Arrays.asList(
            new Budget.Entry("rent", "200"), 
            new Budget.Entry("utilities", "300"),
            new Budget.Entry("subscriptions", "400")
        );
        
        // Add a new budget to the stack
        addBudget(allBudgets, incomeValues, expenseValues);

        // Print the most recent budget, (peek is used to get the most recent budget)
        //System.out.println(allBudgets.peek());

        //Print all budgets from the stack
        for (Budget budget : allBudgets) {
            System.out.println(budget);
        }

    }
}
