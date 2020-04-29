package com.monosun.jmailing.event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.monosun.jmailing.main.JMailingFrame;
/**
 * @author jini
 *
 */
public class CutAction extends AbstractAction
{
	JMailingFrame frame;
	
 	public CutAction(JMailingFrame frame)
 	{
 		super("cut");
 		setEnabled(false);
 		this.frame=frame;
 	} 
 	
 	public void actionPerformed(ActionEvent e) 
 	{
 	}
}
