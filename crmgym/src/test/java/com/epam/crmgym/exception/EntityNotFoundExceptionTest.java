package com.epam.crmgym.exception;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityNotFoundExceptionTest {

    @Test
    public void testConstructor() {
        String message = "Entity not found";
        EntityNotFoundException exception = new EntityNotFoundException(message);

        assertEquals(message, exception.getMessage());
    }
}
