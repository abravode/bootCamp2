
package org.springframework.samples.petclinic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.samples.petclinic.owner.Owner;

public class JDBCApplication {

	public static Owner owner = new Owner();
	
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
		//almacenamos los datos
		owner.setFirstName("Josue");
		owner.setLastName("Velarde");
		owner.setAddress("calle San Antonio Maria Claret");
		owner.setCity("Don Benito");
		owner.setTelephone("658187271");
		//-----------------------------
		//almacenamos en las variables correspondientes los datos del objeto owner
		String first_name = owner.getFirstName();
		String last_name = owner.getLastName();
		String address = owner.getAddress();
		String city = owner.getCity();
		String telephone = owner.getTelephone();
		//-----------------------------------
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic","root", "1234");
			if (connection != null) {
				System.out.println("Conexión establecida");
				//consulta para insertar datos con parametros
				String sql = "Insert into owners (first_name, last_name, address, city, telephone) values (?,?,?,?,?)";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, first_name);
				preparedStatement.setString(2, last_name);
				preparedStatement.setString(3, address);
				preparedStatement.setString(4, city);
				preparedStatement.setString(5, telephone);
				
				int filasAfectadas = preparedStatement.executeUpdate();
				//---------------------------------------------------------------------------------------------------
				
				//consulta para mostrar los propietarios
				String sql1 = "Select * from owners";
				preparedStatement = connection.prepareStatement(sql1);
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()) {
					int id1 = rs.getInt("id");
					String first_name1 = rs.getString("first_name");
					String last_name1 = rs.getString("last_name");
					String address1 = rs.getString("address");
					String city1 = rs.getString("city");
					String telephone1 = rs.getString("telephone");
					System.out.println("ID: "+ id1 + "\nNombre: "+first_name1 + "\nApellidos: " + last_name1 + "\nDireccion: " + address1 + "\nCiudad: " + city1 + "\nTelefono: " + telephone1 + "\n");
				}
				rs.close();
				//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				
				//consulta para borrar los datos
				String sql3 = "delete from owners where first_name like ?";
				preparedStatement = connection.prepareStatement(sql3);
				preparedStatement.setString(1, first_name);
				
				int filasAfectadas2 = preparedStatement.executeUpdate();
				//------------------------------------------------------------------------------------
				System.out.println("-----------------------------\nBorrado\n-----------------------------");
				//consulta para mostrar los propietarios
				String sql4 = "Select * from owners";
				preparedStatement = connection.prepareStatement(sql4);
				ResultSet rs1 = preparedStatement.executeQuery();
				while(rs1.next()) {
					int id1 = rs1.getInt("id");
					String first_name1 = rs1.getString("first_name");
					String last_name1 = rs1.getString("last_name");
					String address1 = rs1.getString("address");
					String city1 = rs1.getString("city");
					String telephone1 = rs1.getString("telephone");
					System.out.println("ID: "+ id1 + "\nNombre: "+first_name1 + "\nApellidos: " + last_name1 + "\nDireccion: " + address1 + "\nCiudad: " + city1 + "\nTelefono: " + telephone1 + "\n");
				}
				rs.close();
				//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				
				
				/*statement = connection.createStatement();
				
				//String sql2 = "INSERT INTO owners (first_name, last_name, address, city, telephone) VALUES ('Marcos', 'Ginel', 'Mi dirección', 'Mi ciudad', '666666666')";
				//int numeroDeFilasModificadas = statement.executeUpdate(sql2);
				
				//String sql1 = "UPDATE owners SET city = 'Sevilla' WHERE first_name = 'Marcos'";
				int numeroDeFilasModificadas1 = statement.executeUpdate(sql1);//
				
				//String sql = "Select * from owners";
				String sql = "Select * from owners where first_name like ? or last_name like ?";
				String busqueda = "Da";
				String termino = "%"+busqueda+"%";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, termino);
				preparedStatement.setString(2, termino);
				
				//ResultSet rs = statement.executeQuery(sql);
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()) {
					int id = rs.getInt("id");
					String first_name = rs.getString("first_name");
					String last_name = rs.getString("last_name");
					String address = rs.getString("address");
					String city = rs.getString("city");
					String telephone = rs.getString("telephone");
					System.out.println("ID: "+ id + "\nNombre: "+first_name + "\nApellidos: " + last_name + "\nDireccion: " + address + "\nCiudad: " + city + "\nTelefono: " + telephone + "\n");
				}
				rs.close();*/
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
