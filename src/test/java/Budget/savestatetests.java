package Budget;

//import junit for testing
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import javax.swing.JFrame;

import Budget.backupStacks.Budget;
import Budget.BudgetMain;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;




public class savestatetests {

    //method to wait x amount of time
    private void wait(int ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

    //method to undo the last action
    // works by simulating the user pressing the control and z keys
    private void pressControlZ() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL); // Press the control key
            robot.keyPress(KeyEvent.VK_Z); // Press the z key
            robot.keyRelease(KeyEvent.VK_Z); // Release the z key
            robot.keyRelease(KeyEvent.VK_CONTROL); // Release the control key
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

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
    public void testBackup1() {

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

    
    @Test
    public void testUndoFunctionality() throws AWTException {
        /*
         * This test will simulate the user input of the GUI
         * Very simple, just inputting 123 into the income field wages
         * Then undoing the input to go back to 12
         * Then undoing the input to go back to 1
         * Then undoing the input to go back to default values
         */

        //Create and set up the window.
        JFrame frame = new JFrame("Budget Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        BudgetMain newContentPane = new BudgetMain(frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        //frame.pack();
        frame.setSize(400,600);
        frame.setVisible(true);
        
        //wait 1 second, to make sure the GUI is loaded
        wait(1000);

        //simulate the user input
        //press key tab
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_TAB); // Press the Tab key
        robot.keyRelease(KeyEvent.VK_TAB); // Release the Tab key
        wait(1000);

        //press 1, 2, 3
        robot.keyPress(KeyEvent.VK_1); // Press the 1 key
        robot.keyRelease(KeyEvent.VK_1); // Release the 1 key
        robot.keyPress(KeyEvent.VK_2); // Press the 2 key
        robot.keyRelease(KeyEvent.VK_2); // Release the 2 key
        robot.keyPress(KeyEvent.VK_3); // Press the 3 key
        robot.keyRelease(KeyEvent.VK_3); // Release the 3 key
        wait(1000);

        //check the current state of the UI
        //Income Values

        
        assertEquals("123", newContentPane.currentIncomeValues.get(0));
        assertEquals("", newContentPane.currentIncomeValues.get(1));
        assertEquals("", newContentPane.currentIncomeValues.get(2));
        assertEquals("per year", newContentPane.currentIncomeTimeValues.get(0));
        assertEquals("per year", newContentPane.currentIncomeTimeValues.get(1));
        assertEquals("per year", newContentPane.currentIncomeTimeValues.get(2));
        
        //Spending Values
        //loop 3 times
        for (int i = 0; i < 3; i++) {
            assertEquals("", newContentPane.currentSpendingValues.get(i));
            assertEquals("per year", newContentPane.currentSpendingTimeValues.get(i));
        }

        //Attempting to undo 
        pressControlZ();
        wait(1000);

        //Screen should now look like 12 in the income wages field
        assertEquals("12", newContentPane.currentIncomeValues.get(0));

        //Attempting to undo 
        pressControlZ();
        wait(1000);

        //Screen should now look like 1 in the income wages field
        assertEquals("1", newContentPane.currentIncomeValues.get(0));

        //Attempting to undo 
        pressControlZ();
        wait(1000);

        //Screen should now look like default values
        assertEquals("", newContentPane.currentIncomeValues.get(0));
    }


    
}
