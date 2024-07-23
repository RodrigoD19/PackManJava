package pt.isec.pa.tinypac.model.data;


import java.io.Serializable;
/**
 * Classe do pacman
 * */

public class PacMan extends BaseClass implements Serializable {
    private static final long serialVersionUID = 1L;
    //singleton
    private static PacMan instance;
    private Direcoes waitdirection;

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    private int linha, coluna;
    private  int initiallinha,initialcoluna;



    private final char symbol='P';

    private Direcoes direcao;




    private PacMan(Game newgame, int coluna, int linha) {
        super(newgame);
        direcao= Direcoes.ArrowDown;
        this.linha = linha;
        this.coluna = coluna;
        this.initialcoluna=coluna;
        this.initiallinha=linha;
    }// colocar o pacman como singleton para dps mover na maze , maybe


    static PacMan getInstance(Game newgame,int linha,int coluna){
     if(instance==null)
         instance=new PacMan(newgame,coluna,linha);
     return instance;
    }

    static void initialize(){
        instance=null;
    }
    /**
     * Muda de direcao conforme o que recebe
     * caso nao seja possivel a mudança essa ira ficar em wait
     * ate ao proximo cruzamento
     * */

    public void changedirection(Direcoes direcoes){// mudar a direção do pacman
         IMazeElement aux;
       //Talvez faça assim

        switch (direcoes){
            case ArrowDown -> {aux= jogo.getelement(linha+1, coluna);
                if(aux==null||(aux.getSymbol()!='x'&&aux.getSymbol()!='Y')){
                    direcao= direcoes;
                    waitdirection=null;
                }else{waitdirection= Direcoes.ArrowDown;}
            }
            case ArrowUp -> {aux= jogo.getelement(linha-1, coluna );
                if(aux==null||(aux.getSymbol()!='x'&&aux.getSymbol()!='Y')){
                    direcao= direcoes;waitdirection=null;}else {
                    waitdirection= Direcoes.ArrowUp;
                }
            }
            case ArrowLeft -> {aux= jogo.getelement(linha , coluna-1);
                if(aux==null||(aux.getSymbol()!='x'&&aux.getSymbol()!='Y')){
                    direcao= direcoes;waitdirection=null;}else {waitdirection= Direcoes.ArrowLeft;}
            }
            case ArrowRight -> {aux= jogo.getelement(linha , coluna+1);
                if(aux==null||(aux.getSymbol()!='x'&&aux.getSymbol()!='Y')){
                    direcao= direcoes;waitdirection=null;}else {waitdirection= Direcoes.ArrowRight;}
            }

        }


    }
    /**
     * Volta posicao inicial
     * */
    public boolean die(){
        linha=initiallinha;coluna=initialcoluna;
        return true;
    }
    public void evolve(){
        System.out.println("A direcao e"+ direcao);

        int sumx=0,sumy=0;
        if(waitdirection!=null){
            changedirection(waitdirection);
        }
        switch (direcao){
            case ArrowDown -> {
                sumx=1;sumy=0;
            }
            case ArrowUp -> {
                sumx=-1;sumy=0;
            }
            case ArrowLeft -> {
                sumx=0;sumy=-1;
            }
            case ArrowRight -> {
                sumx=0;sumy=1;

            }
        }
        if(jogo.addorganism(coluna+sumy , linha+sumx,this)){
            jogo.addorganism(coluna, linha,null);
            linha+=sumx;coluna+=sumy;
        }

    }
    /**
     * Funcao usada para os wraps
     * */
    boolean teleport(int linha,int coluna){
       // jogo.addorganism(coluna,linha,null);
        this.linha =linha;
        this.coluna =coluna;
        return true;
    }


    @Override
    public char getSymbol() {
        return symbol;
    }
}
