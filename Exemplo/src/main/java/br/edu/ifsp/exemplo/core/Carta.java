package br.edu.ifsp.exemplo.core;

public class Carta {
    //atributos
    private String nome;
    private int nivel;
    private double custElixir; //quanto de elixir precisa pra usar a carta
    private TipoDeCarta tipoCarta;
    private Raridade raridade;
    private String imagem;
    private int dano;
    private int danoSeg;
    private int pontosVida;
    private TipoAlvo tipoAlvos;
    private double alcance;
    private Velocidade velocidade;
    private double velocImpacto;

    //contrutores
    public Carta(String nome, int nivel, double custElixir, TipoDeCarta tipoCarta, Raridade raridade, TipoAlvo alvo,
                 int dano, int danoSeg, int pontosVida, double alcance, Velocidade velocidade, double velocImpacto){
        this.nome = nome;
        this.nivel = nivel;
        this.custElixir = custElixir;
        this.tipoCarta = tipoCarta;
        this.raridade = raridade;
        this.tipoAlvos = alvo;
        this.danoSeg = danoSeg;
        this.dano = dano;
        this.pontosVida = pontosVida;
        this.alcance = alcance;
        this.velocidade = velocidade;
        this.velocImpacto = velocImpacto;
    }

    //
    public String getNome(){

        return  nome;
    }

    public int getNivel() {
        return nivel;
    }

    public double getCustElixir() {

        return custElixir;
    }

    public TipoDeCarta getTipoCarta(){

        return tipoCarta;
    }

    public Raridade getRaridade(){

        return raridade;
    }

    public int getDano(){

        return dano;
    }

    public int getDanoSeg() {

        return danoSeg;
    }

    public int getPontosVida() {

        return pontosVida;
    }

    public double getAlcance() {

        return alcance;
    }

    public TipoAlvo getTipoAlvos(){

        return tipoAlvos;
    }

    public Velocidade getVelocidade(){

        return velocidade;
    }

    public double getVelocImpacto() {

        return velocImpacto;
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
