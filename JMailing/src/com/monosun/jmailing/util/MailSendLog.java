package com.monosun.jmailing.util;

import javax.swing.JTextArea;

/**
 * @author Jin-Hee Kang
 * filename:MailSendLog.java
 * date:2002-12-28
 * time:¿ÀÀü 3:23:31
 * @version 1.0
 */
public class MailSendLog //extends Thread
{
	private JTextArea out;
	static int count=0;
	
	public MailSendLog(JTextArea out)
	{
		this.out=out;
	}		
	
	/**
	 * @see java.lang.Runnable#run()
	 */
//	public void run()
//	{
//		while(true)
//		{
//			try
//			{
//				Thread.sleep(1000);
//				//out.append("count="+(count++)+"\n");				
//			}
//			catch(InterruptedException ie)
//			{
//				continue;
//			}
//
//		}
//	}
	
	public void printLog(String msg)
	{
		out.append(msg+"\n");
	}

}
