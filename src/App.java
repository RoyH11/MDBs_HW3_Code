import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {

    private static final String url = "jdbc:postgresql://s-l112.engr.uiowa.edu:5432/mdb_student02";
    private static final String user;
    private static final String password;
    private static final Connection connection;

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

    private void menu(){
        // Create a menu for the user to select from
        System.out.println("Please select an option from the menu below:");
        System.out.println("1. List all the countries");
        System.out.println("2. Select a city/cities");
        System.out.println("3. Add a new city to the cities table");
        System.out.println("4. Update a city in the cities table");
        System.out.println("5. Delete a city from the cities table");
    }
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
    public static void main(String[] args) {

    }
}