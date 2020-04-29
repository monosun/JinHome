package com.monosun.jmailing.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Jin-Hee Kang
 * filename:MailSendHelpDialog.java
 * date:2002-12-28
 * time:¿ÀÀü 3:26:16
 * @version 1.0
 */
public class MailSendHelpDialog extends JDialog
{
	
	final static String[] about= {
		"",
		"",
		"Be maded by MONOSUN",
		"homepage: http://www.monosun.com",
		"",
		""};
	JLabel labelAbout;
	JButton buttonClose;
	JPanel contentPane,mainPane;
		
	public MailSendHelpDialog(String title,JFrame jframe)
	{
		super(jframe);
		setTitle(title);
		contentPane=(JPanel)this.getContentPane();
		mainPane = new JPanel();
		mainPane.setLayout(new GridLayout(about.length,1));
		
		for(int i=0;i<about.length;i++)
		{
			labelAbout=new JLabel(about[i],JLabel.CENTER);
			mainPane.add(labelAbout);
		}
		
		buttonClose=new JButton("close");
		
        buttonClose.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                dispose();
            }

        });
        		
		contentPane.add(mainPane,BorderLayout.CENTER);
		contentPane.add(buttonClose,BorderLayout.SOUTH);						
	}

}
