/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.Reception;
import javaapp.component.DateConverter;
import javaapp.factory.DAOFactory;

public class ReceptionDAO extends DAO<Reception> {

    final static String SQL_INSERT = "INSERT INTO receptions (dateReception, refReception, idCommande, idFournisseur, montant, devise, description, frais, status) "
            + "VALUES (?, ?, ?, (SELECT idFournisseur from commandesFournisseur WHERE id = ?) , ?, (SELECT devise FROM config WHERE idConfig=1), ?, ?, ?)";
    final static String SQL_SELECT = "SELECT r.* FROM receptions r LEFT JOIN commandesFournisseur cf ON r.idCommande = cf.id INNER JOIN fournisseurs f ON r.idFournisseur = f.id";
    final static String SQL_UPDATE = "UPDATE receptions SET dateReception = ?, refReception = ?, idCommande = ?, idFournisseur = ?, montant = ?, devise = ?, description = ?, frais = ?, status=?  WHERE id = ?";
    final static String SQL_DELETE = "DELETE FROM receptions WHERE id = ?";
    final static String SQL_FIND = SQL_SELECT + " WHERE r.id = ?";
    final static String SQL_FIND_BY_REF = SQL_SELECT + " WHERE r.refReception = ?";
    final static String SQL_FIND_COMMANDE = SQL_SELECT + " WHERE r.idCommande = ?";
    final static String SQL_WHERE_NOM = SQL_SELECT + " WHERE nom LIKE ? OR prenom LIKE ?";
    final static String SQL_ORDER = " ORDER BY r.dateReception ";
    private int generatedKey = 0;
    private ReceptionDetailDAO recepDetDAO;
    public ReceptionDAO(Connection conn) {
        super(conn);
        recepDetDAO = DAOFactory.getReceptionDetailDAO();
    }

