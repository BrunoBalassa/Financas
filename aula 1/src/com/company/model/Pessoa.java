package com.company.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Pessoa {
    private String name;
    private String telefone;
    private Date data;
    private String cpf;
    private String genero;

    public Pessoa(String name, String telefone, Date data, String cpf, String genero) {
        this.name = name;
        this.telefone = telefone;
        this.data = data;
        this.cpf = cpf;
        this.genero = genero;
    }

    public Pessoa() {

    }

    public int calcularIdade() {
        Date now = new Date();
        SimpleDateFormat ano = new SimpleDateFormat("YYYY");
        SimpleDateFormat mes = new SimpleDateFormat("MM");
        SimpleDateFormat dia = new SimpleDateFormat("dd");
        int idade = Integer.parseInt(ano.format(now)) - Integer.parseInt(ano.format(data));

        if (Integer.parseInt(mes.format(now)) < Integer.parseInt(mes.format(data))) {
            idade = idade - 1;
        } else {
            if (Integer.parseInt(mes.format(now)) == Integer.parseInt(mes.format(data))) {
                if (Integer.parseInt(dia.format(now)) < Integer.parseInt(dia.format(data))) {

                }
            }
        }
        return idade;
    }
    public String getPerfil(){
        return "Pessoa";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double calcularSalario() {
        return 0.0;
    }
     public String getAtributo(){
        return "";
     }

}
