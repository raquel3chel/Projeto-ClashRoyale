package br.edu.ifsp.exemplo.ui;

import br.edu.ifsp.exemplo.core.Carta;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

public class Detalhes {

    public static void exibir(Carta carta){
        Stage stage = new Stage();
        stage.setTitle("Detalhes - " + carta.getNome());

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        //imagem
        Image image = null;

        try {
            String caminho = carta.getImagem();

            // 1. Se for imagem interna (começa com "imagens/")
            if (caminho.startsWith("imagens/")) {
                String recurso = "/" + caminho;
                URL url = Detalhes.class.getResource(recurso);
                if (url != null) {
                    image = new Image(url.openStream());
                }
            }

            // 2. Se for arquivo externo
            if (image == null || image.isError()) {
                image = new Image(new FileInputStream(caminho));
            }

        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
        }

        ImageView imgView = new ImageView(image);
        imgView.setFitHeight(150);
        imgView.setFitWidth(150);


        Label nome = new Label("Nome: " + carta.getNome());
        Label nivel = new Label("Nível: " + carta.getNivel());
        Label elixir = new Label("Custo de Elixir: " + carta.getCustElixir());
        Label raridade = new Label("Raridade: " + carta.getRaridade());
        Label dano = new Label("Dano: " + carta.getDano());
        Label dps = new Label("Dano por Segundo: " + carta.getDanoSeg());
        Label vida = new Label("Vida: " + carta.getPontosVida());
        Label alcance = new Label("Alcance: " + carta.getAlcance());
        Label velocidade = new Label("Velocidade: " + carta.getVelocidade());
        Label velImpac = new Label("Velocidade de Impacto: " + carta.getVelocImpacto());
        Label tipoAlv = new Label("Tipo de Alvo: " + carta.getTipoAlvos());
        Label tipoCarta = new Label("Tipo de Carta: " + carta.getTipoCarta());


        root.getChildren().addAll(
                imgView, nome, nivel, elixir, raridade,
                dano, dps, vida, alcance, velocidade, velImpac, tipoAlv, tipoCarta
        );

        stage.setScene(new Scene(root, 400, 580));
        stage.show();
    }
}
