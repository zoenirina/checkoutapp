package javaapp;

import java.awt.Color;
import java.awt.Frame;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javaapp.dao.UserDAO;
import javaapp.event.EventMenuSelected;
import javaapp.factory.DAOFactory;
import javaapp.page.Achat.Achat;
import javaapp.page.PageList.Home;
import javaapp.page.Caisse.Caisse;
import javaapp.page.PageList.Client;
import javaapp.page.PageList.Fournisseur;
import javaapp.page.PageList.Magasin;
import javaapp.page.parametre.Parametre;
import javaapp.page.exercice.Exercice1;
import javaapp.page.stock.Stock;
import javaapp.page.exercice.TestExercice;
import javaapp.page.vente.Vente;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class MainFrame extends javax.swing.JFrame {
    
    public static UserType usertype1;
    private UserDAO userDao;
     
    public MainFrame(UserType usertype) {
        initComponents();
        setBackground(new Color(0,0,0,0));
        userDao = DAOFactory.getUserDAO();
        usertype1 = usertype;
        DateFormat dateFormat= new SimpleDateFormat("EEEE, dd MMMM yyyy");
        String formattedDate = dateFormat.format(new Date());
        datenow.setText(formattedDate);
        
        menu.addEventMenuSelected(new EventMenuSelected(){
        @Override
        public void selected(int index){
            switch (index) {
                case 0:
                    setForm(new Home(usertype));
                    title.setText("Acceuil");
                    break;
                case 1:
                    setForm(new Caisse(usertype1));
                    title.setText("Caisse");
                    break;
                case 2:
                    setForm(new Vente());
                    title.setText("Gestion de vente");
                    break;
                case 3:
                    setForm(new Client(usertype1));
                    title.setText("Client");
                    break;
                case 4:
                    setForm(new Achat());
                    title.setText("Achat");
                    break;
                case 5:
                    setForm(new Fournisseur(usertype1));
                    title.setText("Fournisseur");
                    break;
                case 6: 
                    setForm(new Stock(usertype1));
                    title.setText("Gestion de stock");
                    break;
                case 7:
                    setForm(new javaapp.page.PageList.Produit(usertype1));
                    title.setText("Produits");
                    break;
                case 8:
                    setForm(new Magasin(usertype1));
                    title.setText("Entrepôt et Magasin");
                    break;
                case 9:
                    switch(usertype1.getHierarchie()){
                        
                    case 1:
                        setForm(new TestExercice(mainpanel));
                        break;
                    case 2:
                        setForm(new Exercice1());
                        break;
                    }
                    
                    title.setText("Exercice");
                    break;
                case 10:
                        setForm(new Parametre(usertype1));
                        title.setText("Paramètres");
                    break;
                case 11:
                        
                    int result=JOptionPane.showConfirmDialog(null, "Etes vous sure de vouloir vous déconnecter?", "confirmation",JOptionPane.YES_NO_OPTION);
                        if(result== JOptionPane.YES_OPTION){
                        userDao.disableStatus();
                        deconnexion();
                        new Login().setVisible(true);
                        }    
                    
                    break;
                default:
                    break;
            }
        }
      
        });
        setForm(new Home(usertype));
    }
    
    private void setForm(JComponent com){
        mainPanel.removeAll();
        mainPanel.add(com);
        mainPanel.repaint();
        mainPanel.revalidate();
    }
    
    private void deconnexion(){
        this.dispose();
    }
  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainpanel = new javaapp.component.PanelBorderRound();
        menu = new javaapp.Menu();
        header = new javaapp.component.PanelBorderRound();
        title = new javax.swing.JLabel();
        btn_exit = new javax.swing.JLabel();
        datenow = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        mainpanel.setBackground(new java.awt.Color(255, 255, 255));
        mainpanel.setBorder(new javax.swing.border.MatteBorder(null));

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                headerMouseDragged(evt);
            }
        });
        header.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                headerMousePressed(evt);
            }
        });

        title.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        title.setForeground(new java.awt.Color(0, 0, 102));
        title.setText("Acceuil");

        btn_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/close.png"))); // NOI18N
        btn_exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_exitMouseClicked(evt);
            }
        });

        datenow.setFont(new java.awt.Font("Verdana", 2, 14)); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/reduire1.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 457, Short.MAX_VALUE)
                .addComponent(datenow, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btn_exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(title)
                        .addComponent(datenow, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
            .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, 835, Short.MAX_VALUE)
            .addGroup(mainpanelLayout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_exitMouseClicked
        userDao.disableStatus();   
       this.dispose();
    }//GEN-LAST:event_btn_exitMouseClicked

 //déplacer la fenêtre de l'application grâce à l'header
    private int x,xx;
    private int y,yy;
    
    private void headerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMousePressed
        x = evt.getX()+247;
         y = evt.getY();
    }//GEN-LAST:event_headerMousePressed

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMouseDragged
         xx= evt.getXOnScreen();
         yy= evt.getYOnScreen();
         setLocation(xx-x,yy-y);
    }//GEN-LAST:event_headerMouseDragged

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        setState(Frame.ICONIFIED);
    }//GEN-LAST:event_jLabel1MouseClicked

    public static void main(String args[]) {
 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                UserType usertype = null;
                new MainFrame(usertype1).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btn_exit;
    private javax.swing.JLabel datenow;
    private javaapp.component.PanelBorderRound header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel mainPanel;
    private javaapp.component.PanelBorderRound mainpanel;
    private javaapp.Menu menu;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
