import java.awt.*;
import java.io.InputStream;

public class Fonts {
    private static Font fontButton;
    private static Font fontButtonDialog;
    private static Font fontLabel;
    private static Font fontNumberLabel;
    private static Font fontForRegistrationField;
    private static Font nameFont;
    private static Font userNameFont;

    public static Font getFontButton() {
        if (fontButton == null)
            fontButton = new Font("Open Sans Light", Font.PLAIN, 25);
        return fontButton;
    }

    public static Font getFontButtonDialog() {
        if (fontButtonDialog == null)
            fontButtonDialog = new Font("Open Sans Light", Font.PLAIN, 18);
        return fontButtonDialog;
    }

    public static Font getFontLabel() {
        if (fontLabel == null)
            fontLabel = new Font("Open Sans Regular", Font.PLAIN, 16);
        return fontLabel;
    }

    public static Font getFontNumberLabel() {
        if (fontNumberLabel == null)
            fontNumberLabel = new Font("Open Sans Light", Font.PLAIN, 35);
        return fontNumberLabel;
    }

    public static Font getUserNameFont() {
        if (userNameFont == null)
            userNameFont = new Font("Open Sans SemiBold", Font.PLAIN, 16);
        return userNameFont;
    }


    public static Font getFontForRegistrationField() {
        if (fontForRegistrationField == null)
            fontForRegistrationField = new Font("Open Sans Light", Font.PLAIN, 40);
        return fontForRegistrationField;
    }



    public static void setFontToComponent(Component component, Font font, Color color) {
        component.setFont(font);
        component.setForeground(color);
    }

    public static Font getNameFont() {
        if (nameFont == null)
            nameFont = loadFont("OpenSansRegular.ttf");
            //nameFont = getFontLabel();
        return nameFont;
    }

    private static Font loadFont(String name) {
        try(InputStream inputStream = Fonts.class.getResourceAsStream("font/" + name)) {
            return Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("serif", Font.PLAIN, 24);
        }
    }
}
