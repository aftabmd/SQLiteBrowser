package de.szut.dqi12.sqlitebrowser.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Verschiedene Einstellungen für den SQLiteBrowser.
 *
 * @author Till Schlechtweg
 */
public class SettingsUtil {

    //Keys der Properties 

    public static String JDBC_DRIVER_NAME = "jdbc.driver";

    public static String DB_URL = "db.url";
    public static String DB_USER = "db.user";
    public static String DB_PASSWORD = "db.password";

    public static String VIEW_HEIGHT = "view.height";
    public static String VIEW_WIDTH = "view.width";

    public static String VIEW_X_POSITION = "view.x";
    public static String VIEW_Y_POSITION = "view.y";

    private Properties properties;

    public SettingsUtil() {
        this.properties = new Properties();
    }

    /**
     * Liest die Einstellungen der jdbc.properties ein.
     *
     * @author Harm Hörnlein und Till Schlechtweg
     * @return false wenn Updaten der Properties fehlgeschlagen ist und true
     * wenn es erfolgreich war.
     */
    public boolean updateProperties() {
        File file = new File("jdbc.properties");

        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    return false;
                } else {
                    return saveStandardProperties();
                }
            } catch (IOException ex) {
                Logger.getLogger(SettingsUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            try (InputStream inputStream = new FileInputStream(file)) {
                properties.load(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Standard Einstellungen, hart gecoded.
     *
     * @return Ob das Speichern der Standard Einstellungen funktioniert hat.
     */
    private boolean saveStandardProperties() {
        properties.setProperty(SettingsUtil.JDBC_DRIVER_NAME, "org.sqlite.JDBC");
        properties.setProperty(SettingsUtil.VIEW_HEIGHT, "600");
        properties.setProperty(SettingsUtil.VIEW_WIDTH, "800");
        properties.setProperty(SettingsUtil.VIEW_X_POSITION, "0");
        properties.setProperty(SettingsUtil.VIEW_Y_POSITION, "0");
        properties.setProperty(SettingsUtil.DB_URL, "jdbc:sqlite:geodaten.db3");
        properties.setProperty(SettingsUtil.DB_USER, "root");
        properties.setProperty(SettingsUtil.DB_PASSWORD, "");

        return saveProperties();
    }

    /**
     * Speichert die Properties ab.
     *
     * @return Ob das Abspeichern geklappt hat oder nicht.
     */
    public boolean saveProperties() {
        File file = new File("jdbc.properties");

        try {
            OutputStream outputStream = new FileOutputStream(file);
            properties.store(outputStream, "Settings for SQLLiteBrowser");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void setProperty(String key, String value) {
        this.properties.setProperty(key, value);
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }
}
