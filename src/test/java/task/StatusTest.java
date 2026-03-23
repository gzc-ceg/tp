package task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jobpilot.JobPilot;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusTest {

    private ArrayList<Add> applications;

    @BeforeEach
    public void setUp() {
        applications = new ArrayList<>();
    }

    @Test
    public void updateStatus_validInput_updatesAppSuccessfully() {
        Add app = new Add("Google", "SWE", "2026-03-01");
        applications.add(app);

        JobPilot.updateStatus(applications, "status 1 set/OFFER note/Great news");

        assertEquals("OFFER", applications.get(0).getStatus());
        assertEquals("Great news", applications.get(0).getNotes());
    }

    @Test
    public void updateStatus_invalidFormat_doesNotCrash() {
        Add app = new Add("Apple", "DA", "2026-03-02");
        applications.add(app);

        JobPilot.updateStatus(applications, "status 1 wrong_format");

        assertEquals("Pending", applications.get(0).getStatus());
        assertEquals("", applications.get(0).getNotes());
        assertEquals(1, applications.size());
    }
}