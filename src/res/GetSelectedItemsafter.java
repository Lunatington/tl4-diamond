public Collection<Geometry> getSelectedItems(Layer layer) {

	ArrayList<Geometry> selectedItems = new ArrayList<>();

	List<Feature> features = new ArrayList<>(map.keySet()); // snapshot der von anderen Threads nicht verändert werden kann, da Lokal
		
	for (Feature feature : features) {
		selectedItems.addAll(getSelectedItems(layer, feature));
	}

	return selectedItems;
}