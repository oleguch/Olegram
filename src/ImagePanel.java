import com.sun.org.apache.regexp.internal.RE;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel{

    private Image image;
    private boolean bool;
    private boolean keepRatio;
    private Insets insets;


    public ImagePanel(Image image, boolean opaque, boolean keepRatio, Insets insets) {
        this.image = image;
        setOpaque(opaque);
        this.keepRatio = keepRatio;
        this.insets = insets;
    }

    public ImagePanel(Image image, boolean bool) {
        this.image = image;
        this.bool = bool;
    }

    public ImagePanel(Image image, boolean opaque, boolean keepRatio, int inset) {
        this(image, opaque, keepRatio, new Insets(inset, inset, inset, inset));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Rectangle rect = Images.getAreaToFill(getSize(), new Dimension(image.getWidth(null), image.getHeight(null)), bool);
        graphics.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
    }

    @Override
    protected void paintBorder(Graphics graphics) {
        super.paintBorder(graphics);
    }

    public void setImage(Image image) {
        if(image != this.image) {
            this.image = image;
            repaint();
        }
    }
}
