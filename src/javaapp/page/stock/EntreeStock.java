
package javaapp.page.stock;

import javaapp.page.parametre.Groupe;
import javaapp.page.Caisse.EntreeCaisse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.component.DateConverter;
import javaapp.component.FormatUtils;
import javaapp.component.Toast;
import javaapp.dao.ExerciceDAO;
import javaapp.dao.MagasinDAO;
import javaapp.dao.MouvementStockDAO;
import javaapp.dao.ProduitDAO;
import javaapp.dao.ProfileDAO;
import javaapp.dao.TypeMouvementDAO;
import javaapp.dao.UserDAO;
import javaapp.factory.DAOFactory;
import javaapp.pagination.EventPagination;
import javaapp.pagination.PaginationItemRenderStyle1;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ZOENIRINA
 */
public class EntreeStock extends javax.swing.JPanel {
    private final int borderRadius = 10;
    Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
    final static String FILTER_QUERY="select p.id as idProduit,p.designation,(COALENSCE(entree.quantite_entree,0)-COALENSCE(sortie.quantite_sortie, 0)) as quantite_stock, u.poids, u.package, p.package, p.prix from produit p "
        + "JOIN unite_produit u ON p.idUnite = u.id "
        + "LEFT JOIN (SELECT idProduit, SUM(quantite) AS quantite_entree from mouvementStock where sens= 'entree' group by idProduit) entree ON p.idProduit= entree.idProduit LEFT JOIN (SELECT idProduit, SUM(quantite) AS quantite_sortie from mouvementStock where sens='sortie' group by produt) sortie ON p.idProduit = sortie.idProduit  WHERE p.idProduit=?";
    final static String SELECT_ALL="SELECT ms.id, ms.quantite, ms.dateMouvement, ms.idMagasin,p.refProduit,p.designation AS nomProduit, mag.refMagasin FROM mouvementStock ms INNER JOIN produits p ON ms.idProduit = p.id INNER JOIN magasin mag ON ms.idMagasin = mag.id  WHERE ms.idExercice = (select idExercice FROM EXERCICE where status= 1) and  ms.sens='Entrée' AND ms.estValide = 'Oui' ORDER BY ms.dateMouvement desc";
    public String query=null;
    public String title="Autres";
    public PreparedStatement stm ;
    public ResultSet resultSet,result;
    public int idSelected=0;
    private String btn_save_state= "insert";
    public String datetime1;
    public LocalTime timepart;
    public JPanel pan;
    public Toast t;
    int pageCount=1;
    private int activeMenu=1;
    private FormatUtils formater;
    private final MouvementStockDAO mouveStockDAO;
    private final MagasinDAO magDAO;
    private final ProduitDAO produitDAO;
    private final TypeMouvementDAO typeDAO;
    private final ExerciceDAO exerciceDAO;
    private final UserDAO userDAO;

