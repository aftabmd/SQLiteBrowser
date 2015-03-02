package de.szut.dqi12.sqlitebrowser.gui.listeners;

import de.szut.dqi12.sqlitebrowser.Controller;
import de.szut.dqi12.sqlitebrowser.util.SettingsUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;

/**
 *
 * @author Till Schlechtweg
 */
public class FileOpenListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        
        int selectedOption = fileChooser.showOpenDialog(Controller.getView());
        
        if(selectedOption == JFileChooser.APPROVE_OPTION){
            String url = "jdbc:sqlite:" + fileChooser.getSelectedFile().getName();
            Controller.getSettings().setProperty(SettingsUtil.DB_URL, url);
            
            String pass = Controller.getSettings().getProperty(SettingsUtil.DB_PASSWORD);
            String user = Controller.getSettings().getProperty(SettingsUtil.DB_USER);
            
            
            Controller.getModel().updateConnection(url, user, pass);
            Controller.getSettings().saveProperties();
            Controller.getController().updateTree();

        }
    }
    
}
