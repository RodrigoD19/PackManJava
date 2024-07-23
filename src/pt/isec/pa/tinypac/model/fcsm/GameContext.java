package pt.isec.pa.tinypac.model.fcsm;


import org.junit.Test;
import pt.isec.pa.tinypac.model.data.Direcoes;
import pt.isec.pa.tinypac.model.data.Game;

import java.io.Serializable;
/**
 * Classe que faz a gestao dos estados bem como fornece metodos para o gamemanager atuar com a ui
 * */

public class GameContext implements Serializable {
    private static final long serialVersionUID = 1L;
    private Game jogo;

    private long interval;
    private IGameStateAdapter curentstate;
    // private Itotalstate os outros estados

    public GameContext( Game jogo) {
        this.jogo = jogo;
        curentstate=PacManStates.LOADINGSTATE.novoestdado(this,jogo,null);
    }
    /**
     * Metodo que muda o estado, package private
     * */
    void changeState(PacManStates newstate){
        PacManStates aux=curentstate.getstate();
        curentstate=newstate.novoestdado(this,jogo,aux);
    }
    public PacManStates getstate(){

        return curentstate.getstate();


    }
    /**
     * Pausa o jogo
     * */
    public void pause(){
        System.out.println("ALOOO");
        curentstate.pause();
    }

    public char[][] gettab(){
        return jogo.gettab();
    }
    public void changedirection(Direcoes direcoes){
     curentstate.changedirection(direcoes);
    }

    public void resume(){
        curentstate.resume();
    }
    public  int getpontuacao(){
       return jogo.getPontuacao();

    }
    public int getlives(){
        return jogo.getlives();
    }
    public boolean getbesttop(){
        return jogo.bestponis();
    }
    public void inserttop(String name){
        jogo.inserttop(name);
    }
    /**
     * Funcao stop
     * */
    public void stop(){
        changeState(PacManStates.LOADINGSTATE);
    }
    public void start(){curentstate.start();}

    public void evolve( long currentTime) {

    //   System.out.println("O current state e"+curentstate+"o current time e"+currentTime);
        curentstate.evolve(currentTime);
    }
}