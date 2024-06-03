/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.page.vente;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaap.modal.PaiementFacture;
import javaapp.bean.LivraisonDetail;
import javaapp.bean.Profile;
import javaapp.component.DateConverter;
import javaapp.component.FormatUtils;
import javaapp.component.ModalActionEvent;
import javaapp.PrinterPDF.PdfReceiptGenerator;
import javaapp.component.Toast;
import javaapp.dao.CommandeCDAO;
import javaapp.dao.FactureDAO;
import javaapp.dao.LivraisonDetailDAO;
import javaapp.dao.PaiementDAO;
import javaapp.factory.DAOFactory;
import javaapp.pagination.EventPagination;
import javaapp.pagination.PaginationItemRenderStyle1;
import javaapp.swing.TableStatusCellRender;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ZOENIRINA
 */
public class Facture extends javax.swing.JPanel {
private final int borderRadius = 10;   
    public String query=null;
    public String title="Autres";
    private String idSelected;
    public String btn_save_state= "insert";
    public JPanel pan;
    public Toast t;
    int pageCount=1;
    private int activeMenu=1;
    private final PdfReceiptGenerator pdfPrinter;
    private final FormatUtils formater;
    private javaapp.bean.CommandeC commandeSelected = null;
    private javaapp.bean.Livraison livraisonSelected = null;
    private javaapp.bean.Facture facSelected = null;
    private final ModalActionEvent event;
    private final FactureDAO factureDAO;
    private final PaiementDAO paieDAO;
    private final CommandeCDAO commmandeDAO;
    private final LivraisonDetailDAO livraisonDetDAO;

    public Facture(JPanel panel) {
        initComponents();
        factureDAO = DAOFactory.getFactureDAO();
        paieDAO = DAOFactory.getPaiementDAO();
        commmandeDAO = DAOFactory.getCommandeDAO();
        livraisonDetDAO = DAOFactory.getLivraisonDetailDAO();
        
        pan= panel;
        formater= new FormatUtils();
        refreshTable(factureDAO.select());
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
        tableOutput.getColumnModel().getColumn(6).setCellRenderer(new TableStatusCellRender());
         event =  new ModalActionEvent(){
            @Override
            public void onEdit(int row){
//                btn_creer_facture.setVisible(false);
                t = new Toast("Paiement de la facture réussie!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
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
        panelBorderRound3 = new javaapp.component.PanelBorderRound();
        nombre_ligne = new javax.swing.JLabel();
        page2 = new javax.swing.JPanel();
        btn_back = new javaapp.component.ButtonRadius();
        jPanel15 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
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
        jLabel57 = new javax.swing.JLabel();
        dateLivraison = new com.toedter.calendar.JDateChooser();
        heureLivraison = new javax.swing.JComboBox<>();
        minuteLivraison = new javax.swing.JComboBox<>();
        nomClient = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        idCommande = new javax.swing.JLabel();
        btn_modifier = new javaapp.component.ButtonRadius();
        jPanel7 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        btn_modif_fac = new javaapp.component.ButtonRadius();
        dateFacture = new com.toedter.calendar.JDateChooser();
        jPanel11 = new javax.swing.JPanel();
        heureFacture = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        refFactureField = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel16 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        idFacture = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableListCommande = new javaapp.component.Table();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        totalHTField = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        totalTVA = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        totalTTCField = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        totalNetAPayerField = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        totalRemiseField = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        frais = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        btn_modifier2 = new javaapp.component.ButtonRadius();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 1700));

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
        page1.add(btn_refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 140, 40, 40));

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

        page1.add(scrollTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 190, 950, 610));

