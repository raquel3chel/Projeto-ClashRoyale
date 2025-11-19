package br.edu.ifsp.exemplo.core;

import java.util.ArrayList;
import java.util.List;

public class Sistema {

    private List<Carta> cartas;
    private List<Deck> decks;

    public Sistema(){
        this.cartas = new ArrayList<>();
        this.decks = new ArrayList<>();
        cartasIniciais();
    }

    private void cartasIniciais(){

        cartas.add(new Carta("Arqueiras", 11, 3, TipoDeCarta.TROPA, "Comum"));
        cartas.add(new Carta("Bola de Fogo", 11, 4, TipoDeCarta.FEITICO, "Rara"));
        cartas.add(new Carta("Torre Inferno", 11, 5, TipoDeCarta.CONSTRUCAO, "Rara"));
        cartas.add(new Carta("Mosqueteira", 11, 4, TipoDeCarta.TROPA, "Rara"));
        cartas.add(new Carta("Horda de Servos", 11, 5, TipoDeCarta.TROPA, "Comum"));
        cartas.add(new Carta("Gigante", 11, 5, TipoDeCarta.TROPA, "Rara"));
        cartas.add(new Carta("Valquíria", 11, 4, TipoDeCarta.TROPA, "Rara"));
        cartas.add(new Carta("Príncipe", 11, 5, TipoDeCarta.TROPA, "Épica"));
        cartas.add(new Carta("Canhão", 11, 3, TipoDeCarta.CONSTRUCAO, "Comum"));
        cartas.add(new Carta("Zap", 11, 2, TipoDeCarta.FEITICO, "Comum"));

    }

    //metodos
    public boolean cadastrarCarta(Carta carta){
        for(Carta c: cartas){
            if(c.getNome().equalsIgnoreCase(carta.getNome()));
            System.out.println("Ja existe uma carta com esse nome");
        }
    }
}
