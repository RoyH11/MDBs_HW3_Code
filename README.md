# MDBs_HW3_Code
**Author:** Roy Huang

This is a homework assignment to demonstrate how to connect to PostgreSQL relational database from a local application. The program is written in Java.

## How to Run

To execute the program, follow these steps:

1. Open the folder as an IntelliJ IDEA Java project.
2. Add the newest JDK for compiling the code.
3. In 'project structure' -> 'libraries,' add JDBC driver. You can download the driver from [here](https://jdbc.postgresql.org/download/).
4. In `src/credentials`, fill in the first line with your username, and the second line with your password.
5. Change the URL in line 10 to your database URL.
6. After completing the above steps, click `Run App.java`.

## Functionalities

The application provides 9 functionalities:

1. List all the countries.
2. Select a city/cities.
3. Add a new city.
4. Update a city.
5. Delete a city.
6. List all active venues given a country code.
7. List all inactive venues.
8. Delete a venue.
9. Add an event.

## Database Integration

You can also link the database to the IntelliJ IDEA database tool to view the changes in the database.

Thank you for reading.
