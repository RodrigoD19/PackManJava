package pt.isec.pa.tinypac.ui.textui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.Direcoes;
import pt.isec.pa.tinypac.model.fcsm.GameContext;

import java.io.IOException;

public class GameUI implements IGameEngineEvolve {
    GameContext fsm;
    Screen screen;
    Boolean first;
    TextGraphics tx;

    public GameUI(GameContext fsm,GameEngine gameEngine) {
        this.fsm = fsm;
        try {
            screen = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(60,40)).createScreen();
            screen.setCursorPosition(null);
            screen.startScreen();
        } catch (IOException e) {
            System.out.println("Problema a criar o screen");
        }

        menustateui(gameEngine);
    }
    void imprimetab() throws IOException {
       char[][] aux = fsm.gettab();
       if(aux==null)return;
        TextGraphics txo= screen.newTextGraphics();
        txo.putString(10,0,"Pontuacao:"+fsm.getpontuacao());

        for (int i = 0; i < aux.length ; i++) {
            for (int j = 0; j <aux[i].length ; j++) {
                TextColor bc = switch (aux[i][j]) {
                    case 'T'->TextColor.ANSI.BLACK;
                    case 'C'->TextColor.ANSI.BLACK;
                    case'B'->TextColor.ANSI.BLACK;
                    case 'I'->TextColor.ANSI.BLACK;
                    case 'W'->TextColor.ANSI.MAGENTA;
                    case 'x' -> TextColor.ANSI.BLUE_BRIGHT;
                    case 'Y' -> TextColor.ANSI.GREEN_BRIGHT;
                    case 'P'->TextColor.ANSI.BLACK;
                    default -> TextColor.ANSI.BLACK;
                };
                TextColor tc = switch (aux[i][j]) {
                    case 'o'->TextColor.ANSI.CYAN_BRIGHT;
                    case 'W'->TextColor.ANSI.MAGENTA;
                    case 'T'->TextColor.ANSI.MAGENTA_BRIGHT;
                    case 'C'->TextColor.ANSI.RED;
                    case'B'->TextColor.ANSI.YELLOW;
                    case 'I'->TextColor.ANSI.BLACK_BRIGHT;
                    case 'x' -> TextColor.ANSI.BLUE_BRIGHT;
                    case 'Y' -> TextColor.ANSI.GREEN_BRIGHT;
                    case 'P'->TextColor.ANSI.YELLOW_BRIGHT;
                    default -> TextColor.ANSI.WHITE_BRIGHT;
                };


                screen.setCharacter(j,i+1, TextCharacter.fromCharacter(aux[i][j],tc,bc)[0]);


            }

        }
        screen.refresh();
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        try {
        //    System.out.println("current time:"+currentTime);
            KeyStroke key = screen.pollInput();
            if (key != null && key.getKeyType() == KeyType.ArrowUp ){fsm.changedirection(Direcoes.ArrowUp);
            //fsm.changedirection(Direçoes.UP);
            }
            if (key != null && key.getKeyType() == KeyType.ArrowDown ){fsm.changedirection(Direcoes.ArrowDown);  }
            if (key != null && key.getKeyType() == KeyType.ArrowLeft ){fsm.changedirection(Direcoes.ArrowLeft);  }
            if (key != null && key.getKeyType() == KeyType.ArrowRight ){fsm.changedirection(Direcoes.ArrowRight);  }
            if (key != null && key.getKeyType() == KeyType.Escape ){fsm.pause();
            pausedstateui(gameEngine);
            }
               // gameEngine.pause();
                //  pausedstateui();

            imprimetab();


        } catch (IOException e) { }
    }

    private void pausedstateui(IGameEngine gameEngine) {

            gameEngine.pause();
            screen.clear();
            int opt=0;
            //int opt=PAInput.chooseOption("JOGO PAUSADO","Voltar ao jogo","Voltar ao menu iniciar","Save game");
             tx= screen.newTextGraphics();
             tx.putString(10,9,"Jogo Pausado");
             tx.putString(10,10,"1:Voltar ao jogo");
            tx.putString(10,11,"2:Voltar ao Menu inical");
        tx.putString(10,12,"3:Save game");

        try {
            screen.refresh();
            KeyStroke stroke=screen.readInput();
               opt= stroke.getCharacter()-'0';

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        switch (opt){
                case 1->{screen.clear();
                    gameEngine.resume();
                    fsm.resume();
                }
                case 2->{gameEngine.stop();
                menustateui(gameEngine);
                }
                case 3-> System.out.println("A fazer");
                default -> System.out.println("Escolha por favor um numero valido");
            }
    }
    private void menustateui(IGameEngine gameEngine){
        screen.clear();
        int opt=0;
        //int opt=PAInput.chooseOption("JOGO PAUSADO","Voltar ao jogo","Voltar ao menu iniciar","Save game");
        TextGraphics txo= screen.newTextGraphics();
        txo.putString(10,9,"Menu");
        txo.putString(10,10,"1:Jogar o jogo");
        txo.putString(10,11,"2:Ver Top5");
        txo.putString(10,12,"3:Sair");

        try {
            screen.refresh();
            KeyStroke stroke=screen.readInput();
            opt= stroke.getCharacter()-'0';

        } catch (IOException e) {

        }
       // int opt=PAInput.chooseOption("Menu","Jogar","Ver Top5","Sair");
        switch (opt){
            case 1 -> {
                try {
                    screen.startScreen();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                gameEngine.start(300);



            }
            case 2 -> {
                System.out.println("A fazer");

            }
            case 3->{ gameEngine.stop();
                try {
                    screen.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            default -> System.out.println("Escolha um numero valido");
        }

    }


}
