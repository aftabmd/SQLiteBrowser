package de.szut.dqi12.sqlitebrowser;

import de.szut.dqi12.sqlitebrowser.gui.View;
import de.szut.dqi12.sqlitebrowser.sqlite.Model;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Der Kontrolleur des SQLiteBrowser.
 * @author Till Schlechtweg
 */
public class Controller {
    
    private static Model model;
    private static View view;
    public static Controller instance;
    
    private Controller() {
        view = View.getView();
        model = Model.getModel();
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
    }
}
