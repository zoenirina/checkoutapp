/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.dao;

import java.sql.Connection;
import java.util.List;
import javaapp.bean.Magasin;

/**
 *
 * @author ZOENIRINA
 */
public class MagasinDAO extends DAO<Magasin> {
    
    final static String SQL_INSERT = "INSERT INTO clients (nom, prenom, email1, email2, tel1, tel2, tel3, adresse, NIF, stat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    final static String SQL_SELECT = "SELECT * FROM clients ";
    final static String SQL_UPDATE = "UPDATE clients SET nom = ?, prenom = ?, email1 = ?, email2 = ?, tel1 = ?, tel2 = ?, tel3 = ?, adresse = ?, NIF = ?, stat = ? WHERE id = ?";
    final static String SQL_DELETE = "DELETE FROM clients WHERE id = ?";
    final static String SQL_FIND = SQL_SELECT+" WHERE id=? ";
    final static String SQL_WHERE_NOM = SQL_SELECT+" WHERE nom like ? OR prenom like ?";
    
    public MagasinDAO(Connection conn) {
    super(conn);    
    }

    @Override
    public List<Magasin> select() {
        return null;
       
    }

    @Override
    public boolean create(Magasin magasin) {
        return false;
    
    }

    @Override
    public boolean delete(Magasin magasin) {
        return false;
    
    }

    @Override
    public boolean update(Magasin magasin) {
        return false;
    
    }

    @Override
    public Magasin find(int id) {
        return null;
     
    }
    
}
