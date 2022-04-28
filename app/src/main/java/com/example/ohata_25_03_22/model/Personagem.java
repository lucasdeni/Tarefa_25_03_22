package com.example.ohata_25_03_22.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Personagem implements Serializable {
    // Nessa classe tem variaveis e constructos para alterar os dados do personagem
    private String nome;
    private String nascimento;
    private String altura;
    private int id = 0;

    public Personagem(String nome, String nascimento, String altura){
        this.nome = nome;
        this.nascimento = nascimento;
        this.altura = altura;
    }

    public Personagem(){ }

    public void SetNome(String nome) {this.nome = nome;}
    public void SetNascimento(String nascimento) {this.nascimento = nascimento;}
    public void SetAltura(String altura) {this.altura = altura;}

    public String getNome() {return nome;}
    public String getNascimento() {return nascimento;}
    public String getAltura() {return  altura;}

    @NonNull
    @Override

    public String toString() {return nome;}

    public void setId(int id) {this.id = id;}

    public int getId() {return id;}

    public boolean IdValido() {return id > 0;}

    public int indexOf(Personagem personagemEncontrado) {
        return 0;
    }

    public void set(int posicaoDoPersonagem, Personagem personagem) {
    }

    public void setNome(String nome) {
    }

    public void setAltura(String altura) {
    }

    public void setNascimento(String nascimento) {
    }
}
