package bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Patient {

    private final String ID;
    private String firstName;
    private String lastName;
    private int height;
    private double weight;
    private LocalDate DOB;
    private char gender;

    public double getBMI() {
        double BMI = weight != 0 && height != 0 ? weight / Math.pow((height / 100.0), 2) : 0;
        return BMI;
    }

    public int getAge() {
        return Period.between(this.getDOB(), LocalDate.now()).getYears();
    }

    public LocalDate getDOB() {
        return this.DOB != null ?
                DOB : LocalDate.of(0, 1, 1);
    }

}
