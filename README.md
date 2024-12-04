JACOB CORBETT
STUDENT ID: 52316427
Github link: https://github.com/mightymander/CS2020-CA2-2024

### Important Info

main .java files located in /src/main/java/Budget
test .java files located in /src/test/java/Budget

---

- before getting into the program i would like to say that the calculate button has been removed for the reason that its redundant, the program will act like a spread sheet and automatically calculate. (Extensions, bullet point 2)
- When first loading up the program you should see the nice window, with the 3 default income and spending fields, along with their input boxes and a dropdown menu of the time frame you would like to use for them.
- for the input fields you can enter whatever you want into them, being integers or strings, during testing i found that a pop up every single time you entered not a number was actually very annoying so i removed it and replaced it with simply dynamically changing the total fields to "invalid number" this makes it much less annoying when entering a not valid input. (basic system, bullet point 1,2,4 )
- the overall total output will change colour based on if the overall total is positive or negative so black if positive and red if negative. (basic system, bullet point 3 )
- for the dropdowns being per year, per month or per week was simple enough because the overall total is per year, you simply convert the fields if they are not per year, so per month = * 12, and per week = *52. (Extensions, bullet point 1)
- finally the programs made objective to have a undo button, at first this seemed to be something i would be confused about however the lecture that was given in relation to this, where the lecturer explained how he would recommend attacking this problem made me understand what i should do. When first going to tackle this problem i first started by creating a new .java file where i could test and experiment independently of my already created program, so i did not need to worry about any compatibility or anything like that. (Undo, bullet point 1,2)
- instead of trying to implement 1 level of undo and then trying to alter it to be able to go forever, i just went straight to making the complete undo. its very simple the format for the stack is, each Budget object contains a depth, a list of income entries, a list of expense entries and a list of time entries. Looking back depth isn't 100% required however its very useful for debugging being able to see at a glace what depth i am looking at, and since it doesn't effect the user i decided to keep it in.
- For testing the undo, inside the test folder there is "SaveStateTests.java" which conducts multiple different tests on the undo to make sure that it is functioning correctly

---

- Now i will briefly explain the program as a whole from top to bottom.
- FILE: BudgetMain.java

- The first few things that are done are importing all the necessary classes and libraries that are required. Including Swing components that are used to build the GUI, utility classes that are used for managing data. The Budget class from Budget.backupStacks which is for handing the back up functionality.
- Then the next things that are done is setting everything up with defining fields and initialising default values.

- The constructor is called which, creates the frame, gives all required fields there default values, Then saves the state of the program which will be the initial state where everything has default values eg 0. Then some swing calls to create layout and then initialise the components

- When the "initComponents" is run, it first gets the size of all the arrays so that we can do things that required the length of an array, the arrays of objects contain, income, spending and time drop downs objects.
- Then "addNamedLabelsToPanel" is run which adds the labels, Spending and income
- "addSummaryFields" is run which adds the total income, total spending, overall total text labels and also creates the output fields where the totals will be displayed, this is where they are modified to not allow editing so the user cannot change them, and the length of them to be 10 columns by default.
- "addBudgetEntryFieldsToPanel" is run which dynamically adds based on the number of rows that was defined earlier the either default and custom labels entered by the user and there input fields, for the int value and time value, also add a Event listener to the input fields.
- "addActionButtonsToPanel" is run which adds all the buttons that do actions, such as the two add buttons that are beside the Income and Spending Texts which allow the user to add their own custom income and spending categories as well as the undo button and the exit button.

## listeners section

- Next the listeners section, which basically adds all the functionality to the buttons and input fields.
- The exit button is very simple it just called "System.exit(0);" which just exits the program.
- The undo button called "revertToPreviousBudget" and "triggerCalculations", revertToPreviousBudget is the method that goes back to the previous version of the app eg the undo and triggering the calculations just makes sure that the total fields are updated with the new/old values.
- The undo button shortcut which was added later on, his was done by adding a key binding to the root pane’s InputMap to map Ctrl+Z to an action called "undo". Then, in the ActionMap, the "undo" action is linked to the revertToPreviousBudget and triggerCalculations methods. Before its run it checks if its allowed to undo, stopping any potential bugs.
- The add income and add spending field buttons, call their respective addIncome/SpendingField method and then triggerCalculations
- The method "addTriggerCalculationsListener" will update the the income spending and time value lists, trigger all the calculations and then save state because the time drop down box has changed.
- Then the last one for the text boxes where the user enters the numbers, when ever there is a change it will, updated the income spending and time value list, trigger calculations so that the total income spending and overall total are up to date and then save the state because the numbers have changed.

## methods that add more income and spending fields

