package com.automation.framework.webAdaptor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.automation.framework.core.D2DriverScript;
import com.automation.framework.exceptions.BrowserException;
import com.automation.framework.exceptions.ElementFailException;
import com.automation.framework.exceptions.ObjectNameNotFoundException;
import com.automation.framework.interfaces.Validation;
import com.automation.framework.utilities.ObjectRead;

public class ValidationKeywords implements Validation {
	JavascriptExecutor executor = (JavascriptExecutor) D2DriverScript.driver;
	
	/**
	 * @name verifyBrokenLink
	 * @description verify the broken lines
	 * @param params
	 * @throws BrowserException
	 * @throws ObjectNameNotFoundException
	 * @throws ElementFailException
	 * @usage action
	 */
	public void verifyBrokenLink(HashMap<String, Object> params)
                  throws BrowserException, ObjectNameNotFoundException,
                  ElementFailException {
            D2DriverScript.logMessage("info","verifyBrokenLink is invoked");
            // List<WebElement> element = null;
            String locator = (String) params.get("arg0");
            By objLocator = ObjectRead.getObject(locator);
            // for rest Service
            D2DriverScript.logMessage("info","locator is " + locator);
            if (D2DriverScript.driver == null) {
                D2DriverScript.logMessage("error","Browser is not launched or Driver is failed to initialize");
                  throw new BrowserException(
                              new Throwable(
                                          "Browser is not launched or Driver is failed to initialize"));
            } else if (locator != null && locator.length() > 0) {
                  try {
                        String linkName = D2DriverScript.driver.findElement(objLocator).getAttribute("href");
                              URL url = new URL(linkName);
                              HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                              connection.setRequestMethod("GET");
                              connection.connect();

                              int code = connection.getResponseCode();
                            
                              if (code==200)
                              {
                                D2DriverScript.logMessage("info",locator +" and "+linkName +" link is working fine");
                              }else{
                                  D2DriverScript.logMessage("error",linkName +" link is broken");
                                          throw new ElementFailException(new Throwable(linkName +" link is broken"));
                              }

                  } catch (MalformedURLException | ProtocolException e) {
                      D2DriverScript.logMessage("error","Error in the Link Url" + e);
                        throw new ElementFailException(new Throwable(
                                    "Error in the Link Url "+e));
                                    } catch (InvalidSelectorException e) {
                      D2DriverScript.logMessage("error","Invalid xpath, verify the xpath syntax " + locator);
                        throw new ElementFailException(new Throwable(
                                    "Invalid xpath, verify the xpath syntax: " + locator));
                  } catch (NoSuchElementException e) {
                      D2DriverScript.logMessage("error","Invalid locator or locator not found " + locator);
                        throw new ElementFailException(new Throwable(
                                    "Invalid locator or locator not found: " + locator));
                  } catch (WebDriverException e) {
                      D2DriverScript.logMessage("error","Error in Webdriver" + e);
                        throw new ElementFailException(new Throwable(
                                    "Error in Webdriver " + e));
                  } catch (IOException e) {
                      D2DriverScript.logMessage("error","Error in the Link Url" + e);
                        throw new ElementFailException(new Throwable(
                                    "Error in the Link Url "+e));
                  }
            } else {
                D2DriverScript.logMessage("error","Incorrect parameters or error while parsing parameter");
                  throw new ElementFailException(new Throwable(
                              "Incorrect parameters or error while parsing parameter"));
            }
            D2DriverScript.logMessage("info","verifyBrokenLink action is executed");
      }

/**
	 * @name setValueToXpathAndverifyElementText
	 * @description set Value To Xpath And verifyElementText
	 * @param params
	 * @throws BrowserException
	 * @throws ObjectNameNotFoundException
	 * @throws ElementFailException
	 * @usage action
	 */
	public void setValueToXpathAndverifyElementText(HashMap<String, Object> params)
			throws BrowserException, ElementFailException,
			ObjectNameNotFoundException {
		D2DriverScript.logMessage("info","Verify Element Text action is invoked");
		WebElement element = null;
		String locator = (String) params.get("arg0");
		
		String inputext = (String) params.get("arg1");
		By objLocator = ObjectRead.getObject(locator,inputext);
		String expectedText = (String) params.get("arg2");
		
		D2DriverScript.logMessage("info","locator is " + locator);
		if (D2DriverScript.driver == null) {
			D2DriverScript.logMessage("error","Browser is not launched or Driver is failed to initialize");
			throw new BrowserException(
					new Throwable(
							"Browser is not launched or Driver is failed to initialize"));
		} else if (locator != null && locator.length() > 0) {
			try {
				D2DriverScript.logMessage("info","Expected text is " + expectedText);
				String actualText = null;
				//Modified by injecting javascript
				/*element = D2DriverScript.driver.findElement(objLocator);
				actualText = element.getText();*/
				
				element= D2DriverScript.driver.findElement(objLocator);
                actualText  = (String) executor.executeScript("return arguments[0].innerText",element);
                D2DriverScript.logMessage("info","expected value=" + expectedText
						+ " and actual value=" + actualText);
                
				if (!(actualText.equals(expectedText))) {
					D2DriverScript.logMessage("error","expected value= " + expectedText
							+ " and actual value is " + actualText);
					throw new ElementFailException(new Throwable(
							"expected value= " + expectedText
									+ " and actual value is " + actualText));
				}
				D2DriverScript.logMessage("info","setValueToXpathAndverifyElementText is successful");
			} catch (InvalidSelectorException e) {
				D2DriverScript.logMessage("error","Invalid xpath, verify the xpath syntax " + locator);
				throw new ElementFailException(new Throwable(
						"Invalid xpath, verify the xpath syntax "+locator));
			} catch (NoSuchElementException e) {
				D2DriverScript.logMessage("error","Invalid locator or locator not found " + locator);
				throw new ElementFailException(new Throwable(
						"Invalid locator or locator not found: " + locator));
			} catch (WebDriverException e) {
				D2DriverScript.logMessage("error","Error in Webdriver" + e);
				throw new ElementFailException(new Throwable(
						"Error in Webdriver " + e));
			}
		} else {
			D2DriverScript.logMessage("error","Incorrect parameters or error in parameter");
			throw new ElementFailException(new Throwable(
					"Incorrect parameters or error in parameter"));
		}
		D2DriverScript.logMessage("info","Verify Element Text action is executed");

	}
	/**
	 * @name verifyElementText
	 * @description verifyElementText
	 * @param params
	 * @throws BrowserException
	 * @throws ObjectNameNotFoundException
	 * @throws ElementFailException
	 * @usage Actions.VerifyElementText , XpathOfTargetElement ,ValueWhichYouWantToCompare
	 */
	public void verifyElementText(HashMap<String, Object> params)
			throws BrowserException, ElementFailException,
			ObjectNameNotFoundException {
		D2DriverScript.logMessage("info","Verify Element Text action is invoked");
		WebElement element = null;
		String locator = (String) params.get("arg0");
		String expectedText = (String) params.get("arg1");
		D2DriverScript.logMessage("info","locator is " + locator);
		if (D2DriverScript.driver == null) {
			D2DriverScript.logMessage("error","Browser is not launched or Driver is failed to initialize");
			throw new BrowserException(
					new Throwable(
							"Browser is not launched or Driver is failed to initialize"));
		} else if (locator != null && locator.length() > 0) {
			try {
				D2DriverScript.logMessage("info","Expected text is " + expectedText);
				String actualText = null;
				element = D2DriverScript.driver.findElement(ObjectRead
						.getObject(locator));
				actualText = element.getText();
				D2DriverScript.logMessage("info","Actual text is " + actualText);
				if (!(actualText.equals(expectedText))) {
					D2DriverScript.logMessage("error","expected value= " + expectedText
							+ " and actual value is " + actualText);
					throw new ElementFailException(new Throwable(
							"expected value= " + expectedText
									+ " and actual value is " + actualText));
				}
				D2DriverScript.logMessage("info","verify element text is successful");
			} catch (InvalidSelectorException e) {
				D2DriverScript.logMessage("error","Invalid xpath, verify the xpath syntax " + locator);
				throw new ElementFailException(new Throwable(
						"Invalid xpath, verify the xpath syntax "+locator));
			} catch (NoSuchElementException e) {
				D2DriverScript.logMessage("error","Invalid locator or locator not found " + locator);
				throw new ElementFailException(new Throwable(
						"Invalid locator or locator not found: " + locator));
			} catch (WebDriverException e) {
				D2DriverScript.logMessage("error","Error in Webdriver" + e);
				throw new ElementFailException(new Throwable(
						"Error in Webdriver " + e));
			}
		} else {
			D2DriverScript.logMessage("error","Incorrect parameters or error in parameter");
			throw new ElementFailException(new Throwable(
					"Incorrect parameters or error in parameter"));
		}
		D2DriverScript.logMessage("info","Verify Element Text action is executed");

	}
	


