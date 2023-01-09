package br.ufba.sistema_biblioteca.regra_negocio;

public interface LivroSubject {
	public void registerObserver(LivroObserver o);
	public void removeObserver(LivroObserver o);
	public void notifyObservers();
}
