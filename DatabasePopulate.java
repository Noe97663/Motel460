import java.io.*;

/*+----------------------------------------------------------------------
 ||
 ||  Class:   DatabasePopulate.java
 ||
 ||         Author:  Noel Martin Poothokaran, Mike Yu, Gavin Pogson, Huy Le
 ||
 ||        Purpose:  The class populates the tables created with data from 
 ||                  csv files in the data folder in the same directory
 ||                  as this file.
 ||
 ||  Inherits From:  None
 ||
 ||     Interfaces:  None
 ||
 |+-----------------------------------------------------------------------
 ||
 ||      Constants:  None
 ||
 |+-----------------------------------------------------------------------
 ||
 ||   Constructors:  None
 ||
 ||  Class Methods: method - populateAmenity
 ||                 arguments - String CSVFile, BackEnd be
 ||                 returns - void, populates tables
 || 
 ||                 method - populateGuest
 ||                 arguments - String CSVFile, BackEnd be
 ||                 returns - void, populates tables
 || 
 ||                 method - populateClubMember
 ||                 arguments - String CSVFile, BackEnd be
 ||                 returns - void, populates tables
 || 
 ||                 method - populateTransaction
 ||                 arguments - String CSVFile, BackEnd be
 ||                 returns - void, populates tables
 || 
 ||                 method - populateBooking
 ||                 arguments - String CSVFile, BackEnd be
 ||                 returns - void, populates tables
 || 
 ||                 method - populateShift
 ||                 arguments - String CSVFile, BackEnd be
 ||                 returns - void, populates tables
 || 
 ||                 method - populateRating
 ||                 arguments - String CSVFile, BackEnd be
 ||                 returns - void, populates tables
 ||
 ||                 method - populateRoom
 ||                 arguments - String CSVFile, BackEnd be
 ||                 returns - void, populates tables
 || 
 ||                 method - populateRoomClassification
 ||                 arguments - String CSVFile, BackEnd be
 ||                 returns - void, populates tables
 || 
 ||                 method - populateEmployee
 ||                 arguments - String CSVFile, BackEnd be
 ||                 returns - void, populates tables
 ||
 ||  Inst. Methods:  None.
 ||
 ++-----------------------------------------------------------------------*/
public class DatabasePopulate {
    public static void main(String[] args) {
        BackEnd be = new BackEnd();
        populateGuest("data/Guest.csv",be);
        populateAmenity("data/Amenity.csv",be);
        populateRating("data/Rating.csv",be);
        populateClubMember("data/ClubMember.csv",be);
        populateRoom("data/Room.csv",be);
        populateBooking("data/Booking.csv",be);
        populateTransaction("data/Transaction.csv",be);
        populateRoomClassification("data/RoomClassification.csv",be);
        populateEmployee("data/Employee.csv",be);
        populateShift("data/Shift.csv",be);
        

    }

    /*---------------------------------------------------------------------
    | Method populateAmenity
    |
    | Purpose: Populates the Amenity table in the database by reading data
    | from a CSV file. The CSV file should have the following format:
    | AmenityID,Name,Price
    |
    | Pre-condition: The CSV file with the specified file name exists in the
    | current directory and is readable. An instance of the BackEnd class has
    | been initialized and connected to the database.
    |
    | Post-condition: The Amenity table in the database is populated with data
    | from the CSV file.
    |
    | Parameters:
    | CSVFile - the name of the CSV file to read data from.
    | be - an instance of the BackEnd class.
    |
    | Returns: void.
    /-------------------------------------------------------------------*/
    private static void populateAmenity(String CSVFile, BackEnd be) {
        System.out.println("populating Amenity table");
        // Read CSV file
        File fileRef = null;                     // provides exists() method
        BufferedReader reader = null;            // provides buffered text I/O
        try {
            fileRef = new File(CSVFile);
            if (!fileRef.exists()) {
                System.out.println("PROBLEM:  The input file"
                                    + "does not exist in the current directory.");
                System.out.println("          Create or copy the file to the "
                                    + "current directory and try again.");
                System.exit(-1);
            }
        } catch (Exception e) {
            System.out.println("I/O ERROR: Something went wrong with the "
                                + "detection of the CSV input file.");
            System.exit(-1);
        }

        try {
            reader = new BufferedReader(new FileReader(fileRef));
            String line = null;  // content of one line/record of the CSV file
            reader.readLine();
            // one line in this file will be of the form:
            // AmenityID,Name,Price
            while((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                System.out.println("adding: "+values[0]+ values[1]+ values[2]);
                boolean success = be.addAmenity(values[1], Integer.parseInt(values[2]));
                if(!success){
                    System.out.println("adding "+line+" failed");
                }
            }

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("I/O ERROR: Couldn't open, or couldn't read "
                                + "from, the CSV file.");
            System.exit(-1);
        }

        // We're done reading the CSV file, time to close it up.

        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("VERY STRANGE I/O ERROR: Couldn't close "
                                + "the CSV file!");
            System.exit(-1);
        }
    }

