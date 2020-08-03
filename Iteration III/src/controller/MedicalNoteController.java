package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.MedicalNote;
import model.Patient;
import model.DigiSystem;

public class MedicalNoteController {

    @FXML private Label medicalNoteLabel;
    @FXML private TableView<MedicalNote> noteTableView;
    @FXML private TableColumn<MedicalNote, String> dateColumn;
    @FXML private TableColumn<MedicalNote, String> pulseColumn;
    @FXML private TableColumn<MedicalNote, String> bpColumn;
    @FXML private TableColumn<MedicalNote, String> tempColumn;
    @FXML private TableColumn<MedicalNote, String> satColumn;
    @FXML private TableColumn<MedicalNote, String> noteColumn;

    Patient patient;

    public void initialize() {

        setCellValueFactories();
    }

    private void setCellValueFactories() {

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        pulseColumn.setCellValueFactory(new PropertyValueFactory<>("pulse"));
        bpColumn.setCellValueFactory(new PropertyValueFactory<>("BP"));
        tempColumn.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        satColumn.setCellValueFactory(new PropertyValueFactory<>("o2Sat"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));

        ObservableList<MedicalNote> obsList = getNotesObsList();
        noteTableView.setItems(obsList);
    }

    private ObservableList<MedicalNote> getNotesObsList() {

        ObservableList<MedicalNote> noteList = FXCollections.observableArrayList();

        MedicalNote note = new MedicalNote();
        note.setNote("this is a note");
        note.setBP(100, 60);
        note.setPulse(80);
        note.setO2Sat(99);
        note.setTemperature(36.7);
        noteList.add(note);

        return noteList;
    }
    
    public void setPatient(Patient patient){
        
        this.patient = patient;
    }

    public void setTopLabel() {

        medicalNoteLabel.setText("Medical Notes for Patient # " + patient.getPatientID() + " " + patient.getFirstName()
                + " " + patient.getLastName() );
    }
     
}