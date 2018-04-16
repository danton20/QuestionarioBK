package script;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.Driver;

public class Main extends Driver {
  static String burgerKingCNPJ = "07415082002470";
  static String dataCompra = "12 04 18";
  static String horaCompra = "12 59 PM";
  static String urlQuestionario = "https://www.opiniaoburgerking.com.br/";
  
  public static void clicarAleatorio() {
    Boolean existeLista = false;
    Boolean existeRadio = false;
    try {
      existeLista = driver.findElement(By.xpath("//tr[contains(@class, 'InputRow')]")).isDisplayed();
      existeRadio = driver.findElement(By.xpath("//span[@class='radioBranded']")).isDisplayed();
    } catch (Exception e) {
    }
    if (existeLista) {
      Integer sizeLista = retornaNumeroElementos("//tr[contains(@class, 'InputRow')]");
      for (int cntLista = 1; cntLista <= sizeLista; cntLista++) {
        Integer radioAleatorio = retornaNumeroElementos(
            "//tr[contains(@class, 'InputRow')][" + cntLista + "]//span[@class='radioBranded']");
        radioAleatorio = randomNumber(radioAleatorio);
        clicar("(//tr[contains(@class, 'InputRow')][" + cntLista + "]//span[@class='radioBranded'])[" + radioAleatorio + "]");
      }
    }
    if (existeRadio) {
      Integer opcaoAleatorio = randomNumber(retornaNumeroElementos("//span[@class='radioBranded']"));
      driver.findElement(By.xpath("(//span[@class='radioBranded'])[" + opcaoAleatorio + "]")).click();
    }
  }
  
  public static Integer retornaNumeroElementos(String xpath) {
    return driver.findElements(By.xpath(xpath)).size();
  }
  
  public static Integer randomNumber(Integer numeroMaximo) {
    Random rand = new Random();
    return rand.nextInt(numeroMaximo) + 1;
  }
  
  public static Boolean existeCodigoValidacao() {
    try {
      return driver.findElement(By.xpath("//p[@class='ValCode']")).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }
  
  public static String codigoValidacao() {
    return driver.findElement(By.xpath("//p[@class='ValCode']")).getText();
  }
  
  public static void main(String[] args) {
    startDriver();
    acessar(urlQuestionario);
    clicar("//input[@value='Continuar']");
    preencher("//input[@id='SurveyCode']", burgerKingCNPJ);
    selecionar("//select[@id='InputDay']", dataCompra.split(" ")[0]);
    selecionar("//select[@id='InputMonth']", dataCompra.split(" ")[1]);
    selecionar("//select[@id='InputYear']", dataCompra.split(" ")[2]);
    selecionar("//select[@id='InputHour']", horaCompra.split(" ")[0]);
    selecionar("//select[@id='InputMinute']", horaCompra.split(" ")[1]);
    selecionar("//select[@id='InputMeridian']", horaCompra.split(" ")[2]);
    clicar("//input[@value='Iniciar']");
    esperarPagina();
    do {
      clicarAleatorio();
      clicar("//input[@value='Próximo']");
      esperarPagina();
    } while (!existeCodigoValidacao());
    System.out.println(codigoValidacao());
    driver.close();
  }
}
