package model;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class MedicalNote {

    private String writerID;
    private Integer pulse;
    private int noteID;
    private Integer o2Sat;
    private String BP;
    private BloodPressure bloodPressure;
    private Double temperature;
    private boolean deleted;
    private SimpleStringProperty note;
    private LocalDate date;
    
    public MedicalNote() {

        writerID = "";
        BP = "";
        bloodPressure = new BloodPressure(-1, -1);
        pulse = null;
        temperature = null;
        deleted = false;
        note = new SimpleStringProperty("");
        date = LocalDate.now();
    }

    public String getWriterID() {
        return writerID;
    }

    public void setWriterID(String writerID) {
        this.writerID = writerID;
    }

    public String getBP() {

        return BP;
    }

    public void setBP(int SBP, int DBP) {

        bloodPressure.setSBP(SBP);
        bloodPressure.setDBP(DBP);

        BP = bloodPressure.toString();
    }

    public Integer getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public Integer getO2Sat() {

        return o2Sat;
    }

    public void setO2Sat(int o2Sat) {

        this.o2Sat = o2Sat;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getNote() {
        return note.get();
    }

    public SimpleStringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    private class BloodPressure {

        int SBP;
        int DBP;

        public BloodPressure(int SBP, int DBP) {

            this.SBP = SBP;
            this.DBP = DBP;
        }

        public int getSBP() {

            return SBP;
        }

        public void setSBP(int SBP) {

            this.SBP = SBP;
        }

        public int getDBP() {

            return DBP;
        }

        public void setDBP(int DBP) {

            this.DBP = DBP;
        }

        public String toString() {

            return("" + SBP + "/" + DBP);
        }
    }
}