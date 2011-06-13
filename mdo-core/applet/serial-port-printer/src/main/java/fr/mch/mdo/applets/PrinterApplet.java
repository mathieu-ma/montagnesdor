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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * 
 * @author maxime
 * @version
 */
public class PrinterApplet extends java.applet.Applet
{
	/**
     * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;

	// Imprime et fait avancer 160/144 pouces environ, puis decoupe de la feuille.
	/** POS(point of sale) byte code for printing, feeding paper, and then cutting paper. */
	public static final byte[] PRINT_FEED_PAPER_CUT_SHEET = { (byte) 0x1B, (byte) 0x4A, (byte) 0xF0, (byte) 0x1B, (byte) 0x69 };

	// Initialisation de l'imprimante, format de la police, Caracteres internationnaux : Français pour les accents
	/** POS(point of sale) byte code for initializing printer, formatting police in international charset specially for French accents. */
	public static final byte[] INITIALIZE_PRINTER_INTERNATIONAL_CHARACTER = { (byte) 0x1B, (byte) 0x40, (byte) 0x1B, (byte) 0x52, (byte) 0x01 };

	/** Output charset. */
	private String charset = "UTF-8";
	/** Debugging. */
	private boolean debug = false;
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

	/** Printer. */
	private static Printer printer = null;
	/** Number of chars for each data packet. */
	private static int packet = 50;
	/** Pause in millisecond. */
	private static int pause = 1500;
	/** Data container to be printed. */
	private StringBuffer dataBuffer = new StringBuffer();

	/**
	 * Initialization method that will be called after the applet is loaded into
	 * the browser. In the java.policy file we MUST be updated with the
	 * following lines: permission java.lang.RuntimePermission
	 * "loadLibrary.rxtxSerial"; permission java.io.FilePermission
	 * "C:/Program Files/Java/jre6/lib/ext/x86/rxtxSerial.dll", "read";
	 * permission java.io.FilePermission
	 * "C:/Program Files/Java/jre6/lib/ext/rxtxSerial.dll", "read"; permission
	 * java.util.PropertyPermission "gnu.io.log.mode", "read";
	 */
	public void init() {
		super.init();

		String os = System.getProperty("os.name");
		if (driver == null) {
			if (getParameter("charset") != null) {
				charset = getParameter("charset");
			}
			String specialCharactersString = "#;$;à;°;ç;§;^;`;é;ù;è;¨";
			if (getParameter("specialCharactersString") != null) {
				specialCharactersString = getParameter("specialCharactersString");
			}
			StringTokenizer specialCharactersStk = new StringTokenizer(specialCharactersString, ";");
			String bindCaracteresSpeciauxString = "23;24;40;5B;5C;5D;5E;60;7B;7C;7D;7E";
			if (getParameter("bindCaracteresSpeciauxStr") != null) {
				bindCaracteresSpeciauxString = getParameter("bindCaracteresSpeciauxString");
			}
			StringTokenizer bindCaracteresSpeciauxStk = new StringTokenizer(bindCaracteresSpeciauxString, ";");

			if (specialCharactersStk != null && bindCaracteresSpeciauxStk != null) {
				specialCharacters = new HashMap<String, String>(specialCharactersStk.countTokens());
				if (specialCharactersStk.countTokens() <= bindCaracteresSpeciauxStk.countTokens()) {
					while (specialCharactersStk.hasMoreTokens()) {
						specialCharacters.put(specialCharactersStk.nextToken(), bindCaracteresSpeciauxStk.nextToken());
					}
				}
			}

			String driverName = "com.sun.comm.Win32Driver";
			String portCom = "COM1";
			try {
				if (os.toLowerCase().matches("linux")) {
					if (getParameter("linuxDriverName") != null) {
						driverName = getParameter("linuxDriverName");
					}
					if (getParameter("linuxPortCom") != null) {
						portCom = getParameter("linuxPortCom");
					}
				} else {
					if (getParameter("windowsDriverName") != null) {
						driverName = getParameter("windowsDriverName");
					}
					if (getParameter("windowsPortCom") != null) {
						portCom = getParameter("windowsPortCom");
					}
				}
				if (getParameter("serialportBauds") != null) {
					serialportBauds = Integer.parseInt(getParameter("serialportBauds"));
				}
				if (getParameter("serialportBits") != null) {
					serialportBits = Integer.parseInt(getParameter("serialportBits"));
				}
				if (getParameter("serialportStopBits") != null) {
					serialportStopBits = Integer.parseInt(getParameter("serialportStopBits"));
				}
				if (getParameter("serialportParity") != null) {
					serialportParity = Integer.parseInt(getParameter("serialportParity"));
				}
				if (getParameter("packet") != null) {
					packet = Integer.parseInt(getParameter("packet"));
				}
				if (getParameter("pause") != null) {
					pause = Integer.parseInt(getParameter("pause"));
				}

				String debugStr = getParameter("debug");
				debug = (debugStr != null && debugStr.equals("true"));
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
									System.out.println("Identifier port communication found " + portCom);
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
		}
	}

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

		byte[] dataPlusCutPaper = new byte[dataBytes.length + PrinterApplet.PRINT_FEED_PAPER_CUT_SHEET.length];
		System.arraycopy(dataBytes, 0, dataPlusCutPaper, 0, dataBytes.length);
		System.arraycopy(PRINT_FEED_PAPER_CUT_SHEET, 0, dataPlusCutPaper, dataBytes.length, PrinterApplet.PRINT_FEED_PAPER_CUT_SHEET.length);

		ThreadPrinter tp = new ThreadPrinter(printer, dataPlusCutPaper);
		dataBuffer = new StringBuffer();
		tp.start();
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

	class Printer
	{
		private SerialPort serialport = null;

		public Printer() {
		}

		private void openSerialPort() {
			if (portId != null && serialport == null) {
				try {
					if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
						serialport = (SerialPort) portId.open("montagnesdor", 2 * pause);

						serialport.setSerialPortParams(serialportBauds, serialportBits, serialportStopBits, serialportParity);
					}
				} catch (Exception e) {
					System.out.println("Could Not Get Serial Port " + e);
					e.printStackTrace();
				}
				serialport.setDTR(true);
			}
		}

		private void closeSerialPort() {
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
			openSerialPort();
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

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					out.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			closeSerialPort();
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
