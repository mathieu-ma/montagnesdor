package fr.mch.mdo.applets;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

public class ColorScrollCanvas extends Canvas
{

    /**
     * 
     */
    private static final long serialVersionUID = 287398534044679753L;

    private int height = 256;

    private int width = 30;

    private int gap = 5;

    private int colorMap[];

    private Image scrollColor;

    int mouseY;

    int prevY;

    ColorScrollCanvas(int width, int height, Color deFaultColor)
    {
	this.width = width;
	gap = width / 2;
	this.height = height;
	colorMap = new int[(height) * width];
	scrollColor = null;
	mouseY = 0;
	prevY = 0;

	super.setBackground(Color.white);
	super.setSize(2 * width, height);

	refresh(deFaultColor);
	mouseY = height / 2;
    }

    public Color getColorAt(int i, int j)
    {
	Color color = null;
	int index = i + j * width;
	if (j < 0)
	    color = Color.WHITE;
	else if (j > height)
	    color = Color.BLACK;
	else if (0 < index && index < colorMap.length)
	    color = new Color(colorMap[index]);
	else
	{
	    if (j < height / 2)
		color = Color.WHITE;
	    else color = Color.BLACK;
	}

	return color;
    }

    public Color getColor()
    {
	return getColorAt(0, mouseY);
    }

    public void paint(Graphics g)
    {
	int ai[] =
	{
		0, gap, 0
	};
	int ai1[] =
	{
		mouseY - gap, mouseY, mouseY + gap
	};

	g.setColor(Color.black);
	g.fillPolygon(ai, ai1, 3);

	ai[0] = getSize().width - ai[0] - (width % 2 == 1 ? 0 : 1);
	ai[1] = getSize().width - ai[1] - (width % 2 == 1 ? 0 : 1);
	ai[2] = getSize().width - ai[2] - (width % 2 == 1 ? 0 : 1);
	g.fillPolygon(ai, ai1, 3);

	g.drawImage(scrollColor, gap, 0, this);
    }

    public void update(Graphics g)
    {
	g.clearRect(0, 0, gap, getSize().height);
	g
		.clearRect(getSize().width - gap, 0, getSize().width,
			getSize().height);
	paint(g);
    }

    public void refresh(Color color)
    {
	int i = 0;
	do
	{
	    int j = 1;
	    do
		colorMap[i + j * width] = 0xff000000
			| (j * 2 * (color.getRed() - 255)) / height + 255 << 16
			| (j * 2 * (color.getGreen() - 255)) / height + 255 << 8
			| (j * 2 * (color.getBlue() - 255)) / height + 255;
	    while (++j <= (height / 2));
	}
	while (++i < width);
	i = 0;
	do
	{
	    int k = (height / 2);
	    do
		colorMap[i + k * width] = 0xff000000
			| (color.getRed() * 2 * (height - k)) / height << 16
			| (color.getGreen() * 2 * (height - k)) / height << 8
			| (color.getBlue() * 2 * (height - k)) / height;
	    while (++k < height);
	}
	while (++i < width);
	scrollColor = createImage(new MemoryImageSource(width, height,
		colorMap, 0, width));
	repaint();
    }

    public boolean mouseDrag(Event event, int i, int j)
    {
	return true;
    }

    public void setCursor(int i)
    {
	prevY = mouseY;

	if (i < 0)
	    mouseY = 0;
	else if (i > height)
	    mouseY = height;
	else mouseY = i;
    }
}