    /*---------------------------------------------------------------------
    | Method populateShift
    |
    | Purpose: Populates the Shift table in the database by reading data
    | from a CSV file. The CSV file should have the following format:
    | EmployeeID,start,end,weekstartdate
    |
    | Pre-condition: The CSV file with the specified file name exists in the
    | current directory and is readable. An instance of the BackEnd class has
    | been initialized and connected to the database.
    |
    | Post-condition: The Shift table in the database is populated with data
    | from the CSV file.
    |
    | Parameters:
    | CSVFile - the name of the CSV file to read data from.
    | be - an instance of the BackEnd class.
    |
    | Returns: void.
    /-------------------------------------------------------------------*/
    private static void populateShift(String CSVFile, BackEnd be) {
        // Read CSV file
        File fileRef = null;                     // provides exists() method
        BufferedReader reader = null;            // provides buffered text I/O
        try {
            fileRef = new File(CSVFile);
            if (!fileRef.exists()) {
                System.out.println("PROBLEM:  The input file"
                                    + "does not exist in the current directory.");
                System.out.println("          Create or copy the file to the "
                                    + "current directory and try again.");
                System.exit(-1);
            }
        } catch (Exception e) {
            System.out.println("I/O ERROR: Something went wrong with the "
                                + "detection of the CSV input file.");
            System.exit(-1);
        }

        try {
            reader = new BufferedReader(new FileReader(fileRef));
            String line = null;  // content of one line/record of the CSV file
            reader.readLine();
            // one line in this file will be of the form:
            // EmployeeID,start,end,weekstartdate
            while((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                System.out.println("adding: "+values[0]+ values[1]+ values[2]+ values[3]);
                boolean success = be.addShift(values[0],values[1], values[2], values[3]);
                if(!success){
                    System.out.println("adding "+line+" failed");
                }
            }

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("I/O ERROR: Couldn't open, or couldn't read "
                                + "from, the CSV file.");
            System.exit(-1);
        }

        // We're done reading the CSV file, time to close it up.

        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("VERY STRANGE I/O ERROR: Couldn't close "
                                + "the CSV file!");
            System.exit(-1);
        }
    }

    /*---------------------------------------------------------------------
    | Method populateEmployee
    |
    | Purpose: Populates the Employee table in the database by reading data
    | from a CSV file. The CSV file should have the following format:
    | FirstName,LastName,Position
    |
    | Pre-condition: The CSV file with the specified file name exists in the
    | current directory and is readable. An instance of the BackEnd class has
    | been initialized and connected to the database.
    |
    | Post-condition: The Employee table in the database is populated with data
    | from the CSV file.
    |
    | Parameters:
    | CSVFile - the name of the CSV file to read data from.
    | be - an instance of the BackEnd class.
    |
    | Returns: void.
    /-------------------------------------------------------------------*/
    private static void populateEmployee(String CSVFile, BackEnd be) {
        System.out.println("populating Employee table");
        // Read CSV file
        File fileRef = null;                     // provides exists() method
        BufferedReader reader = null;            // provides buffered text I/O
        try {
            fileRef = new File(CSVFile);
            if (!fileRef.exists()) {
                System.out.println("PROBLEM:  The input file"
                                    + "does not exist in the current directory.");
                System.out.println("          Create or copy the file to the "
                                    + "current directory and try again.");
                System.exit(-1);
            }
        } catch (Exception e) {
            System.out.println("I/O ERROR: Something went wrong with the "
                                + "detection of the CSV input file.");
            System.exit(-1);
        }

        try {
            reader = new BufferedReader(new FileReader(fileRef));
            String line = null;  // content of one line/record of the CSV file
            reader.readLine();
            // one line in this file will be of the form:
            // FirstName,LastName,Position
            while((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                System.out.println("adding: "+values[0]+ values[1]+ values[2]);
                boolean success = be.addEmployee(values[0],values[1], values[2]);
                if(!success){
                    System.out.println("adding "+line+" failed");
                }
            }

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("I/O ERROR: Couldn't open, or couldn't read "
                                + "from, the CSV file.");
            System.exit(-1);
        }

        // We're done reading the CSV file, time to close it up.

        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("VERY STRANGE I/O ERROR: Couldn't close "
                                + "the CSV file!");
            System.exit(-1);
        }
    }

