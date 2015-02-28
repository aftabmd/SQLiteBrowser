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

    /**
     * Konstruktor
     *
     * @param url Pfad zur Datenbank
     * @param pass Password des Users der Datenbank
     * @param user Benutzername zur Datenbank
     */
    public Model(String url, String password, String user) {
        // Komponenten der Klasse werden initailisiert.
        this.url = url;
        this.pass = password;
        this.user = user;
        this.start = 0;
        this.end = 20;

        try {
            //Treiber der Datenbank wird geladen.
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Verbindung zur Datenbank wird aufgebaut
        this.conn = this.getConnection();

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
            //Verbindung zur Datenbank wird aufgebaut.
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
            //Ein "Query" wird ausgefuehrt
            stat = this.conn.createStatement();
            rs = stat.executeQuery(query);
            // Es wird durch die Spalten einer Tabelle einer Datenbank iteriert.
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //Fuer jede Spalte wird eine ArrayList erzeugt.
                values = new ArrayList<>();
                //Die einzellenen Reihen einer Spalte werden einer ArrayList hinzugefuegt.
                while (rs.next()) {
                    values.add(rs.getString(rs.getMetaData().getColumnLabel(i)));
                }
                //Je Spalte erhalte eine HashMap einen key, 
                //welcher der name der Spalte ist und als Value eine ArrayList mit deren Werten.
                data.put(rs.getMetaData().getColumnLabel(i), values.clone());
            }
        } catch (SQLException ex) {
            //Bei einem Fehler wird null zurueckgeliefert
            return null;
        }
        //Die HashMap mit Spaltennamen und Werten wird zurueckgeliefert
        return data;
    }

    /**
     * Die Tabellennamen der Datenbank werden ausgelesen
     *
     * @return Es werden die Tabellennamen in einer ArrayList zurueckgegeben
     */
    public ArrayList<String> getTableNames() {
        ResultSet rs = null;
        ArrayList<String> tableNames = new ArrayList<>();
        try {
            //Die namen der Tabellen werden aus den MataDaten der Verbindung zur Datenbank ausgelesen.
            rs = conn.getMetaData().getTables(null, null, "%", null);
            //Die Namen der Tabellen werden einer ArrayList hinzugefuegt.
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //Die ArrayList wird zurueckgeliefert.
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
            //Ein Statement wird vorbereitet, welches eine biliebige Tabelle entfernt.
            PreparedStatement ps = conn.prepareStatement("Drop Table ?");
            //Dem Statement wird der Name der Tablle hinzugefuegt.
            ps.setString(1, table);
            //Das Statement wird ausgefuehrt.
            ps.executeUpdate();
            //es wird true zurueckgelifert
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //es wird false zurueckgelifert
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
            //Ein Statement wird vorbereitet, welches eine Tabelle erzeugt
            stat = this.conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS ?");
            //Der Name der Tabelle wird dem Statement hinzugefuegt.
            ps.setString(1, name);
            //Das Statement wird ausgefuehrt.
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
     * @param orderCol Name der Spalte, nach welcher die Tabelle geordnet wird.
     * @return Eine HashMap, welche als Key den jeweiligen Spaltennamen enthaelt
     * und als Value eine Arraylist vom Typ String, mit den jeweiligen Eintragen
     */
    public HashMap getTable(String name, String orderCol) {
        HashMap data = new HashMap<String, ArrayList>();
        Statement stat;
        ResultSet rs = null;
        ArrayList<String> values;
        try {
            if (orderCol != null) {
                //Ein Statement wird vorbereitet, welches die Daten einer Tabelle ausliest.
                stat = this.conn.createStatement();
                PreparedStatement ps = conn.prepareStatement("select * from ? LIMIT ? OFFSET ? ORDER BY ?");
                //Der Name der Tabelle, ein start der Werte, einer Reihenfolge der Ordnung 
                //und ein Ende der Werte werden dem Statement hinzugefuegt.
                ps.setString(1, name);
                ps.setInt(2, this.end);
                ps.setInt(3, this.start);
                ps.setString(4, orderCol);
                //Das Statement wird ausgefuehrt.
                rs = ps.executeQuery();
            } else {
                //Ein Statement wird vorbereitet, welches die Daten einer Tabelle ausliest.
                stat = this.conn.createStatement();
                PreparedStatement ps = conn.prepareStatement("select * from ? LIMIT ? OFFSET ?");
                //Der Name der Tabelle, ein start der Werte und ein Ende der Werte werden dem Statement hinzugefuegt.
                ps.setString(1, name);
                ps.setInt(2, this.end);
                ps.setInt(3, this.start);
                //Das Statement wird ausgefuehrt.
                rs = ps.executeQuery();
            }

            //Die Namen der Spalten der Tabelle werden ausgelesen und durch ihnen iterriert.
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                values = new ArrayList<>();
                //Einer HashMap werden als Key die Spaltennamen der Tabelle uebergeben
                //und als Values jeweils eine ArrayList mit den jeweiligen Werten der Reihen.
                while (rs.next()) {
                    values.add(rs.getString(rs.getMetaData().getColumnLabel(i)));
                }
                data.put(rs.getMetaData().getColumnLabel(i), values.clone());
            }
        } catch (SQLException ex) {
            //Bei einem Fehler wird null zurueckgegeben
            return null;
        }
        //Die HashMap mit Spaltennamen und Werten wird zurueckgegeben
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
            //Ein Statement wird vorberitet, wleches eine bestimmte Spalte einer Tabelle zurueckliefert.
            stat = this.conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("SELECT ? from ?");
            //Dem Statement wird ein Tabellennamen und ein Spaltennamen uebergeben.
            ps.setString(1, col);
            ps.setString(2, table);
            //Das Staement wird ausgefuehrt.
            rs = ps.executeQuery();

        } catch (SQLException ex) {
            ex.printStackTrace();
            //Bei einem Fehler wird null zurueckgegeben.
            return null;
        }
        //Die werde der Spalte werden in einer ArrayList gespeichert und zurueckgegeben.
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
            //Ein Statement wird vorbereitet, welches einer Tabelle eine Spalte hinzufuegt.
            stat = this.conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("ALTR TABLE ? ADD ? STRING");
            //Dem Statement werden Tabellennamen und Spaltennamen hinzugefuegt.
            ps.setString(1, table);
            ps.setString(2, col);
            //Das Statement wird ausgefuehrt.
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    public boolean addRow(HashMap dataSet, String tableName) {
        try {
            //Ein Statement wird vorbereitetm wleches einer Tabelle eine anzahl von Werten in bestimmte Spalten hinzufuegt.
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ? (?) VALUES (?)");
            //Es werden Stringzwischenspeicher erzeugt.
            StringBuffer bufferCol = new StringBuffer();
            StringBuffer bufferValue = new StringBuffer();
            //Es wird durch die zuveraendernden Spalten iterriert.
            for (Object key : dataSet.keySet()) {
                //Ein String mit den Spaltennamen wird erzeugt.
                bufferCol.append(key + ", ");
                //Es wird ein String mit den Werten der Spalten erzeugt.
                bufferValue.append(dataSet.get(String.valueOf(key) + ", "));

            }
            // Dem Statement wird ein Tabellennamen, Spaltennamen und Werte Hinzugefuegt.
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
            //Es wird ein Statement vorbereitet, welches eine Spalte einer Tabelle entfernt.
            stat = this.conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("ALTER TABLE ? DROP COLUMN ?");
            //Dem Statement wird ein Tabellennamen und ein Spaltennamen uebergeben.
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
