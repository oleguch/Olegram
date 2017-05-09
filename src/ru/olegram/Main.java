package ru.olegram;


import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;
import org.javagram.response.object.User;
import org.javagram.response.object.UserContact;
import org.telegram.api.engine.RpcException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class Main {

    static JFrame frame = new JFrame("Olegram");
    static FormConfirmSMS formConfirmSMS = new FormConfirmSMS();
    static FormPhone formPhone = new FormPhone();
    static FormNewUser formNewUser = new FormNewUser();
    static FormFriends formFriends = new FormFriends();
    static String phoneNumber;
    static AuthCheckedPhone checkPhone;

    private static TelegramApiBridge bridge;

    static {
        try {
            bridge = new TelegramApiBridge("149.154.167.50:443", 95568, "e5649ac9f0c517643f3c8cad067ac7b0");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.setContentPane(formPhone.getRootPanel());

        //                  Реакция на кнопку Продолжить в окне ввода номера телефона
        formPhone.getButtonPhone().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkPhone();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //                  Переключение окон при нажатии Enter
        Action changeForm = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(frame.getContentPane().getName());
                try {
                    if (frame.getContentPane().getName().equals("FormPhone"))
                        checkPhone();
                    else if (frame.getContentPane().getName().equals("FormConfirmSMS"))
                        if (!checkPhone.isRegistered()) {
                            confirmSMS(formNewUser.getRegName().getText(), formNewUser.getRegSurname().getText());          //не уверен что сработает, проверить уже не на чем
                        } else
                            confirmSMS(null, null);
                    else if (frame.getContentPane().getName().equals("FormNewUser"))
                        checkNewUser();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };
        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "changeForm");
        frame.getRootPane().getActionMap().put("changeForm", changeForm);

        frame.setSize(800,600);
        frame.setMinimumSize(new Dimension(600,200));
        frame.setLocationRelativeTo(null);
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);

        bridge.authLogOut();
    }

    private static void checkPhone() throws IOException {
        phoneNumber = formPhone.getFieldPhone().getText().replaceAll("\\D+","");
        try {
            checkPhone = bridge.authCheckPhone(phoneNumber);
            bridge.authSendCode(phoneNumber);
            if (!checkPhone.isRegistered())
                registration();
            else
                authBySMS();
        } catch (RpcException e2) {                                                       //Если возникла ошибка
            if (e2.getMessage().equals("PHONE_NUMBER_INVALID")) {                              //Если неверный номер телефона
                System.out.println("Введен неверный номер телефона");                             //Выводим сообщение
                JOptionPane.showMessageDialog(formPhone.getRootPanel(), "Введен неверный номер телефона");
                formConfirmSMS.getFieldSMS().requestFocus();
            } else if (e2.getMessage().substring(0,10).equals("FLOOD_WAIT")) {
                System.out.println("Много попыток входа");                             //Выводим сообщение
                JOptionPane.showMessageDialog(formPhone.getRootPanel(), "Много попыток входа, ждите " + e2.getMessage().substring(11) + " секунд");
                formConfirmSMS.getFieldSMS().requestFocus();
            }
        }
    }

    private static void registration() throws IOException {
        frame.setContentPane(formNewUser.getRootPanel());
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        formNewUser.getRegName().requestFocus();
        System.out.println("Номер не зарегестрирован");
        formNewUser.getButtonReg().addActionListener(e -> {
            try {
                checkNewUser();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private static void checkNewUser() throws IOException {
        String firstName = formNewUser.getRegName().getText().trim();
        String lastName = formNewUser.getRegSurname().getText().trim();
        if (firstName.length() == 0) {
            JOptionPane.showMessageDialog(formNewUser.getRootPanel(), "Не заполнено поле Имя");
            formNewUser.getRegName().requestFocus();
        } else if (lastName.length() == 0) {
            JOptionPane.showMessageDialog(formNewUser.getRootPanel(), "Не заполнено поле Фамилия");
            formNewUser.getRegSurname().requestFocus();
        } else {
            authBySMS(firstName, lastName);
        }
    }

    private static void authBySMS  () throws IOException {
        authBySMS(null, null);
    }

    private static void authBySMS  (String firstName, String lastName) throws IOException {
        frame.setContentPane(formConfirmSMS.getRootPanel());
        formConfirmSMS.getTextLabelSMS().setText("На номер " + Main.phoneNumber + "\nотправен код через СМС. " + "\nВведите его в следующем поле.");
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        formConfirmSMS.getFieldSMS().requestFocus();
        formConfirmSMS.getButtonSMS().addActionListener(e -> confirmSMS(firstName, lastName));
    }

    private static void confirmSMS(String firstName, String lastName) {
        String smsCode = formConfirmSMS.getFieldSMS().getText();
        System.out.println("Введите код из СМС:");
        try {
            if ((firstName == null) && (lastName == null)) {                                    //Если фио пустые, то авторизовываем, иначе регистрируем
                AuthAuthorization signIn = bridge.authSignIn(smsCode);                          //отправляем только код из смс и авторизовываем пользователя
                System.out.println(" Name: " + getName(signIn));                                        //Если получилось, получаем имя
            } else {
                AuthAuthorization signUp = bridge.authSignUp(smsCode, firstName, lastName);    //и регистрируем, отправив код из смс, имя и фамилию
                System.out.println(" NewName: " + getName(signUp));                                        //выводим имя
            }
            Thread.sleep(100);                  //ВНИМАНИЕ, почему-то иначе дейсвтует через раз, бывает сразу отображает контакты, бывает зависает окно
            toFormFriends();
        } catch (RpcException e2) {                                                       //Если возникла ошибка
            if (e2.getMessage().equals("PHONE_CODE_INVALID")) {                              //Если неверный код
                System.out.println("Введен неверный код");                             //Выводим сообщение
                JOptionPane.showMessageDialog(formConfirmSMS.getRootPanel(),"Введен неверный код");
                formConfirmSMS.getFieldSMS().requestFocus();
            }
        } catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private static void toFormFriends() throws IOException, InterruptedException {

        frame.setContentPane(formFriends.getRootPanel());
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        ArrayList<UserContact> myFriends = bridge.contactsGetContacts();
        System.out.println("Список друзей:" + myFriends);
        for (UserContact friend : myFriends) {
            System.out.println("Имя: " + friend.getFirstName());
            System.out.println("Фамилия: " + friend.getLastName());
            System.out.println("Телефон: " + friend.getPhone() + "\n");
            formFriends.getTextFriends().setText(
                    formFriends.getTextFriends().getText() + "\n" +
                    "Имя: " + friend.getFirstName() + "\n" +
                    "Фамилия: " + friend.getLastName() + "\n" +
                    "Телефон: " + friend.getPhone() + "\n");
            formFriends.getTextAreaFriends().append("\n" +
                    "Имя: " + friend.getFirstName() + "\n" +
                    "Фамилия: " + friend.getLastName() + "\n" +
                    "Телефон: " + friend.getPhone() + "\n");
        }
    }

    private static User getName(AuthAuthorization sign){
        return sign.getUser();
    }
}