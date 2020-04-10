import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Client extends Application {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    private static String server = "localhost";
    private static int port = 50000;


    ClientService clientService = new ClientService(server, port);


    @Override
    public void start(Stage primaryStage) throws Exception {

        Button translateButton = new Button("Translate");
        TextField wordField = new TextField();
        TextField countryCodeField = new TextField();
        final Label translatedWord = new Label("");

        TextField languageCodeField = new TextField();
        Button sendButton = new Button("Send");
        final Label addDictionaryStatusLabel = new Label("");

        translateButton.setOnAction(actionEvent -> {
            if(wordField.getText().equals("")){
                translatedWord.setText("fill word!");
            } else if(countryCodeField.getText().equals("")){
                translatedWord.setText("fill country code!");
            }else {
            translatedWord.setText(clientService.translateWord(wordField.getText(), countryCodeField.getText()));
            }
        });

        sendButton.setOnAction(actionEvent -> {
            if(languageCodeField.getText().equals("")){
                addDictionaryStatusLabel.setText("fill language code!");
            } else {
                addDictionaryStatusLabel.setText(clientService.addDictionary(languageCodeField.getText()));
            }
        });

        GridPane gridPane = new GridPane();

        gridPane.add(new Label("Word: "), 1, 1, 1, 1);
        gridPane.add(wordField, 1, 2, 1, 1);
        gridPane.add(new Label("Country Code: "), 2, 1, 1, 1);
        gridPane.add(countryCodeField, 2, 2, 1, 1);
        gridPane.add(translateButton, 3, 2, 1, 1);
        gridPane.add(new Label("Translated word: "), 1, 3, 1, 1);
        gridPane.add(translatedWord, 1, 4, 1, 1);

        gridPane.add(new Label("To add new language to dictionary servers"), 1, 7, 1, 1);
        gridPane.add(new Label("fill new_dict.txt file with data"), 1, 8, 1, 1);
        gridPane.add(new Label("and fill the language textField, then click send."), 1, 9, 1, 1);
        gridPane.add(new Label("Language code: "), 1, 10, 1, 1);
        gridPane.add(languageCodeField, 1, 11, 1, 1);
        gridPane.add(sendButton, 2, 11, 1, 1);
        gridPane.add(addDictionaryStatusLabel, 1, 12, 1, 1);

        gridPane.setHgap(50);
        gridPane.setVgap(10);


        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setTitle("s16456 - Norbert Kamaszewski, translate java program");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



}
