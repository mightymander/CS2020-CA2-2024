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
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

// class definition
public class BudgetBase extends JPanel {    // based on Swing JPanel

    // high level UI stuff
    JFrame topLevelFrame;  // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // used to control layout

    // widgets which may have listeners and/or values
    // widgets which may have listeners and/or values
    private JButton calculateButton;   // Calculate button
    private JButton exitButton;        // Exit button
    private JTextField[] incomeFields; // Array for income text fields
    private JTextField[] spendingFields; // Array for spending text fields
    private JTextField totalIncomeField; // Total Income field
    private JTextField totalSpendingField;
    private JTextField overalTotalField;

    private JButton addIncomeFieldButton;
    private JButton addSpendingFieldButton;


    // Income, spending categories
    ArrayList<String> incomeCategories = new ArrayList<>(Arrays.asList("Wages", "Loans", "Other"));
    ArrayList<String> spendingCategories = new ArrayList<>(Arrays.asList("Food", "Rent", "apples"));


    private ArrayList<Double> currentIncomeValues = new ArrayList<>(); // To store income values
    private ArrayList<Double> currentSpendingValues = new ArrayList<>(); // To store spending values



    // constructor - create UI  (dont need to change this)
    public BudgetBase(JFrame frame) {
        topLevelFrame = frame; // keep track of top-level frame
        setLayout(new GridBagLayout());  // use GridBag layout
        initComponents();  // initalise components
    }

    // initialise componenents
    // Note that this method is quite long.  Can be shortened by putting Action Listener stuff in a separate method
    // will be generated automatically by IntelliJ, Eclipse, etc
    private void initComponents() {


        int numberIncomeRows = incomeCategories.size();
        incomeFields = new JTextField[numberIncomeRows];



        // Spending categories
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
            JLabel incomeCategoryLabel = new JLabel(incomeCategories.get(i));
            addComponent(incomeCategoryLabel, i + 1, 0);

            // Create corresponding text fields
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

        for (int i = 0; i < numberSpendingRows; i++) {
            JLabel spendingCategoryLabel  = new JLabel(spendingCategories.get(i));
            addComponent(spendingCategoryLabel, numberIncomeRows + 5 + i, 0);

            // Create corresponding text fields
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


        JLabel overallTotalLabel = new JLabel("Overall Total");
        addComponent(overallTotalLabel, numberIncomeRows+numberSpendingRows +7, 0);

        // set up text box for displaying total income.  Users cam view, but cannot directly edit it
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

        addIncomeFieldButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter additional Income field name:  ");
                incomeCategories.add(input);

                // Save current state before clearing components
                saveCurrentState();

                // Clear existing components
                removeAll(); // Remove all components from the panel

                // Re-initialize components
                initComponents(); // Re-run initComponents to set up the UI again

                // Restore saved state
                restoreSavedState();

                // Refresh the layout
                revalidate(); // Refresh the panel to show the new components
                repaint(); // Repaint the panel to ensure it displays correctly
            }
        });

        addSpendingFieldButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter additional Spending field name:  ");
                spendingCategories.add(input);

                // Save current state before clearing components
                saveCurrentState1();
                System.exit(ABORT);

                // Clear existing components
                removeAll(); // Remove all components from the panel

                // Re-initialize components
                initComponents(); // Re-run initComponents to set up the UI again

                // Restore saved state
                restoreSavedState();

                // Refresh the layout
                revalidate(); // Refresh the panel to show the new components
                repaint(); // Repaint the panel to ensure it displays correctly
            }
        });


        for (JTextField incomeField : incomeFields) {
            incomeField.getDocument().addDocumentListener(new DocumentListener() {

                public void insertUpdate(DocumentEvent e) {
                    triggerCalculations();
                }

                public void removeUpdate(DocumentEvent e) {
                    triggerCalculations();
                }

                public void changedUpdate(DocumentEvent e) {
                    triggerCalculations();
                }
            });
        }

        // Add DocumentListener to all spending fields
        for (JTextField spendingField : spendingFields) {
            spendingField.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) {
                    triggerCalculations();
                }

                public void removeUpdate(DocumentEvent e) {
                    triggerCalculations();
                }

                public void changedUpdate(DocumentEvent e) {
                    triggerCalculations();
                }
            });
        }
    }

    //method to save the current state of the all the fields values
    // using stack of objects
    private void saveCurrentState1() {

        //PAUSE THIS GOING TO PRACTIVE IN ANOTHER FILE
        // Save current values of income fields
        ArrayList<Double> currentIncomeValues = new ArrayList<>();
        for (JTextField field : incomeFields) {
            currentIncomeValues.add(getTextFieldValue(field)); // Add current value to the list
        }

        // Save current values of spending fields
        ArrayList<Double> currentSpendingValues = new ArrayList<>();
        for (JTextField field : spendingFields) {
            currentSpendingValues.add(getTextFieldValue(field)); // Add current value to the list
        }

        //append the new values to the stack of objects

        //debug print all
        System.out.println("Income values: " + currentIncomeValues);
        System.out.println("Spending values: " + currentSpendingValues);

    }



    // Method to save the current state of income and spending fields
    private void saveCurrentState() {
        // Save current values of income fields
        ArrayList<Double> currentIncomeValues = new ArrayList<>();
        for (JTextField field : incomeFields) {
            currentIncomeValues.add(getTextFieldValue(field)); // Add current value to the list
        }

        // Save current values of spending fields
        ArrayList<Double> currentSpendingValues = new ArrayList<>();
        for (JTextField field : spendingFields) {
            currentSpendingValues.add(getTextFieldValue(field)); // Add current value to the list
        }

        // Store in a class-level variable or pass them around as needed
        this.currentIncomeValues = currentIncomeValues;
        this.currentSpendingValues = currentSpendingValues;
    }


    // Method to restore the saved state to the fields
    private void restoreSavedState() {
        // Restore income fields
        for (int i = 0; i < incomeFields.length && i < currentIncomeValues.size(); i++) {
            incomeFields[i].setText(String.valueOf(currentIncomeValues.get(i))); // Set saved value
        }

        // Restore spending fields
        for (int i = 0; i < spendingFields.length && i < currentSpendingValues.size(); i++) {
            spendingFields[i].setText(String.valueOf(currentSpendingValues.get(i))); // Set saved value
        }
    }

    // Trigger calculations for income, spending, and overall total
    private void triggerCalculations() {
        calculateTotalIncome();
        calculateTotalSpending();
        calculateOverallTotal();
    }

    // add a component at specified row and column in UI.  (0,0) is top-left corner
    private void addComponent(Component component, int gridrow, int gridcol) {
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;   // always use horixontsl filll
        layoutConstraints.gridx = gridcol;
        layoutConstraints.gridy = gridrow;
        add(component, layoutConstraints);


    }

    // update totalIncomeField (eg, when Calculate is pressed)
    // use double to hold numbers, so user can type fractional amounts such as 134.50
    // General method to calculate the total for an array of text fields
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

    // Example: Calculate total spending using spendingFields array (if you need this)
    public void calculateTotalSpending() {
        double totalSpending = calculateTotal(spendingFields);
        totalSpendingField.setText(String.format("%.2f", totalSpending));
    }

    //does (income - spending)
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
        BudgetBase newContentPane = new BudgetBase(frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //make the frame bigger on start


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