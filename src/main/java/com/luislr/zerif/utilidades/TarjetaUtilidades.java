package com.luislr.zerif.utilidades;

import lombok.experimental.UtilityClass;

import java.time.YearMonth;

@UtilityClass
public class TarjetaUtilidades {
    public YearMonth convertToYearMonth(String year, int mes) {
        String yearActual = "" + YearMonth.now().getYear();
        int yearCompleto = Integer.parseInt(yearActual.substring(0, 2) + year);
        return YearMonth.of(yearCompleto, mes);
    }

}