    /*---------------------------------------------------------------------
    | Method populateRoomClassifciation
    |
    | Purpose: Populates the RoomClassifciation table in the database by reading data
    | from a CSV file. The CSV file should have the following format:
    | type,price,beds,baths
    |
    | Pre-condition: The CSV file with the specified file name exists in the
    | current directory and is readable. An instance of the BackEnd class has
    | been initialized and connected to the database.
    |
    | Post-condition: The RoomClassifciation table in the database is populated with data
    | from the CSV file.
    |
    | Parameters:
    | CSVFile - the name of the CSV file to read data from.
    | be - an instance of the BackEnd class.
    |
    | Returns: void.
    /-------------------------------------------------------------------*/
    private static void populateRoomClassification(String CSVFile, BackEnd be) {
        System.out.println("populating RoomClassification table");
        // Read CSV file
        File fileRef = null;                     // provides exists() method
        BufferedReader reader = null;            // provides buffered text I/O
        try {
            fileRef = new File(CSVFile);
            if (!fileRef.exists()) {
                System.out.println("PROBLEM:  The input file"
                                    + "does not exist in the current directory.");
                System.out.println("          Create or copy the file to the "
                                    + "current directory and try again.");
                System.exit(-1);
            }
        } catch (Exception e) {
            System.out.println("I/O ERROR: Something went wrong with the "
                                + "detection of the CSV input file.");
            System.exit(-1);
        }

        try {
            reader = new BufferedReader(new FileReader(fileRef));
            String line = null;  // content of one line/record of the CSV file
            reader.readLine();
            // one line in this file will be of the form:
            // type,price,beds,baths
            while((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                System.out.println("adding: "+values[0]+ values[1]+ values[2]+ values[3]);
                boolean success = be.addRoomClassification(values[0],values[1],values[2],values[3]);
                if(!success){
                    System.out.println("adding "+line+" failed");
                }
            }

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("I/O ERROR: Couldn't open, or couldn't read "
                                + "from, the CSV file.");
            System.exit(-1);
        }

        // We're done reading the CSV file, time to close it up.

        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("VERY STRANGE I/O ERROR: Couldn't close "
                                + "the CSV file!");
            System.exit(-1);
        }
    }

