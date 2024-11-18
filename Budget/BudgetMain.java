package Budget;

// Swing imports
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import Budget.backupStacks.Budget;

// class definition
public class BudgetMain extends JPanel {    // based on Swing JPanel

    //Fields related to budgets and categories
    private Stack<Budget> allBudgets = new Stack<>(); // Stack to store all budgets
    private ArrayList<String> incomeCategories = new ArrayList<>();
    private ArrayList<String> spendingCategories = new ArrayList<>();
    private ArrayList<String> currentIncomeValues = new ArrayList<>(); // To store income values with default 0
    private ArrayList<String> currentSpendingValues = new ArrayList<>(); // To store spending values with default 
    private ArrayList<String> timeLineCategories = new ArrayList<>();
    private ArrayList<String> currentIncomeTimeValues = new ArrayList<>(); // To store income time values with default per year
    private ArrayList<String> currentSpendingTimeValues = new ArrayList<>(); // To store spending time values with default per year
    

    //UI-related fields
    JFrame topLevelFrame;  // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // used to control layout
    private JButton exitButton, addIncomeFieldButton, addSpendingFieldButton, undoButton; // buttons
    private JTextField[] incomeFields, spendingFields; // arrays of text fields for income and spending
    private JTextField totalIncomeField, totalSpendingField, overalTotalField; // text fields for total income, spending, and overall total
    private JComboBox<String>[] incomeTimeDropDownFields, spendingTimeDropDownFields; // arrays of drop down boxes for income and spending time values


    // constructor - create UI
    public BudgetMain(JFrame frame) {
        topLevelFrame = frame; // keep track of top-level frame
        initDefaultValues(); // set up default values
        saveFields(); // Save the current state as the first state in the stack
        setLayout(new GridBagLayout());  // use GridBag layout
        initComponents();  // initalise components
    }

    private void initDefaultValues() {
        /*
         * Initialize default values for the budget
         * 3 income categories: Wages, Loans, Other
         * 3 spending categories: Food, Rent, Other
         * 3 time line categories: per year, per month, per week
         * 3 default values for current income values: "", "", ""
         * 3 default values for current spending values: "", "", ""
         * 3 default values for current income time values: per year, per year, per year
         */
        currentIncomeValues = new ArrayList<>(Arrays.asList("","",""));
        currentSpendingValues = new ArrayList<>(Arrays.asList("","",""));
        timeLineCategories = new ArrayList<>(Arrays.asList("per year", "per month", "per week"));
        currentIncomeTimeValues = new ArrayList<>(Arrays.asList("per year", "per year", "per year"));
        currentSpendingTimeValues = new ArrayList<>(Arrays.asList("per year", "per year", "per year"));
        incomeCategories = new ArrayList<>(Arrays.asList("Wages", "Loans", "Other"));
        spendingCategories = new ArrayList<>(Arrays.asList("Food", "Rent", "Other"));
    }
   

    // initialise componenents
    // Note that this method is quite long.  Can be shortened by putting Action Listener stuff in a separate method
    // will be generated automatically by IntelliJ, Eclipse, etc
    private void initComponents() { 
    
        //set up number of rows for income and spending fields
        int numberIncomeRows = incomeCategories.size();
        incomeFields = new JTextField[numberIncomeRows];

        int numberSpendingRows = spendingCategories.size();
        spendingFields = new JTextField[numberSpendingRows];

        //set up time drop down fields
        int numberTimeRowsIncome = currentIncomeTimeValues.size();
        incomeTimeDropDownFields = new JComboBox[numberTimeRowsIncome];

        int numberTimeRowsSpending = currentSpendingTimeValues.size();
        spendingTimeDropDownFields = new JComboBox[numberTimeRowsSpending];
        
       
        addBudgetEntryFieldsToPanel(numberIncomeRows,numberSpendingRows); // Add income entry fields to panel
        addSummaryFields(numberIncomeRows,numberSpendingRows);  // Add summary fields for total income, spending, and overall total
        addActionButtonsToPanel(numberIncomeRows,numberSpendingRows); // Add action buttons to panel
        addNamedLabelsToPanel(numberIncomeRows,numberSpendingRows); // Add named labels to panel

        // set up  listeners (in a spearate method)
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
        overalTotalField = new JTextField("0", 10);   // 0 initially, with 10 columns
        overalTotalField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        overalTotalField.setEditable(false);    // user cannot directly edit this field (ie, it is read-only)
        addComponent(overalTotalField, numberIncomeRows+numberSpendingRows +7, 1);
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

        // Loop through incomeCategories and currentIncomeValues to create incomeEntries
        for (int i = 0; i < incomeCategories.size(); i++) {
            incomeEntries.add(new Budget.Entry(incomeCategories.get(i), currentIncomeValues.get(i), currentIncomeTimeValues.get(i)));
        }
        // Loop through spendingCategories and currentSpendingValues to create expenseEntries
        for (int i = 0; i < spendingCategories.size(); i++) {
            expenseEntries.add(new Budget.Entry(spendingCategories.get(i), currentSpendingValues.get(i), currentSpendingTimeValues.get(i)));
        }

        // Add a new budget to the stack
        backupStacks.addBudget(allBudgets, incomeEntries, expenseEntries);

    }

