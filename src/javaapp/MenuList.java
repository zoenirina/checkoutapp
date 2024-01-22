package javaapp;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class MenuList<E extends Object> extends JList<E> {
    private final DefaultListModel model;
    public MenuList(){
        model= new DefaultListModel();
        setModel(model);
    }
    
    @Override
   public ListCellRenderer<? super E> getCellRenderer(){
return new DefaultListCellRenderer(){

    @Override
    public Component getListCellRendererComponent(JList<?> jlist, Object o, int i, boolean bln, boolean blnl ){
        MenuModel data;
        if(o instanceof MenuModel){
    data = (MenuModel)o;
    }else{
        data= new MenuModel("",o + "",MenuModel.MenuType.EMPTY);
        }
        MenuItem item = new MenuItem(data);
        return item;
    }
};
}
   public void addItem(MenuModel data){
   model.addElement(data);
   }
}
