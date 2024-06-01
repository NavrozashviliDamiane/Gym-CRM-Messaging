package com.epam.crmgym.dto.trainer;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainer.TrainerStatusRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainerStatusRequestTest {

    @Test
    public void testConstructorAndGetters() {
        String username = "username";
        boolean isActive = true;

        TrainerStatusRequest request = new TrainerStatusRequest(username, isActive);

        assertEquals(username, request.getUsername());
        assertEquals(isActive, request.isActive());
    }

    @Test
    public void testSetterAndGetters() {
        TrainerStatusRequest request = new TrainerStatusRequest();

        String username = "username";
        boolean isActive = true;

        request.setUsername(username);
        request.setActive(isActive);

        assertEquals(username, request.getUsername());
        assertEquals(isActive, request.isActive());
    }
}
