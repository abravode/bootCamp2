package org.springframework.samples.petclinic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetType;

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
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic","root", "OLAk@se5");
			if (connection != null)
				System.out.println("Conexión establecida");
				
			statement = connection.createStatement();
			
			// Se crea el propietario
			Owner usuarioYo = new Owner();
			usuarioYo.setFirstName("David");
			usuarioYo.setLastName("Fabián");
			usuarioYo.setAddress("calle falsa 1");
			usuarioYo.setCity("Salamanca");
			usuarioYo.setTelephone("612345678");
			
			// Se crea y ejecuta la sentencia sql para insertar los datos del user nuevo
			String sqlRetoInsert = "INSERT INTO owners (first_name, last_name, address, city, telephone) "
					+
					"VALUES (?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStatementRetoInsert = connection.prepareStatement(sqlRetoInsert);
			preparedStatementRetoInsert.setString(1, usuarioYo.getFirstName());
			preparedStatementRetoInsert.setString(2, usuarioYo.getLastName());
			preparedStatementRetoInsert.setString(3, usuarioYo.getAddress());
			preparedStatementRetoInsert.setString(4, usuarioYo.getCity());
			preparedStatementRetoInsert.setString(5, usuarioYo.getTelephone());
			int filasInsert = preparedStatementRetoInsert.executeUpdate();
			System.out.println("Filas añadidas: "+filasInsert);
			
			// Muestra por pantalla la tabla
			String sqlConsultaInsert = "SELECT * FROM owners";
			ResultSet rsInsert = statement.executeQuery(sqlConsultaInsert);
			while(rsInsert.next()) {
				int id = rsInsert.getInt("id");
				String firstName = rsInsert.getString("first_name");
				String tlfn = rsInsert.getString("telephone");
				System.out.println("ID: "+id+", Nombre: "+firstName+", Telefono: "+tlfn+", Id: "+id);
			}
			rsInsert.close();
			

			// Se crea y ejecuta la sentencia sql para eliminar el nuevo user que habíamos insertado
			System.out.println("Tras borrar, la tabla queda asi:");
			String sqlRetoDelete = "DELETE FROM owners WHERE first_name=? and telephone=?";
			PreparedStatement preparedStatementRetoDelete = connection.prepareStatement(sqlRetoDelete);
			preparedStatementRetoDelete.setString(1, usuarioYo.getFirstName());
			preparedStatementRetoDelete.setString(2, usuarioYo.getTelephone());
			int filasDelete = preparedStatementRetoDelete.executeUpdate();
			System.out.println("Filas eliminadas: "+filasDelete);
			System.out.println("Tras borrar, la tabla queda asi:");
			
			// Se muestra por pantalla el listado
			sqlConsultaInsert = "SELECT * FROM owners";
			rsInsert = statement.executeQuery(sqlConsultaInsert);
			while(rsInsert.next()) {
				int id = rsInsert.getInt("id");
				String firstName = rsInsert.getString("first_name");
				System.out.println("ID: "+id+", Nombre: "+firstName);
			}
			rsInsert.close();
			
			
			
			
			
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
