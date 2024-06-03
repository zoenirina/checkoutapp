/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.unusedpackage;

import javaapp.page.vente.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.Profile;
import javaapp.component.DateConverter;
import javaapp.component.FormatUtils;
import javaapp.component.ModalActionEvent;
import javaap.modal.ModalBonLivraison;
import javaap.modal.ModalFactures;
import javaapp.PrinterPDF.PdfReceiptGenerator;
import javaapp.component.Toast;
import javaapp.dao.CommandeCDAO;
import javaapp.dao.CommandeDetailDAO;
import javaapp.dao.FactureDAO;
import javaapp.dao.LivraisonDAO;
import javaapp.dao.ProduitDAO;
import javaapp.factory.DAOFactory;
import javaapp.pagination.PaginationItemRenderStyle1;
import javaapp.swing.TableStatusCellRender;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
/**
 *
 * @author ZOENIRINA
 */
public class Commande1 extends javax.swing.JPanel {
    private ModalActionEvent event;
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
    private final int activeMenu=1;
    private PdfReceiptGenerator pdfPrinter;
    private FormatUtils formater;
    private javaapp.bean.CommandeC commandeSelected = null;
    ModalBonLivraison modal = null;
    javaapp.bean.Livraison livSelected = null;
     javaapp.bean.Facture  facSelected = null;

    public Commande1(JPanel panel) {
        initComponents();
        pan= panel;
        init();
    }
    
