/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.page.Achat;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaap.modal.ModalFactureFournisseur;
import javaap.modal.ModalValidationLivraison;
import javaap.modal.ModalValidationReception;
import javaapp.bean.MouvementStock;
import javaapp.component.FormatUtils;
import javaapp.component.ModalActionEvent;
import javaapp.bean.Profile;
import javaapp.bean.ReceptionDetail;
import javaapp.component.Calculator;
import javaapp.component.DateConverter;
import javaapp.component.Toast;
import javaapp.dao.CommandeFournisseurDAO;
import javaapp.dao.FactureFournisseurDAO;
import javaapp.dao.MagasinDAO;
import javaapp.dao.MouvementStockDAO;
import javaapp.dao.ProduitDAO;
import javaapp.dao.ReceptionDAO;
import javaapp.dao.ReceptionDetailDAO;
import javaapp.dao.StockDAO;
import javaapp.factory.DAOFactory;
import javaapp.page.PageList.Magasin;
import javaapp.pagination.EventPagination;
import javaapp.pagination.PaginationItemRenderStyle1;
import javaapp.swing.TableStatusCellRender;
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
public class Reception extends javax.swing.JPanel {
private final int borderRadius = 10;
 Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
    final static String SELECT_WHERE_ID = "SELECT ld.*, p.designation AS designation_produit,p.refProduit AS refProduit, ld.idProduit, cd.quantite AS quantite_commandee, ld.dateLivraison, cl.id AS id_client, cl.nom AS nom_client, cl.prenom AS prenom_client, c.id as idCommande, c.refCommande FROM livraisonDetails ld INNER JOIN commandeDetails cd ON ld.idCommandeDetail = cd.id INNER JOIN produits p ON ld.idProduit = p.id INNER JOIN commandes c ON cd.idCommande = c.id INNER JOIN clients cl ON c.idClient = cl.id WHERE c.refCommande=? AND p.refProduit=? ";
    final static String SELECT_MAGASIN_AND_QTE_STOCK = "SELECT m.id AS idMagasin, m.refMagasin AS nomMagasin,  (SELECT COALESCE(SUM(CASE WHEN ms.sens = 'Entrée' THEN ms.quantite ELSE 0 END), 0) -  COALESCE(SUM(CASE WHEN ms.sens = 'Sortie' THEN ms.quantite ELSE 0 END), 0)  FROM mouvementStock ms WHERE ms.idProduit = (select id FROM produits where refProduit=?)  AND ms.idMagasin = m.id) AS quantiteStock FROM magasin m";
    public String query=null;
    public String btn_save_state= "insert";
    public JPanel pan;
    public Toast t;
    int pageCount=1;
    private int activeMenu=1;
    private FormatUtils formater;
    private javaapp.bean.Reception recepSelected;
    private javaapp.bean.CommandeF commandeSelected;
    private ModalActionEvent event;
    
    private ReceptionDAO receptionDAO;
    private ReceptionDetailDAO recepDetDAO;
    private FactureFournisseurDAO facDAO;
    private MouvementStockDAO mouvStockDAO ;
    private CommandeFournisseurDAO commandeDAO;
    private MagasinDAO magDAO;
    private StockDAO stockDAO;
    private ModalActionEvent event2;
    
    public Reception(JPanel panel) {
        initComponents();
        pan= panel;
        init();
    }
    
    private void init(){
    formater = new FormatUtils();
    
    receptionDAO = DAOFactory.getReceptionDAO();
    recepDetDAO = DAOFactory.getReceptionDetailDAO();
    facDAO = DAOFactory.getFactureFournisseurDAO();
    mouvStockDAO = DAOFactory.getMouvementStockDAO();
    commandeDAO = DAOFactory.getCommandeFournisseurDAO();
    magDAO = DAOFactory.getMagasinDAO();
    stockDAO = DAOFactory.getStockDAO();
            
        refreshTable(receptionDAO.select());
        scrollTable.getViewport().setBackground(Color.white);
        jScrollPane3.getViewport().setBackground(Color.white);
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
                btn_valider.setVisible(false);
                t = new Toast("Création de la facture réussie!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
            }
        };
        
