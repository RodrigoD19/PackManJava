package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.GameManager;
import pt.isec.pa.tinypac.model.fcsm.GameContext;

public class Mainjfx extends Application {
    GameManager gameManager;




    @Override
    public void init() throws Exception {
        super.init();

        gameManager=new GameManager();





    }

    @Override
    public void start(Stage stage) throws Exception {

             RootPane rootPane=new RootPane(gameManager);
            Scene scene=new Scene(rootPane,700,700);
            stage.setScene(scene);
            stage.setTitle("Jogo do Pacman");
             stage.setMinWidth(700);
              stage.setMinHeight(400);
              rootPane.requestFocus();
              stage.show();
    }
}