	/**
	 * @name verifyBrokenLinkByLinkName
	 * @description verify the broken lines
	 * @param params
	 * @throws BrowserException
	 * @throws ObjectNameNotFoundException
	 * @throws ElementFailException
	 * @usage action
	 */
	public void verifyBrokenLinkByLinkName(HashMap<String, Object> params)
                  throws BrowserException, ObjectNameNotFoundException,
                  ElementFailException {
            D2DriverScript.logMessage("info","verifyBrokenLinkByLinkName is invoked");
            // List<WebElement> element = null;
            String linkName = (String) params.get("arg0");
            
            // for rest Service
            D2DriverScript.logMessage("info","Link is " + linkName);
            if (D2DriverScript.driver == null) {
                D2DriverScript.logMessage("error","Browser is not launched or Driver is failed to initialize");
                  throw new BrowserException(
                              new Throwable(
                                          "Browser is not launched or Driver is failed to initialize"));
            } 
                  try {
                              URL url = new URL(linkName);
                              HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                              connection.setRequestMethod("GET");
                              connection.connect();

                              int code = connection.getResponseCode();
                            
                              if (code==200)
                              {
                                D2DriverScript.logMessage("info",linkName +" link is working fine");
                              }else{
                                  D2DriverScript.logMessage("error",linkName +" link is broken");
                                          throw new ElementFailException(new Throwable(linkName +" link is broken"));
                              }

                  } catch (MalformedURLException | ProtocolException e) {
                      D2DriverScript.logMessage("error","Error in the Link Url" + e);
                        throw new ElementFailException(new Throwable(
                                    "Error in the Link Url "+e));
                  } catch (WebDriverException e) {
                      D2DriverScript.logMessage("error","Error in Webdriver" + e);
                        throw new ElementFailException(new Throwable(
                                    "Error in Webdriver " + e));
                  } catch (IOException e) {
                      D2DriverScript.logMessage("error","Error in the Link Url" + e);
                        throw new ElementFailException(new Throwable(
                                    "Error in the Link Url "+e));
                  }
            
            D2DriverScript.logMessage("info","verifyBrokenLinkByLinkName action is executed");
      }


