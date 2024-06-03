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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.CommandeC;
import javaapp.component.DateConverter;
import javaapp.factory.DAOFactory;

/**
 *
 * @author ZOENIRINA
 */
public class CommandeCDAO extends DAO < CommandeC > {

    final static String SQL_INSERT = "INSERT INTO commandes (dateCommande, refCommande, idClient, quantite, montant, devise, idCreateur, description, pj, avoirFacture) VALUES (?, ?, ?, ?, ?, (SELECT devise FROM config WHERE idConfig=1),(SELECT idUtilisateur from utilisateur where status = 1), ?, ?, ?)";
    final static String SQL_SELECT = "SELECT * FROM commandes ";
//    final static String SQL_UPDATE = "UPDATE commandes SET dateCommande = ?, prenom = ?, email1 = ?, email2 = ?, tel1 = ?, tel2 = ?, tel3 = ?, adresse = ?, NIF = ?, stat = ? WHERE id = ?";
    final static String SQL_DELETE = "DELETE FROM commandes WHERE id = ?";
    final static String SQL_FIND = SQL_SELECT+" WHERE id = ? ";
    final static String SQL_FIND_REF = SQL_SELECT+" WHERE refCommande=? ";
    final static String SQL_FIND_BETWEEN_DATES = SQL_SELECT + " WHERE dateCommande BETWEEN ? AND ?";
    final static String SQL_FIND_BY_DATE = SQL_SELECT + " WHERE dateCommande = ?";
final static String SQL_ORDER = " ORDER BY dateCommande DESC";
    private int generatedKey=0;
    private final PersonneDAO clientDAO;
    private final CommandeDetailDAO comDetDAO;
    
    public CommandeCDAO (Connection conn) {
        super(conn);
        clientDAO = DAOFactory.getClientDAO();
        comDetDAO =  DAOFactory.getCommandeDetailDAO();
    }

    @Override
    public List<CommandeC> select () {
        List<CommandeC> comList = new ArrayList<>();
        try {
	stm = conn.prepareStatement(SQL_SELECT + SQL_ORDER );
	result = stm.executeQuery();
        
	while(result.next()) {
            CommandeC comm = new CommandeC();
            
            comm.setId(result.getInt("id"));
            comm.setIdClient(result.getInt("idClient"));
            comm.setIdCreateur(result.getInt("idCreateur"));
            comm.setMontant(result.getFloat("montant"));
            comm.setQuantite(result.getInt("quantite"));
            comm.setRefCommande(result.getString("refCommande"));
            comm.setDateCommande(result.getString("dateCommande"));
            comm.setDescription(result.getString("description"));
            comm.setStatus(result.getString("status"));
            comm.setAvoirFacture(result.getString("avoirFacture"));
            comm.setClient(clientDAO.find(result.getInt("idClient")));
            comList.add(comm);
        }
       
	}catch(SQLException ex) {
	    Logger.getLogger(CommandeCDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            closeResources();
        }
        return comList;    
    }

    @Override
    public boolean create(CommandeC commande) {
        int rowsAffected = 0;
    try {
            stm = conn.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1, commande.getDateCommande());
            stm.setString(2, commande.getRefCommande());// refCommande
            stm.setInt(3, commande.getIdClient());
            stm.setInt(4, commande.getQuantite());
//            pstmt.setFloat(5, calculerMontantTotal() + ((Number) lbl_fraisLivraison.getValue()).floatValue());
            stm.setFloat(5, commande.getMontant());
            stm.setString(6, commande.getDescription());
            stm.setString(7, commande.getPj());
            stm.setString(8, commande.getAvoirFacture());
            rowsAffected = stm.executeUpdate();
           
           result = stm.getGeneratedKeys();
            if (result.next()) {
                setGeneratedKey(result.getInt(1)) ;
                if(commande.getRefCommande().trim().isEmpty()){
                CommandeC comm = this.find(getGeneratedKey());
                comm.setRefCommande("BC-"+DateConverter.getCurrentDate()+"C"+getGeneratedKey());
                this.update(comm); 
                }
                
                System.out.println("Commande ajoutée avec succès !");
            } else {
                System.out.println("Erreur lors de la récupération de l'ID généré.");
            }
          
        }catch(NumberFormatException | SQLException e){
        } finally {
            closeResources();
        }
        return rowsAffected != 0;
    }

