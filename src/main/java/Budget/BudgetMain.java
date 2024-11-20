package Budget;

// Swing imports
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Budget.backupStacks.Budget;

import java.awt.event.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

// class definition
public class BudgetMain extends JPanel {    // based on Swing JPanel

    //Fields related to budgets and categories
    private Stack<Budget> allBudgets = new Stack<>(); // Stack to store all budgets
    private ArrayList<String> incomeCategories = new ArrayList<>(); // To store income categories
    private ArrayList<String> spendingCategories = new ArrayList<>(); // To store spending categories
    private ArrayList<String> currentIncomeValues = new ArrayList<>(); // To store income values
    private ArrayList<String> currentSpendingValues = new ArrayList<>(); // To store spending values  
    private ArrayList<String> timeLineCategories = new ArrayList<>(); // To store time line categories
    private ArrayList<String> currentIncomeTimeValues = new ArrayList<>(); // To store income time values
    private ArrayList<String> currentSpendingTimeValues = new ArrayList<>(); // To store spending time values 
    

    //UI-related fields
    JFrame topLevelFrame;  // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // used to control layout
    private JButton exitButton, addIncomeFieldButton, addSpendingFieldButton, undoButton,printButton; // buttons
    private JTextField[] incomeFields, spendingFields; // arrays of text fields for income and spending
    private JTextField totalIncomeField, totalSpendingField, overallTotalField; // text fields for total income, spending, and overall total
    private JComboBox<String>[] incomeTimeDropDownFields, spendingTimeDropDownFields; // arrays of drop down boxes for income and spending time values


    // constructor - create UI
    public BudgetMain(JFrame frame) {
        topLevelFrame = frame; // keep track of top-level frame
        initDefaultValues(); // set up default values
        saveFields(); // Save the current state as the first state in the stack
        setLayout(new GridBagLayout());  // use GridBag layout
        initComponents();  // initialise components
    }

    private void initDefaultValues() {
        /*
         * Initialize default values for the budget
         * 3 default values income categories: Wages, Loans, Other
         * 3 default values spending categories: Food, Rent, Other
         * 3 default values time line categories: per year, per month, per week
         * 3 default values for current income values: "", "", ""
         * 3 default values for current spending values: "", "", ""
         * 3 default values for current income time values: per year, per year, per year
         * 3 default values for current spending time values: per year, per year, per year
         */
        incomeCategories = new ArrayList<>(Arrays.asList("Wages", "Loans", "Other"));
        spendingCategories = new ArrayList<>(Arrays.asList("Food", "Rent", "Other"));
        timeLineCategories = new ArrayList<>(Arrays.asList("per year", "per month", "per week"));
        currentIncomeValues = new ArrayList<>(Arrays.asList("","",""));
        currentSpendingValues = new ArrayList<>(Arrays.asList("","",""));
        currentIncomeTimeValues = new ArrayList<>(Arrays.asList("per year", "per year", "per year"));
        currentSpendingTimeValues = new ArrayList<>(Arrays.asList("per year", "per year", "per year"));
        
    }

    /*
     * START OF INITIALIZATION METHODS
     * CREATE COMPONENTS FOR THE BUDGET CALCULATOR
     * CREATE UI
     */

    private void initComponents() { 
        /*
         * Initialize components for the budget calculator
         * Add fields for income and spending entries
         * Add summary fields for total income, spending, and overall total
         * Add action buttons to panel
         * Add named labels to panel
         * Set up listeners for buttons and text fields
         */
    
        // Get the number of rows for income and spending
        int numberIncomeRows = incomeCategories.size();
        int numberTimeRowsIncome = currentIncomeTimeValues.size();
        int numberTimeRowsSpending = currentSpendingTimeValues.size();
        int numberSpendingRows = spendingCategories.size();

        // Create arrays for income and spending fields and time drop down boxes
        incomeFields = new JTextField[numberIncomeRows];
        spendingFields = new JTextField[numberSpendingRows];
        incomeTimeDropDownFields = new JComboBox[numberTimeRowsIncome];    
        spendingTimeDropDownFields = new JComboBox[numberTimeRowsSpending];

        addNamedLabelsToPanel(numberIncomeRows,numberSpendingRows); // Add named labels to panel
        addSummaryFields(numberIncomeRows,numberSpendingRows);  // Add summary fields for total income, spending, and overall total
        addBudgetEntryFieldsToPanel(numberIncomeRows,numberSpendingRows); // Add income entry fields to panel
        addActionButtonsToPanel(numberIncomeRows,numberSpendingRows); // Add action buttons to panel

        // set up  listeners (in a separate method)
        initListeners();
    }

