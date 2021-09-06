package bean;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PatientTest {

    private final int valid_height = 180;
    private final int valid_weight = 60;
    private final int zeroHeightOrWeight = 0;
    private final LocalDate DOB = LocalDate.of(1990, 6, 1);
    private final LocalDate defaultDOB = LocalDate.of(0, 1, 1);
    @Test
    void getBMIWithValidHeightAndWeight() {
        Patient patient = Patient.builder()
                .height(valid_height)
                .weight(valid_weight)
                .build();

        double calculatedBMI = valid_weight / Math.pow((valid_height / 100.0), 2);
        double classBMI = patient.getBMI();

        assertThat(calculatedBMI, is(equalTo(classBMI)));
    }

    @Test
    void getBMIWithInvalidHeight() {
        Patient patient = Patient.builder()
                .height(zeroHeightOrWeight)
                .weight(valid_weight)
                .build();

        double classBMI = patient.getBMI();

        assertThat(0.0, is(equalTo(classBMI)));
    }

    @Test
    void getBMIWithInvalidWeight() {
        Patient patient = Patient.builder()
                .height(valid_height)
                .weight(zeroHeightOrWeight)
                .build();

        double classBMI = patient.getBMI();

        assertThat(0.0, is(equalTo(classBMI)));
    }

    @Test
    void getAge() {
        Patient patient = Patient.builder()
                .DOB(DOB)
                .build();

        int calculatedAge = Period.between(DOB, LocalDate.now()).getYears();
        int classAge = patient.getAge();

        assertThat(calculatedAge, is(equalTo(classAge)));
    }

    @Test
    void getDOBWhenDOBNotNull() {
        Patient patient = Patient.builder()
                .DOB(DOB)
                .build();

        LocalDate classDOB = patient.getDOB();

        assertThat(classDOB, is(equalTo(DOB)));
    }

    @Test
    void getDOBWhenDOBIsNull() {
        Patient patient = Patient.builder()
                .build();

        LocalDate classDOB = patient.getDOB();

        assertThat(classDOB, is(equalTo(defaultDOB)));
    }
}