package com.luislr.zerif.dto;

import com.luislr.zerif.entidades.Perfil;
import com.luislr.zerif.entidades.Usuario;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface UsuarioMapper {
    UsuarioSignupDto toDto(Usuario entity);

    Usuario toEntity(UsuarioSignupDto dto);

    List<PerfilSignupDto> toDtoList(List<Perfil> list);
    List<Perfil> toEntityList(List<PerfilSignupDto> list);

    @Mapping(target = "perfil.usuarios", ignore = true)
    PerfilSignupDto toDto(Perfil entity);

    Perfil toEntity(PerfilSignupDto dto);

}
