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
		
		//Crear un objeto de la clase Owner, rellenar tus datos personales
		Owner owner = new Owner();
		owner.setFirstName("Carmen");
		owner.setLastName("Alonso");
		owner.setCity("Salamanca");
		owner.setAddress("Calle");
		owner.setId(11);
		owner.setTelephone("666000999");
		
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
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic","root", "rootpass01");
			if (connection != null) {
				System.out.println("Conexión establecida");
				
				statement = connection.createStatement();
				
				
				//mediante JDBC parametrizado insertarte a ti mismo como un propietario de mascotas
				String sqlC = "INSERT INTO owners (first_name, last_name, address, city, telephone) " + "VALUES ('"+owner.getFirstName()+"', '"+owner.getLastName()+"', '"+owner.getAddress()+"', '"+owner.getCity()+"', '"+owner.getTelephone()+"')";
				int numeroDeFilasAnnadidas = statement.executeUpdate(sqlC);
				System.out.print("filas modificadas: " + numeroDeFilasAnnadidas);
				System.out.println();
								
				
				//statement = connection.createStatement();
				String sql = "SELECT * from owners";
				ResultSet rs = statement.executeQuery(sql);
				
				while(rs.next()) {
					int id = rs.getInt("id");
					String firstName = rs.getString("first_name");
					
					System.out.print("Id: " + id);
					System.out.print("first name: " + firstName);
					System.out.println();
					
				}
				
				//borra tus datos
				String sqlD = "DELETE FROM owners WHERE first_name = 'Carmen'";
				int numeroDeFilasBorradas = statement.executeUpdate(sqlD);
				System.out.print("filas modificadas: " + numeroDeFilasBorradas);
				System.out.println();
				
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
