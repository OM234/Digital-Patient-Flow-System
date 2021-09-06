package bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medication {

    private String patientID;
    private String prescriberID;
    private String medName;
    private String route;
    private double dose;
    private String frequency;
    private String units;
    private LocalDate prescribed;
    private LocalDate expires;
    public final static List<String> FREQUENCY_LIST = new ArrayList<>(Arrays.asList("DIE", "BID", "TID", "QID"));
    public final static List<String> UNITS_LIST = new ArrayList<>(Arrays.asList("mg", "g", "ml", "units"));
    public final static List<String> ROUTE_LIST = new ArrayList<>(Arrays.asList("PO","PR","SC", "IM","SL", "IV"));
}
