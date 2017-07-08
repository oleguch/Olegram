import org.javagram.dao.*;
import org.javagram.dao.proxy.TelegramProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try {
                    telegramDAO.close();
                    System.exit(0);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
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
        PersonNewUser person = formNewUser.getPerson();
        if (person.getName().isEmpty()) {
            return NAME_EMPTY;
        } else if (person.getSurname().isEmpty()) {
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
        } catch (ApiException e2) {
            e2.printStackTrace();
            if (e2.isPhoneNumberInvalid())
                showMessageError("Номер телефона введен неверно");
        } catch (ParseException e) {
            e.printStackTrace();
            showMessageError("Номер введен не полностью");
            formPhone.setFocusToFieldPhone();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //проверка ввода кода смс
    private void confirmByCodeFromSMS() {
        String smsCode = formConfirmSMS.getCode();
        System.out.println(smsCode);
        try {
            if (telegramDAO.canSignIn())                              //Если пользователь зарегистрирован, то авторизовываем, иначе регистрируем
                telegramDAO.signIn(smsCode);                                                        //отправляем только код из смс и авторизовываем пользователя
            else {
                PersonNewUser person = formNewUser.getPerson();
                telegramDAO.signUp(person.getName(), person.getSurname(), smsCode);         //регистрируем, отправив код из смс, имя и фамилию
            }
            toFormUserList();                                                           //показать список друзей в консоли
        } catch (IOException e2) {                                                      //при ошибке показать тип и сообщение
            e2.printStackTrace();
            showMessageError( e2.getClass().toString() + "\n" + " " + e2.getMessage());
        } catch (ApiException e3) {
            if (e3.isCodeInvalid()) {
                showMessageError("Код введен неверно");
            } else if (e3.isCodeEmpty()) {
                showMessageError("Введите код подтверждения");
            } else if (e3.isCodeExpired()) {
                showMessageError("Код устарел. Отправляем новый код");
                try {
                    telegramDAO.sendCode();
                } catch (Exception e) {             //не уверен, что правильно, в примере все разбито в разных местах, и эта ошибка вроде ловится через два try
                    e.printStackTrace();
                }
            }
        }
    }


    private void toFormUserList() throws IOException, ApiException {
        TelegramProxy telegramProxy = new TelegramProxy(telegramDAO);
        List<Person> list = telegramProxy.getPersons();
        ArrayList<String> users = new ArrayList<>();
        for (org.javagram.dao.Person person: list)
            users.add(person.getFirstName() + " " + person.getLastName());
        formUsersList.setListData(users.toArray());
        nextForm(formUsersList.getRootPanel());
    }

    //переключение формы
    private void nextForm (JPanel panel) {
        decoration.setContentPanel(panel);
    }

    //показать сообщение об ошибке
    private void showMessageError(String message) {
        //Decoration.showOptionDialog(MyFrame.this, message, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, null, null);
        Decoration.showOptionDialog(MyFrame.this, message, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, createDecoratedButtons(JOptionPane.DEFAULT_OPTION), createDecoratedButtons(JOptionPane.DEFAULT_OPTION)[0]);
    }






    public static JButton createDecoratedButton(int buttonType) {
        JButton button = new JButton(getText(buttonType));
        Dimension size = new Dimension(80,30);
        button.setMinimumSize(size);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setSize(size);
        Images.decorateAsImageButton(button, Images.getButtonImage(), Images.getButtonImagePressed(), Color.WHITE);
        return button;
    }



    public static JButton[] createDecoratedButtons(int buttonsType) {
        switch (buttonsType) {
            case JOptionPane.DEFAULT_OPTION:
                return new JButton[] {
                        createDecoratedButton(JOptionPane.DEFAULT_OPTION)
                };
            case JOptionPane.OK_CANCEL_OPTION:
                return new JButton[] {
                        createDecoratedButton(JOptionPane.DEFAULT_OPTION),
                        createDecoratedButton(JOptionPane.CANCEL_OPTION)
                };
            case JOptionPane.YES_NO_OPTION:
                return new JButton[] {
                        createDecoratedButton(JOptionPane.YES_OPTION),
                        createDecoratedButton(JOptionPane.NO_OPTION)
                };
            case JOptionPane.YES_NO_CANCEL_OPTION:
                return new JButton[] {
                        createDecoratedButton(JOptionPane.YES_OPTION),
                        createDecoratedButton(JOptionPane.NO_OPTION),
                        createDecoratedButton(JOptionPane.CANCEL_OPTION)
                };
            default:
                return null;
        }
    }

    private static String getText(int buttonType) {
        switch (buttonType) {
            case JOptionPane.DEFAULT_OPTION:
                return "Ok";
            case JOptionPane.CANCEL_OPTION:
                return "Отмена";
            case JOptionPane.YES_OPTION:
                return "Да";
            case JOptionPane.NO_OPTION:
                return "Нет";
            default:
                return null;
        }
    }


}

