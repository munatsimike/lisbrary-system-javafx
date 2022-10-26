# Java fx library system end-assignment 2022
## Summary
The assisgment is to develop a library system with the following functions
- Login function
- Show a list of items and members in a table view
- Manage items and members
- Save and retrieve members and items from files and in-memory database
## Screens
The application contains the following screens;
    1. Login 
    2. Main window
    3. Items table
    4.Members's table
    5. Manage buttons
    6. Notification
    7. Item dialog form
    8. Member dialog form
    9. Menu
    10. Search form
    All the above mentioned screens except for the login screen are seperate screens combined together in the main window to form one functional application.

## Username & Passwords
Any one of the following passwords can be used to gain access to the library system
    1. **username**: munatsimike **password**: RukudzoM7*
    2. **username**: someuser **password**: RukudzoM7*
    
## Code from other sources
While most of the code used in this project is from the lectures and slides from the university, I used the following code from Stackoverflow.
    1. Regex to validate password on the login screen
    2. Code to search for items and members in a table view
    3. Code to format date of birth (dd-mm-yyyy) on the table view
**NB:** I did not copy and paste the code as it is, but I modified the code to suit the assignment requirements. 

## Unresolved issues
I used Sonarlint to analyse and correct issues. However, 3 issues remain unresolved.
 **Issue 1**: Loops should not be infinite
     I use a try-and-catch to break a while loop that reads objects from files. Sonarlint thinks it's an infinite loop.
     **Issue 2**: Inheritance tree of classes should not be too deep
This issue is on the code that formats date of birth to dd-mm-yyyy on the table view, and I could not figure out how to resolve this issue.
**Issue 3**: Try-catch blocks should not be nested
The issue is with the code that reads objects from files. I could not resolve this issue because I used the code from the slides

