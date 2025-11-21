package br.edu.ifsp.exemplo.core;

import br.edu.ifsp.exemplo.data.CSV;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Sistema {

    private static Sistema instance;

    public static  Sistema getInstance(){
        if(instance == null){
            instance = new Sistema();
        }
        return instance;
    }

    private ObservableList<Carta> cartas;
    private List<Deck> decks ;

    private Sistema(){
        cartas = FXCollections.observableArrayList();
        decks = new ArrayList<>();
        carregarCarta();

        if (cartas.isEmpty()){
            cartasIniciais();
            salvar();
        }
    }

    private void cartasIniciais(){

        cartas.add(new Carta("Arqueiras", 11, 3, TipoDeCarta.TROPA, Raridade.COMUM,TipoAlvo.AMBOS,50,30,150,5.0,Velocidade.MEDIA,1.2));
        cartas.add(new Carta("Bola de Fogo", 11, 4, TipoDeCarta.FEITICO, Raridade.RARA,TipoAlvo.AMBOS,50,30,150,5.0,Velocidade.MEDIA,1.2));
        cartas.add(new Carta("Torre Inferno", 11, 5, TipoDeCarta.CONSTRUCAO, Raridade.RARA,TipoAlvo.AMBOS,50,30,150,5.0,Velocidade.MEDIA,1.2));
        cartas.add(new Carta("Mosqueteira", 11, 4, TipoDeCarta.TROPA, Raridade.RARA,TipoAlvo.TERRESTRE,50,30,150,5.0,Velocidade.MEDIA,1.2));
        cartas.add(new Carta("Horda de Servos", 11, 5, TipoDeCarta.TROPA, Raridade.COMUM,TipoAlvo.TERRESTRE,50,30,150,5.0,Velocidade.MEDIA,1.2));
        cartas.add(new Carta("Gigante", 11, 5, TipoDeCarta.TROPA, Raridade.RARA,TipoAlvo.TERRESTRE,50,30,150,5.0,Velocidade.MEDIA,1.2));
        cartas.add(new Carta("Valquíria", 11, 4, TipoDeCarta.TROPA, Raridade.RARA,TipoAlvo.TERRESTRE,50,30,150,5.0,Velocidade.MEDIA,1.2));
        cartas.add(new Carta("Príncipe", 11, 5, TipoDeCarta.TROPA, Raridade.EPICA,TipoAlvo.TERRESTRE,50,30,150,5.0,Velocidade.MEDIA,1.2));
        cartas.add(new Carta("Canhão", 11, 3, TipoDeCarta.CONSTRUCAO, Raridade.COMUM,TipoAlvo.AMBOS,50,30,150,5.0,Velocidade.MEDIA,1.2));
        cartas.add(new Carta("Zap", 11, 2, TipoDeCarta.FEITICO, Raridade.COMUM,TipoAlvo.AMBOS,50,30,150,5.0,Velocidade.MEDIA,1.2));

    }

    //metodos
    public boolean cadastrarCarta(Carta carta){
        for(Carta c: cartas){
            if (c.getNome().equalsIgnoreCase(carta.getNome())) {
                System.out.println("Ja existe uma carta com esse nome");
                return false;
            }
        }
        cartas.add(carta);
        salvar();
        return true;
    }

    public ObservableList<Carta> getCartas(){
        return cartas;
    }

    public Carta buscarCarta(String nome){
        for(Carta c : cartas){
            if (c.getNome().equalsIgnoreCase(nome)){
                return c;
            }
        }
        return null;
    }

    public void salvar(){
        CSV.salvaCarta(cartas);
    }

    public void carregarCarta(){
        List<Carta> cartasArquivo = CSV.carregarCartas();
        cartas.clear();
        cartas.addAll(cartasArquivo);
    }

    public boolean criarDeck(String nome){

        for(Deck d : decks){
            if(d.getNome().equalsIgnoreCase(nome)){
                System.out.println("Já exixte um deck com esse nome");
                return false;
            }
        }

        decks.add(new Deck(nome));
        return true;
    }
    public List<Deck> getDecks(){
        return decks;
    }
    public Deck buscarDeck(String nome){
        for(Deck d : decks){
            if (d.getNome().equalsIgnoreCase(nome)){
                return d;
            }
        }
        return null;
    }

}
