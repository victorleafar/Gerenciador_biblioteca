package br.ufba.sistema_biblioteca.regra_negocio;

public class Reserva {
	int idUsuario;
	int idLivro;
	String data;

	public Reserva(int idUsuario, int idLivro, String data) {
		this.idUsuario = idUsuario;
		this.idLivro = idLivro;
		this.data = data;
	}

	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdLivro() {
		return idLivro;
	}
	public void setIdLivro(int idLivro) {
		this.idLivro = idLivro;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
