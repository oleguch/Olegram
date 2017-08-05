import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.net.URL;

public class GuiHelper {
    public static Rectangle drawImage(Graphics g, Image image, int x, int y, int width, int height) {
        Rectangle rect = getAreaFor(new Rectangle(x, y, width, height), new Dimension(image.getWidth(null), image.getHeight(null)));
        g.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
        return rect;
    }

    public static int drawImage(Graphics g, BufferedImage image, int x, int y, int width, int height, int inset, boolean right) {
        int photoHeight = height - inset * 2;
        int photoWidth = width - inset * 2;

        x += inset;
        y += inset;

        Rectangle area = getAreaFor(new Rectangle(x, y, photoWidth, photoHeight), new Dimension(image.getWidth(), image.getHeight()));

        if(right) {
            x += width - area.width;
        }

        g.drawImage(image, x, area.y, area.width, area.height, null);

        if(right)
            return x - inset;
        else
            return x + area.width + inset;

    }

    public static Rectangle getAreaFor(Rectangle area, Dimension size) {
        int x = area.x;
        int y = area.y;
        double dx = area.width / size.getWidth();
        double dy = area.height / size.getHeight();
        double d = Math.min(dy, dx);
        int newWidth = (int)Math.round(d * size.getWidth());
        int newHeight = (int)Math.round(d * size.getHeight());
        x += (area.width - newWidth) / 2;
        y += (area.height - newHeight) / 2;
        return new Rectangle(x, y, newWidth, newHeight);
    }
    public static BufferedImage makeCircle(Image image) {
        BufferedImage circle = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = circle.createGraphics();
        try {
            g2d.clip(new Ellipse2D.Double(0, 0, circle.getWidth(), circle.getHeight()));
            g2d.drawImage(image, 0, 0, null);
        } finally {
            g2d.dispose();
        }
        return circle;
    }
    public static BufferedImage loadImage(String name, Class root) {
        try {
            return ImageIO.read(root != null ? root.getResource(name) : new URL(name));
        } catch (Exception e) {
            e.printStackTrace();
            return new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        }
    }
}
