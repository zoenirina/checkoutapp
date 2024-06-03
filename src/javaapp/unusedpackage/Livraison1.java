/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.unusedpackage;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.component.FormatUtils;
import javaapp.component.ModalActionEvent;
import javaap.modal.ModalFactures;
import javaapp.component.DateConverter;
import javaapp.component.Toast;
import javaapp.dao.LivraisonDAO;
import javaapp.factory.DAOFactory;
import javaapp.page.PageList.Magasin;
import javaapp.pagination.EventPagination;
import javaapp.pagination.PaginationItemRenderStyle1;
import javaapp.swing.TableStatusCellRender;
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
    private javaapp.bean.Livraison livSelected;
    private ModalActionEvent event;
    
    public Livraison1(JPanel panel) {
        initComponents();
        pan= panel;
        init();
    }
    
    private void init(){
    formater = new FormatUtils();
//        query= getActiveQuery();
        refreshTable(DAOFactory.getLivraisonDAO().select());
        scrollTable.getViewport().setBackground(Color.white);
        filterData(1);
        
        displayRows(0,13);
        initialisePagination();
        pagination1.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                System.out.print(page);
                 displayRows((page - 1) * 13, page*13);
            }
        });
        event =  new ModalActionEvent(){
            @Override
            public void onEdit(int row){
                btn_creer_facture.setVisible(false);
                t = new Toast("Création de la facture réussie!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
                System.out.print("Evenement dans commande"+row);
            }
        };
        tableOutput.getColumnModel().getColumn(5).setCellRenderer(new TableStatusCellRender());
        switchToPage(1);
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
        btn_modifier1 = new javaapp.component.ButtonRadius();
        panelBorderRound1 = new javaapp.component.PanelBorderRound();
        nombre_ligne = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        date1 = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        date2 = new com.toedter.calendar.JDateChooser();
        btn_search1 = new javaapp.component.ButtonRadius();
        page2 = new javax.swing.JPanel();
        btn_back = new javaapp.component.ButtonRadius();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableListCommande = new javaapp.component.Table();
        jLabel7 = new javax.swing.JLabel();
        btn_delete_invoice_item = new javaapp.component.ButtonRadius();
        btn_modify_invoice_item = new javaapp.component.ButtonRadius();
        btn_creer_facture = new javaapp.component.ButtonRadius();
        btn_delete_commande = new javaapp.component.ButtonRadius();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        nomClient = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        btn_modify_invoice_item2 = new javaapp.component.ButtonRadius();
        jLabel52 = new javax.swing.JLabel();
        fac_id_client = new javax.swing.JLabel();
        nomClient1 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        nomClient2 = new javax.swing.JTextField();
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
        jPanel4 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        fac_descriptionCommande = new javax.swing.JTextPane();
        fac_dateCommande = new javax.swing.JTextField();
        btn_modifier = new javaapp.component.ButtonRadius();
        jPanel7 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        fac_descriptionCommande1 = new javax.swing.JTextPane();
        fac_dateCommande1 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        fac_refCommande1 = new javax.swing.JLabel();
        idCommande1 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        fac_refCommande2 = new javax.swing.JLabel();
        idCommande2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        fac_descriptionCommande2 = new javax.swing.JTextPane();
        fac_dateCommande2 = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 1344));

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
        tableOutput.setRowHeight(45);
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

        btn_modifier1.setBackground(new java.awt.Color(102, 102, 255));
        btn_modifier1.setBorder(null);
        btn_modifier1.setForeground(new java.awt.Color(255, 255, 255));
        btn_modifier1.setText("+ Ajouter");
        btn_modifier1.setBorderColor(new java.awt.Color(102, 102, 255));
        btn_modifier1.setColor(new java.awt.Color(102, 102, 255));
        btn_modifier1.setColorClick(new java.awt.Color(102, 102, 255));
        btn_modifier1.setColorOver(new java.awt.Color(102, 102, 255));
        btn_modifier1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        panelBorderRound1.setBackground(new java.awt.Color(227, 227, 255));

        nombre_ligne.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nombre_ligne.setForeground(new java.awt.Color(102, 102, 255));
        nombre_ligne.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombre_ligne.setText("Documents (5)");

        javax.swing.GroupLayout panelBorderRound1Layout = new javax.swing.GroupLayout(panelBorderRound1);
        panelBorderRound1.setLayout(panelBorderRound1Layout);
        panelBorderRound1Layout.setHorizontalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nombre_ligne, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBorderRound1Layout.setVerticalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nombre_ligne, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Du");

        date1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("au");

        date2.setBackground(new java.awt.Color(255, 255, 255));

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

        javax.swing.GroupLayout page1Layout = new javax.swing.GroupLayout(page1);
        page1.setLayout(page1Layout);
        page1Layout.setHorizontalGroup(
            page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollTable, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(page1Layout.createSequentialGroup()
                        .addComponent(titre_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollTable1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(page1Layout.createSequentialGroup()
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(page1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(34, 34, 34)
                                .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(page1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(35, 35, 35)
                                .addComponent(date2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addComponent(btn_search1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(page1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(80, 80, 80))
                            .addGroup(page1Layout.createSequentialGroup()
                                .addComponent(nomClientKeySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(page1Layout.createSequentialGroup()
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(linegroup, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_modifier1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        page1Layout.setVerticalGroup(
            page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
                        .addComponent(linegroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btn_modifier1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(page1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel5)))
                .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(page1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(page1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_search, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nomClientKeySearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(date2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_search1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(page1Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(jLabel1)))
                    .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollTable, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(scrollTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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
        btn_modify_invoice_item.setColorOver(new java.awt.Color(132, 132, 232));
        btn_modify_invoice_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modify_invoice_itemActionPerformed(evt);
            }
        });

        btn_creer_facture.setBackground(new java.awt.Color(153, 153, 255));
        btn_creer_facture.setBorder(null);
        btn_creer_facture.setForeground(new java.awt.Color(255, 255, 255));
        btn_creer_facture.setText("Classer livrée");
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

        btn_modify_invoice_item2.setBorder(null);
        btn_modify_invoice_item2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/iconModify.png"))); // NOI18N
        btn_modify_invoice_item2.setBorderColor(new java.awt.Color(153, 153, 255));
        btn_modify_invoice_item2.setColor(new java.awt.Color(102, 102, 255));
        btn_modify_invoice_item2.setColorClick(new java.awt.Color(255, 255, 255));
        btn_modify_invoice_item2.setColorOver(new java.awt.Color(132, 132, 232));
        btn_modify_invoice_item2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modify_invoice_item2ActionPerformed(evt);
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

        nomClient2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nomClient2.setForeground(new java.awt.Color(51, 51, 51));

        btn_modify_invoice_item3.setBackground(new java.awt.Color(153, 153, 255));
        btn_modify_invoice_item3.setBorder(null);
        btn_modify_invoice_item3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/iconModify.png"))); // NOI18N
        btn_modify_invoice_item3.setBorderColor(new java.awt.Color(153, 153, 255));
        btn_modify_invoice_item3.setColor(new java.awt.Color(153, 153, 255));
        btn_modify_invoice_item3.setColorClick(new java.awt.Color(153, 153, 255));
        btn_modify_invoice_item3.setColorOver(new java.awt.Color(132, 132, 232));
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
                        .addComponent(btn_modify_invoice_item2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(nomClient2, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_modify_invoice_item3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nomClient2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_modify_invoice_item3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(fac_id_client))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_modify_invoice_item2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jPanel4.setBackground(new java.awt.Color(241, 248, 240));

        jLabel53.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(43, 40, 54));
        jLabel53.setText("Date de fin de validité");

        jTextField3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(43, 40, 54));

        jLabel50.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(43, 40, 54));
        jLabel50.setText("Description");

        jLabel40.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(43, 40, 54));
        jLabel40.setText("Date de commande");

        fac_descriptionCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        fac_descriptionCommande.setForeground(new java.awt.Color(43, 40, 54));
        jScrollPane1.setViewportView(fac_descriptionCommande);

        fac_dateCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        fac_dateCommande.setForeground(new java.awt.Color(43, 40, 54));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3)
                    .addComponent(fac_dateCommande)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fac_dateCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
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

        jTextField4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(43, 40, 54));

        jLabel51.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(43, 40, 54));
        jLabel51.setText("Description");

        jLabel41.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(43, 40, 54));
        jLabel41.setText("Adresse de facturation");

        fac_descriptionCommande1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        fac_descriptionCommande1.setForeground(new java.awt.Color(43, 40, 54));
        jScrollPane2.setViewportView(fac_descriptionCommande1);

        fac_dateCommande1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        fac_dateCommande1.setForeground(new java.awt.Color(43, 40, 54));

        jPanel9.setBackground(new java.awt.Color(204, 204, 255));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Payé");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4)
                    .addComponent(fac_dateCommande1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54)
                    .addComponent(jLabel41))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fac_dateCommande1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addComponent(idCommande1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(fac_refCommande1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel17.setBackground(new java.awt.Color(153, 153, 255));
        jPanel17.setPreferredSize(new java.awt.Dimension(440, 4));

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("BL n°");

        fac_refCommande2.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        fac_refCommande2.setForeground(new java.awt.Color(204, 255, 255));
        fac_refCommande2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        fac_refCommande2.setText("CO0012");

        idCommande2.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        idCommande2.setForeground(new java.awt.Color(204, 255, 255));
        idCommande2.setText("1346");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel14)
                .addGap(9, 9, 9)
                .addComponent(idCommande2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(fac_refCommande2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addComponent(idCommande2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(fac_refCommande2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(241, 248, 240));

        jLabel55.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(43, 40, 54));
        jLabel55.setText("Date prévue de livraison");

        jTextField5.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(43, 40, 54));

        jLabel56.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(43, 40, 54));
        jLabel56.setText("Condition et condition de reglement");

        jLabel42.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(43, 40, 54));
        jLabel42.setText("Adresse de livraison");

        fac_descriptionCommande2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        fac_descriptionCommande2.setForeground(new java.awt.Color(43, 40, 54));
        jScrollPane4.setViewportView(fac_descriptionCommande2);

        fac_dateCommande2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        fac_dateCommande2.setForeground(new java.awt.Color(43, 40, 54));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5)
                    .addComponent(fac_dateCommande2)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55)
                    .addComponent(jLabel56))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fac_dateCommande2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout page2Layout = new javax.swing.GroupLayout(page2);
        page2.setLayout(page2Layout);
        page2Layout.setHorizontalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(page2Layout.createSequentialGroup()
                                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                                    .addGroup(page2Layout.createSequentialGroup()
                                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(page2Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page2Layout.createSequentialGroup()
                                                .addComponent(btn_modify_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btn_delete_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                                    .addGroup(page2Layout.createSequentialGroup()
                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(page2Layout.createSequentialGroup()
                        .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_creer_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_modifier, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_delete_commande, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57))
        );
        page2Layout.setVerticalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_creer_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete_commande, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_modifier, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(page2Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, Short.MAX_VALUE)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_delete_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(page2Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel7)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page2Layout.createSequentialGroup()
                        .addComponent(btn_modify_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(page2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(page1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

//    private void filterData(){
//     int selectedOption = periodeLivraison.getSelectedIndex(); 
//         String FILTER_CLIENT=" AND ( cl.nom like '%"+nomClientKeySearch.getText()+"%' OR cl.prenom like '%"+nomClientKeySearch.getText()+"%' )";
//         String FILTER_REFCOMMANDE=" AND c.refCommande like '%"+refCommande.getText()+"%' ";
//         
//         query = getActiveQuery();
//         query+=(activeMenu == 1)?" WHERE ":" AND ";
//        switch (selectedOption) {
//            case 0:
//                query += " strftime('%Y', ld.dateLivraison) = strftime('%Y', 'now') ";
//                
//                break; 
//            case 1:
//                query += " strftime('%Y-%W', ld.dateLivraison) = strftime('%Y-%W', 'now') ";
//                break;
//            case 2:
//                query += " strftime('%Y-%m', ld.dateLivraison) = strftime('%Y-%m', 'now') ";
//                break;
//            case 3:
//                query += " ld.dateLivraison >= date('now', '-3 month') ";
//                break;
//            case 4:
//                query += " ld.dateLivraison <= date('now', '+3 month') ";
//                break;
//            case 5:
//                query += " ld.dateLivraison <= date('now', '+3 month') ";
//                break; // Aucune condition supplémentaire
//        }
//        if(!nomClientKeySearch.getText().isEmpty()){query += FILTER_CLIENT;}
//        if(!refCommande.getText().isEmpty()){query += FILTER_REFCOMMANDE;}
//        // Mettez à jour votre modèle de tableau avec les données filtrées
//        refreshTable(DAOFactory.getLivraisonDAO().select());
//    }
        private void filterData(int index){
        setActiveMenu(index);
//        query= getActiveQuery();
        displaydata(" ");
        changeActiveButton();
        
    }
    
    private void displaydata(String query){
        
            switch(activeMenu){
            case 1:
                refreshTable(DAOFactory.getLivraisonDAO().select(query));
            break;
            case 2:
                refreshTable(DAOFactory.getLivraisonDAO().selectLivre(query));
            break;
            case 3:
                refreshTable(DAOFactory.getLivraisonDAO().selectEnAttente(query));
            break;
            case 4:
                refreshTable(DAOFactory.getLivraisonDAO().selectEnRetard(query));
                
            break;
            case 5:
                refreshTable(DAOFactory.getLivraisonDAO().selectAnnule(query));
                break;
        }
    }
    
//     private void filterData(int index){
//         
//         setActiveMenu(index);
//        query= getActiveQuery();
//       refreshTable(DAOFactory.getLivraisonDAO().select()); 
//    }
//     
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
    
     private void populateForm(String query, String refCommandeSelected, String refProduitSelected) {
//       try {
//        connectiondb();
//        if (conn == null) {
//            throw new SQLException("Échec de connexion à la base de données.");
//        }
//        stm = conn.prepareStatement(query);
//        stm.setString(1, refCommandeSelected);
//        stm.setString(2, refProduitSelected);
//        resultSet = stm.executeQuery();
//        String refCommande=tableOutput.getModel().getValueAt(tableOutput.getSelectedRow(), 1).toString();
//        while (resultSet.next()) {
//            refCommande1.setText(refCommande);
//            refProduit.setText(resultSet.getString("refProduit"));
//            designationProduit.setText(resultSet.getString("designation_produit"));
//            quantiteCommande.setValue(Integer.parseInt(resultSet.getString("quantite_commandee")));
//            quantiteRecu.setValue(Integer.parseInt(resultSet.getString("quantiteRecu")));
//            quantiteValide.setValue(Integer.parseInt(resultSet.getString("quantiteValide")));
//            dateLivraison.setDate(resultSet.getDate("dateLivraison"));
//            nomClient.setText(resultSet.getString("nom_client") + " " + resultSet.getString("prenom_client"));
//
//        java.sql.Timestamp timestampLivraison = resultSet.getTimestamp("dateLivraison");
//            if (timestampLivraison != null) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(timestampLivraison);
//                
//                // Extraire l'heure et les minutes
//                int heure = calendar.get(Calendar.HOUR_OF_DAY);
//                int minute = calendar.get(Calendar.MINUTE);
//                // Sélectionner les valeurs dans les JComboBox
//                heureLivraison.setSelectedItem(String.valueOf(heure));
//                minuteLivraison.setSelectedItem(String.valueOf(minute));
//            }
//                }
//
//                resultSet.close();
//                stm.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            } finally {
//                try {
//                    if (conn != null) conn.close();
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//            }
    }
    
    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        refreshTable(DAOFactory.getLivraisonDAO().select());
        switchToPage(1);
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_delete_invoice_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_invoice_itemActionPerformed
//        int selectedRow = tableListCommande.getSelectedRow();
//        String designationProduitSelected = tableListCommande.getModel().getValueAt(selectedRow, 0).toString();
//        float montant = formater.parseFloat(tableListCommande.getModel().getValueAt(selectedRow, 3).toString());
//
//        if (selectedRow != -1) {
//
//            if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer cette ligne?","Confirmation de suppression",JOptionPane.YES_NO_OPTION) == 0){
//
//                CommandeCDAO commandeDAO = DAOFactory.getCommandeDAO();
//
//                ProduitDAO produitDAO = DAOFactory.getProduitDAO();
//
//                CommandeDetailDAO commDet = DAOFactory.getCommandeDetailDAO();
//
//                //  modifier le montant dans livraison, facture
//                if(commDet.delete(commandeDAO.find(fac_refCommande.getText()).getId(), produitDAO.find(designationProduitSelected).getId())){
//                    tableListCommande.clearSelection();
//                    ((DefaultTableModel) tableListCommande.getModel()).removeRow(selectedRow);
//                    JOptionPane.showMessageDialog(null, "Ligne supprimé!","Succès",JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        }
    }//GEN-LAST:event_btn_delete_invoice_itemActionPerformed

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

    private void btn_delete_commandeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_commandeActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer cette commande?","Confirmation de suppression",JOptionPane.YES_NO_OPTION) == 0){
            LivraisonDAO livDAO = DAOFactory.getLivraisonDAO();
            livDAO.delete(livSelected);
            JOptionPane.showMessageDialog(null, "Commande suppprimée!","Confirmation",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_delete_commandeActionPerformed

    private void btn_modify_invoice_item2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modify_invoice_item2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_modify_invoice_item2ActionPerformed

    private void btn_modify_invoice_item3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modify_invoice_item3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_modify_invoice_item3ActionPerformed

    private void btn_modifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modifierActionPerformed

//        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir enregistrer les modification?","Confirmation d'annulation",JOptionPane.YES_NO_OPTION) == 0){
//            CommandeCDAO commDAO = DAOFactory.getCommandeDAO();
//            javaapp.bean.CommandeC comm = commandeSelected;
//            comm.setStatus("Annulé");
//            if(commDAO.update(comm)){
//                t = new Toast("CommandeC mise à jour!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
//                t.showtoast();
//            }
//            JOptionPane.showMessageDialog(null, "CommandeC annulée!","Confirmation",JOptionPane.INFORMATION_MESSAGE);
//        }

    }//GEN-LAST:event_btn_modifierActionPerformed

    private void tableInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInputMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableInputMouseClicked

    private void menu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu5MouseClicked
        filterData(5);
    }//GEN-LAST:event_menu5MouseClicked

    private void menu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu4MouseClicked
        filterData(4);
    }//GEN-LAST:event_menu4MouseClicked

    private void menu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu1MouseClicked
        filterData(1);
    }//GEN-LAST:event_menu1MouseClicked

    private void menu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu3MouseClicked
        //        setActiveMenu(3);
        //        query= getActiveQuery();
        //        refreshTable(DAOFactory.getLivraisonDAO().select());
        //        changeActiveButton();
        filterData(3);
    }//GEN-LAST:event_menu3MouseClicked

    private void menu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu2MouseClicked
        filterData(2);
    }//GEN-LAST:event_menu2MouseClicked

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        String FILTER_CLIENT=" ( cl.nom like '%"+nomClientKeySearch.getText()+"%' OR cl.prenom like '%"+nomClientKeySearch.getText()+"%' )";
        String FILTER_REFCOMMANDE="  c.refCommande like '%"+refCommande.getText()+"%' ";
        //
        //         query = getActiveQuery();
        query = (activeMenu == 1) ? " WHERE " : " AND ";
        if(!nomClientKeySearch.getText().isEmpty()){query += FILTER_CLIENT;}
        if(!refCommande.getText().isEmpty()){query += FILTER_REFCOMMANDE;}

        displaydata(query);

    }//GEN-LAST:event_btn_searchActionPerformed

    private void tableOutputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOutputMouseClicked
        DefaultTableModel model = (DefaultTableModel) tableOutput.getModel();
        int rowIndex = tableOutput.getSelectedRow();
        if (rowIndex != -1) {
            switchToPage(2);
            //         int idLivraisonDetail = Integer.parseInt(model.getValueAt(rowIndex, 0).toString());
            String refCommandeSelected = model.getValueAt(rowIndex, 0).toString();
            String refProduitSelected = model.getValueAt(rowIndex, 1).toString();
            populateForm(SELECT_WHERE_ID,refCommandeSelected,refProduitSelected);
        }
    }//GEN-LAST:event_tableOutputMouseClicked

