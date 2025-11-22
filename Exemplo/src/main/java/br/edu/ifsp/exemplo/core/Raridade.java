package br.edu.ifsp.exemplo.core;

public enum Raridade {
    COMUM("Comum"),RARA("Rara"),EPICA("Épica"),LENDARIA("Lendária");

    private final String raridade;

    Raridade (String raridade){

        this.raridade = raridade;
    }

    @Override
    public String toString(){
        return raridade;
    }
}
