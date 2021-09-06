package bean;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class MedicalNoteTest {

    private final int sbp = 100;
    private final int dbp = 60;

    @Test
    void getBP() {
        MedicalNote medicalNote = MedicalNote.builder()
                .sbp(sbp)
                .dbp(dbp)
                .build();
        String bp = medicalNote.getBP();
        assertThat(bp, is(equalTo(sbp + "/" + dbp)));
    }
}