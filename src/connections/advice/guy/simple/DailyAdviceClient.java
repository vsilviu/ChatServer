package connections.advice.guy.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Silviu on 12-Sep-15.
 */
public class DailyAdviceClient {

    public void go() throws IOException {
        Socket s = new Socket("localhost", 8003); //server runs at 8003
        InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
        BufferedReader reader = new BufferedReader(streamReader);

        String advice = reader.readLine();
        System.out.println("Advice : " + advice); //reads only one line from server

        reader.close(); //closes connection
    }

    public static void main(String[] args) throws IOException { //connection kicker
        DailyAdviceClient example = new DailyAdviceClient();
        try {
            example.go();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
