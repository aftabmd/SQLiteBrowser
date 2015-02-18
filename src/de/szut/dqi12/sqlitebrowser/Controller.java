/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.sqlitebrowser;

import de.szut.dqi12.sqlitebrowser.sqlite.Model;
import java.util.ArrayList;

/**
 *
 * @author holyshit
 */
public class Controller {
    
    private Model model;

    public Controller() {
        model = new Model();
    }
    
    
    public ArrayList<String> executeQuerry(String query){
        return null;
    }
    
}
