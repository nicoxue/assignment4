/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.fastenalcompany.datesource;

import ca.fastenalcompany.config.PropertyManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Stone Chao <alpseinstein@gmail.com>
 */
public class DBManager {
    public static Connection getMysqlConn() throws SQLException{
        Connection conn = null;
        try {
            String db_driver = PropertyManager.getProperty("db_driver");
            String db_url = PropertyManager.getProperty("db_url");
            String db_username = PropertyManager.getProperty("db_username");
            String db_password = PropertyManager.getProperty("db_password");
            Class.forName(db_driver);
            
            conn=DriverManager.getConnection(db_url,db_username,db_password);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
		System.out.println("Connection Failed! Check output console");
		e.printStackTrace();

	}
        return conn;
    }
}
