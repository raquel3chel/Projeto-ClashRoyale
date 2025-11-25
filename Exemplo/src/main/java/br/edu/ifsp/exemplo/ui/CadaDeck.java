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
import javafx.scene.control.Button;

public class CadaDeck extends VBox {
    private final Deck deck;
    private final GridPane cartaGrid;
    private final Label custoMedio;
    private static final DecimalFormat ELIXIR = new DecimalFormat("0.0");

    public CadaDeck(Deck deck) {
        this.deck = deck;
        this.setSpacing(10);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.TOP_CENTER);

        //layout para as 8 cartas
        cartaGrid = new GridPane();
        cartaGrid.setHgap(30);
        cartaGrid.setVgap(25);
        cartaGrid.setAlignment(Pos.TOP_CENTER);

        //custo médio de elixir
        custoMedio = new Label();
        custoMedio.setStyle("-fx-font-weight: bold; -fx-font-size: 24px; -fx-text-fill: #9b59b6;"); // Cor roxa para Elixir

        //layout
        VBox mceBox = new VBox(custoMedio);
        mceBox.setAlignment(Pos.BOTTOM_CENTER);
        mceBox.setPadding(new Insets(30, 0, 0, 0));

        this.getChildren().addAll(cartaGrid, mceBox);

        // Inicializa o grid
        atualizarGrid();
    }

    public void atualizarGrid() {
        cartaGrid.getChildren().clear();
        List<Carta> cartas = deck.getCartas();

        //desenha as cartas
        for (int i = 0; i < Deck.LIMITE; i++) {

            VBox cardSlot = new VBox(5);
            cardSlot.setAlignment(Pos.CENTER);
            cardSlot.setPrefSize(100, 120);

            if (i < cartas.size()) {
                Carta carta = cartas.get(i);

                //visualização de carta
                ImageView imageView = new ImageView();
                imageView.setFitWidth(60);
                imageView.setFitHeight(70);

                //carregar imagem (usando a mesma logica robusta)
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
                    //se a imagem falhar, mostra o nome ou um X
                    //cardSlot.setStyle("-fx-border-color: red; -fx-border-width: 1; -fx-background-color: #fdd;");
                    cardSlot.getChildren().add(new Label("Erro"));
                    System.err.println("Erro ao carregar imagem no DeckView para " + carta.getNome());
                }
                //botao de excluir
                Button btnExcluir = new Button("Excluir");
                btnExcluir.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-font-size: 12;");

                btnExcluir.setOnAction(e -> {
                    deck.removerCarta(carta);
                    br.edu.ifsp.exemplo.data.DeckCSV.salvar();
                    atualizarGrid(); // atualiza na tela
                });

                Label elixirLabel = new Label(ELIXIR.format(carta.getCustElixir()));
                elixirLabel.setStyle("-fx-font-weight: bold; -fx-background-color: #9b59b6; -fx-text-fill: white; -fx-padding: 2 4; -fx-background-radius: 3;");

                cardSlot.getChildren().clear();
                cardSlot.getChildren().addAll(elixirLabel, imageView,btnExcluir);
                cardSlot.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 5;");


                final int indexPosicao = i;

                //quando começa a arrastar
                cardSlot.setOnDragDetected(event -> {
                    cardSlot.setStyle("-fx-border-color: #2980b9; -fx-border-width: 3; -fx-border-radius: 5; -fx-padding: 5;");

                    javafx.scene.input.Dragboard db = cardSlot.startDragAndDrop(javafx.scene.input.TransferMode.MOVE);
                    javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
                    content.putString(String.valueOf(indexPosicao));
                    db.setContent(content);

                    event.consume();

                });

                //quando passa por cima
                cardSlot.setOnDragOver(event -> {
                    if (event.getGestureSource() != cardSlot && event.getDragboard().hasString()) {
                        event.acceptTransferModes(javafx.scene.input.TransferMode.MOVE);

                        // Contorno verde
                        cardSlot.setStyle("-fx-border-color: #27ae60; -fx-border-width: 3; -fx-border-radius: 5; -fx-padding: 5;");
                    }
                    event.consume();
                });

                // Quando solta
                cardSlot.setOnDragDropped(event -> {
                    javafx.scene.input.Dragboard db = event.getDragboard();
                    boolean sucesso = false;

                    if (db.hasString()) {
                        int origem = Integer.parseInt(db.getString());
                        int destino = indexPosicao;

                        if (origem >= 0 && origem < cartas.size() && destino >= 0 && destino < cartas.size()) {
                            Carta temp = cartas.get(origem);
                            cartas.set(origem, cartas.get(destino));
                            cartas.set(destino, temp);

                            br.edu.ifsp.exemplo.data.DeckCSV.salvar();
                            atualizarGrid();
                        }

                        sucesso = true;
                    }

                    // Contorno roxo ao soltar
                    cardSlot.setStyle("-fx-border-color: #8e44ad; -fx-border-width: 3; -fx-border-radius: 5; -fx-padding: 5;");

                    event.setDropCompleted(sucesso);
                    event.consume();
                });

                // Quando o drag sai da carta
                cardSlot.setOnDragExited(event -> {
                    cardSlot.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 5;");
                });

            } else {
                // cada deck sem carta
                cardSlot.setStyle("-fx-border-color: #ddd; -fx-border-style: dashed; -fx-background-color: #eee;");
                cardSlot.getChildren().add(new Label("Vazio"));
            }

            cartaGrid.add(cardSlot, i % 4, i / 4); // 4 colunas e 2 linha
        }

        // Atualiza o custo médio
        custoMedio.setText(ELIXIR.format(deck.calcularCustoMedioElixir()));
    }
}

