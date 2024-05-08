/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.page;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
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
import javaapp.component.FormatUtils;
import javaapp.component.PdfReceiptGenerator;
import javaapp.component.Toast;
import javaapp.pagination.EventPagination;
import javaapp.pagination.PaginationItemRenderStyle1;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ZOENIRINA
 */
public class Facture extends javax.swing.JPanel {
private final int borderRadius = 10;
 Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
//    String SELECT_ALL="SELECT c.id AS idCommande, c.dateCommande, c.refCommande, f.id AS idFacturation, c.idClient,c.quantite,c.montant AS montantCommande, c.devise AS deviseCommande,l.dateLivraison,l.refLivraison, l.description as descriptionLivraison, l.frais,f.dateFacture,f.refFacture,p.datePaiement,p.refPaiement,p.montantRecu, p.restePaye,   "+
//    " CASE WHEN p.id IS NOT NULL THEN 'Payé'  ELSE 'Non payé' END AS statut "+
//    " FROM commandes c LEFT JOIN livraisons l ON c.id = l.idCommande LEFT JOIN factures f ON l.id = f.idLivraison LEFT JOIN paiements p ON f.id = p.idFacture";
 
    final static String SELECT_ALL="SELECT c.id AS idCommande, c.dateCommande, c.refCommande, f.id AS idFacturation,f.montant as montantNetapayer, c.idClient,c.quantite,c.montant AS montantCommande, c.devise AS deviseCommande,l.dateLivraison,l.refLivraison, l.description as descriptionLivraison, l.frais,f.dateFacture,f.refFacture,p.datePaiement,p.refPaiement,p.montantRecu, p.restePaye, cl.nom as nom_client, cl.prenom as prenom_client , "+
    " CASE    WHEN p.restePaye = c.montant AND p.montantRecu = 0 THEN 'Non payé' " +
    " WHEN p.restePaye > 0 AND p.restePaye < c.montant THEN 'Payé partiellement' " +
    " WHEN p.restePaye <= 0 THEN 'Payé' " +
    " ELSE 'Non payé' " +
    " END AS statut "+
    " FROM commandes c LEFT JOIN livraisons l ON c.id = l.idCommande LEFT JOIN factures f ON l.id = f.idLivraison LEFT JOIN paiements p ON f.id = p.idFacture left join clients cl  on f.idClient=cl.id ";
    final static String SELECT_ALL_PAYE=SELECT_ALL+" where p.restePaye = 0";
    final static String SELECT_ALL_NON_PAYE=SELECT_ALL+" where  p.montantRecu IS NULL OR p.id IS NULL or p.montantRecu = 0  ";
    final static String SELECT_ALL_PAYE_AU_PARTIEL=SELECT_ALL+" where p.restePaye > 0 AND p.restePaye < c.montant ";
    
    public String query=null;
    public String title="Autres";
    public PreparedStatement stm ;
    public ResultSet resultSet;
    private String idSelected;
    public String btn_save_state= "insert";
    public JPanel pan;
    public Toast t;
    int pageCount=1;
    private int activeMenu=1;
    private PdfReceiptGenerator pdfPrinter;
    private FormatUtils formater;

