package com.monosun.jmailing.sender;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.monosun.jmailing.data.MailObject;
import com.monosun.jmailing.data.MailSendQueue;
import com.monosun.jmailing.main.JMailingFrame;
import com.monosun.jmailing.res.MailSendProperties;
import com.monosun.jmailing.util.Util;

/**
 * @author Jin-Hee Kang
 * filename:MailSend.java
 * date:2002-12-28
 * time:¿ÀÀü 3:20:05
 * @version 1.0
 */
public class MailSender extends Thread
{
	MailSendQueue queue;
	JMailingFrame manager;
	private boolean isStop=false;
	
	private String sender;
	private String testEmail;
	private String testName;
	private String testIndate;	
	private Properties props;
	private Session session;
	private Store store;
	private Transport trans;
	private MimeMessage msg;  	
	/**
	 * Constructor for MailSender.
	 */
	public MailSender(JMailingFrame manager,MailSendQueue queue)
	{
		this.manager=manager;
		this.queue=queue;
	    props = new Properties();
	    try
	    {		  
		    session =Session.getInstance(props,null);
		    props.put("mail.smtp.host",MailSendProperties.getProperty("smtp.server"));
		    sender=MailSendProperties.getProperty("sender.email");	
		    testEmail=MailSendProperties.getProperty("test.email");	  
		    //testName=Util.toKor(MailSendProperties.getProperty("test.name"));	  		    
		    testName=MailSendProperties.getProperty("test.name");
		    testIndate=MailSendProperties.getProperty("test.indate");
		    //manager.printLog(testName);
		    trans = session.getTransport("smtp");		
	    }
	    catch(NoSuchProviderException nspe)
	    {
	    	manager.printLog(nspe.getMessage());
	    }
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		int count=1;
		while(!isStop)
		{
			try
			{
				//Thread.sleep(100);
				while(queue.isEmpty())Thread.sleep(100);
				//queue.getMail();
				MailObject obj=(MailObject)queue.remove();
				if(obj.getEmail().equals("EOF"))
				{
					isStop=true;
					manager.UIEnable(true);
					manager.printLog("All Sended");
					queue.removeAll();
				}
				else
				{	
					//send
					sendMail(obj.getName(),obj.getEmail(),obj.getDate());									
					//manager.printLog("Sended mail count="+count++);				
				}
			}			
			catch(InterruptedException ie)
			{
				manager.printLog(ie.getMessage());
			}
			catch(AddressException ae)
			{
				manager.printLog(ae.getMessage());
			}
			catch(MessagingException me)
			{
				manager.printLog(me.getMessage());
			}
		}
	}
	public void sendTestMail()
	{
		try
		{
			sendMail(testName,testEmail,testIndate);
		}
		catch(AddressException ae)
		{
			manager.printLog(ae.getMessage());
		}
		catch(MessagingException me)
		{
			manager.printLog(me.getMessage());
		}
		manager.printLog("Test Mail Send");
	}
	private void sendMail(String name,String email,String date)
		throws MessagingException, AddressException
	{
		msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(sender));
		msg.setRecipient(Message.RecipientType.TO,new InternetAddress(email.trim()));
		
		msg.setSubject(Util.getAppText(manager.getSubject(),name,email,date));
		msg.setSentDate(new Date());
		if(manager.isHtml())
		{
			msg.setContent(Util.getAppText(manager.getMailContent(),name,email,date),"text/html;charset=ks_c_5601-1987");
		}
		else
		{
			msg.setText(Util.getAppText(manager.getMailContent(),name,email,date));
		}
		trans.send(msg);
		// manager.count();			
	}	
	public void stopRun(boolean enable)
	{
		isStop=enable;
		if(enable)
			manager.printLog("Mail Send Canceled");				
	}
}
