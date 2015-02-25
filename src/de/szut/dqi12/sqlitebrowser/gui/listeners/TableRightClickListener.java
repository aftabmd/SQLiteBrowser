/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.sqlitebrowser.gui.listeners;

import de.szut.dqi12.sqlitebrowser.gui.popups.TableDatasetPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author tsc
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
