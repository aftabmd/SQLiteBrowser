package de.szut.dqi12.sqlitebrowser.sqlite;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {

    private String user;
    private String pass;
    private String url;
    private Connection conn;
    private String database;

    /**
     * Konstruktor
     *
     * @param url Pfad zur Datenbank
     * @param pass Password des Users der Datenbank
     * @param user Benutzername zur Datenbank
     */
    public Model(String url, String pass, String user) {
        this.url = url;
        this.pass = pass;
        this.user = user;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.conn = this.getConnection();
    }

    /**
     * Eine Verbindung zu einer Datenbank wird aufgebaut
     *
     * @return Eine Verbindung zur Datenbank
     */
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.url, this.user, this.pass);
            database = conn.getMetaData().getDatabaseProductName();
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    /**
     * Ein SQL-Befehl wird auf der Datenbank ausgefuehrt
     *
     * @param query
     * @return Die Daten, welche die Datenbank zurueckliefert werden geliefert
     */
    public ResultSet executeQuery(String query) {
        Statement stat;
        ResultSet rs = null;
        try {
            stat = this.conn.createStatement();
            rs = stat.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }
    
    
    public String[] getTables(){
        String[] databases = new String[conn.getMetaData().get];
    }
    
    
    
    

    /**
     * Eine Tabelle der Datenbank wird entfernt
     *
     * @param table name der Tabelle der Datenbank
     * @return liefert true oder false zurueck ob die operation erfolgte
     */
    public boolean deleteTable(String table) {
        Statement stat;
        ResultSet rs = null;
        try {
            stat = this.conn.createStatement();
            rs = stat.executeQuery("DROP TABLE " + table);
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
        ResultSet rs = null;
        try {
            stat = this.conn.createStatement();
            rs = stat.executeQuery("CREATE TABLE IF NOT EXISTS " + name);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    /**
     * Der Inhalt einer Tabelle einer Datenbank wird zurueckgegeben
     *
     * @param table name der Tabelle
     * @return Liefert den Inhalt der Tabelle in einer ArrayList zurueck
     */
    public ArrayList<String> showColumn(String table) {

        Statement stat;
        ResultSet rs = null;
        try {
            stat = this.conn.createStatement();
            rs = stat.executeQuery("SELECT * FROM " + database + "." + table);

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        ArrayList<String> resultArray = new ArrayList();
        HashMap reslut = new HashMap();

        try {
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                try {
                    while (rs.next()) {
                        resultArray.add(rs.getString(rs.getMetaData().getColumnName(i)));
                        if (resultArray.size() > 20) {
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
        ResultSet rs = null;
        try {
            stat = this.conn.createStatement();
            rs = stat.executeQuery("ALTER TABLE " + table + " ADD " + col + " String");
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
        ResultSet rs = null;
        try {
            stat = this.conn.createStatement();
            rs = stat.executeQuery("ALTER TABLE " + table + " DROP COLUMN " + col);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Daten einer Datenbank werden aufbereitet
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
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultArray;
    }

    /**
     * Die Verbindung zur Datenbank wird geschlossen
     */
    public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
