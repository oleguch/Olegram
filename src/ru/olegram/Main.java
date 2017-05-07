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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Main {

    static JFrame frame = new JFrame("changeForms");
    static FormConfirmSMS formConfirmSMS = new FormConfirmSMS();
    static FormPhone formPhone = new FormPhone();
    static FormNewUser formNewUser = new FormNewUser();



    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static TelegramApiBridge bridge;




    static {
        try {
            bridge = new TelegramApiBridge("149.154.167.50:443", 95568, "e5649ac9f0c517643f3c8cad067ac7b0");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        frame.setContentPane(formPhone.getRootPanel());

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
        //frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl ENTER"), "changeForm");
        //frame.getRootPane().getActionMap().put("changeForm", changeForm);

        frame.setSize(800,600);
        frame.setMinimumSize(new Dimension(600,200));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //System.out.println("##########################################Введите номер телефона (в виде 79001234567)");
        //String phoneNumber = reader.readLine().trim();                                     //Вводим номер телефона и запоминаем номер
        //phoneNumber = phoneNumber.replaceAll("\\D+","");                                   //Убираем всё, кроме цифр
        //AuthCheckedPhone checkPhone = bridge.authCheckPhone(phoneNumber);                  //Проверяем номер телефон
        //System.out.println(checkPhone.isRegistered());                                     //выводим рзультат проверки
        //bridge.authSendCode(phoneNumber);                                                  //Отправляем код через смс
        //if (!checkPhone.isRegistered())
        //    registration();
        //else
        //    authBySMS();

//        ArrayList<UserContact> myFriends = bridge.contactsGetContacts();
//        System.out.println("###############################Список друзей:############################" + myFriends);
//        for (UserContact friend : myFriends) {
//            System.out.println("Имя: " + friend.getFirstName());
//            System.out.println("Фамилия: " + friend.getLastName());
//            System.out.println("Телефон: " + friend.getPhone() + "\n");
//        }
        bridge.authLogOut();
    }

    private static void registration() throws IOException {
        frame.setContentPane(formNewUser.getRootPanel());
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        System.out.println("Номер не зарегестрирован");
//        System.out.println("Вы не зарегистрированы. Введите свое имя:");
//        String firstName = reader.readLine().trim();                              //просим ввести имя и фамилию
//        System.out.println("Введите свою фамилию:");
//        String lastName = reader.readLine().trim();
        formNewUser.getButtonReg().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkNewUser();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            private void checkNewUser() throws IOException {
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
        });

        //authBySMS(firstName, lastName);                         //отправляем на регистрацию
    }

    private static void authBySMS  () throws IOException {
        authBySMS(null, null);
    }

    private static void authBySMS  (String firstName, String lastName) throws IOException {
        frame.setContentPane(formConfirmSMS.getRootPanel());
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        final String[] smsCode = new String[1];
        formConfirmSMS.getButtonSMS().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                smsCode[0] = formConfirmSMS.getFieldSMS().getText();
            }
        });
        while (true) {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++Введите код из СМС:");

            //String smsCode = reader.readLine().trim();
            try {
                if ((firstName == null) && (lastName == null)) {                //Если только bridge передается, то авторизовываем, иначе регистрируем
                    AuthAuthorization signIn = bridge.authSignIn(smsCode[0]);                       //отправляем только код из смс и авторизовываем пользователя
                    System.out.println("################################### Name: ############## " + getName(signIn));                                        //Если получилось, получаем имя
                } else {
                    AuthAuthorization signUp = bridge.authSignUp(smsCode[0], firstName, lastName);    //и регистрируем, отправив код из смс, имя и фамилию
                    System.out.println("################################### Name: ################### " + getName(signUp));                                        //выводим имя
                }
            } catch (RpcException e) {                                                       //Если возникла ошибка
                if (e.getMessage().equals("PHONE_CODE_INVALID")) {                              //Если неверный код
                    System.out.println("Введен неверный код");                             //Выводим сообщение
                    JOptionPane.showMessageDialog(formConfirmSMS.getRootPanel(),"Введен неверный код");
                    formConfirmSMS.getFieldSMS().requestFocus();
                    continue;
                }
            }
            break;
        }
    }

    private static User getName(AuthAuthorization sign){
        return sign.getUser();
    }

    private static void checkPhone() throws IOException {
        String phoneNumber = formPhone.getFieldPhone().getText().replaceAll("\\D+","");
        if (phoneNumber.length() != 11) {
            JOptionPane.showMessageDialog(formPhone.getRootPanel(), "Неверно заполнен номер");
            formPhone.getFieldPhone().requestFocus();
        } else {
            AuthCheckedPhone checkPhone = bridge.authCheckPhone(phoneNumber);
            bridge.authSendCode(phoneNumber);
            if (!checkPhone.isRegistered())
                registration();
            else
                authBySMS();
        }

    }
}