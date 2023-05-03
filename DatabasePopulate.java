import java.io.*;
// To compile and execute this program on lectura:
// -Add the Oracle JDBC driver to your CLASSPATH environment variable:
//     export CLASSPATH=/usr/lib/oracle/19.8/client64/lib/ojdbc8.jar:${CLASSPATH}
//  -Compile java files
//  -Run file
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
            // guestID,Start,End,roomID
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
