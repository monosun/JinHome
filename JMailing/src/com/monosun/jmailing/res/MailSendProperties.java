package com.monosun.jmailing.res;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import com.monosun.jmailing.util.MailProperties;
import com.monosun.jmailing.util.MailSendLog;
import com.monosun.jmailing.util.Util;

/**
 * @author Jin-Hee Kang
 * filename:MailSendProperties.java
 * date:2002-12-28
 * time:오전 3:24:54
 * @version 1.0
 */
public class MailSendProperties
{
	static MailProperties prop=new MailProperties();
		
	static
	{
		try
		{
			prop.load(MailSendProperties.class.getResourceAsStream("/MailSend.properties"));

		}
		catch(IOException ioe)
		{
			
		}		
	}
	
	public MailSendProperties()
	{				
	}
    public static void listProperty(MailSendLog logger)
    {
        int i = 0;
        for(Enumeration enumeration = prop.keys(); enumeration.hasMoreElements();)
        {
            String key = (String)enumeration.nextElement();
            logger.printLog(" Property [" + i + "] : " + key + " = " + getProperty(key));
            i++;
        }
    }	
    public static String getProperty(String key)
    {
        synchronized(prop)
        {
            return Util.toKor((String)prop.get(key));
        }
    } 
    public static void setProperty(String key,String value)
    {
        synchronized(prop)
        {
            prop.setProperty(key,value);
        }
    }
    /**
     * 속성 저장하기..
     */ 
    public static void saveProperty()
    	throws IOException
    {
    	URL url=MailSendProperties.class.getResource("/");
// 	    FileWriter out = new FileWriter("MailSend.properties");
 	    FileOutputStream out = new FileOutputStream(url.getPath()+"MailSend.properties");
		
		prop.store(out,"SMTP Setting","smtp.");
		prop.store(out,"Sender Setting","sender.");
		prop.store(out,"Test Setting","test.");
		prop.store(out,"JDBC Setting","jdbc.");

    	out.close();
    }  
                
}
