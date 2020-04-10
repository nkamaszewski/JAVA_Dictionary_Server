import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

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


    public String addDictionary(String language){
        String response = "";
        try {
            File file = new File("new_dict.txt");
            Scanner myReader = new Scanner(file);

            ServerSocket serverSocket = new ServerSocket(ClientServerListenerPort);
            Socket socket = new Socket(server, port);

            PrintWriter pwOut = new PrintWriter(socket.getOutputStream(), true);
            pwOut.write("addDictionary," + language + ".txt," + ClientServerListenerPort+"\n");

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
                pwOut.write(data);
                pwOut.write(System.getProperty( "line.separator" ));
            }
            myReader.close();
            pwOut.close();
            socket.close();

            Socket inputSocket = serverSocket.accept();
            BufferedReader brIn = new BufferedReader(new InputStreamReader(inputSocket.getInputStream()));

            response = brIn.readLine();

            brIn.close();
            serverSocket.close();

        }catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return response;
    }



}
