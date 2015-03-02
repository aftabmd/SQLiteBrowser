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
 * @author Till Schlechtweg
 */
public class ExecuteQueryListener implements ActionListener {

    public JTextField queryField;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JDialog sChange = new JDialog(Controller.getView());
        JPanel grid = new JPanel();
        
        int x = Controller.getView().getX()+Controller.getView().getWidth()/2-sChange.getWidth()/2;
        int y = Controller.getView().getY()+Controller.getView().getHeight()/2-sChange.getHeight()/2;
        
        sChange.setBounds(x, y, sChange.getWidth(), sChange.getHeight());
        
        sChange.setTitle("Query");
        sChange.setSize(450, 50);
        sChange.setResizable(false);
        
        JButton save = new JButton();
        save.setText("Execute Query");
        
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getController().executeQuerry(queryField.getText());
            }
        });
        
        queryField = new JTextField();
        queryField.setText("SELECT * FROM " + Controller.getModel().getTableNames().get(0));
        
        grid.setLayout(new GridLayout());
        grid.add(queryField);
        
        grid.add(save);
        
        sChange.setContentPane(grid);
        sChange.setVisible(true);
    }
}
