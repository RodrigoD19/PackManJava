package pt.isec.pa.tinypac.ui.gui.UiStates;

import javafx.application.Platform;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import pt.isec.pa.tinypac.model.GameManager;
import pt.isec.pa.tinypac.model.fcsm.PacManStates;
/**
 * Classe que imprime o jogo
 * */
public class NormalStateUi extends BorderPane {
    GameManager gameManager;
    Canvas canvasmap,canvastitle;
    Button btn1;
    int i;
    Image Blinky,Pinky,Clyde,Inky,superball,fruta,vidas;
    Label lbl;
    VBox VBox;


    public NormalStateUi(GameManager gameManager)  {

        this.gameManager = gameManager;

        canvasmap=new Canvas(this.getWidth(),this.getHeight());
        CreateViews();
        RegisterHandlers();
        Update();


    }
    /**
     * Cria elementos
     * */

    private void CreateViews() {
       lbl=new Label();

       Blinky=new Image("images/Blinky.png");
      Pinky=new Image("images/Pinky.png");
       Clyde=new Image("images/Clyde.png");
       Inky=new Image("images/Inky.png");
        fruta=new Image("images/fruta.png");
       superball=new Image("images/Superball.png");
       vidas=new Image("images/Coracao.png");


    }
    /**
     * Regista handlers
     * */
    private void RegisterHandlers() {
        gameManager.addPropertyChangeListener(GameManager.PROP_GAME,evt ->{ Platform.runLater(()->Update());
        this.setOnKeyPressed(keyEvent -> {
            System.out.println("APanhou");
            switch (keyEvent.getCode()){
                case UP -> gameManager.changeDirection(KeyCode.UP);
                case DOWN -> gameManager.changeDirection(KeyCode.DOWN);
                case LEFT -> gameManager.changeDirection(KeyCode.LEFT);
                case RIGHT -> gameManager.changeDirection(KeyCode.RIGHT);
                case ESCAPE -> gameManager.pause();
            }

        });
        });

    }
    /**
     * atualiza mapa
     * */
    private void Update() {


        if(gameManager.getstate()== PacManStates.NORMALGAMESTATE || gameManager.getstate()==PacManStates.NOGHOSTSTATE||gameManager.getstate()==PacManStates.STOPSTATE){
            this.setVisible(true);
            this.requestFocus();
            // Clear the canvas
            GraphicsContext gc= canvasmap.getGraphicsContext2D();
            char tab[][]=gameManager.getmaze();
            int tileSize = 20; // Set the size of each tile

            canvasmap.setWidth(tab[0].length * tileSize);
            canvasmap.setHeight(tab.length * tileSize);
            lbl.setText("Pontuacao:"+gameManager.getpontuacao());
            lbl.setAlignment(Pos.TOP_CENTER);
            lbl.setTextFill(Color.BLUE);
            setTop(lbl);


            for (int j = 0; j < tab.length; j++) {
                for (int k = 0; k < tab[j].length; k++) {
                    char element = tab[j][k];
                    double x = k * tileSize;
                    double y = j * tileSize;

                    // Set the fill color based on the tab element
                    gc.setFill(Color.BLACK);
                    gc.fillRect(x, y, tileSize, tileSize);
                    if (element == 'P') {
                        gc.setFill(Color.YELLOW);
                        gc.fillOval(x+5, y+5, 15, 15);
                    }else if(element =='o'){
                        gc.setFill(Color.WHITE);
                        gc.fillOval(x+5, y+5, 5, 5);
                    } else if (element=='Y') {
                        gc.setFill(Color.GREEN);
                        gc.fillRect(x, y, tileSize, tileSize);
                    } else if(element=='x'){
                     gc.setFill(Color.BLUE);
                        gc.fillRect(x, y, tileSize, tileSize);
                    }else if(element=='B'){
                        gc.drawImage(Blinky,x,y);
                    }else if(element=='I'){
                        gc.drawImage(Inky,x,y);
                    } else if (element=='T') {
                        gc.drawImage(Pinky,x,y);
                    } else if (element=='C') {
                        gc.drawImage(Clyde,x,y);
                    }else if(element=='O'){
                        gc.drawImage(superball,x,y);
                    } else if (element=='F') {
                        gc.drawImage(fruta,x,y);
                    }else if(element=='W'){
                        gc.setFill(Color.PURPLE);
                        gc.fillRect(x, y, tileSize, tileSize);
                    }

                    // Draw the rectangle on the canvas

                }
            }

            canvastitle=new Canvas(500,50);
            GraphicsContext gcs=canvastitle.getGraphicsContext2D();
            gcs.setStroke(Color.WHITE);
            gcs.strokeText(lbl.getText(),300,10);

            for (int j = 0; j <gameManager.getlives() ; j++) {
                gcs.drawImage(vidas,j*30+100,20);
            }

            this.setTop(canvastitle);
            this.setCenter(canvasmap);
            this.setBackground(new Background(
                    new BackgroundFill(
                            Color.BLACK,new CornerRadii(0),new Insets(0)
                    )));

        }else {
            this.setVisible(false);
        }


    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}
   /*      for (char []c:tab) {
                hBox=new HBox();
                for (char p:c) {
                    gc.setStroke(Color.RED);

                    if(p=='P'){

                        hBox.getChildren().add( lbl);
                    }else{
                   }
                }
                hBox.setSpacing(5);
                hBox.setAlignment(Pos.CENTER);
                vBox.getChildren().add(hBox);

            }*///vBox.setAlignment(Pos.CENTER);