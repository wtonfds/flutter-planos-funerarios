package br.com.monitoratec.farol.utils.data;

import java.time.LocalDate;
import java.time.Period;

public class AgeUtils {

    public static int getAge(LocalDate date) {
        Period p = Period.between(date, LocalDate.now());
        return p.getYears();
    }
}
