package com.example.ohata_25_03_22.dao;

import com.example.ohata_25_03_22.model.Personagem;

import java.util.ArrayList;
import java.util.List;

// Salva o personagem
public class PersonagemDAO {
    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorDias = 1;

    // Salva o personagem dentro da lista e da refresh
    public void salva(Personagem personagemSalvo){
        personagemSalvo.setId(contadorDias);
        personagens.add(personagemSalvo);
        atualizaId();
    }

    private void atualizaId() {contadorDias++;}

    // Caso tenha alteracao ele edita e atualiza a lista
    public void edita(Personagem personagem){
        Personagem personagemEncontrado = buscaPersonagemId(personagem);
        if(personagemEncontrado != null){
            int posicaoDoPersonagem = personagens.indexOf(personagemEncontrado);
            personagens.set(posicaoDoPersonagem, personagem);
        }
    }

    // Retorna personagem da lista
    private Personagem buscaPersonagemId(Personagem personagem){
        for (Personagem p : personagens){
            if(p.getId() == personagem.getId()){
                return p;
            }
        }
        return null;
    }

    // Array da lista de personagem
    public List<Personagem> todos() { return new ArrayList<>(personagens);}

    // Deleta um personagem da lista
    public void remove(Personagem personagem){
        Personagem personagemDevolvido = buscaPersonagemId(personagem);
        if(personagemDevolvido != null){
            personagens.remove(personagemDevolvido);
        }
    }
}
