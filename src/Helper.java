import org.javagram.dao.Person;
import org.javagram.dao.proxy.TelegramProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by oleg on 09.07.17.
 */
public class Helper {
    public static JButton createDecoratedButton(int buttonType) {
        JButton button = new JButton(getTextButton(buttonType));
        Dimension size = new Dimension(80,30);
        button.setMinimumSize(size);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setSize(size);
        Images.decorateAsImageButton(button, Images.getButtonImage(), Images.getButtonImagePressed(), Color.WHITE);
        Fonts.setFontToComponent(button, Fonts.getFontButtonDialog(), Color.WHITE);
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

    private static String getTextButton(int buttonType) {
        switch (buttonType) {
            case JOptionPane.DEFAULT_OPTION:
                return "OK";
            case JOptionPane.CANCEL_OPTION:
                return "ОТМЕНА";
            case JOptionPane.YES_OPTION:
                return "ДА";
            case JOptionPane.NO_OPTION:
                return "НЕТ";
            default:
                return null;
        }
    }

    public static void clearBorder(JComponent component) {
        component.setBorder(BorderFactory.createEmptyBorder());
    }

    public static void clearBoth(JComponent textPane) {
        clearBackground(textPane);
        clearBorder(textPane);
    }
    public static void clearBackground(JComponent component) {
        component.setOpaque(false);
        component.setBackground(new Color(0, 0, 0, 0));//Для Nimbus

    }

    public static BufferedImage getPhoto(TelegramProxy telegramProxy, Person person, boolean small) {
        BufferedImage image;

        try {
            image = telegramProxy.getPhoto(person, small);
        } catch (Exception e) {
            e.printStackTrace();
            image = null;
        }

        if(image == null)
            image = Images.getUserImage(small);
        return image;
    }


    public static BufferedImage getPhoto(TelegramProxy telegramProxy, Person person, boolean small, boolean circle) {
        BufferedImage photo = getPhoto(telegramProxy, person, small);
        if(circle)
            photo = GuiHelper.makeCircle(photo);
        return photo;
    }
}
