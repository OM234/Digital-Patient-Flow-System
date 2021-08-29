package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.MakeData;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    MakeData makeData;

    public Main() throws SQLException, IOException {
//        makeData = new MakeData();
//        makeData.createRandomData();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/PasswordPrompt.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/view/Styles.css").toExternalForm());
        primaryStage.setTitle("DigiHealth");
        primaryStage.setScene(scene);
        primaryStage.show();
        //makeData.createRandomData();

    }

    public static void main(String[] args) {
        launch(args);
    }

}


