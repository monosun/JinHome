package com.monosun.jmailing.gui;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.monosun.jmailing.res.MailResource;
import com.monosun.jmailing.res.MailSendProperties;
import com.monosun.jmailing.util.Util;
/**
 * @author Jin-Hee Kang
 * filename:MailProference.java
 * date:2002-12-28
 * time:오후 10:21:14
 * @version 1.0
 */
public class MailPreferenceDialog extends JDialog
{

	//DB Setting
	Border paneEdge;	
	JPanel dbPane,dbButtonPane,mailPane,mailButtonPane;
	TitledBorder dbTitle,mailTitle;
	JButton dbApplyButton,dbCancelButton,mailApplyButton,mailCancelButton;
	JTextField vendorField,urlField,driverField,userField,passwordField,queryField;	
	
	//Mail Server Setting
	JTextField smtpServerField,senderEmailField,testEmailField,testNameField,testIndateField;	
	
	/**
	 * Constructor for MailProference.
	 * @throws HeadlessException
	 */
	public MailPreferenceDialog() //throws HeadlessException
	{
	}
	/**
	 * Constructor for MailProference.
	 * @param owner
	 * @throws HeadlessException
	 */
	public MailPreferenceDialog(String title,Frame owner) //throws HeadlessException
	{
		super(owner);
		setTitle(title);

        paneEdge = BorderFactory.createEmptyBorder(0,10,10,10);

        //First pane: JDBC Connection
        dbPane = new JPanel();
        dbPane.setBorder(paneEdge);
        dbPane.setLayout(new BorderLayout());
        //dbTitle = BorderFactory.createTitledBorder("JDBC Connection");
        dbTitle = BorderFactory.createTitledBorder(MailResource.getMsg("JDBC_DRIVER_TITLE"));
        //resource.getMsg("PROGRAMER_INFO")
        dbButtonPane = new JPanel();
        dbApplyButton = new JButton("Apply");
        dbApplyButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
            	try
            	{
					saveDBConfig();
					MailSendProperties.saveProperty();
					
            	}
            	catch(IOException ioe)
            	{
            		ioe.printStackTrace();
            	}
            }

        });        
        dbCancelButton = new JButton("Cancel");
        dbCancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
				dispose();
		        loadDBConfig();
		        loadMailConfig();				
            }

        });                

		dbButtonPane.add(dbApplyButton);
		dbButtonPane.add(dbCancelButton);
        dbPane.add(dbButtonPane,"South");
        addDBTab(dbTitle,dbPane);                
        
        //Second pane: Mail Server Setting
        mailPane = new JPanel();
        mailPane.setBorder(paneEdge);  
        mailPane.setLayout(new BorderLayout());                                                   
        mailTitle = BorderFactory.createTitledBorder(MailResource.getMsg("MAIL_SERVER_WINDOW_TITLE"));
        mailButtonPane = new JPanel();
        mailApplyButton = new JButton("Apply");
        mailApplyButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
            	try
            	{
					saveMailConfig();
					MailSendProperties.saveProperty();
            	}
            	catch(IOException ioe)
            	{
            		ioe.printStackTrace();
            	}
            }

        });        
        mailCancelButton = new JButton("Cancel");        
        mailCancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
				dispose();				
		        loadDBConfig();
		        loadMailConfig();				
            }

        });
        
		mailButtonPane.add(mailApplyButton);
		mailButtonPane.add(mailCancelButton);
        mailPane.add(mailButtonPane,"South");		
        addMaiTab(mailTitle,mailPane); 
                                                      
        //total configuration                 
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("DB Setting", null, dbPane, null);
        tabbedPane.addTab(MailResource.getMsg("MAIL_SERVER_WINDOW_TITLE"), null, mailPane, null);
        tabbedPane.setSelectedIndex(0);        

        getContentPane().add(tabbedPane, BorderLayout.CENTER);		
        
       	tabbedPane.addChangeListener(new TabChangeListener()
       	{
       		public void stateChanged(ChangeEvent e)
       		{
		        loadDBConfig();
		        loadMailConfig();
       		} 
       	});
        loadDBConfig();
        loadMailConfig();
        
	}
    class TabChangeListener implements ChangeListener  {
         public void stateChanged( ChangeEvent event )  {
         }
    }	
	/**
	 * JPanel안에 붙이기
	 */
    private void addMaiTab(Border border,
                          Container container) {
        JPanel comp = new JPanel(false);
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints gridCon = new GridBagConstraints();        
        comp.setLayout(gridBag);
        comp.setBorder(border);
        //smtpServerField,testEmailField,testNameField,testIndateFiel
        JLabel smtpServerLabel = new JLabel("SMTP Server: ", JLabel.RIGHT);
        JLabel senderEmailLabel = new JLabel("Sender Email: ", JLabel.RIGHT);                
        JLabel testEmailLabel = new JLabel("Test Email: ", JLabel.RIGHT);
        JLabel testNameLabel = new JLabel("Test Name: ", JLabel.RIGHT);
        JLabel testIndateLabel = new JLabel("Test Indate: ", JLabel.RIGHT);


        smtpServerField = new JTextField(30);   
        senderEmailField = new JTextField(30);             
        testEmailField = new JTextField(30);
        testNameField = new JTextField(30);
        testIndateField = new JTextField(30);
        
        gridCon.fill=GridBagConstraints.HORIZONTAL;

        gridCon.gridx=0;
        gridCon.gridy=0;        
        gridBag.setConstraints(smtpServerLabel,gridCon);
        comp.add(smtpServerLabel);

        gridCon.gridx=1;
        gridCon.gridy=0;        
        gridBag.setConstraints(smtpServerField,gridCon);  
        comp.add(smtpServerField);       

        gridCon.gridx=0;
        gridCon.gridy=1;        
        gridBag.setConstraints(senderEmailLabel,gridCon);
        comp.add(senderEmailLabel);

        gridCon.gridx=1;
        gridCon.gridy=1;        
        gridBag.setConstraints(senderEmailField,gridCon);  
        comp.add(senderEmailField);      
        
        gridCon.gridx=0;
        gridCon.gridy=2;                 
        gridBag.setConstraints(testEmailLabel,gridCon); 
        comp.add(testEmailLabel); 

        gridCon.gridx=1;
        gridCon.gridy=2;         
        gridBag.setConstraints(testEmailField,gridCon); 
        comp.add(testEmailField);                

        gridCon.gridx=0;
        gridCon.gridy=3;         
        gridBag.setConstraints(testNameLabel,gridCon); 
        comp.add(testNameLabel);

        gridCon.gridx=1;
        gridCon.gridy=3;         
        gridBag.setConstraints(testNameField,gridCon); 
        comp.add(testNameField); 

        gridCon.gridx=0;
        gridCon.gridy=4;                        
        gridBag.setConstraints(testIndateLabel,gridCon); 
        comp.add(testIndateLabel);

        gridCon.gridx=1;
        gridCon.gridy=4;         
        gridBag.setConstraints(testIndateField,gridCon); 
        comp.add(testIndateField);  

         
        container.add(comp,"North");   
    }

    
    private void addDBTab(Border border,
                          Container container) {
        JPanel comp = new JPanel(false);
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints gridCon = new GridBagConstraints();        
        comp.setLayout(gridBag);
        comp.setBorder(border);
        JLabel vendorLabel = new JLabel("DB Vendor: ", JLabel.RIGHT);
        JLabel urlLabel = new JLabel("JDBC URL: ", JLabel.RIGHT);
        JLabel driverLabel = new JLabel("JDBC Driver: ", JLabel.RIGHT);
        JLabel userLabel = new JLabel("DB User: ", JLabel.RIGHT);
        JLabel passwordLabel = new JLabel("DB Password: ", JLabel.RIGHT);
        JLabel queryLabel = new JLabel("SELECT Query: ", JLabel.RIGHT);
        vendorField = new JTextField(30);                
        urlField = new JTextField(30);
        driverField = new JTextField(30);
        userField = new JTextField(30);
        passwordField = new JTextField(30);
        queryField = new JTextField(30);
        
        gridCon.fill=GridBagConstraints.HORIZONTAL;
        //gridCon.weightx=200;
        gridCon.gridx=0;
        gridCon.gridy=0;        
        gridBag.setConstraints(vendorLabel,gridCon);
        comp.add(vendorLabel);

        gridCon.gridx=1;
        gridCon.gridy=0;        
        gridBag.setConstraints(vendorField,gridCon);  
        comp.add(vendorField);       

        gridCon.gridx=0;
        gridCon.gridy=1;                 
        gridBag.setConstraints(urlLabel,gridCon); 
        comp.add(urlLabel); 

        gridCon.gridx=1;
        gridCon.gridy=1;         
        gridBag.setConstraints(urlField,gridCon); 
        comp.add(urlField);                

        gridCon.gridx=0;
        gridCon.gridy=2;         
        gridBag.setConstraints(driverLabel,gridCon); 
        comp.add(driverLabel);

        gridCon.gridx=1;
        gridCon.gridy=2;         
        gridBag.setConstraints(driverField,gridCon); 
        comp.add(driverField); 

        gridCon.gridx=0;
        gridCon.gridy=3;                        
        gridBag.setConstraints(userLabel,gridCon); 
        comp.add(userLabel);

        gridCon.gridx=1;
        gridCon.gridy=3;         
        gridBag.setConstraints(userField,gridCon); 
        comp.add(userField);  

        gridCon.gridx=0;
        gridCon.gridy=4;                       
        gridBag.setConstraints(passwordLabel,gridCon); 
        comp.add(passwordLabel);

        gridCon.gridx=1;
        gridCon.gridy=4;         
        gridBag.setConstraints(passwordField,gridCon); 
        comp.add(passwordField);  

        gridCon.gridx=0;
        gridCon.gridy=5;               
        gridBag.setConstraints(queryLabel,gridCon); 
        comp.add(queryLabel);                                                                              
		
        gridCon.gridx=1;
        gridCon.gridy=5;         
        gridBag.setConstraints(queryField,gridCon);
        
        comp.add(queryField);
         
        container.add(comp,"North");                
    }    
    
    private void loadDBConfig()
    {
    	vendorField.setText(MailSendProperties.getProperty("jdbc.vendor"));
    	urlField.setText(MailSendProperties.getProperty("jdbc.url"));
    	driverField.setText(MailSendProperties.getProperty("jdbc.driver"));
    	userField.setText(MailSendProperties.getProperty("jdbc.user"));
    	passwordField.setText(MailSendProperties.getProperty("jdbc.password"));
    	queryField.setText(MailSendProperties.getProperty("jdbc.query"));    	
    }
    /**
     * db config 저장.
     */
    private void saveDBConfig()
    {
    	MailSendProperties.setProperty("jdbc.vendor",vendorField.getText());
    	MailSendProperties.setProperty("jdbc.url",urlField.getText());
    	MailSendProperties.setProperty("jdbc.driver",driverField.getText());
    	MailSendProperties.setProperty("jdbc.user",userField.getText());
    	MailSendProperties.setProperty("jdbc.password",passwordField.getText());
    	MailSendProperties.setProperty("jdbc.query",queryField.getText());    	    	    	    	
    }
    /**
     * mail config 읽기
     */
    private void loadMailConfig()
    {    	
    	smtpServerField.setText(MailSendProperties.getProperty("smtp.server"));
    	senderEmailField.setText(MailSendProperties.getProperty("sender.email"));
    	testEmailField.setText(MailSendProperties.getProperty("test.email"));
    	testNameField.setText(MailSendProperties.getProperty("test.name"));
    	testIndateField.setText(MailSendProperties.getProperty("test.indate"));
    }
    /**
     * mail config 저장
     */
    private void saveMailConfig()
    {
    	
    	MailSendProperties.setProperty("smtp.server",smtpServerField.getText());
    	MailSendProperties.setProperty("sender.email",senderEmailField.getText());
    	MailSendProperties.setProperty("test.email",testEmailField.getText());
    	MailSendProperties.setProperty("test.name",Util.toEng(testNameField.getText()));
    	MailSendProperties.setProperty("test.indate",testIndateField.getText());   	    	    	    	
    }	

}
