package javaapp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javaapp.event.EventMenuSelected;
import javaapp.page.Home;
import javaapp.page.Caisse;
import javaapp.page.Groupe;
import javaapp.page.Form3;
import javaapp.page.Utilisateur;
import javaapp.page.Personnel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainFrame extends javax.swing.JFrame {


    
    public MainFrame() {
        initComponents();
        setBackground(new Color(0,0,0,0));
        initMoving(this);
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
                    setForm(new Home());
                    title.setText(new Home().title);
                    break;
                case 1:
                    setForm(new Caisse());
                    title.setText(new Caisse().title);
                    break;
                case 2:
                    setForm(new Personnel());
                    title.setText(new Personnel().title);
                    break;
                case 3:
                    setForm(new Utilisateur());
                    title.setText(new Utilisateur().title);
                    break;
                case 4:
                    setForm(new Groupe());
                    title.setText(new Groupe().title);
//             form4.newUser=true;
                    break;
                case 5:
                    setForm(new Caisse());
                    break;
                default:
                    break;
            }
        }
      
        });
        setForm(new Home());
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
        title = new javax.swing.JLabel();
        btn_resize = new javax.swing.JLabel();
        btnlogout = new javax.swing.JLabel();
        datenow = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        mainpanel.setBackground(new java.awt.Color(255, 255, 255));
        mainpanel.setBorder(new javax.swing.border.MatteBorder(null));

        header.setBackground(new java.awt.Color(255, 255, 255));

        title.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        title.setForeground(new java.awt.Color(8, 4, 54));
        title.setText("Acceuil");

        btn_resize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/resize1.png"))); // NOI18N
        btn_resize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_resizeMouseClicked(evt);
            }
        });

        btnlogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/logout.png"))); // NOI18N
        btnlogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnlogoutMouseClicked(evt);
            }
        });

        datenow.setFont(new java.awt.Font("Verdana", 2, 14)); // NOI18N

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 444, Short.MAX_VALUE)
                .addComponent(datenow, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_resize)
                .addGap(30, 30, 30)
                .addComponent(btnlogout, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnlogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(title)
                        .addComponent(datenow, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_resize, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
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

    private void btn_resizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_resizeMouseClicked

        this.setPreferredSize(new Dimension(1920,1060));
        this.setSize(new Dimension(1920,1060));
        mainpanel.setPreferredSize(new Dimension(1920,1060));
        mainpanel.setSize(new Dimension(1920,1060));
        mainPanel.setSize(new Dimension(this.getWidth()-menu.getWidth(),getHeight()-header.getHeight()));
        header.setSize(new Dimension(this.getWidth()-menu.getWidth(),400));
        this.setLocationRelativeTo(null);
        menu.setSize(new Dimension(menu.getWidth(),this.getHeight()));
        this.setLayout(null);
    }//GEN-LAST:event_btn_resizeMouseClicked

     private int x;
    private int y;
    
    public void initMoving(JFrame fram){
    header.addMouseListener(new MouseAdapter(){
     @Override
     public void mousePressed(MouseEvent me){
         x = me.getX();
         y = me.getY();
    }
    });
    
    header.addMouseMotionListener(new MouseMotionAdapter(){
        @Override
        public void mouseDragged(MouseEvent me){
            fram.setLocation(me.getXOnScreen()-x, me.getYOnScreen()-y);
        }
    });
    }

    public static void main(String args[]) {
 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btn_resize;
    private javax.swing.JLabel btnlogout;
    private javax.swing.JLabel datenow;
    private javaapp.component.PanelBorderRound header;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel mainPanel;
    private javaapp.component.PanelBorderRound mainpanel;
    private javaapp.Menu menu;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
