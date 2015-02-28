package de.szut.dqi12.sqlitebrowser;

import de.szut.dqi12.sqlitebrowser.gui.View;
import de.szut.dqi12.sqlitebrowser.sqlite.Model;
import de.szut.dqi12.sqlitebrowser.util.SettingsUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Der Kontrolleur des SQLiteBrowser.
 * @author Till Schlechtweg
 */
public class Controller {
    
    private static Model model;
    private static View view;
    public static SettingsUtil settings;
    public static Controller instance;
    
    private Controller() {
        view = View.getView();
        model = new Model();
        settings = new SettingsUtil();
    }
    
    public static Controller getController(){
        if(instance == null) instance = new Controller();
        return instance;
    }
    
    public void removeRow(){
        
    }
    
    public ArrayList<String> executeQuerry(String query){
        return null;
    }
    
    public void updateTableNames(){
        view.updateTree(model.getTableNames(), new DefaultMutableTreeNode("Penis"));
    }
    
    public static void main(String[] args){
        Controller.getController();
        try {
            Controller.settings.updateProperties("jdbc.properties");
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(SettingsUtil.properties.getProperty(SettingsUtil.JDBC_DRIVER_NAME));
    }
}
