import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;
import org.javagram.response.object.User;
import org.telegram.api.engine.RpcException;

import java.io.IOException;

public class Main {

    private static MyFrame frame;

    public static void main(String[] args) throws Exception {
        frame = new MyFrame();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}