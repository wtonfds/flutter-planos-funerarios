package br.com.monitoratec.farol.graphql.customTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Time implements Comparable<Time> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Time.class);
    private static final String EXPECTED_FORMAT = "HH:mm:ss.SSS"; //Example: 09:10:46.624
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(EXPECTED_FORMAT);

    private LocalTime time;

    public Time(LocalTime time) {
        this.time = time;
    }

    private Time() {
    }

    @Override
    public String toString() {
        return FORMATTER.format(time);
    }

    public static Time fromString(String dateAsString, boolean nullable) {
        try {
            return fromString(dateAsString);
        } catch (DateTimeParseException e) {
            LOGGER.info(e.getMessage());
            if (!nullable) {
                return new Time(LocalTime.now(ZoneOffset.UTC));
            } else {
                return null;
            }
        }
    }

    private static Time fromString(String dateAsString) {
        return new Time(LocalTime.parse(dateAsString, FORMATTER));
    }

    @Override
    public int compareTo(Time o) {
        return this.time.compareTo(o.time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return this.time.equals(time.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }

    public static String getExpectedFormat() {
        return EXPECTED_FORMAT;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
