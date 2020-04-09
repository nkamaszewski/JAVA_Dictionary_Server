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

            String ClientRequestPayload = in .readLine();
            in.close();
            clientSocket.close();

            System.out.println("Handler Request: Otrzymano w zapytaniu: " + ClientRequestPayload);
            String[] splitedPayload = ClientRequestPayload.split(",");

            Socket dictionarySocket = new Socket("localhost", 55000);
            PrintWriter out = new PrintWriter (
                    new OutputStreamWriter(dictionarySocket.getOutputStream()),
                    true);
            out.write(splitedPayload[0] + ",localhost," + splitedPayload[2]);
            out.close();
            dictionarySocket.close();
        } catch (IOException e) {
            System.out.println("Handler Request: exception " + e);
            e.printStackTrace();
        }


    }
}
