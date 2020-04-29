package com.monosun.jmailing.data;

import com.monosun.jmailing.main.JMailingFrame;


/**
 * @author Jin-Hee Kang
 * filename:MailSendQueue.java
 * date:2002-12-28
 * time:���� 3:22:53
 * @version 1.0
 */
public class MailSendQueue
{

	private JMailingFrame manager;

	private Object[] queue;
	private int number=-1;
	private int capacity;
	private int size;
	private int head;
	private int tail;
	private int count=0;
	/**
	 * ������ 
	 * @param cap ť�� ũ��
	 */	
	public MailSendQueue(JMailingFrame manager,int number,int cap) 
	{		
		this(manager,cap);
		this.number=number;
	}	
	/**
	 * ������ 
	 * @param cap ť�� ũ��
	 */	
	public MailSendQueue(JMailingFrame manager,int cap) 
	{
		capacity = ( cap > 0 ) ? cap : 1; // at least 1
		queue = new Object[capacity];
		head = 0;
		tail = 0;
		size = 0;
		this.manager=manager;
	}
	/**
	 * @return 	ť�� ũ��
	 */
	public int getCapacity() 
	{
		return capacity;
	}
	
	/**
	 * @return  ���� ť�� �ִ� ũ��
	 */
	public synchronized int getSize() 
	{
		return size;
	}
	/**
	 * ��� �ִ��� Ȯ��
	 * @return ť�� ��� �ִ� �� ����
	 */
	public synchronized boolean isEmpty() 
	{
		return ( size == 0 );
	}
	/**
	 * ť�� �� á���� Ȯ��
	 * @return ť�� �� á���� ����
	 */
	public synchronized boolean isFull() 
	{
		return ( size == capacity );
	}
	/**
	 * ť�� ��ü �ֱ�
	 * @param  obj ť�� ���� ��ü
	 */
	public synchronized void add(Object obj) 
			throws InterruptedException 
	{

		waitWhileFull();

		queue[head] = obj;
		head = ( head + 1 ) % capacity;
		size++;

		notifyAll(); // let any waiting threads know about change
		//manager.printLog("Stack size="+getSize());
	}
	/**
	 * ��ü ����Ʈ �߰�
	 * @param list ���� ��ü����Ʈ
	 */
	public synchronized void addEach(Object[] list) 
			throws InterruptedException 
	{

		//
		// You might want to code a more efficient 
		// implementation here ... (see ByteFIFO.java)
		//

		for ( int i = 0; i < list.length; i++ ) 
		{
			add(list[i]);
		}
	}
	/**
	 * ť���� ��ü ����
	 * @return ���ŵ� ��ü ��ȯ
	 */	
	public synchronized Object remove() 
			throws InterruptedException 
	{

		waitWhileEmpty();
		
		Object obj = queue[tail];

		// don't block GC by keeping unnecessary reference
		queue[tail] = null; 

		tail = ( tail + 1 ) % capacity;
		size--;

		notifyAll(); // let any waiting threads know about change
		
		//Queue���� ���ŵ� ��
		manager.printLog("Queue Num"+number+": Sent Mail Count="+(count++));
		return obj;
	}
	/**
	 * ť�� �ִ� ��� ��ü���� �����.
	 * @return ť�� �ִ� ��� ��ü��
	 */	
	public synchronized Object[] removeAll() 
			throws InterruptedException 
	{

		//
		// You might want to code a more efficient 
		// implementation here ... (see ByteFIFO.java)
		//

		Object[] list = new Object[size]; // use the current size

		for ( int i = 0; i < list.length; i++ ) 
		{
			list[i] = remove();
		}
		count=0;
		// if FIFO was empty, a zero-length array is returned
		return list; 
	}
	/**
	 * 
	 */
	public synchronized Object[] removeAtLeastOne() 
			throws InterruptedException 
	{

		waitWhileEmpty(); // wait for a least one to be in FIFO
		return removeAll();
	}

	public synchronized boolean waitUntilEmpty(long msTimeout) 
			throws InterruptedException 
	{

		if ( msTimeout == 0L ) 
		{
			waitUntilEmpty();  // use other method
			return true;
		}

		// wait only for the specified amount of time
		long endTime = System.currentTimeMillis() + msTimeout;
		long msRemaining = msTimeout;

		while ( !isEmpty() && ( msRemaining > 0L ) ) 
		{
			wait(msRemaining);
			msRemaining = endTime - System.currentTimeMillis();
		}

		// May have timed out, or may have met condition, 
		// calc return value.
		return isEmpty();
	}

	public synchronized void waitUntilEmpty() 
			throws InterruptedException 
	{

		while ( !isEmpty() ) 
		{
			wait();
		}
	}

	public synchronized void waitWhileEmpty() 
			throws InterruptedException 
	{

		while ( isEmpty() ) 
		{
			wait();
		}
	}

	public synchronized void waitUntilFull() 
			throws InterruptedException 
	{

		while ( !isFull() ) 
		{
			wait();
		}
	}

	public synchronized void waitWhileFull() 
			throws InterruptedException 
	{
		//spin lock
		while ( isFull() ) 
		{
			wait();
		}
	}
}
