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
    private FormFriends formFriends = new FormFriends();
    private  FormWindow formWindow;
    private static final int ERROR_PHONE_NUMBER_INVALID = 1,
                            ERROR_CODE_INVALID = 2,
                            ERROR_FLOOD = 3;

    private static String phoneNumber;
    private static AuthCheckedPhone checkPhone;
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

    FormConfirmSMS getFormConfirmSMS() {
        return formConfirmSMS;
    }
    FormPhone getFormPhone() {
        return formPhone;
    }
    FormNewUser getFormNewUser() {
        return formNewUser;
    }
    FormFriends getFormFriends() {
        return formFriends;
    }
    FormConfirmSMS getConfirmSMS() {
        return formConfirmSMS;
    }
    FormWindow getFormWindow() {
        return formWindow;
    }

    MyFrame() throws Exception {
        setContentPane(formPhone.getRootPanel());
        formWindow = new FormWindow(this);
        formWindow.setContentPanel(formPhone.getRootPanel());
        formPhone.getFieldPhoneFormatted().requestFocusInWindow();
        setTitle("Olegram");
        setSize(800,600);
        setMinimumSize(new Dimension(500,400));
        setUndecorated(true);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });

        addActionToChangeForm(changeForm);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                formPhone.getFieldPhoneFormatted().requestFocusInWindow();
            }
        });
    }

    //                  Переключение окон (по кнопке Продолжить, по Enter в соответствующих формах)
    private Action changeForm = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                if (formWindow.getContentPanel().equals(getFormPhone().getRootPanel()))
                    //checkPhone();
                    checkPhoneLocal();
                else if (formWindow.getContentPanel().equals(getConfirmSMS().getRootPanel()))
