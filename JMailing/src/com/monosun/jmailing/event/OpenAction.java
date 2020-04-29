package com.monosun.jmailing.event;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.text.*;

import com.monosun.jmailing.io.FileLoader;
import com.monosun.jmailing.main.JMailingFrame;

/**
 * @author jini
 *
 */
public class OpenAction extends AbstractAction
{
	JMailingFrame frame;
	FileDialog fileDialog;
	
	public OpenAction(JMailingFrame frame) 
	{
	    super("open");
		this.frame=frame;
	}

	public void actionPerformed(ActionEvent e) 
	{

	    //Frame frame = getFrame();
	    if (fileDialog == null) {
		fileDialog = new FileDialog(frame);
	    }
	    fileDialog.setMode(FileDialog.LOAD);
	    fileDialog.show();

	    String file = fileDialog.getFile();
	    if (file == null) {
		return;
	    }
	    String directory = fileDialog.getDirectory();
	    File f = new File(directory, file);
	    if (f.exists()) {
		Document oldDoc = frame.getEditor().getDocument();
		if(oldDoc != null)
		    oldDoc.removeUndoableEditListener(frame.getUndoableEditListener());
//		if (elementTreePanel != null) {
//		    elementTreePanel.setEditor(null);
//		}
		frame.getEditor().setDocument(new PlainDocument());
		frame.setTitle(file);
		//frame.setFileURL(f.getPath());
		Thread loader = new FileLoader(f, frame.getEditor().getDocument(),frame);
		loader.start();
	    }
	}
}
