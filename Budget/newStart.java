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


//TODO 
// - create method that saves all the current input fields
// - then test that the method works
// - then create a method that loads the saved fields
// - then test that the method works


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

import Budget.stacksTesting.Budget;


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

    private JTextField totalSpendingField;
    private JTextField overalTotalField;


     // Income, spending categories
    ArrayList<String> incomeCategories = new ArrayList<>(Arrays.asList("Wages", "Loans", "Other"));
    ArrayList<String> spendingCategories = new ArrayList<>(Arrays.asList("Food", "Rent", "apples"));

    private ArrayList<String> currentIncomeValues = new ArrayList<>(); // To store income values
    private ArrayList<String> currentSpendingValues = new ArrayList<>(); // To store spending values

    // constructor - create UI  (dont need to change this)
    public newStart(JFrame frame) {
        topLevelFrame = frame; // keep track of top-level frame
        setLayout(new GridBagLayout());  // use GridBag layout
        initComponents();  // initalise components
    }


    // method to save all the current input fields
    public void saveFields() {
        /*
         *  Will save all the current input fields input into a stack
         *  incomes will be saved in a list of entries, eg [salary: 1000, freelance: 2000, investment: 3000]
         *  expenses will be saved in a list of entries, eg [rent: 100, utilities: 200, subscriptions: 300]
         */

        System.out.println(incomeCategories);
        System.out.println(spendingCategories);

        System.out.println(currentIncomeValues);
        System.out.println(currentSpendingValues);



    }





    // initialise componenents
    // Note that this method is quite long.  Can be shortened by putting Action Listener stuff in a separate method
    // will be generated automatically by IntelliJ, Eclipse, etc
    private void initComponents() { 

        
        int numberIncomeRows = incomeCategories.size();
        incomeFields = new JTextField[numberIncomeRows];

        int numberSpendingRows = spendingCategories.size();
        spendingFields = new JTextField[numberSpendingRows];

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
            incomeFields[i] = new JTextField("", 10);
            incomeFields[i].setHorizontalAlignment(JTextField.RIGHT);
            addComponent(incomeFields[i], i + 1, 1);
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
            spendingFields[i] = new JTextField("", 10);
            spendingFields[i].setHorizontalAlignment(JTextField.RIGHT);
            addComponent(spendingFields[i], numberIncomeRows + 5 + i, 1);
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


        //Exit Button
        exitButton = new JButton("Exit");
        addComponent(exitButton, numberIncomeRows + numberSpendingRows + 8, 0);


        // set up  listeners (in a spearate method)
        initListeners();
    }

    // set up listeners
    // initially just for buttons, can add listeners for text fields
    private void initListeners() {

        // exitButton - exit program when pressed
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
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

    
    // Add a DocumentListener to a text field to trigger calculations when text is changed
    private void addTriggerCalculationsListener(JTextField field) {
    field.getDocument().addDocumentListener(new DocumentListener() {
        // Trigger calculations when text is inserted
        public void insertUpdate(DocumentEvent e) {
            updateIncomeSpendingValuesList();
            triggerCalculations();
            saveFields();
            
            
        }
        // Trigger calculations when text is removed
        public void removeUpdate(DocumentEvent e) {
            updateIncomeSpendingValuesList();
            triggerCalculations();
        }
        // Trigger calculations when text is changed
        public void changedUpdate(DocumentEvent e) {
            updateIncomeSpendingValuesList();
            triggerCalculations();
        }
    });
}

    // add a component at specified row and column in UI.  (0,0) is top-left corner
    private void addComponent(Component component, int gridrow, int gridcol) {
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;   // always use horixontsl filll
        layoutConstraints.gridx = gridcol;
        layoutConstraints.gridy = gridrow;
        add(component, layoutConstraints);

    }

    //method for updating the currentIncomeValues and currentSpendingValues lists
    private void updateIncomeSpendingValuesList() {
        // Clear the current values lists
        currentIncomeValues.clear();
        currentSpendingValues.clear();

        // Loop through incomeFields and add values to currentIncomeValues
        for (JTextField field : incomeFields) {
            currentIncomeValues.add(field.getText());
        }

        // Loop through spendingFields and add values to currentSpendingValues
        for (JTextField field : spendingFields) {
            currentSpendingValues.add(field.getText());
        }
    }

    // Trigger calculations for income, spending, and overall total
    private void triggerCalculations() {
        calculateTotalIncome();
        calculateTotalSpending();
        calculateOverallTotal();
    }

    public double calculateTotal(JTextField[] fields) {
        double total = 0.0;

        // Loop through the fields array to get values and sum them
        for (JTextField field : fields) {
            double value = getTextFieldValue(field);

            // Exit and return 0 if any value is NaN (error)
            if (Double.isNaN(value)) {
                return 0.0;
            }

            total += value;
        }

        return total;  // Return the calculated total
    }

    // Calculate total income using incomeFields array
    public void calculateTotalIncome() {
        double totalIncome = calculateTotal(incomeFields);
        totalIncomeField.setText(String.format("%.2f", totalIncome));  // Update the UI field with formatted total
    }

    //Calculate total spending using spendingFields array 
    public void calculateTotalSpending() {
        double totalSpending = calculateTotal(spendingFields);
        totalSpendingField.setText(String.format("%.2f", totalSpending));
    }

    //Calculate overall total using total income and total spending
    public void calculateOverallTotal() {
        double overallTotal = calculateTotal(incomeFields) - calculateTotal(spendingFields);
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
                JOptionPane.showMessageDialog(topLevelFrame, "Please enter a valid number");  // show error message
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

        //testing saveing stacks works
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
        stacksTesting.addBudget(allBudgets, incomeValues, expenseValues);

        



        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }


}