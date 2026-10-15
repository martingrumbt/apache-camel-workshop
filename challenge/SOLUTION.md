# Referenzlösung – Architektur und Grenzen

## Producer

`file` → Jackson JSON `FlightManifest` → Split der `items` → Validate → **Idempotent Consumer** (ID, In-Memory) → JSON → **Wire Tap** zum Audit → JMS `airport.dispatch`.

- Die `flightId` wird vor dem Split auf jede Sendung übertragen.
- Camel wiederholt nicht automatisch eine fehlgeschlagene gesamte Split-Liste; Produktivbetrieb verlangt explizite Fehler- und Wiederanlaufstrategie.
- Das `MemoryIdempotentRepository` schützt nur den laufenden Producer-Prozess; es ist **keine Exactly-once-Garantie**.
- Ein `Wire Tap` läuft asynchron und ist kein transaktionaler Audit-Nachweis.

## Consumer

JMS `airport.dispatch` → Jackson JSON `Shipment` → Choice nach `route` → bei `VIA_HUB` Transfer MUC → in allen Fällen finale Ankunft unter `arrivals/<destination>`.

- Beide Prozesse verwenden Spring-Profile (`producer`, `consumer`) und dieselbe Artemis-Verbindung aus Spring Boot Autoconfiguration.
- Die beiden File-Writes bei einem Umstieg sind nicht gemeinsam mit dem JMS-ACK transaktional. Bei Abstürzen sind Duplikate möglich.
- `fileName=<shipmentId>.json` ist für sichtbare Lernergebnisse gedacht; erneutes Schreiben kann vorhandene Dateien überschreiben. Für produktive Zustellung müssten dafür eindeutige Nachweise/Idempotenz und ein geeignetes Zustellsystem entworfen werden.

## Start und Prüfung

```bash
# Im Repository-Root
 docker compose up -d
 cd challenge

# Zwei getrennte Terminals:
 mvn spring-boot:run -Dspring-boot.run.profiles=producer
 mvn spring-boot:run -Dspring-boot.run.profiles=consumer

# Drittes Terminal, cwd challenge/:
 mkdir -p data/inbox
 cp sample-data/*.json data/inbox/
 python3 verify.py
```

Die Aufträge durchlaufen File → Split → Idempotent Consumer → Wire Tap → JMS → Choice/Direct → File. `verify.py` überprüft die drei erwarteten Dateigruppen; das ist eine fachliche Stichprobe, kein Beweis von JMS-Durability oder end-to-end Exactly-once.