//     private String getActiveQuery(){
//     switch(activeMenu){
//        case 1:
//        return SELECT_ALL;
//        case 2:
//        return SELECT_ALL_LIVREE;
//         case 3:
//        return  SELECT_ALL_EN_ATTENTE;
//         case 4:
//        return SELECT_ALL_RETARD;
//         case 5:
//        return SELECT_ALL_ANNULEE;
//         default:
//        return SELECT_ALL;     
//    }
//     }
    
    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        //        query = getActiveQuery();
        //        refreshTable(DAOFactory.getLivraisonDAO().select());
        displaydata(" ");
        nomClientKeySearch.setText("");
        refCommande.setText("");
        date1.setDate(null);
        date2.setDate(null);
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search1ActionPerformed
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        String FILTER_DATE =" l.dateLivraison BETWEEN '"+(String) dateFormat.format(date1.getDate())+"' AND '"+(String) dateFormat.format(date2.getDate())+"' ";
        query = (activeMenu == 1) ? " WHERE " : " AND ";
        query+=FILTER_DATE;
        displaydata(query);
//        refreshTablewordDAOFactory.getLivraisonDAO().findBetweenDates( (String) dateFormat.format(date1.getDate()), (String) dateFormat.format(date2.getDate())));
    }//GEN-LAST:event_btn_search1ActionPerformed
 
