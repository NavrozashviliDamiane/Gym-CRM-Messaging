package com.epam.crmgym.exception;

import com.epam.crmgym.exception.BindingResultError;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BindingResultErrorTest {

    @Test
    public void testHandleBindingResultErrors() {
        BindingResult bindingResult = mock(BindingResult.class);

        List<FieldError> fieldErrors = Arrays.asList(
                new FieldError("objectName", "field1", "Error message 1"),
                new FieldError("objectName", "field2", "Error message 2")
        );

        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        BindingResultError bindingResultError = new BindingResultError();

        List<String> errors = bindingResultError.handleBindingResultErrors(bindingResult);

        assertEquals(2, errors.size());
        assertEquals("field1: Error message 1", errors.get(0));
        assertEquals("field2: Error message 2", errors.get(1));
    }
}
