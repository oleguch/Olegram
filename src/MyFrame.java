import org.javagram.dao.ApiException;
import org.javagram.dao.Contact;
import org.javagram.dao.TelegramDAO;
import javax.swing.*;
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
    private static final int NAME_EMPTY=1,
                            SURNAME_EMPTY = 2,
                            FIELD_OK = 3;
    private TelegramDAO telegramDAO;

    MyFrame(TelegramDAO telegramDAO) throws Exception {
        this.telegramDAO = telegramDAO;
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
                if (telegramDAO.isLoggedIn())
                    try {
                        telegramDAO.logOut();                   //если я правильно понял, он сам проверяет, залогирован ли пользователь. Но если не залоггирован, то выбрасывает исключение
                    } catch (ApiException | IOException e1) {
                        e1.printStackTrace();
                    }
                System.exit(0 );
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
            telegramDAO.sendCode();
            if (telegramDAO.canSignIn())     //если телефон зарегистрирован, показать форму ввода кода смс
                toFormConfirmSMS();
            else
                toFormNewUser();                //иначе - форму регистрации
        } catch (IOException | ApiException e2) {
            e2.printStackTrace();
            showMessageError( e2.getClass().toString() + "\n" + " " + e2.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            showMessageError("Номер введен не полностью");
            formPhone.setFocusToFieldPhone();
        }
    }

    //проверка ввода кода смс
    private void confirmByCodeFromSMS() {
        String smsCode = formConfirmSMS.getCode();
        try {
            if (telegramDAO.canSignIn())                              //Если пользователь зарегистрирован, то авторизовываем, иначе регистрируем
                telegramDAO.signIn(smsCode);                                                        //отправляем только код из смс и авторизовываем пользователя
            else {
                Person person = formNewUser.getPerson();
                telegramDAO.signUp(person.getName(), person.getSurname(), smsCode);         //регистрируем, отправив код из смс, имя и фамилию
            }
            toFormUserList();                                                           //показать список друзей в консоли
        } catch (IOException | ApiException e2) {                                                      //при ошибке показать тип и сообщение
            e2.printStackTrace();
            showMessageError( e2.getClass().toString() + "\n" + " " + e2.getMessage());
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
        //formUsersList.setListData(userList.toArray());
        String[] nameCont = new String[userList.size()];
        for (int i=0; i< userList.size();i++) {
            nameCont[i] = userList.get(i).getFirstName();
        }
        formUsersList.setListData(nameCont);
        nextForm(formUsersList.getRootPanel());
    }

    //переключение формы
    private void nextForm (JPanel panel) {
        decoration.setContentPanel(panel);
    }

    //показать сообщение об ошибке
    private void showMessageError(String message) {
        Decoration.showOptionDialog(MyFrame.this, message, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, null, null);
    }


}

