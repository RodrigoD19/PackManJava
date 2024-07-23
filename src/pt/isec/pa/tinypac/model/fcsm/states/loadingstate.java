package pt.isec.pa.tinypac.model.fcsm.states;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.fcsm.GameContext;
import pt.isec.pa.tinypac.model.fcsm.GameStateAdapter;
import pt.isec.pa.tinypac.model.fcsm.PacManStates;
/**
 * Classe para dar load do jogo
 * */
public class loadingstate extends GameStateAdapter {
    private static int nlivespacman=3;

    public loadingstate(GameContext context, Game game) {
        super(context, game);

        //if(jogo.getlives()<3);


    }

    @Override
    public void start() {
        jogo.initGame();changestate(PacManStates.STOPSTATE);
    }

    @Override
    public void evolve(long time) {
            start();
       // if(time==-1)
          //  start();
    }

    @Override
    public PacManStates getstate() {
        return PacManStates.LOADINGSTATE;
    }
}
