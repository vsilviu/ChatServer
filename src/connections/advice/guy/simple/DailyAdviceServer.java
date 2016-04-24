package connections.advice.guy.simple;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Silviu on 12-Sep-15.
 */
public class DailyAdviceServer {

    public static final String[] list = {"1", "2", "3"};

    public void go() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8003);
        while(true) { //keeps on running, waiting for clients
            Socket socket = serverSocket.accept(); //called when a client kicks a connection on 8003, returns a socket on a different port! (8003 keeps on listening)
            //it does not accept simultaneous connections!
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            String advice = list[((int) (Math.random() * list.length))];
            writer.println(advice);
            writer.close(); //prints a single line and closes connection
            writer.println("Server advice : " + advice);
        }
    }

    public static void main(final String[] args) {
        DailyAdviceServer server = new DailyAdviceServer();
        try {
            server.go();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

}