    /*---------------------------------------------------------------------
    | Method populateRoom
    |
    | Purpose: Populates the Room table in the database by reading data
    | from a CSV file. The CSV file should have the following format:
    | RoomID,type
    |
    | Pre-condition: The CSV file with the specified file name exists in the
    | current directory and is readable. An instance of the BackEnd class has
    | been initialized and connected to the database.
    |
    | Post-condition: The Room table in the database is populated with data
    | from the CSV file.
    |
    | Parameters:
    | CSVFile - the name of the CSV file to read data from.
    | be - an instance of the BackEnd class.
    |
    | Returns: void.
    /-------------------------------------------------------------------*/
    private static void populateRoom(String CSVFile, BackEnd be) {
        System.out.println("populating Room table");
        // Read CSV file
        File fileRef = null;                     // provides exists() method
        BufferedReader reader = null;            // provides buffered text I/O
        try {
            fileRef = new File(CSVFile);
            if (!fileRef.exists()) {
                System.out.println("PROBLEM:  The input file"
                                    + "does not exist in the current directory.");
                System.out.println("          Create or copy the file to the "
                                    + "current directory and try again.");
                System.exit(-1);
            }
        } catch (Exception e) {
            System.out.println("I/O ERROR: Something went wrong with the "
                                + "detection of the CSV input file.");
            System.exit(-1);
        }

        try {
            reader = new BufferedReader(new FileReader(fileRef));
            String line = null;  // content of one line/record of the CSV file
            reader.readLine();
            // one line in this file will be of the form:
            // RoomID,type
            while((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                System.out.println("adding: "+values[0]+ values[1]);
                boolean success = be.addRoom(Integer.parseInt(values[0]),values[1]);
                if(!success){
                    System.out.println("adding "+line+" failed");
                }
            }

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("I/O ERROR: Couldn't open, or couldn't read "
                                + "from, the CSV file.");
            System.exit(-1);
        }

        // We're done reading the CSV file, time to close it up.

        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("VERY STRANGE I/O ERROR: Couldn't close "
                                + "the CSV file!");
            System.exit(-1);
        }
    }

    /*---------------------------------------------------------------------
    | Method populateTransaction
    |
    | Purpose: Populates the Transaction table in the database by reading data
    | from a CSV file. The CSV file should have the following format:
    | BookingID,AmenityID,ExtraCharge,Tips
    |
    | Pre-condition: The CSV file with the specified file name exists in the
    | current directory and is readable. An instance of the BackEnd class has
    | been initialized and connected to the database.
    |
    | Post-condition: The Transaction table in the database is populated with data
    | from the CSV file.
    |
    | Parameters:
    | CSVFile - the name of the CSV file to read data from.
    | be - an instance of the BackEnd class.
    |
    | Returns: void.
    /-------------------------------------------------------------------*/
    private static void populateTransaction(String CSVFile, BackEnd be) {
        System.out.println("populating Transaction table");
        // Read CSV file
        File fileRef = null;                     // provides exists() method
        BufferedReader reader = null;            // provides buffered text I/O
        try {
            fileRef = new File(CSVFile);
            if (!fileRef.exists()) {
                System.out.println("PROBLEM:  The input file"
                                    + "does not exist in the current directory.");
                System.out.println("          Create or copy the file to the "
                                    + "current directory and try again.");
                System.exit(-1);
            }
        } catch (Exception e) {
            System.out.println("I/O ERROR: Something went wrong with the "
                                + "detection of the CSV input file.");
            System.exit(-1);
        }

        try {
            reader = new BufferedReader(new FileReader(fileRef));
            String line = null;  // content of one line/record of the CSV file
            reader.readLine();
            // one line in this file will be of the form:
            // BookingID,AmenityID,ExtraCharge,Tips
            while((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                System.out.println("adding: "+values[1]+ values[2]+ values[3]+ values[4]);
                boolean success = be.addTransaction(Integer.parseInt(values[1]),
                Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]));
                if(!success){
                    System.out.println("adding "+line+" failed");
                }
            }

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("I/O ERROR: Couldn't open, or couldn't read "
                                + "from, the CSV file.");
            System.exit(-1);
        }

