package br.ufba.sistema_biblioteca.regra_negocio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Emprestimo {
	int idUsuario;
	int idExemplar;
	String dataEmprestimo;
	String dataPrevistaDevolucao;
	String dataDevolucao;
	EmprestimoStatus status = new EmprestimoEmCurso(this);

	public Emprestimo(int idUsuario, int codExemplar, String dataEmpresimo, String dataDevolucao) {
		this.idUsuario = idUsuario;
		this.idExemplar = codExemplar;
		this.dataEmprestimo = dataEmpresimo;
		this.dataPrevistaDevolucao = dataDevolucao;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public int getIdExemplar() {
		return idExemplar;
	}

	public String getDataEmprestimo() {
		return this.dataEmprestimo;
	}

	public String getDataDevolucao() {
		return dataDevolucao;
	}

	public String getDataPrevistaDevolucao() {
		return this.dataPrevistaDevolucao;
	}

	public String getNomeStatus() {
		return this.status.getNomeStatus();
	}

	public boolean isAtrasado() {
		return this.status.isAtrasado();
	}

	public void devolver() {
		this.status.devolver();
	}

	public void setStatus(EmprestimoStatus status) {
		this.status = status;
	}
}
