package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PatientSummaryController {

    @FXML
    private Label patientSummaryLabel;

    public void setPatientSummaryLabel(String text) {

        patientSummaryLabel.setText(text);
    }
}
