package pt.isec.pa.tinypac.model.fcsm.states;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.fcsm.GameContext;
import pt.isec.pa.tinypac.model.fcsm.GameStateAdapter;
import pt.isec.pa.tinypac.model.fcsm.PacManStates;
/**
 * Classe para tratar do fim de jogo
 * */
public class EndingState extends GameStateAdapter {
    public EndingState(GameContext context, Game game) {
        super(context, game);
    }

    @Override
    public void evolve(long time) {
        System.out.println("Ta aqui");
    }
    /**
     * Recomeça jogo sem passar pelo loading state
     * */
    @Override
    public void start() {
        jogo.initGame();
        changestate(PacManStates.STOPSTATE);
    }

    @Override
    public PacManStates getstate() {
        return PacManStates.ENDINGSTATE;
    }
}
