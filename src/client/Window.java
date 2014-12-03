package client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Window {

	private JFrame frame;
	private JTextField fieldToUser;
	private JTextField fieldFromUserName;
	private JTextField fieldCreateTime;
	private JTextField fieldMsgType;
	private JTextField fieldMsgId;
	private JTextField textHost;
	private JTextField fieldContent;

	private String xmlStr="";
	private JTextArea chatBox;
	
	private static final String LOCAL_HOST="http://localhost:8080/wodinow/wodi";
	private static final String PRO_HOST="http://wodinow.duapp.com/wodi";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				
			}
		});
		frame.setBounds(100, 100, 643, 444);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		fieldToUser = new JTextField();
		fieldToUser.setBounds(192, 10, 183, 21);
		fieldToUser.setText("developerID");
		frame.getContentPane().add(fieldToUser);
		fieldToUser.setColumns(10);
		
		fieldFromUserName = new JTextField();
		fieldFromUserName.setBounds(192, 41, 183, 21);
		fieldFromUserName.setText("userID");
		fieldFromUserName.setColumns(10);
		frame.getContentPane().add(fieldFromUserName);
		
		fieldCreateTime = new JTextField();
		fieldCreateTime.setBounds(192, 72, 183, 21);
		fieldCreateTime.setEditable(false);
		fieldCreateTime.setEnabled(false);
		fieldCreateTime.setColumns(10);
		fieldCreateTime.setText(new java.util.Date().getTime()+"");
		
		frame.getContentPane().add(fieldCreateTime);
		
		fieldMsgType = new JTextField();
		fieldMsgType.setBounds(192, 103, 183, 21);
		fieldMsgType.setEditable(false);
		fieldMsgType.setEnabled(false);
		fieldMsgType.setText("text");
		fieldMsgType.setColumns(10);
		frame.getContentPane().add(fieldMsgType);
		
		fieldMsgId = new JTextField();
		fieldMsgId.setBounds(192, 134, 183, 21);
		fieldMsgId.setText("1234567890123456");
		frame.getContentPane().add(fieldMsgId);
		fieldMsgId.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("toUser");
		lblNewLabel.setBounds(102, 13, 71, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("FromUserName");
		lblNewLabel_1.setBounds(102, 44, 80, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("CreateTime");
		lblNewLabel_2.setBounds(102, 75, 80, 15);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("MsgType");
		lblNewLabel_3.setBounds(102, 106, 71, 15);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("MsgId");
		lblNewLabel_4.setBounds(102, 137, 71, 15);
		frame.getContentPane().add(lblNewLabel_4);
		
		JButton buttomSend = new JButton("send");
		buttomSend.setBounds(491, 373, 93, 23);
		buttomSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 fieldCreateTime.setText(new java.util.Date().getTime()+"");
				 RequestTextMessage msg=new RequestTextMessage();
				 msg.setFromUserName(fieldFromUserName.getText());
				 msg.setToUserName(fieldToUser.getText());
				 msg.setCreateTime(Long.parseLong(fieldCreateTime.getText()));
				 msg.setMsgId(Long.parseLong(fieldMsgId.getText()));
				 msg.setMsgType(fieldMsgType.getText());
				 
				 String content=fieldContent.getText();
				 /*try {
					content = URLEncoder.encode(content, "utf-8");
				} catch (UnsupportedEncodingException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				} */
				 msg.setContent(content);		 
				 
				 xmlStr= XMLRequest.textMessageToXml(msg);
				 System.out.println("-----------request-------\n"+xmlStr);
				 
				 
				 String host=textHost.getText();
				 try {

					 chatBox.append("---------------------\n");
					 chatBox.append("you said:\n"+fieldContent.getText()+"\n\n");
					XMLRequest.postMessage(xmlStr,host,"",Window.this.chatBox);
					fieldContent.setText("");
				} catch (Exception e1) {
					 e1.printStackTrace();
					 StringWriter writer = new StringWriter();
					 e1.printStackTrace( new PrintWriter(writer,true ));
					JOptionPane.showMessageDialog(null, writer.toString());
				}
			}
		});
		frame.getContentPane().add(buttomSend);
		
		textHost = new JTextField();
		textHost.setBounds(434, 41, 183, 21);
		textHost.setText("http://wodinow.duapp.com/wodi");   //http://wodinow.duapp.com/wodi ,//http://localhost:8080/wodinow/wodi
		frame.getContentPane().add(textHost);
		textHost.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("host");
		lblNewLabel_5.setBounds(434, 13, 54, 15);
		frame.getContentPane().add(lblNewLabel_5);
		
		fieldContent = new JTextField();
		fieldContent.setBounds(356, 342, 183, 21);
		frame.getContentPane().add(fieldContent);
		fieldContent.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("content");
		lblNewLabel_6.setBounds(302, 345, 54, 15);
		frame.getContentPane().add(lblNewLabel_6);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 153, 550, 178);
		frame.getContentPane().add(scrollPane);
		
		chatBox = new JTextArea();
		chatBox.setTabSize(100);
		scrollPane.setViewportView(chatBox);
		
		JButton clearButton = new JButton("clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chatBox.setText("");
			}
		});
		clearButton.setBounds(388, 373, 93, 23);
		frame.getContentPane().add(clearButton);
		
		JButton btnSwichHost = new JButton("swich host");
		btnSwichHost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textHost.getText().equals(LOCAL_HOST)){
					textHost.setText(PRO_HOST);
				}
				
				else if(textHost.getText().equals(PRO_HOST)){
						textHost.setText(LOCAL_HOST);
				}
			}
		});
		btnSwichHost.setBounds(524, 71, 93, 23);
		frame.getContentPane().add(btnSwichHost);
	}
}
