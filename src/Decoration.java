import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by oleg on 15.06.2017.
 */
public class Decoration {
    private JPanel rootPanel;
    private JPanel titlePanel;
    private JButton closeButton;
    private JButton minimizeButton;
    private JPanel contentPanel;
    private ComponentResizer componentResizer;
    private ComponentMover componentMover;


    public void setContentPanel(Component component) {
        contentPanel.removeAll();
        contentPanel.add(component);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public Decoration(MyFrame frame) {
        setContentPanel(frame.getContentPane());
        frame.setContentPane(rootPanel);
        componentMover = new ComponentMover(frame, titlePanel);       //добавляем перемещение
        componentMover.setChangeCursor(false);                      //убираем курсор перемещения (как-то раздражает он меня)
        componentResizer = new ComponentResizer(frame);
    }


    public void addActionListenerForMinimize(ActionListener listener) {
        minimizeButton.addActionListener(listener);
    }

    public void addActionListenerForClose(ActionListener listener) {
        closeButton.addActionListener(listener);
    }
}
