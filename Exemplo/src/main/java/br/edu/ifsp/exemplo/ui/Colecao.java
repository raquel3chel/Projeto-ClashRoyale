package br.edu.ifsp.exemplo.ui;

import br.edu.ifsp.exemplo.core.Carta;
import br.edu.ifsp.exemplo.core.Sistema;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

public class Colecao {

    public Colecao(){
    }

    public ScrollPane getLayout(){

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label colecao = new Label("Coleção de Cartas");
        colecao.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // usar a ListView
        ListView<Carta> lista = new ListView<>();

        // liga à lista observavel do Sistema
        lista.setItems(Sistema.getInstance().getCartas());

        //usa a  classe CartaDetalhes para desenhar cada item
        lista.setCellFactory(param -> new CartaDetalhes());

        // define a altura para que a lista seja visível
        lista.setPrefHeight(600);

        root.getChildren().addAll(colecao, lista);

        // retorna a ListView dentro do ScrollPane
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        return scroll;
    }
}