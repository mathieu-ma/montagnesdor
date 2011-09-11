package fr.mch.mdo.applets;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MdoPrinterTest extends JApplet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private JLabel label;

	@Override
	public void init() {

		label = new JLabel("Hello World");
		super.getContentPane().add(label);
	}

	public static void main(String[] args) {

		MdoPrinterTest printer = new MdoPrinterTest();

		printer.init();

		JFrame frame = new JFrame("Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(printer.getContentPane()); // set panel on
																// frame
		frame.setSize(400, 600); // Set the size of the frame
		//frame.pack();
		frame.setVisible(true);
	}
}
