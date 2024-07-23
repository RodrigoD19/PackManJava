package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.GameManager;
import pt.isec.pa.tinypac.model.fcsm.PacManStates;
import pt.isec.pa.tinypac.ui.gui.UiStates.*;

/**
 * Classe que alberga as uis
 * */
public class RootPane extends BorderPane {
    GameManager gameManager;
    StackPane pane;

    public RootPane(GameManager gameManager) {
        this.gameManager = gameManager;

        CreateViews();
        RegisterHandles();
        update();
    }

    private void CreateViews() {
        pane=new StackPane(
                new EatStateUI(gameManager),
                new PausedStateUI(gameManager),
                new EndingStateUI(gameManager),
                new NormalStateUi(gameManager),
                new menustateui(gameManager)
        );

       setCenter(pane);



    }

    private void RegisterHandles() {

        gameManager.addPropertyChangeListener(GameManager.PROP_GAME,evt -> Platform.runLater(()->update()));

    }

    private void update() {



    }
}
