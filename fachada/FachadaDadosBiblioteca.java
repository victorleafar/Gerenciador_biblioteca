package br.ufba.sistema_biblioteca.fachada;

import br.ufba.sistema_biblioteca.regra_negocio.*;

import java.util.ArrayList;

public class FachadaDadosBiblioteca {
	private static FachadaDadosBiblioteca instance;
	public ArrayList<Livro> listaLivros = new ArrayList<>();
	public ArrayList<Exemplar> listaExemplares = new ArrayList<>();
	public ArrayList<Usuario> listaUsuarios = new ArrayList<>();
	public ArrayList<Emprestimo> listaEmprestimos = new ArrayList<>();
	public ArrayList<Reserva> listaReserva = new ArrayList<>();
	public ArrayList<Emprestimo> listaEmprestimosDevolvidos = new ArrayList<>();
	public ArrayList<Reserva> listaReservaConcluidas = new ArrayList<>();

	private FachadaDadosBiblioteca() {
		this.InserirRegistrosIniciais();
	}

	public static synchronized FachadaDadosBiblioteca getInstanceBiblioteca() {
		if (instance == null) {
			instance = new FachadaDadosBiblioteca();
		}
		return instance;
	}

	public void addLivro(Livro livro) {
		this.listaLivros.add(livro);
	}

	public void addExemplar(Exemplar exemplar) {
		this.listaExemplares.add(exemplar);
	}

	public void addUsuario(Usuario usuario) {
		this.listaUsuarios.add(usuario);
	}

	public void addEmprestimo(Emprestimo emprestimo) {
		this.listaEmprestimos.add(emprestimo);
	}

	public void addEmprestimoDevolucao(Emprestimo emprestimo) {
		this.listaEmprestimosDevolvidos.add(emprestimo);
	}

	public void addReserva(Reserva reserva) {
		this.listaReserva.add(reserva);
	}

	public void addReservaRemovida(Reserva reserva) {
		this.listaReservaConcluidas.add(reserva);
	}

	public void removeEmprestimo(Emprestimo emprestimo) {
		int i = this.listaEmprestimos.indexOf(emprestimo);
		if (i >= 0)
			;
		this.addEmprestimoDevolucao(emprestimo);
		this.listaEmprestimos.remove(i);
	}

	public void removeReserva(Reserva reserva) {
		int i = this.listaReserva.indexOf(reserva);
		if (i >= 0)
			;
		this.addReservaRemovida(reserva);
		this.listaReserva.remove(i);
	}

	public Livro getLivro(int idLivro) {
		for (Livro livro : listaLivros) {
			if (livro.getIdLivro() == idLivro) {
				return livro;
			}
		}
		return null;
	}

	public Exemplar getExemplarDisponivel(int idLivro) {
		for (Exemplar exemplar : listaExemplares) {
			if (exemplar.getIdLivro() == idLivro) {
				if (exemplar.isDisponivel()) {
					return exemplar;
				}
			}
		}
		return null;
	}

	public Usuario getUsuario(int idusuario) {
		for (Usuario usuario : listaUsuarios) {
			if (usuario.getIdUsuario() == idusuario) {
				return usuario;
			}
		}
		return null;
	}

	public Colegiado getColegiado(int idColegiado) {
		for (Usuario usuario : listaUsuarios) {
			if (usuario.getIdUsuario() == idColegiado) {
				return (Colegiado) usuario;
			}
		}
		return null;
	}

	public Discente getDiscente(int idDiscente) {
		for (Usuario usuario : listaUsuarios) {
			if (usuario.getIdUsuario() == idDiscente) {
				return (Discente) usuario;
			}
		}
		return null;
	}

	public ArrayList<Emprestimo> getAllEmprestimos() {
		return this.listaEmprestimos;
	}

	public Emprestimo getEmprestimo(int idUsuario, int idLivro) {
		Exemplar exemplar;
		for (Emprestimo emprestimo : listaEmprestimos) {
			if (emprestimo.getIdUsuario() == idUsuario) {
				exemplar = this.getExemplar(emprestimo.getIdExemplar());
				if (exemplar.getCodigoExemplar() == idLivro) {
					return emprestimo;
				}
			}
		}
		return null;
	}

