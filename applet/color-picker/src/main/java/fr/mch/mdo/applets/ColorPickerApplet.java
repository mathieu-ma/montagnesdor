package fr.mch.mdo.applets;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public final class ColorPickerApplet extends Applet implements MouseListener,
	MouseMotionListener, KeyListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 2572684428888811676L;

    private ColorRectangleCanvas colorRectangleCanvas;
    private ColorScrollCanvas colorScrollCanvas;
    private TextField hexaColorTextField;
    private Label hexaColorLabel;
    private Canvas colorCanvas;

    private String defaultRGBColor = "C3EBBE"; // "808080";
    private Color defaultColor = Color.WHITE;
    private String defaultLabelRGBColor = "Code en Hexa";
    private String defaultFont = "Arial";

    private void processEvent(MouseEvent mouseevent)
    {
	if (mouseevent.getComponent() instanceof ColorRectangleCanvas)
	{
	    Color color = colorRectangleCanvas.getColorAt(mouseevent.getX(),
		    mouseevent.getY());
	    if (color != null)
	    {
		colorScrollCanvas.refresh(color);
		colorScrollCanvas.repaint();
		color = colorScrollCanvas.getColor();
		if (color != null)
		{
		    updateText(color);
		    colorCanvas.setBackground(color);
		}
	    }
	}
	if (mouseevent.getComponent() instanceof ColorScrollCanvas)
	{
	    Color color = colorScrollCanvas.getColorAt(mouseevent.getX(),
		    mouseevent.getY());
	    updateText(color);
	    colorScrollCanvas.setCursor(mouseevent.getY());
	    colorScrollCanvas.repaint();
	    colorCanvas.setBackground(color);
	}
	if (mouseevent.getComponent().getName().equals("colorCanvas"))
	{
	    updateText(mouseevent.getComponent().getBackground());
	}
	if (mouseevent.getComponent().getName().equals("hexaColorLabel"))
	{
	    colorScrollCanvas.refresh(defaultColor);
	    colorScrollCanvas.repaint();
	    updateText(defaultColor);
	    colorCanvas.setBackground(defaultColor);
	}

	// System.out.println("processEvent : " + hexaColorTextField.getText());

    }

    public void mouseClicked(MouseEvent mouseevent)
    {
	processEvent(mouseevent);
    }

    public void mousePressed(MouseEvent mouseevent)
    {
    }

    public void mouseDragged(MouseEvent mouseevent)
    {
	processEvent(mouseevent);
    }

    public void paint(Graphics g)
    {
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
    }

    public void mouseMoved(MouseEvent mouseevent)
    {
    }

    public final void updateText(Color color)
    {
	String s = Integer.toHexString(color.getRGB() & 0xffffff).toUpperCase();
	s = "000000" + s;
	s = s.substring(s.length() - 6);
	hexaColorTextField.setText(s);
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    private void retrieveParameters()
    {
	if (getParameter("defaultRGBColor") != null)
	    defaultRGBColor = getParameter("defaultRGBColor");
	if (getParameter("defaultLabelRGBColor") != null)
	    defaultLabelRGBColor = getParameter("defaultLabelRGBColor");
	if (getParameter("defaultFont") != null)
	    defaultFont = getParameter("defaultFont");
    }

    public void init()
    {
	retrieveParameters();

	defaultColor = new Color(Integer.parseInt(defaultRGBColor.substring(0,
		2), 16), Integer.parseInt(defaultRGBColor.substring(2, 4), 16),
		Integer.parseInt(defaultRGBColor.substring(4, 6), 16));

	setLayout(new BorderLayout());
	setBackground(Color.white);

	Panel centerPanel = new Panel(new FlowLayout(FlowLayout.CENTER,
		getWidth() * 1 / 100, getHeight() * 1 / 100));
	colorRectangleCanvas = new ColorRectangleCanvas(getWidth() * 83 / 100,
		getHeight() * 83 / 100);
	colorRectangleCanvas.addMouseMotionListener(this);
	colorRectangleCanvas.addMouseListener(this);
	centerPanel.add(colorRectangleCanvas);
	colorScrollCanvas = new ColorScrollCanvas(getWidth() * 7 / 100,
		getHeight() * 83 / 100, defaultColor);
	colorScrollCanvas.addMouseMotionListener(this);
	colorScrollCanvas.addMouseListener(this);
	centerPanel.add(colorScrollCanvas);
	add(centerPanel, BorderLayout.CENTER);

	Panel southPanel = new Panel(new FlowLayout(FlowLayout.CENTER,
		getWidth() * 1 / 100, getHeight() * 1 / 100));
	int fontSize = (int) Math.round(3 * 12.0 * getHeight() * 10 / 100
		/ 72.0);
	hexaColorLabel = new Label(defaultLabelRGBColor);
	hexaColorLabel.setName("hexaColorLabel");
	hexaColorLabel.setSize(getWidth() * 20 / 100, getHeight() * 10 / 100);
	hexaColorLabel.setFont(new Font(defaultFont, Font.PLAIN, fontSize));
	hexaColorLabel.addMouseListener(this);
	southPanel.add(hexaColorLabel);
	hexaColorTextField = new TextField(defaultRGBColor, defaultRGBColor
		.length());
	hexaColorTextField.setName("hexaColorTextField");
	hexaColorTextField.setSize(getWidth() * 20 / 100,
		getHeight() * 10 / 100);
	hexaColorTextField.setFont(new Font(defaultFont, Font.PLAIN, fontSize));
	hexaColorTextField.addKeyListener(this);
	southPanel.add(hexaColorTextField);
	colorCanvas = new Canvas();
	colorCanvas.setName("colorCanvas");
	colorCanvas.setSize(getWidth() * 10 / 100, getHeight() * 10 / 100);
	colorCanvas.setBackground(defaultColor);
	colorCanvas.addMouseListener(this);
	southPanel.add(colorCanvas);
	add(southPanel, BorderLayout.SOUTH);
    }

    public String getColorRGB()
    {
	return hexaColorTextField.getText();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent keyEvent)
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent keyEvent)
    {
	if (keyEvent.getComponent().getName().equals("hexaColorTextField"))
	{
	    String temp = hexaColorTextField.getText();
	    int previousCaret = hexaColorTextField.getCaretPosition();
	    hexaColorTextField.setText(temp.toUpperCase());

	    if (temp.length() < 6)
	    {
		temp += "000000";
	    }
	    temp = temp.substring(0, 6);
	    try
	    {
		Integer.parseInt(temp, 16);
		hexaColorTextField.setText(temp);
	    }
	    catch (NumberFormatException nfe)
	    {
		hexaColorTextField.setText(defaultRGBColor);
	    }
	    String RGBColor = hexaColorTextField.getText();
	    Color color = new Color(Integer.parseInt(RGBColor.substring(0, 2),
		    16), Integer.parseInt(RGBColor.substring(2, 4), 16),
		    Integer.parseInt(RGBColor.substring(4, 6), 16));
	    colorScrollCanvas.refresh(color);
	    colorScrollCanvas.repaint();
	    updateText(color);
	    hexaColorTextField.setCaretPosition(previousCaret);
	    colorCanvas.setBackground(color);
	    // System.out.println("keyReleased : " +
	    // hexaColorTextField.getText());
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent keyEvent)
    {
    }

}