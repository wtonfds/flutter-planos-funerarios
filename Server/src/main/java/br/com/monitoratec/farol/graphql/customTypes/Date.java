package br.com.monitoratec.farol.graphql.customTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Date implements Comparable<Date> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Date.class);
    private static final String EXPECTED_FORMAT = "yyyyMMdd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(EXPECTED_FORMAT);

    private LocalDate date;

    public Date(LocalDate date) {
        this.date = date;
    }

    private Date() {
    }

    @Override
    public String toString() {
        return FORMATTER.format(date);
    }

    public static Date fromString(String dateAsString, boolean nullable) {
        try {
            return fromString(dateAsString);
        } catch (DateTimeParseException e) {
            LOGGER.info(e.getMessage());
            if (!nullable) {
                return new Date(LocalDate.now(ZoneOffset.UTC));
            } else {
                return null;
            }
        }
    }

    private static Date fromString(String dateAsString) {
        return new Date(LocalDate.parse(dateAsString, FORMATTER));
    }

    @Override
    public int compareTo(Date o) {
        return this.date.compareTo(o.date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date1 = (Date) o;
        return Objects.equals(date, date1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    public static String getExpectedFormat() {
        return EXPECTED_FORMAT;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
