#import "../components/formatting.typ": clickable_link, needsCite, todo, needsSpelling
= Analyse UseCaseGeometryModifiedN4Node.java

Zunächst wurde der Quellcode des Plugins analysiert, welches die Verschiebung von N4-Knotenpunkten
regelt: ```"UseCaseGeometryModifiedN4Node.java"```. Da der Fehler ausschließlich bei der Verschiebung
eines Knotenpunktes auftritt, lag die Vermutung nahe, dass der Fehler in diesem Code erzeugt wird.  @n4nodemodifiedJavabefore

Die Klasse erbt von der Klasse ```"AbstractUseCase"```, welche das Grundgerüst für einen UseCase
bereitstellt.
Da im Error-Log die Zeile 81 referenziert wurde, wurde zuerst dieser Codeabschnitt kontrolliert.
```Java
if (layerChanged) {
    layerManager.getLayer(TerminalLayer.EDGES_N4.getName())
        .setFeatureCollectionModified(true);
}
```

```"layerChanged"``` ist ein Boolean, der auf „true" gesetzt wird, sobald tatsächlich Kanten verschoben
werden müssen. Wenn ```"layerChanged"``` den Wert true hat, wird im oben genannten if-Statement für
das Layer ```"EDGES_N4"``` der ```"FeatureCollectionModified"```-Wert auf true gesetzt.

Damit signalisiert der @tge dem @jump‑Framework, dass dieses Layer verändert wurde.
Das Framework reagiert darauf, indem es im Hintergrund alle Use Cases ausführt, die bei einer
Modifikation eines Layers angestoßen werden müssen. Dazu gehören unter anderem die Aktualisierung
interner Datenstrukturen, die Synchronisation zwischen UI und Datenmodell sowie die Neuberechnung
oder Validierung von Geometrien.

Da @jump nicht jede einzelne Operation aktiv überwacht, sondern sich auf solche Änderungsflags
verlässt, ist ```"FeatureCollectionModified"``` ein zentraler Mechanismus, um Konsistenz sicherzustellen
und alle abhängigen Komponenten auf dem aktuellen Stand zu halten. Gerade in interaktiven Editoren
wie dem GeoEditor ist dieses Flag entscheidend, damit UI-Elemente und Datenmodell synchron
bleiben und keine veralteten Geometrien angezeigt oder verarbeitet werden.

Wird das Flag gesetzt, während gleichzeitig andere Prozesse – etwa UI-Aktualisierungen oder
weitere Use Cases – auf dieselbe Datenstruktur zugreifen, kann es zu Konflikten kommen.
Genau in solchen Situationen entsteht häufig eine ```"ConcurrentModificationException"```, wie sie
im Error-Log sichtbar wird, wenn eine TreeMap während der Iteration verändert wird.

Auf den Verdacht hin, dass die Exception durch diesen Code ausgelöst wird, wurde diese Zeile
auskommentiert und das Programm erneut getestet. Dabei ergab sich jedoch kein anderes Ergebnis:
Die Exception trat weiterhin auf. Dies legte nahe, dass die eigentliche Ursache an anderer Stelle
im Code liegt und die Zeile 81 im Error-Log lediglich den Aufrufkontext widerspiegelt, nicht aber
den tatsächlichen Auslöser der Exception.

*Anmerkung zum UseCase-Code:* Der UseCase prüft in seiner Schleife über die Kanten ausschließlich,
ob die „nodeEgressID" mit der ID des verschobenen Knotens übereinstimmt. Die Prüfung der
„nodeIngressID" fehlt. Dies bedeutet, dass nur Kanten angepasst werden, bei denen der verschobene
Knoten der Endknoten (Egress) ist. Kanten, bei denen der verschobene Knoten der Startknoten
(Ingress) ist, werden nicht aktualisiert. Diese Einschränkung ist im bestehenden Code so vorgesehen
und wurde im Rahmen dieser Arbeit nicht verändert, da sie nicht ursächlich für die
ConcurrentModificationException ist.
