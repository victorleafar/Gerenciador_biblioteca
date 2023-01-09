package br.ufba.sistema_biblioteca.command;

import java.util.Scanner;

import br.ufba.sistema_biblioteca.fachada.FachadaBiblioteca;

public class main {

    public static void main(String[] args) {

        FachadaBiblioteca fachadaBiblioteca = FachadaBiblioteca.getInstanceFachadaBiblioteca();

        Command c1 = new CommandFazerEmprestimo(fachadaBiblioteca);
        Command c2 = new CommandFazerDevolucao(fachadaBiblioteca);
        Command c3 = new CommandFazerReserva(fachadaBiblioteca);
        Command c4 = new CommandFazerObservar(fachadaBiblioteca);
        Command c5 = new CommandRelatorioLivro(fachadaBiblioteca);
        Command c6 = new CommandRelatorioUsuario(fachadaBiblioteca);
        Command c7 = new CommandrelatorioQuantidadesAvisoColegiado(fachadaBiblioteca);

        CommandBibliotecaApp app = new CommandBibliotecaApp();
        app.addCommand("emp", c1);
        app.addCommand("dev", c2);
        app.addCommand("res", c3);
        app.addCommand("obs", c4);
        app.addCommand("liv", c5);
        app.addCommand("usu", c6);
        app.addCommand("nft", c7);

        Scanner input = new Scanner(System.in);
        String command = input.nextLine();
        while (command != "-1") {
            app.ExecuteCommand(command);
            command = input.nextLine();
        }
        input.close();

    }

}