package br.ufba.sistema_biblioteca.fachada;

import br.ufba.sistema_biblioteca.regra_negocio.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

public class FachadaBiblioteca {

	static FachadaBiblioteca instance;
	SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
	FachadaDadosBiblioteca bancoDados;

	private FachadaBiblioteca() {
		this.bancoDados = FachadaDadosBiblioteca.getInstanceBiblioteca();
	}

	public static FachadaBiblioteca getInstanceFachadaBiblioteca() {
		if (instance == null) {
			instance = new FachadaBiblioteca();
		}
		return instance;
	}

	public String fazerEmprestimo(int idUsuario, int idLivro) {

		Exemplar exemplar = this.bancoDados.getExemplarDisponivel(idLivro);
		Livro livro = this.bancoDados.getLivro(idLivro);
		Usuario usuario = this.bancoDados.getUsuario(idUsuario);

		if (usuario == null) {
			return "não foi possivel realizar o emprestimo, MOTIVO: Usuario não existe.";
		}

		if (livro == null) {
			return "não foi possivel realizar o emprestimo, MOTIVO: Nenhum Livro com o id passado foi encontrado na base de dados.";
		}

		if (exemplar == null) {
			return "não foi possivel realizar o emprestimo: " + usuario.getNome() + " - " + livro.getTitulo()
					+ " , MOTIVO: emprestar o livro: Nenhum exemplar disponivel.";
		}

		if (this.verificarSeUsuarioJáEstaComEsteLivroEmprestado(idUsuario, idLivro)) {
			return "não foi possivel realizar o emprestimo: " + usuario.getNome() + " - " + livro.getTitulo()
					+ ", MOTIVO: Usuario já possui um exemplar deste livro emprestado.";
		}

		if (this.verificarUsuarioComLivroAtrasado(idUsuario)) {
			return "não foi possivel realizar o emprestimo: " + usuario.getNome() + " - " + livro.getTitulo()
					+ ", MOTIVO: Usuario com Livro Atrasado.";
		}

		if (usuario instanceof Discente) {
			if (this.verificarUsuarioComNumeroMaximoLivro(usuario.getIdUsuario())) {
				return "não foi possivel realizar o emprestimo: " + usuario.getNome() + " - " + livro.getTitulo()
						+ ", MOTIVO: Usuario Já emprestou o maximo de livros permitidos.";
			}
		}

		if (usuario instanceof Discente) {
			if (this.verificarNumeroReservasXExemplaresDisponiveis(usuario.getIdUsuario(), livro.getIdLivro())) {
				return "não foi possivel realizar o emprestimo: " + usuario.getNome() + " - " + livro.getTitulo()
						+ ", MOTIVO: todos os exemplares disponiveis já foram reservados.";
			}
		}
		String dataEmprestimo = formatoData.format(new Date());
		String dataDevolucao = "";
		Calendar cal = Calendar.getInstance();
		int diasMaximoPermitidoParaUsuario = usuario.getTempoEmprestimo();
		try {
			cal.setTime(formatoData.parse(dataEmprestimo));
			cal.add(Calendar.DAY_OF_MONTH, diasMaximoPermitidoParaUsuario);
			dataDevolucao = formatoData.format(cal.getTime());
		} catch (ParseException e) {
			dataDevolucao = dataEmprestimo;
		}
		Emprestimo novoEmprestimo = new Emprestimo(usuario.getIdUsuario(), exemplar.getCodigoExemplar(), dataEmprestimo,
				dataDevolucao);

		exemplar.emprestar();
		bancoDados.addEmprestimo(novoEmprestimo);
		Reserva reserva = bancoDados.getReserva(usuario.getIdUsuario(), livro.getIdLivro());
		if (reserva != null) {
			bancoDados.removeReserva(reserva);
		}
		return "Emprestimo realizado com sucesso: " + usuario.getNome() + " - " + livro.getTitulo();
	}

