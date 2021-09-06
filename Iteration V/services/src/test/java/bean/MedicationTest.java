package bean;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static bean.Medication.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class MedicationTest {

    @Test
    public void frequencyListTest(){
        assertThat(FREQUENCY_LIST, is(equalTo(new ArrayList<>(Arrays.asList("DIE", "BID", "TID", "QID")))));
    }

    @Test
    public void unitsListTest(){
        assertThat(UNITS_LIST, is(equalTo(new ArrayList<>(Arrays.asList("mg", "g", "ml", "units")))));
    }

    @Test
    public void routeListTest(){
        assertThat(ROUTE_LIST, is(equalTo(new ArrayList<>(Arrays.asList("PO","PR","SC", "IM","SL", "IV")))));
    }

}