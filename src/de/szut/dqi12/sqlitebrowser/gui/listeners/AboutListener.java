package de.szut.dqi12.sqlitebrowser.gui.listeners;

import de.szut.dqi12.sqlitebrowser.Controller;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Till Schlechtweg
 */
public class AboutListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JDialog about = new JDialog();
        JPanel grid = new JPanel();
        JLabel aboutL = new JLabel();

        int x = Controller.getView().getX() + Controller.getView().getWidth() / 2 - grid.getWidth() / 2;
        int y = Controller.getView().getY() + Controller.getView().getHeight() / 2 - grid.getHeight() / 2;

        aboutL.setText("SQLiteBrowser made by Robin Bley and Till Schlechtweg");

        grid.setLayout(new GridLayout());
        grid.add(aboutL);

        about.setBounds(x, y, 350, 75);

        about.setContentPane(grid);
        about.setVisible(true);
    }

}
