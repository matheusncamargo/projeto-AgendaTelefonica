package br.com.ada.agenda;

import br.com.ada.agenda.util.ConsoleUIHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Contato {

    private String nome;
    private String sobreNome;
    private String empresa;
    private String email;
    private List<Telefone> telefones;
    private List<Endereco> enderecos;

    //Só o "json" utiliza o construtor padrão:
    private Contato() {
    }

    public Contato(String nome, String sobreNome,
                   String email, String empresa) {
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.email = email;
        this.empresa = empresa;
        this.telefones = new ArrayList<>();
        this.enderecos = new ArrayList<>();
    }

    public Contato (String nome, String sobreNome,
                    String empresa, List<Telefone> telefones,
                    List<Endereco> enderecos) {
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.empresa = empresa;
        this.telefones = telefones;
        this.enderecos = enderecos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Telefone> getTelefones() {
        return Collections.unmodifiableList(telefones); //Lista não pode ser modificada depois de usar .unmodifiableList()
    }

    /* TELEFONE */

    public void addTelefone(Telefone novoTelefone){
        if(verificaTelefoneExiste(novoTelefone)){
            throw new RuntimeException("Telefone já cadastrado!");
        }
        this.telefones.add(novoTelefone);
    }

    private boolean verificaTelefoneExiste(Telefone telefone){
        return this.telefones.stream()
                .anyMatch(telefoneCadastrado -> telefoneCadastrado.equals(telefone));
    }

    public void adicionarTelefone(){
        //List<TipoTelefone> tipoTelefones = Arrays.stream(TipoTelefone.values()).toList();
        List<TipoTelefone> tipoTelefones = Arrays.stream(TipoTelefone.values())
                .collect(Collectors.toList());

        String menuTipos = tipoTelefones.stream()
                .map(tipoTelefone -> String.format("%n%s - %s", tipoTelefone.ordinal() + 1, tipoTelefone.name()))
                .reduce("", String::concat);

        String tipoTelefone = ConsoleUIHelper.askSimpleInput(String.format("Tipo do Telefone%s", menuTipos));
        TipoTelefone tipo = tipoTelefones.get(Integer.parseInt(tipoTelefone) -1);

        String ddi = ConsoleUIHelper.askSimpleInput("DDI do Telefone");
        String ddd = ConsoleUIHelper.askSimpleInput("DDD do Telefone");
        String numero = ConsoleUIHelper.askSimpleInput("Número do Telefone");

        Telefone telefone = new Telefone(tipo, ddi, ddd, numero);

        try{
            addTelefone(telefone);
        }catch (RuntimeException exception){
            System.out.printf("Erro ao cadastrar: %s %n", exception.getMessage());
        }
    }

    public void mostrarTelefones(){
        if (this.telefones.isEmpty()){
            System.out.println("Não há telefone(s) cadastrado(s)");
        } else{
            telefones.forEach(tel -> {
                System.out.printf("%d - %s(%s)%s\n", telefones.indexOf(tel),
                        tel.ddi, tel.ddd, tel.numero);
            });
        }
    }

    public void removerTelefone(int indexTelefone){

        if(indexTelefone <= telefones.size() - 1){
            this.telefones.remove(indexTelefone);
            System.out.println("Telefone removido com sucesso!");
        } else {
            System.out.println("Índice não encontrado");
        };
    }

    public void exibirTelefones(){
        if (this.telefones.isEmpty()){
            System.out.println("Não há telefone(s) cadastrado(s)");
        } else{
            telefones.forEach(System.out::println);
        }
    }

    /* ENDEREÇO */
    public void addEndereco(Endereco novoEndereco){
        if(verificaEnderecoExiste(novoEndereco)){
            throw new RuntimeException("Endereço já cadastrado!");
        }
        this.enderecos.add(novoEndereco);
    }

    private boolean verificaEnderecoExiste(Endereco endereco){
        return this.enderecos.stream()
                .anyMatch(enderecoCadastrado -> enderecoCadastrado.equals(endereco));
    }

    public List<Endereco> getEnderecos() {
        return Collections.unmodifiableList(enderecos);
    }

    public void adicionarEndereco(){
        //List<TipoTelefone> tipoTelefones = Arrays.stream(TipoTelefone.values()).toList();
        List<TipoEndereco> tipoEnderecos = Arrays.stream(TipoEndereco.values())
                .collect(Collectors.toList());

        String menuTipos = tipoEnderecos.stream()
                .map(tipoEndereco -> String.format("%n%s - %s", tipoEndereco.ordinal() + 1, tipoEndereco.name()))
                .reduce("", String::concat);

        String tipoEndereco = ConsoleUIHelper.askSimpleInput(String.format("Tipo do Endereço%s", menuTipos));
        TipoEndereco tipo = tipoEnderecos.get(Integer.parseInt(tipoEndereco) -1);
        String logradouro = ConsoleUIHelper.askSimpleInput("Logradouro");
        String bairro = ConsoleUIHelper.askSimpleInput("Bairro");
        String numero = ConsoleUIHelper.askSimpleInput("Número");
        String complemento = ConsoleUIHelper.askSimpleInput("Complemento");
        String cep = ConsoleUIHelper.askSimpleInput("CEP");
        String cidade = ConsoleUIHelper.askSimpleInput("Cidade");
        String estado = ConsoleUIHelper.askSimpleInput("UF");

        try{
            Endereco endereco = new Endereco(tipo, logradouro, bairro, cep,
                    numero, complemento, cidade, Estado.valueOf(estado));
            addEndereco(endereco);
        } catch (RuntimeException e){
            System.out.println("Erro ao cadastrar");
        }

    }

    public void mostrarEndereco(){
        if (this.enderecos.isEmpty()){
            System.out.println("Não há endereço(s) cadastrado(s)");
        } else{
            enderecos.forEach(end -> {
                System.out.printf("%d - %s, %s, %s, %s, %s - %s\n",
                        enderecos.indexOf(end), end.getLogradouro(), end.getNumero(),
                        end.getBairro(), end.getCep(), end.getCidade(), end.getUf()
                        );
            });
        }
    }

    public void exibirEnderecos(){
        if (this.enderecos.isEmpty()){
            System.out.println("Não há endereço(s) cadastrado(s)");
        } else{
            enderecos.forEach(System.out::println);
        }
    }

    public void removerEndereco(int indexEndereco){
        if(indexEndereco <= enderecos.size() - 1){
            this.enderecos.remove(indexEndereco);
            System.out.println("Endereço removido com sucesso!");
        } else {
            System.out.println("Índice não encontrado");
        };

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contato contato = (Contato) o;
        return Objects.equals(nome, contato.nome) && Objects.equals(sobreNome, contato.sobreNome) && Objects.equals(empresa, contato.empresa) && Objects.equals(email, contato.email) && Objects.equals(telefones, contato.telefones) && Objects.equals(enderecos, contato.enderecos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, sobreNome, empresa, email, telefones, enderecos);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
                "Nome: %s \n" +
                "Sobrenome: %s \n" +
                "Empresa: %s \n", nome, sobreNome, empresa
        ));

        sb.append("Telefone(s): \n");
        telefones.forEach(telefone -> sb.append(telefone.toString()).append("\n"));

        sb.append("Endereço(s): \n");
        enderecos.forEach(endereco -> sb.append(endereco.toString()).append("\n"));

        return sb.toString();
    }
}
