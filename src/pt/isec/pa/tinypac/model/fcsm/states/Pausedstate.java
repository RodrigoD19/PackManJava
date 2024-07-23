package pt.isec.pa.tinypac.model.fcsm.states;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.fcsm.GameContext;
import pt.isec.pa.tinypac.model.fcsm.GameStateAdapter;

import pt.isec.pa.tinypac.model.fcsm.PacManStates;
/**
 * Classe para pausar o jogo o evolve nao faz nada
 * */
public class Pausedstate extends GameStateAdapter {
    private static final long serialVersionUID = 1L;
    PacManStates preview;


    public Pausedstate(GameContext context, Game game,PacManStates preview) {
        super(context, game);
        this.preview=preview;

    }

    @Override
    public PacManStates getstate() {
        return PacManStates.PAUSEDSTATE;
    }

    @Override
    public void savegame() {
        super.savegame();
    }

    @Override
    public void resume() {
        System.out.println("RESUME");
        changestate(preview);
    }

    @Override
    public void evolve(long time) {
    }
}
