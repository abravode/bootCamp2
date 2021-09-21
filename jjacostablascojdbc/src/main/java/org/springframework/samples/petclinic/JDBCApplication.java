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
		PreparedStatement preparedStatement = null;
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic","root", "sasa");
			if (connection != null)
				System.out.println("Conexión establecida");
				statement = connection.createStatement();
				String sqlSelect = "SELECT * FROM owners";
				String sqlInsert = "INSERT INTO owners (first_name, last_name, address, city, telephone) "
				+
				"VALUES ('Marcos', 'Ginel', 'Mi dirección', 'Mi ciudad', '666666666')";
				
				int numeroDeFilasModificadas = statement.executeUpdate(sqlInsert);				
				System.out.println("\nInsercion con Statement...");

				ResultSet rs = statement.executeQuery(sqlSelect);
				System.out.println("\nSelect con Statement...");
				while(rs.next()) {
					int id = rs.getInt("id");
					String nombre = rs.getString("first_name");
					System.out.println("id: " + id + "\n" + "Nombre: "+ nombre);
				}
				
				String sql = "SELECT * FROM owners WHERE first_name LIKE ? OR last_name LIKE ?";

				String busqueda = "Da";

				String termino = "%"+busqueda+"%";

				preparedStatement = connection.prepareStatement(sql);

				preparedStatement.setString(1, termino);

				preparedStatement.setString(2, termino);
				
				System.out.println("\nSelect con PreparedStatement...");

				ResultSet rsps = preparedStatement.executeQuery();

				while(rsps.next()){

				         int id = rsps.getInt("id");

				         String firstName = rsps.getString("first_name");

				         String lastName = rsps.getString("last_name");



				         System.out.print("Id: " + id);

				         System.out.print(", Nombre: " + firstName);

				         System.out.println(", Apellidos: " + lastName);
				         

				}

				rsps.close();
				
				/*¡Este reto es bien sencillo!
				Crear un objeto de la clase Owner, rellenar tus datos personales (o de tu compañero), y mediante JDBC parametrizado insertarte a ti mismo como un propietario de mascotas. Luego, si tienes mascota en casa, asígnate una mediante la clase Pet y JDBC parametrizado, y por último, borra tus datos.
				¡Suerte!*/
				String sqlpsInsertOwner = "INSERT INTO owners (first_name, last_name, address, city, telephone) VALUES (?, ?, ? ,? ,?)";
				preparedStatement = connection.prepareStatement(sqlpsInsertOwner, Statement.RETURN_GENERATED_KEYS);
				Owner owner = new Owner();
				int idOwner = -1;
				
				owner.setFirstName("José Javier");
				owner.setLastName("Acosta Blasco");
				owner.setAddress("C/Canarias Nº2");
				owner.setCity("Badajoz");
				owner.setTelephone("655167884");
				
				preparedStatement.setString(1, owner.getFirstName());
				preparedStatement.setString(2, owner.getLastName());
				preparedStatement.setString(3, owner.getAddress());
				preparedStatement.setString(4, owner.getCity());
				preparedStatement.setString(5, owner.getTelephone());
				
				System.out.println("\nINSERT del Owner con prepared statement");
				preparedStatement.executeUpdate();
				
				ResultSet rspsOwner = preparedStatement.getGeneratedKeys();
				if (rspsOwner.next()) {
				    idOwner = rspsOwner.getInt(1);
				    System.out.println("Inserted ID Owner - " + idOwner); 
				}
				 
			
				String sqlpsInsertPet = "INSERT INTO pets (name, birth_date, type_id, owner_id) VALUES (?, ?, ? ,?)";
				preparedStatement = connection.prepareStatement(sqlpsInsertPet, Statement.RETURN_GENERATED_KEYS);
				
				Pet pet = new Pet();
				int idPet = -1;
				pet.setName("Nora");
				
				LocalDate localDate = LocalDate.parse("2005-10-30");
				pet.setBirthDate(localDate);
				
				PetType petType = new PetType();
				petType.setName("dog");
				petType.setId(2);
				pet.setType(petType);
				
				preparedStatement.setString(1, pet.getName());
				preparedStatement.setString(2, pet.getBirthDate().toString());
				preparedStatement.setInt(3, pet.getType().getId());
				// No se le puede hacer un set a pet del owner ya que este esta protected
				preparedStatement.setInt(4, idOwner);
				System.out.println("INSERT del Pet con prepared statement");
				preparedStatement.executeUpdate();
				
				ResultSet rspsPet = preparedStatement.getGeneratedKeys();
				if (rspsPet.next()) {
				    idPet = rspsPet.getInt(1);
				    System.out.println("Inserted ID Pet - " + idPet + "\n"); 
				}
				
				if(idOwner != -1 && idPet != -1) {
					
					String deletePet = "DELETE FROM pets where id = ?";
					preparedStatement = connection.prepareStatement(deletePet);
					preparedStatement.setInt(1, idPet);
					preparedStatement.executeUpdate();
					System.out.println("DELETED pet - " + idPet);
					
					String deleteOwner = "DELETE FROM owners where id = ?";
					preparedStatement = connection.prepareStatement(deleteOwner);
					preparedStatement.setInt(1, idOwner);
					preparedStatement.executeUpdate();
					System.out.println("DELETED owner - " + idOwner);
					
					

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
