package br.edu.ifsp.exemplo.ui;

import br.edu.ifsp.exemplo.core.Carta;
import br.edu.ifsp.exemplo.core.Deck;
import br.edu.ifsp.exemplo.core.Sistema;
import br.edu.ifsp.exemplo.data.CSV;
import br.edu.ifsp.exemplo.data.DeckCSV;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import java.io.FileInputStream;
import java.util.Optional;
import java.util.stream.Collectors;

public class CartaDetalhes extends ListCell<Carta> {
    private final HBox rootLayout;
    private final ImageView imageView;
    private final Label nomeLabel;
    private final Label nivelElixirLabel;
    private final Button btnDetalhes;
    private final Button btnUsar;
    private final Button btnExcluir;
    private final Button btnEditar;

    public CartaDetalhes() {
        // inicializaçao
        imageView = new ImageView();
        imageView.setFitWidth(60);
        imageView.setFitHeight(70);

        nomeLabel = new Label();
        nomeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #333333;");
        nivelElixirLabel = new Label();
        nivelElixirLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #666666;");

        btnDetalhes = new Button("Detalhes");
        btnUsar = new Button("Usar");
        btnExcluir = new Button("Excluir");
        btnEditar = new Button("Editar");

        // estilização dos botoes
        btnDetalhes.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        btnUsar.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        btnExcluir.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        btnEditar.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");

        // layout para as informações de texto
        VBox infoBox = new VBox(2, nomeLabel, nivelElixirLabel);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        // layout horizontal principal
        rootLayout = new HBox(15, imageView, infoBox, btnDetalhes, btnUsar,btnEditar, btnExcluir);
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
            nomeLabel.setText(carta.getNome());
            // formata Elixir
            nivelElixirLabel.setText( "Nível " + carta.getNivel()  );

            //tenta carregar a imagem
            try {
                Image img = null;
                String imagePath = carta.getImagem();

                if (imagePath != null && !imagePath.isEmpty()) {

                    // se for arquivo local criado pelo usuário
                    if (!imagePath.startsWith("imagens/")) {
                        img = new Image(new FileInputStream(imagePath));
                    }
                    else {
                        // IMAGENS internas da pasta resources/imagens/
                        String resourceName = "/" + imagePath;
                        var resource = CartaDetalhes.class.getResource(resourceName);

                        if (resource != null) {
                            img = new Image(resource.openStream(), 60, 70, true, true);
                        } else {
                            System.err.println("Imagem NÃO encontrada no resources: " + resourceName);
                        }
                    }
                }

                imageView.setPreserveRatio(true);
                imageView.setFitWidth(60);
                imageView.setFitHeight(70);

                imageView.setImage(img);

            } catch (Exception e) {
                imageView.setImage(null);
                System.err.println("Erro ao carregar imagem da carta '" + carta.getNome() +
                        "'. Caminho: " + carta.getImagem());
            }

            // logica dos botões
            //botao que chama a classe Detalhe para cada carta
            btnDetalhes.setOnAction(event -> {
                Detalhes.exibir(carta);
            });

            btnEditar.setOnAction(event -> {
                EditarCarta.exibir(carta); // vamos criar este arquivo depois
                getListView().refresh();
            });

            //botao de adicionar a carta ao deck
            btnUsar.setOnAction(event -> {

                java.util.List<Deck> decksLivres = Sistema.getInstance().getDecks();
                //Deck deckAtual = Sistema.getInstance().getDeckSelecionado();

                if (decksLivres.isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, "Crie um Deck antes de adicionar a carta").show();
                    return;
                }

                java.util.List<String> nomesDecks = decksLivres.stream()
                        .map(Deck::getNome)
                        .collect(Collectors.toList());

                // caixa de diálogo para escolha de deck
                ChoiceDialog<String> dialog = new ChoiceDialog<>(nomesDecks.get(0), nomesDecks);
                dialog.setTitle("Adicionar Carta ao Deck");
                dialog.setHeaderText(carta.getNome());
                dialog.setContentText("Selecione o Deck:");

                // processa a escolha da caixa
                Optional<String> result = dialog.showAndWait();

                result.ifPresent(deckEscolhido -> {
                    Deck deckAlvo = Sistema.getInstance().buscarDeck(deckEscolhido);

                    // verifica se o deck foi encontrado e se tem espaço
                    if (deckAlvo == null) return; // Não deve ocorrer se a lista for bem feita

                    if (deckAlvo.getCartas().size() >= Deck.LIMITE) {
                        new Alert(Alert.AlertType.ERROR, "O " + deckEscolhido + " já possui 8 cartas").show();
                        return;
                    }

                    // nao deixa repetir
                    if (deckAlvo.getCartas().contains(carta)) {
                        new Alert(Alert.AlertType.ERROR, "Esta carta já está no " + deckEscolhido).show();
                        return;
                    }

                    // adicionar e salvar
                    deckAlvo.adicionarCarta(carta);
                    DeckCSV.salvar();

                    new Alert(Alert.AlertType.INFORMATION, "A carta '" + carta.getNome() + "' foi adicionada ao: " + deckEscolhido).show();

                    // atualiza a visualização dos decks
                    Scene scene = btnUsar.getScene();
                    if (scene != null) {
                        javafx.scene.control.TabPane pagina = (javafx.scene.control.TabPane) scene.getRoot();
                        if (pagina != null) {
                            for (javafx.scene.control.Tab tab : pagina.getTabs()) {
                                if (tab.getText().equals("Decks") && tab.getContent() instanceof GerenciadorDecks) {
                                    ((GerenciadorDecks) tab.getContent()).atualizarCadaDeck();
                                    break;
                                }
                            }
                        }
                    }
                });
            });

            //botao de excluir a carta
            btnExcluir.setOnAction(event -> {

                getListView().getItems().remove(carta);
                CSV.excluirCarta(carta);

                DeckCSV.getDecks().forEach(d -> d.removerCarta(carta));
                DeckCSV.salvar();

                Scene scene = btnExcluir.getScene();
                if (scene != null) {
                    javafx.scene.control.TabPane pagina = (javafx.scene.control.TabPane) scene.getRoot();
                    if (pagina != null) {
                        for (javafx.scene.control.Tab tab : pagina.getTabs()) {
                            if (tab.getText().equals("Decks") && tab.getContent() instanceof GerenciadorDecks) {
                                ((GerenciadorDecks) tab.getContent()).atualizarCadaDeck();
                                break;
                            }
                        }
                    }
                }

            });

            setGraphic(rootLayout);
        }
    }
}