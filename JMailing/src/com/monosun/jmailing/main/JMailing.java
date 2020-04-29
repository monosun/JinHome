package com.monosun.jmailing.main;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.monosun.jmailing.data.MailSendQueue;

import com.monosun.jmailing.gui.MailPreferenceDialog;
import com.monosun.jmailing.gui.MailSendAboutDialog;
import com.monosun.jmailing.gui.MailSendHelpDialog;
import com.monosun.jmailing.reader.DBReader;
import com.monosun.jmailing.res.*;
import com.monosun.jmailing.sender.MailSender;
import com.monosun.jmailing.util.*;
import com.monosun.jmailing.util.Util;

/**
 * @author Jin-Hee Kang
 * filename:MailSendManager.java
 * date:2002-12-28
 * time:오전 3:19:39
 * @version 1.0
 */
public class JMailing
{ 
	/**
	 * 구동...
	 */
	public static void main(String[] args)
	{
//		try
//		{
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//		}
//		catch (Exception e)
//		{
//		}
        JMailingFrame mainframe = new JMailingFrame();

        mainframe.pack();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimension1 = mainframe.getSize();
        if(dimension1.height > dimension.height)
            dimension1.height = dimension.height;
        if(dimension1.width > dimension.width)
            dimension1.width = dimension.width;
        mainframe.setLocation((dimension.width - dimension1.width) / 2, (dimension.height - dimension1.height) / 2);
        mainframe.setVisible(true);               
     		
	}

}
