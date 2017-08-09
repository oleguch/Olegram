import org.javagram.dao.ApiBridgeTelegramDAO;
import org.javagram.dao.DebugTelegramDAO;
import org.javagram.dao.TelegramDAO;

public class Main {

    private static MyFrame frame;


    public static void main(String[] args) throws Exception {
        //TelegramDAO telegramDAO = new ApiBridgeTelegramDAO(Config.SERVER, Config.APP_ID, Config.APP_HASH);
        TelegramDAO telegramDAO = new DebugTelegramDAO();
        frame = new MyFrame(telegramDAO);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}