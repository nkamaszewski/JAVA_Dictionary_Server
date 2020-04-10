import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class DictionaryServer {
    private static String mainServerAddress = "localhost";
    private static int mainServerPort = 50000;
    static Map<String, String> dictionary = new TreeMap<String, String>();

    public static void main(String[] args) {

        String selfPort = args[0];
        String dictionaryFileName = args[1];
        // removed .txt from file name
        String languageName = args[1].substring(0, args[1].length() - 4);

        Socket registerSocket = null;
        try {
            registerSocket = new Socket(mainServerAddress, mainServerPort);
            PrintWriter out = new PrintWriter (
                    new OutputStreamWriter(registerSocket.getOutputStream()),
                    true);
            out.write("_dict,"+ languageName + "," + args[0]);
            out.close();
            registerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File myObj = new File(dictionaryFileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] splitedData = data.split(",");
                dictionary.put(splitedData[0], splitedData[1]);
            }
            myReader.close();
        }catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            boolean stop = false;

            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(selfPort));

            while (!stop){
                System.out.println("Dictionary Server Log: server listening on port: " + selfPort );
                Socket clientSocket = serverSocket.accept();
                System.out.println("Dictionary Server Log: Main Server sends request");
                new DictionaryServerRequestHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
