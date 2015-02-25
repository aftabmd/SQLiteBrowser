/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.sqlitebrowser.gui.popups;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author tsc
 */
public class TreeTableSelectionPopupMenu extends JPopupMenu {
    JMenuItem deleteTable;
    JMenuItem createTable;
    
    public TreeTableSelectionPopupMenu(){
        deleteTable = new JMenuItem("delete Table");
        createTable = new JMenuItem("create Table");
        
        add(deleteTable);
        add(createTable);
    }
}