    public void init(){
        formater= new FormatUtils();
        query=SELECT_ALL;
        refreshTable(DAOFactory.getCommandeDAO().select());
        scrollTable.getViewport().setBackground(Color.white);
        jScrollPane3.getViewport().setBackground(Color.white);
        pdfPrinter= new PdfReceiptGenerator();
//        filterData(1);
        switchToPage(1);
        displayRows(0,13);
        initialisePagination();
        pagination1.addEventPagination((int page) -> {
            System.out.print(page);
            displayRows((page - 1) * 13, page*13);
        });
        tableOutput.getColumnModel().getColumn(6).setCellRenderer(new TableStatusCellRender());

        event =  new ModalActionEvent(){
            @Override
            public void onEdit(int row){
                btn_creer_facture.setVisible(false);
                t = new Toast("Création de la facture réussie!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
                System.out.print("Evenement dans commande"+row);
            }
        };
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
        btn_search = new javaapp.component.ButtonRadius();
        facture_non_paye = new javax.swing.JLabel();
        nomClientKeySearch = new javax.swing.JTextField();
        menu5 = new javax.swing.JLabel();
        scrollTable1 = new javax.swing.JScrollPane();
        tableInput = new javaapp.component.Table();
        jPanel1 = new javax.swing.JPanel();
        pagination1 = new javaapp.pagination.Pagination();
        date1 = new com.toedter.calendar.JDateChooser();
        date2 = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btn_search1 = new javaapp.component.ButtonRadius();
        panelBorderRound1 = new javaapp.component.PanelBorderRound();
        jLabel5 = new javax.swing.JLabel();
        page2 = new javax.swing.JPanel();
        btn_back = new javaapp.component.ButtonRadius();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableListCommande = new javaapp.component.Table();
        jLabel7 = new javax.swing.JLabel();
        btn_delete_invoice_item = new javaapp.component.ButtonRadius();
        btn_modify_invoice_item = new javaapp.component.ButtonRadius();
        btn_creer_facture = new javaapp.component.ButtonRadius();
        btn_modify1 = new javaapp.component.ButtonRadius();
        btn_delete_commande = new javaapp.component.ButtonRadius();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        nomClient = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        btn_modify = new javaapp.component.ButtonRadius();
        jLabel52 = new javax.swing.JLabel();
        fac_id_client = new javax.swing.JLabel();
        nomClient1 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        vendeur = new javax.swing.JTextField();
        btn_modify_invoice_item3 = new javaapp.component.ButtonRadius();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        totalHT = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        totalTVA = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        totalTTC = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        totalNET = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        fac_refCommande = new javax.swing.JLabel();
        idCommande = new javax.swing.JLabel();
        btn_valider = new javaapp.component.ButtonRadius();
        btn_stat_facture = new javaapp.component.ButtonRadius();
        btn_stat_livre = new javaapp.component.ButtonRadius();
        btn_annuler = new javaapp.component.ButtonRadius();
        jPanel4 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        fac_descriptionCommande = new javax.swing.JTextPane();
        btn_modif_comm = new javaapp.component.ButtonRadius();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jPanel12 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        btn_modifier = new javaapp.component.ButtonRadius();
        jPanel7 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        description_fac = new javax.swing.JTextPane();
        adresse_fac = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btn_modif_fac = new javaapp.component.ButtonRadius();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        fac_refCommande1 = new javax.swing.JLabel();
        idCommande1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        bl_description = new javax.swing.JTextPane();
        adresse_livraison = new javax.swing.JTextField();
        btn_modif_bl = new javaapp.component.ButtonRadius();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btn_modify_HOUR = new javaapp.component.ButtonRadius();
        jPanel17 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        refLivraison = new javax.swing.JLabel();
        idLivraison = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 1300));

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
        page1.add(btn_refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 90, 37, 37));

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
        tableOutput.setRowHeight(45);
        tableOutput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableOutputMouseClicked(evt);
            }
        });
        scrollTable.setViewportView(tableOutput);

        page1.add(scrollTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 892, 610));

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
        page1.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 37, 37));

        facture_non_paye.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        facture_non_paye.setForeground(new java.awt.Color(51, 51, 51));
        facture_non_paye.setText("(2) Commande à valider");
        page1.add(facture_non_paye, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 260, -1));
        page1.add(nomClientKeySearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 90, 270, 37));

        menu5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu5.setForeground(new java.awt.Color(51, 51, 51));
        menu5.setText("Mot clé");
        page1.add(menu5, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, -1, -1));

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

        page1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 760, 890, -1));

        date1.setBackground(new java.awt.Color(255, 255, 255));
        page1.add(date1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 180, 37));

        date2.setBackground(new java.awt.Color(255, 255, 255));
        page1.add(date2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 170, 37));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Date fin");
        page1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 70, -1));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Date début");
        page1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        btn_search1.setBackground(new java.awt.Color(240, 236, 243));
        btn_search1.setBorder(null);
        btn_search1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/search.png"))); // NOI18N
        btn_search1.setBorderColor(new java.awt.Color(240, 236, 243));
        btn_search1.setColor(new java.awt.Color(240, 236, 243));
        btn_search1.setColorClick(new java.awt.Color(255, 255, 255));
        btn_search1.setColorOver(new java.awt.Color(240, 236, 243));
        btn_search1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search1ActionPerformed(evt);
            }
        });
        page1.add(btn_search1, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 90, 37, 37));

        panelBorderRound1.setBackground(new java.awt.Color(102, 102, 255));

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Documents (5)");

        javax.swing.GroupLayout panelBorderRound1Layout = new javax.swing.GroupLayout(panelBorderRound1);
        panelBorderRound1.setLayout(panelBorderRound1Layout);
        panelBorderRound1Layout.setHorizontalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        panelBorderRound1Layout.setVerticalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        page1.add(panelBorderRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 10, 170, 40));

        page2.setBackground(new java.awt.Color(255, 255, 255));

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

        jLabel7.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        jLabel7.setText("Détails de la commande");

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

        btn_modify_invoice_item.setBackground(new java.awt.Color(153, 153, 255));
        btn_modify_invoice_item.setBorder(null);
        btn_modify_invoice_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/iconModify.png"))); // NOI18N
        btn_modify_invoice_item.setBorderColor(new java.awt.Color(153, 153, 255));
        btn_modify_invoice_item.setColor(new java.awt.Color(153, 153, 255));
        btn_modify_invoice_item.setColorClick(new java.awt.Color(153, 153, 255));
        btn_modify_invoice_item.setColorOver(new java.awt.Color(137, 137, 240));
        btn_modify_invoice_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modify_invoice_itemActionPerformed(evt);
            }
        });

        btn_creer_facture.setBackground(new java.awt.Color(153, 153, 255));
        btn_creer_facture.setBorder(null);
        btn_creer_facture.setForeground(new java.awt.Color(255, 255, 255));
        btn_creer_facture.setText("Convertir en facture");
        btn_creer_facture.setBorderColor(new java.awt.Color(153, 153, 255));
        btn_creer_facture.setColor(new java.awt.Color(153, 153, 255));
        btn_creer_facture.setColorClick(new java.awt.Color(153, 153, 255));
        btn_creer_facture.setColorOver(new java.awt.Color(132, 132, 232));
        btn_creer_facture.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_creer_facture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_creer_factureActionPerformed(evt);
            }
        });

        btn_modify1.setBackground(new java.awt.Color(153, 153, 255));
        btn_modify1.setBorder(null);
        btn_modify1.setForeground(new java.awt.Color(255, 255, 255));
        btn_modify1.setText("Convertir en bon de livraison");
        btn_modify1.setBorderColor(new java.awt.Color(153, 153, 255));
        btn_modify1.setColor(new java.awt.Color(153, 153, 255));
        btn_modify1.setColorClick(new java.awt.Color(153, 153, 255));
        btn_modify1.setColorOver(new java.awt.Color(132, 132, 232));
        btn_modify1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_modify1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modify1ActionPerformed(evt);
            }
        });

        btn_delete_commande.setBackground(new java.awt.Color(255, 226, 226));
        btn_delete_commande.setBorder(null);
        btn_delete_commande.setForeground(new java.awt.Color(255, 57, 57));
        btn_delete_commande.setText("Supprimer");
        btn_delete_commande.setBorderColor(new java.awt.Color(255, 226, 226));
        btn_delete_commande.setColor(new java.awt.Color(255, 226, 226));
        btn_delete_commande.setColorClick(new java.awt.Color(255, 226, 226));
        btn_delete_commande.setColorOver(new java.awt.Color(255, 204, 204));
        btn_delete_commande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_delete_commande.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_commandeActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(241, 248, 240));

        jPanel3.setBackground(new java.awt.Color(153, 153, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(440, 4));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        nomClient.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nomClient.setForeground(new java.awt.Color(51, 51, 51));

        jLabel43.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(43, 40, 54));
        jLabel43.setText("Client n°");

        btn_modify.setBorder(null);
        btn_modify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/iconModify.png"))); // NOI18N
        btn_modify.setBorderColor(new java.awt.Color(153, 153, 255));
        btn_modify.setColor(new java.awt.Color(102, 102, 255));
        btn_modify.setColorClick(new java.awt.Color(255, 255, 255));
        btn_modify.setColorOver(new java.awt.Color(122, 122, 255));
        btn_modify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modifyActionPerformed(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(43, 40, 54));
        jLabel52.setText("Etat de la commande");

        fac_id_client.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        fac_id_client.setForeground(new java.awt.Color(43, 40, 54));
        fac_id_client.setText("001");

        nomClient1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nomClient1.setForeground(new java.awt.Color(51, 51, 51));
        nomClient1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel44.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(43, 40, 54));
        jLabel44.setText("Vendeur");

        vendeur.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        vendeur.setForeground(new java.awt.Color(51, 51, 51));

        btn_modify_invoice_item3.setBackground(new java.awt.Color(153, 153, 255));
        btn_modify_invoice_item3.setBorder(null);
        btn_modify_invoice_item3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/iconModify.png"))); // NOI18N
        btn_modify_invoice_item3.setBorderColor(new java.awt.Color(153, 153, 255));
        btn_modify_invoice_item3.setColor(new java.awt.Color(153, 153, 255));
        btn_modify_invoice_item3.setColorClick(new java.awt.Color(153, 153, 255));
        btn_modify_invoice_item3.setColorOver(new java.awt.Color(51, 51, 255));
        btn_modify_invoice_item3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modify_invoice_item3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nomClient1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fac_id_client, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(nomClient, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_modify, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(vendeur, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_modify_invoice_item3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vendeur, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_modify_invoice_item3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(fac_id_client))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_modify, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nomClient, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomClient1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        jPanel5.setBackground(new java.awt.Color(241, 248, 240));

        jPanel6.setBackground(new java.awt.Color(153, 153, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(440, 4));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 474, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(43, 40, 54));
        jLabel10.setText("Total HT");

        totalHT.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalHT.setForeground(new java.awt.Color(43, 40, 54));
        totalHT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalHT.setText("0");

        totalTVA.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalTVA.setForeground(new java.awt.Color(43, 40, 54));
        totalTVA.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalTVA.setText("0");

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(43, 40, 54));
        jLabel11.setText("TVA");

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(43, 40, 54));
        jLabel12.setText("Total TTC");

        totalTTC.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalTTC.setForeground(new java.awt.Color(43, 40, 54));
        totalTTC.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalTTC.setText("0");

        totalNET.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalNET.setForeground(new java.awt.Color(43, 40, 54));
        totalNET.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalNET.setText("0");

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(43, 40, 54));
        jLabel13.setText("Total net à payer");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalHT, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addComponent(jSeparator5)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalTVA, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalTTC, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                .addComponent(totalNET, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(totalHT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(totalTVA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(totalTTC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(totalNET))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 184, Short.MAX_VALUE)))
        );

        jPanel15.setBackground(new java.awt.Color(153, 153, 255));
        jPanel15.setPreferredSize(new java.awt.Dimension(440, 4));

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Commande n°");

        fac_refCommande.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        fac_refCommande.setForeground(new java.awt.Color(204, 255, 255));
        fac_refCommande.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        fac_refCommande.setText("CO0012");

        idCommande.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        idCommande.setForeground(new java.awt.Color(204, 255, 255));
        idCommande.setText("1346");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel8)
                .addGap(9, 9, 9)
                .addComponent(idCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fac_refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addComponent(idCommande, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(fac_refCommande, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btn_valider.setBackground(new java.awt.Color(241, 248, 240));
        btn_valider.setBorder(null);
        btn_valider.setForeground(new java.awt.Color(43, 40, 54));
        btn_valider.setText("Valider");
        btn_valider.setBorderColor(new java.awt.Color(241, 248, 240));
        btn_valider.setColor(new java.awt.Color(241, 248, 240));
        btn_valider.setColorClick(new java.awt.Color(204, 255, 255));
        btn_valider.setColorOver(new java.awt.Color(204, 255, 255));
        btn_valider.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_valider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_validerActionPerformed(evt);
            }
        });

        btn_stat_facture.setBackground(new java.awt.Color(241, 248, 240));
        btn_stat_facture.setBorder(null);
        btn_stat_facture.setForeground(new java.awt.Color(43, 40, 54));
        btn_stat_facture.setText("Classer facturé");
        btn_stat_facture.setBorderColor(new java.awt.Color(241, 248, 240));
        btn_stat_facture.setColor(new java.awt.Color(241, 248, 240));
        btn_stat_facture.setColorClick(new java.awt.Color(241, 248, 240));
        btn_stat_facture.setColorOver(new java.awt.Color(229, 240, 228));
        btn_stat_facture.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_stat_facture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_stat_factureActionPerformed(evt);
            }
        });

        btn_stat_livre.setBackground(new java.awt.Color(241, 248, 240));
        btn_stat_livre.setBorder(null);
        btn_stat_livre.setForeground(new java.awt.Color(43, 40, 54));
        btn_stat_livre.setText("Classer livré");
        btn_stat_livre.setBorderColor(new java.awt.Color(241, 248, 240));
        btn_stat_livre.setColor(new java.awt.Color(241, 248, 240));
        btn_stat_livre.setColorClick(new java.awt.Color(241, 248, 240));
        btn_stat_livre.setColorOver(new java.awt.Color(229, 240, 228));
        btn_stat_livre.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_stat_livre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_stat_livreActionPerformed(evt);
            }
        });

        btn_annuler.setBackground(new java.awt.Color(255, 226, 226));
        btn_annuler.setBorder(null);
        btn_annuler.setForeground(new java.awt.Color(255, 57, 57));
        btn_annuler.setText("Annuler");
        btn_annuler.setBorderColor(new java.awt.Color(255, 226, 226));
        btn_annuler.setColor(new java.awt.Color(255, 226, 226));
        btn_annuler.setColorClick(new java.awt.Color(255, 226, 226));
        btn_annuler.setColorOver(new java.awt.Color(255, 204, 204));
        btn_annuler.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_annulerActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(241, 248, 240));

        jLabel53.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(43, 40, 54));
        jLabel53.setText("Date de fin de validité");

        jLabel50.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(43, 40, 54));
        jLabel50.setText("Description");

        jLabel40.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(43, 40, 54));
        jLabel40.setText("Date de commande");

        fac_descriptionCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        fac_descriptionCommande.setForeground(new java.awt.Color(43, 40, 54));
        jScrollPane1.setViewportView(fac_descriptionCommande);

        btn_modif_comm.setBackground(new java.awt.Color(204, 204, 255));
        btn_modif_comm.setBorder(null);
        btn_modif_comm.setForeground(new java.awt.Color(51, 51, 51));
        btn_modif_comm.setText("Modifier");
        btn_modif_comm.setBorderColor(new java.awt.Color(204, 204, 255));
        btn_modif_comm.setColor(new java.awt.Color(204, 204, 255));
        btn_modif_comm.setColorClick(new java.awt.Color(204, 204, 255));
        btn_modif_comm.setColorOver(new java.awt.Color(165, 198, 192));
        btn_modif_comm.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_modif_comm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modif_commActionPerformed(evt);
            }
        });

        jDateChooser3.setBackground(new java.awt.Color(241, 248, 240));
        jDateChooser3.setPreferredSize(new java.awt.Dimension(96, 38));

        jPanel12.setBackground(new java.awt.Color(204, 204, 255));

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(43, 40, 54));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("08:30");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );

        jDateChooser4.setBackground(new java.awt.Color(241, 248, 240));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_modif_comm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel53))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jDateChooser3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_modif_comm, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        btn_modifier.setBackground(new java.awt.Color(241, 248, 240));
        btn_modifier.setBorder(null);
        btn_modifier.setForeground(new java.awt.Color(51, 51, 51));
        btn_modifier.setText("Modifier");
        btn_modifier.setBorderColor(new java.awt.Color(241, 248, 240));
        btn_modifier.setColor(new java.awt.Color(241, 248, 240));
        btn_modifier.setColorClick(new java.awt.Color(241, 248, 240));
        btn_modifier.setColorOver(new java.awt.Color(241, 248, 240));
        btn_modifier.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_modifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modifierActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(241, 248, 240));

        jLabel54.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(43, 40, 54));
        jLabel54.setText("Date de facturation");

        jLabel51.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(43, 40, 54));
        jLabel51.setText("Description");

        jLabel41.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(43, 40, 54));
        jLabel41.setText("Adresse de facturation");

        description_fac.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        description_fac.setForeground(new java.awt.Color(43, 40, 54));
        jScrollPane2.setViewportView(description_fac);

        adresse_fac.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        adresse_fac.setForeground(new java.awt.Color(43, 40, 54));

        jPanel9.setBackground(new java.awt.Color(163, 212, 212));

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Payé");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        btn_modif_fac.setBackground(new java.awt.Color(204, 204, 255));
        btn_modif_fac.setBorder(null);
        btn_modif_fac.setForeground(new java.awt.Color(51, 51, 51));
        btn_modif_fac.setText("Modifier");
        btn_modif_fac.setBorderColor(new java.awt.Color(204, 204, 255));
        btn_modif_fac.setColor(new java.awt.Color(204, 204, 255));
        btn_modif_fac.setColorClick(new java.awt.Color(204, 204, 255));
        btn_modif_fac.setColorOver(new java.awt.Color(165, 198, 192));
        btn_modif_fac.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_modif_fac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modif_facActionPerformed(evt);
            }
        });

        jDateChooser2.setBackground(new java.awt.Color(241, 248, 240));

        jPanel11.setBackground(new java.awt.Color(204, 204, 255));

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(43, 40, 54));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("08:30");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_modif_fac, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(adresse_fac)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel54)
                        .addComponent(jLabel41)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adresse_fac, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_modif_fac, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel16.setBackground(new java.awt.Color(153, 153, 255));
        jPanel16.setPreferredSize(new java.awt.Dimension(440, 4));

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Facture n°");

        fac_refCommande1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        fac_refCommande1.setForeground(new java.awt.Color(204, 255, 255));
        fac_refCommande1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        fac_refCommande1.setText("CO0012");

        idCommande1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        idCommande1.setForeground(new java.awt.Color(204, 255, 255));
        idCommande1.setText("1346");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel9)
                .addGap(9, 9, 9)
                .addComponent(idCommande1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fac_refCommande1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addComponent(idCommande1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(fac_refCommande1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(241, 248, 240));

        jLabel55.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(43, 40, 54));
        jLabel55.setText("Date prévue de livraison");

        jLabel56.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(43, 40, 54));
        jLabel56.setText("Condition et condition de reglement");

        jLabel42.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(43, 40, 54));
        jLabel42.setText("Adresse de livraison");

        bl_description.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        bl_description.setForeground(new java.awt.Color(43, 40, 54));
        jScrollPane4.setViewportView(bl_description);

        adresse_livraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        adresse_livraison.setForeground(new java.awt.Color(43, 40, 54));

        btn_modif_bl.setBackground(new java.awt.Color(204, 204, 255));
        btn_modif_bl.setBorder(null);
        btn_modif_bl.setForeground(new java.awt.Color(51, 51, 51));
        btn_modif_bl.setText("Modifier");
        btn_modif_bl.setBorderColor(new java.awt.Color(204, 204, 255));
        btn_modif_bl.setColor(new java.awt.Color(204, 204, 255));
        btn_modif_bl.setColorClick(new java.awt.Color(204, 204, 255));
        btn_modif_bl.setColorOver(new java.awt.Color(165, 198, 192));
        btn_modif_bl.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_modif_bl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modif_blActionPerformed(evt);
            }
        });

        jDateChooser1.setBackground(new java.awt.Color(241, 248, 240));

        jPanel10.setBackground(new java.awt.Color(204, 204, 255));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(43, 40, 54));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("08:30");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btn_modify_HOUR.setBackground(new java.awt.Color(153, 153, 255));
        btn_modify_HOUR.setBorder(null);
        btn_modify_HOUR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/iconModify.png"))); // NOI18N
        btn_modify_HOUR.setBorderColor(new java.awt.Color(153, 153, 255));
        btn_modify_HOUR.setColor(new java.awt.Color(153, 153, 255));
        btn_modify_HOUR.setColorClick(new java.awt.Color(153, 153, 255));
        btn_modify_HOUR.setColorOver(new java.awt.Color(137, 137, 240));
        btn_modify_HOUR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modify_HOURActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btn_modif_bl, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(adresse_livraison)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel56, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_modify_HOUR, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adresse_livraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_modify_HOUR, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_modif_bl, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel17.setBackground(new java.awt.Color(153, 153, 255));
        jPanel17.setPreferredSize(new java.awt.Dimension(440, 4));

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("BL n°");

        refLivraison.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        refLivraison.setForeground(new java.awt.Color(204, 255, 255));
        refLivraison.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        refLivraison.setText("CO0012");

        idLivraison.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        idLivraison.setForeground(new java.awt.Color(204, 255, 255));
        idLivraison.setText("1346");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel14)
                .addGap(9, 9, 9)
                .addComponent(idLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(refLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addComponent(idLivraison, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(refLivraison, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout page2Layout = new javax.swing.GroupLayout(page2);
        page2.setLayout(page2Layout);
        page2Layout.setHorizontalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page2Layout.createSequentialGroup()
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(page2Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page2Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(page2Layout.createSequentialGroup()
                                                .addComponent(btn_stat_livre, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btn_stat_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btn_valider, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btn_annuler, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(2, 2, 2))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page2Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(136, 136, 136)
                                .addComponent(btn_modifier, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_modify1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_creer_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_delete_commande, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4))
                            .addGroup(page2Layout.createSequentialGroup()
                                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(page2Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_modify_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_delete_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(57, 57, 57))
        );
        page2Layout.setVerticalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_creer_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_modify1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete_commande, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_modifier, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_valider, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_annuler, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_stat_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_stat_livre, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(page2Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(266, 266, 266)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_delete_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel7)
                        .addComponent(btn_modify_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(page1, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(page2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(page1, javax.swing.GroupLayout.PREFERRED_SIZE, 832, javax.swing.GroupLayout.PREFERRED_SIZE)
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
 
       
//    public int insererPaiement(Connection connection, String datePaiement, String refPaiement, int idFacture,
//                            int idClient, float montantRecu, float restePaye) throws SQLException {
//    String query = "INSERT INTO paiements (datePaiement, refPaiement, idFacture, idClient, montantRecu, restePaye) " +
//            "VALUES (?, ?, ?, ?, ?, ?)";
//
//    try (PreparedStatement pstmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
//        pstmt.setString(1, datePaiement);
//        pstmt.setString(2, refPaiement);
//        pstmt.setInt(3, idFacture);
//        pstmt.setInt(4, idClient);
//        pstmt.setFloat(5, montantRecu);
//        pstmt.setFloat(6, restePaye);
//
//        int rowsAffected = pstmt.executeUpdate();
//        if (rowsAffected == 0) {
//            throw new SQLException("Insertion du paiement échouée, aucune ligne affectée.");
//        }
//
//        // Récupérer l'ID généré
//        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
//            if (generatedKeys.next()) {
//                return generatedKeys.getInt(1);
//            } else {
//                throw new SQLException("Échec de la récupération de l'ID généré pour le paiement.");
//            }
//        }
//    }
//}
    
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
//       String refCommande = null;
        int selectedRow = tableOutput.getSelectedRow();
    if (selectedRow != -1) {

            String refCommande = (String) tableOutput.getModel().getValueAt(selectedRow, 0);
//            System.out.print("reference de la commande"+refCommande);
            String[] column = {"Article","Réf", "TVA", "PU HT", "Qté", "Total HT"};
   
    DefaultTableModel model = new DefaultTableModel(null, column);
        // Step 1: Retrieve Command Details (including content)
        CommandeCDAO commandeDAO = DAOFactory.getCommandeDAO();
        
           commandeSelected = commandeDAO.find(refCommande);
           
           LivraisonDAO livDAO = DAOFactory.getLivraisonDAO();
           livSelected = livDAO.findByCommande(commandeSelected.getId());
           
           if (livSelected != null){
                idLivraison.setText(String.valueOf(livSelected.getId()));
                refLivraison.setText(livSelected.getRefLivraison());
                try {
                    jDateChooser1.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(livSelected.getDateLivraison()));
                } catch (ParseException ex) {
                    Logger.getLogger(Commande1.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                FactureDAO facDAO = DAOFactory.getFactureDAO();
                 if(facDAO.findBy(livSelected.getId()) != null){
                     facSelected = facDAO.findBy(livSelected.getId());
                 }
           }
           
           
           
           for(javaapp.bean.CommandeDetail commandeDet : commandeSelected.getCommandeDetails()){
            Object[] data = new Object[6];
            
            
            
            float tva = commandeDet.getProduit().getTVA();
            int qte = commandeDet.getQuantite();
            float PUHT = commandeDet.getProduit().getPUHT();
            float PUTTC = PUHT + (PUHT*tva)/100;
            
            data[0] = commandeDet.getProduit().getDesignation();
            data[1] = commandeDet.getProduit().getRefProduit();
            data[2] = tva;
            data[3] = formater.formatFloat(commandeDet.getProduit().getPU()); //PUHT
            data[4] = qte;
            data[5] = formater.formatFloat(PUHT);
            
            model.addRow(data);
           }
           
        tableListCommande.setModel(model);
//        listeArticleCommande.setModel(model);  
        
//       refCommande = (String) tableOutput.getModel().getValueAt(selectedRow, 1);
        // Step 3: Retrieve Client Information
        retrieveAndDisplayClientInformation();
           try {
               retrieveAndDisplayCommandeInformation();
           } catch (ParseException ex) {
               Logger.getLogger(Commande1.class.getName()).log(Level.SEVERE, null, ex);
           }
        updateMontant();
    }
    }//GEN-LAST:event_tableOutputMouseClicked

//    private void filterData(int index){
//        setActiveMenu(index);
//        query= getActiveQuery();
//        refreshTable();
//        changeActiveButton();
//        
//    }
//        public void setActiveMenu(int activeMenu) {
//        this.activeMenu = activeMenu;
//    }
    
    public void updateMontant(){
    // Calculate totals
        float total_HT = 0;
        float total_TVA = 0;
        float total_TTC = 0;
        TableModel model = tableListCommande.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            float PUHT = formater.parseFloat(model.getValueAt(i, 3).toString());
            int qte = Integer.parseInt(model.getValueAt(i, 4).toString());
            float tva = formater.parseFloat(model.getValueAt(i, 2).toString());

            float lineTotalHT = PUHT * qte;
            float lineTotalTVA = lineTotalHT * tva / 100;
            float lineTotalTTC = lineTotalHT + lineTotalTVA;

            total_HT += lineTotalHT;
            total_TVA += lineTotalTVA;
            total_TTC += lineTotalTTC;
        }
        
        totalHT.setText(String.valueOf(formater.formatFloat(total_HT)));
        totalTVA.setText(String.valueOf(formater.formatFloat(total_TVA)));
        totalTTC.setText(String.valueOf(formater.formatFloat(total_TTC)));
        totalNET.setText(String.valueOf(formater.formatFloat(total_TTC)));
        
    }
       
    private void retrieveAndDisplayClientInformation() {
 
            // Set the retrieved client information in your form fields
            nomClient.setText(commandeSelected.getClient().getNom()+" "+commandeSelected.getClient().getPrenom());
            fac_id_client.setText(""+commandeSelected.getClient().getId());
            
            //modifié
//            fac_email_client.setText(commandeSelected.getClient().getEmail1());
//            fac_tel_client.setText(commandeSelected.getClient().getTel1());
//            fac_adresse_client.setText(commandeSelected.getClient().getAdresse());
            
//            nifTextClient.setText(resultSet.getString("NIF"));
//            statClient.setText(resultSet.getString("stat"));
        
    }
  
        private void retrieveAndDisplayCommandeInformation() throws ParseException {
//modifié

            idCommande.setText(""+commandeSelected.getId());
//            subTotal.setText(formater.formatFloat(commandeSelected.getMontant()));
            fac_refCommande.setText(commandeSelected.getRefCommande());
            
            
//            fac_dateCommande.setText(commandeSelected.getDateCommande()); //modif
//            fac_montant.setValue(commandeSelected.getMontant());
//            fac_frais.setValue(resultSet.getFloat("frais"));
//            charge.setText(formater.formatFloat(resultSet.getFloat("frais")));
            fac_descriptionCommande.setText(commandeSelected.getDescription());
            Profile profile = DAOFactory.getProfileDAO().find(commandeSelected.getIdCreateur());
//            vendeur.setText(profile.getNom() +" "+ profile.getPrenom());//decomm
            

            //information sur la paiement     montantRecu, p.restePaye
//            refPaiement.setText(resultSet.getString("refPaiement"));
//            datePaiement.setDate(resultSet.getDate("datePaiement"));
//            
//            montantVerse.setText(formater.formatFloat(resultSet.getFloat("montantRecu")));
//            descriptionPaiement.setText(resultSet.getString("descriptionPaiement"));
//            float somme=resultSet.getFloat("montantCommande")+resultSet.getFloat("frais");
//            montantNet1.setText(formater.formatFloat(somme));
//            montantNet2.setText(formater.formatFloat(somme));
//            restePaiement.setText(formater.formatFloat(resultSet.getFloat("restePaye")));
//            nifTextClient.setText(resultSet.getString("NIF"));
//            statClient.setText(resultSet.getString("stat"));
   
    }
        
    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
//        String searchRefCommande=" c.refCommande like '%"+refCommandeKeySearch.getText()+"%' ";
//        String searchClient=" c.nom like '%"+nomClientKeySearch1.getText()+"%' ";

//    String FILTER_CLIENT="  ( cl.nom like '%"+nomClientKeySearch.getText()+"%' OR cl.prenom like '%"+nomClientKeySearch.getText()+"%') ";
//    String FILTER_REFCOMMANDE=" c.refCommande like '%"+refCommandeKeySearch.getText()+"%' ";
    
//    query = getActiveQuery();
//    query+=(activeMenu == 1)?" WHERE ":" AND ";
//    if(!refCommandeKeySearch.getText().isEmpty() && nomClientKeySearch.getText().trim().isEmpty()){query += FILTER_REFCOMMANDE;}
//    if(refCommandeKeySearch.getText().isEmpty() && !nomClientKeySearch.getText().trim().isEmpty()){query += FILTER_CLIENT;}
//    if(!refCommandeKeySearch.getText().isEmpty() && !nomClientKeySearch.getText().trim().isEmpty()){query += FILTER_REFCOMMANDE +" AND "+ FILTER_CLIENT;}
    
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        refreshTable(DAOFactory.getCommandeDAO().findBetweenDates( (String) dateFormat.format(date1.getDate()), (String) dateFormat.format(date2.getDate())));
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        query = getActiveQuery();
        refreshTable(DAOFactory.getCommandeDAO().select());
//        refCommandeKeySearch.setText("");
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        refreshTable(DAOFactory.getCommandeDAO().select());
        switchToPage(1);
        afficherBoutton(false);
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_delete_commandeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_commandeActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer cette commande?","Confirmation de suppression",JOptionPane.YES_NO_OPTION) == 0){
        CommandeCDAO commDAO = DAOFactory.getCommandeDAO();
        commDAO.delete(commandeSelected);
        JOptionPane.showMessageDialog(null, "Commande suppprimée!","Confirmation",JOptionPane.INFORMATION_MESSAGE);
    } 
    }//GEN-LAST:event_btn_delete_commandeActionPerformed

    private void tableInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInputMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableInputMouseClicked

    private void btn_modify_invoice_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modify_invoice_itemActionPerformed
        int selectedRow = tableListCommande.getSelectedRow();
        
        String designation = tableListCommande.getModel().getValueAt(selectedRow, 0).toString();
        String prix = tableListCommande.getModel().getValueAt(selectedRow, 1).toString();
        int quantite = Integer.parseInt(tableListCommande.getModel().getValueAt(selectedRow, 4).toString());
//        float montant = formater.parseFloat(tableListCommande.getModel().getValueAt(selectedRow, 5).toString());
        
        JPanel panel = new JPanel(new GridLayout(0, 2));

        panel.add(new JLabel("Nom:"));
        JTextField nameField = new JTextField(designation);
        nameField.setEditable(false);
        panel.add(nameField);
        
        panel.add(new JLabel("Prix unitaire HT:"));
        JTextField priceField = new JTextField(prix);
        priceField.setEditable(false);
        panel.add(priceField);

        panel.add(new JLabel("Quantité:"));
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(quantite, 1, 100, 1));
        panel.add(quantitySpinner);
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Entrer la quantité désirée",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
//            connectiondb();
//            try{
//                stm = conn.prepareStatement("UPDATE mouvementStock SET quantite = ? WHERE idCommandeDet IN (SELECT id from commandeDetails WHERE idCommande=? and idProduit=(SELECT id from produits WHERE designation=?) ) ");
//                stm.setInt(1, Integer.parseInt(quantitySpinner.getValue().toString()));
//                stm.setString(2, idCommande.getText().trim());
//                stm.setString(3, designation);
//                resultSet = stm.executeQuery();
//                
//            }catch(Exception ex){
//
//            }
        }
        tableListCommande.clearSelection();

        
    }//GEN-LAST:event_btn_modify_invoice_itemActionPerformed

    private void btn_delete_invoice_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_invoice_itemActionPerformed
        int selectedRow = tableListCommande.getSelectedRow();
        String designationProduitSelected = tableListCommande.getModel().getValueAt(selectedRow, 0).toString();
        float montant = formater.parseFloat(tableListCommande.getModel().getValueAt(selectedRow, 3).toString());
        
        if (selectedRow != -1) {
            
            if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer cette ligne?","Confirmation de suppression",JOptionPane.YES_NO_OPTION) == 0){
            
            CommandeCDAO commandeDAO = DAOFactory.getCommandeDAO();
            
            ProduitDAO produitDAO = DAOFactory.getProduitDAO();

            CommandeDetailDAO commDet = DAOFactory.getCommandeDetailDAO();
            
            //  modifier le montant dans livraison, facture
            if(commDet.delete(commandeDAO.find(fac_refCommande.getText()).getId(), produitDAO.find(designationProduitSelected).getId())){
                tableListCommande.clearSelection();
                ((DefaultTableModel) tableListCommande.getModel()).removeRow(selectedRow);
                JOptionPane.showMessageDialog(null, "Ligne supprimé!","Succès",JOptionPane.INFORMATION_MESSAGE);
            }
            } 
        }
    }//GEN-LAST:event_btn_delete_invoice_itemActionPerformed

    private void btn_modify1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modify1ActionPerformed
        modal = new ModalBonLivraison(Integer.parseInt(idCommande.getText()));
        modal.setVisible(true);
        
    }//GEN-LAST:event_btn_modify1ActionPerformed

    private void btn_creer_factureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_creer_factureActionPerformed
        
        ModalFactures modal = new ModalFactures(Integer.parseInt(idCommande.getText()));
        modal.setVisible(true);
        modal.initEvent(event, 1);
        
