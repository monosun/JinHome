package com.monosun.jmailing.gui;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.Document;
import javax.swing.text.html.*;

/**
 * @author jini
 *
 */ 
public class HtmlPreviewDialog extends JDialog
{
	JEditorPane html;
	JPanel contentPane;
	/**
	 * Constructor for HtmlPreviewDialog.
	 */
	public HtmlPreviewDialog()
	{
		super();
	}
	
	public HtmlPreviewDialog(String title,Frame owner)
	{
		super(owner);
		setTitle(title);	
		html = new JEditorPane();
		html.setContentType("text/html;charset=EUC-KR");
		contentPane=(JPanel)this.getContentPane();
		JScrollPane scroller = new JScrollPane();		
	    System.out.println("000");
        html.setEditable(true);
        html.addHyperlinkListener(createHyperLinkListener());
		JViewport vp = scroller.getViewport();
		vp.add(html);        
        contentPane.add(scroller, BorderLayout.CENTER);
	}
	
	public void setDocument(Document doc)
	{
		HTMLDocument htmldoc= new HTMLDocument();
		html.setDocument(htmldoc);

		
	}

	public void setText(String str)
	{	
		html.setContentType("text/html;charset=EUC-KR");
	
		html.setText(str);	
		html.repaint();
	}
	
	public void setPage(URL url)
	{
		try
		{
			//ml.setContentType("text/html;charset=EUC-KR");
			html.setPage(url);
		}
		catch (Exception e)
		{
			
		}		
	}
		
    public HyperlinkListener createHyperLinkListener() {
	return new HyperlinkListener() {
	    public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		    if (e instanceof HTMLFrameHyperlinkEvent) {
			((HTMLDocument)html.getDocument()).processHTMLFrameHyperlinkEvent(
			    (HTMLFrameHyperlinkEvent)e);
		    } else {
			try {
			    html.setPage(e.getURL());
			} catch (IOException ioe) {
			    System.out.println("IOE: " + ioe);
			}
		    }
		}
	    }
	};
    }	
}
