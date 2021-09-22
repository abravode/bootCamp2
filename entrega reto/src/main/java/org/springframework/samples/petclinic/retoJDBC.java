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

public class retoJDBC {
	public static void main(String[] args) {
		System.out.println("-------- Test de conexión con MySQL ------------");

		try {
			Class.forName("com.mysql.jdbc.Driver"); //Nombre del driver
		} catch (ClassNotFoundException e) {
			System.out.println("No encuentro el driver en el Classpath");
			e.printStackTrace();
			return;
		}

		System.out.println("Driver instalado y funcionando");
		Connection connection = null;
		Statement statement = null;
		try {
			//Conexion a la base de datos
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic","root", "root");
			if (connection != null) {
				System.out.println("Conexión establecida");
				
				//Codificar aqui los accesos a la DB
				statement = connection.createStatement();
				
				Owner o = new Owner();
				o.setAddress("Dirección");
				o.setCity("Salamanca");
				o.setFirstName("David");
				o.setLastName("Alonso");
				o.setTelephone("666111222");			
				
				String sql = "INSERT INTO owners (first_name, last_name, address, city, telephone)" + "VALUES(?,?,?,?,?)";
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, o.getFirstName());
				ps.setString(2, o.getLastName());
				ps.setString(3, o.getAddress());
				ps.setString(4, o.getCity());
				ps.setString(5, o.getTelephone());
				
				System.out.println("Filas insertadas: "+ ps.executeUpdate());
				
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next()) {
					System.out.println("Usuario "+o.getFirstName()+" creado");
					o.setId(rs.getInt(1));
				}else {
					System.out.println("ERROR, usuario no creado!");
				}
				rs.close();
				
				
				
				Pet p = new Pet();
				p.setName("Thayson");
				p.setBirthDate(LocalDate.of(2011, 3, 5));
				p.setType(new PetType());
				
				o.addPet(p);
				
				String sql2 = "INSERT INTO pets (name, birth_date, type_id, owner_id)" + "VALUES(?,?,?,?)";
				ps = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, p.getName());
				ps.setObject(2, p.getBirthDate());
				ps.setInt(3,2);
				ps.setInt(4, p.getOwner().getId());
				
				System.out.println("Filas insertadas: "+ ps.executeUpdate());
				
				ResultSet rs2 = ps.getGeneratedKeys();
				if(rs2.next()) {
					System.out.println("Mascota "+p.getName()+ " creada");
					p.setId(rs2.getInt(1));
				}else {
					System.out.println("ERROR, mascota no creada!");
				}
				rs2.close();
				
				
				String sql3 = "DELETE FROM pets WHERE id = ?";
				ps = connection.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, p.getId());
				System.out.println("Filas eliminadas: "+ ps.executeUpdate());
				System.out.println("Mascota "+p.getName()+" eliminada");
				
				String sql4 = "DELETE FROM owners WHERE id = ?";
				ps = connection.prepareStatement(sql4, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, o.getId());
				System.out.println("Filas eliminadas: "+ ps.executeUpdate());
				System.out.println("Usuario "+o.getFirstName()+" eliminado");
				
				statement.close();
			}
				
			
		    
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		} finally {
			//Cierre de conexiones
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
