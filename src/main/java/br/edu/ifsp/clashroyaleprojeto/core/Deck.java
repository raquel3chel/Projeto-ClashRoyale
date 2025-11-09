package br.edu.ifsp.clashroyaleprojeto.core;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    //atributos
    //nome do deck
    private String nome;
    //cartas do deck
    private List<Carta> cartas;

    //construtor
    public Deck(String nome){
        this.nome = nome;
        this.cartas = new ArrayList<>();
    }

    //metodos
    //adicionar cartas no deck
    //carta adicionada true
    public boolean adicionarCarta(Carta carta){
        if(cartas.size() >= 8){
            System.out.println("Deck cheio");
            return false;
        }
        //se ja tem a carta no deck
        if (cartas.contains(carta)) {
            System.out.println("Essa carta ja existe");
            return false;
        }
        cartas.add(carta);
        return true;
    }

    //remover cartas do deck
    //true se foi removida
    public boolean removerCarta(Carta carta){
        return cartas.remove(carta);
    }




}
