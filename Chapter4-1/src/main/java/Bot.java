import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Bot extends TelegramLongPollingBot {
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());

        try {
            String input = update.getMessage().getText();
            String result = String.valueOf(getMsg(input));

            sendMessage.setText("Вы ввели: " + input + "\nРезультат: " + result);
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private float getMsg(String text) throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        return Float.valueOf(engine.eval(text).toString());
    }

    public String getBotUsername() {
        return null;
    }

    public String getBotToken() {
        return null;
    }
}
