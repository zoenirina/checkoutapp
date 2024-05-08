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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.component.FormatUtils;
import javaapp.component.Toast;
import javaapp.pagination.EventPagination;
import javaapp.pagination.PaginationItemRenderStyle1;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ZOENIRINA
 */
public class Livraison1 extends javax.swing.JPanel {
private final int borderRadius = 10;
 Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
//    String SELECT_ALL="SELECT ld.*, ld.id as idLivDetail, p.designation AS designation_produit, ld.idProduit, cd.quantite AS quantite_commandee,ld.dateLivraison, cl.id AS id_client, cl.nom AS nom_client,cl.prenom AS prenom_client, c.id as idCommande, c.refCommande, cl.id as id_client " +
//    "FROM livraisonDetails ld INNER JOIN commandeDetails cd ON ld.idCommandeDetail = cd.id INNER JOIN produits p ON ld.idProduit = p.id INNER JOIN commandes c ON cd.idCommande = c.id  INNER JOIN clients cl ON c.idClient = cl.id ";
    
     String SELECT_ALL="SELECT ld.id AS idLivDetail, c.refCommande, ld.montant,ld.status, ld.dateLivraison, cl.nom AS nom_client, cl.prenom AS prenom_client, p.refProduit FROM livraisonDetails ld " +
" JOIN commandeDetails cd ON ld.idCommandeDetail = cd.id JOIN commandes c ON cd.idCommande = c.id JOIN clients cl ON c.idClient = cl.id  LEFT JOIN produits p on ld.idProduit= p.id";
    final static String DATA_ORDER = " ORDER BY ld.dateLivraison DESC";
    final String SELECT_ALL_LIVREE = SELECT_ALL+" where ld.status = 'Livrée' ";
    final String SELECT_ALL_EN_ATTENTE = SELECT_ALL+" WHERE ld.dateLivraison > CURRENT_TIMESTAMP and ld.status = 'En attente'";
    final String SELECT_ALL_RETARD = SELECT_ALL+" WHERE  ld.status = 'En attente' AND ld.dateLivraison  < CURRENT_TIMESTAMP";
    final String SELECT_ALL_ANNULEE = SELECT_ALL+" WHERE ld.status = 'Annulée' ";
//    final static String SELECT_WHERE_ID = "SELECT ld.*, p.designation AS designation_produit,p.refProduit AS refProduit, ld.idProduit, cd.quantite AS quantite_commandee, ld.dateLivraison, cl.id AS id_client, cl.nom AS nom_client, cl.prenom AS prenom_client, c.id as idCommande, c.refCommande FROM livraisonDetails ld INNER JOIN commandeDetails cd ON ld.idCommandeDetail = cd.id INNER JOIN produits p ON ld.idProduit = p.id INNER JOIN commandes c ON cd.idCommande = c.id INNER JOIN clients cl ON c.idClient = cl.id WHERE ld.id = ?";
    final static String SELECT_WHERE_ID = "SELECT ld.*, p.designation AS designation_produit,p.refProduit AS refProduit, ld.idProduit, cd.quantite AS quantite_commandee, ld.dateLivraison, cl.id AS id_client, cl.nom AS nom_client, cl.prenom AS prenom_client, c.id as idCommande, c.refCommande FROM livraisonDetails ld INNER JOIN commandeDetails cd ON ld.idCommandeDetail = cd.id INNER JOIN produits p ON ld.idProduit = p.id INNER JOIN commandes c ON cd.idCommande = c.id INNER JOIN clients cl ON c.idClient = cl.id WHERE c.refCommande=? AND p.refProduit=? ";
    final static String SELECT_MAGASIN_AND_QTE_STOCK = "SELECT m.id AS idMagasin, m.refMagasin AS nomMagasin,  (SELECT COALESCE(SUM(CASE WHEN ms.sens = 'Entrée' THEN ms.quantite ELSE 0 END), 0) -  COALESCE(SUM(CASE WHEN ms.sens = 'Sortie' THEN ms.quantite ELSE 0 END), 0)  FROM mouvementStock ms WHERE ms.idProduit = (select id FROM produits where refProduit=?)  AND ms.idMagasin = m.id) AS quantiteStock FROM magasin m";
    public String query=null;
    public String title="Autres";
    public PreparedStatement stm ;
    public ResultSet resultSet;
    private String idSelected;
    public String btn_save_state= "insert";
    public JPanel pan;
    public Toast t;
    private List<String> magasinList = new ArrayList<>();
    int pageCount=1;
    private int activeMenu=1;
    private FormatUtils formater;
    
