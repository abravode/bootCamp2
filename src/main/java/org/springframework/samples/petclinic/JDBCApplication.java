package org.springframework.samples.petclinic;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.samples.petclinic.owner.Owner;

public class JDBCApplication {

	public static void main(String[] args) {
		System.out.println("-------- Test de conexión con MySQL ------------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("No encuentro el driver en el Classpath");
			e.printStackTrace();
			return;
		}

		System.out.println("Driver instalado y funcionando");
		Connection connection = null;
		Statement statement = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic","root", "root");
			if (connection != null) {
				System.out.println("Conexión establecida");
				
				Owner yo = new Owner();//Instanciación de la clase Owner
				
				//Damos valores al objeto owner mediante los seters
				yo.setFirstName("Alex");
				yo.setLastName("Fuentes");
				yo.setAddress("C/ calle 10");
				yo.setCity("Salamanca");
				yo.setTelephone("658255425");
				
				//Declaramos el insert de los datos 
				String sql_add_owner="INSERT INTO owners (first_name, last_name, address, city, telephone) VALUES (?,?,?,?,?)";
				PreparedStatement prepareStatement_insert = connection.prepareStatement(sql_add_owner);
				
				
				prepareStatement_insert.setString(1, yo.getFirstName());
				prepareStatement_insert.setString(2, yo.getLastName());
				prepareStatement_insert.setString(3, yo.getAddress());
				prepareStatement_insert.setString(4, yo.getCity());
				prepareStatement_insert.setString(5, yo.getTelephone());
				
				prepareStatement_insert.executeUpdate();//Insertamos en la base de datos el nuevo owner
				
				
				String sql_select_all_owners="Select * from owners";// Creamos query para solicitar todos los owners
				PreparedStatement prepareStatement_select = connection.prepareStatement(sql_select_all_owners);
				
				ResultSet rs = prepareStatement_select.executeQuery();
				
				System.out.println("\n***************************");
				System.out.println("Añadido nuevo owner " + "(" + yo.getFirstName() + ")");
				System.out.println("***************************");
				
				while (rs.next()) {
					
					imprimir(rs);
					if(rs.isLast()) {
						yo.setId(rs.getInt("Id"));
					}
				}
				
				String sql_delete_owner="DELETE FROM owners WHERE id = ?;";
				PreparedStatement prepareStatement_delete = connection.prepareStatement(sql_delete_owner);
				
				prepareStatement_delete.setInt(1, yo.getId());
				
				prepareStatement_delete.executeUpdate();//Eliminamos de la base de datos el nuevo owner
				
				rs = prepareStatement_select.executeQuery();
				
				System.out.println("\n***************************");
				System.out.println("Eliminado owner añadido " + "(" + yo.getFirstName() + ")");
				System.out.println("***************************");
				
				while (rs.next()) {
					imprimir(rs);
				}
				
				rs.close();
			}
			
			
			
		    
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		} finally {
			try {
				if(statement != null)
					connection.close();
			} catch (SQLException se) {
		    	  
		    }
		    try {
		        if(connection != null)
		            connection.close();
		    } catch (SQLException se) {
		         	se.printStackTrace();
		    }
		}
	}
	
	public static void imprimir (ResultSet rs) {
		
		try {
			System.out.println("Id:" + rs.getInt("Id") +"\t| Nombre:"+ rs.getString("first_name") +"\t| Apellido:"+ rs.getString("last_name")
			+"\t| direccion:"+ rs.getString("address") +"\t| telefono:"+ rs.getString("telephone"));
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

	}

}
