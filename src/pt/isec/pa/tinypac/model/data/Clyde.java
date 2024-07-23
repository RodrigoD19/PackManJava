package pt.isec.pa.tinypac.model.data;



import java.io.Serializable;
import java.util.HashMap;
/**
 * Fantasma Clyde , subclasse de Ghosts
 * */

public class Clyde extends Ghosts{
    private static final long serialVersionUID = 1L;
    private static Clyde instance;

    private final char symbol='C';
    private record Position(int linha,int coluna) implements Serializable { }
    private final HashMap<Direcoes, Position> auxiliar;



    private Clyde(Game newgame, int linha, int coluna) {
        super(newgame,coluna,linha);
        direcao= Direcoes.ArrowUp;
        auxiliar=new HashMap<Direcoes, Position>();
        auxiliar.put(Direcoes.ArrowLeft,new Position(0,-1));
        auxiliar.put(Direcoes.ArrowUp,new Position(-1,0));
        auxiliar.put(Direcoes.ArrowRight,new Position(0,1));
        auxiliar.put(Direcoes.ArrowDown,new Position(1,0));
    }
    public static Clyde getinstance(Game newgame,int linha,int coluna){
        if (instance == null)
            instance =new Clyde(newgame, linha, coluna);
        return instance;
    }

    private int sumx, sumy;

    //Não posso ter aqui nada relacionado à máquina de estados só funções próprias
    /**
     * Metodo para quando não tem o pacman na sua linha de visao
     * */
    private void getnext(){
        IMazeElement aux=null;


        int ran = (int)(Math.random() * 4) + 1;

        switch (ran) {
            case 1 -> {
             //   aux = jogo.getelement(linha+1, coluna );
                if (jogo.canmoveitghost(linha+1,coluna,this)) {
                    direcao = Direcoes.ArrowDown;
                }
            }
            case 2 -> {
              //  aux = jogo.getelement(linha-1, coluna );
                if (jogo.canmoveitghost(linha-1,coluna,this)) {
                    direcao = Direcoes.ArrowUp;
                }
            }
            case 3 -> {
            //    aux = jogo.getelement(linha , coluna-1);
                if (jogo.canmoveitghost(linha,coluna-1,this)) {
                    direcao = Direcoes.ArrowLeft;
                }
            }
            case 4 -> {
            //    aux = jogo.getelement(linha , coluna+1);
                if (jogo.canmoveitghost(linha,coluna+1,this)) {
                    direcao = Direcoes.ArrowRight;
                }
            }

        }


       // return aux.getSymbol();
    }
    /**
     * Vai buscar o elemento a sua frente
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
     * Metodo para verificar se o pacman está na sua linha de visao
     * */
    public boolean ispacmanvisible(){
        IMazeElement aux=null;
        int sum1=0,sum2=0;
        if(jogo.getpacposlinha()==linha){
            if(jogo.getpacposcoluna()>coluna){
                do{
                    sum2++;
                    aux=jogo.getelement(linha,coluna+sum2);
                }while (jogo.canmoveitghostImaze(aux,this)&&!(aux instanceof PacMan));
                if(aux instanceof PacMan)
                {
                    direcao= Direcoes.ArrowRight;return true;
                }
            }else if(jogo.getpacposlinha()<coluna){
                do{
                    sum2--;
                    aux=jogo.getelement(linha,coluna+sum2);
                }while (jogo.canmoveitghostImaze(aux,this)&&!(aux instanceof PacMan));
                if(aux instanceof PacMan)
                {
                    direcao= Direcoes.ArrowLeft;return true;
                }
            }
        }else if(jogo.getpacposcoluna()==coluna){
            if(jogo.getpacposcoluna()>linha){
                do{
                    sum1++;
                    aux=jogo.getelement(linha+sum1,coluna);
                }while (jogo.canmoveitghostImaze(aux,this)&&!(aux instanceof PacMan));
                if(aux instanceof PacMan)
                {
                    direcao= Direcoes.ArrowDown;return true;
                }
            }else if(jogo.getpacposlinha()<linha){
                do{
                    sum1--;
                    aux=jogo.getelement(linha+sum1,coluna);
                }while (jogo.canmoveitghostImaze(aux,this)&&!(aux instanceof PacMan));
                if(aux instanceof PacMan)
                {
                    direcao= Direcoes.ArrowUp;return true;
                }
            }

        }
        return false;
    }
    /**
     * Metodo que chama as funcoes para movimentar
     * */
    @Override
    public void evolve() {
        // System.out.println("A letra do elemento e"+jogo.getelement(linha, coluna).getSymbol());
        if(jogo.isinthebox(this)&&genxt() instanceof Ghosts){
            return;
        }
        if(jogo.isinthebox(this)){
            if(jogo.getelement(linha+auxiliar.get(Direcoes.ArrowUp).linha,coluna+auxiliar.get(Direcoes.ArrowUp).coluna).getSymbol()!='x'){
                direcao= Direcoes.ArrowUp;

                next=jogo.getelement(linha+auxiliar.get(Direcoes.ArrowUp).linha,coluna+auxiliar.get(Direcoes.ArrowUp).coluna);
                move();
            }else {
                direcao= Direcoes.ArrowRight;
                move();

            }

        }
        else {
        if(ispacmanvisible()){
            next = genxt();
            move();


        }else {
            next = genxt();


            while (!move()) {
                getnext();
                next = genxt();

            }
        }
        }
        if(!(next instanceof PacMan)&&!(next instanceof Ghosts) &&jogo.canmoveitghostImaze(next,this)){
            before=next;
        }

        //if next!= instance of ghosts


    }
    /**
     * Inicializa para recomeçar o nivel
     * */
    static void initialize(){
        instance=null;
    }
    /**
     * Faz andar o fantasma
     * */
    @Override
    protected boolean move(){

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
        if(jogo.addorganism(coluna +sumy, linha +sumx,this)){
            jogo.addorganism(coluna, linha,before);

            linha +=sumx;
            coluna +=sumy;
            return true;
        }

        return false;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
