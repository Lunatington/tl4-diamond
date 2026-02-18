
/**
 * Basisklasse f&uuml;r alle UseCases.
 */
public abstract class AbstractUseCase implements UseCase {
    /** Verweis auf die JUMP-Workbench zur Anzeige von Dialogen oder Fehlermeldungen */
    private WorkbenchFrame workbenchFrame;

    private UseCaseFactory useCaseFactory = null;

    private MasterstructureFinder masterstructureFinder = null;
    private SubstructureFinder substructureFinder = null;


    private Logger logger;

    private StringBuffer logInfo = null;


    /** Konstruktor, initialisiert den Logger */
    protected AbstractUseCase(String loggerName) {
        Assert.isTrue(loggerName != null, "loggerName != null");
        logger = LoggerFactory.getLogger(loggerName);
    }


    /** Konstruktor, setzt den Verweis auf die JUMP-Workbench und initialisiert den Logger. */
    protected AbstractUseCase(WorkbenchFrame workbenchFrame, String loggerName) {
        this(loggerName);

        // Bei JUnit-Test der UseCases ist die JUMP-Workbench nicht vorhanden!
        this.workbenchFrame = workbenchFrame;
    }


    @Override
    public abstract void execute(FeatureEvent event);


    /**
     * &Ouml;ffnet den zum angegebenen Layer zugeh&ouml;rigen Dialog f&uuml;r das gegebene Feature. Der Dialog ist <em>modal</em>, d.h. diese Methode kehrt erst nach Ende des Dialogs
     * (OK oder Abbrechen) zur&uuml;ck.
     * 
     * @return True, wenn der Dialog mit &quot;OK&quot; verlassen wurde, false sonst
     */
    protected boolean openDialog(TerminalLayer layer, Feature feature) {
        DialogFactory factory = (DialogFactory) SingletonFactory.getInstance().getSingleton(SingletonName.DIALOG_FACTORY);
        return factory.openDialog(layer, feature);
    }


    protected void initLogInfo() {
        this.logInfo = new StringBuffer();
    }


    protected void addLogInfo(String message) {
        this.logInfo.append(message).append("\n");
    }


    protected void addLogInfo(String message, Exception e) {
        this.addLogInfo(message);
        this.addLogInfo(e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            this.logInfo.append(element.toString()).append("\n");
        }
    }


    protected void flushLogInfo() {
        if (logger.isInfoEnabled() && this.logInfo.length() > 0) {
            this.logInfo.deleteCharAt(this.logInfo.length() - 1);  // sonst doppeltes \n
            logger.info(this.logInfo.toString());
        }
        logInfo = new StringBuffer();
    }


    /** Zeigt den Fehlerdialog in der Workbench an und gibt eine Warnung im Log aus. */
    protected int showConfirmDialog(String title, String message) {
        Assert.isTrue(this.workbenchFrame != null, "workbenchFrame != null");

        return this.workbenchFrame.showConfirmDialog(title, message);
    }


    /** Zeigt den Fehlerdialog in der Workbench an und gibt eine Warnung im Log aus. */
    protected void showErrorDialog(String message, String details) {
        Assert.isTrue(this.workbenchFrame != null, "workbenchFrame != null");

        workbenchFrame.showErrorDialog("Fehler", message, details);
        this.flushLogInfo();
        logger.warn(message + details);
    }


    /** Zeigt den Fehlerdialog in der Workbench an und gibt eine Warnung im Log aus. */
    protected void showErrorDialog(String message, String details, Exception e) {
        Assert.isTrue(this.workbenchFrame != null, "workbenchFrame != null");
        StringBuffer buffer = new StringBuffer(details).append("\n\n");
        buffer.append(e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            buffer.append(element.toString()).append("\n");
        }

        workbenchFrame.showErrorDialog("Fehler", message, buffer.toString());
        this.flushLogInfo();
        logger.warn(message + details, e);
    }


    protected UseCaseFactory getUseCaseFactory() {
        if (this.useCaseFactory == null) {
            useCaseFactory = (UseCaseFactory) SingletonFactory.getInstance().getSingleton(SingletonName.USECASE_FACTORY);
        }
        return this.useCaseFactory;
    }


    protected MasterstructureFinder getMasterstructureFinder() {
        if (this.masterstructureFinder == null) {
            this.masterstructureFinder = (MasterstructureFinder) SingletonFactory.getInstance().getSingleton(SingletonName.MASTERSTRUCTURE_FINDER);
        }
        return this.masterstructureFinder;
    }


    protected SubstructureFinder getSubstructureFinder() {
        if (this.substructureFinder == null) {
            this.substructureFinder = (SubstructureFinder) SingletonFactory.getInstance().getSingleton(SingletonName.SUBSTRUCTURE_FINDER);
        }
        return this.substructureFinder;
    }


    protected WorkbenchFrame getWorkbenchFrame() {
        return workbenchFrame;
    }


    /** F&uuml;gt die angegebenen Features zum Layer in der angegebenen Kategorie hinzu. */
    protected void addLayerData(String category, String layerName, FeatureCollection features, boolean readOnly) {
        Assert.isTrue(this.workbenchFrame != null, "workbenchFrame != null");

        LayerManager layerManager = workbenchFrame.getContext().getLayerManager();
        if (layerManager.getLayer(layerName) != null) {
            layerManager.getLayer(layerName).setReadonly(false);
        }
        layerManager.addOrReplaceLayer(category, layerName, features);

        if (readOnly) {
            layerManager.getLayer(layerName).setReadonly(true);
        }
    }


    /** Entfernt das angegebene Feature aus dem angegebenen Layer. */
    protected void removeFeatureFromLayer(Layer layer, Feature feature) {
        UseCase useCase = this.getUseCaseFactory().removeUseCase(TerminalLayer.getTerminalLayer(layer), FeatureEventType.DELETED);
        layer.getFeatureCollectionWrapper().remove(feature);
        this.getUseCaseFactory().addUseCase(TerminalLayer.getTerminalLayer(layer), FeatureEventType.DELETED, useCase);
    }


    /** Zeichnet die Workbench neu */
    protected void repaint() {
        this.workbenchFrame.repaint();
        SwingUtilities.updateComponentTreeUI(workbenchFrame);
    }
}