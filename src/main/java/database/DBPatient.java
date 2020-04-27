package main.java.database;

import java.sql.*;

import main.java.copas.Patient;

public class DBPatient {
	
	Connection connection = null;
	
	public DBPatient() {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:corona.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Patient SignUp(int cpf, String password) {
		Patient patient = null
				;
		PreparedStatement select = null;
		ResultSet res = null;
		
		try {
			select = this.connection.prepareStatement("select * from PATIENTS where CPF = ? and PASSWORD = ?");
			
			select.setInt(1, cpf);
			select.setString(2, password);
			
			res = select.executeQuery();
			
			patient = new Patient(cpf, res.getInt("RG"), res.getString("NAME"), res.getString("EMAIL"), res.getString("SUSCARD"), res.getString("BORNDATE"), res.getString("ADDRESS"), password, res.getString("STATUS"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return patient;
	}
	
	public Patient SignIn(int cpf, int rg, String name, String email, String susCard, String bornDate, String address, String password) {		
		
		Patient patient = new Patient(cpf, rg, name, email, susCard, bornDate, address, password, "Sem consulta");
		
		try {
			PreparedStatement statement = this.connection.prepareStatement("INSERT INTO PATIENTS(CPF, RG, NAME, EMAIL, SUSCARD, BORNDATE, ADDRESS, PASSWORD, STATUS) VALUES(?,?,?,?,?,?,?,?,?)");
			statement.setInt(1,  cpf);
			statement.setInt(2, rg);
			statement.setString(3, name);
			statement.setString(4, email);
			statement.setString(5, susCard);
			statement.setString(6, bornDate);
			statement.setString(7, address);
			statement.setString(8, password);
			statement.setString(9, "Sem consulta");
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Usuário criado com sucesso");
		
		return patient;
	}
	
	public Boolean UserExists(int cpf) {
		
		Boolean exists = false;
		
		PreparedStatement select;
		try {
			select = this.connection.prepareStatement("select * from PATIENTS where CPF = ?");
			
			select.setInt(1, cpf);
			ResultSet resultSet = select.executeQuery();			

			if (resultSet.next()) {
				exists = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return exists;
	}
}

