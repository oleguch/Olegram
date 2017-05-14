import javax.swing.*;


public class FormPhone {
    private JTextField fieldPhone;
    private JPanel rootPanel;
    private JButton buttonPhone;
    private JLabel titleLabel;
    private JLabel label;

    public JPanel getRootPanel() {
        return rootPanel;
    }
    public JTextField getFieldPhone() {
        return fieldPhone;
    }
    public JButton getButtonPhone() {
        return buttonPhone;
    }

    public FormPhone() {
        getFieldPhone().requestFocus();
        getFieldPhone().requestFocusInWindow();
    }

    public void messageError(Exception e){
        if (e.getMessage().equals("PHONE_NUMBER_INVALID")) {                              //Если неверный номер телефона
            System.out.println("Введен неверный номер телефона");                             //Выводим сообщение
            JOptionPane.showMessageDialog(getRootPanel(), "Введен неверный номер телефона");
            getFieldPhone().requestFocus();
        } else if (e.getMessage().substring(0,10).equals("FLOOD_WAIT")) {
            System.out.println("Много попыток входа");                             //Выводим сообщение
            JOptionPane.showMessageDialog(getRootPanel(), "Много попыток входа, ждите " + e.getMessage().substring(11) + " секунд");
            getFieldPhone().requestFocus();
        }
    }
}
