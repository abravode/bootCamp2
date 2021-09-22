package org.springframework.samples.petclinic;


import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.samples.petclinic.owner.Owner;

import java.sql.PreparedStatement;

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
			if (connection != null)
				System.out.println("Conexión establecida");
			
			statement = connection.createStatement();
			String sql = "INSERT INTO owners (first_name, last_name, address, city, telephone) "
			+ 
			"VALUES ('Marcos', 'Ginel', 'Mi dirección', 'Mi ciudad', '666666666')";
			int numeroDeFilasModificadas = statement.executeUpdate(sql);
			
			statement = connection.createStatement();
			sql = "UPDATE owners "
			           + "SET city = 'Sevilla'"
			           + "WHERE first_name = 'Marcos'";
			numeroDeFilasModificadas = statement.executeUpdate(sql);
			System.out.println("Número de filas modidicadas: "+ numeroDeFilasModificadas);
			
			
			sql = "SELECT * FROM owners";
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				int id = rs.getInt("Id");
				String firstName = rs.getString("first_name");
				
				System.out.print("Id: "+ id);
				System.out.println(", Nombre: "+ firstName);
				
			}
			
			//PreparedStatement
			sql = "SELECT * FROM owners WHERE first_name LIKE ? OR last_name LIKE ?";
			String busqueda = "Da";
			String termino = "%"+busqueda+"%";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, busqueda);
			preparedStatement.setString(2, termino);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
		         String firstName = rs.getString("first_name");
		         String lastName = rs.getString("last_name");
		         System.out.print("Id: " + id);
		         System.out.print(", Nombre: " + firstName);
		         System.out.println(", Apellidos: " + lastName);
			}
			
			rs.close();
			
			//Reto
			Owner ow = new Owner();
			ow.setFirstName("Edu");
			ow.setLastName("Bartel");
			ow.setAddress("Mi dirección");
			ow.setCity("Mi ciudad");
			ow.setTelephone("677777777");
			statement = connection.createStatement();
			sql = "INSERT INTO owners (first_name, last_name, address, city, telephone) "
			+ 
			"VALUES ('"+ ow.getFirstName() +"', '"+ ow.getLastName() +"', '"+ ow.getAddress() +"', '"+ ow.getCity() +"', '"+ ow.getCity() +"')";
			numeroDeFilasModificadas = statement.executeUpdate(sql);
			
			//Comprobamos que se ha insertado correctamente
			sql = "SELECT * FROM owners";
			rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				int id = rs.getInt("Id");
				String firstName = rs.getString("first_name");
				
				System.out.print("Id: "+ id);
				System.out.println(", Nombre: "+ firstName);
				
			}
			
			rs.close();
			
			//Borramos los datos
			sql = "DELETE FROM owners WHERE first_name = 'Edu'";
					numeroDeFilasModificadas = statement.executeUpdate(sql);
			
		    
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

}
