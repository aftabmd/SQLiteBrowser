package de.szut.dqi12.sqlitebrowser.example;

/**
 * short description
 * 
 */

//import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * short description
 * Creates a sqlite Database in memory
 * creates some entries and selects them afterwards
 * uses prepared statements and batch jobs
 * 
 * @version 1.1, 27.09.2010
 *
 */
public class Intro1 {

	/**
	 * short description
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		new Intro1().doWork();
	}

	private void doWork() {
		Connection conn = null;
		try {

			// Treiberclasse 'laden'
			//Class.forName( "org.hsqldb.jdbcDriver" );
			try 
			{ 
				Class.forName("org.sqlite.JDBC");
			} 
			catch ( ClassNotFoundException e ) 
			{ 
				// OO! Treiber konnte nicht geladen werden. 
				e.printStackTrace(); 
			}
			// Wie wird die Datenbank angesprochen
			// jdbc:<protocoll>://<host>:<port>/<datenbank>?<parameter>
			
			//final String url = "jdbc:sqlite::memory:";
			final String url = "jdbc:sqlite:.//data//president.db";
			final String user = "";
			final String password = "";
			// http://www.fb-it-solutions.de/szut/jdk/docs/api/java/sql/DriverManager.html#getConnection%28java.lang.String,%20java.lang.String,%20java.lang.String%29
			
			//conn = DriverManager.getConnection( url, user, password );
			conn = DriverManager.getConnection( url );
			Statement stat = conn.createStatement();
			stat.executeUpdate("drop table if exists people;");
			//stat.executeUpdate("create table people (name, occupation);");
			stat.execute("create table people (name, occupation);");
			
			PreparedStatement prep = conn.prepareStatement("insert into people values (?, ?);");

			prep.setString(1, "Gandhi");
			prep.setString(2, "politics");
			prep.addBatch();
			prep.setString(1, "Turing");
			prep.setString(2, "computers");
			prep.addBatch();
			prep.setString(1, "Wittgenstein");
			prep.setString(2, "smartypants");
			prep.addBatch();

			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);

			ResultSet rs = stat.executeQuery("select * from people;");
			while (rs.next()) {
				System.out.println("name = " + rs.getString("name"));
				System.out.println("job = " + rs.getString("occupation"));
			}
			rs.close();
		}
		catch( Exception e ) {
			System.out.println( e.getMessage() );
		}
		finally {
			try {
				if (conn != null) {
					conn.close();
				}
			}
			catch( SQLException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
