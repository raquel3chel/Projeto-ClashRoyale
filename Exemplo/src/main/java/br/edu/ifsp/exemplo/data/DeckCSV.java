package br.edu.ifsp.exemplo.data;

import br.edu.ifsp.exemplo.core.Carta;
import br.edu.ifsp.exemplo.core.Deck;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeckCSV {

    private static final String DECK = "decks.csv";
    private static final String SEP = ";";
    private static final List<Deck> decks = new ArrayList<>();


    public static List<Deck> getDecks() {
        return decks;
    }

    public static void salvar(){
        try(PrintWriter writer = new PrintWriter(new File(DECK))){

            writer.println("nome;carta1,carta2, carta3, carta4, carta5, carta7, carta8");

            for(Deck d : decks){

                StringBuilder builder = new StringBuilder();
                builder.append(d.getNome());

                List<Carta> lista = d.getCartas();

                for(int i = 0; i < 8; i++){
                    if(i < lista.size()){
                        builder.append(SEP).append(lista.get(i).getNome());
                    }else{
                        builder.append(SEP);
                    }
                }
                writer.println(builder.toString());
            }


        }catch (IOException e){
            System.out.println("Erro ao salvar" + e.getMessage());
        }
    }

    public static List<Deck> carregarDeck (List<Carta> cartas){
        decks.clear();
        File f = new File(DECK);

        if (!f.exists()) return decks;

        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {

            reader.readLine(); // descartar o cabeçalho

            String linha;
            while ((linha = reader.readLine()) != null) {

                String[] dados = linha.split(SEP, -1);

                if (dados.length == 0) continue;

                Deck decks1 = new Deck(dados[0]); // primeiro campo = nome do deck

                for (int i = 1; i < dados.length; i++) {

                    String nomeCarta = dados[i].trim();

                    if (nomeCarta.isEmpty()) continue;

                    Carta encontrada = cartas.stream()
                            .filter(c -> c.getNome().equalsIgnoreCase(nomeCarta))
                            .findFirst()
                            .orElse(null);

                    if (encontrada != null) {
                        decks1.adicionarCarta(encontrada);
                    }
                }

                decks.add(decks1);
            }

        } catch (IOException e) {
            System.out.println("Erro ao carregar decks: " + e.getMessage());
        }

        return decks;
    }


}
