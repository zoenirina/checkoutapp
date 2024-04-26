
package javaapp.page;

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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.component.Toast;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TransferStock extends javax.swing.JPanel {
private final int borderRadius = 10;
 Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
    String SELECT_ALL="SELECT ms.id, ms.quantite, ms.dateMouvement, ms.idMagasin,p.refProduit,p.designation AS nomProduit FROM mouvementStock ms INNER JOIN produits p ON ms.idProduit = p.id  WHERE ms.idExercice = (select idExercice FROM EXERCICE where status= 1) and  ms.sens='Sortie' ORDER BY ms.dateMouvement desc";
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

    public TransferStock(JPanel panel) {
        initComponents();
        setInputSelect();
        pan= panel;
        query=SELECT_ALL; // TOUTES les sortie du caisse dont l'exercice est actif et ordonnée solon la date
        btn_save_state= "insert";
        afficherExerciceActif();
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
        jLabel14 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        nature = new javax.swing.JLabel();
        typeMouvement = new javax.swing.JComboBox<>();
        quantite = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        description = new javax.swing.JTextField();
        nature2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        idMagasin1 = new javax.swing.JComboBox<>();
        idExerciceActif = new javax.swing.JLabel();
        nature1 = new javax.swing.JLabel();
        code_produit = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        btn_save = new javaapp.component.ButtonRadius();
        btn_cancel = new javaapp.component.ButtonRadius();
        nomCreateur = new javax.swing.JComboBox<>();
        nature6 = new javax.swing.JLabel();
        idMagasin2 = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 850));

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

        nature2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature2.setForeground(new java.awt.Color(51, 51, 51));
        nature2.setText("Magasin de prélèvement");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Créateur");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Code produit");

        idExerciceActif.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        idExerciceActif.setForeground(new java.awt.Color(248, 91, 50));
        idExerciceActif.setText("00012");

        nature1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature1.setForeground(new java.awt.Color(51, 51, 51));
        nature1.setText("Description");

        jLabel8.setBackground(new java.awt.Color(255, 102, 0));
        jLabel8.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(36, 36, 36));
        jLabel8.setText("Transfert de stock");

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

        nature6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature6.setForeground(new java.awt.Color(51, 51, 51));
        nature6.setText("Magasin de dépôt");

        containerForm.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(typeMouvement, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(quantite, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(description, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(idMagasin1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(idExerciceActif, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(code_produit, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(btn_save, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(btn_cancel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nomCreateur, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(idMagasin2, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
                                .addComponent(nature6)
                                .addComponent(nomCreateur, 0, 820, Short.MAX_VALUE)
                                .addComponent(idMagasin1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nature2)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(quantite, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(code_produit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(idMagasin2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(containerFormLayout.createSequentialGroup()
                            .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(containerFormLayout.createSequentialGroup()
                            .addComponent(nature4)
                            .addGap(18, 18, 18)
                            .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(nature)
                                .addComponent(typeMouvement, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(43, 43, 43)
                            .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(containerFormLayout.createSequentialGroup()
                                    .addComponent(nature1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel1))
                                .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        containerFormLayout.setVerticalGroup(
            containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerFormLayout.createSequentialGroup()
                .addGap(32, 32, 32)
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
                .addComponent(idMagasin1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nature6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idMagasin2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
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
                .addGap(38, 38, 38)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(84, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(containerForm, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(containerForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private int getQuantiteStock(){
        connectiondb();
        int quantite_stock=0;
        try{
        query="SELECT (SELECT SUM(CASE " +
        "WHEN m.sens = 'Entrée' THEN m.quantite WHEN m.sens = 'Sortie' THEN -m.quantite   ELSE 0 END) FROM mouvementStock m " +
        "WHERE m.idProduit = p.id AND m.idMagasin ="+idMagasin1.getSelectedItem()+") AS quantite_stock FROM   produits p " +
        " WHERE p.id="+code_produit.getSelectedItem();
        
                Statement statement =(Statement)conn.createStatement();
                    resultSet = statement.executeQuery(query);
                    while(resultSet.next()){
                        quantite_stock=resultSet.getInt("quantite_stock");
                    }
        }catch(SQLException ex){
        }
    return quantite_stock;
    }
    private boolean validatedForm(){
        return (typeMouvement.getSelectedItem() != null || !description.getText().isEmpty()) && (int) quantite.getValue() != 0 && idMagasin1.getSelectedItem().toString() != null && idMagasin2.getSelectedItem().toString() != null && nomCreateur.getSelectedItem() != null; 
    }
    
    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        if(validatedForm()){
        try {
            connectiondb();
            conn.setAutoCommit(false); // Commencer la transaction
            try{
                if((getQuantiteStock()-(int)quantite.getValue()) >= 0){
                // 2. Insérer un mouvement de stock de sortie pour le premier magasin
                DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                stm = conn.prepareStatement("INSERT INTO mouvementStock (description, idProduit, quantite, dateMouvement, idTypeMouvement, sens, idMagasin, idExercice, idCreateur) " +
                    "VALUES (?, ?, ?, ?, (select code_type FROM type_mouvement where type=?), ?, ?, (SELECT id FROM exercice where status=1), (SELECT idUtilisateur from utilisateur WHERE nomUtilisateur=?))");
                stm.setString(1, "Sortie de stock pour transfert");
                stm.setString(2, (String) code_produit.getSelectedItem()); // Remplacez idProduit par l'ID du produit à transférer
                stm.setInt(3, (int) quantite.getValue()); // Remplacez quantite par la quantité à transférer
                stm.setString(4, (String) now.format(dtFormat));
                stm.setString(5, (String) typeMouvement.getSelectedItem()); // Remplacez idTypeMouvementSortie par l'ID du type de mouvement de sortie
                stm.setString(6, "Sortie");
                stm.setString(7,idMagasin1.getSelectedItem().toString()); // Remplacez idMagasinSource par l'ID du magasin source
                stm.setString(8, (String) nomCreateur.getSelectedItem()); // Remplacez idCreateur par l'ID de l'utilisateur qui effectue le transfert

                stm.executeUpdate();
                // 2. Insérer un mouvement de stock d'entrée pour le deuxième magasin

                stm = conn.prepareStatement("INSERT INTO mouvementStock (description, idProduit, quantite, dateMouvement, idTypeMouvement, sens, idMagasin, idExercice, idCreateur) " +
                    "VALUES (?, ?, ?, ?, (select code_type FROM type_mouvement where type=?), ?, ?, (SELECT id FROM exercice where status=1), (SELECT idUtilisateur from utilisateur WHERE nomUtilisateur=?))");
                stm.setString(1,"Entrée de stock pour transfert");
                stm.setString(2, (String) code_produit.getSelectedItem()); // Remplacez idProduit par l'ID du produit à transférer
                stm.setInt(3, (int) quantite.getValue()); // Remplacez quantite par la quantité à transférer
                stm.setString(4, (String) now.format(dtFormat));
                stm.setString(5, (String) typeMouvement.getSelectedItem()); // Remplacez idTypeMouvementSortie par l'ID du type de mouvement de sortie
                stm.setString(6, "Entrée");
                stm.setString(7,idMagasin2.getSelectedItem().toString()); // Remplacez idMagasinSource par l'ID du magasin source
                stm.setString(8, (String) nomCreateur.getSelectedItem()); // Remplacez idCreateur par l'ID de l'utilisateur qui effectue le transfert

                stm.executeUpdate();
                t = new Toast("Transfert effectué avec succes!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
                setEmptyForm();
                conn.commit(); // Valider la transaction
                System.out.println("Le transfert de stock a été effectué avec succès.");
                }else{
                    JOptionPane.showMessageDialog(null, "La quantité de ressource demandé est insuffisante pour ce transfert! \nQuantité dsponible: "+getQuantiteStock(),"Error", JOptionPane.OK_OPTION);
                }
            }catch(HeadlessException | SQLException ex){}
        } catch (SQLException e) {
            try {
                conn.rollback(); // En cas d'erreur, annuler la transaction
                System.out.println("La transaction a été annulée.");
            } catch (SQLException ex) {
                System.out.println("Erreur lors de l'annulation de la transaction : " + ex.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true); // Restaurer le mode de commit automatique
            } catch (SQLException e) {
            }
        }
        }else{
              JOptionPane.showMessageDialog(null, "Veuillez vérifier que tous les champs sont correctement remplis!","Message",JOptionPane.INFORMATION_MESSAGE);
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
        description.setText("");
        idMagasin1.setSelectedItem(null);
        idMagasin2.setSelectedItem(null);
        code_produit.setSelectedItem(null);
                
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
                code_produit.addItem("");
                while (resultSet.next()) {
                    code_produit.addItem(resultSet.getString("id"));
                }
                stm = conn.prepareStatement("SELECT id FROM magasin");
                resultSet = stm.executeQuery();
                idMagasin2.addItem("");
                    idMagasin1.addItem("");
                while (resultSet.next()) {
                    idMagasin2.addItem(resultSet.getString("id"));
                    idMagasin1.addItem(resultSet.getString("id"));
                }
                stm.close();
                resultSet.close();
                conn.close();
        } catch (SQLException e) {
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_save;
    private javax.swing.JComboBox<String> code_produit;
    private javax.swing.JLayeredPane containerForm;
    private javax.swing.JTextField description;
    private javax.swing.JLabel idExerciceActif;
    private javax.swing.JComboBox<String> idMagasin1;
    private javax.swing.JComboBox<String> idMagasin2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel nature;
    private javax.swing.JLabel nature1;
    private javax.swing.JLabel nature2;
    private javax.swing.JLabel nature4;
    private javax.swing.JLabel nature6;
    private javax.swing.JComboBox<String> nomCreateur;
    private javax.swing.JSpinner quantite;
    private javax.swing.JComboBox<String> typeMouvement;
    // End of variables declaration//GEN-END:variables
}
