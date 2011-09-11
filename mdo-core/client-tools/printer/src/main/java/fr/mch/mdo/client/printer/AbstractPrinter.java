/*
 * AbstractPrinter.java
 *
 * Created on 21 février 2002, 20:57
 */
package fr.mch.mdo.client.printer;

import gnu.io.CommDriver;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * In oder to used this class in Linux OS, we have to give permission for connected user by following these 2 steps:
 * 1) adduser $connectedUser uucp
 * 2) adduser $connectedUser dialout
 * 
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
public abstract class AbstractPrinter implements IPrinter
{
	/** Output charset. */
	private String charset = "UTF-8";
	/** Debugging. */
	protected boolean debug = false;
	/** Communication Driver. */
	private static CommDriver driver = null;
	/** Communication port. */
	private static CommPortIdentifier portId = null;
	/** Serial Port Bauds. */
	private static int serialportBauds = 9600;
	/** Serial Port Bits. */
	private static int serialportBits = 8;
	/** Serial Port Stop Bits. */
	private static int serialportStopBits = 1;
	/** Serial Port Parity. */
	private static int serialportParity = 0;
	/**
	 * public static final int DATABITS_5 = 5; public static final int
	 * DATABITS_6 = 6; public static final int DATABITS_7 = 7; public static
	 * final int DATABITS_8 = 8; public static final int STOPBITS_1 = 1; public
	 * static final int STOPBITS_2 = 2; public static final int STOPBITS_1_5 =
	 * 3; public static final int PARITY_NONE = 0; public static final int
	 * PARITY_ODD = 1; public static final int PARITY_EVEN = 2; public static
	 * final int PARITY_MARK = 3; public static final int PARITY_SPACE = 4;
	 */
	private static HashMap<String, String> specialCharacters = null;

	/** Number of chars for each data packet. */
	private static int packet = 50;
	/** Pause in millisecond. */
	private static int pause = 1500;
	/** Data container to be printed. */
	private StringBuffer dataBuffer = new StringBuffer();
	/** Printer loaded. */
	private boolean loaded = false;
	/** Printer. */
	private Printer printer = null;

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

		String os = System.getProperty("os.name");
		
