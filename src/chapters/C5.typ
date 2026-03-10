#import "../components/formatting.typ": clickable_link, needsCite, todo, needsSpelling

= Debugging mittels Debugger

Um die genaue Ursache der Exception zu lokalisieren, wurde die Anwendung mithilfe eines
Debuggers schrittweise durchlaufen. Dabei wurde der Fehlerfall manuell in der UI ausgelöst und
der Programmfluss Schritt für Schritt verfolgt, um die Stelle im Code zu identifizieren, an der die
Exception geworfen wird. Mithilfe des Debuggers konnte festgestellt werden, dass die Exception
in der Methode ```"getSelectedItems(Layer layer)"``` der abstrakten Klasse ```"AbstractSelection"```
auftritt.

Diese Klasse ist Teil der UI-Schicht und dient als abstrakte Basisklasse zur Verwaltung der vom
Benutzer ausgewählten Elemente. Intern speichert sie eine ```"Map<Layer, CollectionMap<Feature, Integer>>"```, welche jedem Layer eine ```"CollectionMap"``` zuordnet. Diese ```"CollectionMap"``` wiederum ordnet
jedem Feature eine Sammlung von Indizes zu, die angeben, welche geometrischen Elemente des
jeweiligen Features selektiert sind. @selectitemsbefore

Die Methode ```"getSelectedItems(Layer layer)"``` iteriert über alle Feature-Einträge dieser
internen Map für den übergebenen Layer und ruft für jedes Feature die Methode
```"getSelectedItems(Layer layer, Feature feature)"``` auf, welche die tatsächlichen Geometry-Objekte
anhand der gespeicherten Indizes auflöst. Die gesammelten Geometrien werden in einer
Liste zusammengefasst und zurückgegeben. Da die Klasse außerdem eine Eltern-Kind-Beziehung
zwischen verschiedenen AbstractSelection-Instanzen unterstützt, können Selektionen hierarchisch
aufgebaut werden, wobei Eltern- und Kindselektionen gegenseitig berücksichtigt werden.  @selectitemsbefore @abstractselection 

```Java
public Collection<Geometry> getSelectedItems(Layer layer) {

	ArrayList<Geometry> selectedItems = new ArrayList<>();

	for (Iterator<Feature> i = getFeatureToSelectedItemIndexCollectionMap(layer).keySet().iterator(); i.hasNext();) {
			Feature feature = i.next();
			selectedItems.addAll(getSelectedItems(layer, feature));
		}
		
	return selectedItems;
}
```

Dieser Befund stimmt mit der ursprünglich beobachteten ```"ConcurrentModificationException"``` überein.
Da die ```"AbstractSelection"``` Teil der UI-Schicht ist und die Benutzerinteraktionen verarbeitet, ist es
naheliegend, dass die interne Map gleichzeitig von einem anderen Prozess verändert werden kann,
während über sie iteriert wird. Konkret wird die Verschiebung der Kanten durch den UseCase
ausgelöst, was wiederum eine Aktualisierung der Selektion anstößt. Parallel dazu verarbeitet
der ```"Event-Dispatch-Thread"``` von ```"Swing"``` weitere UI-Events, die ebenfalls auf die Selektions-Map
zugreifen. Bei einer geringen Anzahl an Kanten wird die Iteration so schnell abgeschlossen, dass
kein konkurrierender Zugriff stattfindet. Bei einer größeren Anzahl an Kanten dauert die Iteration
hingegen länger, wodurch die Wahrscheinlichkeit einer gleichzeitigen Modifikation steigt und
der Fail-fast-Iterator die ```"ConcurrentModificationException"``` wirft. @oracle_treemap @Oracle_Concurrent

= Fix

Um dieses Problem zu beheben, wurde zu Beginn der Methode ein Snapshot der KeySet-Collection
erstellt, indem die Schlüssel in eine neue lokale ArrayList kopiert werden. Da diese Liste lokal
ist und nicht von anderen Threads referenziert wird, kann sie während der Iteration nicht verändert
werden, wodurch die ```"ConcurrentModificationException"``` zuverlässig verhindert wird.

```Java
public Collection<Geometry> getSelectedItems(Layer layer) {
  ArrayList<Geometry> selectedItems = new ArrayList<>();
  List<Feature> features = new ArrayList<>(map.keySet()); // snapshot der von anderen Threads nicht verändert werden kann, da Lokal
    
  for (Feature feature : features) {
    selectedItems.addAll(getSelectedItems(layer, feature));
  }
    
  return selectedItems;
}
```

Konkret wird mit ```"new ArrayList<>(map.keySet())"``` eine Kopie aller Schlüssel der Map zum
Zeitpunkt des Methodenaufrufs erstellt. Da diese Liste ausschließlich lokal existiert, haben etwaige
Änderungen an der ursprünglichen Map durch andere Prozesse keinen Einfluss mehr auf die laufende
Iteration. Dieser Ansatz ist leichtgewichtig und erfordert keine weitreichenden Änderungen an der
bestehenden Codebasis. 
@selectitemsafter @goetz2006jcip @oracle_arraylist

Neben dem gewählten Snapshot-Ansatz wurden auch alternative Lösungsmöglichkeiten in Betracht
gezogen. Der Einsatz einer ConcurrentHashMap anstelle der bestehenden TreeMap hätte zwar
thread-sichere Iteratoren bereitgestellt, hätte jedoch einen tieferen Eingriff in die
AbstractSelection-Klasse des @jump–Frameworks erfordert. Ebenso wäre eine Synchronisation
der Zugriffe mittels synchronized-Blöcken möglich gewesen, birgt jedoch das Risiko von
Deadlocks oder Performance-Einbußen in der UI-Schicht. Da die Änderung im Quellcode des
@jump–Frameworks – das nicht unter der Kontrolle der @hhla–Entwicklung steht – so minimal wie
möglich gehalten werden sollte, wurde der Snapshot-Ansatz als pragmatischer Kompromiss zwischen
Robustheit und Wartbarkeit gewählt. @oracle_swing_concurrency @oracle_swing_package

Es lässt sich festhalten, dass die Map durch den ```"Event-Dispatch-Thread"``` von ```"Swing"``` verändert wird,
während die Iteration in demselben Thread, aber in einem verschachtelten Aufruf läuft. Bei einer
geringen Anzahl an Kanten tritt dieses Problem möglicherweise nicht auf, da die Iteration zu schnell
abgeschlossen wird, als dass ein konkurrierender Zugriff stattfinden könnte. Bei einer größeren
Anzahl an Kanten hingegen dauert die Iteration länger, wodurch die Wahrscheinlichkeit eines
gleichzeitigen Zugriffs steigt.