package com.monosun.jmailing.main;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.net.URL;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import javax.swing.undo.UndoManager;

import monosun.gui.RunStateBar;

import com.monosun.jmailing.data.MailSendQueue;
import com.monosun.jmailing.event.*;
import com.monosun.jmailing.gui.*;
import com.monosun.jmailing.reader.DBReader;
import com.monosun.jmailing.res.*;
import com.monosun.jmailing.sender.MailSender;
import com.monosun.jmailing.util.*;


/**
 * @author jini
 *
 */
public class JMailingFrame extends JFrame implements RunStateBar
{
	
	//////////////////////////////////////////////
	int mailSendCount=0;
	//toolbar

    //private JTextComponent editor;
    private Hashtable commands=new Hashtable();
    private Hashtable menuItems;
    private JToolBar toolbar;
    private JComponent status;

    /**
     * Listener for the edits on the current document.
     */
    protected UndoableEditListener undoHandler = new UndoHandler(this);

    /** UndoManager that we add edits to. */
    protected UndoManager undo = new UndoManager();	
    private UndoAction undoAction = new UndoAction(this);
    private RedoAction redoAction = new RedoAction(this);    	

    /**
     * Suffix applied to the key used in resource file
     * lookups for an image.
     */
    public static final String imageSuffix = "Image";

    /**
     * Suffix applied to the key used in resource file
     * lookups for a label.
     */
    public static final String labelSuffix = "Label";

    /**
     * Suffix applied to the key used in resource file
     * lookups for an action.
     */
    public static final String actionSuffix = "Action";

    /**
     * Suffix applied to the key used in resource file
     * lookups for tooltip text.
     */
    public static final String tipSuffix = "Tooltip";

    public static final String openAction = "open";
    public static final String newAction  = "new";
    public static final String saveAction = "save";
    public static final String exitAction = "exit";         
        
	//jframe's panel
	JPanel contentPane;
	//main panel
	JPanel mainPane;
	JPanel mailTopPane,mailBottomPane,iconLeftPane;	
	//terminalpane
	JPanel terminalPane;
	//menu bar
	JMenuBar menuBar;
	
	//terminal
	JTextArea terminalTA;
	JScrollPane terminalTAPane;
	
	//mail ui
	JLabel subjectLabel;
	JTextField subjectField;
	JButton testButton,sendButton,cancelButton,previewButton;
	JTextArea mailTA;
	JScrollPane mailTAPane;
	JRadioButton textRButton,htmlRButton;
	ButtonGroup typeGroup;
	
	/**
	 * 세부창들 
	 */
	MailSendAboutDialog aboutDlg=new MailSendAboutDialog("About",this);
	MailSendHelpDialog helpDlg=new MailSendHelpDialog("Help",this);
	MailPreferenceDialog prefDlg= new MailPreferenceDialog("Preferences",this);
	HtmlPreviewDialog htmlDlg= new HtmlPreviewDialog("Preview",this);
				
	/**
	 * 메일 Resource 설정
	 */
	MailResource resource= new MailResource();
	/**
	 * 메일 설정 값들
	 */
	MailSendProperties props=new MailSendProperties();
	/**
	 * 메일 전송등.. 정보 출력
	 */
	MailSendLog logger;
	/**
	 * DB에서 데이터 읽기
	 */	
	DBReader reader;
	//size
	public static final int THREAD_SIZE=10;
	MailSendQueue[] queueList= new MailSendQueue[THREAD_SIZE];
	MailSender sender[]=new MailSender[THREAD_SIZE];
	MailSender tester;
	/**
	 * 기본 Action
	 */
    private Action[] defaultActions = {
		new NewAction(this),
		new OpenAction(this),
		new SaveAction(this),	
		new ExitAction(this),
		new PreferenceAction(this),
		new CutAction(this),
		new CopyAction(this),
		new PasteAction(this),
		new HelpAction(this),
		new AboutAction(this),
	    new UndoAction(this),
	    new RedoAction(this)
    };
    	
	/**
	 * 생성자
	 * GUI, Queue, Logger 초기화
	 */
	public JMailingFrame()
	{	
		//창의 아이콘 
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/").getPath()+"resources/jmailing.gif"));				
		//GUI 설정
		initUI();
		//Queue 초기화
		InitQueueList();

	}

