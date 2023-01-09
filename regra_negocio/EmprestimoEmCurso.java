package br.ufba.sistema_biblioteca.regra_negocio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class EmprestimoEmCurso implements EmprestimoStatus{
    SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

    Emprestimo emprestimo;
    String nome="Em Curso";

    public EmprestimoEmCurso(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    @Override
    public String getNomeStatus() {
        return this.nome;
    }

    @Override
    public void devolver() {
        String dataDevolucao = formatoData.format(new Date());
        this.emprestimo.dataDevolucao = dataDevolucao;
        this.emprestimo.setStatus(new EmprestimoFinalizado(this.emprestimo));
    }

    @Override
    public boolean isInCurso() {
        return true;
    }

    @Override
    public boolean isAtrasado() {
        try {
            Date dataHoje = formatoData.parse(formatoData.format(new Date()));
            Date dataPrevisaoDevolucao = formatoData.parse(this.emprestimo.dataPrevistaDevolucao);
            Boolean DataUltrapassou = dataPrevisaoDevolucao.compareTo(dataHoje)  < 0;
            if (DataUltrapassou){
                return true;
            }
            return false;
        } catch (ParseException e) {
            System.out.println(e);
            return false;
        }
    }

}
