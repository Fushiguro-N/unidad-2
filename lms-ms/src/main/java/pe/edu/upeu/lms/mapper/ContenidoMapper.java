package pe.edu.upeu.lms.mapper;

import pe.edu.upeu.lms.dto.ContenidoRequest;
import pe.edu.upeu.lms.dto.ContenidoResponse;
import pe.edu.upeu.lms.entity.Contenido;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CursoMapper.class)
public interface ContenidoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "curso", ignore = true)
    Contenido toEntity(ContenidoRequest request);

    ContenidoResponse toResponse(Contenido contenido);
}
