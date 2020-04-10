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


        translateButton.setOnAction(actionEvent -> {
            translatedWord.setText(clientService.translateWord(wordField.getText(), countryCodeField.getText()));
        });


        GridPane gridPane = new GridPane();

        gridPane.add(new Label("Word: "), 1, 1, 1, 1);
        gridPane.add(wordField, 1, 2, 1, 1);
        gridPane.add(new Label("Country Code: "), 2, 1, 1, 1);
        gridPane.add(countryCodeField, 2, 2, 1, 1);
        gridPane.add(translateButton, 3, 2, 1, 1);
        gridPane.add(new Label("Translated word: "), 1, 3, 1, 1);
        gridPane.add(translatedWord, 1, 4, 1, 1);

        gridPane.setHgap(50);
        gridPane.setVgap(10);


        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setTitle("s16456 - Norbert Kamaszewski, translate java program");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



}
