package org.springframework.samples.petclinic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.Pet;

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
			String mysqlPwd = "1234";
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic","root", mysqlPwd);
			if (connection != null) {
				System.out.println("Conexión establecida");
			
				
				/*
				 * SergioSanchez
				 * Aqui comienzan los ejercicios realizados en la sesión la sesion
				 * El reto 1 de la sesión se encuentra en la clase RetoSesion2
				 * 
				 */
			
				//Variables
				statement = connection.createStatement();
				ResultSet resultSet = null;
				PreparedStatement preparedStatement;
				
				
				/*
				 * INSERT en la base de datos
				 */
				String sql = "INSERT INTO owners (first_name, last_name, address, city, telephone) "
				+ 
				"VALUES ('Marcos', 'Ginel', 'Mi dirección', 'Mi ciudad', '666666666')";
				int numeroDeFilasInsertadas = statement.executeUpdate(sql);
				System.out.println("Filas inertadas: " + numeroDeFilasInsertadas);
				
				
				/*
				 * UPDATE en la base de datos
				 */
				String sql2 = "UPDATE owners "
				           + "SET city = 'Sevilla'"
				           + "WHERE first_name = 'Marcos'";
				int numeroDeFilasModificadas = statement.executeUpdate(sql2);
				System.out.println("Filas modificadas: " + numeroDeFilasModificadas);
				
				
				/*
				 * SELECT datos de tabla
				 */
				String sql3 = "SELECT * FROM OWNERS";
				resultSet = statement.executeQuery(sql3);
				System.out.println("Mostrando datos de la tabla OWNERS");
				while(resultSet.next()){
		            //Display values
		            System.out.print("First name: " + resultSet.getString("first_name"));
		            System.out.print(", Last name: " + resultSet.getString("last_name"));
		            System.out.print(", address: " + resultSet.getString("address"));
		            System.out.print(", city: " + resultSet.getString("city"));
		            System.out.println(", telephone: " + resultSet.getString("telephone"));
		         }
				
				
				/*
				 * SELECT con PreparedStatement
				 */
				String sql4 = "SELECT * FROM owners WHERE first_name LIKE ? OR last_name LIKE ?";
				String busqueda = "Da";
				String termino = "%"+busqueda+"%";
				preparedStatement = connection.prepareStatement(sql4);
				preparedStatement.setString(1, termino);
				preparedStatement.setString(2, termino);
				ResultSet rs = preparedStatement.executeQuery();
				System.out.println("Mostrando usuarios con nombres o apellidos que contienne *Da*");
				while(rs.next()){
				         int id = rs.getInt("id");
				         String firstName = rs.getString("first_name");
				         String lastName = rs.getString("last_name");
				         System.out.println("Id: " + id + ", Nombre: " + firstName + ", Apellidos: " + lastName);
				}
				rs.close();
				
				
				/*
				 * INSERT con PreparedStatement
				 */
				String [] valores;
				valores = new String[] {"Jose", "Antonio", "Casa", "Jerez", "666666"};
				String sql5 = "INSERT INTO owners (first_name, last_name, address, city, telephone)"
						+ "VALUES(?,?,?,?,?)";
				preparedStatement = connection.prepareStatement(sql5);
				for(int i=0; i< valores.length; i++) {
					preparedStatement.setString(i+1, valores[i]);
				}
				int numeroDeFilasInsertadas2 = preparedStatement.executeUpdate();
				System.out.println("Filas insertadas: " + numeroDeFilasInsertadas2);
				
				
				/*
				 * El reto 1 de la sesión se encuentra en la clase RetoSesion2
				 */
				
				//Cerrar variables
				statement.close();
				if (resultSet != null)
					resultSet.close();
			
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
				System.out.println("Excepcion SQLException: " + se.toString());
				se.printStackTrace();
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