//                    if (!getCheckPhone().isRegistered()) {
//                        confirmSMS(formNewUser.getRegName().getText(), formNewUser.getRegSurname().getText());
//                    } else
//                        confirmSMS(null, null);
                        confirmSMSLocal();
                else if (formWindow.getContentPanel().equals(getFormNewUser().getRootPanel()))
                    checkNewUser();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    };


    private void addActionToChangeForm(ActionListener listener) {
        getFormPhone().addActionListenerForChangeForm(listener);  //Действия по смене формы при нажатии на Продолжить или при нажатии Enter в поле
        getConfirmSMS().addActionListenerForChangeForm(listener);
        getFormNewUser().addActionListenerForChangeForm(listener);
    }

    private void toFormNewUser() throws IOException {
        formWindow.setContentPanel(formNewUser.getRootPanel());
        formNewUser.getRegName().requestFocusInWindow();
        System.out.println("Номер не зарегестрирован");
    }

    private void checkNewUser() throws IOException {
        String firstName = formNewUser.getRegName().getText().trim();
        String lastName = formNewUser.getRegSurname().getText().trim();
        if (firstName.length() == 0) {
            showMessage("Не заполнено поле Имя");
            formNewUser.getRegName().requestFocus();
        } else if (lastName.length() == 0) {
            showMessage("Не заполнено поле Фамилия");
            formNewUser.getRegSurname().requestFocus();
        } else {
            toFormConfirmSMS();
        }
    }

    //Отрисовка формы ввода кода СМС
    private void toFormConfirmSMS() {
        getFormWindow().setContentPanel(getFormConfirmSMS().getRootPanel());
        formConfirmSMS.getTextLabelSMS().setText("На номер " + getPhoneNumber() + "\nотправен код через СМС. " + "\nВведите его в следующем поле.");
        formConfirmSMS.getPasswordField().requestFocus();
    }

    private void toFormFriends() throws IOException, InterruptedException {
        getFormWindow().setContentPanel(getFormFriends().getRootPanel());
        //getFriendsList();
        getFriendsListLocal();
    }

    //Для работы с серверами Telegram
    private void getFriendsList() throws IOException {
        ArrayList<UserContact> myFriends = getBridge().contactsGetContacts();
        System.out.println("Список друзей:" + myFriends);
        for (UserContact friend : myFriends) {
            System.out.println("Имя: " + friend.getFirstName());
            System.out.println("Фамилия: " + friend.getLastName());
            System.out.println("Телефон: " + friend.getPhone() + "\n");
            formFriends.getListModel().addElement(friend.getFirstName() + " " + friend.getLastName() + " (" + friend.getPhone() + ")");
        }
    }

    //Для локальной проверки работы приложения
    private void getFriendsListLocal() {
        formFriends.getListModel().addElement("Вася Пупкин (+7(123)456-78-78)");
        formFriends.getListModel().addElement("Катя Попова (+7(987)654-21-21)");
    }

    private void checkPhone() throws IOException {
        //phoneNumber = getFormPhone().getFieldPhoneFormatted().getText().replaceAll("\\D+","");
        phoneNumber = (String) getFormPhone().getFieldPhoneFormatted().getValue();
        if (phoneNumber == null)
            showMessage("Введен пустой номер");
        else {
            String phoneOnlyNumber = phoneNumber.replaceAll("\\D+", "");
            try {
                checkPhone = getBridge().authCheckPhone(phoneOnlyNumber);
                getBridge().authSendCode(getPhoneNumber());
                if (!checkPhone.isRegistered())
                    toFormNewUser();
                else
                    toFormConfirmSMS();
            } catch (RpcException e) {                                                       //Если возникла ошибка
                switch (checkMessageError(e)) {
                    case ERROR_PHONE_NUMBER_INVALID:
                        System.out.println("Введен неверный номер телефона");                             //Выводим сообщение
                        showMessage("Введен неверный номер телефона");
                        getFormPhone().getFieldPhoneFormatted().requestFocus();
                        break;
                    case ERROR_FLOOD:
                        System.out.println("Много попыток входа");                             //Выводим сообщение
                        showMessage("Много попыток входа, ждите " + e.getMessage().substring(11) + " секунд");
                        getFormPhone().getFieldPhoneFormatted().requestFocus();
                        break;
                    default:
                        e.printStackTrace();
                }
            }
        }
    }

    private void checkPhoneLocal() throws IOException {
        phoneNumber = (String) getFormPhone().getFieldPhoneFormatted().getValue();
        if (phoneNumber == null)
            showMessage("Введен пустой номер");
        else {
//        phoneNumber = getFormPhone().getFieldPhoneFormatted().getText();
            String phoneOnlyNumber = phoneNumber.replaceAll("\\D+", "");
            if (phoneOnlyNumber.equals("71111111111"))
                toFormNewUser();
            else if (phoneOnlyNumber.equals("72222222222")) {
                toFormConfirmSMS();
            } else
                showMessage("Ошибка номера");
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(MyFrame.this, message, "Ошибка", JOptionPane.WARNING_MESSAGE);
    }

    private void confirmSMS(String firstName, String lastName) {
        String smsCode = new String(getFormConfirmSMS().getPasswordField().getPassword());
        System.out.println("Введите код из СМС:");
        try {
            if ((firstName == null) && (lastName == null)) {                                            //Если фио пустые, то авторизовываем, иначе регистрируем
                AuthAuthorization signIn = getBridge().authSignIn(smsCode);                        //отправляем только код из смс и авторизовываем пользователя
                System.out.println(" Name: " + getName(signIn));                                        //Если получилось, получаем имя
            } else {
                AuthAuthorization signUp = getBridge().authSignUp(smsCode, firstName, lastName);    //и регистрируем, отправив код из смс, имя и фамилию
                System.out.println(" NewName: " + getName(signUp));                                      //выводим имя
            }
            Thread.sleep(100);                  //ВНИМАНИЕ, почему-то иначе действует через раз, бывает сразу отображает контакты, бывает зависает окно
            toFormFriends();
        } catch (RpcException e2) {                                                       //Если возникла ошибка
            if (checkMessageError(e2) == ERROR_CODE_INVALID) {
                System.out.println("Введен неверный код");                             //Выводим сообщение
                showMessage("Введен неверный код");
                getFormConfirmSMS().getPasswordField().setText(null);
                getFormConfirmSMS().getPasswordField().requestFocus();
            } else
                e2.printStackTrace();
        } catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
        }
    }
    private void confirmSMSLocal() throws IOException, InterruptedException {
        String smsCode = new String(getFormConfirmSMS().getPasswordField().getPassword());
        if (smsCode.equals("1111"))
            toFormFriends();
        else {
            showMessage("Неверный код");
            getFormConfirmSMS().getPasswordField().setText(null);
            getFormConfirmSMS().getPasswordField().requestFocus();
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
}

