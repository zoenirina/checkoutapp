package javaapp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        DateFormat dateFormat= new SimpleDateFormat("EEEE, dd MMMM yyyy");
        String formattedDate = dateFormat.format(new Date());
        datenow.setText(formattedDate);
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

        mainpanel = new javaapp.component.PanelBorderRound();
        menu = new javaapp.Menu();
        header = new javaapp.component.PanelBorderRound();
        jPanel1 = new javax.swing.JPanel();
        btnlogout = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        datenow = new javax.swing.JLabel();
        btn_agrandir = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        mainpanel.setBackground(new java.awt.Color(255, 255, 255));
        mainpanel.setBorder(new javax.swing.border.MatteBorder(null));

        header.setBackground(new java.awt.Color(255, 255, 255));

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

        datenow.setFont(new java.awt.Font("Verdana", 2, 14)); // NOI18N

        btn_agrandir.setText("jButton1");
        btn_agrandir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agrandirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 406, Short.MAX_VALUE)
                .addComponent(btn_agrandir)
                .addGap(46, 46, 46)
                .addComponent(datenow, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnlogout, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnlogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_agrandir))
                    .addComponent(datenow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane3.setBackground(new java.awt.Color(219, 229, 255));
        jScrollPane3.setBorder(null);

        mainPanel.setBackground(new java.awt.Color(255, 228, 255));
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new java.awt.BorderLayout());
        jScrollPane3.setViewportView(mainPanel);

        javax.swing.GroupLayout mainpanelLayout = new javax.swing.GroupLayout(mainpanel);
        mainpanel.setLayout(mainpanelLayout);
        mainpanelLayout.setHorizontalGroup(
            mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainpanelLayout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addComponent(jScrollPane3))
            .addGroup(mainpanelLayout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainpanelLayout.setVerticalGroup(
            mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainpanelLayout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3))
            .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void btn_agrandirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agrandirActionPerformed
        
        this.setPreferredSize(new Dimension(1920,1060));
        this.setSize(new Dimension(1920,1060));
        mainpanel.setPreferredSize(new Dimension(1920,1060));
        mainpanel.setSize(new Dimension(1920,1060));
        mainPanel.setSize(new Dimension(this.getWidth()-menu.getWidth(),getHeight()-header.getHeight()));
        header.setSize(new Dimension(this.getWidth()-menu.getWidth(),400));
        this.setLocationRelativeTo(null);
        menu.setSize(new Dimension(menu.getWidth(),this.getHeight()));
        this.setLayout(null);
        
        
    }//GEN-LAST:event_btn_agrandirActionPerformed

    public static void main(String args[]) {
 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agrandir;
    private javax.swing.JLabel btnlogout;
    private javax.swing.JLabel datenow;
    private javaapp.component.PanelBorderRound header;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel mainPanel;
    private javaapp.component.PanelBorderRound mainpanel;
    private javaapp.Menu menu;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
