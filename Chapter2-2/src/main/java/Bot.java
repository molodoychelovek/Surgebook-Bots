import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class Bot extends TelegramLongPollingBot {
    public void onUpdateReceived(Update update) {
        String text = update.getMessage().getText();

        if(text.contains("person")){
            text = text.replace("/person ", "");
            getPerson(text, update.getMessage().getChatId());
        }
    }

    private void getPerson(String name, long chat_id){
        try {
            Author author = new Author(name);

            //Отправляем изображение
            URL url = new URL(author.getImg());
            // берем сслыку на изображение
            BufferedImage img = ImageIO.read(url);
            // качаем изображение в буфер
            File outputfile = new File("image.jpg");
            //создаем новый файл в который поместим
            //скаченое изображение
            ImageIO.write(img, "jpg", outputfile);
            //преобразовуем наше буферное изображение
            //в новый файл
            SendPhoto sendPhoto = new SendPhoto().setChatId(chat_id);
            sendPhoto.setPhoto(outputfile);
            execute(sendPhoto);

            //Отправляем информацию о пользователе
            SendMessage sendMessage = new SendMessage().setChatId(chat_id);
            sendMessage.setText(author.getInfoPerson());
            execute(sendMessage);
        } catch (Exception e) {
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