- Now onto the methods that add more income and spending fields
- There is two methods here "addSpendingField" and "addIncomeField"
- They are very similar they work by sending a pop up to the user and asking for them to enter the name they would like it to be titled, if the user enters a blank string or clicks the okay button without adding anything then show error message. this stops the fields being adds that appear blank.
- Then it adds the new input field to the "incomeCategories" List and adds the default values to currentIncomeValues and currentIncomeTimeValues
- Then it will save the state of the game to the stack.
- Then refresh the layout which will now reflect the new field that was added.

## undo logic

- Now we move onto the logic that handles going back to the previous state, saving fields and checking if the ui is the same and going back to the previous budget.
- "saveCurrentBudgetState" first created two local lists "incomeEntries" and "expenseEntries" this is used to convert the 3 different lists that are used in the program into the format for the stack, which is description, amount, timeFrequency.
- then just "backupStacks.addBudget" which adds the budget to the stack.
- Now onto "revertToPreviousBudget" this method is the undo buttons back end, it firstly checks "isUIIdenticalToLastBudget" and if true then pop the stack, this is used to fix the problem of when you would first press the undo button in a sequence it would not do anything on the first press this is not a error its working as expected, its putting you back to the most recent save however the most recent save is the exact same as the current save so it appears the the end user that nothing is happening. so isUIIdenticalToLastBudget was created to solve this problem, it works by doing lots of checks to see if the current state of the GUI is the same as the most recent save in the stack if so then remove it. Pop the most recent budget into mostRecentBudget, clear all the lists and repopulate them using the mostRecentBudget data then clear and repaint the UI and done that is the logic of the undo button. Then enable/disable the undo button depending on the state of the stack and screen. Finally if the stack is empty put instal values back and save the stack to prevent the stack from being empty.
- now "checkIfUndoButtonShouldBeEnabled" this is the logic if the user should be able to undo eg when the program first loads the undo button would not do anything so its greyed out, this was tricky to implement because of the way i did the whole back up thing, I kept running into the bug, when there was 1 more thing to undo eg 1 number the undo button would grey out because the stack only had 1 thing in it the default values. So i fixed the bug by checking if there was any text on the screen that would mean we should be able to undo so if there is text on the screen and only 1 left eg the default values then let the undo happen.

## methods for updating the current income, spending and time values lists

- "updateIncomeSpendingTimeValuesList" this is a very simple method, which starts by clearing all the lists, and then looking at the UI and setting the Current UI to the lists.

## methods for calculating total income, spending and overall total

- The first method here is "triggerCalculations" which just runs the other 3, calculateTotalIncome, calculateTotalSpending, calculateOverallTotal.
- calculateTotalIncome works but first calling calculateTotal which loops through the fields list and just sums them, also taking into account the time and doing the correct calculation to turn it into per year. taking into account errors so if its NAN then return NAN and break, now back to calculateTotalIncome if the return is NAN then set the total field to be "Invalid Number" if not then set the text on the total income field to be the total.
- calculateTotalSpending works very similarly.
- calculateOverallTotal works by getting the total income value and spending value not by calculating anything but by reading the screen because this method is always called after the other two are called those will always be updated fist. The same catch again if total is NAN display Invalid number, and if the overall total is negative display the number in red.

- Then the rest of the methods are boring, methods that were already included and are needed for swing to work.

## Testing

- looking at "SaveStateTests.java" Here this is where i implemented testing for my undo feature, it ended up being much more difficult than expected and took a lot more time than expected, however its done now and works in testing that the undo feature is doing what its supposed to do.
- So first off in the file I created lots of methods to help me, including wait x amount of time, enter certain keys, check the state of UI, enter details for the UI etc.
- The first test was to make sure the testing tools/features are all working, its simply checking if true = true which it should always, however its a useful thing to keep when something breaks it can help you narrow down the suspect.
- The second test "testBudgetCreated" simply checks if the creation of the backup stack is working correctly
- And the third test "testSingleDepthBudgetCreation" is still simple but inputs fake data into the stack and try's to read it just making sure its all working.
- Now onto the 3 main tests, the first one "testUndoFunctionalityBasic" creates the GUI and moves around like a user would, to simulate real world circumstances, this test just adds 123 to the first input field in the income part of the UI, and undo's and checks every time that its getting the expected result.
- Now the second main test, "testUndoFunctionalityIntermediate" fills the whole UI but does not create any new fields or touch time intervals, it works on the same approach, fills the whole UI and undo's back to default checking every time for an expected output.
- The last main test, "testUndoFunctionalityAdvanced", works on the same principal however it adds fields to income and spending, and changes time values, and undo's right back to default checking every state is correct.
