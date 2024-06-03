
package javaapp.swing;
 import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBconnection {
   private static  Connection conn = null;
   private static String url = "jdbc:sqlite:caisse.db";
//   private static Connection connect;
   
 private DBconnection(){
  try {
            Class.forName("org.sqlite.JDBC");
         try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            Logger.getLogger(DBconnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBconnection.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
 public static Connection getInstance(){
    if(conn == null){
      new DBconnection();
    }
    return conn;   
  } 
 
 public static void close() throws SQLException{
 conn.close();
 }
}