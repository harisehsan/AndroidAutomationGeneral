I have developed the total four scenarios and the explanation of these scenarios are as follows:

Pre-Requisites for execution:

1.	Connect more than one android devices to PC
2.	Install the app “API Demos_4.0_apkcombo.com.apk” on one of the connected device. (This app is found in the “apps” directory)
3.	Set the capabilities like “platformName”, “platformVersion”, “deviceUdid”  in setUp() method of Demo.java file for the device in which “API Demos_4.0_apkcombo.com.apk” is installed
4.	Run the latest version of Appium Server on (Host: 0.0.0.0 and port: 4723)
5.	Execute the file “Demo.java” class using the TestNG Framework.

Scenarios 1: (Scrolling Test)

For this scenario, I have used the app called “API Demos_4.0_apkcombo.com.apk” and perform the following steps will be performed during execution:
1.	Launch app.
2.	Look for the text “Views”.
3.	If the required text is not available then perform the scroll down and look for the text again and again until the text found. (The method swipeDown() and scrollToelement(List<WebElement> elements) are used for that purpose which are defined in Base.java class).
4.	Finally if the required text is available then make the test case pass otherwise fail it.

Note: There are following 3 desired compatibilities are defined to ignore the popup appears in the execution:

caps.setCapability("autoAcceptAlerts",true); // Accepting the alerts
caps.setCapability("autoGrantPermissions",true); // automatically accept permission
caps.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT); // To handle unexpected alert behaviour

Scenarios 2: (Getting Device WI-Fi Name Test)

The following steps are performed to get the network names of devices
1.	Get the connected devices UDID (using this method List<String> deviceUDID()).
2.	Pass each device UDID to get the connected WI-FI network name (using this method Map<String, String> getNetworkName (List<String> UDID) defined in Base.java class).
3.	Perform assertion for test case to make sure that one or more device is connected to WI-FI.

Scenarios 3: (Turn off Wi-Fi of one device Test)

The following steps are performed to turn off the network:
1.	First of all check that at least one or more devices should be connected to Wi-Fi.
2.	Turn off the Wi-Fi of one device (Using method turnoffWifi(String UDID) defined in Base.Java).
3.	Perform assertion to make sure that device is Wi-Fi is turned off. 

Scenarios 4: (Display the UDID of Device which is not connected to WI-FI)

The following steps are performed to get UDID of device which is not connected to Wi-Fi or Wi-Fi is turned off:
1.	Get the UDID of all connected devices.
2.	Get the network name for each UDID.
3.	If the network name is empty then prints its UDID.
4.	Perform assertion to make sure that at least one or more devices are not connected to WI-FI.

Project details:

1.	Code is written in Java with TestNG and Gradle!
2.	Used PageObject/PageFactory Structure.
3.	Code is successfully published on GitHub.
4.	Developed the README.MD file for details. 
5.	Able to get Green for all test cases.
