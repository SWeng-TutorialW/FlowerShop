package il.cshaifasweng.OCSFMediatorExample.server;

import java.io.IOException;
import java.sql.Connection;

/**
 * Hello world!
 *
 */
public class App 
{
	
	private static SimpleServer server;
    public static void main( String[] args ) throws IOException
    {
        // Test database connection
        try (Connection conn = DBUtils.getConnection()) {
            if (conn != null) {
                System.out.println("Connected to the database!");
            }
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
            // Optionally: exit the program if connection fails
            return;
        }

        // If the connection is successful, continue starting the server
        server = new SimpleServer(3000);
        server.listen();
    }

}
