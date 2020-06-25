package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    Debugging debug = new Debugging();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/passwordPrompt.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("DigiHealth");
        primaryStage.setScene(scene);
        primaryStage.show();
        debug.debug();
    }


    public static void main(String[] args) {
        launch(args);
    }

}


