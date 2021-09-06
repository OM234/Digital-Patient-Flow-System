package bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    TODO: can't this class be replaced with a list in Unit?
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientOnUnit {

    private String patientID;
    private String unitID;
}
