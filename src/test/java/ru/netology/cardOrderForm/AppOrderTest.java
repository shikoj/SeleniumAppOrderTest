package ru.netology.cardOrderForm;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

        driver.get("http://localhost:9999");

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void positiveOrderTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79851591831");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement result = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(result.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", result.getText().trim());
    }

    @Test
    public void positiveOrderTestNameWithSpace() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Василий Филипов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79851591831");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement result = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(result.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", result.getText().trim());
    }

    @Test
    public void positiveOrderTestNameWithHyphenatedName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Василий-Филипов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79851591831");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement result = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(result.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", result.getText().trim());
    }

    @Test
    public void negativeOrderTestNullName() {

        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79851591831");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(error.isDisplayed());
        assertEquals("Поле обязательно для заполнения", error.getText().trim());

    }

    @Test
    public void negativeOrderTestNullPhone() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(error.isDisplayed());
        assertEquals("Поле обязательно для заполнения", error.getText().trim());

    }

    @Test
    public void negativeOrderTestNullCheckBox() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79851591831");
        driver.findElement(By.tagName("button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text"));
        assertTrue(error.isDisplayed());
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", error.getText().trim());

    }


    @Test
    public void negativeOrderTestNoValidNameLatinSymbols() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Vasiliy");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79851591831");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(error.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", error.getText().trim());

    }

    @Test
    public void negativeOrderTestNoValidNameWithNumber() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Василий 1");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79851591831");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(error.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", error.getText().trim());

    }


    @Test
    public void negativeOrderTestNoValidPhoneWhithoutPlus() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79851591831");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(error.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", error.getText().trim());

    }

    @Test
    public void negativeOrderTestNoValidPhone10Symbols() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7985159183");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(error.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", error.getText().trim());

    }

    @Test
    public void negativeOrderTestNoValidPhone12Symbols() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+798515918311");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(error.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", error.getText().trim());

    }


    @Test
    public void negativeOrderTestNoValidPhoneLetters() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+bbbbbbbbbbb");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(error.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", error.getText().trim());

    }

    @Test
    public void negativeOrderTestNoValidPhoneSpecialSymbol() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("!79012345678");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(error.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", error.getText().trim());

    }


}