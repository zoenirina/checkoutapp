/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.swing;

/**
 *
 * @author ZOENIRINA
 */
public class CellStatus extends javax.swing.JPanel {
    private String textStatus = "";

    public CellStatus() {
        initComponents();
    }

    CellStatus(String txt) {
//        setTextStatus(txt);
        initComponents();
//        setOpaque(false);
        this.textStatus = txt;
        tableStatus1.setText(textStatus); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tableStatus1 = new javaapp.swing.TableStatus();

        setBackground(new java.awt.Color(255, 255, 255));

        tableStatus1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tableStatus1.setText("tableStatus1");
        tableStatus1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tableStatus1, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tableStatus1, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.swing.TableStatus tableStatus1;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the textStatus
     */
    public String getTextStatus() {
        return textStatus;
    }

    /**
     * @param textStatus the textStatus to set
     */
    public void setTextStatus(String textStatus) {
        this.textStatus = textStatus;
    }
}