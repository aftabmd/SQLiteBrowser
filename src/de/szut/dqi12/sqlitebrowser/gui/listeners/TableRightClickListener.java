package de.szut.dqi12.sqlitebrowser.gui.listeners;

import de.szut.dqi12.sqlitebrowser.gui.popups.TableDatasetPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Till Schlechtweg
 */
public class TableRightClickListener extends MouseAdapter {
    @Override
    public void mousePressed(MouseEvent e){
        if(e.isPopupTrigger()) doPop(e);
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
        if(e.isPopupTrigger()) doPop(e);
    }

    public void doPop(MouseEvent e){
        TableDatasetPopupMenu menu = new TableDatasetPopupMenu();
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
