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

/*
 * SergioSanchez
 * Reto 1 de la sesion2
 * Los ejercicios realizados en la sesion se encuentran en la clase JDBCApplication
 * 
 * Enunciado reto: Crear un objeto de la clase Owner, rellenar tus datos personales (o de tu compañero), y mediante JDBC parametrizado insertarte a ti 
 * mismo como un propietario de mascotas. Luego, si tienes mascota en casa, asígnate una mediante la clase Pet y JDBC parametrizado, y por último, borra tus datos.
 */
public class Reto1Sesion2 {

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
				 * Reto 1 de la sesion2
				 * Los ejercicios realizados en la sesion se encuentran en la clase JDBCApplication
				 * 
				 * Enunciado reto: Crear un objeto de la clase Owner, rellenar tus datos personales (o de tu compañero), y mediante JDBC parametrizado insertarte a ti 
				 * mismo como un propietario de mascotas. Luego, si tienes mascota en casa, asígnate una mediante la clase Pet y JDBC parametrizado, y por último, borra tus datos.
				 */
			
				//Variables
				statement = connection.createStatement();
				PreparedStatement preparedStatement;
				
				/*
				 * Crear un objeto de la clase Owner con los datos personales
				 */
				Owner me = new Owner();
				me.setFirstName("Sergio");
				me.setLastName("Sanchez");
				me.setAddress("Direccion personal");
				me.setCity("Salamanca");
				me.setTelephone("666666666");
				
				
				/*
				 * INSERT con PreparedStatement del Owner
				 */
				String sql1 = "INSERT INTO owners (first_name, last_name, address, city, telephone)"
						+ "VALUES(?,?,?,?,?)";
				preparedStatement = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, me.getFirstName());
				preparedStatement.setString(2, me.getLastName());
				preparedStatement.setString(3, me.getAddress());
				preparedStatement.setString(4, me.getCity());
				preparedStatement.setString(5, me.getTelephone());
				int numeroDeFilasInsertadas1 = preparedStatement.executeUpdate();
				System.out.println("Filas insertadas: " + numeroDeFilasInsertadas1);
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	int id=  generatedKeys.getInt(1);
		                System.out.println("Id asignado al usuario: " + id);
		                me.setId(id);
		            }
		            else 
		            	throw new SQLException("Creating user failed, no ID obtained.");
		        }
				
				
				/*
				 * Mostramos que el Owner se ha añadido correctamente (luego lo borraremos)
				 */
				String sql2 = "SELECT * FROM owners WHERE id = ?";
				preparedStatement = connection.prepareStatement(sql2);
				preparedStatement.setInt(1, me.getId());
				ResultSet rs = preparedStatement.executeQuery();
				System.out.println("Mostrando mi usuario en la base de datos");
				while(rs.next()){
				         int id = rs.getInt("id");
				         String firstName = rs.getString("first_name");
				         String lastName = rs.getString("last_name");
				         System.out.print("Id: " + id + ", Nombre: " + firstName + ", Apellidos: " + lastName);
				}
				rs.close();
				
				
				/*
				 * Crear un objeto de la clase Pet con los datos de la mascota
				*/
				Pet miMascota = new Pet();
				miMascota.setName("Toby");
				miMascota.setBirthDate(LocalDate.of(1999, 12, 26));
				miMascota.setType(new PetType());
				//Al objeto de la clase Owner le añadimos su mascota
				me.addPet(miMascota); //Para posteriormente obtener el id del dueño
				
				
				/*
				 * INSERT con PreparedStatement de la mascota
				 */
				String sql3 = "INSERT INTO pets (name, birth_date, type_id, owner_id)"
						+ "VALUES(?,?,?,?)";
				preparedStatement = connection.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, miMascota.getName());
				preparedStatement.setObject(2, miMascota.getBirthDate());
				preparedStatement.setInt(3, 1); //La clase PetType no está terminada -> miMascota.getType()
				preparedStatement.setInt(4, miMascota.getOwner().getId());
				int numeroDeFilasInsertadas2 = preparedStatement.executeUpdate();
				System.out.println("Filas insertadas: " + numeroDeFilasInsertadas2);
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	int id=generatedKeys.getInt(1);
		                System.out.println("Id asignado a la mascota: " + id);
		                miMascota.setId(id);
		            }
		            else 
		            	throw new SQLException("Creating user failed, no ID obtained.");
		        }
				
				
				/*
				 * Mostramos que la mascota se ha añadido correctamente
				 */
				String sql4 = "SELECT * FROM pets WHERE id = ?";
				preparedStatement = connection.prepareStatement(sql4);
				preparedStatement.setInt(1, miMascota.getId());
				ResultSet rs2 = preparedStatement.executeQuery();
				System.out.println("Mostrando mi mascota en la base de datos");
				while(rs2.next()){
				         int id = rs2.getInt("id");
				         String name = rs2.getString("name");
				         LocalDate birthDate = rs2.getObject ( "birth_date" , LocalDate.class ); 
				         int typeId = rs2.getInt("type_id");
				         int ownerId = rs2.getInt("owner_id");
				         System.out.println("Id: " + id + ", Nombre: " + name + ", Fec nacim: " + birthDate.toString() + ", tipo: " + typeId + ", id dueno: " + ownerId);
				}
				rs2.close();
				
				
				/*
				 * Borramos la mascota primero
				 */
				String sql5 = "DELETE FROM pets WHERE id = ?";
				preparedStatement = connection.prepareStatement(sql5);
				preparedStatement.setInt(1, miMascota.getId());
				int numeroDeFilasEliminadas1 = preparedStatement.executeUpdate();
				System.out.println("Mascota eliminado: " + numeroDeFilasEliminadas1);
				
				
				/*
				 * Borramos el owner despues
				 */
				String sql6 = "DELETE FROM owners WHERE id = ?";
				preparedStatement = connection.prepareStatement(sql6);
				preparedStatement.setInt(1, me.getId());
				int numeroDeFilasEliminadas2 = preparedStatement.executeUpdate();
				System.out.println("Usuario eliminado: " + numeroDeFilasEliminadas2);
				

				//Cerrar variables
				statement.close();
			
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
