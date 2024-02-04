package javaapp;

import java.awt.Color;
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
     public String url = "jdbc:sqlite:caisse.db";
     String sql="select * from utilisateur where nomUtilisateur=? and password=?";
//     PreparedStatement prepare = null;
     ResultSet result;
     
 
public Login() {
        initComponents();
        connectiondb();
        setBackground(new Color(0,0,0,0));
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

        panelBorderRound1 = new javaapp.component.PanelBorderRound();
        jLabel1 = new javax.swing.JLabel();
        text = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnLogin = new javaapp.component.ButtonRadius();
        username = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        btn_close = new javaapp.component.ButtonRadius();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelBorderRound1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/login.png"))); // NOI18N

        text.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 48)); // NOI18N
        text.setForeground(new java.awt.Color(0, 50, 63));
        text.setText("Bienvenue !");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Nom utilisateur");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Mot de passe ");

        btnLogin.setBackground(new java.awt.Color(255, 105, 82));
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Se connecter");
        btnLogin.setBorderColor(new java.awt.Color(255, 105, 82));
        btnLogin.setColor(new java.awt.Color(255, 105, 82));
        btnLogin.setColorClick(new java.awt.Color(255, 105, 82));
        btnLogin.setColorOver(new java.awt.Color(255, 95, 69));
        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLogin.setRadius(20);
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

        btn_close.setBackground(new java.awt.Color(255, 255, 255));
        btn_close.setBorder(null);
        btn_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/close2.png"))); // NOI18N
        btn_close.setBorderColor(new java.awt.Color(255, 255, 255));
        btn_close.setColor(new java.awt.Color(255, 255, 255));
        btn_close.setColorClick(new java.awt.Color(204, 204, 204));
        btn_close.setColorOver(new java.awt.Color(246, 243, 255));
        btn_close.setRadius(50);
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Authentification");

        javax.swing.GroupLayout panelBorderRound1Layout = new javax.swing.GroupLayout(panelBorderRound1);
        panelBorderRound1.setLayout(panelBorderRound1Layout);
        panelBorderRound1Layout.setHorizontalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel4))
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(text, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(178, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_close, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelBorderRound1Layout.setVerticalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addComponent(btn_close, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(text, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(112, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
  
                  //requete non preparé  dispose();
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

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
        dispose();
    }//GEN-LAST:event_btn_closeActionPerformed

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
    private javaapp.component.ButtonRadius btn_close;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javaapp.component.PanelBorderRound panelBorderRound1;
    private javax.swing.JPasswordField password;
    private javax.swing.JLabel text;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