	private boolean verificarUsuarioComLivroAtrasado(int idUsuario) {
		ArrayList<Emprestimo> listaEmprestimos = this.bancoDados.getAllEmprestimos();
		Usuario usuario = this.bancoDados.getUsuario(idUsuario);
		for (Emprestimo e : listaEmprestimos) {
			if (e.getIdUsuario() == usuario.getIdUsuario()) {
				if (e.isAtrasado()) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean verificarUsuarioComNumeroMaximoLivro(int idUsuario) {
		ArrayList<Emprestimo> listaEmprestimos = this.bancoDados.getAllEmprestimos();
		Discente usuario = this.bancoDados.getDiscente(idUsuario);
		int numeroEmprestimo = 0;
		for (Emprestimo e : listaEmprestimos) {
			if (e.getIdUsuario() == usuario.getIdUsuario()) {
				numeroEmprestimo += 1;
			}
		}
		if (numeroEmprestimo >= usuario.getMaximoLivros()) {
			return true;
		}
		return false;
	}

	private boolean verificarNumeroReservasXExemplaresDisponiveis(int idUsuario, int idLivro) {
		ArrayList<Reserva> listaReservas = this.bancoDados.getAllReservas();
		int numeroReservas = 0;
		boolean ReservaPertenceUsuario = false;
		for (Reserva r : listaReservas) {
			if (r.getIdLivro() == idLivro) {
				numeroReservas += 1;
				if (r.getIdUsuario() == idUsuario) {
					ReservaPertenceUsuario = true;
				}
			}
		}

		ArrayList<Exemplar> listaExemplares = this.bancoDados.getAllExemplares();
		int numeroExemplaresDisponivel = 0;
		for (Exemplar e : listaExemplares) {
			if (e.getIdLivro() == idLivro && e.isDisponivel()) {
				numeroExemplaresDisponivel += 1;
			}
		}

		if (numeroReservas >= numeroExemplaresDisponivel && !ReservaPertenceUsuario) {
			return true;
		}
		return false;
	}

	public boolean verificarSeUsuarioJáEstaComEsteLivroEmprestado(int idUsuario, int idLivro) {
		Emprestimo emprestimo = this.bancoDados.getEmprestimo(idUsuario, idLivro);
		return !(emprestimo == null);
	}

	public String fazerDevolucao(int idUsuario, int idLivro) {
		Livro livro = this.bancoDados.getLivro(idLivro);
		Usuario usuario = this.bancoDados.getUsuario(idUsuario);
		Emprestimo emprestimo = this.bancoDados.getEmprestimo(idUsuario, idLivro);
		if (emprestimo == null) {
			return "não foi possivel fazer a devolução: " + usuario.getNome() + " - " + livro.getTitulo()
					+ ", MOTIVO: Nenhum emprestimo cadastrado";
		}

		emprestimo.devolver();
		this.bancoDados.removeEmprestimo(emprestimo);
		return "Devolução realizada: " + usuario.getNome() + " - " + livro.getTitulo();
	}

	public String fazerReserva(int idUsuario, int idLivro) {
		Livro livro = this.bancoDados.getLivro(idLivro);
		Usuario usuario = this.bancoDados.getUsuario(idUsuario);

		if (this.verificarSeUsuarioPossuiNumeroMaximoReserva(usuario.getIdUsuario())) {
			return "Não foi possivel fazer a reserva: " + usuario.getNome() + " - " + livro.getTitulo()
					+ ", MOTIVO: usuário já possui 3 reservas";
		}
		if (this.verificarSeUsuarioJaPossuiLivroReservado(usuario.getIdUsuario(), livro.getIdLivro())) {
			return "Não foi possivel fazer a reserva: " + usuario.getNome() + " - " + livro.getTitulo()
					+ ", MOTIVO: este livro já foi reservado anteriormente por esse usuário";
		}
		String dataReserva = formatoData.format(new Date());
		Reserva novaReserva = new Reserva(idUsuario, idLivro, dataReserva);
		this.bancoDados.addReserva(novaReserva);
		if (this.NotificarProfessoresReservasSimultaneas(idLivro)) {
			livro.notifyObservers();
		}
		return "Reserva realizada: " + usuario.getNome() + " - " + livro.getTitulo();
	}

	private boolean NotificarProfessoresReservasSimultaneas(int idLivro) {
		ArrayList<Reserva> reservas = this.bancoDados.getAllReservas();
		int numeroReservas = 0;

		for (Reserva r : reservas) {
			if (r.getIdLivro() == idLivro) {
				numeroReservas += 1;
			}
		}
		if (numeroReservas == 3) {
			return true;
		}
		return false;
	}

	private boolean verificarSeUsuarioPossuiNumeroMaximoReserva(int idUsuario) {
		ArrayList<Reserva> reservas = this.bancoDados.getAllReservas();
		int numeroReservas = 0;

		for (Reserva r : reservas) {
			if (r.getIdUsuario() == idUsuario) {
				numeroReservas += 1;
			}
		}
		if (numeroReservas >= 3) {
			return true;
		}
		return false;
	}

	private boolean verificarSeUsuarioJaPossuiLivroReservado(int idUsuario, int idLivro) {
		ArrayList<Reserva> reservas = this.bancoDados.getAllReservas();
		for (Reserva r : reservas) {
			if (r.getIdUsuario() == idUsuario && r.getIdLivro() == idLivro) {
				return true;
			}
		}
		return false;
	}

	public String fazerColegiadoObservar(int idUsuario, int idLivro) {
		Livro livro = this.bancoDados.getLivro(idLivro);
		Colegiado usuario = this.bancoDados.getColegiado(idUsuario);
		livro.registerObserver(usuario);
		return "Observador adicionado: " + usuario.getNome() + " - " + livro.getTitulo();
	}

	public String relatorioQuantidadesAvisoColegiado(int idUsuario) {
		Colegiado usuario = this.bancoDados.getColegiado(idUsuario);
		String resultado = "Relatorio Numero de Reservas simultaneas Observadas: "
				+ usuario.getquantidadeLivrosReservados();
		return resultado;
	}

	public String relatorioUsuario(int idUsuario) {
		String resultado = "Relatorio Usuario: \n";
		ArrayList<Emprestimo> listaEmprestimosAtivos = this.bancoDados.getAllEmprestimos();
		ArrayList<Emprestimo> listaEmprestimosDevolvidos = this.bancoDados.getAllEmprestimosDevolvidos();
		resultado += "--------------------------------\n";
		resultado += "Empresimos:\n";

		for (Emprestimo emprestimoAtivo : listaEmprestimosAtivos) {
			if (emprestimoAtivo.getIdUsuario() == idUsuario) {
				Exemplar exemplar = this.bancoDados.getExemplar(emprestimoAtivo.getIdExemplar());
				Livro livro = this.bancoDados.getLivro(exemplar.getIdLivro());
				resultado += " - titulo: " + livro.getTitulo() + ", data-emprestimo: "
						+ emprestimoAtivo.getDataEmprestimo() + ", status: '" + emprestimoAtivo.getNomeStatus() + "' "
						+ ", data-previsão-devolução: " + emprestimoAtivo.getDataPrevistaDevolucao() + "\n";
			}

		}
		for (Emprestimo emprestimoDevolvido : listaEmprestimosDevolvidos) {
			if (emprestimoDevolvido.getIdUsuario() == idUsuario) {
				Exemplar exemplar = this.bancoDados.getExemplar(emprestimoDevolvido.getIdExemplar());
				Livro livro = this.bancoDados.getLivro(exemplar.getIdLivro());
				resultado += " - titulo: " + livro.getTitulo() + ", data-emprestimo: "
						+ emprestimoDevolvido.getDataEmprestimo() + ", status: '" + emprestimoDevolvido.getNomeStatus()
						+ "' " + ", data-devolução: " + emprestimoDevolvido.getDataDevolucao() + "\n";
			}
		}

		ArrayList<Reserva> listaReservasAtivas = this.bancoDados.getAllReservas();
		ArrayList<Reserva> listaReservasConcluidas = this.bancoDados.getAllReservasRemovidas();
		resultado += "Reservas:\n";
		for (Reserva reservaAtiva : listaReservasAtivas) {
			if (reservaAtiva.getIdUsuario() == idUsuario) {
				Livro livro = this.bancoDados.getLivro(reservaAtiva.getIdLivro());
				resultado += " - titulo: " + livro.getTitulo() + ", data-Solicitação: " + reservaAtiva.getData() + "\n";
			}
		}
		for (Reserva reservaConcluida : listaReservasConcluidas) {
			if (reservaConcluida.getIdUsuario() == idUsuario) {
				Livro livro = this.bancoDados.getLivro(reservaConcluida.getIdLivro());
				resultado += " - titulo: " + livro.getTitulo() + ", data-Solicitação: " + reservaConcluida.getData()
						+ "\n";
			}
		}
		return resultado;
	}

	public String relatorioLivro(int idLivro) {
		String resultado = "Relatorio Livro:\n";
		Livro livro = this.bancoDados.getLivro(idLivro);

		ArrayList<Reserva> reservasLivro = this.bancoDados.getAllReservas();
		reservasLivro = reservasLivro.stream().filter(reserva -> reserva.getIdLivro() == idLivro)
				.collect(Collectors.toCollection(ArrayList::new));
		int numeroReservas = reservasLivro.size();
		if (numeroReservas > 0) {
			resultado += "-------------------------------------------\n";
			resultado += "Quantidade Reservas: " + numeroReservas + "\n";
			resultado += "Usuarios que Reservaram\n";
			for (Reserva reserva : reservasLivro) {
				Usuario usuario = this.bancoDados.getUsuario(reserva.getIdUsuario());
				resultado += " - " + usuario.getNome() + "\n";
			}
		}

		ArrayList<Exemplar> exemplaresLivro = this.bancoDados.getAllExemplares();
		exemplaresLivro = exemplaresLivro.stream().filter(exemplar -> exemplar.getIdLivro() == idLivro)
				.collect(Collectors.toCollection(ArrayList::new));
		int numeroExemplares = exemplaresLivro.size();
		if (numeroExemplares > 0) {
			resultado += "-------------------------------------------\n";
			resultado += "Exemplares: \n";
			for (Exemplar exemplar : exemplaresLivro) {
				if (exemplar.isDisponivel()) {
					resultado += " - cod: " + exemplar.getCodigoExemplar() + ", status: " + exemplar.getStatus() + "\n";
				} else {
					Emprestimo emprestimo = this.bancoDados.getEmprestimo(exemplar.getCodigoExemplar());
					Usuario usuario = this.bancoDados.getUsuario(emprestimo.getIdUsuario());
					resultado += " - cod: " + exemplar.getCodigoExemplar() + ", status: " + exemplar.getStatus()
							+ ", emprestimo: " + usuario.getNome() + "(" + emprestimo.getDataEmprestimo() + " - "
							+ emprestimo.getDataPrevistaDevolucao() + ")" + "\n";
				}
			}
		}
		return resultado;
	}

}
