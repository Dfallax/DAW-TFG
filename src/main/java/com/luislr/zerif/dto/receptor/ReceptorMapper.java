package com.luislr.zerif.dto.receptor;

import com.luislr.zerif.entidades.Receptor;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface ReceptorMapper {
    ReceptorResponseDto toDto(Receptor entity);
    Receptor toEntity(ReceptorCreateDto dto);
    Receptor toEntity(ReceptorUpdateDto dto);
}
