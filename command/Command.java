package br.ufba.sistema_biblioteca.command;

import java.util.List;

public interface Command {
    public void execute(List<String> args);
}
