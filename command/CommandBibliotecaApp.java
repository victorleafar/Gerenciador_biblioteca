package br.ufba.sistema_biblioteca.command;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandBibliotecaApp {

    HashMap<String, Command> listaComandos = new HashMap<>();

    public void addCommand(String key, Command comando){
        listaComandos.put(key, comando);
    }

    public void ExecuteCommand(String command){
        
        List<String> args = Arrays.asList(command.split(" "));
        if (args.size() < 0){
            System.out.println("entrada inválida!");
            return;
        }

        String keyCommand = args.get(0);
        if (listaComandos.containsKey(keyCommand)){
            listaComandos.get(keyCommand).execute(args);
        }else{
            System.out.println("entrada inválida!");
        }
        
    }
}