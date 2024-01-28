package javaapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class MenuItem extends javax.swing.JPanel {
    private boolean selected;
    private boolean over;

    public MenuItem( MenuModel data) {
        initComponents();
        setOpaque(false);
        
        if(data.getType() == MenuModel.MenuType.MENU){
        iconItem.setIcon(data.toIcon());
        nameItem.setText(data.getName());
        }else if(data.getType() == MenuModel.MenuType.TITLE){
        iconItem.setText(data.getName());
        iconItem.setFont(new Font("Tahoma",1,16));
        nameItem.setVisible(false);
        }else{
        nameItem.setText(" ");
        }
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }
    public void setOver(boolean over){
    this.over = over;
    repaint();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        iconItem = new javax.swing.JLabel();
        nameItem = new javax.swing.JLabel();

        setForeground(new java.awt.Color(207, 229, 255));
        setPreferredSize(new java.awt.Dimension(168, 32));

        iconItem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        iconItem.setForeground(new java.awt.Color(255, 255, 255));

        nameItem.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 16)); // NOI18N
        nameItem.setForeground(new java.awt.Color(255, 255, 255));
        nameItem.setText("Menu Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(iconItem)
                .addGap(9, 9, 9)
                .addComponent(nameItem, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iconItem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameItem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
@Override
  protected void paintComponent(Graphics grphcs) {
   if(selected || over){
    Graphics2D g2 =(Graphics2D) grphcs;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
   // GradientPaint g = new GradientPaint(0,0,Color.decode("#41295a"),0, getHeight(),Color.decode("#2f0743"));
   // g2.setColor(new Color(255,255,255,80));
    if(selected){
        g2.setColor(new Color(255,255,255,80));
    }else{
        g2.setColor(new Color(255,255,255,20));
    }
    g2.fillRoundRect(10, 0, getWidth() - 20, getHeight(), 5, 5);
   }
  super.paintComponent(grphcs);
  }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel iconItem;
    private javax.swing.JLabel nameItem;
    // End of variables declaration//GEN-END:variables
}
