
/** UseCase zum Modifizieren von N4-Wegenetz-Punkten */
public class UseCaseGeometryModifiedN4Node extends AbstractUseCase {
  public UseCaseGeometryModifiedN4Node(WorkbenchFrame workbenchFrame) {
    super(workbenchFrame, UseCaseGeometryModifiedN4Node.class.getCanonicalName());
  }

  @Override
  public void execute(FeatureEvent event) {
    initLogInfo();

    Iterator<Feature> oldFeaturesIterator = event.getOldFeatureClones().iterator();

    this.addLogInfo("UseCase ausgeführt für " + event.getFeatures().size() + " Objekt(e):  ");

    for (Feature currentFeature : event.getFeatures()) {
      Feature oldFeature = oldFeaturesIterator.next();
      String currentID = (String) currentFeature.getAttribute(LayerNodesN4.ATTRIBUTE_ID_N4);

      this.addLogInfo("ID " + currentID + "  --  " + GeometricalFunctions.compareVertices(currentFeature, oldFeature).getName());

      PrecisionReducer.reduce(currentFeature);

      // Wenn sich Position einer Node ge&auml;ndert hat, m&uuml;ssen alle N4-Edges, die diese Node verwenden, angepasst werden
      String nodeIngressID, nodeEgressID;
      LayerManager layerManager = getWorkbenchFrame().getContext().getLayerManager();
      GeometryFactory factory = new GeometryFactory();

      // UseCase entfernen [02.02.2026 - Ersetzt durch UseCaseManager - Luna Jasmin Kiehn]
      UseCase useCase = UseCaseManager.removeUseCaseFeatureEventTypeGeometryModified(TerminalLayer.EDGES_N4);

      boolean edgeChanged;
      boolean layerChanged = false;
      // Edges anpassen
      for (Feature edge : layerManager.getLayer(TerminalLayer.EDGES_N4.getName()).getFeatureCollectionWrapper().getFeatures()) {
        edgeChanged = false;
        Coordinate[] edgeCoordinates = edge.getGeometry().getCoordinates();
        nodeIngressID = (String) edge.getAttribute(LayerEdgesN4.ATTRIBUTE_INGRESS_VERTEX);
        nodeEgressID = (String) edge.getAttribute(LayerEdgesN4.ATTRIBUTE_EGRESS_VERTEX);

        if (nodeIngressID.equals(currentID)) {
          edgeCoordinates[0] = currentFeature.getGeometry().getCoordinate();
          edgeChanged = true;
          layerChanged = true;
        }

        if (nodeEgressID.equals(currentID)) {
          edgeCoordinates[1] = currentFeature.getGeometry().getCoordinate();
          edgeChanged = true;
          layerChanged = true;
        }

        if (edgeChanged) {
          edge.setGeometry(factory.createLineString(edgeCoordinates));
          this.addLogInfo("Edge von " + nodeIngressID + " nach " + nodeEgressID + ":  Geometrie geändert!");
        }
      }

      if (layerChanged) {
        layerManager.getLayer(TerminalLayer.EDGES_N4.getName()).setFeatureCollectionModified(true);

        //Zwingt den Refresh nach verschiebung [Luna Jasmin Kiehn]
        SwingUtilities.invokeLater(() -> {
            getWorkbenchFrame()
                .getContext()
                .getLayerViewPanel()
                .repaint();});
      }
     
      // Usecase wieder hinzufuegen [02.02.2026 - Ersetzt durch UseCaseManager - Luna Jasmin Kiehn]
      UseCaseManager.addUseCaseFeatureEventTypeGeometryModified(TerminalLayer.EDGES_N4, useCase);
    }



   
    flushLogInfo();
  }
}