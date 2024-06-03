/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.swing;


import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ZOENIRINA
 */
public class TableStatusCellRender extends DefaultTableCellRenderer {

    public TableStatusCellRender(){
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSeleted, boolean bln1, int row, int column) {
//        Component com = super.getTableCellRendererComponent(jtable, o, isSeleted, bln1, row, column);
        String status = (String)o;        
        CellStatus cellStatus = new CellStatus(status);
        if (isSeleted) {
            cellStatus.setBackground(Color.decode("#9999ff"));
        } else if(row%2 == 0){
            cellStatus.setBackground(Color.decode("#EEF5FF")); 
        }else {
            cellStatus.setBackground(Color.WHITE);
        }
        
        return cellStatus;
    }
}