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
        List<Entry> timeFrequencyEntries;

        public Budget(String depth) {
            this.depth = depth;
            this.income = new ArrayList<>();
            this.expenses = new ArrayList<>();
            this.timeFrequencyEntries = new ArrayList<>();
        }

        // Add income with description and amount
        public void addIncome(String description, String amount, String timeFrequency) {
            this.income.add(new Entry(description, amount, timeFrequency));
        }

        // Add expense with description and amount
        public void addExpense(String description, String amount, String timeFrequency) {
            this.expenses.add(new Entry(description, amount, timeFrequency));
        }

        // Inner class to store description and amount
        static class Entry {
            String description;
            String amount;
            String timeFrequency;

            public Entry(String description, String amount, String timeFrequency) {
                this.description = description;
                this.amount = amount;
                this.timeFrequency = timeFrequency;
            }

            @Override
            public String toString() {
                return "Entry{" +
                        "description='" + description + '\'' +
                        ", amount='" + amount + '\'' +
                        ", timeFrequency='" + timeFrequency + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Budget{" +
                    "depth='" + depth + '\'' +
                    ", income=" + income +
                    ", expenses=" + expenses +
                    '}';
        }
    }

    // Function to add budget to stack
    public static void addBudget(Stack<Budget> stack, List<Budget.Entry> incomeEntries, List<Budget.Entry> expenseEntries) {
        // Create new budget object with depth as the size of the stack
        Budget budget = new Budget(Integer.toString(stack.size() + 1));
        
        // Add income and expense entries to the budget
        for (Budget.Entry income : incomeEntries) {
            budget.addIncome(income.description, income.amount, income.timeFrequency);
        }
        for (Budget.Entry expense : expenseEntries) {
            budget.addExpense(expense.description, expense.amount, expense.timeFrequency);
        }
        // Push the budget to the stack
        stack.push(budget);
    }

    public static void main(String[] args) {

        
        
        // Create lists of income and expense entries with time frequency
        List<Budget.Entry> incomeValues = Arrays.asList(
            new Budget.Entry("salary", "1000", "per year"), 
            new Budget.Entry("freelance", "2000", "per month"),
            new Budget.Entry("investment", "3000", "per month")
        );
        List<Budget.Entry> expenseValues = Arrays.asList(
            new Budget.Entry("rent", "100", "per month"), 
            new Budget.Entry("utilities", "200", "per month"),
            new Budget.Entry("subscriptions", "300", "per month")
        );

        // Create new stack of Budget objects
        Stack<Budget> allBudgets = new Stack<>();

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
