package com.monosun.jmailing.event;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.undo.*;

import com.monosun.jmailing.main.JMailingFrame;
/**
 * @author jini
 *
 */
public class RedoAction extends AbstractAction
{
	JMailingFrame frame;
    			
	public RedoAction(JMailingFrame frame) 
	{
	    super("redo");
	    setEnabled(false);
	    this.frame=frame;
	}

	public void actionPerformed(ActionEvent e) 
	{
	    try {
			frame.getUndoManager().redo();
	    } catch (CannotRedoException ex) {
		System.out.println("Unable to redo: " + ex);
		ex.printStackTrace();
	    }
	    update();
	    frame.getUndoAction().update();
	}

	protected void update() 
	{
	    if(frame.getUndoManager().canRedo()) {
		setEnabled(true);
		putValue(Action.NAME, frame.getUndoManager().getRedoPresentationName());
	    }
	    else {
		setEnabled(false);
		putValue(Action.NAME, "Redo");
	    }
	}
}
