package com.monosun.jmailing.res;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import com.monosun.jmailing.util.Util;

/**
 * @author Jin-Hee Kang
 * filename:MailResource.java
 * date:2002-12-28
 * time:오전 3:25:19
 * @version 1.1
 * 
 * 2003-06-12 Bundle로 읽어오기 추가
 */
public class MailResource
{
	private static ResourceBundle resources;
	static
	{
		resources=ResourceBundle.getBundle("com.monosun.jmailing.res.MailResourceBundle",Locale.getDefault());
	}
	
	public MailResource()
	{
	}
		
    public static char getMnemonic(String s)
    {
        char c;
        try
        {
            c = getMsg(s).charAt(0);
        }
        catch(Exception exception)
        {
            c = '?';
            exception.printStackTrace();
        }
        return c;
    }
    
    public static String getMsg(String s)
    {
        String s1;
        try
        {        	
            s1 = resources.getString(s);
        }
        catch(Exception exception)
        {
            s1 = "??????????";
            exception.printStackTrace();
        }
        return s1;
    }
    
    public static String getMsg(String s, String s1)
    {
        String s2;
        try
        {
            s2 = resources.getString(s);
        }
        catch(Exception exception)
        {
            s2 = "??????????";
            exception.printStackTrace();
        }
        try
        {
            Object aobj[] = {
                s1
            };
            s2 = MessageFormat.format(s2, aobj);
        }
        catch(Exception _ex) { }
        return s2;
    }

    public static String getMsg(String s, String s1, String s2)
    {
        String s3;
        try
        {
            s3 = resources.getString(s);
        }
        catch(Exception exception)
        {
            s3 = "??????????";
            exception.printStackTrace();
        }
        try
        {
            Object aobj[] = {
                s1, s2
            };
            s3 = MessageFormat.format(s3, aobj);
        }
        catch(Exception _ex) { }
        return s3;
    }	
}
