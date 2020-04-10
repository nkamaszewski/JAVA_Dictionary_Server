import java.io.*;
import java.net.Socket;

public class ServerRequestHandler extends Thread{

    private Socket clientSocket = null;

    public ServerRequestHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void run(){
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientSocket.setSoTimeout(2000);

            String ClientRequestPayload = in.readLine();
            in.close();
            clientSocket.close();

            System.out.println("Handler Request: received: " + ClientRequestPayload);
            String[] splitedPayload = ClientRequestPayload.split(",");



            if(splitedPayload[0].equals("_dict")){
                String language = splitedPayload[1];
                String dictPort = splitedPayload[2];
                Server.serversAddresses.put(language, dictPort);
            } else {
                String wordToTranslate = splitedPayload[0];
                String language = splitedPayload[1];
                String ClientPort = splitedPayload[2];


                int destPort = Integer.parseInt(Server.serversAddresses.get(language));
                Socket dictionarySocket = new Socket("localhost", destPort);
                PrintWriter out = new PrintWriter (
                        new OutputStreamWriter(dictionarySocket.getOutputStream()),
                        true);
                out.write(wordToTranslate + ",localhost," + ClientPort);
                out.close();
                dictionarySocket.close();
            }

        } catch (IOException e) {
            System.out.println("Handler Request: exception " + e);
            e.printStackTrace();
        }


    }
}