    // Add summary fields for total income, spending, and overall total
    private void addSummaryFields(int numberIncomeRows, int numberSpendingRows) {

        //Total Income label followed by total income field
        JLabel totalIncomeLabel = new JLabel("Total Income");
        addComponent(totalIncomeLabel, numberIncomeRows +1, 0);

        // set up text box for displaying total income.  Users cam view, but cannot directly edit it
        totalIncomeField = new JTextField("0", 10);   // 0 initially, with 10 columns
        totalIncomeField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        totalIncomeField.setEditable(false);    // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalIncomeField, numberIncomeRows +1, 1);

        //total spending
        JLabel totalSpendingLabel = new JLabel("Total Spending");
        addComponent(totalSpendingLabel, numberIncomeRows+numberSpendingRows +5, 0);
        
        // set up text box for displaying total spending.  Users cam view, but cannot directly edit it
        totalSpendingField = new JTextField("0", 10);   // 0 initially, with 10 columns
        totalSpendingField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        totalSpendingField.setEditable(false);    // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalSpendingField, numberIncomeRows+numberSpendingRows +5, 1);
         
        //blank row
        JLabel blankLabel2 = new JLabel(" ");
        addComponent(blankLabel2, numberIncomeRows + numberSpendingRows + 6, 0);

        //Overall total text field
        JLabel overallTotalLabel = new JLabel("Overall Total");
        addComponent(overallTotalLabel, numberIncomeRows+numberSpendingRows +7, 0);
        // set up text box for displaying overall total.  Users cam view, but cannot directly edit it
        overallTotalField = new JTextField("0", 10);   // 0 initially, with 10 columns
        overallTotalField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        overallTotalField.setEditable(false);    // user cannot directly edit this field (ie, it is read-only)
        addComponent(overallTotalField, numberIncomeRows+numberSpendingRows +7, 1);
    }


    private void addBudgetEntryFieldsToPanel(int numberIncomeRows, int numberSpendingRows) {
        /*
         * Add fields for income and spending entries
         * These fields can be entered by the user
         */
        
        // Add income categories
        // Loop through and create income rows dynamically
        for (int i = 0; i < numberIncomeRows; i++) {
            // Create labels for income categories
            JLabel incomeCategoryLabel = new JLabel(incomeCategories.get(i));
            addComponent(incomeCategoryLabel, i + 1, 0);

            // Create text fields
            String incomeValue = i < currentIncomeValues.size() ? currentIncomeValues.get(i) : "";
            incomeFields[i] = new JTextField(incomeValue, 10);
            incomeFields[i].setHorizontalAlignment(JTextField.RIGHT);
            addComponent(incomeFields[i], i + 1, 1);
            addTriggerCalculationsListener(incomeFields[i]); // Add listener to trigger calculations when text is changed

            //create dropdown box for each income category, that has per week/month/year
            String timeValue = i < currentIncomeTimeValues.size() ? currentIncomeTimeValues.get(i) : "";
            incomeTimeDropDownFields[i] = new JComboBox<>(timeLineCategories.toArray(new String[0]));
            incomeTimeDropDownFields[i].setSelectedItem(timeValue);
            addComponent(incomeTimeDropDownFields[i], i + 1, 2);
            addTriggerCalculationsListener(incomeTimeDropDownFields[i]); // Add listener to trigger calculations when selected item is changed

        }

        // Add spending categories
        // Loop through and create spending rows dynamically
        for (int i = 0; i < numberSpendingRows; i++) {
            JLabel spendingCategoryLabel  = new JLabel(spendingCategories.get(i));
            addComponent(spendingCategoryLabel, numberIncomeRows + 5 + i, 0);

            // Create text fields
            String spendingValue = i < currentSpendingValues.size() ? currentSpendingValues.get(i) : ""; 
            spendingFields[i] = new JTextField(spendingValue, 10);
            spendingFields[i].setHorizontalAlignment(JTextField.RIGHT);
            addComponent(spendingFields[i], numberIncomeRows + 5 + i, 1);
            addTriggerCalculationsListener(spendingFields[i]); // Add listener to trigger calculations when text is changed

            //create dropdown box for each spending category, that has per week/month/year
            String timeValue = i < currentSpendingTimeValues.size() ? currentSpendingTimeValues.get(i) : "";
            spendingTimeDropDownFields[i] = new JComboBox<>(timeLineCategories.toArray(new String[0]));
            spendingTimeDropDownFields[i].setSelectedItem(timeValue);
            addComponent(spendingTimeDropDownFields[i], numberIncomeRows + 5 + i, 2);
            addTriggerCalculationsListener(spendingTimeDropDownFields[i]); // Add listener to trigger calculations when selected item is changed
        }
    }


