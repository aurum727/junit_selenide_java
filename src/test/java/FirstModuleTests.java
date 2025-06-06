import com.github.javafaker.Faker;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class FirstModuleTests {

    @BeforeEach
    public void init(){
//        Настраиваем Selenide
        Configuration.browser = "chrome";
        Configuration.headless = false; // false - чтобы видеть браузер
        Configuration.browserSize = "1920x1080"; // Размер окна браузера
        open("https://russcarton.ru/");
    }

    @AfterEach
    public void closeDriver() {
//      закрываем вэб-драйвер
        closeWebDriver();
    }

    @Test
    public void siteHeaderNotEmpty_1(){
        var page_header = $x("//h1").getText();
        assertEquals(page_header, "Интернет-магазин упаковочных материалов");
    }

    @Test
    public void siteRegistratonForm_2(){
        //get fake data for registration to site
        Faker faker = new Faker();
        String fullName = faker.name().name();
        String email = faker.internet().emailAddress();
        String phone = faker.phoneNumber().phoneNumber();
        String pass = "123456";

        String auth_modal_template_locator_string = "//div[@class=\"oes-modal opened\"]//input[@name=\"%s\"]";
        // click through to the registration page
        $x("//div[@id=\"header__top\"]" +
                "//a[@href=\"/personal/\" and not (contains(text(),\"Профиль\"))]").click();
        // open new user registration form
        $x("//button[@class=\"oes-form__button\" and text()=\"ЗАРЕГИСТРИРОВАТЬСЯ\"]").click();
        // filling in the data on the form
        $x(String.format(auth_modal_template_locator_string, "UF_USER_FULL_NAME" )).setValue(fullName);
        $x(String.format(auth_modal_template_locator_string, "REGISTER[EMAIL]")).setValue(email);
        $x(String.format(auth_modal_template_locator_string, "REGISTER[PERSONAL_PHONE]")).setValue(phone);
        $x(String.format(auth_modal_template_locator_string, "REGISTER[PASSWORD]")).setValue(pass);
        $x(String.format(auth_modal_template_locator_string, "REGISTER[CONFIRM_PASSWORD]")).setValue(pass);
        // submit registration
        $x(String.format(auth_modal_template_locator_string,    "register_submit_button")).click();
        // get email data on profile page
        var reg_email = $x("//span[text()=\"Email\"]/parent::*//span[2]").getText();
        // checking the correctness of the email
        assertEquals(email, reg_email);
        System.out.println(String.format("cгенерирован для регистрации email \t %s \n" +
                "на странице профиля email \t %s",email, reg_email));
    }

    @Test
    public void siteImageAltParameter_3() {
        var altValue = $x("//div[@class=\"header__logo\"]//img").getAttribute("alt");
        assertEquals("", altValue);
    }

    @Test
    public void siteToponimViewSelector_4(){
        $x("//button[@class=\"headerToponim parent-element arrow_city\"]").click();
        $x("//div[@class=\"floating-block\"]").shouldBe(visible);
        var visibleParameter =  $x("//div[@class=\"floating-block\"]").getAttribute("style");
        assertEquals(visibleParameter, "display: block;");
    }
}