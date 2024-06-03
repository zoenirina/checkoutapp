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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.Facture;
import javaapp.component.DateConverter;
import javaapp.factory.DAOFactory;

/**
 *
 * @author ZOENIRINA
 */
public class FactureDAO extends DAO<Facture>{
    protected String SQL_INSERT= null;//"INSERT INTO factures (dateFacture, refFacture, idLivraison, idClient, montant, devise, description) VALUES (?, ?, ?, ?, (SELECT SUM(ld.montant) + l.frais FROM   livraisonDetails ld INNER JOIN livraisons l ON ld.idLivraison = l.id WHERE  l.id = ?), (SELECT devise FROM config WHERE idConfig=1), ?)";
    protected String SQL_SELECT ="SELECT f.*, cl.* ,   f.montant - COALESCE(SUM(p.montantRecu), 0) AS resteAPayer , " +
"       CASE    WHEN COALESCE(SUM(p.montantRecu), 0) >= f.montant THEN 'Payée' WHEN COALESCE(SUM(p.montantRecu), 0) = 0 THEN 'Non payée'  ELSE 'Payée partiellement'  END AS etat FROM factures f " +
"   LEFT JOIN paiements p ON f.id = p.idFacture left join clients cl on f.idClient = cl.id ";
    protected String SQL_GROUP = "  GROUP BY f.id ";
    protected String SQL_ORDER = " ORDER BY f.dateFacture DESC";
    protected String SQL_UPDATE = "UPDATE factures SET dateFacture = ?, refFacture = ?, idLivraison = ?, idClient = ?,  montant = ?,  devise = ?,  description = ?,  pj = ? WHERE id = ?;";//"UPDATE factures SET dateFacture=?, refFacture=?, idLivraison=?, idClient=?, montant=?, devise=?, description=? WHERE id = ?";
//    protected String SQL_DELETE = "DELETE FROM facture WHERE id = ?";
    protected String SQL_FIND = SQL_SELECT+" WHERE f.id =? ";
    protected String SQL_FIND_REF= SQL_SELECT+" WHERE f.refFacture = ? ";
//    private String SQL_ORDER = " ORDER BY f.dateFacture";
    
    private int generatedKey=0;
//    private String TABLE_NAME = "";
    private final String SQL_FIND_IDLIV = SQL_SELECT+" WHERE f.idLivraison = ? ";
    private final PersonneDAO clientDAO;
    private final LivraisonDAO livDAO;
    
    public FactureDAO(Connection conn) {
        super(conn);
        livDAO = DAOFactory.getLivraisonDAO();
clientDAO = DAOFactory.getClientDAO(); 
                this.SQL_INSERT = "INSERT INTO factures (dateFacture, refFacture, idLivraison, idClient, montant, devise, description, pj) "
                        + "VALUES (?, ?, ?, ?,?, (SELECT devise FROM config WHERE idConfig=1), ?, ?)";
        
                this.SQL_UPDATE = "UPDATE factures SET dateFacture=?, refFacture=?, idLivraison=?, idClient=?, montant=?, devise=?, description=?, pj=? WHERE id = ?";

    }

    @Override
    public List<Facture> select() {

        return selectWhere(SQL_SELECT + SQL_GROUP + SQL_ORDER);
    }

    public List<Facture> selectPaye(String query) {
        return selectWhere(SQL_SELECT+query+ SQL_GROUP +" HAVING resteAPayer <= 0 "+ SQL_ORDER);
    }
    
    public List<Facture> selectNonPaye(String query) {
        return selectWhere(SQL_SELECT+query+ SQL_GROUP +" HAVING resteAPayer = f.montant OR resteAPayer IS NULL "+ SQL_ORDER);
    }
    
    public List <Facture> selectPayePartiellement(String query) {
        return selectWhere(SQL_SELECT+query+ SQL_GROUP +" HAVING resteAPayer > 0 AND resteAPayer < f.montant "+ SQL_ORDER);
    }
    
    private List<Facture> selectWhere(String query){
        List<Facture> facList = new ArrayList<>();
        try {
	stm = conn.prepareStatement(query);
	result = stm.executeQuery();
        
	while(result.next()) {
            Facture fac = displayInfo(result);
            facList.add(fac);
        }
       return facList;
	}catch(SQLException ex) {
	    Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
           closeResources();
        }
        return null;
    }
    