//        FactureDAO facDAO = DAOFactory.getFactureDAO();
//        Facture fac = new Facture();
//            fac.setDescription(result.getString("description"));
//            fac.setMontant(result.getFloat("montant"));
//            fac.setPj(result.getString("pj"));
//        facDAO.create(fac);
        
    }//GEN-LAST:event_btn_creer_factureActionPerformed

    private void btn_search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search1ActionPerformed
         
//        refreshTable(DAOFactory.getCommandeDAO().findByDate(date1.getDate()));
refreshTable(DAOFactory.getCommandeDAO().filter(nomClientKeySearch.getText()));
        
    }//GEN-LAST:event_btn_search1ActionPerformed

    private void btn_modifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modifyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_modifyActionPerformed

    private void btn_validerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_validerActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir valider cette commande?","Confirmation validation",JOptionPane.YES_NO_OPTION) == 0){
            CommandeCDAO commDAO = DAOFactory.getCommandeDAO();
            javaapp.bean.CommandeC comm = commandeSelected;
            comm.setStatus("Validé");
            if(commDAO.update(comm)){
                t = new Toast("Création de la facture réussie!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
            }
            JOptionPane.showMessageDialog(null, "Commande validée!","Confirmation",JOptionPane.INFORMATION_MESSAGE);
        } 
    }//GEN-LAST:event_btn_validerActionPerformed

    private void btn_stat_factureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_stat_factureActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir classer cette commande sur facturé?","Confirmation de facturation",JOptionPane.YES_NO_OPTION) == 0){
            CommandeCDAO commDAO = DAOFactory.getCommandeDAO();
            javaapp.bean.CommandeC comm = commandeSelected;
            comm.setStatus("Oui");
            if(commDAO.update(comm)){
                t = new Toast("Création de la facture réussie!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
            }
            JOptionPane.showMessageDialog(null, "Commande facturée!","Confirmation",JOptionPane.INFORMATION_MESSAGE);
        } 
    }//GEN-LAST:event_btn_stat_factureActionPerformed

    private void btn_stat_livreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_stat_livreActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir valider cette commande?","Confirmation validation",JOptionPane.YES_NO_OPTION) == 0){
            CommandeCDAO commDAO = DAOFactory.getCommandeDAO();
            javaapp.bean.CommandeC comm = commandeSelected;
            comm.setStatus("Livré");
            if(commDAO.update(comm)){
                t = new Toast("Création de la facture réussie!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
            }
            JOptionPane.showMessageDialog(null, "Commande suppprimée!","Confirmation",JOptionPane.INFORMATION_MESSAGE);
        }  
    }//GEN-LAST:event_btn_stat_livreActionPerformed

    private void btn_annulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_annulerActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir annuler cette commande?","Confirmation d'annulation",JOptionPane.YES_NO_OPTION) == 0){
            CommandeCDAO commDAO = DAOFactory.getCommandeDAO();
            javaapp.bean.CommandeC comm = commandeSelected;
            comm.setStatus("Annulé");
            if(commDAO.update(comm)){
                t = new Toast("Commande annulée!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
            }
            JOptionPane.showMessageDialog(null, "Commande annulée!","Confirmation",JOptionPane.INFORMATION_MESSAGE);
        } 
    }//GEN-LAST:event_btn_annulerActionPerformed

    private void btn_modifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modifierActionPerformed
        afficherBoutton(true);
        
    }//GEN-LAST:event_btn_modifierActionPerformed

    private void toogleModification(){
        afficherBoutton(true);
    }
    private void afficherBoutton(boolean a){
    btn_modif_comm.setVisible(a);
    btn_modif_fac.setVisible(a);
    btn_modif_bl.setVisible(a);
    btn_modify.setVisible(a);
    btn_modify_HOUR.setVisible(a);
    btn_modify_invoice_item.setVisible(a);
    btn_modify_invoice_item.setVisible(a);
    btn_delete_invoice_item.setVisible(a);
    }
    
    private void btn_modify_invoice_item3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modify_invoice_item3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_modify_invoice_item3ActionPerformed

    private void btn_modif_commActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modif_commActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir valider cette commande?","Confirmation validation",JOptionPane.YES_NO_OPTION) == 0){
            CommandeCDAO commDAO = DAOFactory.getCommandeDAO();
            javaapp.bean.CommandeC comm = commandeSelected;
            comm.setStatus("Validé");
            if(commDAO.update(comm)){
                t = new Toast("Création de la facture réussie!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
            }
            JOptionPane.showMessageDialog(null, "Commande validée!","Confirmation",JOptionPane.INFORMATION_MESSAGE);
        } 
    }//GEN-LAST:event_btn_modif_commActionPerformed

    private void btn_modif_facActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modif_facActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir valider cette commande?","Confirmation validation",JOptionPane.YES_NO_OPTION) == 0){
            FactureDAO facDAO = DAOFactory.getFactureDAO();
            javaapp.bean.Facture fac = facSelected;
            fac.setDescription(description_fac.getText());
            if(facDAO.update(fac)){
                t = new Toast("Création de la facture réussie!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
            }
            JOptionPane.showMessageDialog(null, "Commande validée!","Confirmation",JOptionPane.INFORMATION_MESSAGE);
        } 
    }//GEN-LAST:event_btn_modif_facActionPerformed

    private void btn_modif_blActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modif_blActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir valider cette commande?","Confirmation validation",JOptionPane.YES_NO_OPTION) == 0){
            LivraisonDAO livDAO = DAOFactory.getLivraisonDAO();
            javaapp.bean.Livraison liv = livSelected;
            liv.setDateLivraison("2024-12-04");
            liv.setDescription(description_fac.getText());
