import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Book {
    //документ, в котором будет храниться страница
    private Document document;
    private String url;

    public Book(String url) {
        this.url = url;
        connect();
    }

    //подключаемся к странице
    private void connect(){
        try{
            document = Jsoup.connect(url).get();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //получаем название обложки
    public String getTitle(){
        return document.title();
    }

    //смотрим сколько лайков
    public String getLikes(){
        Element element = document.getElementById("likes");
        return element.text();
    }

    //читаем описание
    public String getDescription(){
        Element element = document.getElementById("description");
        return element.text();
    }

    //смотрим жанр
    public String getGenres(){
        Element element = document.getElementsByClass("genres d-block").get(0);
        return element.text();
    }

    //последние комментарии
    public String getCommentList(){
        Elements elements = document.getElementsByClass("comment_mv1_item");

        String comment = elements.text();
        //чистим от ответить
        comment = comment.replaceAll("Ответить", "\n\n");
        //чистим от нравится
        comment = comment.replaceAll("Нравится", "");
        //чистим от дат
        comment = comment.replaceAll("\\d{4}-\\d{2}-\\d{2}", "");
        //чистим от времени
        comment = comment.replaceAll("\\d{4}-\\d{2}-\\d{2}", "");
        return comment;
    }

    //берем обложку книги
    public String getImage(){
        Elements elements = document.getElementsByClass("cover-book");
        String url = elements.attr("style");
        //чистим url от лишнего
        url = url.replace("background-image: url('", "");
        url = url.replace("');", "");
        return url;
    }

    //имя автора
    public String getAutorName(){
        Elements elements = document.getElementsByClass("text-decoration-none column-author-name bold max-w-140 text-overflow-ellipsis");
        return elements.text();
    }
}