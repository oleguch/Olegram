import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class FormWindow extends JPanel {
    private JPanel titleBar;
    private JButton butMinimize;
    private JButton butExit;
    private JPanel rootPanel;
    private JPanel contentPanel;
    private ComponentMover componentMover;

    public FormWindow(MyFrame frame) {
        //setContentPanel(frame.getFormPhone().getRootPanel());
        setContentPanel(frame.getContentPane());
        frame.setContentPane(this);
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        componentMover = new ComponentMover(frame, titleBar);       //добавляем перемещение
        componentMover.setChangeCursor(false);                      //убираем курсор перемещения
        butMinimize.addActionListener(new ActionListener() {        //сворачивание
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setExtendedState(Frame.ICONIFIED);
            }
        });
        butExit.addActionListener(new ActionListener() {            //закрытие
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;
        //contentPanel = new JPanel();
        butExit = new JButton();
        butMinimize = new JButton();
        ImageIcon closingIcon = new ImageIcon("res/img/buttonExit.png");
        butExit.setIcon(closingIcon);
        ImageIcon minimizeIcon = new ImageIcon("res/img/buttonMinimize.png");
        butMinimize.setIcon(minimizeIcon);
    }

    public void setContentPanel (Component component) {
        contentPanel.removeAll();
        contentPanel.add(component);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public JButton getButMinimize() {
        return butMinimize;
    }

    public JButton getButExit() {
        return butExit;
    }

    public Component getContentPanel(){
        return contentPanel.getComponent(0);
    }

    public JPanel getTitleBar() {
        return titleBar;
    }
}
