package pt.isec.pa.tinypac.model.data;

import java.io.Serializable;
/**
 * Classe base do Sapo e dos Ghosts permite os distinguir do resto do mapa
 * */
public class BaseClass implements IMazeElement, Serializable {
    protected Game jogo;
    protected BaseClass(Game newgame){
        jogo=newgame;
    }

    @Override
    public char getSymbol() {
        return 0;
    }
}
