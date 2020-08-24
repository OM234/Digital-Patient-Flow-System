package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.MedicalNote;
import model.Patient;

import java.time.LocalDate;

public class MedicalNoteController {

    @FXML private Label medicalNoteLabel;
    @FXML private Label medicalNoteLabel2;
    @FXML private TableView<MedicalNote> noteTableView;
    @FXML private TableColumn<MedicalNote, String> dateColumn;
    @FXML private TableColumn<MedicalNote, String> pulseColumn;
    @FXML private TableColumn<MedicalNote, String> bpColumn;
    @FXML private TableColumn<MedicalNote, String> tempColumn;
    @FXML private TableColumn<MedicalNote, String> satColumn;
    @FXML private TableColumn<MedicalNote, SimpleStringProperty> noteColumn;
    @FXML private AnchorPane allNotesPane;
    @FXML private AnchorPane newNotePane;
    @FXML private TextField sbpTextField;
    @FXML private TextField dbpTextField;
    @FXML private TextField pulseTextField;
    @FXML private TextField sp02TextField;
    @FXML private TextField tempTextField;
    @FXML private TextArea noteTextArea;
    Patient patient;

    public void initialize() {

        allNotesPane.setVisible(true);
        newNotePane.setVisible(false);

    }

    public void setCellValueFactories() {

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        pulseColumn.setCellValueFactory(new PropertyValueFactory<>("pulse"));
        bpColumn.setCellValueFactory(new PropertyValueFactory<>("BP"));
        tempColumn.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        satColumn.setCellValueFactory(new PropertyValueFactory<>("o2Sat"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));

        ObservableList<MedicalNote> obsList = getNotesObsList();
        noteTableView.setItems(obsList);

        setCustomRowFactoryDeletedRow();
    }

    private void setCustomRowFactoryDeletedRow() {

        noteTableView.setRowFactory( e -> new TableRow<MedicalNote>() {
            @Override
            public void updateItem(MedicalNote item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                }
                else if (item.isDeleted()) {
                    getStyleClass().add("deletedRow");
                }
            }
        });
    }

    private ObservableList<MedicalNote> getNotesObsList() {

        ObservableList<MedicalNote> noteList = FXCollections.observableList(patient.getMedicalNotes());

        return noteList;
    }
    
    public void setPatient(Patient patient){
        
        this.patient = patient;
    }

    public void setTopLabel() {

        medicalNoteLabel.setText("Medical Notes for Patient # " + patient.getPatientID() + " " + patient.getFirstName()
                + " " + patient.getLastName() );
        medicalNoteLabel2.setText("Medical Notes for Patient # " + patient.getPatientID() + " " + patient.getFirstName()
                + " " + patient.getLastName() );
    }

    public void enterCheckValues(){

        boolean tempOkay, sbpOkay, dbpOkay, sp02Okay, pulseOkay;

        sbpOkay = checkNumTextFields(sbpTextField, true);
        dbpOkay = checkNumTextFields(dbpTextField, true);
        sp02Okay = checkNumTextFields(sp02TextField, true);
        pulseOkay = checkNumTextFields(pulseTextField, true);
        tempOkay = checkNumTextFields(tempTextField, false);

        if((sbpTextField.getText().equals("") && !dbpTextField.getText().equals("")) ||
                (!sbpTextField.getText().equals("") && dbpTextField.getText().equals(""))) {

            sp02Okay = false;
            dbpOkay = false;
            turnTextFieldErrorColor(sbpTextField);
            turnTextFieldErrorColor(dbpTextField);
        }

        if(sp02Okay && dbpOkay && sp02Okay && pulseOkay && tempOkay) {
            createNewNote();
            clearNewNotesValues();
            viewAllNotesPane();
            noteTableView.setItems(FXCollections.observableList(patient.getMedicalNotes()));
        }
    }

    private void clearNewNotesValues() {

        sbpTextField.clear();
        dbpTextField.clear();
        sp02TextField.clear();
        pulseTextField.clear();
        tempTextField.clear();
        noteTextArea.clear();
    }

    private void createNewNote() {

        MedicalNote medicalNote = new MedicalNote();

        if(!noteTextArea.getText().equals("")) {
            medicalNote.setNote(noteTextArea.getText());
        }
        if(!sbpTextField.getText().equals("")) {
            medicalNote.setBP(Integer.parseInt(sbpTextField.getText()), Integer.parseInt(dbpTextField.getText()));
        }
        if(!pulseTextField.getText().equals("")) {
            medicalNote.setPulse(Integer.parseInt(pulseTextField.getText()));
        }
        if(!sp02TextField.getText().equals("")) {
            medicalNote.setO2Sat(Integer.parseInt(sp02TextField.getText()));
        }
        if(!tempTextField.getText().equals("")) {
            medicalNote.setTemperature(Double.parseDouble(tempTextField.getText()));
        }

        medicalNote.setDate(LocalDate.now());
        medicalNote.setNoteID(patient.getNextMedicalNoteID());

        patient.addMedicalNote(medicalNote);
    }

    private boolean checkNumTextFields(TextField textField, boolean isInt) {

        double textFieldValue = -1;

        if(!textField.getText().equals("")) {

            try {

                if(isInt) {
                    textFieldValue = Integer.parseInt(textField.getText());
                } else {
                    textFieldValue = Double.parseDouble(textField.getText());
                }

                if(textFieldValue < 0) throw new NumberFormatException("Must be positive num");

            } catch(NumberFormatException ex) {

                turnTextFieldErrorColor(textField);
                return false;
            }
        }
        return true;
    }

    public void deleteNote() {

        MedicalNote medicalNote = noteTableView.getSelectionModel().getSelectedItem();

        if(medicalNote != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this note?",
                    ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);

            alert.showAndWait();

            if(alert.getResult() == ButtonType.YES)
                medicalNote.setDeleted(true);
        }

        noteTableView.refresh();
    }

    public void viewEnterNotePane(){

        allNotesPane.setVisible(false);
        newNotePane.setVisible(true);
    }

    public void viewAllNotesPane() {

        allNotesPane.setVisible(true);
        newNotePane.setVisible(false);
    }

    public void turnTextFieldErrorColor(TextField textField) {

        textField.setStyle("-fx-control-inner-background: RED;");
    }

    public void turnTextFieldDefaultColor(MouseEvent e) {

        ((TextField)e.getSource()).setStyle("-fx-control-inner-background: WHITE;");
    }

    public void turnTextFieldDefaultColorKey(KeyEvent e) {

        ((TextField)e.getSource()).setStyle("-fx-control-inner-background: WHITE;");
    }

}