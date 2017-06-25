import java.awt.*;

public class Fonts {
    private static Font fontButton;
    private static Font fontLabel;
    private static Font fontNumberLabel;
    private static Font fontForRegistrationField;

    public static Font getFontButton() {
        if (fontButton == null)
            fontButton = new Font("Open Sans Light", Font.PLAIN, 25);
        return fontButton;
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

    public static Font getFontForRegistrationField() {
        if (fontForRegistrationField == null)
            fontForRegistrationField = new Font("Open Sans Light", Font.PLAIN, 40);
        return fontForRegistrationField;
    }

    public static void setFontToComponent(Component component, Font font, Color color) {
        component.setFont(font);
        component.setForeground(color);
    }
}
