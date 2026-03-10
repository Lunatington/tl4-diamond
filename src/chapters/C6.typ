#import "../components/formatting.typ": clickable_link, needsCite, todo, needsSpelling
= Refresh <Kapitel8>
Nach der Behebung der ```"ConcurrentModificationException"``` trat ein weiteres Problem auf: Die
UI wurde nach dem Verschieben des Knotens nicht korrekt aktualisiert, wodurch der Knoten und
die zugehörigen Kanten doppelt in der Anwendung angezeigt wurden. Dabei war das alte Element
noch an der ursprünglichen Position sichtbar, während das verschobene Element bereits an der
neuen Position dargestellt wurde.

#figure(
  image("../res/Bild_Nach_Fix.png", height: 10cm),
  caption: "Beide Knoten und Kanten noch sichtbar",
)

Dieses Problem war vor dem Fix nicht sichtbar, da die ```"ConcurrentModificationException"``` die
Ausführung des UseCases vorzeitig abbrach. Durch den Fix wird der UseCase nun vollständig
durchlaufen, wodurch das bereits bestehende – aber zuvor verdeckte – Darstellungsproblem
erstmals in Erscheinung trat.

Behoben wurde dies in der Klasse ```"UseCaseGeometryModifiedN4Node"```, welche den UseCase
zum Modifizieren von N4-Wegenetz-Punkten implementiert. Nachdem alle betroffenen Kanten
angepasst wurden, wird mit ```"setFeatureCollectionModified(true)"``` der Layer als verändert
markiert. Anschließend wird über ```"SwingUtilities.invokeLater()"``` ein expliziter Repaint des
LayerViewPanel angestoßen.

```Java
if (layerChanged) {
  layerManager.getLayer(TerminalLayer.EDGES_N4.getName()).setFeatureCollectionModified(true);

  //Zwingt den Refresh nach verschiebung 
  SwingUtilities.invokeLater(() -> {
      getWorkbenchFrame()
          .getContext()
          .getLayerViewPanel()
          .repaint();});
}
```

Da Swing-UI-Updates im ```"Event-Dispatch-Thread"``` ausgeführt werden müssen, stellt
```"invokeLater()"``` sicher, dass der Repaint korrekt im richtigen Thread ausgeführt wird. Swing ist
das in Java integrierte Framework zur Erstellung von grafischen Benutzeroberflächen, welches alle
UI-Operationen in einem dedizierten Thread – dem sogenannten ```"Event-Dispatch-Thread"``` – ausführt.
Durch den expliziten Repaint-Aufruf wird die doppelte Darstellung zuverlässig verhindert, ohne dass
der Benutzer die Kartenansicht manuell verschieben muss.