    @Override
    public boolean create(Facture fac) {
        int rowsAffected =0;

    try {
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();//récuperer la data courante
        
        stm = conn.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
        stm.setString(1, now.format(dtFormat)); //dateFacture
        stm.setString(2, fac.getRefFacture());
        stm.setInt(3, fac.getIdLivraison());
        stm.setInt(4, fac.getIdClient());
        stm.setFloat(5, fac.getMontant());
//        stm.setString(6, "Ariary");
        stm.setString(6, fac.getDescription());
        stm.setString(7, fac.getPj());

        rowsAffected = stm.executeUpdate();
        // Récupérer l'ID généré
        try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                setGeneratedKey(generatedKeys.getInt(1));
                Facture facture = this.find(getGeneratedKey());
                facture.setRefFacture("FAC-"+DateConverter.getCurrentDate()+ getGeneratedKey());
                this.update(facture);
            } else {
                throw new SQLException("Échec de la récupération de l'ID généré pour la facture.");
            }
        }
        
    }catch(Exception e){
        System.out.println("Echec de la création de facture"+e);
    }finally{
    closeResources();
    }
        
        return rowsAffected != 0;
    }

    @Override
    public boolean delete(Facture obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Facture fac) {
        int rowsAffected =0;

        try {        
        stm = conn.prepareStatement(SQL_UPDATE);
        stm.setString(1, fac.getDateFacture()); //dateFacture
        stm.setString(2, fac.getRefFacture());
        stm.setInt(3, fac.getIdLivraison());
        stm.setInt(4, fac.getIdClient());
        stm.setFloat(5, fac.getMontant());
        stm.setString(6, fac.getDevise());
        stm.setString(7, fac.getDescription());
        stm.setString(8, fac.getPj());
        stm.setInt(9, fac.getId());
        
        rowsAffected = stm.executeUpdate();
        }catch(Exception e){
            System.out.println("Echec de la création de facture"+e);
        }finally{
            closeResources();
        }
        return rowsAffected != 0;
    }

    @Override
    public Facture find(int id) {
        Facture fac = new Facture();
        try {
	stm = conn.prepareStatement(SQL_FIND + SQL_GROUP );
        stm.setInt(1, id);
	result = stm.executeQuery();
        
	while(result.next()) {
            fac = displayInfo(result);
//            facList.add(fac);
        }
	}catch(SQLException ex) {
	    Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            closeResources();
        }
        return fac;
}


    public Facture find(String refFacture){
        Facture fac = new Facture();
        try {
	stm = conn.prepareStatement(SQL_FIND_REF + SQL_GROUP );
        stm.setString(1, refFacture);
	result = stm.executeQuery();
        
	while(result.next()) {
            fac = displayInfo(result);

        }
       
	}catch(SQLException ex) {
	    Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            closeResources();
        }
        return fac;
    }
    
    public int getGeneratedKey() {
        return generatedKey;
    }

    /**
     * @param generatedKey the generatedKey to set
     */
    public void setGeneratedKey(int generatedKey) {
        this.generatedKey = generatedKey;
    }
    
    private Facture displayInfo(ResultSet result) throws SQLException{
    Facture fac = new Facture();
            
            fac.setId(result.getInt("id"));
            fac.setDateFacture(result.getString("dateFacture"));
            fac.setDescription(result.getString("description"));
            fac.setDevise(result.getString("devise"));
            fac.setMontant(result.getFloat("montant"));
            fac.setPj(result.getString("pj"));
            fac.setRefFacture(result.getString("refFacture"));
            
            fac.setIdClient(result.getInt("idClient"));
            fac.setIdLivraison(result.getInt("idLivraison"));
            fac.setPersonne(clientDAO.find(result.getInt("idClient")));
            fac.setStatus(result.getString("etat"));
            fac.setLivraison( livDAO.find(result.getInt("idLivraison")) );
             
            return fac;
    }
    
     private void closeResources() {
        try {
            if(stm != null)stm.close();
            if(result != null)result.close();
        } catch (SQLException ex) {
            Logger.getLogger(CommandeCDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Facture> select(String query) {
        return selectWhere(SQL_SELECT+query+ SQL_GROUP + SQL_ORDER); //To change body of generated methods, choose Tools | Templates.
    }

    public Facture findBy(int idLivraison) {
    Facture fac = null;
        try  {
            stm = conn.prepareStatement(SQL_FIND_IDLIV);
            stm.setInt(1, idLivraison);
            result = stm.executeQuery();
                if (result.next()) {
                    fac = displayInfo(result);
                }
                return fac;
            
        } catch (SQLException e) {
        }finally{
            closeResources();
        }
        return null;
    }
    
    public int getCount(int idLivraison){
        String sql = "SELECT COUNT(id) as size from factures where idLivraison = ?";
        try  {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, idLivraison);
            result = stm.executeQuery();
                if (result.next()) {
                    return result.getInt("size");
                }
            
        } catch (SQLException e) {
        }finally{
            closeResources();
        }
        return 0;
    }
    public int getCountAPayer(){
        String sql = "SELECT COUNT(f.id) as size from factures f where f.montant > COALESCE(SUM(p.montantRecu), 0) "
                + " LEFT JOIN paiements p ON f.id = p.idFacture";
        try  {
            stm = conn.prepareStatement(sql);
            result = stm.executeQuery();
                if (result.next()) {
                    return result.getInt("size");
                }
            
        } catch (SQLException e) {
        }finally{
            closeResources();
        }
        return 0;
    }
    
}
