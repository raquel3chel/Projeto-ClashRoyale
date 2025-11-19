package br.edu.ifsp.exemplo.ui;

import br.edu.ifsp.exemplo.core.Carta;
import br.edu.ifsp.exemplo.core.TipoDeCarta;
import br.edu.ifsp.exemplo.core.Deck;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Cadastro {

    //deck das cartas que vão ser cadastradas
    private Deck deck;

    public Cadastro(Deck deck){
        this.deck = deck;
    }

    public void mostrar(Stage stage){

        Label novaCarta = new Label("Nova Carta");
        novaCarta.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label nomeCarta = new Label("Nome da carta: ");
        TextField txtNomeCarta = new TextField();

        Label nivelCarta = new Label("Nivel da carta: ");
        TextField txtNivel = new TextField();

        Label custoElixir = new Label("Custo de elixir: ");
        TextField txtcustoElixir = new TextField();

        Label tipoCarta = new Label("Tipo de carta: ");
        ComboBox<TipoDeCarta> tipo = new ComboBox<>();
        tipo.getItems().addAll(TipoDeCarta.values());

        Label raridade = new Label("Raridade: ");
        TextField txtRaridade = new TextField();

        Button cadastrar = new Button("Cadastrar");
        Label cadastro = new Label();

        //botao
        cadastrar.setOnAction(e -> {
            try {
                String nome = txtNomeCarta.getText();
                int nivel = Integer.parseInt(txtNivel.getText());
                int custo = Integer.parseInt(custoElixir.getText());
                TipoDeCarta tip = tipo.getValue();
                String rarid = txtRaridade.getText();

                if (nome.isEmpty() || tip == null || rarid.isEmpty()){
                    cadastro.setText("preencha todos os campos!");
                    return;
                }

                Carta carta = new Carta(nome, nivel, custo, tip, rarid);
                deck.adicionarCarta(carta);

                cadastro.setText("Carta cadastrada");

                txtNomeCarta.clear();
                txtNivel.clear();
                txtcustoElixir.clear();
                txtRaridade.clear();
                tipo.setValue(null);

            } catch (Exception ex){
                cadastro.setText("Não é válido");
            }
        });

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20));
        layout.setHgap(10);
        layout.setVgap(10);

        layout.add(novaCarta, 0, 0, 2, 1);

        layout.add(nomeCarta, 0, 1);
        layout.add(txtNomeCarta, 1, 1);

        layout.add(nivelCarta,0, 2);
        layout.add(txtNivel, 1,2);

        layout.add(custoElixir, 0, 3);
        layout.add(txtcustoElixir, 1, 3);

        layout.add(tipoCarta, 0, 4);
        layout.add(tipo, 1, 4);

        layout.add(raridade, 0, 5);
        layout.add(txtRaridade, 1, 5);

        layout.add(cadastrar, 0, 6);
        layout.add(cadastro, 1, 6);

        Scene scene = new Scene(layout, 400, 350);
        stage.setScene(scene);
        stage.setTitle("Cadastro de cartas");
        stage.show();










    }
}
