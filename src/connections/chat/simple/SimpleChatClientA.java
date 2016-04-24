package connections.chat.simple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Silviu on 13-Sep-15.
 */
public class SimpleChatClientA {

    private JTextArea incoming;
    private JTextField outgoing;
    private BufferedReader reader;
    private PrintWriter writer;
    private JFrame frame;
    JScrollPane qScroller;
    JButton sendButton;

    public final void go() throws IOException {
        // builds GUI, registers listener, calls setUpNetworking

        //builds client GUI
        frame = new JFrame("Simple chat client v0.1");
        JPanel mainPanel = new JPanel();
        incoming = new JTextArea(15, 30); //will show messages from all clients
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        qScroller = new JScrollPane(incoming); //will have a vertical scroll bar, no horizontal scroll bar
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outgoing = new JTextField(20); //message sent by client
        sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener()); //will send message to server
        mainPanel.add(qScroller); //has the incoming messages area
        mainPanel.add(outgoing); //has the client message area
        mainPanel.add(sendButton); //is the send button

        setUpNetworking();

        Thread readerThread = new Thread(new IncomingReader()); //after server connection was established, persist this connection in a thread
        //this thread will read messages from the server and show them in the incoming area
        readerThread.start();

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel); //adds main panel to app frame
        frame.setSize(400, 500);
        frame.setVisible(true);
    }

    public void setUpNetworking() throws IOException {
        //will send messages to server
        Socket sock = new Socket("localhost", 5001);
        InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
        reader = new BufferedReader(streamReader); //reads messages from server
        writer = new PrintWriter(sock.getOutputStream()); //writes messages to server
        System.out.println("Connection established client side!");
    }

    public class SendButtonListener implements ActionListener {

        //gets text input from applet, gives it to printer.

        @Override
        public void actionPerformed(ActionEvent e) { //upon clicking send, this is performed
            writer.println(outgoing.getText()); //get client message
            writer.flush(); //send message to server
            outgoing.setText(""); //empty client message input
            outgoing.requestFocus(); //set cursor focus on message input
        }
    }

    public class IncomingReader implements Runnable {

        @Override
        public void run() {
            try {
                String message = reader.readLine();
                while (message != null) { //when server has messages, read them
                    System.out.println("Read " + message);
                    incoming.append(message + "\n"); //put them in incoming area
                    frame.repaint();
                    message = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            new SimpleChatClientA().go(); //wow, is valid.
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
