package com.monosun.jmailing.reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.monosun.jmailing.data.MailObject;
import com.monosun.jmailing.data.MailSendQueue;
import com.monosun.jmailing.main.JMailing;
import com.monosun.jmailing.main.JMailingFrame;
import com.monosun.jmailing.res.MailSendProperties;

/**
 * @author Jin-Hee Kang
 * filename:DBReader.java
 * date:2002-12-29
 * time:오후 1:15:23
 * @version 1.0
 */
public class DBReader extends Thread
{
	MailSendQueue[] queueList;
	JMailingFrame manager;

	private boolean isStop=false;
	/**
	 * Constructor for DBReader.
	 */
	public DBReader(JMailingFrame manager,MailSendQueue[] queueList)
	{
		this.manager=manager;
		this.queueList=queueList;
		manager.printLog(MailSendProperties.getProperty("jdbc.driver"));
		manager.printLog(MailSendProperties.getProperty("jdbc.url"));
		try
		{		
			Class.forName(MailSendProperties.getProperty("jdbc.driver"));

		}
		catch(ClassNotFoundException cnfe)
		{
			manager.printLog("JDBC Driver Not Found");
		}

	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		
//		for(int i=0;i<100;i++)
//		{
//			try
//			{
//				queue.add(new MailObject("monosun@tmax.co.kr","강진희"));
//				//if(i==99)queue.add(new MailObject("EOF","EOF","EOF"));
//			}
//			catch(InterruptedException ie)
//			{
//			}
//		}
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		try
		{
			String vendor=MailSendProperties.getProperty("jdbc.vendor");
			if(vendor.equals("informix"))
				con = DriverManager.getConnection(MailSendProperties.getProperty("jdbc.url"));				
			else if(vendor.equals("oracle"))
				con = DriverManager.getConnection(MailSendProperties.getProperty("jdbc.url"),MailSendProperties.getProperty("jdbc.user"),MailSendProperties.getProperty("jdbc.password"));

			stmt=con.createStatement();
			
			rs=stmt.executeQuery(MailSendProperties.getProperty("jdbc.query"));
			
			for(int i=0;rs.next();i++)
			{
				System.out.println(""+rs.getString(1));
				//각 Queue에 등록
				queueList[i%JMailingFrame.THREAD_SIZE].add(new MailObject(rs.getString("email"),rs.getString("name"),rs.getString("indate")));
				//this.notifyAll();
			}
			
			for(int i=0;i<JMailingFrame.THREAD_SIZE;i++)
				queueList[i%JMailingFrame.THREAD_SIZE].add(new MailObject("EOF","EOF","EOF"));	

		}
		catch(InterruptedException ie)
		{
			manager.printLog(ie.getMessage());
		}
		catch(java.sql.SQLException sqle)
		{
			manager.printLog(sqle.getMessage());
			stopRun(true);
		}
		finally
		{
			try
			{
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(con!=null)con.close();
			}
			catch(java.sql.SQLException sqle2)
			{
				manager.printLog(sqle2.getMessage());
			}		
		}
	}
	/**
	 * 메일 주소를 읽는 것을 멈춘다.
	 */
	public void stopRun(boolean enable)
	{
		isStop=enable;		
	}
}