    @Override
    public List<Reception> select() {
        List<Reception> receptions = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT+SQL_ORDER);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Reception reception = displayInformation(resultSet);
                receptions.add(reception);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return receptions;
    }

    @Override
    public boolean create(Reception reception) {
        int affectedRow = 0;
        try {
            stm = conn.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1, reception.getDateReception());
            stm.setString(2, reception.getRefReception());
            stm.setInt(3, reception.getIdCommande());
            stm.setInt(4, reception.getIdCommande());
            stm.setFloat(5, reception.getMontant());
            stm.setString(6, reception.getDescription());
            stm.setFloat(7, reception.getFrais());
            stm.setString(8, reception.getStatus());
            affectedRow = stm.executeUpdate();
            
            result = stm.getGeneratedKeys();
            if (result.next()) {
                setGeneratedKey(result.getInt(1));
                Reception rec = this.find(getGeneratedKey());
                rec.setRefReception("BR-" +DateConverter.getCurrentDate()+ getGeneratedKey());
                this.update(rec);
                
                System.out.println("Réception ajoutée avec succès !");
            } else {
                System.out.println("Erreur lors de la récupération de l'ID généré.");
            }
            
            return affectedRow != 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    @Override
    public boolean delete(Reception reception) {
        try (PreparedStatement statement = conn.prepareStatement(SQL_DELETE)) {
            statement.setInt(1, reception.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }
    
    @Override
    public boolean update(Reception reception) {
        try {
            stm = conn.prepareStatement(SQL_UPDATE);
            stm.setString(1, reception.getDateReception());
            stm.setString(2, reception.getRefReception());
            stm.setInt(3, reception.getIdCommande());
            stm.setInt(4, reception.getIdFournisseur());
            stm.setFloat(5, reception.getMontant());
            stm.setString(6, reception.getDevise());
            stm.setString(7, reception.getDescription());
            stm.setFloat(8, reception.getFrais());
            stm.setString(9, reception.getStatus());
            stm.setInt(10, reception.getId());
            
            return stm.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    @Override
    public Reception find(int id) {
        Reception reception = null;
        try {
            stm = conn.prepareStatement(SQL_FIND);
            stm.setInt(1, id);
            result = stm.executeQuery();
                if (result.next()) {
                    reception = displayInformation(result);
                    reception.setReceptionDetails(recepDetDAO.select(id));
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            closeResources();
        }
        return reception;
    }
    
    public List<Reception> selectWhere(String query) {
        List<Reception> receptions = new ArrayList<>();
        try {
            stm = conn.prepareStatement(query);
            result = stm.executeQuery();
            while (result.next()) {
                Reception reception = displayInformation(result);
                receptions.add(reception);
            }
            return receptions;
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return null;
    }
     public List<Reception> selectEnRetard(String query) {
        return selectWhere (SQL_SELECT+" WHERE  r.status = 'En attente' AND r.dateReception  < CURRENT_TIMESTAMP "+ query + SQL_ORDER);
    } 
    public List<Reception> selectPretARecevoir(String query) {
        return selectWhere (SQL_SELECT+" where r.status = 'Prêt à recevoir' "+ query + SQL_ORDER);
    } 
    
    public List<Reception> selectRecu(String query) {
        return selectWhere (SQL_SELECT+" where r.status = 'Réçu' "+query + SQL_ORDER);
    } 
    
    public List<Reception> selectAnnule(String query) {
        return selectWhere (SQL_SELECT+"  WHERE r.status = 'Annulé'  "+query + SQL_ORDER);
    } 
    
    public List<Reception> select(String query) {
        return selectWhere(SQL_SELECT + query + SQL_ORDER);
    } 
    
    public Reception find(String refReception) {
        Reception reception = null;
        try {
            stm = conn.prepareStatement(SQL_FIND_BY_REF);
            stm.setString(1, refReception);
            result = stm.executeQuery();
                if (result.next()) {
                    reception = displayInformation(result);
                    reception.setReceptionDetails(recepDetDAO.select(result.getInt("id")));
               }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return reception;
    }
    
    public Reception findByCommande(int idCommande) {
        Reception reception = new Reception();
        try {
            stm = conn.prepareStatement(SQL_FIND_COMMANDE);
            stm.setInt(1, idCommande);
            result = stm.executeQuery();
            if (result.next()) {
                reception = displayInformation(result);
            }
            return reception;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Reception displayInformation(ResultSet resultSet) throws SQLException {
        Reception reception = new Reception();
                
        reception.setId(resultSet.getInt("id"));
        reception.setDateReception(resultSet.getString("dateReception"));
        reception.setDescription(resultSet.getString("description"));
        reception.setDevise(resultSet.getString("devise"));
        reception.setRefReception(resultSet.getString("refReception"));
        reception.setFrais(resultSet.getFloat("frais"));
        reception.setIdCommande(resultSet.getInt("idCommande"));
        reception.setMontant(resultSet.getFloat("montant"));
        reception.setIdFournisseur(resultSet.getInt("idFournisseur"));
        reception.setFournisseur(DAOFactory.getFournisseurDAO().find(resultSet.getInt("idFournisseur")));
        reception.setStatus(resultSet.getString("status"));
        reception.setTypeLivraison(resultSet.getString("typeLivraison"));
//        reception.setProduit(DAOFactory.getProduitDAO().find(resultSet.getInt("idProduit")));
        return reception;
    }

    public int getGeneratedKey() {
        return generatedKey;
    }

    public void setGeneratedKey(int generatedKey) {
        this.generatedKey = generatedKey;
    }
    
    private void closeResources() {
        try {
            if (stm != null) stm.close();
            if (result != null) result.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      public int getCountToValidate(){
        int count = 0;
        try {
            stm = conn.prepareStatement("SELECT COUNT(id) AS count FROM receptions WHERE status NOT IN ('Annulée','Validée')");
            result = stm.executeQuery();

            if (result.next()) {
                count = result.getInt("count");
            }

        } catch (SQLException ex) {
            Logger.getLogger(CommandeCDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return count;
    }
}
