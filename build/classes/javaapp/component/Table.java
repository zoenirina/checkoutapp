
package javaapp.component;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Table extends JTable{
    public Table(){
        setShowHorizontalLines(true);
        setShowVerticalLines(false);
        setGridColor(new Color(230,230,230));
        setRowHeight(40);
       
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
                Component com = super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
                if(i1 == 0){
                    com.setForeground(Color.decode("#e74c4a"));
                    com.setFont(getFont().deriveFont(Font.BOLD));
//                    com.setBackground(Color.WHITE);e74c4a
                   
                }else{
                 com.setForeground(Color.decode("#333333"));
                 com.setFont(getFont().deriveFont(Font.PLAIN));
                }
                if(i%2 == 0){
                   com.setBackground(Color.decode("#f2f2f6")); 
                }else{
                 com.setBackground(Color.WHITE); 
                }
                 return com;
//                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1); //To change body of generated methods, choose Tools | Templates.
            }
        
    });
    }
}
