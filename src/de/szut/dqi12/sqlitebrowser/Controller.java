package de.szut.dqi12.sqlitebrowser;

import de.szut.dqi12.sqlitebrowser.gui.View;
import de.szut.dqi12.sqlitebrowser.sqlite.Model;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Till Schlechtweg
 */
public class Controller {
    
    private static Model model;
    private static View view;
    public static Controller instance;
    
    private Controller() {
        
    }
    
    public static Controller getController(){
        if(instance == null) instance = new Controller();
        return instance;
    }
    
    public ArrayList<String> executeQuerry(String query){
        return null;
    }
    
    public void updateTableNames(){
        view.updateTree(model.getTableNames(), new DefaultMutableTreeNode("Penis"));
    }
    
    public static void main(String[] args){
        view = new View();
        model = new Model(null, null, null);
    }
}
