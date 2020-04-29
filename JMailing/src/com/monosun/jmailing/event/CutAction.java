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
 	/**
 	 * CutAction 이벤트 짤라서 클립보드에 넣기
 	 */
 	public void actionPerformed(ActionEvent e) 
 	{
 		frame.getEditor().cut();
 	}
}
