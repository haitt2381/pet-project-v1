package com.example.petproject.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;


@Slf4j
@UtilityClass
public class DateTimeUtils {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static String nowToString() {
        OffsetDateTime dateTime = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC);
        return dateTime.toString();
    }

    public static OffsetDateTime nowToOffsetDateTime() {
        return OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC);
    }

    public static OffsetDateTime toOffsetDateTime(String dateTimeStr) {
        return OffsetDateTime.parse(dateTimeStr).withOffsetSameInstant(ZoneOffset.UTC);
    }

}