    private void addActionButtonsToPanel(int numberIncomeRows, int numberSpendingRows) {
        //add button beside income
        addIncomeFieldButton = new JButton("ADD");
        addComponent(addIncomeFieldButton, 0, 1);

        //add button beside spending
        addSpendingFieldButton = new JButton("ADD");
        addComponent(addSpendingFieldButton, numberIncomeRows + 4, 1);        

        //Undo Button
        undoButton = new JButton("Undo");
        //undoButton.setEnabled(false);
        addComponent(undoButton, numberIncomeRows + numberSpendingRows + 8, 0);
        
        //Exit Button
        exitButton = new JButton("Exit");
        addComponent(exitButton, numberIncomeRows + numberSpendingRows + 8, 1);

        //add button that prints current state
        printButton = new JButton("Print");
        addComponent(printButton, numberIncomeRows + numberSpendingRows + 8, 2);
    }


    private void addNamedLabelsToPanel(int numberIncomeRows, int numberSpendingRows) {
        //Spending header
        JLabel spendingLabel = new JLabel("SPENDING");
        addComponent(spendingLabel,  numberIncomeRows + 4, 0);
        //blank row after total income
        JLabel blankLabel1 = new JLabel(" ");
        addComponent(blankLabel1, numberIncomeRows+2, 0);

        //Income header
        JLabel incomeLabel = new JLabel("INCOME");
        addComponent(incomeLabel, 0, 0);

    }

    /*
     * END OF INITIALIZATION METHODS
     */

     /*
      * START OF LISTENERS
      * SET UP LISTENERS FOR BUTTONS AND TEXT FIELDS
      */

