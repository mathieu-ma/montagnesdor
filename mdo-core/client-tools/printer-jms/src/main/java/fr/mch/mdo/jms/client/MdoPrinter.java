package fr.mch.mdo.jms.client;

import javax.swing.JFrame;

public class MdoPrinter
{
	public static void main(String[] args) {
		PrinterModel model = new PrinterModel();
		PrinterView view = new PrinterView();
		
		new PrinterController(model, view);
		
		JFrame frame = new JFrame("Mdo Printer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(view); // set panel on frame
		frame.setSize(600, 600); // Set the size of the frame
		//frame.pack();
		frame.setVisible(true);
	}
}
