package de.szut.dqi12.sqlitebrowser.util;

/**
 * Dieser Enthällt die Standard Einstellungen für den SQLiteBrowser.
 * @author Till Schlechtweg
 */
public class SettingsHolder {

    public static String JDBC_DRIVER_NAME = "jdbc.driver";

    public static String DB_URL = "geodaten.db3";
    public static String DB_USER = "root";
    public static String DB_PASSWORD = "";

    public static String VIEW_HEIGHT = "600";
    public static String VIEW_WIDTH = "800";

    public static String VIEW_X_POSITION = "0";
    public static String VIEW_Y_POSITION = "0";

    public static String SETTINGS_FILE = "jdbc.properties";
    
    public String getProperty(String name){
        
    }
    
    public void setProperty(String name, String value){
        
    }
}
