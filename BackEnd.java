import java.sql.*;

public class BackEnd {
    public BackEnd() {
        final String oracleURL =   // Magic lectura -> aloe access spell
                    "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";

        String username = "huyle"; //hardcoded username and password
        String password = "a6398";

            // load the (Oracle) JDBC driver by initializing its base
            // class, 'oracle.jdbc.OracleDriver'.

        try {
                Class.forName("oracle.jdbc.OracleDriver");

        } catch (ClassNotFoundException e) {

                System.err.println("*** ClassNotFoundException:  "
                    + "Error loading Oracle JDBC driver.  \n"
                    + "\tPerhaps the driver is not on the Classpath?");
                System.exit(-1);
        }
            
                // make and return a database connection to the user's
                // Oracle database
        
        Connection dbconn = null;
        Statement stmt = null;

        try {
                dbconn = DriverManager.getConnection
                               (oracleURL,username,password);
                stmt = dbconn.createStatement();

        } catch (SQLException e) {

                System.err.println("*** SQLException:  "
                    + "Could not open JDBC connection.");
                System.err.println("\tMessage:   " + e.getMessage());
                System.err.println("\tSQLState:  " + e.getSQLState());
                System.err.println("\tErrorCode: " + e.getErrorCode());
                System.exit(-1);

        }

        System.out.println("Connected to Oracle database!");
    }
}
