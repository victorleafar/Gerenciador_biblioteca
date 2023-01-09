package br.ufba.sistema_biblioteca.regra_negocio;

public class Professor extends Colegiado{
	
	public Professor(String nome, int id) {
		this.nome = nome;
		this.id = id;
	}

	@Override
	public int getTempoEmprestimo() {
		return 7;
	}

}
