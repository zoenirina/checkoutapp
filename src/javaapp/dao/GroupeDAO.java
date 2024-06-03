package javaapp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.Groupe;
import javaapp.factory.DAOFactory;

public class GroupeDAO extends DAO<Groupe> {
    private final DroitAccesDAO droitAccesDAO;
    
    public GroupeDAO(Connection conn) {
        super(conn);
        droitAccesDAO = DAOFactory.getDroitAccesDAO();
    }

    @Override
    public List<Groupe> select() {
        List<Groupe> groupes = new ArrayList<>();
        String query = "SELECT * FROM groupe";
        try {
            stm = conn.prepareStatement(query);
            result = stm.executeQuery();
            while (result.next()) {
                groupes.add(getGroupe(result));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GroupeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return groupes;
    }

    @Override
    public boolean create(Groupe groupe) {
        int affectedRow= 0;
        String query = "INSERT INTO groupe (nomGroupe, description, idDroit) VALUES (?, ?, ?)";
        try {
            stm = conn.prepareStatement(query);
            stm.setString(1, groupe.getNomGroupe());
            stm.setString(2, groupe.getDescription());
            stm.setInt(3, groupe.getIdDroit());
            affectedRow = stm.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(GroupeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedRow != 0;
    }

    @Override
    public boolean delete(Groupe groupe) {
        String query = "DELETE FROM groupe WHERE idGroupe = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setInt(1, groupe.getIdGroupe());
            int rowsDeleted = stm.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException ex) {
            Logger.getLogger(GroupeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean update(Groupe groupe) {
        String query = "UPDATE groupe SET nomGroupe = ?, description = ?, idDroit = ? WHERE idGroupe = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setString(1, groupe.getNomGroupe());
            stm.setString(2, groupe.getDescription());
            stm.setInt(3, groupe.getIdDroit());
            stm.setInt(4, groupe.getIdGroupe());
            int rowsUpdated = stm.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(GroupeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Groupe find(int id) {
        String query = "SELECT * FROM groupe WHERE idGroupe = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            result = stm.executeQuery();
                if (result.next()) {
                    Groupe groupe = getGroupe(result);
                    return groupe;
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(GroupeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Groupe find(String nomGroupe) {
        String query = "SELECT * FROM groupe WHERE nomGroupe = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setString(1, nomGroupe);
            result = stm.executeQuery();
                if (result.next()) {
                    Groupe groupe = getGroupe(result);
                    return groupe;
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(GroupeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Groupe getGroupe(ResultSet result) throws SQLException{
        Groupe groupe = new Groupe();
        groupe.setIdGroupe(result.getInt("idGroupe"));
        groupe.setNomGroupe(result.getString("nomGroupe"));
        groupe.setDescription(result.getString("description"));
        groupe.setIdDroit(result.getInt("idDroit"));
        groupe.setDroitAcces( droitAccesDAO.find( result.getInt("idDroit") ));
        return groupe;
    }
}