	/**
	 * @name verifyPageTitle
	 * @description verify the page title
	 * @param params
	 * @throws BrowserException
	 * @throws ObjectNameNotFoundException
	 * @throws ElementFailException
	 * @usage action
	 */
	public void verifyPageTitle(HashMap<String, Object> params)
			throws BrowserException, ElementFailException,
			ObjectNameNotFoundException {
		D2DriverScript.logMessage("info","Verify Page Title action is invoked");
		
		String expectedTitle = (String) params.get("arg0");
		D2DriverScript.logMessage("info","Expected Title is "+expectedTitle);
		if (D2DriverScript.driver == null) {
			D2DriverScript.logMessage("error","Browser is not launched or Driver is failed to initialize");
			throw new BrowserException(
					new Throwable(
							"Browser is not launched or Driver is failed to initialize"));
		} else {
			try {
				String actualTitle = D2DriverScript.driver.getTitle();
				D2DriverScript.logMessage("info","Page Title is "+actualTitle);
				if (!(actualTitle.equals(expectedTitle))) {
					throw new ElementFailException(new Throwable(
							"Page title mismatch"));
				}
				D2DriverScript.logMessage("info","Verify Page Title action is successful");
			} catch (WebDriverException e) {
				D2DriverScript.logMessage("error","Error in Webdriver" + e);
				throw new ElementFailException(new Throwable(
						"Error in Webdriver " + e));
			}
		}
		D2DriverScript.logMessage("info","Verify Page Title action is executed");

	}
	
