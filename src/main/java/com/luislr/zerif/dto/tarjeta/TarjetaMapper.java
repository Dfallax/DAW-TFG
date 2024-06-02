package com.luislr.zerif.dto.tarjeta;

import com.luislr.zerif.entidades.Tarjeta;
import com.luislr.zerif.utilidades.TarjetaUtilidades;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface TarjetaMapper {

    @Mapping(target = "fechCaducidad", ignore = true)
    Tarjeta toEntity(TarjetaCreateDto dto);

    @Mapping(target = "fechCaducidad", ignore = true)
    Tarjeta toEntity(TarjetaUpdateDto dto);

    @Mapping(target = "fechCaducidad", ignore = true)
    TarjetaResponseDto toDto(Tarjeta entity);

    @AfterMapping
    default void setFechCaducidad(TarjetaCreateDto dto, @MappingTarget Tarjeta tarjeta) {
        if (dto.getYearCaducidad() != null && dto.getMesCaducidad() != null) {
            tarjeta.setFechCaducidad(TarjetaUtilidades.convertToYearMonth(dto.getYearCaducidad(), dto.getMesCaducidad()));
        }
    }

    @AfterMapping
    default void setFechCaducidad(TarjetaUpdateDto dto, @MappingTarget Tarjeta tarjeta) {
        if (dto.getYearCaducidad() != null && dto.getMesCaducidad() != null) {
            tarjeta.setFechCaducidad(TarjetaUtilidades.convertToYearMonth(dto.getYearCaducidad(), dto.getMesCaducidad()));
        }
    }

    @AfterMapping
    default void setFechCaducidad(Tarjeta tarjeta, @MappingTarget TarjetaResponseDto dto) {
        if (tarjeta.getFechCaducidad() != null) {
            dto.setFechCaducidad(tarjeta.getFechCaducidad().format(DateTimeFormatter.ofPattern("MM/yyyy")));
        }
    }
}
