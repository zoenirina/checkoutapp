
package javaapp.page.parametre;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalTime;
import java.util.Arrays;
import javaapp.bean.Config;
import javaapp.component.Toast;
import javaapp.dao.ConfigDAO;
import javaapp.factory.DAOFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Configuration extends javax.swing.JPanel {
    private final int borderRadius = 10;
    public String datetime1;
    public LocalTime timepart;
    public JPanel pan;
    public Toast t;
    private Config conf = new Config();
    private final ConfigDAO configDAO;
 
    public Configuration(JPanel panel) {
        initComponents();
        configDAO = DAOFactory.getConfigDAO();
        pan= panel;// TOUTES les sortie du caisse dont l'exercice est actif et ordonnée solon la date
        populateField();
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
        nature = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Devise = new javax.swing.JTextField();
        nature2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btn_save = new javaapp.component.ButtonRadius();
        btn_cancel = new javaapp.component.ButtonRadius();
        nature6 = new javax.swing.JLabel();
        DBName = new javax.swing.JTextField();
        DBpath = new javax.swing.JTextField();
        DBuserName = new javax.swing.JTextField();
        nature7 = new javax.swing.JLabel();
        DBpassWord = new javax.swing.JPasswordField();
        nature8 = new javax.swing.JLabel();
        CodeRecuperation = new javax.swing.JPasswordField();
        PathSavePDF = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 850));

        nature4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature4.setForeground(new java.awt.Color(51, 51, 51));
        nature4.setText("Devis");

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("Fichier");

        nature.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature.setForeground(new java.awt.Color(51, 51, 51));
        nature.setText("Libelle");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Base de donnée");

        Devise.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Devise.setForeground(new java.awt.Color(102, 102, 102));

        nature2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature2.setForeground(new java.awt.Color(51, 51, 51));
        nature2.setText("Chemin d'emplacement");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Nom de la base de données");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Chemin d'emplacement des fichiers (par défaut)");

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

        nature6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature6.setForeground(new java.awt.Color(51, 51, 51));
        nature6.setText("Nom utilisateur");

        DBName.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N

        nature7.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature7.setForeground(new java.awt.Color(51, 51, 51));
        nature7.setText("Mot de passe");

        nature8.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nature8.setForeground(new java.awt.Color(51, 51, 51));
        nature8.setText("Code de récupération");

        containerForm.setLayer(nature4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(Devise, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(btn_save, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(btn_cancel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(DBName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(DBpath, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(DBuserName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(DBpassWord, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(nature8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(CodeRecuperation, javax.swing.JLayeredPane.DEFAULT_LAYER);
        containerForm.setLayer(PathSavePDF, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout containerFormLayout = new javax.swing.GroupLayout(containerForm);
        containerForm.setLayout(containerFormLayout);
        containerFormLayout.setHorizontalGroup(
            containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerFormLayout.createSequentialGroup()
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(containerFormLayout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(DBpath))
                    .addGroup(containerFormLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(DBName, javax.swing.GroupLayout.PREFERRED_SIZE, 779, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(53, 53, 53))
            .addGroup(containerFormLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(containerFormLayout.createSequentialGroup()
                        .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(PathSavePDF, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(containerFormLayout.createSequentialGroup()
                            .addGap(167, 167, 167)
                            .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(nature6)
                                .addComponent(nature2)
                                .addComponent(nature7)
                                .addComponent(jLabel6)
                                .addComponent(nature8)
                                .addComponent(nature)
                                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(CodeRecuperation, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
                                    .addComponent(DBpassWord, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DBuserName, javax.swing.GroupLayout.Alignment.LEADING))
                                .addComponent(Devise, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(nature4)
                        .addGroup(containerFormLayout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addGap(118, 118, 118)
                            .addComponent(jLabel2))))
                .addGap(51, 51, 51))
        );
        containerFormLayout.setVerticalGroup(
            containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerFormLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DBName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nature2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DBpath, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(nature6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DBuserName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(nature7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DBpassWord, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(nature8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CodeRecuperation, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PathSavePDF, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nature4)
                    .addComponent(nature))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Devise, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(85, Short.MAX_VALUE))
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
    
    private void populateField(){
        conf = configDAO.find(1);
        DBuserName.setText(conf.getDBuserName());
        DBpassWord.setText(conf.getDBpassWord()); // Attention à la gestion des mots de passe
        DBpath.setText(conf.getDBpath());
        CodeRecuperation.setText(conf.getCodeRecuperation()); // Idem pour la gestion sécurisée
        DBName.setText(conf.getDBName());
        PathSavePDF.setText(conf.getPathSavePDF());
        Devise.setText(conf.getDevise());
        
    }
    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed

            conf.setDBuserName(DBuserName.getText());
            conf.setDBpassWord(Arrays.toString(DBpassWord.getPassword()));
            conf.setDBpath(DBpath.getText());
            conf.setCodeRecuperation(Arrays.toString(CodeRecuperation.getPassword())); 
            conf.setDBName(DBName.getText());
            conf.setPathSavePDF(PathSavePDF.getText()); 
            conf.setDevise(Devise.getText());
          
            if (configDAO.update(conf)){
                JOptionPane.showMessageDialog(null, "Modification enregistrée","Succès",JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Echec de l'enregistrement","Error",JOptionPane.ERROR_MESSAGE);
            }
            populateField();
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        populateField();
    }//GEN-LAST:event_btn_cancelActionPerformed

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField CodeRecuperation;
    private javax.swing.JTextField DBName;
    private javax.swing.JPasswordField DBpassWord;
    private javax.swing.JTextField DBpath;
    private javax.swing.JTextField DBuserName;
    private javax.swing.JTextField Devise;
    private javax.swing.JTextField PathSavePDF;
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_save;
    private javax.swing.JLayeredPane containerForm;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel nature;
    private javax.swing.JLabel nature2;
    private javax.swing.JLabel nature4;
    private javax.swing.JLabel nature6;
    private javax.swing.JLabel nature7;
    private javax.swing.JLabel nature8;
    // End of variables declaration//GEN-END:variables
}
