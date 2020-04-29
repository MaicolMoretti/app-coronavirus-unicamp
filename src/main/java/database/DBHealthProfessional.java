package main.java.database;

import main.java.database.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.java.copas.HealthProfessional;
import main.java.copas.Patient;

public class DBHealthProfessional {
	Connection connection = null;
	private int id = 0;
	
	
	public DBHealthProfessional() {
		
	}
	
	public int nextId() {
		this.id = this.id + 1;

		return this.id;
	}
	
	public HealthProfessional SignUp(int id) throws SQLException {

		HealthProfessional doc = null;
				
		PreparedStatement select = null;
		ResultSet res = null;
		
		try {
			select = DBConnection.getConnection(connection).prepareStatement("select * from HEALTHPROFESSIONAL where ID = ?");
			
			select.setInt(1, id);
			
			res = select.executeQuery();
			
			doc = new HealthProfessional(id, res.getInt("CPF"), res.getInt("RG"), res.getString("NAME"), res.getString("ROLE"));
	
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public HealthProfessional SignIn(int cpf, int rg, String name, String role) throws SQLException {		

		int generatedId = 0;
		
		try {
			PreparedStatement statement = DBConnection.getConnection(connection).prepareStatement("INSERT INTO HEALTHPROFESSIONAL(CPF, RG, NAME, ROLE) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1,  cpf);
			statement.setInt(2, rg);
			statement.setString(3, name);
			statement.setString(4, role);
			
	        int affectedRows = statement.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Creating doctor failed, no rows affected.");
	        }
			
			ResultSet generatedKeys = statement.getGeneratedKeys();
			
            if (generatedKeys.next()) {
                generatedId = (int) generatedKeys.getLong(1);
            }
            else {
            	this.connection.close();
                throw new SQLException("Creating doctor failed, no ID obtained.");
            }
            this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		HealthProfessional doc = new HealthProfessional(generatedId, cpf, rg, name, role);
		
		System.out.println("Doctor created - ID: " + generatedId);
		
		return doc;
	}
	
	public Boolean UserExists(int cpf) throws SQLException {

		Boolean exists = false;
		
		PreparedStatement select;
		try {
			select = DBConnection.getConnection(connection).prepareStatement("select * from HEALTHPROFESSIONAL where CPF = ?");
			
			select.setInt(1, cpf);
			ResultSet resultSet = select.executeQuery();			

			if (resultSet.next()) {
				exists = true;
			}
			
			this.connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return exists;
	}
}
