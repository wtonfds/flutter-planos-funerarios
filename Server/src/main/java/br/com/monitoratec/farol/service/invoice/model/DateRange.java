package br.com.monitoratec.farol.service.invoice.model;

import org.springframework.util.Assert;

import java.time.LocalDate;

public class DateRange {
    private final LocalDate start;
    private final LocalDate end;

    public DateRange(LocalDate start, LocalDate end) {
        Assert.notNull(start, "Start date must not be null");
        Assert.notNull(end, "End date must not be null");

        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }
}
