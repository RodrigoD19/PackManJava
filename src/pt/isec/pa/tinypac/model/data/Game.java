package pt.isec.pa.tinypac.model.data;





import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
/**
 * Classe que alberga tudo em relacao ao funcionamento do jogo
 * */

public class Game implements Serializable{
    private static final long serialVersionUID = 1L;



    record Position (int linha,int coluna)implements Serializable{}

   private HashMap<String,Ghosts>fantasmas;
    private PacMan instance;

    private Fruit fruta;
    private ArrayList<Ghosts> inthebox;
    private ArrayList<Position>wraps;

    private Maze maze;
    private static int pontuacao;
    private int lives;
    private ArrayList<LogTop> top;
    private static float cteste=100;
    private static float nivel=1;

    public void setCaneatghosts(boolean caneatghosts) {
        this.caneatghosts = caneatghosts;
    }

    private  int countballs;

    private  int nrpowerballs;// powerballs que o pacman ja comeu
    private boolean caneatghosts;

    public  int getCountballs() {
        return countballs;
    }

    public int getNrpowerballs() {
        return nrpowerballs;
    }
    public int getlives() {
        return lives;
    }

    public Game(int height,int width) {
        this.maze=new Maze(height,width);

    }
    /**
     * Inicializa variaveis
     * */
    public void initGame(){
        fantasmas=new HashMap<String,Ghosts>();
        wraps=new ArrayList<Position>();
        top=new ArrayList<LogTop>();
        pontuacao=0;
        caneatghosts=false;
        lives=3;
        nrpowerballs=0;
        PacMan.initialize();
        Pinky.initialize();
        Inky.initialize();
        Blinky.initialize();
        Clyde.initialize();
        inthebox=new ArrayList<Ghosts>();
        faztop();
        crianivel();
    }

