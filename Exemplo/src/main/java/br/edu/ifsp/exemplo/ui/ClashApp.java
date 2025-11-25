package br.edu.ifsp.exemplo.ui;

import br.edu.ifsp.exemplo.core.Carta;
import br.edu.ifsp.exemplo.core.Deck;
import br.edu.ifsp.exemplo.core.Sistema;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;

public class
ClashApp extends Application {

    private GerenciadorDecks abasDecks;

    @Override
    public void start(Stage stage) {

        Sistema sistema = Sistema.getInstance();

        TabPane pagina = new TabPane();

        Tab pagCartas = new Tab("Cadastro");
        pagCartas.setClosable(false);

        //coleçao
        Tab pagColecao = new Tab("Coleção");
        pagColecao.setClosable(false);

        //Decks
        Tab pagDeck = new Tab("Decks");
        pagDeck.setClosable(false);

        //GerenciadorDecks e o que tem na aba
        abasDecks = new GerenciadorDecks();
        pagDeck.setContent(abasDecks); //o conteudo da aba

        pagina.getTabs().addAll(pagCartas, pagColecao, pagDeck);

        Cadastro telaCadastro = new Cadastro(null);
        pagCartas.setContent(telaCadastro.getLayout());

        // COLEÇÃO
        Colecao telaColecao = new Colecao();
        pagColecao.setOnSelectionChanged(ev -> {
            if (pagColecao.isSelected()){
                pagColecao.setContent(telaColecao.getLayout());
            }
        });


        Scene cena = new Scene(pagina, 900, 600);
        stage.setTitle("Clash-Royale");
        stage.setScene(cena);
        stage.show();
    }
}
