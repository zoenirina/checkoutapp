
package javaapp.page;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.List;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.component.ButtonRadius;
import javaapp.component.Toast;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

public class Commande extends javax.swing.JPanel {
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
    private final String SELECT_ALL = "SELECT p.id AS idProduit,p.designation,p.PU AS prix,p.refProduit , u.poids, u.capacite," +
            "(SELECT COALESCE(SUM(CASE WHEN m.sens = 'Entrée' THEN m.quantite ELSE 0 END), 0) - COALESCE(SUM(CASE WHEN m.sens = 'Sortie' THEN m.quantite ELSE 0 END), 0)" +
            "FROM mouvementStock m WHERE m.idProduit = p.id) AS quantite_stock FROM produits p LEFT JOIN uniteStockage u ON p.idUnite = u.id";
    private int idClientSelected=0;
    private Integer[] idComDetArray;
    String insertLivraisonDetailsQuery = "INSERT INTO livraisonDetails (idCommandeDetail, idProduit, quantiteRecu, quantiteValide, montant, devise, idLivraison, description, dateLivraison) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    

    public Commande(JPanel panel) {
        initComponents();
        pan= panel;
        query=SELECT_ALL; 
        refreshTable();
        scrollTable.getViewport().setBackground(Color.decode("#F0ECF3"));
        scrollTable1.getViewport().setBackground(Color.decode("#F0ECF3"));
        scrollTableClient.getViewport().setBackground(Color.white);
        btn_save_state= "insert";
        setListeClient();
        switchToPage(1);
        panelwarning.setVisible(true);
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
        listeClient = new javaapp.component.Table();
        btn_next1 = new javaapp.component.ButtonRadius();
        jSeparator7 = new javax.swing.JSeparator();
        panelwarning = new javaapp.component.PanelBorderRound();
        jLabel1 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        page3 = new javax.swing.JPanel();
        scrollTable1 = new javax.swing.JScrollPane();
        listeCommande = new javaapp.component.Table();
        scrollTable = new javax.swing.JScrollPane();
        listeArticle = new javaapp.component.Table();
        jLabel21 = new javax.swing.JLabel();
        produitKeySearch = new javax.swing.JTextField();
        btn_search = new javaapp.component.ButtonRadius();
        btn_refresh = new javaapp.component.ButtonRadius();
        panel_modify_item = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        price_item = new javax.swing.JLabel();
        designation_item = new javax.swing.JLabel();
        quantite_item = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        btn_modify_invoice_item = new javaapp.component.ButtonRadius();
        btn_delete_invoice_item = new javaapp.component.ButtonRadius();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        total_prix = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btn_cancel = new javaapp.component.ButtonRadius();
        btn_next = new javaapp.component.ButtonRadius();
        btn_previous = new javaapp.component.ButtonRadius();
        jSeparator1 = new javax.swing.JSeparator();
        stepPanel = new javax.swing.JPanel();
        step4 = new javaapp.component.ButtonRadius();
        step1 = new javaapp.component.ButtonRadius();
        step2 = new javaapp.component.ButtonRadius();
        step3 = new javaapp.component.ButtonRadius();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        page4 = new javax.swing.JLayeredPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        nombre_article = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listeCommande1 = new javaapp.component.Table();
        enregistrer_commande = new javaapp.component.ButtonRadius();
        btn_previous1 = new javaapp.component.ButtonRadius();
        jLabel29 = new javax.swing.JLabel();
        btn_previous3 = new javaapp.component.ButtonRadius();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel9 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        lbl_nom_complet = new javax.swing.JLabel();
        lbl_id_client = new javax.swing.JLabel();
        lbl_adresse_client = new javax.swing.JLabel();
        lbl_email_client = new javax.swing.JLabel();
        lbl_tel_client = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        radiobtn1 = new javax.swing.JRadioButton();
        radiobtn2 = new javax.swing.JRadioButton();
        lbl_frais_livraison = new javax.swing.JSpinner();
        lbl_date_livraison = new com.toedter.calendar.JDateChooser();
        page5 = new javax.swing.JLayeredPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        panelBorderRound5 = new javaapp.component.PanelBorderRound();
        jLabel31 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        nombre_article1 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table2 = new javaapp.component.Table();
        btn_print = new javaapp.component.ButtonRadius();
        jLabel43 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        fac_nom_client = new javax.swing.JLabel();
        fac_id_client = new javax.swing.JLabel();
        fac_adresse_client = new javax.swing.JLabel();
        fac_email_client = new javax.swing.JLabel();
        fac_tel_client = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        fac_date_livraison = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        fac_frais_livraison = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        fac_type_livraison = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();

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

        scrollTableClient.setBorder(null);

        listeClient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        listeClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listeClientMouseClicked(evt);
            }
        });
        scrollTableClient.setViewportView(listeClient);

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

        panelwarning.setBackground(new java.awt.Color(255, 255, 169));

        jLabel1.setFont(new java.awt.Font("Verdana", 2, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 135, 21));
        jLabel1.setText("Vous avez selectionné aucun client pour le moment.");

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/warning.png"))); // NOI18N

        javax.swing.GroupLayout panelwarningLayout = new javax.swing.GroupLayout(panelwarning);
        panelwarning.setLayout(panelwarningLayout);
        panelwarningLayout.setHorizontalGroup(
            panelwarningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelwarningLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelwarningLayout.setVerticalGroup(
            panelwarningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelwarningLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        page2.setLayer(jLabel22, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(client_key_search, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(btn_search1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(btn_refresh1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(scrollTableClient, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(btn_next1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(jSeparator7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page2.setLayer(panelwarning, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout page2Layout = new javax.swing.GroupLayout(page2);
        page2.setLayout(page2Layout);
        page2Layout.setHorizontalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(page2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(client_key_search, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addComponent(btn_search1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_refresh1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_next1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrollTableClient, javax.swing.GroupLayout.DEFAULT_SIZE, 906, Short.MAX_VALUE)
                    .addComponent(panelwarning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(scrollTableClient, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(panelwarning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_next1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        page3.setBackground(new java.awt.Color(255, 255, 255));

        scrollTable1.setBorder(null);

        listeCommande.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Article", "Prix", "Qte"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listeCommande.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listeCommandeMouseClicked(evt);
            }
        });
        scrollTable1.setViewportView(listeCommande);

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
        listeArticle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listeArticleMouseClicked(evt);
            }
        });
        scrollTable.setViewportView(listeArticle);

        jLabel21.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("Réference ou ID article");

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

        panel_modify_item.setBackground(new java.awt.Color(255, 102, 51));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Article");

        price_item.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        price_item.setForeground(new java.awt.Color(254, 252, 252));
        price_item.setText("            ");

        designation_item.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        designation_item.setForeground(new java.awt.Color(254, 252, 252));
        designation_item.setText("                    ");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Prix");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Quantité");

        jSeparator2.setBackground(new java.awt.Color(51, 51, 51));
        jSeparator2.setForeground(new java.awt.Color(51, 51, 51));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator3.setBackground(new java.awt.Color(51, 51, 51));
        jSeparator3.setForeground(new java.awt.Color(51, 51, 51));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btn_modify_invoice_item.setBackground(new java.awt.Color(255, 102, 51));
        btn_modify_invoice_item.setBorder(null);
        btn_modify_invoice_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/iconModify.png"))); // NOI18N
        btn_modify_invoice_item.setBorderColor(new java.awt.Color(238, 245, 255));
        btn_modify_invoice_item.setColor(new java.awt.Color(255, 102, 51));
        btn_modify_invoice_item.setColorClick(new java.awt.Color(255, 255, 255));
        btn_modify_invoice_item.setColorOver(new java.awt.Color(240, 236, 243));
        btn_modify_invoice_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modify_invoice_itemActionPerformed(evt);
            }
        });

        btn_delete_invoice_item.setBackground(new java.awt.Color(255, 255, 255));
        btn_delete_invoice_item.setBorder(null);
        btn_delete_invoice_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/btnDelete.png"))); // NOI18N
        btn_delete_invoice_item.setBorderColor(new java.awt.Color(255, 255, 255));
        btn_delete_invoice_item.setColor(new java.awt.Color(255, 255, 255));
        btn_delete_invoice_item.setColorClick(new java.awt.Color(255, 255, 255));
        btn_delete_invoice_item.setColorOver(new java.awt.Color(255, 255, 255));
        btn_delete_invoice_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_invoice_itemActionPerformed(evt);
            }
        });

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Panier (4)");

        total_prix.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        total_prix.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Total:");

        javax.swing.GroupLayout panel_modify_itemLayout = new javax.swing.GroupLayout(panel_modify_item);
        panel_modify_item.setLayout(panel_modify_itemLayout);
        panel_modify_itemLayout.setHorizontalGroup(
            panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator5)
            .addGroup(panel_modify_itemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_modify_itemLayout.createSequentialGroup()
                        .addGroup(panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(panel_modify_itemLayout.createSequentialGroup()
                                .addComponent(designation_item, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(price_item, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addGroup(panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(quantite_item, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_modify_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_delete_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addGroup(panel_modify_itemLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total_prix, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );
        panel_modify_itemLayout.setVerticalGroup(
            panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_modify_itemLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(total_prix, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_modify_itemLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(quantite_item, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_modify_invoice_item, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_modify_itemLayout.createSequentialGroup()
                        .addGroup(panel_modify_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_modify_itemLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(designation_item))
                            .addGroup(panel_modify_itemLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(price_item)))
                        .addGap(16, 16, 16)))
                .addContainerGap())
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

        javax.swing.GroupLayout page3Layout = new javax.swing.GroupLayout(page3);
        page3.setLayout(page3Layout);
        page3Layout.setHorizontalGroup(
            page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page3Layout.createSequentialGroup()
                .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page3Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(page3Layout.createSequentialGroup()
                                .addComponent(produitKeySearch)
                                .addGap(18, 18, 18)
                                .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel21)
                            .addComponent(btn_previous, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(page3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page3Layout.createSequentialGroup()
                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(168, 168, 168))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page3Layout.createSequentialGroup()
                        .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(scrollTable1)
                            .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btn_next, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panel_modify_item, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(22, 22, 22))))
            .addComponent(jSeparator1)
        );
        page3Layout.setVerticalGroup(
            page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page3Layout.createSequentialGroup()
                        .addComponent(panel_modify_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(scrollTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(page3Layout.createSequentialGroup()
                        .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(produitKeySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(page3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_next, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_previous, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        stepPanel.setBackground(new java.awt.Color(255, 255, 255));
        stepPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        step4.setBackground(new java.awt.Color(240, 236, 243));
        step4.setText("4");
        step4.setBorderColor(new java.awt.Color(240, 236, 243));
        step4.setColor(new java.awt.Color(240, 236, 243));
        step4.setColorClick(new java.awt.Color(240, 236, 243));
        step4.setColorOver(new java.awt.Color(240, 236, 243));
        step4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        step4.setRadius(50);
        step4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                step4ActionPerformed(evt);
            }
        });
        stepPanel.add(step4, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 20, 50, 50));

        step1.setText("1");
        step1.setBorderColor(new java.awt.Color(248, 91, 50));
        step1.setColor(new java.awt.Color(248, 91, 50));
        step1.setColorClick(new java.awt.Color(240, 236, 243));
        step1.setColorOver(new java.awt.Color(248, 91, 50));
        step1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        step1.setRadius(50);
        stepPanel.add(step1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 50, 50));

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
        stepPanel.add(step2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 50, 50));

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
        stepPanel.add(step3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, 50, 50));

        jPanel2.setBackground(new java.awt.Color(240, 236, 243));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 680, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        stepPanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 680, 5));

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Reçu");
        stepPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 80, -1, -1));

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Choisir un client");
        stepPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, -1, -1));

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Confirmation");
        stepPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, -1, -1));

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Remplir un panier");
        stepPanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 102, 51));

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Date: 14/06/2024");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(48, 48, 48))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel14)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 222, 177));

        nombre_article.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nombre_article.setForeground(new java.awt.Color(248, 91, 50));
        nombre_article.setText("Articles");

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(248, 91, 50));
        jLabel18.setText("Quantité:3");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(nombre_article, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(48, 48, 48))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombre_article)
                    .addComponent(jLabel18))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        listeCommande1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
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
        enregistrer_commande.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enregistrer_commandeActionPerformed(evt);
            }
        });

        btn_previous1.setBackground(new java.awt.Color(240, 236, 243));
        btn_previous1.setBorder(null);
        btn_previous1.setForeground(new java.awt.Color(51, 51, 51));
        btn_previous1.setText("Annuler");
        btn_previous1.setBorderColor(new java.awt.Color(240, 236, 243));
        btn_previous1.setColor(new java.awt.Color(240, 236, 243));
        btn_previous1.setColorClick(new java.awt.Color(240, 236, 243));
        btn_previous1.setColorOver(new java.awt.Color(240, 236, 243));
        btn_previous1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btn_previous1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_previous1ActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 51, 51));
        jLabel29.setText("Montant total");

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
        jLabel49.setText("Client");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(0, 13, Short.MAX_VALUE)
                .addComponent(jLabel49))
        );

        jPanel9.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 70, -1));

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
        jLabel52.setText("Identification");

        lbl_nom_complet.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lbl_nom_complet.setForeground(new java.awt.Color(51, 51, 51));
        lbl_nom_complet.setText("John DOE");

        lbl_id_client.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lbl_id_client.setForeground(new java.awt.Color(51, 51, 51));
        lbl_id_client.setText("001");

        lbl_adresse_client.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lbl_adresse_client.setForeground(new java.awt.Color(51, 51, 51));
        lbl_adresse_client.setText("Lot 23/5 Andakivy");

        lbl_email_client.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lbl_email_client.setForeground(new java.awt.Color(51, 51, 51));
        lbl_email_client.setText("Email: johndoe@gmail.com");

        lbl_tel_client.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lbl_tel_client.setForeground(new java.awt.Color(51, 51, 51));
        lbl_tel_client.setText("Tel: +262 60 388 30");

        jLabel20.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel20.setText("Client N°");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_nom_complet, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                            .addComponent(jLabel20)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl_id_client, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_adresse_client, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(111, 111, 111)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbl_email_client, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_tel_client, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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
                        .addComponent(lbl_email_client)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_tel_client))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_id_client)
                            .addComponent(lbl_adresse_client)
                            .addComponent(jLabel20))
                        .addGap(3, 3, 3)
                        .addComponent(lbl_nom_complet)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 950, 110));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jLabel53.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(51, 51, 51));
        jLabel53.setText("Commande");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel53)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, 30));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel54.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(102, 102, 102));
        jLabel54.setText("Frais de livraison");

        jLabel55.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(102, 102, 102));
        jLabel55.setText("Type");

        jLabel56.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(102, 102, 102));
        jLabel56.setText("Date de livraison");

        radiobtn1.setSelected(true);
        radiobtn1.setText("Livraison complet");
        radiobtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiobtn1ActionPerformed(evt);
            }
        });

        radiobtn2.setText("Livraison partielle");
        radiobtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiobtn2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 175, Short.MAX_VALUE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_date_livraison, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(134, 134, 134)))
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_frais_livraison, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(113, 113, 113)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(radiobtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radiobtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(jLabel54)
                    .addComponent(jLabel55))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lbl_frais_livraison))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radiobtn1)
                        .addComponent(radiobtn2))
                    .addComponent(lbl_date_livraison, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 950, 100));

        page4.setLayer(jPanel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page4.setLayer(jPanel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page4.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page4.setLayer(enregistrer_commande, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page4.setLayer(btn_previous1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page4.setLayer(jLabel29, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page4.setLayer(btn_previous3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page4.setLayer(jSeparator6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page4.setLayer(jPanel9, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout page4Layout = new javax.swing.GroupLayout(page4);
        page4.setLayout(page4Layout);
        page4Layout.setHorizontalGroup(
            page4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(page4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(page4Layout.createSequentialGroup()
                        .addComponent(btn_previous3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_previous1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(enregistrer_commande, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 955, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
            .addComponent(jSeparator6)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(page4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page4Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(177, 177, 177))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page4Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))))
        );
        page4Layout.setVerticalGroup(
            page4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel29)
                .addGap(18, 18, 18)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(page4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enregistrer_commande, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_previous1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_previous3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 102, 51));

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("FACTURE N°");

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Date: 14/06/2024");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(48, 48, 48))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        panelBorderRound5.setBackground(new java.awt.Color(85, 244, 85));

        jLabel31.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("La commande a bien été enregistrée");

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/IMGPOPUP.png"))); // NOI18N

        javax.swing.GroupLayout panelBorderRound5Layout = new javax.swing.GroupLayout(panelBorderRound5);
        panelBorderRound5.setLayout(panelBorderRound5Layout);
        panelBorderRound5Layout.setHorizontalGroup(
            panelBorderRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addGap(15, 15, 15)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(570, Short.MAX_VALUE))
        );
        panelBorderRound5Layout.setVerticalGroup(
            panelBorderRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel31)
                .addContainerGap())
            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(255, 222, 177));

        nombre_article1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nombre_article1.setForeground(new java.awt.Color(248, 91, 50));
        nombre_article1.setText("Articles");

        jLabel32.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(248, 91, 50));
        jLabel32.setText("Quantité:3");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(nombre_article1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel32)
                .addGap(48, 48, 48))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombre_article1)
                    .addComponent(jLabel32))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        table2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(table2);

        btn_print.setBackground(new java.awt.Color(255, 102, 51));
        btn_print.setForeground(new java.awt.Color(255, 255, 255));
        btn_print.setText("Imprimer et valider");
        btn_print.setBorderColor(new java.awt.Color(248, 91, 50));
        btn_print.setColor(new java.awt.Color(255, 102, 51));
        btn_print.setColorClick(new java.awt.Color(248, 91, 50));
        btn_print.setColorOver(new java.awt.Color(248, 91, 50));
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(51, 51, 51));
        jLabel43.setText("Total net à payer:");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 70, -1));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

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

        jLabel19.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel19.setText("Client N°");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fac_nom_client, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel19)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(fac_id_client, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fac_adresse_client, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(111, 111, 111)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(fac_email_client, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(fac_tel_client, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(fac_email_client)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fac_tel_client))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fac_id_client)
                            .addComponent(fac_adresse_client)
                            .addComponent(jLabel19))
                        .addGap(3, 3, 3)
                        .addComponent(fac_nom_client)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 950, 110));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel45.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(51, 51, 51));
        jLabel45.setText("Commande");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel45)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, 30));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        fac_date_livraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        fac_date_livraison.setForeground(new java.awt.Color(51, 51, 51));

        jLabel46.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(102, 102, 102));
        jLabel46.setText("Frais de livraison");

        fac_frais_livraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        fac_frais_livraison.setForeground(new java.awt.Color(51, 51, 51));

        jLabel47.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(102, 102, 102));
        jLabel47.setText("Type");

        fac_type_livraison.setForeground(new java.awt.Color(51, 51, 51));

        jLabel48.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(102, 102, 102));
        jLabel48.setText("Date de livraison");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fac_date_livraison, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fac_frais_livraison, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(88, 88, 88)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fac_type_livraison, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(87, 87, 87))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(jLabel46)
                    .addComponent(jLabel47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fac_frais_livraison, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fac_type_livraison, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fac_date_livraison, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 950, 100));

        page5.setLayer(jPanel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page5.setLayer(panelBorderRound5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page5.setLayer(jPanel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page5.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page5.setLayer(btn_print, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page5.setLayer(jLabel43, javax.swing.JLayeredPane.DEFAULT_LAYER);
        page5.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout page5Layout = new javax.swing.GroupLayout(page5);
        page5.setLayout(page5Layout);
        page5Layout.setHorizontalGroup(
            page5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(page5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 955, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
            .addGroup(page5Layout.createSequentialGroup()
                .addGroup(page5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page5Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(page5Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(panelBorderRound5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        page5Layout.setVerticalGroup(
            page5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(panelBorderRound5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel43)
                .addGap(18, 18, 18)
                .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(page1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(page2)
            .addComponent(stepPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(page4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(page5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(page3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(stepPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(page1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(page2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(page3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(page4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(page5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void listeCommandeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listeCommandeMouseClicked
        int selectedRow = listeCommande.getSelectedRow();
        if (selectedRow != -1) {
//            code_item.setText(listeCommande.getValueAt(selectedRow, 1).toString());  // Référence produit
            designation_item.setText(listeCommande.getValueAt(selectedRow, 1).toString());  // Désignation
            price_item.setText(listeCommande.getValueAt(selectedRow, 2).toString());  // Prix
            quantite_item.setValue(Integer.parseInt(listeCommande.getValueAt(selectedRow, 3).toString()));  // Quantité
        }
    }//GEN-LAST:event_listeCommandeMouseClicked

    private void switchToPage(int page){
    switch(page){
        case 1:
            page1.setVisible(true);
            page2.setVisible(false);
            page3.setVisible(false);
            page4.setVisible(false);
            page5.setVisible(false);
            
            stepPanel.setVisible(false);
        break;
        case 2:
            stepPanel.setVisible(true);
            page1.setVisible(false);
            page2.setVisible(true);
            page3.setVisible(false);
            page4.setVisible(false);
            page5.setVisible(false);
            
            activeButton(step1);
            desactiveButton(step2);
            desactiveButton(step3);
            desactiveButton(step4);
        break;
        case 3:
            page1.setVisible(false);
            page2.setVisible(false);
            page3.setVisible(true);
            page4.setVisible(false);
            page5.setVisible(false);
            
            
            desactiveButton(step1);
            activeButton(step2);
            desactiveButton(step3);
            desactiveButton(step4);
        break;
        case 4:
            page1.setVisible(false);
            page2.setVisible(false);
            page3.setVisible(false);
            page4.setVisible(true);
            page5.setVisible(false);
            
            desactiveButton(step4);
            desactiveButton(step1);
            desactiveButton(step2);
            activeButton(step3);
        break;
        case 5:
            page1.setVisible(false);
            page2.setVisible(false);
            page3.setVisible(false);
            page4.setVisible(false);
            page5.setVisible(true);
            
            activeButton(step4);
            desactiveButton(step1);
            desactiveButton(step2);
            desactiveButton(step3);
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
           query=SELECT_ALL+" WHERE p.designation like '%"+produitKeySearch.getText()+"%' or p.refProduit like '%"+produitKeySearch.getText()+"%'";
              refreshTable();

    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        query=SELECT_ALL;
        refreshTable();
        produitKeySearch.setText("");
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_modify_invoice_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modify_invoice_itemActionPerformed
         int selectedRow = listeCommande.getSelectedRow();
    if (selectedRow != -1) {
        listeCommande.setValueAt(quantite_item.getValue(), selectedRow, 3);  // Mise à jour de la quantité
    }
    total_prix.setText(String.valueOf(calculerMontantTotal()) +" Ar");
    }//GEN-LAST:event_btn_modify_invoice_itemActionPerformed

    private void btn_delete_invoice_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_invoice_itemActionPerformed
        int selectedRow = listeCommande.getSelectedRow();
    if (selectedRow != -1) {
        ((DefaultTableModel) listeCommande.getModel()).removeRow(selectedRow);
    }
    total_prix.setText(String.valueOf(calculerMontantTotal()) +" Ar");
    }//GEN-LAST:event_btn_delete_invoice_itemActionPerformed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
                for(int i = listeCommande.getRowCount()-1 ; i >= 0; i--){
        ((DefaultTableModel) listeCommande.getModel()).removeRow(i);
        }
    }//GEN-LAST:event_btn_cancelActionPerformed

    private void btn_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextActionPerformed
        if(listeCommande.getRowCount() > 0){
            switchToPage(4);
            updateTableModel();
       }else{
        JOptionPane.showMessageDialog(null, "Oups, il semble que votre panier est vide!","Erreur",JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btn_nextActionPerformed

    // Créer une fonction pour mettre à jour le modèle de tableau en fonction de la sélection du bouton radio
    private void updateTableModel() {
    DefaultTableModel model1 = new DefaultTableModel();

    // Vérifier quel bouton radio est sélectionné
    if (radiobtn1.isSelected()) {
        // Si radioButton1 est sélectionné, définir le modèle avec 4 colonnes
        model1.setColumnIdentifiers(new Object[]{"ID", "Article", "Montant", "Quantité"});
    } else if (radiobtn2.isSelected()) {
        // Si radioButton2 est sélectionné, définir le modèle avec 5 colonnes
        model1.setColumnIdentifiers(new Object[]{"ID","Article", "Montant", "Quantité", "DateLivraison"});
    }

    // Parcourir les lignes de listeCommande et copier les données dans listeCommande1 avec un bouton "Enregistrer"
    for (int i = 0; i < listeCommande.getRowCount(); i++) {
        Object[] row = new Object[listeCommande.getColumnCount() + 1]; // Ajouter 1 pour la colonne du bouton
        for (int j = 0; j < listeCommande.getColumnCount(); j++) {
            
            if(j == 2){
            row[j] = Float.parseFloat(listeCommande.getValueAt(i, 2).toString())*Float.parseFloat(listeCommande.getValueAt(i, 3).toString());
            }else{
            row[j] = listeCommande.getValueAt(i, j);
            }
        }
        row[listeCommande.getColumnCount()] = "Non programmé";
        // Ajouter la ligne dans le modèle de tableau listeCommande1
        model1.addRow(row);
    }

    // Définir le modèle de tableau listeCommande1
    listeCommande1.setModel(model1);
}

    private float calculerMontantTotal() {
    
    float montantTotal = 0.0f;
    DefaultTableModel model = (DefaultTableModel) listeCommande.getModel();
    for (int i = 0; i < model.getRowCount(); i++) {
        float prixUnitaire = Float.parseFloat(model.getValueAt(i, 2).toString());
        int quantite = Integer.parseInt(model.getValueAt(i, 3).toString());
        float montantLigne = prixUnitaire * quantite;
        montantTotal += montantLigne;
    }
    return montantTotal;
}

    private void listeArticleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listeArticleMouseClicked
      int selectedRow = listeArticle.getSelectedRow();
    if (selectedRow != -1) {
        String idProduit = listeArticle.getModel().getValueAt(selectedRow, 0).toString();
//        String refProduit = listeArticle.getModel().getValueAt(selectedRow, 1).toString();
        String designation = listeArticle.getModel().getValueAt(selectedRow, 1).toString();
        String prix = listeArticle.getModel().getValueAt(selectedRow, 2).toString();
        String stock = listeArticle.getModel().getValueAt(selectedRow, 3).toString();

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
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        panel.add(quantitySpinner);

        int result = JOptionPane.showConfirmDialog(null, panel, "Entrer la quantité désirée",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if( (int) quantitySpinner.getValue() <= Integer.parseInt(stock)){
             DefaultTableModel model = (DefaultTableModel) listeCommande.getModel();
            boolean articleExiste = false;
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).equals(idProduit)) {
                    // L'article existe déjà dans la liste, mettez à jour la quantité
                    int ancienneQuantite = (int) model.getValueAt(i, 3);
                    int nouvelleQuantite = ancienneQuantite + (int) quantitySpinner.getValue();
                    model.setValueAt(nouvelleQuantite, i, 3); // Mise à jour de la quantité
                    articleExiste = true;
                    break;
                }
            }
            if (!articleExiste) {
                // Si l'article n'existe pas encore dans la liste, ajoutez-le
                model.addRow(new Object[]{idProduit, designation, prix, quantitySpinner.getValue()});
            }
            total_prix.setText(String.valueOf(calculerMontantTotal()) +" Ar");
            }else{
                JOptionPane.showConfirmDialog(null,"Stock insuffisant pour cette opération.\nQuantité disponible: "+stock, "Erreur",
                JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
            }
           
        }
    }
    }//GEN-LAST:event_listeArticleMouseClicked

    private void btn_next1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_next1ActionPerformed
        switchToPage(3);
    }//GEN-LAST:event_btn_next1ActionPerformed

    private void btn_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_startActionPerformed
         switchToPage(2);
    }//GEN-LAST:event_btn_startActionPerformed

    private void btn_previousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_previousActionPerformed
       switchToPage(2);
       for(int i = listeCommande.getRowCount()-1 ; i >= 0; i--){
        ((DefaultTableModel) listeCommande.getModel()).removeRow(i);
        }
    }//GEN-LAST:event_btn_previousActionPerformed

    private void step4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_step4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_step4ActionPerformed

    private void step2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_step2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_step2ActionPerformed

    private void step3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_step3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_step3ActionPerformed

    private void enregistrer_commandeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enregistrer_commandeActionPerformed
    
    
    if(radiobtn1.isSelected() && lbl_date_livraison.getDate() == null){
    
    }
        try {
        // Insérer la commande et récupérer son ID généré
        int idCommande = insererCommande();

        // Insérer les détails de commande
        insererCommandeDetail(idCommande);

        // Insérer la livraison et récupérer son ID généré
        int idLivraison = insererLivraison(idCommande);

        // Insérer les détails de livraison
        insererLivraisonEtDetails(idLivraison,idComDetArray);

        // Créer la facture
        int idFacture = insererFacture(idLivraison, Integer.parseInt(lbl_id_client.getText().trim()));
        switchToPage(5);
        // Créer le paiement
//        int idPaiement = insererPaiement(connection, datePaiement, refPaiement, idFacture, idClient, montantRecu, restePaye);
    } catch (SQLException e) {
        e.printStackTrace();
    }
        
        
    }//GEN-LAST:event_enregistrer_commandeActionPerformed

    private void btn_previous1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_previous1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_previous1ActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        switchToPage(1);
    }//GEN-LAST:event_btn_printActionPerformed

    private void btn_previous3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_previous3ActionPerformed
        switchToPage(3);
    }//GEN-LAST:event_btn_previous3ActionPerformed

    private void listeCommande1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listeCommande1MouseClicked
         int selectedRow = listeCommande1.getSelectedRow();
    if (selectedRow != -1 && radiobtn2.isSelected()) {
//        String idProduit = listeCommande1.getModel().getValueAt(selectedRow, 0).toString();
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10)); // GridLayout pour une mise en page régulière

        panel.add(new JLabel("Designation du produit"));
        panel.add(new JLabel(listeCommande1.getModel().getValueAt(selectedRow, 1).toString()));
        panel.add(new JLabel("Quantité"));
        panel.add(new JLabel(listeCommande1.getModel().getValueAt(selectedRow, 3).toString()));
        JDateChooser dateLivraisonModal = new JDateChooser();
        dateLivraisonModal.setDate(new Date());
        panel.add(new JLabel("Date de livraison"));
        panel.add(dateLivraisonModal); 

        // ComboBox avec des données initiales
        String[] heures = {"06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"};
        JComboBox<String> heureLivraison = new JComboBox<>(heures);
        heureLivraison.setSelectedIndex(0); // Sélectionnez la première valeur par défaut

        // Ajoutez les composants au panneau
        panel.add(new JLabel("Heure"));
        panel.add(heureLivraison);

        String[] minutes = {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
        JComboBox<String> minuteLivraison = new JComboBox<>(minutes);
        heureLivraison.setSelectedIndex(0); // Sélectionnez la première valeur par défaut

        panel.add(new JLabel("Minute"));
        panel.add(minuteLivraison); // Ajoutez directement le composant JComboBox

        // Affichez le panneau dans un dialogue modal JOptionPane
        int option = JOptionPane.showConfirmDialog(null, panel, "Détails de la livraison", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Vérifiez si l'utilisateur a appuyé sur le bouton "OK"
        if (option == JOptionPane.OK_OPTION) {
            // Récupérez la date sélectionnée du JDateChooser
        Date dateSelectionnee = dateLivraisonModal.getDate();

        // Récupérez l'heure sélectionnée du JComboBox
        String heureSelectionnee = (String) heureLivraison.getSelectedItem();

        // Récupérez les minutes sélectionnées du JComboBox
        String minuteSelectionnee = (String) minuteLivraison.getSelectedItem();

        // Formatez la date et l'heure dans le format requis (yyyy-MM-dd HH:mm:ss)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormatee = sdf.format(dateSelectionnee);

        // Combinez la date formatée avec l'heure et les minutes sélectionnées
        String dateHeureFormattee = String.format("%s %s:%s:00", dateFormatee, heureSelectionnee, minuteSelectionnee);

        // Utilisez la date et l'heure formatées comme nécessaire...

        listeCommande1.getModel().setValueAt(dateHeureFormattee, selectedRow, 4);

        } else {
            // L'utilisateur a appuyé sur "Annuler" ou a fermé la fenêtre de dialogue
            System.out.println("Opération annulée");
        }

            
        }
    }//GEN-LAST:event_listeCommande1MouseClicked

    private void listeClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listeClientMouseClicked
        panelwarning.setVisible(false);
        int i = listeClient.getSelectedRow();
        if (i != -1) {
//        idClientSelected = (int) listeClient.getModel().getValueAt(i, 0);
        lbl_id_client.setText(listeClient.getModel().getValueAt(i, 0).toString()); 
        lbl_nom_complet.setText( listeClient.getModel().getValueAt(i, 1).toString()+" "+ listeClient.getModel().getValueAt(i, 2).toString()); 
        lbl_adresse_client.setText( listeClient.getModel().getValueAt(i, 5).toString()); 
        lbl_email_client.setText( listeClient.getModel().getValueAt(i, 4).toString()); 
        lbl_tel_client.setText( listeClient.getModel().getValueAt(i, 3).toString()); 
        }
        
    }//GEN-LAST:event_listeClientMouseClicked

    private void radiobtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiobtn1ActionPerformed

        radiobtn2.setSelected(false);
        radiobtn1.setSelected(true);
        lbl_date_livraison.setEnabled(true);
        updateTableModel();
    }//GEN-LAST:event_radiobtn1ActionPerformed

    private void radiobtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiobtn2ActionPerformed
        JRadioButton selectedRadioButton = (JRadioButton) evt.getSource();
        radiobtn2.setSelected(true);
        radiobtn1.setSelected(false);
        updateTableModel();
        lbl_date_livraison.setEnabled(false);
    }//GEN-LAST:event_radiobtn2ActionPerformed

    public int insererCommande() throws SQLException {
        String query = "INSERT INTO commandes (dateCommande, refCommande, idClient, quantite, montant, devise, idCreateur, description, pj) VALUES (?, ?, ?, ?, ?, ?,(SELECT idUtilisateur from utilisateur where status = 1), ?, ?)";
        connectiondb();
        try {
            stm = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateCommande = now.format(formatter);
            stm.setString(1, dateCommande);
            stm.setString(2, "REF/34");// refCommande
            stm.setInt(3, Integer.parseInt(lbl_id_client.getText().trim()));
            stm.setInt(4, listeCommande1.getRowCount());
//            pstmt.setFloat(5, calculerMontantTotal() + ((Number) lbl_fraisLivraison.getValue()).floatValue());
            stm.setFloat(5, calculerMontantTotal());
            stm.setString(6, "Ariary");
            stm.setString(7, "description");
            stm.setString(8, "ffffff");

            // Exécute la requête d'insertion
            int rowsAffected = stm.executeUpdate();

            // Récupère l'ID généré
            if (rowsAffected == 1) {
                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    System.out.println("Création  de commande réussie, ID commande généré:"+ rs.getInt(1));
                    return rs.getInt(1);
                }
            }
        }catch(NumberFormatException | SQLException e){
        
        }
        // Si aucune commande n'a été insérée ou si l'ID généré n'a pas pu être récupéré, retourne -1
        System.out.println("Echec de la création  de commande.");
        return -1;
    }
    
    public void insererCommandeDetail(int idCommandeGenere){
    String commandeDetailQuery = "INSERT INTO commandeDetails (idCommande, idProduit, quantite, montant) VALUES (?, ?, ?, ?)";

try (PreparedStatement pstmtCommandeDetail = conn.prepareStatement(commandeDetailQuery)) {
    // Récupérer l'idCommande généré précédemment
    List<Integer> idComDetGenerated = new ArrayList<>();
    // Parcourir chaque ligne du tableau
    for (int i = 0; i < listeCommande1.getRowCount(); i++) {

        // Insérer les données dans la table commandeDetail
        pstmtCommandeDetail.setInt(1, idCommandeGenere);
        pstmtCommandeDetail.setInt(2, Integer.parseInt(listeCommande1.getValueAt(i, 0).toString()));
        pstmtCommandeDetail.setInt(3, Integer.parseInt(listeCommande1.getValueAt(i, 3).toString()));
        pstmtCommandeDetail.setFloat(4, Float.parseFloat(listeCommande1.getValueAt(i, 2).toString()));

        // Exécuter la requête d'insertion
        pstmtCommandeDetail.executeUpdate();
        ResultSet generatedKeys = pstmtCommandeDetail.getGeneratedKeys();
        if (generatedKeys.next()) {
            int idCommandeDetailGenere = generatedKeys.getInt(1);
            // Ajouter l'ID généré au tableau
            idComDetGenerated.add(idCommandeDetailGenere);
        }
    }
    
     idComDetArray = idComDetGenerated.toArray(new Integer[0]);
    
    // Faites quelque chose avec le tableau d'IDs générés
    System.out.println("IDs de commandeDetail générés : " + Arrays.toString(idComDetArray));
    System.out.println("Enregistrement de commandeDetail réussie");
} catch (SQLException e) {
    System.out.println("Echec de l'enregistrement de commandeDetail ");
    e.printStackTrace();
}

    }
    
    public int insererLivraison(int idCommande) throws SQLException {
        String query = "INSERT INTO livraisons (refLivraison, idCommande, idClient, montant, devise, description, frais) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?)";
float montantLivraison=0;
        DefaultTableModel model = (DefaultTableModel) listeCommande.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                montantLivraison+=Float.parseFloat(model.getValueAt(i, 3).toString());
            }
        
        try{
            stm = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1, "REF/45");
            stm.setInt(2, idCommande);
            stm.setInt(3, Integer.parseInt(lbl_id_client.getText().trim()));
            stm.setFloat(4, montantLivraison );
            stm.setString(5, "Ariary");
            stm.setString(6, "description");
            stm.setFloat(7, Float.parseFloat(lbl_frais_livraison.getValue().toString()) );

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Insertion de la livraison échouée, aucune ligne affectée.");
            }else{
            System.out.println("Enregistrement de livraison réussie");
            }

            // Récupérer l'ID généré
            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    System.out.println("ID livraison généré: "+generatedKeys.getInt(1));
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Échec de la récupération de l'ID généré pour la livraison.");
                }
            }
        }catch(Exception  e){
        return 0;
        }
        
    }
    
    public void insererLivraisonEtDetails(int idLivraison, Integer[] idComDetArray) throws SQLException {
    try {
        connectiondb();
        stm = conn.prepareStatement(insertLivraisonDetailsQuery);
        
        int i=0;
        for (Integer idCommandeDetail : idComDetArray) {
            
            DefaultTableModel model = (DefaultTableModel) listeCommande1.getModel();
            
            // Remplacer ces valeurs factices par les valeurs réelles de votre application
            stm.setInt(1, idCommandeDetail); // ID de la commande détail
            stm.setInt(2, Integer.parseInt(model.getValueAt(i, 0).toString())); // ID du produit, à remplacer par la valeur réelle
            stm.setInt(3, Integer.parseInt(model.getValueAt(i, 3).toString())); // Quantité reçue, à remplacer par la valeur réelle
            stm.setInt(4, Integer.parseInt(model.getValueAt(i, 3).toString())); // Quantité valide, à remplacer par la valeur réelle
            stm.setFloat(5,Float.parseFloat(model.getValueAt(i, 2).toString()));
            stm.setString(6, "Ariary"); // Devise, à remplacer par la valeur réelle
            stm.setInt(7, idLivraison); // ID de la livraison
            stm.setString(8, "livraison complet ou D2Taillé"); // Description, à remplacer par la valeur réelle
            
            //si le type livraison complet est activé
            if(radiobtn1.isSelected()){
            stm.setString(9, getDateTime (lbl_date_livraison));
            
            //si le type livraison détail est activé
            }else if(radiobtn2.isSelected()){
            stm.setString(9, model.getValueAt(i, 4).toString()); 
            }
            
            stm.executeUpdate();
            i++;
        }
        
    System.out.println("Enregistrement de LivraisonEtDetails réussie");
} catch (SQLException e) {
    System.out.println("Echec de l'enregistrement de LivraisonEtDetails ");
    }finally{
        stm.close();
        conn.close();
    }
}

    
    public int insererFacture(int idLivraison, int idClient) throws SQLException {
    String query = "INSERT INTO factures (dateFacture, refFacture, idLivraison, idClient, montant, devise, description) VALUES (?, ?, ?, ?, (SELECT SUM(ld.montant) + l.frais FROM   livraisonDetails ld INNER JOIN livraisons l ON ld.idLivraison = l.id WHERE  l.id = ?), ?, ?)";
    connectiondb();
    try {
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();//récuperer la data courante
        
        stm = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        stm.setString(1, now.format(dtFormat)); //dateFacture
        stm.setString(2, "REFAC/55");
        stm.setInt(3, idLivraison);
        stm.setInt(4, idClient);
        stm.setFloat(5, idLivraison);
        stm.setString(6, "Ariary");
        stm.setString(7, "facture");

        int rowsAffected = stm.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Insertion de la facture échouée, aucune ligne affectée.");
        }else{
            System.out.println("création de facture réussie");
        }

        // Récupérer l'ID généré
        try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Échec de la récupération de l'ID généré pour la facture.");
            }
        }
    }catch(Exception e){
        System.out.println("Echec de la création de facture");
    return 0;
    }
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
    
    public void refreshTable(){
        String[] column={"ID","Article","PU", "Qte Stock"};
        String[] data= new String[4];
        DefaultTableModel model= new DefaultTableModel(null,column);
        try{
        connectiondb();
//        stm =conn.prepareStatement("select * from entree_caisse");
//        resultSet = stm.executeQuery();
  Statement statement =(Statement)conn.createStatement();
       resultSet = statement.executeQuery(query);
       while(resultSet.next()){
           data[0]=resultSet.getString("idProduit");
//           data[1]=resultSet.getString("refProduit");
           data[1]=resultSet.getString("designation");
           data[2]=resultSet.getString("prix");//quantite_stock
           data[3]=resultSet.getString("quantite_stock");
           model.addRow(data);
       }
       //inserer le tableau dans le panel
      listeArticle.setModel(model);
      conn.close();
      }catch(SQLException e){
            
}
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_delete_invoice_item;
    private javaapp.component.ButtonRadius btn_modify_invoice_item;
    private javaapp.component.ButtonRadius btn_next;
    private javaapp.component.ButtonRadius btn_next1;
    private javaapp.component.ButtonRadius btn_previous;
    private javaapp.component.ButtonRadius btn_previous1;
    private javaapp.component.ButtonRadius btn_previous3;
    private javaapp.component.ButtonRadius btn_print;
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_refresh1;
    private javaapp.component.ButtonRadius btn_search;
    private javaapp.component.ButtonRadius btn_search1;
    private javaapp.component.ButtonRadius btn_start;
    private javax.swing.JTextField client_key_search;
    private javax.swing.JLabel designation_item;
    private javaapp.component.ButtonRadius enregistrer_commande;
    private javax.swing.JLabel fac_adresse_client;
    private javax.swing.JLabel fac_date_livraison;
    private javax.swing.JLabel fac_email_client;
    private javax.swing.JLabel fac_frais_livraison;
    private javax.swing.JLabel fac_id_client;
    private javax.swing.JLabel fac_nom_client;
    private javax.swing.JLabel fac_tel_client;
    private javax.swing.JLabel fac_type_livraison;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
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
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JLabel lbl_adresse_client;
    private com.toedter.calendar.JDateChooser lbl_date_livraison;
    private javax.swing.JLabel lbl_email_client;
    private javax.swing.JSpinner lbl_frais_livraison;
    private javax.swing.JLabel lbl_id_client;
    private javax.swing.JLabel lbl_nom_complet;
    private javax.swing.JLabel lbl_tel_client;
    private javaapp.component.Table listeArticle;
    private javaapp.component.Table listeClient;
    private javaapp.component.Table listeCommande;
    private javaapp.component.Table listeCommande1;
    private javax.swing.JLabel nombre_article;
    private javax.swing.JLabel nombre_article1;
    private javax.swing.JLayeredPane page1;
    private javax.swing.JLayeredPane page2;
    private javax.swing.JPanel page3;
    private javax.swing.JLayeredPane page4;
    private javax.swing.JLayeredPane page5;
    private javaapp.component.PanelBorderRound panelBorderRound5;
    private javax.swing.JPanel panel_modify_item;
    private javaapp.component.PanelBorderRound panelwarning;
    private javax.swing.JLabel price_item;
    private javax.swing.JTextField produitKeySearch;
    private javax.swing.JSpinner quantite_item;
    private javax.swing.JRadioButton radiobtn1;
    private javax.swing.JRadioButton radiobtn2;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JScrollPane scrollTable1;
    private javax.swing.JScrollPane scrollTableClient;
    private javaapp.component.ButtonRadius step1;
    private javaapp.component.ButtonRadius step2;
    private javaapp.component.ButtonRadius step3;
    private javaapp.component.ButtonRadius step4;
    private javax.swing.JPanel stepPanel;
    private javaapp.component.Table table2;
    private javax.swing.JLabel total_prix;
    // End of variables declaration//GEN-END:variables

    private void setListeClient() {
     String column[]= {"ID", "Nom", "Prénom", "Email 1", "Téléphone 1", "Adresse"};
        Object[] data = new Object[6];
        DefaultTableModel model = new DefaultTableModel(null,column);
        try{
            connectiondb();
            Statement statement =(Statement)conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM clients");
        
           while(resultSet.next()){
                data[0]=resultSet.getString("id");
                data[1]=resultSet.getString("nom");
                data[2]=resultSet.getString("prenom");
                data[3]=resultSet.getString("email1");
                data[4]=resultSet.getString("tel1");
                data[5]=resultSet.getString("adresse");
                model.addRow(data);
       }
       listeClient.setModel(model);
        // Fermeture des ressources JDBC
        resultSet.close();
        statement.close();
        conn.close();
    } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
