
package javaapp.swing;
 import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBconnection {
   private static  Connection conn = null;
   private static String url = "jdbc:sqlite:/C://Users/ASUS/Documents/NetBeansProjects/JavaApp/caisse.db";
   
 public DBconnection(){
  
 }
 
  public static void main (String[] args){
      
         try {
             Class.forName("org.sqlite.JDBC");   
 System.out.print("driver chargé!");     
         } catch (ClassNotFoundException ex) {
                System.out.print("driver non chargé!");
             Logger.getLogger(DBconnection.class.getName()).log(Level.SEVERE, null, ex);
         }
            try {       
      conn= DriverManager.getConnection(url);
      System.out.print("connexion effectuée");
     }catch (SQLException e) {
System.out.print("echec de la connexion");
    } 
    
 }
}