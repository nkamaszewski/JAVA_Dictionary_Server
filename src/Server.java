import java.net.*;
import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class Server{
    public static Map<String, String> serversAddresses = new TreeMap<String, String>();

    public static void main(String[] args) {

        try {
            boolean stop = false;

            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));

            while (!stop){
                System.out.println("Server Log: server listening on port: " + args[0] );
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server Log: Client connected with server");
                new ServerRequestHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}