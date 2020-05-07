import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    long chat_id;
    String lastMessage = "";

    public void onUpdateReceived(Update update) {
        chat_id = update.getMessage().getChatId();

        SendMessage sendMessage = new SendMessage()
                .setChatId(chat_id)
                .setText(getMessage(update.getMessage().getText()));
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getMessage(String msg){
        ArrayList<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        if(msg.equals("Привет") || msg.equals("Меню") || msg.equals("/start")){
            keyboard.clear();
            keyboardFirstRow.add("Популярное");
            keyboardFirstRow.add("Новости");
            keyboardSecondRow.add("Полезная информация");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Выберите пункт меню";
        }

        if(msg.equals("Полезная информация")){
            keyboard.clear();
            keyboardFirstRow.add("Информация о книге");
            keyboardFirstRow.add("/person bebosehun_");
            keyboardFirstRow.add("Меню");
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Выберите пункт меню";
        }

        if(msg.equals("Популярное")){
            keyboard.clear();
            keyboardFirstRow.add("Стихи");
            keyboardFirstRow.add("Книги");
            keyboardFirstRow.add("Меню");
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Выберите пункт меню";
        }
        if(msg.equals("Стихи") || msg.equals("Книги")){
            lastMessage = msg;
            keyboard.clear();
            keyboardFirstRow.add("Сегодня");
            keyboardFirstRow.add("За неделю");
            keyboardFirstRow.add("За месяц");
            keyboardFirstRow.add("За все время");
            keyboardFirstRow.add("Меню");
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Выберите пункт меню";
        }
        if(msg.equals("Новости")){
            return "Этот пункт меню не рабочий";
        }


        if(lastMessage.equals("Стихи")){
            if(msg.equals("За все время") || msg.equals("За месяц") || msg.equals("За неделю") || msg.equals("Сегодня")){
                try {
                    return getTopPoems(new Top().getTopPoems(msg));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(lastMessage.equals("Книги")){
            if(msg.equals("За все время") || msg.equals("За месяц") || msg.equals("За неделю") || msg.equals("Сегодня")){
                try {
                    return getInfoBook(new Top().getTopBooks(msg));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "Если возникли проблемы, воспользуйтесь /start";
    }


    public String getTopPoems(String[] text){
        SendMessage sendMessage = new SendMessage().setChatId(chat_id);
        for(int i = 0; i < text.length; i++){
            try {

                if((i + 1) == text.length){
                    return text[i];
                }

                sendMessage.setText(text[i]);
                execute(sendMessage);

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return "Что-то отвечаем :)";
    }

    public String getInfoBook(String[] url){
        for(int i = 0; i < url.length; i++){
            Book book = new Book(url[i]);
            try {
                URL imgURL = new URL(book.getImage());
                //берем сслыку на изображение
                BufferedImage img = ImageIO.read(imgURL);
                //качаем изображение в буфер
                File outputfile = new File("image.jpg");
                //создаем новый файл в который поместим
                //скаченое изображение
                ImageIO.write(img, "jpg", outputfile);
                //преобразовуем наше буферное изображение
                //в новый файл
                SendPhoto sendPhoto = new SendPhoto()
                        .setChatId(chat_id);
                sendPhoto.setPhoto(outputfile);
                execute(sendPhoto);

                String info = book.getTitle() +
                        "\nАвтор " + book.getAutorName() +
                        "\nЖанр " + book.getGenres() +
                        "\n\nОписание \n" + book.getDescription() +
                        "\n\nКоличество лайков " + book.getLikes() +
                        "\n\nПоследние комментарии " + book.getCommentList();

                SendMessage sendMessage = new SendMessage().setChatId(chat_id).setText(info);
                execute(sendMessage);

                if((i + 1) == url.length){
                    return info;
                }
            } catch (Exception e){
                System.out.println("File not found");
                e.printStackTrace();
            }
        }

        return "Что-то отвечаем :)";
    }

    public String getBotUsername() {
        return "";
    }

    public String getBotToken() {
        return "";
    }
}
