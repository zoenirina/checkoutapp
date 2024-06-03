/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.page.vente;

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
import javaap.modal.ModalFacture;
import javaapp.PrinterPDF.BCCustomer;
import javaapp.bean.CommandeDetail;
import javaapp.bean.Produit;
import javaapp.component.ButtonRadius;
import javaapp.component.Calculator;
import javaapp.PrinterPDF.PdfReceiptGenerator;
import javaapp.component.Toast;
import javaapp.dao.CommandeCDAO;
import javaapp.dao.CommandeDetailDAO;
import javaapp.dao.CommandeDetailDAOImpl;
import javaapp.dao.FactureDAO;
import javaapp.dao.LivraisonDAO;
import javaapp.dao.ProduitDAO;
import javaapp.dao.ProfileDAO;
import javaapp.factory.DAOFactory;
import javaapp.pagination.PaginationItemRenderStyle1;
import javaapp.swing.TableStatusCellRender;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
public class Commande extends javax.swing.JPanel {
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
    private final FactureDAO facDAO;
    private final CommandeCDAO commandeDAO;
    private final LivraisonDAO livDAO;
    private final CommandeDetailDAO commDetDAO;
    private final ProduitDAO produitDAO;
    private final ProfileDAO profileDAO;
    private final CommandeDetailDAOImpl cmdDAO;

    public Commande(JPanel panel) {
        initComponents();
        facDAO = DAOFactory.getFactureDAO();
        commandeDAO = DAOFactory.getCommandeDAO();
        livDAO = DAOFactory.getLivraisonDAO();
        commDetDAO = DAOFactory.getCommandeDetailDAO();
        produitDAO = DAOFactory.getProduitDAO();
        profileDAO = DAOFactory.getProfileDAO();
        cmdDAO = DAOFactory.getCommandeDetailDAOImpl();
        
        pan= panel;
        init();
    }
    
    public void init(){
        formater= new FormatUtils();
        query=SELECT_ALL;
        refreshTable(commandeDAO.select());
        scrollTable.getViewport().setBackground(Color.white);
        jScrollPane3.getViewport().setBackground(Color.white);
        pdfPrinter= new PdfReceiptGenerator();
        switchToPage(1);
        displayRows(0,13);
        initialisePagination();
        pagination1.addEventPagination((int page) -> {
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
//        List<Personne> clients = DAOFactory.getClientDAO().select();
//        for (javaapp.bean.Personne client : clients) {
//            nomClient.addItem(client.getNom() + " " + client.getPrenom());
//        }
        
        vendeur.setEditable(false);
        
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
        nombre_ligne = new javax.swing.JLabel();
        page2 = new javax.swing.JPanel();
        btn_back = new javaapp.component.ButtonRadius();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableListCommande = new javaapp.component.Table();
        jLabel7 = new javax.swing.JLabel();
        btn_delete_invoice_item = new javaapp.component.ButtonRadius();
        btn_modify_invoice_item = new javaapp.component.ButtonRadius();
        btn_creer_facture = new javaapp.component.ButtonRadius();
        btnConvertBL = new javaapp.component.ButtonRadius();
        btn_delete_commande = new javaapp.component.ButtonRadius();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        totalHTField = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        totalTVA = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        totalTTCField = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        totalNetAPayerField = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        totalRemiseField = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jPanel15 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btn_valider = new javaapp.component.ButtonRadius();
        jPanel4 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionCommande = new javax.swing.JTextPane();
        btn_modif_comm = new javaapp.component.ButtonRadius();
        dateCommande = new com.toedter.calendar.JDateChooser();
        jPanel12 = new javax.swing.JPanel();
        heureCommande = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        vendeur = new javax.swing.JTextField();
        refCommandeField = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        id_client = new javax.swing.JLabel();
        nomClient = new javax.swing.JTextField();
        btn_livrer = new javaapp.component.ButtonRadius();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btn_print = new javaapp.component.ButtonRadius();
        idCommande = new javax.swing.JLabel();
        factureSection = new javax.swing.JLayeredPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        adresse_fac = new javax.swing.JTextField();
        btn_modif_fac = new javaapp.component.ButtonRadius();
        dateFacture = new com.toedter.calendar.JDateChooser();
        jPanel11 = new javax.swing.JPanel();
        heureFacture = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        refFacture = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        idFacture = new javax.swing.JLabel();
        livraisonSection = new javax.swing.JLayeredPane();
        jPanel17 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        idLivraison = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        adresse_livraison = new javax.swing.JTextField();
        btn_modif_bl = new javaapp.component.ButtonRadius();
        dateLivraison = new com.toedter.calendar.JDateChooser();
        refLivraison = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        heureLivraison = new javax.swing.JComboBox<>();
        minuteLivraison = new javax.swing.JComboBox<>();
        btn_add_invoice_item = new javaapp.component.ButtonRadius();
        btn_annuler_commande = new javaapp.component.ButtonRadius();
        btn_classer_facture = new javaapp.component.ButtonRadius();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 1900));

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
        page1.add(btn_refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 90, 37, 37));

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

