package br.ufba.sistema_biblioteca.regra_negocio;

import java.util.ArrayList;

public class Livro implements LivroSubject {
	int idLivro;
	String titulo;
	String editora;
	String autor;
	String edicao;

	private ArrayList<LivroObserver> observers;

	@Override
	public void registerObserver(LivroObserver o) {
		int i = this.observers.indexOf(o);
		if (i < 0) {
			this.observers.add(o);
		}
	}

	@Override
	public void removeObserver(LivroObserver o) {
		int i = this.observers.indexOf(o);
		if (i >= 0) {
			this.observers.remove(i);
		}

	}

	@Override
	public void notifyObservers() {
		for (int i = 0; i < this.observers.size(); i++) {
			LivroObserver observer = this.observers.get(i);
			observer.update(this);
		}
	}

	int ano;

	public Livro(int idLivro, String titulo, String editora, String autor, String edicao, int ano) {
		this.idLivro = idLivro;
		this.titulo = titulo;
		this.editora = editora;
		this.autor = autor;
		this.edicao = edicao;
		this.ano = ano;
		this.observers = new ArrayList<>();
	}

	public int getIdLivro() {
		return idLivro;
	}

	public void setIdLivro(int idLivro) {
		this.idLivro = idLivro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEdicao() {
		return edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	@Override
	public String toString() {
		return this.titulo + " - " + this.edicao;

	}

}
