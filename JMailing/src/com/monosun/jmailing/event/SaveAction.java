package com.monosun.jmailing.event;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.text.*;

import com.monosun.jmailing.io.FileSaver;
import com.monosun.jmailing.main.JMailingFrame;

/**
 * @author jini
 *
 */
public class SaveAction extends AbstractAction
{
	JMailingFrame frame;
	FileDialog fileDialog;
	public SaveAction(JMailingFrame frame)
	{
		super("save");
		setEnabled(false);
		this.frame=frame;
	} 
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println("==Save===");
	    //Frame frame = getFrame();
	    if (fileDialog == null) {
		fileDialog = new FileDialog(frame);
	    }
	    fileDialog.setMode(FileDialog.SAVE);
	    fileDialog.show();

	    String filename = fileDialog.getFile();
	    System.out.println("filename="+filename);
		if(filename!=null)
		{
		    String directory = fileDialog.getDirectory();
		    File f = new File(directory, filename);
		    if (f.exists()) 
		    {
				Document oldDoc = frame.getEditor().getDocument();
				if(oldDoc != null)
				    oldDoc.removeUndoableEditListener(frame.getUndoableEditListener());
	
				frame.getEditor().setDocument(new PlainDocument());
				frame.setTitle(filename);
		//		Thread loader = new FileLoader(f, frame.getEditor().getDocument());
		//		loader.start();	
		    }
		    else //파일이 존재 하지 않을 때
		    {
		    	Thread saver = new FileSaver(f,frame.getEditor().getDocument(),frame);
		    	saver.start();
		    }
	    }	
	}
}
