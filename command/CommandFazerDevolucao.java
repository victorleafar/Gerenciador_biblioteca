package br.ufba.sistema_biblioteca.command;

import br.ufba.sistema_biblioteca.fachada.FachadaBiblioteca;
import java.util.List;

public class CommandFazerDevolucao implements Command{

    FachadaBiblioteca fachada;
    
    public CommandFazerDevolucao(FachadaBiblioteca fachada) {
        this.fachada = fachada;
    }

    @Override
    public void execute(List<String> args) {
        if (args.size() < 3){
            System.out.println("comando inválido");
            return;
        }
        String result = "";
        String comando = args.get(0);
        int idUsuario = Integer.parseInt(args.get(1));
        int idLivro = Integer.parseInt(args.get(2));
        result = this.fachada.fazerDevolucao(idUsuario, idLivro);
        System.out.println(result);
    }

}
