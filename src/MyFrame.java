import org.javagram.TelegramApiBridge;
import org.javagram.dao.ApiException;
import org.javagram.dao.Contact;
import org.javagram.dao.TelegramDAO;
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
    private FormUsersList formUsersList = new FormUsersList();
    private Decoration decoration;
    private String phoneNumber;
    private AuthCheckedPhone checkPhone;
    //private TelegramApiBridge bridge;
    private AuthAuthorization authSing;
    private static final int NAME_EMPTY=1,
                            SURNAME_EMPTY = 2,
                            FIELD_OK = 3;
    private TelegramDAO telegramDAO;

    MyFrame(TelegramDAO telegramDAO) throws Exception {
        this.telegramDAO = telegramDAO;
        //bridge = new TelegramApiBridge("149.154.167.50:443", 95568, "e5649ac9f0c517643f3c8cad067ac7b0");
        setContentPane(formPhone.getRootPanel());
        decoration = new Decoration(this);
        decoration.setContentPanel(formPhone.getRootPanel());
        //decoration.setContentPanel(formConfirmSMS.getRootPanel());
        //decoration.setContentPanel(formNewUser.getRootPanel());
        setUndecorated(true);
        setTitle("Olegram");
        setSize(800, 600);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {           //установка фокуса
                super.windowOpened(e);
                formPhone.setFocusToFieldPhone();

            }
        });
        decoration.addActionListenerForMinimize(e -> setExtendedState(JFrame.ICONIFIED));

        //ЗАКРЫТИЕ ОКНА
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {

                    super.windowClosed(e);
//                    try {
//                        if (authSing != null)
//                            bridge.authLogOut();                //логаут. Не работает (программа не завершает работу)
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                    }
                    System.exit(0);
            }
        });
        decoration.addActionListenerForClose(e -> dispose());

        //СМЕНА ФОРМ
        formPhone.addActionListenerForChangeForm(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkPhoneAndSendCode();
            }
        });
        formConfirmSMS.addActionListenerForChangeForm(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmByCodeFromSMS();
            }
        });
        formNewUser.addActionListenerForChangeForm(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (checkFieldsFormNewUser()) {
                    case NAME_EMPTY:
                        showMessageError("Не заполнено поле Имя");
                        formNewUser.setFocusToName();
                        break;
                    case SURNAME_EMPTY:
                        showMessageError("Не заполнено поле Фамилия");
                        formNewUser.setFocusToSurname();
                        break;
                    case FIELD_OK:
                        toFormConfirmSMS();
                        break;
                }

            }
        });
    }

    //проверка ввода полей имени-фамилии нового пользователя
    private int checkFieldsFormNewUser()  {
        Person person = formNewUser.getPerson();
        if (person.getName().isEmpty() || person.getName().equals("Имя")) {
            return NAME_EMPTY;
        } else if (person.getSurname().isEmpty() || person.getSurname().equals("Фамилия")) {
            return SURNAME_EMPTY;
        } else {
            return FIELD_OK;
        }
    }

    //на форму регистрации нового пльзователя
    private void toFormNewUser() {
        nextForm(formNewUser.getRootPanel());
        formNewUser.setFocusToName();
    }

    //на форму подтверждения кода смс
    private void toFormConfirmSMS() {
        nextForm(formConfirmSMS.getRootPanel());
        formConfirmSMS.setPhoneNumberToLabel(phoneNumber);
        formConfirmSMS.setFocusToCodeField();
    }

    //проверка введенного номера телефона
    private void checkPhoneAndSendCode() {
        try {
            phoneNumber = formPhone.getPhoneNumber();
            telegramDAO.acceptNumber(phoneNumber);
            //checkPhone = bridge.authCheckPhone(phoneNumber);
            telegramDAO.sendCode();
            //bridge.authSendCode(phoneNumber);
            if (telegramDAO.canSignIn())//checkPhone.isRegistered())     //если телефон не зарегистрирован, показать форму ввода кода смс
                toFormConfirmSMS();
            else
                toFormNewUser();                //иначе - форму регистрации
        } catch (IOException e2) {
            e2.printStackTrace();
            showMessageError( e2.getClass().toString() + "\n" + " " + e2.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            showMessageError("Номер введен не полностью");
            formPhone.setFocusToFieldPhone();
        } catch (ApiException e) {
            e.printStackTrace();    //чуть попозже разобраться
        }
    }

    //проверка ввода кода смс
    private void confirmByCodeFromSMS() {
        String smsCode = formConfirmSMS.getCode();
        try {
            if (telegramDAO.canSignIn())//checkPhone.isRegistered())                              //Если пользователь зарегистрирован, то авторизовываем, иначе регистрируем
                //authSing = bridge.authSignIn(smsCode);                                  //отправляем только код из смс и авторизовываем пользователя
                telegramDAO.signIn(smsCode);
            else {
                Person person = formNewUser.getPerson();
                //authSing = bridge.authSignUp(smsCode, person.getName(), person.getSurname());             //регистрируем, отправив код из смс, имя и фамилию
                telegramDAO.signUp(person.getName(), person.getSurname(), smsCode);
            }
            toFormUserList();                                                           //показать список друзей в консоли
        } catch (IOException e2) {                                                      //при ошибке показать тип и сообщение
            e2.printStackTrace();
            showMessageError( e2.getClass().toString() + "\n" + " " + e2.getMessage());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }


    private void toFormUserList() throws IOException, ApiException {
        ArrayList<Contact> userList = telegramDAO.getContacts();//bridge.contactsGetContacts();
//        System.out.println("Список друзей:" + myFriends);
//        for (UserContact friend : myFriends) {
//            System.out.println("Имя: " + friend.getFirstName());
//            System.out.println("Фамилия: " + friend.getLastName());
//            System.out.println("Телефон: " + friend.getPhone() + "\n");
//        }
        String[] nameCont = new String[userList.size()];
        for (int i=0; i< userList.size();i++) {
            nameCont[i] = userList.get(i).getFirstName();
        }
        //formUsersList.setListData(userList.toArray());
        formUsersList.setListData(nameCont);
        nextForm(formUsersList.getRootPanel());
    }

    private User getNameUser(){
        return authSing.getUser();
    }

    //переключение формы
    private void nextForm (JPanel panel) {
        decoration.setContentPanel(panel);
    }

    //показать сообщение об ошибке
    private void showMessageError(String message) {
        //JOptionPane.showMessageDialog(MyFrame.this, message, "Ошибка", JOptionPane.WARNING_MESSAGE);
        Decoration.showOptionDialog(MyFrame.this, message, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, null, null);
    }


}

