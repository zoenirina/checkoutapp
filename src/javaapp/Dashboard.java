package javaapp;

import java.awt.Color;
import javaapp.event.EventMenuSelected;
import javaapp.page.HomePage;
import javaapp.page.Page1;
import javaapp.page.Form2;
import javaapp.page.Form3;
import javaapp.page.Form4;
import javaapp.page.Form7;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class Dashboard extends javax.swing.JFrame {


    
    public Dashboard() {
        initComponents();
        setBackground(new Color(0,0,0,0));
//        Form4 form4= new Form4();
        menu.addEventMenuSelected(new EventMenuSelected(){
        @Override
        public void selected(int index){
//             JOptionPane.showMessageDialog( null, "index:"+index,"Sucess",JOptionPane.INFORMATION_MESSAGE);
            switch (index) {
                case 0:
                    setForm(new HomePage());
                    title.setText(new HomePage().title);
                    break;
                case 1:
                    setForm(new Page1());
                    title.setText(new Page1().title);
                    break;
                case 2:
                    setForm(new Form7());
                    title.setText(new Form7().title);
                    break;
                case 3:
                    setForm(new Form4());
                    title.setText(new Form4().title);
                    break;
                case 4:
                    setForm(new Form2());
                    title.setText(new Form2().title);
//             form4.newUser=true;
                    break;
                case 5:
                    setForm(new Page1());
                    break;
                default:
                    break;
            }
        }
      
        });
        setForm(new HomePage());
    }
    
    private void setForm(JComponent com){
    mainPanel.removeAll();
    mainPanel.add(com);
    mainPanel.repaint();
    mainPanel.revalidate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorderRound2 = new javaapp.component.PanelBorderRound();
        menu = new javaapp.Menu();
        panelBorderRound3 = new javaapp.component.PanelBorderRound();
        jPanel1 = new javax.swing.JPanel();
        btnlogout = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelBorderRound2.setBackground(new java.awt.Color(248, 248, 248));
        panelBorderRound2.setBorder(new javax.swing.border.MatteBorder(null));
        panelBorderRound2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelBorderRound2.add(menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 930));

        panelBorderRound3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnlogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/logout.png"))); // NOI18N
        btnlogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnlogoutMouseClicked(evt);
            }
        });

        title.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        title.setForeground(new java.awt.Color(8, 4, 54));
        title.setText("Acceuil");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 772, Short.MAX_VALUE)
                .addComponent(btnlogout, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnlogout, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelBorderRound3Layout = new javax.swing.GroupLayout(panelBorderRound3);
        panelBorderRound3.setLayout(panelBorderRound3Layout);
        panelBorderRound3Layout.setHorizontalGroup(
            panelBorderRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelBorderRound3Layout.setVerticalGroup(
            panelBorderRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelBorderRound2.add(panelBorderRound3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 1130, 60));

        jScrollPane3.setBackground(new java.awt.Color(219, 229, 255));
        jScrollPane3.setBorder(null);

        mainPanel.setBackground(new java.awt.Color(255, 228, 255));
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new java.awt.BorderLayout());
        jScrollPane3.setViewportView(mainPanel);

        panelBorderRound2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 1120, 870));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorderRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorderRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnlogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnlogoutMouseClicked
       int result=JOptionPane.showConfirmDialog(null, "Etes vous sure", "confirmation",JOptionPane.YES_NO_OPTION);
       if(result== JOptionPane.YES_OPTION){
       this.dispose();
       new Login().setVisible(true);
       }
    }//GEN-LAST:event_btnlogoutMouseClicked

    public static void main(String args[]) {
 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnlogout;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel mainPanel;
    private javaapp.Menu menu;
    private javaapp.component.PanelBorderRound panelBorderRound2;
    private javaapp.component.PanelBorderRound panelBorderRound3;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
