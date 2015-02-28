package de.szut.dqi12.sqlitebrowser.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Verschiedene Einstellungen für den SQLiteBrowser.
 * 
 * @author Till Schlechtweg
 */
public class SettingsUtil {

    public static String JDBC_DRIVER_NAME = "jdbc.driver";

    public static String DB_URL = "db.url";
    public static String DB_USER = "db.user";
    public static String DB_PASSWORD = "db.password";

    public static String VIEW_HEIGHT = "view.height";
    public static String VIEW_WIDTH = "view.width";

    public static String VIEW_X_POSITION = "view.x";
    public static String VIEW_Y_POSITION = "view.y";

    public static String SETTINGS_FILE = "sqlitebrowser.properties";
    
    /**
     * Liest die Einstellungen der @see Settings#SETTINGS_FILE ein.
     * 
     * @author Harm Hörnlein & Till Schlechtweg
     * @param filename
     * @return 0, wenn Updaten der Properties fehlgeschlagen ist und 1 wenn es erfolgreich war.
     */
    private int updateProperties(String filename) {
        Properties prop = new Properties();
        File file = new File(SettingsUtil.SETTINGS_FILE);
        
        if(!file.exists()){
            return 0;
        }
        
        try {
            try (InputStream inputStream = getClass().getResourceAsStream(SettingsUtil.SETTINGS_FILE)) {
                prop.load(inputStream);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 1;
    }
    
    /**
     *  Speichert die Properties ab.
     * 
     * @param url
     * @param user
     * @param pass 
     */
    public void saveProperties(String url, String user, String pass){
        Properties prop = new Properties();
        File file = new File(SettingsUtil.SETTINGS_FILE);
        
        prop.setProperty("jdbc.driver", this.JDBC_DRIVER_NAME);
        prop.setProperty("view.height", this.VIEW_HEIGHT);
        prop.setProperty("view.width", this.VIEW_WIDTH);
        prop.setProperty("view.xposition", this.VIEW_X_POSITION);
        prop.setProperty("view.yposition", this.VIEW_Y_POSITION);
        
        prop.setProperty("jdbc.url", this.DB_URL);
        prop.setProperty("jdbc.user", this.DB_USER);
        prop.setProperty("jdbc.password", this.DB_PASSWORD);
        
               
        try{
            OutputStream outputStream = new FileOutputStream(file);
            prop.store(outputStream, "Settings for SQLLiteBrowser");
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
