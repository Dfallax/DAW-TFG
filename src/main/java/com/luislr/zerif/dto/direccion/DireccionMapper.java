package com.luislr.zerif.dto.direccion;

import com.luislr.zerif.entidades.Direccion;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface DireccionMapper {

    DireccionResponseDto toDto(Direccion entity);
    Direccion toEntity(DireccionCreateDto dto);
    Direccion toEntity(DireccionUpdateDto dto);

    List<DireccionResponseDto> toDtoList(List<Direccion> list);
    //List<Mascota> toEntityList(List<MascotaDto> list);
}
