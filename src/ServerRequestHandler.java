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

            String[] splitedPayload = ClientRequestPayload.split(",");

            if(splitedPayload[0].equals("_dict")){
                System.out.println("Received: " + ClientRequestPayload + " from dictionary server");
                String language = splitedPayload[1];
                String dictPort = splitedPayload[2];
                Server.serversAddresses.put(language, dictPort);
            } else {
                System.out.println("Received: " + ClientRequestPayload + " from Client request");
                String wordToTranslate = splitedPayload[0];
                String language = splitedPayload[1];
                String ClientPort = splitedPayload[2];

                String dPort = Server.serversAddresses.get(language);

                // there is no language in dictionaries
                if(dPort == null){
                    Socket errorResponseSocket = new Socket("localhost", Integer.parseInt(ClientPort));
                    PrintWriter out = new PrintWriter (new OutputStreamWriter(errorResponseSocket.getOutputStream()),
                                    true);
                    out.write("Error! Language " + language + " is not supported");
                    out.close();
                    errorResponseSocket.close();
                } else {
                    int destPort = Integer.parseInt(Server.serversAddresses.get(language));
                    Socket dictionarySocket = new Socket("localhost", destPort);
                    PrintWriter out = new PrintWriter (
                            new OutputStreamWriter(dictionarySocket.getOutputStream()),
                            true);
                    out.write(wordToTranslate + ",localhost," + ClientPort);
                    out.close();
                    dictionarySocket.close();
                }
            }

        } catch (IOException e) {
            System.out.println("Handler Request: exception " + e);
            e.printStackTrace();
        }


    }
}
