package pt.isec.pa.tinypac.model.fcsm.states;

import pt.isec.pa.tinypac.model.data.Direcoes;
import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.fcsm.GameContext;
import pt.isec.pa.tinypac.model.fcsm.GameStateAdapter;
import pt.isec.pa.tinypac.model.fcsm.PacManStates;
/**
 * Classe que aguarda tecla do player para passar ao estado seguinte
 * */
public class Stopstate extends GameStateAdapter {
    private static final long serialVersionUID = 1L;
    public Stopstate(GameContext context, Game game) {
        super(context, game);
    }

    @Override
    public void changedirection(Direcoes keyType) {
        jogo.changedirectionpacman(keyType);
        changestate(PacManStates.NOGHOSTSTATE);
    }

    @Override
    public void evolve(long time) {
        
    }

    @Override
    public PacManStates getstate() {
        return PacManStates.STOPSTATE;
    }
}
