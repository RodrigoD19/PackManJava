package pt.isec.pa.tinypac.model.data;


import java.io.Serializable;
import java.util.HashMap;
/**
 * Metodo mae dos fantasmas
 * */
public abstract class Ghosts extends BaseClass implements Serializable {
    int linha, coluna;
    int initialposl,initialposc;
    Direcoes direcao;
    IMazeElement before,next;
    private record Position(int linha,int coluna) implements Serializable{ }
    private  HashMap<Direcoes, Position> auxiliar;


    protected Ghosts(Game newgame, int coluna, int linha) {
        super(newgame);
        this.linha = linha;
        this.coluna = coluna;
        this.initialposc=coluna;
        this.initialposl=linha;
        auxiliar=new HashMap<>();
        auxiliar.put(Direcoes.ArrowUp,new Position(-1,0));
        auxiliar.put(Direcoes.ArrowDown,new Position(1,0));
        auxiliar.put(Direcoes.ArrowLeft,new Position(0,-1));
        auxiliar.put(Direcoes.ArrowRight,new Position(0,1));


    }
    public void evolve(){

    }
    /**
     * Retorna a posicao inicial
     * */
    void die(){
        linha=initialposl;
        coluna=initialposc;
    }
    /**
     * Metodo para movimentar os fantasmas no estado eatghost
     * */
    public void evolveback(){
        if(linha==initialposl&&coluna==initialposc)
            return;

     int bestdist=1000;
     int sumx,sumy;
        HashMap<Direcoes, Position>aux=new HashMap<>();
        for (Direcoes au:auxiliar.keySet()) {
            IMazeElement po=jogo.getelement(linha+auxiliar.get(au).linha,coluna+auxiliar.get(au).coluna);
            if(po.getSymbol()=='Y'){direcao= Direcoes.ArrowDown;
                move();
                return;}
            if(jogo.canmoveitghostImaze(po,this)){
                aux.put(au,auxiliar.get(au));
            }

        }


        int bestvalue=1000;
        for (Direcoes au:aux.keySet()) {
            //  System.out.println("A distancia atual e"+dist(linha,coluna)+"A distancia da direcao" +au +"vai ser"+dist(linha+aux.get(au).linha,coluna+aux.get(au).coluna));
            if(closer(0,0)>=closer(aux.get(au).linha,aux.get(au).coluna)){

                bestvalue=closer(aux.get(au).linha,aux.get(au).coluna);
                direcao=au;
                if (Math.random()>0.7)
                    break;



            }
        }
        if(aux.size()==1 && bestvalue==1000)
            direcao= Direcoes.oposta(direcao);
        System.out.println("A direcao e "+direcao);
        next= jogo.getelement(linha+auxiliar.get(direcao).linha,coluna+auxiliar.get(direcao).coluna);
        if(!(next instanceof PacMan)&&!(next instanceof Ghosts) && jogo.canmoveitghostImaze(next,this) ){
            before=next;

        }

        move();



    }
    /**
     * funçaõ auxiliar dos cálculos;
     * */
    private int closer(int sumx,int sumy){
        return (int) Math.round(Math.sqrt(Math.pow(Math.abs(linha+sumx-initialposl),2)+Math.pow(Math.abs(coluna+sumy-initialposc),2)));
    }

    /**
     * Metodo para mover
     * */
    protected boolean move(){
       return false;
    }





    @Override
    public char getSymbol() {
        return 0;
    }
}
