package com.epam.crmgym.exception;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DateDeSerializerTest {

    @Test
    public void testDeserialize_ValidDate() throws IOException {
        DateDeSerializer deserializer = new DateDeSerializer();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        JsonParser jsonParser = new ObjectMapper().getFactory().createParser("\"2023/04/15\"");

        Date date = deserializer.deserialize(jsonParser, null);

        assertEquals("2023/04/15", dateFormat.format(date));
    }


    @Test
    public void testDeserialize_EmptyValue() throws IOException {
        DateDeSerializer deserializer = new DateDeSerializer();

        JsonParser jsonParser = new ObjectMapper().getFactory().createParser("\"\"");

        assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize(jsonParser, null));
    }

    @Test
    public void testDeserialize_NullValue() throws IOException {
        DateDeSerializer deserializer = new DateDeSerializer();

        JsonParser jsonParser = new ObjectMapper().getFactory().createParser("null");

        assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize(jsonParser, null));
    }
}
