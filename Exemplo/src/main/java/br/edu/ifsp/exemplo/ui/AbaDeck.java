package br.edu.ifsp.exemplo.ui;

import br.edu.ifsp.exemplo.core.Carta;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AbaDeck {

    public static void exibir(Carta carta) {
        Stage stage = new Stage();
        stage.setTitle("Criar Deck");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        root.getChildren().add(new Label("Carta escolhida: " + carta.getNome()));
        root.getChildren().add(new Label("(Aqui você vai montar o deck com 8 cartas)"));

        stage.setScene(new Scene(root, 300, 200));
        stage.show();
    }
}
