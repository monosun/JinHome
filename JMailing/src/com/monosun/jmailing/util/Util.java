package com.monosun.jmailing.util;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 
 * @author Jin-Hee Kang
 * 
 * filename: Util.java
 * 
 * date:2002-12-29
 * time:오후 7:27:15
 * 
 * @version 1.0
 * 
 */
public class Util
{
	/**
	 * Constructor for Util.
	 */
	public Util()
	{
	}
	
	public static final String[] types =
	{"@name","@email","@indate"};
	
	/**
	 * 한글로 인코딩
	 */
	public static String toKor(String eng)
	{
		String str=null;
		try
		{
			str = new String(eng.getBytes("8859_1"),"KSC5601");		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return str;			
	}
	/**
	 * 영어로 인코딩
	 */	
	public static String toEng(String kor)
	{
		String str=null;
		try
		{
			str = new String(kor.getBytes("KSC5601"),"8859_1");		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 
	 */
	public static String toPattern(String buf,String value,String type)
	{
		String temp=buf;
		int num;

		if((num=temp.indexOf(type))!=-1)
		{
			return temp=toPattern(buf.substring(0,num),value,type)+
				 value+
				 toPattern(buf.substring(num+type.length()),value,type);
		}
		else
		{		
			return temp;
		}
	}
	/**
	 * 
	 * 	{"@name","@email","@indate"};
	 * @buf source String
	 * @value String to replace
	 */
	public static String getAppText(String buf,String name,String email,String date)
	{
		return toPattern(toPattern(toPattern(buf,name,types[0]),email,types[1]),date,types[2]);
	}
    /**
     * 문자열에서 whitespace로 문자들을 나누어서 배열로 리턴한다.
     */
    public static String[] tokenize(String input) 
    {
		Vector v = new Vector();
		StringTokenizer t = new StringTokenizer(input);
		String cmd[];
	
		while (t.hasMoreTokens())
		    v.addElement(t.nextToken());
		cmd = new String[v.size()];
		for (int i = 0; i < cmd.length; i++)
		    cmd[i] = (String) v.elementAt(i);
	
		return cmd;
    }	
    /**
     * 특정 문자열 제거 ..
     * 
     */
    public static String remove(String str,String removeStr)
    {
   	    //int index = str.startsWith(removeStr);
   	       	      	    
    	return null;
    }
}
