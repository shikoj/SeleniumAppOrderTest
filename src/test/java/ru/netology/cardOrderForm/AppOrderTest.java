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

    public void PositiveOrderTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79851591831");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement result = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(result.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", result.getText().trim());
    }

        @Test

        public void NegativeOrderTestNullName() {

            driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79851591831");
            driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
            driver.findElement(By.tagName("button")).click();

            WebElement error = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
            assertTrue(error.isDisplayed());


    }

    @Test

    public void NegativeOrderTestNullPhone() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(error.isDisplayed());

    }

    @Test

    public void NegativeOrderTestNoValidName() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Vasiliy");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79851591831");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.tagName("button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(error.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", error.getText().trim());

    }



}
