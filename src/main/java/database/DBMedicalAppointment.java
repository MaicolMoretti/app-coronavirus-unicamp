package main.java.database;

import main.java.database.DBConnection;

import java.sql.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.java.copas.Appointment;
import main.java.copas.HealthProfessional;
import main.java.copas.Symptons;

public class DBMedicalAppointment {
	
	Connection connection = null;
	
	public DBMedicalAppointment() {
	}
	
	public void createAppointment(int cpf, int idHealthProfissional, java.util.Date dateAppointment){
	  
		PreparedStatement select = null;
	
		try {
			
			select = DBConnection.getConnection(connection).prepareStatement("insert into MEDICAL_APPOINTMENT(DATE,PACIENT_CPF,HEALTHPROFESSIONAL_ID) values(?,?,?)");
			
			select.setDate(1,  (Date) dateAppointment);
			select.setInt(2,  cpf);
			select.setInt(3,  idHealthProfissional);
			
			
			select.executeUpdate();
			
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	}
	public Appointment[] ShowMedicalAppointment(int id) {
		PreparedStatement select = null;
		ResultSet res = null;
		Appointment[] listAppointment = {
				
		};
		
		try {
			
			select = DBConnection.getConnection(connection).prepareStatement("select PATIENT_CPF,HEALTHPROFESSIONAL_ID  from MEDICAL_APPOINTMENT where id = ?");
			select.setInt(1,  id);
		    res = select.executeQuery();
			select.executeUpdate();
			
			while (res.next()) {
				Appointment a = new Appointment();
				a.setCpf(res.getInt(1));
				a.setIdHealthProfissional(res.getInt(2));
				listAppointment[listAppointment.length] = a;
			}
			
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		
		}	
		return listAppointment;
	}
	/*
	public ShowAppointmentsPatient(int cpf) {
		PreparedStatement select = null;
		ResultSet res = null;
		try {
			
			select = this.connection.prepareStatment("select * from MEDICAL_APPOINTMENT where cpf = ?")
			select.setInt(1,  cpf);
		    res = select.executeQuery()
			select.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		
		}	
		
	}
	*/
	/*Como fazer verifica��o ?*/
	/*
	public void UpdateAppointment(int cpf, int idHealthProfissional, java.util.Date dateAppointment) {
		  
				PreparedStatement select = null;
				ResultSet res = null;
				try {
					
					select = this.connection.prepareStatement("insert into MEDICAL_APPOINTMENT(PACIENT_CPF,HEALTHPROFESSIONAL_ID,DATE) values(?,?,?)")
					select.setInt(1,  cpf);
					select.setInt(2,  idHealthProfissional);
					select.setDate(3,  DateAppointment);
					select.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				
				}
		
	}
	*/
		
}
	
	