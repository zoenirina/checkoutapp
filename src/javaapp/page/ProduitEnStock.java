
package javaapp.page;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javaapp.component.Toast;
import javax.swing.JPanel;
/**
 *
 * @author ZOENIRINA
 */
public class ProduitEnStock extends javax.swing.JPanel {

    public String getIdSelected() {
        return idSelected;
    }

    public void setIdSelected(String idSelected) {
        this.idSelected = idSelected;
    }
    public boolean newUser;
      public  Connection conn = null;
     public  String url = "jdbc:sqlite:caisse.db";
     public String  SELECT_ALL_IDMAGASIN ="SELECT  p.id AS idProduit,  p.designation, p.PU AS prix, u.poids, u.capacite, (SELECT SUM(CASE " +
"WHEN m.sens = 'entrée' THEN m.quantite WHEN m.sens = 'sortie' THEN -m.quantite " +
"ELSE 0 END) FROM mouvementStock m WHERE m.idProduit = p.id AND m.idMagasin = ?) AS quantite_stock FROM   produits p " +
"LEFT JOIN uniteStockage u ON p.idUnite = u.id";
     
//     public String  SELECT_ALL ="SELECT  p.id AS idProduit,  p.designation, p.PU AS prix, u.poids, u.capacite, (SELECT SUM(CASE " +
//"            WHEN m.sens = 'entrée' THEN m.quantite" +
//"            WHEN m.sens = 'sortie' THEN -m.quantite" +
//"            ELSE 0 END) FROM mouvementStock m\n" +
//"     WHERE m.idProduit = p.id) AS quantite_stock FROM   produits p " +
////"       AND m.idExercice = ?) AS quantite_stock FROM   produits p" +
//"INNER JOIN    uniteStockage u ON p.idUnite = u.id"
     public String SELECT_ALL="SELECT p.id AS idProduit,p.designation,p.PU AS prix,p.refProduit , u.poids, u.capacite," +
"(SELECT COALESCE(SUM(CASE WHEN m.sens = 'entrée' THEN m.quantite ELSE 0 END), 0) - COALESCE(SUM(CASE WHEN m.sens = 'sortie' THEN m.quantite ELSE 0 END), 0)" +
"FROM mouvementStock m WHERE m.idProduit = p.id) AS quantite_stock FROM produits p LEFT JOIN uniteStockage u ON p.idUnite = u.id";
     
     public String  SELECT_ALL_IDMAGASIN_IDPRODDUIT ="SELECT  p.id AS idProduit,  p.designation, p.PU AS prix, u.poids, u.capacite, (SELECT SUM(CASE " +
"WHEN m.sens = 'entrée' THEN m.quantite WHEN m.sens = 'sortie' THEN -m.quantite   ELSE 0 END) FROM mouvementStock m\n" +
"WHERE m.idProduit = p.id AND m.idMagasin = ?) AS quantite_stock FROM   produits p" +
"left JOIN    uniteStockage u ON p.idUnite = u.id WHERE p.id = ?;";
     
          public String  SELECT_ALL_IDPRODDUIT ="SELECT  p.id AS idProduit,  p.designation, p.PU AS prix, u.poids, u.capacite, (SELECT SUM(CASE " +
"WHEN m.sens = 'entrée' THEN m.quantite " +
" WHEN m.sens = 'sortie' THEN -m.quantite " +
"ELSE 0 END) FROM mouvementStock m) AS quantite_stock FROM   produits p " +
"LEFT JOIN    uniteStockage u ON p.idUnite = u.id WHERE p.id = ? ";
     String query=null;
     public Toast t;  
     private int rowSelected;
     private String idSelected = null;
     public PreparedStatement stm ;
     public ResultSet resultSet;
     public String btnSaveState;
     public String title="Comptes utilisateus";
//       public String query ="select * from utilisateur";
     
    public ProduitEnStock() {
        initComponents();
        init();     
    }
    
      public ProduitEnStock(JPanel panel) {
        initComponents();
        init();
            
    }
    private void init(){
        formPanel.setVisible(false);
        query =SELECT_ALL;
        refreshTable();
        scrollTable.getViewport().setBackground(Color.white); 
         setInputSelect();
        
    }
    
