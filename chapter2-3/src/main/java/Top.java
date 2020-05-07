import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Top {
    Document document = null;

    public String[] getTopBooks(String period) throws IOException {
        if(period.equals("За все время")) {
            document = Jsoup.connect("https://www.surgebook.com/books/popular?order=popular").get();
        } else if(period.equals("За месяц")){
            document = Jsoup.connect("https://www.surgebook.com/books/popular?when=this_month&order=popular").get();
        } else if(period.equals("За неделю")){
            document = Jsoup.connect("https://www.surgebook.com/books/popular?when=this_week&order=popular").get();
        } else if(period.equals("Сегодня")){
            document = Jsoup.connect("https://www.surgebook.com/books/popular?when=today&order=popular").get();
        }

        Elements nameB = document.getElementsByClass("book_view_mv1v2_title");

        ArrayList<String> books = new ArrayList<String>();
        for(int i = 0; i < nameB.size(); i++){
            if(i < 10){
                books.add(nameB.get(i).attr("href"));
            }
        }



        String[] strBook = new String[books.size()];
        for(int i = 0; i < books.size(); i++){
            strBook[i] = books.get(i);
        }

        return strBook;
    }

    public String[] getTopPoems(String period) throws IOException {
        if (period.equals("За все время")) {
            document = Jsoup.connect("https://www.surgebook.com/poems/popular?order=popular").get();
        } else if (period.equals("За месяц")) {
            document = Jsoup.connect("https://www.surgebook.com/poems/popular?when=this_month&order=popular").get();
        } else if (period.equals("За неделю")) {
            document = Jsoup.connect("https://www.surgebook.com/poems/popular?when=this_week&order=popular").get();
        } else if (period.equals("Сегодня")) {
            document = Jsoup.connect("https://www.surgebook.com/poems/popular?when=today&order=popular").get();
        }

        Elements name = document.getElementsByClass("poem_mv1v2_title");
        Elements autor = document.getElementsByClass("poem_mv1v2_author");
        Elements poem = document.getElementsByClass("poem_mv1v2_text");
        Elements like = document.getElementsByClass("poem_mv1v2_status_list_item poem_mv1v2_status_list_item_like");
        Elements coment = document.getElementsByClass("poem_mv1v2_status_list_item poem_mv1v2_status_list_item_comment");
        Elements view = document.getElementsByClass("poem_mv1v2_status_list_item poem_mv1v2_status_list_item_view");

        String[] info = new String[20];

        for(int i = 0; i < name.size(); i++){
            if(i < 20){
                info[i] = "\n\n\n" + name.get(i).text() + "\n" +
                        "Автор: " + autor.get(i).text() + "\n\n\n" +
                        poem.get(i).text() + "\n\n" +
                        "Лайков " + like.get(i).text() + "\n" +
                        "Комментов " + coment.get(i).text() + "\n" +
                        "Просмотров " + view.get(i).text();
            }
        }
        return info;
    }
}
