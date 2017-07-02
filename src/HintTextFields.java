import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by oleg on 29.06.2017.
 */
public class HintTextFields extends JTextField {
    private String hintString;

    public HintTextFields(String hintString) {
        this.hintString = hintString;
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                hintTextFieldFocusGained();
            }

            @Override
            public void focusLost(FocusEvent e) {
                hintTextFieldLostFocus();
            }
        });
    }

    private void hintTextFieldFocusGained() {
        this.setForeground(Color.WHITE);
        if (this.getText().equals(hintString))
            this.setText("");
    }
    private void hintTextFieldLostFocus() {
        if (this.getText().isEmpty()) {
            this.setForeground(Color.LIGHT_GRAY);
            this.setText(hintString);
        }
    }


}
