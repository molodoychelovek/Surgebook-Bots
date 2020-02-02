import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {
    public static void main(String args[]) throws TelegramApiRequestException {
        ApiContextInitializer.init();
        TelegramBotsApi telegram = new TelegramBotsApi();
        telegram.registerBot(new Bot());
    }
}
