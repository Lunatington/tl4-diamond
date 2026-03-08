public Collection<Geometry> getSelectedItems(Layer layer) {

	ArrayList<Geometry> selectedItems = new ArrayList<>();

	for (Iterator<Feature> i = getFeatureToSelectedItemIndexCollectionMap(layer).keySet().iterator(); i.hasNext();) {
			Feature feature = i.next();
			selectedItems.addAll(getSelectedItems(layer, feature));
		}
		
	return selectedItems;
}