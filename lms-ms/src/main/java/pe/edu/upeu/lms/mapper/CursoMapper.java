package pe.edu.upeu.lms.mapper;

import pe.edu.upeu.lms.dto.CursoRequest;
import pe.edu.upeu.lms.dto.CursoResponse;
import pe.edu.upeu.lms.entity.Curso;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    CursoResponse toResponse(Curso curso);

    @Mapping(target = "id", ignore = true)
    Curso toEntity(CursoRequest request);
}
