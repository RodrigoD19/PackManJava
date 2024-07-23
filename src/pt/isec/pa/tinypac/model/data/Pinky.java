package pt.isec.pa.tinypac.model.data;


import java.io.Serializable;
import java.util.HashMap;
/**
 * Classe do pinky, subclasse Ghosts
 * */
public class Pinky extends Ghosts{
    private static final long serialVersionUID = 1L;
    private static Pinky instance;

    private final char symbol='T';
    private int sumx,sumy;
   private record Position(int linha,int coluna) implements Serializable { }
    private final HashMap<Integer,Position> pontos;
    private static int pontoatual;
    private final HashMap<Direcoes,Position> auxiliar;


    private Pinky(Game newgame, int linha, int coluna) {

        super(newgame,coluna,linha);auxiliar=new HashMap<Direcoes,Position>();
        auxiliar.put(Direcoes.ArrowLeft,new Position(0,-1));
        auxiliar.put(Direcoes.ArrowUp,new Position(-1,0));
        auxiliar.put(Direcoes.ArrowRight,new Position(0,1));
        auxiliar.put(Direcoes.ArrowDown,new Position(1,0));

        direcao= Direcoes.ArrowUp;
        sumx=0;sumy=0;
        pontos=new HashMap<Integer,Position>();
        char aux[][]=jogo.gettab();
        pontoatual=0;
        pontos.put(0,new Position(1, 27));
        pontos.put(1,new Position(29,27 ));
        pontos.put(2,new Position(1,1));
        pontos.put(3,new Position(29,1));
    }
    public static Pinky getInstance(Game newgame,int linha,int coluna){
        if(instance==null){
            instance= new Pinky(newgame,linha,coluna);}
       return instance;
    }
    private int dist(int linha1,int coluna1){
        return (int) Math.round(Math.sqrt((coluna1-pontos.get(pontoatual).coluna)*(coluna1-pontos.get(pontoatual).coluna)+(linha1-pontos.get(pontoatual).linha)*(linha1-pontos.get(pontoatual).linha)));
    }

    private IMazeElement genxt(){
        switch (direcao){
            case ArrowDown -> {
                sumx=1;
                sumy=0;
            }
            case ArrowUp -> {
                sumx=-1;
                sumy=0;
            }
            case ArrowLeft ->{
                sumx=0;
                sumy=-1;
            }
            case ArrowRight ->
            {
                sumx=0;
                sumy=1;
            }
        }
        IMazeElement aux=jogo.getelement(linha +sumx, coluna +sumy);
        return aux;
    }
    public void getbestdirection(){
        Direcoes opposit=null;
        switch (direcao){
            case ArrowDown -> opposit= Direcoes.ArrowUp;
            case ArrowLeft -> opposit= Direcoes.ArrowRight;
            case ArrowRight -> opposit= Direcoes.ArrowLeft;
            case ArrowUp -> opposit= Direcoes.ArrowDown;
        }
        HashMap<Direcoes,Position>aux;
        aux=new HashMap<Direcoes,Position>();
        // esta parte ta a funcionar bem
        for (Direcoes au:auxiliar.keySet()) {
            IMazeElement po=jogo.getelement(linha+auxiliar.get(au).linha,coluna+auxiliar.get(au).coluna);
            if(po.getSymbol()=='Y'&&au== Direcoes.ArrowUp){direcao= Direcoes.ArrowUp;return;}
            if(jogo.canmoveitghostImaze(po,this)&&au!=opposit){

                aux.put(au,auxiliar.get(au));

            }

        }


        if(aux.isEmpty()){direcao=opposit;return;}


        int bestvalue=dist(linha,coluna)+1;
        for (Direcoes au:aux.keySet()) {
          //  System.out.println("A distancia atual e"+dist(linha,coluna)+"A distancia da direcao" +au +"vai ser"+dist(linha+aux.get(au).linha,coluna+aux.get(au).coluna));
            if(dist(linha,coluna)>=dist(linha+aux.get(au).linha,coluna+aux.get(au).coluna)){

               bestvalue=dist(linha+aux.get(au).linha,coluna+aux.get(au).coluna);
                direcao=au;
              if(Math.random()<0.7)return;
            }
        }
        if (jogo.getelement(linha+auxiliar.get(direcao).linha,coluna+auxiliar.get(direcao).coluna).getSymbol()=='x'){
            direcao=opposit;
        }
//        System.out.println("O valor do best value e "+bestvalue+"a distancia e"+dist(linha,coluna));




    }
    static void initialize(){
        instance=null;
    }

    @Override
    public void evolve() {
        if(coluna ==pontos.get(pontoatual).coluna&& linha ==pontos.get(pontoatual).linha){
         //   System.out.println("HAPPENED");
            pontoatual++;
        pontoatual=pontoatual%4;
        }
        if(jogo.isinthebox(this)&& genxt() instanceof Ghosts)
            return;
       // System.out.println("O obj atual e "+pontoatual);
        getbestdirection();
        next=genxt();
        move();
       // System.out.println("o next e "+next.getSymbol()+direcao);
       if(!(next instanceof PacMan)&&!(next instanceof Ghosts)&&jogo.canmoveitghostImaze(next,this)){
            before=next;}


    }
    @Override
    protected boolean move(){
       // System.out.println("Direcao que chega ao pinky"+direcao);
        switch (direcao){
            case ArrowDown -> {
                sumy=0;
                sumx=1;
            }
            case ArrowUp -> {
                sumy=0;
                sumx=-1;
            }
            case ArrowLeft -> {
                sumy=-1;
                sumx=0;
            }
            case ArrowRight -> {
                sumy=1;
                sumx=0;
            }
        }
        if(jogo.addorganism(coluna+sumy, linha +sumx,this)){
            jogo.addorganism(coluna, linha,before);
            linha +=sumx;
            coluna +=sumy;

          //  System.out.println("FEZ");
            return true;
        }
        //System.out.println("NAO FEZ");
        return false;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
