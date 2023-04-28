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

    public boolean addGuest(String firstName, String lastName, String isStudent, String creditCardCompany) {
        assert isStudent == "0" || isStudent == "1" || isStudent == null;
        //put single quote around String
        String query = "INSERT INTO Guest (FirstName, LastName, StudentStatus, CreditCardCompany) VALUES (" 
            + "'" + firstName + "'" + ", " + "'" + lastName + "'" + ", " + isStudent + ", " + "'" + creditCardCompany + "'" + ")";

        return false;
    }

    public boolean updateGuest(int guestID, String firstName, String lastName, String isStudent, String creditCardCompany) {
        assert isStudent == "0" || isStudent == "1" || isStudent == null;
        String setStatement = "SET ";
        if (firstName != null) {
            setStatement += "FirstName = " + "'" + firstName + "'" + ", ";
        }
        if (lastName != null) {
            setStatement += "LastName = " + "'" + lastName + "'" + ", ";
        }
        if (isStudent != null) {
            setStatement += "StudentStatus = " + isStudent + ", ";
        }
        if (creditCardCompany != null) {
            setStatement += "CreditCardCompany = " + "'" + creditCardCompany + "'" + ", ";
        }
        String query = "UPDATE Guest " + setStatement.substring(0, setStatement.length() - 2) + " WHERE GuestID = " + guestID;
        //returns true if successfully updated
        return false;
    }

    public boolean deleteGuest(int guestID) {
        String query = "DELETE FROM Guest WHERE GuestID = " + guestID;
        //returns true if successfully deleted
        return false;
    }

    public boolean addRating(int guestID, int amenityID, int rating, String date) {
        //date is in YYYY-MM-DD format
        //convert to oracle date format
        String query = "INSERT INTO Rating (GuestID, AmenityID, Rating, Date) VALUES (" 
            + guestID + ", " + amenityID + ", " + rating + ", " + "TO_DATE(" + date + ", 'YYYY-MM-DD'))";
        //returns true if successfully added
        return false;
    }

    public boolean updateRating(int guestID, int amenityID, int rating, String date) {
        //date is in YYYY-MM-DD format
        //convert to oracle date format
        //update only rating
        String query = "UPDATE Rating SET Rating = " + rating 
            + " WHERE GuestID = " + guestID + " AND AmenityID = " 
            + amenityID + " AND Date = TO_DATE(" + date + ", 'YYYY-MM-DD')";
        //returns true if successfully updated
        return false;
    }

    public boolean deleteRating(int guestID, int amenityID, String date) {
        String query = "DELETE FROM Rating WHERE GuestID = " + guestID + " AND AmenityID = " 
            + amenityID + " AND Date = TO_DATE(" + date + ", 'YYYY-MM-DD')";
        //returns true if successfully deleted
        return false;
    }

    public boolean addClubMember(int guestID) {
        //insert into ClubMember table, guestID, and point = 0
        String query = "INSERT INTO ClubMember (GuestID, Points) VALUES (" + guestID + ", 0)";
        //returns true if successfully added
        return false;
    }

    public boolean updateClubMember(int guestID, int points) {
        String query = "UPDATE ClubMember SET Points = " + points + " WHERE GuestID = " + guestID;
        //returns true if successfully updated
        return false;
    }

    public boolean removeClubMember(int guestID) {
        String query = "DELETE FROM ClubMember WHERE GuestID = " + guestID;
        //returns true if successfully removed
        return false;
    }

    public boolean addBooking(int guestID, String startDate, String endDate, int roomID) {
        String query = "Insert into Booking (GuestID, StartDate, EndDate, RoomID) VALUES (" 
            + guestID + ", TO_DATE(" + startDate + ", 'YYYY-MM-DD'), TO_DATE(" + endDate + ", 'YYYY-MM-DD'), " + roomID + ")";
        //returns true if successfully added
        return false;
    }

    public boolean updateBooking(int bookingID, String startDate, String endDate, int roomID) {
        //if roomID is -1, then don't update roomID
        String setStatement = "SET ";
        if (startDate != null) {
            setStatement += "StartDate = TO_DATE(" + startDate + ", 'YYYY-MM-DD'), ";
        }
        if (endDate != null) {
            setStatement += "EndDate = TO_DATE(" + endDate + ", 'YYYY-MM-DD'), ";
        }
        if (roomID != -1) {
            setStatement += "RoomID = " + roomID + ", ";
        }
        String query = "UPDATE Booking " + setStatement.substring(0, setStatement.length() - 2) + " WHERE BookingID = " + bookingID;
        //returns true if successfully updated
        return false;
    }

    public boolean removeBooking(int bookingID) {
        String query = "DELETE FROM Booking WHERE BookingID = " + bookingID;
        //returns true if successfully removed
        return false;
    }

    public boolean addTransaction(int bookingID, int amenityID, int extraCharge, int isPaid, int tip) {
        String query = "INSERT INTO Transaction (BookingID, AmenityID, ExtraCharge, IsPaid, Tip) VALUES (" 
            + bookingID + ", " + amenityID + ", " + extraCharge + ", " + isPaid + ", " + tip + ")";
        //returns true if successfully added
        return false;
    }

    public boolean updateTransaction(int bookingID, int transactionID, int amenityID, int extraCharge, int isPaid, int tip) {
        //primary key is bookingID and transactionID
        String setStatement = "SET ";
        if (amenityID != -1) {
            setStatement += "AmenityID = " + amenityID + ", ";
        }
        if (extraCharge != -1) {
            setStatement += "ExtraCharge = " + extraCharge + ", ";
        }
        if (isPaid != -1) {
            setStatement += "IsPaid = " + isPaid + ", ";
        }
        if (tip != -1) {
            setStatement += "Tip = " + tip + ", ";
        }
        String query = "UPDATE Transaction " + setStatement.substring(0, setStatement.length() - 2) + " WHERE BookingID = " + bookingID + " AND TransactionID = " + transactionID;
        //returns true if successfully updated
        return false;
    }

    public boolean addRoom(int roomID, String type) {
        String query = "INSERT INTO Room (RoomID, Type) VALUES (" + roomID + ", '" + type + "')";
        //returns true if successfully added
        return false;
    }

    public boolean deleteRoom(int roomID) {
        String query = "DELETE FROM Room WHERE RoomID = " + roomID;
        //returns true if successfully deleted
        return false;
    }

    public boolean addEmployee(String firstName, String lastName, String position) {
        String query = "INSERT INTO Employee (FirstName, LastName, Position) VALUES ('" + firstName + "', '" + lastName + "', '" + position + "')";
        //returns true if successfully added
        return false;
    }

    public boolean updateEmployee(int employeeID, String position, String FirstName, String LastName) {
        String setStatement = "SET ";
        if (position != null) {
            setStatement += "Position = '" + position + "', ";
        }
        if (FirstName != null) {
            setStatement += "FirstName = '" + FirstName + "', ";
        }
        if (LastName != null) {
            setStatement += "LastName = '" + LastName + "', ";
        }
        String query = "UPDATE Employee " + setStatement.substring(0, setStatement.length() - 2) + " WHERE EmployeeID = " + employeeID;
        //returns true if successfully updated
        return false;
    }

    public boolean deleteEmployee(int employeeID) {
        String query = "DELETE FROM Employee WHERE EmployeeID = " + employeeID;
        //returns true if successfully deleted
        return false;
    }

    public boolean addShift(String EmployeeID, String StartTime, String EndTime, String WeekStartDate) {
        //returns true if successfully added
        String query = "INSERT INTO Shift (EmployeeID, StartTime, EndTime, WeekStartDate) VALUES (" + EmployeeID + ", " + StartTime + ", " + EndTime + ", " + WeekStartDate + ")";
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

    public boolean addAmenity(String name, int price) {
        String query = "INSERT INTO Amenity (Name, Price) VALUES (" + name + ", " + price + ")";
        //returns true if successfully added
        return false;
    }
    
    public boolean updateAmnity(int amenityID, String name, int price) {
        String setStatement = "SET ";
        if (name != null) {
            setStatement += "Name = " + name + ", ";
        }
        if (price != -1) {
            setStatement += "Price = " + price + ", ";
        }
        //returns true if successfully updated
        return false;
    }

    public boolean deleteAmenity(int amenityID) {
        String query = "DELETE FROM Amenity WHERE AmenityID = " + amenityID;
        //returns true if successfully deleted
        return false;
    }
}
