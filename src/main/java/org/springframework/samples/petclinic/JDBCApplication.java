package org.springframework.samples.petclinic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.swing.JOptionPane;

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
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic","root", "1234");
			if (connection != null)
				System.out.println("Conexión establecida");
				
			Owner yo = new Owner();
			yo.setAddress("Calle Falsa 123");
			yo.setCity("Springfield");
			yo.setFirstName("David");
			yo.setLastName("Serrano Blazquez");
			yo.setTelephone("691604790");
							
			String sql = "INSERT INTO owners (first_name, last_name, address, city, telephone) "
					+ "VALUES (?,?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, yo.getFirstName());
			preparedStatement.setString(2, yo.getLastName());				
			preparedStatement.setString(3, yo.getAddress());				
			preparedStatement.setString(4, yo.getCity());				
			preparedStatement.setString(5, yo.getTelephone());				
			int numeroDeFilasModificadas = preparedStatement.executeUpdate();
			/*Comprobacion del nuevo listado*/
			String sql2 = "SELECT * FROM owners";
				statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql2);
				while(rs.next()) {
					int id = rs.getInt("id");
					String first_name = rs.getString("first_name");
					String last_name = rs.getString("last_name");
					String address = rs.getString("address");
					String city = rs.getString("city");
					String telephone = rs.getString("telephone");
					System.out.println("\nID: "+ id + "\nNombre: "+first_name +
							"\nApellidos: " + last_name + "\nDireccion: " + address + 
							"\nCiudad: " + city + "\nNumero de telefono: " + telephone);
					
				}
				/*Le pasamos el valor del campo en específico. También se podría haber hecho parametrizado como antes*/		
				String sql3 = "DELETE FROM owners WHERE telephone = 691604790";
				int numeroDeFilasBorradas = statement.executeUpdate(sql3);
				/*Comprobamos que se borra el registro*/
				rs = statement.executeQuery(sql2);
				while(rs.next()) {
					int id = rs.getInt("id");
					String first_name = rs.getString("first_name");
					String last_name = rs.getString("last_name");
					String address = rs.getString("address");
					String city = rs.getString("city");
					String telephone = rs.getString("telephone");
					System.out.println("\nID: "+ id + "\nNombre: "+first_name +
							"\nApellidos: " + last_name + "\nDireccion: " + address + 
							"\nCiudad: " + city + "\nNumero de telefono: " + telephone);
					
				}
				rs.close();
		    
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
