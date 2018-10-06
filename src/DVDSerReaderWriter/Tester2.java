/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DVDSerReaderWriter;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Kelvin
 */
public class Tester2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Creating the array with data to populate the ser file
        
        ArrayList<DVD> dvdArr = new ArrayList();
        
        dvdArr.add(new DVD(1001,"The Umpire Strikes Back",2,false,false));
        dvdArr.add(new DVD(1002,"Balls of Fury",5,true,true));
        dvdArr.add(new DVD(1003,"Bridget Jones's Diarrhea",4,false,true));
        dvdArr.add(new DVD(1004,"Dumb and Dumberer",5,true,false));
        dvdArr.add(new DVD(1005,"Golden I-pod",3,false,true));
        dvdArr.add(new DVD(1006,"Harry Pothead and the Bong of Fire",6,true,true));
        dvdArr.add(new DVD(1007,"I See Stupid People",1,false,true));
        dvdArr.add(new DVD(1008,"Juwanna Mann",3,false,true));
        dvdArr.add(new DVD(1009,"Parrots of the Coffee-bean",6,true,true));
        dvdArr.add(new DVD(1010,"Who's your Caddy?",1,false,true));
        dvdArr.add(new DVD(1011,"You only Burp Thrice",7,false,true));
        
        //Populating the ser file with data
        
        try
          {
            FileOutputStream fileOut = new FileOutputStream("Movies.ser",true);
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(fileOut));
              for (int i = 0; i < dvdArr.size(); i++) {
                  out.writeObject(dvdArr.get(i));
              }
            
            out.close();
            fileOut.close();
            
            //System.out.println("Serialized data is saved in Movies.ser");
            
          }catch(IOException z)
          {
              z.printStackTrace();
          }
        
        //Reading from the ser file
        DVD aa = new DVD();
        
        try {
            FileInputStream fileIn = new FileInputStream("Movies.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            aa = (DVD) in.readObject();
            
            in.close();
            fileIn.close();
            
        } catch (IOException ia) {
            ia.printStackTrace();
            return;
        } catch(ClassNotFoundException ca) {
            System.out.println("Class not found");
            ca.printStackTrace();
            return;
        }
        //Test to make sure its working
        System.out.println(aa.toString());
        
        //Creating the customer array
        
        ArrayList<Customer> custArr = new ArrayList();
        
        custArr.add(new Customer(450,"Luke","Atmyass","0834561231",80,true));
        custArr.add(new Customer(600,"Chris.P","Bacon","0845682315",100.50,false));
        custArr.add(new Customer(300,"Justin","Case","0847878787",55.50,true));
        custArr.add(new Customer(550,"Ann","Chovey","08245698723",55,true));
        custArr.add(new Customer(425,"Hugh","deMann","0762189898",25,true));
        custArr.add(new Customer(325,"Ben","Dover","0735218968",60,true));
        custArr.add(new Customer(225,"Brighton","Early","0845623258",30.50,true));
        custArr.add(new Customer(125,"Hugo","First","0842323232",60,true));
        custArr.add(new Customer(950,"Dane","Geruss","0745252121",150,true));
        custArr.add(new Customer(900,"Allie","Grater","0834521874",20.50,true));
        custArr.add(new Customer(850,"Al","Kaholic","0835218989",75,true));
        custArr.add(new Customer(800,"Marsha","Mellow","0735468974",90,true));
        custArr.add(new Customer(750,"Constance","Noring","0745689231",45,true));
        custArr.add(new Customer(700,"Stu","Padassol","0764512350",20.50,false));
        custArr.add(new Customer(650,"Sue","Permann","0845689235",55,true));
        custArr.add(new Customer(400,"Isabelle","Ringing","0825623222",68.50,true));
        custArr.add(new Customer(500,"Mike","Rohsopht","0741234568",100,true));
        custArr.add(new Customer(100,"Eileen","Sideways","0845623122",8,true));
        custArr.add(new Customer(200,"Ima","Stewpidas","0752359852",40,true));
        custArr.add(new Customer(350,"Ivana.B","Withew","0827878989",90,true));
        
        //Populating the Customer ser file with data
        
        try
          {
            FileOutputStream fileOutCustomer = new FileOutputStream("Customers.ser",true);
            ObjectOutputStream outCustomer = new ObjectOutputStream(new BufferedOutputStream(fileOutCustomer));
              for (int i = 0; i < custArr.size(); i++) {
                  outCustomer.writeObject(custArr.get(i));
              }
            
            outCustomer.close();
            fileOutCustomer.close();
            
            //System.out.println("Serialized data is saved in Customers.ser");
            
          }catch(IOException z)
          {
              z.printStackTrace();
          }
        
        //Reading from the ser file
        Customer bb = new Customer();
        
        try {
            FileInputStream fileInCustomer = new FileInputStream("Customers.ser");
            ObjectInputStream inCustomer = new ObjectInputStream(fileInCustomer);
            bb = (Customer) inCustomer.readObject();
            
            inCustomer.close();
            fileInCustomer.close();
            
        } catch (IOException ia) {
            ia.printStackTrace();
            return;
        } catch(ClassNotFoundException ca) {
            System.out.println("Class not found");
            ca.printStackTrace();
            return;
        }
        //Test to make sure its working
        System.out.println(bb.toString());
        
    }
    
        
    
}
