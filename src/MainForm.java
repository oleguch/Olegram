
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
        messagesPanel.add(new JPanel());

        GuiHelper.decorateScrollPane(messageTextScrollPane);

        GuiHelper.decorateAsImageButton(settingsButton, Images.getSettingsIcon());
        GuiHelper.decorateAsImageButton(buddyEditButton, Images.getPencilIcon());
        GuiHelper.decorateAsImageButton(sendMessageButton, Images.getSendMessageImage());

        Helper.clearBorder(messageTextArea);
        Helper.clearBorder(searchTextField);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;

        titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0x00b3e6));
                g.fillRect(0, 0, this.getWidth(), this.getHeight());

                int leftMostPoint = settingsButton.getX();
                int rightMostPoint = 25;

                if (meText != null) {

                    int inset = 25;
                    Font font = Fonts.getNameFont().deriveFont(Font.PLAIN, 20);
                    //Color color = Color.white;
                    Color color = new Color(0x71d3ee);
                    String text = meText;

                    leftMostPoint = GuiHelper.drawText(g, text, color, font, rightMostPoint, 0, leftMostPoint - rightMostPoint, this.getHeight(), inset, true);
                }

                if (mePhoto != null) {
                    int inset = 2;
                    BufferedImage image = mePhoto;

                    leftMostPoint = GuiHelper.drawImage(g, image, rightMostPoint, 0, leftMostPoint - rightMostPoint, this.getHeight(), inset, true);
                }

                //rightMostPoint = GuiHelper.drawImage(g, Images.getPencilIcon(), rightMostPoint, 0, leftMostPoint - rightMostPoint, this.getHeight(), 3, false);
                rightMostPoint = GuiHelper.drawImage(g, Images.getLogoMicro(), rightMostPoint, (this.getHeight()/2-24/2), leftMostPoint - rightMostPoint, 24/*this.getHeight()*/, 3, false);
                //rightMostPoint = GuiHelper.drawImage(g,  Images.getPencilIcon(), rightMostPoint, 0, leftMostPoint - rightMostPoint, this.getHeight(), 5, false);
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
                    Font font = Fonts.getNameFont().deriveFont(Font.PLAIN, 18);
                    Color color = new Color(0x949494);
                    String text = buddyText;

                    rightMostPoint = GuiHelper.drawText(graphics, text, color, font, rightMostPoint, 0, leftMostPoint - rightMostPoint, this.getHeight(), inset, false);
                }

            }
        };

        searchTextField = new HintTextFieldUnderlined("", "Поиск...", false, false);

        searchIconPanel = new ImagePanel(Images.getSearchIcon(), true, true, 2);
    }

    public Component getContactsPanel() {
        return this.contactsPanel.getComponent(0);
    }

    public void setContactsPanel(Component contactsPanel) {
        this.contactsPanel.removeAll();
        this.contactsPanel.add(contactsPanel);
    }

    public Component getMessagesPanel() {
        return this.messagesPanel.getComponent(0);
    }

    public void setMessagesPanel(Component messagesPanel) {
        this.messagesPanel.removeAll();
        this.messagesPanel.add(messagesPanel);
    }

    public void addSendMessageListener(ActionListener listener) {
        this.sendMessageButton.addActionListener(listener);
    }

    public void removeSendMessageListener(ActionListener listener) {
        this.sendMessageButton.removeActionListener(listener);
    }

    public void removeGearEventListener(ActionListener listener) {
        this.settingsButton.removeActionListener(listener);
    }

    public void addGearEventListener(ActionListener listener) {
        this.settingsButton.addActionListener(listener);
    }

    public void removeBuddyEditEventListener(ActionListener listener) {
        this.buddyEditButton.removeActionListener(listener);
    }

    public void addBuddyEditEventListener(ActionListener listener) {
        this.buddyEditButton.addActionListener(listener);
    }

    public void removeSearchEventListener(ActionListener listener) {
        this.searchTextField.removeActionListener(listener);
    }

    public void addSearchEventListener(ActionListener listener) {
        this.searchTextField.addActionListener(listener);
    }

    public String getSearchText() {
        return this.searchTextField.getText();
    }

    public String getMessageText() {
        return this.messageTextArea.getText();
    }

    public void setMessageText(String text) {
        this.messageTextArea.setText(text);
    }

    public String getMeText() {
        return meText;
    }

    public void setMeText(String meText) {
        if(!Objects.equals(this.meText, meText)) {
            this.meText = meText;
            repaint();
        }
    }

    public BufferedImage getMePhoto() {
        return mePhoto;
    }

    public void setMePhoto(BufferedImage mePhoto) {
        this.mePhoto = mePhoto;
        repaint();
    }

    public String getBuddyText() {
        return buddyText;
    }

    public void setBuddyText(String buddyText) {
        if(!Objects.equals(this.buddyText, buddyText)) {
            this.buddyText = buddyText;
            repaint();
        }
    }

    public BufferedImage getBuddyPhoto() {
        return buddyPhoto;
    }

    public void setBuddyPhoto(BufferedImage buddyPhoto) {
        this.buddyPhoto = buddyPhoto;
        repaint();
    }

    public boolean isBuddyEditEnabled() {
        return buddyEditButton.isEnabled();
    }

    public void setBuddyEditEnabled(boolean enabled) {
        buddyEditButton.setEnabled(enabled);
    }
}
