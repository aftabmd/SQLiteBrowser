package de.szut.dqi12.sqlitebrowser.sqlite;

import de.szut.dqi12.sqlitebrowser.util.SettingsUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * 
 * @author Robin Bley
 */
public class Model {

    private String user;
    private String pass;
    private String url;
    private Connection conn;
    private int start;
    private int end;
    private static Model instance = null;

    /**
     * Konstruktor
     *
     * @param url Pfad zur Datenbank
     * @param pass Password des Users der Datenbank
     * @param user Benutzername zur Datenbank
     */
    protected Model() {
        this.url = SettingsUtil.DB_URL;
        this.pass = SettingsUtil.DB_PASSWORD;
        this.user = SettingsUtil.DB_USER;
        this.start = 0;
        this.end = 20;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.conn = this.getConnection();
    }
    
    public static Model getModel(){
        if(instance == null) instance = new Model();
        return instance;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * Eine Verbindung zu einer Datenbank wird aufgebaut
     *
     * @return Eine Verbindung zur Datenbank
     */
    public Connection getConnection() {
        try {
            conn = DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    /**
     * Ein SQL-Befehl wird auf der Datenbank ausgefuehrt
     *
     * @param query
     * @return Die Daten, welche die Datenbank zurueckliefert werden geliefert
     */
    public HashMap executeQuery(String query) {
        HashMap data = new HashMap<String, ArrayList>();
        Statement stat;
        ResultSet rs = null;
        ArrayList<String> values;
        try {
            stat = this.conn.createStatement();
            rs = stat.executeQuery(query);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                values = new ArrayList<>();
                while (rs.next()) {
                    values.add(rs.getString(rs.getMetaData().getColumnLabel(i)));
                }
                data.put(rs.getMetaData().getColumnLabel(i), values.clone());
            }
        } catch (SQLException ex) {
            return null;
        }

        return data;
    }

    /**
     * Die Tabellennamen der Datenbank werden ausgelesen
     *
     * @return Es werden die Tabellennamen in einer ArrayList zurueckgegeben
     */
    public ArrayList<String> getTableNames() {
        Statement stat;
        ResultSet rs = null;
        ArrayList<String> tableNames = new ArrayList<>();
        try {
            stat = this.conn.createStatement();
            rs = conn.getMetaData().getTables(null, null, "%", null);
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tableNames;

    }

    /**
     * Eine Tabelle der Datenbank wird entfernt
     *
     * @param table name der Tabelle der Datenbank
     * @return liefert true oder false zurueck ob die operation erfolgte
     */
    public boolean deleteTable(String table) {
        Statement stat;
        try {
            stat = this.conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("Drop Table ?");
            ps.setString(1, table);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Eine neue Tabelle wird in der Datenbank erstellt
     *
     * @param name name der Tabelle der Datenbank
     * @return liefert true oder false zurueck ob die operation erfolgte
     */
    public boolean createTable(String name) {
        Statement stat;
        try {
            stat = this.conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS ?");
            ps.setString(1, name);
            ps.executeQuery();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    /**
     * Eine Tabelle einer Datenbank wird ausgelesen und Convertiert
     * zurueckgeliefert
     *
     * @param name Name der Tabelle
     * @return Eine HashMap, welche als Key den jeweiligen Spaltennamen enthaelt
     * und als Value eine Arraylist vom Typ String, mit den jeweiligen Eintragen
     */
    public HashMap getTable(String name) {
        HashMap data = new HashMap<String, ArrayList>();
        Statement stat;
        ResultSet rs = null;
        ArrayList<String> values;
        try {
            stat = this.conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("select * from ? LIMIT ? OFFSET ?");
            ps.setString(1, name);
            ps.setInt(2, this.end);
            ps.setInt(3, this.start);
            rs = ps.executeQuery();

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                values = new ArrayList<>();
                while (rs.next()) {
                    values.add(rs.getString(rs.getMetaData().getColumnLabel(i)));
                }
                data.put(rs.getMetaData().getColumnLabel(i), values.clone());
            }
        } catch (SQLException ex) {
            return null;
        }

        return data;
    }

    /**
     * Der Inhalt einer Datenbank wird zurueckgegeben
     *
     * @param table name der Tabelle
     * @return Liefert den Inhalt der Tabelle in einer ArrayList zurueck
     */
    public ArrayList<String> showColumn(String table, String col) {

        Statement stat;
        ResultSet rs = null;
        try {
            stat = this.conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("SELECT ? from ?");

            ps.setString(1, col);
            ps.setString(2, table);
            rs = ps.executeQuery();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        ArrayList<String> resultArray = new ArrayList();

        try {
            while (rs.next()) {
                resultArray.add(rs.getString(col));
                if (resultArray.size() > 20) {
                    break;
                }
            }
        } catch (SQLException ex) {
            return null;
        }
        return resultArray;
    }

    /**
     *
     * @param table name der Tabelle der Datenbank
     * @param col Spalte, welche hinzugefuegt werden sollen
     * @return liefert true oder false zurueck ob die operation erfolgte
     */
    public boolean addCol(String col, String table) {
        Statement stat;
        try {
            stat = this.conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("ALTR TABLE ? ADD ? STRING");
            
            ps.setString(1, table);
            ps.setString(2, col);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    public boolean addRow(HashMap dataSet, String tableName) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ? (?) VALUES (?)");
            StringBuffer bufferCol = new StringBuffer();
            StringBuffer bufferValue = new StringBuffer();
            for (Object key : dataSet.keySet()) {

                bufferCol.append(key + ", ");
                bufferValue.append(dataSet.get(String.valueOf(key) + ", "));

            }
            ps.setString(1, tableName);
            ps.setString(2, bufferCol.substring(0, bufferCol.length() - 2));
            ps.setString(3, bufferValue.substring(0, bufferValue.length() - 2));
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Eine Spalte einer Tablle wird entfernt
     *
     * @param col Spalte, welche entfernt werden soll
     * @param table Tabelle, aus welcher die Spalte entfernt werden soll.
     * @return liefert true oder false zurueck ob die Operation erfolgte
     */
    public boolean delColumn(String col, String table) {
        Statement stat;
        try {
            stat = this.conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("ALTER TABLE ? DROP COLUMN ?");
            ps.setString(1, table);
            ps.setString(2, col);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * @deprecated Daten einer Datenbank werden aufbereitet
     *
     * @param resultSet Daten, welche aufbereitet werden sollen
     * @param columnName Name der Spalte, wessen Daten aufbereitet werden sollen
     * @return Aufbereitete Daten werden in einer ArrayList zurueck geliefert
     */
    public ArrayList<String> extractResult(ResultSet resultSet, String columnName) {
        ArrayList<String> resultArray = new ArrayList();

        try {
            while (resultSet.next()) {
                resultArray.add(resultSet.getString(columnName));
                if (resultArray.size() > 20) {
                    break;

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return resultArray;
    }

    public String getDatabaseName() {
        return this.url;
    }

    /**
     * Die Verbindung zur Datenbank wird geschlossen
     */
    public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
