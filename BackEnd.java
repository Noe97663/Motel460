import java.sql.*;

public class BackEnd {
    Connection dbconn = null;
    Statement stmt = null;
    public static void main(String args[]){
        BackEnd be = new BackEnd();
        be.addGuest("Gavin", "Pogson", "1", null);
        be.addGuest("Noel", "Pogson", "1", null);
    }
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

    /*---------------------------------------------------------------------
    |  Method query1
    |
    |  Purpose:  This query returns the integer containing the total bill
    |            for a bookingID. This is the price of the room plus the
    |            costs of any unpaid amenities plus tip.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: The price is returned.
    |
    |  Parameters:
    |      bookingID -- the bookingID for the bill.
    |
    |  Returns:  int containing the total bill for a bookingID.
    *-------------------------------------------------------------------*/
    public double query1(int bookingID) {
        double sum = 0.0; // --- STARTING SUM
        try{
            // ----- GETTING AMENITY PRICES
            String allTransactionsQuery = "SELECT TransactionNo, Price, ExtraCharge, Tips from Transaction, Amenity where " +
                                        "bookingID = " + bookingID +" and "+
                                        "Transaction.AmenityID = Amenity.AmenityID";
            ResultSet TransactionAnswer = stmt.executeQuery(allTransactionsQuery);
            if (TransactionAnswer != null) {
                while (TransactionAnswer.next()) {
                    // ----- ADD EACH TRANSACTION TO THE SUM
                    sum += TransactionAnswer.getInt("Price");
                    sum += TransactionAnswer.getInt("ExtraCharge");
                    sum += TransactionAnswer.getInt("Tips");                
                }
            }
            // ----- GETTING ROOM TYPES 
            String RoomTypeQuery = "SELECT Type from room where roomID = (SELECT RoomID FROM Booking where bookingID = "+ bookingID +")";
            String roomType = "";
            ResultSet roomTypeAnswer = stmt.executeQuery(RoomTypeQuery);
            if (roomTypeAnswer != null) {
                while (roomTypeAnswer.next()) {
                    // ----- GET EACH TYPE
                    roomType = roomTypeAnswer.getString("Type");     
                    String roomPriceQuery = "Select Price from RoomClassification where type = '" + roomType + "'";
                    ResultSet roomPriceAnswer = stmt.executeQuery(roomPriceQuery);
                    // ----- ADD EACH (ROOM PRICE X NUM DAYS)
                    if (roomPriceAnswer != null) {
                        while (roomPriceAnswer.next()) {
                            sum += roomPriceAnswer.getInt("Price"); // * numDays          
                        }
                    }
                }
            }
           
        }
        catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not calculate sum.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            }

