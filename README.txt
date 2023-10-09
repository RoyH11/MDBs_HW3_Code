MDBs_HW3_Code
Author: Roy Huang

This is a homework assignment to demonstrate how to connect to postgresql relational database to a local application.
The program is written in java. 

To execute the program the user will have to use the IntelliJ IDEA. 
I tried to use javac and java in command prompt but ran into some issue with linking the database driver.

To run the code:
* The user will open the folder as a IntelliJ java project.
* Add the newest JDK for compiling the code.
* In 'project structure'-> 'libraries' add JDBC driver. User can download the driver in the following link:https://jdbc.postgresql.org/download/
* In src/credentials, user needs to fill in the first line with their username, and second line with their password.
* Change the URL in line 10 to their database url.
* After the above steps, click run App.java.

Follow the user interface to change or get information from database.

The application provides 9 functionalities:
1. List all the countries
2. Select a city/cities
3. Add a new city
4. Update a city
5. Delete a city
6. List all active venues given a country code
7. List all inactive venues
8. Delete a venue
9. Add an event

User can also link the database to the IntelliJ IDEA database tool to view the changes in the database.

Thank you for reading.