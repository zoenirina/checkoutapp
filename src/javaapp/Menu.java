
package javaapp;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javaapp.event.EventMenuSelected;

public class Menu extends javax.swing.JPanel {
    private EventMenuSelected event; 
    
    public void addEventMenuSelected(EventMenuSelected event){
        this.event= event;
        menuList1.addEventMenuSelected(event);
    }
    
    public Menu() {
        initComponents();
        setOpaque(false); 
        menuList1.setOpaque(false); 
        init();
    }
private void init(){
menuList1.addItem(new MenuModel("tableau","Tableau de bord",MenuModel.MenuType.MENU));
menuList1.addItem(new MenuModel("caisse","Transaction",MenuModel.MenuType.MENU));
menuList1.addItem(new MenuModel("commande", "Commande", MenuModel.MenuType.MENU));
menuList1.addItem(new MenuModel("compte", "Client", MenuModel.MenuType.MENU));

menuList1.addItem(new MenuModel("stat", "Stock", MenuModel.MenuType.MENU));

menuList1.addItem(new MenuModel("produit", "Produit", MenuModel.MenuType.MENU));
menuList1.addItem(new MenuModel("magas", "Magasin", MenuModel.MenuType.MENU));
menuList1.addItem(new MenuModel("exercice", "Exercice", MenuModel.MenuType.MENU));
//menuList1.addItem(new MenuModel("compte", "Compte",MenuModel.MenuType.MENU));
//menuList1.addItem(new MenuModel("groupe", "Groupe",MenuModel.MenuType.MENU));
menuList1.addItem(new MenuModel("parametre", "Paramètres",MenuModel.MenuType.MENU));//logout1
menuList1.addItem(new MenuModel("logout1", "Deconnexion",MenuModel.MenuType.MENU));

}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        menuList1 = new javaapp.MenuList<>();

        setPreferredSize(new java.awt.Dimension(240, 706));

        jLabel1.setBackground(new java.awt.Color(241, 241, 241));
        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(33, 255, 164));
        jLabel1.setText("ZOOM");

        menuList1.setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuList1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addComponent(menuList1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(202, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
protected void paintChildren(Graphics grphcs){
    Graphics2D g2 =(Graphics2D) grphcs;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    GradientPaint g = new GradientPaint(0,0,Color.decode("#080436"),0, getHeight(),Color.decode("#0B4151"));
    // GradientPaint g = new GradientPaint(0,0,Color.decode("#0B4151"),0, getHeight(),Color.decode("#0B4151")); //GREENBLUE
  //  GradientPaint g = new GradientPaint(0,0,Color.decode("#122b3e"),0, getHeight(),Color.decode("#122b3e")); // NICE ONE  BLUE
  // GradientPaint g = new GradientPaint(0,0,Color.decode("#E4584B"),0, getHeight(),Color.decode("#E4584B")); //ORANGE
    g2.setPaint(g);
    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
    g2.fillRect(getWidth()-20, 0, getWidth(), getHeight());
super.paintChildren(grphcs);

}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javaapp.MenuList<String> menuList1;
    // End of variables declaration//GEN-END:variables
}
