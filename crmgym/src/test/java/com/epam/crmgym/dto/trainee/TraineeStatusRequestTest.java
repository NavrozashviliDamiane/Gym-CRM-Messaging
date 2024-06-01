package com.epam.crmgym.dto.trainee;

import com.epam.crmgym.dto.trainee.TraineeStatusRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TraineeStatusRequestTest {

    @Test
    public void testConstructorAndGetters() {
        String username = "testUsername";
        boolean isActive = true;

        TraineeStatusRequest request = new TraineeStatusRequest(username, isActive);

        assertEquals(username, request.getUsername());
        assertEquals(isActive, request.isActive());
    }

    @Test
    public void testSetterAndGetters() {
        TraineeStatusRequest request = new TraineeStatusRequest();

        String username = "testUsername";
        boolean isActive = true;

        request.setUsername(username);
        request.setActive(isActive);

        assertEquals(username, request.getUsername());
        assertEquals(isActive, request.isActive());
    }

    @Test
    public void testEqualsAndHashCode() {
        TraineeStatusRequest request1 = new TraineeStatusRequest("testUsername", true);
        TraineeStatusRequest request2 = new TraineeStatusRequest("testUsername", true);
        TraineeStatusRequest request3 = new TraineeStatusRequest("differentUsername", false);

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1, request3);
    }

    @Test
    public void testToString() {
        TraineeStatusRequest request = new TraineeStatusRequest("testUsername", true);
        assertEquals("TraineeStatusRequest(username=testUsername, isActive=true)", request.toString());
    }
}
