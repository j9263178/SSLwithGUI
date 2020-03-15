package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLSocket;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class MsgDisplay implements Runnable {
	private SSLSocket ss;
	private JTextArea display;
	private StringBuilder msg;

	public MsgDisplay(JFrame frame, SSLSocket ss, JTextArea jta, StringBuilder msg) {
		this.ss = ss;
		this.msg=msg;
		display = jta;
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
