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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class MyFrame extends JFrame{
    private FormConfirmSMS formConfirmSMS = new FormConfirmSMS();
    private FormPhone formPhone = new FormPhone();
    private FormNewUser formNewUser = new FormNewUser();
    private static final int ERROR_PHONE_NUMBER_INVALID = 1,
                            ERROR_CODE_INVALID = 2,
                            ERROR_FLOOD = 3;
    private static Boolean useLocal = false;
    private static String phoneNumber;
    private static AuthCheckedPhone checkPhone;
    private static TelegramApiBridge bridge;

    MyFrame() throws Exception {
        bridge = new TelegramApiBridge("149.154.167.50:443", 95568, "e5649ac9f0c517643f3c8cad067ac7b0");
        setContentPane(formPhone.getRootPanel());
        formPhone.getFieldPhone().requestFocusInWindow();
        setTitle("Olegram");
        setSize(800,600);
        setMinimumSize(new Dimension(500,400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                formPhone.getFieldPhone().requestFocusInWindow();
            }
        });

        formPhone.addActionListenerForChangeForm(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (useLocal)
                        checkPhoneLocal();
                    else
                        checkPhone();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        formConfirmSMS.addActionListenerForChangeForm(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (useLocal) {
                    try {
                        confirmSMSLocal();
                    } catch (IOException | InterruptedException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    if (!checkPhone.isRegistered()) {
                        Person person = formNewUser.getPerson();
                        confirmSMS(person.getName(), person.getSurname());
                    } else
                        confirmSMS(null, null);
                }
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

    private void checkNewUser() throws IOException {
        Person person = formNewUser.getPerson();
        if (person.getName().isEmpty()) {
            showMessage("Не заполнено поле Имя");
            formNewUser.setFocusToName();
        } else if (person.getSurname().isEmpty()) {
            showMessage("Не заполнено поле Фамилия");
            formNewUser.setFocusToSurname();
        } else {
            toFormConfirmSMS();
        }
    }

    private void toFormNewUser() throws IOException {
        nextForm(formNewUser.getRootPanel());
        formNewUser.setFocusToName();
    }

    private void toFormConfirmSMS() {
        setContentPane(formConfirmSMS.getRootPanel());
        formConfirmSMS.getTextLabelSMS().setText("На номер " + phoneNumber + "\nотправен код через СМС. " + "\nВведите его в следующем поле.");
        formConfirmSMS.setFocusToCodeField();
    }

    private void checkPhone() throws IOException {
        phoneNumber = (String) formPhone.getFieldPhone().getValue();
        if (phoneNumber == null)
            showMessage("Введен пустой номер");
        else {
            String phoneOnlyNumber = phoneNumber.replaceAll("\\D+", "");
            try {
                checkPhone = bridge.authCheckPhone(phoneOnlyNumber);
                bridge.authSendCode(phoneOnlyNumber);
                if (!checkPhone.isRegistered())
                    toFormNewUser();
                else
                    toFormConfirmSMS();
            } catch (RpcException e) {                                                       //Если возникла ошибка
                switch (checkMessageError(e)) {
                    case ERROR_PHONE_NUMBER_INVALID:
                        showMessage("Введен неверный номер телефона");
                        formPhone.setFocusToFieldPhone();
                        break;
                    case ERROR_FLOOD:
                        showMessage("Много попыток входа, ждите " + e.getMessage().substring(11) + " секунд");
                        formPhone.setFocusToFieldPhone();
                        break;
                    default:
                        e.printStackTrace();
                }
            }
        }
    }

    private void checkPhoneLocal() throws IOException {
        phoneNumber = (String) formPhone.getFieldPhone().getValue();
        if (phoneNumber == null)
            showMessage("Введен пустой номер");
        else {
            String phoneOnlyNumber = phoneNumber.replaceAll("\\D+", "");
            if (phoneOnlyNumber.equals("71111111111"))
                toFormNewUser();
            else if (phoneOnlyNumber.equals("72222222222")) {
                toFormConfirmSMS();
            } else
                showMessage("Ошибка номера");
        }
    }

    private void confirmSMS(String firstName, String lastName) {
        String smsCode = new String(formConfirmSMS.getCodeField().getPassword());
        System.out.println("Введите код из СМС:");
        try {
            if ((firstName == null) && (lastName == null)) {                                            //Если фио пустые, то авторизовываем, иначе регистрируем
                AuthAuthorization signIn = bridge.authSignIn(smsCode);                        //отправляем только код из смс и авторизовываем пользователя
                System.out.println(" Name: " + getName(signIn));
            } else {
                AuthAuthorization signUp = bridge.authSignUp(smsCode, firstName, lastName);    //регистрируем, отправив код из смс, имя и фамилию
                System.out.println(" NewName: " + getName(signUp));
            }
            Thread.sleep(100);                  //ВНИМАНИЕ, почему-то иначе действует через раз, бывает сразу отображает контакты, бывает зависает окно
            getFriendsList();
        } catch (RpcException e2) {                                                       //Если возникла ошибка
            if (checkMessageError(e2) == ERROR_CODE_INVALID) {
                showMessage("Введен неверный код");
                formConfirmSMS.getCodeField().setText(null);
                formConfirmSMS.getCodeField().requestFocus();
            } else
                e2.printStackTrace();
        } catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
        }
    }
    private void confirmSMSLocal() throws IOException, InterruptedException {
        String smsCode = new String(formConfirmSMS.getCodeField().getPassword());
        if (smsCode.equals("11111"))
            getFriendsList();
        else {
            showMessage("Неверный код");
            formConfirmSMS.getCodeField().setText("");
            formConfirmSMS.setFocusToCodeField();
        }
    }

    private void getFriendsList() throws IOException {
        JOptionPane.showMessageDialog(MyFrame.this, "Введен верный код", "Успешно", JOptionPane.INFORMATION_MESSAGE);
        if (!useLocal) {
            ArrayList<UserContact> myFriends = bridge.contactsGetContacts();
            System.out.println("Список друзей:" + myFriends);
            for (UserContact friend : myFriends) {
                System.out.println("Имя: " + friend.getFirstName());
                System.out.println("Фамилия: " + friend.getLastName());
                System.out.println("Телефон: " + friend.getPhone() + "\n");
            }
            bridge.authLogOut();
        }
    }

    private User getName(AuthAuthorization sign){
        return sign.getUser();
    }

    private int checkMessageError(Exception e) {
        if (e.getMessage().equals("PHONE_NUMBER_INVALID")) {                              //Если неверный номер телефона
            return ERROR_PHONE_NUMBER_INVALID;
        } else if (e.getMessage().substring(0,10).equals("FLOOD_WAIT")) {
            return ERROR_FLOOD;
        } else if (e.getMessage().equals("PHONE_CODE_INVALID")) {                              //Если неверный код
            return ERROR_CODE_INVALID;
        }
        return 0;
    }

    private void nextForm (JPanel panel) {
        setContentPane(panel);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(MyFrame.this, message, "Ошибка", JOptionPane.WARNING_MESSAGE);
    }
}

