package de.szut.dqi12.sqlitebrowser.gui.listeners;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Till Schlechtweg
 */
public class SaveBoundsListener extends ComponentAdapter {

    @Override
    public void componentResized(ComponentEvent evt) {
        JOptionPane.showMessageDialog(null, "Penis");
    }
}
