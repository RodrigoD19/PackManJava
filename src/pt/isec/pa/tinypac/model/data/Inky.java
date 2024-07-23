package pt.isec.pa.tinypac.model.data;



import java.io.Serializable;
import java.util.HashMap;
/**
 * Classe Inky, subclasse Ghost
 * */

public class Inky extends Ghosts{
    private static final long serialVersionUID = 1L;
    private final char symbol='I';
    private static Inky instance;
    private int sumx,sumy;
    private record Position(int linha,int coluna) implements Serializable { }
    private final HashMap<Integer, Position> pontos;
    private static int pontoatual;
    private final HashMap<Direcoes, Position> auxiliar;

    private Inky(Game newgame,int linha,int coluna) {
        super(newgame,coluna,linha);auxiliar=new HashMap<Direcoes, Position>();
        auxiliar.put(Direcoes.ArrowLeft,new Position(0,-1));
        auxiliar.put(Direcoes.ArrowUp,new Position(-1,0));
        auxiliar.put(Direcoes.ArrowRight,new Position(0,1));
        auxiliar.put(Direcoes.ArrowDown,new Position(1,0));

        direcao= Direcoes.ArrowDown;
        sumx=0;sumy=0;
        pontos=new HashMap<Integer, Position>();
        char aux[][]=jogo.gettab();
        pontoatual=0;
        pontos.put(0,new Position(29, 27));
        pontos.put(1,new Position(29,1 ));
        pontos.put(2,new Position(1,27));
        pontos.put(3,new Position(1,1));
    }
    /**
     * Metodo para o Singleton
     * */
    public static Inky getInstance(Game newgame,int linha,int coluna){
        if(instance==null)
            instance =new Inky(newgame,linha,coluna);
         return instance;
    }
    /**
     * Funcao auxiliar para calcular distancia
     * */
    private int dist(int linha1,int coluna1){
        return (int) Math.round(Math.sqrt((coluna1-pontos.get(pontoatual).coluna)*(coluna1-pontos.get(pontoatual).coluna)+(linha1-pontos.get(pontoatual).linha)*(linha1-pontos.get(pontoatual).linha)));
    }
    static void initialize(){
        instance=null;
    }
    /**
     * Metodo para ir buscar o elemento a frente
     * */

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
    /**
     * Metodo para calcular a melhor direçao tendo em conta que ele se dirige pra cantos
     * */
    public void getbestdirection(){
        Direcoes opposit=null;
        switch (direcao){
            case ArrowDown -> opposit= Direcoes.ArrowUp;
            case ArrowLeft -> opposit= Direcoes.ArrowRight;
            case ArrowRight -> opposit= Direcoes.ArrowLeft;
            case ArrowUp -> opposit= Direcoes.ArrowDown;
        }
        HashMap<Direcoes, Position> aux;
        aux=new HashMap<Direcoes, Position>();
        // esta parte ta a funcionar bem
        for (Direcoes au:auxiliar.keySet()) {
            IMazeElement po=jogo.getelement(linha+auxiliar.get(au).linha,coluna+auxiliar.get(au).coluna);
            if(po.getSymbol()=='Y'&&au== Direcoes.ArrowUp){direcao= Direcoes.ArrowUp;return;}
            if(jogo.canmoveitghostImaze(po,this)&&au!=opposit){
                //  System.out.println(au);
                //   System.out.println("PO simbolo: "+po.getSymbol());
                aux.put(au,auxiliar.get(au));
                // bestvalue=dist(linha+auxiliar.get(au).linha,coluna+auxiliar.get(au).coluna);
            }
            //if(bestvalue>=dist(linha,coluna)){
            //   aux.remove(au,auxiliar.get(au));
            // }
        }
        // System.out.println(dist(linha,coluna));

        if(aux.isEmpty()){direcao=opposit;return;}


        int bestvalue=dist(linha,coluna)+1;
        for (Direcoes au:aux.keySet()) {
         //   System.out.println("A distancia atual e"+dist(linha,coluna)+"A distancia da direcao" +au +"vai ser"+dist(linha+aux.get(au).linha,coluna+aux.get(au).coluna));
            if(dist(linha,coluna)>=dist(linha+aux.get(au).linha,coluna+aux.get(au).coluna)){
           //     System.out.println("Entrou aqui");
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
    /**
     * Metodo para movimentar tudo
     * */

    @Override
    public void evolve() {
        if(coluna ==pontos.get(pontoatual).coluna&& linha ==pontos.get(pontoatual).linha){
            //System.out.println("HAPPENED");
            pontoatual++;
             pontoatual=pontoatual%4;
        }
        // System.out.println("O obj atual e "+pontoatual);
        if(jogo.isinthebox(this)&&genxt() instanceof Ghosts){
            return;

        }
        if(jogo.isinthebox(this)){

            if(jogo.getelement(linha+auxiliar.get(Direcoes.ArrowUp).linha,coluna+auxiliar.get(Direcoes.ArrowUp).coluna).getSymbol()=='Y'){
                direcao= Direcoes.ArrowUp;
                next=jogo.getelement(linha+auxiliar.get(Direcoes.ArrowUp).linha,coluna+auxiliar.get(Direcoes.ArrowUp).coluna);
                move();
            }else {
                if(genxt().getSymbol()=='x'||direcao== Direcoes.ArrowLeft){
                    direcao= Direcoes.ArrowLeft;}else{
                direcao= Direcoes.ArrowRight;}
                move();

            }

        }else{
        getbestdirection();
        next=genxt();
        move();}
        // System.out.println("o next e "+next.getSymbol()+direcao);
        if(!(next instanceof PacMan)&&!(next instanceof Ghosts)&& jogo.canmoveitghostImaze(next,this)){
            before=next;
        }


    }
    /**
     * Movimenta no mapa
     * */

    @Override
    protected boolean move(){
        //System.out.println("Direcao que chega ao pinky"+direcao);
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

           System.out.println("FEZ");
            return true;
        }
    //    System.out.println("NAO FEZ");
        return false;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
