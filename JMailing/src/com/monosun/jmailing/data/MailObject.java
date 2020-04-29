package com.monosun.jmailing.data;
/**
 * @author Jin-Hee Kang
 * filename:MailObject.java
 * date:2002-12-28
 * time:오전 3:19:56
 * @version 1.0
 * 
 * 메일 정보를 담는 클래스
 */
public class MailObject
{	
	private String email;
	private String name;
	private String date;
	
	public MailObject(String email,String name,String date)
	{
		this.email=email;
		this.name=name;
		this.date=date;
	}

	/**
	 * Returns the email.
	 * @return String
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the email.
	 * @param email The email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Returns the date.
	 * @return String
	 */
	public String getDate()
	{
		return date;
	}

	/**
	 * Sets the date.
	 * @param date The date to set
	 */
	public void setDate(String date)
	{
		this.date = date;
	}

}
