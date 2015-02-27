package de.szut.dqi12.sqlitebrowser.gui.popups;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Till Schlechtweg
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
