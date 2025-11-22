package br.edu.ifsp.exemplo.core;

import br.edu.ifsp.exemplo.data.CSV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.ArrayList;

/**
 * Gerenciador principal do software (Singleton).
 * Controla a coleção de Cartas e Decks e a persistência de dados.
 */
public class Sistema {

    // Padrão Singleton
    private static Sistema instance;

    public static Sistema getInstance(){
        if(instance == null){
            instance = new Sistema();
        }
        return instance;
    }

    // Coleção de Cartas (ObservableList para atualização automática da UI)
    private ObservableList<Carta> cartas;
    private List<Deck> decks ;

    private Sistema(){
        cartas = FXCollections.observableArrayList();
        decks = new ArrayList<>();
        carregarCarta(); // Tenta carregar dados do arquivo ao iniciar

        if (cartas.isEmpty()){
            cartasIniciais(); // Se o arquivo estiver vazio, cria cartas iniciais
            salvar();
        }
    }

    /**
     * Define as cartas que o sistema deve ter se o arquivo de dados não existir.
     * ATENÇÃO: O 6º argumento é o caminho da imagem (String).
     */
    private void cartasIniciais(){
        // Usando o construtor de 13 argumentos: nome, nivel, elixir(double), tipo, raridade, IMAGEM, alvo, dano...
        cartas.add(new Carta("Arqueiras", 11, 3.0, TipoDeCarta.TROPA, Raridade.COMUM, "imagens/arqueiras.png", TipoAlvo.AMBOS, 50, 30, 150, 5.0, Velocidade.MEDIA, 1.2));
        cartas.add(new Carta("Bola de Fogo", 11, 4.0, TipoDeCarta.FEITICO, Raridade.RARA, "imagens/bolaDeFogo.png", TipoAlvo.AMBOS, 50, 30, 150, 5.0, Velocidade.MEDIA, 1.2));
        cartas.add(new Carta("Torre Inferno", 11, 5.0, TipoDeCarta.CONSTRUCAO, Raridade.RARA, "imagens/torreInferno.png", TipoAlvo.AMBOS, 50, 30, 150, 5.0, Velocidade.MEDIA, 1.2));
        cartas.add(new Carta("Mosqueteira", 11, 4.0, TipoDeCarta.TROPA, Raridade.RARA, "imagens/mosqueteira.png", TipoAlvo.TERRESTRE, 50, 30, 150, 5.0, Velocidade.MEDIA, 1.2));
        cartas.add(new Carta("Horda de Servos", 11, 5.0, TipoDeCarta.TROPA, Raridade.COMUM, "imagens/hordaDeServos.png", TipoAlvo.TERRESTRE, 50, 30, 150, 5.0, Velocidade.MEDIA, 1.2));
        cartas.add(new Carta("Gigante", 11, 5.0, TipoDeCarta.TROPA, Raridade.RARA, "imagens/gigante.png", TipoAlvo.TERRESTRE, 50, 30, 150, 5.0, Velocidade.MEDIA, 1.2));
        cartas.add(new Carta("Valquíria", 11, 4.0, TipoDeCarta.TROPA, Raridade.RARA, "imagens/valquiria.png", TipoAlvo.TERRESTRE, 50, 30, 150, 5.0, Velocidade.MEDIA, 1.2));
        cartas.add(new Carta("Príncipe", 11, 5.0, TipoDeCarta.TROPA, Raridade.EPICA, "imagens/principe.png", TipoAlvo.TERRESTRE, 50, 30, 150, 5.0, Velocidade.MEDIA, 1.2));
        cartas.add(new Carta("Canhão", 11, 3.0, TipoDeCarta.CONSTRUCAO, Raridade.COMUM, "imagens/canhao.png", TipoAlvo.AMBOS, 50, 30, 150, 5.0, Velocidade.MEDIA, 1.2));
        cartas.add(new Carta("Zap", 11, 2.0, TipoDeCarta.FEITICO, Raridade.COMUM, "imagens/zap.png", TipoAlvo.AMBOS, 50, 30, 150, 5.0, Velocidade.MEDIA, 1.2));
    }

    //metodos
    public boolean cadastrarCarta(Carta carta){
        for(Carta c: cartas){
            if (c.getNome().equalsIgnoreCase(carta.getNome())) {
                System.out.println("Ja existe uma carta com esse nome");
                return false;
            }
        }
        cartas.add(carta); // Adicionar aqui dispara a atualização na Coleção
        salvar();
        return true;
    }

    public ObservableList<Carta> getCartas(){
        return cartas; // Retorna a lista observável
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
        // O CSV salva uma List, então convertemos a ObservableList para ArrayList
        CSV.salvaCarta(new java.util.ArrayList<>(cartas));
    }

    public void carregarCarta(){
        List<Carta> cartasArquivo = CSV.carregarCartas();
        // setAll() carrega os dados do CSV na lista observável, mantendo a referência
        cartas.setAll(cartasArquivo);
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