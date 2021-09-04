package controller;

import bean.ContactInfo;
import bean.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import services.PatientServices;
import services.cache.ServicesCache;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientSummaryController {

    private final ServicesCache servicesCache;
    private final PatientServices patientServices;
    Patient patient;
    ContactInfo contactInfo;
    private List<TextField> textFields;
    TableView<Patient> patientsTableView;
    @FXML private Label patientSummaryLabel;
    @FXML private TextField patientIDTextField;
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField heightTextField;
    @FXML private TextField weightTextField;
    @FXML private TextField BMITextField;
    @FXML private TextField streetNumberTextField;
    @FXML private TextField streetNameTextField;
    @FXML private TextField postalCodeTextField;
    @FXML private TextField cityTextField;
    @FXML private TextField provinceTextField;
    @FXML private TextField telephoneTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField countryTextField;
    @FXML private TextField ageTextField;
    @FXML private DatePicker DOBDatePicker;
    @FXML private ToggleGroup genderToggleGroup;
    @FXML private RadioButton maleRadioButton;
    @FXML private RadioButton femaleRadioButton;
    @FXML private RadioButton otherRadioButton;
    @FXML private Button acceptButton;
    @FXML private Button rejectButton;

    public PatientSummaryController(ServicesCache servicesCache) {
        this.servicesCache = servicesCache;
        patientServices = servicesCache.getPatientServices();
    }

    public void initialize() {
        makeTextFieldsList();

        DOBDatePicker.setOpacity(1.0);
        DOBDatePicker.getEditor().setOpacity(1.0);
        maleRadioButton.setOpacity(1.0);
        femaleRadioButton.setOpacity(1.0);
        otherRadioButton.setOpacity(1.0);
    }

    public void setPatient(Patient patient) throws SQLException {

        this.patient = patient;
        this.contactInfo = patientServices.getContactInfo(patient);

        setPatientSummaryLabel();
        initialPopulateTextFields();
    }

    public void setPatientSummaryLabel() {

        String text = ("Patient Summary for " + patient.getFirstName() + " " + patient.getLastName());
        patientSummaryLabel.setText(text);
    }

    public void setPatientTableView(TableView<Patient> patientsTableView) {

        this.patientsTableView = patientsTableView;
    }

    public void makeEditable() {

        acceptButton.setVisible(true);
        rejectButton.setVisible(true);

        textFields.stream().forEach(textField -> textField.setEditable(true));
        genderToggleGroup.getToggles().stream().forEach(toggle -> ((RadioButton) toggle).setDisable(false));
        DOBDatePicker.setDisable(false);
        DOBDatePicker.getEditor().setDisable(false);
    }

    public void initialPopulateTextFields() {

        patientIDTextField.setText(contactInfo.getPatientID());
        firstNameTextField.setText(patient.getFirstName());
        lastNameTextField.setText(patient.getLastName());
        weightTextField.setText(Double.toString(patient.getWeight()));
        heightTextField.setText(Integer.toString(patient.getHeight()));
        DOBDatePicker.setValue(patient.getDOB());
        streetNumberTextField.setText(contactInfo.getStreetNumber());
        streetNameTextField.setText(contactInfo.getStreetName());
        postalCodeTextField.setText(contactInfo.getPostalCode());
        cityTextField.setText(contactInfo.getCity());
        provinceTextField.setText(contactInfo.getProvince());
        countryTextField.setText(contactInfo.getCountry());
        emailTextField.setText(contactInfo.getEmail());
        telephoneTextField.setText(contactInfo.getPhoneNumber());

        if (patient.getWeight() != 0 && patient.getHeight() != 0) {

            BMITextField.setText(String.format("%.2f", patient.getBMI()));
        }
        if (patient.getDOB().compareTo(LocalDate.of(0000, 1, 1)) != 0 &&
                patient.getDOB().compareTo(LocalDate.of(0001, 1, 1)) != 0) {

            ageTextField.setText(Integer.toString(patient.getAge()));
        }

        setGenderButton();
    }

    public void makeTextFieldsList() {

        textFields = new ArrayList<>();

        textFields.add(firstNameTextField);
        textFields.add(lastNameTextField);
        textFields.add(heightTextField);
        textFields.add(weightTextField);
        textFields.add(streetNumberTextField);
        textFields.add(streetNameTextField);
        textFields.add(postalCodeTextField);
        textFields.add(cityTextField);
        textFields.add(provinceTextField);
        textFields.add(telephoneTextField);
        textFields.add(emailTextField);
        textFields.add(countryTextField);
    }

    public void setGenderButton() {

        if (patient.getGender() == 'M') {

            genderToggleGroup.selectToggle(maleRadioButton);

        } else if (patient.getGender() == 'F') {

            genderToggleGroup.selectToggle(femaleRadioButton);

        } else {

            genderToggleGroup.selectToggle(otherRadioButton);
        }
    }

    public void setGender() {

        if (genderToggleGroup.getSelectedToggle() == maleRadioButton) {

            patient.setGender('M');

        } else if (genderToggleGroup.getSelectedToggle() == femaleRadioButton) {

            patient.setGender('F');

        } else {

            patient.setGender('O');
        }
    }

    public void acceptChanges() throws SQLException {

        acceptButton.setVisible(false);
        rejectButton.setVisible(false);

        patientsTableView.getItems().removeIf(e -> e.getID().equals(patient.getID()));

        patient.setFirstName(firstNameTextField.getText());
        patient.setLastName(lastNameTextField.getText());
        patient.setDOB(DOBDatePicker.getValue());
        patient.setHeight(Integer.parseInt(heightTextField.getText()));
        patient.setWeight(Double.parseDouble(weightTextField.getText()));
        contactInfo.setStreetNumber(streetNumberTextField.getText());
        contactInfo.setStreetName(streetNameTextField.getText());
        contactInfo.setPostalCode(postalCodeTextField.getText());
        contactInfo.setCity(cityTextField.getText());
        contactInfo.setProvince(provinceTextField.getText());
        contactInfo.setCountry(countryTextField.getText());
        contactInfo.setPhoneNumber(telephoneTextField.getText());
        contactInfo.setEmail(emailTextField.getText());
        setGender();

        patientServices.updatePatient(patient);
        patientServices.updateContactInfo(contactInfo);
        setPatientSummaryLabel();
        patientsTableView.getItems().add(patient);

        disableEdit();
        scrollToPatient();
    }

    private void scrollToPatient() throws SQLException {

        patientsTableView.getItems().stream().filter(e -> e == patient).findAny().ifPresent(e -> {
            patientsTableView.getSelectionModel().select(e);
            patientsTableView.scrollTo(e);
        });
    }

    public void disableEdit() {

        acceptButton.setVisible(false);
        rejectButton.setVisible(false);

        textFields.stream().forEach(textField -> textField.setEditable(false));
        genderToggleGroup.getToggles().stream().forEach(toggle -> ((RadioButton) toggle).setDisable(true));
        DOBDatePicker.setDisable(true);
        DOBDatePicker.getEditor().setDisable(true);

        initialPopulateTextFields();
    }
}
