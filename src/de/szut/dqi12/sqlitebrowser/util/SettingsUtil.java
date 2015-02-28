package de.szut.dqi12.sqlitebrowser.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

    private Properties properties;

    public SettingsUtil() {
        this.properties = new Properties();
    }

    /**
     * Liest die Einstellungen der @see SettingsUtil#SETTINGS_FILE ein.
     *
     * @author Harm Hörnlein & Till Schlechtweg
     * @param filename
     * @return 0, wenn Updaten der Properties fehlgeschlagen ist und 1 wenn es
     * erfolgreich war.
     * @throws java.io.IOException
     */
    public boolean updateProperties(String filename) throws IOException {
        File file = new File(filename);

        if (!file.exists()) {
            if (!file.createNewFile()) {
                return false;
            } else {
                return saveStandardProperties(filename);
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

    private boolean saveStandardProperties(String filename) {
        properties.setProperty(SettingsUtil.JDBC_DRIVER_NAME, "org.sqlite.JDBC");
        properties.setProperty(SettingsUtil.VIEW_HEIGHT, "600");
        properties.setProperty(SettingsUtil.VIEW_WIDTH, "800");
        properties.setProperty(SettingsUtil.VIEW_X_POSITION, "0");
        properties.setProperty(SettingsUtil.VIEW_Y_POSITION, "0");
        properties.setProperty(SettingsUtil.DB_URL, "jdbc:sqlite:geodaten.db3");
        properties.setProperty(SettingsUtil.DB_USER, "root");
        properties.setProperty(SettingsUtil.DB_PASSWORD, "");

        File file = new File(filename);

        try {
            OutputStream outputStream = new FileOutputStream(file);
            properties.store(outputStream, "Settings for SQLLiteBrowser");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Speichert die Properties ab.
     *
     * @param url
     * @param user
     * @param pass
     */
    public void saveProperties(String filename) {
        File file = new File(filename);

        try {
            OutputStream outputStream = new FileOutputStream(file);
            properties.store(outputStream, "Settings for SQLLiteBrowser");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}
