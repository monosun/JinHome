package com.monosun.jmailing.io;

import java.io.*;

import javax.swing.JProgressBar;
import javax.swing.text.*;

import monosun.gui.RunStateBar;

/**
 * @author jini
 *
 */
public class FileSaver extends Thread
{
		Document doc;
		File f;
		RunStateBar frame;
		public FileSaver(File f, Document doc,RunStateBar frame) {
		    setPriority(4);
		    this.f = f;
		    this.doc = doc;
		    this.frame=frame;
		}
	
        public void run() {
		    try {
				// initialize the statusbar
				//status.removeAll();
				JProgressBar progress = new JProgressBar();
				progress.setMinimum(0);
				progress.setMaximum((int) f.length());
				//status.add(progress);
				//status.revalidate();
		
				// try to start reading
				Writer out = new FileWriter(f);
				
				//out.write(doc.get)
				out.write(doc.getText(0,doc.getLength()));
	//			Reader in = new FileReader(f);
	//			char[] buff = new char[4096];
	//			int nch;
	//			while ((nch = in.read(buff, 0, buff.length)) != -1) {
	//			    doc.insertString(doc.getLength(), new String(buff, 0, nch), null);
	//			    progress.setValue(progress.getValue() + nch);
	//			}
		
				// we are done... get rid of progressbar
				//doc.addUndoableEditListener(undoHandler);
				//status.removeAll();
				//status.revalidate();
		
				//resetUndoManager();
				out.close();
		    }
		    catch (IOException e) {
			System.err.println(e.toString());
		    }
		    catch (BadLocationException e) {
			System.err.println(e.getMessage());
		    }
		    

	}
	
}