    public Facture(JPanel panel) {
        initComponents();
        pan= panel;
        formater= new FormatUtils();
        query=SELECT_ALL;
        refreshTable();
        scrollTable.getViewport().setBackground(Color.white);
        jScrollPane3.getViewport().setBackground(Color.white);
        pdfPrinter= new PdfReceiptGenerator();
        filterData(1);
        switchToPage(1);
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
        menu3 = new javax.swing.JLabel();
        menu1 = new javax.swing.JLabel();
        refCommandeKeySearch = new javax.swing.JTextField();
        btn_search = new javaapp.component.ButtonRadius();
        menu2 = new javax.swing.JLabel();
        linegroup = new javax.swing.JPanel();
        line1 = new javax.swing.JPanel();
        line2 = new javax.swing.JPanel();
        line4 = new javax.swing.JPanel();
        line3 = new javax.swing.JPanel();
        facture_non_paye = new javax.swing.JLabel();
        nomClientKeySearch = new javax.swing.JTextField();
        menu5 = new javax.swing.JLabel();
        menu6 = new javax.swing.JLabel();
        menu4 = new javax.swing.JLabel();
        scrollTable1 = new javax.swing.JScrollPane();
        tableInput = new javaapp.component.Table();
        jPanel1 = new javax.swing.JPanel();
        pagination1 = new javaapp.pagination.Pagination();
        page2 = new javax.swing.JPanel();
        btn_print = new javaapp.component.ButtonRadius();
        btn_back = new javaapp.component.ButtonRadius();
        jPanel7 = new javax.swing.JPanel();
        numero_facture = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        numFac = new javax.swing.JLabel();
        fac_refFacture = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        fac_nom_client = new javax.swing.JLabel();
        fac_id_client = new javax.swing.JLabel();
        fac_adresse_client = new javax.swing.JLabel();
        fac_email_client = new javax.swing.JLabel();
        fac_tel_client = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        idCommande = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        fac_refCommande = new javax.swing.JTextField();
        fac_montant = new javax.swing.JSpinner();
        fac_frais = new javax.swing.JSpinner();
        fac_description = new javax.swing.JLabel();
        fac_dateCommande = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        fac_descriptionCommande = new javax.swing.JTextPane();
        btn_delete_commande = new javaapp.component.ButtonRadius();
        btn_modify = new javaapp.component.ButtonRadius();
        panelBorderRound1 = new javaapp.component.PanelBorderRound();
        jLabel8 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        refPaiement = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        datePaiement = new com.toedter.calendar.JDateChooser();
        jLabel59 = new javax.swing.JLabel();
        restePaiement = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        montantVerse = new javax.swing.JLabel();
        payerTotalite = new javaapp.component.ButtonRadius();
        payerPartie = new javaapp.component.ButtonRadius();
        jLabel61 = new javax.swing.JLabel();
        montantNet2 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        montantRecu = new javax.swing.JSpinner();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        panelBorderRound2 = new javaapp.component.PanelBorderRound();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableListCommande = new javaapp.component.Table();
        jSeparator2 = new javax.swing.JSeparator();
        subTotal = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        charge = new javax.swing.JLabel();
        montantNet1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btn_modify_invoice_item = new javaapp.component.ButtonRadius();
        btn_delete_invoice_item = new javaapp.component.ButtonRadius();
        enregistrerPaiement1 = new javaapp.component.ButtonRadius();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 1577));

        page1.setBackground(new java.awt.Color(255, 255, 255));
        page1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        page1.add(btn_refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 140, 40, 40));

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

        page1.add(scrollTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 892, 610));

        menu3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu3.setForeground(new java.awt.Color(153, 153, 153));
        menu3.setText("Payé au partiel");
        menu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu3MouseClicked(evt);
            }
        });
        page1.add(menu3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, -1, -1));

        menu1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu1.setForeground(new java.awt.Color(51, 51, 51));
        menu1.setText("Tous");
        menu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu1MouseClicked(evt);
            }
        });
        page1.add(menu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, -1, -1));
        page1.add(refCommandeKeySearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, 260, 37));

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
        page1.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 140, 40, 40));

        menu2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu2.setForeground(new java.awt.Color(153, 153, 153));
        menu2.setText("Payé");
        menu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu2MouseClicked(evt);
            }
        });
        page1.add(menu2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, -1, -1));

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
            .addGap(0, 0, Short.MAX_VALUE)
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

        javax.swing.GroupLayout line3Layout = new javax.swing.GroupLayout(line3);
        line3.setLayout(line3Layout);
        line3Layout.setHorizontalGroup(
            line3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 162, Short.MAX_VALUE)
        );
        line3Layout.setVerticalGroup(
            line3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                .addGap(0, 0, Short.MAX_VALUE))
        );
        linegroupLayout.setVerticalGroup(
            linegroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(line1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(line2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(line4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(line3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        page1.add(linegroup, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 82, 440, 3));

        facture_non_paye.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        facture_non_paye.setForeground(new java.awt.Color(51, 51, 51));
        facture_non_paye.setText("(2) Facture non payé");
        page1.add(facture_non_paye, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 240, -1));
        page1.add(nomClientKeySearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 140, 270, 37));

        menu5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu5.setForeground(new java.awt.Color(51, 51, 51));
        menu5.setText("Nom ou prénom du client");
        page1.add(menu5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 120, -1, -1));

        menu6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu6.setForeground(new java.awt.Color(51, 51, 51));
        menu6.setText("Réference de la commande");
        page1.add(menu6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, -1, -1));

        menu4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu4.setForeground(new java.awt.Color(153, 153, 153));
        menu4.setText("Non payé");
        menu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu4MouseClicked(evt);
            }
        });
        page1.add(menu4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, -1, -1));

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

        page1.add(scrollTable1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 910, 892, 0));

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

        page1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 810, 890, -1));

        page2.setBackground(new java.awt.Color(255, 255, 255));

        btn_print.setBackground(new java.awt.Color(240, 236, 243));
        btn_print.setBorder(null);
        btn_print.setForeground(new java.awt.Color(51, 51, 51));
        btn_print.setText("Imprimer une facture");
        btn_print.setBorderColor(new java.awt.Color(240, 236, 243));
        btn_print.setColor(new java.awt.Color(240, 236, 243));
        btn_print.setColorClick(new java.awt.Color(255, 255, 255));
        btn_print.setColorOver(new java.awt.Color(247, 251, 246));
        btn_print.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
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

        jPanel7.setBackground(new java.awt.Color(102, 102, 255));
        jPanel7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        numero_facture.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        numero_facture.setForeground(new java.awt.Color(255, 255, 255));
        numero_facture.setText("FACTURE N°");

        jLabel22.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Date: 14/06/2024");

        numFac.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        numFac.setForeground(new java.awt.Color(255, 255, 255));
        numFac.setText("001");

        fac_refFacture.setBackground(new java.awt.Color(204, 204, 255));
        fac_refFacture.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        fac_refFacture.setForeground(new java.awt.Color(1, 42, 124));
        fac_refFacture.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fac_refFacture.setBorder(null);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(numero_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numFac, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fac_refFacture, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 371, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(46, 46, 46))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numero_facture)
                    .addComponent(jLabel22)
                    .addComponent(numFac)
                    .addComponent(fac_refFacture, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel44.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(51, 51, 51));
        jLabel44.setText("Client");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 13, Short.MAX_VALUE)
                .addComponent(jLabel44))
        );

        jPanel8.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 70, -1));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel41.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(102, 102, 102));
        jLabel41.setText("Adresse");

        jLabel42.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(102, 102, 102));
        jLabel42.setText("Contact");

        jLabel40.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(102, 102, 102));
        jLabel40.setText("Identification");

        fac_nom_client.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        fac_nom_client.setForeground(new java.awt.Color(51, 51, 51));
        fac_nom_client.setText("John DOE");

        fac_id_client.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        fac_id_client.setForeground(new java.awt.Color(51, 51, 51));
        fac_id_client.setText("001");

        fac_adresse_client.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        fac_adresse_client.setForeground(new java.awt.Color(51, 51, 51));
        fac_adresse_client.setText("Lot 23/5 Andakivy");

        fac_email_client.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        fac_email_client.setForeground(new java.awt.Color(51, 51, 51));
        fac_email_client.setText("Email: johndoe@gmail.com");

        fac_tel_client.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        fac_tel_client.setForeground(new java.awt.Color(51, 51, 51));
        fac_tel_client.setText("Tel: +262 60 388 30");

        jLabel23.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel23.setText("Client N°");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fac_nom_client, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel23)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(fac_id_client, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fac_adresse_client, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(111, 111, 111)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(fac_email_client, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(fac_tel_client, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(fac_email_client)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fac_tel_client))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fac_id_client)
                            .addComponent(fac_adresse_client)
                            .addComponent(jLabel23))
                        .addGap(3, 3, 3)
                        .addComponent(fac_nom_client)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 890, 110));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel45.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(51, 51, 51));
        jLabel45.setText("Commande n°");

        idCommande.setText("001");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(idCommande))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 150, 30));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel46.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(102, 102, 102));
        jLabel46.setText("Date de la commande");

        jLabel47.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(102, 102, 102));
        jLabel47.setText("Montant");

        jLabel49.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(102, 102, 102));
        jLabel49.setText("Reférence de la commande");

        jLabel50.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(102, 102, 102));
        jLabel50.setText("Description");

        jLabel51.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(102, 102, 102));
        jLabel51.setText("Frais");

        fac_refCommande.setBackground(new java.awt.Color(204, 204, 255));
        fac_refCommande.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        fac_refCommande.setForeground(new java.awt.Color(0, 0, 102));
        fac_refCommande.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fac_refCommande.setBorder(null);

        fac_montant.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        fac_montant.setEnabled(false);

        fac_frais.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        fac_dateCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        fac_descriptionCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jScrollPane1.setViewportView(fac_descriptionCommande);

        btn_delete_commande.setBackground(new java.awt.Color(255, 222, 177));
        btn_delete_commande.setBorder(null);
        btn_delete_commande.setForeground(new java.awt.Color(255, 66, 28));
        btn_delete_commande.setText("Supprimer la commande");
        btn_delete_commande.setBorderColor(new java.awt.Color(255, 222, 177));
        btn_delete_commande.setColor(new java.awt.Color(255, 222, 177));
        btn_delete_commande.setColorClick(new java.awt.Color(255, 222, 177));
        btn_delete_commande.setColorOver(new java.awt.Color(255, 222, 177));
        btn_delete_commande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_delete_commande.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_commandeActionPerformed(evt);
            }
        });

        btn_modify.setBackground(new java.awt.Color(248, 91, 50));
        btn_modify.setBorder(null);
        btn_modify.setForeground(new java.awt.Color(255, 255, 255));
        btn_modify.setText("Modifier");
        btn_modify.setBorderColor(new java.awt.Color(248, 91, 50));
        btn_modify.setColor(new java.awt.Color(248, 91, 50));
        btn_modify.setColorClick(new java.awt.Color(248, 91, 50));
        btn_modify.setColorOver(new java.awt.Color(248, 91, 50));
        btn_modify.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_modify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modifyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fac_refCommande)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addGap(178, 178, 178)
                                .addComponent(fac_description))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel49)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel46)
                            .addComponent(fac_dateCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fac_montant, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fac_frais))
                .addGap(51, 51, 51)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(btn_delete_commande, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_modify, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fac_refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel51)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fac_frais, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel46)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fac_dateCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fac_description, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47)
                            .addComponent(jLabel50))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fac_montant, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_modify, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_delete_commande, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel8.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 890, 240));

        panelBorderRound1.setBackground(new java.awt.Color(12, 50, 126));

        jLabel8.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 255));
        jLabel8.setText("Paiement");

        jLabel54.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Reférence de paiement");

        refPaiement.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        refPaiement.setBorder(null);

        jLabel55.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Date de paiement");

        datePaiement.setBackground(new java.awt.Color(102, 102, 255));
        datePaiement.setEnabled(false);

        jLabel59.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Reste");

        restePaiement.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        restePaiement.setForeground(new java.awt.Color(255, 255, 255));
        restePaiement.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        restePaiement.setText("0");
        restePaiement.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel60.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(204, 204, 255));
        jLabel60.setText("Montant déjà versé");

        montantVerse.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        montantVerse.setForeground(new java.awt.Color(204, 204, 255));
        montantVerse.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        montantVerse.setText("0");

        payerTotalite.setBackground(new java.awt.Color(102, 102, 255));
        payerTotalite.setBorder(null);
        payerTotalite.setForeground(new java.awt.Color(255, 255, 255));
        payerTotalite.setText("Payer la totalité");
        payerTotalite.setBorderColor(new java.awt.Color(102, 102, 255));
        payerTotalite.setColor(new java.awt.Color(102, 102, 255));
        payerTotalite.setColorClick(new java.awt.Color(102, 102, 255));
        payerTotalite.setColorOver(new java.awt.Color(102, 102, 255));
        payerTotalite.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        payerTotalite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payerTotaliteActionPerformed(evt);
            }
        });

        payerPartie.setBackground(new java.awt.Color(12, 50, 126));
        payerPartie.setBorder(null);
        payerPartie.setForeground(new java.awt.Color(255, 255, 255));
        payerPartie.setText("Payer une partie");
        payerPartie.setBorderColor(new java.awt.Color(248, 248, 255));
        payerPartie.setColor(new java.awt.Color(12, 50, 126));
        payerPartie.setColorClick(new java.awt.Color(12, 50, 126));
        payerPartie.setColorOver(new java.awt.Color(12, 50, 126));
        payerPartie.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        payerPartie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payerPartieActionPerformed(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("Montant net à payer");

        montantNet2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        montantNet2.setForeground(new java.awt.Color(255, 255, 255));
        montantNet2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        montantNet2.setText("0");

        jLabel62.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("Description");

        jTextPane1.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        jScrollPane2.setViewportView(jTextPane1);

        jLabel63.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("ou");

        jLabel64.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("Montant reçu");

        javax.swing.GroupLayout panelBorderRound1Layout = new javax.swing.GroupLayout(panelBorderRound1);
        panelBorderRound1.setLayout(panelBorderRound1Layout);
        panelBorderRound1Layout.setHorizontalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(montantRecu)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel62)
                    .addComponent(jLabel55)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(refPaiement)
                    .addComponent(datePaiement, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound1Layout.createSequentialGroup()
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel61, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(montantVerse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(restePaiement, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(montantNet2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(payerTotalite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(payerPartie, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel63)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        panelBorderRound1Layout.setVerticalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refPaiement, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(datePaiement, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(montantNet2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(montantVerse, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(restePaiement, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(payerTotalite, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(jLabel64)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(montantRecu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(payerPartie, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );

        panelBorderRound2.setBackground(new java.awt.Color(252, 252, 252));

        jScrollPane3.setBorder(null);

        tableListCommande.setForeground(new java.awt.Color(51, 51, 51));
        tableListCommande.setModel(new javax.swing.table.DefaultTableModel(
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
        tableListCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jScrollPane3.setViewportView(tableListCommande);

        subTotal.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        subTotal.setForeground(new java.awt.Color(51, 51, 51));
        subTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        subTotal.setText("0");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Sous-total");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Charge");

        charge.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        charge.setForeground(new java.awt.Color(51, 51, 51));
        charge.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        charge.setText("0");

        montantNet1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        montantNet1.setForeground(new java.awt.Color(248, 91, 50));
        montantNet1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        montantNet1.setText("0");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Net à payer");

        jLabel7.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        jLabel7.setText("Détails de la commande");

        btn_modify_invoice_item.setBackground(new java.awt.Color(102, 102, 255));
        btn_modify_invoice_item.setBorder(null);
        btn_modify_invoice_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/iconModify.png"))); // NOI18N
        btn_modify_invoice_item.setBorderColor(new java.awt.Color(102, 102, 255));
        btn_modify_invoice_item.setColor(new java.awt.Color(102, 102, 255));
        btn_modify_invoice_item.setColorClick(new java.awt.Color(255, 255, 255));
        btn_modify_invoice_item.setColorOver(new java.awt.Color(51, 51, 255));
        btn_modify_invoice_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modify_invoice_itemActionPerformed(evt);
            }
        });

        btn_delete_invoice_item.setBackground(new java.awt.Color(255, 204, 204));
        btn_delete_invoice_item.setBorder(null);
        btn_delete_invoice_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/btnDelete.png"))); // NOI18N
        btn_delete_invoice_item.setBorderColor(new java.awt.Color(255, 204, 204));
        btn_delete_invoice_item.setColor(new java.awt.Color(255, 204, 204));
        btn_delete_invoice_item.setColorClick(new java.awt.Color(255, 204, 204));
        btn_delete_invoice_item.setColorOver(new java.awt.Color(255, 142, 142));
        btn_delete_invoice_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_invoice_itemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorderRound2Layout = new javax.swing.GroupLayout(panelBorderRound2);
        panelBorderRound2.setLayout(panelBorderRound2Layout);
        panelBorderRound2Layout.setHorizontalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound2Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound2Layout.createSequentialGroup()
                        .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(219, 219, 219)
                        .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(charge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(montantNet1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(subTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelBorderRound2Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_modify_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btn_delete_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(26, 26, 26))
        );
        panelBorderRound2Layout.setVerticalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound2Layout.createSequentialGroup()
                .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel7))
                    .addGroup(panelBorderRound2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_delete_invoice_item, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_modify_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subTotal)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(charge))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(montantNet1)
                    .addComponent(jLabel6))
                .addContainerGap())
        );

        enregistrerPaiement1.setBackground(new java.awt.Color(248, 91, 50));
        enregistrerPaiement1.setBorder(null);
        enregistrerPaiement1.setForeground(new java.awt.Color(255, 255, 255));
        enregistrerPaiement1.setText("Valider la facture");
        enregistrerPaiement1.setBorderColor(new java.awt.Color(248, 91, 50));
        enregistrerPaiement1.setColor(new java.awt.Color(248, 91, 50));
        enregistrerPaiement1.setColorClick(new java.awt.Color(248, 91, 50));
        enregistrerPaiement1.setColorOver(new java.awt.Color(248, 91, 50));
        enregistrerPaiement1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        enregistrerPaiement1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enregistrerPaiement1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout page2Layout = new javax.swing.GroupLayout(page2);
        page2.setLayout(page2Layout);
        page2Layout.setHorizontalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(page2Layout.createSequentialGroup()
                                .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(520, 520, 520)
                                .addComponent(enregistrerPaiement1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(page2Layout.createSequentialGroup()
                                    .addComponent(panelBorderRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(871, 871, 871))
        );
        page2Layout.setVerticalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(enregistrerPaiement1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBorderRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelBorderRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(page1, javax.swing.GroupLayout.PREFERRED_SIZE, 994, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(page2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

     private String getActiveQuery(){
     switch(activeMenu){
        case 1:
        return SELECT_ALL;
        case 2:
        return SELECT_ALL_PAYE;
         case 3:
        return  SELECT_ALL_PAYE_AU_PARTIEL;
         case 4:
        return SELECT_ALL_NON_PAYE;
         default:
        return SELECT_ALL;     
    }
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
//             menu5.setForeground((activeMenu == 5)?Color.decode("#333333"):Color.decode("#999999"));
//            line5.setBackground((activeMenu == 5)?Color.decode("#F85B32"):Color.decode("#F0F0F0"));
    }
       
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

    private void tableOutputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOutputMouseClicked
       switchToPage(2);
       String refCommande=null;
        int selectedRow = tableOutput.getSelectedRow();
    if (selectedRow != -1) {
        //int idCommande = Integer.parseInt(idCommandeStr);
        
        // Step 1: Retrieve Command Details (including content)
       try {
            refCommande = (String) tableOutput.getModel().getValueAt(selectedRow, 1);
            System.out.print("reference de la commande"+refCommande);
            String[] column = {"Article","PU", "Qte", "Montant","Livraison"};
   
    DefaultTableModel model = new DefaultTableModel(null, column);
        // Step 1: Retrieve Command Details (including content)
        connectiondb();
        stm = conn.prepareStatement("SELECT cd.idProduit, p.designation, p.PU, cd.quantite, cd.montant, ld.dateLivraison, ld.status FROM livraisonDetails ld INNER JOIN commandeDetails cd ON ld.idCommandeDetail=cd.id  INNER JOIN produits p ON ld.idProduit = p.id WHERE cd.idCommande = (SELECT id FROM commandes where refCommande=?)");
        
        stm.setString(1 ,refCommande);
        resultSet = stm.executeQuery();
        System.out.print(resultSet.getRow());
        while (resultSet.next()) { 
            Object[] data = new Object[5];
            data[0] = resultSet.getString("designation");
            data[1] = formater.formatFloat(resultSet.getFloat("PU"));
            data[2] = resultSet.getString("quantite");
            data[3] = formater.formatFloat(resultSet.getFloat("PU")*resultSet.getFloat("quantite"));
            data[4] = resultSet.getString("status");
            model.addRow(data);
        }
        tableListCommande.setModel(model);
//        listeArticleCommande.setModel(model);
        stm.close();
        conn.close();
    } catch (SQLException ex) {
    }
        
       refCommande = (String) tableOutput.getModel().getValueAt(selectedRow, 1);
        // Step 3: Retrieve Client Information
        retrieveAndDisplayClientInformation(refCommande);
        retrieveAndDisplayCommandeInformation(refCommande);
    }
    }//GEN-LAST:event_tableOutputMouseClicked

    private void filterData(int index){
        setActiveMenu(index);
        query= getActiveQuery();
        refreshTable();
        changeActiveButton();
        
    }
        public void setActiveMenu(int activeMenu) {
        this.activeMenu = activeMenu;
    }
        
    private void retrieveAndDisplayClientInformation(String refCommande) {
    try {
        // Step 3: Retrieve Client Information
        connectiondb();
        stm = conn.prepareStatement("SELECT * FROM clients INNER JOIN commandes ON clients.id = commandes.idClient WHERE commandes.refCommande = ?");
        stm.setString(1, refCommande);
        resultSet = stm.executeQuery();

        if (resultSet.next()) {
            // Set the retrieved client information in your form fields
            fac_nom_client.setText(resultSet.getString("nom")+" "+resultSet.getString("prenom"));
            fac_id_client.setText(resultSet.getString("id"));
            fac_email_client.setText(resultSet.getString("email1"));
            fac_tel_client.setText(resultSet.getString("tel1"));
            fac_adresse_client.setText(resultSet.getString("adresse"));
//            nifTextClient.setText(resultSet.getString("NIF"));
//            statClient.setText(resultSet.getString("stat"));
        }

        stm.close();
        conn.close();
    } catch (SQLException ex) {
    }
    }
  
        private void retrieveAndDisplayCommandeInformation(String refCommande) {
    try {
        // Step 3: Retrieve Client Information
        connectiondb();
        stm = conn.prepareStatement(SELECT_ALL+" WHERE c.refCommande=?");
        stm.setString(1, refCommande);
        resultSet = stm.executeQuery();

        if (resultSet.next()) {
            // Set the retrieved client information in your form fields
            idCommande.setText(resultSet.getString("idCommande"));
            numFac.setText(resultSet.getString("idFacturation"));
            fac_refFacture.setText(resultSet.getString("refFacture"));
            fac_dateCommande.setText(resultSet.getString("dateCommande"));
            fac_refCommande.setText(resultSet.getString("refCommande"));
            fac_montant.setValue(resultSet.getFloat("montantCommande"));
            subTotal.setText(formater.formatFloat(resultSet.getFloat("montantCommande")));
            fac_frais.setValue(resultSet.getFloat("frais"));
            charge.setText(formater.formatFloat(resultSet.getFloat("frais")));
            fac_descriptionCommande.setText(resultSet.getString("descriptionLivraison"));
            
            //information sur la paiement     montantRecu, p.restePaye
            refPaiement.setText(resultSet.getString("refPaiement"));
            datePaiement.setDate(resultSet.getDate("datePaiement"));
            
            montantVerse.setText(formater.formatFloat(resultSet.getFloat("montantRecu")));
//            descriptionPaiement.setText(resultSet.getString("descriptionPaiement"));
            float somme=resultSet.getFloat("montantCommande")+resultSet.getFloat("frais");
            montantNet1.setText(formater.formatFloat(somme));
            montantNet2.setText(formater.formatFloat(somme));
            restePaiement.setText(formater.formatFloat(resultSet.getFloat("restePaye")));
//            nifTextClient.setText(resultSet.getString("NIF"));
//            statClient.setText(resultSet.getString("stat"));
        }

        stm.close();
        conn.close();
    } catch (SQLException ex) {
    }
    }
        
    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
//        String searchRefCommande=" c.refCommande like '%"+refCommandeKeySearch.getText()+"%' ";
//        String searchClient=" c.nom like '%"+nomClientKeySearch1.getText()+"%' ";

    String FILTER_CLIENT="  ( cl.nom like '%"+nomClientKeySearch.getText()+"%' OR cl.prenom like '%"+nomClientKeySearch.getText()+"%') ";
    String FILTER_REFCOMMANDE=" c.refCommande like '%"+refCommandeKeySearch.getText()+"%' ";
    
    query = getActiveQuery();
    query+=(activeMenu == 1)?" WHERE ":" AND ";
    if(!refCommandeKeySearch.getText().isEmpty() && nomClientKeySearch.getText().trim().isEmpty()){query += FILTER_REFCOMMANDE;}
    if(refCommandeKeySearch.getText().isEmpty() && !nomClientKeySearch.getText().trim().isEmpty()){query += FILTER_CLIENT;}
    if(!refCommandeKeySearch.getText().isEmpty() && !nomClientKeySearch.getText().trim().isEmpty()){query += FILTER_REFCOMMANDE +" AND "+ FILTER_CLIENT;}
    
        refreshTable();
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        query = getActiveQuery();
        refreshTable();
        refCommandeKeySearch.setText("");
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
         pdfPrinter.printPDF(HEIGHT, WIDTH);
    }//GEN-LAST:event_btn_printActionPerformed

    private void menu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu2MouseClicked
        filterData(2);
    }//GEN-LAST:event_menu2MouseClicked

    private void menu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu3MouseClicked
        filterData(3);
    }//GEN-LAST:event_menu3MouseClicked

    private void menu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu1MouseClicked
        filterData(1);
    }//GEN-LAST:event_menu1MouseClicked

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        refreshTable();
        switchToPage(1);
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_modifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modifyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_modifyActionPerformed

    private void btn_delete_commandeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_commandeActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer cette commande?","Confirmation de suppression",JOptionPane.YES_NO_OPTION) == 0){
        try {
        connectiondb();
        stm = conn.prepareStatement("DELETE FROM commandes WHERE refCommande=?");
        stm.setString(1,fac_refCommande.getText());
        stm.executeUpdate();
        stm.close();
        conn.close();
        JOptionPane.showMessageDialog(null, "Facture marqué payée!","Succès",JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException ex) {
    }
        }
    }//GEN-LAST:event_btn_delete_commandeActionPerformed

    private void payerTotaliteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payerTotaliteActionPerformed
        int selectedRow = tableOutput.getSelectedRow();
        int rowsAffected=0;
        String sql;
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
          if(JOptionPane.showConfirmDialog(null, "Encaissement total  de la commande "+fac_refCommande.getText()+" d'une somme de "+formater.parseFloat(montantNet2.getText())+" au nom de "+fac_nom_client.getText()+"?","Confirmation de paiement",JOptionPane.YES_NO_OPTION) == 0){
        connectiondb();
   
        
        try {
            switch((String) tableOutput.getModel().getValueAt(selectedRow, 5)){
                case "Payé partiellement":
//                   JOptionPane.showMessageDialog(null, "Payé partiellement"+rowsAffected,"Sucess",JOptionPane.INFORMATION_MESSAGE);
                    
                        connectiondb();
                      sql = " UPDATE paiements SET montantRecu = ?, restePaye = 0 WHERE refPaiement = ?";
                    
                        stm = conn.prepareStatement(sql);
//                        stm.setFloat(1, Float.parseFloat(montantRecu.getValue().toString()));
                        stm.setFloat(1, formater.parseFloat(montantNet2.getText()));
//                        stm.setFloat(3, formater.parseFloat(restePaiement.getText()));//refPaiement
//                        stm.setString(4, refPaiement.getText());
                        stm.setString(2, refPaiement.getText());
                         rowsAffected =stm.executeUpdate();
                    
                    break;
                case "Non payé":
                    connectiondb();
              
                     sql = "INSERT INTO paiements (datePaiement, refPaiement, idFacture, idClient, montantRecu, restePaye) " +
                    "VALUES (?, ?, ?, ?, ?, 0)";
                    stm = conn.prepareStatement(sql);
                    stm.setString(1, (String) now.format(dtFormat));
                    stm.setString(2, refPaiement.getText());
                    stm.setInt(3, Integer.parseInt(numFac.getText()));
                    stm.setString(4, fac_id_client.getText());;
                    stm.setFloat(5, formater.parseFloat(montantNet2.getText()));
//                    stm.setFloat(6, Float.parseFloat(montantNet2.getText()) - Float.parseFloat(montantRecu.getValue().toString()));//net_a_payer
                    rowsAffected =stm.executeUpdate();
//                     JOptionPane.showMessageDialog(null, "Non payé","Sucess",JOptionPane.INFORMATION_MESSAGE);
                    break;
            } 
            if(rowsAffected == 0){
                JOptionPane.showMessageDialog(null, "Echec de l'enregistrement du paiement!","Erreur",JOptionPane.ERROR_MESSAGE);
            }else{
                try {  
                insererEntreeCaisse(formater.parseFloat(restePaiement.getText()));     //encaisser le montant réçu
                JOptionPane.showMessageDialog(null, "Facture marqué payée!","Succès",JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                Logger.getLogger(Facture.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
           } catch (SQLException ex) {
    }
   refreshTable();
          }
    }//GEN-LAST:event_payerTotaliteActionPerformed

    private void insererEntreeCaisse( float montantEncaisse) throws SQLException{
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        stm = conn.prepareStatement("insert into entree_caisse(idSourceReference,montant,devise,idCreateur,idExercice,dateEntree,description) "
                + " values ((SELECT id from paiements where refPaiement=?),?,(SELECT devise from config where idConfig=1),(select idUtilisateur from utilisateur where status=1),(SELECT id FROM exercice WHERE status=1),?,?)");
//        stm.setString(1, null);
        stm.setString(1, refPaiement.getText());
        stm.setFloat(2, montantEncaisse);
        stm.setString(3,  (String) now.format(dtFormat));
        stm.setString(4, " Paiement reference "+refPaiement.getText()+" de la commande "+fac_refCommande.getText());
        stm.executeUpdate();
    }
    private void menu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu4MouseClicked
        filterData(4);
    }//GEN-LAST:event_menu4MouseClicked

    private void tableInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInputMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableInputMouseClicked

    private void enregistrerPaiement1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enregistrerPaiement1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_enregistrerPaiement1ActionPerformed

    private void payerPartieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payerPartieActionPerformed
         int selectedRow = tableOutput.getSelectedRow();
        int rowsAffected=0;
        String sql;
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        connectiondb();
   if(Float.parseFloat(montantRecu.getValue().toString()) !=0){
   if(JOptionPane.showConfirmDialog(null, "Encaissement d'une somme de "+montantRecu.getValue().toString()+" de la commande "+fac_refCommande.getText()+" au nom de "+fac_nom_client.getText()+"?","Confirmation de paiement",JOptionPane.YES_NO_OPTION) == 0){
            switch((String) tableOutput.getModel().getValueAt(selectedRow, 5)){
                case "Payé partiellement":
                    
                    connectiondb();
                    sql = " UPDATE paiements SET  montantRecu = (SELECT montantRecu + ?  from paiements WHERE refPaiement = ? ), restePaye = (SELECT restePaye - ?  from paiements WHERE refPaiement = ? ) WHERE refPaiement = ?";
                    try {
                    stm = conn.prepareStatement(sql);
                    stm.setFloat(1, Float.parseFloat(montantRecu.getValue().toString()));
                    stm.setString(2, refPaiement.getText());
                    stm.setFloat(3, Float.parseFloat(montantRecu.getValue().toString()));//refPaiement
                    stm.setString(4, refPaiement.getText());
                    stm.setString(5, refPaiement.getText());
                    rowsAffected =stm.executeUpdate();
//                    JOptionPane.showMessageDialog(null, "Versement enregistré","Sucess",JOptionPane.INFORMATION_MESSAGE);

                    } catch (SQLException ex) {
                        Logger.getLogger(Facture.class.getName()).log(Level.SEVERE, null, ex);
                    }
        
                break;
                case "Non payé":
                    
                    connectiondb();
                    sql = "INSERT INTO paiements (datePaiement, refPaiement, idFacture, idClient, montantRecu, restePaye) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
                    try {
                        stm = conn.prepareStatement(sql);
                        stm.setString(1, (String) now.format(dtFormat));
                        stm.setString(2, refPaiement.getText());
                        stm.setInt(3, Integer.parseInt(numFac.getText()));
                        stm.setString(4, fac_id_client.getText());
                        stm.setFloat(5, Float.parseFloat(montantRecu.getValue().toString()));
                        stm.setFloat(6, formater.parseFloat(montantNet2.getText()) - Float.parseFloat(montantRecu.getValue().toString()));//net_a_payer
                        rowsAffected = stm.executeUpdate();
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(Facture.class.getName()).log(Level.SEVERE, null, ex);
                    }
         
                    break;
            }
             if(rowsAffected == 0){
                 JOptionPane.showMessageDialog(null, "Echec de l'enregistrement du paiement!","Erreur",JOptionPane.ERROR_MESSAGE);
             }else{
             try {  
                 insererEntreeCaisse(Float.parseFloat(montantRecu.getValue().toString()));
                 JOptionPane.showMessageDialog(null, "Paiement enregistré","Succès",JOptionPane.INFORMATION_MESSAGE);
             } catch (SQLException ex) {
                 Logger.getLogger(Facture.class.getName()).log(Level.SEVERE, null, ex);
             }
             }     
             refreshTable();
   }
   }else{
                    JOptionPane.showMessageDialog(null, "Veuillez saisir le montant à verser","Erreur",JOptionPane.ERROR_MESSAGE);
   }
    }//GEN-LAST:event_payerPartieActionPerformed

    private void btn_modify_invoice_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modify_invoice_itemActionPerformed
        int selectedRow = tableListCommande.getSelectedRow();
        String designation = tableListCommande.getModel().getValueAt(selectedRow, 0).toString();
        String prix = tableListCommande.getModel().getValueAt(selectedRow, 1).toString();
        int quantite = Integer.parseInt(tableListCommande.getModel().getValueAt(selectedRow, 2).toString());
        float montant = formater.parseFloat(tableListCommande.getModel().getValueAt(selectedRow, 3).toString());
        
        JPanel panel = new JPanel(new GridLayout(0, 2));

        panel.add(new JLabel("Nom:"));
        JTextField nameField = new JTextField(designation);
        nameField.setEditable(false);
        panel.add(nameField);
        
        panel.add(new JLabel("Prix unitaire:"));
        JTextField priceField = new JTextField(prix);
        priceField.setEditable(false);
        panel.add(priceField);

        panel.add(new JLabel("Quantité:"));
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(quantite, 1, 100, 1));
        panel.add(quantitySpinner);
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Entrer la quantité désirée",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            connectiondb();
            try{
                stm = conn.prepareStatement("UPDATE mouvementStock SET quantite = ? WHERE idCommandeDet IN (SELECT id from commandeDetails WHERE idCommande=? and idProduit=(SELECT id from produits WHERE designation=?) ) ");
                stm.setInt(1, Integer.parseInt(quantitySpinner.getValue().toString()));
                stm.setString(2, idCommande.getText().trim());
                stm.setString(3, designation);
                resultSet = stm.executeQuery();
                
            }catch(Exception ex){

            }
        }
        tableListCommande.clearSelection();

        
    }//GEN-LAST:event_btn_modify_invoice_itemActionPerformed

    private void btn_delete_invoice_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_invoice_itemActionPerformed
        int selectedRow = tableListCommande.getSelectedRow();
        String designationProduitSelected = tableListCommande.getModel().getValueAt(selectedRow, 0).toString();
        float montant = formater.parseFloat(tableListCommande.getModel().getValueAt(selectedRow, 3).toString());
        if (selectedRow != -1) {
          if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer cette ligne?","Confirmation de suppression",JOptionPane.YES_NO_OPTION) == 0){
             
        try {
        connectiondb();
        stm = conn.prepareStatement("DELETE FROM commandeDetails WHERE idCommande =(SELECT id FROM commandes WHERE refCommande=?)  and idProduit IN (SELECT id from produits where designation =?)");
        stm.setString(1,fac_refCommande.getText().trim());
         stm.setString(2,designationProduitSelected);
        stm.executeUpdate();
        
        
        
        stm.close();
        conn.close();
        tableListCommande.clearSelection();
        ((DefaultTableModel) tableListCommande.getModel()).removeRow(selectedRow);
        JOptionPane.showMessageDialog(null, "Ligne supprimé!","Succès",JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException ex) {
    }
        } 
        }
    }//GEN-LAST:event_btn_delete_invoice_itemActionPerformed
 
    public void updateMontant() throws SQLException{
        stm = conn.prepareStatement("UPDATE facture set montant = ? WHERE idFacture=?");
        stm.setString(1,fac_refFacture.getText().trim());
        stm.setInt(2,Integer.parseInt(numFac.getText()));
        stm.executeUpdate();
        
        stm = conn.prepareStatement("UPDATE commandes set montant = ( SELECT SUM(montant) from commandeDetails WHERE idCommande in (SELECT id from commandes WHERE refCommande=?) and idProduit= (SELECT id from produits WHERE designation=?) ) WHERE refCommande=? ");
        stm.setString(1,fac_refFacture.getText().trim());
        stm.setInt(2,Integer.parseInt(numFac.getText()));
        stm.executeUpdate();
        
    }
    public void refreshTable(){
    String[] column = {"Réference facture", "Réference commande","Nom du client", "Montant net à payer", "Reste à payer", "Status"};
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
            data[0] = result.getString("refFacture");
            data[1] = result.getString("refCommande");
            data[2] = getClientName(result.getInt("idClient"));
            data[3] = formater.formatFloat(result.getFloat("montantNetapayer"));
            data[4] = formater.formatFloat((result.getString("restePaye") == null) ? result.getFloat("montantNetapayer"):result.getFloat("restePaye"));
            data[5] = result.getString("statut");
            model.addRow(data);
        }
        tableInput.setModel(model);
        result.close();
        statement.close();
        displayRows(0,13);
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
//        nomCreateur.setText("");
        btn_save_state="insert";
    }        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_back;
    private javaapp.component.ButtonRadius btn_delete_commande;
    private javaapp.component.ButtonRadius btn_delete_invoice_item;
    private javaapp.component.ButtonRadius btn_modify;
    private javaapp.component.ButtonRadius btn_modify_invoice_item;
    private javaapp.component.ButtonRadius btn_print;
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_search;
    private javax.swing.JLabel charge;
    private com.toedter.calendar.JDateChooser datePaiement;
    private javaapp.component.ButtonRadius enregistrerPaiement1;
    private javax.swing.JLabel fac_adresse_client;
    private javax.swing.JLabel fac_dateCommande;
    private javax.swing.JLabel fac_description;
    private javax.swing.JTextPane fac_descriptionCommande;
    private javax.swing.JLabel fac_email_client;
    private javax.swing.JSpinner fac_frais;
    private javax.swing.JLabel fac_id_client;
    private javax.swing.JSpinner fac_montant;
    private javax.swing.JLabel fac_nom_client;
    private javax.swing.JTextField fac_refCommande;
    private javax.swing.JTextField fac_refFacture;
    private javax.swing.JLabel fac_tel_client;
    private javax.swing.JLabel facture_non_paye;
    private javax.swing.JLabel idCommande;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JPanel line1;
    private javax.swing.JPanel line2;
    private javax.swing.JPanel line3;
    private javax.swing.JPanel line4;
    private javax.swing.JPanel linegroup;
    private javax.swing.JLabel menu1;
    private javax.swing.JLabel menu2;
    private javax.swing.JLabel menu3;
    private javax.swing.JLabel menu4;
    private javax.swing.JLabel menu5;
    private javax.swing.JLabel menu6;
    private javax.swing.JLabel montantNet1;
    private javax.swing.JLabel montantNet2;
    private javax.swing.JSpinner montantRecu;
    private javax.swing.JLabel montantVerse;
    private javax.swing.JTextField nomClientKeySearch;
    private javax.swing.JLabel numFac;
    private javax.swing.JLabel numero_facture;
    private javax.swing.JPanel page1;
    private javax.swing.JPanel page2;
    private javaapp.pagination.Pagination pagination1;
    private javaapp.component.PanelBorderRound panelBorderRound1;
    private javaapp.component.PanelBorderRound panelBorderRound2;
    private javaapp.component.ButtonRadius payerPartie;
    private javaapp.component.ButtonRadius payerTotalite;
    private javax.swing.JTextField refCommandeKeySearch;
    private javax.swing.JTextField refPaiement;
    private javax.swing.JLabel restePaiement;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JScrollPane scrollTable1;
    private javax.swing.JLabel subTotal;
    private javaapp.component.Table tableInput;
    private javaapp.component.Table tableListCommande;
    private javaapp.component.Table tableOutput;
    // End of variables declaration//GEN-END:variables

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
}
