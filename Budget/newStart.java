// base code for student budget assessment
// Students do not need to use this code in their assessment, fine to junk it and do something different!
//
// Your submission must be a maven project, and must be submitted via Codio, and run in Codio
//
// user can enter in wages and loans and calculate total income
//
// run in Codio 
// To see GUI, run with java and select Box Url from Codio top line menu
//
// Layout - Uses GridBag layout in a straightforward way, every component has a (column, row) position in the UI grid
// Not the prettiest layout, but relatively straightforward
// Students who use IntelliJ or Eclipse may want to use the UI designers in these IDEs , instead of GridBagLayout
package Budget;

// Swing imports
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.*;
import java.lang.reflect.Array;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import Budget.stacksTesting.Budget;


/*
 * TODO
 *  - replace with just all total saying "Invalid number"
 */

// class definition
public class newStart extends JPanel {    // based on Swing JPanel

    // high level UI stuff
    JFrame topLevelFrame;  // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // used to control layout

    // widgets which may have listeners and/or values
    private JButton calculateButton;   // Calculate button
    private JButton exitButton;        // Exit button
    private JTextField wagesField;     // Wages text field
    private JTextField loansField;     // Loans text field
    private JTextField totalIncomeField; // Total Income field\

    private JTextField[] incomeFields; // Array for income text fields
    private JTextField[] spendingFields; // Array for spending text fields

    private JButton addIncomeFieldButton;
    private JButton addSpendingFieldButton;
    private JButton undoButton;

    private JTextField totalSpendingField;
    private JTextField overalTotalField;
    
    private JComboBox<String>[] incomeTimeDropDownFields; // Array for time drop down boxes
    private JComboBox<String>[] spendingTimeDropDownFields; // Array for time drop down boxes

    //combo box options
    ArrayList<String> timeLineCategories = new ArrayList<>(Arrays.asList("per year", "per month", "per week"));
    private ArrayList<String> currentIncomeTimeValues = new ArrayList<>(Arrays.asList("per year", "per year", "per year")); // To store income time values with default per year
    private ArrayList<String> currentSpendingTimeValues = new ArrayList<>(Arrays.asList("per year", "per year", "per year")); // To store spending time values with default per year

     // Income, spending categories
    ArrayList<String> incomeCategories = new ArrayList<>(Arrays.asList("Wages", "Loans", "Other"));
    ArrayList<String> spendingCategories = new ArrayList<>(Arrays.asList("Food", "Rent", "Other"));
    private ArrayList<String> currentIncomeValues = new ArrayList<>(Arrays.asList("","","")); // To store income values with default 0
    private ArrayList<String> currentSpendingValues = new ArrayList<>(Arrays.asList("","","")); // To store spending values with default 0

    // Stack to store all budgets
    private Stack<Budget> allBudgets = new Stack<>();
   
    // constructor - create UI  (dont need to change this)
    public newStart(JFrame frame) {
        
        topLevelFrame = frame; // keep track of top-level frame
        setLayout(new GridBagLayout());  // use GridBag layout
        initComponents();  // initalise components
    }


    //debug method to print incomeCategories and spendingCategories, and currentIncomeValues and currentSpendingValues
    public void DEBUG_printCurrentValues() {
        System.out.println("Current values:");
        System.out.println(incomeCategories);
        System.out.println(currentIncomeValues);
        System.out.println((" "));
        System.out.println(spendingCategories);
        System.out.println(currentSpendingValues);       
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
            incomeEntries.add(new Budget.Entry(incomeCategories.get(i), currentIncomeValues.get(i)));
        }
        // Loop through spendingCategories and currentSpendingValues to create expenseEntries
        for (int i = 0; i < spendingCategories.size(); i++) {
            expenseEntries.add(new Budget.Entry(spendingCategories.get(i), currentSpendingValues.get(i)));
        }

        // Add a new budget to the stack
        stacksTesting.addBudget(allBudgets, incomeEntries, expenseEntries);

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

        

         // Top row (0) - "INCOME" label
        JLabel incomeLabel = new JLabel("INCOME");
        addComponent(incomeLabel, 0, 0);

        //add button besie income
        addIncomeFieldButton = new JButton("ADD");
        addComponent(addIncomeFieldButton, 0, 1);

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

        //Total Income label followed by total income field
        JLabel totalIncomeLabel = new JLabel("Total Income");
        addComponent(totalIncomeLabel, numberIncomeRows +1, 0);

        // set up text box for displaying total income.  Users cam view, but cannot directly edit it
        totalIncomeField = new JTextField("0", 10);   // 0 initially, with 10 columns
        totalIncomeField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        totalIncomeField.setEditable(false);    // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalIncomeField, numberIncomeRows +1, 1);

        //blank row after total income
        JLabel blankLabel1 = new JLabel(" ");
        addComponent(blankLabel1, numberIncomeRows+2, 0);

        //Spending header
        JLabel spendingLabel = new JLabel("SPENDING");
        addComponent(spendingLabel,  numberIncomeRows + 4, 0);

        //add spending field button
        //add button besie income
        addSpendingFieldButton = new JButton("ADD");
        addComponent(addSpendingFieldButton, numberIncomeRows + 4, 1);

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

        //Undo Button
        undoButton = new JButton("Undo");
        //undoButton.setEnabled(false);
        addComponent(undoButton, numberIncomeRows + numberSpendingRows + 8, 0);
        
        //Exit Button
        exitButton = new JButton("Exit");
        addComponent(exitButton, numberIncomeRows + numberSpendingRows + 8, 1);

        // Save the current fields to the stack
        //only saves initial fields
        if (allBudgets.isEmpty()) {
            saveFields(); // Save the current state as the first state in the stack
        }

        // set up  listeners (in a spearate method)
        initListeners();
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
        }


        //check if the current values are the same as the most recent budget
        checkIfValuesAreSame();    
        

        //grab the most recent budget
        Budget mostRecentBudget = allBudgets.pop();
        //System.out.println("Most recent budget: " + mostRecentBudget);

        //remove everything from incomeCategories, spendingCategories, currentIncomeValues, and currentSpendingValues
        incomeCategories.clear();
        spendingCategories.clear();
        currentIncomeValues.clear();
        currentSpendingValues.clear();

        //repopulate incomeCategories, spendingCategories, currentIncomeValues, and currentSpendingValues
        // from mostRecentBudget
        for (Budget.Entry income : mostRecentBudget.income) {
            incomeCategories.add(income.description);
            currentIncomeValues.add(income.amount);
        }
        for (Budget.Entry spending : mostRecentBudget.expenses) {
            spendingCategories.add(spending.description);
            currentSpendingValues.add(spending.amount);
        }

        //System.out.println("Updated income categories: " + incomeCategories);
        //System.out.println("Updated income values: " + currentIncomeValues);
        //System.out.println("Updated spending categories: " + spendingCategories);
        //System.out.println("Updated spending values: " + currentSpendingValues);

        //remove all components from the panel
        removeAll();

        //reinitialize the components
        initComponents(); // Reinitialize the components

        // Refresh the layout
        revalidate(); // Refresh the panel to show the new components
        repaint(); // Repaint the panel to ensure it displays correctly

        //System.out.println("UI components reinitialized and layout refreshed.");
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
        newStart newContentPane = new newStart(frame);
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