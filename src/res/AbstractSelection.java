/**
 * Superclass for holding a user-selected collection of {@link Feature} items.
 */
public abstract class AbstractSelection {

	private Map<Layer, CollectionMap<Feature, Integer>> layerMap = new HashMap<>();

	public abstract String getRendererContentID();

	private AbstractSelection child;

	private AbstractSelection parent;

	private SelectionManager selectionManager;

	public AbstractSelection(SelectionManager selectionManager) {

		this.selectionManager = selectionManager;
	}

	public abstract List<Geometry> items(Geometry geometry);

	public Collection<Geometry> items(Geometry geometry, Collection<Integer> indices) {

		List<Geometry> allItems = items(geometry);
		ArrayList<Geometry> items = new ArrayList<>();

		for (Iterator<Integer> i = indices.iterator(); i.hasNext();) {

			Integer index = i.next();
			items.add(allItems.get(index));
		}

		return items;
	}

	/**
	 * Note that some features in the map may not have selected items.
	 */
	public CollectionMap<Feature, Integer> getFeatureToSelectedItemIndexCollectionMap(Layer layer) {

		if (!layerMap.containsKey(layer))
			layerMap.put(layer, new CollectionMap<>());

		return layerMap.get(layer);
	}

	public Collection<Integer> getSelectedItemIndices(Layer layer, Feature feature) {

		Collection<Integer> indices = getFeatureToSelectedItemIndexCollectionMap(layer).getItems(feature);

		return indices == null ? new ArrayList<>() : indices;
	}

	/**
	 * Note that some features in the map may not have selected items.
	 */
	public CollectionMap<Feature, Geometry> getFeatureToSelectedItemCollectionMap(Layer layer) {

		CollectionMap<Feature, Geometry> collectionMap = new CollectionMap<>();

		for (Iterator<Feature> i = getFeatureToSelectedItemIndexCollectionMap(layer).keySet().iterator(); i
				.hasNext();) {

			Feature feature = i.next();
			collectionMap.put(feature,
					items(feature.getGeometry(), getFeatureToSelectedItemIndexCollectionMap(layer).getItems(feature)));
		}
		return collectionMap;
	}

	public Collection<Layer> getLayersWithSelectedItems() {

		ArrayList<Layer> layersWithSelectedItems = new ArrayList<>();

		for (Iterator<Layer> i = layerMap.keySet().iterator(); i.hasNext();) {

			Layer layer = i.next();

			if (!getFeaturesWithSelectedItems(layer).isEmpty())
				layersWithSelectedItems.add(layer);
		}

		return layersWithSelectedItems;
	}

	public Collection<Feature> getFeaturesWithSelectedItems() {

		ArrayList<Feature> featuresWithSelectedItems = new ArrayList<>();

		for (Iterator<Layer> i = layerMap.keySet().iterator(); i.hasNext();) {

			Layer layer = i.next();
			featuresWithSelectedItems.addAll(getFeaturesWithSelectedItems(layer));
		}
		return featuresWithSelectedItems;
	}

	public Collection<Feature> getFeaturesWithSelectedItems(Layer layer) {

		ArrayList<Feature> featuresWithSelectedItems = new ArrayList<>();

		for (Iterator<Feature> i = getFeatureToSelectedItemIndexCollectionMap(layer).keySet().iterator(); i
				.hasNext();) {

			Feature feature = i.next();

			if (!getFeatureToSelectedItemIndexCollectionMap(layer).getItems(feature).isEmpty()) {

				featuresWithSelectedItems.add(feature);
			}
		}

		return featuresWithSelectedItems;
	}

	public Collection<Geometry> getSelectedItems() {

		ArrayList<Geometry> selectedItems = new ArrayList<>();

		for (Iterator<Layer> i = new ArrayList<>(layerMap.keySet()).iterator(); i.hasNext();) {

			Layer layer = i.next();
			selectedItems.addAll(getSelectedItems(layer));
		}

		return selectedItems;
	}

	public Collection<Geometry> getSelectedItems(Layer layer) {

		ArrayList<Geometry> selectedItems = new ArrayList<>();

		//Fix für CurrentmodificationException wenn min. 79 Kanten bewegt werden [Luna Jasmin Kiehn]	
		Map<Feature, ?> map = getFeatureToSelectedItemIndexCollectionMap(layer);
		List<Feature> features = new ArrayList<>(map.keySet()); // snapshot der von anderen Threads nicht verändert werden kann, da Lokal
		
		for (Feature feature : features) {
		    selectedItems.addAll(getSelectedItems(layer, feature));
		}
		
		return selectedItems;
	}

	public Collection<Geometry> getSelectedItems(Layer layer, Feature feature) {

		return getSelectedItems(layer, feature, feature.getGeometry());
	}

	/**
	 * @param geometry the feature's Geometry or equivalent; that is, a clone or
	 *                 similar enough Geometry from which Geometries can be
	 *                 retrieved using the selection indices.
	 */
	public Collection<Geometry> getSelectedItems(Layer layer, Feature feature, Geometry geometry) {

		return items(geometry, getFeatureToSelectedItemIndexCollectionMap(layer).getItems(feature));
	}

	public Collection<Integer> indices(Geometry geometry, Collection<Geometry> items) {

		List<Geometry> allItems = items(geometry);
		ArrayList<Integer> indices = new ArrayList<>();

		for (Iterator<Geometry> i = items.iterator(); i.hasNext();) {

			Geometry item = i.next();
			indices.add(allItems.indexOf(item));
		}

		return indices;
	}

