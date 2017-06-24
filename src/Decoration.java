import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


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
        titlePanel.setBackground(new Color(231,231,231));
        frame.setContentPane(rootPanel);
        componentMover = new ComponentMover(frame, titlePanel);       //добавляем перемещение
        componentMover.setChangeCursor(false);                      //убираем курсор перемещения (как-то раздражает он меня)
        componentResizer = new ComponentResizer(frame);
        componentResizer.setMaximumSize(new Dimension(905,596));
        Images.decorateAsImageButton(closeButton, Images.getButtonClose(), Images.getButtonClosePressed(), null);
        Images.decorateAsImageButton(minimizeButton, Images.getButtonMinimize(), Images.getButtonMinimizePressed(), null);
    }

    public Decoration(JDialog dialog) {
        setContentPanel(dialog.getContentPane());
        dialog.setContentPane(rootPanel);
        dialog.setUndecorated(true);
        dialog.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        //titlePanel.setBackground(Color.red);                        //отдельный цвет для заголовка
        componentMover = new ComponentMover(dialog, titlePanel);       //добавляем перемещение
        componentMover.setChangeCursor(false);                      //убираем курсор перемещения
        titlePanel.remove(minimizeButton);                          //убираем кнопку сворачивания
        Images.decorateAsImageButton(closeButton, Images.getButtonClose(), Images.getButtonClosePressed(), null);
        closeButton.addActionListener(new ActionListener() {            //закрытие
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
            }
        });
    }


    public void addActionListenerForMinimize(ActionListener listener) {
        minimizeButton.addActionListener(listener);
    }

    public void addActionListenerForClose(ActionListener listener) {
        closeButton.addActionListener(listener);
    }

    public static int showOptionDialog (Window window,String message, int optionType, int messageType, Icon icon, Object[] options, Object initialValue) {
        JOptionPane optionPane = new JOptionPane(message, optionType, messageType, icon, options, initialValue);
            JDialog dialog;
            if (window instanceof Frame)
                dialog = new JDialog((Frame) window);
            else if (window instanceof Dialog)
                dialog = new JDialog((Dialog) window);
            else
                dialog = new JDialog(window);
            dialog.setModal(true);
            dialog.setContentPane(optionPane);
            new Decoration(dialog);
            dialog.pack();
            dialog.setLocationRelativeTo(window);                   //для центрирования по центру окна
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
