import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;
import org.javagram.response.object.User;
import org.javagram.response.object.UserContact;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class MyFrame extends JFrame{
    private FormConfirmSMS formConfirmSMS = new FormConfirmSMS();
    private FormPhone formPhone = new FormPhone();
    private FormNewUser formNewUser = new FormNewUser();
    private String phoneNumber;
    private AuthCheckedPhone checkPhone;
    private TelegramApiBridge bridge;

    MyFrame() throws Exception {
        bridge = new TelegramApiBridge("149.154.167.50:443", 95568, "e5649ac9f0c517643f3c8cad067ac7b0");
        setContentPane(formPhone.getRootPanel());
        setTitle("Olegram");
        setSize(800,600);
        setMinimumSize(new Dimension(500,400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {           //установка фокуса
                super.windowOpened(e);
                formPhone.setFocusToFieldPhone();
            }
        });

        formPhone.addActionListenerForChangeForm(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkPhone();
            }
        });
        formConfirmSMS.addActionListenerForChangeForm(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkPhone.isRegistered()) {
                    Person person = formNewUser.getPerson();
                    confirmSMS(person.getName(), person.getSurname());
                } else
                    confirmSMS(null, null);
            }
        });
        formNewUser.addActionListenerForChangeForm(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkNewUser();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    //проверка ввода полей имени-фамилии нового пользователя
    private void checkNewUser() throws IOException {
        Person person = formNewUser.getPerson();
        if (person.getName().isEmpty()) {
            showMessage("Не заполнено поле Имя");
            formNewUser.setFocusToName();
        } else if (person.getSurname().isEmpty()) {
            showMessage("Не заполнено поле Фамилия");
            formNewUser.setFocusToSurname();
        } else {                    //если удачно, переходим в форму подтверждения номера через смс
            toFormConfirmSMS();
        }
    }

    //на форму регистрации нового пльзователя
    private void toFormNewUser() throws IOException {
        nextForm(formNewUser.getRootPanel());
        formNewUser.setFocusToName();
    }

    //на форму подтверждения кода смс
    private void toFormConfirmSMS() {
        setContentPane(formConfirmSMS.getRootPanel());
        formConfirmSMS.getTextLabelSMS().setText("На номер " + phoneNumber + "\nотправен код через СМС. " + "\nВведите его в следующем поле.");
        formConfirmSMS.setFocusToCodeField();
    }

    //проверка ввода номера телефона
    private void checkPhone() {
        try {
            phoneNumber = formPhone.getPhoneNumber();
            System.out.println(phoneNumber);
            checkPhone = bridge.authCheckPhone(phoneNumber);
            bridge.authSendCode(phoneNumber);
            if (!checkPhone.isRegistered())     //если телефон не зарегистрирован, показать форму регистрации
                toFormNewUser();
            else
                toFormConfirmSMS();             //иначе - форма ввода кода смс
        } catch (IOException | ParseException e2) {
            e2.printStackTrace();
            showMessage( e2.getClass().toString() + "\n" + " " + e2.getMessage());
        }
    }

    //проверка ввода кода смс
    private void confirmSMS(String firstName, String lastName) {
        String smsCode = formConfirmSMS.getCode();
        try {
            if ((firstName == null) && (lastName == null)) {                                            //Если фио пустые, то авторизовываем, иначе регистрируем
                AuthAuthorization signIn = bridge.authSignIn(smsCode);                        //отправляем только код из смс и авторизовываем пользователя
                System.out.println(" Name: " + getName(signIn));
            } else {
                AuthAuthorization signUp = bridge.authSignUp(smsCode, firstName, lastName);    //регистрируем, отправив код из смс, имя и фамилию
                System.out.println(" NewName: " + getName(signUp));
            }
            getFriendsList();               //показать список друзей
        } catch (IOException e2) {          //при ошибке показать тип и сообщение
            e2.printStackTrace();
            showMessage( e2.getClass().toString() + "\n" + " " + e2.getMessage());
        }
    }

    //список друзей
    private void getFriendsList() throws IOException {
        JOptionPane.showMessageDialog(MyFrame.this, "Введен верный код", "Успешно", JOptionPane.INFORMATION_MESSAGE);
        ArrayList<UserContact> myFriends = bridge.contactsGetContacts();
        System.out.println("Список друзей:" + myFriends);
        for (UserContact friend : myFriends) {
            System.out.println("Имя: " + friend.getFirstName());
            System.out.println("Фамилия: " + friend.getLastName());
            System.out.println("Телефон: " + friend.getPhone() + "\n");
        }
        bridge.authLogOut();
    }

    private User getName(AuthAuthorization sign){
        return sign.getUser();
    }

    //переключение формы
    private void nextForm (JPanel panel) {
        setContentPane(panel);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    //показать сообщение об ошибке
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(MyFrame.this, message, "Ошибка", JOptionPane.WARNING_MESSAGE);
    }
}