	/**
	 * @name verifyPageTitle
	 * @description verify the page title
	 * @param params
	 * @throws BrowserException
	 * @throws ObjectNameNotFoundException
	 * @throws ElementFailException
	 * @usage action
	 */
	public void setValueToXpathAndverifyElementContainsText(HashMap<String, Object> params)
			throws BrowserException, ElementFailException,
			ObjectNameNotFoundException {
		D2DriverScript.logMessage("info","setValueToXpathAndverifyElementContainsText action is invoked");
		WebElement element = null;
		String locator = (String) params.get("arg0");
		
		String inputext = (String) params.get("arg1");
		By objLocator = ObjectRead.getObject(locator,inputext);
		String expectedText = (String) params.get("arg2");
		
		D2DriverScript.logMessage("info","locator is " + locator);
		if (D2DriverScript.driver == null) {
			D2DriverScript.logMessage("error","Browser is not launched or Driver is failed to initialize");
			throw new BrowserException(
					new Throwable(
							"Browser is not launched or Driver is failed to initialize"));
		} else if (locator != null && locator.length() > 0) {
			try {
				D2DriverScript.logMessage("info","Expected text is " + expectedText);
				String actualText = null;
				element = D2DriverScript.driver.findElement(objLocator);
				actualText = element.getText();
                D2DriverScript.logMessage("info","expected value=" + expectedText
						+ " and actual value=" + actualText);
                
				if (!(actualText.equals(expectedText))) {
					D2DriverScript.logMessage("error","expected value= " + expectedText
							+ " and actual value is " + actualText);
					throw new ElementFailException(new Throwable(
							"expected value= " + expectedText
									+ " and actual value is " + actualText));
				}
				D2DriverScript.logMessage("info","setValueToXpathAndverifyElementText is successful");
			} catch (InvalidSelectorException e) {
				D2DriverScript.logMessage("error","Invalid xpath, verify the xpath syntax " + locator);
				throw new ElementFailException(new Throwable(
						"Invalid xpath, verify the xpath syntax "+locator));
			} catch (NoSuchElementException e) {
				D2DriverScript.logMessage("error","Invalid locator or locator not found " + locator);
				throw new ElementFailException(new Throwable(
						"Invalid locator or locator not found: " + locator));
			} catch (WebDriverException e) {
				D2DriverScript.logMessage("error","Error in Webdriver" + e);
				throw new ElementFailException(new Throwable(
						"Error in Webdriver " + e));
			}
		} else {
			D2DriverScript.logMessage("error","Incorrect parameters or error in parameter");
			throw new ElementFailException(new Throwable(
					"Incorrect parameters or error in parameter"));
		}
		D2DriverScript.logMessage("info","setValueToXpathAndverifyElementContainsText action is executed");

	}
	

	
	/**
	 * @name verifyTextUsingJavascript
	 * @description verifyTextUsingJavascript
	 * @param params
	 * @throws BrowserException
	 * @throws ObjectNameNotFoundException
	 * @throws ElementFailException
	 * @usage Actions.VerifyElementText , XpathOfTargetElement ,ValueWhichYouWantToCompare
	 */
	public void verifyTextUsingJavascript(HashMap<String, Object> params)
			throws BrowserException, ElementFailException,
			ObjectNameNotFoundException {
		D2DriverScript.logMessage("info","verifyElementContainsText action is invoked");
		WebElement element = null;
		String locator = (String) params.get("arg0");
		String expectedText = (String) params.get("arg1");
		D2DriverScript.logMessage("info","locator is " + locator);
		if (D2DriverScript.driver == null) {
			D2DriverScript.logMessage("error","Browser is not launched or Driver is failed to initialize");
			throw new BrowserException(
					new Throwable(
							"Browser is not launched or Driver is failed to initialize"));
		} else if (locator != null && locator.length() > 0) {
			try {
				D2DriverScript.logMessage("info","Expected text is " + expectedText);
				String actualText = null;
				element = D2DriverScript.driver.findElement(ObjectRead
						.getObject(locator));
				actualText = element.getText();
				D2DriverScript.logMessage("info","Actual text is " + actualText);
				if (!(actualText.contains(expectedText))) {
					D2DriverScript.logMessage("error","expected value= " + expectedText
							+ " and actual value is " + actualText);
					throw new ElementFailException(new Throwable(
							"expected value= " + expectedText
									+ " and actual value is " + actualText));
				}
				D2DriverScript.logMessage("info","verify element text contains is successful");
			} catch (InvalidSelectorException e) {
				D2DriverScript.logMessage("error","Invalid xpath, verify the xpath syntax " + locator);
				throw new ElementFailException(new Throwable(
						"Invalid xpath, verify the xpath syntax "+locator));
			} catch (NoSuchElementException e) {
				D2DriverScript.logMessage("error","Invalid locator or locator not found " + locator);
				throw new ElementFailException(new Throwable(
						"Invalid locator or locator not found: " + locator));
			} catch (WebDriverException e) {
				D2DriverScript.logMessage("error","Error in Webdriver" + e);
				throw new ElementFailException(new Throwable(
						"Error in Webdriver " + e));
			}
		} else {
			D2DriverScript.logMessage("error","Incorrect parameters or error in parameter");
			throw new ElementFailException(new Throwable(
					"Incorrect parameters or error in parameter"));
		}
		D2DriverScript.logMessage("info","verifyElementContainsText action is executed");

	}
	
