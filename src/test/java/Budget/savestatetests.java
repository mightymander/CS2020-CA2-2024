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
            wait(100);
            robot.keyPress(KeyEvent.VK_CONTROL); // Press the control key
            wait(50);
            robot.keyPress(KeyEvent.VK_Z); // Press the z key
            robot.keyRelease(KeyEvent.VK_Z); // Release the z key
            robot.keyRelease(KeyEvent.VK_CONTROL); // Release the control key
            wait(50);
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
                wait(50);
                robot.keyRelease(KeyEvent.VK_TAB); // Release the Tab key
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    //method to press down arrow x amount of times
    private void pressDownArrow(int times) {
        try {
            Robot robot = new Robot();
            for (int i = 0; i < times; i++) {
                robot.keyPress(KeyEvent.VK_DOWN); // Press the Down key
                robot.keyRelease(KeyEvent.VK_DOWN); // Release the Down key
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    //method to press enter x amount of times
    private void pressEnter(int times) {
        try {
            Robot robot = new Robot();
            for (int i = 0; i < times; i++) {
                robot.keyPress(KeyEvent.VK_ENTER); // Press the Enter key
                robot.keyRelease(KeyEvent.VK_ENTER); // Release the Enter key
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    //method to enter a string into the GUI
    private void enterString(String s) {
        wait(100);
        try {
            Robot robot = new Robot();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                wait(100);
                robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(c)); // Press the key
                robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(c)); // Release the key
            }
        } catch (AWTException e) {
            e.printStackTrace();}
        }

        


        //method to check the current state of the GUI
        //takes in lists of strings and compares them to the current state of the GUI
        private void checkState(BudgetMain newContentPane, List<String> incomeValues, List<String> incomeTimeValues, List<String> spendingValues, List<String> spendingTimeValues) {
            //Income Values
            for (int i = 0; i < incomeValues.size(); i++) {
                assertEquals(incomeValues.get(i), newContentPane.currentIncomeValues.get(i));
                assertEquals(incomeTimeValues.get(i), newContentPane.currentIncomeTimeValues.get(i));
            }
            //Spending Values
            for (int i = 0; i < spendingValues.size(); i++) {
                assertEquals(spendingValues.get(i), newContentPane.currentSpendingValues.get(i));
                assertEquals(spendingTimeValues.get(i), newContentPane.currentSpendingTimeValues.get(i));
            }
        }

        //method to enter into the whole UI
        private void enterAll(BudgetMain newContentPane, List<String> incomeValues, List<String> incomeTimeValues, List<String> spendingValues, List<String> spendingTimeValues) {
            wait(1000);
            pressTab(1);

            //enter the income values
            for (int i = 0; i < incomeValues.size(); i++) {
                enterString(incomeValues.get(i));
                pressTab(1);
                wait(1000);
                
                //if per year, then skip over
                // if per month, press down arrow twice, then enter
                // if per week, press down arrow three times, then enter

                if (incomeTimeValues.get(i).equals("per year")){
                    pressTab(1);
                }
                else if (incomeTimeValues.get(i).equals("per month")) {
                    pressDownArrow(2);
                    pressEnter(1);
                    pressTab(1);

                } else if (incomeTimeValues.get(i).equals("per week")) {
                    pressDownArrow(3);
                    pressEnter(1);
                    pressTab(1);
                }
            }
            pressTab(2);

            //enter the spending values
            for (int i = 0; i < spendingValues.size(); i++) {
                enterString(spendingValues.get(i));
                pressTab(1);
                wait(1000);
                
                //if per year, then skip over
                // if per month, press down arrow twice, then enter
                // if per week, press down arrow three times, then enter

                if (spendingTimeValues.get(i).equals("per year")){
                    pressTab(1);
                }
                else if (spendingTimeValues.get(i).equals("per month")) {
                    pressDownArrow(2);
                    pressEnter(1);
                    pressTab(1);

                } else if (spendingTimeValues.get(i).equals("per week")) {
                    pressDownArrow(3);
                    pressEnter(1);
                    pressTab(1);
                }
            }
            
            

        }

        //method too add income field
        private void addIncomeField(BudgetMain newContentPane, String description) {
            try {
                Robot robot = new Robot();
                //press control I
                robot.keyPress(KeyEvent.VK_CONTROL); // Press the control key
                robot.keyPress(KeyEvent.VK_I); // Press the I key
                robot.keyRelease(KeyEvent.VK_I); // Release the I key
                robot.keyRelease(KeyEvent.VK_CONTROL); // Release the control key
                wait(500);
                enterString(description);
                //press enter
                robot.keyPress(KeyEvent.VK_ENTER); // Press the enter key
                robot.keyRelease(KeyEvent.VK_ENTER); // Release the enter key

            } catch (AWTException e) {
                e.printStackTrace();
            }
        }

        //method to add spending field
        private void addSpendingField(BudgetMain newContentPane, String description) {
            try {
                Robot robot = new Robot();
                //press control I
                robot.keyPress(KeyEvent.VK_CONTROL); // Press the control key
                robot.keyPress(KeyEvent.VK_S); // Press the S key
                robot.keyRelease(KeyEvent.VK_S); // Release the S key
                robot.keyRelease(KeyEvent.VK_CONTROL); // Release the control key
                wait(500);
                enterString(description);
                //press enter
                robot.keyPress(KeyEvent.VK_ENTER); // Press the enter key
                robot.keyRelease(KeyEvent.VK_ENTER); // Release the enter key

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
        

        
        enterAll(newContentPane, Arrays.asList("123","456","789"), Arrays.asList("per year","per year","per year"), Arrays.asList("100","200","300"),Arrays.asList("per year","per year","per year"));
    

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
        checkState(newContentPane, Arrays.asList("123","456","789"), Arrays.asList("per year", "per year", "per year"), Arrays.asList("100","200","300"), Arrays.asList("per year", "per year", "per year"));
        wait(100);
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789"), Arrays.asList("per year", "per year", "per year"), Arrays.asList("100","200","30"), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789"), Arrays.asList("per year", "per year", "per year"), Arrays.asList("100","200","3"), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789"), Arrays.asList("per year", "per year", "per year"), Arrays.asList("100","200",""), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789"), Arrays.asList("per year", "per year", "per year"), Arrays.asList("100","20",""), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789"), Arrays.asList("per year", "per year", "per year"), Arrays.asList("100","2",""), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789"), Arrays.asList("per year", "per year", "per year"), Arrays.asList("100","",""), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789"), Arrays.asList("per year", "per year", "per year"), Arrays.asList("10","",""), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789"), Arrays.asList("per year", "per year", "per year"), Arrays.asList("1","",""), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789"), Arrays.asList("per year", "per year", "per year"), Arrays.asList("","",""), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","78"), Arrays.asList("per year", "per year", "per year"), Arrays.asList("","",""), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","7"), Arrays.asList("per year", "per year", "per year"), Arrays.asList("","",""), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456",""), Arrays.asList("per year", "per year", "per year"), Arrays.asList("","",""), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","45",""), Arrays.asList("per year", "per year", "per year"), Arrays.asList("","",""), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","4",""), Arrays.asList("per year", "per year", "per year"), Arrays.asList("","",""), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","",""), Arrays.asList("per year", "per year", "per year"), Arrays.asList("","",""), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("12","",""), Arrays.asList("per year", "per year", "per year"), Arrays.asList("","",""), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("1","",""), Arrays.asList("per year", "per year", "per year"), Arrays.asList("","",""), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("","",""), Arrays.asList("per year", "per year", "per year"), Arrays.asList("","",""), Arrays.asList("per year", "per year", "per year"));
    }


    @Test
    public void testUndoFunctionalityAdvanced() {
        /*
         * This test will simulate the user input of the GUI
         * This will be the most complex test, using all the features of the GUI
         * Including adding new income/spending fields, and changing the time frequency
         * 
         * The test will input the following values
         * Income:
         * Wages: 123 per year
         * Loans: 456 per month
         * Other: 789 per week
         * Side Job: 1000 per year
         * Total: 2368 per year
         * 
         * Spending:
         * Food: 100 per year
         * Rent: 200 per week
         * Other: 300 per month
         * Chocolate: 400 per year
         * Total: 1000 per year
         * 
         * Overall Total: 1368 per year
         * 
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


        //add new income fields
        addIncomeField(newContentPane,"Side Jobs");
        wait(100);
        addSpendingField(newContentPane, "Chocolate");
        
        wait(100);
        
        enterAll(newContentPane, Arrays.asList("123","456","789","1000"),Arrays.asList("per year","per month","per week","per year"),  Arrays.asList("100","200","300","400"),Arrays.asList("per year","per week","per month", "per year"));
        wait(100);

        //check the current state of the UI
        checkState(newContentPane, Arrays.asList("123","456","789","1000"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("100","200","300","400"), Arrays.asList("per year", "per week", "per month", "per year"));

        //now undo back to default values
        
        //spending seciton
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","1000"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("100","200","300","40"), Arrays.asList("per year", "per week", "per month", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","1000"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("100","200","300","4"), Arrays.asList("per year", "per week", "per month", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","1000"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("100","200","300",""), Arrays.asList("per year", "per week", "per month", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","1000"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("100","200","300",""), Arrays.asList("per year", "per week", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","1000"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("100","200","30",""), Arrays.asList("per year", "per week", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","1000"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("100","200","3",""), Arrays.asList("per year", "per week", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","1000"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("100","200","",""), Arrays.asList("per year", "per week", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","1000"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("100","200","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","1000"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("100","20","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","1000"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("100","2","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","1000"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("100","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","1000"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("10","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","1000"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("1","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","1000"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        
        //income section
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","100"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","10"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789","1"), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789",""), Arrays.asList("per year", "per month", "per week", "per year"), Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","789",""), Arrays.asList("per year", "per month", "per year", "per year"), Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","78",""), Arrays.asList("per year", "per month", "per year", "per year"), Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","7",""), Arrays.asList("per year", "per month", "per year", "per year"), Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","",""), Arrays.asList("per year", "per month", "per year", "per year"), Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","456","",""), Arrays.asList("per year", "per year", "per year", "per year"), Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","45","",""), Arrays.asList("per year", "per year", "per year", "per year"), Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","4","",""), Arrays.asList("per year", "per year", "per year", "per year"), Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("123","","",""), Arrays.asList("per year", "per year", "per year", "per year"), Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("12","","",""), Arrays.asList("per year", "per year", "per year", "per year"), Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("1","","",""), Arrays.asList("per year", "per year", "per year", "per year"), Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"), Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("","","",""), Arrays.asList("per year", "per year", "per year","per year"), Arrays.asList("","",""), Arrays.asList("per year", "per year", "per year"));
        pressControlZ();
        checkState(newContentPane, Arrays.asList("","",""), Arrays.asList("per year", "per year", "per year"), Arrays.asList("","",""), Arrays.asList("per year", "per year", "per year"));
    }



}
