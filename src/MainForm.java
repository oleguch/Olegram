
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Objects;


/**
 * Created by HerrSergio on 06.04.2016.
 */
public class MainForm extends JPanel {
    private JPanel rootPanel;
    private JPanel titlePanel;
    private JPanel contactsPanel;
    private JPanel messagesPanel;
    private JTextArea messageTextArea;
    private JButton sendMessageButton;
    private JScrollPane messageTextScrollPane;
    private JButton settingsButton;
    private JTextField searchTextField;
    private JPanel searchIconPanel;
    private JPanel buddyPanel;
    private JButton buddyEditButton;

    private String meText;
    private BufferedImage mePhoto;

    private String buddyText;
    private BufferedImage buddyPhoto;


    public MainForm() {
        contactsPanel.add(new JPanel());
//        messagesPanel.add(new JPanel());

//        GuiHelper.decorateScrollPane(messageTextScrollPane);

        Images.decorateAsImageButton(settingsButton, Images.getSettingsIcon(), Images.getSettingsIconPress(), null);
        Images.decorateAsImageButton(buddyEditButton, Images.getPencilIcon(), Images.getPencilIconPress(), null);
        Images.decorateAsImageButton(sendMessageButton, Images.getSendMessageImage(), Images.getSendMessageImagePress(), null);

//        Helper.clearBorder(messageTextArea);
//        Helper.clearBorder(searchTextField);


    }

    private void createUIComponents() {
        rootPanel = this;

        titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Colors.getLightBlueColor());
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                int leftMostPoint = settingsButton.getX();
                int rightMostPoint = 25;            //граница?, но почему справа, если это влияет на отступ логотипа слева?
                //Установка текста фио пользователя
                if (meText != null) {
                    int inset = 25;             //отступ справа-слева?
                    Font font = Fonts.getUserNameTitleFont();
                    //Color color = Color.white;
                    Color color = Colors.getColorUserNameTitle();
                    String text = meText;

                    leftMostPoint = GuiHelper.drawText(g, text, color, font, rightMostPoint, 0, leftMostPoint - rightMostPoint, this.getHeight(), inset, true);
                }

                if (mePhoto != null) {
                    int inset = 10;
                    BufferedImage image = mePhoto;

                    leftMostPoint = GuiHelper.drawImage(g, image, rightMostPoint, 0, leftMostPoint - rightMostPoint, this.getHeight(), inset, true);
                }

                rightMostPoint = GuiHelper.drawImage(g, Images.getLogoMicro(), rightMostPoint, (this.getHeight()/2-24/2), leftMostPoint - rightMostPoint, 24/*this.getHeight()*/, 3, false);
            }
        };


        //Альтернтивное решение
        //settingsButton = new ImageButton(Images.getSettingsIcon());
        //sendMessageButton = new ImageButton(Images.getSendMessageImage());
        //buddyEditButton = new ImageButton(Images.getPencilIcon());

        buddyPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);

                int leftMostPoint = buddyEditButton.getX();
                int rightMostPoint = 2;

                if (buddyPhoto != null) {
                    int inset = 2;
                    BufferedImage image = buddyPhoto;

                    rightMostPoint = GuiHelper.drawImage(graphics, image, rightMostPoint, 0, leftMostPoint - rightMostPoint, this.getHeight(), inset, false);
                }

                if (buddyText != null) {

                    int inset = 10;
                    Font font = Fonts.getBuddyTitleFont();
                    Color color = Colors.getColorBuddyNameFont();
                    String text = buddyText;

                    rightMostPoint = GuiHelper.drawText(graphics, text, color, font, rightMostPoint, 0, leftMostPoint - rightMostPoint, this.getHeight(), inset, false);
                }

            }
        };

//        searchTextField = new HintTextFieldUnderlined("", "Поиск...", false, false);
//
//        searchIconPanel = new ImagePanel(Images.getSearchIcon(), true, true, 2);
    }

    public Component getContactsPanel() {
        return this.contactsPanel.getComponent(0);
    }

    public void setContactsPanel(Component contactsPanel) {
        this.contactsPanel.removeAll();
        this.contactsPanel.add(contactsPanel);
    }


    public void setMeText(String meText) {
        if(!Objects.equals(this.meText, meText)) {
            this.meText = meText;
            repaint();
        }
    }

    public void setMePhoto(BufferedImage mePhoto) {
        this.mePhoto = mePhoto;
        repaint();
    }

    public void setBuddyText(String buddyText) {
        if(!Objects.equals(this.buddyText, buddyText)) {
            this.buddyText = buddyText;
            repaint();
        }
    }

    public void setBuddyPhoto(BufferedImage buddyPhoto) {
        this.buddyPhoto = buddyPhoto;
        repaint();
    }

    public void setBuddyEditEnabled(boolean enabled) {
        buddyEditButton.setEnabled(enabled);
    }
}
