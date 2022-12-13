package br.com.ada;

import br.com.ada.agenda.Agenda;
import br.com.ada.agenda.Contato;
import br.com.ada.agenda.util.Arquivo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {

        Agenda agenda;

        try{
            agenda = new ObjectMapper().readValue(new URL("file:agenda.json"), Agenda.class);
        } catch (Exception e){
            agenda = new Agenda();
        }

        Scanner entrada = new Scanner(System.in);
        String resposta;

        do {
            System.out.println("####### AGENDA CONTATOS #########\n");
            System.out.println("1 - Adicionar Contato   \n2 - Listar Contatos " +
                    "\n3 - Remover um contato \n4 - Remover todos os contatos" +
                    "\n5 - Buscar um contato por nome e sobrenome" +
            "\n6 - Adicionar um telefone a um contato existente" +
                    "\n7 - Adicionar um endereço a um contato existente" +
                    "\n8 - Remover um telefone de um contato existente" +
                    "\n9 - Remover um endereço de um contato existente" +
                    "\n10 - Exportar contatos para um arquivo de texto" +
                    "\n11 - Importar contatos a partir de um arquivo de texto" +
                    "\n12 - Listar todos os telefones de um contato" +
                    "\n13 - Listar todos os endereços de um contato" +
                    "\n14 - Exibir todas as informações de um endereço de um contato" +
            "\n15 - Exibir todas as informações de um telefone de um contato");
            System.out.print("\nEscolha uma opção: ");

            switch (entrada.nextLine().trim()) {
                case "1" -> agenda.adicionarContato(entrada, agenda);
                case "2" -> agenda.listarContatos();
                case "3" -> agenda.removerContato(entrada, agenda);
                case "4" -> agenda.removerTodosContatos(agenda);
                case "5" -> agenda.printContatoEncontrado(entrada);
                case "6" -> agenda.adicionarTelefone(entrada, agenda);
                case "7" -> agenda.adicionarEndereco(entrada, agenda);
                case "8" -> agenda.removerTelefone(entrada, agenda);
                case "9" -> agenda.removerEndereco(entrada, agenda);
                case "10" -> Arquivo.exportarArquivo(agenda);
                case "11" -> agenda = Arquivo.importarArquivo();
                case "12" -> agenda.listarTelefones(entrada);
                case "13" -> agenda.listarEndereco(entrada);
                case "14" -> agenda.exibirInfosEnderecos(entrada);
                case "15" -> agenda.exibirInfosTelefones(entrada);
                default -> System.out.println("Digite uma opção válida");
            }

            System.out.println("Deseja continuar? 1 - Sim, 2 - Não");
            resposta = (entrada.nextLine());

        } while(resposta.equals("1"));
    }
}
