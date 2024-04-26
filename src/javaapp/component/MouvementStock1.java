
package javaapp.component;

import javaapp.page.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.component.Toast;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class MouvementStock1 extends javax.swing.JPanel {
private final int borderRadius = 10;
 Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
    String FILTER_QUERY="select p.id as idProduit,p.designation,(COALENSCE(entree.quantite_entree,0)-COALENSCE(sortie.quantite_sortie, 0)) as quantite_stock, u.poids, u.package, p.package, p.prix from produit p "
            + "JOIN unite_produit u ON p.idUnite = u.id "
            + "LEFT JOIN (SELECT idProduit, SUM(quantite) AS quantite_entree from mouvementStock where sens= 'entree' group by idProduit) entree ON p.idProduit= entree.idProduit LEFT JOIN (SELECT idProduit, SUM(quantite) AS quantite_sortie from mouvementStock where sens='sortie' group by produt) sortie ON p.idProduit = sortie.idProduit  WHERE p.idProduit=?";
//    String SELECT_ALL="SELECT ms.id, ms.description, ms.idProduit, ms.quantite, ms.dateMouvement, ms.idTypeMouvement, ms.sens, ms.idMagasin, ms.idExercice, ms.idCreateur, " +
//               "p.designation AS nomProduit, tm.type AS typeMouvement FROM mouvementStock ms INNER JOIN produits p ON ms.idProduit = p.id " +
//               "LEFT JOIN type_mouvement tm ON ms.idTypeMouvement = tm.code_type " +
//               "WHERE ms.idExercice = (select idExercice FROM exercice where status= 1) ORDER BY ms.dateMouvement";
    String SELECT_ALL="SELECT ms.id, ms.quantite, ms.dateMouvement, ms.idMagasin,p.refProduit,p.designation AS nomProduit FROM mouvementStock ms INNER JOIN produits p ON ms.idProduit = p.id  WHERE ms.idExercice = (select idExercice FROM EXERCICE where status= 1) and  ms.sens='Entrée' ORDER BY ms.dateMouvement desc";
    public String query=null;
    public String title="Autres";
    public PreparedStatement stm ;
    public ResultSet resultSet;
    public String idSelected;
    private String btn_save_state= "insert";
//    private final int pointX;
//    private final int pointY;
    public String datetime1;
    public LocalTime timepart;
    public JPanel pan;
    public Toast t;

    public MouvementStock1(JPanel panel) {
        initComponents();
        setInputSelect();
        pan= panel;
        query=SELECT_ALL; // TOUTES les sortie du caisse dont l'exercice est actif et ordonnée solon la date
        refreshTable();
        rowcount();
        scrollTable.getViewport().setBackground(Color.white);
        btn_save_state= "insert";
        btn_delete.setVisible(false);
        afficherExerciceActif();
        dateCreation.setEnabled(false);
        containerForm.setVisible(false);
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

        containerForm = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        nature4 = new javax.swing.JLabel();
        dateCreation = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        nature = new javax.swing.JLabel();
        typeMouvement = new javax.swing.JComboBox<>();
        quantite = new javax.swing.JSpinner();
        sens = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        description = new javax.swing.JTextField();
        nature5 = new javax.swing.JLabel();
        nature2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        idMagasin = new javax.swing.JComboBox<>();
        btn_delete = new javaapp.component.ButtonRadius();
        idExerciceActif = new javax.swing.JLabel();
        nature3 = new javax.swing.JLabel();
        nature1 = new javax.swing.JLabel();
        code_produit = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        btn_save = new javaapp.component.ButtonRadius();
        btn_cancel = new javaapp.component.ButtonRadius();
        nomCreateur = new javax.swing.JComboBox<>();
        btn_back = new javaapp.component.ButtonRadius();
        containerList = new javax.swing.JLayeredPane();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        scrollTable = new javax.swing.JScrollPane();
        table = new javaapp.component.Table();
        nombre_ligne = new javax.swing.JLabel();
        idMagasinKey = new javax.swing.JComboBox<>();
        btn_search = new javaapp.component.ButtonRadius();
        date1 = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        date2 = new com.toedter.calendar.JDateChooser();
        btnnewuser = new javaapp.component.ButtonRadius();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        btn_refresh = new javaapp.component.ButtonRadius();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("(Si nature non spécifié)");

        nature4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature4.setForeground(new java.awt.Color(51, 51, 51));
        nature4.setText("Mouvement");

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("Produit");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Quantité");

        nature.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature.setForeground(new java.awt.Color(51, 51, 51));
        nature.setText("Nature");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Exercice N°");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Acteur");

        description.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        description.setForeground(new java.awt.Color(102, 102, 102));

        nature5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature5.setForeground(new java.awt.Color(51, 51, 51));
        nature5.setText("Date");

        nature2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature2.setForeground(new java.awt.Color(51, 51, 51));
        nature2.setText("Magasin");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Créateur");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Code produit");

        btn_delete.setBackground(new java.awt.Color(255, 255, 255));
        btn_delete.setBorder(null);
        btn_delete.setForeground(new java.awt.Color(248, 91, 50));
        btn_delete.setText("Supprimer");
        btn_delete.setBorderColor(new java.awt.Color(248, 91, 50));
        btn_delete.setColor(new java.awt.Color(255, 255, 255));
        btn_delete.setColorClick(new java.awt.Color(255, 255, 255));
        btn_delete.setColorOver(new java.awt.Color(255, 248, 246));
        btn_delete.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        idExerciceActif.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        idExerciceActif.setForeground(new java.awt.Color(248, 91, 50));
        idExerciceActif.setText("00012");

        nature3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature3.setForeground(new java.awt.Color(51, 51, 51));
        nature3.setText("Sens");

        nature1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature1.setForeground(new java.awt.Color(51, 51, 51));
        nature1.setText("Description");

        jLabel8.setBackground(new java.awt.Color(255, 102, 0));
        jLabel8.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(36, 36, 36));
        jLabel8.setText("Mouvement de stock");

        btn_save.setBackground(new java.awt.Color(248, 91, 50));
        btn_save.setBorder(null);
        btn_save.setForeground(new java.awt.Color(255, 255, 255));
        btn_save.setText("Enregistrer");
        btn_save.setBorderColor(new java.awt.Color(248, 91, 50));
        btn_save.setColor(new java.awt.Color(248, 91, 50));
        btn_save.setColorClick(new java.awt.Color(248, 91, 50));
        btn_save.setColorOver(new java.awt.Color(255, 51, 0));
        btn_save.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_cancel.setBackground(new java.awt.Color(240, 236, 243));
        btn_cancel.setBorder(null);
        btn_cancel.setForeground(new java.awt.Color(51, 51, 51));
        btn_cancel.setText("Annuler");
        btn_cancel.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_cancel.setColor(new java.awt.Color(240, 236, 243));
        btn_cancel.setColorClick(new java.awt.Color(240, 240, 240));
        btn_cancel.setColorOver(new java.awt.Color(217, 217, 217));
        btn_cancel.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        nomCreateur.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nomCreateur.setForeground(new java.awt.Color(102, 102, 102));
        nomCreateur.setMinimumSize(new java.awt.Dimension(64, 40));
        nomCreateur.setPreferredSize(new java.awt.Dimension(64, 40));

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

        containerForm.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(dateCreation, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(typeMouvement, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(quantite, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(sens, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(description, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(idMagasin, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(btn_delete, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(idExerciceActif, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(code_produit, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(btn_save, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(btn_cancel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nomCreateur, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(btn_back, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout containerFormLayout = new javax.swing.GroupLayout(containerForm);
        containerForm.setLayout(containerFormLayout);
        containerFormLayout.setHorizontalGroup(
            containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerFormLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(containerFormLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(51, 51, 51)
                        .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)))
                    .addComponent(jLabel3)
                    .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, containerFormLayout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(idExerciceActif, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, containerFormLayout.createSequentialGroup()
                            .addGap(103, 103, 103)
                            .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(nomCreateur, 0, 820, Short.MAX_VALUE)
                                .addComponent(idMagasin, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nature2)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(quantite, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(code_produit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(containerFormLayout.createSequentialGroup()
                            .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(containerFormLayout.createSequentialGroup()
                            .addComponent(nature4)
                            .addGap(18, 18, 18)
                            .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(nature3)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerFormLayout.createSequentialGroup()
                                    .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nature)
                                        .addComponent(typeMouvement, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                                    .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(containerFormLayout.createSequentialGroup()
                                            .addComponent(nature1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel1))
                                        .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(sens, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nature5)
                                .addComponent(dateCreation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(containerFormLayout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        containerFormLayout.setVerticalGroup(
            containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerFormLayout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idExerciceActif, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomCreateur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nature2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idMagasin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel14))
                .addGap(3, 3, 3)
                .addComponent(code_produit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(3, 3, 3)
                .addComponent(quantite, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nature4)
                    .addComponent(nature3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sens, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(containerFormLayout.createSequentialGroup()
                        .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nature)
                            .addComponent(nature1)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(typeMouvement, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(containerFormLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nature5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateCreation, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Filtre par date:");

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("à");

        scrollTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        table.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        table.setForeground(new java.awt.Color(255, 255, 255));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        scrollTable.setViewportView(table);

        nombre_ligne.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nombre_ligne.setForeground(new java.awt.Color(102, 102, 102));
        nombre_ligne.setText("Tous (5)");

        btn_search.setBackground(new java.awt.Color(240, 236, 243));
        btn_search.setBorder(null);
        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/search.png"))); // NOI18N
        btn_search.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_search.setColor(new java.awt.Color(240, 236, 243));
        btn_search.setColorClick(new java.awt.Color(255, 255, 255));
        btn_search.setColorOver(new java.awt.Color(232, 230, 230));
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Identifiant du magasin");

        btnnewuser.setBackground(new java.awt.Color(248, 91, 50));
        btnnewuser.setBorder(null);
        btnnewuser.setForeground(new java.awt.Color(255, 255, 255));
        btnnewuser.setText("Ajouter");
        btnnewuser.setBorderColor(new java.awt.Color(248, 91, 50));
        btnnewuser.setColor(new java.awt.Color(248, 91, 50));
        btnnewuser.setColorClick(new java.awt.Color(248, 91, 50));
        btnnewuser.setColorOver(new java.awt.Color(255, 105, 82));
        btnnewuser.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnnewuser.setRadius(18);
        btnnewuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewuserActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 0, 102));

        jLabel4.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(36, 36, 36));
        jLabel4.setText("Entrée de stock");

        btn_refresh.setBackground(new java.awt.Color(240, 236, 243));
        btn_refresh.setBorder(null);
        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/refresh.png"))); // NOI18N
        btn_refresh.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_refresh.setColor(new java.awt.Color(240, 236, 243));
        btn_refresh.setColorClick(new java.awt.Color(255, 255, 255));
        btn_refresh.setColorOver(new java.awt.Color(232, 230, 230));
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        containerList.setLayer(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(scrollTable, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(nombre_ligne, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(idMagasinKey, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(btn_search, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(date1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(date2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(btnnewuser, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(jSeparator1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(btn_refresh, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout containerListLayout = new javax.swing.GroupLayout(containerList);
        containerList.setLayout(containerListLayout);
        containerListLayout.setHorizontalGroup(
            containerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(containerListLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnnewuser, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
            .addGroup(containerListLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(containerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollTable)
                    .addComponent(nombre_ligne)
                    .addGroup(containerListLayout.createSequentialGroup()
                        .addGroup(containerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(idMagasinKey, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(containerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(containerListLayout.createSequentialGroup()
                                .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(date2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        containerListLayout.setVerticalGroup(
            containerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerListLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(containerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(btnnewuser, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(containerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(containerListLayout.createSequentialGroup()
                        .addComponent(nombre_ligne)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idMagasinKey, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(containerListLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(containerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(date2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerListLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(11, 11, 11))
                            .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(8, 8, 8)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(containerList)
            .addComponent(containerForm, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(containerForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(containerList)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
//        JOptionPane.showMessageDialog(null, (String) dateFormat.format(date1.getDate()) + (String) dateFormat.format(date2.getDate()), "Sucess", JOptionPane.INFORMATION_MESSAGE);
        query="SELECT ms.id, ms.quantite, ms.dateMouvement, ms.idMagasin,p.refProduit,p.designation AS nomProduit FROM mouvementStock ms INNER JOIN produits p ON ms.idProduit = p.id  WHERE ms.idExercice = (select idExercice FROM EXERCICE where status= 1) and  ms.sens='Entrée' ";        
        if(idMagasinKey.getSelectedItem() != "Tous" && date1.getDate() != null && date2.getDate() != null ){
            query += " AND ms.idMagasin="+idMagasinKey.getSelectedItem()+"  AND ms.dateMouvement between '"+(String) dateFormat.format(date1.getDate())+"' AND '"+(String) dateFormat.format(date2.getDate())+"'  ORDER BY ms.dateMouvement desc";
        }else if(idMagasinKey.getSelectedItem() != "Tous" && date1.getDate() == null && date2.getDate() == null ){
            query += " AND ms.idMagasin="+idMagasinKey.getSelectedItem()+" ORDER BY ms.dateMouvement desc";
        }else if(idMagasinKey.getSelectedItem() == "Tous" && date1.getDate() != null && date2.getDate() != null ){
            query += "  AND ms.dateMouvement between '"+(String) dateFormat.format(date1.getDate())+"' AND '"+(String) dateFormat.format(date2.getDate())+"'  ORDER BY ms.dateMouvement desc";
        }else if(idMagasinKey.getSelectedItem() == "Tous" && date1.getDate() != null && date2.getDate() == null ){
            query += " AND ms.dateMouvement like '"+(String) dateFormat.format(date1.getDate())+"' ORDER BY ms.dateMouvement desc";
        }else{
            JOptionPane.showMessageDialog(null, "Veuiller remplir préalablement les champs  pour vos recherche!","Sucess",JOptionPane.INFORMATION_MESSAGE);
            query=SELECT_ALL;
        }
        refreshTable();
    }//GEN-LAST:event_btn_searchActionPerformed
    
    public void rowcount(){
    nombre_ligne.setText("Tous ("+ table.getRowCount()+")");
    }
    
    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        
         try {
            switch(btn_save_state){
                case "insert":
                    connectiondb();
                DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                stm = conn.prepareStatement("INSERT INTO mouvementStock (description, idProduit, quantite, dateMouvement, idTypeMouvement, sens, idMagasin, idExercice, idCreateur) " +
                               "VALUES (?, ?, ?, ?, (select code_type FROM type_mouvement where type=?), ?, ?, (SELECT id FROM exercice where status=1), (SELECT idUtilisateur from utilisateur WHERE nomUtilisateur=?))");
                stm.setString(1, description.getText());
                stm.setString(2, (String) code_produit.getSelectedItem()); // Remplacez idProduit par l'ID du produit à transférer
                stm.setInt(3, (int) quantite.getValue()); // Remplacez quantite par la quantité à transférer
                stm.setString(4, (String) now.format(dtFormat));
                stm.setString(5, (String) typeMouvement.getSelectedItem()); // Remplacez idTypeMouvementSortie par l'ID du type de mouvement de sortie
                stm.setString(6, "Entrée");
                stm.setString(7,idMagasin.getSelectedItem().toString()); // Remplacez idMagasinSource par l'ID du magasin source
                stm.setString(8, (String) nomCreateur.getSelectedItem()); // Remplacez idCreateur par l'ID de l'utilisateur qui effectue le transfert

                stm.executeUpdate();
                stm.close();
                conn.close();
                t = new Toast("Enregistrement réussie!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
//                JOptionPane.showMessageDialog(null, "insertion réussie!","Sucess",JOptionPane.INFORMATION_MESSAGE);
                break;

                case "update":
                    if(JOptionPane.showConfirmDialog(null, "Voulez-vous sauvegarder les modifications?","Confirmation de mise à jour",JOptionPane.YES_NO_OPTION) == 0){
                    connectiondb();
                stm = conn.prepareStatement("update mouvementStock set description=?, idProduit=?, quantite=?, idTypeMouvement=(select code_type FROM type_mouvement where type=?), sens='Entrée', idMagasin=?, idExercice=(SELECT idExercice FROM exercice where status=1), idCreateur=(SELECT idUtilisateur from utilisateur WHERE nomUtilisateur=?) WHERE idCreateur=?");
//              
                stm.setString(1, description.getText());
                stm.setString(2, (String) code_produit.getSelectedItem()); // Remplacez idProduit par l'ID du produit à transférer
                stm.setString(3,quantite.getValue().toString()); // Remplacez quantite par la quantité à transférer
                stm.setString(4, (String) typeMouvement.getSelectedItem()); // Remplacez idTypeMouvementSortie par l'ID du type de mouvement de sortie
                stm.setString(5, idMagasin.getSelectedItem().toString()); // Remplacez idMagasinSource par l'ID du magasin source
                stm.setString(6, (String) nomCreateur.getSelectedItem()); // Remplacez idCreateur par l'ID de l'utilisateur qui effectue le transfert
                stm.setString(7, idSelected);
                stm.executeUpdate();
                stm.close();
                conn.close();
                setEmptyForm();
                t = new Toast("Modification enregistrée", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
                    }               
break;
            }
//            conn.close();
            refreshTable();

        } catch (SQLException ex) {
            Logger.getLogger(Groupe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        setEmptyForm();
    }//GEN-LAST:event_btn_cancelActionPerformed
private void setEmptyForm(){
        typeMouvement.setSelectedItem("");
        nomCreateur.setSelectedItem(""); 
        quantite.setValue(0);
        nomCreateur.setSelectedItem("");
        btn_save_state="insert";
        btn_delete.setVisible(false);
        description.setText("");
        idMagasin.setSelectedItem(null);
        code_produit.setSelectedItem(null);
        dateCreation.setDate(null);
                
}
    
    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer cete ligne?","Confirmation de suppression",JOptionPane.YES_NO_OPTION) == 0){
            connectiondb();
            try{stm= conn.prepareStatement("delete from mouvementStock where id=?");
                stm.setString(1, idSelected);
                stm.executeUpdate();
                JOptionPane.showMessageDialog(null, "Supression réussie!","Sucess", JOptionPane.INFORMATION_MESSAGE);
                setEmptyForm();
                refreshTable();
            }catch(HeadlessException | SQLException e){
            }
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        btn_delete.setVisible(true); // afficher le boutton supprimer
        btn_save_state="update";
        containerForm.setVisible(true);
        containerList.setVisible(false);
//        DateTimeFormatter timeFormat;  //récuperer l'heure actuelle
//                timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        DefaultTableModel model=(DefaultTableModel) table.getModel();
        int i= table.getSelectedRow();
        idSelected = model.getValueAt(i,0).toString();
        connectiondb();
        //requete à modifier
            try {
            stm =conn.prepareStatement("SELECT ms.id, ms.description, ms.idProduit, ms.quantite, ms.dateMouvement, " +
               "ms.idTypeMouvement, ms.sens, ms.idMagasin, ms.idExercice, u.nomUtilisateur, " +
               "p.designation AS nomProduit, tm.type AS typeMouvement " +
               "FROM mouvementStock ms " +
               "INNER JOIN produits p ON ms.idProduit = p.id " +
               "INNER JOIN utilisateur u ON u.idUtilisateur = ms.idCreateur " +
               "LEFT JOIN type_mouvement tm ON ms.idTypeMouvement = tm.code_type " +
               "WHERE ms.id =?");
            stm.setString(1, idSelected);
            resultSet = stm.executeQuery();
  
           while(resultSet.next()){
           quantite.setValue(resultSet.getFloat("quantite"));
           idMagasin.setSelectedItem(resultSet.getString("idMagasin"));
           code_produit.setSelectedItem(resultSet.getString("idProduit"));
           nomCreateur.setSelectedItem(resultSet.getString("nomUtilisateur"));
                try {
                    dateCreation.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("dateMouvement")));
                } catch (ParseException ex) {
                    Logger.getLogger(MouvementStock1.class.getName()).log(Level.SEVERE, null, ex);
                }
           description.setText(resultSet.getString("description"));
           typeMouvement.setSelectedItem(resultSet.getString("typeMouvement"));
           }
           
           resultSet.close();
           stm.close();
           conn.close();   
           } catch (SQLException ex) {
            Logger.getLogger(EntreeCaisse.class.getName()).log(Level.SEVERE, null, ex);
           } 
    }//GEN-LAST:event_tableMouseClicked

    private void btnnewuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewuserActionPerformed
        containerList.setVisible(false);
        setEmptyForm();
        containerForm.setVisible(true);
        btn_save_state="insert";
    }//GEN-LAST:event_btnnewuserActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        containerList.setVisible(true);
        containerForm.setVisible(false);
        refreshTable();
        setEmptyForm();
//        layerDates.setVisible(false);
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        query=SELECT_ALL;
        date1.setDate(null);
        date2.setDate(null);
        idMagasinKey.setSelectedItem(null);
        refreshTable();
    
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void refreshTable(){
        String[] column={"ID","Réference Produit","Designation Produit","Quantite ","Magasin","Date de création"};
        String[] data= new String[6];
        DefaultTableModel model= new DefaultTableModel(null,column);
        try{
        connectiondb();
//        stm =conn.prepareStatement("select * from entree_caisse");
//        resultSet = stm.executeQuery();
  Statement statement =(Statement)conn.createStatement();
       resultSet = statement.executeQuery(query);
       while(resultSet.next()){
           data[0]=resultSet.getString("id");
           data[1]=resultSet.getString("refProduit") ;
           data[2]=resultSet.getString("nomProduit");
           data[3]=resultSet.getString("quantite");
           data[4]=resultSet.getString("idMagasin");
           data[5]=resultSet.getString("dateMouvement");

           model.addRow(data);
       }
       //inserer le model dans le tableau
      table.setModel(model);
      conn.close();
      rowcount();//mettre à jour le nombre de ligne
        }catch(SQLException e){
        }
}

   private void afficherExerciceActif(){
            try {
                connectiondb();
                stm = conn.prepareStatement("SELECT id FROM exercice where status=1");
                resultSet = stm.executeQuery();
               
                while (resultSet.next()) {
                    idExerciceActif.setText(resultSet.getString("id"));
                }
                resultSet.close();
                stm.close();
                conn.close();
        } catch (SQLException e) {
        }
    }
   
    private void setInputSelect(){
            try {
                connectiondb();
                stm = conn.prepareStatement("SELECT nomUtilisateur FROM utilisateur");
                resultSet = stm.executeQuery();
               nomCreateur.addItem("");
                while (resultSet.next()) {
                    nomCreateur.addItem(resultSet.getString("nomUtilisateur"));
                }
                stm = conn.prepareStatement("SELECT type FROM type_mouvement");
                resultSet = stm.executeQuery();
                typeMouvement.addItem("");
                while (resultSet.next()) {
                    typeMouvement.addItem(resultSet.getString("type"));
                }
                stm = conn.prepareStatement("SELECT id FROM produits");
                resultSet = stm.executeQuery();
                typeMouvement.addItem("");
                while (resultSet.next()) {
                    code_produit.addItem(resultSet.getString("id"));
                }
                stm = conn.prepareStatement("SELECT id FROM magasin");
                resultSet = stm.executeQuery();
                idMagasinKey.addItem("Tous");
                    idMagasin.addItem("");
                while (resultSet.next()) {
                    idMagasinKey.addItem(resultSet.getString("id"));
                    idMagasin.addItem(resultSet.getString("id"));
                }
                stm.close();
                resultSet.close();
                conn.close();
        } catch (SQLException e) {
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_back;
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_delete;
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_save;
    private javaapp.component.ButtonRadius btn_search;
    private javaapp.component.ButtonRadius btnnewuser;
    private javax.swing.JComboBox<String> code_produit;
    private javax.swing.JLayeredPane containerForm;
    private javax.swing.JLayeredPane containerList;
    private com.toedter.calendar.JDateChooser date1;
    private com.toedter.calendar.JDateChooser date2;
    private com.toedter.calendar.JDateChooser dateCreation;
    private javax.swing.JTextField description;
    private javax.swing.JLabel idExerciceActif;
    private javax.swing.JComboBox<String> idMagasin;
    private javax.swing.JComboBox<String> idMagasinKey;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel nature;
    private javax.swing.JLabel nature1;
    private javax.swing.JLabel nature2;
    private javax.swing.JLabel nature3;
    private javax.swing.JLabel nature4;
    private javax.swing.JLabel nature5;
    private javax.swing.JComboBox<String> nomCreateur;
    private javax.swing.JLabel nombre_ligne;
    private javax.swing.JSpinner quantite;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JComboBox<String> sens;
    private javaapp.component.Table table;
    private javax.swing.JComboBox<String> typeMouvement;
    // End of variables declaration//GEN-END:variables
}
