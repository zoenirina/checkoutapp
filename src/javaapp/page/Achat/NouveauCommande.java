
package javaapp.page.Achat;

import com.toedter.calendar.JDateChooser;
import javaapp.page.parametre.Groupe;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.List;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.CommandeDetailFournisseur;
import javaapp.bean.Personne;
import javaapp.bean.Produit;
import javaapp.component.ButtonRadius;
import javaapp.component.DateConverter;
import javaapp.component.FormatUtils;
import javaapp.component.Toast;
import javaapp.dao.CommandeDetailFournisseurDAO;
import javaapp.dao.CommandeFournisseurDAO;
import javaapp.dao.MagasinDAO;
import javaapp.dao.PersonneDAO;
import javaapp.dao.StockDAO;
import javaapp.factory.DAOFactory;
import javax.swing.JComboBox;
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
public class NouveauCommande extends javax.swing.JPanel {
    private final int borderRadius = 10;
    Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
    public String query=null;
    public String title="Autres";
    public PreparedStatement stm ;
    public ResultSet resultSet;
    public String idSelected;
    private String btn_save_state= "insert";
    public String datetime1;
    public LocalTime timepart;
    public JPanel pan;
    public Toast t;
    private FormatUtils formater;
    private final String SELECT_ALL = "SELECT p.id AS idProduit,p.designation,p.PU AS prix,p.refProduit , u.poids, u.capacite," +
            "(SELECT COALESCE(SUM(CASE WHEN m.sens = 'Entrée' THEN m.quantite ELSE 0 END), 0) - COALESCE(SUM(CASE WHEN m.sens = 'Sortie' THEN m.quantite ELSE 0 END), 0)" +
            "FROM mouvementStock m WHERE m.idProduit = p.id) AS quantite_stock FROM produits p LEFT JOIN uniteStockage u ON p.idUnite = u.id";
    final static String SELECT_MAGASIN_AND_QTE_STOCK = "SELECT m.id AS idMagasin, m.refMagasin, m.labelle AS nomMagasin,  (SELECT COALESCE(SUM(CASE WHEN ms.sens = 'Entrée' THEN ms.quantite ELSE 0 END), 0) -  COALESCE(SUM(CASE WHEN ms.sens = 'Sortie' THEN ms.quantite ELSE 0 END), 0)  FROM mouvementStock ms WHERE ms.idProduit in (SELECT id FROM produits WHERE refProduit=?)  AND ms.idMagasin = m.id) AS quantiteStock FROM magasin m";
    final static String SQL_VERIFIER_QTE_STOCK = "SELECT m.id AS idMagasin, m.refMagasin, m.labelle AS nomMagasin, (SELECT COALESCE(SUM(CASE WHEN ms.sens = 'Entrée' THEN ms.quantite ELSE 0 END), 0) -  COALESCE(SUM(CASE WHEN ms.sens = 'Sortie' THEN ms.quantite ELSE 0 END), 0)  FROM mouvementStock ms WHERE ms.idProduit = ?  AND ms.idMagasin = m.id) AS quantiteStock FROM magasin m";
    
    private Integer[] idComDetArray;
    String insertLivraisonDetailsQuery = "INSERT INTO livraisonDetails (idCommandeDetail, idProduit, quantiteRecu, quantiteValide, montant, devise, idLivraison, description, dateLivraison) "
            + "VALUES (?, (SELECT id FROM produits WHERE refProduit=?), ?, ?, ?, (SELECT devise FROM config WHERE idConfig=1), ?, ?, ?)";
    private List<String> magasinList = new ArrayList<>();
    private Map<String, String> magasinMap = new HashMap<>();
    private JComboBox<String> magasin_qte_stock = new JComboBox<>();//quantiteStock
    private List<Integer> quantiteStockList = new ArrayList<>();
    private MagasinDAO magDAO;
    private StockDAO stockDAO;
    private PersonneDAO fournisseurDAO;
    
    public NouveauCommande(JPanel panel) {
        initComponents();
        //initialiser les dao
        magDAO = DAOFactory.getMagasinDAO();
        stockDAO = DAOFactory.getStockDAO();
        fournisseurDAO = DAOFactory.getFournisseurDAO();
        formater = new FormatUtils();
        
        pan= panel;
        query=SELECT_ALL; 
        scrollTable.getViewport().setBackground(Color.decode("#F0ECF3"));
        scrollTable1.getViewport().setBackground(Color.decode("#F0ECF3"));
        scrollTableClient.getViewport().setBackground(Color.white);
        btn_save_state= "insert";
        setInputSelect();
        initialiserCommande();
    }
        public NouveauCommande() {
        initComponents();
        formater = new FormatUtils();
        magDAO = DAOFactory.getMagasinDAO();
        stockDAO = DAOFactory.getStockDAO();
        fournisseurDAO = DAOFactory.getFournisseurDAO();
        
        query=SELECT_ALL; 
        scrollTable.getViewport().setBackground(Color.decode("#F0ECF3"));
        scrollTable1.getViewport().setBackground(Color.decode("#F0ECF3"));
        scrollTableClient.getViewport().setBackground(Color.white);
        btn_save_state= "insert";
        setInputSelectMagasin();
        setInputSelect();
        initialiserCommande();
    }

