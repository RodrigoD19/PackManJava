package pt.isec.pa.tinypac.ui.gui.UiStates;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.GameManager;
import pt.isec.pa.tinypac.model.fcsm.PacManStates;

import java.io.File;
/**
 * Classe de pausa
 * */
public class PausedStateUI extends BorderPane {
    GameManager gameManager;
    Button btnplay,btnsave,btnmenuinicial;


    public PausedStateUI(GameManager gameManager) {

        this.gameManager = gameManager;
        CreateViews();
        RegisterHandlers();
        Update();
    }
    /**
     * Cria elementos
     * */
    private void CreateViews() {
        btnmenuinicial=new Button("Voltar ao menu Inicial");
        btnplay=new Button("Voltar ao jogo");
        btnsave=new Button("Salvar jogo");
        btnmenuinicial.prefWidth(200);
        btnsave.prefWidth(200);
        btnplay.prefWidth(200);
        VBox vBox=new VBox(btnplay,btnsave,btnmenuinicial);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        setCenter(vBox);

    }
    /**
     * regisra handles de eventos
     * */
    private void RegisterHandlers() {
        gameManager.addPropertyChangeListener(GameManager.PROP_GAME,evt ->{

            Platform.runLater(()->Update());});
        btnplay.setOnMouseClicked(mouseEvent -> gameManager.resume());
        btnsave.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser=new FileChooser();//nova janela
            fileChooser.setTitle("File save...");
            fileChooser.setInitialDirectory(new File("."));// diretorio inicial
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Binario(*.bin)","*.bin")
                 //   new FileChooser.ExtensionFilter("All","*.*")//todo o tipo de ficheiros
            );
            File hfile=fileChooser.showSaveDialog(this.getScene().getWindow());
            if(hfile!=null)
            {
                gameManager.save(hfile);
            }
            });
        btnmenuinicial.setOnMouseClicked(mouseEvent -> gameManager.stop());

    }


    private void Update() {
        if(gameManager.getstate()== PacManStates.PAUSEDSTATE){
            this.setVisible(true);

        }else {
            this.setVisible(false);


        }
    }
}
