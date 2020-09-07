package com.company.model;

import java.util.Date;

public class Administrativo extends Pessoa{
    private Double salario;

    public Administrativo(String name, String telefone, Date data, String cpf, String genero, Double salario) {
        super(name, telefone, data, cpf, genero);
        this.salario = salario;
    }

    public Administrativo(Double salario) {
        this.salario = salario;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }
    public double calcularSalario(){
        return salario;
    }
    public String getPerfil(){
        return "Administrativo";
    }
    public String getAtributo(){
        return "";
    }
}
