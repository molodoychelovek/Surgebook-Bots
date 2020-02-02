import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        update.getUpdateId();

        String chat_id = String.valueOf(update.getMessage().getChatId());
        SendMessage sendMessage = new SendMessage().setChatId(chat_id);

        if(update.getMessage().getText().equals("Привет")){
            sendMessage.setText("Привет дружище");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    public String getBotUsername() {
        return null;
    }

    public String getBotToken() {
        return null;
    }
}
