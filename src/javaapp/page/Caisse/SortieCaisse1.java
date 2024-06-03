
package javaapp.page.Caisse;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javaapp.component.DateConverter;
import javaapp.component.FormatUtils;
import javaapp.component.Toast;
import javaapp.dao.ExerciceDAO;
import javaapp.dao.SortieCaisseDAO;
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
public class SortieCaisse1 extends javax.swing.JPanel {
    private final int borderRadius = 10;
    public String query=null;
    public String idSelected;
    private String btn_save_state= "insert";
    public LocalTime timepart;
    public JPanel pan;
    public Toast t;
    private float soldeIdSelected=0;
    private final String SELECT_ALL = "select sortie_caisse.*,utilisateur.nomUtilisateur, tm.* from sortie_caisse inner join utilisateur on sortie_caisse.idCreateur=utilisateur.idUtilisateur "
            + "left join type_mouvement tm on sortie_caisse.idType= tm.code_type where idExercice in (select id from exercice where status=1)";
    private final String SELECT_ALL_ORDERED = SELECT_ALL+"ORDER BY sortie_caisse.dateSortie DESC";

    int pageCount=1;
    private final FormatUtils formater;
    private final SortieCaisseDAO sortieCaisseDAO;
    private javaapp.bean.SortieCaisse sortieCaisseSelected;
    private final TypeMouvementDAO typeDAO;
    private final UserDAO userDAO;
    private final ExerciceDAO exerciceDAO;
    
