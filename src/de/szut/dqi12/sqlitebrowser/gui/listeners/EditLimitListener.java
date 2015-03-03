package de.szut.dqi12.sqlitebrowser.gui.listeners;

import de.szut.dqi12.sqlitebrowser.Controller;
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
public class EditLimitListener implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JDialog limit = new JDialog();
        JPanel grid = new JPanel();
        JLabel vonL = new JLabel();
        JTextField bisF = new JTextField();
        JButton limitB = new JButton();
        
        limitB.setText("set LIMIT");
        
        limitB.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getModel().setStart(Integer.valueOf(bisF.getText()));
                Controller.getController().updateTable(Controller.getModel().getTableNames().get(0));
            }
            
        });
        
        vonL.setText("LIMIT: ");
        
        grid.setLayout(new GridLayout());
        grid.add(vonL);
        grid.add(bisF);
        grid.add(limitB);
        
        limit.setSize(300, 60);
        limit.setContentPane(grid);
        
        int x = Controller.getView().getX() + Controller.getView().getWidth() / 2 - limit.getWidth() / 2;
        int y = Controller.getView().getY() + Controller.getView().getHeight() / 2 - limit.getHeight() / 2;
        
        limit.setBounds(x, y, limit.getWidth(), limit.getHeight());
        
        limit.setVisible(true);
    }
    
}
