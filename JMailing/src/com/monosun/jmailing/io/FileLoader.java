package com.monosun.jmailing.io;

import java.io.*;

import javax.swing.JProgressBar;
import javax.swing.text.*;

import monosun.gui.RunStateBar;

/**
 * @author jini
 *
 */
    /**
     * Thread to load a file into the text storage model
     */
public class FileLoader extends Thread {
		Document doc;
		File f;
		RunStateBar frame;
		
		public FileLoader(File f, Document doc,RunStateBar  frame) {
		    setPriority(4);
		    this.f = f;
		    this.doc = doc;
		    this.frame=frame;
		}
	
	        public void run() 
	        {
	        	Reader in=null;
		    try {
			// initialize the statusbar
			frame.getStatusPanel().removeAll();
			JProgressBar progress = new JProgressBar();
			progress.setMinimum(0);
			progress.setMaximum((int)f.length());
			progress.setStringPainted(true);
			frame.getStatusPanel().add(progress);
			frame.getStatusPanel().revalidate();
	
			// try to start reading
			 in = new FileReader(f);
			char[] buff = new char[4096];
			int nch;
			int total=0;
			while ((nch = in.read(buff, 0, buff.length)) != -1) {
			    doc.insertString(doc.getLength(), new String(buff, 0, nch), null);
			    progress.setValue(progress.getValue() + nch);
			    total+=nch;
				progress.setString(""+(int)((double)total/(double)f.length()*100)+"%");
			}			
			progress.setValue((int)f.length());
			progress.setString("100%");
			// we are done... get rid of progressbar
			//doc.addUndoableEditListener(undoHandler);
			frame.getStatusPanel().removeAll();
			frame.getStatusPanel().revalidate();
	
			//resetUndoManager();
		    }
		    catch (IOException e) {
			System.err.println(e.toString());
		    }
		    catch (BadLocationException e) {
			System.err.println(e.getMessage());
		    }
		    finally
		    {
		    	try
		    	{
					if(in!=null)
						in.close();	    	
		    	}
		    	catch(Exception e2)
		    	{
		    		e2.printStackTrace();
		    	}
		    }
//		    if (elementTreePanel != null) {
//			SwingUtilities.invokeLater(new Runnable() {
//			    public void run() {
//				elementTreePanel.setEditor(getEditor());
//			    }
//			});
//		    }
		}
	

    }