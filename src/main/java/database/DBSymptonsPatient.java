package main.java.database;

//import java.sql.Statement;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.java.database.DBConnection;

import main.java.copas.Symptons;
import main.java.copas.SymptonsPatient;
public class DBSymptonsPatient {

	Connection connection = null;
	
	private int id = 0;
	
	public DBSymptonsPatient() {
	}

	public SymptonsPatient RegisterSymptons (int idSintoma, int cpf) {
			int generatedId = 0;		
			Connection connection = null;
			
			try {
				connection = DBConnection.getConnection(connection);
				PreparedStatement select = connection.prepareStatement("INSERT into PATIENT_SYMPTONS(SYMPTONS_ID,PATIENT_CPF) values (?,?)",Statement.RETURN_GENERATED_KEYS);
				select.setInt(1,idSintoma);
				select.setInt(2,cpf);
				System.out.println("ate aqui ok");
				System.out.println(select);
				int affectedRows = select.executeUpdate();
				System.out.println("ate aqui ok2");
	
		        if (affectedRows == 0) {
		            throw new SQLException("Updating Symptons, no rows affected.");
		        }

				ResultSet generatedKeys = select.getGeneratedKeys();
				
	            if (generatedKeys.next()) {
	                generatedId = (int) generatedKeys.getLong(1);
	            }
	            else {
	            	this.connection.close();
	                throw new SQLException("Updating Symptons, no ID obtained.");
	            }
	            
	            connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			SymptonsPatient s = new  SymptonsPatient(generatedId,idSintoma,cpf);
			
			return s;
		}
	}