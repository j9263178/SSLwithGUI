package client;

import java.awt.Font;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLSocket;
import javax.swing.*;

public class ChatroomClient implements Runnable {
	private JFrame frame;
	private JTextField typea;
	private JTextArea display;
	private SSLSocket ss;
	private BufferedWriter bw;
	private String nick;
	private StringBuilder msg=new StringBuilder();
	public void run() {
		
		room();

		this.frame.setVisible(true);
		
		try {
			bw = new BufferedWriter(new OutputStreamWriter(ss.getOutputStream()));
			if(nick.equals("")) nick="anonymous";
			bw.write("/NICK " +nick+ '\n');
			bw.flush();
		} catch (javax.net.ssl.SSLProtocolException e) {
			ErrorPrinter err=new ErrorPrinter(3);
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

		(new Thread(new MsgDisplay(this.frame, ss, display,msg))).start();
	}
	
	ChatroomClient(SSLSocket ss, String nick) {
		this.ss=ss;
		this.nick=nick;
	}

	private void room() {
		frame = new JFrame();
		frame.setTitle("chatroom_i");
		frame.setBounds(100, 100, 630, 480);

		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		WindowListener exitListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showOptionDialog(
						null, "Are You Sure to Close Application?",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (confirm == 0) {
					try {
						bw.write("/EXIT");
						bw.flush();
						System.exit(0);
					} catch (IOException ex) {
						ex.printStackTrace();
					}

				}
			}
		};
		frame.addWindowListener(exitListener);

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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 5, 620, 380);
		frame.getContentPane().add(scrollPane);
		
		display = new JTextArea();
		scrollPane.setViewportView(display);
		display.setWrapStyleWord(true);
		display.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		display.setEditable(false);
		
		btn_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					btn_send_Click();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		btn_clean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					btn_clean_Click();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		typea.addKeyListener(new KeyAdapter (){
				public void keyPressed(KeyEvent ke) {
					switch(ke.getKeyCode()) {
					case KeyEvent.VK_ENTER:
						try {
							btn_send_Click();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					break;
					}
				}
		});


	}

	private void btn_send_Click() throws IOException {
		try {

			bw.write(typea.getText()+"\t"+'\n');
			bw.flush();
			typea.setText("");

		} catch (javax.net.ssl.SSLProtocolException e) {
			ErrorPrinter err=new ErrorPrinter(3);
			e.printStackTrace();
		}catch(javax.net.ssl.SSLHandshakeException e2){
			ErrorPrinter err=new ErrorPrinter(3);
			e2.printStackTrace();
		}
	}

	private void btn_clean_Click() throws IOException  {
			msg.delete(0, msg.length());
	}

	}


