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
import javaapp.bean.Livraison;
import javaapp.component.DateConverter;
import javaapp.factory.DAOFactory;

public class LivraisonDAO extends DAO<Livraison> {

    final static String SQL_INSERT = "INSERT INTO livraisons ( dateLivraison, refLivraison, idCommande, idClient, montant, devise, description, frais, status) VALUES (?, ?, ?, (SELECT idClient from commandes WHERE id = ?) , ?, (SELECT devise FROM config WHERE idConfig=1), ?, ?, ?)";
    final static String SQL_SELECT = "SELECT l.* FROM livraisons l left join commandes c on l.idCommande = c.id inner join clients cl on l.idClient = cl.id  ";
    final static String SQL_UPDATE = "UPDATE livraisons SET dateLivraison = ?, refLivraison = ?, idCommande = ?, idClient = ?, montant = ?, devise = ?, description = ?, frais = ?, status=? WHERE id = ?";
    final static String SQL_DELETE = "DELETE FROM livraisons WHERE id = ?";
    final static String SQL_FIND = SQL_SELECT + " WHERE l.id =?";
    final static String SQL_FIND_BY_REF = SQL_SELECT + " WHERE l.refLivraison =?";
    final static String SQL_FIND_COMMANDE = SQL_SELECT + " WHERE l.idCommande=?";
    final static String SQL_WHERE_NOM = SQL_SELECT + " WHERE nom LIKE ? OR prenom LIKE ?";
    final static String SQL_ORDER = " ORDER BY l.dateLivraison DESC";
    private int generatedKey=0;
    private final LivraisonDetailDAO livraisonDetDAO;
    private final PersonneDAO clientDAO;
    public LivraisonDAO(Connection conn) {
        super(conn);
        livraisonDetDAO = DAOFactory.getLivraisonDetailDAO();
        clientDAO = DAOFactory.getClientDAO();
    }

