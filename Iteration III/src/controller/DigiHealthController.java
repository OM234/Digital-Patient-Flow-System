package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DigiSystem;
import model.Patient;
import model.Unit2;

import java.io.IOException;
import java.util.stream.Collectors;

public class DigiHealthController {

    private DigiSystem digiSystem = DigiSystem.getInstance();
    private boolean viewingPatientsOnUnits = false;
    private String patientOnUnitID;
    private Stage prevOpenStage;

    @FXML private Button viewUnitsButton;
    @FXML private Button viewPatientsButton;
    @FXML private Button addButton;
    @FXML private Button addPatientToUnitButton;
    @FXML private Button removeButton;
    @FXML private Button patientsOnUnitButton;
    @FXML private Button patientSummaryButton;
    @FXML private Button medicalNotesButton;
    @FXML private Button searchButton;
    @FXML private RadioButton unitsRadioButton;
    @FXML private RadioButton allPatientsRadioButton;
    @FXML private RadioButton patientsOnUnitRadioButton;
    @FXML private TextField searchTextField;
    @FXML private TableView<Unit2> unitsTableView;
    @FXML private TableView<Patient> patientsTableView;
    @FXML private Label bottomViewingLabel;
    @FXML private Label bigUnitNameLabel;


    public void initialize() {

        prevOpenStage = null;

        initializePatientTable();
        initializeUnitTable();
    }

    public void initializePatientTable() {

        TableColumn<Patient, String> patientIDCol = new TableColumn<>("Patient ID");
        TableColumn<Patient, String> patientFirstNameCol = new TableColumn<>("First Name");
        TableColumn<Patient, String> patientLastNameCol = new TableColumn<>("Last Name");
        TableColumn<Patient, String> patientGenderChar = new TableColumn<>("Gender");

        patientIDCol.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        patientFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        patientLastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patientGenderChar.setCellValueFactory(new PropertyValueFactory<>("gender"));

        patientFirstNameCol.prefWidthProperty().bind(patientsTableView.widthProperty().multiply(0.25));
        patientLastNameCol.prefWidthProperty().bind(patientsTableView.widthProperty().multiply(0.25));

        patientsTableView.getColumns().addAll(patientIDCol, patientFirstNameCol, patientLastNameCol, patientGenderChar);
    }

    public void initializeUnitTable() {

        TableColumn<Unit2, String> unitIDCol = new TableColumn<>("Unit ID");
        TableColumn<Unit2, String> unitNameCol = new TableColumn<>("Unit Name");
        TableColumn<Unit2, String> numPatCol = new TableColumn<>("# Patients");

        unitIDCol.setCellValueFactory(new PropertyValueFactory<Unit2, String>("unitID"));
        unitNameCol.setCellValueFactory(new PropertyValueFactory<Unit2, String>("unitName"));
        numPatCol.setCellValueFactory(new PropertyValueFactory<Unit2, String>("NumPatients"));

        unitNameCol.prefWidthProperty().bind(unitsTableView.widthProperty().multiply(0.4));

        unitsTableView.getColumns().addAll(unitIDCol, unitNameCol, numPatCol);
    }

    public void TableViewAppear(ActionEvent event) {

        addButton.setDisable(false);
        removeButton.setDisable(false);
        bigUnitNameLabel.setVisible(false);
        patientsOnUnitRadioButton.setDisable(true);
        patientsOnUnitRadioButton.setSelected(false);

        viewingPatientsOnUnits = false;

        if (event.getSource() == viewUnitsButton) {

            patientsTableView.getSelectionModel().clearSelection();
            populateUnitsTable();
        }

        if (event.getSource() == viewPatientsButton) {

            unitsTableView.getSelectionModel().clearSelection();
            populatePatientsTable();
        }
    }

    public void populatePatientsTable() {

        patientsTableView.getItems().clear();

        ObservableList<Patient> obsList = getPatientsObsList();
        patientsTableView.setItems(obsList);

        patientView(obsList.size());
    }

