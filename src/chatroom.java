
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.EventListener;

import javax.net.ssl.SSLSocket;
import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class chatroom implements Runnable {
	private JFrame frame;
	private JTextField typea;
	public JTextArea display;
	private JScrollPane scrollPane;
	StringBuilder wtf=new StringBuilder();
	SSLSocket ss;
	BufferedWriter bw;
	String nick;
	StringBuilder msg=new StringBuilder();
	public void run() {
		
		room();
		this.frame.setVisible(true);
		
		try {
			bw = new BufferedWriter(new OutputStreamWriter(ss.getOutputStream()));
			bw.write("NICK " +nick+ '\n');
			bw.flush();
			bw.write("VIEW lobby"+'\n');
			bw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		(new Thread(new msgdisplayer(this.frame, ss, display,msg))).start();
	}
	
	public chatroom(SSLSocket ss,String nick) {
		this.ss=ss;
		this.nick=nick;
	}
	public void room() {
		frame = new JFrame();
		frame.setTitle("chatroom_i");
		frame.setBounds(100, 100, 630, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		typea = new JTextField();
		typea.setHorizontalAlignment(SwingConstants.RIGHT);
		typea.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		typea.setBounds(0, 400, 530, 35);
		frame.getContentPane().add(typea);
		typea.setColumns(10);
		
		JButton btn_send = new JButton("SEND");
		btn_send.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		btn_send.setBounds(530, 385, 95, 35);
		frame.getContentPane().add(btn_send);
		
		JButton btn_clean = new JButton("CLEAN");
		btn_clean.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		btn_clean.setBounds(530, 420, 95, 35);
		frame.getContentPane().add(btn_clean);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 5, 620, 380);
		frame.getContentPane().add(scrollPane);
		
		display = new JTextArea();
		scrollPane.setViewportView(display);
		display.setWrapStyleWord(true);
		display.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		display.setEditable(false);
		
		btn_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btn_send_Click();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btn_clean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btn_clean_Click();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		

		   typea.addKeyListener(new KeyAdapter (){
				
				public void keyPressed(KeyEvent e) {
					switch(e.getKeyCode()) {
					case KeyEvent.VK_ENTER:
					
						try {
							String txt=new String();
							txt=typea.getText()+"\t";
							bw.write("SAY "+txt + '\n');
							bw.flush();
						} catch (IOException e1) {
							
							e1.printStackTrace();
						}
					
					typea.setText("");
					break;
					}
				}
						
				
				});
		
		
	}
		
			
	
	public void btn_send_Click() throws IOException {
		try {
			String txt=new String();
			txt=typea.getText()+"\t";
			bw.write("SAY "+txt + '\n');
			bw.flush();
			typea.setText("");
			
			
		} catch (javax.net.ssl.SSLProtocolException e) {
			errprinter err=new errprinter(3);
			e.printStackTrace();
		} 
	}
		public void btn_clean_Click() throws IOException  {
			msg.delete(0, msg.length());
			bw.write("SAY 【CLEAN】"+'\n');
			bw.flush();

	       }
	
	}


