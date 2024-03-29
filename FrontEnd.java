/*
 * FrontEnd.java -- This program present the connection between the customers and JDBC, this program will prompt the 
 *                  users for input and send the input to BackEnd to perform the database works.

 *            
 * Before you run this program, please add the Oracle JDBC driver to your CLASSPATH environment variable:
 * 
 *         export CLASSPATH=/usr/lib/oracle/19.8/client64/lib/ojdbc8.jar:${CLASSPATH}
 *       
 * Assignment: Program #4: Database Design and Implementation
 * Author:  Mike Yu, Gavin Pogson, Huy Le, Noel Poothokaran

 * Class: CSC460
 * Professor: L. McCann
 * Due: 2023-05-02
 */

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class FrontEnd {

    /*---------------------------------------------------------------------
    |  Method insertData
    |
    |  Purpose: Insert the data into the specific table.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: N/A
    |
    |  Parameters:
    |      insertData -- The connection to the database.
    |      scn -- Scanner to scan user's input.
    |
    |  Returns:  N/A
    *-------------------------------------------------------------------*/
    public static void insertData(BackEnd backEnd, Scanner scn){
        System.out.println("Inserting data on:");
        System.out.println("1. Guest  2. Rating  3.ClubMembers 4.Booking  5. Transaction  6. Room \n" 
                            + "7. Employee  8.Shift  9. Amenity 10. Room Classification");
        String fieldString = scn.nextLine();
        int field = -99;
        try{
            field = Integer.parseInt(fieldString);
        }
        catch (Exception e){
            System.out.println("Invalid input. Insert Failed.\n");
        }
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
            int guestid = Integer.parseInt(scn.nextLine());
            System.out.println("AmenityID: ");
            int aID = Integer.parseInt(scn.nextLine());
            System.out.println("Date in this form: YYYY-MM-DD");
            String date = scn.nextLine();
            System.out.println("Rating (1-5)");
            int rating = Integer.parseInt(scn.nextLine());// error check 
            backEnd.addRating(guestid, aID, rating, date);
        } else if (field == 3){ // ClubMembers
            System.out.println("Guest ID: ");
            int guestid =Integer.parseInt(scn.nextLine());
            System.out.println("Point: ");
            int point = Integer.parseInt(scn.nextLine());
            backEnd.addClubMember(guestid, point);
        } else if (field == 4){ // Booking 
            System.out.println("Date from in this form: YYYY-MM-DD ");
            String dateF = scn.nextLine();
            System.out.println("Date to in this form: YYYY-MM-DD ");
            String dateT = scn.nextLine();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            try {
                if (sdf.parse(dateF).after(sdf.parse(dateT))){
                    System.out.println("Invalid dates input, start date must be before end date");
                    return;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            System.out.println("RoomID: ");
            int rID = Integer.parseInt(scn.nextLine());
            System.out.println("GuestID: ");
            int gID = Integer.parseInt(scn.nextLine());
            backEnd.addBooking(gID, dateF, dateT, rID);
        } else if (field == 5){ //Transaction
            System.out.println("BookID: ");
            int bID = Integer.parseInt(scn.nextLine());
            System.out.println("AmenityID: ");
            int aID = Integer.parseInt(scn.nextLine());
            System.out.println("Extra charges: ");
            int charges = Integer.parseInt(scn.nextLine());
            System.out.println("Tips: ");
            int tips = Integer.parseInt(scn.nextLine());
            backEnd.addTransaction(bID, aID, charges, tips);
        } else if (field == 6){ // Room
            System.out.println("Room Type: (Single/ Suite/ DeluxeSuite) ");
            String rt = scn.nextLine();
            System.out.println("RoomID: ");
            int rID = Integer.parseInt(scn.nextLine());
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
            System.out.println("Startdate: in this form: YYYY-MM-DD ");
            String date = scn.nextLine();
            System.out.println("From time: (XXXX) ");
            String fromTime = scn.nextLine();
            System.out.println("To time: (XXXX) ");
            String toTime  = scn.nextLine(); 
            backEnd.addShift(eID,fromTime, toTime, date);
        } else if (field == 9){ // Amenity
            System.out.println("Name:  ");
            String name = scn.nextLine();
            System.out.println("Price:  ");
            int price  = Integer.parseInt(scn.nextLine());
            backEnd.addAmenity(name, price);
        } 
        else if (field == 10){ // RoomClassification
            System.out.println("Type: (Single, Suite, DeluxeSuite) ");
            String type = scn.nextLine();
            System.out.println("Price: ");
            String price = scn.nextLine();
            System.out.println("Beds");
            String Beds = scn.nextLine();
            System.out.println("Baths");
            String baths = scn.nextLine();
            backEnd.addRoomClassification(type, price, Beds, baths);
        }
       
    }

    /*---------------------------------------------------------------------
    |  Method updateData
    |
    |  Purpose: Update the data in the specific table.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: N/A
    |
    |  Parameters:
    |      insertData -- The connection to the database.
    |      scn -- Scanner to scan user's input.
    |
    |  Returns:  N/A
    *-------------------------------------------------------------------*/
    public static void updatData(BackEnd backEnd, Scanner scn){
        System.out.println("Updating data on:");
        System.out.println("1. Guest  2. Rating  3.ClubMember 4.Booking\n" 
                            + "5. Employee  6.Shift  7. Amenity 8. Transaction 9.Room 10. RoomClassification");
        String fieldString = scn.nextLine();
        int field = -99;
        try{
            field = Integer.parseInt(fieldString);
        }
        catch (Exception e){
            System.out.println("Invalid input. Update Failed.\n");
        }
        if (field == 1){ // Guest
            System.out.println("Guest ID: ");
            int guestid = Integer.parseInt(scn.nextLine());
            System.out.println("Is student?: (0/ 1/ null) ");
            String bool = scn.nextLine();
            System.out.println("CreditCard Company (Visa/ Master/ null)");
            String ccc = scn.nextLine();
            System.out.println("First Name: (null for not updating)");
            String fName = scn.nextLine();
            System.out.println("Last Name: (null for not updating)");
            String lName = scn.nextLine();
            if (bool.equals("null")){
                bool = null;
            }
            if (ccc.equals("null")){
                ccc = null;
            }
            if (lName.equals("null")){
                lName = null;
            }
            if (fName.equals("null")){
                fName = null;
            }

            backEnd.updateGuest(guestid, fName, lName, bool, ccc);
        } else if (field == 2){ // Rating 
            System.out.println("Guest ID: ");
            int guestid = Integer.parseInt(scn.nextLine());
            System.out.println("AmenityID: ");
            int aID = Integer.parseInt(scn.nextLine());
            System.out.println("Date in this form: YYYY-MM-DD");
            String date = scn.nextLine();
            System.out.println("Rating (1-5)");
            int rating = Integer.parseInt(scn.nextLine()); // error check 
            backEnd.updateRating(guestid, aID, rating, date);
        } else if (field == 3){ // ClubMembers
            System.out.println("Guest ID: ");
            int guestid = Integer.parseInt(scn.nextLine());
            System.out.println("points: ");
            int points = Integer.parseInt(scn.nextLine());
            backEnd.updateClubMember(guestid, points);
        } else if (field == 4){ // Booking 
            System.out.println("BookingID: ");
            int bID = Integer.parseInt(scn.nextLine());
            System.out.println("Date from in this form: YYYY-MM-DD (null if not updating)");
            String dateF = scn.nextLine();
            System.out.println("Date to in this form: YYYY-MM-DD (null if not updating)");
            String dateT = scn.nextLine();
            System.out.println("RoomID: (-1 if not updating) ");
            int rID = Integer.parseInt(scn.nextLine());
            if (dateF.equals("null")){
                dateF = null;
            }
            if (dateT.equals("null")){
                dateT = null;
            }
            backEnd.updateBooking(bID, dateF, dateT, rID);
        }  else if (field == 5){ // Employee
            System.out.println("EmployeeID: ");
            int eID = Integer.parseInt(scn.nextLine());
            System.out.println("Position: (null if not updating)");
            String pos = scn.nextLine();
            System.out.println("First Name: (null if not updating)");
            String fName = scn.nextLine();
            System.out.println("Last Name: (null if not updating)");
            String lName = scn.nextLine(); 
            if (fName.equals("null")){
                fName = null;
            }
            if (lName.equals("null")){
                lName = null;
            }
            if (pos.equals("null")){
                pos = null;
            } 
            backEnd.updateEmployee(eID, pos, fName, lName);
        } else if (field == 6){ // Shift
            System.out.println("Employee ID: ");
            String eID = scn.nextLine();
            System.out.println("WeekStartDate: in this form: YYYY-MM-DD ");
            String date = scn.nextLine();
            System.out.println("From time: (if 18:00 input 1800) (null if not updating)");
            String fromTime = scn.nextLine();
            System.out.println("To time: (if 18:00 input 1800) (null if not updating)");
            String toTime  = scn.nextLine(); 
            if (fromTime.equals("null")){
                fromTime = null;
            } 
            if (toTime.equals("null")){
                toTime = null;
            } 
            backEnd.updateShift(eID, fromTime, toTime, date);
        } else if (field == 7){ // Amenity
            System.out.println("AmenityID: ");
            int aID = Integer.parseInt(scn.nextLine());
            System.out.println("Name:  ");
            String name = scn.nextLine();
            System.out.println("Price:   (-1 if not updating)");
            int price  = Integer.parseInt(scn.nextLine());
            if (name.equals("null")){
                name = null;
            } 

            backEnd.updateAmnity(aID, name, price);
        } else if (field == 8){ // Transaction
            System.out.println("BookingID: ");
            int bID = Integer.parseInt(scn.nextLine());
            System.out.println("TransactionID: ");
            int tID = Integer.parseInt(scn.nextLine());
            System.out.println("AmenityID: (-1 if not updating)");
            int aID = Integer.parseInt(scn.nextLine());
            System.out.println("Extra charges:  (-1 if not updating)");
            int echarge = Integer.parseInt(scn.nextLine());
            System.out.println("AmenityID: (-1 if not updating)");
            int tips = Integer.parseInt(scn.nextLine());
            backEnd.updateTransaction(bID, tID, aID, echarge, tips);
        } else if (field == 9){ // Room
            System.out.println("RoomID: ");
            int rID = Integer.parseInt(scn.nextLine());
            System.out.println("Type: (null if not updating)");
            String type = scn.nextLine();
            if (type.equals("null")){
                type = null;
            }
            backEnd.updateRoom(rID, type);
        } else if (field == 10){ // RoomClassification
            System.out.println("Type:  (null if not updating)");
            String type = scn.nextLine();
            if (type.equals("null")){
                type = null;
            }
            System.out.println("Price:  (null if not updating)");
            String price = scn.nextLine();
            if (price.equals("null")){
                price = null;
            }
            System.out.println("beds:  (null if not updating)");
            String beds = scn.nextLine();
            if (beds.equals("null")){
                beds = null;
            }
            System.out.println("baths:  (null if not updating)");
            String baths = scn.nextLine();
            if (baths.equals("null")){
                baths = null;
            }
            backEnd.updateRoomClassification(type, price, beds, baths);
        }
    }

    /*---------------------------------------------------------------------
    |  Method deleteData
    |
    |  Purpose: Delete the data in the specific table.
    |
    |  Pre-condition:  Connection to the database has been established.
    |
    |  Post-condition: N/A
    |
    |  Parameters:
    |      insertData -- The connection to the database.
    |      scn -- Scanner to scan user's input.
    |
    |  Returns:  N/A
    *-------------------------------------------------------------------*/
    public static void deleteData(BackEnd backEnd, Scanner scn){
        System.out.println("Deleting data on:");
        System.out.println("1. Guest  2. Rating  3.ClubMember  4.Booking \n" 
                            + "5. Employee  6.Shift  7. Amenity 8. Room");
        String fieldString = scn.nextLine();
        int field = -99;
        try{
            field = Integer.parseInt(fieldString);
        }
        catch (Exception e){
            System.out.println("Invalid input. Delete Failed.\n");
        }
        if (field == 1){ // Guest
            System.out.println("Guest ID: ");
            int guestid = Integer.parseInt(scn.nextLine());
            backEnd.deleteGuest(guestid);
        } else if (field == 2){ // Rating 
            System.out.println("Guest ID: ");
            int guestid = Integer.parseInt(scn.nextLine());
            System.out.println("AmenityID: ");
            int aID = Integer.parseInt(scn.nextLine());
            System.out.println("Date in this form: YYYY-MM-DD");
            String date = scn.nextLine();
            backEnd.deleteRating(guestid, aID, date);
        } else if (field == 3){ // ClubMembers
            System.out.println("Guest ID: ");
            int guestid = Integer.parseInt(scn.nextLine());
            backEnd.removeClubMember(guestid);
        } else if (field == 4){ // Booking 
            System.out.println("BookingID: ");
            int bID = Integer.parseInt(scn.nextLine());
            backEnd.removeBooking(bID);
        }  else if (field == 5){ // Employee
            System.out.println("EmployeeID: ");
            int eID = Integer.parseInt(scn.nextLine());
            backEnd.deleteEmployee(eID);
        } else if (field == 6){ // Shift
            System.out.println("Employee ID: ");
            String eID = scn.nextLine();
            System.out.println("Startdate: in this form: YYYY-MM-DD ");
            String date = scn.nextLine();
            backEnd.deleteShift(eID, date);
        } else if (field == 7){ // Amenity
            System.out.println("AmenityID: ");
            int aID = Integer.parseInt(scn.nextLine());
            backEnd.deleteAmenity(aID);
        } else if (field == 8){ // Room
            System.out.println("RoomID: ");
            int rID = Integer.parseInt(scn.nextLine());
            backEnd.deleteRoom(rID);
        } 
    }
    public static void main(String[] args) throws IOException, SQLException{       
            //create a menu, and get user input.
        BackEnd backEnd = new BackEnd();

        int conti = 1;
        Scanner scn = new Scanner(System.in);
        while (conti == 1){
            // display menu when run 

            System.out.println("What would you like to know?");
            System.out.println("1. Print a current bill (total $) for a customer for their stay and all unpaid amenities.");
            System.out.println("2. Given a certain date, output the customers that are currently staying at the hotel along with their room numbers. Order by room numbers and group by category of customer.");
            System.out.println("3. Print the schedule of staff given a week (input the start date of the week by the user). A schedule contains the list of staff members working that week and a staff member’s working hours (start and stop times).");
            System.out.println("4. Print the average ratings of different amenities recorded within the range of two dates(input by the user) and sort them in descending order.");
            System.out.println("5. Print the top x guests with the most club460 points in descending order");
            System.out.println("6. Insert Data");
            System.out.println("7. Update Data");
            System.out.println("8. Delete Data");
            System.out.println("--------------------------------------------------");

            System.out.println("Enter 1/2/3/4/5/6/7/8 or -1 to exit: ");
            String ques = scn.nextLine();
            int quesNum = -99;
            try{
                quesNum = Integer.parseInt(ques);
            }
            catch (Exception e){
                System.out.println("Invalid input. Try again.\n");
                continue;
            }
            if (quesNum == 1){
                System.out.println("Enter the BookingID Associated with the Bill: ");
                int bID = scn.nextInt();
                backEnd.query1(bID);
                // print data...
            } else if (quesNum == 2){
                scn.nextLine();
                System.out.println("Enter the date in this format: YYYY-MM-DD");
                String date = scn.nextLine();
                backEnd.query2(date);
            } else if (quesNum == 3) {
                scn.nextLine();
                System.out.println("Enter the week (YYYY-MM-DD): "); // from?
                String week = scn.nextLine();
                backEnd.query3(week);
            } else if (quesNum == 4) {
                scn.nextLine();
                System.out.println("Enter the date from: ");
                String dateFrom = scn.nextLine();
                System.out.println("Enter the date to: "); 
                String dateTo = scn.nextLine().strip();
                backEnd.query4(dateFrom, dateTo);
            } else if (quesNum == 5){ // TBD
                scn.nextLine();
                System.out.println("Enter the value for x");
                String val = scn.nextLine();
                backEnd.query5(val);
            }  else if (quesNum == 6){ //
                insertData(backEnd, scn);
            } else if (quesNum == 7){
                updatData(backEnd, scn);
            } else if (quesNum == 8){
                deleteData(backEnd, scn);
            } else if (quesNum == -1){ // if user input -1, exit.
                conti = 0;
                backEnd.close();
            } else{
                System.out.println("---------- Please enter only 1/2/3/4/5/6/7/8 or -1 to exit ----------");
            }
        // execute

        }      

    }
}