    @Override
    public List<Livraison> select() {
        List<Livraison> livraisons = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT + SQL_ORDER);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Livraison livraison = displayInformation(resultSet);
                livraisons.add(livraison);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livraisons;
    }

    @Override
    public boolean create(Livraison livraison) {
        int affectedRow = 0;
        try  {
            stm = conn.prepareStatement("INSERT INTO livraisons ( dateLivraison, refLivraison, idCommande, idClient, montant, devise, description, frais, status) VALUES (?, ?, ?, 1 , ?, (SELECT devise FROM config WHERE idConfig=1), ?, ?, ?)");
            stm.setString(1, livraison.getDateLivraison());
            stm.setString(2, livraison.getRefLivraison());
            stm.setInt(3, livraison.getIdCommande());
//            stm.setInt(4, livraison.getIdCommande());
//            stm.setFloat(5, livraison.getMontant());
////            stm.setString(6, livraison.getDevise());
//            stm.setString(6, livraison.getDescription());
//            stm.setFloat(7, livraison.getFrais());
//            stm.setString(8, livraison.getStatus());
            stm.setFloat(4, livraison.getMontant());
//            stm.setString(6, livraison.getDevise());
            stm.setString(5, livraison.getDescription());
            stm.setFloat(6, livraison.getFrais());
            stm.setString(7, livraison.getStatus());
            affectedRow = stm.executeUpdate();
            
            result = stm.getGeneratedKeys();
            if (result.next()) {
                setGeneratedKey(result.getInt(1));
                Livraison liv = this.find(getGeneratedKey());
                liv.setRefLivraison("BL-"+DateConverter.getCurrentDate()+getGeneratedKey());
                liv.setIdClient(livraison.getIdClient());
                this.update(liv);
                
                System.out.println("Livraison ajoutée avec succès !");
            } else {
                System.out.println("Erreur lors de la récupération de l'ID généré.");
            }
            
            return affectedRow != 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Livraison livraison) {
        try (PreparedStatement statement = conn.prepareStatement(SQL_DELETE)) {
            statement.setInt(1, livraison.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

      
    
    @Override
    public boolean update(Livraison livraison) {
        try {
            stm = conn.prepareStatement(SQL_UPDATE);
            stm.setString(1, livraison.getDateLivraison());
            stm.setString(2, livraison.getRefLivraison());
            stm.setInt(3, livraison.getIdCommande());
            stm.setInt(4, livraison.getIdClient());
            stm.setFloat(5, livraison.getMontant());
            stm.setString(6, livraison.getDevise());
            stm.setString(7, livraison.getDescription());
            stm.setFloat(8, livraison.getFrais());
            stm.setString(9, livraison.getStatus());
            stm.setInt(10, livraison.getId());
            
            return stm.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Livraison find(int id) {
        Livraison livraison = null;
        try (PreparedStatement statement = conn.prepareStatement(SQL_FIND)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    livraison = displayInformation(resultSet);
                    livraison.setLivraisonDetails(livraisonDetDAO.select(id));
                   
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livraison;
    }
    
    public List<Livraison> selectWhere(String query) {
        List<Livraison> livraisons = new ArrayList<>();
        try {
	stm = conn.prepareStatement(query);
	result = stm.executeQuery();
        
	while(result.next()) {
            Livraison fac = displayInformation(result);
            livraisons.add(fac);
        }
       return livraisons;
	}catch(SQLException ex) {
	    Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
           closeResources();
        }
        return null;
//        return livraison;
    }

    public List<Livraison> selectEnRetard(String query) {
        return selectWhere (SQL_SELECT+" WHERE  l.status = 'En attente' AND l.dateLivraison  < CURRENT_TIMESTAMP "+ query + SQL_ORDER);
    } 
    public List<Livraison> selectEnAttente(String query) {
//        return selectWhere (SQL_SELECT+" WHERE l.dateLivraison > CURRENT_TIMESTAMP and l.status = 'En attente' "+ query + SQL_ORDER);
return selectWhere (SQL_SELECT+" WHERE l.status = 'En attente' "+ query + SQL_ORDER);
    } 
    
    public List<Livraison> selectLivre(String query) {
        return selectWhere (SQL_SELECT+" where l.status = 'Livrée' or l.status = 'Livré'  "+query + SQL_ORDER);
    } 
    
    public List<Livraison> selectAnnule(String query) {
        return selectWhere (SQL_SELECT+"  WHERE l.status = 'Annulée' or  l.status = 'Annulé' "+query + SQL_ORDER);
    } 
    public List<Livraison> select(String query) {
        return selectWhere (SQL_SELECT+ query + SQL_ORDER);
    } 
    public Livraison find(String refLivraison) {
        Livraison livraison = null;
        try {
            stm = conn.prepareStatement(SQL_FIND_BY_REF);
            stm.setString(1, refLivraison);
            result = stm.executeQuery();
                if (result.next()) {
                    livraison = displayInformation(result);
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livraison;
    }
    
    public Livraison findByCommande(int idCommande) {
        Livraison livraison = new Livraison();
        try {
            stm = conn.prepareStatement(SQL_FIND_COMMANDE);
            stm.setInt(1, idCommande);
            
            result = stm.executeQuery();
                if (result.next()) {
                    livraison = displayInformation(result);
                }
                return livraison;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//    public List<Livraison> findBetweenDates(String startDate, String endDate) {
//      return selectWhere (SQL_SELECT+ query + SQL_ORDER);
//    }
    public Livraison displayInformation(ResultSet resultSet) throws SQLException{
        Livraison livraison = new Livraison();
                
        livraison.setId(resultSet.getInt("id"));
        livraison.setDateLivraison(resultSet.getString("dateLivraison"));
        livraison.setDescription(resultSet.getString("description"));
        livraison.setDevise(resultSet.getString("devise"));
        livraison.setRefLivraison(resultSet.getString("refLivraison"));
        livraison.setFrais(resultSet.getFloat("frais"));
        livraison.setIdCommande(resultSet.getInt("idCommande"));
        livraison.setMontant(resultSet.getFloat("montant"));
        livraison.setIdClient(resultSet.getInt("idClient"));
        livraison.setClient(clientDAO.find(resultSet.getInt("idClient")));
        livraison.setStatus(resultSet.getString("status"));
        livraison.setTypeLivraison(resultSet.getString("typeLivraison"));
        return livraison;
    }

    /**
     * @return the generatedKey
     */
    public int getGeneratedKey() {
        return generatedKey;
    }

    public void setGeneratedKey(int generatedKey) {
        this.generatedKey = generatedKey;
    }
    
     private void closeResources() {
        try {
            if(stm != null)stm.close();
            if(result != null)result.close();
//            if(conn != null)conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CommandeCDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
      public int getCountToValidate(){
        int count = 0;
        try {
            stm = conn.prepareStatement("SELECT COUNT(id) AS count FROM livraisons WHERE status NOT IN ('Annulée','Validée')");
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
