import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class FrontEnd {

    public static void insertData(BackEnd backEnd, Scanner scn){
        System.out.println("Inserting data on:");
        System.out.println("1. Guest  2. Rating  3.ClubMembers 4.Booking  5. Transaction  6. Room \n" 
                            + "7. Employee  8.Shift  9. Amenity 10 Room Classification");
        int field = scn.nextInt();
        if (field == 1){ // Guest
            System.out.println("Is student?: (0/ 1) ");
            String bool = scn.nextLine();
            System.out.println("CreditCard Company (Visa/ Master/ null)");
            String ccc = scn.nextLine();
            System.out.println("First Name: ");
            String fName = scn.nextLine();
            System.out.println("Last Name: ");
            String lName = scn.nextLine();

            backEnd.addGuest(fName, lName, bool, ccc);

        } else if (field == 2){ // Rating 
            System.out.println("Guest ID: ");
            int guestid = scn.nextInt();
            System.out.println("AmenityID: ");
            int aID = scn.nextInt();
            System.out.println("Date in this form: DD-MON-YY ");
            String date = scn.nextLine();
            System.out.println("Rating (1-5)");
            int rating = scn.nextInt(); // error check 
            backEnd.addRating(guestid, aID, rating, date);
        } else if (field == 3){ // ClubMembers
            System.out.println("Guest ID: ");
            int guestid = scn.nextInt();
            System.out.println("Point: ");
            int point = scn.nextInt();
            backEnd.addClubMember(guestid, point);
        } else if (field == 4){ // Booking 
            System.out.println("Date from in this form: DD-MON-YY ");
            String dateF = scn.nextLine();
            System.out.println("Date to in this form: DD-MON-YY ");
            String dateT = scn.nextLine();
            System.out.println("RoomID: ");
            int rID = scn.nextInt();
            System.out.println("GuestID: ");
            int gID = scn.nextInt();
            backEnd.addBooking(gID, dateF, dateT, rID);
        } else if (field == 5){ //Transaction
            System.out.println("BookID: ");
            int bID = scn.nextInt();
            System.out.println("AmenityID: ");
            int aID = scn.nextInt();
            System.out.println("Extra charges: ");
            int charges = scn.nextInt();
            System.out.println("Paid?: (0/ 1) ");
            int bool = scn.nextInt();
            System.out.println("Tips: ");
            int tips = scn.nextInt();
            backEnd.addTransaction(bID, aID, charges, bool, tips);
        } else if (field == 6){ // Room
            System.out.println("Room Type: (Single/ Suite/ DeluxeSuite) ");
            String rt = scn.nextLine();
            System.out.println("RoomID: ");
            int rID = scn.nextInt();
            backEnd.addRoom(rID, rt);
        } else if (field == 7){ // Employee
            System.out.println("Position: ");
            String pos = scn.nextLine();
            System.out.println("First Name: ");
            String fName = scn.nextLine();
            System.out.println("Last Name: ");
            String lName = scn.nextLine(); 
            backEnd.addEmployee(fName, lName, pos);
        } else if (field == 8){ // Shift
            System.out.println("Employee ID: ");
            String eID = scn.nextLine();
            System.out.println("Startdate: in this form: DD-MON-YY ");
            String date = scn.nextLine();
            System.out.println("From time: (XX:XX) ");
            String fromTime = scn.nextLine();
            System.out.println("To time: (XX:XX) ");
            String toTime  = scn.nextLine(); 
            backEnd.addShift(eID,fromTime, toTime, date);
        } else if (field == 9){ // Amenity
            System.out.println("Name:  ");
            String name = scn.nextLine();
            System.out.println("Price:  ");
            int price  = scn.nextInt();
            backEnd.addAmenity(name, price);
        } else if (field == 10){
            System.out.println("Type: (Single, Suite, DeluxeSuite) ");
            String type = scn.nextLine();
            System.out.println("Price: ");
            String price = scn.nextLine();
            System.out.println("Beds");
            String Beds = scn.nextLine();
            System.out.println("Baths");
            String baths = scn.nextLine();
            // backEnd.add
        }
       
    }

    public static void updatData(BackEnd backEnd, Scanner scn){
        System.out.println("Updating data on:");
        System.out.println("1. Guest  2. Rating  3.ClubMembers 4.Booking\n" 
                            + "5. Employee  6.Shift  7. Amenity ");
        int field = scn.nextInt();
        if (field == 1){ // Guest
            System.out.println("Guest ID: ");
            int guestid = scn.nextInt();
            System.out.println("Is student?: (0/ 1) ");
            String bool = scn.nextLine();
            System.out.println("CreditCard Company (Visa/ Master/ null)");
            String ccc = scn.nextLine();
            System.out.println("First Name: ");
            String fName = scn.nextLine();
            System.out.println("Last Name: ");
            String lName = scn.nextLine();
            backEnd.updateGuest(guestid, fName, lName, bool, ccc);
        } else if (field == 2){ // Rating 
            System.out.println("Guest ID: ");
            int guestid = scn.nextInt();
            System.out.println("AmenityID: ");
            int aID = scn.nextInt();
            System.out.println("Date in this form: YYYY-MM-DD");
            String date = scn.nextLine();
            System.out.println("Rating (1-5)");
            int rating = scn.nextInt(); // error check 
            backEnd.updateRating(guestid, aID, rating, date);
        } else if (field == 3){ // ClubMembers
            System.out.println("Guest ID: ");
            int guestid = scn.nextInt();
            System.out.println("points: ");
            int points = scn.nextInt();
            backEnd.updateClubMember(guestid, points);
        } else if (field == 4){ // Booking 
            System.out.println("Date from in this form: YYYY-MM-DD ");
            String dateF = scn.nextLine();
            System.out.println("Date to in this form: YYYY-MM-DD ");
            String dateT = scn.nextLine();
            System.out.println("RoomID: ");
            int rID = scn.nextInt();
            System.out.println("BookingID: ");
            int bID = scn.nextInt();
            backEnd.updateBooking(bID, dateF, dateT, rID);
        }  else if (field == 5){ // Employee
            System.out.println("EmployeeID: ");
            int eID = scn.nextInt();
            System.out.println("Position: ");
            String pos = scn.nextLine();
            System.out.println("First Name: ");
            String fName = scn.nextLine();
            System.out.println("Last Name: ");
            String lName = scn.nextLine(); 
            backEnd.updateEmployee(eID, pos, fName, lName);
        } else if (field == 6){ // Shift
            System.out.println("Employee ID: ");
            String eID = scn.nextLine();
            System.out.println("Startdate: in this form: YYYY-MM-DD ");
            String date = scn.nextLine();
            System.out.println("From time: (XX:XX) ");
            String fromTime = scn.nextLine();
            System.out.println("To time: (XX:XX) ");
            String toTime  = scn.nextLine(); 
            backEnd.updateShift(eID, fromTime, toTime, date);
        } else if (field == 7){ // Amenity
            System.out.println("AmenityID: ");
            int aID = scn.nextInt(); 
            System.out.println("Name:  ");
            String name = scn.nextLine();
            System.out.println("Price:  ");
            int price  = scn.nextInt();
            backEnd.updateAmnity(aID, name, price);
        }
    }

    public static void deleteData(BackEnd backEnd, Scanner scn){
        System.out.println("Deleting data on:");
        System.out.println("1. Guest  2. Rating  3.ClubMembers  4.Booking \n" 
                            + "5. Employee  6.Shift  7. Amenity ");
        int field = scn.nextInt();
        if (field == 1){ // Guest
            System.out.println("Guest ID: ");
            int guestid = scn.nextInt();
            backEnd.deleteGuest(guestid);
        } else if (field == 2){ // Rating 
            System.out.println("Guest ID: ");
            int guestid = scn.nextInt();
            System.out.println("AmenityID: ");
            int aID = scn.nextInt();
            System.out.println("Date in this form: YYYY-MM-DD");
            String date = scn.nextLine();
            backEnd.deleteRating(guestid, aID, date);
        } else if (field == 3){ // ClubMembers
            System.out.println("Guest ID: ");
            int guestid = scn.nextInt();
            backEnd.removeClubMember(guestid);
        } else if (field == 4){ // Booking 
            System.out.println("BookingID: ");
            int bID = scn.nextInt();
            backEnd.removeBooking(bID);
        }  else if (field == 5){ // Employee
            System.out.println("EmployeeID: ");
            int eID = scn.nextInt();
            backEnd.deleteEmployee(eID);
        } else if (field == 6){ // Shift
            System.out.println("Employee ID: ");
            String eID = scn.nextLine();
            System.out.println("Startdate: in this form: YYYY-MM-DD ");
            String date = scn.nextLine();
            backEnd.deleteShift(eID, date);
        } else if (field == 7){ // Amenity
            System.out.println("AmenityID: ");
            int aID = scn.nextInt(); 
            backEnd.deleteAmenity(aID);
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
                System.out.println("7. Update Data");
                System.out.println("8. Delete Data");
                System.out.println("--------------------------------------------------");
            }
            menuOn = 0;
            System.out.println("Enter 1/2/3/4/5 or -1 to exit: ");
            ResultSet ans;
            int quesNum = scn.nextInt();  // Read user input
            if (quesNum == 1){
                System.out.println("Enter the name of the customer: ");
                int bID = scn.nextInt();
                ans = backEnd.query1(bID);
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
                        System.out.println(ans.getDate("dates") + "\t"); // TBD
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
            }  else if (quesNum == 6){ //
                insertData(backEnd, scn);
            } else if (quesNum == 7){
                updatData(backEnd, scn);
            } else if (quesNum == 8){
                deleteData(backEnd, scn);
            } else if (quesNum == -1){ // if user input -1, exit.
                conti = 0;
            } else{
                System.out.println("---------- Please enter only 1/2/3/4/5/6/7/8 or -1 to exit ----------");
            }
        // execute  
        
        }      

    }
}

