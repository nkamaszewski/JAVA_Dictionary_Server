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

            String[] splitedPayload = ClientRequestPayload.split(",");

            if(splitedPayload[0].equals("_dict")){
                in.close();
                clientSocket.close();

                System.out.println("Registered new Dictionary. Received: " + ClientRequestPayload + " from dictionary server");
                String language = splitedPayload[1];
                String dictPort = splitedPayload[2];
                Server.serversAddresses.put(language, dictPort);
            } else if(splitedPayload[0].equals("addDictionary")) {

                System.out.println("Client sent new Dictionary. Received: " + ClientRequestPayload);
                String fileName = splitedPayload[1];
                String ClientPort = splitedPayload[2];
                File file = new File("dictionaries\\" + fileName);

                if(!file.createNewFile()) {
                    in.close();
                    clientSocket.close();
                    Socket errorResponseSocket = new Socket("localhost", Integer.parseInt(ClientPort));
                    PrintWriter out = new PrintWriter (new OutputStreamWriter(errorResponseSocket.getOutputStream()),
                            true);
                    out.write("Error! dictionary already exist!");
                    out.close();
                    errorResponseSocket.close();
                } else {
                    PrintStream fileStream = new PrintStream(file);
                    String line = in.readLine();
                    while (line != null) {
                        System.out.println(line);
                        fileStream.println(line);
                        line = in.readLine();
                    }
                    fileStream.close();
                    in.close();
                    clientSocket.close();

                    Socket errorResponseSocket = new Socket("localhost", Integer.parseInt(ClientPort));
                    PrintWriter out = new PrintWriter (new OutputStreamWriter(errorResponseSocket.getOutputStream()),
                            true);
                    out.write("Dictionary saved!");
                    out.close();
                    errorResponseSocket.close();
//                    try {
//                        runProcess("java DictionaryServer 50003 it.txt");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }

            } else {
                in.close();
                clientSocket.close();
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

//    private static void runProcess(String command) throws Exception {
//        Process pro = Runtime.getRuntime().exec(command);
//        pro.waitFor();
//        System.out.println(command + " exitValue() " + pro.exitValue());
//    }

}
