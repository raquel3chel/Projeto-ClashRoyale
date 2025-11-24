package br.edu.ifsp.exemplo.ui;

import br.edu.ifsp.exemplo.core.Deck;
import br.edu.ifsp.exemplo.core.Sistema;
import br.edu.ifsp.exemplo.data.DeckCSV;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class GerenciadorDecks extends BorderPane {

    private final TabPane tabPaneDecks;

    public GerenciadorDecks(){
        tabPaneDecks = new TabPane();
        this.setCenter(tabPaneDecks);

        //titulo e botao para adicionar outros decks
        Label titulo = new Label("Meus Deks");
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

        Button btnAdicionar = new Button("+");
        btnAdicionar.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #f39c12; -fx-text-fill: white;");
        btnAdicionar.setOnAction(e -> adicionarNovoDeck());

        HBox topBox = new HBox(10, titulo, btnAdicionar);
        topBox.setPadding(new Insets(10, 10, 10, 20));
        topBox.setAlignment(Pos.CENTER_LEFT);

        this.setTop(topBox);

        carregarDecksExistentes();
    }

    private void carregarDecksExistentes(){
        tabPaneDecks.getTabs().clear();

        List<Deck> decks = Sistema.getInstance().getDecks();

        if(decks.isEmpty()){
            Sistema.getInstance().criarDeck("Deck 1");
            decks = Sistema.getInstance().getDecks();
        }

        for (int i =0; i < decks.size(); i++){
            adicionarAbaDeck(decks.get(i), i + 1);
        }
        if (!decks.isEmpty()){
            Sistema.getInstance().setDeckSelecionado(decks.get(0));
            tabPaneDecks.getSelectionModel().select(0);
        }

        tabPaneDecks.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != null){
                int index = tabPaneDecks.getTabs().indexOf(newTab);
                Deck deck = Sistema.getInstance().getDecks().get(index);
                Sistema.getInstance().setDeckSelecionado(deck);
            }
        });
    }

    private void adicionarAbaDeck(Deck deck, int num){
        Tab tab = new Tab(String.valueOf(num));
        tab.setClosable(false); //nao deixa as abas fecharem

        tab.setContent(new CadaDeck(deck));

        tabPaneDecks.getTabs().add(tab);
    }

    private void adicionarNovoDeck(){
        int prox = Sistema.getInstance().getDecks().size() + 1;
        String nomeDeck = "Deck" + prox;

        if (Sistema.getInstance().criarDeck(nomeDeck)) {

            Deck novoDeck = Sistema.getInstance().buscarDeck(nomeDeck);
            adicionarAbaDeck(novoDeck, prox);
            DeckCSV.salvar();

            tabPaneDecks.getSelectionModel().selectLast();
            Sistema.getInstance().setDeckSelecionado(novoDeck);
        }else {
            new Alert(Alert.AlertType.WARNING, "Esse deck ja existe").show();
        }
    }

    public void atualizarCadaDeck(){
        for (Tab tab : tabPaneDecks.getTabs()){
            if (tab.getContent() instanceof CadaDeck){
                Deck deck = Sistema.getInstance().getDecks().get(tabPaneDecks.getTabs().indexOf(tab));
                ((CadaDeck)tab.getContent()).atualizarGrid();
            }
        }
    }


}
