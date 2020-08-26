package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Medication {

    private String name;
    private double dose;
    private String units;
    private String route;
    private String frequency;
    private String prescriberID;
    private LocalDate prescribed;
    private LocalDate expires;
    public static List<String> unitsList;
    public static List<String> routeList;
    public static List<String> frequencyList;
    public static Set<String> medicationNames;

    public Medication(String name, double dose, String units, String route, String frequency, String prescriberID,
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

    public double getDose() {
        return dose;
    }

    public void setDose(double dose) {
        this.dose = dose;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
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
