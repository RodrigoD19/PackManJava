package pt.isec.pa.tinypac.ui.gui.UiStates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.GameManager;
import pt.isec.pa.tinypac.model.fcsm.PacManStates;
/**
 * Classe para o estado do eatstate  ui ira imprimir o mapa com os fantasmas vultrtaveis
 * */

public class EatStateUI extends BorderPane {
    GameManager gameManager;
    Canvas canvasmap,canvastitle;
    Button btn1;
    int i;
    Image ghost,superball,fruta,vidas;
    Label lbl;
    javafx.scene.layout.VBox VBox;


    public EatStateUI(GameManager gameManager)  {

        this.gameManager = gameManager;

        canvasmap=new Canvas(this.getWidth(),this.getHeight());
        CreateViews();
        RegisterHandlers();
        Update();


    }
    /**
     * Cria os elementos
     * */
    private void CreateViews() {
        lbl=new Label();
     ghost=new Image("images/fantasmasvulneraveis.png");
        fruta=new Image("images/fruta.png");
        superball=new Image("images/Superball.png");
        vidas=new Image("images/Coracao.png");


    }
    /**
     * Regista handlers de eventos
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
     * funcao update
     * atualiza mapa
     *
     *
*/
    private void Update() {


        if(gameManager.getstate()== PacManStates.EATGHOSTSTATE ){
            this.setVisible(true);
            this.requestFocus();
            // Clear the canvas
            GraphicsContext gc= canvasmap.getGraphicsContext2D();
            char tab[][]=gameManager.getmaze();
            int tileSize = 20; // Set the size of each tile
            lbl.setText("Pontuacao:"+gameManager.getpontuacao());
            canvasmap.setWidth(tab[0].length * tileSize);
            canvasmap.setHeight(tab.length * tileSize);

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
                    } else if (element=='F') {
                        gc.drawImage(fruta,x,y);
                    } else if (element=='B'|| element=='I'||element=='T'||element=='C') {
                        gc.drawImage(ghost,x,y);
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
