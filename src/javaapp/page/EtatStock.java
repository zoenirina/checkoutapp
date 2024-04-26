
package javaapp.page;

import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javaapp.component.Toast;
import javax.swing.JPanel;

public class EtatStock extends javax.swing.JPanel {

    public boolean newUser;
    public  Connection conn = null;
    public  String url = "jdbc:sqlite:caisse.db";

     public String SELECT_ALL="SELECT p.id AS idProduit,p.designation,p.PU AS prix,p.refProduit , u.poids, u.capacite," +
    " (SELECT COALESCE(SUM(CASE WHEN m.sens = 'Entrée' THEN m.quantite ELSE 0 END), 0) - COALESCE(SUM(CASE WHEN m.sens = 'Sortie' THEN m.quantite ELSE 0 END), 0)" +
    " FROM mouvementStock m WHERE m.idProduit = p.id) AS quantite_stock FROM produits p LEFT JOIN uniteStockage u ON p.idUnite = u.id";

    String query=null;
    public Toast t;  
    public JPanel pan;
    private int rowSelected;
    private String idSelected = null;
    public PreparedStatement stm ;
    public ResultSet resultSet;
    public String btnSaveState;
    public String title="Comptes utilisateus";
     
    public EtatStock() {
        initComponents();
        init();     
    }
    
      public EtatStock(JPanel panel) {
        initComponents();
        init();
        pan= panel;
            
    }
    private void init(){
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
            Logger.getLogger(EtatStock.class.getName()).log(Level.SEVERE, null, ex);
        }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EtatStock.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
    @SuppressWarnings("unchecked") 
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listPanel = new javaapp.component.PanelBorderRound();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        produitKeySearch = new javax.swing.JTextField();
        btn_search = new javaapp.component.ButtonRadius();
        idMagasinSearch = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        btn_refresh = new javaapp.component.ButtonRadius();
        scrollTable = new javax.swing.JScrollPane();
        table = new javaapp.component.Table();

        listPanel.setBackground(new java.awt.Color(255, 255, 255));
        listPanel.setPreferredSize(new java.awt.Dimension(1075, 800));

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(36, 36, 36));
        jLabel1.setText("Etat de stock");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 102));

        btn_search.setBackground(new java.awt.Color(240, 236, 243));
        btn_search.setBorder(null);
        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/search.png"))); // NOI18N
        btn_search.setBorderColor(new java.awt.Color(240, 236, 243));
        btn_search.setColor(new java.awt.Color(240, 236, 243));
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

        btn_refresh.setBackground(new java.awt.Color(240, 236, 243));
        btn_refresh.setBorder(null);
        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/refresh.png"))); // NOI18N
        btn_refresh.setBorderColor(new java.awt.Color(240, 236, 243));
        btn_refresh.setColor(new java.awt.Color(240, 236, 243));
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
                        .addGap(101, 820, Short.MAX_VALUE))
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
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(listPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1099, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(listPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 823, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        if( idMagasinSearch.getSelectedItem() != "Tous" && !produitKeySearch.getText().isEmpty()){
            query="SELECT  p.id AS idProduit,  p.designation, p.PU AS prix, p.refProduit, u.poids, u.capacite, (SELECT SUM(CASE " +
            "WHEN m.sens = 'Entrée' THEN m.quantite WHEN m.sens = 'Sortie' THEN -m.quantite   ELSE 0 END) FROM mouvementStock m " +
            "WHERE m.idProduit = p.id AND m.idMagasin = "+idMagasinSearch.getSelectedItem()+") AS quantite_stock FROM   produits p " +
            "left JOIN    uniteStockage u ON p.idUnite = u.id WHERE p.designation like '%"+produitKeySearch.getText()+"%' or p.refProduit like '%"+produitKeySearch.getText()+"%'";
        }else if( idMagasinSearch.getSelectedItem() == "Tous" && !produitKeySearch.getText().isEmpty()){
            query="SELECT p.id AS idProduit,p.designation,p.PU AS prix,p.refProduit , u.poids, u.capacite," +
            "(SELECT COALESCE(SUM(CASE WHEN m.sens = 'Entrée' THEN m.quantite ELSE 0 END), 0) - COALESCE(SUM(CASE WHEN m.sens = 'Sortie' THEN m.quantite ELSE 0 END), 0)" +
            "FROM mouvementStock m WHERE m.idProduit = p.id) AS quantite_stock FROM produits p LEFT JOIN uniteStockage u ON p.idUnite = u.id WHERE p.designation like '%"+produitKeySearch.getText()+"%' or p.refProduit like '%"+produitKeySearch.getText()+"%'";
        }else if(idMagasinSearch.getSelectedItem() != "Tous" && produitKeySearch.getText().isEmpty()){
            query="SELECT  p.id AS idProduit,  p.designation, p.PU AS prix, p.refProduit, u.poids, u.capacite, (SELECT SUM(CASE " +
            "WHEN m.sens = 'Entrée' THEN m.quantite WHEN m.sens = 'Sortie' THEN -m.quantite " +
            "ELSE 0 END) FROM mouvementStock m WHERE m.idProduit = p.id AND m.idMagasin = "+idMagasinSearch.getSelectedItem()+") AS quantite_stock FROM   produits p " +
            "LEFT JOIN uniteStockage u ON p.idUnite = u.id";
        }else{
            query=SELECT_ALL;
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
                data[5]=("0".equals(resultSet.getString("quantite_stock")) || resultSet.getString("quantite_stock") == null)?"Stock épuisé":"Disponible";
                model.addRow(data);
       };
       table.setModel(model);
        conn.close();

    } catch (SQLException ex) {
            Logger.getLogger(EtatStock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Vider tous les champs du formuaire
   
  
     public int getRowSelected() {
        return rowSelected;
    }
    public void setRowSelected(int rowSelected) {
        this.rowSelected = rowSelected;
    }
        public String getIdSelected() {
        return idSelected;
    }

    public void setIdSelected(String idSelected) {
        this.idSelected = idSelected;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_search;
    private javax.swing.JComboBox<String> idMagasinSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JSeparator jSeparator1;
    private javaapp.component.PanelBorderRound listPanel;
    private javax.swing.JTextField produitKeySearch;
    private javax.swing.JScrollPane scrollTable;
    private javaapp.component.Table table;
    // End of variables declaration//GEN-END:variables

   
}
