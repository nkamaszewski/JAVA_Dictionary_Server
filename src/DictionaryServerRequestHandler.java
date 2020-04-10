import java.io.*;
import java.net.Socket;
import java.util.Map;

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
            System.out.println("received: " + ClientRequestPayload + " from Main Server");
            String[] splitedPayload = ClientRequestPayload.split(",");

            String wordToTranslate = splitedPayload[0];
            String ClientAddress = splitedPayload[1];
            String ClientPort = splitedPayload[2];

            Socket clientSocket = new Socket(ClientAddress, Integer.parseInt(ClientPort));

            String translatedWord = DictionaryServer.dictionary.get(wordToTranslate);
            PrintWriter out = new PrintWriter (
                    new OutputStreamWriter(clientSocket.getOutputStream(), "UTF8"),
                    true);
            // there is not that word in dictionary
            if(translatedWord == null){
                out.write("Error! " + wordToTranslate + " - that word is not avalaible in dictionary");
            } else {
                out.write(translatedWord);
            }

            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;

    }
}
