
package javaapp.swing;

import java.awt.Color;
import java.awt.Component;
import javaapp.component.TableHeader;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Table extends JTable{
     public Table(){
        setShowHorizontalLines(true);
        setShowVerticalLines(false);
        setGridColor(new Color(230,230,230));
        setRowHeight(40);
//        setVerticalSrollBar(new ScrollBar());
//        getVerticalScrollBar().setBackground(Color.WHITE);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int il){
                TableHeader header = new TableHeader(o + "");
                return header;
            }
        });
        setDefaultRenderer(Object.class , new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                if(i1 == 4){
                    Component com = super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
                    com.setBackground(Color.WHITE);
                    return com;
                }
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1); //To change body of generated methods, choose Tools | Templates.
//                    return new JLabel("Testing");
            }
        
    });
    }
} 

