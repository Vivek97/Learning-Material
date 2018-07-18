Check for broken link

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
 
import java.util.List;
 
 
public class HttpResponseCode {
 
    WebDriver driver;
    int statusCode
 
    public void checkBrokenLinks() {
        driver = new FirefoxDriver();
        driver.get("http://www.testingexcellence.com");
 
//Get all the links on the page
        List<WebElement> links = driver.findElements(By.cssSelector("a"));
 
        String href;
 
        for(WebElement link : links) {
            href = link.getAttribute("href");
            statusCode = new HttpResponseCode().httpResponseCodeViaGet(href);
 
            if(200 != statusCode) {
                System.out.println(href + " gave a response code of " + statusCode);
            }
        }
    }
 
    public static void main(String args[]) {
        new HttpResponseCode().checkBrokenLinks();
    }
}