import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientService {
    private static int ClientServerListenerPort = 49000;
    private String server;
    private int port;



    public ClientService(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public String translateWord(String wordToTranslate, String language) {
        String translatedWord = "";

        try {
            ServerSocket serverSocket = new ServerSocket(ClientServerListenerPort);
            Socket socket = new Socket(server, port);


            PrintWriter pwOut = new PrintWriter(socket.getOutputStream(), true);


            pwOut.write(wordToTranslate + "," + language + ","+ ClientServerListenerPort);
            pwOut.close();
            socket.close();

            Socket inputSocket = serverSocket.accept();
            BufferedReader brIn = new BufferedReader(new InputStreamReader(inputSocket.getInputStream()));

            translatedWord = brIn.readLine();

            if(translatedWord.equals("")) {
                translatedWord = "Brak słowa lub języka w słowniku";
            }

            brIn.close();
            serverSocket.close();



        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(translatedWord);
        return translatedWord;
    }



}