         event2 =  new ModalActionEvent(){
            @Override
            public void onEdit(int row){
                t = new Toast("Bon de réception validée!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
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
        scrollTable1 = new javax.swing.JScrollPane();
        tableInput = new javaapp.component.Table();
        jPanel1 = new javax.swing.JPanel();
        pagination1 = new javaapp.pagination.Pagination();
        panelBorderRound1 = new javaapp.component.PanelBorderRound();
        nombre_ligne = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        date1 = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        date2 = new com.toedter.calendar.JDateChooser();
        btn_search1 = new javaapp.component.ButtonRadius();
        menu5 = new javax.swing.JLabel();
        page2 = new javax.swing.JPanel();
        btn_back = new javaapp.component.ButtonRadius();
        btn_valider = new javaapp.component.ButtonRadius();
        btn_delete_livraison = new javaapp.component.ButtonRadius();
        btn_print = new javaapp.component.ButtonRadius();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btn_recu = new javaapp.component.ButtonRadius();
        jPanel15 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionCommande = new javax.swing.JTextPane();
        dateCommande = new com.toedter.calendar.JDateChooser();
        jPanel12 = new javax.swing.JPanel();
        heureCommande = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        vendeur = new javax.swing.JTextField();
        refCommandeField = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        id_client = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        facturation = new javax.swing.JTextField();
        nomClient = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        idLivraison = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        btn_modif_bl = new javaapp.component.ButtonRadius();
        dateLivraison = new com.toedter.calendar.JDateChooser();
        refLivraison = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        heureLivraison = new javax.swing.JComboBox<>();
        minuteLivraison = new javax.swing.JComboBox<>();
        fraisRecep = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableListCommande = new javaapp.component.Table();
        btn_modify_invoice_item = new javaapp.component.ButtonRadius();
        btn_delete_invoice_item = new javaapp.component.ButtonRadius();
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
        jLabel15 = new javax.swing.JLabel();
        frais = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        btn_annuler_livraison = new javaapp.component.ButtonRadius();
        btn_create_facture = new javaapp.component.ButtonRadius();
        idReception = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 1620));

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
        jLabel2.setText("Nom client");

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
            .addGap(0, 180, Short.MAX_VALUE)
        );
        line3Layout.setVerticalGroup(
            line3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout line4Layout = new javax.swing.GroupLayout(line4);
        line4.setLayout(line4Layout);
        line4Layout.setHorizontalGroup(
            line4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 136, Short.MAX_VALUE)
        );
        line4Layout.setVerticalGroup(
            line4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout line5Layout = new javax.swing.GroupLayout(line5);
        line5.setLayout(line5Layout);
        line5Layout.setHorizontalGroup(
            line5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 124, Short.MAX_VALUE)
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
                .addGap(2, 2, 2)
                .addComponent(line3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(line4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        linegroupLayout.setVerticalGroup(
            linegroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(line1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(line2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(line3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(line4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(line5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu2.setForeground(new java.awt.Color(153, 153, 153));
        menu2.setText("Reçu");
        menu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu2MouseClicked(evt);
            }
        });

        menu3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu3.setForeground(new java.awt.Color(153, 153, 153));
        menu3.setText("Prêt à recevoir");
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
        titre_facture.setText("(0) Réception à valider");

        menu4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu4.setForeground(new java.awt.Color(153, 153, 153));
        menu4.setText("Annulé");
        menu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu4MouseClicked(evt);
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

        menu5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu5.setForeground(new java.awt.Color(153, 153, 153));
        menu5.setText("Brouillon");
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
                    .addComponent(scrollTable, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(page1Layout.createSequentialGroup()
                        .addComponent(titre_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollTable1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(page1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(date2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_search1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nomClientKeySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(page1Layout.createSequentialGroup()
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(page1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(menu1)
                                .addGap(53, 53, 53)
                                .addComponent(menu2)
                                .addGap(62, 62, 62)
                                .addComponent(menu3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(menu4)
                                .addGap(69, 69, 69)
                                .addComponent(menu5))
                            .addComponent(linegroup, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                                .addComponent(menu1)
                                .addComponent(menu3))
                            .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(menu4)
                                .addComponent(menu5)))
                        .addGap(5, 5, 5)
                        .addComponent(linegroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(date2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(date1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_search1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(nomClientKeySearch)
                                .addComponent(refCommande, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)))))
                .addGap(18, 18, 18)
                .addComponent(scrollTable, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
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

        btn_valider.setBackground(new java.awt.Color(66, 230, 119));
        btn_valider.setBorder(null);
        btn_valider.setForeground(new java.awt.Color(255, 255, 255));
        btn_valider.setText("Valider");
        btn_valider.setBorderColor(new java.awt.Color(66, 230, 119));
        btn_valider.setColor(new java.awt.Color(66, 230, 119));
        btn_valider.setColorClick(new java.awt.Color(66, 230, 119));
        btn_valider.setColorOver(new java.awt.Color(66, 230, 119));
        btn_valider.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_valider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_validerActionPerformed(evt);
            }
        });

        btn_delete_livraison.setBackground(new java.awt.Color(255, 226, 226));
        btn_delete_livraison.setBorder(null);
        btn_delete_livraison.setForeground(new java.awt.Color(255, 57, 57));
        btn_delete_livraison.setText("Supprimer");
        btn_delete_livraison.setBorderColor(new java.awt.Color(255, 226, 226));
        btn_delete_livraison.setColor(new java.awt.Color(255, 226, 226));
        btn_delete_livraison.setColorClick(new java.awt.Color(255, 226, 226));
        btn_delete_livraison.setColorOver(new java.awt.Color(255, 204, 204));
        btn_delete_livraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_delete_livraison.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_livraisonActionPerformed(evt);
            }
        });

        btn_print.setBackground(new java.awt.Color(241, 248, 240));
        btn_print.setBorder(null);
        btn_print.setForeground(new java.awt.Color(51, 51, 51));
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

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Bon de reception n°");

        jPanel3.setBackground(new java.awt.Color(241, 248, 240));
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

        btn_recu.setBackground(new java.awt.Color(241, 248, 240));
        btn_recu.setBorder(null);
        btn_recu.setForeground(new java.awt.Color(51, 51, 51));
        btn_recu.setText("Accuser de réception");
        btn_recu.setBorderColor(new java.awt.Color(241, 248, 240));
        btn_recu.setColor(new java.awt.Color(241, 248, 240));
        btn_recu.setColorClick(new java.awt.Color(241, 248, 240));
        btn_recu.setColorOver(new java.awt.Color(241, 248, 240));
        btn_recu.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_recu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_recuActionPerformed(evt);
            }
        });

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
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
        jLabel45.setText("Fournisseur");

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

        jLabel51.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(43, 40, 54));
        jLabel51.setText("Facturation");

        facturation.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        facturation.setForeground(new java.awt.Color(51, 51, 51));

        nomClient.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nomClient.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(78, 78, 78)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(refCommandeField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                                .addComponent(vendeur, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(facturation, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(nomClient, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(dateCommande, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(55, 55, 55)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel49)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(id_client, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel48)
                    .addComponent(jLabel51))
                .addGap(50, 51, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(id_client, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel45))
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nomClient, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(facturation, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel17.setBackground(new java.awt.Color(153, 153, 255));
        jPanel17.setPreferredSize(new java.awt.Dimension(440, 4));

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Réception n°");

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
                .addContainerGap(696, Short.MAX_VALUE))
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
        jLabel42.setText("Frais de livraison");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55))
                .addGap(40, 40, 40)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(refLivraison)
                    .addComponent(fraisRecep, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_modif_bl, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(heureLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(minuteLivraison, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(43, 43, 43))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47)
                            .addComponent(refLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(fraisRecep, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(heureLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(minuteLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(dateLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addComponent(btn_modif_bl, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        jLabel7.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        jLabel7.setText("Détails de la commande");

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

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(43, 40, 54));
        jLabel15.setText("Frais de livraison");

        frais.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        frais.setForeground(new java.awt.Color(43, 40, 54));
        frais.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        frais.setText("0");

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
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(frais, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.TRAILING)
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
                    .addComponent(jLabel15)
                    .addComponent(frais))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        btn_annuler_livraison.setBackground(new java.awt.Color(255, 226, 226));
        btn_annuler_livraison.setBorder(null);
        btn_annuler_livraison.setForeground(new java.awt.Color(255, 57, 57));
        btn_annuler_livraison.setText("Annuler le transfert");
        btn_annuler_livraison.setBorderColor(new java.awt.Color(255, 226, 226));
        btn_annuler_livraison.setColor(new java.awt.Color(255, 226, 226));
        btn_annuler_livraison.setColorClick(new java.awt.Color(255, 226, 226));
        btn_annuler_livraison.setColorOver(new java.awt.Color(255, 204, 204));
        btn_annuler_livraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_annuler_livraison.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_annuler_livraisonActionPerformed(evt);
            }
        });

        btn_create_facture.setBackground(new java.awt.Color(241, 248, 240));
        btn_create_facture.setBorder(null);
        btn_create_facture.setForeground(new java.awt.Color(51, 51, 51));
        btn_create_facture.setText("Convertir en facture");
        btn_create_facture.setBorderColor(new java.awt.Color(241, 248, 240));
        btn_create_facture.setColor(new java.awt.Color(241, 248, 240));
        btn_create_facture.setColorClick(new java.awt.Color(241, 248, 240));
        btn_create_facture.setColorOver(new java.awt.Color(241, 248, 240));
        btn_create_facture.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_create_facture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_create_factureActionPerformed(evt);
            }
        });

        idReception.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        idReception.setForeground(new java.awt.Color(43, 40, 54));

        javax.swing.GroupLayout page2Layout = new javax.swing.GroupLayout(page2);
        page2.setLayout(page2Layout);
        page2Layout.setHorizontalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page2Layout.createSequentialGroup()
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 961, Short.MAX_VALUE)
                            .addGroup(page2Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_modify_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_delete_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(page2Layout.createSequentialGroup()
                                .addComponent(btn_recu, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_annuler_livraison, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_delete_livraison, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_valider, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 961, Short.MAX_VALUE)
                            .addGroup(page2Layout.createSequentialGroup()
                                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(page2Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(idReception, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 958, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 961, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_create_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        page2Layout.setVerticalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_create_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(idReception, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_recu, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_annuler_livraison, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete_livraison, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_valider, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel7)
                        .addGap(20, 20, 20))
                    .addGroup(page2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_delete_invoice_item, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_modify_invoice_item, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(266, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(page2, javax.swing.GroupLayout.PREFERRED_SIZE, 1003, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(page1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        private void filterData(int index){
        setActiveMenu(index);
//        query= getActiveQuery();
        displaydata(" ");
        changeActiveButton();
        
    }
    
    private void displaydata(String query){
        
            switch(activeMenu){
            case 1:
                refreshTable(receptionDAO.select(query));
            break;
            case 2:
                refreshTable(receptionDAO.selectRecu(query));
            break;
            case 3:
                refreshTable(receptionDAO.selectPretARecevoir(query));
            break;
            case 4:
                refreshTable(receptionDAO.selectAnnule(query));
                
            break;
            case 5:
                refreshTable(receptionDAO.selectAnnule(query));
                break;
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
    
     private void populateForm(String query, String refCommandeSelected) {
            
        Profile profile = DAOFactory.getProfileDAO().find(commandeSelected.getIdCreateur());
        vendeur.setText(profile.getNom() +" "+ profile.getPrenom());//decomm
        heureCommande.setText(DateConverter.convertToHour(commandeSelected.getDateCommande()));
        nomClient.setText(commandeSelected.getFournisseur().getNom()+" "+commandeSelected.getFournisseur().getPrenom());
        try {
            dateCommande.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(commandeSelected.getDateCommande())); 
        } catch (ParseException ex) {
            Logger.getLogger(Reception.class.getName()).log(Level.SEVERE, null, ex);
        }
        refCommandeField.setText( commandeSelected.getRefCommande() ); 
        idLivraison.setText(String.valueOf(recepSelected.getId()));
        idReception.setText(String.valueOf(recepSelected.getId()));
        refLivraison.setText(recepSelected.getRefReception());
        fraisRecep.setValue(recepSelected.getFrais());
        frais.setText(String.valueOf(recepSelected.getFrais()));
        try {
            if(!recepSelected.getDateReception().isEmpty()){
            DateConverter.initializeDate(dateLivraison, heureLivraison, minuteLivraison, recepSelected.getDateReception());
            }
        } catch (ParseException ex) {
            Logger.getLogger(Commande.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        refreshTable(receptionDAO.select());
        switchToPage(1);
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_validerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_validerActionPerformed
        ModalValidationReception modal = new ModalValidationReception(recepSelected);
        modal.setVisible(true);
        modal.initEvent(event2, 1);

//        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir valider ce bon de réception? \n\nAprès cette opération, il est impossible de modifier le document !","Confirmation de validation",JOptionPane.YES_NO_OPTION) == 0){   
//        
//            DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            LocalDateTime now = LocalDateTime.now();
//            
//            for(int i = tableListCommande.getRowCount()-1 ; i >= 0; i--){
//            
//                String refProdSelected = tableListCommande.getModel().getValueAt(i, 1).toString();
//                String qte = tableListCommande.getModel().getValueAt(i, 8).toString();
//                ReceptionDetail recepDetail = recepDetDAO.findByReceptionProduit(recepSelected.getId(), refProdSelected);
//                MouvementStock mouvement = mouvStockDAO.findIN(recepDetail.getId());
//                mouvement.setDateMouvement (now.format(dtFormat));
//                mouvement.setEstValide ("Oui");
//                mouvement.setIdSourceEntree(recepDetail.getId());
//                if( mouvStockDAO.updateIN (mouvement)){
//                    new Toast("Bon de réception validé!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7).showtoast();
//                    updateButtonVisibility();
//                }
//            }
//            recepSelected.setStatus("Validée");
//            receptionDAO.update(recepSelected);
//        }
    }//GEN-LAST:event_btn_validerActionPerformed

    private void btn_delete_livraisonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_livraisonActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer ce bon de réception ["+recepSelected.getRefReception()+"] ?","Confirmation de suppression",JOptionPane.YES_NO_OPTION) == 0){
            if(receptionDAO.delete(recepSelected)){
                t = new Toast("Bon de réception supprimé!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
            }
        }
    }//GEN-LAST:event_btn_delete_livraisonActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed

        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir enregistrer les modification?","Confirmation d'annulation",JOptionPane.YES_NO_OPTION) == 0){
           

        }

    }//GEN-LAST:event_btn_printActionPerformed

    private void tableInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInputMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableInputMouseClicked

    private void menu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu4MouseClicked
        filterData(4);
    }//GEN-LAST:event_menu4MouseClicked

    private void menu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu1MouseClicked
        filterData(1);
    }//GEN-LAST:event_menu1MouseClicked

    private void menu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu3MouseClicked
        //        setActiveMenu(3);
        //        query= getActiveQuery();
        //        refreshTable(receptionDAO.select());
        //        changeActiveButton();
        filterData(3);
    }//GEN-LAST:event_menu3MouseClicked

    private void menu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu2MouseClicked
        filterData(2);
    }//GEN-LAST:event_menu2MouseClicked

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        String FILTER_CLIENT=" ( cl.nom like '%"+nomClientKeySearch.getText()+"%' OR cl.prenom like '%"+nomClientKeySearch.getText()+"%' )";
        String FILTER_REFCOMMANDE="  cf.refCommande like '%"+refCommande.getText()+"%' ";
        
        query = (activeMenu == 1) ? " WHERE " : " AND ";
        
        if(!nomClientKeySearch.getText().isEmpty()){query += FILTER_CLIENT;}
        if(!refCommande.getText().isEmpty()){query += FILTER_REFCOMMANDE;}

        displaydata(query);

    }//GEN-LAST:event_btn_searchActionPerformed

    private void tableOutputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOutputMouseClicked
        switchToPage(2);
        DefaultTableModel model = (DefaultTableModel) tableOutput.getModel();
        int rowIndex = tableOutput.getSelectedRow();
        if (rowIndex != -1) {
            
            String refReceptionSelected = model.getValueAt(rowIndex, 0).toString();
            
            recepSelected = receptionDAO.find(refReceptionSelected);
            commandeSelected = commandeDAO.find(recepSelected.getIdCommande());
            populateForm(SELECT_WHERE_ID,refReceptionSelected);
            updateButtonVisibility();
            List <ReceptionDetail> recepDets = recepDetDAO.select(recepSelected.getId());
            String[] column = {"Article","Réf","PU HT","TVA", "Remise", "Qté commandé", "Qté colissé", "Total TTC", "Entrepôt", "Dispo"};
            DefaultTableModel model1 = new DefaultTableModel(null, column);
             Object[] data = new Object[10];
            for(javaapp.bean.ReceptionDetail recepDet : recepDets){
                
            float tva = recepDet.getProduit().getTVA();
            int qte = recepDet.getCommandeDetail().getQuantite();
            float PUHT = recepDet.getProduit().getPUHT();
            float remise = recepDet.getCommandeDetail().getRemise();
            
            data[0] = recepDet.getProduit().getDesignation();
            data[1] = recepDet.getProduit().getRefProduit();
            data[2] = formater.formatFloat(PUHT); 
            data[3] = tva;
            data[4] = recepDet.getCommandeDetail().getRemise();
            data[5] = qte;
            data[6] = recepDet.getQuantiteRecu();
            data[7] = formater.formatFloat(Calculator.getTotalTTC(PUHT, remise, tva, qte));
            
            int idMag = mouvStockDAO.findIN(recepDet.getId()).getIdMagasin();
            String refMagasin = magDAO.find(idMag).getRefMagasin();
            int qteStock = stockDAO.findByProduitMagasin(recepDet.getProduit().getRefProduit(),refMagasin).getQuantiteStock();
            
            data[8] = refMagasin;//+" [ Stock: "+qteStock+" ]"; 
            data[9] = qteStock;
            model1.addRow(data);
           }
           
        tableListCommande.setModel(model1);
        updateTotals();
        }
    }//GEN-LAST:event_tableOutputMouseClicked
    
    private void showBTNline(boolean show){
        btn_modify_invoice_item.setVisible(show);
        btn_delete_invoice_item.setVisible(show);
        btn_modif_bl.setVisible(show);
    }
    
    private void updateButtonVisibility() {
    showBTNline(true);
    btn_delete_livraison.showB();
    btn_valider.showB();
    btn_print.showB();
    btn_annuler_livraison.setText("Annuler");
    switch (recepSelected.getStatus()) {
        case "Brouillon":
        case "Créée":
            btn_recu.hideB();
            btn_annuler_livraison.hideB();
            btn_create_facture.showB();
            break;
        case "Validée":
        case "Validé":
            btn_recu.hideB();
            btn_valider.hideB();
            btn_print.showB();
            btn_annuler_livraison.hideB();
            btn_delete_livraison.hideB();
            showBTNline(false);
            break;
        case "Annulé":
        case "Annulée":
            btn_recu.hideB();
            btn_valider.hideB();
            btn_annuler_livraison.setText("Relancer");
            btn_annuler_livraison.showB();
            btn_delete_livraison.hideB();
            showBTNline(false);
            break;
        case "Réçue":
        case "Réçu":
            btn_recu.setText("Mettre en attente");
            break;
        case "En attente":
            btn_recu.setText("Accuser de réception");
            break;
        default:
            btn_recu.showB();
            btn_annuler_livraison.showB();
            break;
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

    totalHTField.setText(formater.formatFloat(total_HT));
    totalTVA.setText(formater.formatFloat(total_TVA));
    totalRemiseField.setText(String.valueOf(total_Remise));
    totalTTCField.setText(formater.formatFloat(total_TTC));
    totalNetAPayerField.setText(formater.formatFloat(total_TTC - total_Remise + recepSelected.getFrais()));
    
    }
    
    
    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed

        displaydata(" ");
        nomClientKeySearch.setText("");
        refCommande.setText("");
        date1.setDate(null);
        date2.setDate(null);
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search1ActionPerformed
       if(date1.getDate() != null && date2 != null){
           SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
       
        String FILTER_DATE =" r.dateReception BETWEEN '"+(String) dateFormat.format(date1.getDate())+"' AND '"+(String) dateFormat.format(date2.getDate())+"' ";
        query = (activeMenu == 1) ? " WHERE " : " AND ";
        query+=FILTER_DATE;
        displaydata(query);
    }
//        refreshTablewordreceptionDAO.findBetweenDates( (String) dateFormat.format(date1.getDate()), (String) dateFormat.format(date2.getDate())));
    }//GEN-LAST:event_btn_search1ActionPerformed

    private void btn_recuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_recuActionPerformed
        
        if ( !"Reçu".equals(recepSelected.getStatus()) ){
            if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir accusé cette commande de réception?","Confirmation d'annulation",JOptionPane.YES_NO_OPTION) == 0){
                recepSelected.setStatus("Réçu");
                if(receptionDAO.update(recepSelected)){
                    t = new Toast("Bon de réception marquée réçu", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                    t.showtoast();
                    updateButtonVisibility();
                }
            }
        }else{
            if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir mettre cette commande en attente?","Confirmation d'annulation",JOptionPane.YES_NO_OPTION) == 0){
            recepSelected.setStatus("Réçu");
            if(receptionDAO.update(recepSelected)){
                t = new Toast("Bon de réception marquée réçu", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
                updateButtonVisibility();
            }
            }
        }
    }//GEN-LAST:event_btn_recuActionPerformed

    private void btn_modif_blActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modif_blActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir valider cette commande?","Confirmation validation",JOptionPane.YES_NO_OPTION) == 0){
            javaapp.bean.Reception recep = recepSelected;
            recep.setDateReception("2024-12-04");
            
            float fraisUpdated = Float.parseFloat(fraisRecep.getValue().toString());
            recep.setFrais(fraisUpdated);
            recepSelected.setFrais(fraisUpdated);
            if(receptionDAO.update(recep)){
                updateTotals();
                t = new Toast("Création de la facture réussie!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
            }
//            JOptionPane.showMessageDialog(null, "Commande validée!","Confirmation",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_modif_blActionPerformed

    private void btn_modify_invoice_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modify_invoice_itemActionPerformed

        int row = tableListCommande.getSelectedRow();
        if (row != -1) {
            TableModel model = tableListCommande.getModel();
            JFrame frame = new JFrame("Gestion des Commandes");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 400);

            JPanel dialog = new JPanel(new GridLayout(8, 2));
            JTextField articleField = new JTextField(model.getValueAt(row, 0).toString());
            JTextField refField = new JTextField(model.getValueAt(row, 1).toString());
            JTextField puhtField = new JTextField(model.getValueAt(row, 2).toString());
            JTextField tvaField = new JTextField(model.getValueAt(row, 3).toString());
            JSpinner remiseField = new JSpinner(new SpinnerNumberModel(Float.parseFloat(model.getValueAt(row, 4).toString()), 0, 100, 1));
            JSpinner qteField = new JSpinner(new SpinnerNumberModel( Integer.parseInt(model.getValueAt(row, 6).toString()), 0, 100, 1));
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
            dialog.add(new JLabel("Fait"));
            dialog.add(qteField);

            int result = JOptionPane.showConfirmDialog(frame, dialog, "Modifier une réception", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                model.setValueAt(Float.parseFloat(remiseField.getValue().toString()), row, 4);
                model.setValueAt(Integer.parseInt(qteField.getValue().toString()), row, 5);
                model.setValueAt(formater.formatFloat( formater.parseFloat(puhtField.getText()) * Integer.parseInt(qteField.getValue().toString())), row, 7);
                updateTotals();
                ReceptionDetail recepDetSelected = recepDetDAO.findByReceptionProduit(recepSelected.getId(), refField.getText());
                recepDetSelected.setQuantiteRecu(Integer.parseInt(qteField.getValue().toString()));
                if(recepDetDAO.update(recepDetSelected)){
                    tableListCommande.clearSelection();
                    javaapp.bean.Reception recep = recepSelected;
                    recep.setMontant( formater.parseFloat(totalNetAPayerField.getText()) );
                    receptionDAO.update(recep);
                    
                    //Modifier le stock
                    MouvementStock ms = mouvStockDAO.findIN(recepDetSelected.getId());
                    ms.setQuantite( Integer.parseInt(qteField.getValue().toString()) );
                    mouvStockDAO.updateIN(ms);
                    
                    //Modifier la facture
                    javaapp.bean.FactureFournisseur fac = facDAO.findBy(recepSelected.getId());
                    fac.setMontant( formater.parseFloat(totalTTCField.getText()) );
                    if(facDAO.update(fac)){
                        new Toast("Ligne modifiée!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7).showtoast();
                    }
                }
            }
        } else{
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une ligne à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        tableListCommande.clearSelection();
    }//GEN-LAST:event_btn_modify_invoice_itemActionPerformed

    private void btn_delete_invoice_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_invoice_itemActionPerformed
        int selectedRow = tableListCommande.getSelectedRow();
        DefaultTableModel tableModel = (DefaultTableModel) tableListCommande.getModel();
        String designationProduitSelected = tableListCommande.getModel().getValueAt(selectedRow, 0).toString();
        float montant = formater.parseFloat(tableListCommande.getModel().getValueAt(selectedRow, 3).toString());

        if (selectedRow != -1) {

            if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer cette ligne, cette action est irreversible?","Confirmation de suppression",JOptionPane.YES_NO_OPTION) == 0){
                ProduitDAO produitDAO = DAOFactory.getProduitDAO();
                if( recepDetDAO.delete( receptionDAO.find(refCommandeField.getText()).getId() , produitDAO.find(designationProduitSelected).getId()) ){
                    updateTotals();
                    ((DefaultTableModel) tableListCommande.getModel()).removeRow(selectedRow);
                    javaapp.bean.FactureFournisseur fac = facDAO.findBy(recepSelected.getId());
                    if(fac != null && fac.getId() != 0){
                    fac.setMontant( Float.parseFloat(totalTTCField.getText()) );
                    facDAO.update(fac);
                    }
                    new Toast("Ligne supprimée!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7).showtoast();
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une ligne à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        tableListCommande.clearSelection();
    }//GEN-LAST:event_btn_delete_invoice_itemActionPerformed

    private void btn_annuler_livraisonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_annuler_livraisonActionPerformed
        if ( !"Annulée".equals(recepSelected.getStatus()) ){
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir annuler la commande?","Message de confirmation",JOptionPane.YES_NO_OPTION) == 0){
            recepSelected.setStatus("Annulée");
            if(receptionDAO.update(recepSelected)){
                new Toast("Bon de réception annulée", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7).showtoast();
                updateButtonVisibility();
            }
        } 
        }else {
         if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir mettre la commande 'A planifier' ?","Message de confirmation",JOptionPane.YES_NO_OPTION) == 0){
            recepSelected.setStatus("A planifier");
            if(receptionDAO.update(recepSelected)){
                new Toast("Bon de réception à planifier", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7).showtoast();
                
                updateButtonVisibility();
            }
        }    
        }

    }//GEN-LAST:event_btn_annuler_livraisonActionPerformed

    private void menu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_menu5MouseClicked

    private void btn_create_factureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_create_factureActionPerformed
        ModalFactureFournisseur m = new ModalFactureFournisseur(Integer.parseInt(idLivraison.getText()));
                m.setVisible(true);
                m.initEvent(event);
        
    }//GEN-LAST:event_btn_create_factureActionPerformed
 
public void refreshTable(List<javaapp.bean.Reception> receptions) {
    String[] column = {"Référence", "Date de réception","Fournisseur", "Commande",  "Total TTC","Status"};
    DefaultTableModel model = new DefaultTableModel(null, column);

        for(javaapp.bean.Reception recep : receptions){
            String[] data = new String[6];
            data[0] = recep.getRefReception();
            data[1] = DateConverter.convertToLetter(recep.getDateReception());
            data[2] = recep.getFournisseur().getNom()+" "+recep.getFournisseur().getPrenom();
            data[3] = commandeDAO.find(recep.getIdCommande()).getRefCommande();
            data[4] = formater.formatFloat(recep.getMontant());
            data[5] = recep.getStatus();
            model.addRow(data);
        }
        
        tableInput.setModel(model);
        nombre_ligne.setText("Receptions ("+tableInput.getRowCount()+")");
        titre_facture.setText("("+receptionDAO.getCountToValidate()+ ") livaison en attente");
        refreshPagination();
        tableOutput.getColumnModel().getColumn(5).setCellRenderer(new TableStatusCellRender());

}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_annuler_livraison;
    private javaapp.component.ButtonRadius btn_back;
    private javaapp.component.ButtonRadius btn_create_facture;
    private javaapp.component.ButtonRadius btn_delete_invoice_item;
    private javaapp.component.ButtonRadius btn_delete_livraison;
    private javaapp.component.ButtonRadius btn_modif_bl;
    private javaapp.component.ButtonRadius btn_modify_invoice_item;
    private javaapp.component.ButtonRadius btn_print;
    private javaapp.component.ButtonRadius btn_recu;
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_search;
    private javaapp.component.ButtonRadius btn_search1;
    private javaapp.component.ButtonRadius btn_valider;
    private com.toedter.calendar.JDateChooser date1;
    private com.toedter.calendar.JDateChooser date2;
    private com.toedter.calendar.JDateChooser dateCommande;
    private com.toedter.calendar.JDateChooser dateLivraison;
    private javax.swing.JTextPane descriptionCommande;
    private javax.swing.JTextField facturation;
    private javax.swing.JLabel frais;
    private javax.swing.JSpinner fraisRecep;
    private javax.swing.JLabel heureCommande;
    private javax.swing.JComboBox<String> heureLivraison;
    private javax.swing.JLabel idLivraison;
    private javax.swing.JLabel idReception;
    private javax.swing.JLabel id_client;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
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
    private javax.swing.JComboBox<String> minuteLivraison;
    private javax.swing.JTextField nomClient;
    private javax.swing.JTextField nomClientKeySearch;
    private javax.swing.JLabel nombre_ligne;
    private javax.swing.JPanel page1;
    private javax.swing.JPanel page2;
    private javaapp.pagination.Pagination pagination1;
    private javaapp.component.PanelBorderRound panelBorderRound1;
    private javax.swing.JTextField refCommande;
    private javax.swing.JTextField refCommandeField;
    private javax.swing.JTextField refLivraison;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JScrollPane scrollTable1;
    private javaapp.component.Table tableInput;
    private javaapp.component.Table tableListCommande;
    private javaapp.component.Table tableOutput;
    private javax.swing.JLabel titre_facture;
    private javax.swing.JLabel totalHTField;
    private javax.swing.JLabel totalNetAPayerField;
    private javax.swing.JLabel totalRemiseField;
    private javax.swing.JLabel totalTTCField;
    private javax.swing.JLabel totalTVA;
    private javax.swing.JTextField vendeur;
    // End of variables declaration//GEN-END:variables

    /**
     * @param activeMenu the activeMenu to set
     */
    public void setActiveMenu(int activeMenu) {
        this.activeMenu = activeMenu;
    }
}
