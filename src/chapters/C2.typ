#import "../components/formatting.typ": clickable_link, needsCite, todo, needsSpelling
= Fehler
#needsSpelling
== Beschreibung Fehler
Wenn ein N4‑Knotenpunkt, an dem mehr als 79 Kanten hängen, bewegt wird, tritt eine ConcurrentModificationException auf.

Die N4‑Knotenpunkte und N4‑Kanten sind eine @hhla‑spezifische Erweiterung des ursprünglichen Frameworks, die für das @tos N4 benötigt wird. Der @tge exportiert dabei eine sogenannte ```"Yard‑File"```, welche von N4‑XPS direkt eingelesen werden kann. @ImportCompiledYardFile

Diese Daten sind notwendig, damit N4‑XPS — das unter anderem für die automatische Routenplanung der @vc verantwortlich ist — sicherstellen kann, dass geplante Fahrwege den jeweiligen Verkehrsregeln entsprechen (Einbahnstraßen, Durchgängigkeit, Fahrtrichtung usw.). #needsCite

Der Fehler tritt ausschließlich bei N4‑Kanten und N4‑Knotenpunkten auf, nicht jedoch bei den Standard‑Objekten des ursprünglichen @jump‑Frameworks. Dies deutet darauf hin, dass die Ursache in der @hhla‑Erweiterung oder deren Interaktion mit dem alten @jump‑Datenmodell liegt.

== Reproduktion des Fehlers
Der Fehler ist sehr gut reproduzierbar. Um ihn nachzustellen, sollte zunächst die @ctt‑Version des @tge gestartet werden. Anschließend wird die aktuellste @ctt‑Geometrie geöffnet, welche über die @hhla‑Server heruntergeladen werden kann.

Danach muss das Layer ```"N4‑Knotenpunkte"``` aktiviert und die Bearbeitung eingeschaltet werden. Sobald der betreffende N4‑Knotenpunkt ausgewählt ist, kann er mit dem Verschiebe‑Werkzeug bewegt werden. Direkt nach dem Verschieben tritt die ```"ConcurrentModificationException"``` auf.
#clickable_link("Video zum Fehler")


#todo("Bild hinzufügen evtl.")
== ConcurrentModificationException
Die ConcurrentModificationException in Java ist eine Laufzeitausnahme, die auftritt, wenn eine Collection während einer Iteration unerwartet strukturell verändert wird. @Oracle_Concurrent @IeeeConcurrent #needsCite

Viele der gängigen Java-Collections wie ArrayList, HashMap oder HashSet verwenden sogenannte fail‑fast Iteratoren. Diese Iteratoren überwachen intern einen Modifikationszähler (modCount), der sich immer dann ändert, wenn die Collection strukturell modifiziert wird, etwa durch das Hinzufügen oder Entfernen von Elementen. Wenn ein Iterator feststellt, dass sich dieser Zähler seit Beginn der Iteration auf eine Weise verändert hat, die nicht durch den Iterator selbst ausgelöst wurde, wirft er eine ConcurrentModificationException. Der fehler kann sowohl in single-Thread als auch in muti-Thread sitauationen auftreten. @Oracle_Concurrent @IeeeConcurrent #needsCite

Dieser Mechanismus dient dazu, inkonsistente Zustände zu verhindern, die entstehen könnten, wenn eine Collection während des Durchlaufens verändert wird. @Oracle_Concurrent @IeeeConcurrent #needsCite

== Analyse Error-Log
