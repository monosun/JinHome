package com.monosun.jmailing.event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.monosun.jmailing.main.JMailingFrame;
/**
 * @author jini
 *
 */
public class HelpAction extends AbstractAction
{
	JMailingFrame frame;
	/**
	 * Constructor for HelpAction.
	 */
	public HelpAction(JMailingFrame frame)
	{
		super("help");
		setEnabled(true);
		this.frame=frame;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		frame.actionMenuHelp();
	}
}
