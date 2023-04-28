import java.sql.*;

public class BackEnd {
    Connection dbconn = null;
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

        try {
                dbconn = DriverManager.getConnection
                               (oracleURL,username,password);

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

    public ResultSet query1(String guestName) {
        return null;
    }

    public ResultSet query2(String date) {
        return null;
    }

    public ResultSet query3(String weekDate) {
        return null;
    }

    public ResultSet query4(String dateStart, String dateEnd) {
        return null;
    }

    public ResultSet query5(String TBD) {
        return null;
    }

    public boolean addGuest(String firstName, String lastName, boolean isStudent, String creditCardCompany) {
        //returns true if successfully added
        return false;
    }

    public boolean updateGuest(int guestID, String firstName, String lastName, boolean isStudent, String creditCardCompany) {
        //returns true if successfully updated
        return false;
    }

    public boolean deleteGuest(int guestID) {
        //returns true if successfully deleted
        return false;
    }

    public boolean addRating(int guestID, int amenityID, int rating, String date) {
        //returns true if successfully added
        return false;
    }

    public boolean updateRating(int guestID, int amenityID, int rating, String date) {
        //returns true if successfully updated
        return false;
    }

    public boolean deleteRating(int guestID, int amenityID, String date) {
        //returns true if successfully deleted
        return false;
    }

    public boolean addClubMember(int guestID) {
        //returns true if successfully added
        return false;
    }

    public boolean updateClubMember(int guestID, int points) {
        //returns true if successfully updated
        return false;
    }

    public boolean removeClubMember(int guestID) {
        //returns true if successfully removed
        return false;
    }

    public boolean addBooking(int guestID, String startDate, String endDate, String roomID) {
        //returns true if successfully added
        return false;
    }

    public boolean updateBooking(int bookingID, String startDate, String endDate, String roomID) {
        //returns true if successfully updated
        return false;
    }

    public boolean removeBooking(int bookingID) {
        //returns true if successfully removed
        return false;
    }

    public boolean addTransaction(int bookingID, int amenityID, int extraCharge, String date, boolean isPaid, int tip) {
        //returns true if successfully added
        return false;
    }

    public boolean addRoom(String roomID, String type) {
        //returns true if successfully added
        return false;
    }

    public boolean addEmployee(String firstName, String lastName, String position) {
        //returns true if successfully added
        return false;
    }

    public boolean updateEmployee(int employeeID, String position, String FirstName, String LastName) {
        //returns true if successfully updated
        return false;
    }

    public boolean addShift(String TBD) {
        //returns true if successfully added
        return false;
    }

    public boolean updateShift(String TBD) {
        //returns true if successfully updated
        return false;
    }

    public boolean deleteShift(String TBD) {
        //returns true if successfully deleted
        return false;
    }

    public boolean deleteEmployee(int employeeID) {
        //returns true if successfully deleted
        return false;
    }

    public boolean addAmenity(String name, int price) {
        //returns true if successfully added
        return false;
    }
    
    public boolean updateAmnity(int amenityID, String name, int price) {
        //returns true if successfully updated
        return false;
    }

    public boolean deleteAmenity(int amenityID) {
        //returns true if successfully deleted
        return false;
    }
}