	/**
	 * return UndoAction
	 */
	public UndoAction getUndoAction()
	{
		return undoAction;
	}	
	/**
	 * return UndoManager
	 */
	public UndoManager getUndoManager()
	{
		return undo;
	}
	/**
	 * return RedoAction
	 */
	public RedoAction getRedoAction()
	{
		return redoAction;
	}
	/**
	 * return UndoableEditorListener
	 */
	public UndoableEditListener getUndoableEditListener()
	{
		return undoHandler;
	}		
	////////////////////////////////////////////////////////////////////////////////
    /**
     * Create an editor to represent the given document.  
     */
//    protected void createEditor() 
//    {
//		editor = new JTextArea();
//		editor.setDragEnabled(true);
//		editor.setFont(new Font("monospaced", Font.PLAIN, 12));
//    }	
	
	////////////////////////////////////////////////////////////////////////////////
	/**
	 * 큐를 초기화
	 */
	private void InitQueueList()
	{
		for(int i=0;i<THREAD_SIZE;i++)
			queueList[i]=new MailSendQueue(this,i,500);
	}
	/**
	 * GUI 초기화
	 */
	private void initUI()
	{
				
		//전체 Pane
		contentPane = (JPanel)getContentPane();
				
		//jframe setting
		setSize(new Dimension(650, 600));		
		setTitle("Mail Sender Tool");	

		mainPane= new JPanel();		
		mainPane.setLayout(new BorderLayout());
		
		mailTopPane = new JPanel();
		mailBottomPane = new JPanel();
		
		subjectLabel = new JLabel("Subject:");
		subjectField = new JTextField(40);
		subjectField.setBackground(new Color(100,100,160));
		//focus가 갔을때 배경색이 바뀜.
		subjectField.addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent e)
			{
				subjectField.setBackground(Color.white);
			}
			
