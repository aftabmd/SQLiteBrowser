/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.sqlitebrowser.gui.listeners;

import de.szut.dqi12.sqlitebrowser.Controller;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Manfred
 */
public class EditCreateTableListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JDialog sChange = new JDialog(Controller.getView());
        JPanel grid = new JPanel();
        JTextField tableField = new JTextField();
        JButton delete = new JButton();

        delete.setText("Create table");

        delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getModel().createTable(tableField.getText());
            }
        });

        int x = Controller.getView().getX() + Controller.getView().getWidth() / 2 - sChange.getWidth() / 2;
        int y = Controller.getView().getY() + Controller.getView().getHeight() / 2 - sChange.getHeight() / 2;

        sChange.setBounds(x, y, sChange.getWidth(), sChange.getHeight());

        sChange.setTitle("Delete Table");
        sChange.setSize(450, 50);
        sChange.setResizable(false);
        
        grid.setLayout(new GridLayout());
        sChange.setContentPane(grid);
        sChange.getContentPane().add(tableField);
        sChange.getContentPane().add(delete);
        
        sChange.setVisible(true);
    }
}
