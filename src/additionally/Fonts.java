package additionally;

import java.awt.*;

public class Fonts {
    private static Font fontButton;
    private static Font fontButtonDialog;
    private static Font fontLabel;
    private static Font fontNumberLabel;
    private static Font fontForRegistrationField;
    private static Font fontUserNameList;
    private static Font fontLastMessage;
    private static Font userNameTitleFont;
    private static Font buddyTitleFont;
    private static Font textFieldFontOverlay;
    private static Font overlayTitleFont;
    private static Font logoutOverlayFont;

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

    public static Font getFontLastMessage() {
        if (fontLastMessage == null)
            fontLastMessage = new Font("Open Sans Regular", Font.PLAIN, 15);
        return fontLastMessage;
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

    public static Font getFontUserNameList() {
        if (fontUserNameList == null)
            fontUserNameList = new Font("Open Sans SemiBold", Font.PLAIN, 18);
        return fontUserNameList;
    }

    public static Font getUserNameTitleFont() {
        if (userNameTitleFont == null)
            userNameTitleFont = new Font("Open Sans Regular", Font.PLAIN, 18);
        return userNameTitleFont;
    }

    public static Font getBuddyTitleFont() {
        if (buddyTitleFont == null)
            buddyTitleFont = new Font("Open Sans Regular", Font.PLAIN, 18);
        return buddyTitleFont;
    }

    public static Font getTextFieldFontOverlay() {
        if (textFieldFontOverlay == null)
            textFieldFontOverlay = new Font("Open Sans Regular", Font.PLAIN, 30);
        return textFieldFontOverlay;
    }

    public static Font getOverlayTitleFont() {
        if (overlayTitleFont == null)
            overlayTitleFont = new Font("Open Sans Light", Font.PLAIN, 45);
        return overlayTitleFont;
    }

    public static Font getLogoutOverlayFont() {
        if (logoutOverlayFont== null)
            logoutOverlayFont = new Font("Open Sans Regular", Font.PLAIN, 20);
        return logoutOverlayFont;
    }



    public static void setFontToComponent(Component component, Font font, Color color) {
        component.setFont(font);
        component.setForeground(color);
    }


}
