package br.edu.ifsp.exemplo.ui;

import br.edu.ifsp.exemplo.core.Deck;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClashApp extends Application {
    @Override
    public void start(Stage stage) {

        Deck decks = new Deck("Meu primeiro deck");
        TabPane pagina = new TabPane();

        //pagina das cartas
        Tab pagCartas = new Tab("Cartas");
        pagCartas.setClosable(false);

        //coleçao
        Tab pagColecao = new Tab("Coleção");
        pagColecao.setClosable(false);

        //Decks
        Tab pagDeck = new Tab("Decks");
        pagDeck.setClosable(false);

        pagina.getTabs().addAll(pagCartas, pagColecao, pagDeck);

        Cadastro tela = new Cadastro(decks);
        tela.mostrar(stage);

        Scene cena = new Scene(pagina, 900, 600);
        stage.setTitle("Clash-Royale");
        stage.setScene(cena);
        stage.show();
    }
}
