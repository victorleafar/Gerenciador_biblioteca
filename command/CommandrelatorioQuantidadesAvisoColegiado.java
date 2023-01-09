package br.ufba.sistema_biblioteca.command;

import br.ufba.sistema_biblioteca.fachada.FachadaBiblioteca;
import java.util.List;

public class CommandrelatorioQuantidadesAvisoColegiado implements Command{

    FachadaBiblioteca fachada;
    
    public CommandrelatorioQuantidadesAvisoColegiado(FachadaBiblioteca fachada) {
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
        int idUsuario = Integer.parseInt(args.get(1));
        result = this.fachada.relatorioQuantidadesAvisoColegiado(idUsuario);
        System.out.println(result);
    }

}
