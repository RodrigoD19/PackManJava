package pt.isec.pa.tinypac.model;

import javafx.scene.input.KeyCode;
import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.data.Direcoes;
import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.fcsm.GameContext;
import pt.isec.pa.tinypac.model.fcsm.PacManStates;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;

/**
 * Classe Responsável pela ligação UI e jogo
 *
 * */
public class GameManager {
    private GameContext fsm;
    private GameEngine gameEngine;

    PropertyChangeSupport pcs;
    public static  String PROP_GAME = "_game_";
    public GameManager() {
        this.fsm = new GameContext(new Game(31,29));
        this.gameEngine=new GameEngine();
        this.pcs=new PropertyChangeSupport(this);
        gameEngine.registerClient((u,t)->evolve(t));


    }

    public  int getpontuacao(){
        return fsm.getpontuacao();
    }
    /**
     * Função que permite adicionar observables ao comportamento do manager
     *
     * */
    public void addPropertyChangeListener(String property,PropertyChangeListener listener) {
        System.out.println("Novo listener");
        pcs.addPropertyChangeListener(property,listener);
    }
    /**
     * Função que inicia tudo
     * */
    public void start(){
        gameEngine.start(500);
        fsm.start();
        pcs.firePropertyChange(PROP_GAME,null,null);  
    }
    /**
     * Função que coloca o jogo em pausa
     *
     * */
    public  void pause(){
        fsm.pause();
        gameEngine.pause();
        pcs.firePropertyChange(PROP_GAME,null,null);
    }
    /**
     * Função que repõe o jogo depois de estar em pausa
     *
     * */
    public void resume(){
        fsm.resume();
        gameEngine.resume();
        pcs.firePropertyChange(PROP_GAME,null,null);

    }

    /**
     * Chamada no termino do jogo
     * */
    public void stop(){
        fsm.stop();
        gameEngine.stop();
        gameEngine.waitForTheEnd();
        pcs.firePropertyChange(PROP_GAME,null,null);
    }
    /**
     * Principal funcao do manager chama o evolve do context e da fire para avisar os listener
     * */
    public void evolve( long currentTime) {
        System.out.println("TA a fazer evolve");
        fsm.evolve(currentTime);
        pcs.firePropertyChange(PROP_GAME,null,null);
    }
    /**
     * Recebe um Keycode que ira converter depois para o
     * enum do direçoes permite o procesamento das setas
     * */
    public void changeDirection(KeyCode keyCode){
        Direcoes direcoes=null;
        switch (keyCode){
            case UP -> direcoes=Direcoes.ArrowUp;
            case DOWN -> direcoes=Direcoes.ArrowDown;
            case LEFT -> direcoes=Direcoes.ArrowLeft;
            case RIGHT -> direcoes=Direcoes.ArrowRight;
            default -> direcoes=Direcoes.ArrowUp;
        }
        fsm.changedirection(direcoes);
        pcs.firePropertyChange(PROP_GAME,null,null);
    }
    public PacManStates getstate(){
        return fsm.getstate();
    }
    public int getlives (){
       return fsm.getlives();
    }
    /**
     * Verifica se a pontuação do jogador é suficiente para entrar no top5
     * */
    public Boolean cantop(){
        return fsm.getbesttop();
    }
    /**
     * Caso tenha pontos necessarios para entrar no top 5 sera aqui introduzido o nome
     * */
    public void inserttop(String nome){
        fsm.inserttop(nome);
    }
    public char[][] getmaze(){
        return fsm.gettab();
    }
    /**
     * Permite carregar um jogo guardado
     * */
    public void load(File hfile){
        try (ObjectInputStream oos=new ObjectInputStream(
                new FileInputStream(hfile)
        )){
            fsm= (GameContext) oos.readObject();
            gameEngine.start(500);
        }
        catch (Exception e){
            e.printStackTrace();
            System.err.println("Error loading file");
            fsm=new GameContext(new Game(31,29));
            return ;
        }finally {
            pcs.firePropertyChange(PROP_GAME,null,null);// avisar que houve alteraçoes no objeto
        }

    }
    /**
     * Permite guardar jogo
     * */
    public void save(File hfile){
        try (ObjectOutputStream oos=new ObjectOutputStream(
                new FileOutputStream(hfile)
        )){
            oos.writeObject(fsm);
        }
        catch (Exception e){
            e.printStackTrace();
            System.err.println("Error saving file");
            return ;
        }

    }
}
