package com.monosun.jmailing.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.monosun.jmailing.res.MailResource;

/**
 * @author Jin-Hee Kang
 * filename:MailSendAboutBox.java
 * date:2002-12-28
 * time:¿ÀÀü 3:25:08
 * @version 1.0
 */
public class MailSendAboutDialog extends JDialog
{
	//final static String about="Mail Send Tool ver 1.0";
	JLabel labelAbout;
	JButton buttonClose;
	JPanel contentPane;
	
	public MailSendAboutDialog(String title,JFrame jframe)
	{
		super(jframe);
		setTitle(title);
		contentPane=(JPanel)this.getContentPane();
		labelAbout=new JLabel(MailResource.getMsg("VERSION"),JLabel.CENTER);
		buttonClose=new JButton("close");
		
        buttonClose.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                dispose();
            }

        });
        		
		contentPane.add(labelAbout,BorderLayout.CENTER);
		contentPane.add(buttonClose,BorderLayout.SOUTH);		
		
	}
}
