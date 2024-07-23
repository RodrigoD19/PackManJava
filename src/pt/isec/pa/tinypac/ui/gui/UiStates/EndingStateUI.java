package pt.isec.pa.tinypac.ui.gui.UiStates;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.tinypac.model.GameManager;
import pt.isec.pa.tinypac.model.fcsm.PacManStates;
/**
 * Classe para mostrar o resultado do jogo
 * */
public class EndingStateUI extends BorderPane {
    GameManager gameManager;
    Button bt1,btn2;
    Label lbl;
    VBox vBox;
    TextField txt;

    public EndingStateUI(GameManager gameManager) {
        this.gameManager = gameManager;
        CreateViews();
        RegisterHandles();
        update();
    }
    /**
     * atualiza
     * */
    private void update() {
        if(gameManager.getstate()==PacManStates.ENDINGSTATE){
            this.setVisible(true);
            System.out.println("raa aqui");
            lbl.setText("Pontuação:"+gameManager.getpontuacao());

        }else {
            this.setVisible(false);

        }
    }
    /**
     * Regista Handlers de eventos
     * */
    private void RegisterHandles() {
        gameManager.addPropertyChangeListener(GameManager.PROP_GAME,evt ->{
            // Run listener logic on UI thread
            Platform.runLater(() ->{
                        update();
                    }
            );
        });
           bt1.setOnMouseClicked(mouseEvent -> gameManager.start());
           btn2.setOnMouseClicked(mouseEvent -> gameManager.stop());
        txt.setOnKeyPressed(keyEvent ->
        {
            if(keyEvent.getCode()== KeyCode.ENTER){
                if(gameManager.cantop()){
                gameManager.inserttop(  txt.getText());}
            }
        });
    }
    /**
     * Cria elementos
     * */
    private void CreateViews() {
        bt1=new Button("Recomeçar jogo");
        lbl=new Label("Pontuação:"+gameManager.getpontuacao());
        btn2=new Button("Voltar ao menu Inicial");
        txt=new TextField();
        bt1.setMinWidth(100);
        btn2.setMinWidth(100);
        vBox=new VBox(lbl,bt1,btn2);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        setCenter(vBox);
        setTop(txt);


    }
}