    public void populatePatientsOnUnitTable() {

        Unit2 selected = unitsTableView.getSelectionModel().getSelectedItem();

        if(selected == null && viewingPatientsOnUnits) {

            selected = digiSystem.getUnit(patientOnUnitID);

        } else if (selected == null && !viewingPatientsOnUnits) {

            return;

        } else {

            patientOnUnitID = unitsTableView.getSelectionModel().getSelectedItem().getUnitID();
        }

        viewingPatientsOnUnits = true;

        bigUnitNameLabel.setVisible(true);
        bigUnitNameLabel.setText(selected.getUnitName());

        patientsTableView.getItems().clear();
        ObservableList<Patient> obsList = getPatientsOnUnitObsList(selected);
        patientsTableView.setItems(obsList);

        patientView(obsList.size());

        patientsOnUnitRadioButton.setDisable(false);

        unitsTableView.getSelectionModel().clearSelection();
    }

    private ObservableList<Patient> getPatientsObsList() {

        ObservableList<Patient> patientsList = FXCollections.observableArrayList();

        for (String patientID : digiSystem.getMapOfPatients().keySet()) {

            patientsList.add(digiSystem.getMapOfPatients().get(patientID));
        }

        return patientsList;
    }

    private ObservableList<Patient> getPatientsOnUnitObsList(Unit2 unit) {

        ObservableList<Patient> patientsOnUnitList = FXCollections.observableArrayList();

        for (String patientID : unit.getUnitPatientIDs()) {

            patientsOnUnitList.add(digiSystem.getPatient(patientID));
        }

        return patientsOnUnitList;
    }

    private void populateUnitsTable() {

        unitsTableView.getItems().clear();

        ObservableList<Unit2> obsList = getUnitsObsList();
        unitsTableView.setItems(obsList);

        unitView(obsList.size());
    }

    private ObservableList<Unit2> getUnitsObsList() {

        ObservableList<Unit2> allUnits = FXCollections.observableArrayList();

        for (String unitID : digiSystem.getMapOfUnits().keySet()) {

            allUnits.add(digiSystem.getMapOfUnits().get(unitID));
        }

        return allUnits;
    }

    public void deletePatOrUnit() {

        if (unitsTableView.getSelectionModel().getSelectedItem() != null) {

            deleteUnit();
            unitsTableView.getSelectionModel().clearSelection();

        } else if (patientsTableView.getSelectionModel().getSelectedItem() != null) {

            deletePatientFromSystemOrUnit();
            patientsTableView.getSelectionModel().clearSelection();
        }
    }

