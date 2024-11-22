package Budget;

//import junit for testing
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Budget.backupStacks.Budget;
import Budget.BudgetMain;

public class savestatetests {

    //test if true = true, to make sure junit is working
    @Test
    public void testTrueIsTrue() {
        assertTrue(true);
    }


    //test if the budget object is created
    @Test
    public void testBudgetObject() {
        backupStacks.Budget budget = new backupStacks.Budget("1");
        assertNotNull(budget);
    }

    //test if budget object is created with depth and empty lists
    @Test
    public void testBudgetCreated() {
        backupStacks.Budget budget = new backupStacks.Budget("1");
        assertEquals("1", budget.depth);
        assertEquals(0, budget.income.size());
        assertEquals(0, budget.expenses.size());
    }

    //test if budget object is created with depth and lists
    // only 1 depth is tested
    @Test
    public void testBackup() {

        Stack<Budget> stack = new Stack<>();
        List<Budget.Entry> incomeEntries = new ArrayList<>();
        List<Budget.Entry> expenseEntries = new ArrayList<>();

        incomeEntries.add(new backupStacks.Budget.Entry("Salary", "1000", "per year"));
        expenseEntries.add(new backupStacks.Budget.Entry("Rent", "500", "per year"));
        expenseEntries.add(new backupStacks.Budget.Entry("Food", "200", "per year"));

        backupStacks.addBudget(stack, incomeEntries, expenseEntries);

        assertEquals(1, stack.size());
        assertEquals("1", stack.peek().depth);
        assertEquals(1, stack.peek().income.size());
        assertEquals(2, stack.peek().expenses.size());
        //check that the entries are correct
        assertEquals("Salary", stack.peek().income.get(0).description);
        assertEquals("1000", stack.peek().income.get(0).amount);
        assertEquals("per year", stack.peek().income.get(0).timeFrequency);
       
        assertEquals("Rent", stack.peek().expenses.get(0).description);
        assertEquals("500", stack.peek().expenses.get(0).amount);
        assertEquals("per year", stack.peek().expenses.get(0).timeFrequency);
        
        assertEquals("Food", stack.peek().expenses.get(1).description);
        assertEquals("200", stack.peek().expenses.get(1).amount);
        assertEquals("per year", stack.peek().expenses.get(1).timeFrequency);
    }


    
}
