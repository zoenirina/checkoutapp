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
import javaapp.bean.CommandeF;
import javaapp.component.DateConverter;
import javaapp.factory.DAOFactory;

/**
 *
 * @author ZOENIRINA
 */
public class CommandeFDAO extends DAO < CommandeF > {

    final static String SQL_INSERT = "INSERT INTO commandesFournisseur (dateCommande, refCommande, idFournisseur, quantite, montant, devise, idCreateur, description, pj, avoirFacture) VALUES (?, ?, ?, ?, ?, (SELECT devise FROM config WHERE idConfig=1),(SELECT idUtilisateur from utilisateur where status = 1), ?, ?, ?)";
    final static String SQL_SELECT = "SELECT * FROM commandesFournisseur ";
//    final static String SQL_UPDATE = "UPDATE commandesFournisseur SET dateCommande = ?, prenom = ?, email1 = ?, email2 = ?, tel1 = ?, tel2 = ?, tel3 = ?, adresse = ?, NIF = ?, stat = ? WHERE id = ?";
    final static String SQL_DELETE = "DELETE FROM commandesFournisseur WHERE id = ?";
    final static String SQL_FIND = SQL_SELECT+" WHERE id = ? ";
    final static String SQL_FIND_REF = SQL_SELECT+" WHERE refCommande=? ";
    final static String SQL_FIND_BETWEEN_DATES = SQL_SELECT + " WHERE dateCommande BETWEEN ? AND ?";
    final static String SQL_FIND_BY_DATE = SQL_SELECT + " WHERE dateCommande = ?";
    final static String SQL_ORDER = " ORDER BY dateCommande";
    private int generatedKey=0;
    
    public CommandeFDAO (Connection conn) {
        super(conn);
    }

    @Override
    public List<CommandeF> select () {
        List<CommandeF> comList = new ArrayList<>();
        try {
	stm = conn.prepareStatement(SQL_SELECT+SQL_ORDER);
	result = stm.executeQuery();
        
	while(result.next()) {
            CommandeF comm = new CommandeF();
            
            comm.setId(result.getInt("id"));
            comm.setIdFournisseur(result.getInt("idFournisseur"));
            comm.setIdCreateur(result.getInt("idCreateur"));
            comm.setMontant(result.getFloat("montant"));
            comm.setQuantite(result.getInt("quantite"));
            comm.setRefCommande(result.getString("refCommande"));
            comm.setDateCommande(result.getString("dateCommande"));
            comm.setDescription(result.getString("description"));
            comm.setStatus(result.getString("status"));
            
            PersonneDAO persDAO = DAOFactory.getClientDAO();
            comm.setFournisseur(persDAO.find(result.getInt("idFournisseur")));
            comList.add(comm);
        }
       
	}catch(SQLException ex) {
	    Logger.getLogger(CommandeFDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            closeResources();
        }
        return comList;    
    }

    @Override
    public boolean create(CommandeF commande) {
        int rowsAffected = 0;
    try {
            stm = conn.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1, commande.getDateCommande());
            stm.setString(2, commande.getRefCommande());// refCommande
            stm.setInt(3, commande.getIdFournisseur());
            stm.setInt(4, commande.getQuantite());
//            pstmt.setFloat(5, calculerMontantTotal() + ((Number) lbl_fraisLivraison.getValue()).floatValue());
            stm.setFloat(5, commande.getMontant());
//            stm.setString(6, "Ariary");
            stm.setString(6, commande.getDescription());
            stm.setString(7, commande.getPj());
            stm.setString(8, commande.getAvoirFacture());
            rowsAffected = stm.executeUpdate();
            
            ResultSet res = stm.getGeneratedKeys();
            if (res.next()) {
                setGeneratedKey(res.getInt(1)) ;
                CommandeF comm = this.find(getGeneratedKey());
                comm.setRefCommande("BC-"+ DateConverter.getCurrentDate()+"F"+getGeneratedKey());
                this.update(comm);
                
                System.out.println("Commande ajoutée avec succès !");
            } else {
                System.out.println("Erreur lors de la récupération de l'ID généré.");
            }
        }catch(NumberFormatException | SQLException e){
        
        }
        return rowsAffected != 0;
    }

