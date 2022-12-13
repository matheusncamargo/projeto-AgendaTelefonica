package br.com.ada.agenda;

import br.com.ada.agenda.util.ConsoleUIHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Agenda {

    private static final int FATOR_INDICE = 1;
    private List<Contato> contatos;

    public Agenda(){
        this.contatos = new ArrayList<>();
    }

    public List<Contato> getContatos() {
        return Collections.unmodifiableList(contatos);
    }

    public void addContatos(List<Contato> contatos) {
        contatos.forEach(novoContato -> {
            if(verificaContatoExiste(novoContato)){
                throw new RuntimeException("Contato já cadastrado");
            }
        });

        if(contatos.stream().distinct().count() != contatos.size()){
            throw new RuntimeException("Contatos duplicados");
        }

        this.contatos.addAll(contatos);
    }

    private boolean verificaContatoExiste(Contato contato){
        return this.contatos
                .stream()
                .anyMatch(contatoCadastrado -> contatoCadastrado.equals(contato));
    }

    public void adicionarContato(Scanner entrada) {

        String nome = ConsoleUIHelper.askSimpleInput("Nome do Contato");
        String sobreNome = ConsoleUIHelper.askSimpleInput("Sobrenome do Contato");
        String email = ConsoleUIHelper.askSimpleInput("E-mail do Contato");
        String empresa = ConsoleUIHelper.askSimpleInput("Empresa do Contato");

        Contato novoContato = new Contato(nome, sobreNome, email, empresa);

        try{
            addContatos(List.of(novoContato));
        }catch (RuntimeException exception){
            System.out.printf("Erro ao cadastrar: %s %n", exception.getMessage());
        }
    }

    public void listarContatos() {

        int width = 80;

        if(!this.contatos.isEmpty()){
            this.contatos.stream()
                      .map(contato -> {
                        StringBuilder sb = new StringBuilder();
                        sb.append(ConsoleUIHelper.columnPaddingRight(String.valueOf(this.contatos.indexOf(contato)+FATOR_INDICE), 3, ' '));
                        sb.append(ConsoleUIHelper.columnPaddingRight((contato.getNome() + " " + contato.getSobreNome()), 52, ' '));
                        sb.append(ConsoleUIHelper.columnPaddingRight(contato.getEmail(), 25, ' '));
                        return sb.toString();
                    })
                   .forEach(System.out::println);
            ConsoleUIHelper.drawLine(width);
        } else {
            ConsoleUIHelper.drawWithPadding("Não há contatos", width);
        }
    }

    public Contato getContatoPeloCodigo(int codigoContato) {
        return this.contatos.get(codigoContato - FATOR_INDICE);
    }

    public void removerContato(Scanner entrada){
        System.out.println("Qual o primeiro nome do contato que deseja remover: ");
        String nomeRemover = entrada.nextLine();

        System.out.println("Qual o sobrenome do contato que deseja remover: ");
        String sobrenomeRemover = entrada.nextLine();

        System.out.println("Qual o email do contato que deseja remover: ");
        String emailRemover = entrada.nextLine();

        try{
            contatos.removeIf(e -> e.getEmail().equals(emailRemover) &&
                    e.getNome().equalsIgnoreCase(nomeRemover) &&
                    e.getSobreNome().equalsIgnoreCase(sobrenomeRemover) );

        } catch (Exception e){
            System.out.println("Contato não encontrado.");
        }
    }

    public void removerTodosContatos(){
        contatos.clear();
    }

    public Contato buscarContatoNomeSobrenome(Scanner entrada){
        System.out.println("Qual o primeiro nome do contato que deseja procurar: ");
        String primeiroNome = entrada.nextLine();

        System.out.println("Qual o sobrenome do contato que deseja procurar: ");
        String sobrenome = entrada.nextLine();

        List<Contato> contatosEncontrados = new ArrayList<>();

        contatosEncontrados = this.contatos.stream()
                .filter(e ->  e.getNome().equalsIgnoreCase(primeiroNome) &&
                                e.getSobreNome().equalsIgnoreCase(sobrenome))
                .collect(Collectors.toList());

        if (contatosEncontrados.size() > 0){
            return contatosEncontrados.get(0);
        } else {
            return null;
        }
    }

    public void printContatoEncontrado(Scanner entrada){

        Contato contato = buscarContatoNomeSobrenome(entrada);

        if (contato != null){
            System.out.println(contato);
        } else {
            System.out.println("Contato não encontrado");
        }
    }

/* TELEFONE */

    public void listarTelefones(Scanner entrada){
        Contato contato = buscarContatoNomeSobrenome(entrada);

        if (contato != null){
            contato.mostrarTelefones();
        } else {
            System.out.println("Contato não encontrado");
        }
    }

    public void exibirInfosTelefones(Scanner entrada){
        Contato contato = buscarContatoNomeSobrenome(entrada);

        if (contato != null){
            contato.exibirTelefones();
        } else {
            System.out.println("Contato não encontrado");
        }
    }
    public void adicionarTelefone(Scanner entrada){
        Contato contato = buscarContatoNomeSobrenome(entrada);

        if (contato != null){
            contato.adicionarTelefone();
        } else {
            System.out.println("Contato não encontrado");
        }
    }

    public void removerTelefone(Scanner entrada){
        Contato contato = buscarContatoNomeSobrenome(entrada);

        if (contato != null){
            contato.mostrarTelefones();
            try{
                int indice = Integer.parseInt(ConsoleUIHelper.askSimpleInput("Informe o índice do telefone: "));
                contato.removerTelefone(indice);
            } catch (RuntimeException e){
                System.out.println("Digite um número inteiro maior que zero: ");
            }
        } else {
            System.out.println("Contato não encontrado");
        }
    }
    /* ENDEREÇO */
    public void adicionarEndereco(Scanner entrada){
        Contato contato = buscarContatoNomeSobrenome(entrada);

        if (contato != null){
            contato.adicionarEndereco();
        } else {
            System.out.println("Contato não encontrado");
        }
    }

    public void listarEndereco(Scanner entrada){
        Contato contato = buscarContatoNomeSobrenome(entrada);

        if (contato != null){
            contato.mostrarEndereco();
        } else {
            System.out.println("Contato não encontrado");
        }
    }

    public void exibirInfosEnderecos(Scanner entrada){
        Contato contato = buscarContatoNomeSobrenome(entrada);

        if (contato != null){
            contato.exibirEnderecos();
        } else {
            System.out.println("Contato não encontrado");
        }
    }

    public void removerEndereco(Scanner entrada){
        Contato contato = buscarContatoNomeSobrenome(entrada);

        if (contato != null){
            contato.mostrarEndereco();
            try{
                int indice = Integer.parseInt(ConsoleUIHelper.askSimpleInput("Informe o índice do endereço: "));
                contato.removerEndereco(indice);
            } catch (RuntimeException e){
                System.out.println("Digite um número inteiro maior que zero: ");
            }
        } else {
            System.out.println("Contato não encontrado");
        }
    }


}