    @Override
    public boolean delete(CommandeC comm) {
        int rowAffected=0;
        try {
	stm = conn.prepareStatement(SQL_DELETE);
        stm.setInt(1, comm.getId());
	rowAffected = stm.executeUpdate();
        }catch(SQLException ex) {
	    Logger.getLogger(CommandeCDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            closeResources();
        }
        return rowAffected != 0; 
    }

    @Override
public boolean update(CommandeC commande) {
    int rowsAffected = 0;
    try {
        String SQL_UPDATE = "UPDATE commandes SET dateCommande = ?, refCommande = ?, idClient = ?, quantite = ?, montant = ?, idCreateur = ?, description = ?, pj = ?, avoirFacture= ? , status = ? WHERE id = ?";
        stm = conn.prepareStatement(SQL_UPDATE);
        stm.setString(1, commande.getDateCommande());
        stm.setString(2, commande.getRefCommande());
        stm.setInt(3, commande.getIdClient());
        stm.setInt(4, commande.getQuantite());
        stm.setFloat(5, commande.getMontant());
        stm.setInt(6, commande.getIdCreateur());
        stm.setString(7, commande.getDescription());
        stm.setString(8, commande.getPj());
        stm.setString(9, commande.getAvoirFacture());
        stm.setString(10, commande.getStatus());
        stm.setInt(11, commande.getId());

        rowsAffected = stm.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
       closeResources();
    }
    return rowsAffected != 0;
}

@Override
    public CommandeC find(int id) {
        CommandeC comm = new CommandeC();
        try {
            stm = conn.prepareStatement(SQL_FIND);
            stm.setInt(1, id);
            result = stm.executeQuery();

            while (result.next()) {
                comm = mapResultSetToCommande(result);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CommandeCDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return comm;
    }
    
    public int getCountToValidate(){
        int count = 0;
        try {
            stm = conn.prepareStatement("SELECT COUNT(id) AS count FROM commandes WHERE status NOT IN ('Clôturée','Livrée','Annulée')");
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
    
    public CommandeC find(String refCommande) {
        CommandeC comm = new CommandeC();
        try {
	stm = conn.prepareStatement(SQL_FIND_REF);
        stm.setString(1, refCommande);
	result = stm.executeQuery();
        
	while(result.next()) {
            comm = mapResultSetToCommande(result);
        }
        
	}catch(SQLException ex) {
	    Logger.getLogger(CommandeCDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            closeResources();
        }
        return comm; 
    }

        public List<CommandeC> findBetweenDates(String startDate, String endDate) {
        List<CommandeC> comList = new ArrayList<>();
        try {
            stm = conn.prepareStatement(SQL_FIND_BETWEEN_DATES + SQL_ORDER);
            stm.setString(1,  startDate);
            stm.setString(2,  endDate);
            result = stm.executeQuery();

            while (result.next()) {
                CommandeC comm = mapResultSetToCommande(result);
                comList.add(comm);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeCDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return comList;
    }

    public List<CommandeC> findByDate(Date date) {
        List<CommandeC> comList = new ArrayList<>();
        try {
            stm = conn.prepareStatement(SQL_FIND_BY_DATE + SQL_ORDER);
            stm.setDate(1, (java.sql.Date) date);
            result = stm.executeQuery();

            while (result.next()) {
                CommandeC comm = mapResultSetToCommande(result);
                comList.add(comm);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeCDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return comList;
    }
    
        public List<CommandeC> filter(String refCommande) {
        List<CommandeC> comList = new ArrayList<>();
        try {
            stm = conn.prepareStatement(SQL_SELECT+" WHERE refCommande like '% "+refCommande+" %'" + SQL_ORDER);
            result = stm.executeQuery();

            while (result.next()) {
                CommandeC comm = mapResultSetToCommande(result);
                comList.add(comm);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeCDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return comList;
    }

    // Méthode utilitaire pour mapper le résultat de la requête SQL à un objet CommandeC
    private CommandeC mapResultSetToCommande(ResultSet result) throws SQLException {
        CommandeC comm = new CommandeC();
        comm.setId(result.getInt("id"));
        comm.setIdClient(result.getInt("idClient"));
        comm.setIdCreateur(result.getInt("idCreateur"));
        comm.setMontant(result.getFloat("montant"));
        comm.setQuantite(result.getInt("quantite"));
        comm.setRefCommande(result.getString("refCommande"));
        comm.setDateCommande(result.getString("dateCommande"));
        comm.setDescription(result.getString("description"));
        comm.setStatus(result.getString("status"));
        comm.setMontantHT(3000.0f);
        comm.setMontantTTC(1000.0f);
        comm.setMontantHT(result.getFloat("montantHT"));
        comm.setMontantTTC(result.getFloat("montantTTC"));
        comm.setClient(clientDAO.find(result.getInt("idClient")));
        comm.setCommandeDetails(comDetDAO.select(result.getInt("id")));
        
        return comm;
    }
    
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
//                if(conn != null)conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CommandeCDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
}
