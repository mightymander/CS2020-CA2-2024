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

    //method to press tab x amount of times
    private void pressTab(int times) {
        try {
            Robot robot = new Robot();
            for (int i = 0; i < times; i++) {
                robot.keyPress(KeyEvent.VK_TAB); // Press the Tab key
                robot.keyRelease(KeyEvent.VK_TAB); // Release the Tab key
            }
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
    public void testUndoFunctionalityBasic() throws AWTException {
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
        wait(500);

        //press 1, 2, 3
        robot.keyPress(KeyEvent.VK_1); // Press the 1 key
        robot.keyRelease(KeyEvent.VK_1); // Release the 1 key
        robot.keyPress(KeyEvent.VK_2); // Press the 2 key
        robot.keyRelease(KeyEvent.VK_2); // Release the 2 key
        robot.keyPress(KeyEvent.VK_3); // Press the 3 key
        robot.keyRelease(KeyEvent.VK_3); // Release the 3 key
        wait(500);

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
        wait(500);

        //Screen should now look like 12 in the income wages field
        assertEquals("12", newContentPane.currentIncomeValues.get(0));

        //Attempting to undo 
        pressControlZ();
        wait(500);

        //Screen should now look like 1 in the income wages field
        assertEquals("1", newContentPane.currentIncomeValues.get(0));

        //Attempting to undo 
        pressControlZ();
        wait(500);

        //Screen should now look like default values
        assertEquals("", newContentPane.currentIncomeValues.get(0));
    }


    @Test
    public void testUndoFunctionalityIntermediate()throws AWTException { 
        /*
         * This test will simulate the user input of the GUI
         * More complex, inputting 123 into the income field wages
         * Then inputting 456 into the loans field 
         * Then inputting 789 into the other field
         * Now going down to spending 
         * Inputting 100 into the Food field
         * Inputting 200 into the Rent field
         * Inputting 300 into the Other field
         * 
         * However not touching the time fields or adding new income/spending feilds
         * 
         * Then undoing the input going all the way back to default values
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
        //press tab key
        Robot robot = new Robot();
        pressTab(1);
        wait(100);

        //press 1, 2, 3
        robot.keyPress(KeyEvent.VK_1); // Press the 1 key
        robot.keyRelease(KeyEvent.VK_1); // Release the 1 key
        robot.keyPress(KeyEvent.VK_2); // Press the 2 key
        robot.keyRelease(KeyEvent.VK_2); // Release the 2 key
        robot.keyPress(KeyEvent.VK_3); // Press the 3 key
        robot.keyRelease(KeyEvent.VK_3); // Release the 3 key
        wait(500);

        //go down to loans
        pressTab(2);
        wait(100);

        //press 4, 5, 6
        robot.keyPress(KeyEvent.VK_4); // Press the 4 key
        robot.keyRelease(KeyEvent.VK_4); // Release the 4 key
        robot.keyPress(KeyEvent.VK_5); // Press the 5 key
        robot.keyRelease(KeyEvent.VK_5); // Release the 5 key
        robot.keyPress(KeyEvent.VK_6); // Press the 6 key
        robot.keyRelease(KeyEvent.VK_6); // Release the 6 key
        wait(500);

        //go down to other
        pressTab(2);
        wait(100);

        //press 7, 8, 9
        robot.keyPress(KeyEvent.VK_7); // Press the 7 key
        robot.keyRelease(KeyEvent.VK_7); // Release the 7 key
        robot.keyPress(KeyEvent.VK_8); // Press the 8 key
        robot.keyRelease(KeyEvent.VK_8); // Release the 8 key
        robot.keyPress(KeyEvent.VK_9); // Press the 9 key
        robot.keyRelease(KeyEvent.VK_9); // Release the 9 key
        wait(500);

        //go down to spending, rent
        pressTab(4);
        wait(100);

        //press 1, 0, 0
        robot.keyPress(KeyEvent.VK_1); // Press the 1 key
        robot.keyRelease(KeyEvent.VK_1); // Release the 1 key
        robot.keyPress(KeyEvent.VK_0); // Press the 0 key
        robot.keyRelease(KeyEvent.VK_0); // Release the 0 key
        robot.keyPress(KeyEvent.VK_0); // Press the 0 key
        robot.keyRelease(KeyEvent.VK_0); // Release the 0 key
        wait(500);

        //go down to spending, rent
        pressTab(2);

        //press 2, 0, 0
        robot.keyPress(KeyEvent.VK_2); // Press the 2 key
        robot.keyRelease(KeyEvent.VK_2); // Release the 2 key
        robot.keyPress(KeyEvent.VK_0); // Press the 0 key
        robot.keyRelease(KeyEvent.VK_0); // Release the 0 key
        robot.keyPress(KeyEvent.VK_0); // Press the 0 key
        robot.keyRelease(KeyEvent.VK_0); // Release the 0 key
        wait(500);

        //go down to spending, other
        pressTab(2);

        //press 3, 0, 0
        robot.keyPress(KeyEvent.VK_3); // Press the 3 key
        robot.keyRelease(KeyEvent.VK_3); // Release the 3 key
        robot.keyPress(KeyEvent.VK_0); // Press the 0 key
        robot.keyRelease(KeyEvent.VK_0); // Release the 0 key
        robot.keyPress(KeyEvent.VK_0); // Press the 0 key
        robot.keyRelease(KeyEvent.VK_0); // Release the 0 key
        wait(500);

        /*
         * Now the screen should look like this
         * Income:
         * Wages: 123 per year
         * Loans: 456 per year
         * Other: 789 per year
         * Total: 1368 per year
         * 
         * Spending:
         * Food: 100 per year
         * Rent: 200 per year
         * Other: 300 per year
         * Total: 600 per year
         * 
         * Overall Total: 768 per year
         */

        //check the current state of the UI
        //Income Values
        assertEquals("123", newContentPane.currentIncomeValues.get(0));
        assertEquals("456", newContentPane.currentIncomeValues.get(1));
        assertEquals("789", newContentPane.currentIncomeValues.get(2));
        assertEquals("per year", newContentPane.currentIncomeTimeValues.get(0));
        assertEquals("per year", newContentPane.currentIncomeTimeValues.get(1));
        assertEquals("per year", newContentPane.currentIncomeTimeValues.get(2));

        //Spending Values
        assertEquals("100", newContentPane.currentSpendingValues.get(0));
        assertEquals("200", newContentPane.currentSpendingValues.get(1));
        assertEquals("300", newContentPane.currentSpendingValues.get(2));
        assertEquals("per year", newContentPane.currentSpendingTimeValues.get(0));
        assertEquals("per year", newContentPane.currentSpendingTimeValues.get(1));
        assertEquals("per year", newContentPane.currentSpendingTimeValues.get(2));


    }
    
}
