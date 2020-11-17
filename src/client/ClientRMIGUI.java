package client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;

public class ClientRMIGUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;	
	private JPanel textPanel, inputPanel;
	private JTextField textField;
	private String name, message;
	private Font meiryoFont = new Font("Meiryo", Font.PLAIN, 14);
	private Border blankBorder = BorderFactory.createEmptyBorder(10,10,20,10);//top,r,b,l
	private ChatClient3 chatClient;
  private JList<String> list;
  private DefaultListModel<String> listModel;
  
  protected JTextArea textArea, userArea;
  protected JFrame frame;
  protected JButton privateMsgButton, startButton, sendButton;
  protected JPanel clientPanel, userPanel;

	/**
	 * Main method to start client GUI app.
	 * @param args
	 */
	public static void main(String args[]){
		//set the look and feel to 'Nimbus'
		try{
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
				if("Nimbus".equals(info.getName())){
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch(Exception e){
			}
		new ClientRMIGUI();
		}//end main

	/**
	 * GUI Constructor
	 */
	public ClientRMIGUI(){
			
		frame = new JFrame("Client Chat Console");	
	
		//-----------------------------------------
		/*
		 * intercept close method, inform server we are leaving
		 * then let the system exit.
		 */
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        
		    	if(chatClient != null){
			    	try {
			        	sendMessage("Bye all, I am leaving");
			        	chatClient.serverIF.leaveChat(name);
					} catch (RemoteException e) {
						e.printStackTrace();
					}		        	
		        }
		        System.exit(0);  
		    }   
		});
		//-----------------------------------------
		//remove window buttons and border frame
		//to force user to exit on a button
		//- one way to control the exit behaviour
	    //frame.setUndecorated(true);
	    //frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
	
		Container c = getContentPane();
		JPanel outerPanel = new JPanel(new BorderLayout());
		outerPanel.add(getInputPanel(), BorderLayout.CENTER);
		outerPanel.add(getTextPanel(), BorderLayout.NORTH);
		
		c.setLayout(new BorderLayout());
		c.add(outerPanel, BorderLayout.CENTER);
		c.add(getUsersPanel(), BorderLayout.WEST);

		frame.add(c);
		frame.pack();
		frame.setAlwaysOnTop(true);
		frame.setLocation(150, 150);
		textField.requestFocus();
	
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * Method to set up the JPanel to display the chat text
	 * @return
	 */
	public JPanel getTextPanel(){
		String welcome = "Welcome enter your name and press Start to begin\n";
		textArea = new JTextArea(welcome, 14, 34);
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setFont(meiryoFont);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textPanel = new JPanel();
		textPanel.add(scrollPane);
	
		textPanel.setFont(new Font("Meiryo", Font.PLAIN, 14));
		return textPanel;
	}
}