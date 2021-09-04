package main;

import controller.PassPromptController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import initRandomData.MakeData;
import persistence.*;
import services.*;
import services.cache.ServicesCache;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //createRandomData();
        Scene scene = getScene();
        showStage(primaryStage, scene);
    }

    private void createRandomData() {
        new MakeData(getServicesCache()).createRandomData();
        System.exit(0);
    }


    private Scene getScene() throws SQLException, IOException {
        FXMLLoader loader = getFxmlLoader();
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/view/Styles.css").toExternalForm());
        return scene;
    }

    private FXMLLoader getFxmlLoader() throws SQLException {
        PassPromptController passPromptController = new PassPromptController(getServicesCache());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PasswordPrompt.fxml"));
        loader.setController(passPromptController);
        return loader;
    }

    private void showStage(Stage primaryStage, Scene scene) {
        primaryStage.setTitle("DigiHealth");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ServicesCache getServicesCache() {
        ContactInfoDAO contactInfoDAO = new ContactInfoDAO();
        MedicalNoteDAO medicalNoteDAO = new MedicalNoteDAO();
        MedicationDAO medicationDAO = new MedicationDAO();
        MedicationNameDAO medicationNameDAO = new MedicationNameDAO();
        PatientDAO patientDAO = new PatientDAO();
        PatOnUnitDAO patOnUnitDAO = new PatOnUnitDAO();
        UnitDAO unitDAO = new UnitDAO();
        UserDAO userDAO = new UserDAO();

        PatientServices patientServices = new PatientServices(patientDAO, contactInfoDAO);
        UnitServices unitServices = new UnitServices(unitDAO);
        MedicalNoteServices medicalNoteServices = new MedicalNoteServices(medicalNoteDAO, patientServices);
        MedicationServices medicationServices = new MedicationServices(medicationDAO, medicationNameDAO, patientServices);
        PatOnUnitServices patOnUnitServices = new PatOnUnitServices(patOnUnitDAO, unitServices, patientServices);
        UserServices userServices = new UserServices(userDAO);

        ServicesCache servicesCache = new ServicesCache(
                medicalNoteServices, medicationServices,
                patientServices, patOnUnitServices, unitServices, userServices);

        return servicesCache;
    }

}


