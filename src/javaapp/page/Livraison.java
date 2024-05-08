/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.page;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.component.ModalDetailLivraison;
import javaapp.component.Toast;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ZOENIRINA
 */
public class Livraison extends javax.swing.JPanel {
private final int borderRadius = 10;
 Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
//    String SELECT_ALL="SELECT ld.*, ld.id as idLivDetail, p.designation AS designation_produit, ld.idProduit, cd.quantite AS quantite_commandee,ld.dateLivraison, cl.id AS id_client, cl.nom AS nom_client,cl.prenom AS prenom_client, c.id as idCommande, c.refCommande, cl.id as id_client " +
//    "FROM livraisonDetails ld INNER JOIN commandeDetails cd ON ld.idCommandeDetail = cd.id INNER JOIN produits p ON ld.idProduit = p.id INNER JOIN commandes c ON cd.idCommande = c.id  INNER JOIN clients cl ON c.idClient = cl.id ";
    
     String SELECT_ALL="SELECT ld.id AS idLivDetail, c.refCommande, ld.montant, ld.dateLivraison, cl.nom AS nom_client, cl.prenom AS prenom_client FROM livraisonDetails ld " +
" JOIN commandeDetails cd ON ld.idCommandeDetail = cd.id JOIN commandes c ON cd.idCommande = c.id JOIN clients cl ON c.idClient = cl.id";
    String DATA_ORDER=" ORDER BY ld.dateLivraison DESC";
    String SELECT_ALL_LIVREE=SELECT_ALL+" ld.status = 'Livrée'";
    String SELECT_ALL_EN_ATTENTE=SELECT_ALL+" WHERE ld.dateLivraison < CURRENT_TIMESTAMP and ld.status = 'En attente'";
    String SELECT_ALL_RETARD=SELECT_ALL+" WHERE  ld.status = 'En attente' AND ld.dateLivraison > CURRENT_TIMESTAMP";
    String SELECT_ALL_ANNULEE=SELECT_ALL+" where ld.status = 'Annulée'";
    
    public String query=null;
    public String title="Autres";
    public PreparedStatement stm ;
    public ResultSet resultSet;
    private String idSelected;
    public String btn_save_state= "insert";
    public JPanel pan;
    public Toast t;

    public Livraison(JPanel panel) {
        initComponents();
        pan= panel;
        query= SELECT_ALL+ DATA_ORDER;
        refreshTable();
        scrollTable.getViewport().setBackground(Color.white);
        filterData();
    }
    