//            liv.setDateLivraison(dateLivraison.getText());    //modif
            if(livDAO.update(liv)){
                t = new Toast("Création de la facture réussie!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
            }
            JOptionPane.showMessageDialog(null, "Commande validée!","Confirmation",JOptionPane.INFORMATION_MESSAGE);
        } 
    }//GEN-LAST:event_btn_modif_blActionPerformed

    private void btn_modify_HOURActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modify_HOURActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_modify_HOURActionPerformed
 
//    public void updateMontant() throws SQLException{
//        stm = conn.prepareStatement("UPDATE facture set montant = ? WHERE idFacture=?");
//        stm.setString(1,fac_refFacture.getText().trim());
//        stm.setInt(2,Integer.parseInt(numFac.getText()));
//        stm.executeUpdate();
//        
//        stm = conn.prepareStatement("UPDATE commandes set montant = ( SELECT SUM(montant) from commandeDetails WHERE idCommande in (SELECT id from commandes WHERE refCommande=?) and idProduit= (SELECT id from produits WHERE designation=?) ) WHERE refCommande=? ");
//        stm.setString(1,fac_refFacture.getText().trim());
//        stm.setInt(2,Integer.parseInt(numFac.getText()));
//        stm.executeUpdate();
//        
//    }
    
    public void refreshTable(List<javaapp.bean.CommandeC> commandes){
    String[] column = {"Réference", "Date commande","Client", "Montant HT","Date livraison", "Facturé", "Status"};
    Object[] data = new Object[7];
    DefaultTableModel model = new DefaultTableModel(null, column);
   
//        CommandeCDAO commandeDAO = DAOFactory.getCommandeDAO();
        
       for(javaapp.bean.CommandeC commande : commandes){
            data[0]=commande.getRefCommande();
            data[1]=DateConverter.convertToLetter(commande.getDateCommande());
            data[2]=commande.getClient().getNom()+" "+commande.getClient().getPrenom();
            data[3]=commande.getMontant();
            
            javaapp.bean.Livraison liv = DAOFactory.getLivraisonDAO().findByCommande(commande.getId());
            data[4]=( liv != null ) ? liv.getDateLivraison() : " ";
            data[5]= "Non";
            data[6]=commande.getStatus();
            
            model.addRow(data);
        }
        tableInput.setModel(model);
        displayRows(0,13);
        tableOutput.getColumnModel().getColumn(6).setCellRenderer(new TableStatusCellRender());

    }
 
    public void setEmptyForm(){
//        nomCreateur.setText("");
        btn_save_state="insert";
    }        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField adresse_fac;
    private javax.swing.JTextField adresse_livraison;
    private javax.swing.JTextPane bl_description;
    private javaapp.component.ButtonRadius btn_annuler;
    private javaapp.component.ButtonRadius btn_back;
    private javaapp.component.ButtonRadius btn_creer_facture;
    private javaapp.component.ButtonRadius btn_delete_commande;
    private javaapp.component.ButtonRadius btn_delete_invoice_item;
    private javaapp.component.ButtonRadius btn_modif_bl;
    private javaapp.component.ButtonRadius btn_modif_comm;
    private javaapp.component.ButtonRadius btn_modif_fac;
    private javaapp.component.ButtonRadius btn_modifier;
    private javaapp.component.ButtonRadius btn_modify;
    private javaapp.component.ButtonRadius btn_modify1;
    private javaapp.component.ButtonRadius btn_modify_HOUR;
    private javaapp.component.ButtonRadius btn_modify_invoice_item;
    private javaapp.component.ButtonRadius btn_modify_invoice_item3;
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_search;
    private javaapp.component.ButtonRadius btn_search1;
    private javaapp.component.ButtonRadius btn_stat_facture;
    private javaapp.component.ButtonRadius btn_stat_livre;
    private javaapp.component.ButtonRadius btn_valider;
    private com.toedter.calendar.JDateChooser date1;
    private com.toedter.calendar.JDateChooser date2;
    private javax.swing.JTextPane description_fac;
    private javax.swing.JTextPane fac_descriptionCommande;
    private javax.swing.JLabel fac_id_client;
    private javax.swing.JLabel fac_refCommande;
    private javax.swing.JLabel fac_refCommande1;
    private javax.swing.JLabel facture_non_paye;
    private javax.swing.JLabel idCommande;
    private javax.swing.JLabel idCommande1;
    private javax.swing.JLabel idLivraison;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JLabel menu5;
    private javax.swing.JTextField nomClient;
    private javax.swing.JTextField nomClient1;
    private javax.swing.JTextField nomClientKeySearch;
    private javax.swing.JPanel page1;
    private javax.swing.JPanel page2;
    private javaapp.pagination.Pagination pagination1;
    private javaapp.component.PanelBorderRound panelBorderRound1;
    private javax.swing.JLabel refLivraison;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JScrollPane scrollTable1;
    private javaapp.component.Table tableInput;
    private javaapp.component.Table tableListCommande;
    private javaapp.component.Table tableOutput;
    private javax.swing.JLabel totalHT;
    private javax.swing.JLabel totalNET;
    private javax.swing.JLabel totalTTC;
    private javax.swing.JLabel totalTVA;
    private javax.swing.JTextField vendeur;
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
