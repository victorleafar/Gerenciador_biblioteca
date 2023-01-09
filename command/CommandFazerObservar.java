package br.ufba.sistema_biblioteca.command;

import java.util.List;

import br.ufba.sistema_biblioteca.fachada.FachadaBiblioteca;

public class CommandFazerObservar implements Command{

    FachadaBiblioteca fachada;
    
    public CommandFazerObservar(FachadaBiblioteca fachada) {
        this.fachada = fachada;
    }

    @Override
    public void execute(List<String> args) {
        String result = "";
        if (args.size() < 3){
            System.out.println("comando inválido");
            return;
        }
        String comando = args.get(0);
        int idUsuario = Integer.parseInt(args.get(1));
        int idLivro = Integer.parseInt(args.get(2));
        result = this.fachada.fazerColegiadoObservar(idUsuario, idLivro);
        System.out.println(result);
    }

}
