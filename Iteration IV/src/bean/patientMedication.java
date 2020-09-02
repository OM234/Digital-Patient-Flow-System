package bean;

import java.time.LocalDate;

public class patientMedication {

    private String patientID;
    private String prescriberID;
    private String medName;
    private String route;
    private double dose;
    private String frequency;
    private String units;
    private LocalDate prescribed;
    private LocalDate expires;

    public patientMedication() {}

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPrescriberID() {
        return prescriberID;
    }

    public void setPrescriberID(String prescriberID) {
        this.prescriberID = prescriberID;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public double getDose() {
        return dose;
    }

    public void setDose(double dose) {
        this.dose = dose;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public LocalDate getPrescribed() {
        return prescribed;
    }

    public void setPrescribed(LocalDate prescribed) {
        this.prescribed = prescribed;
    }

    public LocalDate getExpires() {
        return expires;
    }

    public void setExpires(LocalDate expires) {
        this.expires = expires;
    }
}
