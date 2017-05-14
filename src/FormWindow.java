import javax.swing.*;
import java.awt.*;

public class FormWindow extends JPanel {
    private JPanel titleBar;
    private JButton butMinimize;
    private JButton butExit;
    private JPanel rootPanel;
    private JPanel contentPanel;

    public FormWindow() {
        contentPanel.add(new JPanel());
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;
        contentPanel = new JPanel();
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
//
//    public JPanel getContentPanel() {
//        return contentPanel;
//    }
    public Component getContentPanel(){
        return contentPanel.getComponent(0);
    }

    public JPanel getTitleBar() {
        return titleBar;
    }
}
