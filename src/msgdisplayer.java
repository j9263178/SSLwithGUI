

import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLSocket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class msgdisplayer implements Runnable {
	SSLSocket ss;
	boolean iscon=true;
	private JTextArea display;
	StringBuilder msg;
	//private JFrame frame;
	


	public msgdisplayer(JFrame frame, SSLSocket ss, JTextArea jta,StringBuilder msg) {
		this.ss = ss;
		/*
		frame = new JFrame();
		frame.setTitle("chatroom_i");
		frame.setBounds(0, 0, 640, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);*/
		this.msg=msg;
		display = jta;
		//frame.add(display);
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(ss.getInputStream()));
			String line;
			
			while (true) {
				
				while ((line = br.readLine()) != null) {
					msg.append(line+"\n");
					display.setText(msg.toString());
				}
			
				}
		} catch (IOException e) {
			e.printStackTrace();
			
	 }
	}
}
