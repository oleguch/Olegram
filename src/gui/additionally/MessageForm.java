package gui.additionally;

import additionally.Helper;

import javax.swing.*;
import java.awt.*;


public class MessageForm extends JPanel {
    private JEditorPane textPane = new JEditorPane();
    private JLabel dateLabel = new JLabel();
    private BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

    private Color color;

    private final int MARGIN = 5;
    private final int RADIUS = 10;

    public MessageForm(String text, String date, int width, Color color, String fontColor) {

        setLayout(boxLayout);

        textPane.setAlignmentX(0.05f);
        add(textPane);

        dateLabel.setAlignmentX(0.0f);
        add(dateLabel);

        textPane.setContentType("text/html");
        textPane.setSize(width, Short.MAX_VALUE);
        textPane.setText("<HTML><BODY><TABLE color=\"" + fontColor +"\" style=\'table-layout: fixed; font-family: \"Open Sans Light\", Times, sans-serif;\' width=\'" +
                width + "px\' max-width=\'" + width + "px><TR><TD style=\"word-wrap: break-word;\" width=\'" +
                width + "px\' max-width=\'" + width + "px\'>" + text.replaceAll("\n", "<br/>") + "</TD></TR></TABLE></BODY></HTML>");
        textPane.setOpaque(false);
        textPane.setEditable(false);
        textPane.setMargin(new Insets(MARGIN, MARGIN, MARGIN, MARGIN));

        Helper.clearBoth(textPane);
        dateLabel.setForeground(new Color(0x8d8d8f));
        dateLabel.setText(date);
        this.color = color;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(color);
        graphics.fillRoundRect(textPane.getX(), textPane.getY(), textPane.getWidth(), textPane.getHeight(), RADIUS, RADIUS);
    }
}
