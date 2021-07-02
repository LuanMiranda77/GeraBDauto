package model;

public class ConfigBD {
	
	//config banco
	private String URL="jdbc:postgresql://localhost:5432/postgres"; // incica o caminho do banco de dados
	private String USER="postgres"; // aqui vai o nome usuario que vc quer acessar
	private String SENHA="ads54321";// senha adimn
	
	//config da verção
	private String versao="V-2.0-26052020";
	private String TipoInstalacao="SERVIDOR";

	
	public String getTipoInstalacao() {
		return TipoInstalacao;
	}
	public void setTipoInstalacao(String tipoInstalacao) {
		TipoInstalacao = tipoInstalacao;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getUSER() {
		return USER;
	}
	public void setUSER(String uSER) {
		USER = uSER;
	}
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	public String getSENHA() {
		return SENHA;
	}
	public void setSENHA(String sENHA) {
		SENHA = sENHA;
	}

	
	
	

}
