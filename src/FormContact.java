import org.javagram.dao.Dialog;
import org.javagram.dao.Person;
import org.javagram.dao.proxy.TelegramProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

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
        graphics.setColor(Colors.getColorBorderUserList());         //Граница формы контакта
        graphics.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
        if(hasFocus) {
            graphics.setColor(Colors.getLightBlueColor());
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
        Fonts.setFontToComponent(this.nameLabel, Fonts.getFontUserNameList(), Color.DARK_GRAY);
        lastMessageLabel.setForeground(Color.GRAY);
        lastMessageLabel.setFont(Fonts.getFontLastMessage());
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
            setBackground(Colors.getColorUnselected());
            photoPanel.setBackground(Colors.getColorUnselected());
            lastMessageLabel.setBackground(Colors.getColorUnselected());
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
