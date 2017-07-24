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
        graphics.setColor(Color.lightGray);
        graphics.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
        if(hasFocus) {
            graphics.setColor(Color.blue);
            graphics.fillRect(0/*this.getWidth() - focusMarkerWidth*/, 0, focusMarkerWidth, this.getHeight());
        }
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }


    @Override
    public Component getListCellRendererComponent(JList<? extends Person> jList, Person person, int index, boolean selected, boolean hasFocus) {
        Dialog dialog = telegramProxy.getDialog(person);
        this.nameLabel.setText(person.getFirstName() + " " + person.getLastName());

        if(dialog != null){
            this.lastMessageLabel.setText(dialog.getLastMessage().getText());
        } else {
            this.lastMessageLabel.setText("");
        }

        if(selected)
            setBackground(Color.white);
        else {
            setBackground(Color.lightGray);
        }

        this.hasFocus = hasFocus;

        //((PhotoPanel)photoPanel).setImage(Helper.getPhoto(telegramProxy, person, true, true));
        //((PhotoPanel)photoPanel).setOnline(telegramProxy.isOnline(person));

        return this;
    }

    private void createUIComponents() {
        rootPanel = this;
    }
}
