JACOB CORBETT
STUDENT ID: 52316427

basic system (30 pts)
• 10 pts: user enters three (or more) income fields (wages, loan, other) and the system
computes total income when a Calculate button is pressed.
• 10 pts: user enters three (or more) spending fields (food, rent, other) and the system
computes total income when a Calculate button is pressed.
• 5 pts: When Calculate is pressed, the system also shows surplus/deficit (income minus
spending). This is black if positive or zero, and red if negative.
• 5 pts: System checks user input (numbers for validity), and produces an appropriate error
message if input is not valid. Empty fields are treated as 0 (no error message)
Extensions (20 pts)
• 10 pts: allow users to specify income/expenditure numbers per week, per month, or per
year, for each input field. This requires both adding appropriate choice widgets (such as
combo boxes), and also modifying the way you calculate totals. You can assume that there
are 52 weeks in a year, 12 months in a year, and 4.3333333 weeks in a month.
• 10 pts: implement “spreadsheet” behaviour, that is totals are updated whenever the user
changes a number or time-period, with no need to press a Calculate button. You should
update whenever the focus shifts, as well as whenever an action is performed.
Undo (30 pts)
• 10 pts: implement a single-level of Undo, so the user can “undo” his or her most recent
action. This require saving the state of the system (ie, all numbers) when a change is made.
• 10 pts: implement multiple Undo, so the user can undo multiple actions. We recommend
that you create a class to hold state information numbers, and then maintain a stack of
states.
• 10 pts: Good collection of JUnit tests for Undo
Programming style (20 pts)
• 10 pts: Java code follows good coding style, including good code comments and variable
names
• 10 pts: Java code is well structured and decomposed into methods; for example win/loss
detection is done using methods that check for a win in a specified row or column.
