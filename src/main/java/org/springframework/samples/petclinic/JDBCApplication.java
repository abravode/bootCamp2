
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
		}
		catch (ClassNotFoundException e) {
			System.out.println("No encuentro el driver en el Classpath");
			e.printStackTrace();
			return;
		}

		System.out.println("Driver instalado y funcionando");
		Connection connection = null;
		Statement statement = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic", "root", "root");
			if (connection != null)
				System.out.println("Conexión establecida");
			statement = connection.createStatement();
			String sql = "Select * from owners";
			ResultSet rs = statement.executeQuery(sql);

			// Realización del reto 3.

			Owner o = new Owner();
			o.setFirstName("Carlos");
			o.setLastName("Villarino");
			o.setAddress("Una calle por ahí");
			o.setTelephone("607753906");
			o.setCity("Salamanca");

			// Mi mascota
			Pet p = new Pet();
			p.setName("Mico");
			p.setOwner(o);
			o.addPet(p);

			statement = connection.createStatement();
			String n = o.getFirstName();
			String l = o.getLastName();
			String a = o.getAddress();
			String c = o.getCity();
			String t = o.getTelephone();

			String sql2 = "INSERT INTO owners (first_name, last_name, address, city, telephone)" + "VALUES ('" + n
					+ "', '" + l + "', '" + a + "', '" + c + "', '" + t + "')";
			statement.executeUpdate(sql2);

			// Sacamos por pantalla los datos de la nueva identidad creada

			System.out.println("----Reto 3----");
			statement = connection.createStatement();
			String sql4 = "SELECT * FROM owners WHERE last_name LIKE ?";
			String surname = "Villarino";
			PreparedStatement preparedStatement2 = connection.prepareStatement(sql4);
			preparedStatement2.setString(1, surname);
			rs = preparedStatement2.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lstName = rs.getString("last_name");
				String adress = rs.getString("address");
				String telephone = rs.getString("telephone");
				String city = rs.getString("city");
				System.out.println("Id: " + id + "| " + "Nombre: " + firstName + "| " + "Apellido: " + "| " + lstName + "| "
						+ "Dirección: " + "| " + adress + "| " + "Teléfono: " + "| " + telephone + "| " + "Ciudad: " + "| "
						+ city);
			}
			rs.close();

			sql = "DELETE FROM owners WHERE id > 10";
			statement.executeUpdate(sql);

		}
		catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		finally {
			try {
				if (statement != null)
					connection.close();
			}
			catch (SQLException se) {

			}
			try {
				if (connection != null)
					connection.close();
			}
			catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

}
