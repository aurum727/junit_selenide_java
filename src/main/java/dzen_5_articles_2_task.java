import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class dzen_5_articles_2_task {

    public static void main(String[] args) {
        Configuration.browser = "chrome";
        Configuration.headless = false; // false - чтобы видеть браузер
        open("https://www.dzen.ru");

        ElementsCollection news = $$x("//li[@data-testid=\"news-item\"]//p[@aria-label=\"Заголовок\"]");
        int count = news.size(); // extract to evaluate once only
        for (int i=0; i<count; i++) {
            try {
                news.get(i).shouldBe(visible);
                System.out.println(news.get(i).getText());
            } catch (Throwable e) {
                //TODO
            }
        }
        closeWebDriver();
    }

}
