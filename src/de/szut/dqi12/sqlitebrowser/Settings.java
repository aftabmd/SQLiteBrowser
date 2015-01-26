package de.szut.dqi12.sqlitebrowser;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * Verschiedene Einstellungen für den SQLiteBrowser.
 * 
 * @author Till Schlechtweg
 */
public class Settings {

    final static String JDBC_DRIVER_NAME = "jdbc.driver";

    final static String DB_URL = "db.url";
    final static String DB_USER = "db.user";
    final static String DB_PASSWORD = "db.password";

    final static String VIEW_HEIGHT = "view.height";
    final static String VIEW_WIDTH = "view.width";

    final static String VIEW_X_POSITION = "view.x";
    final static String VIEW_Y_POSITION = "view.y";

    final static String SETTINGS_FILE = "sqlitebrowser.properties";

    /**
     * Liest die Einstellungen der @see Settings#SETTINGS_FILE ein.
     * 
     * @author Harm Hörnlein & Till Schlechtweg
     * @param filename
     * @return Properties Objekt oder null wenn die Datei nicht exestiert.
     */
    private Properties getProperties(String filename) {
        Properties prop = new Properties();
        File file = new File(Settings.SETTINGS_FILE);
        
        if(!file.exists()){
            return null;
        }
        
        try {
            InputStream inputStream = getClass().getResourceAsStream(Settings.SETTINGS_FILE);
            prop.load(inputStream);
            inputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return prop;
    }
    
    public void saveProperties(String url, String user, String pass){
        
    }
    
    public void saveProperties(int x, int y, int height, int width){
        
    }
}
