package br.edu.ifsp.exemplo.data;

import br.edu.ifsp.exemplo.core.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSV {
    private static final String ARQUIVO = "cartas.csv";
    private static final String SEPARADOR = ";"; // Mantido o separador ';' que você usou

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

    //le as cartas
    public static List<Carta>carregarCartas(){
        List<Carta> cartas = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))){
            reader.readLine();//pula o titulo

            String linha;
            while((linha = reader.readLine()) != null) {
                String[] dados = linha.split(SEPARADOR);

                // CRÍTICO: Agora tem 13 campos (12 atributos + Imagem). O construtor tem 13 argumentos.
                if (dados.length != 13) {
                    System.out.println("Linha inválida no CSV, esperado 13 campos, ignorada: " + linha);
                    continue;
                }

                try {
                    // Mapeamento: 13 argumentos no construtor da Carta
                    Carta c = new Carta(
                            dados[0],
                            Integer.parseInt(dados[1]),
                            Double.parseDouble(dados[2]), // CORREÇÃO AQUI: Deve ser Double.parseDouble para Elixir
                            TipoDeCarta.valueOf(dados[3]),
                            Raridade.valueOf(dados[4]),
                            dados[5], // Imagem (String)
                            TipoAlvo.valueOf(dados[6]),
                            Integer.parseInt(dados[7]),
                            Integer.parseInt(dados[8]),
                            Integer.parseInt(dados[9]),
                            Double.parseDouble(dados[10]),
                            Velocidade.valueOf(dados[11]),
                            Double.parseDouble(dados[12])
                    );
                    cartas.add(c);
                } catch (Exception e) {
                    System.out.println("Erro ao converter linha do CSV: " + linha + ". Erro: " + e.getMessage());
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("Arquivo CSV não encontrado. Criando novo.");
        }catch (IOException e){
            System.out.println("Erro ao ler CSV: " + e.getMessage());
        }
        return cartas;
    }


}