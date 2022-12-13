package br.com.ada.agenda.util;

import br.com.ada.agenda.Agenda;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {
    public static void exportarArquivo(Agenda agenda) throws JsonProcessingException {

        String teste = new ObjectMapper().writeValueAsString(agenda);
        List<String> teste2 = new ArrayList<>();
        teste2.add(teste);

        try {
            java.nio.file.Files.write(Path.of("agenda.json"), teste2);
            System.out.println("Lista de contatos salva com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        try {
            File agendaJson = new File("agenda.json");

            if (!agendaJson.exists()) {
                FileWriter alt = new FileWriter(agendaJson);
                System.out.println("File created: " + agendaJson.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

         */

        /*
        try {

            Files.write(Path.of("agenda.json"), teste);
            FileWriter file = new FileWriter("/caminho/do/arquivo");
            file.write(obj.toJSONString());
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
         */

    }

    public static Agenda importarArquivo(){
//        String lines = Files.readAllLines(Path.of(agenda.json));
        Agenda agenda = null;
        try {
//            File agendaJson = new File("agenda.json");
//            String agendaJson = Files.readAllLines(Path.of("agenda.json")).get(0);
            agenda = new ObjectMapper().readValue(new URL("file:agenda.json"), Agenda.class);
            System.out.println("Arquivo importato com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return agenda;
    }
}
