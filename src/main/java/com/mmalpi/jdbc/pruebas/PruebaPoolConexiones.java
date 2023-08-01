package com.mmalpi.jdbc.pruebas;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import com.mmalpi.jdbc.factory.ConnectionFactory;

public class PruebaPoolConexiones {
	
	public static void main(String[] args) throws SQLException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		for (int i = 0; i < 20; i++) {
			Connection conexion = connectionFactory.recuperaConexion();
			System.out.println("Abriendo conexion numero" + (i+1));
			
		}
	}
}
