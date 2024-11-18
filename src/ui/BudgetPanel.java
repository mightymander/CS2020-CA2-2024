package src.ui;

import javax.swing.*;

import Budget.BudgetMain;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import logic.BudgetManager;
import src.model.Budget;

public class BudgetPanel extends JPanel {

    // high level UI stuff
    JFrame topLevelFrame;  // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // used to control layout

    // widgets which may have listeners and/or values
    private JButton exitButton;        // Exit button
    private JTextField totalIncomeField; // Total Income field

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
}


    // constructor - create UI  (dont need to change this)
    public BudgetPanel(JFrame frame) {
        
        topLevelFrame = frame; // keep track of top-level frame
        setLayout(new GridBagLayout());  // use GridBag layout
        initComponents();  // initalise components
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
 



    // add a component at specified row and column in UI.  (0,0) is top-left corner
    private void addComponent(Component component, int gridrow, int gridcol) {
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;   // always use horixontsl filll
        layoutConstraints.gridx = gridcol;
        layoutConstraints.gridy = gridrow;
        add(component, layoutConstraints);

    }
