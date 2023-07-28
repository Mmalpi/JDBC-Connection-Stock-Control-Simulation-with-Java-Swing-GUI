package com.mmalpi.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {
	
	public Connection recuperaConexion() throws SQLException{
		//agrega tu propio user //Add your own user
		String user = "root";
		//agrega tu propia contraseña //Add your own password
		String password = "mmalpipass";
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC", user, password);
		return con;	
	}

}