    public EntreeStock(JPanel panel) {
        initComponents();
        mouveStockDAO = DAOFactory.getMouvementStockDAO();
        magDAO = DAOFactory.getMagasinDAO();
        produitDAO = DAOFactory.getProduitDAO();
        typeDAO = DAOFactory.getTypeMouvementDAO(); 
        exerciceDAO = DAOFactory.getExerciceDAO();
        userDAO = DAOFactory.getUserDAO();
        
         refreshTable(mouveStockDAO.selectIN());
         setInputSelect();
        pan= panel;
        query=SELECT_ALL; // TOUTES les sortie du caisse dont l'exercice est actif et ordonnée solon la date
        
       
        rowcount();
        scrollTable.getViewport().setBackground(Color.white);
        btn_save_state= "insert";
        btn_delete.setVisible(false);
        afficherExerciceActif();
        dateCreation.setEnabled(false);
        containerForm.setVisible(false);
        
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

        containerForm = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        nature4 = new javax.swing.JLabel();
        dateCreation = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        nature = new javax.swing.JLabel();
        typeMouvement = new javax.swing.JComboBox<>();
        quantite = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        description = new javax.swing.JTextField();
        nature5 = new javax.swing.JLabel();
        nature2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        refMagasin = new javax.swing.JComboBox<>();
        btn_delete = new javaapp.component.ButtonRadius();
        idExerciceActif = new javax.swing.JLabel();
        nature1 = new javax.swing.JLabel();
        refProduit = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        btn_save = new javaapp.component.ButtonRadius();
        btn_cancel = new javaapp.component.ButtonRadius();
        nomCreateur = new javax.swing.JComboBox<>();
        btn_back = new javaapp.component.ButtonRadius();
        containerList = new javax.swing.JLayeredPane();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        scrollTable = new javax.swing.JScrollPane();
        tableOutput = new javaapp.component.Table();
        refMagasinKey = new javax.swing.JComboBox<>();
        btn_search = new javaapp.component.ButtonRadius();
        date1 = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        date2 = new com.toedter.calendar.JDateChooser();
        btnnewuser = new javaapp.component.ButtonRadius();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        btn_refresh = new javaapp.component.ButtonRadius();
        scrollTable1 = new javax.swing.JScrollPane();
        tableInput = new javaapp.component.Table();
        jPanel1 = new javax.swing.JPanel();
        pagination1 = new javaapp.pagination.Pagination();
        panelBorderRound2 = new javaapp.component.PanelBorderRound();
        nombre_ligne1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 1000));

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

        btn_delete.setBackground(new java.awt.Color(255, 222, 177));
        btn_delete.setBorder(null);
        btn_delete.setForeground(new java.awt.Color(248, 91, 50));
        btn_delete.setText("Supprimer");
        btn_delete.setBorderColor(new java.awt.Color(255, 222, 177));
        btn_delete.setColor(new java.awt.Color(255, 222, 177));
        btn_delete.setColorClick(new java.awt.Color(255, 222, 177));
        btn_delete.setColorOver(new java.awt.Color(255, 222, 177));
        btn_delete.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        idExerciceActif.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        idExerciceActif.setForeground(new java.awt.Color(248, 91, 50));
        idExerciceActif.setText("00012");

        nature1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature1.setForeground(new java.awt.Color(51, 51, 51));
        nature1.setText("Description");

        jLabel8.setBackground(new java.awt.Color(255, 102, 0));
        jLabel8.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(36, 36, 36));
        jLabel8.setText("Information générale");

        btn_save.setBackground(new java.awt.Color(248, 91, 50));
        btn_save.setBorder(null);
        btn_save.setForeground(new java.awt.Color(255, 255, 255));
        btn_save.setText("Enregistrer");
        btn_save.setBorderColor(new java.awt.Color(248, 91, 50));
        btn_save.setColor(new java.awt.Color(248, 91, 50));
        btn_save.setColorClick(new java.awt.Color(248, 91, 50));
        btn_save.setColorOver(new java.awt.Color(255, 51, 0));
        btn_save.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
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
        btn_cancel.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        nomCreateur.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nomCreateur.setForeground(new java.awt.Color(102, 102, 102));
        nomCreateur.setEnabled(false);
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
        containerForm.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(description, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(refMagasin, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(btn_delete, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(idExerciceActif, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(refProduit, javax.swing.JLayeredPane.DEFAULT_LAYER);
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
                                .addComponent(refMagasin, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nature2)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(quantite, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(refProduit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(containerFormLayout.createSequentialGroup()
                        .addComponent(nature4)
                        .addGap(18, 18, 18)
                        .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(containerFormLayout.createSequentialGroup()
                                .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerFormLayout.createSequentialGroup()
                                    .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nature)
                                        .addComponent(typeMouvement, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(containerFormLayout.createSequentialGroup()
                                            .addComponent(nature1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel1))
                                        .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(nature5)
                                .addComponent(dateCreation, javax.swing.GroupLayout.PREFERRED_SIZE, 819, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                .addComponent(refMagasin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel14))
                .addGap(3, 3, 3)
                .addComponent(refProduit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(3, 3, 3)
                .addComponent(quantite, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nature4)
                    .addGroup(containerFormLayout.createSequentialGroup()
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
                            .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(115, 115, 115))
        );

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Filtre par date:");

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("à");

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
        btn_search.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_search.setColor(new java.awt.Color(240, 236, 243));
        btn_search.setColorClick(new java.awt.Color(255, 255, 255));
        btn_search.setColorOver(new java.awt.Color(232, 230, 230));
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        date1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Reférence du magasin");

        date2.setBackground(new java.awt.Color(255, 255, 255));

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
        tableInput.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
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
                .addContainerGap(362, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelBorderRound2.setBackground(new java.awt.Color(255, 222, 177));

        nombre_ligne1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nombre_ligne1.setForeground(new java.awt.Color(248, 91, 50));
        nombre_ligne1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombre_ligne1.setText("Documents (5)");

        javax.swing.GroupLayout panelBorderRound2Layout = new javax.swing.GroupLayout(panelBorderRound2);
        panelBorderRound2.setLayout(panelBorderRound2Layout);
        panelBorderRound2Layout.setHorizontalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nombre_ligne1, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBorderRound2Layout.setVerticalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nombre_ligne1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        containerList.setLayer(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(scrollTable, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(refMagasinKey, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(btn_search, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(date1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(date2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(btnnewuser, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(jSeparator1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(btn_refresh, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(scrollTable1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerList.setLayer(panelBorderRound2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout containerListLayout = new javax.swing.GroupLayout(containerList);
        containerList.setLayout(containerListLayout);
        containerListLayout.setHorizontalGroup(
            containerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(containerListLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(containerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(containerListLayout.createSequentialGroup()
                        .addGroup(containerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(refMagasinKey, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(scrollTable1)
                    .addComponent(scrollTable)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(54, Short.MAX_VALUE))
            .addGroup(containerListLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelBorderRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnnewuser, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );
        containerListLayout.setVerticalGroup(
            containerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerListLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(containerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addGroup(containerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnnewuser, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(panelBorderRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(containerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(containerListLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refMagasinKey, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(containerListLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollTable, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(scrollTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(containerForm, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(containerList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(containerList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(containerForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    
    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    List<javaapp.bean.MouvementStock> mouvements = new ArrayList<>();

    if (refMagasinKey.getSelectedItem() != "Tous" && date1.getDate() != null && date2.getDate() != null) {
        mouvements = mouveStockDAO.filterINByMagasinAndDate(
            magDAO.findByRef(refMagasinKey.getSelectedItem().toString()).getId(), 
            dateFormat.format(date1.getDate()), 
            dateFormat.format(date2.getDate())
        );
        
        // Update the table with the list of movements
    } else if (refMagasinKey.getSelectedItem() != "Tous" && date1.getDate() == null && date2.getDate() == null) {
        mouvements = mouveStockDAO.filterINByMagasin(magDAO.findByRef(refMagasinKey.getSelectedItem().toString()).getId());
        // Update the table with the list of movements
    } else if (refMagasinKey.getSelectedItem() == "Tous" && date1.getDate() != null && date2.getDate() != null) {
        try {
            mouvements = mouveStockDAO.filterINBetweenDate(dateFormat.format(date1.getDate()), dateFormat.format(date2.getDate()));
            
        } catch (SQLException ex) {
            Logger.getLogger(EntreeStock.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else if (refMagasinKey.getSelectedItem() == "Tous" && date1.getDate() != null && date2.getDate() == null) {
        try {
            mouvements = mouveStockDAO.filterINByDate(dateFormat.format(date1.getDate()));
            // Update the table with the list of movements
        } catch (SQLException ex) {
            Logger.getLogger(EntreeStock.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        JOptionPane.showMessageDialog(null, "Veuillez remplir préalablement les champs pour vos recherches!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
        mouvements = mouveStockDAO.selectIN();
    }
    refreshTable(mouvements);
    }//GEN-LAST:event_btn_searchActionPerformed
    
    public void rowcount(){
    nombre_ligne1.setText("Entrées ("+ tableInput.getRowCount()+")");
    }
    
    private boolean validatedForm(){
        return refProduit.getSelectedItem() != null &&  !quantite.getValue().equals(0) && refMagasin.getSelectedItem() != null && nomCreateur.getSelectedItem() != null; 
    }
    
    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
       if (validatedForm()) {
           javaapp.bean.MouvementStock mouvementStock = new javaapp.bean.MouvementStock();
           mouvementStock.setDescription(description.getText());
           mouvementStock.setIdProduit(produitDAO.find(refProduit.getSelectedItem().toString()).getId());
           mouvementStock.setQuantite((int) quantite.getValue());
           mouvementStock.setDateMouvement(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
           if(!typeMouvement.getSelectedItem().toString().trim().isEmpty())mouvementStock.setIdTypeMouvement(typeDAO.find(typeMouvement.getSelectedItem().toString()).getCodeType());
           mouvementStock.setIdMagasin(magDAO.findByRef(refMagasin.getSelectedItem().toString()).getId());
    switch (btn_save_state) {
    case "insert":
        if (mouveStockDAO.createIN(mouvementStock)) {
            showMessage("Enregistrement réussi!", "Success");
        }
        break;
    case "update":
        if (JOptionPane.showConfirmDialog(null, "Voulez-vous sauvegarder les modifications?", "Confirmation de mise à jour", JOptionPane.YES_NO_OPTION) == 0) {
            mouvementStock.setId(idSelected);
            if (mouveStockDAO.updateIN(mouvementStock)) {
                showMessage("Modification enregistrée", "Success");
            }
        }
        break;
    }
    refreshTable(mouveStockDAO.selectIN());
    } else {
        JOptionPane.showMessageDialog(null, "Veuillez vérifier que tous les champs sont correctement remplis!", "Message", JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_btn_saveActionPerformed

    private void showMessage (String message, String title){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
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
        refMagasin.setSelectedItem(null);
        refProduit.setSelectedItem(null);
        dateCreation.setDate(null);
                
    }
    
    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer cette ligne?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION) == 0) {
            javaapp.bean.MouvementStock mouvementStock = new javaapp.bean.MouvementStock();
            mouvementStock.setId(idSelected);
            if (mouveStockDAO.delete(mouvementStock)) {
                showMessage("Suppression réussie!", "Success");
                setEmptyForm();
                refreshTable(mouveStockDAO.selectIN());
            }
    }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void tableOutputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOutputMouseClicked
        btn_delete.setVisible(true);
        btn_save_state="update";
        containerForm.setVisible(true);
        containerList.setVisible(false);
        DefaultTableModel model=(DefaultTableModel) tableOutput.getModel();
        int i= tableOutput.getSelectedRow();
        int id = Integer.parseInt(model.getValueAt(i,0).toString());
        javaapp.bean.MouvementStock mouvement = mouveStockDAO.find(id);
            quantite.setValue(mouvement.getQuantite());
            refMagasin.setSelectedItem(mouvement.getMagasin().getRefMagasin());
            refProduit.setSelectedItem(mouvement.getProduit().getRefProduit());
            nomCreateur.setSelectedItem(userDAO.find(mouvement.getIdCreateur()));
            try {
                dateCreation.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(mouvement.getDateMouvement()));
            } catch (ParseException ex) {
                Logger.getLogger(EntreeStock.class.getName()).log(Level.SEVERE, null, ex);
            }
           description.setText(mouvement.getDescription());
           
           if(mouvement.getTypeMouvement() != null && mouvement.getTypeMouvement().getCodeType() != 0)typeMouvement.setSelectedItem(mouvement.getTypeMouvement().getCodeType());
           
          
    }//GEN-LAST:event_tableOutputMouseClicked

    private void btnnewuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewuserActionPerformed
        containerList.setVisible(false);
        setEmptyForm();
        containerForm.setVisible(true);
        btn_save_state="insert";
    }//GEN-LAST:event_btnnewuserActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        containerList.setVisible(true);
        containerForm.setVisible(false);
        refreshTable(mouveStockDAO.selectIN());
        setEmptyForm();
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        date1.setDate(null);
        date2.setDate(null);
        refMagasinKey.setSelectedItem("Tous");
        refreshTable(mouveStockDAO.selectIN());
    
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void tableInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInputMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableInputMouseClicked
    
    private void refreshTable( List<javaapp.bean.MouvementStock> mouvements){
        String[] column={"#","Date de création","Magasin","Référence","Designation ","Quantité"};
//        String[] data= new String[6];
        DefaultTableModel model= new DefaultTableModel(null,column);

        for (javaapp.bean.MouvementStock mouvement : mouvements) {
        model.addRow(new Object[]{
            mouvement.getId(),
            DateConverter.convertToDate(mouvement.getDateMouvement()),
            mouvement.getMagasin().getRefMagasin(),
            mouvement.getProduit().getRefProduit(),
            mouvement.getProduit().getDesignation(),
            mouvement.getQuantite()
        });
        }
    tableInput.setModel(model);
      rowcount();//mettre à jour le nombre de ligne
      refreshPagination();//refraichir la page
    }

   private void afficherExerciceActif(){
        idExerciceActif.setText(String.valueOf(exerciceDAO.getActiveExercice()));
    }
   
    private void setInputSelect(){
        
        List<javaapp.bean.User> users = userDAO.getUserOrderByStatus();
        for(javaapp.bean.User user : users) {
            nomCreateur.addItem(user.getNomUtilisateur());
        }

        List<javaapp.bean.TypeMouvement> types = typeDAO.select();
        typeMouvement.addItem("");
        for(javaapp.bean.TypeMouvement type : types) {
            typeMouvement.addItem(type.getType());
        }
        
        List<javaapp.bean.Magasin> magasins = magDAO.select();
        refMagasin.addItem("");
        refMagasinKey.addItem("Tous");
        for(javaapp.bean.Magasin mag : magasins) {
            refMagasin.addItem(mag.getRefMagasin());
            refMagasinKey.addItem(mag.getRefMagasin());
        }
        
        List<javaapp.bean.Produit> produits = produitDAO.select();
        refProduit.addItem("");
        for(javaapp.bean.Produit produit : produits) {
            refProduit.addItem(produit.getRefProduit());
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
    private javax.swing.JLayeredPane containerForm;
    private javax.swing.JLayeredPane containerList;
    private com.toedter.calendar.JDateChooser date1;
    private com.toedter.calendar.JDateChooser date2;
    private com.toedter.calendar.JDateChooser dateCreation;
    private javax.swing.JTextField description;
    private javax.swing.JLabel idExerciceActif;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel nature;
    private javax.swing.JLabel nature1;
    private javax.swing.JLabel nature2;
    private javax.swing.JLabel nature4;
    private javax.swing.JLabel nature5;
    private javax.swing.JComboBox<String> nomCreateur;
    private javax.swing.JLabel nombre_ligne1;
    private javaapp.pagination.Pagination pagination1;
    private javaapp.component.PanelBorderRound panelBorderRound2;
    private javax.swing.JSpinner quantite;
    private javax.swing.JComboBox<String> refMagasin;
    private javax.swing.JComboBox<String> refMagasinKey;
    private javax.swing.JComboBox<String> refProduit;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JScrollPane scrollTable1;
    private javaapp.component.Table tableInput;
    private javaapp.component.Table tableOutput;
    private javax.swing.JComboBox<String> typeMouvement;
    // End of variables declaration//GEN-END:variables
}
