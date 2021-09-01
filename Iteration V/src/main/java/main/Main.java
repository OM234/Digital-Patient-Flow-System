package main;

import controller.PassPromptController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistence.*;
import services.*;
import services.cache.ServicesCache;

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
        startServices(); //TODO: remove
        Scene scene = getScene();
        showStage(primaryStage, scene);
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

    private void startServices() throws SQLException {
        UserServices userServices = new UserServices(new UserDAO());
        DigiServices.getInstance(new UserDAO(), new PatientDAO(), new UnitDAO(), new PatOnUnitDAO(),
                new MedicalNoteDAO(), new ContactInfoDAO(), new MedicationDAO(), new MedicationNameDAO());
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


