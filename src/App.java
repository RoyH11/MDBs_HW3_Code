import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class App {

    private static final String url = "jdbc:postgresql://s-l112.engr.uiowa.edu:5432/mdb_student02";
    private static final String user;
    private static final String password;
    private static final Connection connection;
    private static final Scanner scanner = new Scanner(System.in);


    static {
        try (BufferedReader br = new BufferedReader(new FileReader("src/credentials.txt"))) {
            // Read the first line as the username
            user = br.readLine();

            // Read the second line as the password
            password = br.readLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        connection = connect();
    }

    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    private static void menu(){
        // Create a menu for the user to select from
        System.out.println("Please select an option from the menu below:");
        System.out.println("1. List all the countries");
        System.out.println("2. Select a city/cities");
        System.out.println("3. Add a new city to the cities table");
        System.out.println("4. Update a city in the cities table");
        System.out.println("5. Delete a city from the cities table");
        System.out.println("6. Exit");
    }


    private static String getUserInput() {
        return scanner.nextLine().trim();
    } // getUserInput

    private static void sqlHandler(String sql, String attribute){
        // sql execution helper method
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                System.out.println(resultSet.getString(attribute));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listCountries() {
        // List all the countries
        String sql = "SELECT * FROM homework.countries;";
        sqlHandler(sql, "country_name");
    }


    private static void searchCity(String city_name) {
        // Search a city by name
        String sql = "SELECT * FROM homework.cities WHERE lower(name) = '" + city_name +"';";
        sqlHandler(sql, "name");
    }

    private static void createCity(String city_name, String postal_code, String country_code){
        // Create a city
        String sql = "INSERT INTO homework.cities VALUES ('"+city_name+"', '"+postal_code+"', '"+country_code+"');";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }







    public static void main(String[] args) {

        boolean loop = true;

        while (loop){
            menu();
            String choice = getUserInput();
            String city_name;
            String postal_code;
            String country_code;

            switch (choice) {
                case "1":
                    // List all the countries
                    listCountries();
                    break;
                case "2":
                    // Search a city
                    city_name = getUserInput();
                    searchCity(city_name);
                    break;
                case "3":
                    // Add a new city to the cities table
                    city_name = getUserInput();
                    postal_code = getUserInput();
                    country_code = getUserInput();
                    createCity(city_name, postal_code, country_code);
                    break;
                case "4":
                    // Update a city in the cities table
                    city_name = getUserInput();
                    postal_code = getUserInput();
                    country_code = getUserInput();
                    // TODO: create method
                    break;
                case "5":
                    // Delete a city
                    // TODO: start this
                    break;
                case "6":
                    // Exit
                    loop = false;
                    break;
            }
        }
    }
}