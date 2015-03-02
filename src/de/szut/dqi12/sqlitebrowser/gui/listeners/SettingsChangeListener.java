/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.sqlitebrowser.gui.listeners;

import de.szut.dqi12.sqlitebrowser.Controller;
import de.szut.dqi12.sqlitebrowser.util.SettingsUtil;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Till Schlechtweg
 */
public class SettingsChangeListener implements ActionListener {
    
    public JTextField userField;
    public JTextField passField;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JDialog sChange = new JDialog(Controller.getView());
        JPanel grid = new JPanel();
        
        int x = Controller.getView().getX()+Controller.getView().getWidth()/2-sChange.getWidth()/2;
        int y = Controller.getView().getY()+Controller.getView().getHeight()/2-sChange.getHeight()/2;
        
        sChange.setBounds(x, y, sChange.getWidth(), sChange.getHeight());
        
        sChange.setTitle("Change Settings");
        sChange.setSize(650, 50);
        sChange.setResizable(false);
        
        JButton save = new JButton();
        save.setText("Save Settings");
        
        save.addActionListener((ActionEvent e1) -> {
            Controller.getSettings().setProperty(SettingsUtil.DB_USER, userField.getText());
            Controller.getSettings().setProperty(SettingsUtil.DB_PASSWORD, passField.getText());
            Controller.getSettings().saveProperties();
        });
        
        JLabel userLabel = new JLabel();
        JLabel passLabel = new JLabel();
        
        passField = new JTextField();
        userField = new JTextField();
        
        userLabel.setText("Database User: ");
        passLabel.setText("Database Password: ");
        
        userField.setText(Controller.getSettings().getProperty(SettingsUtil.DB_USER));
        passField.setText(Controller.getSettings().getProperty(SettingsUtil.DB_PASSWORD));
        
        grid.setLayout(new GridLayout());
        grid.add(userLabel);
        grid.add(userField);
        grid.add(passLabel);
        grid.add(passField);
        grid.add(save);
        
        sChange.setContentPane(grid);
        sChange.setVisible(true);
    }
}
