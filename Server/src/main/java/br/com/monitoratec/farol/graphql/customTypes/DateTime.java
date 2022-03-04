package br.com.monitoratec.farol.graphql.customTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class DateTime {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateTime.class);
    private static final String EXPECTED_FORMAT = "yyyyMMdd HH:mm:ss.SSS"; //Example: 20180830 09:10:46.624
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(EXPECTED_FORMAT);

    private LocalDateTime date;

    public DateTime(LocalDateTime date) {
        this.date = date;
    }

    private DateTime() {
    }

    @Override
    public String toString() {
        return FORMATTER.format(date);
    }

    public static DateTime fromString(String dateAsString, boolean nullable) {
        try {
            return fromString(dateAsString);
        } catch (DateTimeParseException e) {
            LOGGER.info(e.getMessage());
            if (!nullable) {
                return new DateTime(LocalDateTime.now(ZoneOffset.UTC));
            } else {
                return null;
            }
        }
    }

    public static DateTime fromString(String dateAsString) {
        return new DateTime(LocalDateTime.parse(dateAsString, FORMATTER));
    }

    public static String getExpectedFormat() {
        return EXPECTED_FORMAT;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateTime dateTime = (DateTime) o;
        return Objects.equals(date, dateTime.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
