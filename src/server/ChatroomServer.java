package server;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import javax.net.ssl.SSLSocket;


public class ChatroomServer {
    private ArrayList<SSLSocket> online_sslsockets = new ArrayList<SSLSocket>();
    public ArrayList<String> online_ips = new ArrayList<String>();
   // private String name = "";
    private String log = "";

    public ChatroomServer(String ch_name){
        /*if (!isCreated(ch_name)) {
            this.name = ch_name;
        } else {
            throw new ChatroomExistsException("this chatroom is exists, you cannot re-create it");
        }*/
    }

    public String getLog(int line_amount) {
        String[] lines = log.split("\n");
        int offset = lines.length - line_amount < 0 ? 0 : lines.length - line_amount;
        String result = "";
        for (int i = offset; i < lines.length; i++) {
            result = result.concat(lines[i] + '\n');
        }
        return result;
    }

    public ArrayList<SSLSocket> getSSLSockets() {
        return online_sslsockets;
    }

    public void messageUsers(String message) throws IOException {

        //#debug
        //System.out.println(online_sslsockets.size());
        for (SSLSocket ss : online_sslsockets) {
            if (ss.isClosed()) {
                continue;
            }

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ss.getOutputStream()));
            bw.write(message + '\n');
            bw.flush();
        }
    }

    public void appendLog(String line) throws IOException {
        log = log.concat(line + '\n');
        messageUsers(line);
    }

    /*public static boolean isCreated(String channel) {
        Iterator<chatroom_server> it = chatroom_list.iterator();
        while (it.hasNext()) {
            if (it.next().getName().equals(channel)) {
                return true;
            }
        }
        return false;
    }*/

    /*public static chatroom_server getChatroom(String channel) throws NoSuchChannelException {
        Iterator<chatroom_server> it = chatroom_list.iterator();
        while (it.hasNext()) {
            chatroom_server c = it.next();
            if (c.getName().equals(channel)) {
                return c;
            }
        }
        throw new NoSuchChannelException("NoSuchChannel!");
    }*/

    public void UserJoin(SSLSocket ss) {
        online_sslsockets.add(ss); 
        online_ips.add(ss.getLocalAddress().toString());   
    }

    public void UserLeave(SSLSocket ss) {
        online_sslsockets.remove(ss);
        online_ips.remove(ss.getLocalAddress().toString());
    }
}
