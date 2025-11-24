package br.edu.ifsp.exemplo.ui;

import br.edu.ifsp.exemplo.core.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage; // Necessário para FileChooser
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Cadastro {

    // variaveis de instancia
    // TextFields
    private final TextField txtNomeCarta = new TextField();
    private final TextField txtNivel = new TextField();
    private final TextField txtCustoElixir = new TextField();
    private final TextField txtDano = new TextField();
    private final TextField txtDanoSeg = new TextField();
    private final TextField txtVida = new TextField();
    private final TextField txtAlcance = new TextField();
    private final TextField txtImpac = new TextField();
    private final TextField txtCaminhoImagem = new TextField();

    // ComboBoxes
    private final ComboBox<Velocidade> tipoVel = new ComboBox<>();
    private final ComboBox<TipoDeCarta> tipoCar = new ComboBox<>();
    private final ComboBox<Raridade> tipoRarid = new ComboBox<>();
    private final ComboBox<TipoAlvo> tipoAl = new ComboBox<>();

    // Outros Componentes
    private final Label lblMensagem = new Label();
    private final ImageView imageView = new ImageView();

    // O Deck é mantido, mas não usado na lógica de cadastro
    public Cadastro(Deck deck){
        // Inicialização da ImageView
        imageView.setFitWidth(40);
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);
        txtCaminhoImagem.setEditable(false);
    }

    private void handleBuscarImagem() {
        // Usa o stage da aplicação (getScene().getWindow())
        Stage stage = (Stage) txtCaminhoImagem.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Imagem da Carta");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Arquivos de Imagem", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            String imagePath = file.getAbsolutePath();
            txtCaminhoImagem.setText(imagePath);

            try {
                // Pré-visualização da imagem selecionada
                Image image = new Image(new FileInputStream(file), 50, 50, true, true);
                imageView.setImage(image);
            } catch (FileNotFoundException ex) {
                imageView.setImage(null);
                lblMensagem.setText("Erro ao carregar pré-visualização.");
            }
        }
    }

    private void limparCampos() {
        // some os dados dos campos ao criar a carta
        txtNomeCarta.clear();
        txtNivel.clear();
        txtCustoElixir.clear();
        txtDano.clear();
        txtDanoSeg.clear();
        txtVida.clear();
        txtAlcance.clear();
        txtImpac.clear();
        txtCaminhoImagem.clear();
        imageView.setImage(null);

        tipoRarid.setValue(null);
        tipoCar.setValue(null);
        tipoAl.setValue(null);
        tipoVel.setValue(null);

        lblMensagem.setText("");
        txtNomeCarta.requestFocus();
    }


    public GridPane getLayout(){

        // configuração dos comboBoxes
        tipoVel.getItems().addAll(Velocidade.values());
        tipoCar.getItems().addAll(TipoDeCarta.values());
        tipoRarid.getItems().addAll(Raridade.values());
        tipoAl.getItems().addAll(TipoAlvo.values());

        // botões e componentes de acao
        Button cadastrar = new Button("Cadastrar");

        Button btnBuscarImagem = new Button("Selecionar");
        btnBuscarImagem.setOnAction(e -> handleBuscarImagem());

        HBox imagemInputBox = new HBox(5, txtCaminhoImagem, btnBuscarImagem);

        // logica do cadastro

        cadastrar.setOnAction(e -> {
            String caminhoImagem = txtCaminhoImagem.getText().trim();

            // deixa os campos vazios depois de cadastrar a carta
            if (txtNomeCarta.getText().trim().isEmpty() || txtNivel.getText().trim().isEmpty() ||
                    txtCustoElixir.getText().trim().isEmpty() || txtDano.getText().trim().isEmpty() ||
                    txtDanoSeg.getText().trim().isEmpty() || txtVida.getText().trim().isEmpty() ||
                    txtAlcance.getText().trim().isEmpty() || txtImpac.getText().trim().isEmpty() ||
                    caminhoImagem.isEmpty() || // Validação da imagem
                    tipoCar.getValue() == null || tipoRarid.getValue() == null || tipoAl.getValue() == null ||
                    tipoVel.getValue() == null)
            {
                lblMensagem.setText("Preencha todos os campos, incluindo a imagem!");
                return;
            }

            try {
                // coleta e conversão de dados
                int nivel = Integer.parseInt(txtNivel.getText());
                int custo = Integer.parseInt(txtCustoElixir.getText());
                int danoCar = Integer.parseInt(txtDano.getText());
                int danoS = Integer.parseInt(txtDanoSeg.getText());
                int pontosVida = Integer.parseInt(txtVida.getText());
                double alc = Double.parseDouble(txtAlcance.getText());
                double impac = Double.parseDouble(txtImpac.getText());
                String nomeCar = txtNomeCarta.getText().trim();

                // Cria a carta
                Carta carta = new Carta(nomeCar, nivel, custo, tipoCar.getValue(), tipoRarid.getValue(),
                        caminhoImagem, tipoAl.getValue(), danoCar, danoS, pontosVida,
                        alc, tipoVel.getValue(), impac);

                boolean ok = Sistema.getInstance().cadastrarCarta(carta);

                if(!ok) {
                    lblMensagem.setText("Essa carta já existe");
                    return;
                }

                lblMensagem.setText("Carta cadastrada!");

                limparCampos();

            } catch (NumberFormatException ex){
                lblMensagem.setText("Nível, Custo, Dano, Vida e Impacto devem ser números válidos!");
            } catch (Exception ex) {
                lblMensagem.setText("Erro ao salvar carta: " + ex.getMessage());
                System.err.println("Erro ao cadastrar: " + ex.getMessage());
            }

        });

        // estrutura do layout

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20));
        layout.setHgap(10);
        layout.setVgap(10);

        int row = 0;

        Label titulo = new Label("Nova Carta");
        //layout.add(new Label("Nova Carta"), 0, row, 2, 1);
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 0 0 4 0;");
        titulo.setAlignment(Pos.CENTER_LEFT);
        GridPane.setHgrow(titulo, Priority.ALWAYS);
        layout.add(titulo, 0, row, 2, 1); // Pré-visualização
        row++;

        layout.add(new Label("Nome da carta:"), 0, row);
        layout.add(txtNomeCarta, 1, row++);

        layout.add(new Label("Nivel da carta:"),0, row);
        layout.add(txtNivel, 1,row++);

        layout.add(new Label("Custo de elixir:"), 0, row);
        layout.add(txtCustoElixir, 1, row++);

        layout.add(new Label("Dano:"),0, row);
        layout.add(txtDano, 1,row++);

        layout.add(new Label("Dano por Segundo:"),0, row);
        layout.add(txtDanoSeg, 1,row++);

        layout.add(new Label("Pontos de Vida:"),0, row);
        layout.add(txtVida, 1,row++);

        layout.add(new Label("Alcance da Carta:"),0, row);
        layout.add(txtAlcance, 1,row++);

        layout.add(new Label("Velocidade do Impacto"),0, row);
        layout.add(txtImpac, 1,row++);

        layout.add(new Label("Tipo de carta:"), 0, row);
        layout.add(tipoCar, 1, row++);

        layout.add(new Label("Raridade:"), 0, row);
        layout.add(tipoRarid, 1, row++);

        layout.add(new Label("Tipo de Alvo"),0, row);
        layout.add(tipoAl, 1,row++);

        layout.add(new Label("Velocidade:"),0, row);
        layout.add(tipoVel, 1,row++);

        // campo e botao da imagem
        layout.add(new Label("Arquivo da Imagem:"), 0, row);
        layout.add(imagemInputBox, 1, row++);

        layout.add(cadastrar, 0, row);
        layout.add(lblMensagem, 1, row);

        return layout;
    }
}