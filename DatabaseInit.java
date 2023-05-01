import java.sql.*;
// To compile and execute this program on lectura:
// -Add the Oracle JDBC driver to your CLASSPATH environment variable:
//     export CLASSPATH=/usr/lib/oracle/19.8/client64/lib/ojdbc8.jar:${CLASSPATH}
//  -Compile java files
//  -Run file
public class DatabaseInit {
    /*---------------------------------------------------------------------
    |  Method: establishConnection(String cmdLineArgs[])
    |
    |  Purpose:        Uses a username and password (from the command line
    |                   arguments, if provided) to establish a connection to the
    |                   oracle datatbase.
    |
    |
    |  Pre-condition:  The username and password given as command line arguments 
    |                   are valid. The JDBC driver has been added to the classpath.
    |                   cmdLineArgs are of size 2, if custom login credentials are
    |                   preferred.
    |                  
    |
    |  Post-condition: A connection to the database has been established.
    |
    |  Parameters:
    |      cmdLineArgs - an array of Strings representing the command line 
    |                     arguments used to run Prog3. Contains login credentials
    |
    |  Returns:        A Connection object that enables use of the database
    *-------------------------------------------------------------------*/
    public static Connection establishConnection(String cmdLineArgs[]){
        final String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
 
        String username = "huyle";    // Oracle DBMS username
        String password = "a6398";    // Oracle DBMS password

        // get username/password from cmd line args
        if (cmdLineArgs.length == 2) {
            username = cmdLineArgs[0];
            password = cmdLineArgs[1];
        }
        System.out.print("Using username-password combo of: ");
        System.out.print(username+"-"+password);
        System.out.println(" to access oracle database.\n");
 
 
        // load the (Oracle) JDBC driver by initializing its base
        // class, 'oracle.jdbc.OracleDriver'.
 
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } 
        catch (ClassNotFoundException e) {
            System.err.println("*** ClassNotFoundException:  "
                + "Error loading Oracle JDBC driver.  \n"
                + "\tPerhaps the driver is not on the Classpath?");
            System.exit(-1);
        }

        // make and return a database connection to the user's
        // Oracle database
 
        Connection dbConn = null;
        try {
            dbConn = DriverManager.getConnection(oracleURL,username,password);
            }
        catch (SQLException e) {
            System.err.println("*** SQLException:  "
                + "Could not open JDBC connection.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
        return dbConn;
    }  // establihsConnection()

    /*---------------------------------------------------------------------
    |  Method: dropTables(Connection dbConn)
    |
    |  Purpose:        Drops the tables that may have been created previously
    |                   when testing the program.
    |
    |  Pre-condition:  A valid connection has been established to the oracle 
    |                  database through the dbConn object.
    |
    |  Post-condition: The required tables have been dropped from the database/
    |
    |  Parameters:
    |      dbConn - A Connection object used to drop tables in the database.
    |
    |  Returns:   None. Drops relevant tables in the oracle database.
    *-------------------------------------------------------------------*/
    public static void dropTables(Connection dbConn){
        Statement stmt = null;
        ResultSet answer = null;


        //dropping Rating table
        try {
            String query = "drop table HUYLE.RATING";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nDropped table HUYLE.RATING.\n");
            }
        } 
        //dropping table failed.
        catch (SQLException e) {
            System.err.println("Table HUYLE.RATING cannot be dropped"
            + " as it does not exist.");
        }

        //dropping ClubMember table
        try {
            String query = "drop table HUYLE.CLUBMEMBER";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nDropped table HUYLE.CLUBMEMBER.\n");
            }
        } 
        //dropping table failed.
        catch (SQLException e) {
            System.err.println("Table HUYLE.CLUBMEMBER cannot be dropped"
            + " as it does not exist.");
        }

        

