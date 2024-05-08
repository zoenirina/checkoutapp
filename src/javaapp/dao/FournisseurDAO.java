/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.dao;

import java.sql.Connection;

/**
 *
 * @author ASUS
 */
public class FournisseurDAO extends ClientDAO {
    private static String SQL_INSERT = "INSERT INTO fournisseurs (nom, prenom, email1, email2, tel1, tel2, tel3, adresse, NIF, stat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static String SQL_SELECT = "SELECT * FROM fournisseurs ";
    private static String SQL_UPDATE = "UPDATE fournisseurs SET nom = ?, prenom = ?, email1 = ?, email2 = ?, tel1 = ?, tel2 = ?, tel3 = ?, adresse = ?, NIF = ?, stat = ? WHERE id = ?";
    private static String SQL_DELETE = "DELETE FROM fournisseurs WHERE id = ?";
    private static String SQL_FIND = SQL_SELECT+" WHERE id=? ";
    private static String SQL_FILTER = SQL_SELECT+" WHERE nom like ? OR prenom like ?";
   
    public FournisseurDAO(Connection conn) {
        super(conn);
    }
    
}
