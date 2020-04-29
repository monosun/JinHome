package com.monosun.jmailing.gui;
import java.awt.Graphics;

import javax.swing.*;
/**
 * @author jini
 *
 */
public class StatusBar extends JComponent
{
	/**
	 * Constructor for StatusBar.
	 */
	public StatusBar()
	{
	    super();
	    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}

    public void paint(Graphics g) 
    {
	    super.paint(g);
	}
}
