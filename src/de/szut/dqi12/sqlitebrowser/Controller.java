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
 *
 * @author Till Schlechtweg
 */
public class Controller {

    private static Model model;
    private static View view;
    public static SettingsUtil settings;
    public static Controller instance;

    /**
     * Startet alles was zur Ausführung des SQLiteBrowsers benötigt wird.
     */
    private Controller() {
        settings = new SettingsUtil();

        settings.updateProperties();

        String url = settings.getProperty(SettingsUtil.DB_URL);
        String pass = settings.getProperty(SettingsUtil.DB_PASSWORD);
        String user = settings.getProperty(SettingsUtil.DB_USER);
        String driver = settings.getProperty(SettingsUtil.JDBC_DRIVER_NAME);
        model = new Model(url, pass, user, driver);

        view = View.getView();
    }

    
    public static Controller getController() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /**
     * Kommuniziert zwischen Model and View um die Abfrage auszuführen.
     * @param query Die auszuführende Abfrage (SELECT only)
     */
    public void executeQuerry(String query) {
        view.updateTable(model.executeQuery(query));
    }

    /**
     * Updated den Tree des Views mithilfe von Daten aus dem Model, sodass die View nicht auf das Model zugreifen muss.
     */
    public void updateTree() {
        view.updateTree(model.getTableNames(), new DefaultMutableTreeNode(model.getDatabaseName()));
    }

    public static void main(String[] args) {
        Controller.getController();
    }

    /**
     * Updated den Table der View mithilfe von Daten aus dem Model.
     * @param tableName Der Name der anzuzeigenden Tabelle
     */
    public void updateTable(String tableName) {
        view.updateTable(model.getTable(tableName, null));
    }

    /*
     Getter und Setter des Controllers.
     */
    public static Model getModel() {
        return model;
    }

    public static void setModel(Model model) {
        Controller.model = model;
    }

    public static View getView() {
        return view;
    }

    public static void setView(View view) {
        Controller.view = view;
    }

    public static SettingsUtil getSettings() {
        return settings;
    }

    public static void setSettings(SettingsUtil settings) {
        Controller.settings = settings;
    }
}
