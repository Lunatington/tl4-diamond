java.util.ConcurrentModificationException 

at java.util.TreeMap$PrivateEntryIterator.nextEntry(TreeMap.java:1211) 

at java.util.TreeMap$KeyIterator.next(TreeMap.java:1265) 

at de.hhla.tools.geoeditor.terminal.usecase.geometry.UseCaseGeometryModifiedN4Node.execute(UseCaseGeometryModifiedN4Node.java:81) 

at com.vividsolutions.jump.workbench.ui.AbstractSelection.getSelectedItems(AbstractSelection.java:192) 

at com.vividsolutions.jump.workbench.ui.AbstractSelection.getSelectedItems(AbstractSelection.java:178) 

at com.vividsolutions.jump.workbench.ui.SelectionManager.getSelectedItems(SelectionManager.java:222) 

at com.vividsolutions.jump.workbench.plugin.EnableCheckFactory$18.check(EnableCheckFactory.java:355) 

at com.vividsolutions.jump.workbench.plugin.MultiEnableCheck.check(MultiEnableCheck.java:56) 

at com.vividsolutions.jump.workbench.ui.EnableableToolBar.updateEnabledState(EnableableToolBar.java:75) 

at com.vividsolutions.jump.workbench.ui.WorkbenchFrame$13.undoHistoryChanged(WorkbenchFrame.java:759) 

at com.vividsolutions.jump.workbench.model.UndoableEditReceiver.fireUndoHistoryChanged(UndoableEditReceiver.java:131) 

at com.vividsolutions.jump.workbench.model.UndoableEditReceiver.stopReceiving(UndoableEditReceiver.java:115) 

at com.vividsolutions.jump.workbench.ui.cursortool.AbstractCursorTool.fireGestureFinished(AbstractCursorTool.java:485) 

at com.vividsolutions.jump.workbench.ui.cursortool.DragTool.mouseReleased(DragTool.java:171) 

at com.vividsolutions.jump.workbench.ui.cursortool.LeftClickFilter.mouseReleased(LeftClickFilter.java:96) 

at com.vividsolutions.jump.workbench.ui.cursortool.DelegatingTool.mouseReleased(DelegatingTool.java:123) 

at java.awt.AWTEventMulticaster.mouseReleased(AWTEventMulticaster.java:290) 

at java.awt.Component.processMouseEvent(Component.java:6539) 

at javax.swing.JComponent.processMouseEvent(JComponent.java:3324) 

at java.awt.Component.processEvent(Component.java:6304) 

at java.awt.Container.processEvent(Container.java:2239) 

at java.awt.Component.dispatchEventImpl(Component.java:4889) 

at java.awt.Container.dispatchEventImpl(Container.java:2297) 

at java.awt.Component.dispatchEvent(Component.java:4711) 

at java.awt.LightweightDispatcher.retargetMouseEvent(Container.java:4904) 

at java.awt.LightweightDispatcher.processMouseEvent(Container.java:4535) 

at java.awt.LightweightDispatcher.dispatchEvent(Container.java:4476) 

at java.awt.Container.dispatchEventImpl(Container.java:2283) 

at java.awt.Window.dispatchEventImpl(Window.java:2746) 

at java.awt.Component.dispatchEvent(Component.java:4711) 

at java.awt.EventQueue.dispatchEventImpl(EventQueue.java:760) 

at java.awt.EventQueue.access$500(EventQueue.java:97) 

at java.awt.EventQueue$3.run(EventQueue.java:709) 

at java.awt.EventQueue$3.run(EventQueue.java:703) 

at java.security.AccessController.doPrivileged(Native Method) 

at java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:74) 

at java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:84) 

at java.awt.EventQueue$4.run(EventQueue.java:733) 

at java.awt.EventQueue$4.run(EventQueue.java:731) 

at java.security.AccessController.doPrivileged(Native Method) 

at java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:74) 

at java.awt.EventQueue.dispatchEvent(EventQueue.java:730) 

at java.awt.EventDispatchThread.pumpOneEventForFilters(EventDispatchThread.java:205) 

at java.awt.EventDispatchThread.pumpEventsForFilter(EventDispatchThread.java:116) 

at java.awt.EventDispatchThread.pumpEventsForHierarchy(EventDispatchThread.java:105) 

at java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:101) 

at java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:93) 

at java.awt.EventDispatchThread.run(EventDispatchThread.java:82) 