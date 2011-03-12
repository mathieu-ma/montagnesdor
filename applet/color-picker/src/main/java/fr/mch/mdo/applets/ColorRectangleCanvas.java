package fr.mch.mdo.applets;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

public class ColorRectangleCanvas extends Canvas
{
    /**
     * 
     */
    private static final long serialVersionUID = -4512214117741522648L;

    private int width;
    private int height;
    private int colorMap[];
    private Image rectangleColor;

    ColorRectangleCanvas(int width, int height)
    {
	this.width = width;
	this.height = height;
	colorMap = new int[width * height];
	rectangleColor = null;
	super.setSize(width, height);
	int i = 0;
	do
	{
	    int j = 1;
	    do
		colorMap[i + (height - j) * width] = Color.HSBtoRGB((float) i
			/ width, (float) j / height, 0.75F);
	    while (++j <= height);
	}
	while (++i < width);
	rectangleColor = createImage(new MemoryImageSource(width, height,
		colorMap, 0, width));
    }

    public Color getColorAt(int i, int j)
    {
	Color color = null;
	try
	{
	    int index = i + j * width;
	    if (0 < j && j < height && 0 < i && i < width)
		color = new Color(colorMap[index]);
	}
	catch (ArrayIndexOutOfBoundsException e)
	{
	    e.printStackTrace();
	}

	return color;
    }

    public void paint(Graphics g)
    {
	g.drawImage(rectangleColor, 0, 0, this);
    }

}
