package de.szut.dqi12.sqlitebrowser.gui.listeners;

import de.szut.dqi12.sqlitebrowser.gui.popups.TreeTableSelectionPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Till Schlechtweg
 */
public class NodeRightClickListener extends MouseAdapter {
        
    @Override
    public void mousePressed(MouseEvent e){
        if(e.isPopupTrigger()) doPop(e);
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
        if(e.isPopupTrigger()) doPop(e);
    }
    
    private void doPop(MouseEvent e){
        TreeTableSelectionPopupMenu menu = new TreeTableSelectionPopupMenu();
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