	/**
	 * @name verifyElementContainsText
	 * @description verifyElementText
	 * @param params
	 * @throws BrowserException
	 * @throws ObjectNameNotFoundException
	 * @throws ElementFailException
	 * @usage Actions.VerifyElementText , XpathOfTargetElement ,ValueWhichYouWantToCompare
	 */
	public void verifyElementContainsText(HashMap<String, Object> params)
			throws BrowserException, ElementFailException,
			ObjectNameNotFoundException {
		D2DriverScript.logMessage("info","verifyElementContainsText action is invoked");
		WebElement element = null;
		String locator = (String) params.get("arg0");
		String expectedText = (String) params.get("arg1");
		D2DriverScript.logMessage("info","locator is " + locator);
		if (D2DriverScript.driver == null) {
			D2DriverScript.logMessage("error","Browser is not launched or Driver is failed to initialize");
			throw new BrowserException(
					new Throwable(
							"Browser is not launched or Driver is failed to initialize"));
		} else if (locator != null && locator.length() > 0) {
			try {
				D2DriverScript.logMessage("info","Expected text is " + expectedText);
				String actualText = null;
				element = D2DriverScript.driver.findElement(ObjectRead
						.getObject(locator));
				actualText = element.getText();
				D2DriverScript.logMessage("info","Actual text is " + actualText);
				if (!(actualText.contains(expectedText))) {
					D2DriverScript.logMessage("error","expected value= " + expectedText
							+ " and actual value is " + actualText);
					throw new ElementFailException(new Throwable(
							"expected value= " + expectedText
									+ " and actual value is " + actualText));
				}
				D2DriverScript.logMessage("info","verify element text contains is successful");
			} catch (InvalidSelectorException e) {
				D2DriverScript.logMessage("error","Invalid xpath, verify the xpath syntax " + locator);
				throw new ElementFailException(new Throwable(
						"Invalid xpath, verify the xpath syntax "+locator));
			} catch (NoSuchElementException e) {
				D2DriverScript.logMessage("error","Invalid locator or locator not found " + locator);
				throw new ElementFailException(new Throwable(
						"Invalid locator or locator not found: " + locator));
			} catch (WebDriverException e) {
				D2DriverScript.logMessage("error","Error in Webdriver" + e);
				throw new ElementFailException(new Throwable(
						"Error in Webdriver " + e));
			}
		} else {
			D2DriverScript.logMessage("error","Incorrect parameters or error in parameter");
			throw new ElementFailException(new Throwable(
					"Incorrect parameters or error in parameter"));
		}
		D2DriverScript.logMessage("info","verifyElementContainsText action is executed");

	}
	
