package gui;

import additionally.Helper;
import gui.additionally.ContactInfo;
import gui.additionally.PersonNewUser;
import gui.overlays.OverlayDialog;
import gui.overlays.ProfileForm;
import org.javagram.dao.*;
import org.javagram.dao.Dialog;
import org.javagram.dao.proxy.TelegramProxy;
import org.javagram.dao.proxy.changes.UpdateChanges;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;
import gui.guiForms.*;
import gui.additionally.PersonNewUser;


public class MyFrame extends JFrame{
    private FormConfirmSMS formConfirmSMS = new FormConfirmSMS();
    private FormPhone formPhone = new FormPhone();
    private FormNewUser formNewUser = new FormNewUser();
    private FormUsersList formUsersList = new FormUsersList();
    private MainForm mainForm = new MainForm();
    private Decoration decoration;
    private String phoneNumber;
    private ProfileForm profileForm = new ProfileForm();
    private OverlayDialog overlayDialog = new OverlayDialog();
    private int messagesFrozen;





    private static final int NAME_EMPTY=1,
                            SURNAME_EMPTY = 2,
                            FIELD_OK = 3;
    private TelegramDAO telegramDAO;
    private TelegramProxy telegramProxy;

    public MyFrame(TelegramDAO telegramDAO) throws Exception {
        this.telegramDAO = telegramDAO;
        setContentPane(formPhone.getRootPanel());
        decoration = new Decoration(this);
        //decoration.setContentPanel(formPhone.getRootPanel());
        decoration.setContentPanel(overlayDialog);
        nextForm(formPhone.getRootPanel());
        //decoration.setContentPanel(formConfirmSMS.getRootPanel());
        //decoration.setContentPanel(formNewUser.getRootPanel());
        setUndecorated(true);
        setTitle("Olegram");
        setSize(800, 600);




        mainForm.setContactsPanel(formUsersList);

        formUsersList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (telegramProxy != null) {
                    displayBuddy(formUsersList.getSelectedValue());
                }
            }
        });

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
                    abort(e1);
                }
            }
        });
        //decoration.addActionListenerForClose(e -> dispose());
        decoration.addActionListenerForClose(e -> this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));

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
        mainForm.addSettingEventListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Button pressed");
                Me me = telegramProxy.getMe();
                ContactInfo contactInfo = Helper.toContactInfo(me, telegramProxy, false, false);
                profileForm.setContactInfo(contactInfo);
                changeOverlayPanel(profileForm);
            }
        });
        profileForm.addActionListenerForLogout(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchToBegin();
            }
        });
        profileForm.addActionListenerForClose(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeOverlayPanel(null);
            }
        });

        mainForm.addSendMessageListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Person buddy = formUsersList.getSelectedValue();
                String text = mainForm.getMessageText().trim();
                if (telegramProxy != null && buddy != null && !text.isEmpty()) {
                    try {
                        telegramProxy.sendMessage(buddy, text);
                        mainForm.setMessageText("");
                        checkForUpdates(true);
                    } catch (Exception e) {
                        showMessageError("Не могу отправить сообщение");
                    }
                }
            }
        });
        formUsersList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {

                if (listSelectionEvent.getValueIsAdjusting() || messagesFrozen != 0)
                    return;

                if (telegramProxy == null) {
                    displayDialog(null);
                } else {
                    displayDialog(formUsersList.getSelectedValue());
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
            try {
                phoneNumber = formPhone.getPhoneNumber();
                telegramDAO.acceptNumber(phoneNumber);
                telegramDAO.sendCode();
                if (telegramDAO.canSignIn())     //если телефон зарегистрирован, показать форму ввода кода смс
                    toFormConfirmSMS();
                else
                    toFormNewUser();                //иначе - форму регистрации
            } catch (ApiException e2) {
                if (e2.isPhoneNumberInvalid()) {
                    showMessageError("Номер телефона введен неверно");
                    return;
                }
                throw e2;
            } catch (ParseException e) {
                showMessageError("Номер введен не полностью");
                formPhone.setFocusToFieldPhone();
            }
        } catch (Exception e) {
            catchException(e);
        }
    }

    //проверка ввода кода смс
    private void confirmByCodeFromSMS() {
        String smsCode = formConfirmSMS.getCode();
        try {


            try {
                if (telegramDAO.canSignIn())                              //Если пользователь зарегистрирован, то авторизовываем, иначе регистрируем
                    telegramDAO.signIn(smsCode);                                                        //отправляем только код из смс и авторизовываем пользователя
                else {
                    PersonNewUser person = formNewUser.getPerson();
                    telegramDAO.signUp(smsCode, person.getName(), person.getSurname());         //регистрируем, отправив код из смс, имя и фамилию
                }
                toFormUserList();                                                           //показать список друзей в консоли
            } catch (IOException e2) {                                                      //при ошибке показать тип и сообщение
                e2.printStackTrace();
                showMessageError(e2.getClass().toString() + "\n" + " " + e2.getMessage());
            } catch (ApiException e3) {
                if (e3.isCodeInvalid()) {
                    showMessageError("Код введен неверно");
                    return;
                }
                if (e3.isCodeEmpty()) {
                    showMessageError("Введите код подтверждения");
                    return;
                }
                if (e3.isCodeExpired()) {
                    showMessageError("Код устарел. Отправляю новый");
                    sendAndRequestCode();
                }
            }
        }  catch (Exception e) {
            catchException(e);
        }
    }

    private void sendAndRequestCode() throws IOException, ApiException {
        sendCode();
        showCodeRequest();
    }

    private void showCodeRequest() {
        formConfirmSMS.clearCode();
        formConfirmSMS.setFocusToCodeField();
    }

    private void sendCode() throws IOException, ApiException {
        telegramDAO.sendCode();
    }


    private void catchException(Exception e) {
        if (e instanceof IOException) {
            showMessageError("Потеряно соединение с сервером");
        } else if (e instanceof ApiException) {
            showMessageError("Непредвиденная ошибка API :: " + e.getMessage());
        } else {
            showMessageError("Непредвиденная ошибка");
        }
        abort(e);
    }

    private void abort(Throwable e) {
        if (e != null)
            e.printStackTrace();
        else
            System.err.println("Unknown Error");
        try {
            telegramDAO.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.exit(-1);
    }


    private void toFormUserList() throws IOException, ApiException {
        //nextForm(formUsersList);
        nextForm(mainForm);
        createTelegramProxy();
    }

    //переключение формы
    private void nextForm (JPanel panel) {
        //decoration.setContentPanel(panel);
        overlayDialog.setContentPanel(panel);
    }

    //показать сообщение об ошибке
    private void showMessageError(String message) {
        //Decoration.showOptionDialog(gui.MyFrame.this, message, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, null, null);
        JButton[] okButton = Helper.createDecoratedButtons(JOptionPane.DEFAULT_OPTION);
        Decoration.showOptionDialog(MyFrame.this, message, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, okButton, okButton[0]);
    }


    private void createTelegramProxy() throws ApiException {
        telegramProxy = new TelegramProxy(telegramDAO);
        updateTelegramProxy();
    }

    private void destroyTelegramProxy() {
        telegramProxy = null;
        updateTelegramProxy();
    }

    private void updateTelegramProxy() {
        messagesFrozen++;
        try {
            formUsersList.setTelegramProxy(telegramProxy);
            formUsersList.setSelectedValue(null);
            //createMessagesForm();
            //displayDialog(null);
            displayMe(telegramProxy != null ? telegramProxy.getMe() : null);
        } finally {
            messagesFrozen--;
        }
        formUsersList.revalidate();
        formUsersList.repaint();
        mainForm.revalidate();
        mainForm.repaint();
    }

    private void displayMe(Me me) {
        if (me == null) {
            mainForm.setMeText(null);
            mainForm.setMePhoto(null);
        } else {
            mainForm.setMeText(me.getFirstName() + " " + me.getLastName());
            mainForm.setMePhoto(Helper.getPhoto(telegramProxy, me, true, true));
        }
    }

    private void displayBuddy(Person person) {
        if (person == null) {
            mainForm.setBuddyText(null);
            mainForm.setBuddyPhoto(null);
            mainForm.setBuddyEditEnabled(false);
        } else {
            mainForm.setBuddyText(person.getFirstName() + " " + person.getLastName());
            mainForm.setBuddyPhoto(Helper.getPhoto(telegramProxy, person, true, true));
            mainForm.setBuddyEditEnabled(person instanceof Contact);
        }
    }

    private void changeOverlayPanel(Container overlayPanel) {
        overlayDialog.setOverlayPanel(overlayPanel);
    }


    private void switchToBegin() {
        try {
            destroyTelegramProxy();
            changeOverlayPanel(null);
            formPhone.clearNumber();
            formConfirmSMS.clearCode();
            formPhone.setFocusToFieldPhone();
            nextForm(formPhone.getRootPanel());
            telegramDAO.logOut();
        } catch (Exception e) {
            catchException(e);
        }
    }

    protected void checkForUpdates(boolean force) {
        if (telegramProxy != null) {
            UpdateChanges updateChanges = telegramProxy.update(force ? TelegramProxy.FORCE_SYNC_UPDATE : TelegramProxy.USE_SYNC_UPDATE);

            int photosChangedCount = updateChanges.getLargePhotosChanged().size() +
                    updateChanges.getSmallPhotosChanged().size() +
                    updateChanges.getStatusesChanged().size();

            if (updateChanges.getListChanged()) {
                updateContacts();
            } else if (photosChangedCount != 0) {
                formUsersList.repaint();
            }

            Person currentBuddy = getMessagesForm().getPerson();
            Person targetPerson = formUsersList.getSelectedValue();

            Dialog currentDialog = currentBuddy != null ? telegramProxy.getDialog(currentBuddy) : null;

            if (!Objects.equals(targetPerson, currentBuddy) ||
                    updateChanges.getDialogsToReset().contains(currentDialog) ||
                    //updateChanges.getDialogsChanged().getChanged().containsKey(currentDialog) ||
                    updateChanges.getDialogsChanged().getDeleted().contains(currentDialog)) {
                updateMessages();
            } else if (updateChanges.getPersonsChanged().getChanged().containsKey(currentBuddy)
                    || updateChanges.getSmallPhotosChanged().contains(currentBuddy)
                    || updateChanges.getLargePhotosChanged().contains(currentBuddy)) {
                displayBuddy(targetPerson);
            }

            if (updateChanges.getPersonsChanged().getChanged().containsKey(telegramProxy.getMe())
                    || updateChanges.getSmallPhotosChanged().contains(telegramProxy.getMe())
                    || updateChanges.getLargePhotosChanged().contains(telegramProxy.getMe())) {
                displayMe(telegramProxy.getMe());
            }
        }
    }

    private void updateContacts() {
        messagesFrozen++;
        try {
            Person person = formUsersList.getSelectedValue();
            formUsersList.setTelegramProxy(telegramProxy);
            formUsersList.setSelectedValue(person);
        } finally {
            messagesFrozen--;
        }
    }

    private void updateMessages() {
        displayDialog(formUsersList.getSelectedValue());
        mainForm.revalidate();
        mainForm.repaint();
    }
    private void displayDialog(Person person) {
        try {
            MessagesForm messagesForm = getMessagesForm();
            messagesForm.display(person);
            displayBuddy(person);
            revalidate();
            repaint();
        } catch (Exception e) {
            showMessageError("Проблема соединения с сервером");
            abort(e);
        }
    }

    private MessagesForm getMessagesForm() {
        if (mainForm.getMessagesPanel() instanceof MessagesForm) {
            return (MessagesForm) mainForm.getMessagesPanel();
        } else {
            return createMessagesForm();
        }
    }
    private MessagesForm createMessagesForm() {
        MessagesForm messagesForm = new MessagesForm(telegramProxy);
        mainForm.setMessagesPanel(messagesForm);
        mainForm.revalidate();
        mainForm.repaint();
        return messagesForm;
    }
}

