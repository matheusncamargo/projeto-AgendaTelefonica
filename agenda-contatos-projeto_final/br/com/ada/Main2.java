package br.com.ada;

import br.com.ada.agenda.Agenda;
import br.com.ada.agenda.Telefone;
import br.com.ada.agenda.TipoTelefone;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main2 {
    public static void main(String[] args) throws JsonProcessingException {
        new ObjectMapper();

        Telefone telefone = new Telefone(TipoTelefone.RESIDENCIAL, "55", "19", "9595959");
        String teste = new ObjectMapper().writeValueAsString(telefone);

        System.out.println(teste);
    }
}
