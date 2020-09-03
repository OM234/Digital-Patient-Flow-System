package controller;

import bean.Patient;
import bean.Unit;
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
import services.DigiServices;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class DigiHealthController {

    private DigiServices digiServices;
    private boolean viewingPatientsOnUnits = false;
    private String patientOnUnitID;
    private Stage prevOpenStage;
    private DigiHealthController digiHealthController;
    @FXML private Button viewUnitsButton;
    @FXML private Button viewPatientsButton;
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Button patientsOnUnitButton;
    @FXML private Button patientSummaryButton;
    @FXML private Button medicalNotesButton;
    @FXML private Button patientMedicationsButton;
    @FXML private RadioButton unitsRadioButton;
    @FXML private RadioButton allPatientsRadioButton;
    @FXML private RadioButton patientsOnUnitRadioButton;
    @FXML private TextField searchTextField;
    @FXML private TableView<Unit> unitsTableView;
    @FXML private TableView<Patient> patientsTableView;
    @FXML private Label bottomViewingLabel;
    @FXML private Label bigUnitNameLabel;
    @FXML private Label addLabel;
    @FXML private Label deleteLabel;

    public DigiHealthController() throws SQLException {

        digiServices = DigiServices.getInstance();
    }

    public void initialize() {

        prevOpenStage = null;

        initializePatientTable();
        initializeUnitTable();
    }

    public void setDigiHealthController (DigiHealthController digiHealthController) {

        this.digiHealthController = digiHealthController;
    }

    public void initializePatientTable() {

        TableColumn<bean.Patient, String> patientIDCol = new TableColumn<>("Patient ID");
        TableColumn<bean.Patient, String> patientFirstNameCol = new TableColumn<>("First Name");
        TableColumn<bean.Patient, String> patientLastNameCol = new TableColumn<>("Last Name");
        TableColumn<bean.Patient, String> patientGenderChar = new TableColumn<>("Gender");

        patientIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        patientFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        patientLastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patientGenderChar.setCellValueFactory(new PropertyValueFactory<>("gender"));

        patientFirstNameCol.prefWidthProperty().bind(patientsTableView.widthProperty().multiply(0.25));
        patientLastNameCol.prefWidthProperty().bind(patientsTableView.widthProperty().multiply(0.25));

        patientsTableView.getColumns().addAll(patientIDCol, patientFirstNameCol, patientLastNameCol, patientGenderChar);
    }

    public void initializeUnitTable() {

        TableColumn<Unit, String> unitIDCol = new TableColumn<>("Unit ID");
        TableColumn<Unit, String> unitNameCol = new TableColumn<>("Unit Name");
        TableColumn<Unit, String> numPatCol = new TableColumn<>("# Patients");

        unitIDCol.setCellValueFactory(new PropertyValueFactory<Unit, String>("ID"));
        unitNameCol.setCellValueFactory(new PropertyValueFactory<Unit, String>("name"));
        numPatCol.setCellValueFactory(new PropertyValueFactory<Unit, String>("NumPatients"));

        unitNameCol.prefWidthProperty().bind(unitsTableView.widthProperty().multiply(0.4));

        unitsTableView.getColumns().addAll(unitIDCol, unitNameCol, numPatCol);
    }

    public void TableViewAppear(ActionEvent event) throws SQLException {

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

    public void populatePatientsTable() throws SQLException {

        patientsTableView.getItems().clear();

        ObservableList<Patient> obsList = getPatientsObsList();
        patientsTableView.setItems(obsList);

        patientView(obsList.size());
    }

    public void populatePatientsOnUnitTable() throws SQLException {

        Unit selected = unitsTableView.getSelectionModel().getSelectedItem();

        if(selected == null && viewingPatientsOnUnits) {

            selected = digiServices.getUnit(patientOnUnitID);
//            selected = digiSystem.getUnit(patientOnUnitID);

        } else if (selected == null && !viewingPatientsOnUnits) {

            return;

        } else {

            patientOnUnitID = unitsTableView.getSelectionModel().getSelectedItem().getID();
        }

        viewingPatientsOnUnits = true;

        bigUnitNameLabel.setVisible(true);
        bigUnitNameLabel.setText(selected.getName());

        patientsTableView.getItems().clear();
        ObservableList<Patient> obsList = getPatientsOnUnitObsList(selected);
        patientsTableView.setItems(obsList);

        patientView(obsList.size());

        patientsOnUnitRadioButton.setDisable(false);

        unitsTableView.getSelectionModel().clearSelection();
    }

    private ObservableList<bean.Patient> getPatientsObsList() throws SQLException {

        ObservableList<bean.Patient> patientsList = FXCollections.observableArrayList(digiServices.getAllPatients());

//        for (String patientID : digiSystem.getMapOfPatients().keySet()) {
//
//            patientsList.add(digiSystem.getMapOfPatients().get(patientID));
//        }

        return patientsList;
    }

    private ObservableList<bean.Patient> getPatientsOnUnitObsList(Unit unit) throws SQLException {

        ObservableList<Patient> patientsOnUnitList = FXCollections.observableArrayList(digiServices.getPatientsOnUnit(unit));

//        for (String patientID : unit.getUnitPatientIDs()) {
//
//            patientsOnUnitList.add(digiSystem.getPatient(patientID));
//        }

        return patientsOnUnitList;
    }

    private void populateUnitsTable() throws SQLException {

        unitsTableView.getItems().clear();

        ObservableList<Unit> obsList = getUnitsObsList();
        unitsTableView.setItems(obsList);

        unitView(obsList.size());
    }

    private ObservableList<Unit> getUnitsObsList() throws SQLException {

        ObservableList<Unit> allUnits = FXCollections.observableArrayList(digiServices.getAllUnits());

        return allUnits;
    }

    public void deletePatOrUnit() throws SQLException {

        if (unitsTableView.getSelectionModel().getSelectedItem() != null) {

            deleteUnit();
            unitsTableView.getSelectionModel().clearSelection();

        } else if (patientsTableView.getSelectionModel().getSelectedItem() != null) {

            deletePatientFromSystemOrUnit();
            patientsTableView.getSelectionModel().clearSelection();
        }
    }

    public void deleteUnit() throws SQLException {

        Unit selected = unitsTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selected.getName() + " ?",
                ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            unitsTableView.getItems().remove(selected);
            digiServices.removeUnit(selected);
//            digiSystem.removeUnit(selected.getUnitID());
            bottomViewingLabel.setText("Viewing " + unitsTableView.getItems().size() + " patients");
        }
    }

    public void deletePatientFromSystemOrUnit() throws SQLException {

        if (viewingPatientsOnUnits == false) {

            deletePatientFromSystem();

        } else {

            deletePatientFromUnit();
        }

    }

    public void deletePatientFromSystem() throws SQLException {

        Patient selected = patientsTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selected.getFirstName() + " " +
                selected.getLastName() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            patientsTableView.getItems().remove(selected);
            digiServices.removePatient(selected);
            //digiSystem.removePatient(selected.getPatientID());
            bottomViewingLabel.setText("Viewing " + patientsTableView.getItems().size() + " patients");
        }
    }

    public void deletePatientFromUnit() throws SQLException {

        Patient patientSelected = patientsTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + patientSelected.getFirstName() +
                " " + patientSelected.getLastName() + " from " + digiServices.getUnit(patientOnUnitID).getName() +
                "? ", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            digiServices.removePatientFromUnit(patientSelected, digiServices.getUnit(patientOnUnitID));
            //digiSystem.removePatientFromUnit(patientSelectedID, patientOnUnitID);
            populatePatientsOnUnitTable();
        }
    }

    public void addUnitOrPatient() throws IOException, SQLException {

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
            addPatientToUnitController.setDigiHealthController(digiHealthController);

            closePrevOpenStage();
            prevOpenStage = primaryStage;
        }
    }

    public void viewPatientSummary() throws IOException, SQLException {

        if(patientsTableView.getSelectionModel().getSelectedItem() == null) {

            return;
        }

        String patientID = patientsTableView.getSelectionModel().getSelectedItem().getID();
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
        //patientSummaryController.setPatientSummaryLabel("Patient Summary for " + firstName + " " + lastName);
        patientSummaryController.setPatient(digiServices.getPatient(patientID));
        //patientSummaryController.setPatient(digiSystem.getPatient(patientID));
        patientSummaryController.setPatientTableView(patientsTableView);

        closePrevOpenStage();
        prevOpenStage = primaryStage;
    }

    public void viewMedicalNotes() throws IOException, SQLException {

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

    public void viewPatientMedications() throws IOException {

        Patient selected = patientsTableView.getSelectionModel().getSelectedItem();

        if(patientsTableView.getSelectionModel().getSelectedItem() == null) {

            return;
        }

        Stage primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Medications.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/view/Styles.css").toExternalForm());
        primaryStage.show();

        MedicationsController medicationsController = fxmlLoader.getController();
        medicationsController.setPatient(selected);
        medicationsController.setTopLabel();
        medicationsController.setCellFactoryValues();

        closePrevOpenStage();
        prevOpenStage = primaryStage;
    }

    public void search() throws SQLException {

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

    private void searchPatient(String search, boolean onUnit) throws SQLException {

        ObservableList<Patient> searchList;

        if(!onUnit) {

            searchList = FXCollections.observableArrayList(getPatientsObsList()
                    .stream()
                    .filter(e -> e.getID().equals(search) ||
                            e.getFirstName().equalsIgnoreCase(search) || e.getLastName().equalsIgnoreCase(search))
                    .collect(Collectors.toList())
            );

        } else {

            Unit selected = digiServices.getUnit(patientOnUnitID);

            searchList = FXCollections.observableArrayList(getPatientsOnUnitObsList(selected)
                    .stream()
                    .filter(e -> e.getID().equals(search) ||
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

    private void searchUnit(String search) throws SQLException {

        ObservableList<Unit> searchList = FXCollections.observableArrayList(getUnitsObsList()
                .stream()
                .filter(e -> e.getID().equalsIgnoreCase(search) || e.getName().equalsIgnoreCase(search))
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
        patientMedicationsButton.setVisible(true);
        patientsOnUnitButton.setVisible(false);
        bottomViewingLabel.setText("Viewing " + count + " patients");

        setAddDeleteLabels();
    }

    public void unitView(int count) {

        unitsTableView.setVisible(true);
        patientsTableView.setVisible(false);
        patientSummaryButton.setVisible(false);
        medicalNotesButton.setVisible(false);
        patientMedicationsButton.setVisible(false);
        patientsOnUnitButton.setVisible(true);
        bigUnitNameLabel.setVisible(false);
        bottomViewingLabel.setText("Viewing " + count + " units");

        setAddDeleteLabels();
    }

    private void setAddDeleteLabels() {

        if(!viewingPatientsOnUnits) {

            addLabel.setText("New unit/patient");
            deleteLabel.setText("Delete unit/patient");

        } else {

            addLabel.setText("Add patient to unit");
            deleteLabel.setText("Remove from unit");
        }
    }

    private void closePrevOpenStage() {

        if(prevOpenStage != null) {
            prevOpenStage.close();
        }
    }
}