        page1.add(scrollTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 950, 610));

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
        page1.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 90, 37, 37));

        facture_non_paye.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        facture_non_paye.setForeground(new java.awt.Color(51, 51, 51));
        facture_non_paye.setText("(2) Commande à valider");
        page1.add(facture_non_paye, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 260, -1));
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

        page1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 760, 960, -1));

        date1.setBackground(new java.awt.Color(255, 255, 255));
        page1.add(date1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 180, 37));

        date2.setBackground(new java.awt.Color(255, 255, 255));
        page1.add(date2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 170, 37));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Date fin");
        page1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 70, -1));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Date début");
        page1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, -1, -1));

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

        panelBorderRound1.setBackground(new java.awt.Color(227, 227, 255));

        nombre_ligne.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nombre_ligne.setForeground(new java.awt.Color(102, 102, 255));
        nombre_ligne.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombre_ligne.setText("Documents (5)");

        javax.swing.GroupLayout panelBorderRound1Layout = new javax.swing.GroupLayout(panelBorderRound1);
        panelBorderRound1.setLayout(panelBorderRound1Layout);
        panelBorderRound1Layout.setHorizontalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(nombre_ligne, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        panelBorderRound1Layout.setVerticalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nombre_ligne, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        page1.add(panelBorderRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 20, 170, 40));

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

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableListCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jScrollPane3.setViewportView(tableListCommande);

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(43, 40, 54));
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

        btn_creer_facture.setBackground(new java.awt.Color(241, 248, 240));
        btn_creer_facture.setBorder(null);
        btn_creer_facture.setForeground(new java.awt.Color(43, 40, 54));
        btn_creer_facture.setText("Convertir en facture");
        btn_creer_facture.setBorderColor(new java.awt.Color(241, 248, 240));
        btn_creer_facture.setColor(new java.awt.Color(241, 248, 240));
        btn_creer_facture.setColorClick(new java.awt.Color(241, 248, 240));
        btn_creer_facture.setColorOver(new java.awt.Color(241, 248, 240));
        btn_creer_facture.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_creer_facture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_creer_factureActionPerformed(evt);
            }
        });

        btnConvertBL.setBackground(new java.awt.Color(241, 248, 240));
        btnConvertBL.setBorder(null);
        btnConvertBL.setForeground(new java.awt.Color(43, 40, 54));
        btnConvertBL.setText("Convertir en bon de livraison");
        btnConvertBL.setBorderColor(new java.awt.Color(241, 248, 240));
        btnConvertBL.setColor(new java.awt.Color(241, 248, 240));
        btnConvertBL.setColorClick(new java.awt.Color(241, 248, 240));
        btnConvertBL.setColorOver(new java.awt.Color(241, 248, 240));
        btnConvertBL.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnConvertBL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConvertBLActionPerformed(evt);
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

        totalHTField.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalHTField.setForeground(new java.awt.Color(43, 40, 54));
        totalHTField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalHTField.setText("0");

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

        totalTTCField.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalTTCField.setForeground(new java.awt.Color(43, 40, 54));
        totalTTCField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalTTCField.setText("0");

        totalNetAPayerField.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalNetAPayerField.setForeground(new java.awt.Color(102, 102, 255));
        totalNetAPayerField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalNetAPayerField.setText("0");

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 255));
        jLabel13.setText("Total net à payer");

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(43, 40, 54));
        jLabel16.setText("Remise");

        totalRemiseField.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalRemiseField.setForeground(new java.awt.Color(43, 40, 54));
        totalRemiseField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalRemiseField.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalHTField, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(totalTTCField, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                .addComponent(totalNetAPayerField, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalRemiseField, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(totalHTField))
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
                    .addComponent(jLabel16)
                    .addComponent(totalRemiseField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(totalTTCField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(totalNetAPayerField))
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 184, Short.MAX_VALUE)))
        );

        jPanel15.setBackground(new java.awt.Color(153, 153, 255));
        jPanel15.setPreferredSize(new java.awt.Dimension(440, 4));

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Information générale");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_valider.setBackground(new java.awt.Color(66, 230, 119));
        btn_valider.setBorder(null);
        btn_valider.setForeground(new java.awt.Color(255, 255, 255));
        btn_valider.setText("Confirmer la vente");
        btn_valider.setBorderColor(new java.awt.Color(66, 230, 119));
        btn_valider.setColor(new java.awt.Color(66, 230, 119));
        btn_valider.setColorClick(new java.awt.Color(66, 230, 119));
        btn_valider.setColorOver(new java.awt.Color(74, 246, 130));
        btn_valider.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_valider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_validerActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(241, 248, 240));

        jLabel50.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(43, 40, 54));
        jLabel50.setText("Description");

        jLabel40.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(43, 40, 54));
        jLabel40.setText("Date de commande");

        descriptionCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        descriptionCommande.setForeground(new java.awt.Color(43, 40, 54));
        jScrollPane1.setViewportView(descriptionCommande);

        btn_modif_comm.setBackground(new java.awt.Color(204, 204, 255));
        btn_modif_comm.setBorder(null);
        btn_modif_comm.setForeground(new java.awt.Color(102, 102, 255));
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

        dateCommande.setBackground(new java.awt.Color(241, 248, 240));
        dateCommande.setForeground(new java.awt.Color(43, 40, 54));
        dateCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        dateCommande.setPreferredSize(new java.awt.Dimension(96, 38));

        jPanel12.setBackground(new java.awt.Color(204, 204, 255));

        heureCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        heureCommande.setForeground(new java.awt.Color(43, 40, 54));
        heureCommande.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(heureCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(heureCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel45.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(43, 40, 54));
        jLabel45.setText("Client n°");

        jLabel48.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(43, 40, 54));
        jLabel48.setText("Responsable");

        vendeur.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        vendeur.setForeground(new java.awt.Color(51, 51, 51));

        refCommandeField.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        refCommandeField.setForeground(new java.awt.Color(51, 51, 51));

        jLabel49.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(43, 40, 54));
        jLabel49.setText("Réference commande");

        id_client.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        id_client.setForeground(new java.awt.Color(43, 40, 54));

        nomClient.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nomClient.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(id_client, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel48)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btn_modif_comm, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(78, 78, 78)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(vendeur, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(dateCommande, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(refCommandeField, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nomClient, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(id_client, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel45))
                        .addGap(25, 25, 25))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(nomClient, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(vendeur, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(refCommandeField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateCommande, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel40)))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btn_modif_comm, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        btn_livrer.setBackground(new java.awt.Color(241, 248, 240));
        btn_livrer.setBorder(null);
        btn_livrer.setForeground(new java.awt.Color(43, 40, 54));
        btn_livrer.setText("Classer livrée");
        btn_livrer.setBorderColor(new java.awt.Color(241, 248, 240));
        btn_livrer.setColor(new java.awt.Color(241, 248, 240));
        btn_livrer.setColorClick(new java.awt.Color(241, 248, 240));
        btn_livrer.setColorOver(new java.awt.Color(241, 248, 240));
        btn_livrer.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_livrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_livrerActionPerformed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(43, 40, 54));
        jLabel5.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(43, 40, 54));
        jLabel5.setText("Bon de commande n°");

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(440, 4));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        btn_print.setBackground(new java.awt.Color(241, 248, 240));
        btn_print.setBorder(null);
        btn_print.setForeground(new java.awt.Color(43, 40, 54));
        btn_print.setText("Imprimer");
        btn_print.setBorderColor(new java.awt.Color(241, 248, 240));
        btn_print.setColor(new java.awt.Color(241, 248, 240));
        btn_print.setColorClick(new java.awt.Color(241, 248, 240));
        btn_print.setColorOver(new java.awt.Color(241, 248, 240));
        btn_print.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        idCommande.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        idCommande.setForeground(new java.awt.Color(43, 40, 54));

        jPanel7.setBackground(new java.awt.Color(241, 248, 240));

        jLabel54.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(43, 40, 54));
        jLabel54.setText("Date de facturation");

        jLabel41.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(43, 40, 54));
        jLabel41.setText("Adresse de facturation");

        adresse_fac.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        adresse_fac.setForeground(new java.awt.Color(43, 40, 54));

        btn_modif_fac.setBackground(new java.awt.Color(204, 204, 255));
        btn_modif_fac.setBorder(null);
        btn_modif_fac.setForeground(new java.awt.Color(102, 102, 255));
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

        dateFacture.setBackground(new java.awt.Color(241, 248, 240));
        dateFacture.setForeground(new java.awt.Color(43, 40, 54));
        dateFacture.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        dateFacture.setPreferredSize(new java.awt.Dimension(96, 38));

        jPanel11.setBackground(new java.awt.Color(204, 204, 255));

        heureFacture.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        heureFacture.setForeground(new java.awt.Color(43, 40, 54));
        heureFacture.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(heureFacture, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(heureFacture, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel46.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(43, 40, 54));
        jLabel46.setText("Reference");

        refFacture.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        refFacture.setForeground(new java.awt.Color(43, 40, 54));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41)
                    .addComponent(jLabel46)
                    .addComponent(jLabel54))
                .addGap(61, 61, 61)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(adresse_fac)
                    .addComponent(refFacture)
                    .addComponent(dateFacture, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
                .addGap(23, 23, 23)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_modif_fac, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refFacture, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adresse_fac, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateFacture, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(btn_modif_fac, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel54)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel16.setBackground(new java.awt.Color(153, 153, 255));
        jPanel16.setPreferredSize(new java.awt.Dimension(440, 4));

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Facture n°");

        idFacture.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        idFacture.setForeground(new java.awt.Color(204, 255, 255));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel9)
                .addGap(9, 9, 9)
                .addComponent(idFacture, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(730, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addComponent(idFacture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        factureSection.setLayer(jPanel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        factureSection.setLayer(jPanel16, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout factureSectionLayout = new javax.swing.GroupLayout(factureSection);
        factureSection.setLayout(factureSectionLayout);
        factureSectionLayout.setHorizontalGroup(
            factureSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 961, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        factureSectionLayout.setVerticalGroup(
            factureSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(factureSectionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel17.setBackground(new java.awt.Color(153, 153, 255));
        jPanel17.setPreferredSize(new java.awt.Dimension(440, 4));

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Livraison n°");

        idLivraison.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        idLivraison.setForeground(new java.awt.Color(204, 255, 255));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(705, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addComponent(idLivraison, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(241, 248, 240));

        jLabel55.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(43, 40, 54));
        jLabel55.setText("Date prévue de livraison");

        jLabel42.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(43, 40, 54));
        jLabel42.setText("Adresse de livraison");

        adresse_livraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        adresse_livraison.setForeground(new java.awt.Color(43, 40, 54));

        btn_modif_bl.setBackground(new java.awt.Color(204, 204, 255));
        btn_modif_bl.setBorder(null);
        btn_modif_bl.setForeground(new java.awt.Color(102, 102, 255));
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

        dateLivraison.setBackground(new java.awt.Color(241, 248, 240));
        dateLivraison.setForeground(new java.awt.Color(43, 40, 54));
        dateLivraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        refLivraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        refLivraison.setForeground(new java.awt.Color(43, 40, 54));

        jLabel47.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(43, 40, 54));
        jLabel47.setText("Réference");

        heureLivraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        heureLivraison.setForeground(new java.awt.Color(43, 40, 54));

        minuteLivraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        minuteLivraison.setForeground(new java.awt.Color(43, 40, 54));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel55)
                        .addGap(40, 40, 40))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(67, 67, 67)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(adresse_livraison)
                    .addComponent(dateLivraison, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                    .addComponent(refLivraison))
                .addGap(18, 18, 18)
                .addComponent(heureLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(minuteLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_modif_bl, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(refLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(adresse_livraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel55))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(heureLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(minuteLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dateLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(14, 14, 14)
                .addComponent(btn_modif_bl, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        livraisonSection.setLayer(jPanel17, javax.swing.JLayeredPane.DEFAULT_LAYER);
        livraisonSection.setLayer(jPanel8, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout livraisonSectionLayout = new javax.swing.GroupLayout(livraisonSection);
        livraisonSection.setLayout(livraisonSectionLayout);
        livraisonSectionLayout.setHorizontalGroup(
            livraisonSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 961, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        livraisonSectionLayout.setVerticalGroup(
            livraisonSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(livraisonSectionLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
        );

        btn_add_invoice_item.setBackground(new java.awt.Color(153, 153, 255));
        btn_add_invoice_item.setBorder(null);
        btn_add_invoice_item.setForeground(new java.awt.Color(255, 255, 255));
        btn_add_invoice_item.setText("+");
        btn_add_invoice_item.setBorderColor(new java.awt.Color(153, 153, 255));
        btn_add_invoice_item.setColor(new java.awt.Color(153, 153, 255));
        btn_add_invoice_item.setColorClick(new java.awt.Color(153, 153, 255));
        btn_add_invoice_item.setColorOver(new java.awt.Color(137, 137, 240));
        btn_add_invoice_item.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        btn_add_invoice_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_invoice_itemActionPerformed(evt);
            }
        });

        btn_annuler_commande.setBackground(new java.awt.Color(255, 226, 226));
        btn_annuler_commande.setBorder(null);
        btn_annuler_commande.setForeground(new java.awt.Color(255, 57, 57));
        btn_annuler_commande.setText("Annuler");
        btn_annuler_commande.setBorderColor(new java.awt.Color(255, 226, 226));
        btn_annuler_commande.setColor(new java.awt.Color(255, 226, 226));
        btn_annuler_commande.setColorClick(new java.awt.Color(255, 226, 226));
        btn_annuler_commande.setColorOver(new java.awt.Color(255, 204, 204));
        btn_annuler_commande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_annuler_commande.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_annuler_commandeActionPerformed(evt);
            }
        });

        btn_classer_facture.setBackground(new java.awt.Color(241, 248, 240));
        btn_classer_facture.setBorder(null);
        btn_classer_facture.setForeground(new java.awt.Color(51, 51, 51));
        btn_classer_facture.setText("Classer facturé");
        btn_classer_facture.setBorderColor(new java.awt.Color(241, 248, 240));
        btn_classer_facture.setColor(new java.awt.Color(241, 248, 240));
        btn_classer_facture.setColorClick(new java.awt.Color(241, 248, 240));
        btn_classer_facture.setColorOver(new java.awt.Color(241, 248, 240));
        btn_classer_facture.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_classer_facture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_classer_factureActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout page2Layout = new javax.swing.GroupLayout(page2);
        page2.setLayout(page2Layout);
        page2Layout.setHorizontalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page2Layout.createSequentialGroup()
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, page2Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jScrollPane3))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, page2Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_add_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_modify_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_delete_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(page2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57))
            .addGroup(page2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 961, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 961, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(factureSection)
                    .addComponent(livraisonSection)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addComponent(btn_livrer, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_classer_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_annuler_commande, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_delete_commande, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(btn_valider, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(page2Layout.createSequentialGroup()
                                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnConvertBL, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(page2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(idCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(450, 450, 450)))
                        .addComponent(btn_creer_facture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        page2Layout.setVerticalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_creer_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConvertBL, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(idCommande, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_livrer, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_annuler_commande, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete_commande, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_valider, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_classer_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(factureSection)
                .addGap(18, 18, 18)
                .addComponent(livraisonSection)
                .addGap(26, 26, 26)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page2Layout.createSequentialGroup()
                            .addComponent(btn_delete_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(21, 21, 21))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page2Layout.createSequentialGroup()
                            .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btn_add_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_modify_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18))))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(page1, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(page2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(page1, javax.swing.GroupLayout.PREFERRED_SIZE, 832, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(page2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void show(ButtonRadius b){
        b.setVisible(true);
    }
    private void hide(ButtonRadius b){
        b.setVisible(false);
    }
    private void showBTNline(boolean show){
        btn_add_invoice_item.setVisible(show);
        btn_modify_invoice_item.setVisible(show);
        btn_delete_invoice_item.setVisible(show);
        btn_modif_bl.setVisible(show);
        btn_modif_fac.setVisible(show);
        btn_modif_comm.setVisible(show);
    }
    
    private void tableOutputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOutputMouseClicked
        switchToPage(2);
        updateCommandeSelected();
    
    }//GEN-LAST:event_tableOutputMouseClicked
    private void updateCommandeSelected(){
        int selectedRow = tableOutput.getSelectedRow();
        if (selectedRow != -1) {

            String refCommande = (String) tableOutput.getModel().getValueAt(selectedRow, 0);
            commandeSelected = commandeDAO.find(refCommande);
            nomClient.setText(commandeSelected.getClient().getNom()+" "+commandeSelected.getClient().getPrenom());
            id_client.setText(""+commandeSelected.getClient().getId());
            idCommande.setText(""+commandeSelected.getId());
            refCommandeField.setText(commandeSelected.getRefCommande());
            descriptionCommande.setText(commandeSelected.getDescription());
            Profile profile = profileDAO.find(commandeSelected.getIdCreateur());
            vendeur.setText(profile.getNom() +" "+ profile.getPrenom());//decomm
            heureCommande.setText(DateConverter.convertToHour(commandeSelected.getDateCommande()));
            
            
            updateButtonVisibility();

            try {
                dateCommande.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(commandeSelected.getDateCommande()));
            } catch (ParseException ex) {
                Logger.getLogger(Commande.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //récupérer les détails de la livraison
//            LivraisonDAO livDAO = DAOFactory.getLivraisonDAO();
            livSelected = livDAO.findByCommande(commandeSelected.getId());

            if (livSelected != null && livSelected.getId() != 0){
                idLivraison.setText(String.valueOf(livSelected.getId()));
                refLivraison.setText(livSelected.getRefLivraison());
                
                try {
//                    dateLivraison.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(livSelected.getDateLivraison()));
                if(!livSelected.getDateLivraison().isEmpty()){
                DateConverter.initializeDate(dateLivraison, heureLivraison, minuteLivraison, livSelected.getDateLivraison());
                }
                    
                } catch (ParseException ex) {
                    Logger.getLogger(Commande.class.getName()).log(Level.SEVERE, null, ex);
                }
                livraisonSection.setVisible(true);
                
            //recupérer les détails de la facture
//                FactureDAO facDAO = DAOFactory.getFactureDAO();
                facSelected =facDAO.findBy(livSelected.getId());
                 if(facSelected != null && facSelected.getId() != 0){
                     factureSection.setVisible(true);
                     facSelected = facDAO.findBy(livSelected.getId());
                     idFacture.setText(""+facSelected.getId());
                     heureFacture.setText(DateConverter.convertToHour(facSelected.getDateFacture()));
                     refFacture.setText(facSelected.getRefFacture());
                    try {
                        if(!livSelected.getDateLivraison().isEmpty())dateFacture.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(facSelected.getDateFacture()));
                    } catch (ParseException ex) {
                        Logger.getLogger(Commande.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 }
                 else{
                    factureSection.setVisible(false);
                }
                 
           }else{
            factureSection.setVisible(false);
            livraisonSection.setVisible(false);
            }
           
           
            String[] column = {"Article", "Réf", "PU HT", "TVA", "Remise", "Qté comm.", "Qté livrée", "Reste à livrer", "Total TTC"};
            DefaultTableModel model = new DefaultTableModel(null, column);
            Object[] data = new Object[9];
//            List <CommandeDetail> cmd = DAOFactory.getCommandeDetailDAOImpl().selectAll(commandeSelected.getId());
            for(javaapp.bean.CommandeDetail commandeDet :  commDetDAO.select(commandeSelected.getId())) {
//           System.out.print(commandeSelected.getCommandeDetails().size());
            float tva = commandeDet.getProduit().getTVA();
            int qte = commandeDet.getQuantite();
            float PUHT = commandeDet.getProduit().getPUHT();
            float PUHTremise = PUHT - (PUHT*commandeDet.getRemise())/100;
            float PUTTC = PUHTremise + (PUHTremise*tva)/100;
            
            data[0] = commandeDet.getProduit().getDesignation();
            data[1] = commandeDet.getProduit().getRefProduit();
            data[2] = formater.formatFloat(PUHT); //PUHT
            data[3] = tva;
            data[4] = commandeDet.getRemise();
            data[5] = qte;
            
            CommandeDetail comm2 = cmdDAO.findById(commandeDet.getId());
            data[6] = comm2.getQuantiteLivree();
            data[7] = comm2.getQuantiteRestante();
// data[6] =commandeDet.getQuantiteLivree();
//            data[7] = commandeDet.getQuantiteRestante();
            data[8] = formater.formatFloat(PUHT*qte);
            
            model.addRow(data);
           }
           
        tableListCommande.setModel(model);
        updateTotals();
    }
}

private void updateButtonVisibility() {
    showBTNline(true);
    btn_valider.setText("Clôturer");
    btn_classer_facture.showB();
//    if (commandeSelected.getStatus() == null) {
//        btnConvertBL.showB();
//        btn_creer_facture.showB();
//        btn_valider.showB();
//        btn_valider.setText("Clôturer");
//        btn_print.showB();
//    } else {
        switch (commandeSelected.getStatus()) {
            case "Brouillon":
            case "Créée":
                btnConvertBL.hideB();
                btn_creer_facture.hideB();
                btn_valider.setText("Confirmer la vente");
                btn_valider.showB();
                btn_print.hideB();
                btn_annuler_commande.hideB();
                btn_livrer.hideB();
                btn_classer_facture.hideB();
                break;
            case "Clôturée":
                btnConvertBL.hideB();
                btn_creer_facture.hideB();
                btn_valider.hideB();
                btn_print.showB();
                btn_livrer.hideB();
                btn_annuler_commande.hideB();
                btn_delete_commande.hideB();
                btn_classer_facture.hideB();
                showBTNline(false);
                break;
            case "Livrée":
                btn_livrer.setText("Classer attente");
                btn_livrer.showB();
                break;
            case "Annulée":
                btn_annuler_commande.setText("Relancer");
                btn_annuler_commande.showB();
                break;
            default:
                btnConvertBL.showB();
                btn_creer_facture.showB();
                btn_valider.showB();
                btn_print.showB();
                btn_annuler_commande.showB();
                btn_delete_commande.showB();
                btn_livrer.showB();
                break;
        }
        if ( "Oui".equals(commandeSelected.getAvoirFacture()) ){
            btn_classer_facture.setText("Classer non facturé");
        } else {
            btn_classer_facture.setText("Classer facturé");
        }
        if ( "Annulée".equals(commandeSelected.getStatus()) ){
            btn_annuler_commande.setText("Relancer");
        } else {
            btn_annuler_commande.setText("Annuler");
        }
}

 private void updateTotals() {
     
    DefaultTableModel model = (DefaultTableModel) tableListCommande.getModel();

    float total_HT = 0.0f;
    float total_TVA = 0.0f;
    float total_Remise = 0.0f;
    float total_TTC = 0.0f;

    for (int i = 0; i < model.getRowCount(); i++) {
        float puHT = formater.parseFloat((String) model.getValueAt(i, 2));
        float tva = Float.parseFloat( model.getValueAt(i, 3).toString());
        float remise = Float.parseFloat( model.getValueAt(i, 4).toString() );
        int qte = (int) model.getValueAt(i, 5);
        float totalHTPerItem = puHT * qte;
        float remiseAmount = totalHTPerItem * (remise / 100);
        float totalHTAfterRemise = totalHTPerItem - remiseAmount;
        float tvaAmount = totalHTAfterRemise * (tva / 100);
        float totalTTCPerItem = totalHTAfterRemise + tvaAmount;

        total_HT += totalHTAfterRemise;
        total_TVA += tvaAmount;
        total_Remise += remiseAmount;
        total_TTC += totalTTCPerItem;
    }

    totalHTField.setText(formater.formatFloat(total_HT));   //total ht
    totalTVA.setText(formater.formatFloat(total_TVA));  //tva
    totalRemiseField.setText(String.valueOf(total_Remise)); //total remis
    totalTTCField.setText(formater.formatFloat(total_TTC)); //total  ttc
    totalNetAPayerField.setText(formater.formatFloat(total_TTC - total_Remise));    //total net à payer
    
}
     
    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
   if(date1.getDate() != null && date2 != null){
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        refreshTable(commandeDAO.findBetweenDates( (String) dateFormat.format(date1.getDate()), (String) dateFormat.format(date2.getDate())));
   }
       
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        query = getActiveQuery();
        refreshTable(commandeDAO.select());
        nomClientKeySearch.setText("");
        date1.setDate(null);
        date2.setDate(null);
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        refreshTable(commandeDAO.select());
        switchToPage(1);
//        afficherBoutton(false);
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_delete_commandeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_commandeActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer cette commande?","Confirmation de suppression",JOptionPane.YES_NO_OPTION) == 0){
            commandeDAO.delete(commandeSelected);
            t = new Toast("Commande supprimée!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
            t.showtoast();
        } 
    }//GEN-LAST:event_btn_delete_commandeActionPerformed

    private void tableInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInputMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableInputMouseClicked

    private void btn_modify_invoice_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modify_invoice_itemActionPerformed
int row = tableListCommande.getSelectedRow();
    if (row != -1) {
        DefaultTableModel model = (DefaultTableModel) tableListCommande.getModel();
        JFrame frame = new JFrame("Gestion des Commandes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        JPanel dialog = new JPanel(new GridLayout(8, 2));
        JTextField articleField = new JTextField(model.getValueAt(row, 0).toString());
        JTextField refField = new JTextField(model.getValueAt(row, 1).toString());
        JTextField puhtField = new JTextField(model.getValueAt(row, 2).toString());
        JTextField tvaField = new JTextField(model.getValueAt(row, 3).toString());
        JSpinner remiseField = new JSpinner(new SpinnerNumberModel(Float.parseFloat(model.getValueAt(row, 4).toString()), 0, 100, 1));
        JSpinner qteField = new JSpinner(new SpinnerNumberModel(Integer.parseInt(model.getValueAt(row, 5).toString()), 0, 100, 1));
        JSpinner qteLivreField = new JSpinner(new SpinnerNumberModel(Integer.parseInt(model.getValueAt(row, 6).toString()), 0, 100, 1)); // Ajout du Spinner pour la Qté livrée
        JSpinner qteValideeField = new JSpinner(new SpinnerNumberModel(Integer.parseInt(model.getValueAt(row, 7).toString()), Integer.parseInt(model.getValueAt(row, 7).toString()), 100, 1)); // Ajout du Spinner pour la Qté validée
        puhtField.setEditable(false);
        refField.setEditable(false);
        dialog.add(new JLabel("Article"));
        dialog.add(articleField);
        dialog.add(new JLabel("Réf"));
        dialog.add(refField);
        dialog.add(new JLabel("PU HT"));
        dialog.add(puhtField);
        dialog.add(new JLabel("TVA"));
        dialog.add(tvaField);
        dialog.add(new JLabel("Remise"));
        dialog.add(remiseField);
        dialog.add(new JLabel("Qté"));
        dialog.add(qteField);
        dialog.add(new JLabel("Qté livrée")); // Ajout de l'étiquette pour la Qté livrée
        dialog.add(qteLivreField); // Ajout du Spinner pour la Qté livrée
        dialog.add(new JLabel("Qté validée")); // Ajout de l'étiquette pour la Qté validée
        dialog.add(qteValideeField); // Ajout du Spinner pour la Qté validée

        int result = JOptionPane.showConfirmDialog(frame, dialog, "Modifier Commande", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            model.setValueAt(Float.parseFloat(remiseField.getValue().toString()), row, 4);
            model.setValueAt(Integer.parseInt(qteField.getValue().toString()), row, 5);
            model.setValueAt(Integer.parseInt(qteLivreField.getValue().toString()), row, 6); // Mettre à jour la Qté livrée
            model.setValueAt(Integer.parseInt(qteValideeField.getValue().toString()), row, 7); // Mettre à jour la Qté validée

            float puHT = formater.parseFloat(puhtField.getText());
            float tva = Float.parseFloat(tvaField.getText());
            float remise = Float.parseFloat(remiseField.getValue().toString());
            int qte = Integer.parseInt(qteField.getValue().toString());
            int qteLivre = Integer.parseInt(qteLivreField.getValue().toString()); // Récupérer la Qté livrée
            CommandeDetail commDetSelected = commDetDAO.findByCommandeProduit(commandeSelected.getId(), refField.getText());
            commDetSelected.setQuantite(Integer.parseInt(qteField.getValue().toString()));
            commDetSelected.setQuantiteLivree(qteLivre); // Mettre à jour la Qté livrée
            commDetSelected.setRemise(Float.parseFloat(remiseField.getValue().toString()));
            if (commDetDAO.update(commDetSelected)) {
                updateTotals();
                tableListCommande.clearSelection();
                commandeSelected.setMontant(formater.parseFloat(totalNetAPayerField.getText()));
                commandeDAO.update(commandeSelected);
                t = new Toast("Ligne modifiée!", pan.getLocationOnScreen().x + pan.getWidth() - 320, pan.getLocationOnScreen().y + 7);
                t.showtoast();
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Veuillez sélectionner la ligne à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
    }
    tableListCommande.clearSelection();
    }//GEN-LAST:event_btn_modify_invoice_itemActionPerformed

    private void btn_delete_invoice_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_invoice_itemActionPerformed
        int selectedRow = tableListCommande.getSelectedRow();
        if (selectedRow != -1) {
        DefaultTableModel tableModel = (DefaultTableModel) tableListCommande.getModel();
        String refProduitSelected = tableListCommande.getModel().getValueAt(selectedRow, 1).toString();
        float montant = formater.parseFloat(tableListCommande.getModel().getValueAt(selectedRow, 3).toString());
            if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer cette ligne, cette action est irreversible?","Confirmation de suppression",JOptionPane.YES_NO_OPTION) == 0){
                if(commDetDAO.delete(commandeSelected.getId(), produitDAO.find(refProduitSelected).getId())){

                    ((DefaultTableModel) tableListCommande.getModel()).removeRow(selectedRow);
                    updateTotals();
                    commandeSelected.setMontant(formater.parseFloat(totalNetAPayerField.getText()));
                    commandeDAO.update(commandeSelected);
                    t = new Toast("Ligne supprimée!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                    t.showtoast();
                }
            } 
        }else{
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une ligne à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        tableListCommande.clearSelection();
       
    }//GEN-LAST:event_btn_delete_invoice_itemActionPerformed

    private void btnConvertBLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConvertBLActionPerformed
        modal = new ModalBonLivraison(Integer.parseInt(idCommande.getText()));
        modal.setVisible(true);
        
    }//GEN-LAST:event_btnConvertBLActionPerformed

    private void btn_creer_factureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_creer_factureActionPerformed

if (livSelected != null && livSelected.getId() != 0){
            ModalFacture modal = new ModalFacture(Integer.parseInt(idLivraison.getText()));
            modal.setVisible(true);
             modal.initEvent(event, 1);
}
        
    }//GEN-LAST:event_btn_creer_factureActionPerformed

    private void btn_search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search1ActionPerformed
         
//        refreshTable(DAOFactory.getCommandeDAO().findByDate(date1.getDate()));
refreshTable(commandeDAO.filter(nomClientKeySearch.getText()));
        
    }//GEN-LAST:event_btn_search1ActionPerformed

    private void btn_validerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_validerActionPerformed
        switch(commandeSelected.getStatus()){
            case "Brouillon":
                if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir confirmer cette commande?\nLe status sera modifié en 'En préparation' ?","Confirmation de la commande n°"+commandeSelected.getId(),JOptionPane.YES_NO_OPTION) == 0){
                    commandeSelected.setStatus("En préparation");
                    if(commandeDAO.update(commandeSelected)){
                    t = new Toast("Commande en préparation!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                    t.showtoast();
                    updateButtonVisibility();
                    } 
                } 
               break;
            default:
                if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir clôturer cette commande?\n\nUne fois marquée 'Clôturée', une commande est non modifiable.\nAssurez-vous d'avoir vérifié correctement la commande","Validation de la commande n°"+commandeSelected.getId(),JOptionPane.YES_NO_OPTION) == 0){
                    commandeSelected.setStatus("Clôturée");
                    if(commandeDAO.update(commandeSelected)){
                    t = new Toast("Commande Clôturée!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                    t.showtoast();
                    updateButtonVisibility();
                    }
                } 
               break;
       }
        
    }//GEN-LAST:event_btn_validerActionPerformed

    private void btn_livrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_livrerActionPerformed
        if ( "Livrée".equals(commandeSelected.getStatus()) ){
            if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir modifier le status en 'Livrée'?","Message",JOptionPane.YES_NO_OPTION) == 0){
            commandeSelected.setStatus("En attente");
            if(commandeDAO.update(commandeSelected)){
                new Toast("Commande marquée en attente", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7).showtoast();
                updateButtonVisibility();
            }
            }
        } else {
             if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir modifier le status en 'Livrée'?","Message",JOptionPane.YES_NO_OPTION) == 0){
            commandeSelected.setStatus("Livrée");
            if(commandeDAO.update(commandeSelected)){
                new Toast("Commande marquée livrée", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7).showtoast();
                updateButtonVisibility();
            }
            }
        }
         
        
    }//GEN-LAST:event_btn_livrerActionPerformed

    private void toogleModification(){
        afficherBoutton(true);
    }
    private void afficherBoutton(boolean a){
    btn_modif_comm.setVisible(a);
    btn_modif_fac.setVisible(a);
    btn_modif_bl.setVisible(a);
    btn_modify_invoice_item.setVisible(a);
    btn_modify_invoice_item.setVisible(a);
    btn_delete_invoice_item.setVisible(a);
    }
    
    private void btn_modif_commActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modif_commActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir sauvegarder les modification sur la commande n°"+commandeSelected.getId()+" ?","Confirmation de mise à jour",JOptionPane.YES_NO_OPTION) == 0){
            commandeSelected.setRefCommande(refCommandeField.getText());
            commandeSelected.setDescription(descriptionCommande.getText());
            if(commandeDAO.update(commandeSelected)){
                t = new Toast("Modification enregistrée!!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
            }
            } 
    }//GEN-LAST:event_btn_modif_commActionPerformed

    private void btn_modif_facActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modif_facActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir valider cette commande?","Confirmation validation",JOptionPane.YES_NO_OPTION) == 0){
            javaapp.bean.Facture fac = facSelected;
            fac.setRefFacture(refFacture.getText());
            fac.setAdresse(refFacture.getText());
            if(facDAO.update(fac)){
                t = new Toast("Modification enregistrée!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
            }
       } 
    }//GEN-LAST:event_btn_modif_facActionPerformed

    private void btn_modif_blActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modif_blActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir valider cette commande?","Confirmation validation",JOptionPane.YES_NO_OPTION) == 0){
//            LivraisonDAO livDAO = DAOFactory.getLivraisonDAO();
            javaapp.bean.Livraison liv = livSelected;
            liv.setDateLivraison("2024-12-04");
//            liv.setDescription(description_fac.getText());
//            liv.setDateLivraison(dateLivraison.getText());    //modif
            if(livDAO.update(liv)){
                t = new Toast("Création de la facture réussie!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
            }
            JOptionPane.showMessageDialog(null, "Commande validée!","Confirmation",JOptionPane.INFORMATION_MESSAGE);
        } 
    }//GEN-LAST:event_btn_modif_blActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
           if(JOptionPane.showConfirmDialog(null, "Voulez-vous imprimer  cette bon de commande ?","Message de confirmation",JOptionPane.YES_NO_OPTION) == 0){
        PdfReceiptGenerator p = new PdfReceiptGenerator();
        BCCustomer.printPDF(pan, commandeSelected);
    }
    }//GEN-LAST:event_btn_printActionPerformed

    private void btn_add_invoice_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_invoice_itemActionPerformed
    TableModel model = tableListCommande.getModel();
    JFrame frame = new JFrame("Gestion des Commandes");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 400);

    JPanel dialog = new JPanel(new GridLayout(8, 2));
    JSpinner remiseField = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
    JSpinner qteField = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1));
