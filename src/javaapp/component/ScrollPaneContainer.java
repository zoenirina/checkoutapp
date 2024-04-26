
package javaapp.component;

import javax.swing.JComponent;



public class ScrollPaneContainer extends javax.swing.JPanel {
   
    public ScrollPaneContainer(JComponent com) { 
        
        initComponents();
        scrollPane.removeAll();
        scrollPane.add(com);
        scrollPane.repaint();
        scrollPane.revalidate();
//        refreshTable();         
//        setForm(new EntreeCaisse());

        
    }
   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        scrollPane = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1101, 800));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        scrollPane.setBackground(new java.awt.Color(255, 255, 255));
        scrollPane.setLayout(new java.awt.BorderLayout());
        jScrollPane1.setViewportView(scrollPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel scrollPane;
    // End of variables declaration//GEN-END:variables
}
