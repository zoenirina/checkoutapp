
package javaapp.component;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Table extends JTable{
    private int selectedRow = -1;
    
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
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSeleted, boolean bln1, int i, int i1) {
                Component com = super.getTableCellRendererComponent(jtable, o, isSeleted, bln1, i, i1);
                if(i1 == 0){
                    com.setForeground(Color.decode("#e74c4a"));
//                    com.setFont(getFont().deriveFont(Font.BOLD));
//                    com.setBackground(Color.WHITE);e74c4a
                }else{
                 com.setForeground(Color.decode("#333333"));
                }
                
                if (isSeleted) { // Vérifie si la ligne est celle sélectionnée
                    com.setBackground(Color.decode("#9999ff")); // Changer la couleur de fond pour la ligne sélectionnée
                    if(i1 == 0){
                        com.setForeground(Color.decode("#FFFFFF"));
                    }else{
                     com.setForeground(Color.decode("#191986"));
                    }
                } else if(i%2 == 0){
                   com.setBackground(Color.decode("#EEF5FF")); //F1F8F0
//                   com.setBackground(Color.decode("#F1F8F0"));
                }else{
                 com.setBackground(Color.WHITE); 
                }
                com.setFont(getFont().deriveFont(Font.PLAIN));
                 return com;
//                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1); //To change body of generated methods, choose Tools | Templates.
            }
        
    });
        
//      addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int row = rowAtPoint(e.getPoint());
//                if (row != selectedRow) {
//                    selectedRow = row;
//                    repaint(); // Redessine la table pour mettre à jour la couleur de fond
//                }
//            }
//        });  
    }
}
