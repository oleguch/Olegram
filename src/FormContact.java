import javax.swing.*;
import java.awt.*;

public class FormContact implements ListCellRenderer{
    private JButton button;


    private JPanel rootPanel;

    @Override
    public Component getListCellRendererComponent(JList jList, Object o, int index, boolean selected, boolean hasFocus) {
        button.setText(o.toString());
        button.setEnabled(!selected);
        rootPanel.setPreferredSize(new Dimension(0,50));

        return rootPanel;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

}
