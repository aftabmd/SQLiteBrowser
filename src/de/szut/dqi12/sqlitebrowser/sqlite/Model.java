package de.szut.dqi12.sqlitebrowser.sqlite;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {

    public String user;
    public String pass;
    public String url;
    public Connection conn;

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
     * Initializiert eine Datenbank Verbindung mit den Variablen, der
     * Klasse @see SQL 
     * Wenn keine Verbindung erstellt werden kann loggt der Logger diesen Vorfall.
     * 
     * @return Eine Verbindung mit den gegebenen Variablen oder null wenn es nicht klappt.
     */
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
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
    
    public ArrayList<String> extractResult(ResultSet resultSet, String columnName){
        ArrayList<String> resultArray = new ArrayList();
        
        try {
            while(resultSet.next()){
                resultArray.add(resultSet.getString(columnName));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultArray;
    }
    
    public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void test(){
    }
    
}
