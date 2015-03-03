package de.szut.dqi12.sqlitebrowser.gui.listeners;

import de.szut.dqi12.sqlitebrowser.Controller;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author Till Schlechtweg
 */
public class EditDeleteTableListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JDialog sChange = new JDialog(Controller.getView());
        JPanel grid = new JPanel();
        JComboBox box = new JComboBox();
        JButton delete = new JButton();

        delete.setText("Create table");
        
        for( String name: Controller.getModel().getTableNames()){
            box.addItem(name);
        }

        delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getModel().deleteTable(box.getSelectedItem().toString());
                Controller.getController().updateTree();
            }
        });
        
        sChange.setTitle("Delete Table");
        sChange.setSize(450, 50);
        sChange.setResizable(false);
        
        grid.setLayout(new GridLayout());
        sChange.setContentPane(grid);
        sChange.getContentPane().add(box);
        sChange.getContentPane().add(delete);
        
        int x = Controller.getView().getX() + Controller.getView().getWidth() / 2 - sChange.getWidth() / 2;
        int y = Controller.getView().getY() + Controller.getView().getHeight() / 2 - sChange.getHeight() / 2;

        sChange.setBounds(x, y, sChange.getWidth(), sChange.getHeight());
        
        sChange.setVisible(true);
    }
}
