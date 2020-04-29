package com.monosun.jmailing.event;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.undo.CannotUndoException;

import com.monosun.jmailing.main.JMailingFrame;
/**
 * @author jini
 *
 */
public class UndoAction extends AbstractAction
{
	JMailingFrame frame;
	
	public UndoAction(JMailingFrame frame) 
	{
	    super("undo");
	    setEnabled(false);
	    this.frame=frame;
	}

	public void actionPerformed(ActionEvent e) 
	{
	    try {
		frame.getUndoManager().undo();
	    } catch (CannotUndoException ex) {
		System.out.println("Unable to undo: " + ex);
		ex.printStackTrace();
	    }
	    update();
	    frame.getRedoAction().update();
	}

	protected void update() 
	{
		//System.out.println("==>"+frame.getUndoManager());
	    if(frame.getUndoManager().canUndo()) {
			setEnabled(true);
			putValue(Action.NAME, frame.getUndoManager().getUndoPresentationName());
	    }
	    else {
			setEnabled(false);
			putValue(Action.NAME, "Undo");
	    }
	}
}
