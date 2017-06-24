import com.sun.org.apache.regexp.internal.RE;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel{

    private Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Rectangle rect = Images.getAreaToFill(getSize(), new Dimension(image.getWidth(null), image.getHeight(null)));
        graphics.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
    }

    @Override
    protected void paintBorder(Graphics graphics) {
        super.paintBorder(graphics);
    }
}
