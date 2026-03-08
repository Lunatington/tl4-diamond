public abstract class AbstractTerminalGeometrieeditorTestCase extends AbstractGeoTestCase {

    private static final String DEFAULT_DOOR_DIRECTION = DoorDirection.EITHER.getDialogValue();
    private static final String DEFAULT_CODE_CHARS = "";
    private static final String DEFAULT_LABEL_FORMAT_BLOCK = "B2";
    private static final String DEFAULT_LABEL_FORMAT_ROW = "R2";
    private static final String LABEL_FORMAT_ROW_3 = "R3";
    private static final String DEFAULT_LABEL_FORMAT_COL = "C2";
    private static final String LABEL_FORMAT_COL_1 = "C1";
    private static final String DEFAULT_LABEL_FORMAT_TIER = "T1";
    private static final String DEFAULT_USER_LABEL = DEFAULT_LABEL_FORMAT_BLOCK + DEFAULT_LABEL_FORMAT_ROW + DEFAULT_LABEL_FORMAT_COL + DEFAULT_LABEL_FORMAT_TIER;
    private static final String DEFAULT_TABLE_ROW = "TWO";
    private static final String DEFAULT_TABLE_COL = "TWO";
    public static final String TABLE_ONE_DIGIT = "ONE";
    public static final String TABLE_TWO_DIGITS = "TWO";
    public static final String TABLE_THREE_DIGITS = "THREE";
    public static final String TABLE_CHAR = "CHAR";

    private static final String TABLE_COL_GRID = "CHAR";
    private static final String DEFAULT_TABLE_TIER = "ONE";

    private static final String DEFAULT_COMPRESS_SCAN = NO;
    private static final String DEFAULT_EXTENDS_ASCENDING = YES;
    private static final String DEFAULT_XPS_OUTLINE = XPSBlockOutline.BLOCK.getDialogValue();
    private static final StorageArea STORAGE_AREA_TERMINAL = StorageArea.TERMINAL;

    private final static Logger logger = LoggerFactory.getLogger(AbstractTerminalGeometrieeditorTestCase.class);

    static {
        MyWorkbench workbench = null;
        try {
            workbench = new MyWorkbench("Test", new String[] {}, IconLoader.icon("app-icon.gif"), null, null);
            context = workbench.getContext().createPlugInContext();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TerminalGeometrieeditorInit.addSingletonInstances(context);
        TerminalGeometrieeditorInit.addFeatureSchemataForPlugIn();
        TerminalGeometrieeditorInit.addFeatureSchemataOtherLayers();
        TerminalGeometrieeditorInit.addLayoutFactories();
        TerminalGeometrieeditorInit.addUsecases(context);
        TerminalGeometrieeditorInit.addXMLTagInterpreters();

        useCaseFactory = (UseCaseFactory) SingletonFactory.getInstance().getSingleton(SingletonName.USECASE_FACTORY);
        plugInFactory = (PlugInFactory) SingletonFactory.getInstance().getSingleton(SingletonName.PLUGIN_FACTORY);
        schemaFactory = (FeatureSchemaFactory) SingletonFactory.getInstance().getSingleton(SingletonName.FEATURE_SCHEMA_FACTORY);

        layerManager = new LayerManager();

        // Kategorie 'Vermessung':
        layerManager.addLayer(Category.SURVEY_DATA.getName(), TerminalLayer.QUAY_METERMARKS.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.QUAY_METERMARKS)));
        layerManager.addLayer(Category.SURVEY_DATA.getName(), TerminalLayer.BERTH.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.BERTHS)));
        layerManager.addLayer(Category.SURVEY_DATA.getName(), TerminalLayer.SURVEY_POINTS.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.SURVEY_POINTS)));
        layerManager.addLayer(Category.SURVEY_DATA.getName(), TerminalLayer.REF_POINTS_N4.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.N4_REF_POINTS)));

        // Kategorie 'Flaechen':
        layerManager.addLayer(Category.AREAS.getName(), TerminalLayer.PARKING_AREAS.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.PARKING_AREAS)));
        layerManager.addLayer(Category.AREAS.getName(), TerminalLayer.BLOCKED_AREAS.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.BLOCKED_AREAS)));
        layerManager.addLayer(Category.AREAS.getName(), TerminalLayer.TRACKS.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.TRACKS)));
        layerManager.addLayer(Category.AREAS.getName(), TerminalLayer.TRANSFER_AREAS.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.TRANSFER_AREAS)));
        layerManager.addLayer(Category.AREAS.getName(), TerminalLayer.BLOCKS.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.BLOCKS)));
        layerManager.addLayer(Category.AREAS.getName(), TerminalLayer.TRAFFIC_AREAS.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.TRAFFIC_AREAS)));

        // Kategorie 'Flaechenbestandteile':
        layerManager.addLayer(Category.EMBEDDED_AREAS.getName(), TerminalLayer.OBSTACLES.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.OBSTACLES)));
        layerManager.addLayer(Category.EMBEDDED_AREAS.getName(), TerminalLayer.PARKING_LOTS.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.PARKING_LOTS)));
        layerManager.addLayer(Category.EMBEDDED_AREAS.getName(), TerminalLayer.TRANSFER_LANES.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.TRANSFER_LANES)));
        layerManager.addLayer(Category.EMBEDDED_AREAS.getName(), TerminalLayer.GROUNDSLOTS_20.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.GROUNDSLOTS)));
        layerManager.addLayer(Category.EMBEDDED_AREAS.getName(), TerminalLayer.GROUNDSLOTS_30.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.GROUNDSLOTS)));
        layerManager.addLayer(Category.EMBEDDED_AREAS.getName(), TerminalLayer.GROUNDSLOTS_40.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.GROUNDSLOTS)));
        layerManager.addLayer(Category.EMBEDDED_AREAS.getName(), TerminalLayer.GROUNDSLOTS_45.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.GROUNDSLOTS)));

        // Kategorie 'Wegenetz N4':
        layerManager.addLayer(Category.PATHNET_N4.getName(), TerminalLayer.NODES_N4.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.NODES_N4)));
        layerManager.addLayer(Category.PATHNET_N4.getName(), TerminalLayer.EDGES_N4.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.EDGES_N4)));

        // Kategorie 'Wegenetz':
        layerManager.addLayer(Category.PATHNETWORK.getName(), TerminalLayer.NODES.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.NODES)));
        layerManager.addLayer(Category.PATHNETWORK.getName(), TerminalLayer.EDGES.getName(), new FeatureDataset(schemaFactory.getFeatureSchema(SchemaName.EDGES)));

        if (workbench != null) {
            WorkbenchContext workbenchContext = workbench.getContext();
            if (workbenchContext != null) {
                ((MyWorkbenchContext) workbenchContext).setLayerManager(layerManager);
            }
        }
        TerminalGeometrieeditorInit.addMenuItems(context);

        n4NodeFinder = (N4NodeFinder) SingletonFactory.getInstance().getSingleton(SingletonName.N4_NODE_FINDER);

        BinNameTable.addIncludedMapping(DEFAULT_TABLE_COL);
        BinNameTable.addIncludedMapping(DEFAULT_TABLE_ROW);
        BinNameTable.addIncludedMapping(DEFAULT_TABLE_TIER);
        BinNameTable.addIncludedMapping(TABLE_CHAR);
        BinNameTable.addIncludedMapping(TABLE_COL_GRID);
    }


    public void setUp() throws Exception {
        super.setUp();
        
        // alle Layer leeren:
        Iterator<Layer> iterator = layerManager.iterator();
        Layer layer;

        while (iterator.hasNext()) {
            layer = iterator.next();
            layer.getFeatureCollectionWrapper().clear();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Flaechen erzeugen:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Erzeugt einen rechtwinkligen Block incl. Groundslots. Die Platzrichtung verl&auml;uft vertikal von (xMin, yMin) in positiver Y-Richtung, die Baseline horizontal von (xMin, yMin)
     * nach (xMax, yMin) in positiver X-Richtung
     */
    protected static Feature createBlock(String name, double xMin, double yMin, int numRows, int numBays, String blockType, Set<Facility> facilities) {
        // TODO_26: ueber XMLImport loesen, wenn GS-Mittelpunkte relvant  --  ueber createBlock(), wenn Block-Ecken relevant
        return createBlock(name, xMin, yMin, numRows, numBays, blockType, 0, 0, facilities);
    }

    /**
     * Erzeugt einen rechtwinkligen Block incl. Groundslots. Dabei werden das angegebene Reihen- und Platzoffset ber&uuml;cksichtigt. Die Platzrichtung verl&auml;uft vertikal von
     * (xMin, yMin) in positiver Y-Richtung, die Baseline horizontal von (xMin, yMin) nach (xMax, yMin) in positiver X-Richtung
     */
    protected static Feature createBlock(String name, double xMin, double yMin, int numRows, int numBays, String blockType, int rowOffset, int bayOffset, Set<Facility> facilities) {
        // Block erzeugen: ceil wg. Rundungsfehlern => funzt, da GS > 1.0 m
        double xMax, yMax;

        switch (blockType) {
        case LayerBlocks.TYPE_EMPTY:
        case LayerBlocks.TYPE_EMPTY_FLAT:
            yMax = Math.ceil(GroundslotData.WIDTH * numBays + DEFAULT_BAY_DELTA_MT * (numBays) + yMin); // TODO: Platzabstand = ???
            xMax = Math.ceil(GroundslotData.LENGTH_20FT * numRows + (numRows - 1) * DEFAULT_ROW_DELTA + xMin);
            break;

        default:
            yMax = Math.ceil(GroundslotData.LENGTH_20FT * numBays + GroundslotData.VC_BAY_DELTA_20FT * (numBays) + yMin);
            if (blockType.equals(LayerBlocks.TYPE_BS_WS)) {
                yMax += GroundslotData.BS_WS_EXTENDED_BAY;
            }
            xMax = Math.ceil(GroundslotData.WIDTH * numRows + (numRows - 1) * DEFAULT_ROW_DELTA + xMin);
            break;
        }

        return createBlockHorizontalBaseline(name, xMin, yMin, xMax, yMax, blockType, rowOffset, bayOffset, facilities);
    }


    /**
     * Erzeugt einen rechtwinkligen Block. Die Erzeugung der Groundslots erfolgt in den aufgerufenen Methoden durch den entsprechenden UseCase. Die Platzrichtung verl&auml;uft vertikal
     * von (xMin, yMin) nach (xMin, yMax), die Baseline horizontal von (xMin, yMin) nach (xMax, yMin)
     */
    protected static Feature createBlockHorizontalBaseline(String name, double xMin, double yMin, double xMax, double yMax, String blockType, int rowOffset, int bayOffset, Set<Facility> facilities) {
        Coordinate[] coordinates = { new Coordinate(xMin, yMin), new Coordinate(xMin, yMax), new Coordinate(xMax, yMax), new Coordinate(xMax, yMin), new Coordinate(xMin, yMin) };

        return createBlock(name, coordinates, blockType, rowOffset, bayOffset, DEFAULT_ROW_DELTA, GroundslotData.VC_BAY_DELTA_20FT, facilities);
    }


    /**
     * Erzeugt einen rechtwinkligen Block mit Groundslots. Die Platzrichtung verl&auml;uft vertikal von (xMin, yMin) nach (xMin, yMax), die Baseline horizontal von (xMin, yMin) nach
     * (xMax, yMin)
     */
    protected static Feature createBlockHorizontalBaseline(String name, double xMin, double yMin, double xMax, double yMax, String blockType, int rowOffset, int bayOffset, double rowDelta,
            double bayDelta, Set<Facility> facilities) {
        Coordinate[] coordinates = { new Coordinate(xMin, yMin), new Coordinate(xMin, yMax), new Coordinate(xMax, yMax), new Coordinate(xMax, yMin), new Coordinate(xMin, yMin) };

        return createBlock(name, coordinates, blockType, rowOffset, bayOffset, rowDelta, bayDelta, facilities);
    }


    /**
     * Erzeugt einen rechtwinkligen Block ohne Groundslots <em> mit Orientierung 270 Grad</em>. Die Platzrichtung verl&auml;uft horizontal von (xMin, yMin) nach (xMax, yMin), die
     * Baseline vertikal von (xMin, yMin) nach (xMin, yMax)
     */
    protected static Feature createBlockVerticalBaseline(String name, double xMin, double yMin, double xMax, double yMax, String blockType, double rowDelta, double bayDelta,
            Set<Facility> facilities) {
        Coordinate[] coordinates = { new Coordinate(xMin, yMin), new Coordinate(xMax, yMin), new Coordinate(xMax, yMax), new Coordinate(xMin, yMax), new Coordinate(xMin, yMin) };

        return createBlock(name, coordinates, blockType, 0, 0, rowDelta, bayDelta, facilities);
    }


    /**
     * Erzeugt einen Block incl. Groundslots mit den angegebenen Koordinaten. Die Groundslot-Berechnung erfolgt durch den entsprechenden UseCase<br>
     * Die Groundslot-bezogenen Attribute werden auf Default-Werte gesetzt. Die Orientierung der Fl&auml;che wird berechnet.
     */
    protected static Feature createBlock(String name, Coordinate[] coordinates, String blockType, int rowOffset, int bayOffset, double rowDelta, double bayDelta, Set<Facility> facilities) {
        Feature currentBlock = createStructuredArea(name, coordinates, SchemaName.BLOCKS, blockType, new VehicleType[] { VehicleType.VC }, rowOffset, bayOffset, rowDelta, bayDelta, facilities);

        currentBlock.setAttribute(LayerBlocks.ATTRIBUTE_MAX_LOGICAL_HEIGHT, 3);
        currentBlock.setAttribute(LayerBlocks.ATTRIBUTE_MAX_PHYSICAL_HEIGHT, 7.777);

        List<Feature> featureList = new ArrayList<>();
        featureList.add(currentBlock);

        getLayer(TerminalLayer.BLOCKS).getFeatureCollectionWrapper().add(currentBlock);

        useCaseFactory.getUseCase(TerminalLayer.BLOCKS, FeatureEventType.ADDED).execute(new FeatureEvent(featureList, FeatureEventType.ADDED, getLayer(TerminalLayer.BLOCKS), null));

        return currentBlock;
    }


    /**
     * Erzeugt einen Block incl. Groundslots mit den angegebenen Koordinaten. Die Groundslot-Berechnung erfolgt durch den entsprechenden UseCase<br>
     * Die Groundslot-bezogenen Attribute werden auf Default-Werte gesetzt. Die Orientierung der Fl&auml;che wird berechnet.
     */
    protected static Feature createBlock(String name, Coordinate[] coordinates, String blockType, int rowOffset, int bayOffset, Set<Facility> facilities) {
        Feature currentBlock = createStructuredArea(name, coordinates, SchemaName.BLOCKS, blockType, new VehicleType[] { VehicleType.VC }, rowOffset, bayOffset, GroundslotData.ROW_DELTA_VC,
                GroundslotData.VC_BAY_DELTA_20FT, facilities);

        currentBlock.setAttribute(LayerBlocks.ATTRIBUTE_MAX_LOGICAL_HEIGHT, 3);
        currentBlock.setAttribute(LayerBlocks.ATTRIBUTE_MAX_PHYSICAL_HEIGHT, 7.777);
        currentBlock.setAttribute(LayerStructuredArea.ATTRIBUTE_DOOR_DIRECTION, DoorDirection.EITHER.getDialogValue());

        List<Feature> featureList = new ArrayList<>();
        featureList.add(currentBlock);

        getLayer(TerminalLayer.BLOCKS).getFeatureCollectionWrapper().add(currentBlock);

        useCaseFactory.getUseCase(TerminalLayer.BLOCKS, FeatureEventType.ADDED).execute(new FeatureEvent(featureList, FeatureEventType.ADDED, getLayer(TerminalLayer.BLOCKS), null));

        return currentBlock;
    }


    /** Erzeugt eine rechteckige Parkplatzfl&auml;che mit dem angegebenen Reihenoffset und Orientierung 0 Grad */
    protected static Feature createParkingArea(String name, double xMin, double yMin, int rowOffset, int numParkingLots, String parkingAreaType, VehicleType[] vehicles, Set<Facility> facilities) {
        // TransferArea erzeugen: ceil wg. Rundungsfehlern => funzt, da Lanes > 1.0 m
        double yMax = Math.ceil(ParkingLotData.LENGTH_VC + DEFAULT_BAY_DELTA + yMin);
        double xMax = Math.ceil(ParkingLotData.WIDTH_VC * numParkingLots + (numParkingLots - 1) * DEFAULT_ROW_DELTA + xMin);

        Coordinate[] coordinates = { new Coordinate(xMin, yMin), new Coordinate(xMin, yMax), new Coordinate(xMax, yMax), new Coordinate(xMax, yMin), new Coordinate(xMin, yMin) };

        Feature currentParkingArea = createStructuredArea(name, coordinates, SchemaName.PARKING_AREAS, parkingAreaType, vehicles, 0, 0, DEFAULT_ROW_DELTA, DEFAULT_BAY_DELTA, facilities);
        currentParkingArea.setAttribute(LayerStructuredArea.ATTRIBUTE_ROW_OFFSET, rowOffset);

        List<Feature> featureList = new ArrayList<>();
        featureList.add(currentParkingArea);

        getLayer(TerminalLayer.PARKING_AREAS).getFeatureCollectionWrapper().add(currentParkingArea);

        useCaseFactory.getUseCase(TerminalLayer.PARKING_AREAS, FeatureEventType.ADDED).execute(new FeatureEvent(featureList, FeatureEventType.ADDED, getLayer(TerminalLayer.PARKING_AREAS), null));

        return currentParkingArea;
    }


    /** Erzeugt eine Parkplatzfl&auml;che mit Dummy-Geometrie */
    protected static Feature createSimpleParkingArea(String name, String type, VehicleType vehicleType, String orientation, String bayOffset, String rowOffset, String bayBorderDelta,
            String rowBorderDelta, Set<Facility> facilities) {
        Feature feature = createArea(name, new Coordinate[] {}, SchemaName.PARKING_AREAS, type, new VehicleType[] { vehicleType }, facilities);

        feature.setGeometry(DUMMY_POLYGON);
        feature.setAttribute(LayerStructuredArea.ATTRIBUTE_ORIENTATION, orientation);
        feature.setAttribute(LayerStructuredArea.ATTRIBUTE_BAY_OFFSET, bayOffset);
        feature.setAttribute(LayerStructuredArea.ATTRIBUTE_ROW_OFFSET, rowOffset);
        feature.setAttribute(LayerStructuredArea.ATTRIBUTE_BAY_BORDER_DELTA, bayBorderDelta);
        feature.setAttribute(LayerStructuredArea.ATTRIBUTE_ROW_BORDER_DELTA, rowBorderDelta);
        feature.setAttribute(LayerStructuredArea.ATTRIBUTE_BAY_DELTA, DEFAULT_BAY_DELTA);
        feature.setAttribute(LayerStructuredArea.ATTRIBUTE_ROW_DELTA, DEFAULT_ROW_DELTA);
        feature.setAttribute(LayerStructuredArea.ATTRIBUTE_ENTRY_FIRST_COL, YES);
        feature.setAttribute(LayerStructuredArea.ATTRIBUTE_ENTRY_LAST_COL, YES);

        getLayer(TerminalLayer.PARKING_AREAS).getFeatureCollectionWrapper().add(feature);

        return feature;
    }


    /** Erzeugt einen rechteckigen &Uuml;bergabebereich mit Orientierung nach Norden */
    protected static Feature createTransferArea(String name, double xMin, double yMin, int numLanes, String transferAreaType, VehicleType[] vehicles, int rowOffset, String entryFirstColumn,
            String entryLastColumn, Set<Facility> facilities) {
        // Fehler, falls Typ=LayerTransferAreas.TYPE_LKS und Anz. Reihen > 4
        // (widerspricht dem Layout!!!)
        com.vividsolutions.jts.util.Assert.isTrue(!transferAreaType.equals(LayerTransferAreas.TYPE_LKS) || numLanes <= 4, "Nur max. vier Reihen erlaubt!");

        double rowDelta = TransferAreaData.ROW_DELTA_WIDTH;
        double rowBorderDelta = TransferAreaData.ROW_BOUNDARY_WIDTH;

        // TransferArea erzeugen: ceil wg. Rundungsfehlern => funzt, da Lanes > 1.0 m
        double yMax = // Math.ceil(
                yMin + TransferAreaData.LENGTH + DEFAULT_BAY_DELTA; // );
        double xMax = // Math.ceil(
                xMin + rowBorderDelta + TransferAreaData.WIDTH * numLanes + (numLanes - 1) * rowDelta;

        Coordinate[] coordinates = { new Coordinate(xMin, yMin), new Coordinate(xMin, yMax), new Coordinate(xMax, yMax), new Coordinate(xMax, yMin), new Coordinate(xMin, yMin) };

        Feature currentTransferArea = createStructuredArea(name, coordinates, SchemaName.TRANSFER_AREAS, transferAreaType, vehicles, rowOffset, 0, DEFAULT_ROW_DELTA, DEFAULT_BAY_DELTA, facilities);
        currentTransferArea.setAttribute(LayerStructuredArea.ATTRIBUTE_ROW_DELTA, rowDelta);
        currentTransferArea.setAttribute(LayerStructuredArea.ATTRIBUTE_ENTRY_FIRST_COL, entryFirstColumn);
        currentTransferArea.setAttribute(LayerStructuredArea.ATTRIBUTE_ENTRY_LAST_COL, entryLastColumn);
        currentTransferArea.setAttribute(LayerStructuredArea.ATTRIBUTE_LABEL_FORMAT_COL, transferAreaType.equals(LayerTransferAreas.TYPE_CHASSIS) ? DEFAULT_LABEL_FORMAT_COL : LABEL_FORMAT_COL_1);
        currentTransferArea.setAttribute(LayerStructuredArea.ATTRIBUTE_USER_LABEL_FORMAT,
                transferAreaType.equals(LayerTransferAreas.TYPE_CHASSIS) ? DEFAULT_LABEL_FORMAT_BLOCK + DEFAULT_LABEL_FORMAT_COL
                        : DEFAULT_LABEL_FORMAT_BLOCK + DEFAULT_LABEL_FORMAT_ROW + "." + LABEL_FORMAT_COL_1);
        currentTransferArea.setAttribute(LayerStructuredArea.ATTRIBUTE_TABLE_COL, transferAreaType.equals(LayerTransferAreas.TYPE_CHASSIS) ? TABLE_TWO_DIGITS : TABLE_COL_GRID);

        List<Feature> featureList = new ArrayList<>();
        featureList.add(currentTransferArea);

        getLayer(TerminalLayer.TRANSFER_AREAS).getFeatureCollectionWrapper().add(currentTransferArea);

        useCaseFactory.getUseCase(TerminalLayer.TRANSFER_AREAS, FeatureEventType.ADDED).execute(new FeatureEvent(featureList, FeatureEventType.ADDED, getLayer(TerminalLayer.TRANSFER_AREAS), null));

        return currentTransferArea;
    }


    /**
     * Erzeugt einen rechtwinkligen Rail-&Uuml;bergabebereich incl. Groundslots. Die Platzrichtung verl&auml;uft vertikal von (xMin, yMin) in positiver Y-Richtung, die Baseline
     * horizontal von (xMin, yMin) nach (xMax, yMin) in positiver X-Richtung
     */
    protected static Feature createTransferAreaRail(String name, double xMin, double yMin, int numRows, int numBays, Set<Facility> facilities) {
        return createTransferAreaRail(name, xMin, yMin, numRows, numBays, 0, 0, facilities);
    }


    /**
     * Erzeugt einen rechtwinkligen Bahn-&Uuml;bergabebereich (RS-Zugriff) incl. Groundslots. Dabei werden das angegebene Reihen- und Platzoffset ber&uuml;cksichtigt. Die Platzrichtung
     * verl&auml;uft vertikal von (xMin, yMin) in positiver Y-Richtung, die Baseline horizontal von (xMin, yMin) nach (xMax, yMin) in positiver X-Richtung
     */
    protected static Feature createTransferAreaRail(String name, double xMin, double yMin, int numRows, int numBays, int rowOffset, int bayOffset, Set<Facility> facilities) {
        // Block erzeugen: ceil wg. Rundungsfehlern => funzt, da GS > 1.0 m
        double yMax = Math.ceil(GroundslotData.LENGTH_20FT * numBays + GroundslotData.VC_BAY_DELTA_20FT * (numBays) + yMin);
        double xMax = Math.ceil(GroundslotData.WIDTH * numRows + (numRows - 1) * DEFAULT_ROW_DELTA + xMin);

        return createTransferAreaRailHorizontalBaseline(name, xMin, yMin, xMax, yMax, rowOffset, bayOffset, facilities);
    }


    /**
     * Erzeugt einen rechtwinkligen Bahn-&Uuml;bergabebereich (RS-Zugriff) incl. Groundslots. Die Platzrichtung verl&auml;uft vertikal von (xMin, yMin) nach (xMin, yMax), die Baseline
     * horizontal von (xMin, yMin) nach (xMax, yMin)
     */
    protected static Feature createTransferAreaRailHorizontalBaseline(String name, double xMin, double yMin, double xMax, double yMax, int rowOffset, int bayOffset, Set<Facility> facilities) {
        Coordinate[] coordinates = { new Coordinate(xMin, yMin), new Coordinate(xMin, yMax), new Coordinate(xMax, yMax), new Coordinate(xMax, yMin), new Coordinate(xMin, yMin) };

        return createTransferAreaRail(name, coordinates, rowOffset, bayOffset, DEFAULT_ROW_DELTA, GroundslotData.VC_BAY_DELTA_20FT, facilities);
    }


    /**
     * Erzeugt einen Bahn-&Uuml;bergabebereich (RS-Zugriff) incl. Groundslots mit den angegebenen Koordinaten. Die Groundslot-Berechnung erfolgt durch den entsprechenden UseCase<br>
     * Die Groundslot-bezogenen Attribute werden auf Default-Werte gesetzt. Die Orientierung der Fl&auml;che wird berechnet.
     */
    protected static Feature createTransferAreaRail(String name, Coordinate[] coordinates, int rowOffset, int bayOffset, double rowDelta, double bayDelta, Set<Facility> facilities) {
        Feature currentArea = createStructuredArea(name, coordinates, SchemaName.TRANSFER_AREAS, LayerTransferAreas.TYPE_RAIL, new VehicleType[] { VehicleType.REACHSTACKER, VehicleType.RMG },
                rowOffset, bayOffset, rowDelta, bayDelta, facilities);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_LABEL_FORMAT_ROW, LABEL_FORMAT_ROW_3);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_LABEL_FORMAT_COL, LABEL_FORMAT_COL_1);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_USER_LABEL_FORMAT, DEFAULT_LABEL_FORMAT_BLOCK + LABEL_FORMAT_ROW_3 + LABEL_FORMAT_COL_1 + DEFAULT_LABEL_FORMAT_TIER);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_TABLE_ROW_20, TABLE_THREE_DIGITS);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_TABLE_ROW_40, TABLE_THREE_DIGITS);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_TABLE_COL, TABLE_ONE_DIGIT);

        List<Feature> featureList = new ArrayList<>();
        featureList.add(currentArea);

        getLayer(TerminalLayer.TRANSFER_AREAS).getFeatureCollectionWrapper().add(currentArea);

        useCaseFactory.getUseCase(TerminalLayer.TRANSFER_AREAS, FeatureEventType.ADDED).execute(new FeatureEvent(featureList, FeatureEventType.ADDED, getLayer(TerminalLayer.TRANSFER_AREAS), null));

        return currentArea;
    }


    /** Erzeugt eine rechteckige Fahrstrasse */
    protected static Feature createTrafficArea(String name, double xMin, double yMin, double xMax, double yMax, String trafficAreaType, VehicleType[] vehicles, Set<Facility> facilities) {
        Coordinate[] coordinates = { new Coordinate(xMin, yMin), new Coordinate(xMin, yMax), new Coordinate(xMax, yMax), new Coordinate(xMax, yMin), new Coordinate(xMin, yMin) };

        Feature trafficArea = createArea(name, coordinates, SchemaName.TRAFFIC_AREAS, trafficAreaType, vehicles, facilities);
        getLayer(TerminalLayer.TRAFFIC_AREAS).getFeatureCollectionWrapper().add(trafficArea);

        return trafficArea;
    }


    /** Erzeugt eine rechteckige Gleisfl&auml;che, lange Kante von xMin/ yMin nach xMax/ yMin */
    protected static Feature createTrackArea(String name, double xMin, double xMax, double yMin, double yMax, VehicleType[] vehicles, Set<Facility> facilities, int trackIndex) {
        Coordinate[] coordinates = { new Coordinate(xMin, yMin), new Coordinate(xMax, yMin), new Coordinate(xMax, yMax), new Coordinate(xMin, yMax), new Coordinate(xMin, yMin) };

        Feature trackArea = createArea(name, coordinates, SchemaName.TRACKS, LayerTracks.TYPE_TRACK, vehicles, facilities);
        trackArea.setAttribute(LayerTracks.ATTRIBUTE_INDEX, trackIndex);
        getLayer(TerminalLayer.TRACKS).getFeatureCollectionWrapper().add(trackArea);

        return trackArea;
    }


    /** Erzeugt eine Gleisfl&auml;che <em>ohne Geometrie</em> */
    protected static Feature createSimpleTrackArea(String name, String type, VehicleType vehicleType, int trackIndex, Set<Facility> facilities) {
        Feature trackArea = createArea(name, new Coordinate[] {}, SchemaName.TRACKS, type, new VehicleType[] { vehicleType }, facilities);
        trackArea.setAttribute(LayerTracks.ATTRIBUTE_INDEX, trackIndex);
        getLayer(TerminalLayer.TRACKS).getFeatureCollectionWrapper().add(trackArea);

        return trackArea;
    }


    /** Erzeugt eine rechteckige Sperrfl&auml;che */
    protected static Feature createBlockedArea(String name, String type, double xMin, double yMin, double xMax, double yMax, Set<Facility> facilities) {
        Coordinate[] coordinates = { new Coordinate(xMin, yMin), new Coordinate(xMax, yMin), new Coordinate(xMax, yMax), new Coordinate(xMin, yMax), new Coordinate(xMin, yMin) };

        Feature blockedArea = createArea(name, coordinates, SchemaName.BLOCKED_AREAS, type, new VehicleType[] {}, facilities);
        getLayer(TerminalLayer.BLOCKED_AREAS).getFeatureCollectionWrapper().add(blockedArea);

        return blockedArea;
    }


    /** Erzeugt eine rechteckige Leerlager-Heap-Fl&auml;che */
    protected static Feature createMTHeapArea(String name, double xMin, double yMin, double xMax, double yMax, Set<Facility> facilities) {
        Coordinate[] coordinates = { new Coordinate(xMin, yMin), new Coordinate(xMax, yMin), new Coordinate(xMax, yMax), new Coordinate(xMin, yMax), new Coordinate(xMin, yMin) };

        Feature mtHeap = createStructuredArea(name, coordinates, SchemaName.BLOCKS, LayerBlocks.TYPE_EMPTY_HEAP, new VehicleType[] { VehicleType.REACHSTACKER }, 0, 0, 0, 0, facilities);
        getLayer(TerminalLayer.BLOCKS).getFeatureCollectionWrapper().add(mtHeap);

        return mtHeap;
    }


    /** Erzeugt ein rechteckiges Geb&auml;ude */
    protected static Feature createBuildingArea(String name, double xMin, double xMax, double yMin, double yMax, Set<Facility> facilities) {
        return createBlockedArea(name, LayerBlockedAreas.TYPE_BUILDING, xMin, xMax, yMin, yMax, facilities);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Flaechenbestandteile erzeugen:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /** Erzeugt einen Groundslot */
    protected static Feature createGroundslot(String row, String bay, LayoutIterator iterator, double groundslotLenght) {
        Coordinate[] gsCoordinates = { new Coordinate(iterator.getRowCoordinate(), iterator.getBayCoordinate()),
                new Coordinate(iterator.getRowCoordinate() + GroundslotData.WIDTH, iterator.getBayCoordinate()),
                new Coordinate(iterator.getRowCoordinate() + GroundslotData.WIDTH, iterator.getBayCoordinate() - groundslotLenght),
                new Coordinate(iterator.getRowCoordinate(), iterator.getBayCoordinate() - groundslotLenght), new Coordinate(iterator.getRowCoordinate(), iterator.getBayCoordinate()) };

        return createGroundslot(row, bay, gsCoordinates);
    }


    /** Erzeugt einen Groundslot. Die angegebenen Koordinaten m&uuml;ssen ein geschlossener Polygonzug sein! */
    protected static Feature createGroundslot(String row, String bay, Coordinate[] coordinates) {
        Polygon gsPolygon = geometryFactory.createPolygon(geometryFactory.createLinearRing(coordinates), null);

        Feature gs = new BasicFeature(schemaFactory.getFeatureSchema(SchemaName.GROUNDSLOTS));
        gs.setGeometry(gsPolygon);
        gs.setAttribute(LayerEmbeddedArea.ATTRIBUTE_BAY, bay);
        gs.setAttribute(LayerEmbeddedArea.ATTRIBUTE_ROW, row);
        gs.setAttribute(LayerGroundslot.ATTRIBUTE_DOOR_DIRECTION, DoorDirection.EITHER.getDialogValue());
        gs.setAttribute(LayerGroundslot.ATTRIBUTE_MAX_PHYSICAL_HEIGHT, 7.71);
        gs.setAttribute(LayerGroundslot.ATTRIBUTE_MAX_LOGICAL_HEIGHT, 3);
        gs.setAttribute(LayerGroundslot.ATTRIBUTE_TYPE, "[]");

        return gs;
    }


    /** Erzeugt &Uuml;bergabespuren */
    protected static Feature createTransferLane(String row, LayoutIterator iterator) {
        Coordinate[] gsCoordinates = { new Coordinate(iterator.getRowCoordinate(), iterator.getBayCoordinate()),
                new Coordinate(iterator.getRowCoordinate() + de.hhla.tools.geo.util.layer.TransferAreaData.WIDTH, iterator.getBayCoordinate()),
                new Coordinate(iterator.getRowCoordinate() + de.hhla.tools.geo.util.layer.TransferAreaData.WIDTH, iterator.getBayCoordinate() - de.hhla.tools.geo.util.layer.TransferAreaData.LENGTH),
                new Coordinate(iterator.getRowCoordinate(), iterator.getBayCoordinate() - de.hhla.tools.geo.util.layer.TransferAreaData.LENGTH),
                new Coordinate(iterator.getRowCoordinate(), iterator.getBayCoordinate()) };
        Polygon gsPolygon = geometryFactory.createPolygon(geometryFactory.createLinearRing(gsCoordinates), null);

        Feature lane = new BasicFeature(schemaFactory.getFeatureSchema(SchemaName.TRANSFER_LANES));
        lane.setGeometry(gsPolygon);
        lane.setAttribute(LayerEmbeddedArea.ATTRIBUTE_ROW, row);
        lane.setAttribute(LayerEmbeddedArea.ATTRIBUTE_BAY, 1);

        return lane;
    }


    /** Erzeugt ein Hindernis mit der angegebenen Geometrie */
    protected static Feature createObstacle(String name, Geometry geometry) {
        Feature obstacle = createObstacleFeature(name, geometry);
        obstacle.setAttribute(LayerObstacles.ATTRIBUTE_TYPE, LayerObstacles.TYPE_OBSTACLE);

        layerManager.getLayer(TerminalLayer.OBSTACLES.getName()).getFeatureCollectionWrapper().add(obstacle);

        return obstacle;
    }


    /** Erzeugt ein rechteckiges, achsenparalleles Hindernis */
    protected static Feature createObstacle(String name, double xMin, double yMin, double xMax, double yMax) {
        Feature obstacle = createObstacleFeature(name, xMin, yMin, xMax, yMax);
        obstacle.setAttribute(LayerObstacles.ATTRIBUTE_TYPE, LayerObstacles.TYPE_OBSTACLE);

        layerManager.getLayer(TerminalLayer.OBSTACLES.getName()).getFeatureCollectionWrapper().add(obstacle);

        return obstacle;
    }


    /** Erzeugt ein Hindernis f&uuml;r einen Block mit beliebiger Orientierung. */
    protected static Feature createObstacle(String name, Feature block, int startRow, int endRow, int startBay, int endBay) {
        Feature obstacle = createObstacleFeature(name, block, startRow, endRow, startBay, endBay);
        obstacle.setAttribute(LayerObstacles.ATTRIBUTE_TYPE, LayerObstacles.TYPE_OBSTACLE);

        if (logger.isInfoEnabled()) {
            logger.info("Hindernis " + obstacle.getAttribute(LayerArea.ATTRIBUTE_NAME) + " vom Typ " + obstacle.getAttribute(LayerObstacles.ATTRIBUTE_TYPE) + " angelegt.  Geometrie="
                    + obstacle.getGeometry().toText());
        }

        layerManager.getLayer(TerminalLayer.OBSTACLES.getName()).getFeatureCollectionWrapper().add(obstacle);

        return obstacle;
    }


    /** Erzeugt ein achsenparalleles Hindernis der angegebenen L&auml;nge und Breite */
    protected static Geometry createObstacle(String name, LayoutIterator iterator, double lenght, double width) {
        Coordinate[] gsCoordinates = { new Coordinate(iterator.getRowCoordinate(), iterator.getBayCoordinate()), new Coordinate(iterator.getRowCoordinate() + width, iterator.getBayCoordinate()),
                new Coordinate(iterator.getRowCoordinate() + width, iterator.getBayCoordinate() - lenght), new Coordinate(iterator.getRowCoordinate(), iterator.getBayCoordinate() - lenght),
                new Coordinate(iterator.getRowCoordinate(), iterator.getBayCoordinate()) };
        return geometryFactory.createPolygon(geometryFactory.createLinearRing(gsCoordinates), null);
    }


    /** Erzeugt ein Hindernis mit den angegebenen Koordinaten */
    protected static Geometry createObstacle(String name, LayoutIterator iterator, double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        Coordinate[] gsCoordinates = { new Coordinate(iterator.getRowCoordinate() + x1, iterator.getBayCoordinate() + y1),
                new Coordinate(iterator.getRowCoordinate() + x2, iterator.getBayCoordinate() + y2), new Coordinate(iterator.getRowCoordinate() + x3, iterator.getBayCoordinate() + y3),
                new Coordinate(iterator.getRowCoordinate() + x4, iterator.getBayCoordinate() + y4), new Coordinate(iterator.getRowCoordinate() + x1, iterator.getBayCoordinate() + y1) };
        return geometryFactory.createPolygon(geometryFactory.createLinearRing(gsCoordinates), null);
    }


    /** Erzeugt einen rechteckigen, achsenparallelen Lichtmast */
    protected static Feature createLamppost(String name, double xMin, double yMin, double xMax, double yMax) {
        Feature lamppost = createObstacleFeature(name, xMin, yMin, xMax, yMax);
        lamppost.setAttribute(LayerObstacles.ATTRIBUTE_TYPE, LayerObstacles.TYPE_LAMPPOST);

        layerManager.getLayer(TerminalLayer.OBSTACLES.getName()).getFeatureCollectionWrapper().add(lamppost);

        return lamppost;
    }


    /** Erzeugt einen Lichtmast f&uuml;r einen Block mit beliebiger Orientierung. */
    protected static Feature createLamppost(String name, Feature block, int startRow, int endRow, int startBay, int endBay) {
        Feature lamppost = createObstacleFeature(name, block, startRow, endRow, startBay, endBay);
        lamppost.setAttribute(LayerObstacles.ATTRIBUTE_TYPE, LayerObstacles.TYPE_OBSTACLE);

        if (logger.isInfoEnabled()) {
            logger.info("Hindernis " + lamppost.getAttribute(LayerArea.ATTRIBUTE_NAME) + " vom Typ " + lamppost.getAttribute(LayerObstacles.ATTRIBUTE_TYPE) + " angelegt.  Geometrie="
                    + lamppost.getGeometry().toText());
        }

        layerManager.getLayer(TerminalLayer.OBSTACLES.getName()).getFeatureCollectionWrapper().add(lamppost);

        return lamppost;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Vermessungselemente erzeugen:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /** Erzeugt einen Vermessungspunkt mit Koordinaten (0, 0) */
    protected static Feature createSurveyPoint(String name, String type) {
        return FeatureCreator.createSurveyPoint(name, type, new Coordinate(0.0, 0.0));
    }


    /** Erzeugt einen Vermessungspunkt vom Typ 'Standard' mit den angegebenen Koordinaten */
    protected static void createSurveyPoint(String name, Coordinate coordinate) {
        layerManager.getLayer(TerminalLayer.SURVEY_POINTS.getName()).getFeatureCollectionWrapper().add(FeatureCreator.createSurveyPoint(name, LayerSurveyPoints.TYPE_STD, coordinate));
    }


    /** Erzeugt eine Kaimetermarke von (xMin, yMin) nach (xMin + length, yMin) */
    protected static void createQuaymeter(double xMin, double yMin, int length) {
        Feature quayMeter = FeatureCreator.createQuaymeter(new Coordinate(xMin, yMin), new Coordinate(xMin + length, yMin), 0);

        layerManager.getLayer(TerminalLayer.QUAY_METERMARKS.getName()).getFeatureCollectionWrapper().add(quayMeter);
    }


    /** Erzeugt einen N4-Referenzpunkt und f&uuml;gt ihn dem Layer der N4-Referenzpunkte hinzu */
    protected static void createN4RefPoint(String name, Coordinate coordinate, String vpName, Double longitude, Double latitude) {
        Feature refPoint;

        refPoint = new BasicFeature(schemaFactory.getFeatureSchema(SchemaName.N4_REF_POINTS));

        refPoint.setGeometry(geometryFactory.createPoint(coordinate));
        refPoint.setAttribute(LayerRefPointsN4.ATTRIBUTE_NAME, name);
        refPoint.setAttribute(LayerRefPointsN4.ATTRIBUTE_VP_REF, vpName);
        refPoint.setAttribute(LayerRefPointsN4.ATTRIBUTE_LONGITUDE, longitude);
        refPoint.setAttribute(LayerRefPointsN4.ATTRIBUTE_LATITUDE, latitude);

        layerManager.getLayer(TerminalLayer.REF_POINTS_N4.getName()).getFeatureCollectionWrapper().add(refPoint);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Wegenetzelemente erzeugen:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /** Erzeugt f&uuml;r alle Fl&auml;chen in der &uuml;bergebenen Liste den zugeh&ouml;rigen Knoten f&uuml;r das Wegenetz. */
    protected static Map<Integer, Feature> createNodes(List<Feature> areas) {
        FeatureSchema nodeSchema;
        Map<Integer, Feature> nodes = new HashMap<>();
        // FeatureSchemata fuer das Wegenetz holen
        nodeSchema = schemaFactory.getFeatureSchema(SchemaName.NODES);

        for (Feature currentArea : areas) {
            // Knoten erzeugen
            Feature node = new BasicFeature(nodeSchema);
            node.setGeometry(currentArea.getGeometry().getCentroid());
            node.setAttribute(LayerNodes.ATTRIBUTE_AREA_ID, currentArea.getID());
            // in Knotenliste einfuegen
            nodes.put(currentArea.getID(), node);
        }

        layerManager.getLayer(TerminalLayer.NODES.getName()).getFeatureCollectionWrapper().addAll(nodes.values());
        return nodes;
    }


    /** Erzeugt Kanten zwischen den Knoten von benachbarten Fl&auml;chen, wenn eine Ein-/Durchfahrt m&ouml;glich ist. */
    protected static List<Feature> createEdges(List<Feature> areas, Map<Integer, Feature> mapOfNodes, List<Feature> listOfAreas) {
        List<Feature> edgeList = new ArrayList<>();

        for (int i = 0; i < areas.size() - 1; i++) {
            for (int j = i + 1; j < areas.size(); j++) {
                Feature currentArea = areas.get(i);
                Feature nextArea = areas.get(j);

                Geometry intersection = currentArea.getGeometry().intersection(nextArea.getGeometry());

                // Wenn die Schnittmenge leer ist, ist die Anzahl der Punkte 0
                if (intersection.getNumPoints() > 1) {
                    // die aktuelle Flaeche ist eine strukturierte Flaeche
                    if (currentArea.getSchema().hasAttribute(LayerStructuredArea.ATTRIBUTE_BAY_BORDER_DELTA)) {
                        // Nachbarflaeche ist auch strukturiert
                        if (nextArea.getSchema().hasAttribute(LayerStructuredArea.ATTRIBUTE_BAY_BORDER_DELTA)) {
                            if (isValidEdge(currentArea, nextArea, intersection, listOfAreas) && isValidEdge(nextArea, currentArea, intersection, listOfAreas)) {
                                edgeList.add(createEdge(currentArea, nextArea, mapOfNodes));
                                edgeList.add(createEdge(nextArea, currentArea, mapOfNodes));
                            }
                        }
                        // Nachbarflaeche ist nicht strukturiert
                        else {
                            if (isValidEdge(currentArea, nextArea, intersection, listOfAreas)) {
                                edgeList.add(createEdge(currentArea, nextArea, mapOfNodes));
                                edgeList.add(createEdge(nextArea, currentArea, mapOfNodes));
                            }
                        }
                    }
                    // die aktuelle Flaeche ist nicht strukturiert
                    else {
                        // Nachbarflaeche ist strukturiert
                        if (nextArea.getSchema().hasAttribute(LayerStructuredArea.ATTRIBUTE_BAY_BORDER_DELTA)) {
                            if (isValidEdge(nextArea, currentArea, intersection, listOfAreas)) {
                                edgeList.add(createEdge(currentArea, nextArea, mapOfNodes));
                                edgeList.add(createEdge(nextArea, currentArea, mapOfNodes));
                            }
                        }
                        // Nachbarflaeche ist nicht strukturiert
                        else {
                            edgeList.add(createEdge(currentArea, nextArea, mapOfNodes));
                            edgeList.add(createEdge(nextArea, currentArea, mapOfNodes));
                        }
                    }
                }
            }
        }

        layerManager.getLayer(TerminalLayer.EDGES.getName()).getFeatureCollectionWrapper().addAll(edgeList);
        return edgeList;
    }


    protected static void createEdges(List<Feature> listOfAreas, List<Feature> listOfNodes) {
        Map<Integer, Feature> mapOfNodes = new HashMap<>();
        for (Feature listOfNode : listOfNodes) {
            mapOfNodes.put(Integer.parseInt(listOfNode.getString(LayerNodes.ATTRIBUTE_AREA_ID)), listOfNode);
        }

        createEdges(listOfAreas, mapOfNodes, new ArrayList<>());
    }


    /**
     * Erzeugt eine Kante zwischen den beiden zu der Fläche geh&ouml;renden Knoten.
     *
     * @param currentArea Erste Fl&auml;che (hier liegt der Startknoten)
     * @param nextArea    Zweite Fl&auml;che (hier liegt der Endknoten)
     */
    protected static Feature createEdge(Feature currentArea, Feature nextArea, Map<Integer, Feature> mapOfNodes) {
        // zu den Flaechen gehoerende Knoten holen
        Feature currentNode = mapOfNodes.get(currentArea.getID());
        Feature nextNode = mapOfNodes.get(nextArea.getID());

        // Geometrie fuer die Kante erzeugen
        Geometry edgeGeometry = geometryFactory.createLineString(new Coordinate[] { currentNode.getGeometry().getCoordinate(), nextNode.getGeometry().getCoordinate() });

        String currentAreaVehicles = currentArea.getString(LayerArea.ATTRIBUTE_VEHICLES);
        String nextAreaVehicles = nextArea.getString(LayerArea.ATTRIBUTE_VEHICLES);

        // zulaessige Fahrzeugtypen fuer die Kante ermitteln
        VehicleType[] types = VehicleType.values();
        List<VehicleType> vehicles = new ArrayList<>();

        for (VehicleType type : types) {
            if (currentAreaVehicles.contains(type.toString()) && nextAreaVehicles.contains(type.toString())) {
                vehicles.add(type);
            }
        }

        // Kante erzeugen
        Feature edge = new BasicFeature(schemaFactory.getFeatureSchema(SchemaName.EDGES));
        edge.setGeometry(edgeGeometry);
        edge.setAttribute(LayerEdges.ATTRIBUTE_START_ID, currentNode.getID());
        edge.setAttribute(LayerEdges.ATTRIBUTE_END_ID, nextNode.getID());
        edge.setAttribute(LayerEdges.ATTRIBUTE_VEHICLE_TYPES, VehicleType.formatVehicleTypes(vehicles));
        // in Kantenliste einfuegen
        return edge;
    }


    protected static Feature createNodeN4(String nodeId, Coordinate coordinate, String owningGraph) {
        FeatureSchema nodeSchema = schemaFactory.getFeatureSchema(SchemaName.NODES_N4);

        Feature node = new BasicFeature(nodeSchema);
        node.setGeometry(geometryFactory.createPoint(coordinate));
        node.setAttribute(LayerNodesN4.ATTRIBUTE_ID_N4, nodeId);
        node.setAttribute(LayerNodesN4.ATTRIBUTE_OWNING_GRAPH, owningGraph);

        layerManager.getLayer(TerminalLayer.NODES_N4.getName()).getFeatureCollectionWrapper().add(node);
        
        return node;
    }


    protected static void createEdgeN4(String startId, String endId, Facility facility, TTrafficDirection trafficDirection) {
        FeatureSchema edgeSchema = schemaFactory.getFeatureSchema(SchemaName.EDGES_N4);

        Feature edge = new BasicFeature(edgeSchema);
        edge.setGeometry(
                geometryFactory.createLineString(new Coordinate[] { n4NodeFinder.getN4Node(startId).getGeometry().getCoordinate(), n4NodeFinder.getN4Node(endId).getGeometry().getCoordinate() }));

        edge.setAttribute(LayerEdgesN4.ATTRIBUTE_INGRESS_VERTEX, startId);
        edge.setAttribute(LayerEdgesN4.ATTRIBUTE_EGRESS_VERTEX, endId);

        edge.setAttribute(LayerEdgesN4.ATTRIBUTE_FACILITY, facility.getName());
        edge.setAttribute(LayerEdgesN4.ATTRIBUTE_TRAFFIC_FLOW, trafficDirection.toString());

        edge.setAttribute(LayerEdgesN4.ATTRIBUTE_RECEIVALS_PROHIBITED, ExportN4Properties.NO);
        edge.setAttribute(LayerEdgesN4.ATTRIBUTE_DELIVERIES_PROHIBITED, ExportN4Properties.NO);
        edge.setAttribute(LayerEdgesN4.ATTRIBUTE_YARD_MOVES_PROHIBITED, ExportN4Properties.NO);
        edge.setAttribute(LayerEdgesN4.ATTRIBUTE_YARD_SHIFTS_PROHIBITED, ExportN4Properties.NO);
        edge.setAttribute(LayerEdgesN4.ATTRIBUTE_RAIL_DISCH_PROHIBITED, ExportN4Properties.NO);
        edge.setAttribute(LayerEdgesN4.ATTRIBUTE_RAIL_LOADS_PROHIBITED, ExportN4Properties.NO);
        edge.setAttribute(LayerEdgesN4.ATTRIBUTE_VESSEL_DISCH_PROHIBITED, ExportN4Properties.NO);
        edge.setAttribute(LayerEdgesN4.ATTRIBUTE_VESSEL_LOADS_PROHIBITED, ExportN4Properties.NO);

        layerManager.getLayer(TerminalLayer.EDGES_N4.getName()).getFeatureCollectionWrapper().add(edge);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Sonstige Elemente erzeugen:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Erzeugt aus der &uuml;bergebenen Geometrie zun&auml;chst die <em>Bounding Box</em> und aus dieser ein achsenparalleles Rechteck, indem sie in X-Richtung um den Wert addWidth und
     * in Y-Richtung um den Wert addLength vergr&ouml;&szlig;ert wird. Diese Werte k&ouml;nnen auch negativ sein.
     */
    protected static Geometry createGeometry(Geometry template, double addLength, double addWidth) {
        Geometry envelope = template.getEnvelope();

        // Ermittlung der min- und max-Werte, da JTS diese nicht wie spezifiziert in der Reihenfolge minx miny, maxx miny, maxx maxy, minx maxy, minx miny erzeugt!
        double minX = Double.MAX_VALUE;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.MAX_VALUE;
        double maxY = Double.NEGATIVE_INFINITY;

        for (Coordinate coordinate : envelope.getCoordinates()) {
            if (coordinate.x < minX) {
                minX = coordinate.x;
            }
            if (coordinate.x > maxX) {
                maxX = coordinate.x;
            }
            if (coordinate.y < minY) {
                minY = coordinate.y;
            }
            if (coordinate.y > maxY) {
                maxY = coordinate.y;
            }
        }

        Coordinate[] gsCoordinates = { new Coordinate(minX - addWidth, minY - addLength), new Coordinate(maxX + addWidth, minY - addLength), new Coordinate(maxX + addWidth, maxY + addLength),
                new Coordinate(minX - addWidth, maxY + addLength), new Coordinate(minX - addWidth, minY - addLength) };

        return geometryFactory.createPolygon(geometryFactory.createLinearRing(gsCoordinates), null);
    }


    protected static void setSlotDoorDirecion(Feature block, DoorDirection doorDirection) {
        for (TerminalLayer slaveLayer : TerminalLayer.SLAVE_LAYER_GROUNDSLOT) {

            for (Feature groundslot : getFeatures(slaveLayer)) {
                if (!block.getGeometry().contains(groundslot.getGeometry())) {
                    continue; // Groundslot liegt nicht im fraglichen Block
                }

                groundslot.setAttribute(LayerGroundslot.ATTRIBUTE_DOOR_DIRECTION, doorDirection.getDialogValue());
            }

        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Elemente entfernen:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /** Leert den angegebenen Layer */
    protected static void removeAllElements(TerminalLayer terminalLayer) {
        getLayer(terminalLayer).getFeatureCollectionWrapper().clear();
    }


    /** Entfernt die Substrukturen mit der angegebenen Reihen- und Platzbezeichnung im angegebenen Layer aus der Groundslotliste */
    protected static void removeGroundslot(Feature area, String row, String bay, TerminalLayer substructureLayer) {
        List<Feature> toBeRemoved = new ArrayList<>();

        for (Feature currentFeature : getFeatures(substructureLayer)) {
            if (!area.getGeometry().contains(currentFeature.getGeometry())) {
                continue; // Groundslot liegt nicht im fraglichen Block
            }
            if (currentFeature.getString(LayerEmbeddedArea.ATTRIBUTE_ROW).equals(row) && containsBay(currentFeature.getString(LayerEmbeddedArea.ATTRIBUTE_BAY), bay)) {
                toBeRemoved.add(currentFeature);
            }
        }

        for (Feature feature : toBeRemoved) {
            getLayer(substructureLayer).getFeatureCollectionWrapper().remove(feature);
            logger.debug("Feature in row " + feature.getString(LayerEmbeddedArea.ATTRIBUTE_ROW) + ", bay " + feature.getString(LayerEmbeddedArea.ATTRIBUTE_BAY) + " entfernt");
        }
    }


    /** Entfernt die Substrukturen mit der angegebenen Reihen- und Platzbezeichnung in den angegebenen Layern aus der Groundslotliste */
    protected static void removeGroundslot(Feature area, String row, String bay, TerminalLayer[] substructureLayers) {
        for (TerminalLayer layer : substructureLayers) {
            removeGroundslot(area, row, bay, layer);
        }
    }


    /** Entfernt alle Substrukturen des angegebenen Layers mit der angegebenen Reihe aus der Groundslotliste */
    protected static void removeGroundslot(Feature area, String row, TerminalLayer substructureLayer) {
        List<Feature> toBeRemoved = new ArrayList<>();

        for (Feature currentFeature : getFeatures(substructureLayer)) {
            if (!area.getGeometry().contains(currentFeature.getGeometry())) {
                continue; // Groundslot liegt nicht im fraglichen Block
            }
            if (currentFeature.getString(LayerEmbeddedArea.ATTRIBUTE_ROW).equals(row)) {
                toBeRemoved.add(currentFeature);
            }
        }

        for (Feature feature : toBeRemoved) {
            getLayer(substructureLayer).getFeatureCollectionWrapper().remove(feature);
        }
    }


    /** Entfernt alle Substrukturen der angegebenen Layer mit der angegebenen Reihe aus der Groundslotliste */
    protected static void removeGroundslot(Feature area, String row, TerminalLayer[] substructureLayers) {
        for (TerminalLayer layer : substructureLayers) {
            removeGroundslot(area, row, layer);
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Private Methoden:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /** Erzeugt eine rechteckige Fl&auml;che im angegebenen Layer mit den angegebenen Attributen */
    private static Feature createArea(String name, Coordinate[] coordinates, SchemaName schemaName, String type, VehicleType[] vehicles, Set<Facility> facilities) {
        Feature currentArea;

        Polygon polygon = geometryFactory.createPolygon(geometryFactory.createLinearRing(coordinates), null);

        currentArea = new BasicFeature(schemaFactory.getFeatureSchema(schemaName));

        currentArea.setGeometry(polygon);
        currentArea.setAttribute(LayerArea.ATTRIBUTE_NAME, name);
        currentArea.setAttribute(LayerArea.ATTRIBUTE_TYPE, type);
        currentArea.setAttribute(LayerArea.ATTRIBUTE_VEHICLES, VehicleType.formatVehicleTypes(vehicles));
        currentArea.setAttribute(LayerArea.ATTRIBUTE_CONFIRMATION_TYPE, ConfirmationType.AUTOMATICALLY.getName());
        currentArea.setAttribute(LayerArea.ATTRIBUTE_FACILITY, Facility.formatFacilities(facilities));
        currentArea.setAttribute(LayerArea.ATTRIBUTE_STORAGE_AREA, STORAGE_AREA_TERMINAL.getName());

        return currentArea;
    }


    /**
     * Erzeugt eine rechteckige <em>strukturierte Fl&auml;che</em> im angegebenen Layer mit den angegebenen Attributen. Die Attribute Reihen-/ Platzabstand, Reihen-/ Platzoffset,
     * Reihen-/ Platzrandbreite werden mit Defaultwerten besetzt, die Orientierung wird aus dem Rechteck berechnet.
     */
    private static Feature createStructuredArea(String name, Coordinate[] coordinates, SchemaName schemaName, String type, VehicleType[] vehicles, int rowOffset, int bayOffset, double rowDelta,
            double bayDelta, Set<Facility> facilities) {
        Feature currentArea = createArea(name, coordinates, schemaName, type, vehicles, facilities);

        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_ORIENTATION, GeometricalFunctions.getAreaOrientation(currentArea.getGeometry()));
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_ROW_BORDER_DELTA, DEFAULT_ROW_BORDER_DELTA);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_BAY_BORDER_DELTA, DEFAULT_BAY_BORDER_DELTA);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_ROW_DELTA, rowDelta);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_BAY_DELTA, bayDelta);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_ROW_OFFSET, rowOffset);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_BAY_OFFSET, bayOffset);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_ENTRY_FIRST_COL, YES);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_ENTRY_LAST_COL, YES);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_DOOR_DIRECTION, DEFAULT_DOOR_DIRECTION);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_CODE_CHARS, DEFAULT_CODE_CHARS);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_LABEL_FORMAT_BLOCK, DEFAULT_LABEL_FORMAT_BLOCK);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_LABEL_FORMAT_ROW, DEFAULT_LABEL_FORMAT_ROW);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_LABEL_FORMAT_COL, DEFAULT_LABEL_FORMAT_COL);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_LABEL_FORMAT_TIER, DEFAULT_LABEL_FORMAT_TIER);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_USER_LABEL_FORMAT, DEFAULT_USER_LABEL);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_TABLE_ROW_20, DEFAULT_TABLE_ROW);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_TABLE_ROW_40, DEFAULT_TABLE_ROW);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_TABLE_COL, DEFAULT_TABLE_COL);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_TABLE_TIER, DEFAULT_TABLE_TIER);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_COMPRESS_SCAN, DEFAULT_COMPRESS_SCAN);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_EXTENDS_ASCENDING, DEFAULT_EXTENDS_ASCENDING);
        currentArea.setAttribute(LayerStructuredArea.ATTRIBUTE_XPS_OUTLINE, DEFAULT_XPS_OUTLINE);

        return currentArea;
    }


    /** Erzeugt ein Element des Layers <em>Hindernisse</em> f&uuml;r einen Block mit beliebiger Orientierung. Der Typ wird <em>nicht</em> gesetzt. */
    private static Feature createObstacleFeature(String name, Feature block, int startRow, int endRow, int startBay, int endBay) {
        // virtuelle Flaechenrotation --> Platzrichtung parallel zur Y-Achse
        FeatureRotation rotation = new FeatureRotation(block.getGeometry());
        rotation.setAngle(-1 * GeometricalFunctions.getAreaOrientation(block.getGeometry()));
        Feature rotatedBlock = (Feature) block.clone();
        // Rotation um den Winkel => die zuerst aufgezogene Seite der Fläche liegt
        // parallel zur y-Achse
        rotatedBlock.getGeometry().apply(rotation);
        rotatedBlock.getGeometry().geometryChanged();

        LayoutIterator iterator = new LayoutIterator(rotatedBlock);

        int blockAttributeRowOffset = Integer.parseInt(block.getString(LayerStructuredArea.ATTRIBUTE_ROW_OFFSET));
        int blockAttributeBayOffset = Integer.parseInt(block.getString(LayerStructuredArea.ATTRIBUTE_BAY_OFFSET));
        double blockAttributeRowDelta = Double.parseDouble(block.getString(LayerStructuredArea.ATTRIBUTE_ROW_DELTA));
        double blockAttributeBayDelta = Double.parseDouble(block.getString(LayerStructuredArea.ATTRIBUTE_BAY_DELTA));

        Assert.assertTrue(startRow >= blockAttributeRowOffset);
        Assert.assertTrue(startBay >= blockAttributeBayOffset);

        double rowOffset = (startRow - 1 - blockAttributeRowOffset) * (GroundslotData.WIDTH + blockAttributeRowDelta);
        double bayOffset = (startBay - 1 - blockAttributeBayOffset) * (GroundslotData.LENGTH_20FT + blockAttributeBayDelta);
        iterator.addToRowCoordinate(rowOffset);
        iterator.addToBayCoordinate(bayOffset);
        double width = (endRow - startRow + 1) * GroundslotData.WIDTH + (endRow - startRow) * blockAttributeRowDelta;
        double lenght = (endBay - startBay + 1) * GroundslotData.LENGTH_20FT + (endBay - startBay) * blockAttributeBayDelta;

        Coordinate[] gsCoordinates = { new Coordinate(iterator.getRowCoordinate(), iterator.getBayCoordinate()), new Coordinate(iterator.getRowCoordinate() + width, iterator.getBayCoordinate()),
                new Coordinate(iterator.getRowCoordinate() + width, iterator.getBayCoordinate() + lenght), new Coordinate(iterator.getRowCoordinate(), iterator.getBayCoordinate() + lenght),
                new Coordinate(iterator.getRowCoordinate(), iterator.getBayCoordinate()) };
        Polygon gsPolygon = geometryFactory.createPolygon(geometryFactory.createLinearRing(gsCoordinates), null);

        Feature obstacle = new BasicFeature(schemaFactory.getFeatureSchema(SchemaName.OBSTACLES));

        rotation.setAngle(GeometricalFunctions.getAreaOrientation(block.getGeometry()));
        gsPolygon.apply(rotation);
        obstacle.setGeometry(gsPolygon);
        obstacle.setAttribute(LayerObstacles.ATTRIBUTE_NAME, name);
        return obstacle;
    }


    /** Erzeugt ein rechteckiges, achsenparalleles Hindernis. Der Typ wird <em>noch nicht</em> gesetzt. */
    private static Feature createObstacleFeature(String name, double xMin, double yMin, double xMax, double yMax) {
        Coordinate[] coordinates = { new Coordinate(xMin, yMin), new Coordinate(xMin, yMax), new Coordinate(xMax, yMax), new Coordinate(xMax, yMin), new Coordinate(xMin, yMin) };
        Polygon gsPolygon = geometryFactory.createPolygon(geometryFactory.createLinearRing(coordinates), null);

        return createObstacleFeature(name, gsPolygon);
    }


    private static Feature createObstacleFeature(String name, Geometry geometry) {
        Feature feature = new BasicFeature(schemaFactory.getFeatureSchema(SchemaName.OBSTACLES));
        feature.setGeometry(geometry);
        feature.setAttribute(LayerObstacles.ATTRIBUTE_NAME, name);

        return feature;
    }


    /**
     * Pr&uuml;ft, ob zwischen einer Fl&auml;che und einer strukturierten Fl&auml;che eine Kante gezogen werden darf.
     * 
     * @param  structuredArea Die strukturierte Fl&auml;che
     * @param  area           Die zweite Fl&auml;che
     * @param  intersection   Die Geometrie des Schnitts
     * 
     * @return                <tt>true</tt>, wenn eine Kante gezogen werden darf, andernfalls <tt>false</tt>
     */
    private static boolean isValidEdge(Feature structuredArea, Feature area, Geometry intersection, List<Feature> listOfParkingAreas) {
        // Es wird erwartet, dass die Intersection-Linie in einem 90-Grad-Winkel zur Reihenorientierung einer Flaeche liegt
        int expectedDiff = 90;

        // Bei Parkflaechen muss die Intersection-Linie parallel zu den Reihen liegen
        if (listOfParkingAreas.contains(structuredArea)) {
            expectedDiff = 0;
        }

        // Aufteilen in einzelne Linien
        for (int k = 0; k < intersection.getCoordinates().length - 1; k++) {
            /* Nur wenn das Linien-Segment kein Punkt ist, Winkel pruefen => bei einer Intersection zweier Polygone kann das Ergebnis ein MULTILINESTRING sein, der Punkte enthaelt. Dies ist
             * genau dann der Fall, wenn eines der Polygone durch einen Punkt auf der Intersection definiert ist. Wenn in der Intersection ein Punkt vorkommt, hat dieser Punkt immer den Winkel
             * 0 Grad, es kann daher zu fachlich nicht vorhandenen Kanten kommen. */

            if (!intersection.getCoordinates()[k].equals(intersection.getCoordinates()[k + 1])) {
                Geometry lineSegmentGeometry = geometryFactory.createLineString(new Coordinate[] { intersection.getCoordinates()[k], intersection.getCoordinates()[k + 1] });

                int intersectionOrientation = (int) Math.round(GeometricalFunctions.getDefaultAngle(lineSegmentGeometry));
                int rowOrientation = (int) Math.round(Double.parseDouble(structuredArea.getString(LayerStructuredArea.ATTRIBUTE_ORIENTATION)));

                int difference = Math.abs(((intersectionOrientation + expectedDiff) % 360) - rowOrientation) % 180;

                if (difference <= variance || difference >= (180 - variance)) {
                    return true;
                }
            }
        }

        return false;
    }
}
