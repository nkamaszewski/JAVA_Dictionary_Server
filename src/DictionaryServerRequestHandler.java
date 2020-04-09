import java.io.*;
import java.net.Socket;

public class DictionaryServerRequestHandler extends Thread {
    private Socket mainServerSocket = null;

    public DictionaryServerRequestHandler(Socket mainServerSocket){
        this.mainServerSocket = mainServerSocket;
    }

    public void run(){
        try {
            BufferedReader in = new BufferedReader (new InputStreamReader(mainServerSocket.getInputStream(), "UTF8"));
            String ClientRequestPayload = in.readLine();
            in.close();
            mainServerSocket.close();
            System.out.println("Handler Request: Otrzymano w zapytaniu: " + ClientRequestPayload);
            String[] splitedPayload = ClientRequestPayload.split(",");

            Socket clientSocket = new Socket(splitedPayload[1], Integer.parseInt(splitedPayload[2]));

            PrintWriter out = new PrintWriter (
                    new OutputStreamWriter(clientSocket.getOutputStream(), "UTF8"),
                    true);
            out.write("przetlumaczone slowo");
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;

    }
}