        // We're done reading the CSV file, time to close it up.

        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("VERY STRANGE I/O ERROR: Couldn't close "
                                + "the CSV file!");
            System.exit(-1);
        }
    }

    /*---------------------------------------------------------------------
    | Method populateBooking
    |
    | Purpose: Populates the Booking table in the database by reading data
    | from a CSV file. The CSV file should have the following format:
    | bookingID,guestID,Start,End,roomID
    |
    | Pre-condition: The CSV file with the specified file name exists in the
    | current directory and is readable. An instance of the BackEnd class has
    | been initialized and connected to the database.
    |
    | Post-condition: The Booking table in the database is populated with data
    | from the CSV file.
    |
    | Parameters:
    | CSVFile - the name of the CSV file to read data from.
    | be - an instance of the BackEnd class.
    |
    | Returns: void.
    /-------------------------------------------------------------------*/
    private static void populateBooking(String CSVFile, BackEnd be) {
        System.out.println("populating Booking table");
        // Read CSV file
        File fileRef = null;                     // provides exists() method
        BufferedReader reader = null;            // provides buffered text I/O
        try {
            fileRef = new File(CSVFile);
            if (!fileRef.exists()) {
                System.out.println("PROBLEM:  The input file"
                                    + "does not exist in the current directory.");
                System.out.println("          Create or copy the file to the "
                                    + "current directory and try again.");
                System.exit(-1);
            }
        } catch (Exception e) {
            System.out.println("I/O ERROR: Something went wrong with the "
                                + "detection of the CSV input file.");
            System.exit(-1);
        }

        try {
            reader = new BufferedReader(new FileReader(fileRef));
            String line = null;  // content of one line/record of the CSV file
            reader.readLine();
            // one line in this file will be of the form:
            // bookingID,guestID,Start,End,roomID
            while((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                System.out.println("adding: "+values[1]+ values[2]+ values[3]+ values[4]);
                boolean success = be.addBooking(Integer.parseInt(values[1]),
                values[2], values[3], Integer.parseInt(values[4]));
                if(!success){
                    System.out.println("adding "+line+" failed");
                }
            }

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("I/O ERROR: Couldn't open, or couldn't read "
                                + "from, the CSV file.");
            System.exit(-1);
        }

        // We're done reading the CSV file, time to close it up.

        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("VERY STRANGE I/O ERROR: Couldn't close "
                                + "the CSV file!");
            System.exit(-1);
        }
    }

    /*---------------------------------------------------------------------
    | Method populateClubMember
    |
    | Purpose: Populates the ClubMember table in the database by reading data
    | from a CSV file. The CSV file should have the following format:
    | GuestID,Points
    |
    | Pre-condition: The CSV file with the specified file name exists in the
    | current directory and is readable. An instance of the BackEnd class has
    | been initialized and connected to the database.
    |
    | Post-condition: The ClubMember table in the database is populated with data
    | from the CSV file.
    |
    | Parameters:
    | CSVFile - the name of the CSV file to read data from.
    | be - an instance of the BackEnd class.
    |
    | Returns: void.
    /-------------------------------------------------------------------*/
    private static void populateClubMember(String CSVFile, BackEnd be) {
        System.out.println("populating ClubMember table");
        // Read CSV file
        File fileRef = null;                     // provides exists() method
        BufferedReader reader = null;            // provides buffered text I/O
        try {
            fileRef = new File(CSVFile);
            if (!fileRef.exists()) {
                System.out.println("PROBLEM:  The input file"
                                    + "does not exist in the current directory.");
                System.out.println("          Create or copy the file to the "
                                    + "current directory and try again.");
                System.exit(-1);
            }
        } catch (Exception e) {
            System.out.println("I/O ERROR: Something went wrong with the "
                                + "detection of the CSV input file.");
            System.exit(-1);
        }

        try {
            reader = new BufferedReader(new FileReader(fileRef));
            String line = null;  // content of one line/record of the CSV file
            reader.readLine();
            // one line in this file will be of the form:
            // GuestID,Points
            while((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                System.out.println("adding: "+values[0]+ values[1]);
                boolean success = be.addClubMember(Integer.parseInt(values[0]),
                                                 Integer.parseInt(values[1]));
                if(!success){
                    System.out.println("adding "+line+" failed");
                }
            }

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("I/O ERROR: Couldn't open, or couldn't read "
                                + "from, the CSV file.");
            System.exit(-1);
        }

        // We're done reading the CSV file, time to close it up.

        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("VERY STRANGE I/O ERROR: Couldn't close "
                                + "the CSV file!");
            System.exit(-1);
        }
    }

    /*---------------------------------------------------------------------
    | Method populateRating
    |
    | Purpose: Populates the Rating table in the database by reading data
    | from a CSV file. The CSV file should have the following format:
    | GuestID,AmenityID,RatingDate,Rating
    |
    | Pre-condition: The CSV file with the specified file name exists in the
    | current directory and is readable. An instance of the BackEnd class has
    | been initialized and connected to the database.
    |
    | Post-condition: The Rating table in the database is populated with data
    | from the CSV file.
    |
    | Parameters:
    | CSVFile - the name of the CSV file to read data from.
    | be - an instance of the BackEnd class.
    |
    | Returns: void.
    /-------------------------------------------------------------------*/
    private static void populateRating(String CSVFile, BackEnd be) {
        System.out.println("populating Rating table");
        // Read CSV file
        File fileRef = null;                     // provides exists() method
        BufferedReader reader = null;            // provides buffered text I/O
        try {
            fileRef = new File(CSVFile);
            if (!fileRef.exists()) {
                System.out.println("PROBLEM:  The input file"
                                    + "does not exist in the current directory.");
                System.out.println("          Create or copy the file to the "
                                    + "current directory and try again.");
                System.exit(-1);
            }
        } catch (Exception e) {
            System.out.println("I/O ERROR: Something went wrong with the "
                                + "detection of the CSV input file.");
            System.exit(-1);
        }

        try {
            reader = new BufferedReader(new FileReader(fileRef));
            String line = null;  // content of one line/record of the CSV file
            reader.readLine();
            // one line in this file will be of the form:
            // GuestID,AmenityID,RatingDate,Rating
            while((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                System.out.println("adding: "+values[0]+ values[1]+ values[3]+ values[2]);
                boolean success = be.addRating(Integer.parseInt(values[0]),
                Integer.parseInt(values[1]), Integer.parseInt(values[3]), values[2]);
                if(!success){
                    System.out.println("adding "+line+" failed");
                }
            }

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("I/O ERROR: Couldn't open, or couldn't read "
                                + "from, the CSV file.");
            System.exit(-1);
        }

        // We're done reading the CSV file, time to close it up.

        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("VERY STRANGE I/O ERROR: Couldn't close "
                                + "the CSV file!");
            System.exit(-1);
        }
    }

    /*---------------------------------------------------------------------
    | Method populateGuest
    |
    | Purpose: Populates the Guest table in the database by reading data
    | from a CSV file. The CSV file should have the following format:
    | GuestID,FirstName,LastName,StudentStatus,CreditCardCompany
    |
    | Pre-condition: The CSV file with the specified file name exists in the
    | current directory and is readable. An instance of the BackEnd class has
    | been initialized and connected to the database.
    |
    | Post-condition: The Guest table in the database is populated with data
    | from the CSV file.
    |
    | Parameters:
    | CSVFile - the name of the CSV file to read data from.
    | be - an instance of the BackEnd class.
    |
    | Returns: void.
    /-------------------------------------------------------------------*/
    public static void populateGuest(String CSVFile, BackEnd be) {
        System.out.println("populating Guest table");
        // Read CSV file
        File fileRef = null;                     // provides exists() method
        BufferedReader reader = null;            // provides buffered text I/O
        try {
            fileRef = new File(CSVFile);
            if (!fileRef.exists()) {
                System.out.println("PROBLEM:  The input file"
                                    + "does not exist in the current directory.");
                System.out.println("          Create or copy the file to the "
                                    + "current directory and try again.");
                System.exit(-1);
            }
        } catch (Exception e) {
            System.out.println("I/O ERROR: Something went wrong with the "
                                + "detection of the CSV input file.");
            System.exit(-1);
        }

        try {
            reader = new BufferedReader(new FileReader(fileRef));
            String line = null;  // content of one line/record of the CSV file
            reader.readLine();
            // one line in this file will be of the form:
            // GuestID,FirstName,LastName,StudentStatus,CreditCardCompany
            while((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                System.out.println("adding: "+values[1]+ values[2]+ values[3]+ values[4]);
                boolean success = be.addGuest(values[1], values[2], values[3], values[4]);
                if(!success){
                    System.out.println("adding "+line+" failed");
                }
            }

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("I/O ERROR: Couldn't open, or couldn't read "
                                + "from, the CSV file.");
            System.exit(-1);
        }

        // We're done reading the CSV file, time to close it up.

        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("VERY STRANGE I/O ERROR: Couldn't close "
                                + "the CSV file!");
            System.exit(-1);
        }

    }


}
