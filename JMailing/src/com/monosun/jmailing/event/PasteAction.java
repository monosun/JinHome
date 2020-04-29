package com.monosun.jmailing.event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.monosun.jmailing.main.JMailingFrame;
/**
 * @author jini
 *
 */
public class PasteAction extends AbstractAction
{
	JMailingFrame frame;
	/**
	 * Constructor for PasteAction.
	 */
	public PasteAction(JMailingFrame frame)
	{
		super("paste");
		setEnabled(false);
		this.frame=frame;
	}
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
	}
}
