import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = null;

        try {
            socket = new Socket("SergeyKlyus-PC", 4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }

        String fromServer;
        String fromUser;

        try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            while ((fromUser = stdIn.readLine()) != null && !fromUser.isEmpty()) {
                System.out.println("Client: " + fromUser);
                out.println(fromUser);
                fromServer = in.readLine();
                System.out.println(fromServer);
            }
        }

        socket.close();
    }
}