			public void focusLost(FocusEvent e)
			{
				subjectField.setBackground(new Color(100,100,160));				
			}
		});
		testButton = new JButton("Send mail to Test Account");
		testButton.addActionListener(new ActionListener() {
		
		    public void actionPerformed(ActionEvent actionevent)
		    {
				actionTestButton();
		    }
		
		});		
		sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
		
		    public void actionPerformed(ActionEvent actionevent)
		    {
				actionSendButton();
		    }
		
		});
		cancelButton = new JButton("Cancel");
		cancelButton.setEnabled(false);
		cancelButton.addActionListener(new ActionListener() {
		
		    public void actionPerformed(ActionEvent actionevent)
		    {
				actionCancelButton();
		    }
		
		});
		previewButton = new JButton("Preview");
		previewButton.setEnabled(true);
		previewButton.addActionListener(new ActionListener() {
		
		    public void actionPerformed(ActionEvent actionevent)
		    {
				actionPreviewButton();
		    }
		
		});					
		//mail top
		
		textRButton = new JRadioButton("Text");
		textRButton.setActionCommand("Text");
		htmlRButton = new JRadioButton("html",true);
		htmlRButton.setActionCommand("Html");
		
		typeGroup= new ButtonGroup();
		typeGroup.add(htmlRButton);
		typeGroup.add(textRButton);		
		
		mailTopPane.setLayout(new FlowLayout());		
		mailTopPane.add(subjectLabel);		
		mailTopPane.add(subjectField);
		mailTopPane.add(htmlRButton);		
		mailTopPane.add(textRButton);
		
		//mail center
		mailTA= new JTextArea();
		mailTA.setDragEnabled(true);
		mailTA.getDocument().addUndoableEditListener(undoHandler);
		mailTA.setBackground(new Color(100,100,160));		
		mailTAPane = new JScrollPane(mailTA);
		mailTA.addFocusListener(new FocusAdapter()
		{			
			public void focusGained(FocusEvent e)
			{
				mailTA.setBackground(Color.white);				
			}
			
			public void focusLost(FocusEvent e)
			{
				mailTA.setBackground(new Color(100,100,160));
			}
		});
		Action[] actions = getActions();
		System.out.println(""+Action.NAME);
		System.out.println("length="+actions.length);
		//Action들을 hashtable에 입력함..
		for (int i = 0; i < actions.length; i++) {
		    Action a = actions[i];
		    //System.out.println("=="+a.getValue(Action.NAME));		    
		    commands.put(a.getValue(Action.NAME), a);
		}
						
		//mail bottom
		mailBottomPane.setLayout(new FlowLayout());
		
		mailBottomPane.add(testButton);
		mailBottomPane.add(sendButton);
		mailBottomPane.add(cancelButton);
		mailBottomPane.add(previewButton);
		
		//mailTopPane.add(createToolbar());
		JPanel mailPane= new JPanel();
		mailPane.setLayout(new BorderLayout());
						
		mailPane.add(mailTopPane,"North");
		mailPane.add(mailTAPane,"Center");
		mailPane.add(mailBottomPane,"South");
		
		mainPane.add(createToolbar(),"North");
		mainPane.add(mailPane,"Center");
		
		//main setting
		mainPane.setPreferredSize(new Dimension(650,400));		
		//setMenu();			
        //createEditor();
		// Add this as a listener for undoable edits.
		mailTA.getDocument().addUndoableEditListener(undoHandler);  		
		
			
		menuItems = new Hashtable();
		menuBar = createMenubar();
				
		//setMail();
				
		setTerminal();        
		// 전체 GUI 모양 	
        contentPane.add(menuBar,"North");
        contentPane.add(mainPane,"Center");
        contentPane.add(terminalPane,"South");
        
        this.addWindowListener(new WindowAdapter(){
        	
        	public void windowClosing(WindowEvent e)
        	{
        		System.exit(0);
        	}        
        		
        });                       
	}
	public JComponent getStatusPanel()
	{
		return status;
	}	
	public JPanel getMainPanel()
	{
		return contentPane;
	}
    /** 
     * Fetch the editor contained in this panel
     */
    public JTextComponent getEditor() 
    {
		return mailTA;
    }
 
    /**
     * Find the hosting frame, for the file-chooser dialog.
     */
    protected Frame getFrame() 
    {
		for (Container p = getParent(); p != null; p = p.getParent()) {
		    if (p instanceof Frame) {
			return (Frame) p;
		    }
		}
		return null;
    }       

    /**
     * Fetch the list of actions supported by this
     * editor.  It is implemented to return the list
     * of actions supported by the embedded JTextComponent
     * augmented with the actions defined locally.
     */
    public Action[] getActions() 
    {    	
		return TextAction.augmentList(mailTA.getActions(), defaultActions);
    }
    	
    protected Action getAction(String cmd) 
    {
		return (Action) commands.get(cmd);
    }	
	/**
	 * Terminal 창 :log 출력 부분
	 */
	private void setTerminal()
	{
		status= new StatusBar();	
		//terminal setting
		terminalPane= new JPanel();
		terminalPane.setLayout(new BorderLayout());
		terminalTA = new JTextArea();
		terminalTAPane = new JScrollPane(terminalTA);
		
		terminalTAPane.setPreferredSize(new Dimension(650,150));
		terminalTA.setEditable(false);
		terminalTA.setWrapStyleWord(true);
		terminalTA.setRows(5);
		terminalTA.setLineWrap(true);
		terminalTA.setBackground(Color.black);
		terminalTA.setMinimumSize(new Dimension(0, 0));
		terminalTA.setForeground(Color.white);        
		
		terminalTA.addComponentListener(new ComponentAdapter() {
		
		    public void componentResized(ComponentEvent componentevent)
		    {
				actionTerminalTA(componentevent);
		    }
		
		});
		
		terminalPane.add(terminalTAPane,"Center");
		terminalPane.add(status,"South");		
		
		logger=new MailSendLog(terminalTA);		

		//logger.start();
		logger.printLog(resource.getMsg("PROGRAMER_INFO"));
		props.listProperty(logger);		
	}

    /**
     * resource파일에서 정보를 읽어서 menubar를 생성한다.
     */
    protected JMenuBar createMenubar() {
		JMenuItem mi;
		JMenuBar mb = new JMenuBar();
	
		String[] menuKeys = tokenize(getResource("menubar"));
		//menubar=file edit help
		for (int i = 0; i < menuKeys.length; i++) {
		    JMenu m = createMenu(menuKeys[i]);
		    if (m != null) {
			mb.add(m);
		    }
		}
		return mb;
    }
    /**
     * Menu당 생성
     */	
    protected JMenu createMenu(String key) 
    {
		String[] itemKeys = tokenize(getResource(key));
		// file=new open save - preference - exit
		JMenu menu = new JMenu(getResource(key + labelSuffix));
		for (int i = 0; i < itemKeys.length; i++) {
		    if (itemKeys[i].equals("-")) {
				menu.addSeparator();
		    } else {
				JMenuItem mi = createMenuItem(itemKeys[i]);
				menu.add(mi);
		    }
		}
		return menu;
    }	
    /**
     * menuitem을 생성하고 menuitem들을 hashtable에 추가한다.
     */
    protected JMenuItem createMenuItem(String cmd) 
    {
		JMenuItem mi = new JMenuItem(getResource(cmd + labelSuffix));
//	        URL url = getResource(cmd + imageSuffix);
//		if (url != null) {
//		    mi.setHorizontalTextPosition(JButton.RIGHT);
//		    mi.setIcon(new ImageIcon(url));
//		}
		//System.out.println("#"+cmd + actionSuffix);
		String astr = getResource(cmd + actionSuffix);
		//System.out.println("#="+astr);
		if (astr == null) {
		    astr = cmd;
		}
		mi.setActionCommand(astr);
		Action a = getAction(astr);
		
		if (a != null) {
		    mi.addActionListener(a);
		    a.addPropertyChangeListener(createActionChangeListener(mi));
		    mi.setEnabled(a.isEnabled());		    
		} else {
			//System.out.println(astr+"=false");
		    mi.setEnabled(false);
		}
		menuItems.put(cmd, mi);
		return mi;
    }
    // Yarked from JMenu, ideally this would be public.
    protected PropertyChangeListener createActionChangeListener(JMenuItem b) 
    {
		return new ActionChangedListener(b);
    }

    // Yarked from JMenu, ideally this would be public.
    private class ActionChangedListener implements PropertyChangeListener {
        JMenuItem menuItem;
        
        ActionChangedListener(JMenuItem mi) {
            super();
            this.menuItem = mi;
        }
        public void propertyChange(PropertyChangeEvent e) {
            String propertyName = e.getPropertyName();
            if (e.getPropertyName().equals(Action.NAME)) {
                String text = (String) e.getNewValue();
                menuItem.setText(text);
            } else if (propertyName.equals("enabled")) {
                Boolean enabledState = (Boolean) e.getNewValue();
                menuItem.setEnabled(enabledState.booleanValue());
            }
        }
    }        
	/**
	 * 로그 출력
	 */
	public void printLog(String msg)
	{
		logger.printLog(msg);
	}
    /**
     * toolbar를 생성한다. 디폴트로 toolbar로 정의된 
     * resource 파일에서 정보를 읽어들인다.
     */
    private Component createToolbar() 
    {
		toolbar = new JToolBar();
		//toolbar=new open save - cut copy paste
		String[] toolKeys = tokenize(getResource("toolbar"));
		for (int i = 0; i < toolKeys.length; i++) 
		{
			// -와 같을 때 수평 기둥 생성
		    if (toolKeys[i].equals("-")) 
		    {
				toolbar.add(Box.createHorizontalStrut(5));
		    } else 
		    {
				toolbar.add(createTool(toolKeys[i]));
		    }
		}
		toolbar.add(Box.createHorizontalGlue());
		return toolbar;
    }
    /**
     * resource에서 읽어 들임
     */
	private String getResource(String key)
	{
		return MailResource.getMsg(key);
	}	
    /**
     * Hook through which every toolbar item is created.
     */
    private Component createTool(String key) 
    {
		return createToolbarButton(key);
    }

    /**
     * Create a button to go inside of the toolbar.  By default this
     * will load an image resource.  The image filename is relative to
     * the classpath (including the '.' directory if its a part of the
     * classpath), and may either be in a JAR file or a separate file.
     * 
     * @param key The key in the resource file to serve as the basis
     *  of lookups.
     */
    protected JButton createToolbarButton(String key) {		
	    JButton b = new JButton(new ImageIcon(this.getClass().getResource("/").getPath()+getResource(key + imageSuffix))) 
	    {
	    	public float getAlignmentY() { return 0.5f; }
		};
	        b.setRequestFocusEnabled(false);
	        b.setMargin(new Insets(1,1,1,1));

		String astr = getResource(key + actionSuffix);
		if (astr == null) {
		    astr = key;
		}
		Action a = getAction(astr);
		if (a != null) {
		    b.setActionCommand(astr);
		    b.addActionListener(a);
		} else {
		    b.setEnabled(false);
		}
	
		String tip = getResource(key + tipSuffix);
		if (tip != null) {
		    b.setToolTipText(tip);
		}

        return b;
    }    
	/**
	 * click send button
	 */
	public void actionSendButton()
	{	
		reader = new DBReader(this,queueList);
		reader.setPriority(Thread.MAX_PRIORITY);
		reader.start();	
		
		UIEnable(false);
		//실행	
		for(int i=0;i<sender.length;i++)
		{
			sender[i]=new MailSender(this,queueList[i]);
			sender[i].setPriority(Thread.NORM_PRIORITY);
			sender[i].start();
		}	
		
	}
	/**
	 * click send button
	 */
	public void actionTestButton()
	{				
		//실행	
		tester = new MailSender(this,queueList[0]);	
		tester.sendTestMail();	
		
	}
	public void actionPreviewButton()
	{	
					
		htmlDlg.pack();
		htmlDlg.setSize(400,300);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dimension1 = htmlDlg.getSize();
		if(dimension1.height > dimension.height)
		    dimension1.height = dimension.height;
		if(dimension1.width > dimension.width)
		    dimension1.width = dimension.width;
		htmlDlg.setLocation((dimension.width - dimension1.width) / 2, (dimension.height - dimension1.height) / 2);         	
		htmlDlg.setText(mailTA.getText());
/*		try
		{
			htmlDlg.setPage(new URL("http://java.sun.com"));
		}
		catch (Exception e)
		{
		}
*/
		htmlDlg.setModal(true);
		htmlDlg.setVisible(true);
						
	}	
	public void UIEnable(boolean enable)
	{
		cancelButton.setEnabled(!enable);		
		subjectLabel.setEnabled(enable);
		sendButton.setEnabled(enable);
		testButton.setEnabled(enable);
		textRButton.setEnabled(enable);
		htmlRButton.setEnabled(enable);
		subjectField.setEditable(enable);
		mailTA.setEditable(enable);
	}
	/**
	 * click cancel button
	 */
	public void actionCancelButton()
	{
		UIEnable(true);		
		
		reader.stopRun(true);
		for(int i=0;i<sender.length;i++)
		{
			sender[i].stopRun(false);
		}	
		
		try
		{
			//queue.removeAll();
			for(int i=0;i<queueList.length;i++)
				queueList[i].removeAll();
			//clearCount();
		}
		catch(InterruptedException ie)
		{
		}
	}

	/**
	 * click Help -> About
	 */
	public void actionMenuAbout()
	{
		aboutDlg.pack();
		aboutDlg.setSize(400,300);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dimension1 = aboutDlg.getSize();
		if(dimension1.height > dimension.height)
		    dimension1.height = dimension.height;
		if(dimension1.width > dimension.width)
		    dimension1.width = dimension.width;
		aboutDlg.setLocation((dimension.width - dimension1.width) / 2, (dimension.height - dimension1.height) / 2);         	
		aboutDlg.setModal(true);
		aboutDlg.setVisible(true);
	}
	/**
	 * click Help -> Help
	 */
	public void actionMenuHelp()
	{
		helpDlg.pack();
		helpDlg.setSize(400,300);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dimension1 = helpDlg.getSize();
		if(dimension1.height > dimension.height)
		    dimension1.height = dimension.height;
		if(dimension1.width > dimension.width)
		    dimension1.width = dimension.width;
		helpDlg.setLocation((dimension.width - dimension1.width) / 2, (dimension.height - dimension1.height) / 2);         	
		helpDlg.setModal(true);
		helpDlg.setVisible(true);
	}	
	/**
	 * 
	 */
	public void actionTerminalTA(ComponentEvent componentevent)
	{
		Rectangle rectangle = componentevent.getComponent().getBounds();
		rectangle.setLocation((int)rectangle.getX(), (int)((rectangle.getY() + rectangle.getHeight()) - 10D));
		rectangle.setSize((int)rectangle.getWidth(), 10);
		terminalTAPane.getViewport().scrollRectToVisible(rectangle);
	}
	/**
	 * click menu file->proferences
	 */
	public void actionMenuProp()
	{
		prefDlg.pack();
		prefDlg.setSize(500,400);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dimension1 = prefDlg.getSize();
		if(dimension1.height > dimension.height)
		    dimension1.height = dimension.height;
		if(dimension1.width > dimension.width)
		    dimension1.width = dimension.width;
		prefDlg.setLocation((dimension.width - dimension1.width) / 2, (dimension.height - dimension1.height) / 2);         	
		prefDlg.setModal(true);
		prefDlg.setVisible(true);
	}
	/**
	 * 
	 */	
	public String getMailContent()
	{
		return mailTA.getText();
	}
	public boolean isHtml()
	{
		return (htmlRButton.isSelected());
	}
	public String getSubject()
	{
		return subjectField.getText();
	}
    /**
     * 문자열에서 whitespace로 문자들을 나누어서 배열로 리턴한다.
     */
    protected String[] tokenize(String input) 
    {
		return Util.tokenize(input);
    }
    /**
     * 전송한 메일 수
     */
    public synchronized void count()
    {
    	mailSendCount++;
    	this.printLog("Sent Mail Count="+mailSendCount);
    }
    /**
     * 보낸 메일 수를 0으로 초기화
     */	
    public synchronized void clearCount()
    {
    	mailSendCount=0;
    }    	
}
