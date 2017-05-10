package ru.olegram;
import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;
import org.javagram.response.object.User;
import org.telegram.api.engine.RpcException;

import java.io.IOException;

public class Main {

    private static String phoneNumber;
    private static AuthCheckedPhone checkPhone;
    private static MyFrame frame;

    private static TelegramApiBridge bridge;

    public static TelegramApiBridge getBridge() {
        return bridge;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static AuthCheckedPhone getCheckPhone() {
        return checkPhone;
    }

    static {
        try {
            bridge = new TelegramApiBridge("149.154.167.50:443", 95568, "e5649ac9f0c517643f3c8cad067ac7b0");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        frame = new MyFrame();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static void checkPhone() throws IOException {
        phoneNumber = frame.getFormPhone().getFieldPhone().getText().replaceAll("\\D+","");
        try {
            checkPhone = getBridge().authCheckPhone(phoneNumber);
            getBridge().authSendCode(Main.getPhoneNumber());
            if (!checkPhone.isRegistered())
                registration();
            else
                frame.authBySMS();
        } catch (RpcException e2) {                                                       //Если возникла ошибка
            frame.getFormPhone().messengerError(e2);
        }
    }

    private static void registration() throws IOException {
        frame.toFormNewUser();
    }

    static void confirmSMS(String firstName, String lastName) {
        String smsCode = new String(frame.getFormConfirmSMS().getPasswordField().getPassword());
        System.out.println("Введите код из СМС:");
        try {
            if ((firstName == null) && (lastName == null)) {                                            //Если фио пустые, то авторизовываем, иначе регистрируем
                AuthAuthorization signIn = Main.getBridge().authSignIn(smsCode);                        //отправляем только код из смс и авторизовываем пользователя
                System.out.println(" Name: " + getName(signIn));                                        //Если получилось, получаем имя
            } else {
                AuthAuthorization signUp = Main.getBridge().authSignUp(smsCode, firstName, lastName);    //и регистрируем, отправив код из смс, имя и фамилию
                System.out.println(" NewName: " + getName(signUp));                                      //выводим имя
            }
            Thread.sleep(100);                  //ВНИМАНИЕ, почему-то иначе дейсвтует через раз, бывает сразу отображает контакты, бывает зависает окно
            frame.toFormFriends();
        } catch (RpcException e2) {                                                       //Если возникла ошибка
            frame.getFormConfirmSMS().messageError(e2);
        } catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
        }
    }


    public static MyFrame getFrame() {
        return frame;
    }

    static User getName(AuthAuthorization sign){
        return sign.getUser();
    }
}