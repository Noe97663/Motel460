import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BackEnd {
    Connection dbconn = null;
    Statement stmt = null;
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

    public void close() {
        try {
            stmt.close();
            dbconn.close();
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                + "Could not close JDBC connection.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
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
    |  Returns:  No return
    *-------------------------------------------------------------------*/
    public void query1(int bookingID) {
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
            ArrayList<String> roomType = new ArrayList<>();
            ResultSet roomTypeAnswer = stmt.executeQuery(RoomTypeQuery);
            if (roomTypeAnswer != null) {
                while (roomTypeAnswer.next()) {
                    // ----- GET EACH TYPE
                    roomType.add(roomTypeAnswer.getString("Type"));     
                }
            }
            // ---- GET DATES FOR THE STAY
            String roomDates = "Select TO_CHAR(StartDate, 'YYYY-MM-DD') as StartDate, TO_CHAR(EndDate, 'YYYY-MM-DD') as EndDate from Booking where BookingID = " + bookingID;
            ResultSet roomDatesAnswer = stmt.executeQuery(roomDates);
            String[] StartDate;
            String[] EndDate;
            int numDays = 0;
            if (roomDatesAnswer != null) {
                while (roomDatesAnswer.next()) {
                    StartDate = roomDatesAnswer.getString("StartDate").split("-");         
                    EndDate = roomDatesAnswer.getString("EndDate").split("-");         
                    // ---- GET NUM DAYS FOR THE STAY FROM THE DATES
                    if(Integer.parseInt(StartDate[1]) == Integer.parseInt(EndDate[1])){
                        // --- DATES IN THE SAME MONTH
                        numDays = Integer.parseInt(EndDate[2]) - Integer.parseInt(StartDate[2]);
                    }
                    else{
                        // --- DATES NOT IN SAME MONTH
                        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                        numDays += (Integer.parseInt(EndDate[2]) + 
                             months[Integer.parseInt(EndDate[1]) + 1]) - Integer.parseInt(StartDate[2]);
                    }
                }
            }
            else{
                System.out.println("ROOM DATES QUERY RETURNED EMPTY");
            }
            for (String i : roomType){
                String roomPriceQuery = "Select Price from RoomClassification where Type = '" + i + "'";
                ResultSet roomPriceAnswer = stmt.executeQuery(roomPriceQuery);

                // ----- ADD EACH (ROOM PRICE X NUM DAYS)
                if (roomPriceAnswer != null) {
                    while (roomPriceAnswer.next()) {
                        sum += roomPriceAnswer.getInt("Price") * numDays;   
                    }  
                }
            }

            // ------ ADD DISCOUNTS 
            double totalDiscount = 0.0;
            double discount1 = 0.0;
            double discount2 = 0.0;

            // ----- GET STUDENT DISCOUNT
            String StudentQuery = "SELECT StudentStatus from Guest where GuestID = (SELECT GuestID FROM Booking where bookingID = "+ bookingID +")";
            ResultSet StudentAnswer = stmt.executeQuery(StudentQuery);
            if (StudentAnswer != null) {
                while (StudentAnswer.next()) {
                    int StudentStatus = StudentAnswer.getInt("StudentStatus");
                    if (StudentStatus == 1){
                        discount1 = 10.0;
                    }
                }
            }
            // ----- GET CLUB MEMBER POINTS
            String GuestMemberQuery = "SELECT Points from ClubMember where GuestID = (SELECT GuestID FROM Booking where bookingID = "+ bookingID +")";
            ResultSet GuestMemberAnswer = stmt.executeQuery(GuestMemberQuery);
            if (GuestMemberAnswer != null) {
                while (GuestMemberAnswer.next()) {
                    discount2 +=  GuestMemberAnswer.getInt("Points") * .01;
                }
            }
            if(discount1 > discount2){
                totalDiscount = discount1;
            }
            else{
                totalDiscount = discount2;
            }
            sum *= (100 - totalDiscount) * .01;
        }
        catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not calculate sum.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            }

        System.out.println("YOUR TOTAL BILL IS: $" + sum);
    }

    public void query2(String date){
        try{
        String query = "select guest.guestid , firstname, lastname, studentstatus, points, roomid " 
                    +  "from huyle.guest join huyle.booking " 
                    +  "on enddate > to_date('" + date + "','YYYY-MM-DD') and startdate < to_date('" + date + "','YYYY-MM-DD') and booking.guestid = guest.guestid "
                    +  "left join huyle.clubmember on huyle.clubmember.guestid = huyle.guest.guestid "
                    +  "group by guest.guestid , firstname, lastname, studentstatus, points, roomid order by roomid";
        ResultSet ans = stmt.executeQuery(query);
        
        if (!ans.next()){
            System.out.println("No one is staying on the date: " + date);
        } else {
            System.out.println("\nThe results of the query 2 are:\n");
                // Get the data about the query result to learn
                // the attribute names and use them as column headers
            ResultSetMetaData answermetadata = ans.getMetaData();
            for (int i = 1; i <= answermetadata.getColumnCount(); i++) {
                System.out.print(answermetadata.getColumnName(i) + "   ");
            }
            System.out.println();
                // Use next() to advance cursor through the result
                        // tuples and print their attribute values
            while (ans.next()) {
                System.out.println(ans.getInt("guestID") + "    " + ans.getString("FirstName")
                    + "    " + ans.getString("LastName") + "    " + ans.getInt("Studentstatus") + "    " + ans.getInt("Points")
                    + "    " + ans.getInt("RoomID"));
            }
        }

        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not calculate sum.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
    }

    /*---------------------------------------------------------------------
    |  Method query3
    |
    |  Purpose:  Print the schedule of staff given a week (input the start date of the week by the user). A schedule
    |            contains the list of staff members working that week and a staff member’s working hours (start and stop
    |            times).
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: Schedule is printed.
    |
    |  Parameters:
    |      weekDate -- String containing the start date of the week in the format YYYY-MM-DD.
    |
    |  Returns:  None.
    *-------------------------------------------------------------------*/
    public void query3(String weekDate) {
        // Print the schedule of staff given a week (input the start date of the week by the user). A schedule
        //contains the list of staff members working that week and a staff member’s working hours (start and stop
        //times).

        //find all the employees working on the week starting on weekDate (full schedule)
        String query = "SELECT FirstName, LastName, starttime, endtime, weekstartdate FROM Shift JOIN "
            + "Employee ON Shift.EmployeeID = Employee.EmployeeID WHERE weekstartdate between to_date('" + weekDate + "','YYYY-MM-DD') - 6"
            + " and to_date('" + weekDate + "','YYYY-MM-DD') + 7";

        //find all the employees working on the week starting on weekDate, but only print their names once
        String query2 = "SELECT DISTINCT FirstName, LastName FROM Shift JOIN "
        + "Employee ON Shift.EmployeeID = Employee.EmployeeID WHERE weekstartdate between to_date('" + weekDate + "','YYYY-MM-DD') - 6"
        + " and to_date('" + weekDate + "','YYYY-MM-DD') + 7";

        try {
            ResultSet ans = stmt.executeQuery(query2);
            if (ans != null) {
                System.out.println("\nEmployees working on the week starting on " + weekDate + " :\n");
                while(ans.next()) {;
                    System.out.println(ans.getString("FirstName") + " " + ans.getString("LastName"));
                }
            }
            ResultSet ans2 = stmt.executeQuery(query);
            if (ans2 != null) {
                System.out.println("\nShift hours:\n");
                while (ans2.next()) {
                    String start = ans2.getString("starttime");
                    String end = ans2.getString("endtime");
                    //beautify the output eg. from 400 to 4:00
                    if (start.length() == 3) {
                        start = start.substring(0,1) + ":" + start.substring(1,3);
                    } else if (start.length() == 4) {
                        start = start.substring(0,2) + ":" + start.substring(2,4);
                    }
                    if (end.length() == 3) {
                        end = end.substring(0,1) + ":" + end.substring(1,3);
                    } else if (end.length() == 4) {
                        end = end.substring(0,2) + ":" + end.substring(2,4);
                    }
                    System.out.println(ans2.getString("FirstName") + " " + ans2.getString("LastName") + " " + start + "-" 
                        + end + " Week Shift Started On: " + ans2.getString("weekstartdate").substring(0,10));
                }
            }
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not calculate schedule.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        System.out.println();
    }

    public void query4(String dateStart, String dateEnd) {
        try{
            // ----- GETTING AMENITY PRICES 
            String query = "SELECT Name,AVG(Rating.Rating) from Amenity,Rating where " +
                                        "Amenity.AmenityID = Rating.AmenityID and "+
                                        "RatingDate between TO_DATE('"+dateStart
                                        +"','YYYY-MM-DD') and TO_DATE('"+dateEnd+
                                         "','YYYY-MM-DD') group by RATING.AmenityID,Name"+
                                         " order by AVG(Rating.rating) desc";
            
            ResultSet answer = stmt.executeQuery(query);
            System.out.println("\nAverage ratings of amenities from ratings between the"+
            "given dates\n");
            int count = 0;
            if (answer != null) {
                while (answer.next()) {
                    count++;
                    System.out.println(answer.getString("Name")+
                    " had an average rating of: "+
                     answer.getFloat("AVG(Rating.Rating)"));              
                }
            }
            if(count==0){
                System.out.println("No amenities were rated in the given time period.");
            }
        }
        catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not run query 4.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            }
        System.out.println("\n");
    }

    public void query5(String num) {
        int numConverted = 0;
        int count = 0;
        try{
            numConverted = Integer.parseInt(num);
        }
        catch (Exception e){
            System.out.println("Invalid input. Returning to main menu.\n");
        }
        try{
            // ----- GETTING AMENITY PRICES 
            String numQuery = "Select COUNT(*) from ClubMember";
            String query = "SELECT FirstName,LastName,Points from Guest,ClubMember "+
            "where Guest.GuestID=ClubMember.GuestID "+
            "order by points desc";
            
            ResultSet numAnswer= stmt.executeQuery(numQuery);
            numAnswer.next();
            int numRows = numAnswer.getInt("COUNT(*)");
            if(numRows<numConverted){
                num = Integer.toString(numRows);
            }
            ResultSet answer = stmt.executeQuery(query);
            System.out.println("\n");
            System.out.println("Here are the top "+num+" guests with the most club 460 points:");
            if (answer != null) {
                while (answer.next() && count<numConverted) {
                    System.out.println(answer.getString("FIRSTNAME")+ " " +
                    answer.getString("LASTNAME")+
                    " has "+ answer.getInt("Points")+
                    " Club460 points.");
                    count++;
                }
                
            }
        }
        catch (SQLException e) {
            System.err.println("*** SQLException:  "
                    + "Could not run query 5.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            }
        System.out.println("\n");
    }

    /*---------------------------------------------------------------------
    |  Method addGuest
    |
    |  Purpose: Adds a guest to the database
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: Guest is added to the database.
    |
    |  Parameters:
    |      firstName -- the first name of the guest.
    |      lastName -- the last name of the guest.
    |      isStudent-- the student status of the guest.
    |      creditCardCompany -- the credit card company of the guest.
    |
    |  Returns:  true if the guest was successfully added, false otherwise.
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method updateGuest
    |
    |  Purpose: Update a guest's information in the database. Null parameters are not updated.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: The guest's information has been updated in the database.
    |
    |  Parameters:
    |      guestID -- the guestID of the guest to be updated.
    |      firstName -- the new first name of the guest.
    |      lastName -- the new last name of the guest.
    |      isStudent-- the new student status of the guest.
    |      creditCardCompany -- the new credit card company of the guest.
    |
    |  Returns:  true if the guest was successfully updated, false otherwise.
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method deleteGuest
    |
    |  Purpose: Deletes a guest from the database.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: The guest is deleted from the database. Any tuple referencing
    |                  the guest is also deleted.
    |
    |  Parameters:
    |      guestID -- the ID of the guest to be deleted
    |
    |  Returns:  true if the guest was successfully deleted, false otherwise.
    *-------------------------------------------------------------------*/
    public boolean deleteGuest(int guestID) {
        String query2 = "DELETE FROM Clubmember WHERE GuestID = " + guestID;
        String query5 = "select bookingId from booking where GuestID = " + guestID;
        String query3 = "DELETE FROM Booking WHERE GuestID = " + guestID;
        String query4 = "DELETE FROM Rating WHERE GuestID = " + guestID;
        String query = "DELETE FROM Guest WHERE GuestID = " + guestID;
        //returns true if successfully deleted

        // need to remove the guest info from all related tables
        try {
            stmt.executeUpdate(query2);
            ResultSet ans = stmt.executeQuery(query5);
            ArrayList<Integer> res = new ArrayList<>();
            while (ans.next()){ // can't execute another query before a resultset is closed, so store it into arraylist
                int bookingId = ans.getInt("BookingID");
                res.add(bookingId);
            } 
            for (int i = 0; i < res.size(); i++){
                int b = res.get(i);
                String query7 = "DELETE FROM Transaction WHERE bookingID = " + b;
                stmt.executeUpdate(query7);  
            }
            stmt.executeUpdate(query3);
            stmt.executeUpdate(query4);
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

    /*---------------------------------------------------------------------
    |  Method addRating
    |
    |  Purpose: Adds a rating for a guest for a particular amenity on a particular date.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: The rating has been added to the database.
    |
    |  Parameters:
    |      guestID -- the guestID of the guest 
    |      amenityID -- the amenityID of the amenity
    |      rating -- the rating given by the guest
    |      date -- the date the rating was given
    |
    |  Returns:  true if the rating was successfully added, false otherwise.
    *-------------------------------------------------------------------*/
    public boolean addRating(int guestID, int amenityID, int rating, String date) {
        //date is in YYYY-MM-DD format
        //convert to oracle date format
        String query = "INSERT INTO Rating (GuestID, AmenityID, Rating, RatingDate) VALUES (" 
            + guestID + ", " + amenityID + ", " + rating + ", " + "TO_DATE('" + date + "', 'YYYY-MM-DD'))";
        //returns true if successfully added
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

    /*---------------------------------------------------------------------
    |  Method updateRating
    |
    |  Purpose: Updates the rating of a guest for a particular amenity on a particular date.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: Ratings of a guest for a particular amenity on a particular date are updated.
    |
    |  Parameters:
    |      guestID -- the guestID of the guest whose rating is being updated
    |      amenityID -- the amenityID of the amenity whose rating is being updated
    |      rating -- the new rating
    |
    |  Returns:  true if the rating was successfully updated, false otherwise.
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method deleteRating
    |
    |  Purpose: Deletes a rating from the database.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: The rating has been deleted from the database.
    |
    |  Parameters:
    |      guestID -- the guestID of the rating to be deleted
    |      amenityID -- the amenityID of the rating to be deleted
    |      date -- the date of the rating to be deleted (in YYYY-MM-DD format)
    |
    |  Returns:  true if the rating was successfully deleted, false otherwise.
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method addClubMember
    |
    |  Purpose: Adds a club member to the database.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: The club member has been added to the database.
    |
    |  Parameters:
    |      guestID -- the guestID of the club member
    |      points -- intial points of the club member
    |
    |  Returns:  true if the club member was successfully added, false otherwise.
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method updateClubMember
    |
    |  Purpose: Updates the points of a club member
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: The club member's points have been updated.
    |
    |  Parameters:
    |      guestID -- the guestID of the club member
    |      points -- Points added to the club member's account (can be negative)
    |
    |  Returns:  true if the club member was successfully updated, false otherwise.
    *-------------------------------------------------------------------*/
    public boolean updateClubMember(int guestID, int points) {
        String query = "UPDATE ClubMember SET Points = Points " + points + " WHERE GuestID = " + guestID;
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

    /*---------------------------------------------------------------------
    |  Method removeClubMember
    |
    |  Purpose: removes a club member from the database
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: club member is removed from the database
    |
    |  Parameters:
    |      guestID -- the guestID of the club member to be removed
    |
    |  Returns:  true if the club member was successfully removed, false otherwise.
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method addbooking
    |
    |  Purpose: Adds a booking to the database.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: Booking is added to the database.
    |
    |  Parameters:
    |      guestID -- the guestID of the person booking
    |      startDate -- the start date of the booking
    |      endDate -- the end date of the booking
    |      roomID -- the roomID of the booking
    |
    |  Returns:  true if the booking was successfully added, false otherwise.
    *-------------------------------------------------------------------*/
    public boolean addBooking(int guestID, String startDate, String endDate, int roomID) {
        //auto increment implementation
        String prequery = "SELECT MAX(bookingID) FROM BOOKING";
        int bookingID = 0;

        String queryCheck1 = "SELECT * FROM booking WHERE roomID =" + roomID + "and startdate between to_date('" + startDate + "','YYYY-MM-DD')"
        + " and to_date('" + endDate + "','YYYY-MM-DD') and enddate between to_date('" + startDate + "','YYYY-MM-DD') and to_date('" + endDate + "','YYYY-MM-DD')";

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
        //returns true if successfully added
        String queryAddPoints = "UPDATE ClubMember SET Points = Points + 10 WHERE GuestID = " + guestID;
        try {
            stmt.executeQuery(queryCheck1);
            ResultSet answer = stmt.executeQuery(queryCheck1);
            if (answer.next()) {
                System.out.println("Room is already booked for that time period");
                return false;
            }
            stmt.executeUpdate(query);
            stmt.executeUpdate(queryAddPoints);
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

    /*---------------------------------------------------------------------
    |  Method UpdateBooking
    |
    |  Purpose: Updates a booking information in the database.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: Booking information has been updated in the database.
    |
    |  Parameters:
    |      bookingID -- the bookingID of the booking to be updated
    |      startDate -- the new start date of the booking
    |      endDate -- the new end date of the booking
    |      roomID -- the new roomID of the booking
    |
    |  Returns:  true if the booking was successfully updated, false otherwise.
    *-------------------------------------------------------------------*/
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

        //locate the tuple to be updated
        String queryCheck1 = "SELECT * From Booking WHERE BookingID = " + bookingID;

        try {
            System.out.println(queryCheck1);
            ResultSet answer = stmt.executeQuery(queryCheck1);
            //look for the tuple to be updated, and update the fields
            if (!answer.next()) {
                System.out.println("Booking does not exist");
                return false;
            }
            else {
                if (roomID == -1) {
                    roomID = answer.getInt("RoomID");
                }
                if (startDate == null) {
                    startDate = answer.getDate("StartDate").toString();
                }
                if (endDate == null) {
                    endDate = answer.getDate("EndDate").toString();
                }
            }
            //check if the room is already booked for that time period, ignore the current booking
            String queryCheck2 = "SELECT * FROM booking WHERE roomID = " + roomID + " and startdate between to_date('" + startDate + "','YYYY-MM-DD')"
                + " and to_date('" + endDate + "','YYYY-MM-DD') and enddate between to_date('" + startDate + "','YYYY-MM-DD') and to_date('" + endDate + "','YYYY-MM-DD')"
                + " MINUS SELECT * FROM booking WHERE BookingID = " + bookingID;
            System.out.println(queryCheck2);
            answer = stmt.executeQuery(queryCheck2);
            if (answer.next()) {
                System.out.println("Room is already booked for that time period");
                return false;
            }

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

    /*---------------------------------------------------------------------
    |  Method removeBooking
    |
    |  Purpose: Removes a booking from the database.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: The booking has been removed from the database.
    |
    |  Parameters:
    |      bookingID -- the bookingID of the booking to be removed.
    |
    |  Returns:  true if the booking was successfully removed, false otherwise.
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method addTransaction
    |
    |  Purpose: Adds a transaction to the database.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: Transaction is added to the database.
    |
    |  Parameters:
    |      bookingID -- the bookingID of the transaction.
    |      amenityID -- the amenityID of the transaction.
    |      extraCharge -- the extraCharge of the transaction.
    |      tip -- the tip of the transaction.
    |
    |  Returns:  true if the transaction was successfully added, false otherwise.
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method updateTransaction
    |
    |  Purpose: Updates a transaction in the database. Arguments that are null are not updated.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: Transaction is updated in the database.
    |
    |  Parameters:
    |      bookingID -- the bookingID of the transaction to be updated.
    |      transactionID -- the transactionID of the transaction to be updated.
    |      amenityID -- the new amenityID of the transaction.
    |      extraCharge -- the new extraCharge of the transaction.
    |      tip -- the new tip of the transaction.
    |
    |  Returns:  true if the transaction was successfully updated, false otherwise.
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method AddRoom
    |
    |  Purpose: Adds a room to the database.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: Room has been added to the database.
    |
    |  Parameters:
    |      roomID -- the roomID of the room to be added.
    |      type -- the type of the room to be added.
    |
    |  Returns:  true if the room was successfully added, false otherwise.
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method updateRoom
    |
    |  Purpose: Updates a room in the database. 
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: Room is updated in the database.
    |
    |  Parameters:
    |      roomID -- the roomID of the room to be updated.
    |      type -- the type of the room to be updated.
    |
    |  Returns:  true if the room was successfully updated, false otherwise.
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method deleteRoom
    |
    |  Purpose: Deletes a room from the database.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: Room is deleted from the database.
    |
    |  Parameters:
    |      roomID -- the roomID of the room to be deleted.
    |
    |  Returns:  true if the room was successfully deleted, false otherwise.
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method addEmployee
    |
    |  Purpose: Adds an employee to the database.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: Employee has been added to the database.
    |
    |  Parameters:
    |      firstName -- the first name of the employee
    |      lastName -- the last name of the employee
    |      position -- the position of the employee
    |
    |  Returns:  true if the employee was successfully added, false otherwise.
    *-------------------------------------------------------------------*/
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

        String query = "INSERT INTO Employee (EmployeeID, FirstName, LastName, Position) VALUES (" + employeeID + ",'" 
            + firstName + "', '" + lastName + "', '" + position + "')";
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

    /*---------------------------------------------------------------------
    |  Method UpdateEmployee
    |
    |  Purpose: Updates an employee's information in the database.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: Employee's information has been updated in the database.
    |
    |  Parameters:
    |      employeeID -- the employee's ID (primary key)
    |      position -- the employee's position
    |      FirstName -- the employee's first name
    |      LastName -- the employee's last name
    |
    |  Returns:  true if the employee was successfully updated, false otherwise.
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method deleteEmployee
    |
    |  Purpose: Deletes an employee from the database.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: Employee is deleted from the database.
    |
    |  Parameters:
    |      employeeID -- the employeeID of the employee to be deleted.
    |
    |  Returns:  true if the employee was successfully deleted, false otherwise.
    *-------------------------------------------------------------------*/
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
    /*---------------------------------------------------------------------
    |  Method addShift
    |
    |  Purpose: Add a shift into the database.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: The shift has been added in the database.
    |
    |  Parameters:
    |      EmployeeID -- the employeeID of the shift to be updated. (primary key)
    |      StartTime -- the new start time of the shift.
    |      EndTime -- the new end time of the shift.
    |      WeekStartDate -- the week start date of the shift (primary key)
    |
    |  Returns:  true if the shift was successfully added, false otherwise.
    *-------------------------------------------------------------------*/
    public boolean addShift(String EmployeeID, String StartTime, String EndTime, String WeekStartDate) {
        //returns true if successfully added
        WeekStartDate = "TO_DATE('" + WeekStartDate + "', 'YYYY-MM-DD')";
        String query = "INSERT INTO Shift (EmployeeID, StartTime, EndTime, WeekStartDate) VALUES (" + EmployeeID + ", " + StartTime + ", " 
            + EndTime + ", " + WeekStartDate + ")";

        String prequery = "SELECT * FROM Shift WHERE employeeID =" + EmployeeID + "and weekstartdate between " + WeekStartDate + " - 6"
        + " and " + WeekStartDate + " + 7 and ((starttime between " + StartTime + " and " + EndTime + ") or (endtime between "
             + StartTime + " and " + EndTime + "))";
        try {
            stmt.executeQuery(prequery);
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                System.out.println("Shift overlaps with another shift for this employee.");
                return false;
            }
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

    /*---------------------------------------------------------------------
    |  Method updateShift
    |
    |  Purpose: Updates a shift in the database. If a parameter is null, it will not be updated.
    |           Cannot update the employeeID or weekStartDate of a shift.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: The shift has been updated in the database.
    |
    |  Parameters:
    |      EmployeeID -- the employeeID of the shift to be updated. (primary key)
    |      StartTime -- the new start time of the shift.
    |      EndTime -- the new end time of the shift.
    |      WeekStartDate -- the week start date of the shift (primary key)
    |
    |  Returns:  true if the shift was successfully updated, false otherwise.
    *-------------------------------------------------------------------*/
    public boolean updateShift(String EmployeeID, String StartTime, String EndTime, String WeekStartDate) {
        //returns true if successfully updated
        String setStatement = "SET ";
        if (StartTime != null) {
            setStatement += "StartTime = " + StartTime + ", ";
        }
        if (EndTime != null) {
            setStatement += "EndTime = " + EndTime + ", ";
        }
        //look for shift with same employeeID and weekstartdate
        String queryCheck = "SELECT * FROM Shift WHERE employeeID =" + EmployeeID + " and weekstartdate = to_date('" + WeekStartDate + "','YYYY-MM-DD')";

        String query = "UPDATE Shift " + setStatement.substring(0, setStatement.length() - 2) 
            + " WHERE EmployeeID = " + EmployeeID + "AND WeekStartDate = to_date('" + WeekStartDate + "','YYYY-MM-DD')";
        try {
            System.out.println(queryCheck);
            stmt.executeQuery(queryCheck);
            ResultSet rs = stmt.getResultSet();
            if (!rs.next()) {
                System.out.println("Shift does not exist.");
                return false;
            }
            else {
                if (StartTime == null) {
                    StartTime = rs.getString("StartTime");
                }
                if (EndTime == null) {
                    EndTime = rs.getString("EndTime");
                }
                //we need to check to see if there is any overlap with another shift (minus the shift we are updating)
                String queryCheck2 = "SELECT * FROM Shift WHERE employeeID =" + EmployeeID + "and weekstartdate between to_date('" 
                    + WeekStartDate + "','YYYY-MM-DD') - 6"
                    + " and to_date('" + WeekStartDate + "','YYYY-MM-DD') + 7 and ((starttime > " + StartTime + " and starttime <" + EndTime + ") or (endtime > " 
                    + StartTime + " and endtime <" + EndTime + ")) MINUS SELECT * FROM Shift WHERE employeeID =" + EmployeeID + " and weekstartdate = to_date('"
                    + WeekStartDate + "','YYYY-MM-DD') and starttime = " + StartTime + " and endtime = " + EndTime;
                stmt.executeQuery(queryCheck2);
                ResultSet rs2 = stmt.getResultSet();
                if (rs2.next()) {
                    System.out.println("Shift overlaps with another shift for this employee. Update failed");
                    return false;
                }
            }
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

    /*---------------------------------------------------------------------
    |  Method deleteShift
    |
    |  Purpose: Deletes a shift from the database.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: The shift has been deleted from the database.
    |
    |  Parameters:
    |      EmployeeID -- the employeeID of the employee whose shift is being deleted
    |      WeekStartDate -- the week start date of the shift being deleted
    |
    |  Returns:  true if the shift was successfully deleted, false otherwise.
    *-------------------------------------------------------------------*/
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


    /*---------------------------------------------------------------------
    |  Method addAmnity
    |
    |  Purpose: add an amenity in the database
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: Amenity has been added to the database.
    |
    |  Parameters:
    |      name-- the name of the amenity to be added
    |      price-- the price of the amenity to be added
    |
    |  Returns:  true if the amenity was successfully added, false otherwise.
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method updateAmnity
    |
    |  Purpose: Updates an amenity in the database. If a parameter is null, it will not be updated.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: Amenity has been updated in the database.
    |
    |  Parameters:
    |      amenityID -- the amenityID of the amenity to be updated
    |      name -- the new name of the amenity to be updated
    |      price -- the new price of the amenity to be updated
    |
    |  Returns:  true if the amenity was successfully updated, false otherwise.
    *-------------------------------------------------------------------*/
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
    /*---------------------------------------------------------------------
    |  Method deleteAmenity
    |
    |  Purpose:  Delete the amenity with the given primary key from the database.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: The amenity with the given primary key has been deleted
    |
    |  Parameters:
    |      amenityID -- the primary key of the amenity to be deleted
    |
    |  Returns:  true if the amenity was successfully deleted, false otherwise.
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method addRoomClassification
    |
    |  Purpose:  Add the room classification with the given arguments.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: The room classification with the given primary key has been added.
    |
    |  Parameters:
    |      Type -- the primary key of the new room classification.
    |      Price -- the price of the room classification.
    |      Beds -- the number of beds of the room classification.
    |      Baths -- the number of baths of the room classification.
    |
    |  Returns:  true if the room was successfully added, false otherwise.
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method updateRoomClassification
    |
    |  Purpose:  Update the room classification with the given primary key from the database.
    |            If a parameter is null, it will not be updated. Type cannot be null.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: The room classification with the given primary key has been updated.
    |
    |  Parameters:
    |      Type -- the primary key of the room classification to be updated.
    |      Price -- the new price of the room classification.
    |      Beds -- the new number of beds of the room classification.
    |      Baths -- the new number of baths of the room classification.
    |
    |  Returns:  true if the room was successfully updated, false otherwise.
    *-------------------------------------------------------------------*/
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
}