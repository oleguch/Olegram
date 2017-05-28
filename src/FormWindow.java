import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FormWindow extends JPanel {
    private JPanel titleBar;
    private JButton butMinimize;
    private JButton butExit;
    private JPanel rootPanel;
    private JPanel contentPanel;
    private ComponentMover componentMover;
    private ComponentResizer componentResizer;

    public Component getContentPanel(){
        return contentPanel.getComponent(0);
    }
    public JPanel getTitleBar() {
        return titleBar;
    }

    public FormWindow(MyFrame frame) {
        //setContentPanel(frame.getFormPhone().getRootPanel());
        setContentPanel(frame.getContentPane());
        frame.setContentPane(this);
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        componentMover = new ComponentMover(frame, titleBar);       //добавляем перемещение
        componentMover.setChangeCursor(false);                      //убираем курсор перемещения (как-то раздражает он меня)
        componentResizer = new ComponentResizer(frame);
        //componentResizer.setMinimumSize(new Dimension(500,400));
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

    public FormWindow(JDialog dialog) {
        setContentPanel(dialog.getContentPane());
        dialog.setContentPane(this);
        dialog.setUndecorated(true);
        dialog.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        titleBar.setBackground(Color.red);
        componentMover = new ComponentMover(dialog, titleBar);       //добавляем перемещение
        componentMover.setChangeCursor(false);                      //убираем курсор перемещения
        getTitleBar().remove(butMinimize);
        butExit.addActionListener(new ActionListener() {            //закрытие
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
            }
        });
    }
    private void createUIComponents() {
        rootPanel = this;
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


//    public static int showOptionDialog (Window window,String message, int optionType, int messageType, Icon icon, Object[] options, Object initialValu) {
//        JOptionPane optionPane = new JOptionPane(message, optionType, messageType, icon, options, initialValue);
    public static int showOptionDialog (Window window, String message, int messageType) {
        JOptionPane optionPane = new JOptionPane(message, messageType);
        JDialog dialog;
        if (window instanceof Frame)
            dialog = new JDialog((Frame) window);
        else if (window instanceof Dialog)
            dialog = new JDialog((Dialog) window);
        else
            dialog = new JDialog(window);
        dialog.setModal(true);
        dialog.setContentPane(optionPane);
        new FormWindow(dialog);
        dialog.pack();
        dialog.setLocationRelativeTo(window);
        optionPane.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                dialog.dispose();                           //без этого окно диалога по кнопкам не закроется?
            }
        });
        dialog.setVisible(true);
        Object value = optionPane.getValue();
        if (value instanceof Integer)
            return (int) value;
        return JOptionPane.CLOSED_OPTION;
    }
}
