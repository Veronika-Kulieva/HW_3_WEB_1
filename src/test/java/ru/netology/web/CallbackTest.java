package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldBeSuccessfulSubmittedApplication() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Кулиева Вероника");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79775557755");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void validNameAndSurnameWithDashTest() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вероника Гвилия-Кулиева");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79175557755");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void enterSymbolNameTest() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("#$%^");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79175557755");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
    }

    @Test
    public void firstAndLastNameIncorrect() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Kulieva Veronika");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79775557755");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
    }

    @Test
    public void shouldBeFilledFirstAndLastName() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79775557755");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
    }

    @Test
    public void shouldBeInputCorrectPhone() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Кулиева Вероника");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("1234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void textPhoneTest() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вероника Кулиева");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("xxxx");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }

    @Test
    public void shouldBeFilledPhone() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Кулиева Вероника");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void phoneWithoutPlusTest() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вероника Кулиева");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79175557755");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }

    @Test
    public void shouldBeFilledCheckbox() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Кулиева Вероника");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79775557755");
        driver.findElement(By.cssSelector("button.button")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).isDisplayed());
    }

}