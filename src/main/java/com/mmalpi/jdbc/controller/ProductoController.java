package com.mmalpi.jdbc.controller;

import java.sql.SQLException;
import java.util.List;

import com.mmalpi.jdbc.dao.ProductoDAO;
import com.mmalpi.jdbc.factory.ConnectionFactory;
import com.mmalpi.jdbc.modelo.Producto;

public class ProductoController {

	private ProductoDAO productoDAO;
	public ProductoController() {
		var factory = new ConnectionFactory();
		this.productoDAO = new ProductoDAO(factory.recuperaConexion());
	}

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
		return productoDAO.modificar(nombre, descripcion, cantidad, id);
	}

	public int eliminar(Integer id) throws SQLException {
		return productoDAO.eliminar(id);
		
	}

	public List<Producto> listar() {
		return productoDAO.listar();		
	}


    public void guardar(Producto producto) {
		
		productoDAO.guardar(producto);
    }

	

}
