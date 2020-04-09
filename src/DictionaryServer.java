import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DictionaryServer {

    public static void main(String[] args) {

        try {
            boolean stop = false;

            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));

            while (!stop){
                System.out.println("Dictionary Server Log: Server oczekuje na porcie: " + args[0] );
                Socket clientSocket = serverSocket.accept();
                System.out.println("Dictionary Server Log: Main Server przeslal zapytanie");
                new DictionaryServerRequestHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