	public Emprestimo getEmprestimo(int codExemplar) {
		Exemplar exemplar;
		for (Emprestimo emprestimo : listaEmprestimos) {
			if (emprestimo.getIdExemplar() == codExemplar) {
				return emprestimo;
			}
		}
		return null;
	}

	public ArrayList<Emprestimo> getAllEmprestimosDevolvidos() {
		return this.listaEmprestimosDevolvidos;
	}

	public ArrayList<Exemplar> getAllExemplares() {
		return this.listaExemplares;
	}

	public Exemplar getExemplar(int codExemplar) {
		for (Exemplar exemplar : this.listaExemplares) {
			if (exemplar.getCodigoExemplar() == codExemplar) {
				return exemplar;
			}
		}
		return null;
	}

	public ArrayList<Reserva> getAllReservas() {
		return this.listaReserva;
	}

	public Reserva getReserva(int idUsuario, int idLivro) {
		for (Reserva reserva : this.listaReserva) {
			if (reserva.getIdUsuario() == idUsuario && reserva.getIdLivro() == idLivro) {
				return reserva;
			}
		}
		return null;
	}

	public ArrayList<Reserva> getAllReservasRemovidas() {
		return this.listaReservaConcluidas;
	}

	private void InserirRegistrosIniciais() {
		Livro livro1 = new Livro(100, "Engenharia de Software", "Addison Wesley", "Ian Sommervile", "6�", 2000);
		Livro livro2 = new Livro(101, "UML  Guia do Usu�rio", "Campus", "Grady Booch James Rumbaugh Ivar Jacobson",
				"7�", 2000);
		Livro livro3 = new Livro(200, "Code Complete", "Microsoft Press", "Steve McConnell", "2�", 2014);
		Livro livro4 = new Livro(300, "Biblia", "Addison Wesley", "Ian Sommervile", "6�", 2000);
		Livro livro5 = new Livro(301, "Contos da carochina", "Addison Wesley", "Ian Sommervile", "6�", 2000);
		Livro livro6 = new Livro(302, "Se eu pudesse voar", "Addison Wesley", "Ian Sommervile", "6�", 2000);
		Livro livro7 = new Livro(303, "James e o pessego gigante", "Addison Wesley", "Ian Sommervile", "6�", 2000);
		Livro livro8 = new Livro(304, "Computação é para malucos", "Addison Wesley", "Ian Sommervile", "6�", 2000);
		Professor professor1 = new Professor("Victor", 1);
		Professor professor2 = new Professor("Luiz", 2);
		AlunoGraduacao aluno1 = new AlunoGraduacao(3, "Caio");
		AlunoGraduacao aluno2 = new AlunoGraduacao(4, "Maria");
		Exemplar exemplar1 = new Exemplar(100, 1);
		Exemplar exemplar2 = new Exemplar(100, 2);
		Exemplar exemplar9 = new Exemplar(100, 9);
		Exemplar exemplar10 = new Exemplar(100, 10);

		Exemplar exemplar3 = new Exemplar(101, 3);
		Exemplar exemplar4 = new Exemplar(200, 4);
		Exemplar exemplar5 = new Exemplar(300, 5);
		Exemplar exemplar6 = new Exemplar(301, 6);
		Exemplar exemplar7 = new Exemplar(302, 7);
		Exemplar exemplar8 = new Exemplar(303, 8);
		Emprestimo emprestimo1 = new Emprestimo(3, 3, "01/01/2022", "04/01/2022");
		Reserva reserva1 = new Reserva(2, 301, "04/12/2022");

		this.addLivro(livro1);
		this.addLivro(livro2);
		this.addLivro(livro4);
		this.addLivro(livro5);
		this.addLivro(livro6);
		this.addLivro(livro7);
		this.addLivro(livro8);
		this.addUsuario(professor1);
		this.addUsuario(professor2);
		this.addExemplar(exemplar1);
		this.addExemplar(exemplar2);
		this.addExemplar(exemplar3);
		this.addExemplar(exemplar4);
		this.addExemplar(exemplar5);
		this.addExemplar(exemplar6);
		this.addExemplar(exemplar7);
		this.addExemplar(exemplar8);
		this.addExemplar(exemplar9);
		this.addExemplar(exemplar10);
		this.addUsuario(aluno1);
		this.addUsuario(aluno2);
		this.addEmprestimo(emprestimo1);
		this.addReserva(reserva1);
	}

}
