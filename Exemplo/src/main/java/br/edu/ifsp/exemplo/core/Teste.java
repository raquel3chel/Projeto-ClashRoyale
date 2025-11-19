package br.edu.ifsp.exemplo.core;

import java.util.ArrayList;
import java.util.List;

public class Teste {

    public static void main(String[] args) {
        List<Carta> cartas = new ArrayList<>();

        cartas.add(new Carta("Arqueiras", 7, 3, TipoDeCarta.TROPA, "Comum"));
        cartas.add(new Carta("Bola de Fogo",9, 4,TipoDeCarta.FEITICO, "Raro"));
        cartas.add(new Carta("Golem", 6,8,TipoDeCarta.CONSTRUCAO, "Épico"));
        cartas.add(new Carta("Goblins", 10, 2, TipoDeCarta.TROPA, "Comum"));

        System.out.println("Lista Original:");
        cartas.forEach(System.out::println);
    }
}
