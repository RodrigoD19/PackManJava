package pt.isec.pa.tinypac.model.fcsm.states;


import pt.isec.pa.tinypac.model.data.Direcoes;
import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.fcsm.GameContext;
import pt.isec.pa.tinypac.model.fcsm.GameStateAdapter;

import pt.isec.pa.tinypac.model.fcsm.PacManStates;
/**
 * Classe do EatGhostState estado de jogo
 * */
public class Eatghoststate extends GameStateAdapter {
    private static float timer=0;
    private long starttime=-1;
    private float timeelapsed=0;

    public Eatghoststate(GameContext context, Game game) {
        super(context, game);
        game.setCaneatghosts(true);
        timer=0;
    }
    /**
     * Pausa o jogo
     * */
    @Override
    public void pause() {
        changestate(PacManStates.PAUSEDSTATE);
    }

    @Override
    public PacManStates getstate() {
        return PacManStates.EATGHOSTSTATE;
    }

    /**
     * Permite mudança de direçao
     * */
    @Override
    public void changedirection(Direcoes keyType) {
        jogo.changedirectionpacman(keyType);
    }
    /**
     * faz andar o jogo e calcula o tempo que vai estar aqui
     * */
    @Override
    public void evolve(long time) {
        if(starttime==-1 ){
            starttime=time;
            System.out.println("time start "+starttime);
        }else if(timeelapsed ==0){
            timeelapsed=time-starttime;
            timeelapsed/=1_000_000_000;
            interval=timeelapsed;
            System.out.println("time elapsed "+timeelapsed);
        }else if(timeelapsed<20-(2*interval)){
            timeelapsed+=interval;
            jogo.evolveback();
            System.out.println("time elapsed "+timeelapsed);
            if(jogo.getlives()==0 || jogo.getCountballs()==0)
                changestate(PacManStates.ENDINGSTATE);
        }else{
            System.out.println("O timer ficou com"+interval);
            changestate(PacManStates.NORMALGAMESTATE);
        }
    }
}
