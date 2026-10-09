package pe.edu.upeu.lms.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pe.edu.upeu.lms.event.PagoValidadoEvento;
import pe.edu.upeu.lms.service.MatriculaService;

@Slf4j
@Component
@RequiredArgsConstructor
public class PagoEventosConsumer {

    private static final String PAGO_VALIDADO = "pago.validado";

    private final MatriculaService matriculaService;

    @KafkaListener(topics = "${app.kafka.topic.pagos}")
    public void alRecibirPago(PagoValidadoEvento evento) {
        if (!PAGO_VALIDADO.equals(evento.getTipoEvento())) {
            log.warn("component=consumer eventType={} status=ignored", evento.getTipoEvento());
            return;
        }
        log.info("component=consumer eventType={} matriculaId={} status=consumed",
                evento.getTipoEvento(), evento.getMatriculaId());
        matriculaService.marcarConfirmada(evento.getMatriculaId());
    }
}
