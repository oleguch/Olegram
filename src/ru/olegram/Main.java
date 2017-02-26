package ru.olegram;

import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;
import org.javagram.response.object.User;
import org.javagram.response.object.UserContact;
import org.telegram.api.engine.RpcException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        TelegramApiBridge bridge = new TelegramApiBridge("149.154.167.50:443", 95568, "e5649ac9f0c517643f3c8cad067ac7b0");
        System.out.println("##########################################Введите номер телефона (в виде 79001234567)");
        String phoneNumber = reader.readLine().trim();                                  //Вводим номер телефона и запоминаем номер
        phoneNumber = phoneNumber.replaceAll("\\D+","");                                 //Убираем всё, кроме цифр
        AuthCheckedPhone checkPhone = bridge.authCheckPhone(phoneNumber);              //Проверяем номер телефон
        System.out.println(checkPhone.isRegistered());                                  //выводим рзультат проверки
        bridge.authSendCode(phoneNumber);                                                  //Отправляем код через смс
        if (!checkPhone.isRegistered())
            registration(bridge);
        else
            authBySMS(bridge);

        ArrayList<UserContact> myFriends = bridge.contactsGetContacts();
        System.out.println("###############################Список друзей:############################" + myFriends);
        for (UserContact friend : myFriends) {
            System.out.println("Имя: " + friend.getFirstName());
            System.out.println("Фамилия: " + friend.getLastName());
            System.out.println("Телефон: " + friend.getPhone() + "\n");
        }
        bridge.authLogOut();
    }

    private static void registration(TelegramApiBridge bridge) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Номер не зарегестрирован");
        System.out.println("Вы не зарегистрированы. Введите свое имя:");
        String firstName = reader.readLine().trim();                              //просим ввести имя и фамилию
        System.out.println("Введите свою фамилию:");
        String lastName = reader.readLine().trim();
        authBySMS(bridge, firstName, lastName);                         //отправляем на регистрацию
    }

    private static void authBySMS  (TelegramApiBridge bridge) throws IOException {
        authBySMS(bridge, null, null);
    }

    private static void authBySMS  (TelegramApiBridge bridge, String firstName, String lastName) throws IOException {
        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите код из СМС:");
            String smsCode = reader.readLine().trim();
            try {
                if ((firstName == null) && (lastName == null)) {                //Если только bridge передается, то авторизовываем, иначе регистрируем
                    AuthAuthorization signIn = bridge.authSignIn(smsCode);                       //отправляем только код из смс и авторизовываем пользователя
                    System.out.println("################################### Name: ############## " + getName(signIn));                                        //Если получилось, получаем имя
                } else {
                    AuthAuthorization signUp = bridge.authSignUp(smsCode, firstName, lastName);    //и регистрируем, отправив код из смс, имя и фамилию
                    System.out.println("################################### Name: ################### " + getName(signUp));                                        //выводим имя
                }
            } catch (RpcException e) {                                                       //Если возникла ошибка
                if (e.getMessage().equals("PHONE_CODE_INVALID")) {                              //Если неверный код
                    System.out.println("Введен неверный номер");                             //Выводим сообщение
                    continue;
                }
            }
            break;
        }
    }

    public static User getName (AuthAuthorization sign){
        return sign.getUser();
    }
}