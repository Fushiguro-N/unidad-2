import json
import random
import time

from kafka import KafkaProducer


TOPIC_MATRICULAS = "matricula-eventos"
METODOS_PAGO = ["YAPE_PLIN", "TARJETA", "PAGO_EFECTIVO"]

producer = KafkaProducer(
    bootstrap_servers="kafka:9092",
    value_serializer=lambda value: json.dumps(value).encode("utf-8"),
)

print(json.dumps({
    "service": "eventos-py",
    "component": "producer",
    "bootstrapServers": "kafka:9092",
    "status": "connected",
}))

while True:
    data = {
        "tipoEvento": "matricula.creada",
        "matriculaId": random.randint(1, 1000),
        "idEstudiante": random.randint(1, 20),
        "total": float(random.randint(50, 500)),
        "metodoPago": random.choice(METODOS_PAGO),
        "origen": "python",
        "timestamp": int(time.time() * 1000),
    }

    metadata = producer.send(TOPIC_MATRICULAS, value=data).get(timeout=10)

    log = {
        "service": "eventos-py",
        "component": "producer",
        "topic": metadata.topic,
        "partition": metadata.partition,
        "offset": metadata.offset,
        "eventType": data["tipoEvento"],
        "matriculaId": data["matriculaId"],
        "timestamp": data["timestamp"],
        "status": "published",
    }

    print(json.dumps(log))
    time.sleep(2)
