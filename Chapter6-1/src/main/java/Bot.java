import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        WebSurfing webSurfing = new WebSurfing();

        String chat_id = String.valueOf(update.getMessage().getChatId());
        String text = update.getMessage().getText();

        SendMessage msg = new SendMessage().setChatId(chat_id);
        String answer = "";
        try {
            if(text.contains("/login ")){
                String[] user = text.split(" ");
                answer = webSurfing.signIn(chat_id, user[1], user[2]);
            } else if(text.contains("/post ")){
                text = text.replace("/post ", "");
                answer = webSurfing.postBlog(chat_id, text);
            } else if(text.equals("/start")){
                answer = "Для начала вам нужно авторизоватся!\n /login [email] [password]";
            } else
                answer = "Если возникли проблемы, пропишите /start";
            execute(msg.setText(answer));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return null;
    }

    public String getBotToken() {
        return null;
    }
}