        //dropping Transaction table
        try {
            String query = "drop table HUYLE.TRANSACTION";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nDropped table HUYLE.TRANSACTION.\n");
            }
        } 
        //dropping table failed.
        catch (SQLException e) {
            System.err.println("Table HUYLE.TRANSACTION cannot be dropped"
            + " as it does not exist.");
        }

        


        //dropping RoomClassification table
        try {
            String query = "drop table HUYLE.ROOMCLASSIFICATION";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nDropped table HUYLE.ROOMCLASSIFICATION.\n");
            }
        } 
        //dropping table failed.
        catch (SQLException e) {
            System.err.println("Table HUYLE.ROOMCLASSIFICATION cannot be dropped"
            + " as it does not exist.");
        }

        //dropping Shift table
        try {
            String query = "drop table HUYLE.SHIFT";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nDropped table HUYLE.SHIFT.\n");
            }
        } 
        //dropping table failed.
        catch (SQLException e) {
            System.err.println("Table HUYLE.SHIFT cannot be dropped"
            + " as it does not exist.");
        }

        //dropping Employee table
        try {
            String query = "drop table HUYLE.EMPLOYEE";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nDropped table HUYLE.EMPLOYEE.\n");
            }
        } 
        //dropping table failed.
        catch (SQLException e) {
            System.err.println("Table HUYLE.EMPLOYEE cannot be dropped"
            + " as it does not exist.");
        }


        //dropping Amenity table
        try {
            String query = "drop table HUYLE.AMENITY";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nDropped table HUYLE.AMENITY.\n");
            }
        } 
        //dropping table failed.
        catch (SQLException e) {
            System.err.println("Table HUYLE.AMENITY cannot be dropped"
            + " as it does not exist.");
        }

        //dropping Booking table
        try {
            String query = "drop table HUYLE.BOOKING";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nDropped table HUYLE.BOOKING.\n");
            }
        } 
        //dropping table failed.
        catch (SQLException e) {
            System.err.println("Table HUYLE.BOOKING cannot be dropped"
            + " as it does not exist.");
        }

        //dropping Room table
        try {
            String query = "drop table HUYLE.ROOM";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nDropped table HUYLE.ROOM.\n");
            }
        } 
        //dropping table failed.
        catch (SQLException e) {
            System.err.println("Table HUYLE.ROOM cannot be dropped"
            + " as it does not exist.");
        }

        //dropping Guest table
        try {
            String query = "drop table HUYLE.GUEST";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nDropped table HUYLE.GUEST.\n");
            }
            stmt.close();
        } 
        //dropping table failed.
        catch (SQLException e) {
            System.err.println("Table HUYLE.GUEST cannot be dropped"
            + " as it does not exist.");
        }

        

        //closing Statement object
        try{
            stmt.close();
        }
        catch(SQLException e){
            System.out.println("Error while closing Statement.");
        }
        
    }  // dropTables()

    /*---------------------------------------------------------------------
    |  Method: createTables(Connection dbConn)
    |
    |  Purpose:        Creates the guest table.
    |
    |  Pre-condition:  A valid connection has been established to the oracle 
    |                  database through the dbConn object.
    |
    |  Post-condition: The required tables have been created in the database.
    |
    |  Parameters:
    |      dbConn - A Connection object used to drop tables in the database.
    |
    |  Returns:   None. Creates relevant tables in the oracle database.
    *-------------------------------------------------------------------*/
    public static void createTables(Connection dbConn){
        Statement stmt = null;
        ResultSet answer = null;
        String tableName = "HUYLE.GUEST";
        
        try {
            //creating table guest
            String query = "create table "+tableName +
            " (GUESTID INT NOT NULL,"+ " STUDENTSTATUS VARCHAR(1) NOT NULL,"+
            " CREDITCARDCOMPANY VARCHAR(10),"+ " FIRSTNAME VARCHAR(10) NOT NULL,"+
            " LASTNAME VARCHAR(10) NOT NULL,"+" PRIMARY KEY (GUESTID))";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nCreated the table "+tableName+".\n");
            }

            //granting select permissions to all users
            query = "grant select on "+tableName+" to public";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);

            //creating table amenity
            tableName = "HUYLE.AMENITY";
            query = "create table "+ tableName +
            " (AMENITYID INT NOT NULL,"+ " NAME VARCHAR(10) NOT NULL,"+
            "PRICE INT,"+" PRIMARY KEY (AMENITYID))";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nCreated the table "+tableName+".\n");
            }

            //granting select permissions to all users
            query = "grant select on "+tableName+" to public";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);

            //creating table rating
            tableName = "HUYLE.RATING";
            query = "create table "+ tableName +
            " (GUESTID INT,"+ " AMENITYID INT,"+
            "RATINGDATE DATE,"+ " RATING INT NOT NULL,"+
            " PRIMARY KEY (GUESTID,AMENITYID,RATINGDATE),"+
            " FOREIGN KEY (GUESTID) REFERENCES HUYLE.GUEST(GUESTID),"+
            "FOREIGN KEY (AMENITYID) REFERENCES HUYLE.AMENITY(AMENITYID))";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nCreated the table "+tableName+".\n");
            }

            //granting select permissions to all users
            query = "grant select on "+tableName+" to public";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);

            //creating table CLUBMEMBER
            tableName = "HUYLE.CLUBMEMBER";
            query = "create table "+ tableName +
            " (GUESTID INT NOT NULL,"+ " POINTS INT NOT NULL,"+
            " FOREIGN KEY (GUESTID) REFERENCES HUYLE.GUEST(GUESTID))";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nCreated the table "+tableName+".\n");
            }

            //granting select permissions to all users
            query = "grant select on "+tableName+" to public";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);

            //creating table room
            tableName = "HUYLE.ROOM";
            query = "create table "+ tableName +
            " (ROOMID INT NOT NULL,"+
            " TYPE VARCHAR(10) NOT NULL,"+" PRIMARY KEY (ROOMID))";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nCreated the table "+tableName+".\n");
            }

            //granting select permissions to all users
            query = "grant select on "+tableName+" to public";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);

            //creating table booking
            tableName = "HUYLE.BOOKING";
            query = "create table "+ tableName +
            " (BOOKINGID INT NOT NULL,"+ " STARTDATE DATE NOT NULL,"+
            "ENDDATE DATE NOT NULL,"+ " ROOMID INT NOT NULL,"+
            " GUESTID INT NOT NULL,"+
            " FOREIGN KEY (GUESTID) REFERENCES HUYLE.GUEST(GUESTID),"+
            " FOREIGN KEY (ROOMID) REFERENCES HUYLE.ROOM(ROOMID),"+
            " PRIMARY KEY (BOOKINGID))";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nCreated the table "+tableName+".\n");
            }

            //granting select permissions to all users
            query = "grant select on "+tableName+" to public";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);

            //creating table transaction
            tableName = "HUYLE.TRANSACTION";
            query = "create table "+ tableName +
            " (TRANSACTIONNO INT NOT NULL,"+ " BOOKINGID INT NOT NULL,"+
            " AMENITYID INT NOT NULL,"+ " EXTRACHARGE INT,"+
            " TIPS INT,"+
            " FOREIGN KEY (BOOKINGID) REFERENCES HUYLE.BOOKING(BOOKINGID),"+
            " FOREIGN KEY (AMENITYID) REFERENCES HUYLE.AMENITY(AMENITYID),"+
            " PRIMARY KEY (TRANSACTIONNO))";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nCreated the table "+tableName+".\n");
            }

            //granting select permissions to all users
            query = "grant select on "+tableName+" to public";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);


            //creating table roomclassification
            tableName = "HUYLE.ROOMCLASSIFICATION";
            query = "create table "+ tableName +
            " (TYPE VARCHAR(10) NOT NULL,"+ " PRICE INT NOT NULL,"+
            "BEDS INT NOT NULL,"+ " BATHS INT NOT NULL,"+
            " PRIMARY KEY (TYPE))";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nCreated the table "+tableName+".\n");
            }

            //granting select permissions to all users
            query = "grant select on "+tableName+" to public";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);

            //creating table employee
            tableName = "HUYLE.EMPLOYEE";
            query = "create table "+ tableName +
            " (EMPLOYEEID INT NOT NULL,"+ " POSITION VARCHAR(20) NOT NULL,"+
             " FIRSTNAME VARCHAR(20) NOT NULL,"+" LASTNAME VARCHAR(20) NOT NULL,"+
             " PRIMARY KEY (EMPLOYEEID))";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nCreated the table "+tableName+".\n");
            }

            //granting select permissions to all users
            query = "grant select on "+tableName+" to public";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);


            //creating table shift
            tableName = "HUYLE.SHIFT";
            query = "create table "+ tableName +
            " (EMPLOYEEID INT NOT NULL,"+ " STARTIME INT NOT NULL,"+
            "ENDTIME INT NOT NULL,"+ " WEEKSTARTDATE DATE NOT NULL,"+
            " FOREIGN KEY (EMPLOYEEID) REFERENCES HUYLE.EMPLOYEE(EMPLOYEEID),"+
            " PRIMARY KEY (EMPLOYEEID,WEEKSTARTDATE))";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);
            if (answer != null) {
                System.out.println("\nCreated the table "+tableName+".\n");
            }

            //granting select permissions to all users
            query = "grant select on "+tableName+" to public";
            stmt = dbConn.createStatement();
            answer = stmt.executeQuery(query);

            

            stmt.close();

        } 
        catch (SQLException e) {
            System.err.println("Error while creating tables.");
        }
        try{
            stmt.close();
        }
        catch(SQLException e){
            System.out.println("Error while closing Statement.");
        }

    }  // createTables()


    public static void main(String[] args) {
        
        Connection dbConn = establishConnection(args);
        dropTables(dbConn);
        createTables(dbConn);

    }   
}