    public ArrayList<LogTop> getTop() {
        return top;
    }
    /**
     * Metodo para ir ler o top5 ao ficheiro
     * */
    public void faztop(){
        top=new ArrayList<LogTop>();
        File logtop=new File("top5.txt");
        if(!logtop.exists()){
            try {
                logtop.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Incapacidade de criar o ficheiro do top 5");
            }
            return;
        }
        BufferedReader ficheiro = null;
        try {
            ficheiro = new BufferedReader(new FileReader(logtop));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner sc=new Scanner(ficheiro);
        sc.useDelimiter("-");
        for (int i = 0; i < 5; i++) {
            if(!sc.hasNext())
                return;
            top.add(new LogTop(sc.next(), sc.nextInt()));
        }
    }
    /**
     * Metodo para verificar se a pontuação necessaria é o suficiente para entrar no top
     * */
    public boolean bestponis(){

        if(top.isEmpty()){
            return true;
        }

        for (LogTop log:top) {
            if(pontuacao>log.pontos)
                return true;
        }
        return false;
    }
    /**
     * Le o ficheiro e cria o nivel
     * */
    public void crianivel() {

        try {
            File file=new File("Level101.txt");
            BufferedReader nivel = new BufferedReader(new FileReader(file));
            Scanner sc=new Scanner(nivel);
            sc.useDelimiter(System.lineSeparator());
            character aux ;
            char[][] auxi = maze.getMaze();
            String helpme;
            for (int i = 0; i < auxi.length; i++) {
                helpme=sc.next();
                int h=0;
                for (int j = 0; j < helpme.length(); j++) {
                    switch (helpme.charAt(j)){
                        case 'W'->{maze.set(i,j,new character('W'));
                                    wraps.add(new Position(i,j));
                        }
                        case 'M'->{maze.set(i,j,PacMan.getInstance(this,i,j));
                                    instance=PacMan.getInstance(this,0,0);
                        }
                        case 'O'->{maze.set(i,j,new character('O'));
                                    nrpowerballs++;
                        }
                        case 'F'->{maze.set(i,j, Fruit.getInstance(i,j,1));
                                    fruta=Fruit.getInstance(0,0,0);
                        }
                      case 'y'->{
                             if(!fantasmas.containsKey("Pinky"))
                            {maze.set(i,j,Pinky.getInstance(this,i,j));
                             fantasmas.put("Pinky",Pinky.getInstance(this,0,0));
                             inthebox.add(fantasmas.get("Pinky"));
                            }else
                            if(!fantasmas.containsKey("Clyde"))
                            {maze.set(i,j,Clyde.getinstance(this,i,j));
                                fantasmas.put("Clyde",Clyde.getinstance(this,0,0));
                                inthebox.add(fantasmas.get("Clyde"));
                            }else
                            if(!fantasmas.containsKey("Inky"))
                            {maze.set(i,j,Inky.getInstance(this,i,j));
                                fantasmas.put("Inky",Inky.getInstance(this,0,0));
                                inthebox.add(fantasmas.get("Inky"));
                            }else
                            if(!fantasmas.containsKey("Blinky"))
                            {maze.set(i,j,Blinky.getInstance(this,i,j));
                                fantasmas.put("Blinky",Blinky.getInstance(this,0,0));
                                inthebox.add(fantasmas.get("Blinky"));
                            }else{
                                if(helpme.charAt(j)=='o')
                                    countballs++;
                                maze.set(i, j, null);
                            }

                        }
                        default -> { aux =new character(helpme.charAt(j));
                            maze.set(i, j, aux);}

                    }
                    }
            nivel.close();


            }
        }
        catch (FileNotFoundException e){
                e.printStackTrace();
            System.out.println("Problemas a criar nivel");
            return;

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public boolean isinthebox(Ghosts ghost){
        return inthebox.contains(ghost);
    }
    /**
     * Metodo para inserir o jogador no top5
     * */
    public void inserttop(String nome){
        top.add(new LogTop(nome,pontuacao) );
        Collections.sort(top,new LogTop());
        if(top.size()>5){
            top.remove(5);
        }
        File file=new File("top5.txt");
        try {
            int i=1;
            BufferedWriter bf=new BufferedWriter(new FileWriter(file));
            for (LogTop to:top
            ) {
                bf.write(i+'-'+to.nome+'-'+to.pontos);
                bf.newLine();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Metodo para usar nos testes
     * */

    public void setLives(int lives) {
        this.lives = lives;
    }
    /**
     * Metodo para mudar a direçao
     * */
    public void changedirectionpacman(Direcoes direcoes){
        instance.changedirection(direcoes);
    }
    /**
     * Metodo para verificar se o fantasma pode mover se para a posição recebida
     * */
    boolean canmoveitghost(int linha,int coluna,Ghosts ghosts){
        IMazeElement aux=getelement(linha,coluna);
        if(aux==null){
           return true;
        }
        if(aux.getSymbol()=='Y'){
            if(isinthebox(ghosts)){
                inthebox.remove(ghosts);
                return true;
            }else return false;
        }
        if(aux.getSymbol()=='x'||aux.getSymbol()=='W')
            return false;

        return true;
    }
    /**
     * Metodo para verificar se o fantasma se pode mover tendo em conta o objeto que se enconta na posicao
     * */
    boolean canmoveitghostImaze(IMazeElement aux,Ghosts ghosts){

        if(aux==null){
            return true;
        }
        if(aux.getSymbol()=='Y'){
            if(isinthebox(ghosts)){
                inthebox.remove(ghosts);
                return true;
            }else return false;
        }
        if(aux.getSymbol()=='x'||aux.getSymbol()=='W')
            return false;

        return true;
    }
    /**
     * Metodo para mover tudo
     * */
    public boolean addorganism(int coluna,int linha,IMazeElement element){
        IMazeElement aux= maze.get(linha,coluna);
        if(aux==null){
            aux=new character(' ');
            maze.set(linha,coluna,aux);
        }
        if(!(element instanceof BaseClass)&&aux.getSymbol()!='Y'){
            maze.set(linha,coluna,element);
            return true;
        }

        if(element instanceof Ghosts){
            if(aux.getSymbol()=='Y'&& isinthebox((Ghosts) element)){
                inthebox.remove(element);
                return true;}
            if(aux.getSymbol()=='Y' && caneatghosts){
                inthebox.add((Ghosts) element);
                return false;
            }
            if(aux.getSymbol()=='P' && !caneatghosts){
               maze.set(linha,coluna,element);
                instance.die();
                lives--;
                return true;
            }

            if(canmoveitghostImaze(aux, (Ghosts) element)){
                maze.set(linha,coluna,element);
                return true;
            }else return false;
        }

        if(aux.getSymbol()=='W'){

            int i= wraps.indexOf(new Position(linha,coluna));
            if(i==0){
                instance.teleport(wraps.get(1).linha,wraps.get(1).coluna-1);
                  maze.set(linha,coluna+1,null);
            } else if (i==1) {
                instance.teleport(wraps.get(0).linha,wraps.get(0).coluna+1);
                maze.set(linha,coluna-1,null);
            }

            return false;

        }
        //System.out.println("O elemento que ele ve e"+aux.getSymbol());
        if(aux.getSymbol()=='O'){
            nrpowerballs--;
        }
        if(aux.getSymbol()=='F'){
            pontuacao+=fruta.getMultiplier();
            fruta.eaten();
        }
        if(aux instanceof Ghosts && caneatghosts){
            inthebox.add((Ghosts) aux);
            ((Ghosts) aux).die();
            return true;
        }else if(aux instanceof Ghosts){
            instance.die();
            lives--;
            return false;
        }

       if(aux.getSymbol()=='Y')
            return false;
        if(aux.getSymbol()!='x'){
            maze.set(linha,coluna,element);
            countballs--;
            fruta.addcontador();
            pontuacao++;
            return true;
        }



       return false;
    }
    public IMazeElement getelement(int linha,int coluna){
            if(maze.get(linha,coluna)==null)
                return new character(' ');
            return maze.get(linha,coluna);

    }

    public int getPontuacao() {
        return pontuacao;
    }
    /**
     * Metodo para mover os fantasmas quando no estado eatghost
     * */
    public void evolveback(){

        for (Ghosts ghos:fantasmas.values()
             ) {
            ghos.evolveback();
        }


        if(cteste>=100) {
            instance.evolve();
            cteste-=100;
        }
        cteste+=100-((nivel-1)*2);

    }
    public int getpacposlinha(){
        return instance.getLinha();
    }
    public int getpacposcoluna(){
        return instance.getColuna();
    }
    /**
     * Metodo para andar normal
     * */

    public void evolve() {

        Inky inky=(Inky) fantasmas.get("Inky");
       for (Ghosts ghos: fantasmas.values()
             ) {
           if(ghos.getClass()!=Inky.class)
             ghos.evolve();

        }
        inky.evolve();
        if(cteste>=100) {
            instance.evolve();
            cteste-=100;
        }
        cteste+=100-((nivel-1)*2);
        System.out.println("TA no evolve do game");





    }


    /**
     * Metodo para andar no estado em que os fantasmas se encontram ainda imoveis
     * */
    public void evolveinitialstate(){
        instance.evolve();
    }

    public char[][] gettab() {
        return maze.getMaze();
    }
    /**
     * Nested class usada para auxiliar nos processos
     * */

      class character implements IMazeElement,Serializable{
         char a;

        public character(char a) {
            this.a = a;
        }

        public character(IMazeElement element){
            this.a= element.getSymbol();
        }
        public boolean isEmpty(){
            if(a==' ') {
                return true;
            }
            return false;
        }




        @Override
        public char getSymbol() {
            return a;
        }

    }


}


