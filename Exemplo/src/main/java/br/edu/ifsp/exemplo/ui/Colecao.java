package br.edu.ifsp.exemplo.ui;

import br.edu.ifsp.exemplo.core.Carta;
import br.edu.ifsp.exemplo.core.Sistema;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;


public class Colecao {

    public Colecao(){
    }

    public VBox getLayout(){
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label colecao = new Label("Coleção de cartas");
        colecao.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ListView<Carta> lista = new ListView<>();
        lista.setItems(Sistema.getInstance().getCartas());

        lista.setCellFactory(new Callback<ListView<Carta>, ListCell<Carta>>() {
            @Override
            public ListCell<Carta> call(ListView<Carta> param) {
                return new ListCell<Carta>(){
                    @Override
                    protected void updateItem(Carta item, boolean empty){
                        super.updateItem(item,empty);
                        if(empty || item == null){
                            setText(null);
                        }else{
                            setText(item.getNome() + " - " + item.getRaridade() + " (" + item.getCustElixir() + "elixir)");
                        }
                    }
                };
            }
        });
        root.getChildren().addAll(colecao, lista);

        return root;
    }

}
