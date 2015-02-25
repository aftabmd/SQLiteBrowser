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
public class TableDatasetPopupMenu extends JPopupMenu {

    JMenuItem deleteRow;
    JMenuItem deleteColumn;
    JMenuItem createRow;
    JMenuItem createColumn;

    public TableDatasetPopupMenu() {
        deleteRow = new JMenuItem("delete Row");
        deleteColumn = new JMenuItem("delete Column");
        createRow = new JMenuItem("create Row");
        createColumn = new JMenuItem("create Column");

        add(deleteRow);
        add(deleteColumn);
        add(createRow);
        add(createColumn);
    }
}