    // set up listeners
    // initially just for buttons, can add listeners for text fields
    private void initListeners() {

        // exitButton - exit program when pressed
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Print all budgets from the stack
            //for (Budget budget : allBudgets) {
             //   System.out.println(budget);
            //}

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


        // Add DocumentListener to all income and spending fields
        for (JTextField incomeField : incomeFields) {
            addTriggerCalculationsListener(incomeField);
        }
        for (JTextField spendingField : spendingFields) {
            addTriggerCalculationsListener(spendingField);

        }
        
    }

    //Add a DocumentListner to the ComboBox, to trigger calculations when the selected item is changed
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

    // Add action listener to the addIncomeFieldButton
    // Remove all action listeners from the income field button
    // This is to prevent multiple action listeners from being added
    for (ActionListener al : addIncomeFieldButton.getActionListeners()) {
    addIncomeFieldButton.removeActionListener(al);
    }

    addIncomeFieldButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            addIncomeField();
            triggerCalculations();
            saveFields();
        }
    });


    // Remove all action listeners from the spending field button
    // This is to prevent multiple action listeners from being added
    for (ActionListener al : addSpendingFieldButton.getActionListeners()) {
    addSpendingFieldButton.removeActionListener(al);
    }

    addSpendingFieldButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            addSpendingField();
            triggerCalculations();
            saveFields();
        }
    });
}

    //method to add a spending field
    private void addSpendingField() {
        // Add a new spending category to the list
        String input = JOptionPane.showInputDialog("Enter additional Spending field name:  ");
        spendingCategories.add(input);
        currentSpendingValues.add("");
        currentSpendingTimeValues.add("per year");

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

        // Remove all components from the panel
        removeAll();

        // Reinitialize the components
        initComponents();

        // Refresh the layout
        revalidate(); // Refresh the panel to show the new components
        repaint(); // Repaint the panel to ensure it displays correctly
    }
    
    //method to check if the currentIncomeValues and currentSpendingValues are the same as the most recent budget
    private void checkIfValuesAreSame() {
       // Check if the current income and spending values match the most recent budget
        boolean valuesMatch = true;
        Budget mostRecentBudget = allBudgets.peek();

        for (int i = 0; i < currentIncomeValues.size(); i++) {
            // Compare income values
            if (!currentIncomeValues.get(i).equals(mostRecentBudget.income.get(i).amount)) {
                System.out.println("Income values are different.");
                valuesMatch = false;  // Set flag to false if there's a mismatch
                break;
            }
        }

        // Only proceed to spending comparison if income values matched
        if (valuesMatch) {
            for (int i = 0; i < currentSpendingValues.size(); i++) {
                // Compare spending values
                if (!currentSpendingValues.get(i).equals(mostRecentBudget.expenses.get(i).amount)) {
                    System.out.println("Spending values are different.");
                    valuesMatch = false;  // Set flag to false if there's a mismatch
                    break;
                }
            }
        }

        //check if income  time values match
        if (valuesMatch) {
            for (int i = 0; i < currentIncomeTimeValues.size(); i++) {
                // Compare income time values
                if (!currentIncomeTimeValues.get(i).equals(mostRecentBudget.income.get(i).timeFrequency)) {
                    System.out.println("Income time values are different.");
                    valuesMatch = false;  // Set flag to false if there's a mismatch
                    break;
                }
            }

        //check if spending time values match
            if (valuesMatch) {
                for (int i = 0; i < currentSpendingTimeValues.size(); i++) {
                    // Compare spending time values
                    if (!currentSpendingTimeValues.get(i).equals(mostRecentBudget.expenses.get(i).timeFrequency)) {
                        System.out.println("Spending time values are different.");
                        valuesMatch = false;  // Set flag to false if there's a mismatch
                        break;
                    }
                }
            }

            // Only proceed to spending comparison if income time values matched
            if (valuesMatch) {
                for (int i = 0; i < currentSpendingTimeValues.size(); i++) {
                    // Compare spending time values
                    if (!currentSpendingTimeValues.get(i).equals(mostRecentBudget.expenses.get(i).timeFrequency)) {
                        System.out.println("Spending time values are different.");
                        valuesMatch = false;  // Set flag to false if there's a mismatch
                        break;
                    }
                }
            }
        }

        // If both income and spending values match, pop the most recent budget
        if (valuesMatch) {
            System.out.println("Income and spending values are the same. Removing the most recent budget.");
            allBudgets.pop();
        } else {
            System.out.println("Values don't match, not removing the budget.");
        }

    }

    //method to goback to the previous budget 
    private void goBack() {
    
        //if theres only 1 budget, set default values to 0
        if (allBudgets.size() == 1) {
            System.out.println("Only one budget in the stack. Setting default values to 0.");
            currentIncomeValues = new ArrayList<>(Arrays.asList("0", "0", "0"));
            currentSpendingValues = new ArrayList<>(Arrays.asList("0", "0", "0"));
            currentIncomeTimeValues = new ArrayList<>(Arrays.asList("per year", "per year", "per year"));
            currentSpendingTimeValues = new ArrayList<>(Arrays.asList("per year", "per year", "per year"));
        }

        //check if the current values are the same as the most recent budget
        checkIfValuesAreSame();    
        
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
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;   // always use horixontsl filll
        layoutConstraints.gridx = gridcol;
        layoutConstraints.gridy = gridrow;
        add(component, layoutConstraints);

    }

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

    // Trigger calculations for income, spending, and overall total
    private void triggerCalculations() {
        calculateTotalIncome();
        calculateTotalSpending();
        calculateOverallTotal();
    }

    // Calculate total income using incomeFields array
    public void calculateTotalIncome() {
        double totalIncome = 0.0;

        // Loop through the incomeFields array to get values and sum them
        // Also check what time period the value is in, and convert it to per year
        for (int i = 0; i < incomeFields.length; i++) {
            double value = getTextFieldValue(incomeFields[i]);  // get value from text field
            String timeValue = currentIncomeTimeValues.get(i);  // get time value from time drop down box

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

            // Exit and set total income to NaN if any value is NaN (error)
            if (Double.isNaN(value)) {
                totalIncome = Double.NaN;
                break;
            }

            totalIncome += value;  // add value to total income
        }

        // If total income is NaN, set the field to "Invalid number"
        if (Double.isNaN(totalIncome)) {
            totalIncomeField.setText("Invalid number");
            return;
        }
        totalIncomeField.setText(String.format("%.2f", totalIncome));  // Update the UI field with formatted total
    }

    //Calculate total spending using spendingFields array 
    public void calculateTotalSpending() {
        double totalSpending = 0.0;

        //Loop through the spendingFields array to get values and sum them
        //Also check what time period the value is in, and convert it to per year
        for (int i = 0; i < spendingFields.length; i++) {
            double value = getTextFieldValue(spendingFields[i]);  // get value from text field
            String timeValue = currentSpendingTimeValues.get(i);  // get time value from time drop down box

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

            // Exit and set total spending to NaN if any value is NaN (error)
            if (Double.isNaN(value)) {
                totalSpending = Double.NaN;
                break;
            }

            totalSpending += value;  // add value to total spending
        }

        //if total spending is NaN, set the field to "Invalid number"
        if (Double.isNaN(totalSpending)) {
            totalSpendingField.setText("Invalid number");
            return;
        }
        totalSpendingField.setText(String.format("%.2f", totalSpending));
    }

    //Calculate overall total using total income and total spending
    public void calculateOverallTotal() {
        double totalIncome = getTextFieldValue(totalIncomeField);  // get total income value
        double totalSpending = getTextFieldValue(totalSpendingField);  // get total spending value
        double overallTotal = totalIncome - totalSpending;  // calculate overall total

        //if overall total is NaN, set the field to "Invalid number"
        if (Double.isNaN(overallTotal)) {
            overalTotalField.setText("Invalid number");
            return;
        }
        //if overall total is negative, display text in red
        if (overallTotal < 0) {
            overalTotalField.setForeground(Color.RED);
        } else {
            overalTotalField.setForeground(Color.BLACK);
        }

        overalTotalField.setText(String.format("%.2f", overallTotal));
    }

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


// below is standard code to set up Swing, which students shouldnt need to edit much
    // standard mathod to show UI
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


}