    @Override
    public boolean delete(CommandeF comm) {
        int rowAffected=0;
        try {
	stm = conn.prepareStatement(SQL_DELETE);
        stm.setInt(1, comm.getId());
	rowAffected = stm.executeUpdate();
        }catch(SQLException ex) {
	    Logger.getLogger(CommandeFDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            closeResources();
        }
        return rowAffected != 0; 
    }

    @Override
public boolean update(CommandeF commande) {
    int rowsAffected = 0;
    try {
        String SQL_UPDATE = "UPDATE commandesFournisseur SET dateCommande = ?, refCommande = ?, idFournisseur = ?, quantite = ?, montant = ?, idCreateur = ?, description = ?, pj = ?, avoirFacture= ? , status = ? WHERE id = ?";
        stm = conn.prepareStatement(SQL_UPDATE);
        stm.setString(1, commande.getDateCommande());
        stm.setString(2, commande.getRefCommande());
        stm.setInt(3, commande.getIdFournisseur());
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
    public CommandeF find(int id) {
        CommandeF comm = new CommandeF();
        try {
            stm = conn.prepareStatement(SQL_FIND);
            stm.setInt(1, id);
            result = stm.executeQuery();

            while (result.next()) {
                comm = mapResultSetToCommande(result);
//                comm.setId(result.getInt("id"));
//                comm.setIdClient(result.getInt("idClient"));
//                comm.setIdCreateur(result.getInt("idCreateur"));
//                comm.setMontant(result.getFloat("montant"));
//                comm.setQuantite(result.getInt("quantite"));
//                comm.setRefCommande(result.getString("refCommande"));
//                comm.setDateCommande(result.getString("dateCommande"));
//                comm.setDescription(result.getString("description"));
//
//                PersonneDAO persDAO = DAOFactory.getClientDAO();
//                comm.setClient(persDAO.find(result.getInt("idClient")));
////            comList.add(comm);
//
//                CommandeDetailDAO comDetDAO =  DAOFactory.getCommandeDetailDAO();
//                comm.setCommandeDetails(comDetDAO.select(result.getInt("id")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(CommandeFDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return comm;
    }
    
    public CommandeF find(String refCommande) {
        CommandeF comm = new CommandeF();
        try {
	stm = conn.prepareStatement(SQL_FIND_REF);
        stm.setString(1, refCommande);
	result = stm.executeQuery();
        
	while(result.next()) {
            comm = mapResultSetToCommande(result);
//            comm.setId(result.getInt("id"));
//            comm.setIdClient(result.getInt("idClient"));
//            comm.setIdCreateur(result.getInt("idCreateur"));
//            comm.setMontant(result.getFloat("montant"));
//            comm.setQuantite(result.getInt("quantite"));
//            comm.setRefCommande(result.getString("refCommande"));
//            comm.setDateCommande(result.getString("dateCommande"));
//            comm.setDescription(result.getString("description"));
//            
//            PersonneDAO persDAO = DAOFactory.getClientDAO();
//            comm.setClient(persDAO.find(result.getInt("idClient")));
//            
////            List<CommandeDetail> comDet= new ArrayList<>();
//            CommandeDetailDAO comDetDAO =  DAOFactory.getCommandeDetailDAO();
//            
//            comm.setCommandeDetails(comDetDAO.select(result.getInt("id")));
       
        }
        
	}catch(SQLException ex) {
	    Logger.getLogger(CommandeFDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            closeResources();
        }
        return comm; 
    }

        public List<CommandeF> findBetweenDates(String startDate, String endDate) {
        List<CommandeF> comList = new ArrayList<>();
        try {
            stm = conn.prepareStatement(SQL_FIND_BETWEEN_DATES+SQL_ORDER);
            stm.setString(1,  startDate);
            stm.setString(2,  endDate);
            result = stm.executeQuery();

            while (result.next()) {
                CommandeF comm = mapResultSetToCommande(result);
                comList.add(comm);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeFDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return comList;
    }

    public List<CommandeF> findByDate(Date date) {
        List<CommandeF> comList = new ArrayList<>();
        try {
            stm = conn.prepareStatement(SQL_FIND_BY_DATE+SQL_ORDER);
            stm.setDate(1, (java.sql.Date) date);
            result = stm.executeQuery();

            while (result.next()) {
                CommandeF comm = mapResultSetToCommande(result);
                comList.add(comm);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeFDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return comList;
    }
    
        public List<CommandeF> filter(String refCommande) {
        List<CommandeF> comList = new ArrayList<>();
        try {
            stm = conn.prepareStatement(SQL_SELECT+" WHERE refCommande like '% "+refCommande+" %'"+SQL_ORDER);
            result = stm.executeQuery();

            while (result.next()) {
                CommandeF comm = mapResultSetToCommande(result);
                comList.add(comm);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeFDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return comList;
    }

    // Méthode utilitaire pour mapper le résultat de la requête SQL à un objet CommandeC
    private CommandeF mapResultSetToCommande(ResultSet result) throws SQLException {
        CommandeF comm = new CommandeF();
        comm.setId(result.getInt("id"));
        comm.setIdFournisseur(result.getInt("idFournisseur"));
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
        
        PersonneDAO persDAO = DAOFactory.getClientDAO();
        comm.setFournisseur(persDAO.find(result.getInt("idFournisseur")));
        
//        List<CommandeDetail> comDet= new ArrayList<>();
        CommandeDetailFournisseurDAO comDetDAO =  DAOFactory.getCommandeDetailFournisseurDAO();
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
            } catch (SQLException ex) {
                Logger.getLogger(CommandeFDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
}
