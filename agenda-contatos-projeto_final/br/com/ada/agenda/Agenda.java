package br.com.ada.agenda;

import br.com.ada.agenda.util.Arquivo;
import br.com.ada.agenda.util.ConsoleUIHelper;
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

    /* CONTATO */
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

    public void adicionarContato(Scanner entrada, Agenda agenda) {

        String nome = ConsoleUIHelper.askSimpleInput("Nome do Contato");
        String sobreNome = ConsoleUIHelper.askSimpleInput("Sobrenome do Contato");
        String email = ConsoleUIHelper.askSimpleInput("E-mail do Contato");
        String empresa = ConsoleUIHelper.askSimpleInput("Empresa do Contato");

        Contato novoContato = new Contato(nome, sobreNome, email, empresa);

        try{
            addContatos(List.of(novoContato));
            Arquivo.exportarArquivo(agenda);
        }catch (Exception e){
            System.out.printf("Erro ao cadastrar: %s %n", e.getMessage());
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

    public void removerContato(Scanner entrada, Agenda agenda){
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
            Arquivo.exportarArquivo(agenda);
        } catch (Exception e){
            System.out.println("Contato não encontrado.");
        }
    }

    public void removerTodosContatos(Agenda agenda){
        contatos.clear();
        try{
            Arquivo.exportarArquivo(agenda);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
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
    public void adicionarTelefone(Scanner entrada, Agenda agenda){
        Contato contato = buscarContatoNomeSobrenome(entrada);

        if (contato != null){
            contato.adicionarTelefone();
            try{
                Arquivo.exportarArquivo(agenda);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Contato não encontrado");
        }
    }

    public void removerTelefone(Scanner entrada, Agenda agenda){
        Contato contato = buscarContatoNomeSobrenome(entrada);

        if (contato != null){
            contato.mostrarTelefones();
            try{
                int indice = Integer.parseInt(ConsoleUIHelper.askSimpleInput("Informe o índice do telefone: "));
                contato.removerTelefone(indice);
                Arquivo.exportarArquivo(agenda);
            } catch (Exception e){
                System.out.println("Digite um número inteiro maior que zero: ");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Contato não encontrado");
        }
    }
    /* ENDEREÇO */
    public void adicionarEndereco(Scanner entrada, Agenda agenda){
        Contato contato = buscarContatoNomeSobrenome(entrada);

        if (contato != null){
            contato.adicionarEndereco();
            try{
                Arquivo.exportarArquivo(agenda);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
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

    public void removerEndereco(Scanner entrada, Agenda agenda){
        Contato contato = buscarContatoNomeSobrenome(entrada);

        if (contato != null){
            contato.mostrarEndereco();
            try{
                int indice = Integer.parseInt(ConsoleUIHelper.askSimpleInput("Informe o índice do endereço: "));
                contato.removerEndereco(indice);
                Arquivo.exportarArquivo(agenda);
            } catch (Exception e){
                System.out.println("Digite um número inteiro maior que zero: ");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Contato não encontrado");
        }
    }
}
