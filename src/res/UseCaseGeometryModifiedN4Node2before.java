
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
      }
     
      // Usecase wieder hinzufuegen [02.02.2026 - Ersetzt durch UseCaseManager - ]
      UseCaseManager.addUseCaseFeatureEventTypeGeometryModified(TerminalLayer.EDGES_N4, useCase);
    }
    
    flushLogInfo();
  }
}