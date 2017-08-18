package gui.additionally;

import javax.swing.*;
import java.awt.*;

public class MyLayeredPane extends JLayeredPane {
    @Override
    public void doLayout() {
        super.doLayout();
        for(Component component : getComponents()) {
            component.setBounds(0, 0, this.getWidth(), this.getHeight());
        }
    }
}
