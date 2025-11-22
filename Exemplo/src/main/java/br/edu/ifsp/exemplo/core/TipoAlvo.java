package br.edu.ifsp.exemplo.core;

public enum TipoAlvo {
    TERRESTRE("Terrestre"),AEREO("Aéreo"),AMBOS("Ambos");

    private final String alvo;

    TipoAlvo (String vel){

        this.alvo = vel;
    }

    @Override
    public String toString(){
        return alvo;
    }
}