    private void setInputSelect(){
            try {
                connectiondb();
                stm = conn.prepareStatement("SELECT id FROM magasin");
                resultSet = stm.executeQuery();
               idMagasinSearch.addItem("Tous");
                while (resultSet.next()) {
                    idMagasinSearch.addItem(resultSet.getString("id"));
                }
//                stm = conn.prepareStatement("SELECT type FROM type_mouvement");
//                resultSet = stm.executeQuery();
//                typeMouvement.addItem("");
//                while (resultSet.next()) {
//                    typeMouvement.addItem(resultSet.getString("type"));
//                }
                
                stm.close();
                conn.close();
        } catch (SQLException e) { }
    }
    
    private  void connectiondb(){
       
        try {
            Class.forName("org.sqlite.JDBC");
         try {
            conn = DriverManager.getConnection("jdbc:sqlite:caisse.db");
        } catch (SQLException ex) {
            Logger.getLogger(ProduitEnStock.class.getName()).log(Level.SEVERE, null, ex);
        }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProduitEnStock.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
    @SuppressWarnings("unchecked") 
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listPanel = new javaapp.component.PanelBorderRound();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnnewuser = new javaapp.component.ButtonRadius();
        produitKeySearch = new javax.swing.JTextField();
        btn_search = new javaapp.component.ButtonRadius();
        idMagasinSearch = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        btn_refresh = new javaapp.component.ButtonRadius();
        scrollTable = new javax.swing.JScrollPane();
        table = new javaapp.component.Table();
        formPanel = new javax.swing.JPanel();
        panelBorderRound1 = new javaapp.component.PanelBorderRound();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnsave = new javaapp.component.ButtonRadius();
        btn_back = new javaapp.component.ButtonRadius();
        jLabel9 = new javax.swing.JLabel();
        refProduit = new javax.swing.JTextField();
        idProduit = new javax.swing.JTextField();
        btn_cancel = new javaapp.component.ButtonRadius();
        btndelete = new javaapp.component.ButtonRadius();
        jLabel10 = new javax.swing.JLabel();
        designation = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        nbr_package = new javax.swing.JSpinner();
        unite_package = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        prix = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        poids = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        longueur = new javax.swing.JSpinner();
        jLabel18 = new javax.swing.JLabel();
        volume = new javax.swing.JSpinner();
        jLabel19 = new javax.swing.JLabel();
        capacite = new javax.swing.JSpinner();
        jLabel20 = new javax.swing.JLabel();
        description_unite = new javax.swing.JTextField();

        listPanel.setBackground(new java.awt.Color(255, 255, 255));
        listPanel.setPreferredSize(new java.awt.Dimension(1075, 800));

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(36, 36, 36));
        jLabel1.setText("Liste des produits");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 102));

        btnnewuser.setBackground(new java.awt.Color(248, 91, 50));
        btnnewuser.setBorder(null);
        btnnewuser.setForeground(new java.awt.Color(255, 255, 255));
        btnnewuser.setText("Ajouter");
        btnnewuser.setBorderColor(new java.awt.Color(248, 91, 50));
        btnnewuser.setColor(new java.awt.Color(248, 91, 50));
        btnnewuser.setColorClick(new java.awt.Color(248, 91, 50));
        btnnewuser.setColorOver(new java.awt.Color(255, 105, 82));
        btnnewuser.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnnewuser.setRadius(18);
        btnnewuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewuserActionPerformed(evt);
            }
        });

        btn_search.setBorder(null);
        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/search.png"))); // NOI18N
        btn_search.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_search.setColorClick(new java.awt.Color(255, 255, 255));
        btn_search.setColorOver(new java.awt.Color(240, 236, 243));
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("Réference ou désignation du produit");

        jLabel22.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("Identifiant du magasin");

        btn_refresh.setBorder(null);
        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/refresh.png"))); // NOI18N
        btn_refresh.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_refresh.setColorClick(new java.awt.Color(255, 255, 255));
        btn_refresh.setColorOver(new java.awt.Color(240, 236, 243));
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        scrollTable.setBorder(null);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        scrollTable.setViewportView(table);

        javax.swing.GroupLayout listPanelLayout = new javax.swing.GroupLayout(listPanel);
        listPanel.setLayout(listPanelLayout);
        listPanelLayout.setHorizontalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(listPanelLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnnewuser, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(101, 101, 101))
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(scrollTable, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(listPanelLayout.createSequentialGroup()
                                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(idMagasinSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(64, 64, 64)
                                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addGroup(listPanelLayout.createSequentialGroup()
                                        .addComponent(produitKeySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 128, Short.MAX_VALUE))))
        );
        listPanelLayout.setVerticalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnnewuser, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(idMagasinSearch)
                        .addComponent(produitKeySearch)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109))
        );

        panelBorderRound1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(36, 36, 36));
        jLabel4.setText("Enregistrer un nouveau produit");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Code produit");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Poids (kg)");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Nombre de paquetage");

        btnsave.setBackground(new java.awt.Color(248, 91, 50));
        btnsave.setBorder(null);
        btnsave.setForeground(new java.awt.Color(255, 255, 255));
        btnsave.setText("Enregistrer");
        btnsave.setBorderColor(new java.awt.Color(248, 91, 50));
        btnsave.setColor(new java.awt.Color(248, 91, 50));
        btnsave.setColorClick(new java.awt.Color(248, 91, 50));
        btnsave.setColorOver(new java.awt.Color(248, 91, 50));
        btnsave.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        btn_back.setBackground(new java.awt.Color(255, 255, 255));
        btn_back.setBorder(null);
        btn_back.setForeground(new java.awt.Color(51, 51, 51));
        btn_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/prev2.png"))); // NOI18N
        btn_back.setText("Retour");
        btn_back.setBorderColor(new java.awt.Color(255, 255, 255));
        btn_back.setColor(new java.awt.Color(255, 255, 255));
        btn_back.setColorClick(new java.awt.Color(255, 255, 255));
        btn_back.setColorOver(new java.awt.Color(247, 251, 246));
        btn_back.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Réference");

        refProduit.setForeground(new java.awt.Color(102, 102, 102));
        refProduit.setSelectionColor(new java.awt.Color(102, 102, 102));

        btn_cancel.setBorder(null);
        btn_cancel.setForeground(new java.awt.Color(51, 51, 51));
        btn_cancel.setText("Annuler");
        btn_cancel.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_cancel.setColorClick(new java.awt.Color(240, 240, 240));
        btn_cancel.setColorOver(new java.awt.Color(240, 240, 240));
        btn_cancel.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N

        btndelete.setBackground(new java.awt.Color(255, 11, 31));
        btndelete.setBorder(null);
        btndelete.setForeground(new java.awt.Color(255, 255, 255));
        btndelete.setText("Supprimer");
        btndelete.setBorderColor(new java.awt.Color(255, 0, 0));
        btndelete.setColor(new java.awt.Color(255, 11, 31));
        btndelete.setColorClick(new java.awt.Color(255, 11, 31));
        btndelete.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Désignation");

        designation.setForeground(new java.awt.Color(102, 102, 102));
        designation.setSelectionColor(new java.awt.Color(102, 102, 102));

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Prix");

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Unite ");

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Mesure");

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("Paquetage");

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("Information générale");

        jLabel17.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("Longueur (m)");

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("Volume (m3)");

        jLabel19.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("Capacité");

        jLabel20.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setText("Description");

        description_unite.setForeground(new java.awt.Color(102, 102, 102));
        description_unite.setSelectionColor(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout panelBorderRound1Layout = new javax.swing.GroupLayout(panelBorderRound1);
        panelBorderRound1.setLayout(panelBorderRound1Layout);
        panelBorderRound1Layout.setHorizontalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 669, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addGap(49, 49, 49))
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 1002, Short.MAX_VALUE))
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15))
                        .addGap(45, 45, 45)
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12)
                            .addComponent(prix, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(poids, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound1Layout.createSequentialGroup()
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(longueur, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(idProduit, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addGap(53, 53, 53)
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(refProduit, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)))
                            .addComponent(designation)
                            .addComponent(nbr_package)
                            .addComponent(unite_package)
                            .addComponent(jLabel18)
                            .addComponent(volume, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addGap(380, 380, 380)
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(capacite, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel20)
                            .addComponent(description_unite))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelBorderRound1Layout.setVerticalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idProduit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(refProduit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addComponent(designation, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(prix, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(poids, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(longueur, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(volume, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(capacite, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(description_unite, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)))
                .addGap(9, 9, 9)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nbr_package, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unite_package, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(538, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout formPanelLayout = new javax.swing.GroupLayout(formPanel);
        formPanel.setLayout(formPanelLayout);
        formPanelLayout.setHorizontalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorderRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        formPanelLayout.setVerticalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(listPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1099, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(listPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 823, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnnewuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewuserActionPerformed
        listPanel.setVisible(false);
        setEmptyForm();
        formPanel.setVisible(true);
        btnSaveState="insert";
    }//GEN-LAST:event_btnnewuserActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
       listPanel.setVisible(false);
        formPanel.setVisible(true);
        btnSaveState="update"; 
        DefaultTableModel model=(DefaultTableModel) table.getModel();
        int i = table.getSelectedRow();
        setIdSelected(model.getValueAt(i,0).toString());
        
            try {
                connectiondb();
                stm = conn.prepareStatement("select produits.* ,uniteStockage.* from produits,uniteStockage where  produits.idUnite=uniteStockage.id and produits.id=?");
                stm.setString(1, getIdSelected());
                resultSet = stm.executeQuery();
                while (resultSet.next()) {
                    idProduit.setText(resultSet.getString("id"));
                    refProduit.setText(resultSet.getString("refProduit"));
                    designation.setText(resultSet.getString("designation"));
                    prix.setValue(resultSet.getFloat("PU"));
                    
                    poids.setValue(resultSet.getFloat("poids"));
                    capacite.setValue(resultSet.getFloat("capacite"));
                    volume.setValue(resultSet.getFloat("volume"));
                    longueur.setValue(resultSet.getFloat("longueur"));
                    nbr_package.setValue(resultSet.getFloat("package"));
                    unite_package.setValue(resultSet.getFloat("unite"));
                }
                stm.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProduitEnStock.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_tableMouseClicked

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed

        try {
          connectiondb();
          switch(btnSaveState){
              case "insert":
                  //creer l'unité de stockage
                  String insertUniteQuery = "INSERT INTO uniteStockage (unite, poids, capacite, volume, longueur, package, labelle, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                      try (PreparedStatement pstmt = conn.prepareStatement(insertUniteQuery, Statement.RETURN_GENERATED_KEYS)) {
                      pstmt.setInt(1, (int) unite_package.getValue()); // Unite
                      pstmt.setFloat(2, ((Number) poids.getValue()).floatValue()); // Poids
                      pstmt.setFloat(3, ((Number) capacite.getValue()).floatValue()); // Capacité
                      pstmt.setFloat(4, ((Number) volume.getValue()).floatValue()); // Volume
                      pstmt.setFloat(5, ((Number) longueur.getValue()).floatValue()); // Longueur
                      pstmt.setInt(6, (int) nbr_package.getValue()); // Package
                      pstmt.setString(7, "Labelle exemple"); // Labelle
                      pstmt.setString(8, description_unite.getText()); // Description

                      int affectedRows = pstmt.executeUpdate();
                      if (affectedRows > 0) {
                          try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                              if (generatedKeys.next()) {
                                  long idUnite = generatedKeys.getLong(1);
                                  System.out.println("L'ID généré pour la nouvelle unité est : " + idUnite);

                                  // Utiliser l'ID récupéré pour insérer dans la table produits
                                  String insertProduitQuery = "INSERT INTO produits (designation, refProduit, idUnite, PU, dateCreation, dateModif) VALUES (?, ?, ?, ?, ?, ?)";
                                  try (PreparedStatement pstmtProduit = conn.prepareStatement(insertProduitQuery)) {
                                      pstmtProduit.setString(1, designation.getText());
                                      pstmtProduit.setString(2, refProduit.getText());
                                      pstmtProduit.setLong(3, idUnite); // Utilisation de l'ID d'unité généré
                                      pstmtProduit.setFloat(4, ((Number) prix.getValue()).floatValue()); // Prix unitaire
                                      pstmtProduit.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now())); // Date de création
                                      pstmtProduit.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now())); // Date de modification

                                      pstmtProduit.executeUpdate();
                                      System.out.println("Une ligne a été insérée avec succès dans produits.");
                                  }
                              }
                          }
                      }
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
                       
                            break;
                    case "update":
                        if(JOptionPane.showConfirmDialog(null, "Voulez-vous sauvegarder vos modification?","Mise à jour",JOptionPane.YES_NO_OPTION) == 0){
                       try {
                            conn.setAutoCommit(false); // Commencer la transaction
                            // Étape 1: Mettre à jour la table `produit`.
                            String updateProduitQuery = "UPDATE produits SET designation = ?, refProduit = ?, PU = ?, dateModif = ? WHERE id = ?";
                            try (PreparedStatement pstmtProduit = conn.prepareStatement(updateProduitQuery)) {
                                pstmtProduit.setString(1, designation.getText());
                                pstmtProduit.setString(2, refProduit.getText());
                                pstmtProduit.setFloat(3, ((Number) prix.getValue()).floatValue()); // Nouveau prix
                                pstmtProduit.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now())); // Date de modification
                                pstmtProduit.setString(5, idSelected); // ID du produit à modifier

                                pstmtProduit.executeUpdate();
                            }

                            // Étape 2: Identifier l'`idUnite` pour ce produit et mettre à jour `uniteStockage`.
                            // Ceci peut être optimisé si vous avez déjà l'idUnite, éliminant la nécessité de le sélectionner.
                            int idUnite = 0;
                            String selectIdUniteQuery = "SELECT idUnite FROM produits WHERE id = ?";
                            try (PreparedStatement pstmtSelect = conn.prepareStatement(selectIdUniteQuery)) {
                                pstmtSelect.setString(1, idSelected); // ID du produit
                                resultSet = pstmtSelect.executeQuery();
                                if (resultSet.next()) {
                                    idUnite = resultSet.getInt("idUnite");
                                }
                            }
                            System.out.println("Vous avez selectionné idUnite="+idUnite);
                            // Si l'idUnite a été trouvé, mettre à jour la table `uniteStockage`.
                            if (idUnite > 0) {
                            //String updateUniteQuery = "UPDATE uniteStockage SET unite = ?, poids = ?, capacite = ?, volume = ?, longueur = ?, package = ?, labelle = ?, description = ? WHERE id = ?";
                                try {
                                    stm = conn.prepareStatement("UPDATE uniteStockage SET unite = ?, poids = ?, capacite = ?, volume = ?, longueur = ?, package = ?, labelle = ?, description = ? WHERE id = ?");
                                    stm.setString(1, unite_package.getValue().toString()); // Unite
                                    stm.setFloat(2, ((Number) poids.getValue()).floatValue()); // Poids
                                    stm.setFloat(3, ((Number) capacite.getValue()).floatValue()); // Capacité
                                    stm.setFloat(4, ((Number) volume.getValue()).floatValue()); // Volume
                                    stm.setFloat(5, ((Number) longueur.getValue()).floatValue()); // Longueur
                                    stm.setString(6, nbr_package.getValue().toString()); // Package
                                    stm.setString(7, "Labelle exemple"); // Labelle
                                    stm.setString(8, description_unite.getText()); // Description
                                    stm.setInt(9, idUnite);

                                    stm.executeUpdate();
                                }catch(SQLException e){}
                            }

                            conn.commit(); // Valider la transaction
                            System.out.println("La mise à jour des tables 'produits' et 'uniteStockage' a été effectuée avec succès.");
                        } catch (SQLException e) {
                            try {
                                conn.rollback(); // En cas d'erreur, annuler la transaction
                                System.out.println("La transaction a été annulée.");
                            } catch (SQLException ex) {
                                System.out.println("Erreur lors de l'annulation de la transaction : " + ex.getMessage());
                            }
                            e.printStackTrace();
                        } finally {
                            try {
                                conn.setAutoCommit(true); // Restaurer le mode de commit automatique
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        t = new Toast("Modification enregistrée", this.getLocationOnScreen().x+this.getWidth()-320, this.getLocationOnScreen().y+7);
                        t.showtoast();
                        }
                  break;
                }
                  JOptionPane.showMessageDialog(null, "insertION R2USSIE? IDSELECTED"+getIdSelected(),"Sucess",JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
                stm.close();
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
           
        
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        try {
            if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer?","Confirmation de suppression",JOptionPane.YES_NO_OPTION) == 0){
           connectiondb(); 
           stm = conn.prepareStatement("delete from utilisateur where idUtilisateur=?"); 
            stm.setString(1, idSelected);
            stm.executeUpdate();
            stm.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Suppression réussie"+getIdSelected(),"Sucess",JOptionPane.INFORMATION_MESSAGE);}
        } catch (SQLException ex) {
            Logger.getLogger(ProduitEnStock.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        listPanel.setVisible(true);
        formPanel.setVisible(false);
        refreshTable();
        setEmptyForm();
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
         if( idMagasinSearch.getSelectedItem() != "Tous" && !produitKeySearch.getText().isEmpty()){
        query="SELECT  p.id AS idProduit,  p.designation, p.PU AS prix, p.refProduit, u.poids, u.capacite, (SELECT SUM(CASE " +
"WHEN m.sens = 'entrée' THEN m.quantite WHEN m.sens = 'sortie' THEN -m.quantite   ELSE 0 END) FROM mouvementStock m " +
"WHERE m.idProduit = p.id AND m.idMagasin = "+idMagasinSearch.getSelectedItem()+") AS quantite_stock FROM   produits p " +
"left JOIN    uniteStockage u ON p.idUnite = u.id WHERE p.designation like '%"+produitKeySearch.getText()+"%' or p.refProduit like '%"+produitKeySearch.getText()+"%'";
        }else if( idMagasinSearch.getSelectedItem() == "Tous" && !produitKeySearch.getText().isEmpty()){
        query="SELECT p.id AS idProduit,p.designation,p.PU AS prix,p.refProduit , u.poids, u.capacite," +
"(SELECT COALESCE(SUM(CASE WHEN m.sens = 'entrée' THEN m.quantite ELSE 0 END), 0) - COALESCE(SUM(CASE WHEN m.sens = 'sortie' THEN m.quantite ELSE 0 END), 0)" +
"FROM mouvementStock m WHERE m.idProduit = p.id) AS quantite_stock FROM produits p LEFT JOIN uniteStockage u ON p.idUnite = u.id WHERE p.designation like '%"+produitKeySearch.getText()+"%' or p.refProduit like '%"+produitKeySearch.getText()+"%'";
        }else if(idMagasinSearch.getSelectedItem() != "Tous" && produitKeySearch.getText().isEmpty()){
        query="SELECT  p.id AS idProduit,  p.designation, p.PU AS prix, p.refProduit, u.poids, u.capacite, (SELECT SUM(CASE " +
"WHEN m.sens = 'entrée' THEN m.quantite WHEN m.sens = 'sortie' THEN -m.quantite " +
"ELSE 0 END) FROM mouvementStock m WHERE m.idProduit = p.id AND m.idMagasin = "+idMagasinSearch.getSelectedItem()+") AS quantite_stock FROM   produits p " +
"LEFT JOIN uniteStockage u ON p.idUnite = u.id";
        }
       
        refreshTable();
        
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        query=SELECT_ALL;
        refreshTable();  
        idMagasinSearch.setSelectedIndex(0);
        produitKeySearch.setText("");
    }//GEN-LAST:event_btn_refreshActionPerformed
 
    private void refreshTable(){
        String column[]= {"ID","Reference","Designation","Prix","Quantité de stock","Status"};
        Object[] data = new Object[6];
        DefaultTableModel model = new DefaultTableModel(null,column);
        try{
            connectiondb();
            Statement statement =(Statement)conn.createStatement();
            resultSet = statement.executeQuery(query);
        
           while(resultSet.next()){
                data[0]=resultSet.getString("idProduit");
                data[1]=resultSet.getString("designation");
                data[2]=resultSet.getString("refProduit");
                data[3]=resultSet.getString("prix");
                data[4]=(resultSet.getString("quantite_stock") == null)?"0":resultSet.getString("quantite_stock");
                data[5]=(!"0".equals(resultSet.getString("quantite_stock")) || resultSet.getString("quantite_stock") == null)?"Disponible":"Stock épuisé";
                model.addRow(data);
       };
       table.setModel(model);
        conn.close();

    } catch (SQLException ex) {
            Logger.getLogger(ProduitEnStock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setEmptyForm(){
        idProduit.setText("");
        refProduit.setText("");
        designation.setText("");
        prix.setValue(0);
                    
        poids.setValue(0);
        capacite.setValue(0);
        volume.setValue(0);
        longueur.setValue(0);
        nbr_package.setValue(0);
        unite_package.setValue(0);
    }
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_back;
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_search;
    private javaapp.component.ButtonRadius btndelete;
    private javaapp.component.ButtonRadius btnnewuser;
    private javaapp.component.ButtonRadius btnsave;
    private javax.swing.JSpinner capacite;
    private javax.swing.JTextField description_unite;
    private javax.swing.JTextField designation;
    private javax.swing.JPanel formPanel;
    private javax.swing.JComboBox<String> idMagasinSearch;
    private javax.swing.JTextField idProduit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javaapp.component.PanelBorderRound listPanel;
    private javax.swing.JSpinner longueur;
    private javax.swing.JSpinner nbr_package;
    private javaapp.component.PanelBorderRound panelBorderRound1;
    private javax.swing.JSpinner poids;
    private javax.swing.JSpinner prix;
    private javax.swing.JTextField produitKeySearch;
    private javax.swing.JTextField refProduit;
    private javax.swing.JScrollPane scrollTable;
    private javaapp.component.Table table;
    private javax.swing.JSpinner unite_package;
    private javax.swing.JSpinner volume;
    // End of variables declaration//GEN-END:variables

    public int getRowSelected() {
        return rowSelected;
    }
    public void setRowSelected(int rowSelected) {
        this.rowSelected = rowSelected;
    }
}
