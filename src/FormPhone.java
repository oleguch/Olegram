import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;


public class FormPhone {
    private JFormattedTextField fieldPhoneFormatted;
    private JPanel rootPanel;
    private JButton buttonPhone;
    private JLabel titleLabel;
    private JLabel label;

    public JPanel getRootPanel() {
        return rootPanel;
    }
    public JFormattedTextField getFieldPhoneFormatted() {
        return fieldPhoneFormatted;
    }
    public JButton getButtonPhone() {
        return buttonPhone;
    }

    public FormPhone() throws ParseException {
        MaskFormatter maskPhone = new MaskFormatter("+#(###)###-##-##");
        maskPhone.setPlaceholderCharacter('*');
        maskPhone.install(getFieldPhoneFormatted());
        getFieldPhoneFormatted().requestFocusInWindow();
    }

    public void messageError(Exception e){
        if (e.getMessage().equals("PHONE_NUMBER_INVALID")) {                              //Если неверный номер телефона
            System.out.println("Введен неверный номер телефона");                             //Выводим сообщение
            JOptionPane.showMessageDialog(getRootPanel(), "Введен неверный номер телефона");
            getFieldPhoneFormatted().requestFocus();
        } else if (e.getMessage().substring(0,10).equals("FLOOD_WAIT")) {
            System.out.println("Много попыток входа");                             //Выводим сообщение
            JOptionPane.showMessageDialog(getRootPanel(), "Много попыток входа, ждите " + e.getMessage().substring(11) + " секунд");
            getFieldPhoneFormatted().requestFocus();
        }
    }
}
