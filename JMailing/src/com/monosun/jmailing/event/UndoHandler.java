package com.monosun.jmailing.event;
import javax.swing.event.*;

import com.monosun.jmailing.main.JMailingFrame;
/**
 * @author jini
 *
 */
public class UndoHandler implements UndoableEditListener
{
	JMailingFrame frame;
    			
	public UndoHandler(JMailingFrame frame) 
	{
	    this.frame=frame;
	}
	/**
	 * @see javax.swing.event.UndoableEditListener#undoableEditHappened(UndoableEditEvent)
	 */
	public void undoableEditHappened(UndoableEditEvent e)
	{
	    frame.getUndoManager().addEdit(e.getEdit());	    
	    frame.getUndoAction().update();
	    frame.getRedoAction().update();		
	}
}
