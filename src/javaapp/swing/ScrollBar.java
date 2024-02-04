
package javaapp.swing;

import java.awt.Dimension;
import javax.swing.JScrollBar;

public class ScrollBar extends JScrollBar{

    public ScrollBar() {
//        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(10,10));
        setOpaque(false);
        setUnitIncrement(20);
        
    }
    
}
