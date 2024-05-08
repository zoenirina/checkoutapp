/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.dao;

import java.sql.Connection;
import java.util.List;
import javaapp.bean.SortieCaisse;

/**
 *
 * @author ASUS
 */
public class EntreeCaisseDAO  extends DAO<SortieCaisse>{

    final static String SQL_INSERT = "INSERT INTO clients (nom, prenom, email1, email2, tel1, tel2, tel3, adresse, NIF, stat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    final static String SQL_SELECT = "SELECT * FROM clients ";
    final static String SQL_UPDATE = "UPDATE clients SET nom = ?, prenom = ?, email1 = ?, email2 = ?, tel1 = ?, tel2 = ?, tel3 = ?, adresse = ?, NIF = ?, stat = ? WHERE id = ?";
    final static String SQL_DELETE = "DELETE FROM clients WHERE id = ?";
    final static String SQL_FIND = SQL_SELECT+" WHERE id=? ";
    final static String SQL_WHERE_NOM = SQL_SELECT+" WHERE nom like ? OR prenom like ?";
    
    public EntreeCaisseDAO(Connection conn) {
        super(conn);
    }

    @Override
    public List<SortieCaisse> select() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(SortieCaisse obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(SortieCaisse obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(SortieCaisse obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SortieCaisse find(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
