package br.edu.ifsp.exemplo.core;

public enum TipoDeCarta {
    TROPA("Tropa"), FEITICO("Feitiço"), CONSTRUCAO("Contrução");

    private final String descricao;

    TipoDeCarta(String descricao){
        this.descricao = descricao;
    }

    @Override
    public String toString(){
        return descricao;
    }

}
