JACOB CORBETT
STUDENT ID: 52316427

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

- before getting into the program i would like to say that the calculate button has been removed for the reason that its redundant, the program will act like a spread sheet and automatically calculate. (Extensions, bullet point 2)
- When first loading up the program you should see the nice window, with the 3 default income and spending fields, along with their input boxes and a dropdown menu of the time frame you would like to use for them.
- for the input fields you can enter whatever you want into them, being integers or strings, during testing i found that a pop up every single time you entered not a number was actually very annoying so i removed it and replaced it with simply dynamically changing the total fields to "invalid number" this makes it much less annoying when entering a not valid input. (basic system, bullet point 1,2,4 )
- the overall total output will change colour based on if the overall total is positive or negative so black if positive and red if negative. (basic system, bullet point 3 )
- for the dropdowns being per year, per month or per week was simple enough because the overall total is per year, you simply convert the fields if they are not per year, so per month = * 12, and per week = *52. (Extensions, bullet point 1)
- finally the programs made objective to have a undo button, at first this seemed to be something i would be confused about however the lecture that was given in relation to this, where the lecturer explained how he would recommend attacking this problem made me understand what i should do. When first going to tackle this problem i first started by creating a new .java file where i could test and experiment independently of my already created program, so i did not need to worry about any compatibility or anything like that.
