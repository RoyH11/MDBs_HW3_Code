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
        System.out.println("---------------------------------------------");
        System.out.println("Please select an option from the menu below:");
        System.out.println("1. List all the countries");
        System.out.println("2. Select a city/cities");
        System.out.println("3. Add a new city");
        System.out.println("4. Update a city");
        System.out.println("5. Delete a city");
        System.out.println("6. List all active venues given a country code");
        System.out.println("7. List all inactive venues");
        System.out.println("8. Delete a venue");
        System.out.println("9. Add an event");
        System.out.println("10. Exit");
        System.out.println("11. test");
    }


    private static String getUserInput() {
        return scanner.nextLine().trim();
    } // getUserInput

    private static String lowerCase(String input){
        return input.toLowerCase();
    } // lowerCase helper method

    private static boolean sqlHandler(String sql, String... attribute){
        // sql execution helper method
        boolean result = false;
        int counter = 0;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                for (String s : attribute) {
                    System.out.print(resultSet.getString(s)+" ");
                }
                System.out.println();
                counter++;
            }

            if (counter > 0){
                result = true;
            }
            return result;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return result;
        }
    }


    private static void listCountries() {
        // List all the countries
        String sql = "SELECT * FROM homework.countries;";
        sqlHandler(sql, "country_name", "country_code");
    }

    private static boolean searchCity(String city_name) {
        // Search a city by name
        boolean result = true;
        city_name = lowerCase(city_name);
        String sql = "SELECT * FROM homework.cities WHERE LOWER(name) = '" + city_name +"';";
        if (!sqlHandler(sql, "name", "postal_code")){
            result = false;
        }

        return result;
    }

    private static void createCity(String city_name, String postal_code, String country_code){
        // Create a city
        String sql = "INSERT INTO homework.cities VALUES ('"+city_name+"', '"+postal_code+"', '"+country_code+"');";
        sqlHandler(sql, null);
    }

    private static void updateCity(String city_name, String postal_code){
        // Update a city
        city_name = lowerCase(city_name);
        postal_code = lowerCase(postal_code);
        String sql = "UPDATE homework.cities SET LOWER(postal_code) = '"+postal_code+"' WHERE LOWER(name) = '"+city_name+"';";
        sqlHandler(sql, null);
    }

    private static void deleteCity(String city_name){
        // Delete a city
        city_name = lowerCase(city_name);
        String sql = "DELETE FROM homework.cities WHERE LOWER(name) = '"+city_name+"';";
        sqlHandler(sql, null);
    }

    private static void listAllActiveVenues(String country_code){
        // List all active venues given a country code
        country_code = lowerCase(country_code);
        String sql = "SELECT * FROM homework.venues WHERE LOWER(country_code) = '"+country_code+"' AND active = true;";
        sqlHandler(sql, "name");
    }

    private static void listAllInactiveVenues(){
        // List all inactive venues
        String sql = "SELECT * FROM homework.venues WHERE active = false;";
        sqlHandler(sql, "name");
    }

    private static void deleteVenue(String venue_name){
        // Delete a venue
        venue_name = lowerCase(venue_name);
        String sql = "DELETE FROM homework.venues WHERE LOWER(name) = '"+venue_name+"';";
        sqlHandler(sql, null);
    }

    private static void addEvent(String title, String starts, String ends, String venue, String postal, String country){
        // Add an event
        String sql = "INSERT INTO homework.events VALUES ('"+title+"', '"+starts+"', '"+ends+"', '"+venue+"', '"+postal+"', '"+country+"');";
        sqlHandler(sql, null);
    }



    public static void main(String[] args) {

        boolean loop = true;

        String choice;
        String city_name;
        String postal_code;
        String country_code;
        String venue_name;

        String title, starts, ends, venue, postal, country;

        while (loop){
            menu();
            choice = getUserInput();

            switch (choice) {
                case "1":
                    // List all the countries
                    listCountries();
                    break;
                case "2":
                    // Search a city
                    System.out.print("Enter city name: ");
                    city_name = getUserInput();
                    if(!searchCity(city_name)){
                        System.out.println("City not found!");
                    }
                    break;
                case "3":
                    // Add a new city
                    System.out.print("Enter city name: ");
                    city_name = getUserInput();
                    if (searchCity(city_name)){
                        System.out.println("City already exists!");
                    } else{
                        System.out.print("Enter postal code: ");
                        postal_code = getUserInput();
                        System.out.print("Enter country code: ");
                        country_code = getUserInput();
                        createCity(city_name, postal_code, country_code);
                    }
                    break;
                case "4":
                    // Update a city
                    System.out.print("Enter city name: ");
                    city_name = getUserInput();
                    System.out.print("Enter new postal code: ");
                    postal_code = getUserInput();
                    //country_code = getUserInput();
                    updateCity(city_name, postal_code);
                    break;
                case "5":
                    // Delete a city
                    city_name = getUserInput();
                    deleteCity(city_name);
                    break;
                case "6":
                    // List all active venues given a country code
                    country_code = getUserInput();
                    listAllActiveVenues(country_code);
                    break;
                case "7":
                    // List all inactive venues
                    listAllInactiveVenues();
                    break;
                case "8":
                    // Delete a venue
                    venue_name = getUserInput();
                    deleteVenue(venue_name);
                    break;
                case "9":
                    // Add an event
                    title = getUserInput();
                    starts = getUserInput();
                    ends = getUserInput();
                    venue = getUserInput();
                    postal = getUserInput();
                    country = getUserInput();
                    addEvent(title, starts, ends, venue, postal, country);
                    break;
                case "10":
                    // Exit
                    loop = false;
                    break;
                case "11":
                    // test
                    String test = getUserInput();
                    if(test.isEmpty()){
                        System.out.println("test");
                    }
                    break;
            }
        }
    }
}