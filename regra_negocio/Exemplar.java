package br.ufba.sistema_biblioteca.regra_negocio;

public class Exemplar {


	int idLivro;
	int codigoExemplar;
	ExemplarStatus status;
	
	public Exemplar(int idLivro, int codigoExemplar) {
		super();
		this.idLivro = idLivro;
		this.codigoExemplar = codigoExemplar;
		this.status = new ExemplarDisponivelStatus(this);
	}
	
	public int getIdLivro() {
		return idLivro;
	}
	public void setIdLivro(int idLivro) {
		this.idLivro = idLivro;
	}
	public int getCodigoExemplar() {
		return codigoExemplar;
	}
	public void setCodigoExemplar(int codigoExemplar) {
		this.codigoExemplar = codigoExemplar;
	}
	public String getStatus() {
		return this.status.getNomeStatus();
	}
	
	public void setStatus(ExemplarStatus status) {
		this.status = status;
	}
	
	public boolean emprestar() {
		return this.status.emprestar();
	}
	
	public boolean devolver() {
		return this.status.devolver();
	}

	public boolean isDisponivel() {
		return this.status.isDisponivel();
	}

}
