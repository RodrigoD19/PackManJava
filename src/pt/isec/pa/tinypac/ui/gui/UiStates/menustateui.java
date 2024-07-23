package pt.isec.pa.tinypac.ui.gui.UiStates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.GameManager;
import pt.isec.pa.tinypac.model.fcsm.PacManStates;

import java.io.File;
/**
 * Classe do Menu
 * */
public class menustateui extends BorderPane {
    GameManager gameManager;

    Button btn1,btn2,btn3,btn4;
    Image image;
    VBox vBox;
    Label lbl;

    public menustateui(GameManager gameManager) {

        this.gameManager = gameManager;
        CreateViews();
        RegisterHandlers();
        Update();
    }
    /**
     * Cria elementos
     * */
    private void CreateViews() {
        btn1=new Button("Iniciar Jogo");
        btn4=new Button("Carregar jogo");
        btn2=new Button("Ver Top 5");
        btn3=new Button("Sair");
        lbl=new Label();
        btn1.setMinWidth(100);
        btn2.setMinWidth(100);
        btn4.setMinWidth(100);
        btn3.setMinWidth(100);
        lbl.setAlignment(Pos.CENTER);
        lbl.setTextFill(Color.WHITE);
        lbl.setText("Rodrigo Duarte-Jogo do Pacman");   setTop(lbl);

        lbl=new Label("DEIS-ENGENHARIA INFORMATICA\nPROGRAMAÇAO AVANÇADA");
        lbl.setTextFill(Color.WHITE);
        setBottom(lbl);
        image=new Image("images/back.jpeg");
         vBox=new VBox(btn1,btn4,btn2,btn3);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        setCenter(vBox);
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
     //   setBackground(new Background(backgroundImage));
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));



    }
    /**
     * Regista handlers de eventos
     * */

    private void RegisterHandlers() {
        gameManager.addPropertyChangeListener(GameManager.PROP_GAME,evt ->{
            // Run listener logic on UI thread

            Platform.runLater(() ->{
                Update();
                    }
            );




        });
        btn1.setOnMouseClicked(mouseEvent ->{
            gameManager.start();
        });
        btn4.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser=new FileChooser();//nova janela
            fileChooser.setTitle("File open...");
            fileChooser.setInitialDirectory(new File("."));// diretorio inicial
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Game(*.bin)","*.bin")
              //      new FileChooser.ExtensionFilter("All","*.*")//todo o tipo de ficheiros
            );
            File hfile=fileChooser.showOpenDialog(this.getScene().getWindow());
            if(hfile!=null)
            {
                gameManager.load(hfile);
            }
        });
        btn2.setOnMouseClicked(mouseEvent -> {});
        btn3.setOnMouseClicked(mouseEvent -> {

            Platform.exit();
        });


    }
    /**
     * atualiza a visibilidade
     * */
    private void Update() {
        if(gameManager.getstate()== PacManStates.LOADINGSTATE){
            this.setVisible(true);
        }else {
            this.setVisible(false);

        }

    }
}
