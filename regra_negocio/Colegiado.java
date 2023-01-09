package br.ufba.sistema_biblioteca.regra_negocio;

public abstract class Colegiado implements Usuario, LivroObserver {
	String nome;
	int id;
	int quantidadeLivrosReservados;
	
	@Override
	public void update(Livro livro) {
		this.quantidadeLivrosReservados +=1;
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
	
	public int getquantidadeLivrosReservados() {
		return this.quantidadeLivrosReservados;
	}
	
	@Override
	public abstract int getTempoEmprestimo();
}
