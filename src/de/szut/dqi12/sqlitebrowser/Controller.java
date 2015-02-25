package de.szut.dqi12.sqlitebrowser;

import de.szut.dqi12.sqlitebrowser.gui.View;
import de.szut.dqi12.sqlitebrowser.sqlite.Model;
import java.util.ArrayList;

/**
 *
 * @author Till Schlechtweg
 */
public class Controller {
    
    private Model model;
    private View view;
    
    public Controller() {
        view = new View();
    }
    
    public ArrayList<String> executeQuerry(String query){
        return null;
    }
    
    public ArrayList<String> getTableNames(){
        
        return null;
    }
    
    public static void main(String[] args){
        Controller c = new Controller();
        
    }
}