        return sum;
    
    }

    public ResultSet query2(String date) {
        String query = "select guest.firstname, guest.lastname, guest.studentstatus, booking.roomid" 
                    +  "from huyle.booking, huyle.guest " 
                    +  "where enddate > to_date('" + date + "','YYYY-MM-DD') and startdate < to_date('" + date + "','YYYY-MM-DD'') and booking.guestid = guest.guestid"
                    +  "order by booking.roomid"; // sample, not final missing group by studentstatus
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
        //auto increment implementation
        String prequery = "SELECT MAX(GUESTID) FROM GUEST";
        int guestID = 0;
        try{
            ResultSet answer = stmt.executeQuery(prequery);
            while (answer.next()) {
                guestID = answer.getInt("MAX(GUESTID)");
            }
        }
        catch (SQLException e) {
        System.err.println("*** SQLException:  "
                + "Could not calculate guestID.");
        System.err.println("\tMessage:   " + e.getMessage());
        System.err.println("\tSQLState:  " + e.getSQLState());
        System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        guestID+=1;
        
        //put single quote around String
        String query = "INSERT INTO HUYLE.Guest (GuestID,FirstName, LastName, StudentStatus, CreditCardCompany) VALUES ("+guestID+","
            + "'" + firstName + "'" + ", " + "'" + lastName + "'" + ", " + isStudent + ", " + "'" + creditCardCompany + "'" + ")";
        //returns true if successfully added
        try {
            stmt.execute(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not add Rating.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
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

        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not update Guest.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean deleteGuest(int guestID) {
        String query = "DELETE FROM Guest WHERE GuestID = " + guestID;
        //returns true if successfully deleted

        // need to remove the guest info from all related tables
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not delete Guest.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean addRating(int guestID, int amenityID, int rating, String date) {
        //date is in YYYY-MM-DD format
        //convert to oracle date format
        String query = "INSERT INTO Rating (GuestID, AmenityID, Rating, RatingDate) VALUES (" 
            + guestID + ", " + amenityID + ", " + rating + ", " + "TO_DATE('" + date + "', 'YYYY-MM-DD'))";
        //returns true if successfully added
        System.out.println(query);
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not add Rating.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean updateRating(int guestID, int amenityID, int rating, String date) {
        //date is in YYYY-MM-DD format
        //convert to oracle date format
        //update only rating
        String query = "UPDATE Rating SET Rating = " + rating 
            + " WHERE GuestID = " + guestID + " AND AmenityID = " 
            + amenityID + " AND RatingDate = TO_DATE(" + date + ", 'YYYY-MM-DD')";
        //returns true if successfully updated
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not update Rating.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean deleteRating(int guestID, int amenityID, String date) {
        String query = "DELETE FROM Rating WHERE GuestID = " + guestID + " AND AmenityID = " 
            + amenityID + " AND RatingDate = TO_DATE(" + date + ", 'YYYY-MM-DD')";
        //returns true if successfully deleted
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not delete Rating.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean addClubMember(int guestID, int points) {
        //insert into ClubMember table, guestID, and point = 0
        String query = "INSERT INTO ClubMember (GuestID, Points) VALUES (" + guestID + ", " + points + ")";
        //returns true if successfully added
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not add ClubMember.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean updateClubMember(int guestID, int points) {
        String query = "UPDATE ClubMember SET Points = " + points + " WHERE GuestID = " + guestID;
        //returns true if successfully updated
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not update ClubMember.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean removeClubMember(int guestID) {
        String query = "DELETE FROM ClubMember WHERE GuestID = " + guestID;
        //returns true if successfully removed
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not remove ClubMember.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean addBooking(int guestID, String startDate, String endDate, int roomID) {
        //auto increment implementation
        String prequery = "SELECT MAX(bookingID) FROM BOOKING";
        int bookingID = 0;
        try{
            ResultSet answer = stmt.executeQuery(prequery);
            while (answer.next()) {
                bookingID = answer.getInt("MAX(bookingID)");
            }
        }
        catch (SQLException e) {
        System.err.println("*** SQLException:  "
                + "Could not calculate bookingID.");
        System.err.println("\tMessage:   " + e.getMessage());
        System.err.println("\tSQLState:  " + e.getSQLState());
        System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        bookingID+=1;
        String query = "Insert into Booking (BookingID, GuestID, StartDate, EndDate, RoomID) VALUES (" + bookingID + ", " 
            + guestID + ", TO_DATE('" + startDate + "', 'YYYY-MM-DD'), TO_DATE('" + endDate + "', 'YYYY-MM-DD'), " + roomID + ")";
        System.out.println(query);
        //returns true if successfully added
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not add Booking.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean updateBooking(int bookingID, String startDate, String endDate, int roomID) {
        //if roomID is -1, then don't update roomID
        String setStatement = "SET ";
        if (startDate != null) {
            setStatement += "StartDate = TO_DATE('" + startDate + "'', 'YYYY-MM-DD'), ";
        }
        if (endDate != null) {
            setStatement += "EndDate = TO_DATE('" + endDate + "'', 'YYYY-MM-DD'), ";
        }
        if (roomID != -1) {
            setStatement += "RoomID = " + roomID + ", ";
        }
        String query = "UPDATE Booking " + setStatement.substring(0, setStatement.length() - 2) + " WHERE BookingID = " + bookingID;
        //returns true if successfully updated
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not update Booking.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean removeBooking(int bookingID) {
        String query = "DELETE FROM Booking WHERE BookingID = " + bookingID;
        //returns true if successfully removed
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not remove Booking.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean addTransaction(int bookingID, int amenityID, int extraCharge, int tip) {

        //auto increment implementation
        String prequery = "SELECT MAX(transactionNO) FROM TRANSACTION";
        int transactionNO = 0;
        try{
            ResultSet answer = stmt.executeQuery(prequery);
            while (answer.next()) {
                transactionNO = answer.getInt("MAX(transactionNO)");
            }
        }
        catch (SQLException e) {
        System.err.println("*** SQLException:  "
                + "Could not calculate transactionNO.");
        System.err.println("\tMessage:   " + e.getMessage());
        System.err.println("\tSQLState:  " + e.getSQLState());
        System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        transactionNO+=1;

        String query = "INSERT INTO Transaction (transactionNO, BookingID, AmenityID, ExtraCharge, Tips) VALUES (" 
            + transactionNO + "," + bookingID + ", " + amenityID + ", " + extraCharge + ", " + tip + ")";
        //returns true if successfully added
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not add Transaction.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean updateTransaction(int bookingID, int transactionID, int amenityID, int extraCharge, int tip) {
        //primary key is bookingID and transactionID
        String setStatement = "SET ";
        if (amenityID != -1) {
            setStatement += "AmenityID = " + amenityID + ", ";
        }
        if (extraCharge != -1) {
            setStatement += "ExtraCharge = " + extraCharge + ", ";
        }
        if (tip != -1) {
            setStatement += "Tip = " + tip + ", ";
        }
        String query = "UPDATE Transaction " + setStatement.substring(0, setStatement.length() - 2) + " WHERE BookingID = " + bookingID + " AND TransactionID = " + transactionID;
        //returns true if successfully updated
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not update Transaction.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean addRoom(int roomID, String type) {
        String query = "INSERT INTO Room (RoomID, Type) VALUES (" + roomID + ", '" + type + "')";
        //returns true if successfully added
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not add Room.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean updateRoom(int roomID, String type) {
        //primary key is roomID
        String setStatement = "SET ";
        if (type != null) {
            setStatement += "Type = '" + type + "', ";
        }
        String query = "UPDATE Room " + setStatement.substring(0, setStatement.length() - 2) + " WHERE RoomID = " + roomID;
        //returns true if successfully updated
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not update Room.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean deleteRoom(int roomID) {
        String query = "DELETE FROM Room WHERE RoomID = " + roomID;
        //returns true if successfully deleted
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not delete Room.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean addEmployee(String firstName, String lastName, String position) {

        //auto increment implementation
        String prequery = "SELECT MAX(employeeID) FROM EMPLOYEE";
        int employeeID = 0;
        try{
            ResultSet answer = stmt.executeQuery(prequery);
            while (answer.next()) {
                employeeID = answer.getInt("MAX(employeeID)");
            }
        }
        catch (SQLException e) {
        System.err.println("*** SQLException:  "
                + "Could not calculate employeeID.");
        System.err.println("\tMessage:   " + e.getMessage());
        System.err.println("\tSQLState:  " + e.getSQLState());
        System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        employeeID+=1;

        String query = "INSERT INTO Employee (EmployeeID, FirstName, LastName, Position) VALUES (" + employeeID + ",'" + firstName + "', '" + lastName + "', '" + position + "')";
        //returns true if successfully added
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not add Employee.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
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
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not update Employee.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean deleteEmployee(int employeeID) {
        String query = "DELETE FROM Employee WHERE EmployeeID = " + employeeID;
        //returns true if successfully deleted
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not delete Employee.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean addShift(String EmployeeID, String StartTime, String EndTime, String WeekStartDate) {
        //returns true if successfully added
        WeekStartDate += "TO_DATE(" + WeekStartDate + ", 'YYYY-MM-DD')";
        String query = "INSERT INTO Shift (EmployeeID, StartTime, EndTime, WeekStartDate) VALUES (" + EmployeeID + ", " + StartTime + ", " + EndTime + ", " + WeekStartDate + ")";
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not add Shift.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean updateShift(String EmployeeID, String StartTime, String EndTime, String WeekStartDate) {
        //returns true if successfully updated
        String setStatement = "SET ";
        if (StartTime != null) {
            setStatement += "StartTime = " + StartTime + ", ";
        }
        if (EndTime != null) {
            setStatement += "EndTime = " + EndTime + ", ";
        }
        String query = "UPDATE Shift " + setStatement.substring(0, setStatement.length() - 2) + " WHERE EmployeeID = " + EmployeeID + "AND WeekStartDate = " + WeekStartDate;
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not update Shift.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean deleteShift(String EmployeeID, String WeekStartDate) {
        //returns true if successfully deleted
        String query = "DELETE FROM Shift WHERE EmployeeID = " + EmployeeID + "AND WeekStartDate = " + WeekStartDate;
        //returns true if successfully deleted
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not delete Shift.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean addAmenity(String name, int price) {
        
         //auto increment implementation
         String prequery = "SELECT MAX(AMENITYID) FROM AMENITY";
         int amenityID = 0;
         try{
             ResultSet answer = stmt.executeQuery(prequery);
             while (answer.next()) {
                amenityID = answer.getInt("MAX(AMENITYID)");
             }
         }
         catch (SQLException e) {
         System.err.println("*** SQLException:  "
                 + "Could not calculate amenityID.");
         System.err.println("\tMessage:   " + e.getMessage());
         System.err.println("\tSQLState:  " + e.getSQLState());
         System.err.println("\tErrorCode: " + e.getErrorCode());
         }
         amenityID+=1;
        //returns true if successfully added
        String query = "INSERT INTO HUYLE.AMENITY (AMENITYID, NAME, PRICE) VALUES ("+ amenityID + ", '" + name + "', " + price + ")";
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not add Amenity.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
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
        String query = "UPDATE Amenity " + setStatement.substring(0, setStatement.length() - 2) + " WHERE AmenityID = " + amenityID;
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not update Amenity.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean deleteAmenity(int amenityID) {
        String query = "DELETE FROM Amenity WHERE AmenityID = " + amenityID;
        //returns true if successfully deleted
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not delete Amenity.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean addRoomClassification(String Type, String Price, String Beds, String Baths) {
        //returns true if successfully added
        
        String query = "INSERT INTO RoomClassification (Type, Price, Beds, Baths) VALUES ('" + Type + "', " + Price + ", " + Beds + ", " + Baths + ")";
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not add RoomClassification.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean updateRoomClassification(String Type, String Price, String Beds, String Baths) {
        //returns true if successfully updated
        String setStatement = "SET ";
        if (Price != null) {
            setStatement += "Price = " + Price + ", ";
        }
        if (Beds != null) {
            setStatement += "Beds = " + Beds + ", ";
        }
        if (Baths != null) {
            setStatement += "Baths = " + Baths + ", ";
        }
        String query = "UPDATE RoomClassification " + setStatement.substring(0, setStatement.length() - 2) + " WHERE Type = '" + Type+"'";
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not update RoomClassification.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }

    public boolean deleteRoomClassification(String Type, String Price, String Beds, String Baths) {
        //returns true if successfully deleted
        String query = "DELETE FROM RoomClassification WHERE Type = '" + Type+"'";
        //returns true if successfully deleted
        try {
            stmt.executeUpdate(query);
            return true;
            
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not delete RoomClassification.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        return false;
    }
}
