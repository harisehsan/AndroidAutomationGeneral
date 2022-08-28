package Pages;

import base.Base;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.DemoPageObjects;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {

    WebDriver driver;
    DemoPageObjects demoPageObjects = new DemoPageObjects();
    Base base;
    int Max_tries = 20;
    List<String> devices;

    Map<String, String> networkNames = new HashMap<String, String>();

    @BeforeClass
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("automationName", "UIAutomator2");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "8.0.0");
        caps.setCapability("deviceUdid", "FFY5T18925000998");
        caps.setCapability("autoAcceptAlerts",true); // Accepting the alerts
        caps.setCapability("autoGrantPermissions",true); // automatically accept permission
        caps.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT); // To handle unexpected alert behaviour
        caps.setCapability("app", System.getProperty("user.dir") + "/apps/API Demos_4.0_apkcombo.com.apk");
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), caps);
        PageFactory.initElements(new AppiumFieldDecorator(driver), demoPageObjects);
        base = new Base((AppiumDriver<WebElement>) driver);
    }


    @Test (priority = 1)
    public void scrolling_test() throws MalformedURLException, InterruptedException {
        try {
            String Message = "Views";
            base.scrollToelement(demoPageObjects.views_lstItm);
            String text = demoPageObjects.views_lstItm.get(demoPageObjects.views_lstItm.size()-1).getText();
            Assert.assertEquals(Message,text,"Required text is not available!");
        } catch (Exception e) {
            Assert.fail("Problem occurred in performing test steps!\n"+e.getMessage());
        }
    }

    @Test (priority = 2)
    public void getting_devices_wifi_name_test() throws IOException, InterruptedException {
        try {
            devices = base.deviceUdid();
            networkNames = base.getNetworkName(devices);
            Assert.assertFalse(base.allValuesEmpty(networkNames),"No device is not connected to wifi!");
            System.out.println("\nThe Network of devices are as follows:");
            for (String device : devices) {
                System.out.println(networkNames.get(device));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

        @Test (priority = 3)
        public void turn_off_the_wifi_of_one_device_test() throws IOException, InterruptedException {
            devices = base.deviceUdid();
            networkNames = base.getNetworkName(devices);
            Assert.assertFalse(base.allValuesEmpty(networkNames),"No device is not connected to wifi!");
            boolean wifiTurnedOff = false;
            for (int i=networkNames.size()-1; i>=0; i--)
            {
                if (!networkNames.get(devices.get(i)).equalsIgnoreCase(""))
                {
                    wifiTurnedOff = base.turnOffWifi(devices.get(i));
                    System.out.println("\nWifi has been turned off for device: "+devices.get(i));
                    break;
                }
            }
            Assert.assertTrue(wifiTurnedOff,"\nUnable to turn off the wifi!");
    }

    @Test (priority = 4)
    public void display_udid_of_devices_not_connected_to_wifi_test() throws IOException, InterruptedException {
        devices = base.deviceUdid();
        networkNames = base.getNetworkName(devices);
        boolean displayUdid = false;
        for (int i=0; i<networkNames.size(); i++)
        {
            if (networkNames.get(devices.get(i)).equalsIgnoreCase(""))
            {
                System.out.println("The device having udid \""+devices.get(i)+"\" is not connected to wifi");
                displayUdid =true;
            }
        }
        Assert.assertTrue(displayUdid,"\nAll devices are connected to wifi!");
    }

    @AfterClass
    public void tearDown() {
        try {
            driver.quit();
        } catch (Exception e) {

        }
    }





    }
