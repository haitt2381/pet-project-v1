package com.example.petproject.common.annotation;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class CustomDateDeserializer extends JsonDeserializer<OffsetDateTime> {

    private static final String PATTERN = "yyyyMMdd";

    private final DateTimeFormatter formatter;

    public CustomDateDeserializer() {
        this.formatter = DateTimeFormatter.ofPattern(PATTERN);
    }

    @Override
    public OffsetDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {

        LocalDate localDate = LocalDate.parse(parser.getText(), formatter);
        return OffsetDateTime.of(localDate, LocalTime.MIDNIGHT, ZoneOffset.UTC);
    }
}
