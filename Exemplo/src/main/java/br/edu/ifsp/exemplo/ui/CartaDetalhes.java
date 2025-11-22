package br.edu.ifsp.exemplo.ui;

import br.edu.ifsp.exemplo.core.Carta;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat; // Importação para formatar o Elixir

/**
 * Classe para mostrar os detalhes de cada item da ListView (Coleção).
 * Estende ListCell<Carta> e define o visual de Carta com imagem, texto e botoes Detalhes/Usar.
 */
public class CartaDetalhes extends ListCell<Carta> {
    private final HBox rootLayout;
    private final ImageView imageView;
    private final Label nomeLabel;
    private final Label nivelElixirLabel;
    private final Button btnDetalhes;
    private final Button btnUsar;

    // Formatador para o Elixir
    private static final DecimalFormat ELIXIR_FORMAT = new DecimalFormat("0.#");

    public CartaDetalhes() {
        // Inicialização dos componentes
        imageView = new ImageView();
        imageView.setFitWidth(60);
        imageView.setFitHeight(70);

        nomeLabel = new Label();
        nomeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #333333;");
        nivelElixirLabel = new Label();
        nivelElixirLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #666666;");

        btnDetalhes = new Button("Detalhes");
        btnUsar = new Button("Usar");

        // Estilização
        btnDetalhes.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        btnUsar.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");

        // Layout para as informações de texto
        VBox infoBox = new VBox(2, nomeLabel, nivelElixirLabel);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        // Layout horizontal principal
        rootLayout = new HBox(15, imageView, infoBox, btnDetalhes, btnUsar);
        rootLayout.setPadding(new Insets(10, 10, 10, 10));
        rootLayout.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(infoBox, Priority.ALWAYS);
    }

    @Override
    protected void updateItem(Carta carta, boolean empty) {
        super.updateItem(carta, empty);

        if (empty || carta == null) {
            setGraphic(null);
            setText(null);
        } else {
            // define os labels
            nomeLabel.setText(carta.getNome() + " (Nível " + carta.getNivel() + ")");
            // Formata Elixir
            nivelElixirLabel.setText("Elixir: " + ELIXIR_FORMAT.format(carta.getCustElixir()) + " | " + carta.getRaridade());

            // tenta carregar a imagem
            try {
                String imagePath = carta.getImagem();
                if (imagePath != null && !imagePath.isEmpty()) {
                    // carrega a imagem como recurso (do classpath: /imagens/...)
                    if (imagePath.startsWith("/imagens/")) {
                        Image img = new Image(getClass().getResourceAsStream(imagePath), 60, 70, true, true);
                        imageView.setImage(img);
                    } else {
                        // Se for um caminho absoluto (cadastrado pelo usuário), usa FileInputStream
                        Image img = new Image(new FileInputStream(imagePath), 60, 70, true, true);
                        imageView.setImage(img);
                    }
                } else {
                    imageView.setImage(null);
                }
            } catch (Exception e) {
                // Captura FileNotFoundException ou qualquer outro erro de carregamento (I/O)
                imageView.setImage(null);
                System.err.println("Erro ao carregar imagem para '" + carta.getNome() + "'. Verifique o caminho: " + carta.getImagem());
            }

            // 3. Lógica dos botões
            btnDetalhes.setOnAction(event -> {
                // Chama a sua classe Detalhes (janela separada)
                Detalhes.exibir(carta);
            });

            btnUsar.setOnAction(event -> {
                // TODO: Lógica para enviar a carta para o deck
                System.out.println("A carta '" + carta.getNome() + "' foi selecionada para uso no deck.");
            });

            setGraphic(rootLayout);
        }
    }
}