    public void deleteUnit() {

        Unit2 selected = unitsTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selected.getUnitName() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            unitsTableView.getItems().remove(selected);
            digiSystem.removeUnit(selected.getUnitID());
            bottomViewingLabel.setText("Viewing " + unitsTableView.getItems().size() + " patients");
        }
    }

    public void deletePatientFromSystemOrUnit() {

        if (viewingPatientsOnUnits == false) {

            deletePatientFromSystem();

        } else {

            deletePatientFromUnit();
        }

    }

    public void deletePatientFromSystem() {

        Patient selected = patientsTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selected.getFirstName() + " " +
                selected.getLastName() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            patientsTableView.getItems().remove(selected);
            digiSystem.removePatient(selected.getPatientID());
            bottomViewingLabel.setText("Viewing " + patientsTableView.getItems().size() + " patients");
        }
    }

    public void deletePatientFromUnit() {

        String patientSelectedID = patientsTableView.getSelectionModel().getSelectedItem().getPatientID();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + digiSystem.getPatientFirstName(patientSelectedID) +
                " " + digiSystem.getPatientLastName(patientSelectedID) + " from " + digiSystem.getUnitName(patientOnUnitID) +
                "? ", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            digiSystem.removePatientFromUnit(patientSelectedID, patientOnUnitID);
            populatePatientsOnUnitTable();
        }
    }

    public void addUnitOrPatient() throws IOException {

        if(!viewingPatientsOnUnits) {

            Stage primaryStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddUnitPatient.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("/view/Styles.css").toExternalForm());
            primaryStage.setTitle("Add Patient or Unit");
            primaryStage.show();

            AddUnitPatientController addUnitPatientController = fxmlLoader.getController();
            addUnitPatientController.setTableViews(patientsTableView, unitsTableView);

            closePrevOpenStage();
            prevOpenStage = primaryStage;

        } else {

            Stage primaryStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddPatientToUnit.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("/view/Styles.css").toExternalForm());
            primaryStage.setTitle("Add Patient or Unit");
            primaryStage.show();

            AddPatientToUnitController addPatientToUnitController = fxmlLoader.getController();
            addPatientToUnitController.setUnitID(patientOnUnitID);

            closePrevOpenStage();
            prevOpenStage = primaryStage;

        }
    }

    public void viewPatientSummary() throws IOException {

        if(patientsTableView.getSelectionModel().getSelectedItem() == null) {

            return;
        }

        String patientID = patientsTableView.getSelectionModel().getSelectedItem().getPatientID();
        String firstName = patientsTableView.getSelectionModel().getSelectedItem().getFirstName();
        String lastName = patientsTableView.getSelectionModel().getSelectedItem().getLastName();

        Stage primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PatientSummary.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/view/Styles.css").toExternalForm());
        primaryStage.show();


        PatientSummaryController patientSummaryController = fxmlLoader.getController();
        patientSummaryController.setPatientSummaryLabel("Patient Summary for " + firstName + " " + lastName);
        patientSummaryController.setPatient(digiSystem.getPatient(patientID));
        patientSummaryController.setPatientTableView(patientsTableView);

        closePrevOpenStage();
        prevOpenStage = primaryStage;
    }

    public void viewMedicalNotes() throws IOException {

        Patient selected = patientsTableView.getSelectionModel().getSelectedItem();

        if(patientsTableView.getSelectionModel().getSelectedItem() == null) {

            return;
        }

        Stage primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MedicalNotes.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/view/Styles.css").toExternalForm());
        primaryStage.show();

        MedicalNoteController medicalNoteController = fxmlLoader.getController();
        medicalNoteController.setPatient(selected);
        medicalNoteController.setTopLabel();
        medicalNoteController.setCellValueFactories();

        closePrevOpenStage();
        prevOpenStage = primaryStage;

    }

    public void search() {

        String search = searchTextField.getText();

        if(search.equals("")) {

            return;
        }

        if(unitsRadioButton.isSelected()) {

            searchUnit(search);

        } else if (allPatientsRadioButton.isSelected()) {

            searchPatient(search, false);

        } else if (patientsOnUnitRadioButton.isSelected() ) {

            searchPatient(search, true);
        }
    }

    private void searchPatient(String search, boolean onUnit) {

        ObservableList<Patient> searchList;

        if(!onUnit) {

            searchList = FXCollections.observableArrayList(getPatientsObsList()
                    .stream()
                    .filter(e -> e.getPatientID().equals(search) ||
                            e.getFirstName().equalsIgnoreCase(search) || e.getLastName().equalsIgnoreCase(search))
                    .collect(Collectors.toList())
            );

        } else {

            Unit2 selected = digiSystem.getUnit(patientOnUnitID);

            searchList = FXCollections.observableArrayList(getPatientsOnUnitObsList(selected)
                    .stream()
                    .filter(e -> e.getPatientID().equals(search) ||
                            e.getFirstName().equalsIgnoreCase(search) || e.getLastName().equalsIgnoreCase(search))
                    .collect(Collectors.toList())
            );
        }

        if(!searchList.isEmpty() && !onUnit) {

            bigUnitNameLabel.setVisible(false);
        }

        if(!searchList.isEmpty()) {

            patientsTableView.setItems(searchList);

            patientView(searchList.size());
        }
    }

    private void searchUnit(String search) {

        ObservableList<Unit2> searchList = FXCollections.observableArrayList(getUnitsObsList()
                .stream()
                .filter(e -> e.getUnitID().equalsIgnoreCase(search) || e.getUnitName().equalsIgnoreCase(search))
                .collect(Collectors.toList())
        );

        if (!searchList.isEmpty()) {

            unitsTableView.setItems(searchList);

            unitView(searchList.size());
        }
    }

    public void patientView(int count) {

        unitsTableView.setVisible(false);
        patientsTableView.setVisible(true);
        patientSummaryButton.setVisible(true);
        medicalNotesButton.setVisible(true);
        patientsOnUnitButton.setVisible(false);
        bottomViewingLabel.setText("Viewing " + count + " patients");
    }

    public void unitView(int count) {

        unitsTableView.setVisible(true);
        patientsTableView.setVisible(false);
        patientSummaryButton.setVisible(false);
        medicalNotesButton.setVisible(false);
        patientsOnUnitButton.setVisible(true);
        bigUnitNameLabel.setVisible(false);
        bottomViewingLabel.setText("Viewing " + count + " units");
    }

    private void closePrevOpenStage() {

        if(prevOpenStage != null) {
            prevOpenStage.close();
        }
    }
}
