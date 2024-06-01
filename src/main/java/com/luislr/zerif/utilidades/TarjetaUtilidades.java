package com.luislr.zerif.utilidades;

import com.luislr.zerif.dto.tarjeta.TarjetaCompraDto;
import com.luislr.zerif.entidades.Tarjeta;
import lombok.experimental.UtilityClass;

import java.time.YearMonth;

@UtilityClass
public class TarjetaUtilidades {
    public YearMonth fechCaducidadEntityCompra(String year, int mes){
        String yearActual = ""+YearMonth.now().getYear();
        int yearCompleto = Integer.parseInt(yearActual.substring(2,4)+year);
        return YearMonth.of(yearCompleto, mes);
    }

    public int mesDtoCompra(YearMonth fecha){
        return fecha.getMonthValue();
    }

    public String yearDtoCompra(YearMonth fecha){
        String yearString = ""+fecha.getYear();
        return yearString.substring(2,4);
    }

    public Tarjeta convertToEntityCompra(TarjetaCompraDto tarjetaCompraDto) {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setNumTarjeta(tarjetaCompraDto.getNumTarjeta());
        tarjeta.setCv(tarjetaCompraDto.getCv());
        YearMonth fechCaducidad = fechCaducidadEntityCompra(tarjetaCompraDto.getYearCaducidad(), tarjetaCompraDto.getMesCaducidad());
        tarjeta.setFechCaducidad(fechCaducidad);
        return tarjeta;
    }
    public static TarjetaCompraDto convertToDtoCompra(Tarjeta tarjeta) {
        if (tarjeta == null) return null;
        TarjetaCompraDto tarjetaCompraDto = new TarjetaCompraDto();
        tarjetaCompraDto.setNumTarjeta(tarjeta.getNumTarjeta());
        tarjetaCompraDto.setCv(tarjeta.getCv());
        tarjetaCompraDto.setMesCaducidad(mesDtoCompra(tarjeta.getFechCaducidad()));
        tarjetaCompraDto.setYearCaducidad(yearDtoCompra(tarjeta.getFechCaducidad()));
        return tarjetaCompraDto;
    }
}
