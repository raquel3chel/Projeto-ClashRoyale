package br.edu.ifsp.exemplo.core;

public class Carta {
    //atributos
    private String nome;
    private int nivel;
    private int custElixir; //quanto de elixir precisa pra usar a carta
    private TipoDeCarta tipoCarta;
    private String raridade;
    private String imagem;
    private int dano;
    private int danoSeg;
    private int pontosVida;
    private String tipoAlvos;
    private Double alcance;
    private String velocidade;
    private String velocImpacto;

    //contrutores
    public Carta(String nome, int nivel, int custElixir, TipoDeCarta tipoCarta, String raridade){
        this.nome = nome;
        this.nivel = nivel;
        this.custElixir = custElixir;
        this.tipoCarta = tipoCarta;
        this.raridade = raridade;
    }

    //
    public String getNome(){
        return  nome;
    }

    public int getCustElixir() {
        return custElixir;
    }

    public TipoDeCarta getTipoCarta(){
        return tipoCarta;
    }


    //cartas consideradas iguais/mesmo nome
    @Override
    public boolean equals(Object obj){
        if(this == obj) {
            return true;
        }
        if (!(obj instanceof Carta))
            return false;
        Carta outra = (Carta)obj;
        return this.nome.equalsIgnoreCase(outra.nome);

    }

    @Override
    public int hashCode(){
        return nome.toLowerCase().hashCode();
    }

    @Override
    public String toString(){
        return nome + "(" + tipoCarta + "," + custElixir + " elixir)";
    }
}