public void refreshTable(List<javaapp.bean.Livraison> livraisons) {
    String[] column = {"Réference", "Date de livraison","Client", "Commande",  "Total TTC","Status"};
    DefaultTableModel model = new DefaultTableModel(null, column);
    try {
        connectiondb();
        if (conn == null) {
            throw new SQLException("Échec de connexion à la base de données.");
        }
        for(javaapp.bean.Livraison liv : livraisons){
            String[] data = new String[6];
            data[0] = liv.getRefLivraison();
            data[1] = DateConverter.convertToLetter(liv.getDateLivraison());
            data[2] = liv.getClient().getNom()+" "+liv.getClient().getPrenom();
            data[3] = DAOFactory.getCommandeDAO().find(liv.getIdCommande()).getRefCommande();
            data[4] = formater.formatFloat(liv.getMontant());
            data[5] = liv.getStatus();
            model.addRow(data);
        }
        
        tableInput.setModel(model);
        nombre_ligne.setText("Livraisons ("+tableInput.getRowCount()+")");
        refreshPagination();
        tableOutput.getColumnModel().getColumn(5).setCellRenderer(new TableStatusCellRender());
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

//  private void setInputSelect(){
//            
//      try {
//                connectiondb();
//                stm = conn.prepareStatement(SELECT_MAGASIN_AND_QTE_STOCK);
//                stm.setString(1, refProduit.getText());
//                resultSet = stm.executeQuery();
//                magasin_qte_stock.addItem("");
//                while (resultSet.next()) {
//                    magasinList.add(resultSet.getString("nomMagasin"));
//                    magasin_qte_stock.addItem(resultSet.getString("nomMagasin")+"................................................................"+resultSet.getInt("quantiteStock"));
//                }
//                resultSet.close();
//                stm.close();
//                conn.close();
//        } catch (SQLException e) {
//        }
//    }
//
//  private void toogleCheck(){
//  quantiteRecu.setEnabled(livraisonStatus.isSelected());
//  quantiteValide.setEnabled(livraisonStatus.isSelected());
//  btn_marquerLivre.setVisible(livraisonStatus.isSelected());
//  checkboxLivree.setBackground((livraisonStatus.isSelected())?Color.decode("#00C308"):Color.decode("#FF3333"));
//  }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_back;
    private javaapp.component.ButtonRadius btn_creer_facture;
    private javaapp.component.ButtonRadius btn_delete_commande;
    private javaapp.component.ButtonRadius btn_delete_invoice_item;
    private javaapp.component.ButtonRadius btn_modifier;
    private javaapp.component.ButtonRadius btn_modifier1;
    private javaapp.component.ButtonRadius btn_modify_invoice_item;
    private javaapp.component.ButtonRadius btn_modify_invoice_item2;
    private javaapp.component.ButtonRadius btn_modify_invoice_item3;
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_search;
    private javaapp.component.ButtonRadius btn_search1;
    private com.toedter.calendar.JDateChooser date1;
    private com.toedter.calendar.JDateChooser date2;
    private javax.swing.JTextField fac_dateCommande;
    private javax.swing.JTextField fac_dateCommande1;
    private javax.swing.JTextField fac_dateCommande2;
    private javax.swing.JTextPane fac_descriptionCommande;
    private javax.swing.JTextPane fac_descriptionCommande1;
    private javax.swing.JTextPane fac_descriptionCommande2;
    private javax.swing.JLabel fac_id_client;
    private javax.swing.JLabel fac_refCommande;
    private javax.swing.JLabel fac_refCommande1;
    private javax.swing.JLabel fac_refCommande2;
    private javax.swing.JLabel idCommande;
    private javax.swing.JLabel idCommande1;
    private javax.swing.JLabel idCommande2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JPanel line1;
    private javax.swing.JPanel line2;
    private javax.swing.JPanel line3;
    private javax.swing.JPanel line4;
    private javax.swing.JPanel line5;
    private javax.swing.JPanel linegroup;
    private javax.swing.JLabel menu1;
    private javax.swing.JLabel menu2;
    private javax.swing.JLabel menu3;
    private javax.swing.JLabel menu4;
    private javax.swing.JLabel menu5;
    private javax.swing.JTextField nomClient;
    private javax.swing.JTextField nomClient1;
    private javax.swing.JTextField nomClient2;
    private javax.swing.JTextField nomClientKeySearch;
    private javax.swing.JLabel nombre_ligne;
    private javax.swing.JPanel page1;
    private javax.swing.JPanel page2;
    private javaapp.pagination.Pagination pagination1;
    private javaapp.component.PanelBorderRound panelBorderRound1;
    private javax.swing.JTextField refCommande;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JScrollPane scrollTable1;
    private javaapp.component.Table tableInput;
    private javaapp.component.Table tableListCommande;
    private javaapp.component.Table tableOutput;
    private javax.swing.JLabel titre_facture;
    private javax.swing.JLabel totalHT;
    private javax.swing.JLabel totalNET;
    private javax.swing.JLabel totalTTC;
    private javax.swing.JLabel totalTVA;
    // End of variables declaration//GEN-END:variables

    /**
     * @param activeMenu the activeMenu to set
     */
    public void setActiveMenu(int activeMenu) {
        this.activeMenu = activeMenu;
    }
}
