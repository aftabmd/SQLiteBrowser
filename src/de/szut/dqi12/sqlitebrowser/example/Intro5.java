package de.szut.dqi12.sqlitebrowser.example;

/**
 * short description
 * 
 */

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * short description opens the president database no more PRAGMA Statement = not
 * flexible selects complete data uses Interface Constants and Property File
 * 
 * @version 1.0, 27.09.2010
 *
 */
public class Intro5 {

	/**
	 * short description
	 *
	 * @param args
	 */

	private Properties prop = null;

	public static void main(String[] args) {
		new Intro5().doWork();
	}

	private void doWork() {
		Connection conn = null;
		// Property File auslesen
		prop = getProperties(Constants.JDBC_PROPERTIES_FILE);
		try {

			// Treiberclasse 'laden'
			// Class.forName( "org.hsqldb.jdbcDriver" );
			try {
				Class.forName(prop.getProperty(Constants.JDBC_DRIVER));
			} catch (ClassNotFoundException e) {
				// OO! Treiber konnte nicht geladen werden.
				e.printStackTrace();
			}
			// Wie wird die Datenbank angesprochen
			// jdbc:<protocoll>://<host>:<port>/<datenbank>?<parameter>
			// http://www.fb-it-solutions.de/szut/jdk/docs/api/java/sql/DriverManager.html#getConnection%28java.lang.String,%20java.lang.String,%20java.lang.String%29
			conn = DriverManager.getConnection(
					prop.getProperty(Constants.JDBC_URL),
					prop.getProperty(Constants.JDBC_USER),
					prop.getProperty(Constants.JDBC_PASSWORD));

			Statement stat = conn.createStatement();

			// Tabellenbeschriftung mit Metadaten
			ResultSet rs = stat.executeQuery("select * from president;");
			ResultSetMetaData meta = rs.getMetaData();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				System.out.print(stringformatter(meta.getColumnName(i), 10));
				System.out.print("|");
			}
			System.out.println();
			// Daten
			while (rs.next()) {
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					System.out.print(stringformatter(rs.getString(i), 10));
					System.out.print("|");
				}
				System.out.println();
			}
			rs.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String stringformatter(String s, int len) {
		if (s == null) {
			return new String(" -                       ").substring(0, len);
		}
		int slength = s.length();
		String res = s + "                        ";
		if (slength > len + 1) {
			return (s.substring(0, len - 3) + "...");

		} else {
			return (res.substring(0, len));
		}
	}

	private Properties getProperties(String filename) {
		Properties p = new Properties();
		try {
			InputStream inputStream = getClass().getResourceAsStream(
					Constants.JDBC_PROPERTIES_FILE);
			p.load(inputStream);
			inputStream.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return p;
	}
}
