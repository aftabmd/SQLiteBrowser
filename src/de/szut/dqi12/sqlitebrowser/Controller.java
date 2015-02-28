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

    private Controller() {
        settings = new SettingsUtil();

        try {
            settings.updateProperties("jdbc.properties");
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        String url = settings.getProperties().getProperty(SettingsUtil.DB_URL);
        String pass = settings.getProperties().getProperty(SettingsUtil.DB_PASSWORD);
        String user = settings.getProperties().getProperty(SettingsUtil.DB_USER);
        String driver = settings.getProperties().getProperty(SettingsUtil.JDBC_DRIVER_NAME);
        model = new Model(url, pass, user, driver);

        view = View.getView();
    }

    public static Controller getController() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void removeRow() {

    }

    public ArrayList<String> executeQuerry(String query) {
        return null;
    }

    public void updateTree() {
        view.updateTree(model.getTableNames(), new DefaultMutableTreeNode(model.getDatabaseName()));
    }

    public static void main(String[] args) {
        Controller.getController();
    }

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
