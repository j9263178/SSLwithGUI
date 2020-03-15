package server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.HashMap;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;


public class MultiThreadServer implements Runnable {
	SSLSocket ss;
	public static ChatroomServer lobby;
	//public static HashMap<SSLSocket, chatroom_server> ss_current_channels = new HashMap<SSLSocket, chatroom_server>();
	public static HashMap<SSLSocket, String> ss_nick = new HashMap<SSLSocket, String>();

	public MultiThreadServer(SSLSocket ss) {
		this.ss = ss;
	}

	/*
	 * args0 = listen port 
	 * args1 = password
	 */

	public static void main(String args[]) throws Exception {
		lobby = new ChatroomServer("lobby");

		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream("./server.cer"), args[1].toCharArray());

		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(ks, args[1].toCharArray());

		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(kmf.getKeyManagers(), null, SecureRandom.getInstanceStrong());

		SSLServerSocketFactory sssf = sc.getServerSocketFactory();
		SSLServerSocket sss = (SSLServerSocket) sssf.createServerSocket(Integer.parseInt(args[0]));

		System.out.println("\n\nServer created sucessfully!");
		System.out.println("\nwaiting for connecting...\n\n");
		
		while (true) {
			SSLSocket ss = (SSLSocket) sss.accept();
			System.out.println("A new client connected!");
			//System.out.println(ss.hashCode());
			(new Thread(new MultiThreadServer(ss))).start();
		}
	}

	@Override
	public void run() {
		boolean stop = false;
		try {

			if(lobby.online_ips.contains(ss.getLocalAddress().toString())){
				ss_nick.remove(ss);
				ss.close();
				System.out.println(ss_nick.get(ss)+" has left.");
				lobby.appendLog(ss_nick.get(ss)+" has left.");
				stop = true;
			}else{
				lobby.appendLog("A new client connected!");
				lobby.UserJoin(ss);
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(ss.getInputStream()));
			//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ss.getOutputStream()));
			
			String in;
			while (!stop) {
				while ((in = br.readLine()) != null ) {
					
					String[] parsed_in = Parser.parse(in);
					
					switch (parsed_in[0]) {
						case "":
							lobby.appendLog(ss_nick.get(ss) + "> " + parsed_in[1]);
							break;

						case "NICK":
							lobby.appendLog(
									"Now " + ss_nick.get(ss) + " has changed his(her) nickname" + " to " + parsed_in[1]+"!");
							ss_nick.put(ss, parsed_in[1]);
							break;

						case "EXIT":
							System.out.println(ss_nick.get(ss)+" has left.");
							lobby.UserLeave(ss);
							ss_nick.remove(ss);
							ss.close();
							stop = true;
					default:
						break;
					}

				}
			}

		} catch (SocketException se){
			try {
				lobby.UserLeave(ss);
				ss_nick.remove(ss);
				ss.close();
				Thread.currentThread().interrupt();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
}