        menu3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu3.setForeground(new java.awt.Color(153, 153, 153));
        menu3.setText("Payé au partiel");
        menu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu3MouseClicked(evt);
            }
        });
        page1.add(menu3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, -1, -1));

        menu1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu1.setForeground(new java.awt.Color(51, 51, 51));
        menu1.setText("Tous");
        menu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu1MouseClicked(evt);
            }
        });
        page1.add(menu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));
        page1.add(refCommandeKeySearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 260, 37));

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
        page1.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 140, 40, 40));

        menu2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu2.setForeground(new java.awt.Color(153, 153, 153));
        menu2.setText("Payé");
        menu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu2MouseClicked(evt);
            }
        });
        page1.add(menu2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, -1, -1));

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

        page1.add(linegroup, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 440, 3));

        facture_non_paye.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        facture_non_paye.setForeground(new java.awt.Color(51, 51, 51));
        facture_non_paye.setText("(2) Facture non payé");
        page1.add(facture_non_paye, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 240, -1));
        page1.add(nomClientKeySearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 140, 270, 37));

        menu5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu5.setForeground(new java.awt.Color(51, 51, 51));
        menu5.setText("Nom ou prénom du client");
        page1.add(menu5, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 120, -1, -1));

        menu6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu6.setForeground(new java.awt.Color(51, 51, 51));
        menu6.setText("Réference de la commande");
        page1.add(menu6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, -1, -1));

        menu4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu4.setForeground(new java.awt.Color(153, 153, 153));
        menu4.setText("Non payé");
        menu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu4MouseClicked(evt);
            }
        });
        page1.add(menu4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, -1, -1));

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

        page1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 810, 950, -1));

        panelBorderRound3.setBackground(new java.awt.Color(227, 227, 255));

        nombre_ligne.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nombre_ligne.setForeground(new java.awt.Color(102, 102, 255));
        nombre_ligne.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombre_ligne.setText("Documents (5)");

        javax.swing.GroupLayout panelBorderRound3Layout = new javax.swing.GroupLayout(panelBorderRound3);
        panelBorderRound3.setLayout(panelBorderRound3Layout);
        panelBorderRound3Layout.setHorizontalGroup(
            panelBorderRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nombre_ligne, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBorderRound3Layout.setVerticalGroup(
            panelBorderRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nombre_ligne, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        page1.add(panelBorderRound3, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 40, 160, 40));

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

        jPanel15.setBackground(new java.awt.Color(153, 153, 255));
        jPanel15.setPreferredSize(new java.awt.Dimension(440, 4));

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Information général");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(241, 248, 240));

        jLabel40.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(43, 40, 54));
        jLabel40.setText("Date de commande");

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
        vendeur.setForeground(new java.awt.Color(43, 40, 54));

        refCommandeField.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        refCommandeField.setForeground(new java.awt.Color(43, 40, 54));

        jLabel49.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(43, 40, 54));
        jLabel49.setText("Réference commande");

        id_client.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        id_client.setForeground(new java.awt.Color(43, 40, 54));

        jLabel57.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(43, 40, 54));
        jLabel57.setText("Date prévue de livraison");

        dateLivraison.setBackground(new java.awt.Color(241, 248, 240));
        dateLivraison.setForeground(new java.awt.Color(43, 40, 54));
        dateLivraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        heureLivraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        heureLivraison.setForeground(new java.awt.Color(43, 40, 54));

        minuteLivraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        minuteLivraison.setForeground(new java.awt.Color(43, 40, 54));

        nomClient.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nomClient.setForeground(new java.awt.Color(43, 40, 54));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btn_modif_comm, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel57)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(78, 78, 78)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(refCommandeField)
                                        .addComponent(vendeur)
                                        .addComponent(nomClient)
                                        .addComponent(dateCommande, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(228, 228, 228)
                                    .addComponent(dateLivraison, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGap(14, 14, 14)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(heureLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(minuteLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jLabel49)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(id_client, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel48))
                .addGap(29, 29, 29))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel57)))
                        .addGap(59, 59, 59)
                        .addComponent(btn_modif_comm, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(heureLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(minuteLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

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

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Facture n°");

        idCommande.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        idCommande.setForeground(new java.awt.Color(51, 51, 51));

        btn_modifier.setBackground(new java.awt.Color(66, 230, 119));
        btn_modifier.setBorder(null);
        btn_modifier.setForeground(new java.awt.Color(255, 255, 255));
        btn_modifier.setText("Effectuer un paiement");
        btn_modifier.setBorderColor(new java.awt.Color(66, 230, 119));
        btn_modifier.setColor(new java.awt.Color(66, 230, 119));
        btn_modifier.setColorClick(new java.awt.Color(66, 230, 119));
        btn_modifier.setColorOver(new java.awt.Color(66, 230, 119));
        btn_modifier.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_modifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modifierActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(241, 248, 240));

        jLabel56.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(43, 40, 54));
        jLabel56.setText("Date de facturation");

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
            .addComponent(heureFacture, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        jLabel46.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(43, 40, 54));
        jLabel46.setText("Reference");

        refFactureField.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        refFactureField.setForeground(new java.awt.Color(43, 40, 54));

        jLabel58.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(43, 40, 54));
        jLabel58.setText("Adresse de facturation");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(43, 40, 54));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jScrollPane4.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_modif_fac, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46)
                            .addComponent(jLabel56)
                            .addComponent(jLabel58))
                        .addGap(86, 86, 86)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dateFacture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(refFactureField))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refFactureField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateFacture, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel56)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel58))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btn_modif_fac, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel16.setBackground(new java.awt.Color(153, 153, 255));
        jPanel16.setPreferredSize(new java.awt.Dimension(440, 4));

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Facture n°");

        idFacture.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        idFacture.setForeground(new java.awt.Color(204, 255, 255));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel10)
                .addGap(9, 9, 9)
                .addComponent(idFacture, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addComponent(idFacture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        jLabel7.setText("Ligne de la facture");

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

        jPanel13.setBackground(new java.awt.Color(241, 248, 240));

        jPanel14.setBackground(new java.awt.Color(153, 153, 255));
        jPanel14.setPreferredSize(new java.awt.Dimension(440, 4));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 474, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(43, 40, 54));
        jLabel11.setText("Total HT");

        totalHTField.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalHTField.setForeground(new java.awt.Color(43, 40, 54));
        totalHTField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalHTField.setText("0");

        totalTVA.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalTVA.setForeground(new java.awt.Color(43, 40, 54));
        totalTVA.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalTVA.setText("0");

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(43, 40, 54));
        jLabel12.setText("TVA");

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(43, 40, 54));
        jLabel13.setText("Total TTC");

        totalTTCField.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalTTCField.setForeground(new java.awt.Color(43, 40, 54));
        totalTTCField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalTTCField.setText("0");

        totalNetAPayerField.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalNetAPayerField.setForeground(new java.awt.Color(102, 102, 255));
        totalNetAPayerField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalNetAPayerField.setText("0");

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 255));
        jLabel14.setText("Total net à payer");

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

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalHTField, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addComponent(jSeparator5)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalTVA, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalTTCField, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                .addComponent(totalNetAPayerField, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalRemiseField, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(frais, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(totalHTField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(totalTVA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(totalRemiseField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(totalTTCField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(frais))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(totalNetAPayerField))
                .addGap(16, 16, 16))
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 230, Short.MAX_VALUE)))
        );

        btn_modifier2.setBackground(new java.awt.Color(241, 248, 240));
        btn_modifier2.setBorder(null);
        btn_modifier2.setForeground(new java.awt.Color(43, 40, 54));
        btn_modifier2.setText("Imprimer");
        btn_modifier2.setBorderColor(new java.awt.Color(241, 248, 240));
        btn_modifier2.setColor(new java.awt.Color(241, 248, 240));
        btn_modifier2.setColorClick(new java.awt.Color(241, 248, 240));
        btn_modifier2.setColorOver(new java.awt.Color(241, 248, 240));
        btn_modifier2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_modifier2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modifier2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout page2Layout = new javax.swing.GroupLayout(page2);
        page2.setLayout(page2Layout);
        page2Layout.setHorizontalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, page2Layout.createSequentialGroup()
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(page2Layout.createSequentialGroup()
                                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(824, 824, 824))
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(47, 47, 47))
            .addGroup(page2Layout.createSequentialGroup()
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addComponent(btn_modifier, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_modifier2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(page2Layout.createSequentialGroup()
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(page2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        page2Layout.setVerticalGroup(
            page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(page2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(idCommande, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(page2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_modifier2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_modifier, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void tableOutputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOutputMouseClicked
       switchToPage(2);
       String refCommande = null;
        int selectedRow = tableOutput.getSelectedRow();
        String refFacture = (String) tableOutput.getModel().getValueAt(selectedRow, 0);
        facSelected = factureDAO.find(refFacture);
        commandeSelected = commmandeDAO.find(facSelected.getLivraison().getIdCommande());
        livraisonSelected = facSelected.getLivraison();
    if (selectedRow != -1) {

        if( paieDAO.getMontantVerse(facSelected.getId())  <  facSelected.getMontant()){
            btn_modifier.setVisible(true);
        }else {
            btn_modifier.setVisible(false);
        }

        List <LivraisonDetail> livDets = livraisonDetDAO.select(facSelected.getIdLivraison());
        String[] column = {"Article","Réf","PU HT","TVA", "Remise", "Qté commandé", "Qté colissé", "Total HT"};
            DefaultTableModel model = new DefaultTableModel(null, column);
            
           for(javaapp.bean.LivraisonDetail livDet : livDets){
            Object[] data = new Object[8];
            
            
            
            float tva = livDet.getProduit().getTVA();
            int qte = livDet.getCommandeDetail().getQuantite();
            float PUHT = livDet.getProduit().getPUHT();
            float PUTTC = PUHT + (PUHT*tva)/100;
            
            data[0] = livDet.getProduit().getDesignation();
            data[1] = livDet.getProduit().getRefProduit();
            data[2] = formater.formatFloat(PUHT); 
            data[3] = tva;
            data[4] = livDet.getCommandeDetail().getRemise();
            data[5] = qte;
            data[6] = livDet.getQuantiteRecu();
            data[7] = formater.formatFloat(PUHT*livDet.getQuantiteRecu());
            
            model.addRow(data);
           }
           
        tableListCommande.setModel(model);
        updateTotals();
        
        
       refCommande = (String) tableOutput.getModel().getValueAt(selectedRow, 2);
       refFacture = (String) tableOutput.getModel().getValueAt(selectedRow, 0);
           try {
               // Step 3: Retrieve Client Information
                retrieveAndDisplayCommandeInformation(refCommande, refFacture);
           } catch (ParseException ex) {
               Logger.getLogger(Facture.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
    }//GEN-LAST:event_tableOutputMouseClicked

    private void filterData(int index){
        setActiveMenu(index);
//        query= getActiveQuery();
        displaydata(" ");
        changeActiveButton();
        
    }
    
    private void displaydata(String query){
            switch(activeMenu){
            case 1:
                refreshTable(factureDAO.select(query));
            break;
            case 2:
                refreshTable(factureDAO.selectPaye(query));
            break;
            case 3:
                refreshTable(factureDAO.selectPayePartiellement(query));
            break;
            case 4:
                refreshTable(factureDAO.selectNonPaye(query));
            break;
        }
    }
        public void setActiveMenu(int activeMenu) {
        this.activeMenu = activeMenu;
    }
        

  
        private void retrieveAndDisplayCommandeInformation(String refCommande, String refFacture) throws ParseException {
    
        // Step 3: Retrieve Client Information
//            javaapp.bean.Facture facture = factureDAO.find(refFacture);
//            javaapp.bean.CommandeC comm = commmandeDAO.find(refCommande);
            javaapp.bean.Paiement paie = paieDAO.findByFacture(facSelected.getId());
            
            
            Profile profile = DAOFactory.getProfileDAO().find(commandeSelected.getIdCreateur());
            vendeur.setText(profile.getNom() +" "+ profile.getPrenom());//decomm
            heureCommande.setText(DateConverter.convertToHour(commandeSelected.getDateCommande()));
            nomClient.setText(commandeSelected.getClient().getNom()+" "+commandeSelected.getClient().getPrenom());
            idCommande.setText( String.valueOf(commandeSelected.getId())); 
            idFacture.setText( String.valueOf(facSelected.getId()) ); 
            refFactureField.setText(facSelected.getRefFacture()); 
            dateFacture.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(facSelected.getDateFacture()));
            heureFacture.setText(DateConverter.convertToHour(facSelected.getDateFacture()));
            dateCommande.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(commandeSelected.getDateCommande())); 
            refCommandeField.setText( commandeSelected.getRefCommande() ); 
            frais.setText(String.valueOf(facSelected.getLivraison().getFrais()));
            if(facSelected.getLivraison() != null){
//            fac_frais.setValue( facture.getLivraison().getFrais()); 
//            charge.setText(formater.formatFloat(facture.getLivraison().getFrais())); 
            
            }
            try {
                DateConverter.initializeDate(dateLivraison, heureLivraison, minuteLivraison, livraisonSelected.getDateLivraison());

            } catch (ParseException ex) {
                Logger.getLogger(Commande.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
query =  " WHERE ";
    String FILTER_CLIENT="  ( cl.nom like '%"+nomClientKeySearch.getText()+"%' OR cl.prenom like '%"+nomClientKeySearch.getText()+"%') ";
    if(refCommandeKeySearch.getText().isEmpty() && !nomClientKeySearch.getText().trim().isEmpty()){
        query+=FILTER_CLIENT;}
    
    displaydata(query);
    
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        displaydata(" ");
        refCommandeKeySearch.setText("");
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

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        refreshTable(factureDAO.select());
        switchToPage(1);
    }//GEN-LAST:event_btn_backActionPerformed

    private void menu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu4MouseClicked
        filterData(4);
    }//GEN-LAST:event_menu4MouseClicked

    private void tableInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInputMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableInputMouseClicked

    private void btn_modif_commActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modif_commActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir sauvegarder les modification sur la commande n°"+livraisonSelected.getId()+" ?","Confirmation de mise à jour",JOptionPane.YES_NO_OPTION) == 0){
//            CommandeCDAO commDAO = DAOFactory.getCommandeDAO();
            javaapp.bean.CommandeC comm = commandeSelected;
            comm.setRefCommande(refCommandeField.getText());
            if(commmandeDAO.update(comm)){
                t = new Toast("Modification enregistrée!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
            }
//            JOptionPane.showMessageDialog(null, "Commande validée!","Confirmation",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_modif_commActionPerformed

    private void btn_modifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modifierActionPerformed
        PaiementFacture p = new PaiementFacture(facSelected.getId());
        p.setVisible(true);
        p.initEvent(event, 1);
    }//GEN-LAST:event_btn_modifierActionPerformed

    private void afficherBoutton(boolean a){
        btn_modif_comm.setVisible(a);
        btn_modif_fac.setVisible(a);
    }
    
    private void btn_modif_facActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modif_facActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir valider cette commande?","Confirmation validation",JOptionPane.YES_NO_OPTION) == 0){
            javaapp.bean.Facture fac = facSelected;
            fac.setRefFacture(refFactureField.getText());
            fac.setAdresse(refFactureField.getText());
            if(factureDAO.update(fac)){
                t = new Toast("Création de la facture réussie!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
            }
            JOptionPane.showMessageDialog(null, "Commande validée!","Confirmation",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_modif_facActionPerformed

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
    totalNetAPayerField.setText(formater.formatFloat(total_TTC - total_Remise));
    
    }
    
    private void btn_modifier2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modifier2ActionPerformed
    if(JOptionPane.showConfirmDialog(null, "Voulez-vous impimer la facture n°"+facSelected.getRefFacture()+" ?","Message",JOptionPane.YES_NO_OPTION) == 0){
        PdfReceiptGenerator p = new PdfReceiptGenerator();
        p.printPDF(facSelected, commandeSelected, livraisonSelected);
    }
    }//GEN-LAST:event_btn_modifier2ActionPerformed

    public void refreshTable(List<javaapp.bean.Facture> factures){
    String[] column = {"Référence", "Date facturation","Client","Bon de livraison", "Montant à payer", "Reste à payer", "Status"};
    Object[] data = new Object[7];
    DefaultTableModel model = new DefaultTableModel(null, column);
        if(factures != null){
            for(javaapp.bean.Facture facture : factures){
            data[0] = facture.getRefFacture();
            data[1] = DateConverter.convertToLetter(facture.getDateFacture());
            data[2] = facture.getPersonne().getNom()+" "+facture.getPersonne().getPrenom();
            data[3] = (facture.getLivraison() == null) ? "" : facture.getLivraison().getRefLivraison();
            data[4] = formater.formatFloat(facture.getMontant());
//            data[4] = formater.formatFloat((facture.getLivraison() == null) ? facture.getMontant() : paieDAO.findByFacture(facture.getId()).getRestePaye() );
float reste = facture.getMontant() - paieDAO.getMontantVerse(facture.getId());
            data[5] = (reste > 0)? reste : 0;            
//data[5] =  facture.getMontant() - paieDAO.getMontantVerse(facture.getId()) ;            
            data[6] = facture.getStatus();
            model.addRow(data);
        }
        
        tableInput.setModel(model);
        nombre_ligne.setText("Factures ("+tableInput.getRowCount()+")");//getCountAPayer
        facture_non_paye.setText("("+factureDAO.getCountAPayer()+ ") Factures à payer");
        displayRows(0,13);
        tableOutput.getColumnModel().getColumn(6).setCellRenderer(new TableStatusCellRender());
        }
}

    public void setEmptyForm(){
        btn_save_state="insert";
    }        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_back;
    private javaapp.component.ButtonRadius btn_modif_comm;
    private javaapp.component.ButtonRadius btn_modif_fac;
    private javaapp.component.ButtonRadius btn_modifier;
    private javaapp.component.ButtonRadius btn_modifier2;
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_search;
    private com.toedter.calendar.JDateChooser dateCommande;
    private com.toedter.calendar.JDateChooser dateFacture;
    private com.toedter.calendar.JDateChooser dateLivraison;
    private javax.swing.JLabel facture_non_paye;
    private javax.swing.JLabel frais;
    private javax.swing.JLabel heureCommande;
    private javax.swing.JLabel heureFacture;
    private javax.swing.JComboBox<String> heureLivraison;
    private javax.swing.JLabel idCommande;
    private javax.swing.JLabel idFacture;
    private javax.swing.JLabel id_client;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextArea jTextArea1;
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
    private javax.swing.JComboBox<String> minuteLivraison;
    private javax.swing.JTextField nomClient;
    private javax.swing.JTextField nomClientKeySearch;
    private javax.swing.JLabel nombre_ligne;
    private javax.swing.JPanel page1;
    private javax.swing.JPanel page2;
    private javaapp.pagination.Pagination pagination1;
    private javaapp.component.PanelBorderRound panelBorderRound3;
    private javax.swing.JTextField refCommandeField;
    private javax.swing.JTextField refCommandeKeySearch;
    private javax.swing.JTextField refFactureField;
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
