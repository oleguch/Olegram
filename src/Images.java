import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Images {
    private Images() {
        System.out.println("гав");
    }

    private static BufferedImage buttonImage;
    private static BufferedImage buttonImagePressed;
    private static BufferedImage buttonClose;
    private static BufferedImage buttonClosePressed;
    private static BufferedImage buttonMinimize;
    private static BufferedImage buttonMinimizePressed;
    private static BufferedImage background;
    private static BufferedImage logo;
    private static BufferedImage iconPhone;
    private static BufferedImage iconLock;
    private static BufferedImage logoMini;

    public static BufferedImage getIconLock() {
        if (iconLock == null)
            iconLock = getImage("img/icon-lock.png");
        return iconLock;
    }
    public static BufferedImage getLogoMini() {
        if (logoMini == null)
            logoMini = getImage("img/logo-mini.png");
        return logoMini;
    }

    public static BufferedImage getIconPhone() {
        if (iconPhone == null)
            iconPhone = getImage("img/icon-phone.png");
        return iconPhone;
    }

    public static BufferedImage getLogo() {
        if (logo == null)
            logo = getImage("img/logo.png");
        return logo;
    }

    public static BufferedImage getBackground() {
        if (background == null)
            background = getImage("img/background.png");
        return background;
    }

    public static BufferedImage getButtonImage() {
        if (buttonImage == null)
            buttonImage = getImage("img/button-background.png");
        return buttonImage;
    }

    public static BufferedImage getButtonImagePressed() {
        if (buttonImagePressed == null)
            buttonImagePressed = getImage("img/button-background-press.png");
        return buttonImagePressed;
    }

    public static BufferedImage getButtonClose() {
        if (buttonClose== null)
            buttonClose = getImage("img/icon-close.png");
        return buttonClose;
    }

    public static BufferedImage getButtonClosePressed() {
        if (buttonClosePressed== null)
            buttonClosePressed = getImage("img/icon-close-press.png");
        return buttonClosePressed;
    }

    public static BufferedImage getButtonMinimize() {
        if (buttonMinimize == null)
            buttonMinimize = getImage("img/icon-hide.png");
        return buttonMinimize;
    }

    public static BufferedImage getButtonMinimizePressed() {
        if (buttonMinimizePressed == null)
            buttonMinimizePressed = getImage("img/icon-hide-press.png");
        return buttonMinimizePressed;
    }

    private static BufferedImage getImage(String path) {
        try {
            return ImageIO.read(Images.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            return new BufferedImage(1,1,BufferedImage.TYPE_4BYTE_ABGR);            //заглушка
        }
    }

    public static void decorateAsImageButton(JButton button, Image image, Image imagePress, Color foreground) {
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorder(null);
        button.setBorderPainted(false);
        if (foreground != null)
            button.setForeground(foreground);
        else
            button.setText("");
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        Dimension size = button.getPreferredSize();
        button.setIcon(new ImageIcon(scaleImage(image, size.width, size.height)));
        button.setPressedIcon(imagePress == null ? null : new ImageIcon(scaleImage(imagePress, size.width, size.height)));
    }

    private static BufferedImage scaleImage(Image image, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        Graphics2D g2d = bufferedImage.createGraphics();
        try {
            g2d.drawImage(image, 0,0, width, height, null);
        } finally {
            g2d.dispose();
        }
        return bufferedImage;
    }

    public static Rectangle getAreaToFill(Dimension area, Dimension image, boolean notCrop){
        double scaleX = area.getWidth() / image.getWidth();
        double scaleY = area.getHeight() / image.getHeight();
        double scale = notCrop ? Math.max(scaleX, scaleY) : Math.min(scaleX, scaleY);
        int width = (int) Math.round(image.getWidth() * scale);
        int height = (int) Math.round(image.getHeight() * scale);
        int x = (area.width - width) / 2;
        int y = (area.height - height) / 2;
        return new Rectangle(x,y,width,height);
    }
}
