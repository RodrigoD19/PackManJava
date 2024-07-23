package pt.isec.pa.tinypac.model.fcsm.states;


import pt.isec.pa.tinypac.model.data.Direcoes;
import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.fcsm.GameContext;
import pt.isec.pa.tinypac.model.fcsm.GameStateAdapter;

import pt.isec.pa.tinypac.model.fcsm.PacManStates;
/**
 * Classe para por o jogo a andar durante 5 segundos sem os fantasmas mexerem
 * */
public class Noghoststate extends GameStateAdapter {


    private static final long serialVersionUID = 1L;
    private static long starttime=-1;
    private static float timeelapsed=0;


    public Noghoststate(GameContext context, Game game) {
        super(context, game);//timecount=0;
        System.out.println("ENTrou no no ghost");
        timeelapsed=0;
        starttime=-1;

    }

    @Override
    public void pause() {
        changestate(PacManStates.PAUSEDSTATE);
    }

    @Override
    public PacManStates getstate() {
        return PacManStates.NOGHOSTSTATE;
    }

    @Override
    public void changedirection(Direcoes keyType) {
        jogo.changedirectionpacman(keyType);
    }



    @Override
    public void startnormalgame() {
        changestate(PacManStates.NORMALGAMESTATE);
    }

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
        }else if(timeelapsed<5-(2*interval)){
            timeelapsed+=interval;
            jogo.evolveinitialstate();
         System.out.println("time elapsed "+timeelapsed);
        }else{
            System.out.println("O timer ficou com"+interval);
            changestate(PacManStates.NORMALGAMESTATE);
        }

        //jogo.evolve();
    }


}
