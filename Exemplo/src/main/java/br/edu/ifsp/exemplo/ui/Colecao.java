package br.edu.ifsp.exemplo.ui;

import br.edu.ifsp.exemplo.core.Carta;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;


public class Colecao {

    private final List<Carta> cartasIniciais;

    public Colecao(List<Carta> cartasIniciais){
        this.cartasIniciais = cartasIniciais;
    }

    public VBox getLayout(){
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label colecao = new Label("Coleção de cartas");
        colecao.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ListView<String> lista = new ListView<>();
        atualizarLista(lista);

        root.getChildren().addAll(colecao, lista);

        return root;
    }

    private void atualizarLista(ListView<String> lista){
        lista.getItems().clear();
        for(Carta c : cartasIniciais){
            lista.getItems().add(
                    c.getNome() + " | Tipo: " + c.getTipoCarta() + " | Elixir: " + c.getCustElixir()
            );
        }
    }
}
