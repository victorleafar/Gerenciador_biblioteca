package br.ufba.sistema_biblioteca.regra_negocio;

public interface ExemplarStatus {
	public boolean emprestar();
	public String getNomeStatus();
	public boolean devolver();
	public boolean isDisponivel();
}
