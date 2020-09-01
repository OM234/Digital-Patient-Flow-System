package view;

import model.makeData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistence.*;
import java.sql.SQLException;

public class Main extends Application {

    makeData debug = new makeData();

    public Main() throws SQLException {}

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/PasswordPrompt.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/view/Styles.css").toExternalForm());
        primaryStage.setTitle("DigiHealth");
        primaryStage.setScene(scene);
        primaryStage.show();
        debug.debug();
        UserDAO userDAO = new UserDAO();
        userDAO.getAll();
    }

    public static void main(String[] args) {
        launch(args);
    }

}