	/**
	 * @name setValueToXpathAndVerifyElementTextUsingJavascript
	 * @description verify element text using javascript by passing xpath value
	 * @param params
	 * @throws BrowserException
	 * @throws ObjectNameNotFoundException
	 * @throws ElementFailException
	 * @usage action
	 */
	public void setValueToXpathAndVerifyElementTextUsingJavascript(HashMap<String, Object> params)
              throws BrowserException, ObjectNameNotFoundException,
              ElementFailException {
				D2DriverScript.logMessage("info","stValueToXpathAndVerifyElementTextUsingJavascript action is invoked");
				WebElement element = null;
				String locator = (String) params.get("arg0");
				String xapthValue = (String) params.get("arg1");
				By objLocator = ObjectRead.getObject(locator,xapthValue);
				String expectedText = (String) params.get("arg2");
				D2DriverScript.logMessage("info","locator is " + locator);
				if (D2DriverScript.driver == null) {
		              D2DriverScript.logMessage("error","Browser is not launched or Driver is failed to initialize");
		              throw new BrowserException(new Throwable(
		            		  "Browser is not launched or Driver is failed to initialize"));
				} else if (locator != null && locator.length() > 0) {
		              try {
                              String actualText = null;
                              D2DriverScript.logMessage("info","Expected Text= " + expectedText);
                              element= D2DriverScript.driver.findElement(objLocator);
                              JavascriptExecutor executor = (JavascriptExecutor)D2DriverScript.driver;
                              actualText = (String) executor.executeScript("return arguments[0].value", element);
                              D2DriverScript.logMessage("info","Text from the given locator is actualText=" + actualText);
                             
                              if (actualText != null && actualText!= "") {                                             
                          
                            	  D2DriverScript.logMessage("info","setValueToXpathAndVerifyElementTextUsingJavascript is successful");
                                          if (!(actualText.equals(expectedText))) {
                            					D2DriverScript.logMessage("error","expected value= " + expectedText
                            							+ " and actual value is " + actualText);
                            					throw new ElementFailException(new Throwable(
                            							"expected value= " + expectedText
                            									+ " and actual value is " + actualText));
                            				}
                                          
                              } else {
                                          D2DriverScript.logMessage("error","Element found, but  Element Text Not Found");
                                          throw new ElementFailException(
                                                      "Element found, but  Element Text Not Found");
                              }
                              
                             
              				
		              } catch (InvalidSelectorException e) {
		                              D2DriverScript.logMessage("error","Invalid xpath, verify the xpath syntax " + locator);
		                              throw new ElementFailException(new Throwable(
		                                                              "Invalid xpath, verify the xpath syntax: " + locator));
		              } catch (NoSuchElementException e) {
		                              D2DriverScript.logMessage("error","Invalid locator or locator not found " + locator);
		                              throw new ElementFailException(new Throwable(
		                                                              "Invalid locator or locator not found: " + locator));
		              } catch (WebDriverException e) {
		                              D2DriverScript.logMessage("error","Error in Webdriver" + e);
		                              throw new ElementFailException(new Throwable(
		                                                              "Error in Webdriver " + e));
		              }
				} else {
				              D2DriverScript.logMessage("error","Incorrect parameters or error while parsing parameter");
				              throw new ElementFailException(new Throwable(
				                                              "Incorrect parameters or error while parsing parameter"));
				}
				D2DriverScript.logMessage("info","stValueToXpathAndVerifyElementTextUsingJavascript action is executed");
		
		}
	
