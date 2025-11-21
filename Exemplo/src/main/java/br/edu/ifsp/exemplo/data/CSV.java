package br.edu.ifsp.exemplo.data;

import br.edu.ifsp.exemplo.core.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSV {
    private static final String ARQUIVO = "cartas.csv";

    //grava as cartas
    public static void salvaCarta(List<Carta> cartas){
        try(PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO))){

            writer.println("nome;nivel;elixir;tipo;raridade;alvo;dano;danoSeg;vida;alcance;velocidade;velocidadeImpacto");

            for(Carta c : cartas){
                writer.println( c.getNome() + ";" + c.getNivel() + ";" + c.getCustElixir() + ";" + c.getTipoCarta() + ";" +
                        c.getRaridade() + ";" + c.getTipoAlvos() + ";" + c.getDano() + ";" + c.getDanoSeg() + ";" +
                        c.getPontosVida() + ";" + c.getAlcance() + ";" + c.getVelocidade() + ";" + c.getVelocImpacto()
                );
            }
        } catch (IOException e){
            System.out.println("Erro ao salvar CSV" + e.getMessage());
        }
    }

    //le as cartas
    public static List<Carta>carregarCartas(){
        List<Carta> cartas = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))){
            String linha = reader.readLine();//pula o titulo

            while((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");

                if (dados.length != 12) {
                    System.out.println("Linha inválida no CSV, ignorada: " + linha);
                    continue;
                }

                try {
                    Carta c = new Carta(dados[0], Integer.parseInt(dados[1]), Integer.parseInt(dados[2]),
                            TipoDeCarta.valueOf(dados[3]), Raridade.valueOf(dados[4]), TipoAlvo.valueOf(dados[5]),
                            Integer.parseInt(dados[6]), Integer.parseInt(dados[7]), Integer.parseInt(dados[8]),
                            Double.parseDouble(dados[9]), Velocidade.valueOf(dados[10]), Double.parseDouble(dados[11])
                    );
                    cartas.add(c);
                } catch (Exception e) {
                    System.out.println("Erro ao converter linha do CSV: " + linha);
                }
            }
        }catch (FileNotFoundException e){
                    System.out.println("Arquivo CSV não encontrado");
        }catch (IOException e){
            System.out.println("Erro ao ler CSV: " + e.getMessage());
        }
        return cartas;
    }


}
