/*
 * PrinterApplet.java
 *
 * Created on 21 février 2002, 20:57
 */
package fr.mch.mdo.client.printer;


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
public interface IPrinter
{
	/** parameters key for debug */
	String PARAMETER_DEBUG_KEY = "debug";
	/** Default value for DEFAULT_CHARSET */
	String DEFAULT_CHARSET = "ISO8859_1";
	/** parameters key for charset */
	String PARAMETER_CHARSET_KEY = "charset";
	/** Default value for PARAMETER_LINUX_DRIVER_NAME_KEY */
	String DEFAULT_LINUX_DRIVER_NAME = "gnu.io.RXTXCommDriver";
	/** parameters key for linuxDriverName */
	String PARAMETER_LINUX_DRIVER_NAME_KEY = "linuxDriverName";
	/** Default value for PARAMETER_LINUX_DRIVER_NAME_KEY */
	String DEFAULT_LINUX_PORT_COM = "/dev/ttyUSB0";
	/** parameters key for linuxPortCom */
	String PARAMETER_LINUX_PORT_COM_KEY = "linuxPortCom";
	/** Default value for PARAMETER_WINDOWS_DRIVER_NAME_KEY */
	String DEFAULT_WINDOWS_DRIVER_NAME = "com.sun.comm.Win32Driver";
	/** parameters key for windowsDriverName */
	String PARAMETER_WINDOWS_DRIVER_NAME_KEY = "windowsDriverName";
	/** Default value for PARAMETER_WINDOWS_PORT_COM_KEY */
	String DEFAULT_WINDOWS_PORT_COM = "COM1";
	/** parameters key for windowsPortCom */
	String PARAMETER_WINDOWS_PORT_COM_KEY = "windowsPortCom";
	/** Default value for PARAMETER_SERIAL_PORT_BAUDS_KEY */
	String DEFAULT_SERIAL_PORT_BAUDS = "9600";
	/** parameters key for serialportBauds */
	String PARAMETER_SERIAL_PORT_BAUDS_KEY = "serialportBauds";
	/** Default value for PARAMETER_SERIAL_PORT_BITS_KEY */
	String DEFAULT_SERIAL_PORT_BITS = "8";
	/** parameters key for serialportBits */
	String PARAMETER_SERIAL_PORT_BITS_KEY = "serialportBits";
	/** Default value for PARAMETER_SERIAL_PORT_STOP_BITS_KEY */
	String DEFAULT_SERIAL_PORT_STOP_BITS = "1";
	/** parameters key for serialportStopBits */
	String PARAMETER_SERIAL_PORT_STOP_BITS_KEY = "serialportStopBits";
	/** Default value for PARAMETER_SERIAL_PORT_PARITY_KEY */
	String DEFAULT_SERIAL_PORT_PARITY = "0";
	/** parameters key for serialportParity */
	String PARAMETER_SERIAL_PORT_PARITY_KEY = "serialportParity";
	/** Default value for PARAMETER_PACKET_KEY */
	String DEFAULT_PACKET = "40";
	/** parameters key for packet */
	String PARAMETER_PACKET_KEY = "packet";
	/** Default value for PARAMETER_PAUSE_KEY */
	String DEFAULT_PAUSE = "1500";
	/** parameters key for pause */
	String PARAMETER_PAUSE_KEY = "pause";
	/** Default value for PARAMETER_SPECIAL_CHARACTERS_STRING_KEY */
	String DEFAULT_SPECIAL_CHARACTERS_STRING = "#;$;à;°;ç;§;^;`;é;ù;è;¨";
	/** parameters key for specialCharactersString */
	String PARAMETER_SPECIAL_CHARACTERS_STRING_KEY = "specialCharactersString";
	/** Default value for PARAMETER_BIND_SPECIAL_CHARACTERS_STRING_KEY */
	String DEFAULT_BIND_SPECIAL_CHARACTERS_STRING = "23;24;40;5B;5C;5D;5E;60;7B;7C;7D;7E";
	/** parameters key for bindSpecialCharactersString */
	String PARAMETER_BIND_SPECIAL_CHARACTERS_STRING_KEY = "bindSpecialCharactersString";
	
	// Imprime et fait avancer 160/144 pouces environ, puis decoupe de la feuille.
	/** POS(point of sale) byte code for printing, feeding paper, and then cutting paper. */
	byte[] PRINT_FEED_PAPER_CUT_SHEET = { (byte) 0x1B, (byte) 0x4A, (byte) 0xF0, (byte) 0x1B, (byte) 0x69 };

	// Initialisation de l'imprimante, format de la police, Caracteres internationnaux : Français pour les accents
	/** POS(point of sale) byte code for initializing printer, formatting police in international charset specially for French accents. */
	byte[] INITIALIZE_PRINTER_INTERNATIONAL_CHARACTER = { (byte) 0x1B, (byte) 0x40, (byte) 0x1B, (byte) 0x52, (byte) 0x01 };

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
	void init();
	
	/**
	 * This method check the value of key applet parameter name and returns it
	 * if this not null else it returns the default value
	 * 
	 * @param key
	 *            applet parameter name
	 * @param defaultValue
	 *            default value if there is no value with key applet parameter
	 *            name
	 * @return the value of key applet parameter name if this is not null else
	 *         the default value
	 */
	String getParameter(String key, String defaultValue);

	void print();

	void stop();

	void reload();

	void resetDataBuffer();

	void addData1(String data);

	void addData2(String data);
	
	void close();
}