    // set up listeners
    // initially just for buttons, can add listeners for text fields
    private void initListeners() {

        // exitButton - exit program when pressed
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //undo button
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goBack();
                triggerCalculations();
            }
        });

        //print button
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("");
                System.out.println("Current state: ");
                System.out.println("Income categories: " + incomeCategories);
                System.out.println("Spending categories: " + spendingCategories);
                System.out.println("Current income values: " + currentIncomeValues);
                System.out.println("Current spending values: " + currentSpendingValues);
                System.out.println("Current income time values: " + currentIncomeTimeValues);
                System.out.println("Current spending time values: " + currentSpendingTimeValues);
                System.out.println("Total income: " + totalIncomeField.getText());
                System.out.println("Total spending: " + totalSpendingField.getText());
                System.out.println("Overall total: " + overallTotalField.getText());
                System.out.println("");
                System.out.println("All budgets: ");
                for (Budget budget : allBudgets) {
                    System.out.println(budget);
                }

            }
        });

        //add income category button
        addIncomeFieldButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addIncomeField();
                triggerCalculations();
            }
        });

        //add spending category button
        addSpendingFieldButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSpendingField();
                triggerCalculations();
            }
        });
    }


    //Add a DocumentListener to the ComboBox, to trigger calculations when the selected item is changed
    private void addTriggerCalculationsListener(JComboBox<String> comboBox) {
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateIncomeSpendingTimeValuesList();                
                triggerCalculations();
                saveFields();
            }
        });
    }


    // Add a DocumentListener to a text field to trigger calculations when text is changed
    private void addTriggerCalculationsListener(JTextField field) {
    field.getDocument().addDocumentListener(new DocumentListener() {
        // Trigger calculations when text is inserted
        public void insertUpdate(DocumentEvent e) {
            updateIncomeSpendingTimeValuesList();
            triggerCalculations();
            saveFields();
        }
        // Trigger calculations when text is removed
        public void removeUpdate(DocumentEvent e) {
            updateIncomeSpendingTimeValuesList();
            triggerCalculations();
            saveFields();
        }
        // Trigger calculations when text is changed
        public void changedUpdate(DocumentEvent e) {
            updateIncomeSpendingTimeValuesList();
            triggerCalculations();
            saveFields();
        }
    });
}

    /*
     * END OF LISTENERS
     */

     /*
      * START OF METHODS FOR ADDING MORE INCOME AND SPENDING FIELDS
      */

    
    //method to add a spending field
    private void addSpendingField() {
        // Add a new spending category to the list
        String input = JOptionPane.showInputDialog("Enter additional Spending field name:  ");
        spendingCategories.add(input);
        currentSpendingValues.add("");
        currentSpendingTimeValues.add("per year");

        //save the current state
        saveFields();

        // Remove all components from the panel
        removeAll();

        // Reinitialize the components
        initComponents();

        // Refresh the layout
        revalidate(); // Refresh the panel to show the new components
        repaint(); // Repaint the panel to ensure it displays correctly
    }

    //method to add an income field
    private void addIncomeField() {
        // Add a new income category to the list
        String input = JOptionPane.showInputDialog("Enter additional Income field name:  ");
        incomeCategories.add(input);
        currentIncomeValues.add("");
        currentIncomeTimeValues.add("per year");

        //save the current state
        saveFields();

        // Remove all components from the panel
        removeAll();

        // Reinitialize the components
        initComponents();

        // Refresh the layout
        revalidate(); // Refresh the panel to show the new components
        repaint(); // Repaint the panel to ensure it displays correctly
    }

    /*
     * END OF METHODS FOR ADDING MORE INCOME AND SPENDING FIELDS
     */


     
    /*
     * METHODS FOR SAVING AND GOING BACK TO PREVIOUS STATE
     * SAVE FIELDS
     * CHECK IF VALUES ARE SAME
     * GO BACKS
     */


    // method to save all the current input fields
    public void saveFields() {
        /*
         *  Will save all the current input fields input into a stack
         *  incomes will be saved in a list of entries, eg [salary: 1000, freelance: 2000, investment: 3000]
         *  expenses will be saved in a list of entries, eg [rent: 100, utilities: 200, subscriptions: 300]
         */

        //convert the currentIncomeValues and currentSpendingValues into a list of entries
        List<Budget.Entry> incomeEntries = new ArrayList<>();
        List<Budget.Entry> expenseEntries = new ArrayList<>();

        // Add income and spending entries to the list
        if (incomeCategories.size() == currentIncomeValues.size() && currentIncomeValues.size() == currentIncomeTimeValues.size()) {
            for (int i = 0; i < incomeCategories.size(); i++) {
            incomeEntries.add(new Budget.Entry(incomeCategories.get(i), currentIncomeValues.get(i), currentIncomeTimeValues.get(i)));
            }
        } else {
            System.out.println("Income fields mismatch.");
        }

        // Add income and spending entries to the list
        if (spendingCategories.size() == currentSpendingValues.size() && currentSpendingValues.size() == currentSpendingTimeValues.size()) {
            for (int i = 0; i < spendingCategories.size(); i++) {
            expenseEntries.add(new Budget.Entry(spendingCategories.get(i), currentSpendingValues.get(i), currentSpendingTimeValues.get(i)));
            }
        } else {
            System.out.println("Spending fields mismatch.");
        }

        // Add a new budget to the stack
        backupStacks.addBudget(allBudgets, incomeEntries, expenseEntries);

    }

    private boolean isUIIdenticalToLastBudget() {
        /*
         * Check if the current UI is identical to the last budget
         * If it is, remove the most recent budget
         * This is used to prevent duplicate budgets from being added to the stack
         */


        //if the stack is empty, return false
        if (allBudgets.isEmpty()) {
            System.out.println("No previous budgets to compare to.");
            return false;
        }
        
        //get the most recent budget
        Budget mostRecentBudget = allBudgets.peek();

        //check the size of the income and spending categories, this stops the program from crashing if the sizes are different
        if (incomeCategories.size() != mostRecentBudget.income.size() || spendingCategories.size() != mostRecentBudget.expenses.size()) {
            return false;
        }

        // Compare income categories from the UI to the last budget
        for (int i = 0; i < incomeCategories.size(); i++) {
            if (!incomeCategories.get(i).equals(mostRecentBudget.income.get(i).description)) {
                return false;
            }
        }

        // Compare spending categories from the UI to the last budget
        for (int i = 0; i < spendingCategories.size(); i++) {
            if (!spendingCategories.get(i).equals(mostRecentBudget.expenses.get(i).description)) {
                return false;
            }
        }

        // Compare income values from the UI to the last budget
        for (int i = 0; i < currentIncomeValues.size(); i++) {
            if (!currentIncomeValues.get(i).equals(mostRecentBudget.income.get(i).amount)) {
                return false;
            }
        }

        // Compare spending values from the UI to the last budget
        for (int i = 0; i < currentSpendingValues.size(); i++) {
            if (!currentSpendingValues.get(i).equals(mostRecentBudget.expenses.get(i).amount)) {
                return false;
            }
        }

        // Compare income time values from the UI to the last budget
        for (int i = 0; i < currentIncomeTimeValues.size(); i++) {
            if (!currentIncomeTimeValues.get(i).equals(mostRecentBudget.income.get(i).timeFrequency)) {
                return false;
            }
        }
        // Compare spending time values from the UI to the last budget
        for (int i = 0; i < currentSpendingTimeValues.size(); i++) {
            if (!currentSpendingTimeValues.get(i).equals(mostRecentBudget.expenses.get(i).timeFrequency)) {
                return false;
            }
        }
        
       // If no differences were found, UI is identical to the last budget
        return true;

    }


    private void goBack() {
        /*
         * Will go back to the previous state of the budget
         * Undo button backend
         */
    
        //if theirs only 1 budget, call the default values method
        if (allBudgets.size() == 1) {
            System.out.println("Only one budget in the stack. Setting default values to 0.");
            initDefaultValues();
            // Remove all components from the panel
            removeAll();
            // Reinitialize the components
            initComponents();
            // Refresh the layout
            revalidate(); // Refresh the panel to show the new components
            repaint(); // Repaint the panel to ensure it displays correctly
            return;
        }

       // Check if the UI is identical to the last budget
       // If it is, remove the most recent budget
       // fixes of the undo button not working on the first click
        if (isUIIdenticalToLastBudget()) {
            allBudgets.pop(); // Remove the most recent budget
        } 
        
        //if the stack is empty, print a message and return, should not happen
        if (allBudgets.isEmpty()) {
        System.out.println("No previous budgets to go back to.");
        return;
}
        //grab the most recent budget
        Budget mostRecentBudget = allBudgets.pop();

        //remove everything from incomeCategories, spendingCategories, currentIncomeValues, and currentSpendingValues, and time values
        incomeCategories.clear();
        spendingCategories.clear();
        currentIncomeValues.clear();
        currentSpendingValues.clear();
        currentIncomeTimeValues.clear();
        currentSpendingTimeValues.clear();

        //repopulate incomeCategories, spendingCategories, currentIncomeValues, currentSpendingValues and time values
        // from mostRecentBudget
        for (Budget.Entry income : mostRecentBudget.income) {
            incomeCategories.add(income.description);
            currentIncomeValues.add(income.amount);
            currentIncomeTimeValues.add(income.timeFrequency);
        }
        for (Budget.Entry spending : mostRecentBudget.expenses) {
            spendingCategories.add(spending.description);
            currentSpendingValues.add(spending.amount);
            currentSpendingTimeValues.add(spending.timeFrequency);
        }

        //remove all components from the panel
        removeAll();

        //reinitialize the components
        initComponents(); // Reinitialize the components

        // Refresh the layout
        revalidate(); // Refresh the panel to show the new components
        repaint(); // Repaint the panel to ensure it displays correctly
    }
    

    // add a component at specified row and column in UI.  (0,0) is top-left corner
    private void addComponent(Component component, int gridrow, int gridcol) {
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;   
        layoutConstraints.gridx = gridcol;
        layoutConstraints.gridy = gridrow;
        add(component, layoutConstraints);

    }

    /*
     * END OF METHODS FOR SAVING AND GOING BACK TO PREVIOUS STATE
     */


    /*
     * METHODS FOR UPDATING THE CURRENT INCOME AND SPENDING VALUES AND TIME VALUES
     */

    //method for updating the currentIncomeValues and currentSpendingValues lists and time drop down boxes
    private void updateIncomeSpendingTimeValuesList() {
        // Clear the current values lists
        currentIncomeValues.clear();
        currentSpendingValues.clear();
        currentIncomeTimeValues.clear();
        currentSpendingTimeValues.clear();

        // Loop through incomeFields and add values to currentIncomeValues
        for (JTextField field : incomeFields) {
            currentIncomeValues.add(field.getText());
        }

        // Loop through spendingFields and add values to currentSpendingValues
        for (JTextField field : spendingFields) {
            currentSpendingValues.add(field.getText());
        }

        // Loop through incomeTimeDropDownFields and add values to currentIncomeTimeValues
        for (JComboBox<String> comboBox : incomeTimeDropDownFields) {
            currentIncomeTimeValues.add((String) comboBox.getSelectedItem());
        }

        // Loop through spendingTimeDropDownFields and add values to currentSpendingTimeValues
        for (JComboBox<String> comboBox : spendingTimeDropDownFields) {
            currentSpendingTimeValues.add((String) comboBox.getSelectedItem());
        }
    }

    /*
     * END OF METHODS FOR UPDATING THE CURRENT INCOME AND SPENDING VALUES AND TIME VALUES
     */


    /*
      * START OF METHODS FOR CALCULATING TOTAL INCOME, SPENDING, AND OVERALL TOTAL
      */

    // Trigger calculations for income, spending, and overall total
    private void triggerCalculations() {
        calculateTotalIncome();
        calculateTotalSpending();
        calculateOverallTotal();
    }


    public void calculateTotalIncome() {
        // Calculate total income
        double totalIncome = calculateTotal(incomeFields, currentIncomeTimeValues);

        //if total income is NaN, set the field to "Invalid number"
        if (Double.isNaN(totalIncome)) {
            totalIncomeField.setText("Invalid number");
            return;
        }

        totalIncomeField.setText(String.format("%.2f", totalIncome));
    }


    public void calculateTotalSpending() {
        // Calculate total spending
        double totalSpending = calculateTotal(spendingFields, currentSpendingTimeValues);

        //if total spending is NaN, set the field to "Invalid number"
        if (Double.isNaN(totalSpending)) {
            totalSpendingField.setText("Invalid number");
            return;
        }

        totalSpendingField.setText(String.format("%.2f", totalSpending));
    }


    private double calculateTotal(JTextField[] fields, List<String> timeValues){
        double total = 0.0;

        //Loop through the fields array to get values and sum them
        //Also check what time period the value is in, and convert it to per year
        for (int i = 0; i < fields.length; i++) {
            double value = getTextFieldValue(fields[i]);  // get value from text field
            String timeValue = timeValues.get(i);  // get time value from time drop down box

            // Convert value to per year based on time period
            switch (timeValue) {
                case "per month":
                    value *= 12;
                    break;
                case "per week":
                    value *= 52;
                    break;
                default:
                    break;
            }

            // Exit and set total to NaN if any value is NaN (error)
            if (Double.isNaN(value)) {
                total = Double.NaN;
                break;
            }

            total += value;  // add value to total
        }

        return total;
    }


    //Calculate overall total using total income and total spending
    public void calculateOverallTotal() {
        double totalIncome = getTextFieldValue(totalIncomeField);  // get total income value
        double totalSpending = getTextFieldValue(totalSpendingField);  // get total spending value
        double overallTotal = totalIncome - totalSpending;  // calculate overall total

        //if overall total is NaN, set the field to "Invalid number"
        if (Double.isNaN(overallTotal)) {
            overallTotalField.setText("Invalid number");
            return;
        }
        //if overall total is negative, display text in red
        if (overallTotal < 0) {
            overallTotalField.setForeground(Color.RED);
        } else {
            overallTotalField.setForeground(Color.BLACK);
        }

        overallTotalField.setText(String.format("%.2f", overallTotal));
    }

    /*
     * END OF METHODS FOR CALCULATING TOTAL INCOME, SPENDING, AND OVERALL TOTAL
     */

     /*
      * START OF METHODS ALREADY IMPLEMENTED
      * GET TEXT FIELD VALUE
      * CREATE AND SHOW GUI
      * MAIN METHOD
      */


    // return the value if a text field as a double
    // --return 0 if field is blank
    // --return NaN if field is not a number
    private double getTextFieldValue(JTextField field) {

        // get value as String from field
        String fieldString = field.getText();  // get text from text field

        if (fieldString.isBlank()) {   // if text field is blank, return 0
            return 0;
        }

        else {  // if text field is not blank, parse it into a double
            try {
                return Double.parseDouble(fieldString);  // parse field number into a double
             } catch (java.lang.NumberFormatException ex) {  // catch invalid number exception
                //JOptionPane.showMessageDialog(topLevelFrame, "Please enter a valid number");  // show error message
                return Double.NaN;  // return NaN to show that field is not a number
            }
        }
    }


// below is standard code to set up Swing, which students should not need to edit much
    // standard method to show UI
    private static void createAndShowGUI() {
 
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
    }
 

    // standard main class to set up Swing UI
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }
    /*
     * END OF METHODS ALREADY IMPLEMENTED
     */
}