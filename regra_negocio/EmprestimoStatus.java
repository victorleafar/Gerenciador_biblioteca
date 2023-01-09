package br.ufba.sistema_biblioteca.regra_negocio;

public interface EmprestimoStatus{

    public String getNomeStatus();
    public void devolver();
    public boolean isInCurso();
    public boolean isAtrasado();
}
