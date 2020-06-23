package br.com.codenation;

import java.util.Comparator;

class Comparador implements Comparator<Jogador > {
    @Override
    public int compare(Jogador o1, Jogador o2) {
        if (o1.getNivelHabilidade() > o2.getNivelHabilidade()) return -1;
        else if (o1.getNivelHabilidade() < o2.getNivelHabilidade()) return +1;
        else return 0;
    }
}
