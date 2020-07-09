package model;

import javafx.beans.property.SimpleStringProperty;

public class ContactInformation {

    private SimpleStringProperty streetNumber;
    private SimpleStringProperty streetName;
    private SimpleStringProperty postalCode;
    private SimpleStringProperty city;
    private SimpleStringProperty province;
    private SimpleStringProperty country;
    private SimpleStringProperty phoneNumber;
    private SimpleStringProperty email;

    public ContactInformation() {

        this.streetNumber = new SimpleStringProperty();
        this.streetName = new SimpleStringProperty();
        this.postalCode = new SimpleStringProperty();
        this.city = new SimpleStringProperty();
        this.province = new SimpleStringProperty();
        this.country = new SimpleStringProperty();
        this.phoneNumber = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
    }

    public String getStreetNumber() {

        return streetNumber.get();
    }

    public void setStreetNumber(String streetNumber) {

        this.streetNumber.set(streetNumber);
    }

    public String getStreetName() {

        return streetName.get();
    }

    public void setStreetName(String streetName) {

        this.streetName.set(streetName);
    }

    public String getPostalCode() {

        return postalCode.get();
    }

    public void setPostalCode(String postalCode) {

        this.postalCode.set(postalCode);
    }

    public String getCity() {

        return city.get();
    }

    public void setCity(String city) {

        this.city.set(city);
    }

    public String getProvince() {

        return province.get();
    }

    public void setProvince(String province) {

        this.province.set(province);
    }

    public String getCountry() {

        return country.get();
    }

    public void setCountry(String country) {

        this.country.set(country);
    }

    public String getPhoneNumber() {

        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber.set(phoneNumber);
    }

    public String getEmail() {

        return email.get();
    }

    public void setEmail(String email) {

        this.email.set(email);
    }
}
