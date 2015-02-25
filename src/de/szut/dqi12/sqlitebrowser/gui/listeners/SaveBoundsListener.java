/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.sqlitebrowser.gui.listeners;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author tsc
 */
public class SaveBoundsListener extends ComponentAdapter {

    @Override
    public void componentResized(ComponentEvent evt){
        JOptionPane.showMessageDialog(null, "Penis");
    }
}
