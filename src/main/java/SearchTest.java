import com.codeborne.selenide.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import static com.codeborne.selenide.Selenide.*;

public class SearchTest {

    public static void main(String[] args) {
        // Настройки Selenide
        Configuration.browser = "chrome";
        Configuration.headless = false; // false - чтобы видеть браузер
        // Открываем ya.ru
        open("https://www.ya.ru");
        // Закрываем всплывающее окно с куками, если оно появится
        try {
            $(By.xpath("//input[@class=\"search3__input mini-suggest__input\"]")).click();
        } catch (Exception e) {
            System.out.println("Cookie popup not found or already closed");
        }
        // Вводим текст в поисковую строку и выполняем поиск
        $(By.xpath("//input[@class=\"search3__input mini-suggest__input\"]"))
                .setValue("Selenide")
                .pressEnter();

        try{
            $(By.xpath
                    ("//button[@class=\"Button Distribution-Button Distribution-ButtonClose " +
                            "Distribution-ButtonClose_view_button Button_view_default Button_size_m\"]")).click();
        } catch (NoSuchElementException e) {
            System.out.println("Cookie popup not found or already closed");
        }


        // Ждем загрузки результатов (лучше использовать явные ожидания в реальных тестах)
//        sleep(3000);
        // Закрываем браузер
        closeWebDriver();
    }
}