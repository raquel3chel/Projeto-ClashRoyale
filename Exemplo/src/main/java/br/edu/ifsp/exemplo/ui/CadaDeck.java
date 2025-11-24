package br.edu.ifsp.exemplo.ui;

import br.edu.ifsp.exemplo.core.Carta;
import br.edu.ifsp.exemplo.core.Deck;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.List;

public class CadaDeck extends VBox {
    private final Deck deck;
    private final GridPane cartaGrid;
    private final Label custoMedioLabel;
    private static final DecimalFormat ELIXIR_FORMAT = new DecimalFormat("0.0");

    public CadaDeck(Deck deck) {
        this.deck = deck;
        this.setSpacing(10);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.TOP_CENTER);

        // Layout para as 8 cartas
        cartaGrid = new GridPane();
        cartaGrid.setHgap(30);
        cartaGrid.setVgap(25);
        cartaGrid.setAlignment(Pos.TOP_CENTER);

        // Label para o custo médio de elixir
        custoMedioLabel = new Label();
        custoMedioLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 24px; -fx-text-fill: #9b59b6;"); // Cor roxa para Elixir

        // Layout para o MCE na parte inferior
        VBox mceBox = new VBox(custoMedioLabel);
        mceBox.setAlignment(Pos.BOTTOM_CENTER);
        mceBox.setPadding(new Insets(30, 0, 0, 0));

        this.getChildren().addAll(cartaGrid, mceBox);

        // Inicializa o grid
        atualizarGrid();
    }

    public void atualizarGrid() {
        cartaGrid.getChildren().clear();
        List<Carta> cartas = deck.getCartas();

        // Desenha as cartas
        for (int i = 0; i < Deck.LIMITE; i++) {
            VBox cardSlot = new VBox(5);
            cardSlot.setAlignment(Pos.CENTER);
            cardSlot.setPrefSize(100, 120);

            if (i < cartas.size()) {
                Carta carta = cartas.get(i);

                // Placeholder para visualização de carta
                ImageView imageView = new ImageView();
                imageView.setFitWidth(60);
                imageView.setFitHeight(70);

                // Tenta carregar imagem (usando a mesma lógica robusta)
                try {
                    String imagePath = carta.getImagem();
                    Image img = null;
                    if (imagePath != null && !imagePath.isEmpty()) {
                        if (!imagePath.startsWith("imagens/")) {
                            img = new Image(new FileInputStream(imagePath), 60, 70, true, true);
                        } else {
                            String resourceName = "/" + imagePath;
                            var resource = CadaDeck.class.getResource(resourceName);
                            if (resource != null) {
                                img = new Image(resource.openStream(), 60, 70, true, true);
                            }
                        }
                    }
                    imageView.setImage(img);
                } catch (Exception e) {
                    // Se a imagem falhar, mostra o nome ou um X
                    //cardSlot.setStyle("-fx-border-color: red; -fx-border-width: 1; -fx-background-color: #fdd;");
                    cardSlot.getChildren().add(new Label("Erro"));
                    System.err.println("Erro ao carregar imagem no DeckView para " + carta.getNome());
                }

                Label elixirLabel = new Label(ELIXIR_FORMAT.format(carta.getCustElixir()));
                elixirLabel.setStyle("-fx-font-weight: bold; -fx-background-color: #9b59b6; -fx-text-fill: white; -fx-padding: 2 4; -fx-background-radius: 3;");

                cardSlot.getChildren().clear();
                cardSlot.getChildren().addAll(elixirLabel, imageView);
                cardSlot.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 5;");

            } else {
                // cada deck sem carta
                cardSlot.setStyle("-fx-border-color: #ddd; -fx-border-style: dashed; -fx-background-color: #eee;");
                cardSlot.getChildren().add(new Label("Vazio"));
            }

            cartaGrid.add(cardSlot, i % 4, i / 4); // 2 colunas
        }

        // Atualiza o custo médio
        custoMedioLabel.setText(ELIXIR_FORMAT.format(deck.calcularCustoMedioElixir()));
    }
}

