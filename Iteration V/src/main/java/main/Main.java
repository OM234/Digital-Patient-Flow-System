package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistence.*;
import services.DigiServices;
import services.UserServices;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    //MakeData makeData;

    public Main() throws SQLException, IOException {
        //makeData = new MakeData();
        //makeData.createRandomData();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startServices();
        Parent root = FXMLLoader.load(getClass().getResource("/view/PasswordPrompt.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/view/Styles.css").toExternalForm());
        primaryStage.setTitle("DigiHealth");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startServices() throws SQLException {
        UserServices userServices = new UserServices(new UserDAO());
        DigiServices.getInstance(new UserDAO(), new PatientDAO(), new UnitDAO(), new PatOnUnitDAO(),
                new MedicalNoteDAO(), new ContactInfoDAO(), new MedicationDAO(), new MedicationNameDAO());
    }

}


