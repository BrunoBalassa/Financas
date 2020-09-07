package com.company.model;

import java.util.Date;

public class Enfermeiro extends Pessoa {
    private int cargahoraria;


    public Enfermeiro(String name, String telefone, Date data, String cpf, String genero, int cargahoraria) {
        super(name, telefone, data, cpf, genero);
        this.cargahoraria = cargahoraria;
    }

    public Enfermeiro(int cargahoraria) {
        this.cargahoraria = cargahoraria;
    }

    public Enfermeiro(String nome, String telefone, String data, String genero, String cpf, String especialidade) {
    }

    public int getCargahoraria() {
        return cargahoraria;
    }

    public void setCargahoraria(int cargahoraria) {
        this.cargahoraria = cargahoraria;
    }
    public double calcularSalario(){
        return 0.0;
    }
    public String getPerfil(){
        return "Enfermeiro";
    }
    public String getAtributo(){
        return "Carga horaria: " + this.getCargahoraria();
    }
}
