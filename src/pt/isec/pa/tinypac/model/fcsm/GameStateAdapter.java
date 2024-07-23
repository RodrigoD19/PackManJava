package pt.isec.pa.tinypac.model.fcsm;


import pt.isec.pa.tinypac.model.data.Direcoes;
import pt.isec.pa.tinypac.model.data.Game;
/**
 * Classe Mae dos estados fornece implementações dos metodos evitando que sejam sempre aplicados em cada estado
 * */
public abstract class GameStateAdapter implements IGameStateAdapter {
    protected GameContext context;
    protected Game jogo;
    protected float interval;
    public GameStateAdapter(GameContext context, Game game) {
        this.context=context;
        this.jogo=game;
    }
    protected void changestate(PacManStates novo){
        context.changeState(novo);
    }

    @Override
    public void evolve(long time) {

    }

    @Override
    public void savegame() {

    }

    @Override
    public void changedirection(Direcoes keyType) {

    }


    @Override
    public void start() {

    }

    @Override
    public void resume() {

    }



    @Override
    public void startnormalgame() {

    }

    @Override
    public void endvulnerable() {
        
    }

    @Override
    public void eatspecialBall() {

    }

    @Override
    public void pause() {
        changestate(PacManStates.PAUSEDSTATE);
    }

    @Override
    public PacManStates getstate() {
        return null;
    }
}