		if (!loaded) {
			debug = Boolean.TRUE.toString().equalsIgnoreCase(this.getParameter(IPrinter.PARAMETER_DEBUG_KEY, Boolean.TRUE.toString()));

			charset = this.getParameter(IPrinter.PARAMETER_CHARSET_KEY, IPrinter.DEFAULT_CHARSET);
			String specialCharactersString = this.getParameter(IPrinter.PARAMETER_SPECIAL_CHARACTERS_STRING_KEY, IPrinter.DEFAULT_SPECIAL_CHARACTERS_STRING);
			StringTokenizer specialCharactersStk = new StringTokenizer(specialCharactersString, ";");
			String bindSpecialCharactersString = this.getParameter(IPrinter.PARAMETER_BIND_SPECIAL_CHARACTERS_STRING_KEY, IPrinter.DEFAULT_BIND_SPECIAL_CHARACTERS_STRING);
			StringTokenizer bindSpecialCharactersStk = new StringTokenizer(bindSpecialCharactersString, ";");

			if (specialCharactersStk != null && bindSpecialCharactersStk != null) {
				specialCharacters = new HashMap<String, String>(specialCharactersStk.countTokens());
				if (specialCharactersStk.countTokens() <= bindSpecialCharactersStk.countTokens()) {
					while (specialCharactersStk.hasMoreTokens()) {
						specialCharacters.put(specialCharactersStk.nextToken(), bindSpecialCharactersStk.nextToken());
					}
				}
			}

			String driverName = IPrinter.DEFAULT_WINDOWS_DRIVER_NAME;
			String portCom = IPrinter.DEFAULT_WINDOWS_PORT_COM;
			try {
				if (os.toLowerCase().matches("linux")) {
					driverName = this.getParameter(IPrinter.PARAMETER_LINUX_DRIVER_NAME_KEY, IPrinter.DEFAULT_LINUX_DRIVER_NAME);
					portCom = this.getParameter(IPrinter.PARAMETER_LINUX_PORT_COM_KEY, IPrinter.DEFAULT_LINUX_PORT_COM);
				} else {
					driverName = this.getParameter(IPrinter.PARAMETER_WINDOWS_DRIVER_NAME_KEY, IPrinter.DEFAULT_WINDOWS_DRIVER_NAME);
					portCom = this.getParameter(IPrinter.PARAMETER_WINDOWS_PORT_COM_KEY, IPrinter.DEFAULT_WINDOWS_PORT_COM);
				}
				String serialportBaudsString = this.getParameter(IPrinter.PARAMETER_SERIAL_PORT_BAUDS_KEY, IPrinter.DEFAULT_SERIAL_PORT_BAUDS);
				serialportBauds = Integer.parseInt(serialportBaudsString);
				String serialportBitsString = this.getParameter(IPrinter.PARAMETER_SERIAL_PORT_BITS_KEY, IPrinter.DEFAULT_SERIAL_PORT_BITS);
				serialportBits = Integer.parseInt(serialportBitsString);
				String serialportStopBitsString = this.getParameter(IPrinter.PARAMETER_SERIAL_PORT_STOP_BITS_KEY, IPrinter.DEFAULT_SERIAL_PORT_STOP_BITS);
				serialportStopBits = Integer.parseInt(serialportStopBitsString);
				String serialportParityString = this.getParameter(IPrinter.PARAMETER_SERIAL_PORT_PARITY_KEY, IPrinter.DEFAULT_SERIAL_PORT_PARITY);
				serialportParity = Integer.parseInt(serialportParityString);
				String packetString = this.getParameter(IPrinter.PARAMETER_PACKET_KEY, IPrinter.DEFAULT_PACKET);
				packet = Integer.parseInt(packetString);
				String pauseString = this.getParameter(IPrinter.PARAMETER_PAUSE_KEY, IPrinter.DEFAULT_PAUSE);
				pause = Integer.parseInt(pauseString);

			} catch (Exception e) {
				// debug = true;
				//System.out.println("Erreur de récupération des paramètres de l'imprimante série");
				System.out.println("Error in getting printer parameters.");
				e.printStackTrace();
			}
			/*
			 * if (debug) {
			 * System.out.println("Initialisation de l'applet ImpressionApplet : "
			 * ); System.out.println("driver.name : " + driverName);
			 * System.out.println("imprimante.portcom : " + portCom);
			 * System.out.println("serialport.bauds : " + serialportBauds);
			 * System.out.println("serialport.bits : " + serialportBits);
			 * System.out.println("serialport.stopbits : " +
			 * serialportStopBits); System.out.println("serialport.parity : " +
			 * serialportParity); System.out.println("packet : " + packet);
			 * System.out.println("pause : " + pause);
			 * System.out.println("imprimante.caracteresSpeciaux : " +
			 * specialCharactersString);
			 * System.out.println("imprimante.bindCaracteresSpeciaux : " +
			 * bindCaracteresSpeciauxStr); }
			 */
			try {
				driver = (CommDriver) Class.forName(driverName).newInstance();
				driver.initialize();
			} catch (Exception e) {
				// debug = true;
				//System.out.println("Erreur d'instanciation du driver");
				System.out.println("Error instanciation driver.");
				e.printStackTrace();
			}

			boolean flagNoPortComFound = false;
			try {
				portId = CommPortIdentifier.getPortIdentifier(portCom);
				if (debug) {
					//System.out.println("Identifiant du port de communication trouvé sur " + portCom);
					System.out.println("Identifier port communication found " + portCom);
				}
			} catch (Exception e) {
				flagNoPortComFound = true;
				//System.out.println("Erreur de récupération de l'identifiant du port de communication " + portCom + " : premier essai");
				System.out.println("Error getting identifier port communication " + portCom + " : fisrt try.");
				e.printStackTrace();
			}
			int attempt = 0;
			if (flagNoPortComFound) {
				Enumeration<?> enumeration = CommPortIdentifier.getPortIdentifiers();
				if (enumeration != null) {
					while (enumeration.hasMoreElements()) {
						CommPortIdentifier searchedPortId = (CommPortIdentifier) enumeration.nextElement();
						if (searchedPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
							attempt++;
							if (!searchedPortId.isCurrentlyOwned()) {
								portId = searchedPortId;
								flagNoPortComFound = false;
								if (debug) {
									//System.out.println("Identifiant du port de communication trouvé sur " + portCom);
									System.out.println("Identifier port communication found " + portId.getName() + " after " + attempt + " tries");
								}
								break;
							}
						}
					}
				}
			}
			if (flagNoPortComFound) {
				//System.out.println("Impossible de récupérer de l'identifiant du port série de communication après " + attempt + " tentative(s)");
				System.out.println("Unable to get identifier port communication " + portCom + " after " + attempt + " tries.");
			}
			printer = new Printer();
			loaded = true;
		}
	}

	@Override
	public void reload() {
		loaded = false;
		init();
	}
	
	/**
	 * @return the printer
	 */
	public Printer getPrinter() {
		return printer;
	}

	/**
	 * @param printer the printer to set
	 */
	public void setPrinter(Printer printer) {
		this.printer = printer;
	}

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
	public abstract String getParameter(String key, String defaultValue);

	private byte[] getLine(String line, int size) {
		byte[] result = null;
		StringBuffer strBuff = new StringBuffer();

		if (size == 2) {
			// Format de la police en gras et avec une taille double en largeur et en longueur
			// POS(point of sale) byte code for font bold, font size double. 
			strBuff.append((char) 0x1B);
			strBuff.append((char) 0x21);
			strBuff.append((char) 0x38);
		}

		if (line.equals(""))
			line += " ";

		strBuff.append(bindSpecialCharacters(line));

		if (size == 2) {
			// Format de la police par défaut
			strBuff.append((char) 0x1B);
			strBuff.append((char) 0x21);
			strBuff.append((char) 0x01);
		}

		// Saut de ligne
		strBuff.append((char) 0x0A);

		try {
			result = strBuff.toString().getBytes(charset);
		} catch (UnsupportedEncodingException uee) {
			result = strBuff.toString().getBytes();
		}
		result = strBuff.toString().getBytes();

		return result;
	}

	public void print() {
		// Initialisation imprimante
		dataBuffer.insert(0, new String(INITIALIZE_PRINTER_INTERNATIONAL_CHARACTER));
		// dataBuffer.append(new String(PRINT_FEED_PAPER_CUT_SHEET));

		if (debug) {
			System.out.println("Data dans le buffer avant envoie vers imprimante : \n" + dataBuffer.toString());
		}

		byte[] dataBytes = null;
		try {
			dataBytes = dataBuffer.toString().getBytes(charset);
		} catch (UnsupportedEncodingException uee) {
			if (debug) {
				System.out.println("Charset impossible d'encoder : " + charset);
			}
			dataBytes = dataBuffer.toString().getBytes();
		}

		byte[] dataPlusCutPaper = new byte[dataBytes.length + IPrinter.PRINT_FEED_PAPER_CUT_SHEET.length];
		System.arraycopy(dataBytes, 0, dataPlusCutPaper, 0, dataBytes.length);
		System.arraycopy(PRINT_FEED_PAPER_CUT_SHEET, 0, dataPlusCutPaper, dataBytes.length, IPrinter.PRINT_FEED_PAPER_CUT_SHEET.length);

		ThreadPrinter tp = new ThreadPrinter(printer, dataPlusCutPaper);
		dataBuffer = new StringBuffer();
		tp.start();
	}

	@Override
	public void stop() {
		if (printer != null && !printer.isStop()) {
			printer.setStop(true);
		}
	}
	
	public void resetDataBuffer() {
		dataBuffer = new StringBuffer();
	}

	public void addData1(String data) {
		dataBuffer.append(new String(getLine(data, 1)));
	}

	public void addData2(String data) {
		dataBuffer.append(new String(getLine(data, 2)));
	}

	private String bindSpecialCharacters(String data) {
		Object[] specialCharactersArray = specialCharacters.keySet().toArray();
		for (int i = 0; i < specialCharactersArray.length; i++) {
			data = data.replace(((String) specialCharactersArray[i]).charAt(0), (char) Integer.parseInt((String) specialCharacters.get(specialCharactersArray[i]), 16));
		}

		return data;
	}

	public void close() {
		if (printer != null) {
			printer.closeSerialPort();
		}
	}
	
	class Printer
	{
		private boolean stop = false;
		
		private SerialPort serialport = null;

		public Printer() {
		}

		/**
		 * @param stop the stop to set
		 */
		public void setStop(boolean stop) {
			this.stop = stop;
		}

		/**
		 * @return the stop
		 */
		public boolean isStop() {
			return stop;
		}

		private boolean openSerialPort() {
			boolean result = false;
			if (portId != null && serialport == null) {
				try {
					if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
						serialport = (SerialPort) portId.open("montagnesdor", 2 * pause);
						serialport.setSerialPortParams(serialportBauds, serialportBits, serialportStopBits, serialportParity);
						serialport.setDTR(true);
						result = true;
					}
				} catch (Exception e) {
					System.out.println("Could Not Get Serial Port " + e);
					e.printStackTrace();
				}
			}
			return result;
		}

		public void closeSerialPort() {
			try {
				serialport.close();
			} catch (Exception e) {
				System.out.println("Could Not Close Serial Port " + e);
				e.printStackTrace();
			}
			serialport = null;
			try {
				Thread.sleep(2 * pause);
			} catch (Exception e) {
				System.out.println("Impossible de faire la pause de " + (2 * pause) + "\n" + e);
			}
		}

		public synchronized void printData(byte[] data) {
			if (openSerialPort()) {
				stop = false;
				BufferedOutputStream out = null;
				try {
					out = new BufferedOutputStream(serialport.getOutputStream());
					int lastLength = data.length % packet;
					int indexDataBegin = 0;
					while (indexDataBegin < data.length) {
						if (serialport.isDSR()) {
							if (indexDataBegin + packet <= data.length) {
								out.write(data, indexDataBegin, packet);
								if (debug) {
									System.out.println("Data sent to printer: \n" + new String(data, indexDataBegin, packet));
								}
							} else {
								out.write(data, indexDataBegin, lastLength);
								if (debug) {
									System.out.println("Data sent to printer: \n" + new String(data, indexDataBegin, lastLength));
								}
							}
							out.flush();
							try {
								Thread.sleep(pause / 5);
							} catch (Exception e) {
								System.out.println("Impossible de faire la pause de " + (pause / 5) + " " + e);
							}
							indexDataBegin += packet;
						} else {
							System.out.println("Imprimante occupée !!! ");
							try {
								Thread.sleep(pause);
							} catch (Exception e) {
								System.out.println("Impossible de faire la pause de " + (pause / 5) + " " + e);
							}
						}
						if (stop) {
							System.out.println("Stop printing by user");
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {

						out.close();
					} catch (Exception e) {
						System.out.println("Could not close the printer output " + e);
					}
					closeSerialPort();
				}
			}
		}
	}

	class ThreadPrinter extends Thread
	{
		Printer printer = null;
		byte[] data = null;

		public ThreadPrinter(Printer printer, byte[] data) {
			this.printer = printer;
			this.data = data;
		}

		public void run() {
			printer.printData(data);
		}
	}
}
