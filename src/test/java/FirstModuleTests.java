import com.github.javafaker.Faker;
import com.codeborne.selenide.Configuration;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class FirstModuleTests {
    @Test
    public void siteHeaderNotEmpty(){
        // Настройки Selenide
        Configuration.browser = "chrome";
        Configuration.headless = false; // false - чтобы видеть браузер
        Configuration.timeout = 10000; // 10 секунд ожидания элементов
        Configuration.browserSize = "1920x1080"; // Размер окна браузера
        // Открываем russcarton.ru
        open("https://russcarton.ru/");
        try {
            var page_header = $(By.xpath("//h1")).getText();
            assertEquals(page_header, "Интернет-магазин упаковочных материалов");
        } catch (Exception e) {
            System.out.println("Page header does`n exist");
        }
        closeWebDriver();
    }

    @Test
    public void siteRegistratonForm(){
        Faker faker = new Faker();

        String fullName = faker.name().name();
        String email = faker.internet().emailAddress();
        String phone = faker.phoneNumber().phoneNumber();
        String pass = "123456";

        Configuration.browser = "chrome";
        Configuration.headless = false; // false - чтобы видеть браузер
        Configuration.timeout = 10000; // 10 секунд ожидания элементов
        Configuration.browserSize = "1920x1080"; // Размер окна браузера
        // Открываем russcarton.ru
        open("https://spb.russcarton.ru/personal/");

        String auth_modal_template_string = "//div[@class=\"oes-modal opened\"]//input[@name=\"%s\"]";

        $(By.xpath("//button[@class=\"oes-form__button\" and text()=\"ЗАРЕГИСТРИРОВАТЬСЯ\"]")).click();

        $(By.xpath(String.format(auth_modal_template_string,    "UF_USER_FULL_NAME" ))).setValue(fullName);
        $(By.xpath(String.format(auth_modal_template_string,    "REGISTER[EMAIL]"))).setValue(email);
        $(By.xpath(String.format(auth_modal_template_string,    "REGISTER[PERSONAL_PHONE]"))).setValue(phone);
        $(By.xpath(String.format(auth_modal_template_string,    "REGISTER[PASSWORD]"))).setValue(pass);
        $(By.xpath(String.format(auth_modal_template_string,    "REGISTER[CONFIRM_PASSWORD]"))).setValue(pass);
        $(By.xpath(String.format(auth_modal_template_string,    "register_submit_button"))).click();
        var reg_email = $(By.xpath("//span[text()=\"Email\"]/parent::*//span[2]")).getText();

        assertEquals(email, reg_email);

        closeWebDriver();

        System.out.println(String.format("cгенерирован email \t %s \nна странице email \t %s",email, reg_email));
    }

    @Test
    public void siteImageAltParameter() {
        Configuration.browser = "chrome";
        Configuration.headless = false; // false - чтобы видеть браузер
        Configuration.timeout = 10000; // 10 секунд ожидания элементов
        Configuration.browserSize = "1920x1080"; // Размер окна браузера
        // Открываем russcarton.ru
        open("https://spb.russcarton.ru/");

        var altValue = $(By.xpath("//div[@class=\"header__logo\"]//img")).getAttribute("alt");

        closeWebDriver();
        assertEquals("", altValue);
    }

    @Test
    public void siteToponimViewSelector(){
        Configuration.browser = "chrome";
        Configuration.headless = false; // false - чтобы видеть браузер
        Configuration.timeout = 10000; // 10 секунд ожидания элементов
        Configuration.browserSize = "1920x1080"; // Размер окна браузера
        // Открываем russcarton.ru
        open("https://russcarton.ru/");

        $(By.xpath("//button[@class=\"headerToponim parent-element arrow_city\"]")).click();
        $(By.xpath("//div[@class=\"floating-block\"]")).shouldBe(visible);
        var visibleParameter =  $(By.xpath("//div[@class=\"floating-block\"]")).getAttribute("style");
        assertEquals(visibleParameter, "display: block;");
        closeWebDriver();
    }
}