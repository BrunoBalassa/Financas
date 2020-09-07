package com.company.model;

import java.util.HashMap;

public class Especialidade {
    private int id;
    private String descricao;
    private HashMap<Integer,Especialidade> hmEsp;

    public Especialidade(int id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    public Especialidade(){
        this.hmEsp = new HashMap<>();
        Especialidade e = new Especialidade(1,"Otorreno");
        this.hmEsp.put(e.getId(), e);
        e = new Especialidade(2, "Teste");
        this.hmEsp.put(e.getId(), e);

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public HashMap<Integer,Especialidade> getEspecialidade(){
        return this.hmEsp;
    }
}
