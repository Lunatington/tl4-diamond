#import "../components/formatting.typ": clickable_link, needsCite, todo, needsSpelling
= JUnit-Tests
Um die Behebung der Exception zu erleichtern, wurde entschieden, einen JUnit-Test zu schreiben,
der das Auftreten des Fehlers schneller überprüfbar macht. Dadurch muss nicht jedes Mal das
gesamte Programm gestartet werden, um festzustellen, ob die Exception erneut ausgelöst wird. Ein
solcher Test isoliert den relevanten Codeabschnitt und ermöglicht es, die problematische Situation
gezielt und wiederholt auszuführen, was den Debugging-Prozess deutlich beschleunigt.
== Definition
JUnit ist ein weit verbreitetes Testframework für Java, das dabei hilft, Code automatisiert zu überprüfen und Fehler frühzeitig zu erkennen. Es ermöglicht das Schreiben kleiner, wiederholbarer Testmethoden, die einzelne Funktionen oder Abläufe isoliert ausführen. Dadurch lassen sich Fehlerquellen schneller eingrenzen, weil Tests unabhängig vom restlichen Programm laufen und exakt zeigen, welche Methode korrekt funktioniert und welche nicht. @Beck_Gamma_2000 @1383111 @app11167250
== Reproduzierung des Fehlers per JUnit-Test
Für einen JUnit-Test im @tge stellt das HHLA-Plugin eine Oberklasse zur Verfügung: die "GeoEditorTest"-Klasse. Von dieser erbt der JUnit-Test, mit dem der Fehler und die Exception reproduziert werden sollen.

Zuerst wird eine Einstiegsfunktion erstellt, welche beim Test-Start ausgeführt wird. Die Einstiegsfunktion wird über der Deklaration der Funktion mit ```"@before"``` deklariert. In dieser wird die Superklasse instanziiert, damit ihre Funktionen genutzt werden können.
Eine Testklasse kann mehrere Tests enthalten; jeder Test wird ähnlich wie die Einstiegsfunktion mit ```"@test"``` deklariert. @Beck_Gamma_2000 @junit4_test_annotation

```Java
@Before
public void setUp() throws Exception {
  super.setUp();
  }
```

Um das Problem zu reproduzieren, müssen zunächst der Mittelpunkt und die Kanten erstellt werden. Diese können durch die Funktionen "createN4Node()" und "createN4Edge()" der Oberklasse erstellt werden. Dabei wird die Anzahl der zu erstellenden Kanten anhand der finalen Variable "maxEdgesN4" festgelegt. @testnodemove @abstracttest
```Java
@Test
public void MoveNodeWithManyEdges() {
  Feature node = createNodeN4("Main",new Coordinate(0, 0),Graph);      
  //Erstellen aller N4Nodes
  for(int i=0;i<maxEdges;i++) {
    createNodeN4("e"+i ,new Coordinate(-maxCoordinate, i),Graph);
    createEdgeN4("e"+i, "Main",FACILITY_CTB, TTrafficDirection.BOTH);
  }
```


In einem For-Loop werden alle Kanten erstellt und dem Hauptknoten ```"Main"``` zugewiesen.

Als Nächstes wird die N4-Node auf einen anderen Punkt verschoben und das Feature-Event ```"GEOMETRY_CHANGED"``` aufgerufen, welches den ```"UseCaseGeometryModiefedN4Node"``` aufruft.
```Java
try {
  List<Feature> oldFeatures = new ArrayList<>();
  oldFeatures.add((Feature) node.clone());

  node.setGeometry(geometryFactory.createPoint(new Coordinate(maxCoordinate, maxCoordinate)));

  List<Feature> featureList = new ArrayList<>();
  featureList.add(node);

  useCaseFactory.getUseCase(TerminalLayer.NODES_N4, FeatureEventType.GEOMETRY_MODIFIED)
    .execute(new FeatureEvent(featureList, FeatureEventType.GEOMETRY_MODIFIED,
    layerManager.getLayer(TerminalLayer.NODES_N4.getName()), oldFeatures));

  } catch (Exception e) {
    fail();
  }
```

Bei der Ausführung des Tests mit 80 Kanten ist der Test jedoch nicht wie erwartet fehlgeschlagen.
Stattdessen ist der Test fehlerfrei durchgelaufen. In der Konsolenausgabe war zu erkennen, dass die
Kanten tatsächlich erfolgreich verschoben wurden. Um sicherzustellen, dass es sich nicht um einen
Grenzwerteffekt handelt, wurde die Anzahl der Kanten schrittweise erhöht. Selbst bei 80.000 Kanten
wurde keine Exception geworfen und der Test lief erneut ohne Fehler durch.

#todo("EEvtl. Foto von console logs oder console logs als codeblock")
Daraus lässt sich schlussfolgern, dass der Fehler nicht in der Geschäftslogik des UseCases selbst
liegt, da diese die Verschiebung der Kanten korrekt verarbeitet. Der JUnit-Test simuliert
ausschließlich den direkten Aufruf der execute()-Methode, ohne die UI-Schicht und deren
Event-Verarbeitung einzubeziehen. Da der Fehler ausschließlich beim manuellen Verschieben in
der laufenden Anwendung auftritt, liegt die Ursache vermutlich in der Interaktion zwischen der
Kantenverschiebung und der gleichzeitigen UI-Aktualisierung. Der Fokus der weiteren
Fehleranalyse wurde daher auf die Benutzeroberfläche und deren Zusammenspiel mit der
zugrunde liegenden Logik gerichtet.
