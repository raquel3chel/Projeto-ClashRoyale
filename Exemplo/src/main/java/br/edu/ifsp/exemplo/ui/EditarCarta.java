package br.edu.ifsp.exemplo.ui;

import br.edu.ifsp.exemplo.core.Carta;
import br.edu.ifsp.exemplo.core.Sistema;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditarCarta{

    public static void exibir(Carta carta) {
        Stage janela = new Stage();
        janela.setTitle("Editar Carta - " + carta.getNome());
        janela.initModality(Modality.APPLICATION_MODAL);

        TextField nome = new TextField(carta.getNome());
        TextField nivel = new TextField(String.valueOf(carta.getNivel()));
        TextField elixir = new TextField(String.valueOf(carta.getCustElixir()));
        TextField dano = new TextField(String.valueOf(carta.getDano()));
        TextField dps = new TextField(String.valueOf(carta.getDanoSeg()));
        TextField vida = new TextField(String.valueOf(carta.getPontosVida()));
        TextField alcance = new TextField(String.valueOf(carta.getAlcance()));
        TextField impacto = new TextField(String.valueOf(carta.getVelocImpacto()));

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setStyle("-fx-background-color: #2ecc71; -fx-text-fill:white; -fx-font-size:14;");

        btnSalvar.setOnAction(e -> {
            carta.setNome(nome.getText());
            carta.setNivel(Integer.parseInt(nivel.getText()));
            carta.setCustElixir(Double.parseDouble(elixir.getText()));
            carta.setDano(Integer.parseInt(dano.getText()));
            carta.setDanoSeg(Integer.parseInt(dps.getText()));
            carta.setPontosVida(Integer.parseInt(vida.getText()));
            carta.setAlcance(Double.parseDouble(alcance.getText()));
            carta.setVelocImpacto(Double.parseDouble(impacto.getText()));

            Sistema.getInstance().salvar();
            janela.close();
        });

        VBox layout = new VBox(
                new Label("Nome"), nome,
                new Label("Nível"), nivel,
                new Label("Elixir"), elixir,
                new Label("Dano"), dano,
                new Label("Dano/s"), dps,
                new Label("Vida"), vida,
                new Label("Alcance"), alcance,
                new Label("Velocidade impacto"), impacto,
                btnSalvar
        );

        layout.setPadding(new Insets(10));
        layout.setSpacing(8);

        Scene cena = new Scene(layout, 300, 580);
        janela.setScene(cena);
        janela.showAndWait();
    }
}
