package fr.mch.mdo.applets.tools;

import gnu.io.CommDriver;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/**
 * In oder to used this class in Linux OS, we have to give permission for connected user by following these 2 steps:
 * 1) adduser $connectedUser uucp
 * 2) adduser $connectedUser dialout
 *
 * Warnings:
 * I) On linux Ubuntu 2.6.38-11 and RXTX 2.2-pre2, 
 * 		if we want to receive data from serial port terminal, 
 * 		NEVER set the event serialPort.notifyOnOutputEmpty at true in method PrinterSimulator.openSerialPort. 
 * II) In order to run this class, we have 2 choices depending on how you are going to run it:
 * 		1) If you run it in command line then add the system property java.library.path and point to the right folder.
 * 		2) If you run it in eclipse, 
 * 			2.1) Open the "Package Explorer", 
 * 			2.2) "Alt + Enter" on the rxtx jar file(in "Referenced Libraries"), 
 * 			2.3) Enter into "Native Lirary" menu,
 * 			2.4) Point to the right location path.
 * 
 * @author user
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class PrinterSimulator extends JFrame
{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(PrinterSimulator.class.getName());

	private static ResourceBundle resource;
	private static String driverName;
	private static CommPortIdentifier portId = null;

	static {
		try {
			resource = ResourceBundle.getBundle(PrinterSimulator.class.getName());
			driverName = resource.getString("drivername");

			PatternLayout patternLayout1 = new PatternLayout(resource.getString("log4j.appender.A1.layout.ConversionPattern"));
			ConsoleAppender consoleAppender = new ConsoleAppender(patternLayout1);
			logger.addAppender(consoleAppender);

			boolean flag = resource.getString("log4j.appender.A2.Append") == null ? false : resource.getString("log4j.appender.A2.Append").equals("true");
			String file = resource.getString("log4j.appender.A2.FileName");
			PatternLayout patternLayout2 = new PatternLayout(resource.getString("log4j.appender.A2.layout.ConversionPattern"));
			RollingFileAppender rollingFileAppender = new RollingFileAppender(patternLayout2, file, flag);
			rollingFileAppender.setName(resource.getString("log4j.appender.A2.Name"));
			rollingFileAppender.setMaxFileSize(resource.getString("log4j.appender.A2.MaxFileSize"));
			rollingFileAppender.setMaxBackupIndex(Integer.parseInt(resource.getString("log4j.appender.A2.MaxBackupIndex")));
			logger.addAppender(rollingFileAppender);
			
			logger.setLevel(Level.toLevel((resource.getString("log4j.rootLogger.level"))));
		} catch (Exception e) {
			System.err.println("Error loading Log4J properties : " + e);
		}

		try {
//			System.load("/home/mathieu/development/workspace/mdo-core/applet/serial-port-printer/src/main/resources/rxtx/lib64/linux/librxtxSerial.so");
//			System.load("/home/mathieu/development/workspace/mdo-core/applet/serial-port-printer/src/main/resources/rxtx/lib64/linux/librxtxParallel.so");
			System.setProperty("java.library.path", "/home/mathieu/development/workspace/mdo-core/applet/serial-port-printer/src/main/resources/rxtx/lib64/linux/");
//			System.loadLibrary("rxtxSerial");
			CommDriver driver = (CommDriver) Class.forName(driverName).newInstance();
			driver.initialize();
		} catch (Exception e) {
			logger.fatal("Enable to load Serial Port Printer driver " + e);
		}
	}

	private SerialPort serialPort = null;

	private JTextArea dataToSendArea = new JTextArea(10, 50);
	private JTextArea dataRecievedArea = new JTextArea(20, 100);
	private JButton DSRRecieved = new JButton("DSR recieved");

	public PrinterSimulator() {
		PrinterActionListener printerActionListener = new PrinterActionListener();
		if (portId == null) {
			logger.info("DEBUT portId bloc static " + portId);
			try {

				boolean flagNoPortComFound = false;
				String portCom = resource.getString("imprimante.portcom");
				try {
					portId = CommPortIdentifier.getPortIdentifier(portCom);
					//logger.info("Identifiant du port de communication trouvé sur " + portId.getName());
					logger.info("Communication Port Identifier found on " + portId.getName());
				} catch (Exception e) {
					flagNoPortComFound = true;
					logger.error("Erreur de récupération de l'identifiant du port de communication " + portCom + " : premier essai");
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
									//logger.info("Identifiant du port de communication trouvé sur " + portId.getName());
									logger.info("Communication Port Identifier found on " + portId.getName());
									break;
								}
							}
						}
					}
				}
				if (flagNoPortComFound) {
					//logger.info("Impossible de recuperer de l'identifiant du port série de communication après " + attempt + " tentative(s)");
					logger.info("Could not find any serial port communication after " + attempt + " try(ies)");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("FIN portId bloc static " + portId);
		}
		// this.setSize(50, 50);
		this.addWindowListener(new PrinterWindowListener());
		this.getContentPane().setLayout(new GridLayout(2, 1, 1, 1));
		// this.getContentPane().setLayout(new BorderLayout());
		// this.getContentPane().setLayout(new FlowLayout());

		JPanel libelleToSendPanel = new JPanel(new BorderLayout());
		JLabel libelleToSend = new JLabel("Data To Be Sent");
		libelleToSendPanel.add(libelleToSend, BorderLayout.NORTH);
		dataToSendArea.setLineWrap(true);
		dataToSendArea.setWrapStyleWord(true);
		JScrollPane dataToSendAreaScroll = new JScrollPane(dataToSendArea);
		libelleToSendPanel.add(dataToSendAreaScroll, BorderLayout.CENTER);
		JPanel DTRToSendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton DTRToSendT = new JButton("DTR TRUE à envoyer");
		DTRToSendT.addActionListener(printerActionListener);
		DTRToSendT.setActionCommand("DTRT");
		DTRToSendPanel.add(DTRToSendT);
		JButton DTRToSendF = new JButton("DTR FALSE à envoyer");
		DTRToSendF.addActionListener(printerActionListener);
		DTRToSendF.setActionCommand("DTRF");
		DTRToSendPanel.add(DTRToSendF);
		JButton dataToSend = new JButton("Data To Be Sent");
		dataToSend.addActionListener(printerActionListener);
		dataToSend.setActionCommand("DATA");
		DTRToSendPanel.add(dataToSend);
		JButton openSerialPort = new JButton("Open Serial Port");
		openSerialPort.addActionListener(printerActionListener);
		openSerialPort.setActionCommand("OPEN_SERIAL");
		DTRToSendPanel.add(openSerialPort);
		JButton closeSerialPort = new JButton("Close Serial Port");
		closeSerialPort.addActionListener(printerActionListener);
		closeSerialPort.setActionCommand("CLOSE_SERIAL");
		DTRToSendPanel.add(closeSerialPort);
		libelleToSendPanel.add(DTRToSendPanel, BorderLayout.SOUTH);
		// this.getContentPane().add(libelleToSendPanel, BorderLayout.NORTH);
		this.getContentPane().add(libelleToSendPanel);

		JPanel libelleRecievedPanel = new JPanel(new BorderLayout());
		JLabel libelleRecieved = new JLabel("Données reçues");
		libelleRecievedPanel.add(libelleRecieved, BorderLayout.NORTH);
		dataRecievedArea.setLineWrap(true);
		dataRecievedArea.setWrapStyleWord(true);
		// dataRecievedArea.setSize(200, 400);
		JScrollPane dataRecievedAreaScroll = new JScrollPane(dataRecievedArea);
		libelleRecievedPanel.add(dataRecievedAreaScroll, BorderLayout.CENTER);
		JPanel DSRRecievedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		DSRRecievedPanel.add(DSRRecieved);
		JButton removeButton = new JButton("Effacer");
		removeButton.addActionListener(printerActionListener);
		removeButton.setActionCommand("removeButton");
		DSRRecievedPanel.add(removeButton);
		libelleRecievedPanel.add(DSRRecievedPanel, BorderLayout.SOUTH);
		// this.getContentPane().add(libelleRecievedPanel, BorderLayout.SOUTH);
		this.getContentPane().add(libelleRecievedPanel);

		DSRRecieved.setBackground(Color.RED);

		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				// JFrame tmp = (JFrame)e.getSource();
				// tmp.pack();
			}
		});
		this.pack();
		this.validate();
		this.setVisible(true);

		openSerialPort();
		if (serialPort.isDSR())
			DSRRecieved.setBackground(Color.GREEN);
	}

	public PrinterSimulator(String title) {
		this();
		this.setTitle(title);
	}

	public void openSerialPort() {
		if (portId != null) {
			try {
				if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					serialPort = (SerialPort) portId.open("montagnesdor", Integer.parseInt(resource.getString("pause")) * 2);
					serialPort.setSerialPortParams(Integer.parseInt(resource.getString("serialportBauds")), Integer.parseInt(resource.getString("serialportBits")),
							Integer.parseInt(resource.getString("serialportStopBits")), Integer.parseInt(resource.getString("serialportParity")));
					
					serialPort.addEventListener(new PrinterSerialPortEventListener());
					serialPort.setOutputBufferSize(20);
					// Set notifyOnDataAvailable to true to allow event driven
					// input.
					serialPort.notifyOnDataAvailable(true);
					// Don't use this event serialPort.notifyOnOutputEmpty if you want to receive data from serial port terminal 
					// only on linux Ubuntu 2.6.38-11 and RXTX 2.2-pre2
					//serialPort.notifyOnOutputEmpty(true);
					serialPort.notifyOnDSR(true);
					serialPort.notifyOnBreakInterrupt(true);
					serialPort.notifyOnCarrierDetect(true);
					serialPort.notifyOnCTS(true);
					serialPort.notifyOnOverrunError(true);
					serialPort.notifyOnParityError(true);
					serialPort.notifyOnRingIndicator(true);
					serialPort.notifyOnFramingError(true);

					// serialPort.setDTR(true);
					logger.info("Time in milliseconds to block waiting for port open: " + Integer.parseInt(resource.getString("pause")) * 2);
					logger.info("serialportBauds: " + Integer.parseInt(resource.getString("serialportBauds")));
					logger.info("serialportBits: " + Integer.parseInt(resource.getString("serialportBits")));
					logger.info("serialportStopBits: " + Integer.parseInt(resource.getString("serialportStopBits")));
					logger.info("serialportParity: " + Integer.parseInt(resource.getString("serialportParity")));
				}
			} catch (Exception e) {
				logger.fatal("Impossible to get back the Serial Port " + e);
			}
		}
		logger.info("Serial Port opened " + serialPort);
		// return serialPort;
	}

	private String printDataInHexa(byte[] data) {
		// Hexa Text + String Text
		StringBuffer text = new StringBuffer("");
		for (int i = 0; i < data.length; i++) {
			/* Wrap any control characters */
			if (Character.isISOControl((char) data[i]) && !Character.isWhitespace((char) data[i])) {
				text.append("<0x");
				text.append((data[i] < 16 ? "0" : "") + Integer.toHexString(data[i]).toUpperCase());
				text.append(">");
			} else {
				text.append((char) data[i]);
			}
		}
		// Only Hexa Text
		StringBuffer hexaText = new StringBuffer("");
		for (int i = 0; i < data.length; i++) {
			hexaText.append("<0x");
			hexaText.append((data[i] < 16 ? "0" : "") + Integer.toHexString(data[i]).toUpperCase());
			hexaText.append(">");
		}
		logger.info("Données traitées : " + text.toString());
		logger.info("Données traitées en hexa : " + hexaText.toString());

		return text.toString();
	}


	/**
	 * In order to run this class, we have 2 choices depending on how you are going to run it:
	 * 1) If you run it in command line then add the system property java.library.path and point to the right folder.
	 * 2) If you run it in eclipse, 
	 * 		2.1) Open the "Package Explorer", 
	 * 		2.2) "Alt + Enter" on the rxtx jar file(in "Referenced Libraries"), 
	 * 		2.3) Enter into "Native Lirary" menu,
	 * 		2.4) Point to the right location path.
	 * @param args
	 */
	public static void main(String[] args) {
		// This is used to prevent null pointer exception for logging, this maybe not need in next rxtx release.
		System.setProperty("gnu.io.log.mode", "PRINT_MODE");

		new PrinterSimulator("Printer Simulator for Serial Port");
	}
	
	private class PrinterWindowListener implements WindowListener {
		/**
		 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
		 */
		public void windowActivated(WindowEvent e) {
		}

		/**
		 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
		 */
		public void windowClosed(WindowEvent e) {
		}

		/**
		 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
		 */
		public void windowClosing(WindowEvent event) {
			logger.info("Close Printer Window !!!");
			try {
				serialPort.close();
			} catch (Exception e) {
				logger.debug("Could not Close Serial Port");
			}
			System.exit(0);
		}

		/**
		 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
		 */
		public void windowDeactivated(WindowEvent e) {
		}

		/**
		 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
		 */
		public void windowDeiconified(WindowEvent e) {
		}

		/**
		 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
		 */
		public void windowIconified(WindowEvent e) {
		}

		/**
		 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
		 */
		public void windowOpened(WindowEvent e) {
		}
	}
	
	private class PrinterActionListener implements ActionListener {
		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("DTRT")) {
				// if(serialPort.isDTR())
				// serialPort.setDTR(false);
				// else
				serialPort.setDTR(true);
				logger.info("DTR envoyé TRUE");
			}
			if (e.getActionCommand().equals("DTRF")) {
				// if(serialPort.isDTR())
				// serialPort.setDTR(false);
				// else
				serialPort.setDTR(false);
				logger.info("DTR envoyé FALSE");
			}

			if (e.getActionCommand().equals("removeButton")) {
				dataRecievedArea.setText("");
				logger.info("removeButton");
			}
			if (e.getActionCommand().equals("DATA")) {
				String donnees = dataToSendArea.getText().trim();
				logger.info("Données à envoyer : " + donnees);
				logger.info("Taille du buffer " + serialPort.getOutputBufferSize());
				logger.info("Etat Modem " + serialPort.isDSR());
				logger.info("Etat Ordinateur " + serialPort.isDTR());
				byte[] dataToBeSent = donnees.getBytes();
				if (!donnees.equals("")) {
					try {
//						if (serialPort.isDSR()) {
							logger.info("Données envoyées : " + donnees);
							printDataInHexa(dataToBeSent);
							OutputStream os = serialPort.getOutputStream();
							os.write(dataToBeSent);
							os.flush();
							os.close();
//						} else {
//							logger.info("DTE occupée");
//						}
					} catch (Exception ex) {
						logger.info("Erreur d'envoie de Données", ex);
					}
				}
				serialPort.setDTR(true);
			}
			if (e.getActionCommand().equals("OPEN_SERIAL")) {
				openSerialPort();
			}
			if (e.getActionCommand().equals("CLOSE_SERIAL")) {
				serialPort.close();
				logger.info("Close Serial Port");
			}
		}
	}
	
	private class PrinterSerialPortEventListener implements SerialPortEventListener {
		@Override
		public void serialEvent(SerialPortEvent spEvent) {
			logger.info("Evénement du port série " + spEvent);
			switch (spEvent.getEventType()) {

			case SerialPortEvent.BI:
				logger.info("serialPort.notifyOnBreakInterrupt(true) SerialPortEvent.BI : " + SerialPortEvent.BI);
				break;

			case SerialPortEvent.OE:
				logger.info("serialPort.notifyOnOverrunError(true) SerialPortEvent.OE : " + SerialPortEvent.OE);
				break;

			case SerialPortEvent.FE:
				logger.info("serialPort.notifyOnFramingError(true) SerialPortEvent.FE : " + SerialPortEvent.FE);
				break;

			case SerialPortEvent.PE:
				logger.info("serialPort.notifyOnParityError(true) SerialPortEvent.PE : " + SerialPortEvent.PE);
				break;

			case SerialPortEvent.CD:
				logger.info("serialPort.notifyOnCarrierDetect(true) SerialPortEvent.CD : " + SerialPortEvent.CD);
				break;

			case SerialPortEvent.CTS:
				logger.info("serialPort.notifyOnCTS(true) SerialPortEvent.CTS : " + SerialPortEvent.CTS);
				break;

			case SerialPortEvent.DSR:
				logger.info("serialPort.notifyOnDSR(true) SerialPortEvent.DSR : " + SerialPortEvent.DSR);
				if (serialPort.isDSR())
					DSRRecieved.setBackground(Color.GREEN);
				else {
					DSRRecieved.setBackground(Color.RED);
					// serialPort.setDTR(true);
				}
				break;

			case SerialPortEvent.RI:
				logger.info("serialPort.notifyOnRingIndicator(true) SerialPortEvent.RI : " + SerialPortEvent.RI);
				break;

			case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
				logger.info("SerialPortEvent.OUTPUT_BUFFER_EMPTY : " + SerialPortEvent.OUTPUT_BUFFER_EMPTY + " : taille du buffer " + serialPort.getOutputBufferSize());

				break;

			case SerialPortEvent.DATA_AVAILABLE:
				logger.info("serialPort.notifyOnDataAvailable(true) SerialPortEvent.DATA_AVAILABLE : " + SerialPortEvent.DATA_AVAILABLE);
				readSerial();
				break;

			}
		}
	}
	
	private void readSerial() {
		
		try {
			if (serialPort.getInputStream().available() > 0) {
				BufferedInputStream bis = new BufferedInputStream(serialPort.getInputStream());
				serialPort.setDTR(false);
				byte[] data = new byte[bis.available()];
				logger.info("Taille des Données reçues " + bis.available());
				bis.read(data);
	
				logger.info("Données réelles reçues: " + new String(data));
	
				dataRecievedArea.append(printDataInHexa(data));
				serialPort.setDTR(true);
			}
		} catch (Exception e) {
		}
	}
}
