import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.ArrayList;

public class WebSurfing {

    public WebSurfing(){
        System.setProperty("webdriver.gecko.driver", "/geckodriver.exe");
    }
    /*
         Если вы запускаете на Linux или у вас сервер на Linux
         тогда нужно скачать geckodriver-v0.26.0-linux64.tar.gz,
         или другую любую версию. К примеру если вы закинете файл
         в /usr/bin тогда setProperty будет таким:
         System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");

          Перед запуском следует установить geckodriver подробно
          изложенно тут
          https://askubuntu.com/questions/870530/how-to-install-geckodriver-in-ubuntu

          Возможно после запуска драйвера у вас возникнет конфликт на сервере
          скорее всего из-за того что браузер не удаеться открыть визуально
          Если в этом проблема тогда нам следует работать в скрытом режиме

          Следует прописать после каждого вызова браузера
          FirefoxOptions options = new FirefoxOptions();
          options.addArguments("--headless");
          WebDriver driver = new FirefoxDriver(options);
     */


    public String signIn(String chat_id, String login, String password){
        WebDriver driver = new FirefoxDriver();
        // Можно использовать и другие браузеры
        try {
            driver.get("https://www.surgebook.com/login"); //заходим на страницу авторизации
            driver.findElement(By.id("email")).sendKeys(login); // ищем в браузере код элемента. В нашем случае
                                                                // элименты TextBoxEmail и Password содержат id
                                                                // и это намного упрощает поиск.
                                                                // SendKeys - вставить текст в текст. поле
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.xpath("//button[@class='btn btn-blue mt-40']")).click(); // ищем через class конпку
                                                                                            // входа. Нажимаем

            if(driver.getCurrentUrl().contains("login")) { // Если не переходим на новую страницу
                return "Не верный логин или пароль!";
            }

            UsersData ud = new UsersData(); // Создаем БД
            ud.addUser(chat_id, login, password); // Если логин и пораль подходят тогда сохраняем в бд,
                                                    // как и ид чата, который будет индефицировать полдбзователей
            return "Вы успешно авторизованы! \nЧто-бы опубликовать пост пропишите /post [text]";
        } catch (Exception e){
            e.printStackTrace();
            return "Ошибка авторизации";
        } finally {
            driver.quit();
            // Объязательно закрываем сессию браузера, иначе потом придется
            // делать это вручную
        }
    }

    public String postBlog(String chat_id, String text){
        WebDriver driver = new FirefoxDriver();
        try {

            UsersData ud = new UsersData();
            ArrayList<String> user = ud.getUser(chat_id); // Берем данные пользователя

            driver.get("https://www.surgebook.com/login"); //логинимся
            driver.findElement(By.id("email")).sendKeys(user.get(0)); //get(0) - email
            driver.findElement(By.id("password")).sendKeys(user.get(1)); // get(1) - pass
            driver.findElement(By.xpath("//button[@class='btn btn-blue mt-40']")).click();
            driver.findElement(By.id("btn-dropdown-create-menu")).click(); // Кнопка "Написать"
            driver.findElement(By.xpath("//a[@class='nav_top_add_new_content_blog_tile nav_top_add_new_content_tile']")).click();
            //Кнопка "Блоги"
            driver.findElement(By.id("outBlogText")).sendKeys(text); // Передаем текст в текстовое поле
            driver.findElement(By.id("btn_form_publish")).click(); // кнопка "Продолжить"
            driver.findElement(By.id("btnOpenGenresDropdown")).click(); // кнопка "Выбрать тему"
            driver.findElement(By.xpath("//ul[contains(@id, 'genresUlDropdown')]/li[2]")).click(); // Выбираем 2 элемент списка
            driver.findElement(By.id("btn_send_publish")).click(); // Кнопка "Опубликовать"

            return "Пост успешно опубликован!";
        } catch (Exception e){
            e.printStackTrace();
            return "Ошибка  публикации";
        }
        finally {
            driver.quit();
        }
    }

}
