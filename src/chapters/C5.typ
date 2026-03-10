#import "../components/formatting.typ": clickable_link, needsCite, todo, needsSpelling
= Debugging mittels Debugger
Um die genaue Ursache der Exception zu lokalisieren, wurde die Anwendung mithilfe eines Debuggers schrittweise durchlaufen. Dabei wurde der Fehlerfall manuell in der UI ausgelöst und der Programmfluss Schritt für Schritt verfolgt, um die Stelle im Code zu identifizieren, an der die Exception geworfen wird. Mithilfe des Debuggers konnte festgestellt werden, dass die Exception in der Methode ```"getSelectedItems(Layer layer)"``` der abstrakten Klasse ```"AbstractSelection"``` auftrat. 

Diese Klasse ist Teil der UI-Schicht und dient als abstrakte Basisklasse zur Verwaltung der vom Benutzer ausgewählten Elemente. Intern speichert sie eine ```"Map<Layer, CollectionMap<Feature, Integer>>"```, welche jeder ```"Layer"``` eine ```"CollectionMap"``` zuordnet. Diese ```"CollectionMap"``` wiederum ordnet jedem ```"Feature"``` eine Sammlung von Indizes zu, die angeben, welche geometrischen Elemente des jeweiligen ```"Feature"``` selektiert sind. @selectitemsbefore #needsCite

Die Methode ```"getSelectedItems(Layer layer)"``` iteriert über alle ```"Feature"```-Einträge dieser internen Map für die übergebene ```"Layer"``` und ruft für jedes ```"Feature"``` die Methode ```"getSelectedItems(Layer layer, Feature feature)"``` auf, welche die tatsächlichen ```"Geometry"```-Objekte anhand der gespeicherten Indizes auflöst. Die gesammelten Geometrien werden in einer Liste zusammengefasst und zurückgegeben. Da die Klasse außerdem eine Eltern-Kind-Beziehung zwischen verschiedenen ```"AbstractSelection"```-Instanzen unterstützt, können Selektionen hierarchisch aufgebaut werden, wobei Eltern- und Kindselektionen gegenseitig berücksichtigt werden. @selectitemsbefore @abstractselection #needsCite

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

Dies stimmt mit der ursprünglich beobachteten ```"ConcurrentModificationException"``` überein. Da die ```"AbstractSelection"``` Teil der UI-Schicht ist und die Benutzerinteraktionen verarbeitet, ist es naheliegend, dass die interne Map gleichzeitig von einem anderen Thread verändert werden kann, während über sie iteriert wird. Beispielsweise können Änderungen an der Selektion durch UI-Events oder Hintergrundprozesse ausgelöst werden, die parallel zur Iteration laufen. Java's Iterator wirft in diesem Fall eine ```"ConcurrentModificationException"```, da er einen inkonsistenten Zustand der Collection erkennt. #needsCite

= Fix

Um dieses Problem zu beheben, wurde zu Beginn der Methode ein Snapshot der KeySet-Collection erstellt, indem die Schlüssel in eine neue lokale ```"ArrayList"``` kopiert werden. Da diese Liste lokal ist und nicht von anderen Threads referenziert wird, kann sie während der Iteration nicht verändert werden, wodurch die ```"ConcurrentModificationException"``` zuverlässig verhindert wird.
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

Konkret wird mit ```"new ArrayList<>(map.keySet())"``` eine Kopie aller Schlüssel der Map zum Zeitpunkt des Methodenaufrufs erstellt. Da diese Liste ausschließlich lokal existiert, haben etwaige Änderungen an der ursprünglichen Map durch andere Threads keinen Einfluss mehr auf die laufende Iteration. Dieser Ansatz ist leichtgewichtig und erfordert keine weitreichenden Änderungen an der bestehenden Codebasis.
@selectitemsafter #needsCite

Es lässt sich mutmaßen, dass die Map durch einen UI-Event-Thread verändert wird, während die Iteration in einem anderen Thread läuft. Bei einer geringen Anzahl an Kanten tritt dieses Problem möglicherweise nicht auf, da die Iteration zu schnell abgeschlossen wird, als dass ein konkurrierender Zugriff stattfinden könnte. Bei einer größeren Anzahl an Kanten hingegen dauert die Iteration länger, wodurch die Wahrscheinlichkeit eines gleichzeitigen Zugriffs steigt. Der genaue Auslöser konnte jedoch nicht abschließend identifiziert werden. #needsCite