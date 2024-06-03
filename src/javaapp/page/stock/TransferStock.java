
package javaapp.page.stock;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javaapp.component.Toast;
import javaapp.dao.ExerciceDAO;
import javaapp.dao.MagasinDAO;
import javaapp.dao.MouvementStockDAO;
import javaapp.dao.ProduitDAO;
import javaapp.dao.StockDAO;
import javaapp.dao.TypeMouvementDAO;
import javaapp.dao.UserDAO;
import javaapp.factory.DAOFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TransferStock extends javax.swing.JPanel {
private final int borderRadius = 10;
    public String idSelected;
    private String btn_save_state= "insert";
    public String datetime1;
    public LocalTime timepart;
    public JPanel pan;
    public Toast t;
    
    private final MouvementStockDAO mouveStockDAO;
    private final MagasinDAO magDAO;
    private final ProduitDAO produitDAO;
    private final TypeMouvementDAO typeDAO;
    private final ExerciceDAO exerciceDAO;
    private final UserDAO userDAO;
    private final StockDAO stockDAO;

    public TransferStock(JPanel panel) {
        
        mouveStockDAO = DAOFactory.getMouvementStockDAO();
        magDAO = DAOFactory.getMagasinDAO();
        produitDAO = DAOFactory.getProduitDAO();
        typeDAO = DAOFactory.getTypeMouvementDAO(); 
        exerciceDAO = DAOFactory.getExerciceDAO();
        userDAO = DAOFactory.getUserDAO();
        stockDAO = DAOFactory.getStockDAO();
        
        initComponents();
        setInputSelect();
        pan= panel;
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
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        containerForm = new javax.swing.JLayeredPane();
        nature4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        nature = new javax.swing.JLabel();
        typeMouvement = new javax.swing.JComboBox<>();
        quantite = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nature2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        refMagasin1 = new javax.swing.JComboBox<>();
        idExerciceActif = new javax.swing.JLabel();
        refProduit = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        btn_save = new javaapp.component.ButtonRadius();
        btn_cancel = new javaapp.component.ButtonRadius();
        nomCreateur = new javax.swing.JComboBox<>();
        nature6 = new javax.swing.JLabel();
        refMagasin2 = new javax.swing.JComboBox<>();
        nature1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        description = new javax.swing.JTextPane();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 850));

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

        nature2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature2.setForeground(new java.awt.Color(51, 51, 51));
        nature2.setText("Magasin de prélèvement");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Créateur");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Reférence du produit");

        idExerciceActif.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        idExerciceActif.setForeground(new java.awt.Color(248, 91, 50));
        idExerciceActif.setText("00012");

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
        btn_save.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
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
        btn_cancel.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
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

        nature6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature6.setForeground(new java.awt.Color(51, 51, 51));
        nature6.setText("Magasin de dépôt");

        nature1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature1.setForeground(new java.awt.Color(51, 51, 51));
        nature1.setText("Description");

        jScrollPane1.setViewportView(description);

        containerForm.setLayer(nature4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(typeMouvement, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(quantite, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(refMagasin1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(idExerciceActif, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(refProduit, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(btn_save, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(btn_cancel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nomCreateur, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(refMagasin2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout containerFormLayout = new javax.swing.GroupLayout(containerForm);
        containerForm.setLayout(containerFormLayout);
        containerFormLayout.setHorizontalGroup(
            containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerFormLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(containerFormLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jLabel5))
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
                            .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nature6)
                                    .addComponent(nomCreateur, 0, 820, Short.MAX_VALUE)
                                    .addComponent(refMagasin1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nature2)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(quantite, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(refProduit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(refMagasin2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(containerFormLayout.createSequentialGroup()
                            .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(containerFormLayout.createSequentialGroup()
                            .addComponent(nature4)
                            .addGap(18, 18, 18)
                            .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(typeMouvement, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1)
                                .addGroup(containerFormLayout.createSequentialGroup()
                                    .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nature)
                                        .addComponent(nature1))
                                    .addGap(737, 737, 737)))))
                    .addComponent(jLabel14))
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
                .addComponent(refMagasin1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nature6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refMagasin2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refProduit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(3, 3, 3)
                .addComponent(quantite, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nature4)
                    .addComponent(nature))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typeMouvement, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nature1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
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
        return stockDAO.findByProduitMagasin(refProduit.getSelectedItem().toString(), refMagasin1.getSelectedItem().toString()).getQuantiteStock();
   }
    private boolean validatedForm(){
        return (typeMouvement.getSelectedItem() != null || !description.getText().isEmpty()) && (int) quantite.getValue() != 0 && refMagasin1.getSelectedItem().toString() != null && refMagasin2.getSelectedItem().toString() != null && nomCreateur.getSelectedItem() != null; 
    }
    
    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        if(validatedForm()){
            if((getQuantiteStock() >= (int)quantite.getValue())){
                
                // 2. Insérer un mouvement de stock de sortie pour le premier magasin
                javaapp.bean.MouvementStock mouvementStock = new javaapp.bean.MouvementStock();
                mouvementStock.setDescription(description.getText());
                mouvementStock.setIdProduit(produitDAO.find(refProduit.getSelectedItem().toString()).getId());
                mouvementStock.setQuantite((int) quantite.getValue());
                mouvementStock.setDateMouvement(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                if(!typeMouvement.getSelectedItem().toString().trim().isEmpty())mouvementStock.setIdTypeMouvement(typeDAO.find(typeMouvement.getSelectedItem().toString()).getCodeType());
                mouvementStock.setIdMagasin(magDAO.findByRef(refMagasin1.getSelectedItem().toString()).getId());
                if (mouveStockDAO.createOUT(mouvementStock)) {
                    mouvementStock.setIdMagasin(magDAO.findByRef(refMagasin2.getSelectedItem().toString()).getId());
                    if(mouveStockDAO.createIN(mouvementStock)) new Toast("Transfert effectué avec succes!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7).showtoast();
                    setEmptyForm();
                }
            }else{
                    JOptionPane.showMessageDialog(null, "La quantité de ressource demandé est insuffisante pour ce transfert! \nQuantité dsponible: "+getQuantiteStock(),"Error", JOptionPane.OK_OPTION);
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
        refMagasin1.setSelectedItem(null);
        refMagasin2.setSelectedItem(null);
        refProduit.setSelectedItem(null);
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
        refMagasin1.addItem("");
        refMagasin2.addItem("");
        for(javaapp.bean.Magasin mag : magasins) {
            refMagasin1.addItem(mag.getRefMagasin());
            refMagasin2.addItem(mag.getRefMagasin());
        }
        
        List<javaapp.bean.Produit> produits = produitDAO.select();
        refProduit.addItem("");
        for(javaapp.bean.Produit produit : produits) {
            refProduit.addItem(produit.getRefProduit());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_save;
    private javax.swing.JLayeredPane containerForm;
    private javax.swing.JTextPane description;
    private javax.swing.JLabel idExerciceActif;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nature;
    private javax.swing.JLabel nature1;
    private javax.swing.JLabel nature2;
    private javax.swing.JLabel nature4;
    private javax.swing.JLabel nature6;
    private javax.swing.JComboBox<String> nomCreateur;
    private javax.swing.JSpinner quantite;
    private javax.swing.JComboBox<String> refMagasin1;
    private javax.swing.JComboBox<String> refMagasin2;
    private javax.swing.JComboBox<String> refProduit;
    private javax.swing.JComboBox<String> typeMouvement;
    // End of variables declaration//GEN-END:variables
}
