/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.Magasin;
import javaapp.factory.DAOFactory;

/**
 *
 * @author ZOENIRINA
 */
public class MagasinDAO extends DAO<Magasin> {
    
   final static String SQL_INSERT = "INSERT INTO magasin (refMagasin, labelle, adresse, capacite, idResponsable) VALUES (?, ?, ?, ?, ?)";
    final static String SQL_SELECT = "SELECT * FROM magasin ";
    final static String SQL_UPDATE = "UPDATE magasin SET refMagasin = ?, labelle = ?, adresse = ?, capacite = ?, idResponsable = ? WHERE id = ?";
    final static String SQL_DELETE = "DELETE FROM magasin WHERE id = ?";
    final static String SQL_FIND = SQL_SELECT + " WHERE id = ? ";
    final static String SQL_WHERE_NOM = SQL_SELECT + " WHERE labelle like ?";
    final static String SQL_WHERE_REF = SQL_SELECT + " WHERE refMagasin = ? ";
    private final ProfileDAO profileDAO;

    public MagasinDAO(Connection conn) {
    super(conn);    
    profileDAO = DAOFactory.getProfileDAO();
    
    }

    @Override
    public List<Magasin> select() {
     List<Magasin> magasins = new ArrayList<>();
        try {
            stm = conn.prepareStatement(SQL_SELECT);
            result = stm.executeQuery(); 

            while (result.next()) {
                magasins.add( map(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRessources();
        }
        return magasins;
    }

    @Override
    public boolean create(Magasin magasin) {
    try { stm = conn.prepareStatement(SQL_INSERT);
            stm.setString(1, magasin.getRefMagasin());
            stm.setString(2, magasin.getLabelle());
            stm.setString(3, magasin.getAdresse());
            stm.setInt(4, magasin.getCapacite());
            stm.setInt(5, magasin.getIdResponsable());

            int rowsInserted = stm.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Magasin magasin) {
      try {
          stm = conn.prepareStatement(SQL_DELETE);
            stm.setInt(1, magasin.getId());

            int rowsDeleted = stm.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Magasin magasin) {
    try {
        stm = conn.prepareStatement(SQL_UPDATE);
            stm.setString(1, magasin.getRefMagasin());
            stm.setString(2, magasin.getLabelle());
            stm.setString(3, magasin.getAdresse());
            stm.setInt(4, magasin.getCapacite());
            stm.setInt(5, magasin.getIdResponsable());
            stm.setInt(6, magasin.getId());

            int rowsUpdated = stm.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeRessources();
        }
    }

    @Override
    public Magasin find(int id) {
        Magasin magasin = null;
        try {
            stm = conn.prepareStatement(SQL_FIND);
            stm.setInt(1, id);
            result = stm.executeQuery();
                if (result.next()) {
                    magasin = map(result);
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRessources();
        }
        return magasin;
     
    }
    
    public Magasin findByRef(String refMagasin) {
        Magasin magasin = null;
        try {
            stm = conn.prepareStatement(SQL_WHERE_REF);
            stm.setString(1, refMagasin);
            result = stm.executeQuery();
                if (result.next()) {
                    magasin = map(result);
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRessources();
        }
        return magasin;
     
    }
     public void closeRessources(){
            try {
                if(stm != null)stm.close();
//                if(conn != null)conn.close();
                if(result != null)result.close();
            } catch (SQLException ex) {
                Logger.getLogger(MagasinDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
     
     private Magasin map(ResultSet result) throws SQLException{
        Magasin magasin = new Magasin(
            result.getInt("id"),
            result.getString("refMagasin"),
            result.getString("labelle"),
            result.getString("adresse"),
            result.getInt("capacite"),
            result.getInt("idResponsable")
        );
        magasin.setProfile(profileDAO.find( result.getInt("id") ));
       return magasin;
     }
    
}
