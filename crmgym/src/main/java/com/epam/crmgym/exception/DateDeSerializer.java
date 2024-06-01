package com.epam.crmgym.exception;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateDeSerializer extends StdDeserializer<Date> {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public DateDeSerializer() {
        this(null);
    }

    public DateDeSerializer(Class<?> vc) {
        super(vc);
        dateFormat.setLenient(false);
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String value = p.readValueAs(String.class);
        log.info("Input value: " + value);

        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Date of birth cannot be empty");
        }

        if (!isValidDateFormat(value)) {
            log.info("Invalid date format. Please use yyyy/MM/dd");
            throw new IllegalArgumentException("Invalid date format. Please use correct calendar (yyyy/MM/dd) dates");
        }

        try {
            Date parsedDate = dateFormat.parse(value);
            log.info("Parsed date: " + parsedDate);

            return parsedDate;
        } catch (ParseException e) {
            log.info("Exception while parsing date: " + e.getMessage());
            throw new IllegalArgumentException("Invalid date format. Please use yyyy/MM/dd", e);
        }
    }



    private boolean isValidDateFormat(String date) {
        return date.matches("\\d{4}/\\d{2}/\\d{2}");
    }

    private boolean isValidDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        if (!isValidYear(year) || !isValidMonth(month) || !isValidDay(year, month, day)) {
            return false;
        }


        if (month < 1 || month > 12 || day < 1 || day > Month.of(month).length(Year.isLeap(year))) {
            throw new IllegalArgumentException("Invalid date. Please provide a valid calendar date.");
        }

        return true;
    }


    private boolean isValidYear(int year) {
        return year >= 1900 && year <= 2100;
    }

    private boolean isValidMonth(int month) {
        return month >= 1 && month <= 12;
    }

    private boolean isValidDay(int year, int month, int day) {
        int maxDay = Month.of(month).length(Year.isLeap(year));
        return day >= 1 && day <= maxDay;
    }
}
