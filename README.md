# Apache Camel Workshop

Code und Beispiele für den Apache Camel Workshop.

## Setup

Benötigt werden:

- Java 21+
- Maven
- Git
- Docker
- Apache Camel CLI
- eine Java IDE

### Camel CLI installieren

#### macOS / Linux

```bash
curl -fsSL https://camel.apache.org/install.sh | sh
```

#### Windows

In PowerShell:

```powershell
irm https://camel.apache.org/install.ps1 | iex
```

Anschließend prüfen, ob die CLI verfügbar ist:

```bash
camel version
```

Mit `camel doctor` könnt ihr zusätzlich das lokale Setup prüfen:

```bash
camel doctor
```

Weitere Installationsmöglichkeiten findet ihr in der [Camel CLI Dokumentation](https://camel.apache.org/manual/camel-jbang.html).

### Setup prüfen

```bash
java --version
mvn --version
docker --version
camel version
git --version
```

Zum Schluss könnt ihr mit einer kleinen Camel Route testen, ob alles funktioniert:

```bash
camel run playground/hello.java
```

Wenn

```text
Camel CLI is working!
```

in der Ausgabe erscheint, passt das Setup.

Den Prozess anschließend mit `Ctrl+C` beenden.

## Repository

```bash
git clone <repository-url>
cd apache-camel-workshop
```

Die Beispiele bauen im Laufe des Workshops aufeinander auf. Für die einzelnen Schritte gibt es jeweils einen Start- und einen fertigen Branch:

```text
01-timer
01-timer-final

02-file
02-file-final
```

Zum Start einer Übung:

```bash
git switch 01-timer
```

Falls ihr hängen bleibt oder euren Stand mit der fertigen Variante vergleichen wollt:

```bash
git switch 01-timer-final
```

Der aktuelle Branch steht während des Workshops auch auf der jeweiligen Folie.

## Projektstruktur

```text
.
├── playground/
├── application-service/
└── provider-simulator/
```

Im `playground` starten wir mit kleinen Beispielen über die Camel CLI.

Danach wechseln wir zu Spring Boot und bauen die Beispiele dort weiter aus.

## Links

- [Apache Camel Dokumentation](https://camel.apache.org/docs/)
- [Camel Components](https://camel.apache.org/components/latest/)
- [Enterprise Integration Patterns](https://camel.apache.org/components/latest/eips/enterprise-integration-patterns.html)
- [Camel CLI](https://camel.apache.org/manual/camel-jbang.html)