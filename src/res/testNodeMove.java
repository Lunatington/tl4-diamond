public class TestNodeMove extends AbstractTerminalGeometrieeditorTestCase {
    private static final String Graph = "MainGraph";
    private static final int maxEdges = 80;
    private static final double maxCoordinate = 800000000;
    protected static GeometryFactory geometryFactory = new GeometryFactory();
    
    @Before
    public void setUp() throws Exception {super.setUp();}
    
    @Test
    public void MoveNodeWithManyEdges() {
        
        Feature node = createNodeN4("Main",new Coordinate(0, 0),Graph);
        
        //Erstellen aller N4Nodes
        for(int i=0;i<maxEdges;i++) {
            createNodeN4("e"+i ,new Coordinate(-maxCoordinate, i),Graph);
            createEdgeN4("e"+i, "Main",FACILITY_CTB, TTrafficDirection.BOTH);
        }
         
        

        //Test ob exception bei bewegung von kanten
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
    }

}
