import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Created by oleg on 10.06.17.
 */ //фильтр кода смс
public class CodeDocumentFilter extends DocumentFilter {
    int amountNumberOfCode = 5;                                                     //количество цифр в коде

    public void insertString(FilterBypass fb, int offset,            //действия при вставке
                             String text, AttributeSet attr) throws BadLocationException {
        text = text.replaceAll("\\D", "");                          //оставляем только цифры
        int lengthCode = fb.getDocument().getLength();
        if (lengthCode >= amountNumberOfCode)
            text = "";                                                              //в случае если уже макс цифр в коде, то убираем строку
        else if (lengthCode + text.length() >= amountNumberOfCode)                  //иначе смотрим, не станет ли больше при вставке
            text = text.substring(0, amountNumberOfCode - lengthCode);                //если станет, то обрезаем вставляемую строку
        fb.insertString(offset, text, attr);                                        //вставляем строку
    }

    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        fb.remove(offset, length);                                                  //удаляем
    }

    public void replace(FilterBypass fb, int offset, int length,     //действия при замене символов
                        String text, AttributeSet attr) throws BadLocationException {
        int lengthCode = fb.getDocument().getLength();
        if (text.isEmpty())
            remove(fb, 0, lengthCode);                                          //если вставляем пустую строку, то удаляем всю (например при setValue(""))
        else {
            text = text.replaceAll("\\D", "");
            if (text.length() + lengthCode - length >= amountNumberOfCode)            //смотрим, не станет ли больше макс при замене и вставке новой строки
                text = text.substring(0, amountNumberOfCode - lengthCode + length);
            fb.replace(offset, length, text, attr);
        }
    }
}
