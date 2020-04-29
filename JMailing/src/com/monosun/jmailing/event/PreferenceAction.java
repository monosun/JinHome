package com.monosun.jmailing.event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.monosun.jmailing.main.JMailingFrame;
/**
 * @author jini
 *
 */
public class PreferenceAction extends AbstractAction
{
	JMailingFrame frame;
	
	public PreferenceAction(JMailingFrame frame)
	{
		super("preference");
		setEnabled(true);
		this.frame=frame;
	} 
	
	public void actionPerformed(ActionEvent e) 
	{
		frame.actionMenuProp();
	}
}
