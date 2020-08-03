package model;

import java.time.LocalDate;

public class MedicalNote {
    
    private int SBP;            //systolic and diastolic BP
    private int BDP;
    private int pulse;
    private int noteID;
    private double temperature;
    private boolean deleted;
    private String note;
    private LocalDate date;
    
    public MedicalNote() {
        
        SBP = -1;
        BDP = -1;
        pulse = -1;
        temperature = -1;
        deleted = false;
        note = "";
        date = LocalDate.now();
    }
}