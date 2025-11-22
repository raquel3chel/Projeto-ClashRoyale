package br.edu.ifsp.exemplo.ui;

import br.edu.ifsp.exemplo.core.Carta;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class Detalhes {

    public static void exibir(Carta carta){
        Stage stage = new Stage();
        stage.setTitle("Detalhes -" + carta.getNome());

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        //imagem
        ImageView img = new ImageView(new Image(Detalhes.class.getResourceAsStream(
                "/imagens/" + carta.getNome().toLowerCase().replace(" ", " ") + ".png"))
        );
        img.setFitHeight(150);
        img.setFitWidth(150);

        root.getChildren().add(img);

        root.getChildren().add(new Label("Nome: " + carta.getNome()));
        root.getChildren().add(new Label("Tipo: " + carta.getTipoCarta()));
        root.getChildren().add(new Label("Raridade: " + carta.getRaridade()));
        root.getChildren().add(new Label("Custo de Elixir: " + carta.getCustElixir()));
        root.getChildren().add(new Label("Alvo: " + carta.getTipoAlvos()));
        root.getChildren().add(new Label("Dano: " + carta.getDano()));
        root.getChildren().add(new Label("Dano/S: " + carta.getDanoSeg()));
        root.getChildren().add(new Label("Vida: " + carta.getPontosVida()));

        stage.setScene(new Scene(root, 300, 500));
        stage.show();
    }
}
