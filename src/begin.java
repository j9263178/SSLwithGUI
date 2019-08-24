import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
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


public class begin {
	private JFrame frame;
	private JTextField t1;
	private JTextField t2;
	private JTextField t3;
	private JTextField t4;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					begin window = new begin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public begin() {
		initialize();
		}
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("aaaaa .1");
		frame.setBounds(100, 100, 300, 320);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		
		JLabel l1 = new JLabel("ip:");
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		l1.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		l1.setBounds(33, 18, 50, 35);
		frame.getContentPane().add(l1);
		
		JLabel l2 = new JLabel("port:");
		l2.setHorizontalAlignment(SwingConstants.CENTER);
		l2.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		l2.setBounds(33, 18, 50, 135);
		frame.getContentPane().add(l2);
		
		JLabel l3 = new JLabel("密碼:");
		l3.setHorizontalAlignment(SwingConstants.CENTER);
		l3.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		l3.setBounds(33, 18, 50, 235);
		frame.getContentPane().add(l3);
		
		JLabel l4 = new JLabel("暱稱:");
		l4.setHorizontalAlignment(SwingConstants.CENTER);
		l4.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		l4.setBounds(33, 18, 50, 335);
		frame.getContentPane().add(l4);

		
		JButton btnRun = new JButton("連線");
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
		btnRun.setBounds(100, 220, 85, 32);
		frame.getContentPane().add(btnRun);
		
		info.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		info.setBounds(210, 260, 85, 32);
		frame.getContentPane().add(info);


		t1 = new JTextField();
		t1.setHorizontalAlignment(SwingConstants.RIGHT);
		t1.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		t1.setBounds(94, 20, 155, 31);
		frame.getContentPane().add(t1);
		t1.setColumns(10);
		
		t2 = new JTextField();
		t2.setHorizontalAlignment(SwingConstants.RIGHT);
		t2.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		t2.setBounds(94, 70, 155, 31);
		frame.getContentPane().add(t2);
		t2.setColumns(10);
		
		t3 = new JTextField();
		t3.setHorizontalAlignment(SwingConstants.RIGHT);
		t3.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		t3.setBounds(94, 120, 155, 31);
		frame.getContentPane().add(t3);
		t3.setColumns(10);
		
		t4 = new JTextField();
		t4.setHorizontalAlignment(SwingConstants.RIGHT);
		t4.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		t4.setBounds(94, 170, 155, 31);
		frame.getContentPane().add(t4);
		t4.setColumns(10);
	}
	
	protected void btnRun_Click() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, KeyManagementException {
		// 從視窗取得資料

		if(t4.getText()=="") {
			t4.setText("Unset");
		}else {}
		
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
		(new Thread(new chatroom(ss,nick))).start();
		}catch(Exception e) {
			errprinter err=new errprinter(1);
			e.printStackTrace();
		}
		
		
		
	}
	
	protected void info_Click(){
		errprinter err =new errprinter(0);
	}

}