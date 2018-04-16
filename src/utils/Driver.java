package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class Driver {
  protected static WebDriver driver;
  protected static WebDriverWait wait;
  
  public static void startDriver() {
    String userDir = System.getProperty("user.dir");
    System.setProperty("webdriver.chrome.driver", userDir + "\\driver\\chromedriver.exe");
    driver = new ChromeDriver();
    wait = new WebDriverWait(driver, 10);
  }
  
  public static void clicar(String xpath) {
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    driver.findElement(By.xpath(xpath)).click();
  }
  
  public static void preencher(String xpath, String texto) {
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    driver.findElement(By.xpath(xpath)).sendKeys(texto);
  }
  
  public static void selecionar(String xpath, String opcao) {
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    Select selector = new Select(driver.findElement(By.xpath(xpath)));
    selector.selectByVisibleText(opcao);
  }
  
  public static void acessar(String url) {
    driver.get(url);
  }
  
  public static void esperarPagina() {
    new WebDriverWait(driver, 30)
        .until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
  }
}