//    JSpinner qteLivreField = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1)); // Ajout du Spinner pour la Qté livrée
//    JSpinner qteValideeField = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1)); // Ajout du Spinner pour la Qté validée
    dialog.add(new JLabel("Référence article"));

    JComboBox<String> produitComboBox = new JComboBox<>();

    for (javaapp.bean.Produit p : produitDAO.select()) {
        produitComboBox.addItem(p.getRefProduit());
    }
    produitComboBox.setSelectedIndex(0);

    dialog.add(produitComboBox);
    dialog.add(new JLabel("Remise"));
    dialog.add(remiseField);
    dialog.add(new JLabel("Qté"));
    dialog.add(qteField);
//    dialog.add(new JLabel("Qté livrée")); // Ajout de l'étiquette pour la Qté livrée
//    dialog.add(qteLivreField); // Ajout du Spinner pour la Qté livrée
//    dialog.add(new JLabel("Qté validée")); // Ajout de l'étiquette pour la Qté validée
//    dialog.add(qteValideeField); // Ajout du Spinner pour la Qté validée

    int result = JOptionPane.showConfirmDialog(frame, dialog, "Ajouter une ligne", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
        Produit produit = produitDAO.find(produitComboBox.getSelectedItem().toString());
        float puHT = produit.getPUHT();
        float tva = produit.getTVA();
        int qte = Integer.parseInt(qteField.getValue().toString());
//        int qteLivre = Integer.parseInt(qteLivreField.getValue().toString()); // Récupérer la Qté livrée
//        int qteValidee = Integer.parseInt(qteValideeField.getValue().toString()); // Récupérer la Qté validée
        float remiseItem = Float.parseFloat(remiseField.getValue().toString());
        float totalTTC = Calculator.getTotalTTC(puHT, remiseItem, tva, qte);

//        CommandeDetailDAO commDetDAO = DAOFactory.getCommandeDetailDAO();

        CommandeDetail commDet = new CommandeDetail();
        commDet.setIdCommande(commandeSelected.getId());
        commDet.setIdProduit(produit.getId());
        commDet.setProduit(produit);
        commDet.setQuantite(qte);
//        commDet.setQuantiteLivree(qteLivre); // Mettre à jour la Qté livrée
////        commDet.setQuantiteValidee(qteValidee); // Mettre à jour la Qté validée
        commDet.setRemise(remiseItem);
        commDet.setMontant(totalTTC);

        if (commDetDAO.create(commDet)) {
            // Mettre à jour le tableau
            Object[] newRow = {
                produit.getDesignation(),
                produitComboBox.getSelectedItem().toString(),
                formater.formatFloat(produit.getPUHT()),
                tva,
                (int) remiseField.getValue(),
                qte,
                0, // Afficher la Qté livrée dans le tableau
                (int) remiseField.getValue(), // Afficher la Qté validée dans le tableau
                formater.formatFloat(totalTTC)
            };

            ((DefaultTableModel) tableListCommande.getModel()).addRow(newRow);
            updateTotals();
            tableListCommande.clearSelection();
            t = new Toast("Nouvelle ligne ajoutée!", pan.getLocationOnScreen().x + pan.getWidth() - 320, pan.getLocationOnScreen().y + 7);
            t.showtoast();
            commandeSelected.setMontant(formater.parseFloat(totalNetAPayerField.getText()));
            commandeDAO.update(commandeSelected);
        }
    }
    }//GEN-LAST:event_btn_add_invoice_itemActionPerformed

    private void btn_annuler_commandeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_annuler_commandeActionPerformed
        if ( !"Annulée".equals(commandeSelected.getStatus()) ){
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir annuler cette commande?","Confirmation d'annulation",JOptionPane.YES_NO_OPTION) == 0){
            commandeSelected.setStatus("Annulée");
            if(commandeDAO.update(commandeSelected)){
                t = new Toast("Commande annulée!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
                updateButtonVisibility();
            }
        }
        }else{
            if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir relancer cette commande?","Confirmation d'annulation",JOptionPane.YES_NO_OPTION) == 0){
            commandeSelected.setStatus("En préparation");
            if(commandeDAO.update(commandeSelected)){
                t = new Toast("Commande en préparation!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
                updateButtonVisibility();
            }
        }
        }
    }//GEN-LAST:event_btn_annuler_commandeActionPerformed

    private void btn_classer_factureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_classer_factureActionPerformed
        if ( "Non".equals(commandeSelected.getAvoirFacture()) ){
            if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir classer la commande à 'Facturée' ?","Confirmation de facturation",JOptionPane.YES_NO_OPTION) == 0){
                commandeSelected.setAvoirFacture("Oui");
                if( commandeDAO.update(commandeSelected) ){
                    t = new Toast("Commande marquée facturé!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                    t.showtoast();
                }
            }
        }else{
            if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir classer la commande à 'Non facturée' ?","Confirmation de facturation",JOptionPane.YES_NO_OPTION) == 0){
                commandeSelected.setAvoirFacture("Non");
                if( commandeDAO.update(commandeSelected) ){
                    t = new Toast("Commande marquée Non facturée!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                    t.showtoast();
                }
            }
        }
        updateButtonVisibility();
    }//GEN-LAST:event_btn_classer_factureActionPerformed
    
    public void refreshTable(List<javaapp.bean.CommandeC> commandes){
        String[] column = {"Référence", "Date commande","Client", "Montant HT","Date livraison", "Facturé", "Status"};
        Object[] data = new Object[8];
        DefaultTableModel model = new DefaultTableModel(null, column);
       for(javaapp.bean.CommandeC commande : commandes){
            data[0]=commande.getRefCommande();
            data[1]=DateConverter.convertToLetter(commande.getDateCommande());
            data[2]=commande.getClient().getNom()+" "+commande.getClient().getPrenom();
            data[3]=formater.formatFloat(commande.getMontant());
            javaapp.bean.Livraison liv = livDAO.findByCommande(commande.getId());
            data[4]=( liv != null && liv.getId() != 0) ? DateConverter.convertToLetter(liv.getDateLivraison()) : "- ";
            data[5]= commande.getAvoirFacture();
            data[6]=commande.getStatus();
            
            model.addRow(data);
        }
        tableInput.setModel(model);
        nombre_ligne.setText("Commandes ("+tableInput.getRowCount()+")");
        facture_non_paye.setText("("+commandeDAO.getCountToValidate()+ ") Commandes à valider");
        displayRows(0,13);
        refreshPagination();
        tableOutput.getColumnModel().getColumn(6).setCellRenderer(new TableStatusCellRender());
    }
 
    public void setEmptyForm(){
        btn_save_state="insert";
    }        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField adresse_fac;
    private javax.swing.JTextField adresse_livraison;
    private javaapp.component.ButtonRadius btnConvertBL;
    private javaapp.component.ButtonRadius btn_add_invoice_item;
    private javaapp.component.ButtonRadius btn_annuler_commande;
    private javaapp.component.ButtonRadius btn_back;
    private javaapp.component.ButtonRadius btn_classer_facture;
    private javaapp.component.ButtonRadius btn_creer_facture;
    private javaapp.component.ButtonRadius btn_delete_commande;
    private javaapp.component.ButtonRadius btn_delete_invoice_item;
    private javaapp.component.ButtonRadius btn_livrer;
    private javaapp.component.ButtonRadius btn_modif_bl;
    private javaapp.component.ButtonRadius btn_modif_comm;
    private javaapp.component.ButtonRadius btn_modif_fac;
    private javaapp.component.ButtonRadius btn_modify_invoice_item;
    private javaapp.component.ButtonRadius btn_print;
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_search;
    private javaapp.component.ButtonRadius btn_search1;
    private javaapp.component.ButtonRadius btn_valider;
    private com.toedter.calendar.JDateChooser date1;
    private com.toedter.calendar.JDateChooser date2;
    private com.toedter.calendar.JDateChooser dateCommande;
    private com.toedter.calendar.JDateChooser dateFacture;
    private com.toedter.calendar.JDateChooser dateLivraison;
    private javax.swing.JTextPane descriptionCommande;
    private javax.swing.JLayeredPane factureSection;
    private javax.swing.JLabel facture_non_paye;
    private javax.swing.JLabel heureCommande;
    private javax.swing.JLabel heureFacture;
    private javax.swing.JComboBox<String> heureLivraison;
    private javax.swing.JLabel idCommande;
    private javax.swing.JLabel idFacture;
    private javax.swing.JLabel idLivraison;
    private javax.swing.JLabel id_client;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JLayeredPane livraisonSection;
    private javax.swing.JLabel menu5;
    private javax.swing.JComboBox<String> minuteLivraison;
    private javax.swing.JTextField nomClient;
    private javax.swing.JTextField nomClientKeySearch;
    private javax.swing.JLabel nombre_ligne;
    private javax.swing.JPanel page1;
    private javax.swing.JPanel page2;
    private javaapp.pagination.Pagination pagination1;
    private javaapp.component.PanelBorderRound panelBorderRound1;
    private javax.swing.JTextField refCommandeField;
    private javax.swing.JTextField refFacture;
    private javax.swing.JTextField refLivraison;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JScrollPane scrollTable1;
    private javaapp.component.Table tableInput;
    private javaapp.component.Table tableListCommande;
    private javaapp.component.Table tableOutput;
    private javax.swing.JLabel totalHTField;
    private javax.swing.JLabel totalNetAPayerField;
    private javax.swing.JLabel totalRemiseField;
    private javax.swing.JLabel totalTTCField;
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
