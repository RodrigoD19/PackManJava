package pt.isec.pa.tinypac.model.data;

import java.io.Serializable;
import java.util.Comparator;
/**
 * Classe auxiliar para o top5
 * */

public class LogTop implements Serializable, Comparator<LogTop> {
    String nome;
    int pontos;

    public LogTop(String nome, int pontos) {
        this.nome = nome;
        this.pontos = pontos;
    }

    public LogTop() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }


    @Override
    public int compare(LogTop o1, LogTop o2) {
        return Integer.compare(o2.pontos, o1.pontos);
    }
}
