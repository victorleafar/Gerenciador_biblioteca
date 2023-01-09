package br.ufba.sistema_biblioteca.regra_negocio;

public class AlunoPosgraduacao implements Discente {

	private int id;
	private String nome;
	

	public AlunoPosgraduacao(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	@Override
	public String getNome() {
		return this.nome;
	}

	@Override
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int getIdUsuario() {
		return this.id;
	}

	@Override
	public int getTempoEmprestimo() {
		return 4;
	}

	@Override
	public int getMaximoLivros() {
		return 4;
	}

}
