package br.edu.ifsp.exemplo.core;

public enum Velocidade {
    LENTA("Lenta"), MEDIA("Média"), RAPIDA("Rápida"), MUITO_RAPIDA("Muito Rápida");

    private final String vel;

    Velocidade (String vel){
        this.vel = vel;
    }

    @Override
    public String toString(){
        return vel;
    }
}
