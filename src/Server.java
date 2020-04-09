import java.net.*;
import java.io.*;

public class Server{

    public static void main(String[] args) {

        try {
            boolean stop = false;

            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));

            while (!stop){
                System.out.println("Server Log: Server oczekuje na porcie: " + args[0] );
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server Log: Klient się polaczyl");
                new ServerRequestHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}