	public void unselectItems(Layer layer, CollectionMap<Feature, Geometry> featureToItemCollectionMap) {

		boolean originalPanelUpdatesEnabled = selectionManager.arePanelUpdatesEnabled();
		selectionManager.setPanelUpdatesEnabled(false);
		try {

			for (Iterator<Feature> i = featureToItemCollectionMap.keySet().iterator(); i.hasNext();) {
				Feature feature = i.next();
				unselectItems(layer, feature, featureToItemCollectionMap.getItems(feature));
			}
		} finally {

			selectionManager.setPanelUpdatesEnabled(originalPanelUpdatesEnabled);
		}

		updatePanel();
	}

	public void selectItems(Layer layer, CollectionMap<Feature, Geometry> featureToItemCollectionMap) {

		boolean originalPanelUpdatesEnabled = selectionManager.arePanelUpdatesEnabled();
		selectionManager.setPanelUpdatesEnabled(false);

		try {

			for (Iterator<Feature> i = featureToItemCollectionMap.keySet().iterator(); i.hasNext();) {
				Feature feature = i.next();
				selectItems(layer, feature, featureToItemCollectionMap.getItems(feature));
			}
		} finally {

			selectionManager.setPanelUpdatesEnabled(originalPanelUpdatesEnabled);
		}

		updatePanel();
	}

	public void selectItems(Layer layer, Feature feature, Collection<Geometry> items) {

		Collection<Geometry> itemsToSelect = itemsNotSelectedInAncestors(layer, feature, items);
		boolean originalPanelUpdatesEnabled = selectionManager.arePanelUpdatesEnabled();
		selectionManager.setPanelUpdatesEnabled(false);

		try {

			unselectInDescendants(layer, feature, itemsToSelect);
			getFeatureToSelectedItemIndexCollectionMap(layer).addItems(feature,
					indices(feature.getGeometry(), itemsToSelect));
		} finally {

			selectionManager.setPanelUpdatesEnabled(originalPanelUpdatesEnabled);
		}

		updatePanel();
	}

	public void unselectItems(Layer layer, Feature feature, Collection<Geometry> items) {

		boolean originalPanelUpdatesEnabled = selectionManager.arePanelUpdatesEnabled();
		selectionManager.setPanelUpdatesEnabled(false);
		try {
			getFeatureToSelectedItemIndexCollectionMap(layer).removeItems(feature,
					indices(feature.getGeometry(), items));
		} finally {
			selectionManager.setPanelUpdatesEnabled(originalPanelUpdatesEnabled);
		}
		updatePanel();
	}

	public Collection<Geometry> itemsNotSelectedInAncestors(Layer layer, Feature feature, Collection<Geometry> items) {

		ArrayList<Geometry> itemsNotSelectedInAncestors = new ArrayList<>();

		if (layer.isSelectable())
			for (Iterator<Geometry> i = items.iterator(); i.hasNext();) {

				Geometry item = i.next();

				if (!selectedInAncestors(layer, feature, item))
					itemsNotSelectedInAncestors.add(item);
			}

		return itemsNotSelectedInAncestors;
	}

	protected abstract boolean selectedInAncestors(Layer layer, Feature feature, Geometry item);

	protected abstract void unselectInDescendants(Layer layer, Feature feature, Collection<Geometry> items);

	public void selectItems(Layer layer, Feature feature) {

		selectItems(layer, feature, items(feature.getGeometry()));
	}

	public void selectItems(Layer layer, Collection<Feature> features) {

		boolean originalPanelUpdatesEnabled = selectionManager.arePanelUpdatesEnabled();
		selectionManager.setPanelUpdatesEnabled(false);
		try {

			for (Iterator<Feature> i = new ArrayList<>(features).iterator(); i.hasNext();)
				selectItems(layer, i.next());
		} finally {

			selectionManager.setPanelUpdatesEnabled(originalPanelUpdatesEnabled);
		}
		updatePanel();
	}

	public void unselectFromFeaturesWithModifiedItemCounts(Layer layer, Collection<Feature> features,
			Collection<Feature> oldFeatureClones) {

		ArrayList<Feature> featuresToUnselect = new ArrayList<>();
		Iterator<Feature> j = oldFeatureClones.iterator();
		j.hasNext();

		for (Iterator<Feature> i = features.iterator(); i.hasNext();) {

			Feature feature = i.next();
			Feature oldFeatureClone = j.next();

			if (items(feature.getGeometry()).size() != items(oldFeatureClone.getGeometry()).size())
				featuresToUnselect.add(feature);
		}

		unselectItems(layer, featuresToUnselect);
	}

	public void unselectItems() {

		layerMap.clear();
		updatePanel();
	}

	public void unselectItems(Layer layer) {

		layerMap.remove(layer);
		updatePanel();
	}

	public void unselectItems(Layer layer, Collection<Feature> features) {

		boolean originalPanelUpdatesEnabled = selectionManager.arePanelUpdatesEnabled();
		selectionManager.setPanelUpdatesEnabled(false);
		try {

			for (Iterator<Feature> i = features.iterator(); i.hasNext();)
				unselectItems(layer, i.next());
		} finally {

			selectionManager.setPanelUpdatesEnabled(originalPanelUpdatesEnabled);
		}
		updatePanel();
	}

	public void unselectItems(Layer layer, Feature feature) {

		getFeatureToSelectedItemIndexCollectionMap(layer).remove(feature);
		updatePanel();
	}

	public void unselectItem(Layer layer, Feature feature, int selectedItemIndex) {

		getFeatureToSelectedItemIndexCollectionMap(layer).removeItem(feature, selectedItemIndex);
	}

	private void updatePanel() {

		selectionManager.updatePanel();
	}

	public void setChild(AbstractSelection child) {

		this.child = child;
	}

	public void setParent(AbstractSelection parent) {

		this.parent = parent;
	}

	protected AbstractSelection getChild() {

		return child;
	}

	protected AbstractSelection getParent() {

		return parent;
	}

}
