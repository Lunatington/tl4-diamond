#import "../components/formatting.typ": clickable_link, needsCite, todo, needsSpelling
= Fehler
== Beschreibung Fehler
Wenn ein N4‑Knotenpunkt, an dem mehr als 79 Kanten hängen, bewegt wird, tritt eine ConcurrentModificationException auf.
#clickable_link("Video zum Fehler")

Die N4‑Knotenpunkte und N4‑Kanten sind eine @hhla‑spezifische Erweiterung des ursprünglichen Frameworks, die für das @tos N4 benötigt wird. Der @tge exportiert dabei eine sogenannte ```"Yard‑File"```, welche von N4‑XPS direkt eingelesen werden kann. @ImportCompiledYardFile

Diese Daten sind notwendig, damit N4‑XPS — das unter anderem für die automatische Routenplanung der @vc verantwortlich ist — sicherstellen kann, dass geplante Fahrwege den jeweiligen Verkehrsregeln entsprechen (Einbahnstraßen, Durchgängigkeit, Fahrtrichtung usw.). #needsCite

Der Fehler tritt ausschließlich bei N4‑Kanten und N4‑Knotenpunkten auf, nicht jedoch bei den Standard‑Objekten des ursprünglichen @jump‑Frameworks. Dies deutet darauf hin, dass die Ursache in der @hhla‑Erweiterung oder deren Interaktion mit dem alten @jump‑Datenmodell liegt.

== Reproduktion des Fehlers


== Analyse Error-Log