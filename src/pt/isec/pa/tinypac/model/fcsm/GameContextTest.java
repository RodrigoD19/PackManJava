package pt.isec.pa.tinypac.model.fcsm;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import pt.isec.pa.tinypac.model.GameManager;
import pt.isec.pa.tinypac.model.data.Direcoes;
import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.fcsm.GameContext;
import pt.isec.pa.tinypac.model.fcsm.PacManStates;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe de testes
 * */
class GameContextTest {

    @Test
    void startgame() {
        var gamemacontex=new GameContext(new Game(31,29));
        assertEquals(PacManStates.LOADINGSTATE,gamemacontex.getstate());
    }

    @Test
    void gotostop(){
        var game=new GameManager();
        game.start();
        assertEquals(PacManStates.STOPSTATE,game.getstate());
    }
    @Test
    void startmoving(){
        var game=new GameContext(new Game(31,29));
        game.start();

        game.changedirection(Direcoes.ArrowRight);
        assertEquals(PacManStates.NOGHOSTSTATE,game.getstate());
    }

    @Test
    void pausecomeback(){
        var game =new GameContext(new Game(31,29));
        game.changeState(PacManStates.NOGHOSTSTATE);
        game.pause();
        game.resume();
        assertEquals(PacManStates.NOGHOSTSTATE,game.getstate());
    }
    @Test
    void morre(){
        var game=new Game(31,29);
        var gamecx=new GameContext(game);
        gamecx.start();
        game.setLives(0);
        assertNotEquals(PacManStates.ENDINGSTATE,gamecx.getstate());
    }
    @Test
    void vaimorrer(){
        var game=new Game(31,29);
        var gamecx=new GameContext(game);
        gamecx.start();
        gamecx.changeState(PacManStates.NORMALGAMESTATE);
        game.setLives(0);
        gamecx.evolve(2000);
        assertEquals(PacManStates.ENDINGSTATE,gamecx.getstate());

    }
    @Test
    void reiniciarjogo(){
        var game=new Game(31,29);
        var gamecx=new GameContext(game);
        gamecx.start();
        gamecx.changeState(PacManStates.NORMALGAMESTATE);
        game.setLives(0);
        gamecx.evolve(2000);
        gamecx.start();
        assertEquals(PacManStates.STOPSTATE,gamecx.getstate());
    }


}