    private  void connectiondb(){
       
        try {
            Class.forName("org.sqlite.JDBC");
         try {
            conn = DriverManager.getConnection("jdbc:sqlite:caisse.db");
        } catch (SQLException ex) {
            Logger.getLogger(Magasin.class.getName()).log(Level.SEVERE, null, ex);
        }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Magasin.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    } 
    
     @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RoundRectangle2D.Float roundRect = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);
        g2.setColor(getBackground());
        g2.fill(roundRect);
        super.paintComponent(g);
        g2.setColor(getForeground());
        g2.draw(roundRect);
        g2.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        page1 = new javax.swing.JPanel();
        btn_refresh = new javaapp.component.ButtonRadius();
        scrollTable = new javax.swing.JScrollPane();
        listeCommandes = new javaapp.component.Table();
        nomClientKeySearch = new javax.swing.JTextField();
        btn_search = new javaapp.component.ButtonRadius();
        periodeLivraison = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        refCommande = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        linegroup = new javax.swing.JPanel();
        line1 = new javax.swing.JPanel();
        line2 = new javax.swing.JPanel();
        line3 = new javax.swing.JPanel();
        line4 = new javax.swing.JPanel();
        line5 = new javax.swing.JPanel();
        menu2 = new javax.swing.JLabel();
        menu3 = new javax.swing.JLabel();
        menu1 = new javax.swing.JLabel();
        facture_non_paye = new javax.swing.JLabel();
        menu4 = new javax.swing.JLabel();
        menu5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 700));

        page1.setBackground(new java.awt.Color(255, 255, 255));

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

        scrollTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        listeCommandes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        listeCommandes.setForeground(new java.awt.Color(255, 255, 255));
        listeCommandes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        listeCommandes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listeCommandesMouseClicked(evt);
            }
        });
        scrollTable.setViewportView(listeCommandes);

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

        periodeLivraison.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cette année", "Cette semaine", "Ce mois", "3 derniers mois", "3 prochains mois" }));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Période de livraison");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Nom ou prénom du client");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Reférence de commande");

        linegroup.setPreferredSize(new java.awt.Dimension(74, 2));

        line1.setBackground(new java.awt.Color(248, 91, 50));

        javax.swing.GroupLayout line1Layout = new javax.swing.GroupLayout(line1);
        line1.setLayout(line1Layout);
        line1Layout.setHorizontalGroup(
            line1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 85, Short.MAX_VALUE)
        );
        line1Layout.setVerticalGroup(
            line1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout line2Layout = new javax.swing.GroupLayout(line2);
        line2.setLayout(line2Layout);
        line2Layout.setHorizontalGroup(
            line2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 85, Short.MAX_VALUE)
        );
        line2Layout.setVerticalGroup(
            line2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout line3Layout = new javax.swing.GroupLayout(line3);
        line3.setLayout(line3Layout);
        line3Layout.setHorizontalGroup(
            line3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 108, Short.MAX_VALUE)
        );
        line3Layout.setVerticalGroup(
            line3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout line4Layout = new javax.swing.GroupLayout(line4);
        line4.setLayout(line4Layout);
        line4Layout.setHorizontalGroup(
            line4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 108, Short.MAX_VALUE)
        );
        line4Layout.setVerticalGroup(
            line4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout line5Layout = new javax.swing.GroupLayout(line5);
        line5.setLayout(line5Layout);
        line5Layout.setHorizontalGroup(
            line5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 108, Short.MAX_VALUE)
        );
        line5Layout.setVerticalGroup(
            line5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout linegroupLayout = new javax.swing.GroupLayout(linegroup);
        linegroup.setLayout(linegroupLayout);
        linegroupLayout.setHorizontalGroup(
            linegroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(linegroupLayout.createSequentialGroup()
                .addComponent(line1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(line2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(line3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(line4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(line5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        linegroupLayout.setVerticalGroup(
            linegroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(line1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(line2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(line3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(line4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(line5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu2.setForeground(new java.awt.Color(153, 153, 153));
        menu2.setText("Livré");
        menu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu2MouseClicked(evt);
            }
        });

        menu3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu3.setForeground(new java.awt.Color(153, 153, 153));
        menu3.setText("En attente");
        menu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu3MouseClicked(evt);
            }
        });

        menu1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu1.setForeground(new java.awt.Color(51, 51, 51));
        menu1.setText("Tous");
        menu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu1MouseClicked(evt);
            }
        });

        facture_non_paye.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        facture_non_paye.setForeground(new java.awt.Color(51, 51, 51));
        facture_non_paye.setText("(0) Livraison en retard");

        menu4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu4.setForeground(new java.awt.Color(153, 153, 153));
        menu4.setText("En retard");
        menu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu4MouseClicked(evt);
            }
        });

        menu5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu5.setForeground(new java.awt.Color(153, 153, 153));
        menu5.setText("Annulée");
        menu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout page1Layout = new javax.swing.GroupLayout(page1);
        page1.setLayout(page1Layout);
        page1Layout.setHorizontalGroup(
            page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollTable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
                    .addGroup(page1Layout.createSequentialGroup()
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(page1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(periodeLivraison, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nomClientKeySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(page1Layout.createSequentialGroup()
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(linegroup, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(facture_non_paye, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(page1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(menu1)
                                .addGap(53, 53, 53)
                                .addComponent(menu2)
                                .addGap(34, 34, 34)
                                .addComponent(menu3)
                                .addGap(34, 34, 34)
                                .addComponent(menu4)
                                .addGap(44, 44, 44)
                                .addComponent(menu5)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        page1Layout.setVerticalGroup(
            page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page1Layout.createSequentialGroup()
                .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(page1Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_refresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nomClientKeySearch, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)))
                    .addGroup(page1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(facture_non_paye)
                        .addGap(17, 17, 17)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(menu2)
                                .addComponent(menu1))
                            .addComponent(menu3)
                            .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(menu4)
                                .addComponent(menu5)))
                        .addGap(5, 5, 5)
                        .addComponent(linegroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(2, 2, 2)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(refCommande, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                            .addComponent(periodeLivraison))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(page1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(page1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void listeCommandesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listeCommandesMouseClicked
    DefaultTableModel model = (DefaultTableModel) listeCommandes.getModel();
    
    // Récupérer l'index de la ligne sélectionnée
    int rowIndex = listeCommandes.getSelectedRow();
    
    // Vérifier si une ligne est effectivement sélectionnée
    if (rowIndex != -1) {
         String idLivraisonDetail = model.getValueAt(rowIndex, 0).toString();
       ModalDetailLivraison modal=new  ModalDetailLivraison( Integer.parseInt(idLivraisonDetail));
       modal.setVisible(true);
    }
    }//GEN-LAST:event_listeCommandesMouseClicked

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
       filterData();
    }//GEN-LAST:event_btn_searchActionPerformed

    private void filterData(){
     int selectedOption = periodeLivraison.getSelectedIndex(); // Récupération de l'option sélectionnée
//JOptionPane.showMessageDialog(null, selectedOption,"Sucess",JOptionPane.INFORMATION_MESSAGE); 
         query = SELECT_ALL; 
         String FILTER_CLIENT=" AND cl.nom like '%"+nomClientKeySearch.getText()+"%' OR cl.prenom like '%"+nomClientKeySearch.getText()+"%'";
        switch (selectedOption) {
            case 0:
                query += " WHERE strftime('%Y', ld.dateLivraison) = strftime('%Y', 'now')";
                if(!nomClientKeySearch.getText().isEmpty()){query += FILTER_CLIENT;};
                break; 
            case 1:
                query += " WHERE strftime('%Y-%W', ld.dateLivraison) = strftime('%Y-%W', 'now')";
                if(!nomClientKeySearch.getText().isEmpty()){query += FILTER_CLIENT;};
                break;
            case 2:
                query += " WHERE strftime('%Y-%m', ld.dateLivraison) = strftime('%Y-%m', 'now')";
                if(!nomClientKeySearch.getText().isEmpty()){query += FILTER_CLIENT;};
                break;
            case 3:
                query += " WHERE ld.dateLivraison >= date('now', '-3 month')";
                if(!nomClientKeySearch.getText().isEmpty()){query += FILTER_CLIENT;};
                break;
            case 4:
                query += " WHERE ld.dateLivraison <= date('now', '+3 month')";
                if(!nomClientKeySearch.getText().isEmpty()){query += FILTER_CLIENT;};
                break;
            default:
                break; // Aucune condition supplémentaire
        }
        // Mettez à jour votre modèle de tableau avec les données filtrées
        refreshTable();
    }
    
     private void filterData(int index){
            menu1.setForeground((index == 1)?Color.decode("#333333"):Color.decode("#999999"));
            line1.setBackground((index == 1)?Color.decode("#F85B32"):Color.decode("#F0F0F0"));
            menu1.setForeground((index == 1)?Color.decode("#333333"):Color.decode("#999999"));
            line1.setBackground((index == 1)?Color.decode("#F85B32"):Color.decode("#F0F0F0"));
            menu2.setForeground((index == 2)?Color.decode("#333333"):Color.decode("#999999"));
            line2.setBackground((index == 2)?Color.decode("#F85B32"):Color.decode("#F0F0F0"));
            menu3.setForeground((index == 3)?Color.decode("#333333"):Color.decode("#999999"));
            line3.setBackground((index == 3)?Color.decode("#F85B32"):Color.decode("#F0F0F0"));
            menu4.setForeground((index == 4)?Color.decode("#333333"):Color.decode("#999999"));
            line4.setBackground((index == 4)?Color.decode("#F85B32"):Color.decode("#F0F0F0"));
             menu5.setForeground((index == 5)?Color.decode("#333333"):Color.decode("#999999"));
            line5.setBackground((index == 5)?Color.decode("#F85B32"):Color.decode("#F0F0F0"));
            switch(index){
        case 1:
        query=SELECT_ALL+DATA_ORDER;
        refreshTable();
        break;
        case 2:
        query=SELECT_ALL_LIVREE+DATA_ORDER;
        refreshTable();
        break;
         case 3:
        query= SELECT_ALL_EN_ATTENTE+DATA_ORDER;
        refreshTable();
        break;
         case 4:
        query=SELECT_ALL_RETARD+DATA_ORDER;
        refreshTable();
        break;
         case 5:
        query=SELECT_ALL_ANNULEE+DATA_ORDER;
        refreshTable();
        break;
    
    }
        
    }
    
    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        query=SELECT_ALL;
        refreshTable();
        nomClientKeySearch.setText("");
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void menu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu2MouseClicked
        filterData(2);
    }//GEN-LAST:event_menu2MouseClicked

    private void menu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu3MouseClicked
        filterData(3);
    }//GEN-LAST:event_menu3MouseClicked

    private void menu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu1MouseClicked
        filterData(1);
    }//GEN-LAST:event_menu1MouseClicked

    private void menu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu4MouseClicked
        filterData(4);
    }//GEN-LAST:event_menu4MouseClicked

    private void menu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_menu5MouseClicked
 
public void refreshTable() {
    String[] column = {"ID", "Référence de la commande", "Montant de la commande", "Date de la commande", "Nom du client", "ID du client"};
    DefaultTableModel model = new DefaultTableModel(null, column);
    try {
        connectiondb();
        if (conn == null) {
            throw new SQLException("Échec de connexion à la base de données.");
        }
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            String[] data = new String[5];
            data[0] = result.getString("idLivDetail");
            data[1] = result.getString("refCommande");
            data[2] = result.getString("montant");
            data[3] = result.getString("dateLivraison");
            data[4] = result.getString("nom_client")+" "+result.getString("prenom_client");
//            data[5] = result.getString("id_client");
            model.addRow(data);
        }
        listeCommandes.setModel(model);
        result.close();
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

private String getClientName(int clientId) {
    String clientName = "";
    try {
        connectiondb();
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT nom, prenom FROM clients WHERE id = ?");
        preparedStatement.setInt(1, clientId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            clientName = resultSet.getString("nom")+" "+resultSet.getString("prenom");
        }
        resultSet.close();
        preparedStatement.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return clientName;
}   
      

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_search;
    private javax.swing.JLabel facture_non_paye;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel line1;
    private javax.swing.JPanel line2;
    private javax.swing.JPanel line3;
    private javax.swing.JPanel line4;
    private javax.swing.JPanel line5;
    private javax.swing.JPanel linegroup;
    private javaapp.component.Table listeCommandes;
    private javax.swing.JLabel menu1;
    private javax.swing.JLabel menu2;
    private javax.swing.JLabel menu3;
    private javax.swing.JLabel menu4;
    private javax.swing.JLabel menu5;
    private javax.swing.JTextField nomClientKeySearch;
    private javax.swing.JPanel page1;
    private javax.swing.JComboBox<String> periodeLivraison;
    private javax.swing.JTextField refCommande;
    private javax.swing.JScrollPane scrollTable;
    // End of variables declaration//GEN-END:variables
}
