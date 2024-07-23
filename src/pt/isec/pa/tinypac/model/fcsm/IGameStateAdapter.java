package pt.isec.pa.tinypac.model.fcsm;

import pt.isec.pa.tinypac.model.data.Direcoes;

import java.io.Serializable;
/**
 * Interface dos estados
 * */
public interface IGameStateAdapter extends Serializable {// hipotese
    PacManStates getstate();
    void pause();
    void evolve(long time);
    void savegame();
    void startnormalgame();
    void eatspecialBall();
    void endvulnerable();


    void start();
    void resume();


    void changedirection(Direcoes direcoes);

}
