import javafx.application.Application;
import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.GameManager;
import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.fcsm.GameContext;
import pt.isec.pa.tinypac.ui.gui.Mainjfx;
import pt.isec.pa.tinypac.ui.textui.GameUI;

public class Main {

    public static void main(String[] args) {
      /*
        GameEngine ga=new GameEngine();
        Game jogo=new Game(31,29);
        GameContext context=new GameContext(jogo);
        GameUI ui=new GameUI(context,ga);
        //ga.start(500);
        ga.registerClient((g,t) ->context.evolve(t) );
        ga.registerClient(ui);

        ga.waitForTheEnd();*/
        Application.launch(Mainjfx.class,args);

    }
}