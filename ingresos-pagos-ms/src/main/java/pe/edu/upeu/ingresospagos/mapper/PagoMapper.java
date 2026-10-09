package pe.edu.upeu.ingresospagos.mapper;

import org.mapstruct.Mapper;
import pe.edu.upeu.ingresospagos.dto.PagoResponse;
import pe.edu.upeu.ingresospagos.entity.Pago;

@Mapper(componentModel = "spring")
public interface PagoMapper {
    PagoResponse toResponse(Pago pago);
}
