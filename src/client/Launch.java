package client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Launch {
	private static Launch window;
	private static JFrame frame;
	private JTextField t1;
	private JTextField t2;
	private JTextField t3;
	private JTextField t4;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new Launch();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Launch() {
		initialize();
		}
	
	private void initialize() {
		frame = new JFrame();

		frame.setVisible(true);
		frame.setTitle("login");
		frame.setBounds(100, 100, 380, 320);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		
		JLabel l1 = new JLabel("ip:");
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		l1.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		l1.setBounds(33, 18, 80, 35);
		frame.getContentPane().add(l1);
		
		JLabel l2 = new JLabel("port:");
		l2.setHorizontalAlignment(SwingConstants.CENTER);
		l2.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		l2.setBounds(33, 18, 80, 135);
		frame.getContentPane().add(l2);
		
		JLabel l3 = new JLabel("password:");
		l3.setHorizontalAlignment(SwingConstants.LEFT);
		l3.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		l3.setBounds(33, 18, 180, 235);
		frame.getContentPane().add(l3);
		
		JLabel l4 = new JLabel("nickname:");
		l4.setHorizontalAlignment(SwingConstants.LEFT);
		l4.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		l4.setBounds(33, 18, 180, 335);
		frame.getContentPane().add(l4);

		
		JButton btnRun = new JButton("Connect");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnRun_Click();
				} catch (KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException
						| IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton info = new JButton("About");
		info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					info_Click();
			
				}
			
		});
		
		
		
		btnRun.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		btnRun.setBounds(100, 220, 185, 32);
		frame.getContentPane().add(btnRun);
		
		info.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		info.setBounds(210, 260, 185, 32);
		frame.getContentPane().add(info);


		t1 = new JTextField();
		t1.setHorizontalAlignment(SwingConstants.RIGHT);
		t1.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		t1.setBounds(124, 20, 155, 31);
		frame.getContentPane().add(t1);
		t1.setColumns(10);
		
		t2 = new JTextField();
		t2.setHorizontalAlignment(SwingConstants.RIGHT);
		t2.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		t2.setBounds(124, 70, 155, 31);
		frame.getContentPane().add(t2);
		t2.setColumns(10);
		
		t3 = new JTextField();
		t3.setHorizontalAlignment(SwingConstants.RIGHT);
		t3.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		t3.setBounds(124, 120, 155, 31);
		frame.getContentPane().add(t3);
		t3.setColumns(10);
		
		t4 = new JTextField();
		t4.setHorizontalAlignment(SwingConstants.RIGHT);
		t4.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		t4.setBounds(124, 170, 155, 31);
		frame.getContentPane().add(t4);
		t4.setColumns(10);
	}
	
	protected void btnRun_Click() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, KeyManagementException {
		// 從視窗取得資料
		String ip=t1.getText();
		String port=t2.getText();
		String key=t3.getText();
		String nick=t4.getText();
		try {
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream("./server.cer"), key.toCharArray());

		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(ks);

		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, tmf.getTrustManagers(), SecureRandom.getInstanceStrong());

		SSLSocketFactory sf = sc.getSocketFactory();
		SSLSocket ss = (SSLSocket) sf.createSocket(ip, Integer.parseInt(port));
		(new Thread(new ChatroomClient(ss,nick))).start();
		frame.setVisible(false);
		}catch(Exception e) {
			ErrorPrinter err=new ErrorPrinter(1);
			e.printStackTrace();
		}
		
		
		
	}
	
	protected void info_Click(){
		ErrorPrinter err =new ErrorPrinter(0);
	}

}