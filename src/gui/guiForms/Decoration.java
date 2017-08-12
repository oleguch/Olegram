package gui.guiForms;

import additionally.ComponentMover;
import additionally.ComponentResizer;
import additionally.Images;
import gui.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;


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
        componentResizer.setMinimumSize(new Dimension(600,500));
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
        PropertyChangeListener propertyChangeListener = propertyChangeEvent -> dialog.setVisible(false);
        optionPane.addPropertyChangeListener("value", propertyChangeListener);
        Map<ActionListener, AbstractButton> listeners = new HashMap<>();
        if(options != null) {
            for (Object option : options) {
                if(option instanceof AbstractButton) {
                    AbstractButton abstractButton = (AbstractButton)option;
                    ActionListener actionListener = actionEvent -> optionPane.setValue(option);
                    abstractButton.addActionListener(actionListener);
                    listeners.put(actionListener, abstractButton);
                }
            }
        }
        dialog.setVisible(true);
        optionPane.removePropertyChangeListener("value", propertyChangeListener);
        for(Map.Entry<ActionListener, AbstractButton> entry : listeners.entrySet())
            entry.getValue().removeActionListener(entry.getKey());
        Object value = optionPane.getValue();
        if (value == null)
            return JOptionPane.CLOSED_OPTION;

        //If there is not an array of option buttons:
        if (options == null) {
            if (value instanceof Integer)
                return ((Integer) value);
            else
                return JOptionPane.CLOSED_OPTION;
        }
        //If there is an array of option buttons:
        for (int counter = 0, maxCounter = options.length; counter < maxCounter; counter++) {
            if (options[counter].equals(value))
                return counter;
        }
        return JOptionPane.CLOSED_OPTION;
    }
}
