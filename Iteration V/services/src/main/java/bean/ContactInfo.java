package bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactInfo {

    private String patientID;
    private String streetNumber;
    private String streetName;
    private String postalCode;
    private String city;
    private String province;
    private String country;
    private String phoneNumber;
    private String email;
}

