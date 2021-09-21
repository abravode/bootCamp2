package org.springframework.samples.petclinic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		PreparedStatement preparedStatement = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic","root", "root");
			if (connection != null) {
				System.out.println("Conexión establecida");
				
				statement = connection.createStatement();
				
				//Reto JDBC
				
				/*
				 * Crear un objeto de la clase Owner, rellenar tus datos personales 
				 * (o de tu compañero), y mediante JDBC parametrizado insertarte a ti 
				 * mismo como un propietario de mascotas. 
				 * Luego, si tienes mascota en casa, asígnate una mediante la clase Pet 
				 * y JDBC parametrizado, y por último, borra tus datos.
				 */			
				/*
				 * Parte 1
				 * Inserción de mis datos en la Base de Datos
				 */
				String sqlowner = "INSERT INTO owners VALUES (null, ?, ?, ?, ?, ?)";
				String values [] = {"Javier","Benavides","Calle falsa", "Leon", "987654321"};
				preparedStatement = connection.prepareStatement(sqlowner);
				preparedStatement.setString(1, values[0]);
				preparedStatement.setString(2, values[1]);
				preparedStatement.setString(3, values[2]);
				preparedStatement.setString(4, values[3]);
				preparedStatement.setString(5, values[4]);
				
				int filaowner = preparedStatement.executeUpdate();
				System.out.println("\nFilas insertadas parte1: "+filaowner);
				
				/*
				 * Parte 2
				 * Inserción de mi Mascota en la Base de Datos
				 */
				String sqlidowner = "SELECT id FROM owners WHERE first_name = 'Javier'";
				ResultSet rsidowner = statement.executeQuery(sqlidowner);
				String idowner=null;
				while (rsidowner.next()) {
					idowner= String.valueOf(rsidowner.getInt("id"));
				}
				rsidowner.close();
				
				String sqlidtype = "SELECT id FROM types WHERE name = 'dog'";
				ResultSet rsidtype = statement.executeQuery(sqlidtype);
				String idtype = null;
				while (rsidtype.next()) {
					idtype= String.valueOf(rsidtype.getInt("id"));
				}
				rsidtype.close();

				String sqlpet = "INSERT INTO pets VALUES (null, ?, ?, ?, ?)";
				preparedStatement = connection.prepareStatement(sqlpet);
				
				String valuespet [] = {"Bilbo","2012-01-27", idtype , idowner};
				preparedStatement = connection.prepareStatement(sqlpet);
				preparedStatement.setString(1, valuespet[0]);
				preparedStatement.setString(2, valuespet[1]);
				preparedStatement.setString(3, valuespet[2]);
				preparedStatement.setString(4, valuespet[3]);
				
				int filapet = preparedStatement.executeUpdate();
				
				String sqlSelectMascota = "SELECT * FROM pets";
				ResultSet rsMascota = statement.executeQuery(sqlSelectMascota);
				
				System.out.println("Filas insertadas: "+filapet);
				
				/*
				 * Parte 3
				 * Eliminacion de mis datos de la Base de Datos
				 */
				
				String sqlEliminacionMascota= "Delete pets FROM pets INNER JOIN owners ON pets.owner_id = owners.id WHERE pets.name='Bilbo' ";
				preparedStatement = connection.prepareStatement(sqlEliminacionMascota);
				int filasEliminadasMascota = preparedStatement.executeUpdate();
				
				String sqlEliminacion= "DELETE FROM owners WHERE first_name = 'Javier'";
				preparedStatement = connection.prepareStatement(sqlEliminacion);
				int filasEliminadas = preparedStatement.executeUpdate();
				
				System.out.println("Numero de datos eliminados: "+filasEliminadas);
				
				System.out.println("Numero de datos eliminados mascotas: "+filasEliminadasMascota);
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

}