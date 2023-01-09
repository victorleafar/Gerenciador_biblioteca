package br.ufba.sistema_biblioteca.regra_negocio;

public interface Usuario {
	
	String getNome();
	void setNome(String nome) ;
	int getIdUsuario();
	int getTempoEmprestimo();
};



