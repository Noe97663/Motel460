import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class FrontEnd {

    public static void insertData(BackEnd backEnd, Scanner scn){
        System.out.println("Inserting data on:");
        System.out.println("1. Guest  2. Rating  3.ClubMember 4.Booking  5. Transaction  6. Room \n" 
                            + "7. Employee  8.Shift  9. Amenity ");
        int field = scn.nextInt();
        if (field == 1){ // Guest
            System.out.println("Is student?: (true/ false) ");
            Boolean bool = scn.nextBoolean();
            System.out.println("CreditCard Company (Visa/ Master/ null)");
            String ccc = scn.nextLine();
            System.out.println("First Name: ");
            String fName = scn.nextLine();
            System.out.println("Last Name: ");
            String lName = scn.nextLine();
            // backEnd.
        } else if (field == 2){ // Rating 
            System.out.println("Guest ID: ");
            int guestid = scn.nextInt();
            System.out.println("AmenityID: ");
            int aID = scn.nextInt();
            System.out.println("Date in this form: DD-MON-YY ");
            String date = scn.nextLine();
            System.out.println("Rating (1-5)");
            int rating = scn.nextInt(); // error check 
        } else if (field == 3){ // ClubMember
            System.out.println("Guest ID: ");
            int guestid = scn.nextInt();
        } else if (field == 4){ // Booking 
            System.out.println("Date from in this form: DD-MON-YY ");
            String dateF = scn.nextLine();
            System.out.println("Date to in this form: DD-MON-YY ");
            String dateT = scn.nextLine();
            System.out.println("RoomID: ");
            String rID = scn.nextLine();
            System.out.println("GuestID: ");
            int gID = scn.nextInt();
        } else if (field == 5){ //Transaction
            System.out.println("BookID: ");
            int bID = scn.nextInt();
            System.out.println("AmenityID: ");
            int aID = scn.nextInt();
            System.out.println("Extra charges: ");
            int charges = scn.nextInt();
            System.out.println("Paid?: (true/ false) ");
            Boolean bool = scn.nextBoolean();
            System.out.println("Tips: ");
            int tips = scn.nextInt();
        } else if (field == 6){ // Room
            System.out.println("Room Type: (Single/ Suite/ DeluxeSuite) ");
            String rt = scn.nextLine();
            System.out.println("RoomID: ");
            String rID = scn.nextLine();
        } else if (field == 7){ // Employee
            System.out.println("Position: ");
            String pos = scn.nextLine();
            System.out.println("First Name: ");
            String fName = scn.nextLine();
            System.out.println("Last Name: ");
            String lName = scn.nextLine(); 
        } else if (field == 8){ // Shift
            System.out.println("Startdate: in this form: DD-MON-YY ");
            String date = scn.nextLine();
            System.out.println("From time: (XX:XX) ");
            String fromTime = scn.nextLine();
            System.out.println("To time: (XX:XX) ");
            String toTime  = scn.nextLine(); 
        } else if (field == 9){ // Amenity
            System.out.println("Name:  ");
            String name = scn.nextLine();
            System.out.println("Price:  ");
            int price  = scn.nextInt();
        }
       
    }

    public static void updatData(BackEnd backEnd, Scanner scn){
        System.out.println("Updating data on:");
        System.out.println("1. Guest  2. Rating  3.ClubMember 4.Booking\n" 
                            + "5. Employee  6.Shift  7. Amenity ");
        int field = scn.nextInt();
        if (field == 1){ // Guest
            System.out.println("Guest ID: ");
            int guestid = scn.nextInt();
            System.out.println("Is student?: (true/ false) ");
            Boolean bool = scn.nextBoolean();
            System.out.println("CreditCard Company (Visa/ Master/ null)");
            String ccc = scn.nextLine();
            System.out.println("First Name: ");
            String fName = scn.nextLine();
            System.out.println("Last Name: ");
            String lName = scn.nextLine();
            // backEnd.
        } else if (field == 2){ // Rating 
            System.out.println("Guest ID: ");
            int guestid = scn.nextInt();
            System.out.println("AmenityID: ");
            int aID = scn.nextInt();
            System.out.println("Date in this form: YYYY-MM-DD");
            String date = scn.nextLine();
            System.out.println("Rating (1-5)");
            int rating = scn.nextInt(); // error check 
        } else if (field == 3){ // ClubMember
            System.out.println("Guest ID: ");
            int guestid = scn.nextInt();
            System.out.println("points: ");
            int points = scn.nextInt();
        } else if (field == 4){ // Booking 
            System.out.println("Date from in this form: YYYY-MM-DD ");
            String dateF = scn.nextLine();
            System.out.println("Date to in this form: YYYY-MM-DD ");
            String dateT = scn.nextLine();
            System.out.println("RoomID: ");
            String rID = scn.nextLine();
            System.out.println("BookingID: ");
            int bID = scn.nextInt();
        }  else if (field == 5){ // Employee
            System.out.println("EmployeeID: ");
            int eID = scn.nextInt();
            System.out.println("Position: ");
            String pos = scn.nextLine();
            System.out.println("First Name: ");
            String fName = scn.nextLine();
            System.out.println("Last Name: ");
            String lName = scn.nextLine(); 
        } else if (field == 6){ // Shift
            System.out.println("Startdate: in this form: YYYY-MM-DD ");
            String date = scn.nextLine();
            System.out.println("From time: (XX:XX) ");
            String fromTime = scn.nextLine();
            System.out.println("To time: (XX:XX) ");
            String toTime  = scn.nextLine(); 
        } else if (field == 7){ // Amenity
            System.out.println("AmenityID: ");
            int aID = scn.nextInt(); 
            System.out.println("Name:  ");
            String name = scn.nextLine();
            System.out.println("Price:  ");
            int price  = scn.nextInt();
        }
    }

    public static void deleteData(BackEnd backEnd, Scanner scn){
        System.out.println("Deleting data on:");
        System.out.println("1. Guest  2. Rating  3.ClubMember  4.Booking \n" 
                            + "5. Employee  6.Shift  7. Amenity ");
        int field = scn.nextInt();
        if (field == 1){ // Guest
            System.out.println("Guest ID: ");
            int guestid = scn.nextInt();
            // backEnd.
        } else if (field == 2){ // Rating 
            System.out.println("Guest ID: ");
            int guestid = scn.nextInt();
            System.out.println("AmenityID: ");
            int aID = scn.nextInt();
            System.out.println("Date in this form: YYYY-MM-DD");
            String date = scn.nextLine();
        } else if (field == 3){ // ClubMember
            System.out.println("Guest ID: ");
            int guestid = scn.nextInt();
        } else if (field == 4){ // Booking 
            System.out.println("BookingID: ");
            int bID = scn.nextInt();
        }  else if (field == 5){ // Employee
            System.out.println("EmployeeID: ");
            int eID = scn.nextInt();
        } else if (field == 6){ // Shift
            System.out.println("Startdate: in this form: YYYY-MM-DD ");
            String date = scn.nextLine();
            System.out.println("From time: (XX:XX) ");
            String fromTime = scn.nextLine();
            System.out.println("To time: (XX:XX) ");
            String toTime  = scn.nextLine(); 
        } else if (field == 7){ // Amenity
            System.out.println("AmenityID: ");
            int aID = scn.nextInt(); 
        }
    }
    public static void main(String[] args) throws IOException, SQLException{       
            //create a menu, and get user input.
        BackEnd backEnd = new BackEnd();

        int conti = 1;
        int menuOn = 1;
        Scanner scn = new Scanner(System.in);
        while (conti == 1){
            // display menu when run 
            if (menuOn == 1){
                System.out.println("Hello, what would you like to know?");
                System.out.println("1. Print a current bill (total $) for a customer for their stay and all unpaid amenities.");
                System.out.println("2. Given a certain date, output the customers that are currently staying at the hotel along with their room numbers. Order by room numbers and group by category of customer.");
                System.out.println("3. Print the schedule of staff given a week (input the start date of the week by the user). A schedule contains the list of staff members working that week and a staff member’s working hours (start and stop times).");
                System.out.println("4. Print the average ratings of different amenities recorded within the range of two dates(input by the user) and sort them in descending order.");
                System.out.println("5. DESIGN");
                System.out.println("6. Insert Data");
                System.out.println("--------------------------------------------------");
            }
            menuOn = 0;
            System.out.println("Enter 1/2/3/4/5 or -1 to exit: ");
            ResultSet ans;
            int quesNum = scn.nextInt();  // Read user input
            if (quesNum == 1){
                System.out.println("Enter the name of the customer: ");
                String name = scn.nextLine();
                ans = backEnd.query1(name);
                // print data...
                if (ans != null) {
                    System.out.println("\nThe results of the query are:\n");
                            // Get the data about the query result to learn
                            // the attribute names and use them as column header
                    ResultSetMetaData answermetadata = ans.getMetaData();
                    for (int i = 1; i <= answermetadata.getColumnCount(); i++) {
                        System.out.print(answermetadata.getColumnName(i) + "\t        ");
                    }
                    System.out.println();
                        // Use next() to advance cursor through the result
                        // tuples and print their attribute values
                    while (ans.next()) {
                        System.out.println(ans.getDate("dates") + "\t" + ans.getFloat("dif") + "\t        " + ans.getFloat("maxtemp"));
                    }
                } 
            } else if (quesNum == 2){
                System.out.println("Enter the date in this format: YYYY-MM-DD");
                String date = scn.nextLine();
                ans = backEnd.query2(date);
            } else if (quesNum == 3) {
                System.out.println("Enter the week: "); // from?
                String week = scn.nextLine();
                ans = backEnd.query3(week);
            } else if (quesNum == 4) {
                System.out.println("Enter the date from: ");
                String dateFrom = scn.nextLine();
                System.out.println("Enter the date to: "); 
                String dateTo = scn.nextLine();
                ans = backEnd.query4(dateFrom, dateTo);
            } else if (quesNum == 5){ // TBD
                // ans = backEnd.query5();
            } else if (quesNum == -1){ // if user input -1, exit.
                conti = 0;
            } else if (quesNum == 6){ //
                insertData(backEnd, scn);
            } else if (quesNum == 7){

            } else{
                System.out.println("---------- Please enter only 1/2/3/4/5/6/7/8 ----------");
            }
        // execute  
        
        }      

    }
}
