import org.javagram.dao.Dialog;
import org.javagram.dao.Person;
import org.javagram.dao.proxy.TelegramProxy;

import javax.swing.*;
import java.awt.*;

public class FormContact extends JPanel implements ListCellRenderer <Person> {
    private JButton button;


    private JPanel rootPanel;
    private JPanel photoPanel;
    private JTextPane lastMessageLabel;
    private JLabel nameLabel;

    private TelegramProxy telegramProxy;
    private boolean hasFocus;
    private final int focusMarkerWidth = 4;

    public FormContact(TelegramProxy telegramProxy) {
        this.telegramProxy = telegramProxy;
    }

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(new Color(0xe6e6e6));
        graphics.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
        if(hasFocus) {
            graphics.setColor(new Color(0x00b3e6));
            graphics.fillRect(this.getWidth() - focusMarkerWidth, 0, this.getWidth(), this.getHeight());
        }
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }


    @Override
    public Component getListCellRendererComponent(JList<? extends Person> jList, Person person, int index, boolean selected, boolean hasFocus) {
        Dialog dialog = telegramProxy.getDialog(person);
        this.nameLabel.setText(person.getFirstName() + " " + person.getLastName());
        Fonts.setFontToComponent(this.nameLabel, Fonts.getUserNameFont(), new Color(0x434343));
        lastMessageLabel.setForeground(new Color(0x6c6c6c));
        if(dialog != null){
            this.lastMessageLabel.setText(dialog.getLastMessage().getText());
        } else {
            this.lastMessageLabel.setText("");
        }

        if(selected) {
            setBackground(Color.white);
            photoPanel.setBackground(Color.white);
            lastMessageLabel.setBackground(Color.white);
        } else {
            Color colorSelected = new Color(0xe6e6e6);
            setBackground(colorSelected);
            photoPanel.setBackground(colorSelected);
            lastMessageLabel.setBackground(colorSelected);
        }

        this.hasFocus = hasFocus;

        ((PhotoPanel)photoPanel).setImage(Helper.getPhoto(telegramProxy, person, true, true));
        ((PhotoPanel)photoPanel).setOnline(telegramProxy.isOnline(person));

        return this;
    }

    private void createUIComponents() {
        rootPanel = this;
        photoPanel = new PhotoPanel(null, true, false, 0, false);
    }
}
