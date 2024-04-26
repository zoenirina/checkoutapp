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
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.component.Toast;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class ListeCommande extends javax.swing.JPanel {
private final int borderRadius = 10;
 Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
    String SELECT_ALL="SELECT c.id AS idCommande, c.dateCommande, c.refCommande, c.idClient,c.quantite,c.montant AS montantCommande, c.devise AS deviseCommande,l.dateLivraison,l.refLivraison,f.dateFacture,f.refFacture,p.datePaiement,p.refPaiement, "+
    " CASE WHEN p.id IS NOT NULL THEN 'Payé' WHEN f.id IS NOT NULL THEN 'Facturé' WHEN l.id IS NOT NULL THEN 'En attente' ELSE 'Non livré' END AS statut "+
" FROM commandes c LEFT JOIN livraisons l ON c.id = l.idCommande LEFT JOIN factures f ON l.id = f.idLivraison LEFT JOIN paiements p ON f.id = p.idFacture";
 
    String SELECT_ALL_PAYE=SELECT_ALL+" where p.id IS NOT NULL";
    String SELECT_ALL_EN_ATTENTE=SELECT_ALL+" where l.id IS NOT NULL AND p.id IS NULL";
    String SELECT_ALL_NON_LIVRE=SELECT_ALL+" where l.id IS NULL";
    
    public String query=null;
    public String title="Autres";
    public PreparedStatement stm ;
    public ResultSet resultSet;
    private String idSelected;
    public String btn_save_state= "insert";
    public JPanel pan;
    public Toast t;

    public ListeCommande(JPanel panel) {
        initComponents();
        pan= panel;
//        setOpaque(false);
        query=SELECT_ALL;
        refreshTable();
        scrollTable.getViewport().setBackground(Color.white);
        jScrollPane2.getViewport().setBackground(Color.white);
        filterData(1);
//        btn_delete.setVisible(false);
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
        line4 = new javax.swing.JPanel();
        btn_refresh = new javaapp.component.ButtonRadius();
        line2 = new javax.swing.JPanel();
        line3 = new javax.swing.JPanel();
        menu4 = new javax.swing.JLabel();
        scrollTable = new javax.swing.JScrollPane();
        listeCommandes = new javaapp.component.Table();
        menu3 = new javax.swing.JLabel();
        menu1 = new javax.swing.JLabel();
        nomClientKeySearch = new javax.swing.JTextField();
        btn_search = new javaapp.component.ButtonRadius();
        menu2 = new javax.swing.JLabel();
        line1 = new javax.swing.JPanel();
        page2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        montantTotal = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        btn_print2 = new javaapp.component.ButtonRadius();
        jScrollPane2 = new javax.swing.JScrollPane();
        listeArticleCommande = new javaapp.component.Table();
        dateCommande = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        numClient = new javax.swing.JLabel();
        nomClient = new javax.swing.JLabel();
        telClient = new javax.swing.JLabel();
        emailClient = new javax.swing.JLabel();
        adresseClient = new javax.swing.JLabel();
        montantCommande = new javax.swing.JLabel();
        montantTotal1 = new javax.swing.JLabel();
        doit = new javax.swing.JLabel();
        nomCreateur = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        refCommande = new javax.swing.JLabel();
        numCommande = new javax.swing.JLabel();
        btn_print1 = new javaapp.component.ButtonRadius();
        btn_print = new javaapp.component.ButtonRadius();
        btn_delete = new javaapp.component.ButtonRadius();
        btn_back = new javaapp.component.ButtonRadius();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 1577));

        page1.setBackground(new java.awt.Color(255, 255, 255));

        line4.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout line4Layout = new javax.swing.GroupLayout(line4);
        line4.setLayout(line4Layout);
        line4Layout.setHorizontalGroup(
            line4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        line4Layout.setVerticalGroup(
            line4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

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

        line2.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout line2Layout = new javax.swing.GroupLayout(line2);
        line2.setLayout(line2Layout);
        line2Layout.setHorizontalGroup(
            line2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 74, Short.MAX_VALUE)
        );
        line2Layout.setVerticalGroup(
            line2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        line3.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout line3Layout = new javax.swing.GroupLayout(line3);
        line3.setLayout(line3Layout);
        line3Layout.setHorizontalGroup(
            line3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        line3Layout.setVerticalGroup(
            line3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        menu4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu4.setForeground(new java.awt.Color(153, 153, 153));
        menu4.setText("Non livré");
        menu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu4MouseClicked(evt);
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

        menu2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu2.setForeground(new java.awt.Color(153, 153, 153));
        menu2.setText("Payé");
        menu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu2MouseClicked(evt);
            }
        });

        line1.setBackground(new java.awt.Color(248, 91, 50));

        javax.swing.GroupLayout line1Layout = new javax.swing.GroupLayout(line1);
        line1.setLayout(line1Layout);
        line1Layout.setHorizontalGroup(
            line1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 85, Short.MAX_VALUE)
        );
        line1Layout.setVerticalGroup(
            line1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout page1Layout = new javax.swing.GroupLayout(page1);
        page1.setLayout(page1Layout);
        page1Layout.setHorizontalGroup(
            page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page1Layout.createSequentialGroup()
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(line1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(page1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(menu1)))
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(page1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(menu2))
                            .addGroup(page1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(line2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(line3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(page1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(menu3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(line4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(page1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(menu4)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(scrollTable, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(nomClientKeySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        page1Layout.setVerticalGroup(
            page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page1Layout.createSequentialGroup()
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(menu1)
                            .addComponent(menu2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(line1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(line2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(page1Layout.createSequentialGroup()
                            .addComponent(menu4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(line4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(page1Layout.createSequentialGroup()
                            .addComponent(menu3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(line3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(11, 11, 11)
                .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nomClientKeySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        page2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        montantTotal.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        montantTotal.setForeground(new java.awt.Color(51, 51, 51));
        montantTotal.setText("Total: ");

        jLabel17.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("Creé le");

        btn_print2.setBackground(new java.awt.Color(240, 236, 243));
        btn_print2.setBorder(null);
        btn_print2.setForeground(new java.awt.Color(51, 51, 51));
        btn_print2.setText("Programmer une livraison");
        btn_print2.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_print2.setColorClick(new java.awt.Color(240, 240, 240));
        btn_print2.setColorOver(new java.awt.Color(240, 240, 240));
        btn_print2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btn_print2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_print2ActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(null);

        listeArticleCommande.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(listeArticleCommande);

        dateCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        dateCommande.setForeground(new java.awt.Color(51, 51, 51));
        dateCommande.setText(" 12 Janvier 2024");

        jPanel6.setBackground(new java.awt.Color(245, 245, 245));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(228, 227, 227)));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Client N°");

        numClient.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        numClient.setForeground(new java.awt.Color(255, 102, 0));
        numClient.setText("2334");

        nomClient.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nomClient.setForeground(new java.awt.Color(51, 51, 51));
        nomClient.setText("John DOE");

        telClient.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        telClient.setForeground(new java.awt.Color(51, 51, 51));
        telClient.setText("John DOE");

        emailClient.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        emailClient.setForeground(new java.awt.Color(51, 51, 51));
        emailClient.setText("John DOE");

        adresseClient.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        adresseClient.setForeground(new java.awt.Color(51, 51, 51));
        adresseClient.setText("John DOE");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(numClient))
                    .addComponent(nomClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(telClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(emailClient, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adresseClient))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(numClient))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nomClient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(telClient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emailClient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adresseClient)
                .addGap(21, 21, 21))
        );

        montantCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        montantCommande.setForeground(new java.awt.Color(51, 51, 51));
        montantCommande.setText("57000");

        montantTotal1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        montantTotal1.setForeground(new java.awt.Color(51, 51, 51));
        montantTotal1.setText("Doit:");

        doit.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        doit.setForeground(new java.awt.Color(51, 51, 51));
        doit.setText("57000");

        nomCreateur.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nomCreateur.setForeground(new java.awt.Color(51, 51, 51));
        nomCreateur.setText("par ARIANA Grande");

        jPanel1.setBackground(new java.awt.Color(255, 187, 119));

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Commande N° ");

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("Réference: ");

        refCommande.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        refCommande.setForeground(new java.awt.Color(51, 51, 51));

        numCommande.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        numCommande.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(101, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(numCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dateCommande)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nomCreateur))
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(453, 453, 453)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator1)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(montantTotal)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(montantCommande))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(montantTotal1)
                                        .addGap(18, 18, 18)
                                        .addComponent(doit))))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(204, 204, 204)
                        .addComponent(btn_print2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(dateCommande)
                    .addComponent(nomCreateur))
                .addGap(42, 42, 42)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_print2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(montantTotal)
                    .addComponent(montantCommande))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(montantTotal1)
                    .addComponent(doit))
                .addGap(67, 67, 67))
        );

        btn_print1.setBackground(new java.awt.Color(65, 228, 71));
        btn_print1.setBorder(null);
        btn_print1.setForeground(new java.awt.Color(255, 255, 255));
        btn_print1.setText("Régler un paiement");
        btn_print1.setBorderColor(new java.awt.Color(65, 228, 71));
        btn_print1.setColor(new java.awt.Color(65, 228, 71));
        btn_print1.setColorClick(new java.awt.Color(65, 228, 71));
        btn_print1.setColorOver(new java.awt.Color(65, 228, 71));
        btn_print1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btn_print1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_print1ActionPerformed(evt);
            }
        });

        btn_print.setBackground(new java.awt.Color(240, 236, 243));
        btn_print.setBorder(null);
        btn_print.setForeground(new java.awt.Color(51, 51, 51));
        btn_print.setText("Imprimer une facture");
        btn_print.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_print.setColorClick(new java.awt.Color(240, 240, 240));
        btn_print.setColorOver(new java.awt.Color(240, 240, 240));
        btn_print.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        btn_delete.setBackground(new java.awt.Color(255, 222, 177));
        btn_delete.setBorder(null);
        btn_delete.setForeground(new java.awt.Color(255, 51, 0));
        btn_delete.setText("Supprimer");
        btn_delete.setBorderColor(new java.awt.Color(255, 222, 177));
        btn_delete.setColor(new java.awt.Color(255, 222, 177));
        btn_delete.setColorClick(new java.awt.Color(255, 222, 177));
        btn_delete.setColorOver(new java.awt.Color(255, 202, 135));
        btn_delete.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
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

        javax.swing.GroupLayout page2Layout = new javax.swing.GroupLayout(page2);
        page2.setLayout(page2Layout);
        page2Layout.setHorizontalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_print, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                            .addComponent(btn_print1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        page2Layout.setVerticalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGap(384, 384, 384)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_print1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(page2Layout.createSequentialGroup()
                        .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(page2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(page1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(page1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(page2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        connectiondb();
        try{stm= conn.prepareStatement("delete from exercice where id=?");
            stm.setString(1, idSelected);
            stm.executeUpdate();
            JOptionPane.showMessageDialog(null, "modification réussie!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
            refreshTable();
        }catch(Exception e){
            e.printStackTrace(); ;}
    }//GEN-LAST:event_btn_deleteActionPerformed

    public int insererPaiement(Connection connection, String datePaiement, String refPaiement, int idFacture,
                            int idClient, float montantRecu, float restePaye) throws SQLException {
    String query = "INSERT INTO paiements (datePaiement, refPaiement, idFacture, idClient, montantRecu, restePaye) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    try (PreparedStatement pstmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
        pstmt.setString(1, datePaiement);
        pstmt.setString(2, refPaiement);
        pstmt.setInt(3, idFacture);
        pstmt.setInt(4, idClient);
        pstmt.setFloat(5, montantRecu);
        pstmt.setFloat(6, restePaye);

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Insertion du paiement échouée, aucune ligne affectée.");
        }

        // Récupérer l'ID généré
        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Échec de la récupération de l'ID généré pour le paiement.");
            }
        }
    }
}
    
    public void modifierPaiement(Connection connection, int idPaiement, String datePaiement, String refPaiement,
                             int idFacture, int idClient, float montantRecu, float restePaye) throws SQLException {
    String query = "UPDATE paiements SET datePaiement = ?, refPaiement = ?, idFacture = ?, idClient = ?, " +
            "montantRecu = ?, restePaye = ? WHERE id = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        pstmt.setString(1, datePaiement);
        pstmt.setString(2, refPaiement);
        pstmt.setInt(3, idFacture);
        pstmt.setInt(4, idClient);
        pstmt.setFloat(5, montantRecu);
        pstmt.setFloat(6, restePaye);
        pstmt.setInt(7, idPaiement);

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("La modification du paiement a échoué, aucune ligne affectée.");
        }
    }
}

    private void listeCommandesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listeCommandesMouseClicked
        int selectedRow = listeCommandes.getSelectedRow();
    if (selectedRow != -1) {
        String idCommandeStr = (String) listeCommandes.getModel().getValueAt(selectedRow, 0);
int idCommande = Integer.parseInt(idCommandeStr);
        
        // Step 1: Retrieve Command Details (including content)
       try {
            String[] column = {"ID", "Designation", "Quantite", "Montant"};
   
    DefaultTableModel model = new DefaultTableModel(null, column);
        // Step 1: Retrieve Command Details (including content)
        connectiondb();
        stm = conn.prepareStatement("SELECT cd.idProduit, p.designation, cd.quantite, cd.montant FROM commandeDetails cd INNER JOIN produits p ON cd.idProduit = p.id WHERE cd.idCommande = ?");
        stm.setInt(1 ,idCommande);
       
        resultSet = stm.executeQuery();
        System.out.print(resultSet.getRow());;
        while (resultSet.next()) { 
            String[] data = new String[4];
            
            data[0] = resultSet.getString("idProduit");
            data[1] = resultSet.getString("designation");
            data[2] = resultSet.getString("quantite");
            data[3] = resultSet.getString("montant");
            model.addRow(data);
        }
        listeArticleCommande.setModel(model);
        stm.close();
        conn.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
        
        // Step 3: Retrieve Client Information
        retrieveAndDisplayClientInformation(idCommande);
    }
    }//GEN-LAST:event_listeCommandesMouseClicked

    private void retrieveAndDisplayCommandDetails(String idCommande) {
    try {
            String[] columns = {"ID", "Designation", "Quantite", "Montant"};
    String[] data = new String[4];
    DefaultTableModel model = new DefaultTableModel(null, columns);
        // Step 1: Retrieve Command Details (including content)
        connectiondb();
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery("SELECT cd.idProduit, p.designation, cd.quantite, cd.montant FROM commandeDetails cd INNER JOIN produits p ON cd.idProduit = p.id WHERE cd.idCommande = 1");
//        stm.setInt(1, 1);
//        resultSet = stm.executeQuery();
        while (result.next()) {
            System.out.print(result.getString("idProduit") + result.getString("designation") +result.getString("quantite"));
            data[0] = result.getString("idProduit");
            data[1] = result.getString("designation");
            data[2] = result.getString("quantite");
            data[3] = result.getString("montant");
model.addRow(data);
        }
        listeArticleCommande.setModel(model);
        stm.close();
        conn.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

    private void filterData(int index){
            menu1.setForeground((index == 1)?Color.decode("#333333"):Color.decode("#999999"));
            line1.setBackground((index == 1)?Color.decode("#F85B32"):Color.decode("#999999"));
            menu1.setForeground((index == 1)?Color.decode("#333333"):Color.decode("#999999"));
            line1.setBackground((index == 1)?Color.decode("#F85B32"):Color.decode("#999999"));
            menu2.setForeground((index == 2)?Color.decode("#333333"):Color.decode("#999999"));
            line2.setBackground((index == 2)?Color.decode("#F85B32"):Color.decode("#999999"));
            menu3.setForeground((index == 3)?Color.decode("#333333"):Color.decode("#999999"));
            line3.setBackground((index == 3)?Color.decode("#F85B32"):Color.decode("#999999"));
            menu4.setForeground((index == 4)?Color.decode("#333333"):Color.decode("#999999"));
            line4.setBackground((index == 4)?Color.decode("#F85B32"):Color.decode("#999999"));
            switch(index){
        case 1:
        query=SELECT_ALL;
        refreshTable();
        break;
        case 2:
        query=SELECT_ALL_PAYE;
        refreshTable();
        break;
         case 3:
        query= SELECT_ALL_EN_ATTENTE;
        refreshTable();
        break;
         case 4:
        query=SELECT_ALL_NON_LIVRE;
        refreshTable();
        break;
    
    }
        
    }
    
    private void retrieveAndDisplayClientInformation(int idCommande) {
    try {
        // Step 3: Retrieve Client Information
        connectiondb();
        stm = conn.prepareStatement("SELECT * FROM clients INNER JOIN commandes ON clients.id = commandes.idClient WHERE commandes.id = ?");
        stm.setInt(1, idCommande);
        resultSet = stm.executeQuery();

        if (resultSet.next()) {
            // Set the retrieved client information in your form fields
            nomClient.setText(resultSet.getString("nom")+" "+resultSet.getString("prenom"));
            emailClient.setText(resultSet.getString("email1"));
            telClient.setText(resultSet.getString("tel1"));
            adresseClient.setText(resultSet.getString("adresse"));
//            nifTextClient.setText(resultSet.getString("NIF"));
//            statClient.setText(resultSet.getString("stat"));
        }

        stm.close();
        conn.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
  
    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        query=SELECT_ALL+" WHERE p.designation like '%"+nomClientKeySearch.getText()+"%' or p.refProduit like '%"+nomClientKeySearch.getText()+"%'";
        refreshTable();
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        query=SELECT_ALL;
        refreshTable();
        nomClientKeySearch.setText("");
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_printActionPerformed

    private void btn_print1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_print1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_print1ActionPerformed

    private void btn_print2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_print2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_print2ActionPerformed

    private void menu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu2MouseClicked
        filterData(2);
    }//GEN-LAST:event_menu2MouseClicked

    private void menu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu3MouseClicked
        filterData(3);
    }//GEN-LAST:event_menu3MouseClicked

    private void menu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu4MouseClicked
        filterData(4);
    }//GEN-LAST:event_menu4MouseClicked

    private void menu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu1MouseClicked
        filterData(1);
    }//GEN-LAST:event_menu1MouseClicked
 
    public void refreshTable(){
    String[] column = {"ID", "Réference", "Montant", "Date de la commande", "Nom du client", "Status"};
    String[] data = new String[6];
    DefaultTableModel model = new DefaultTableModel(null, column);
    try {
        connectiondb();
        if (conn == null) {
            throw new SQLException("Échec de connexion à la base de données.");
        }
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            data[0] = result.getString("idCommande");
            data[1] = result.getString("refCommande");
            data[2] = result.getString("montantCommande");
            data[3] = result.getString("dateCommande");
            data[4] = getClientName(result.getInt("idClient"));
            data[5] = result.getString("statut");
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
 
    public void setEmptyForm(){
        nomCreateur.setText("");
        btn_save_state="insert";
    }        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adresseClient;
    private javaapp.component.ButtonRadius btn_back;
    private javaapp.component.ButtonRadius btn_delete;
    private javaapp.component.ButtonRadius btn_print;
    private javaapp.component.ButtonRadius btn_print1;
    private javaapp.component.ButtonRadius btn_print2;
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_search;
    private javax.swing.JLabel dateCommande;
    private javax.swing.JLabel doit;
    private javax.swing.JLabel emailClient;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel line1;
    private javax.swing.JPanel line2;
    private javax.swing.JPanel line3;
    private javax.swing.JPanel line4;
    private javaapp.component.Table listeArticleCommande;
    private javaapp.component.Table listeCommandes;
    private javax.swing.JLabel menu1;
    private javax.swing.JLabel menu2;
    private javax.swing.JLabel menu3;
    private javax.swing.JLabel menu4;
    private javax.swing.JLabel montantCommande;
    private javax.swing.JLabel montantTotal;
    private javax.swing.JLabel montantTotal1;
    private javax.swing.JLabel nomClient;
    private javax.swing.JTextField nomClientKeySearch;
    private javax.swing.JLabel nomCreateur;
    private javax.swing.JLabel numClient;
    private javax.swing.JLabel numCommande;
    private javax.swing.JPanel page1;
    private javax.swing.JPanel page2;
    private javax.swing.JLabel refCommande;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JLabel telClient;
    // End of variables declaration//GEN-END:variables
}
