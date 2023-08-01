package com.mmalpi.jdbc.controller;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mmalpi.jdbc.factory.ConnectionFactory;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
		final Connection CON = new ConnectionFactory().recuperaConexion();
		try(CON){
			final PreparedStatement STATEMENT = CON.prepareStatement("UPDATE PRODUCTO SET NOMBRE = ?, DESCRIPCION = ?, CANTIDAD = ? WHERE ID = ?");
			try(STATEMENT){
			STATEMENT.setString(1, nombre); 
			STATEMENT.setString(2, descripcion); 
			STATEMENT.setInt(3, cantidad); 
			STATEMENT.setInt(4, id);
			STATEMENT.execute();
			int updateCount = STATEMENT.getUpdateCount();
			
			return updateCount;
			}
			
			
		}
	}

	public int eliminar(Integer id) throws SQLException {
		final Connection CON = new ConnectionFactory().recuperaConexion();
		try(CON){
			PreparedStatement STATEMENT = CON.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
			try(STATEMENT){
				STATEMENT.setInt(1, id);
				STATEMENT.execute();
			}
			
			return STATEMENT.getUpdateCount(); //how many rows where afected by this query // cuantas filas fueron afectadas con este query
		}
	}

	public List<Map<String, String>> listar() throws SQLException {
		final Connection CON = new ConnectionFactory().recuperaConexion();
		try(CON){
			final PreparedStatement STATEMENT = CON.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
			try(STATEMENT){
				STATEMENT.execute();
				
				ResultSet resultSet = STATEMENT.getResultSet();
				
				List<Map<String, String>> resultado = new ArrayList<>();
				
				while(resultSet.next()) {
					Map<String,String> fila = new HashMap<>();
					fila.put("ID", String.valueOf(resultSet.getInt("ID")));
					fila.put("NOMBRE", resultSet.getString("NOMBRE"));
					fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
					fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));
					
					resultado.add(fila);
				}
				return resultado;
			}
				
		}
	}

	/*
	 * This method divides the insertions into groups of up to 50 products. 
	 * Este metodo divide las inserciones en grupos de maximo 50 productos 
	 * */
    public void guardar(Map<String,String> producto) throws SQLException {
		
		String nombre = producto.get("NOMBRE");
		Integer cantidad= Integer.valueOf(producto.get("CANTIDAD"));
		String descripcion = producto.get("DESCRIPCION");
		Integer maximoCantidad = 50;
		
		final Connection CON = new ConnectionFactory().recuperaConexion();
		//try with resources
		try(CON){
			CON.setAutoCommit(false);
			
			final PreparedStatement STATEMENT = CON.prepareStatement("INSERT INTO PRODUCTO (nombre, descripcion, cantidad) " + " VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
			try(STATEMENT){	
				do {
					int cantidadParaGuardar = Math.min(cantidad, maximoCantidad);
					ejecutaRegistro(nombre, cantidadParaGuardar, descripcion, STATEMENT);
						cantidad -= maximoCantidad;
				}while(cantidad > 0);
				//It will only commit if all the products are registered succesfully
				//solo hara el comit si todos los productos hgan sido guardados satisfactoriamante.
				CON.commit();
				}catch(Exception e) {
					//si existe algun error en la transaccion se hace un rollback de la transaccion
					//if there is any error in the transaction it makes a rollback of the transaction.
					CON.rollback();
			}
		}
    }

	private void ejecutaRegistro(String nombre, Integer cantidad, String descripcion, PreparedStatement statement)
			throws SQLException {
		statement.setString(1, nombre); 
		statement.setString(2, descripcion); 
		statement.setInt(3, cantidad); 
		statement.execute();
		
		final ResultSet RESULTSET = statement.getGeneratedKeys(); // Return the generated key by this query//retorna La key generada con este query
		
		//try with resources
		try(RESULTSET){
		
		while(RESULTSET.next()) {
			System.out.println(String.format("Fue insertado el producto de ID %d", RESULTSET.getInt(1)));
		}
		}
	}

}
