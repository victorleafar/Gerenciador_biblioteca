package br.ufba.sistema_biblioteca.regra_negocio;

public class ExemplarEmprestadoStatus implements ExemplarStatus {
	private Exemplar exemplar;
	private String nome = "Emprestado";
	



	public ExemplarEmprestadoStatus(Exemplar exemplar) {
		this.exemplar = exemplar;
	}

	
	@Override
	public String getNomeStatus() {
		return this.nome;
	}


	@Override
	public boolean devolver() {
		this.exemplar.setStatus(new ExemplarDisponivelStatus(this.exemplar));
		return true;
	}


	@Override
	public boolean emprestar() {
		return false;
	}

	@Override
	public boolean isDisponivel() {
		return false;
	}	
	
}
