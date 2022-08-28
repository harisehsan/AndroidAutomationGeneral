package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Base {
    final int max_tries = 10;
    public AppiumDriver<WebElement> driver;

    public Base(AppiumDriver<WebElement> driver) {
        this.driver = driver;
    }


    public void waitForElementsToAppear(List<WebElement> id) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfAllElements(id));
    }
    private void swipeDown() {   // Perform little bit Down Scroll on Current Screen
        try {
            PointOption pointOption = new PointOption();
            Dimension dim = driver.manage().window().getSize();
            int height = dim.getHeight();
            int width = dim.getWidth();
            int x = width / 2;
            int top_y = (int) (height * 0.9);
            int bottom_y = (int) (height * 0.788);
//            System.out.println("These are the coordinates :" + x + "  " + top_y + " " + bottom_y);
            TouchAction ts = new TouchAction((PerformsTouchActions) driver);
            ts.press(pointOption.withCoordinates(x, (top_y))).moveTo(pointOption.withCoordinates(x, (bottom_y))).release().perform();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void scrollToelement(List<WebElement> elements) {
        int i = 0;
        while (i < max_tries ) {
            if (!elements.get(elements.size()-1).getText().equalsIgnoreCase("Views")) {
                swipeDown();
                i++;
            } else
                break;
        }
    }


    public List<String> deviceUdid() throws IOException {
        try {
            String commandString1;
            commandString1 = String.format("%s", "adb devices");
            Process process = Runtime.getRuntime().exec(commandString1);
            process.waitFor(3, TimeUnit.SECONDS);
            String udid = getOutputFromCommand(process);
            udid = udid.replace("List of devices attached", "").replace("device", "").replaceAll("&#x9;", "");
            return Arrays.asList(udid.split("\\s+"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

        public Map<String, String> getNetworkName (List<String> udid) throws IOException, InterruptedException {
            try {
                Map<String, String> map = new HashMap<String, String>();
                List<String> extractedCommand;
                String commandString1;
                for (String s : udid) {
                    commandString1 = String.format("%s", "adb -s "+s+" shell dumpsys netstats | grep -E 'iface=wlan.*networkId'");
                    Process process = Runtime.getRuntime().exec(commandString1);
                    process.waitFor(3, TimeUnit.SECONDS);
                        String networkName = getOutputFromCommand(process);
                        if (!networkName.isEmpty()) {
                            extractedCommand = Arrays.asList(networkName.split("\\s"));
                            map.put(s, extractedCommand.get(5).replace("networkId=\"","").replace("\",",""));
                        }
                    else
                        map.put(s, "");
                }
                return map;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
    }

    public boolean turnOffWifi(String udid) throws IOException {
        try {
            String commandString1;
            commandString1 = String.format("%s", "adb -s "+udid+" shell svc wifi disable");
            Process process = Runtime.getRuntime().exec(commandString1);
            process.waitFor(3, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    private String getOutputFromCommand(Process process) throws IOException {
        String output2 = "";
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            output2 = output2.concat(line);
        }
        return output2;
    }

    public boolean allValuesEmpty(Map<String, String> map) throws IOException {
        for (int i=0; i<map.size();i++)
        {
          if (!map.get(deviceUdid().get(i)).equalsIgnoreCase(""))
              return false;
        }
        return true;

    }
}
