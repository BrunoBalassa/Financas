package com.company.model;

import java.util.Date;

public class Medico  extends Pessoa{
    private Especialidade especialidade;

    public Medico(String name, String telefone, Date data, String cpf, String genero, Especialidade especialidade) {
        super(name, telefone, data, cpf, genero);
        this.especialidade = especialidade;
    }

    public Medico(String nome, String telefone, String data, String genero, String cpf, String especialidade) {
    }

    public Medico(String nome, String telefone, String data, String genero, String cpf, Especialidade e) {
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public double calcularSalario(){
        return 0.0;
    }
    public String getPerfil(){
        return "Medico";
    }
    public String getAtributo(){
        return "Especialidade: " + especialidade.getEspecialidade();
    }

}
