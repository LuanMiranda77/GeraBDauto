package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBD {
	
	private ConfigBD config;
	private Connection conexao;
	private boolean verificacao = false;
	
	
	public boolean criarConexao()throws SQLException {
		
			config = new ConfigBD();
			conexao = DriverManager.getConnection(config.getURL(),config.getUSER(),config.getSENHA());
			verificacao=true;
			Statement s = conexao.createStatement();
			s.executeUpdate("CREATE DATABASE bdclinica");
			s.close();
		
		return verificacao;
	}
	
	
	
}
