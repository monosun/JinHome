package com.monosun.jmailing.event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.monosun.jmailing.main.JMailingFrame;
/**
 * @author jini
 *
 */
public class ExitAction extends AbstractAction
{
	JMailingFrame frame;
	public ExitAction(JMailingFrame frame) 
	{
	    super("exit");
		this.frame=frame;
	}
	/**
	 * Exit ��ư Ŭ���� ���� �׼�
	 */
	public void actionPerformed(ActionEvent e) 
	{
		    System.exit(0);
	}
}
