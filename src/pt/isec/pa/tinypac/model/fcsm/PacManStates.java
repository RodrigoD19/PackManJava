package pt.isec.pa.tinypac.model.fcsm;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.fcsm.states.*;
/**
 * Estados do pacman
 * */
public enum PacManStates {
  EATGHOSTSTATE,NOGHOSTSTATE,NORMALGAMESTATE,PAUSEDSTATE,LOADINGSTATE,STOPSTATE,ENDINGSTATE;
    /**
     * Fabrica de estados
     * */
    public  IGameStateAdapter novoestdado(GameContext context, Game game,PacManStates preview){
        return   switch (this){
            case LOADINGSTATE -> new loadingstate(context,game);
            case PAUSEDSTATE -> new Pausedstate(context,game,preview);
            case NOGHOSTSTATE -> new Noghoststate(context,game);
            case EATGHOSTSTATE -> new Eatghoststate(context,game);
            case ENDINGSTATE -> new EndingState(context, game);
            case NORMALGAMESTATE -> {System.out.println("CHEgou aqui");
                yield new NormalGamestate(context,game);
            }
            case STOPSTATE -> new Stopstate(context,game);

        };
    }

}