    public Livraison1(JPanel panel) {
        initComponents();
        pan= panel;
        init();
    }
    
    private void init(){
    formater = new FormatUtils();
        query= getActiveQuery();
        refreshTable();
        scrollTable.getViewport().setBackground(Color.white);
        filterData();
        
        displayRows(0,13);
        initialisePagination();
        pagination1.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                System.out.print(page);
                 displayRows((page - 1) * 13, page*13);
            }
        });
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
        tableOutput = new javaapp.component.Table();
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
        titre_facture = new javax.swing.JLabel();
        menu4 = new javax.swing.JLabel();
        menu5 = new javax.swing.JLabel();
        scrollTable1 = new javax.swing.JScrollPane();
        tableInput = new javaapp.component.Table();
        jPanel1 = new javax.swing.JPanel();
        pagination1 = new javaapp.pagination.Pagination();
        page2 = new javax.swing.JLayeredPane();
        btn_back = new javaapp.component.ButtonRadius();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        quantiteCommande = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        minuteLivraison = new javax.swing.JComboBox<>();
        heureLivraison = new javax.swing.JComboBox<>();
        dateLivraison = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        quantiteRecu = new javax.swing.JSpinner();
        quantiteValide = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        modifierDateLivraison = new javaapp.component.ButtonRadius();
        btn_marquerLivre = new javaapp.component.ButtonRadius();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        magasin_qte_stock = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        nomClient = new javax.swing.JLabel();
        refCommande1 = new javax.swing.JLabel();
        designationProduit = new javax.swing.JLabel();
        refProduit = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        checkboxLivree = new javax.swing.JPanel();
        livraisonStatus = new javax.swing.JCheckBox();
        jLabel21 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 900));

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

        tableOutput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tableOutput.setForeground(new java.awt.Color(255, 255, 255));
        tableOutput.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableOutput.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        tableOutput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableOutputMouseClicked(evt);
            }
        });
        scrollTable.setViewportView(tableOutput);

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

        titre_facture.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        titre_facture.setForeground(new java.awt.Color(51, 51, 51));
        titre_facture.setText("(0) Livraison en retard");

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

        scrollTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        tableInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tableInput.setForeground(new java.awt.Color(255, 255, 255));
        tableInput.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableInput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableInputMouseClicked(evt);
            }
        });
        scrollTable1.setViewportView(tableInput);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        pagination1.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(367, 367, 367)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout page1Layout = new javax.swing.GroupLayout(page1);
        page1.setLayout(page1Layout);
        page1Layout.setHorizontalGroup(
            page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollTable, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(page1Layout.createSequentialGroup()
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(page1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 118, Short.MAX_VALUE))
                            .addComponent(periodeLivraison, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(page1Layout.createSequentialGroup()
                                .addComponent(nomClientKeySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(page1Layout.createSequentialGroup()
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(linegroup, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(titre_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollTable1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        page1Layout.setVerticalGroup(
            page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(page1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_refresh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nomClientKeySearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(page1Layout.createSequentialGroup()
                        .addComponent(titre_facture)
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
                        .addGap(7, 7, 7)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(periodeLivraison, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                            .addComponent(refCommande))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(scrollTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Client");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Nom");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Commande");

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Reférence de la commande");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Designation du produit");

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Quantite");

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Date de livraison");

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Livraison");

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Heure");

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("Minute");

        minuteLivraison.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        heureLivraison.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18" }));

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Quantite Reçu");

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("Quantite Valide");

        modifierDateLivraison.setBackground(new java.awt.Color(255, 222, 177));
        modifierDateLivraison.setBorder(null);
        modifierDateLivraison.setForeground(new java.awt.Color(248, 91, 50));
        modifierDateLivraison.setText("Modifier");
        modifierDateLivraison.setBorderColor(new java.awt.Color(255, 222, 177));
        modifierDateLivraison.setColor(new java.awt.Color(255, 222, 177));
        modifierDateLivraison.setColorClick(new java.awt.Color(255, 222, 177));
        modifierDateLivraison.setColorOver(new java.awt.Color(255, 222, 177));
        modifierDateLivraison.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        modifierDateLivraison.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifierDateLivraisonActionPerformed(evt);
            }
        });

        btn_marquerLivre.setBackground(new java.awt.Color(248, 91, 50));
        btn_marquerLivre.setBorder(null);
        btn_marquerLivre.setForeground(new java.awt.Color(255, 255, 255));
        btn_marquerLivre.setText("Enregistrer");
        btn_marquerLivre.setBorderColor(new java.awt.Color(248, 91, 50));
        btn_marquerLivre.setColor(new java.awt.Color(248, 91, 50));
        btn_marquerLivre.setColorClick(new java.awt.Color(248, 91, 50));
        btn_marquerLivre.setColorOver(new java.awt.Color(255, 78, 33));
        btn_marquerLivre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_marquerLivre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_marquerLivreActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("Reception");

        jLabel17.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("Reference du produit");

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("Magasin de prelevement");

        jLabel19.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("Reference du magasin...............Qte disponible");

        nomClient.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N

        refCommande1.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N

        designationProduit.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N

        refProduit.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setText("Status");

        checkboxLivree.setBackground(new java.awt.Color(0, 195, 8));

        livraisonStatus.setBackground(new java.awt.Color(0, 195, 8));
        livraisonStatus.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        livraisonStatus.setForeground(new java.awt.Color(255, 255, 255));
        livraisonStatus.setText("Livrée");
        livraisonStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                livraisonStatusMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout checkboxLivreeLayout = new javax.swing.GroupLayout(checkboxLivree);
        checkboxLivree.setLayout(checkboxLivreeLayout);
        checkboxLivreeLayout.setHorizontalGroup(
            checkboxLivreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(livraisonStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        );
        checkboxLivreeLayout.setVerticalGroup(
            checkboxLivreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(livraisonStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jLabel21.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        jLabel21.setText("Selectionner pour marquer un accusé de reception");

        page2.setLayer(btn_back, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel12, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(quantiteCommande, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(minuteLivraison, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(heureLivraison, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(dateLivraison, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(quantiteRecu, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(quantiteValide, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel15, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(modifierDateLivraison, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(btn_marquerLivre, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel16, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel17, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel18, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(magasin_qte_stock, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel19, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(nomClient, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(refCommande1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(designationProduit, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(refProduit, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel20, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(checkboxLivree, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jLabel21, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout page2Layout = new javax.swing.GroupLayout(page2);
        page2.setLayout(page2Layout);
        page2Layout.setHorizontalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page2Layout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(page2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10)
                    .addComponent(jLabel5)
                    .addComponent(jLabel16)
                    .addComponent(jLabel18))
                .addGap(32, 32, 32)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page2Layout.createSequentialGroup()
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(page2Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(176, 176, 176))
                            .addGroup(page2Layout.createSequentialGroup()
                                .addComponent(quantiteRecu)
                                .addGap(43, 43, 43)))
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(quantiteValide)
                            .addGroup(page2Layout.createSequentialGroup()
                                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(page2Layout.createSequentialGroup()
                                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(heureLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14)
                                            .addComponent(minuteLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addComponent(modifierDateLivraison, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page2Layout.createSequentialGroup()
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(refProduit, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(designationProduit, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45))
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(quantiteCommande, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(magasin_qte_stock, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(dateLivraison, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nomClient, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(refCommande1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(page2Layout.createSequentialGroup()
                        .addComponent(checkboxLivree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_marquerLivre, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        page2Layout.setVerticalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page2Layout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomClient, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel12))
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addGroup(page2Layout.createSequentialGroup()
                                .addComponent(refCommande1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refProduit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(page2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(designationProduit, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quantiteCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(page2Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(6, 6, 6)
                                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(page2Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(minuteLivraison))
                                    .addComponent(modifierDateLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(page2Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(67, 67, 67)
                                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(page2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(7, 7, 7)
                                .addComponent(heureLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32)
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_marquerLivre, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(page2Layout.createSequentialGroup()
                                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(page2Layout.createSequentialGroup()
                                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel16))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(quantiteRecu, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page2Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(quantiteValide, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkboxLivree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(page2Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(magasin_qte_stock, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addGap(90, 90, 90))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(page1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(page2))
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(page1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(page2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

 private void refreshPagination(){
        pageCount= (tableInput.getRowCount()%13 == 0) ? tableInput.getRowCount()/13 : tableInput.getRowCount()/13+1 ;
        displayRows(0,13);
        pagination1.setPagegination(1, pageCount);
    }
    private void initialisePagination(){
        pageCount= (tableInput.getRowCount()%13 == 0) ? tableInput.getRowCount()/13 : tableInput.getRowCount()/13+1 ;
        pagination1.setPaginationItemRender(new PaginationItemRenderStyle1());
        pagination1.setPagegination(1, pageCount);
    }
    
    
private void displayRows(int start, int end) {
    for(int i = tableOutput.getRowCount()-1 ; i >= 0; i--){
        ((DefaultTableModel) tableOutput.getModel()).removeRow(i);
        }
    DefaultTableModel model = (DefaultTableModel) tableInput.getModel();
    DefaultTableModel newModel = new DefaultTableModel();

    // Copie des noms de colonnes
    for (int i = 0; i < model.getColumnCount(); i++) {
        newModel.addColumn(model.getColumnName(i));
    }

    // Ajout des lignes de la plage spécifiée
    for (int i = start; i < end && i < model.getRowCount(); i++) {
        Object[] row = new Object[model.getColumnCount()];
        for (int j = 0; j < model.getColumnCount(); j++) {
            row[j] = model.getValueAt(i, j);
        }
        newModel.addRow(row);
    }

    // Appliquer le nouveau modèle à JTable
    tableOutput.setModel(newModel);
}

    private void tableOutputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOutputMouseClicked
    DefaultTableModel model = (DefaultTableModel) tableOutput.getModel();
    int rowIndex = tableOutput.getSelectedRow();
    if (rowIndex != -1) {
        switchToPage(2);
//         int idLivraisonDetail = Integer.parseInt(model.getValueAt(rowIndex, 0).toString());
          String refCommandeSelected = model.getValueAt(rowIndex, 0).toString();
          String refProduitSelected = model.getValueAt(rowIndex, 1).toString();
         populateForm(SELECT_WHERE_ID,refCommandeSelected,refProduitSelected);
//       ModalDetailLivraison modal =new  ModalDetailLivraison( Integer.parseInt(idLivraisonDetail));
//       modal.setVisible(true);
        setInputSelect();
        if("Livrée".equals(model.getValueAt(rowIndex, 5).toString())){
        btn_marquerLivre.setText("Enregistrer");
livraisonStatus.setSelected(true);
        }else{
        btn_marquerLivre.setText("Valider");
livraisonStatus.setSelected(false);
        }
        toogleCheck();
    }
    }//GEN-LAST:event_tableOutputMouseClicked

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
       filterData();
       
    }//GEN-LAST:event_btn_searchActionPerformed

    private void filterData(){
     int selectedOption = periodeLivraison.getSelectedIndex(); 
         String FILTER_CLIENT=" AND ( cl.nom like '%"+nomClientKeySearch.getText()+"%' OR cl.prenom like '%"+nomClientKeySearch.getText()+"%' )";
         String FILTER_REFCOMMANDE=" AND c.refCommande like '%"+refCommande.getText()+"%' ";
         
         query = getActiveQuery();
         query+=(activeMenu == 1)?" WHERE ":" AND ";
        switch (selectedOption) {
            case 0:
                query += " strftime('%Y', ld.dateLivraison) = strftime('%Y', 'now') ";
                
                break; 
            case 1:
                query += " strftime('%Y-%W', ld.dateLivraison) = strftime('%Y-%W', 'now') ";
                break;
            case 2:
                query += " strftime('%Y-%m', ld.dateLivraison) = strftime('%Y-%m', 'now') ";
                break;
            case 3:
                query += " ld.dateLivraison >= date('now', '-3 month') ";
                break;
            case 4:
                query += " ld.dateLivraison <= date('now', '+3 month') ";
                break;
            case 5:
                query += " ld.dateLivraison <= date('now', '+3 month') ";
                break; // Aucune condition supplémentaire
        }
        if(!nomClientKeySearch.getText().isEmpty()){query += FILTER_CLIENT;}
        if(!refCommande.getText().isEmpty()){query += FILTER_REFCOMMANDE;}
        // Mettez à jour votre modèle de tableau avec les données filtrées
        refreshTable();
    }
    
     private void filterData(int index){
         
         setActiveMenu(index);
         
            
            
    query= getActiveQuery();
       refreshTable(); 
    }
     
    private void changeActiveButton(){
            menu1.setForeground((activeMenu == 1)?Color.decode("#333333"):Color.decode("#999999"));
            line1.setBackground((activeMenu == 1)?Color.decode("#F85B32"):Color.decode("#F0F0F0"));
            menu1.setForeground((activeMenu == 1)?Color.decode("#333333"):Color.decode("#999999"));
            line1.setBackground((activeMenu == 1)?Color.decode("#F85B32"):Color.decode("#F0F0F0"));
            menu2.setForeground((activeMenu == 2)?Color.decode("#333333"):Color.decode("#999999"));
            line2.setBackground((activeMenu == 2)?Color.decode("#F85B32"):Color.decode("#F0F0F0"));
            menu3.setForeground((activeMenu == 3)?Color.decode("#333333"):Color.decode("#999999"));
            line3.setBackground((activeMenu == 3)?Color.decode("#F85B32"):Color.decode("#F0F0F0"));
            menu4.setForeground((activeMenu == 4)?Color.decode("#333333"):Color.decode("#999999"));
            line4.setBackground((activeMenu == 4)?Color.decode("#F85B32"):Color.decode("#F0F0F0"));
             menu5.setForeground((activeMenu == 5)?Color.decode("#333333"):Color.decode("#999999"));
            line5.setBackground((activeMenu == 5)?Color.decode("#F85B32"):Color.decode("#F0F0F0"));
    }
     
     private String getActiveQuery(){
     switch(activeMenu){
        case 1:
        return SELECT_ALL;
        case 2:
        return SELECT_ALL_LIVREE;
         case 3:
        return  SELECT_ALL_EN_ATTENTE;
         case 4:
        return SELECT_ALL_RETARD;
         case 5:
        return SELECT_ALL_ANNULEE;
         default:
        return SELECT_ALL;     
    }
     }
    
    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        query = getActiveQuery();
        refreshTable();
        nomClientKeySearch.setText("");
        refCommande.setText("");
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void menu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu2MouseClicked
        setActiveMenu(2);
        query= getActiveQuery();
        refreshTable();
        changeActiveButton();
    }//GEN-LAST:event_menu2MouseClicked

    private void menu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu3MouseClicked
        setActiveMenu(3);
        query= getActiveQuery();
        refreshTable();
        changeActiveButton();
    }//GEN-LAST:event_menu3MouseClicked

    private void menu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu1MouseClicked
        setActiveMenu(1);
        query= getActiveQuery();
        refreshTable();
        changeActiveButton();
    }//GEN-LAST:event_menu1MouseClicked

    private void menu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu4MouseClicked
        setActiveMenu(4);
        query= getActiveQuery();
        refreshTable();
        changeActiveButton();
    }//GEN-LAST:event_menu4MouseClicked

    private void menu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu5MouseClicked
        setActiveMenu(5);
        query= getActiveQuery();
        refreshTable();
        changeActiveButton();
    }//GEN-LAST:event_menu5MouseClicked

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        switchToPage(1);
        magasin_qte_stock.removeAllItems();
        magasinList.clear();
        query= getActiveQuery();
        refreshTable();
    }//GEN-LAST:event_btn_backActionPerformed

    private void switchToPage(int i) {
        switch(i){
            case 1:
                page1.setVisible(true);
                page2.setVisible(false);
                break;
            case 2:
                page1.setVisible(false);
                page2.setVisible(true);
                break;
        }
    }
    
    private void modifierDateLivraisonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifierDateLivraisonActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir enregistrer cette modification?","Mise à jour",JOptionPane.YES_NO_OPTION) == 0){
        try {
            connectiondb(); // Connexion à la base de données
            if (conn == null) {
                throw new SQLException("Échec de connexion à la base de données.");
            }

            //mettre à jour les informations de livraison
            String updateQuery = "UPDATE livraisonDetails SET dateLivraison=? WHERE id = ?";
            stm = conn.prepareStatement(updateQuery);

//            int newQuantiteRecu = (int) quantiteRecu.getValue();
//            int newQuantiteValide = (int) quantiteValide.getValue();
            
            String idLivraisonDetailstr=tableOutput.getModel().getValueAt(tableOutput.getSelectedRow(), 0).toString();
            
            java.util.Date date = dateLivraison.getDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int heure =  Integer.parseInt(heureLivraison.getSelectedItem().toString());
            int minute = Integer.parseInt(minuteLivraison.getSelectedItem().toString());
            calendar.set(Calendar.HOUR_OF_DAY, heure);
            calendar.set(Calendar.MINUTE, minute);

            // Formater la date selon le format "yyyy-MM-dd HH:mm:ss"
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(calendar.getTime());
//            java.sql.Timestamp newDate = java.sql.Timestamp.valueOf(formattedDate);
            
//            stm.setInt(1, newQuantiteRecu);
//            stm.setInt(2, newQuantiteValide);
            stm.setString(1, formattedDate);
            stm.setInt(2, Integer.parseInt(idLivraisonDetailstr));
            
            int rowsAffected = stm.executeUpdate();
            //         JOptionPane.showMessageDialog(null, "Les informations de livraison ont été mises à jour avec succès."+ newDate);
            if (rowsAffected > 0) {
                t = new Toast("Commande enregistrée!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
                query = SELECT_ALL;
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(null, "Échec de l'enregistrement");
            }
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Une erreur s'est produite lors de la mise à jour des informations de livraison.");
        }
//        this.dispose();
        }
    }//GEN-LAST:event_modifierDateLivraisonActionPerformed

     private void populateForm(String query, String refCommandeSelected, String refProduitSelected) {
       try {
        connectiondb();
        if (conn == null) {
            throw new SQLException("Échec de connexion à la base de données.");
        }
        stm = conn.prepareStatement(query);
        stm.setString(1, refCommandeSelected);
        stm.setString(2, refProduitSelected);
        resultSet = stm.executeQuery();
        String refCommande=tableOutput.getModel().getValueAt(tableOutput.getSelectedRow(), 1).toString();
        while (resultSet.next()) {
            refCommande1.setText(refCommande);
            refProduit.setText(resultSet.getString("refProduit"));
            designationProduit.setText(resultSet.getString("designation_produit"));
            quantiteCommande.setValue(Integer.parseInt(resultSet.getString("quantite_commandee")));
            quantiteRecu.setValue(Integer.parseInt(resultSet.getString("quantiteRecu")));
            quantiteValide.setValue(Integer.parseInt(resultSet.getString("quantiteValide")));
            dateLivraison.setDate(resultSet.getDate("dateLivraison"));
            nomClient.setText(resultSet.getString("nom_client") + " " + resultSet.getString("prenom_client"));

        java.sql.Timestamp timestampLivraison = resultSet.getTimestamp("dateLivraison");
            if (timestampLivraison != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(timestampLivraison);
                
                // Extraire l'heure et les minutes
                int heure = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                // Sélectionner les valeurs dans les JComboBox
                heureLivraison.setSelectedItem(String.valueOf(heure));
                minuteLivraison.setSelectedItem(String.valueOf(minute));
            }
                }

                resultSet.close();
                stm.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
    }
    
    private void btn_marquerLivreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_marquerLivreActionPerformed
//        magasinList.toArray(new Integer[0]);
 try {
        connectiondb(); // Connexion à la base de données
        
        if (conn == null) {
            throw new SQLException("Échec de connexion à la base de données.");
        }

        if("Livrée".equals(tableOutput.getModel().getValueAt(tableOutput.getSelectedRow(), 0).toString())){
            String updateQuery = "UPDATE livraisonDetails SET quantiteRecu = ?, quantiteValide = ? WHERE id = ?";
        stm = conn.prepareStatement(updateQuery);
        // Récupérez les nouvelles valeurs des champs de saisie
        int newQuantiteRecu = (int) quantiteRecu.getValue(); 
        int newQuantiteValide = (int) quantiteValide.getValue();
        String idLivraisonDetailstr=tableOutput.getModel().getValueAt(tableOutput.getSelectedRow(), 0).toString();
        stm.setInt(1, newQuantiteRecu);
        stm.setInt(2, newQuantiteValide);
        stm.setInt(3, Integer.parseInt(idLivraisonDetailstr));
        }else{
            String updateQuery = "UPDATE livraisonDetails SET quantiteRecu = ?, quantiteValide = ?, status='Livrée' WHERE id = ?";
        stm = conn.prepareStatement(updateQuery);
        
        // Récupérez les nouvelles valeurs des champs de saisie
        int newQuantiteRecu = (int) quantiteRecu.getValue(); 
        int newQuantiteValide = (int) quantiteValide.getValue();
        String idLivraisonDetailstr=tableOutput.getModel().getValueAt(tableOutput.getSelectedRow(), 0).toString();
        stm.setInt(1, newQuantiteRecu);
        stm.setInt(2, newQuantiteValide);
        stm.setInt(3, Integer.parseInt(idLivraisonDetailstr));
        }
        int rowsAffected = stm.executeUpdate();
        
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Article marqué livrée!");
            query=SELECT_ALL;
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, "Échec de la mise à jour");
        }
        
        // Fermez la connexion et le statement
        stm.close();
        conn.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Une erreur s'est produite lors de la mise à jour des informations de livraison.");
    }
        
//        connectiondb();
//                DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                LocalDateTime now = LocalDateTime.now();
//    try {
//        stm = conn.prepareStatement("INSERT INTO mouvementStock (description, idProduit, quantite, dateMouvement, idTypeMouvement, sens, idMagasin, idExercice, idCreateur) " +
//                "VALUES (?, (SELECT id from produits WHERE refProduit=?), ?, ?, (select code_type FROM type_mouvement where type=?), ?, (SELECT id FROM magasin where refMagasin=?), (SELECT id FROM exercice where status=1), (SELECT idUtilisateur from utilisateur WHERE nomUtilisateur=?))");
//        stm.setString(1, "sortie de stock");
//        stm.setString(2, (String) refProduit.getSelectedItem()); // Remplacez idProduit par l'ID du produit à transférer
//        stm.setInt(3, (int) quantite.getValue()); // Remplacez quantite par la quantité à transférer
//        stm.setString(4, (String) now.format(dtFormat));
//        stm.setString(5, (String) typeMouvement.getSelectedItem()); // Remplacez idTypeMouvementSortie par l'ID du type de mouvement de sortie
//        stm.setString(6, "Sortie");
//        stm.setString(7,refMagasin.getSelectedItem().toString()); // Remplacez idMagasinSource par l'ID du magasin source
//        stm.setString(8, (String) nomCreateur.getSelectedItem()); // Remplacez idCreateur par l'ID de l'utilisateur qui effectue le transfert
//
//        stm.executeUpdate();
//        stm.close();
//        conn.close();
//    } catch (SQLException ex) {
//        Logger.getLogger(Livraison1.class.getName()).log(Level.SEVERE, null, ex);
//    }
                
    }//GEN-LAST:event_btn_marquerLivreActionPerformed

    private void livraisonStatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_livraisonStatusMouseClicked
        toogleCheck();
    }//GEN-LAST:event_livraisonStatusMouseClicked

    private void tableInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInputMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableInputMouseClicked
 
public void refreshTable() {
    String[] column = {"Reférence commande", "Reférence produit", "Nom du client", "Montant", "Date de livraison","Status"};
    DefaultTableModel model = new DefaultTableModel(null, column);
    try {
        connectiondb();
        if (conn == null) {
            throw new SQLException("Échec de connexion à la base de données.");
        }
//        query+=DATA_ORDER;
        PreparedStatement statement = conn.prepareStatement(query+DATA_ORDER);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            String[] data = new String[6];
            data[0] = result.getString("refCommande");
            data[1] = result.getString("refProduit");
            data[2] = result.getString("nom_client")+" "+result.getString("prenom_client");
            data[3] = formater.formatFloat(result.getFloat("montant"));
            data[4] = result.getString("dateLivraison");
            data[5] = result.getString("status");
            model.addRow(data);
        }
        tableInput.setModel(model);
        refreshPagination();
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

  private void setInputSelect(){
            
      try {
                connectiondb();
                stm = conn.prepareStatement(SELECT_MAGASIN_AND_QTE_STOCK);
                stm.setString(1, refProduit.getText());
                resultSet = stm.executeQuery();
                magasin_qte_stock.addItem("");
                while (resultSet.next()) {
                    magasinList.add(resultSet.getString("nomMagasin"));
                    magasin_qte_stock.addItem(resultSet.getString("nomMagasin")+"................................................................"+resultSet.getInt("quantiteStock"));
                }
                resultSet.close();
                stm.close();
                conn.close();
        } catch (SQLException e) {
        }
    }

  private void toogleCheck(){
  quantiteRecu.setEnabled(livraisonStatus.isSelected());
  quantiteValide.setEnabled(livraisonStatus.isSelected());
  btn_marquerLivre.setVisible(livraisonStatus.isSelected());
  checkboxLivree.setBackground((livraisonStatus.isSelected())?Color.decode("#00C308"):Color.decode("#FF3333"));
  }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_back;
    private javaapp.component.ButtonRadius btn_marquerLivre;
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_search;
    private javax.swing.JPanel checkboxLivree;
    private com.toedter.calendar.JDateChooser dateLivraison;
    private javax.swing.JLabel designationProduit;
    private javax.swing.JComboBox<String> heureLivraison;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel line1;
    private javax.swing.JPanel line2;
    private javax.swing.JPanel line3;
    private javax.swing.JPanel line4;
    private javax.swing.JPanel line5;
    private javax.swing.JPanel linegroup;
    private javax.swing.JCheckBox livraisonStatus;
    private javax.swing.JComboBox<String> magasin_qte_stock;
    private javax.swing.JLabel menu1;
    private javax.swing.JLabel menu2;
    private javax.swing.JLabel menu3;
    private javax.swing.JLabel menu4;
    private javax.swing.JLabel menu5;
    private javax.swing.JComboBox<String> minuteLivraison;
    private javaapp.component.ButtonRadius modifierDateLivraison;
    private javax.swing.JLabel nomClient;
    private javax.swing.JTextField nomClientKeySearch;
    private javax.swing.JPanel page1;
    private javax.swing.JLayeredPane page2;
    private javaapp.pagination.Pagination pagination1;
    private javax.swing.JComboBox<String> periodeLivraison;
    private javax.swing.JSpinner quantiteCommande;
    private javax.swing.JSpinner quantiteRecu;
    private javax.swing.JSpinner quantiteValide;
    private javax.swing.JTextField refCommande;
    private javax.swing.JLabel refCommande1;
    private javax.swing.JLabel refProduit;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JScrollPane scrollTable1;
    private javaapp.component.Table tableInput;
    private javaapp.component.Table tableOutput;
    private javax.swing.JLabel titre_facture;
    // End of variables declaration//GEN-END:variables

    /**
     * @param activeMenu the activeMenu to set
     */
    public void setActiveMenu(int activeMenu) {
        this.activeMenu = activeMenu;
    }
}
