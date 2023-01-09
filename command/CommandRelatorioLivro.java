package br.ufba.sistema_biblioteca.command;

import br.ufba.sistema_biblioteca.fachada.FachadaBiblioteca;
import java.util.List;

public class CommandRelatorioLivro implements Command{

    FachadaBiblioteca fachada;
    
    public CommandRelatorioLivro(FachadaBiblioteca fachada) {
        this.fachada = fachada;
    }

    @Override
    public void execute(List<String> args) {
        if (args.size() < 2){
            System.out.println("comando inválido");
            return;
        }
        String result = "";
        String comando = args.get(0);
        int idLivro = Integer.parseInt(args.get(1));
        result = this.fachada.relatorioLivro(idLivro);
        System.out.println(result);
    }

}
