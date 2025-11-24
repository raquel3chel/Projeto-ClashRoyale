package br.edu.ifsp.exemplo.data;

import br.edu.ifsp.exemplo.core.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSV {
    private static final String ARQUIVO = "cartas.csv";
    private static final String SEPARADOR = ";"; // separador ';'

    //grava as cartas
    public static void salvaCarta(List<Carta> cartas){
        try(PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO))){

            writer.println("nome;nivel;elixir;tipo;raridade;imagem;alvo;dano;danoSeg;vida;alcance;velocidade;velocidadeImpacto");

            for(Carta c : cartas){
                writer.println( c.getNome() + SEPARADOR + c.getNivel() + SEPARADOR + c.getCustElixir() + SEPARADOR + // Custo Elixir é double
                        c.getTipoCarta().name() + SEPARADOR + c.getRaridade().name() + SEPARADOR + c.getImagem() + SEPARADOR +
                        c.getTipoAlvos().name() + SEPARADOR + c.getDano() + SEPARADOR + c.getDanoSeg() + SEPARADOR +
                        c.getPontosVida() + SEPARADOR + c.getAlcance() + SEPARADOR + c.getVelocidade().name() + SEPARADOR +
                        c.getVelocImpacto()
                );
            }
        } catch (IOException e){
            System.out.println("Erro ao salvar CSV: " + e.getMessage());
        }
    }

    //exclui a carta do CSV
    public static void excluirCarta(Carta excluir){
        List<Carta> cartas = carregarCartas();

        cartas.removeIf(c -> c.getNome().equalsIgnoreCase(excluir.getNome()));

        salvaCarta(cartas);

    }

    //le as cartas
    public static List<Carta>carregarCartas(){
        List<Carta> cartas = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))){
            reader.readLine();//pula o titulo

            String linha;
            while((linha = reader.readLine()) != null) {
                String[] dados = linha.split(SEPARADOR);

                // 11 atributos e Imagem
                if (dados.length != 13) {
                    System.out.println("Linha inválida no CSV, esperado 13 campos, ignorada: " + linha);
                    continue;
                }

                try {
                    Carta c = new Carta(
                            dados[0],
                            Integer.parseInt(dados[1]), Double.parseDouble(dados[2]),
                            TipoDeCarta.valueOf(dados[3]), Raridade.valueOf(dados[4]),
                            dados[5], TipoAlvo.valueOf(dados[6]),
                            Integer.parseInt(dados[7]), Integer.parseInt(dados[8]),
                            Integer.parseInt(dados[9]), Double.parseDouble(dados[10]),
                            Velocidade.valueOf(dados[11]), Double.parseDouble(dados[12])
                    );
                    cartas.add(c);
                } catch (Exception e) {
                    System.out.println("Erro ao converter linha do CSV: " + linha + ". Erro: " + e.getMessage());
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("Arquivo CSV não encontrado. Um novo sera criado");
        }catch (IOException e){
            System.out.println("Erro ao ler CSV: " + e.getMessage());
        }
        return cartas;
    }


}