        private void initialiserCommande(){
        refreshTableArticle(stockDAO.select());
        refreshTableFournisseur( DAOFactory.getFournisseurDAO().select() );
        switchToPage(1);
        setEmptyCard();
        enregistrer_commande.setVisible(true);
        
    }
    private void setEmptyCard(){
    for(int i = listePanier.getRowCount()-1 ; i >= 0; i--){
        ((DefaultTableModel) listePanier.getModel()).removeRow(i);
        }
    }
    private void setEmptyFieldCardItem(){
    designation_item.setText(null);
    price_item.setText(null);
    quantite_item.setValue(0);
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
    
    public void connectiondb(){
        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Groupe.class.getName()).log(Level.SEVERE, null, ex);
            }
            conn = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            Logger.getLogger(Groupe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        page1 = new javax.swing.JLayeredPane();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btn_start = new javaapp.component.ButtonRadius();
        page2 = new javax.swing.JLayeredPane();
        jLabel22 = new javax.swing.JLabel();
        client_key_search = new javax.swing.JTextField();
        btn_search1 = new javaapp.component.ButtonRadius();
        btn_refresh1 = new javaapp.component.ButtonRadius();
        scrollTableClient = new javax.swing.JScrollPane();
        listeFournisseur = new javaapp.component.Table();
        btn_next1 = new javaapp.component.ButtonRadius();
        jSeparator7 = new javax.swing.JSeparator();
        page3 = new javax.swing.JPanel();
        scrollTable1 = new javax.swing.JScrollPane();
        listePanier = new javaapp.component.Table();
        scrollTable = new javax.swing.JScrollPane();
        listeArticle = new javaapp.component.Table();
        jLabel21 = new javax.swing.JLabel();
        produitKeySearch = new javax.swing.JTextField();
        btn_search = new javaapp.component.ButtonRadius();
        btn_refresh = new javaapp.component.ButtonRadius();
        panel_modify_item = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        quantite_item = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btn_modify_invoice_item = new javaapp.component.ButtonRadius();
        btn_delete_invoice_item = new javaapp.component.ButtonRadius();
        designation_item = new javax.swing.JTextField();
        price_item = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        remise_item = new javax.swing.JSpinner();
        btn_cancel = new javaapp.component.ButtonRadius();
        btn_next = new javaapp.component.ButtonRadius();
        btn_previous = new javaapp.component.ButtonRadius();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        total_prix = new javax.swing.JLabel();
        refMagasinSearch = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        stepPanel = new javax.swing.JPanel();
        step1 = new javaapp.component.ButtonRadius();
        step2 = new javaapp.component.ButtonRadius();
        step3 = new javaapp.component.ButtonRadius();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        page4 = new javax.swing.JLayeredPane();
        jPanel6 = new javax.swing.JPanel();
        nombre_article = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listeCommande1 = new javaapp.component.Table();
        enregistrer_commande = new javaapp.component.ButtonRadius();
        btn_previous3 = new javaapp.component.ButtonRadius();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel9 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        lbl_id_client = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        lbl_nom_complet = new javax.swing.JLabel();
        lbl_adresse_client = new javax.swing.JLabel();
        lbl_email_client = new javax.swing.JLabel();
        lbl_tel_client = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        description = new javax.swing.JTextArea();
        minuteCommande = new javax.swing.JComboBox<>();
        heureCommande = new javax.swing.JComboBox<>();
        dateCommandeInput = new com.toedter.calendar.JDateChooser();
        jLabel58 = new javax.swing.JLabel();
        refCommande = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/caisselogin.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Enregistrer une nouvelle commande");

        btn_start.setBackground(new java.awt.Color(248, 91, 50));
        btn_start.setForeground(new java.awt.Color(255, 255, 255));
        btn_start.setText("Commencer");
        btn_start.setBorderColor(new java.awt.Color(248, 91, 50));
        btn_start.setColor(new java.awt.Color(248, 91, 50));
        btn_start.setColorClick(new java.awt.Color(248, 91, 50));
        btn_start.setColorOver(new java.awt.Color(248, 91, 50));
        btn_start.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btn_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_startActionPerformed(evt);
            }
        });

        page1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page1.setLayer(btn_start, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout page1Layout = new javax.swing.GroupLayout(page1);
        page1.setLayout(page1Layout);
        page1Layout.setHorizontalGroup(
            page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page1Layout.createSequentialGroup()
                .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page1Layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(page1Layout.createSequentialGroup()
                        .addGap(384, 384, 384)
                        .addGroup(page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_start, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        page1Layout.setVerticalGroup(
            page1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_start, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
        );

        jLabel22.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("Nom ou prénom");

        btn_search1.setBorder(null);
        btn_search1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/search.png"))); // NOI18N
        btn_search1.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_search1.setColor(new java.awt.Color(240, 236, 243));
        btn_search1.setColorClick(new java.awt.Color(255, 255, 255));
        btn_search1.setColorOver(new java.awt.Color(240, 236, 243));

        btn_refresh1.setBackground(new java.awt.Color(240, 236, 243));
        btn_refresh1.setBorder(null);
        btn_refresh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/refresh.png"))); // NOI18N
        btn_refresh1.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_refresh1.setColor(new java.awt.Color(240, 236, 243));
        btn_refresh1.setColorClick(new java.awt.Color(255, 255, 255));
        btn_refresh1.setColorOver(new java.awt.Color(240, 236, 243));
        btn_refresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refresh1ActionPerformed(evt);
            }
        });

        scrollTableClient.setBorder(null);

        listeFournisseur.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        listeFournisseur.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        listeFournisseur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listeFournisseurMouseClicked(evt);
            }
        });
        scrollTableClient.setViewportView(listeFournisseur);

        btn_next1.setBackground(new java.awt.Color(248, 91, 50));
        btn_next1.setForeground(new java.awt.Color(255, 255, 255));
        btn_next1.setText("Suivant");
        btn_next1.setBorderColor(new java.awt.Color(248, 91, 50));
        btn_next1.setColor(new java.awt.Color(248, 91, 50));
        btn_next1.setColorClick(new java.awt.Color(248, 91, 50));
        btn_next1.setColorOver(new java.awt.Color(248, 91, 50));
        btn_next1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_next1ActionPerformed(evt);
            }
        });

        page2.setLayer(jLabel22, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(client_key_search, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(btn_search1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(btn_refresh1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(scrollTableClient, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(btn_next1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jSeparator7, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout page2Layout = new javax.swing.GroupLayout(page2);
        page2.setLayout(page2Layout);
        page2Layout.setHorizontalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(page2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(client_key_search, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addComponent(btn_search1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_refresh1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_next1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrollTableClient, javax.swing.GroupLayout.PREFERRED_SIZE, 906, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        page2Layout.setVerticalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_refresh1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_search1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(client_key_search, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(scrollTableClient, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_next1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        page3.setBackground(new java.awt.Color(255, 255, 255));

        scrollTable1.setBorder(null);

        listePanier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Réf", "Article", "Pu HT", "TVA", "Remise", "Qte", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listePanier.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        listePanier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listePanierMouseClicked(evt);
            }
        });
        scrollTable1.setViewportView(listePanier);

        scrollTable.setBorder(null);

        listeArticle.setModel(new javax.swing.table.DefaultTableModel(
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
        listeArticle.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        listeArticle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listeArticleMouseClicked(evt);
            }
        });
        scrollTable.setViewportView(listeArticle);

        jLabel21.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("Réference");

        produitKeySearch.setPreferredSize(new java.awt.Dimension(130, 22));

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

        panel_modify_item.setBackground(new java.awt.Color(102, 102, 255));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Réference");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Prix");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Quantité");

        btn_modify_invoice_item.setBackground(new java.awt.Color(102, 102, 255));
        btn_modify_invoice_item.setBorder(null);
        btn_modify_invoice_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/iconModify.png"))); // NOI18N
        btn_modify_invoice_item.setBorderColor(new java.awt.Color(238, 245, 255));
        btn_modify_invoice_item.setColor(new java.awt.Color(102, 102, 255));
        btn_modify_invoice_item.setColorClick(new java.awt.Color(255, 255, 255));
        btn_modify_invoice_item.setColorOver(new java.awt.Color(87, 87, 255));
        btn_modify_invoice_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modify_invoice_itemActionPerformed(evt);
            }
        });

        btn_delete_invoice_item.setBackground(new java.awt.Color(240, 240, 255));
        btn_delete_invoice_item.setBorder(null);
        btn_delete_invoice_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/btnDelete.png"))); // NOI18N
        btn_delete_invoice_item.setBorderColor(new java.awt.Color(240, 240, 255));
        btn_delete_invoice_item.setColor(new java.awt.Color(240, 240, 255));
        btn_delete_invoice_item.setColorClick(new java.awt.Color(240, 240, 255));
        btn_delete_invoice_item.setColorOver(new java.awt.Color(240, 240, 255));
        btn_delete_invoice_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_invoice_itemActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Remise (%)");

        remise_item.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout panel_modify_itemLayout = new javax.swing.GroupLayout(panel_modify_item);
        panel_modify_item.setLayout(panel_modify_itemLayout);
        panel_modify_itemLayout.setHorizontalGroup(
            panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modify_itemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(designation_item, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(price_item, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_modify_itemLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(31, 31, 31))
                    .addGroup(panel_modify_itemLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(quantite_item)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(panel_modify_itemLayout.createSequentialGroup()
                        .addComponent(remise_item, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_modify_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addComponent(btn_delete_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        panel_modify_itemLayout.setVerticalGroup(
            panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_modify_itemLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_delete_invoice_item, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_modify_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel_modify_itemLayout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(designation_item, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(quantite_item, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(panel_modify_itemLayout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(price_item, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_modify_itemLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(remise_item, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_modify_itemLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(40, 40, 40))))
                    .addGroup(panel_modify_itemLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(61, 61, 61))))
        );

        btn_cancel.setBackground(new java.awt.Color(255, 222, 177));
        btn_cancel.setBorder(null);
        btn_cancel.setForeground(new java.awt.Color(248, 91, 50));
        btn_cancel.setText("Tout effacer");
        btn_cancel.setBorderColor(new java.awt.Color(255, 222, 177));
        btn_cancel.setColor(new java.awt.Color(255, 222, 177));
        btn_cancel.setColorClick(new java.awt.Color(255, 222, 177));
        btn_cancel.setColorOver(new java.awt.Color(255, 222, 177));
        btn_cancel.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        btn_next.setBackground(new java.awt.Color(248, 91, 50));
        btn_next.setBorder(null);
        btn_next.setForeground(new java.awt.Color(255, 255, 255));
        btn_next.setText("Suivant");
        btn_next.setBorderColor(new java.awt.Color(248, 91, 50));
        btn_next.setColor(new java.awt.Color(248, 91, 50));
        btn_next.setColorClick(new java.awt.Color(248, 91, 50));
        btn_next.setColorOver(new java.awt.Color(248, 91, 50));
        btn_next.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btn_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nextActionPerformed(evt);
            }
        });

        btn_previous.setBackground(new java.awt.Color(240, 236, 243));
        btn_previous.setBorder(null);
        btn_previous.setForeground(new java.awt.Color(51, 51, 51));
        btn_previous.setText("Précedent");
        btn_previous.setBorderColor(new java.awt.Color(240, 236, 243));
        btn_previous.setColor(new java.awt.Color(240, 236, 243));
        btn_previous.setColorClick(new java.awt.Color(240, 236, 243));
        btn_previous.setColorOver(new java.awt.Color(240, 236, 243));
        btn_previous.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btn_previous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_previousActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Total:");

        total_prix.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        total_prix.setForeground(new java.awt.Color(0, 153, 0));

        refMagasinSearch.setPreferredSize(new java.awt.Dimension(135, 22));
        refMagasinSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refMagasinSearchActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 51, 51));
        jLabel24.setText("Magasin");

        javax.swing.GroupLayout page3Layout = new javax.swing.GroupLayout(page3);
        page3.setLayout(page3Layout);
        page3Layout.setHorizontalGroup(
            page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(page3Layout.createSequentialGroup()
                        .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(refMagasinSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(produitKeySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_previous, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrollTable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(scrollTable1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, page3Layout.createSequentialGroup()
                        .addGap(396, 396, 396)
                        .addComponent(btn_next, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, page3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total_prix, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panel_modify_item, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
            .addComponent(jSeparator1)
        );
        page3Layout.setVerticalGroup(
            page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page3Layout.createSequentialGroup()
                        .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page3Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page3Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(10, 10, 10)))
                        .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(produitKeySearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(refMagasinSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(panel_modify_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page3Layout.createSequentialGroup()
                        .addComponent(scrollTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(page3Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(total_prix, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_next, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_previous, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        stepPanel.setBackground(new java.awt.Color(255, 255, 255));
        stepPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        step1.setText("1");
        step1.setBorderColor(new java.awt.Color(248, 91, 50));
        step1.setColor(new java.awt.Color(248, 91, 50));
        step1.setColorClick(new java.awt.Color(240, 236, 243));
        step1.setColorOver(new java.awt.Color(248, 91, 50));
        step1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        step1.setRadius(50);
        stepPanel.add(step1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 50, 50));

        step2.setBackground(new java.awt.Color(240, 236, 243));
        step2.setText("2");
        step2.setBorderColor(new java.awt.Color(240, 236, 243));
        step2.setColor(new java.awt.Color(240, 236, 243));
        step2.setColorClick(new java.awt.Color(240, 236, 243));
        step2.setColorOver(new java.awt.Color(240, 236, 243));
        step2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        step2.setRadius(50);
        step2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                step2ActionPerformed(evt);
            }
        });
        stepPanel.add(step2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 50, 50));

        step3.setBackground(new java.awt.Color(240, 236, 243));
        step3.setText("3");
        step3.setBorderColor(new java.awt.Color(240, 240, 240));
        step3.setColor(new java.awt.Color(240, 236, 243));
        step3.setColorClick(new java.awt.Color(240, 236, 243));
        step3.setColorOver(new java.awt.Color(240, 236, 243));
        step3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        step3.setRadius(50);
        step3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                step3ActionPerformed(evt);
            }
        });
        stepPanel.add(step3, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, 50, 50));

        jPanel2.setBackground(new java.awt.Color(240, 236, 243));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        stepPanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 450, 4));

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Choisir un client");
        stepPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, -1, -1));

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Confirmation");
        stepPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 80, -1, -1));

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Remplir un panier");
        stepPanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 80, -1, -1));

        jPanel6.setBackground(new java.awt.Color(203, 216, 255));

        nombre_article.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nombre_article.setForeground(new java.awt.Color(0, 0, 102));
        nombre_article.setText("Articles");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(nombre_article, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(nombre_article)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        listeCommande1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        listeCommande1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        listeCommande1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listeCommande1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listeCommande1);

        enregistrer_commande.setBackground(new java.awt.Color(248, 91, 50));
        enregistrer_commande.setForeground(new java.awt.Color(255, 255, 255));
        enregistrer_commande.setText("Passer la commande");
        enregistrer_commande.setBorderColor(new java.awt.Color(248, 91, 50));
        enregistrer_commande.setColor(new java.awt.Color(248, 91, 50));
        enregistrer_commande.setColorClick(new java.awt.Color(248, 91, 50));
        enregistrer_commande.setColorOver(new java.awt.Color(248, 91, 50));
        enregistrer_commande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        enregistrer_commande.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enregistrer_commandeActionPerformed(evt);
            }
        });

        btn_previous3.setBackground(new java.awt.Color(240, 236, 243));
        btn_previous3.setBorder(null);
        btn_previous3.setForeground(new java.awt.Color(51, 51, 51));
        btn_previous3.setText("Précédent");
        btn_previous3.setBorderColor(new java.awt.Color(240, 236, 243));
        btn_previous3.setColor(new java.awt.Color(240, 236, 243));
        btn_previous3.setColorClick(new java.awt.Color(240, 236, 243));
        btn_previous3.setColorOver(new java.awt.Color(240, 236, 243));
        btn_previous3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btn_previous3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_previous3ActionPerformed(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel49.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(51, 51, 51));
        jLabel49.setText("Client N°");

        lbl_id_client.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lbl_id_client.setForeground(new java.awt.Color(51, 51, 51));
        lbl_id_client.setText("001");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel49)
                .addGap(6, 6, 6)
                .addComponent(lbl_id_client, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(0, 13, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(lbl_id_client, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel9.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 110, -1));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel50.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(102, 102, 102));
        jLabel50.setText("Adresse");

        jLabel51.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(102, 102, 102));
        jLabel51.setText("Contact");

        jLabel52.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(102, 102, 102));
        jLabel52.setText("Nom");

        lbl_nom_complet.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lbl_nom_complet.setForeground(new java.awt.Color(51, 51, 51));

        lbl_adresse_client.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lbl_adresse_client.setForeground(new java.awt.Color(51, 51, 51));

        lbl_email_client.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lbl_email_client.setForeground(new java.awt.Color(51, 51, 51));

        lbl_tel_client.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lbl_tel_client.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_nom_complet, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(119, 119, 119)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_adresse_client, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(111, 111, 111)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_tel_client, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_email_client, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(jLabel50)
                    .addComponent(jLabel51))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_email_client, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_adresse_client, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_tel_client, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_nom_complet, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 950, 110));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        jLabel60.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(51, 51, 51));
        jLabel60.setText("Commande");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel60)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel62.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(102, 102, 102));
        jLabel62.setText("Description");

        jLabel56.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(102, 102, 102));
        jLabel56.setText("Date de la commande");

        description.setColumns(20);
        description.setRows(5);
        jScrollPane2.setViewportView(description);

        minuteCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        minuteCommande.setForeground(new java.awt.Color(43, 40, 54));

        heureCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        heureCommande.setForeground(new java.awt.Color(43, 40, 54));

        dateCommandeInput.setBackground(new java.awt.Color(241, 248, 240));
        dateCommandeInput.setForeground(new java.awt.Color(43, 40, 54));
        dateCommandeInput.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        jLabel58.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(102, 102, 102));
        jLabel58.setText("Référence");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                            .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(85, 85, 85)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(dateCommandeInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(heureCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(minuteCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(refCommande))))
                .addGap(38, 38, 38))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateCommandeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel56)
                        .addGap(11, 11, 11))
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(heureCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(minuteCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel9.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, -1, 220));

        page4.setLayer(jPanel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page4.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page4.setLayer(enregistrer_commande, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page4.setLayer(btn_previous3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page4.setLayer(jSeparator6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page4.setLayer(jPanel9, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout page4Layout = new javax.swing.GroupLayout(page4);
        page4.setLayout(page4Layout);
        page4Layout.setHorizontalGroup(
            page4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator6)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(page4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(page4Layout.createSequentialGroup()
                        .addComponent(btn_previous3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(enregistrer_commande, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 955, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );
        page4Layout.setVerticalGroup(
            page4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(page4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enregistrer_commande, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_previous3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(page1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(page2)
            .addComponent(stepPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(page4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(page3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(stepPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(page1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(page2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(page3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(page4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void listePanierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listePanierMouseClicked
        int selectedRow = listePanier.getSelectedRow();
        if (selectedRow != -1) {
            designation_item.setText(listePanier.getValueAt(selectedRow, 1).toString());  // Désignation
            price_item.setText(listePanier.getValueAt(selectedRow, 2).toString());  // Prix
            quantite_item.setValue(Integer.parseInt(listePanier.getValueAt(selectedRow, 5).toString()));  // Quantité
            remise_item.setValue(Float.parseFloat(listePanier.getValueAt(selectedRow, 4).toString()));
        }
    }//GEN-LAST:event_listePanierMouseClicked

    private void switchToPage(int page){
    switch(page){
        case 1:
            page1.setVisible(true);
            page2.setVisible(false);
            page3.setVisible(false);
            page4.setVisible(false);
            
            stepPanel.setVisible(false);
        break;
        case 2:
            stepPanel.setVisible(true);
            page1.setVisible(false);
            page2.setVisible(true);
            page3.setVisible(false);
            page4.setVisible(false);
            
            activeButton(step1);
            desactiveButton(step2);
            desactiveButton(step3);
        break;
        case 3:
            page1.setVisible(false);
            page2.setVisible(false);
            page3.setVisible(true);
            page4.setVisible(false);
            
            
            desactiveButton(step1);
            activeButton(step2);
            desactiveButton(step3);
        break;
        case 4:
            page1.setVisible(false);
            page2.setVisible(false);
            page3.setVisible(false);
            page4.setVisible(true);


            desactiveButton(step1);
            desactiveButton(step2);
            activeButton(step3);
        break;   
        default:  
        break;
    }
    }
    
    private void activeButton(ButtonRadius btn){
        btn.setBackground(Color.decode("#F85B32"));
        btn.setBorderColor(Color.decode("#F85B32"));
        btn.setColorOver(Color.decode("#F85B32"));
        btn.setColor((Color.decode("#F85B32")));
    }
    
    private void desactiveButton(ButtonRadius btn){
        btn.setBackground(Color.decode("#F0ECF3"));
        btn.setBorderColor(Color.decode("#F0ECF3"));
        btn.setColorOver(Color.decode("#F0ECF3"));
        btn.setColor((Color.decode("#F0ECF3")));
        
    }
    
    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed

        if( refMagasinSearch.getSelectedItem() != "Tous" && !produitKeySearch.getText().isEmpty()){
            refreshTableArticle(stockDAO.filterByProduitMagasin(produitKeySearch.getText(), refMagasinSearch.getSelectedItem().toString()));
        }else if( refMagasinSearch.getSelectedItem() == "Tous" && !produitKeySearch.getText().isEmpty()){
            refreshTableArticle(stockDAO.filterByProduit(produitKeySearch.getText()));
        }else if(refMagasinSearch.getSelectedItem() != "Tous" && produitKeySearch.getText().isEmpty()){
            refreshTableArticle(stockDAO.filterByMagasin(refMagasinSearch.getSelectedItem().toString()));
        }else{
            refreshTableArticle(stockDAO.select());
        }
//        refreshTableArticle();

    }//GEN-LAST:event_btn_searchActionPerformed

     private void setInputSelect(){

                List<javaapp.bean.Magasin> magasins = magDAO.select();
                refMagasinSearch.addItem("Tous");
                for(javaapp.bean.Magasin mag : magasins) {
                    refMagasinSearch.addItem(mag.getRefMagasin());
                }
    }
    
    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        query=SELECT_ALL;
        refreshTableArticle(stockDAO.select());
        produitKeySearch.setText("");
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_modify_invoice_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modify_invoice_itemActionPerformed
        int selectedRow = listePanier.getSelectedRow();
        String refProduitSelected = listePanier.getModel().getValueAt(selectedRow, 0).toString();
        String tvaSelected = listePanier.getModel().getValueAt(selectedRow, 3).toString();
        
        if (selectedRow != -1) {
            listePanier.setValueAt((int) quantite_item.getValue(), selectedRow, 5);  // Mise à jour de la quantité
            listePanier.setValueAt( Float.parseFloat(remise_item.getValue().toString()), selectedRow, 4);
            float pu = formater.parseFloat(price_item.getText());
            float tva =  Float.parseFloat(tvaSelected);
            float remise = Float.parseFloat(remise_item.getValue().toString());
            float puAvecRemise = pu - (pu*remise)/100;
            int qte = Integer.parseInt(quantite_item.getValue().toString());            
            float ttc =( puAvecRemise + (puAvecRemise*tva)/100 ) * qte;
            listePanier.setValueAt(ttc, selectedRow, 6);
            listePanier.clearSelection();
        }
        total_prix.setText(String.valueOf(calculerMontantTotal()) +" Ar");
        setEmptyFieldCardItem();
    }//GEN-LAST:event_btn_modify_invoice_itemActionPerformed

    private void btn_delete_invoice_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_invoice_itemActionPerformed
        int selectedRow = listePanier.getSelectedRow();
    if (selectedRow != -1) {
        ((DefaultTableModel) listePanier.getModel()).removeRow(selectedRow);
    }
    total_prix.setText(String.valueOf(calculerMontantTotal()) +" Ar");
    setEmptyFieldCardItem();
    }//GEN-LAST:event_btn_delete_invoice_itemActionPerformed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
                setEmptyCard();
    }//GEN-LAST:event_btn_cancelActionPerformed

    private void btn_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextActionPerformed
        if(listePanier.getRowCount() > 0){
            switchToPage(4);
            updateTableModel();
            try {
                DateConverter.initializeDate(dateCommandeInput, heureCommande, minuteCommande, null);
            } catch (ParseException ex) {
                Logger.getLogger(NouveauCommande.class.getName()).log(Level.SEVERE, null, ex);
            }
       }else{
        JOptionPane.showMessageDialog(null, "Oups, il semblerait que votre panier est vide!","Erreur",JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btn_nextActionPerformed

    // Créer une fonction pour mettre à jour le modèle de tableau en fonction de la sélection du bouton radio
    private void updateTableModel() {
        DefaultTableModel model1 = new DefaultTableModel();
        model1.setColumnIdentifiers(new Object[]{"Reference", "Article","Prix HT", "TVA","Remise",  "Qté commandé", "Total"});
        for (int i = 0; i < listePanier.getRowCount(); i++) {
            Object[] row = new Object[listePanier.getColumnCount() + 1]; // Ajouter 1 pour la colonne du bouton
            for (int j = 0; j < listePanier.getColumnCount(); j++) {
                row[j] = listePanier.getValueAt(i, j);
            }
            // Ajouter la ligne dans le modèle de tableau listeCommande1
            model1.addRow(row);
        }
        listeCommande1.setModel(model1);
    }

    private float calculerMontantTotal() {
    
        float montantTotal = 0.0f;
        DefaultTableModel model = (DefaultTableModel) listePanier.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
        float montantLigne = formater.parseFloat(model.getValueAt(i, 6).toString());
            montantTotal += montantLigne;
        }
        return montantTotal;
    }

    private void listeArticleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listeArticleMouseClicked
      int selectedRow = listeArticle.getSelectedRow();
        if (selectedRow != -1) {
            String refProduit = listeArticle.getModel().getValueAt(selectedRow, 0).toString();
    //        String refProduit = listeArticle.getModel().getValueAt(selectedRow, 1).toString();
            String designation = listeArticle.getModel().getValueAt(selectedRow, 1).toString();
            String prix = listeArticle.getModel().getValueAt(selectedRow, 2).toString();
            String stock = listeArticle.getModel().getValueAt(selectedRow, 3).toString();
            Produit produit = DAOFactory.getProduitDAO().find(refProduit);
            JPanel panel = new JPanel(new GridLayout(0, 2));

            panel.add(new JLabel("Designation:"));
            JTextField nameField = new JTextField(designation);
            nameField.setEditable(false);
            panel.add(nameField);

            panel.add(new JLabel("Prix HT:"));
            JTextField priceField = new JTextField(formater.formatFloat(produit.getPUHT()));
            priceField.setEditable(false);
            panel.add(priceField);

            panel.add(new JLabel("TVA (%):"));
            JSpinner tvaSpinner = new JSpinner(new SpinnerNumberModel(produit.getTVA(), 1, 100, 1));
            panel.add(tvaSpinner);
            
            panel.add(new JLabel("Prix TTC:"));
            JTextField priceTTCField = new JTextField(prix);
            priceTTCField.setEditable(false);
            panel.add(priceTTCField);
            
            panel.add(new JLabel("Remise:"));
            JSpinner remiseSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            panel.add(remiseSpinner);
            
            panel.add(new JLabel("Quantité:"));
            JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
            panel.add(quantitySpinner);


            int result = JOptionPane.showConfirmDialog(null, panel, "Entrer la quantité désirée",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (result == JOptionPane.OK_OPTION ) {
                
//                if ((Integer.parseInt(quantitySpinner.getValue().toString()) <= Integer.parseInt(stock))) {
                    
                DefaultTableModel model = (DefaultTableModel) listePanier.getModel();
                boolean articleExiste = false;
                float pu = formater.parseFloat(priceField.getText());
                float tva =  Float.parseFloat(tvaSpinner.getValue().toString());
                float remise = Float.parseFloat(remiseSpinner.getValue().toString());
                float puAvecRemise = pu - (pu*remise)/100;
                float ttc = ( puAvecRemise + (puAvecRemise*tva)/100)*Integer.parseInt(quantitySpinner.getValue().toString());
                    
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).equals(refProduit)) {
                        int ancienneQuantite = (int) model.getValueAt(i, 5);
                        int nouvelleQuantite = ancienneQuantite + (int) quantitySpinner.getValue();
                        model.setValueAt(nouvelleQuantite, i, 5); // Mise à jour de la quantité
                        model.setValueAt(Float.parseFloat(remiseSpinner.getValue().toString()), i, 4);
                        ttc =( puAvecRemise + (puAvecRemise*tva)/100)* nouvelleQuantite;
                        model.setValueAt(ttc, i, 6);
                        articleExiste = true;
                        break;
                    }
                }
                if (!articleExiste) {
                    model.addRow(new Object[]{refProduit, designation, priceField.getText(), Float.parseFloat(tvaSpinner.getValue().toString()),  Float.parseFloat(remiseSpinner.getValue().toString()) , quantitySpinner.getValue(), ttc});
                }
                total_prix.setText( formater.formatFloat(calculerMontantTotal()) +" Ar");
//                }else{
//                    JOptionPane.showMessageDialog(null,"Stock insuffisant pour cette opération.", "Erreur", JOptionPane.ERROR_MESSAGE);
//                }
        
            }
    }
    }//GEN-LAST:event_listeArticleMouseClicked

    
    
     private void setInputSelectMagasin() {
        try { 
            int selectedRow = listeArticle.getSelectedRow();
            String refProduitSelected = listeArticle.getModel().getValueAt(selectedRow, 0).toString();

            connectiondb();
            stm = conn.prepareStatement(SELECT_MAGASIN_AND_QTE_STOCK);
    //        stm.setInt(1, Integer.parseInt(idProduitSelected) );
    //refProduitSelected
            stm.setString(1, refProduitSelected);
            resultSet = stm.executeQuery();

            magasin_qte_stock.removeAllItems();
            magasinMap.clear();
            magasinList.clear();
            quantiteStockList.clear();

            while (resultSet.next()) {
                int quantiteStock = resultSet.getInt("quantiteStock");
                String refMagasin = resultSet.getString("refMagasin");
                magasinList.add(refMagasin);

                quantiteStockList.add(quantiteStock);
                // Ajoutez le nom du magasin avec sa quantité de stock dans le JComboBox
                magasin_qte_stock.addItem(refMagasin + "................................................................" + quantiteStock);
            }
            magasin_qte_stock.setSelectedIndex(0);

            resultSet.close();
            stm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions de manière appropriée dans votre application
        }
}
    
    
    private void btn_next1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_next1ActionPerformed
        if(listeFournisseur.getSelectedRow() != -1){
            switchToPage(3);
        }else{
            JOptionPane.showMessageDialog(null,"Veuillez selectionner un fournisseur", "Erreur", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_btn_next1ActionPerformed

    private void btn_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_startActionPerformed
         switchToPage(2);
    }//GEN-LAST:event_btn_startActionPerformed

    private void btn_previousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_previousActionPerformed
       switchToPage(2);
       for(int i = listePanier.getRowCount()-1 ; i >= 0; i--){
        ((DefaultTableModel) listePanier.getModel()).removeRow(i);
        }
    }//GEN-LAST:event_btn_previousActionPerformed

    private void step2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_step2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_step2ActionPerformed

    private void step3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_step3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_step3ActionPerformed

    private void enregistrer_commandeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enregistrer_commandeActionPerformed
if(JOptionPane.showConfirmDialog(null, "Cette commande sera enregistrée avec un status 'Brouillon'.\n\nVeuillez confirmer la commande dans le menu Bon de commande !","Message",JOptionPane.YES_NO_OPTION) == 0){
        try {
            int idCommande = insererCommande();
            insererCommandeDetail(idCommande);
            initialiserCommande();
        } catch (SQLException ex) {
            Logger.getLogger(NouveauCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
} 
        
        
    }//GEN-LAST:event_enregistrer_commandeActionPerformed

    private void btn_previous3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_previous3ActionPerformed
        switchToPage(3);
    }//GEN-LAST:event_btn_previous3ActionPerformed

    private void listeCommande1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listeCommande1MouseClicked
//         int selectedRow = listeCommande1.getSelectedRow();
//    if (selectedRow != -1 ) {
////        String idProduit = listeCommande1.getModel().getValueAt(selectedRow, 0).toString();
//        
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(0, 2, 10, 10)); // GridLayout pour une mise en page régulière
//
//        panel.add(new JLabel("Designation du produit"));
//        panel.add(new JLabel(listeCommande1.getModel().getValueAt(selectedRow, 1).toString()));
//        panel.add(new JLabel("Quantité"));
//        panel.add(new JLabel(listeCommande1.getModel().getValueAt(selectedRow, 3).toString()));
//        JDateChooser dateLivraisonModal = new JDateChooser();
//        dateLivraisonModal.setDate(new Date());
//        panel.add(new JLabel("Date de livraison"));
//        panel.add(dateLivraisonModal); 
//
//        // ComboBox avec des données initiales
//        String[] heures = {"06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"};
//        JComboBox<String> heureLivraison = new JComboBox<>(heures);
//        heureLivraison.setSelectedIndex(0); // Sélectionnez la première valeur par défaut
//
//        // Ajoutez les composants au panneau
//        panel.add(new JLabel("Heure"));
//        panel.add(heureLivraison);
//
//        String[] minutes = {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
//        JComboBox<String> minuteLivraison = new JComboBox<>(minutes);
//        heureLivraison.setSelectedIndex(0); // Sélectionnez la première valeur par défaut
//
//        panel.add(new JLabel("Minute"));
//        panel.add(minuteLivraison); // Ajoutez directement le composant JComboBox
//
//        // Affichez le panneau dans un dialogue modal JOptionPane
//        int option = JOptionPane.showConfirmDialog(null, panel, "Détails de la livraison", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//
//        // Vérifiez si l'utilisateur a appuyé sur le bouton "OK"
//        if (option == JOptionPane.OK_OPTION) {
////            // Récupérez la date sélectionnée du JDateChooser
//        Date dateSelectionnee = dateLivraisonModal.getDate();
//
//        // Récupérez l'heure sélectionnée du JComboBox
//        String heureSelectionnee = (String) heureLivraison.getSelectedItem();
//
//        // Récupérez les minutes sélectionnées du JComboBox
//        String minuteSelectionnee = (String) minuteLivraison.getSelectedItem();
//
//        // Formatez la date et l'heure dans le format requis (yyyy-MM-dd HH:mm:ss)
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String dateFormatee = sdf.format(dateSelectionnee);
//
//        // Combinez la date formatée avec l'heure et les minutes sélectionnées
//        String dateHeureFormattee = String.format("%s %s:%s:00", dateFormatee, heureSelectionnee, minuteSelectionnee);
//
//        // Utilisez la date et l'heure formatées comme nécessaire...
//
//        listeCommande1.getModel().setValueAt(getDateFormatDateTime(dateLivraisonModal, heureLivraison, minuteLivraison ), selectedRow, 4);
//
//        } else {
//            // L'utilisateur a appuyé sur "Annuler" ou a fermé la fenêtre de dialogue
//            System.out.println("Opération annulée");
//        }
//
//            
//        }
    }//GEN-LAST:event_listeCommande1MouseClicked

    //changer le format de la date en yyyy-MM-dd HH:mm:ss
    private String getDateFormatDateTime(JDateChooser dates, JComboBox<String> heures, JComboBox<String> minutes ){
        Date dateSelectionnee = dates.getDate();

        // Récupérez l'heure sélectionnée du JComboBox
        String heureSelectionnee = (String) heures.getSelectedItem();

        // Récupérez les minutes sélectionnées du JComboBox
        String minuteSelectionnee = (String) minutes.getSelectedItem();

        // Formatez la date et l'heure dans le format requis (yyyy-MM-dd HH:mm:ss)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        String dateFormatee = sdf.format(dateSelectionnee);

        // Combinez la date formatée avec l'heure et les minutes sélectionnées
        String dateHeureFormattee = String.format("%s %s:%s:00", dateFormatee, heureSelectionnee, minuteSelectionnee);
        
        return dateHeureFormattee;
    }
    
    private void listeFournisseurMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listeFournisseurMouseClicked
//        panelwarning.setVisible(false);
        int i = listeFournisseur.getSelectedRow();
        if (i != -1) {
        lbl_id_client.setText(listeFournisseur.getModel().getValueAt(i, 0).toString()); 
        lbl_nom_complet.setText( listeFournisseur.getModel().getValueAt(i, 1).toString()+" "+ listeFournisseur.getModel().getValueAt(i, 2).toString()); 
        lbl_adresse_client.setText( listeFournisseur.getModel().getValueAt(i, 5).toString()); 
        lbl_email_client.setText( listeFournisseur.getModel().getValueAt(i, 4).toString()); 
        lbl_tel_client.setText( listeFournisseur.getModel().getValueAt(i, 3).toString()); 
        }
        
    }//GEN-LAST:event_listeFournisseurMouseClicked

 
    
    private void refMagasinSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refMagasinSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_refMagasinSearchActionPerformed

    private void btn_refresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refresh1ActionPerformed
        listeFournisseur.clearSelection();
        refreshTableFournisseur( DAOFactory.getFournisseurDAO().select() );
    }//GEN-LAST:event_btn_refresh1ActionPerformed

    public int insererCommande() throws SQLException {
//            stm = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateCommande = now.format(formatter);
            int key = 0;
            javaapp.bean.CommandeF comm = new javaapp.bean.CommandeF();
            
            comm.setId(0);
            comm.setIdFournisseur(Integer.parseInt(lbl_id_client.getText().trim()));
            comm.setMontant(calculerMontantTotal());
            comm.setQuantite(listeCommande1.getRowCount());
            comm.setAvoirFacture("Non");
            comm.setPj("pj");
            try{
             comm.setDateCommande(DateConverter.getInputDate(dateCommandeInput, heureCommande, minuteCommande));
            } catch (ParseException ex) {
            Logger.getLogger(NouveauCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
            comm.setRefCommande(refCommande.getText());
            comm.setDescription(description.getText());
            comm.setStatus("Brouillon");
            CommandeFournisseurDAO commandeDAO = DAOFactory.getCommandeFournisseurDAO();
            
            if ( commandeDAO.create(comm) ) {
                key = commandeDAO.getGeneratedKey();
            }
       
        return key;
    }
    
    public void insererCommandeDetail(int idCommandeGenere){

        for (int i = 0; i < listeCommande1.getRowCount(); i++) {
            
            // Insérer les données dans la table commandeDetail
            CommandeDetailFournisseur comDetail = new CommandeDetailFournisseur();
            comDetail.setIdCommande(idCommandeGenere);
            Produit prod = DAOFactory.getProduitDAO().find(listeCommande1.getValueAt(i, 0).toString());
            comDetail.setIdProduit( prod.getId());
            comDetail.setQuantite(Integer.parseInt(listeCommande1.getValueAt(i, 5).toString()));
            comDetail.setDescription("Produit:"+listeCommande1.getValueAt(i, 1).toString()+" reference "+listeCommande1.getValueAt(i, 0).toString());
            comDetail.setProduit(prod);
            comDetail.setMontant(Float.parseFloat(listeCommande1.getValueAt(i, 6).toString()));
            comDetail.setRemise(Float.parseFloat(listeCommande1.getValueAt(i, 4).toString()));
            CommandeDetailFournisseurDAO commDetDAO = DAOFactory.getCommandeDetailFournisseurDAO();
            commDetDAO.create(comDetail);
            
        }
        t = new Toast("Commande enregistrée!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
        t.showtoast();
        
    }
 
    public static String getDateTime(JDateChooser dateChooser) {
        Date selectedDate = dateChooser.getDate(); // Récupère la date du JDateChooser
        Calendar calendar = Calendar.getInstance(); // Obtient l'instance de calendrier actuelle
        calendar.setTime(selectedDate); // Définit la date récupérée sans modifier l'heure
        
        // Ajout de l'heure actuelle
        Calendar now = Calendar.getInstance(); // Obtient l'heure actuelle
        calendar.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, now.get(Calendar.SECOND));

        // Formatter pour la sortie en format yyyy-MM-dd HH:mm:ss
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(calendar.getTime());
    }

    public void refreshTableArticle(List<javaapp.bean.Stock> stocks){
        String column[]= {"Reference","Designation","Prix TTC","Qté stock","Reservé"};
        Object[] data = new Object[5];
        DefaultTableModel model = new DefaultTableModel(null,column);
        
           for (javaapp.bean.Stock stock : stocks){
//                data[0]=resultSet.getString("idProduit");
                data[0]=stock.getProduit().getRefProduit();
                data[1]=stock.getProduit().getDesignation();
                float puht = stock.getProduit().getPUHT();
                data[2]=formater.formatFloat(puht + (puht*stock.getProduit().getTVA()/100));
                data[3]= stock.getQuantiteStock();
                data[4]=stock.getQuantiteReserve();                
                model.addRow(data);
       }
       listeArticle.setModel(model);
    }
    
     private void refreshTableFournisseur( List < javaapp.bean.Personne > fournisseurs ){
        String column[]= {"ID", "Nom", "Prénom", "Email 1", "Téléphone 1", "Adresse"};
        Object[] data = new Object[6];
        DefaultTableModel model = new DefaultTableModel(null,column);
        for(Personne fournisseur : fournisseurs){
            data[0]=fournisseur.getId();
            data[1]=fournisseur.getNom();
            data[2]=fournisseur.getPrenom();
            data[3]=fournisseur.getEmail1();
            data[4]=fournisseur.getTel1();
            data[5]=fournisseur.getAdresse();
            model.addRow(data);
        }
        listeFournisseur.setModel(model);
//        rowcount();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_delete_invoice_item;
    private javaapp.component.ButtonRadius btn_modify_invoice_item;
    private javaapp.component.ButtonRadius btn_next;
    private javaapp.component.ButtonRadius btn_next1;
    private javaapp.component.ButtonRadius btn_previous;
    private javaapp.component.ButtonRadius btn_previous3;
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_refresh1;
    private javaapp.component.ButtonRadius btn_search;
    private javaapp.component.ButtonRadius btn_search1;
    private javaapp.component.ButtonRadius btn_start;
    private javax.swing.JTextField client_key_search;
    private com.toedter.calendar.JDateChooser dateCommandeInput;
    private javax.swing.JTextArea description;
    private javax.swing.JTextField designation_item;
    private javaapp.component.ButtonRadius enregistrer_commande;
    private javax.swing.JComboBox<String> heureCommande;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JLabel lbl_adresse_client;
    private javax.swing.JLabel lbl_email_client;
    private javax.swing.JLabel lbl_id_client;
    private javax.swing.JLabel lbl_nom_complet;
    private javax.swing.JLabel lbl_tel_client;
    private javaapp.component.Table listeArticle;
    private javaapp.component.Table listeCommande1;
    private javaapp.component.Table listeFournisseur;
    private javaapp.component.Table listePanier;
    private javax.swing.JComboBox<String> minuteCommande;
    private javax.swing.JLabel nombre_article;
    private javax.swing.JLayeredPane page1;
    private javax.swing.JLayeredPane page2;
    private javax.swing.JPanel page3;
    private javax.swing.JLayeredPane page4;
    private javax.swing.JPanel panel_modify_item;
    private javax.swing.JTextField price_item;
    private javax.swing.JTextField produitKeySearch;
    private javax.swing.JSpinner quantite_item;
    private javax.swing.JTextField refCommande;
    private javax.swing.JComboBox<String> refMagasinSearch;
    private javax.swing.JSpinner remise_item;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JScrollPane scrollTable1;
    private javax.swing.JScrollPane scrollTableClient;
    private javaapp.component.ButtonRadius step1;
    private javaapp.component.ButtonRadius step2;
    private javaapp.component.ButtonRadius step3;
    private javax.swing.JPanel stepPanel;
    private javax.swing.JLabel total_prix;
    // End of variables declaration//GEN-END:variables



}
