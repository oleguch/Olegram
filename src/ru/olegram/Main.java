package ru.olegram;





import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;
import org.javagram.response.AuthSentCode;
import org.javagram.response.object.User;
import org.telegram.api.engine.RpcException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        User nameUser;                                                                  //создадим отдельную переменную для имени (необязательно, но может пригодится)
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        TelegramApiBridge bridge = new TelegramApiBridge("149.154.167.50:443", 95568, "e5649ac9f0c517643f3c8cad067ac7b0");
        System.out.println("Введите номер телефона (в виде 79001234567");
        String phoneNumber = reader.readLine().trim();                                  //Вводим номер телефона и запоминаем номер
        phoneNumber = phoneNumber.replaceAll("\\D+","");                                 //Убираем всё, кроме цифр
        AuthCheckedPhone checkPhone = bridge.authCheckPhone(phoneNumber);              //Проверяем номер телефон
        System.out.println(checkPhone.isRegistered());                                  //выводим рзультат проверки
        bridge.authSendCode(phoneNumber);                                                  //Отправляем код через смс

        while (true) {
            System.out.println("Введите код из СМС");
            String smsCode = reader.readLine().trim();
            try {
                AuthAuthorization signIn = bridge.authSignIn(smsCode);                       //отправляем только код из смс и авторизовываем пользователя
                nameUser = signIn.getUser();                                                 //Если получилось, получаем имя
                System.out.println(nameUser);
            } catch (RpcException e) {                                                       //Если возникла ошибка
                System.out.println(e.getMessage());                                          //Выводим её текст
                if (e.getMessage().equals("PHONE_CODE_INVALID")) {                              //Если неверный код
                    System.out.println("Введен неверный номер");                             //Выводим сообщение
                    continue;                                                                //И снова просим ввести
                } else if (e.getMessage().equals("PHONE_NUMBER_UNOCCUPIED")) {              //Если номер не зарегистрирован
                    System.out.println("Номер не зарегестрирован");
                    System.out.println("Вы не зарегистрированы. Введите свое имя:");
                    String firstName = reader.readLine().trim();                              //просим ввести имя и фамилию
                    System.out.println("Введите свою фамилию:");
                    String lastName = reader.readLine().trim();
                    AuthAuthorization signUp = bridge.authSignUp(smsCode, firstName, lastName);    //и регистрируем, отправив код из смс, имя и фамилию
                    nameUser = signUp.getUser();                                                  //выводим имя
                    System.out.println(nameUser);
                }
            }
            break;
        }
        bridge.authLogOut();
    }
}
