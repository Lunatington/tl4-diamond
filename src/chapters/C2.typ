#import "../components/formatting.typ": clickable_link, needsCite, todo, needsSpelling
= Fehler
== Beschreibung Fehler

Wenn ein N4-Knotenpunkt, an welchem mehr als 79 Kanten hängen, bewegt wird, tritt eine
ConcurrentModificationException auf.

Die N4-Knotenpunkte und N4-Kanten sind eine @hhla‑spezifische  Erweiterung des ursprünglichen
Frameworks, die für das @tos N4 benötigt wird. Ein N4-Knotenpunkt repräsentiert dabei eine Position im Fahrwegenetz des Terminals – beispielsweise eine Kreuzung, einen Wendepunkt oder einen Ein-/Ausfahrtspunkt –, während die N4-Kanten die Verbindungsstrecken zwischen diesen Punkten darstellen. Gemeinsam bilden sie ein gerichtetes Wegenetz, das die Grundlage für die automatische Routenplanung der Fahrzeuge auf dem Terminalgelände bildet. Der @tge exportiert dieses Wegenetz als sogenannte „Yard-File", welche von N4-XPS direkt eingelesen werden kann. @ImportCompiledYardFile

Diese Daten sind notwendig, damit N4-XPS – das unter anderem für die automatische Routenplanung der @vc verantwortlich ist – sicherstellen kann, dass geplante Fahrwege den
jeweiligen Verkehrsregeln entsprechen (Einbahnstraßen, Durchgängigkeit, Fahrtrichtung usw.). @Editor_Wiki_TerminalGeometrie @Editor_Wiki_N4-xps @Wiki_N4-xps

Der Fehler tritt ausschließlich bei N4-Kanten und N4-Knotenpunkten auf, nicht jedoch bei den
Standard-Objekten des ursprünglichen @jump‑Frameworks. Dies deutet darauf hin, dass die Ursache
in der @hhla‑Erweiterung oder deren Interaktion mit dem alten @jump‑Datenmodell liegt.

== Reproduktion des Fehlers
Der Fehler ist sehr gut reproduzierbar. Um ihn nachzustellen, sollte zunächst die @ctt‑Version des @tge gestartet werden. Anschließend wird die aktuellste @ctt‑Geometrie geöffnet, welche über die @hhla‑Server heruntergeladen werden kann.

Danach muss das Layer ```"N4‑Knotenpunkte"``` aktiviert und die Bearbeitung eingeschaltet werden. Sobald der betreffende N4‑Knotenpunkt ausgewählt ist, kann er mit dem Verschiebe‑Werkzeug bewegt werden. 

#figure(
  image("../res/Bild vor Exception Ausgewählt.png", height: 10cm),
  caption: "Bild vor der Exception",
) <bild1>

Direkt nach dem Verschieben tritt die ```"ConcurrentModificationException"``` auf und der Knotenpunkt wird mehrfach angezeigt.

#figure(
  image("../res/Bild nach Exception.png", height: 10cm),
  caption: "Bild von der Exception",
) <bild2>
== ConcurrentModificationException
Die ```"ConcurrentModificationException"``` in Java ist eine Laufzeitausnahme, die auftritt, wenn eine
Collection während einer Iteration unerwartet strukturell verändert wird. Sie ist im Paket
java.util definiert und erbt von der Klasse RuntimeException. @Oracle_Concurrent @IeeeConcurrent @oracle_arraylist

Viele der gängigen Java-Collections wie ArrayList, HashMap oder HashSet verwenden sogenannte
Fail-fast-Iteratoren. Diese Iteratoren überwachen intern einen Modifikationszähler (modCount), der
sich bei jeder strukturellen Veränderung der Collection erhöht – etwa beim Hinzufügen oder
Entfernen von Elementen. Stellt ein Iterator fest, dass sich dieser Zähler seit Beginn der Iteration
auf eine Weise verändert hat, die nicht durch den Iterator selbst ausgelöst wurde, wirft er eine
```"ConcurrentModificationException"```. Dieser Fehler kann sowohl in Single-Thread- als auch in
Multi-Thread-Situationen auftreten. @Oracle_Concurrent @IeeeConcurrent

Dieser Mechanismus dient dazu, inkonsistente Zustände zu verhindern, die entstehen könnten,
wenn eine Collection während des Durchlaufens verändert wird. Es handelt sich dabei um ein
sogenanntes „Best-Effort"-Verhalten: Die Exception wird nicht garantiert geworfen, sondern nur
dann, wenn die Änderung vom Iterator erkannt wird. @Oracle_Concurrent @IeeeConcurrent @oracle_arraylist

== Analyse Error-Log

Wenn die Exception auftritt, erzeugt Java einen Logeintrag, der anzeigt, an welcher Stelle der Fehler entstanden ist. @errorlog

An erster Stelle steht:
```Java
java.util.ConcurrentModificationException 
    at java.util.TreeMap$PrivateEntryIterator.nextEntry(TreeMap.java:1211) 
    at java.util.TreeMap$KeyIterator.next(TreeMap.java:1265) 
```

Das deutet darauf hin, dass während der Iteration über eine TreeMap gleichzeitig eine Veränderung an genau dieser TreeMap vorgenommen wird.

Danach folgt:

```Java
at de.hhla.tools.geoeditor.terminal.usecase.geometry.UseCaseGeometryModifiedN4Node
.execute(UseCaseGeometryModifiedN4Node.java:81) 
```

Dieser Eintrag legt nahe, dass der Fehler in dem Codeabschnitt entsteht, der für die Bewegung bzw. Anpassung der Kanten zuständig ist.

Darauffolgend erscheinen zahlreiche weitere Logeinträge, die auf UI‑Komponenten verweisen. Das passt zu der Beobachtung, dass während der Exception beide Punkte im Interface angezeigt werden – was ebenfalls ein Hinweis auf eine fehlerhafte oder konkurrierende Aktualisierung im UI‑Kontext sein kann.