package client;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class ErrorPrinter {
	private JFrame frame;
	int i;
	private JTextArea display;
	private JScrollPane scrollPane;
 public ErrorPrinter(int i) {
	 this.i=i;
	 err(i);
 }
 public void err(int i){
	 	frame = new JFrame();
		frame.setTitle("Error");
		frame.setBounds(300, 300, 320, 100);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		JLabel txt = new JLabel();
		txt.setHorizontalAlignment(SwingConstants.CENTER);
		txt.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		txt.setBounds(5, 5, 300, 50);
		frame.getContentPane().add(txt);
		
	if (i==0) {

		
		frame.setTitle("About");
	
		frame.setBounds(200, 200, 500, 300);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 500, 300);
		frame.getContentPane().add(scrollPane);
		
		display = new JTextArea();
		scrollPane.setViewportView(display);
		display.setWrapStyleWord(true);
		display.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		display.setEditable(false);
		
		display.setText(
				"\nThis is our SSL API usage practice.\n" + 
				"This tiny program can use TLS to communicate with others.\n\n"+
				"Source codes are recently available on github: \n"	+
				"https://github.com/jeffry1829/ \n"+
				"If you want to launch your own server, \n"+
				"there you can also find the files you need! \n\n"+
				"Contact us if you have any problem: \n"+
				"Jeffry : jeffry1829@gmail.com \n"+
				"Joseph : j9263178@gmail.com");
		
		
	}	
		
	if (i==1) {
		txt.setText("Connection failed!");
	}
	if(i==2) {
		txt.setText("Please check your Ip or Password!");
	}
	if(i==3) {
		txt.setText("Please check your Ip or Password!");
	}
 }
}