    public SortieCaisse1(JPanel panel) {
        initComponents();
        sortieCaisseDAO = DAOFactory.getSortieCaisseDAO();
        typeDAO = DAOFactory.getTypeMouvementDAO(); 
        userDAO = DAOFactory.getUserDAO();
        exerciceDAO = DAOFactory.getExerciceDAO();
        
        setInputSelect();
        pan= panel;
        query=SELECT_ALL_ORDERED; // TOUTES les sortie du caisse dont l'exercice est actif et ordonnée solon la date
        formater = new FormatUtils();
        refreshTable(sortieCaisseDAO.select());
        rowcount();
        scrollTable.getViewport().setBackground(Color.white);
        btn_save_state= "insert";
        afficherExerciceActif();
        nomCreateur.setEnabled(false);
        
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

        scrollTable = new javax.swing.JScrollPane();
        tableOutput = new javaapp.component.Table();
        jLabel9 = new javax.swing.JLabel();
        nombre_ligne = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        date1 = new com.toedter.calendar.JDateChooser();
        date2 = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        btn_filter = new javaapp.component.ButtonRadius();
        jLabel7 = new javax.swing.JLabel();
        idExerciceActif = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        reference = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        nomCreateur = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        nature = new javax.swing.JLabel();
        typeMouvement = new javax.swing.JComboBox<>();
        dateCreation = new com.toedter.calendar.JDateChooser();
        dateCreation_lbl = new javax.swing.JLabel();
        btn_save = new javaapp.component.ButtonRadius();
        btn_cancel = new javaapp.component.ButtonRadius();
        idSearch = new javax.swing.JTextField();
        btn_search1 = new javaapp.component.ButtonRadius();
        nature1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btn_refresh = new javaapp.component.ButtonRadius();
        jLabel13 = new javax.swing.JLabel();
        montant = new javax.swing.JSpinner();
        scrollTable1 = new javax.swing.JScrollPane();
        tableInput = new javaapp.component.Table();
        jPanel1 = new javax.swing.JPanel();
        pagination1 = new javaapp.pagination.Pagination();
        jScrollPane1 = new javax.swing.JScrollPane();
        description = new javax.swing.JTextPane();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));

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
        scrollTable.setViewportView(tableOutput);

        jLabel9.setBackground(new java.awt.Color(255, 102, 0));
        jLabel9.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(36, 36, 36));
        jLabel9.setText("Liste des sorties en caisse");

        nombre_ligne.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nombre_ligne.setForeground(new java.awt.Color(102, 102, 102));
        nombre_ligne.setText("Tous (5)");

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Filtre par date:");

        date1.setBackground(new java.awt.Color(255, 255, 255));

        date2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("à");

        btn_filter.setBackground(new java.awt.Color(240, 236, 243));
        btn_filter.setBorder(null);
        btn_filter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/search.png"))); // NOI18N
        btn_filter.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_filter.setColor(new java.awt.Color(240, 236, 243));
        btn_filter.setColorClick(new java.awt.Color(255, 255, 255));
        btn_filter.setColorOver(new java.awt.Color(232, 230, 230));
        btn_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filterActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Exercice N°");

        idExerciceActif.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        idExerciceActif.setForeground(new java.awt.Color(248, 91, 50));
        idExerciceActif.setText("00012");

        jLabel8.setBackground(new java.awt.Color(255, 102, 0));
        jLabel8.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(36, 36, 36));
        jLabel8.setText("Transaction sortante");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Réference");

        reference.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        reference.setForeground(new java.awt.Color(102, 102, 102));

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Identifiant utilisateur");

        nomCreateur.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nomCreateur.setForeground(new java.awt.Color(102, 102, 102));
        nomCreateur.setMinimumSize(new java.awt.Dimension(64, 40));
        nomCreateur.setPreferredSize(new java.awt.Dimension(64, 40));

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Montant");

        nature.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature.setForeground(new java.awt.Color(51, 51, 51));
        nature.setText("Nature");

        typeMouvement.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        typeMouvement.setForeground(new java.awt.Color(102, 102, 102));

        dateCreation.setBackground(new java.awt.Color(255, 255, 255));
        dateCreation.setForeground(new java.awt.Color(102, 102, 102));
        dateCreation.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N

        dateCreation_lbl.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        dateCreation_lbl.setForeground(new java.awt.Color(51, 51, 51));
        dateCreation_lbl.setText("Date de la transaction");

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

        idSearch.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        idSearch.setForeground(new java.awt.Color(102, 102, 102));

        btn_search1.setBackground(new java.awt.Color(240, 236, 243));
        btn_search1.setBorder(null);
        btn_search1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/search.png"))); // NOI18N
        btn_search1.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_search1.setColor(new java.awt.Color(240, 236, 243));
        btn_search1.setColorClick(new java.awt.Color(255, 255, 255));
        btn_search1.setColorOver(new java.awt.Color(232, 230, 230));
        btn_search1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search1ActionPerformed(evt);
            }
        });

        nature1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature1.setForeground(new java.awt.Color(51, 51, 51));
        nature1.setText("Description");

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("(Si nature non spécifié)");

        btn_refresh.setBackground(new java.awt.Color(240, 236, 243));
        btn_refresh.setBorder(null);
        btn_refresh.setForeground(new java.awt.Color(51, 51, 51));
        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/refresh.png"))); // NOI18N
        btn_refresh.setText("refresh");
        btn_refresh.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_refresh.setColor(new java.awt.Color(240, 236, 243));
        btn_refresh.setColorClick(new java.awt.Color(255, 255, 255));
        btn_refresh.setColorOver(new java.awt.Color(232, 230, 230));
        btn_refresh.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Numéro ou reference");

        montant.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N

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

        description.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jScrollPane1.setViewportView(description);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nature1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateCreation_lbl, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 677, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(reference, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 543, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(idExerciceActif, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dateCreation, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scrollTable, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(nombre_ligne)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(idSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_search1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(date2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(btn_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(nomCreateur, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scrollTable1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addGap(48, 48, 48))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(typeMouvement, 0, 450, Short.MAX_VALUE)
                                .addComponent(nature, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(montant)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idExerciceActif, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomCreateur, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reference, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(montant, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(nature)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typeMouvement, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(dateCreation_lbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateCreation, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nature1)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nombre_ligne)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(date2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(idSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(1, 1, 1))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addGap(11, 11, 11))
                        .addComponent(btn_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_search1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
  
    
    private void btn_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filterActionPerformed
        if(date2.getDate() == null && date1.getDate() == null){       
            JOptionPane.showMessageDialog(null, "Veuillez inserer la date à rechercher!", "Sucess", JOptionPane.INFORMATION_MESSAGE);    
        }else{  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<javaapp.bean.SortieCaisse> sorties;
        if(date2.getDate() == null){
            sorties = sortieCaisseDAO.filterByDate(dateFormat.format(date1.getDate()), null);
        } else {
            sorties = sortieCaisseDAO.filterByDate(dateFormat.format(date1.getDate()), dateFormat.format(date2.getDate()));
        }
        refreshTable(sorties);
        }
    }//GEN-LAST:event_btn_filterActionPerformed
    
    public void rowcount(){
    nombre_ligne.setText("Tous ("+ tableOutput.getRowCount()+")");
    }
    
    private boolean validatedForm(){
        return (typeMouvement.getSelectedItem() != null || !description.getText().isEmpty()) && ((Number) montant.getValue()).floatValue() != 0 && !reference.getText().isEmpty() && nomCreateur.getSelectedItem() != null && dateCreation.getDate() != null; 
    }
    
    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        if( validatedForm() ){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        javaapp.bean.SortieCaisse sortieCaisse = ( "insert".equals(btn_save_state))? new javaapp.bean.SortieCaisse() : sortieCaisseSelected;
        sortieCaisse.setIdSourceReference(Integer.parseInt(reference.getText()));
        sortieCaisse.setMontant(((Number) montant.getValue()).floatValue());
        sortieCaisse.setIdExercice(Integer.parseInt(idExerciceActif.getText()));
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        sortieCaisse.setDateSortie(now.format(formatter));
        if(!typeMouvement.getSelectedItem().toString().isEmpty())sortieCaisse.setIdType(DAOFactory.getTypeMouvementDAO().find(typeMouvement.getSelectedItem().toString()).getCodeType()); // Implement this method to get idType
        sortieCaisse.setDescription(description.getText());
        
        float solde = sortieCaisseDAO.getSoldeActuel();
            if((solde - sortieCaisse.getMontant()) >= 0){
                sortieCaisseDAO.create(sortieCaisse);
                new Toast("Enregistrement réussie!", pan.getLocationOnScreen().x + pan.getWidth() - 320, pan.getLocationOnScreen().y + 7).showtoast();
            } else {
                JOptionPane.showMessageDialog(null, "Votre solde actuel est insuffisant pour effectuer ce mouvement! \nSolde disponible: " + sortieCaisseDAO.getSoldeActuel(), "Warning", JOptionPane.OK_OPTION);
            }
        refreshTable(sortieCaisseDAO.select());
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez vérifier que tous les champs sont correctement remplis!", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        setEmptyForm();
    }//GEN-LAST:event_btn_cancelActionPerformed
private void setEmptyForm(){
        typeMouvement.setSelectedItem("");
        nomCreateur.setSelectedIndex(0);
        reference.setText("");
        montant.setValue(0);
        dateCreation.setDate(null);
        btn_save_state="insert";
        dateCreation.setVisible(true);
        dateCreation_lbl.setVisible(true);
        description.setText("");
}
    private void btn_search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search1ActionPerformed
       
        try{
            int number = Integer.parseInt(idSearch.getText());
           query="select * from sortie_caisse where id = "+idSearch.getText()+" and idExercice in (select id from exercice where status=1) order by dateSortie DESC"; 
        }catch(NumberFormatException e){
        query="select * from sortie_caisse where idSourceReference like '%"+idSearch.getText()+"%' and idExercice in (select id from exercice where status=1) order by dateSortie DESC";
        }
//        refreshTable(sortieCaisseDAO.filterByDate(datetime1, query));
    }//GEN-LAST:event_btn_search1ActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        query=SELECT_ALL_ORDERED;
        date1.setDate(null);
        date2.setDate(null);
        idSearch.setText("");
        
        refreshTable(sortieCaisseDAO.select());
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void tableInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInputMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableInputMouseClicked

    public void refreshTable(List<javaapp.bean.SortieCaisse> sorties) {
        String[] column = {"ID", "Date de sortie", "Réference", "Montant", "Nature", "Createur"};
        DefaultTableModel model = new DefaultTableModel(null, column);
        for(javaapp.bean.SortieCaisse sortie : sorties) {
            model.addRow(new Object[]{
                sortie.getId(),
                DateConverter.convertToDate(sortie.getDateSortie()),
                sortie.getIdSourceReference() != 0 ? sortie.getIdSourceReference() : " ",
                formater.formatFloat(sortie.getMontant()),
                sortie.getCreateur().getNomUtilisateur(),
                sortie.getDescription() != null ? sortie.getDescription() : "-",
            });
        }
        tableInput.setModel(model);
        rowcount();
        refreshPagination();
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
                
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_filter;
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_save;
    private javaapp.component.ButtonRadius btn_search1;
    private com.toedter.calendar.JDateChooser date1;
    private com.toedter.calendar.JDateChooser date2;
    private com.toedter.calendar.JDateChooser dateCreation;
    private javax.swing.JLabel dateCreation_lbl;
    private javax.swing.JTextPane description;
    private javax.swing.JLabel idExerciceActif;
    private javax.swing.JTextField idSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner montant;
    private javax.swing.JLabel nature;
    private javax.swing.JLabel nature1;
    private javax.swing.JComboBox<String> nomCreateur;
    private javax.swing.JLabel nombre_ligne;
    private javaapp.pagination.Pagination pagination1;
    private javax.swing.JTextField reference;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JScrollPane scrollTable1;
    private javaapp.component.Table tableInput;
    private javaapp.component.Table tableOutput;
    private javax.swing.JComboBox<String> typeMouvement;
    // End of variables declaration//GEN-END:variables
}
