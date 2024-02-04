
package javaapp;

import javaapp.MenuModel;
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
menuList1.addItem(new MenuModel("14","Acceuil",MenuModel.MenuType.MENU));
//menuList1.addItem(new MenuModel("", "",MenuModel.MenuType.EMPTY));
//
//menuList1.addItem(new MenuModel("", "Vente",MenuModel.MenuType.TITLE));
menuList1.addItem(new MenuModel("8","Caisse",MenuModel.MenuType.MENU));
menuList1.addItem(new MenuModel("14","Personnel",MenuModel.MenuType.MENU));

//menuList1.addItem(new MenuModel("", "",MenuModel.MenuType.EMPTY));
//
//menuList1.addItem(new MenuModel("", "Produit",MenuModel.MenuType.TITLE));
menuList1.addItem(new MenuModel("7", "Utilisateur",MenuModel.MenuType.MENU));
menuList1.addItem(new MenuModel("7", "Groupe",MenuModel.MenuType.MENU));
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        menuList1 = new javaapp.MenuList<>();

        setPreferredSize(new java.awt.Dimension(240, 706));

        jLabel1.setBackground(new java.awt.Color(241, 241, 241));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SUPERMARKET");

        menuList1.setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addContainerGap(85, Short.MAX_VALUE))
            .addComponent(menuList1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addComponent(menuList1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(261, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
protected void paintChildren(Graphics grphcs){
    Graphics2D g2 =(Graphics2D) grphcs;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    GradientPaint g = new GradientPaint(0,0,Color.decode("#080436"),0, getHeight(),Color.decode("#080436"));
    // GradientPaint g = new GradientPaint(0,0,Color.decode("#0B4151"),0, getHeight(),Color.decode("#0B4151")); //GREENBLUE
  //  GradientPaint g = new GradientPaint(0,0,Color.decode("#122b3e"),0, getHeight(),Color.decode("#122b3e")); // NICE ONE  BLUE
  // GradientPaint g = new GradientPaint(0,0,Color.decode("#E4584B"),0, getHeight(),Color.decode("#E4584B")); //ORANGE
    //GradientPaint g = new GradientPaint(0,0,Color.decode("#ff7e5f"),0, getHeight(),Color.decode("#feb47b"));
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
