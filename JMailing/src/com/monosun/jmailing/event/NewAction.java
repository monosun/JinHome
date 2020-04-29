package com.monosun.jmailing.event;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.text.*;

import com.monosun.jmailing.main.JMailingFrame;
/**
 * @author jini
 *
 */
public class NewAction extends AbstractAction
{
	JMailingFrame frame;
	
	
	public NewAction(JMailingFrame frame) 
	{
	    super("new");
	    this.frame=frame;
	}

    public void actionPerformed(ActionEvent e) 
    {
	    Document oldDoc = frame.getEditor().getDocument();
	    
    	System.out.println("==New===");
	    if(oldDoc != null)
			oldDoc.removeUndoableEditListener(frame.getUndoableEditListener());

	    frame.getEditor().setDocument(new PlainDocument());
	    frame.getEditor().getDocument().addUndoableEditListener(frame.getUndoableEditListener());
	    resetUndoManager();
	    frame.getEditor().revalidate();
	}
    /**
     * Resets the undo manager.
     */
    protected void resetUndoManager() {
		frame.getUndoManager().discardAllEdits();
		frame.getUndoAction().update();
		frame.getRedoAction().update();
    }	
}
