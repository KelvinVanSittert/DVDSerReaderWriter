/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester2;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kelvin
 */
public class Tester2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ArrayList<DVD> dvds = GetDVDList();
        ArrayList<Customer> customers = GetCustomers();
        ArrayList<Rental> rentals = GetRentals();
        
        AddToDatabase(dvds,customers,rentals);
    }

    private static ArrayList<Rental> GetRentals() {
        ArrayList<Rental> rentalArr = new ArrayList();
        try {
            FileInputStream fileIn = new FileInputStream("Renals.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            try{
                while (in.readObject() != null) {
                    rentalArr.add((Rental) in.readObject());
                }
                //aa = (ArrayList<DVD>) in.readObject();

                in.close();
            }
            catch (EOFException exc)
            {
                in.close();
            }
            catch (IOException ia) {
                ia.printStackTrace();
                in.close();
                return rentalArr;
            }
            fileIn.close();
        }  catch (ClassNotFoundException ca) {
            System.out.println("Class not found");
            ca.printStackTrace();
            return rentalArr;
        }
        catch (IOException ia) {
            ia.printStackTrace();
            return rentalArr;
            }
        
        //Test to make sure its working
        //System.out.println(aa.toString());
        return rentalArr;
    }

    private static ArrayList<Customer> GetCustomers() {
        ArrayList<Customer> custArr = new ArrayList();
        try {
            FileInputStream fileIn = new FileInputStream("Customers.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            try{
                while (in.readObject() != null) {
                    custArr.add((Customer) in.readObject());
                }
                //aa = (ArrayList<DVD>) in.readObject();

                in.close();
            }
            catch (EOFException exc)
            {
                in.close();
            }
            catch (IOException ia) {
                ia.printStackTrace();
                in.close();
                return custArr;
            }
            fileIn.close();
        }  catch (ClassNotFoundException ca) {
            System.out.println("Class not found");
            ca.printStackTrace();
            return custArr;
        }
        catch (IOException ia) {
            ia.printStackTrace();
            return custArr;
            }
        
        //Test to make sure its working
        //System.out.println(aa.toString());
        return custArr;
    }

    private static ArrayList<DVD> GetDVDList() {
        //Creating the array with data to populate the ser file
        
        ArrayList<DVD> dvdArr = new ArrayList();
        try {
            FileInputStream fileIn = new FileInputStream("Movies.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            try{
                while (in.readObject() != null) {
                    dvdArr.add((DVD) in.readObject());
                }
                //aa = (ArrayList<DVD>) in.readObject();

                in.close();
            }
            catch (EOFException exc)
            {
                in.close();
            }
            catch (IOException ia) {
                ia.printStackTrace();
                in.close();
                return dvdArr;
            }
            fileIn.close();
        }  catch (ClassNotFoundException ca) {
            System.out.println("Class not found");
            ca.printStackTrace();
            return dvdArr;
        }
        catch (IOException ia) {
            ia.printStackTrace();
            return dvdArr;
            }
        
        //Test to make sure its working
        //System.out.println(aa.toString());
        return dvdArr;
    }

    private static void AddToDatabase(ArrayList<DVD> dvds, ArrayList<Customer> customers, ArrayList<Rental> rentals) {
        
        String create_CustomersTable_stmt="create table Customers (custNum INTEGER NOT NULL PRIMARY KEY, firstName VARCHAR(30), surname VARCHAR(30), phoneNum VARCHAR(20), credit DECIMAL(5,2), canRent BOOLEAN)";
        String create_MoviesTable_stmt="create table Movies (dvdNumber INTEGER NOT NULL PRIMARY KEY, title VARCHAR(50), category VARCHAR(30), price DECIMAL(5,2), newRelease BOOLEAN, availableForRent BOOLEAN)";
        String create_RentalsTable_stmt="create table Rentals (rentalNumber INTEGER NOT NULL PRIMARY KEY, dateRented VARCHAR(30), dateReturned VARCHAR(30), custNumber INTEGER, dvdNumber INTEGER , totalPenaltyCost DECIMAL(5,2)";
        //String create_RentalsTable_stmt="create table Rentals (rentalNumber INTEGER NOT NULL PRIMARY KEY, dateRented VARCHAR(30), dateReturned VARCHAR(30), custNumber INTEGER references Customers(custNum), dvdNumber INTEGER references Movies(dvdNumber), totalPenaltyCost DECIMAL(5,2))";
        create_RentalsTable_stmt += " ,CONSTRAINT fk_Movies" +
            "    FOREIGN KEY (dvdNumber)" +
            "    REFERENCES Movies (dvdNumber)" +
            "    ON DELETE CASCADE";
        create_RentalsTable_stmt += " ,CONSTRAINT fk_Customers" +
            "    FOREIGN KEY (custNumber)" +
            "    REFERENCES Customers (custNum)" +
            "    ON DELETE CASCADE)";

        
        String drop_CustomersTable_stmt="drop table Customers";
        String drop_MoviesTable_stmt="drop table Movies";
        String drop_RentalsTable_stmt="drop table Rentals;";
       // String identityInsert_On_stmt="SET IDENTITY_INSERT Customers ON;SET IDENTITY_INSERT Movies ON;SET IDENTITY_INSERT Rentals ON;";
      //  String identityInsert_Off_stmt="SET IDENTITY_INSERT Customers OFF;SET IDENTITY_INSERT Movies OFF;SET IDENTITY_INSERT Rentals OFF;";

          try {
                Path path = Paths.get("Database/publisher.mdb");
                Path absolutePath = path.toAbsolutePath();
                String filename = absolutePath.toString();
               String dbURL = "jdbc:ucanaccess://";//specify the full pathname of the database
               dbURL+= filename.trim() + ";DriverID=22;READONLY=true}"; 
                String driverName = "net.ucanaccess.jdbc.UcanaccessDriver";

                System.out.println("About to Load the JDBC Driver....");
                Class.forName(driverName);
                System.out.println("Driver Loaded Successfully....");
                System.out.println("About to get a connection....");
                Connection con = DriverManager.getConnection(dbURL); 
                System.out.println("Connection Established Successfully....");
               // create a java.sql.Statement so we can run queries
                System.out.println("Creating statement Object....");
                Statement s = con.createStatement();
                
                System.out.println("Statement object created Successfully....");

                System.out.println("About to execute SQL stmt....");
                DropTable(s, drop_CustomersTable_stmt);
                DropTable(s, drop_MoviesTable_stmt);
                DropTable(s, drop_RentalsTable_stmt);

                s.executeUpdate(create_MoviesTable_stmt);
                s.executeUpdate(create_CustomersTable_stmt);
                s.executeUpdate(create_RentalsTable_stmt);
               // s.executeUpdate(identityInsert_On_stmt); 
                for(DVD dvd:dvds) { 
                      String insert_stmt="insert into Movies(dvdNumber, title, category, price, newRelease, availableForRent)";
                      insert_stmt += "values(" + dvd.getDvdNumber() + ",'" + dvd.getTitle().replace("'", "''") + "','" + dvd.getCategory() + "'," + dvd.getPrice() + "," + dvd.isNewRelease() + "," + dvd.isAvailable() + ");";
                      s.executeUpdate(insert_stmt); 
                }
                
                for(Customer customer:customers) { 
                      String insert_stmt="insert into Customers(custNum, firstName, surname, phoneNum, credit,canRent)";
                      insert_stmt += "values(" + customer.getCustNumber()+ ",'" + customer.getName()+ "','" + customer.getSurname()+ "','" + customer.getPhoneNum()+ "'," + customer.getCredit()+ "," + customer.canRent()+   ");";
                      s.executeUpdate(insert_stmt); 
                }
                
                for(Rental rental:rentals) { 
                      String insert_stmt="insert into Movies(rentalNumber, dateRented, dateReturned, custNumber, dvdNumber, totalPenaltyCost)";
                      insert_stmt += "values(" + rental.getRentalNumber()+ ",'" + rental.getDateRented()+ "','" + rental.getDateReturned()+ "'," + rental.getCustNumber()+ "," + rental.getDvdNumber()+ "," + rental.getTotalPenaltyCost()+   ");";
                      s.executeUpdate(insert_stmt); 
                }
                //s.executeUpdate(identityInsert_Off_stmt); 

                System.out.println("About to close Statement....");
                s.close(); // close the Statement to let the database know we're done with it
                con.close(); // close the Connection to let the database know we're done with it
                System.out.println("Statement closed successfully....");
            }


                catch (Exception err) {
                System.out.println("ERROR: " + err);
            }
    }

    private static void DropTable(Statement s, String dropStatement) {
        try {
            s.executeUpdate(dropStatement);
        } catch (SQLException ex) {
            Logger.getLogger(Tester2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        
    
}
