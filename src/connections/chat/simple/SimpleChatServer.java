package connections.chat.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Silviu on 19-Sep-15.
 */
public class SimpleChatServer {

    private List clientOutputStreams; //has writers for each client connection established

    //worker class which intercepts all client messages
    public class ClientHandler implements Runnable { //manages logic of connection for each connected client

        private BufferedReader reader; //reads messages sent by client
        private Socket sock; //connection socket to client

        public ClientHandler(Socket clientSocket) { //constructor
            try {
                //initializes client socket, client message reader
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() { //reads message from the client, sends it to all clients in same chat area
            String message;
            try {
                message = reader.readLine();
                while(message != null) { //read message from each individual client, each in a thread
                    //if a client sent a message from client interface
                    System.out.println("Read " + message);
                    tellEveryone(message); //sends message to all clients connected to server
                    message = reader.readLine();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void tellEveryone(String message) {
        Iterator it = clientOutputStreams.iterator();
        while(it.hasNext()) { //go through all the writers
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                writer.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void go() {
        clientOutputStreams = new ArrayList<>(); //first initialize client output streams
        try {
            ServerSocket serverSocket = new ServerSocket(5001);
            while(true) {
                Socket clientSocket = serverSocket.accept(); //establishes connection with client upon request
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream()); //writer will write back to client
                clientOutputStreams.add(writer);

                Thread t = new Thread(new ClientHandler(clientSocket)); //have this connection persisted in a thread
                t.start();
                System.out.println("Got a connection!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //set up a connection list, start the server, wait for client connections, persist connections in threads
        new SimpleChatServer().go();
    }

}
