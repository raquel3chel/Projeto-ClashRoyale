package br.edu.ifsp.exemplo.ui;

import br.edu.ifsp.exemplo.core.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.w3c.dom.Text;

public class Cadastro {

    //deck das cartas que vão ser cadastradas
    private Deck deck;

    public Cadastro(Deck deck){
        this.deck = deck;
    }

    public GridPane getLayout(){

        Label novaCarta = new Label("Nova Carta");
        novaCarta.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label nomeCarta = new Label("Nome da carta: ");
        TextField txtNomeCarta = new TextField();

        Label nivelCarta = new Label("Nivel da carta: ");
        TextField txtNivel = new TextField();

        Label custoElixir = new Label("Custo de elixir: ");
        TextField txtcustoElixir = new TextField();

        Label dano = new Label("Dano:");
        TextField txtdano = new TextField();

        Label danoSeg = new Label("Dano por Segundo:");
        TextField txtdanoSeg = new TextField();

        Label vida = new Label("Pontos de Vida:");
        TextField txtvida = new TextField();

        Label alcance = new Label("Alcance da Carta:");
        TextField txtalcance = new TextField();

        Label velImpacto = new Label("Velocidade do Impacto");
        TextField txtImpac = new TextField();

        Label velocidade = new Label("Velocidade:");
        ComboBox<Velocidade> tipoVel = new ComboBox<>();
        tipoVel.getItems().addAll(Velocidade.values());

        Label tipoCarta = new Label("Tipo de carta: ");
        ComboBox<TipoDeCarta> tipoCar = new ComboBox<>();
        tipoCar.getItems().addAll(TipoDeCarta.values());

        Label tipoRaridade = new Label("Raridade:");
        ComboBox<Raridade> tipoRarid = new ComboBox<>();
        tipoRarid.getItems().addAll(Raridade.values());

        Label tipoAlvo = new Label("Tipo de Alvo");
        ComboBox<TipoAlvo> tipoAl = new ComboBox<>();
        tipoAl.getItems().addAll(TipoAlvo.values());

        Button cadastrar = new Button("Cadastrar");
        Label cadastro = new Label();

        //botao
        cadastrar.setOnAction(e -> {

            //verifica e mostra msg se tiver algum campo vazio
            if (txtNomeCarta.getText().trim().isEmpty() || txtNivel.getText().trim().isEmpty() ||
                    txtcustoElixir.getText().trim().isEmpty() || txtdano.getText().trim().isEmpty() ||
                    txtdanoSeg.getText().trim().isEmpty() || txtvida.getText().trim().isEmpty() ||
                    txtalcance.getText().trim().isEmpty() || txtImpac.getText().trim().isEmpty() ||
                    tipoCar.getValue() == null|| tipoRarid.getValue() == null || tipoAl.getValue() == null ||
                    tipoVel.getValue() == null
                    ){

                cadastro.setText("Preencha todos os campos!");
                return;
            }

            try {

                int nivel = Integer.parseInt(txtNivel.getText());
                int custo = Integer.parseInt(txtcustoElixir.getText());
                int danoCar = Integer.parseInt(txtdano.getText());
                int danoS = Integer.parseInt(txtdanoSeg.getText());
                int pontosVida = Integer.parseInt(txtvida.getText());
                double alc = Double.parseDouble(txtalcance.getText());
                double impac = Double.parseDouble(txtImpac.getText());
                String nomeCar = txtNomeCarta.getText().trim();

                TipoDeCarta tip = tipoCar.getValue();
                Raridade ra = tipoRarid.getValue();
                TipoAlvo alvo = tipoAl.getValue();
                Velocidade veloc = tipoVel.getValue();

                //cria a carta
                Carta carta = new Carta(nomeCar, nivel, custo, tip, ra, alvo, danoCar, danoS, pontosVida, alc, veloc, impac);

                boolean ok = Sistema.getInstance().cadastrarCarta(carta);

                if(!ok) {
                    cadastro.setText("Essa carta já existe");
                    return;
                }

                cadastro.setText("Carta cadastrada");

                txtNomeCarta.clear();
                txtNivel.clear();
                txtcustoElixir.clear();
                txtalcance.clear();
                txtdanoSeg.clear();
                txtdano.clear();
                txtvida.clear();
                txtImpac.clear();
                tipoRarid.setValue(null);
                tipoCar.setValue(null);
                tipoAl.setValue(null);
                tipoVel.setValue(null);

            } catch (NumberFormatException ex){
                cadastro.setText("Nível e Custo devem ser números inteiros");
                return;
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

        layout.add(dano,0, 4);
        layout.add(txtdano, 1,4);

        layout.add(danoSeg,0, 5);
        layout.add(txtdanoSeg, 1,5);

        layout.add(vida,0, 6);
        layout.add(txtvida, 1,6);

        layout.add(alcance,0, 7);
        layout.add(txtalcance, 1,7);

        layout.add(velImpacto,0, 8);
        layout.add(txtImpac, 1,8);

        layout.add(tipoCarta, 0, 9);
        layout.add(tipoCar, 1, 9);

        layout.add(tipoRaridade, 0, 10);
        layout.add(tipoRarid, 1, 10);

        layout.add(tipoAlvo,0, 11);
        layout.add(tipoAl, 1,11);

        layout.add(velocidade,0, 12);
        layout.add(tipoVel, 1,12);

        layout.add(cadastrar, 0, 13);
        layout.add(cadastro, 1, 13);

        return layout;
    }
}