	/**
	 * @name verifyElementTextUsingJavascript
	 * @description verify element text using javascript
	 * @param params
	 * @throws BrowserException
	 * @throws ObjectNameNotFoundException
	 * @throws ElementFailException
	 * @usage action
	 */
	public void verifyElementTextUsingJavascript(HashMap<String, Object> params)
              throws BrowserException, ObjectNameNotFoundException,
              ElementFailException {
				D2DriverScript.logMessage("info","verifyElementTextUsingJavascript action is invoked");
				WebElement element = null;
				String locator = (String) params.get("arg0");
				By objLocator = ObjectRead.getObject(locator);
				String expectedText = (String) params.get("arg1");
				D2DriverScript.logMessage("info","locator is " + locator);
				if (D2DriverScript.driver == null) {
		              D2DriverScript.logMessage("error","Browser is not launched or Driver is failed to initialize");
		              throw new BrowserException(new Throwable(
		            		  "Browser is not launched or Driver is failed to initialize"));
				} else if (locator != null && locator.length() > 0) {
		              try {
                              String actualText = null;
                              D2DriverScript.logMessage("info","Expected Text= " + expectedText);
                              element= D2DriverScript.driver.findElement(objLocator);
                              JavascriptExecutor executor = (JavascriptExecutor)D2DriverScript.driver;
                              actualText = (String) executor.executeScript("return arguments[0].value", element);
                              D2DriverScript.logMessage("info","Text from the given locator is actualText=" + actualText);
                             
                              if (actualText != null && actualText!= "") {                                             
                          
                            	  D2DriverScript.logMessage("info","verifyElementTextUsingJavascript successful");
                                          if (!(actualText.equals(expectedText))) {
                            					D2DriverScript.logMessage("error","expected value= " + expectedText
                            							+ " and actual value is " + actualText);
                            					throw new ElementFailException(new Throwable(
                            							"expected value= " + expectedText
                            									+ " and actual value is " + actualText));
                            				}
                                          
                              } else {
                                          D2DriverScript.logMessage("error","Element found, but  Element Text Not Found");
                                          throw new ElementFailException(
                                                      "Element found, but  Element Text Not Found");
                              }
                              
                             
              				
		              } catch (InvalidSelectorException e) {
		                              D2DriverScript.logMessage("error","Invalid xpath, verify the xpath syntax " + locator);
		                              throw new ElementFailException(new Throwable(
		                                                              "Invalid xpath, verify the xpath syntax: " + locator));
		              } catch (NoSuchElementException e) {
		                              D2DriverScript.logMessage("error","Invalid locator or locator not found " + locator);
		                              throw new ElementFailException(new Throwable(
		                                                              "Invalid locator or locator not found: " + locator));
		              } catch (WebDriverException e) {
		                              D2DriverScript.logMessage("error","Error in Webdriver" + e);
		                              throw new ElementFailException(new Throwable(
		                                                              "Error in Webdriver " + e));
		              }
				} else {
				              D2DriverScript.logMessage("error","Incorrect parameters or error while parsing parameter");
				              throw new ElementFailException(new Throwable(
				                                              "Incorrect parameters or error while parsing parameter"));
				}
				D2DriverScript.logMessage("info","verifyElementTextUsingJavascript action is executed");
		
		}
	  
	/**
	 * @name verifyPageURL
	 * @description Verifies page tiltle with the expected input URL
	 * @param params
	 * @throws BrowserException
	 * @throws ObjectNameNotFoundException
	 * @throws ElementFailException
	 * @usage action
	 */
	public void verifyPageURL(HashMap<String, Object> params)
			throws BrowserException, ElementFailException,
			ObjectNameNotFoundException {
		D2DriverScript.logMessage("info","Verify Page URL  action is invoked");
		String expectedURL = null;
		String inputParams = (String) params.get("arg0");
		expectedURL = inputParams;
		if (D2DriverScript.driver == null) {
			D2DriverScript.logMessage("error","Browser is not launched or Driver is failed to initialize");
			throw new BrowserException(
					new Throwable(
							"Browser is not launched or Driver is failed to initialize"));
		} else {
			try {
				D2DriverScript.logMessage("info","Expected URL is " + expectedURL);
				String actualURL = null;
				actualURL = D2DriverScript.driver.getCurrentUrl();
				D2DriverScript.logMessage("info","Actual URL is " + actualURL);
				if (!(actualURL.equals(expectedURL))) {
					D2DriverScript.logMessage("error","expected value= " + expectedURL
							+ " and actual value is " + actualURL);
					throw new ElementFailException(new Throwable(
							"expected value= " + expectedURL
									+ " and actual value is " + actualURL));
				}
				D2DriverScript.logMessage("info","verifyPageURL is successful");
			} catch (WebDriverException e) {
				D2DriverScript.logMessage("error","Error in Webdriver" + e);
				throw new ElementFailException(new Throwable(
						"Error in Webdriver " + e));
			}
		}
		D2DriverScript.logMessage("info","Verify Page URL action is executed");

	}
}