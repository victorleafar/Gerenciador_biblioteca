package br.ufba.sistema_biblioteca.regra_negocio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmprestimoFinalizado implements EmprestimoStatus {
    SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

    Emprestimo emprestimo;
    String nome="Finalizado";

    public EmprestimoFinalizado(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    @Override
    public String getNomeStatus() {
        return this.nome;
    }

    @Override
    public void devolver() {

    }

    @Override
    public boolean isInCurso() {
        return false;
    }

    @Override
    public boolean isAtrasado() {
       return false;
    }
}
