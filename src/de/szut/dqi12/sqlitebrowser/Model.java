/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.szut.dqi12.sqlitebrowser;

import java.io.InputStream;

/**
 *
 */
public class Model {

    
    /**
     * 
     * @author Harm Hörnlein
     * @param filename
     * @return 
     */
    private java.util.Properties getProperties(String filename) {
        java.util.Properties p = new java.util.Properties();

        try {
            InputStream inputStream = getClass().getResourceAsStream(
                    Properties.JDBC_PROPERTIES_FILE);
            p.load(inputStream);
            inputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return p;
    }
}
