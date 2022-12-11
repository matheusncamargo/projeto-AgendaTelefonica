package br.com.ada.agenda;

import java.util.Objects;

public class Endereco {

    private TipoEndereco tipo;
    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private Estado uf;

    public Endereco(TipoEndereco tipo, String logradouro, String bairro, String cep, String numero, String complemento, String cidade, Estado uf) {
        this.tipo = tipo;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.uf = uf;
    }

    public TipoEndereco getTipo() {
        return tipo;
    }

    public void setTipo(TipoEndereco tipo) {
        this.tipo = tipo;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Estado getUf() {
        return uf;
    }

    public void setUf(Estado uf) {
        this.uf = uf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return tipo == endereco.tipo && logradouro.equals(endereco.logradouro) &&
                bairro.equals(endereco.bairro) && cep.equals(endereco.cep) &&
                numero.equals(endereco.numero) && complemento.equals(endereco.complemento) &&
                cidade.equals(endereco.cidade) && uf == endereco.uf;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, logradouro, bairro, cep, numero, complemento, cidade, uf);
    }

    @Override
    public String toString() {
        return String.format(
                "\tTipo: %s \n" +
                        "\tLogradouro: %s \n" +
                        "\tNúmero: %s \n" +
                        "\tBairro: %s \n" +
                        "\tComplemento: %s \n" +
                        "\tCidade: %s \n" +
                        "\tCEP: %s \n" +
                        "\tUF: %s \n",
                tipo, logradouro, numero, bairro, complemento, cidade, cep, uf)
                ;
    }
}
