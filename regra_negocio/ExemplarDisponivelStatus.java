package br.ufba.sistema_biblioteca.regra_negocio;

public class ExemplarDisponivelStatus implements ExemplarStatus {
	private Exemplar exemplar;
	private String nome = "Disponivel";
	
	public ExemplarDisponivelStatus(Exemplar exemplar) {
		this.exemplar = exemplar;
	}

	@Override
	public boolean emprestar(){
		this.exemplar.setStatus(new ExemplarEmprestadoStatus(this.exemplar));
		return true;
	}
	
	@Override
	public boolean devolver() {
		this.exemplar.setStatus(new ExemplarDisponivelStatus(this.exemplar));
		return false;
	}

	@Override
	public String getNomeStatus() {
		return this.nome;
	}

	@Override
	public boolean isDisponivel() {
		return true;
	}
	
	
	
	
	
	
	

}
