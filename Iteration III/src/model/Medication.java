package model;

import java.time.LocalDate;

enum Units {
    MG {public String toString() {return "mg";}},
    ML {public String toString() {return "ml";}},
}

enum Route {
    PO, SL, SC, IM, PR
}

enum Frequency {
    DIE, BID, TID, QID
}

public class Medication {

    String name;
    Double dose;
    Units units;
    Route route;
    Frequency frequency;
    String prescriberID;
    LocalDate prescribed;
    LocalDate expires;

    public Medication(String name, Double dose, Units units, Route route, Frequency frequency, String prescriberID,
                      LocalDate prescribed, LocalDate expires) {

        this.name = name;
        this.dose = dose;
        this.units = units;
        this.route = route;
        this.frequency = frequency;
        this.prescriberID = prescriberID;
        this.prescribed = prescribed;
        this.expires = expires;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDose() {
        return dose;
    }

    public void setDose(Double dose) {
        this.dose = dose;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public String getPrescriberID() {
        return prescriberID;
    }

    public void setPrescriberID(String prescriberID) {
        this.prescriberID = prescriberID;
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
