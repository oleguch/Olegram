package gui.additionally;

import additionally.Images;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel{

    private Image image;
    private boolean bool;
    private Insets insets;
    private boolean keepRatio;

    public ImagePanel(Image image, boolean bool) {
        this.image = image;
        this.bool = bool;
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


public ImagePanel(Image image, boolean opaque, boolean keepRatio, Insets insets) {
    this.image = image;
    setOpaque(opaque);
    this.keepRatio = keepRatio;
    this.insets = insets;
}

    public ImagePanel(Image image, boolean opaque, boolean keepRatio, int inset) {
        this(image, opaque, keepRatio, new Insets(inset, inset, inset, inset));
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        if(image != this.image) {
            this.image = image;
            repaint();
        }
    }

    public boolean isKeepRatio() {
        return keepRatio;
    }

    public void setKeepRatio(boolean keepRatio) {
        if(keepRatio !=this.keepRatio) {
            this.keepRatio = keepRatio;
            repaint();
        }
    }

//    @Override
//    public Insets getInsets() {
//        return insets;
//    }

    public void setInsets(Insets insets) {
        if(insets != this.insets) {
            this.insets = insets;
            repaint();
        }
    }

}
