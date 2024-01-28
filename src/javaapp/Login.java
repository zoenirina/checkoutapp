package javaapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame {
      Connection conn = null;
     public String url = "jdbc:sqlite://C:/Users/ASUS/Documents/NetBeansProjects/JavaApp/caisse.db";
     String sql="select * from utilisateur where nomUtilisateur=? and password=?";
//     PreparedStatement prepare = null;
     ResultSet result;
     
 
public Login() {
        initComponents();
        connectiondb();
    }

    public void connectiondb(){
        try {
             Class.forName("org.sqlite.JDBC");
//              JOptionPane.showMessageDialog(null, "forme reussie","Sucess",JOptionPane.INFORMATION_MESSAGE); 
         try {
            conn = DriverManager.getConnection(url);
//             JOptionPane.showMessageDialog(null, "driver reussie","Sucess",JOptionPane.INFORMATION_MESSAGE); 
        } catch (SQLException ex) {
             Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
   }  catch (ClassNotFoundException ex) { 
             Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorderRound1 = new javaapp.PanelBorderRound();
        jLabel1 = new javax.swing.JLabel();
        text = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        exit_btn = new javax.swing.JButton();
        btnLogin = new javaapp.component.ButtonRadius();
        username = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelBorderRound1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/caisselogin.png"))); // NOI18N

        text.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 48)); // NOI18N
        text.setForeground(new java.awt.Color(0, 50, 63));
        text.setText("Bienvenue !");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Nom utilisateur");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Mot de passe ");

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 2, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Mot de passe oublié? ");

        exit_btn.setText("exit");
        exit_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exit_btnActionPerformed(evt);
            }
        });

        btnLogin.setBackground(new java.awt.Color(255, 204, 255));
        btnLogin.setForeground(new java.awt.Color(102, 0, 51));
        btnLogin.setText("Se connecter");
        btnLogin.setBorderColor(new java.awt.Color(102, 0, 51));
        btnLogin.setColor(new java.awt.Color(255, 204, 255));
        btnLogin.setColorClick(new java.awt.Color(102, 0, 0));
        btnLogin.setColorOver(new java.awt.Color(153, 0, 51));
        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLogin.setRadius(50);
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoginMouseClicked(evt);
            }
        });
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorderRound1Layout = new javax.swing.GroupLayout(panelBorderRound1);
        panelBorderRound1.setLayout(panelBorderRound1Layout);
        panelBorderRound1Layout.setHorizontalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addGap(147, 147, 147)
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel4))))))
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(text, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(129, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(exit_btn))
        );
        panelBorderRound1Layout.setVerticalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(username)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelBorderRound1Layout.createSequentialGroup()
                            .addGap(61, 61, 61)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(exit_btn)))
                .addGap(44, 44, 44)
                .addComponent(text, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(187, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorderRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorderRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exit_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exit_btnActionPerformed
        dispose();
    }//GEN-LAST:event_exit_btnActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseClicked
              
            try (PreparedStatement prepare = conn.prepareStatement(sql)){ 
            prepare.setString(1, username.getText());
            prepare.setString(2,new String(password.getPassword()));

              result =  prepare.executeQuery();    
              if(result.next()){
                   JOptionPane.showMessageDialog(null, "Bravo, vous avez réussie","Sucess",JOptionPane.INFORMATION_MESSAGE); 
                   new Dashboard().setVisible(true);
                   this.setVisible(false);
                  }else{
                       JOptionPane.showMessageDialog(null, "Nom d'utilisateur ou mot de passe incorrect !, acces auto","Error",JOptionPane.INFORMATION_MESSAGE);  
                  }
      
          } catch (SQLException ex) {
              Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Nom d'utilisateur ou mot de passe incorrect !, acces auto","Error",JOptionPane.INFORMATION_MESSAGE);  
          }
  
                  //requete non preparé
//        try { 
//            sql +="where nomUtilisateur='"+username.getText()+"' and password='"+password.getPassword().toString()+"'";
////            //prepared req
////            prepare = conn.prepareStatement(sql);
////            prepare.setString(1, username.getText());
////            prepare.setString(2, password.getPassword().toString());
//            
////              st1 = conn.createStatement();
////              result =  st1.executeQuery(sql);
//              
//              while(result.next()){
//                  if(username.getText() == result.getString("nomUtilisateur") && password.getPassword().toString() == result.getString("password")){
//                   JOptionPane.showMessageDialog(null, "Brav, vous avez réussie","Sucess",JOptionPane.INFORMATION_MESSAGE);  
//                  }else{
//                       JOptionPane.showMessageDialog(null, "Nom d'utilisateur ou mot de passe incorrect !, acces auto","Error",JOptionPane.INFORMATION_MESSAGE);  
//                  }
//      }
//          } catch (SQLException ex) {
//              Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//          }
        
//        JOptionPane.showMessageDialog(null, "aizoooooooo","Error",JOptionPane.INFORMATION_MESSAGE);  
//        try {
//             Class.forName("org.sqlite.JDBC");
//             JOptionPane.showMessageDialog(null, "connexion forname reussie","success",JOptionPane.INFORMATION_MESSAGE); 
//
//    try {
//        conn = DriverManager.getConnection(url);
//         JOptionPane.showMessageDialog(null, "connexion drivermanager reussie","success",JOptionPane.INFORMATION_MESSAGE); 
//    } catch (SQLException ex) {
//        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//         JOptionPane.showMessageDialog(null, "connexion drivermanager échoué","success",JOptionPane.INFORMATION_MESSAGE); 
//    }
//   
//      System.out.print("connexion success !");
//      JOptionPane.showMessageDialog(null, "Votre username est"+username.getText()+"et votre mot de passe est "+password.getPassword().toString(),"Error",JOptionPane.INFORMATION_MESSAGE); 
//}  catch (ClassNotFoundException ex) { 
//             Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//        JOptionPane.showMessageDialog(null, "classname non connecté"+ ex,"Error",JOptionPane.INFORMATION_MESSAGE);  
//} 


    }//GEN-LAST:event_btnLoginMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btnLogin;
    private javax.swing.JButton exit_btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javaapp.PanelBorderRound panelBorderRound1;
    private javax.swing.JPasswordField password;
    private javax.swing.JLabel text;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
