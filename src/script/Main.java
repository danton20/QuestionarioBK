package script;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Main {
  static WebDriver driver;
  static WebDriverWait wait;
  static String burgerKingCNPJ = "07415082002470";
  static String dataCompra = "12 04 18";
  static String horaCompra = "12 59 PM";
  static String urlQuestionario = "https://www.opiniaoburgerking.com.br/";
  
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
  
  public static void acessar() {
    driver.get(urlQuestionario);
  }
  
  public static void clicarAleatorio() {
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@type='radio'])[1]")));
    Integer opcaoAleatorio = randomNumber(retornaNumeroElementos("//input[@type='radio']"));
    driver.findElement(By.xpath("(//input[@type='radio'])[" + opcaoAleatorio + "]")).click();
  }
  
  public static Integer retornaNumeroElementos(String xpath) {
    return driver.findElements(By.xpath(xpath)).size();
  }
  
  public static void main(String[] args) {
    try {
      startDriver();
      acessar();
      clicar("//input[@value='Continuar']");
      preencher("//input[@id='SurveyCode']", burgerKingCNPJ);
      selecionar("//select[@id='InputDay']", dataCompra.split(" ")[0]);
      selecionar("//select[@id='InputMonth']", dataCompra.split(" ")[1]);
      selecionar("//select[@id='InputYear']", dataCompra.split(" ")[2]);
      selecionar("//select[@id='InputHour']", horaCompra.split(" ")[0]);
      selecionar("//select[@id='InputMinute']", horaCompra.split(" ")[1]);
      selecionar("//select[@id='InputMeridian']", horaCompra.split(" ")[2]);
      clicar("//input[@value='Iniciar']");
      for (int cntPagina = 0; cntPagina < 30; cntPagina++) {
        clicarAleatorio();
        clicar("//input[@value='Próximo']");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      driver.close();
    }
  }
  
  public static Integer randomNumber(Integer numeroMaximo) {
    Random rand = new Random();
    return rand.nextInt(numeroMaximo) + 1;
  }
}
