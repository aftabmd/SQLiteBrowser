package de.szut.dqi12.sqlitebrowser;

import de.szut.dqi12.sqlitebrowser.sqlite.Model;

public class Main {
	public static void main(String[] args) {
		Model sql = new Model("C:\\Users\\Robin\\Documents\\NetBeansProjects\\SQLiteBrowser\\geodaten.db3", "", "root");
                sql.getConnection();
                
                
                

	}
}
