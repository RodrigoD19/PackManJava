package pt.isec.pa.tinypac.model.fcsm.states;

import pt.isec.pa.tinypac.model.data.Direcoes;
import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.fcsm.GameContext;
import pt.isec.pa.tinypac.model.fcsm.GameStateAdapter;

import pt.isec.pa.tinypac.model.fcsm.PacManStates;
/**
 * Classe que ira correr a maior parte do tempo
 * */
public class NormalGamestate extends GameStateAdapter {

    private static final long serialVersionUID = 1L;
    public NormalGamestate(GameContext context, Game game) {
        super(context, game);


    }

    @Override
    public void pause() {
        changestate(PacManStates.PAUSEDSTATE);
    }

    @Override
    public PacManStates getstate() {
        return PacManStates.NORMALGAMESTATE;
    }


    @Override
    public void changedirection(Direcoes keyType) {
        jogo.changedirectionpacman(keyType);
    }

    @Override
    public void evolve(long time) {
        int aux=jogo.getNrpowerballs();

        jogo.evolve();
        if(jogo.getlives()==0 || jogo.getCountballs()==0)
            changestate(PacManStates.ENDINGSTATE);
        System.out.println("FEZ o o ending state");
        if(jogo.getNrpowerballs()<aux)
            changestate(PacManStates.EATGHOSTSTATE);
    }

}
