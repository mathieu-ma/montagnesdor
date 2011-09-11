/*
 * PrinterApplet.java
 *
 * Created on 21 février 2002, 20:57
 */
package fr.mch.mdo.applets;

import gnu.io.CommDriver;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.swing.JApplet;

/**
 * In the jre/lib/security/java.policy file, we have to add these following lines:
 * For Ubuntu sun JDK 6:
 * permission java.lang.RuntimePermission "loadLibrary.rxtxSerial";
 * permission java.io.FilePermission "/usr/lib/jvm/java-6-sun-1.6.0.26/jre/lib/ext/amd64/librxtxSerial.so", "read";
 * permission java.io.FilePermission "/usr/lib/jvm/java-6-sun-1.6.0.26/jre/lib/ext/librxtxSerial.so", "read";
 * permission java.io.FilePermission "/usr/java/packages/lib/ext/amd64/librxtxSerial.so", "read";
 * permission java.io.FilePermission "/usr/java/packages/lib/ext/librxtxSerial.so", "read";
 * permission java.util.PropertyPermission "gnu.io.log.mode", "read";
 * 
 * PropertyPermission "gnu.io.log.mode" ==> Required by RXTX
 * 
 * @author mathieu ma
 * @version
 */
public class CopyOfPrinterApplet extends JApplet
{
	/**
     * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;

	private AbstractPrinter printerAdapter; 

	/**
	 * Initialization method that will be called after the applet is loaded into the browser.
	 * TODO Add a new parameter named noCheckDsr for USB Epson Printer ==> See Old java code 
	 *  
	 * In the java.policy file we MUST be updated with the
	 * following lines: 
	 * permission java.lang.RuntimePermission "loadLibrary.rxtxSerial"; 
	 * permission java.io.FilePermission "C:/Program Files/Java/jre6/lib/ext/x86/rxtxSerial.dll", "read";
	 * permission java.io.FilePermission "C:/Program Files/Java/jre6/lib/ext/rxtxSerial.dll", "read"; 
	 * permission java.util.PropertyPermission "gnu.io.log.mode", "read";
	 * 
	 * The permission java.util.PropertyPermission "gnu.io.log.mode", "read" must be set for applet.
	 */
	public void init() {
		super.init();

		printerAdapter = new AbstractPrinter() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public String getParameter(String key, String defaultValue) {
				String result = null;
				try {
					// Get parameter from applet
					result = CopyOfPrinterApplet.this.getParameter(key);
				} catch (Exception e) {
					debug = true;
					String message = "Could not get the parameter %s";
					System.err.println(String.format(message, key));
					// e.printStackTrace();
				}
				if (result == null) {
					// Get parameter from resource file
					if (result == null) {
						result = defaultValue;
					}
				}
				
				if (debug) {
					System.out.println("Parameter " + key + ": " + result);
				}
				return result;
			}
		};
		printerAdapter.init();
	}


	public void print() {
		printerAdapter.print();
	}

	public void resetDataBuffer() {
		printerAdapter.resetDataBuffer();
	}

	public void addData1(String data) {
		printerAdapter.addData1(data);
	}

	public void addData2(String data) {
		printerAdapter.addData2(data);
	}

	public static void main(String[] args) {
		CopyOfPrinterApplet printerApplet = new CopyOfPrinterApplet();
		printerApplet.init();
		//Vider le buffer de l'applet 
		printerApplet.resetDataBuffer();

		//Entete
		printerApplet.addData2("document.getElementById(");
		printerApplet.print();

	}
}
