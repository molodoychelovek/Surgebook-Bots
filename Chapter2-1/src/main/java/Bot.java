import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class Bot extends TelegramLongPollingBot {
    Book book = new Book();
    private long chat_id;

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();

        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        chat_id = update.getMessage().getChatId();
        sendMessage.setText(input(update.getMessage().getText()));

            try{
                execute(sendMessage);
            }catch (TelegramApiException e){
                e.printStackTrace();
            }
    }

    @Override
    public String getBotUsername() {
        return "@storebook_bot";
    }

    @Override
    public String getBotToken() {
        return "920362303:AAGRJr-maK8Xq8oVVGwkADvUNdXPDuIplFY";
    }

    public String input(String msg){
        if (msg.contains("Hi") || msg.contains("Hello") || msg.contains("Привет")) {
            return "Привет друг!";
        }
        if(msg.contains("Информация о книге")){
            return getInfoBook();
        }
        return msg;
    }

    public String getInfoBook(){
        try {
            URL url = new URL(book.getImage());
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
        } catch (Exception e){
            System.out.println("File not found");
            e.printStackTrace();
        }

        String info = book.getTitle()
                + "\nАвтор" + book.getAutorName()
                + "\nЖанр" + book.getGenres()
                + "\n\nОписание\n" + book.getDescription()
                + "\n\nКоличество лайков " + book.getLikes()
                + "\n\nПоследние комментарии\n" + book.getCommentList();

        return info;
    }
}
