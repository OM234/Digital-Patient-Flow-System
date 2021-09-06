package bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalNote {

    String patientID;
    String writerID;
    int pulse;
    int noteID;
    int o2Sat;
    int sbp;
    int dbp;
    double temp;
    boolean deleted;
    String note;
    LocalDate date;

    public String getBP() {
        return sbp + "/" + dbp;
    }
}
