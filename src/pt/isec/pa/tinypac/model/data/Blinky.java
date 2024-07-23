package pt.isec.pa.tinypac.model.data;

/**
 * Classe do fantasma blinky, subclasse do Ghosts
 * */
public class Blinky extends Ghosts {
    private static final long serialVersionUID = 1L;
    private static Blinky instance;
    private final char symbol = 'B';



    private Blinky(Game newgame, int linha , int coluna) {
        super(newgame,coluna,linha);
        direcao = Direcoes.ArrowUp;
        before = null;
        next = null;


    }

    private int sumx, sumy;

    //Não posso ter aqui nada relacionado à máquina de estados só funções próprias
    /**
     * Metodo Singleton para obter a instance do fantasma
     * */
    public static Blinky getInstance(Game newgame, int linha, int coluna) {
        if (instance == null)
            instance =new Blinky(newgame, linha, coluna);
         return instance;
    }

    /**
     * metodo responsavel por decidir qual o proximo movimento
     * */
    private void getnext(){
        IMazeElement aux=null;


        int ran = (int)(Math.random() * 4) + 1;

            switch (ran) {
                case 1 -> {
                //    aux = jogo.getelement(linha+1, coluna );
                    if (jogo.canmoveitghost(linha+1,coluna,this)) {
                        direcao = Direcoes.ArrowDown;
                    }
                }
                case 2 -> {
                //    aux = jogo.getelement(linha-1, coluna );
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



    }
    /**
     * vai devolver o elemento a que esta logo a sua frente
     * isto é para dps posicionar esse elemento quando passar por cima dele
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
     *Poe a instancia a null para recomeçar o jogo
     * */

    static void initialize(){
        instance=null;
    }
    /**
     * chama as funçoes para o blinky andar
     * */
    @Override
    public void evolve() {
       // System.out.println("A letra do elemento e"+jogo.getelement(linha, coluna).getSymbol());
        next=genxt();



        if(next instanceof Ghosts)
            return;
        while (!move()) {
            getnext();
            next=genxt();

        }
        if(jogo.isinthebox(this))
            direcao=Direcoes.ArrowUp;
        if(!(next instanceof PacMan)&&!(next instanceof Ghosts)&&jogo.canmoveitghostImaze(next,this) ){
            before=next;

        }

        //if next!= instance of ghosts


    }

    /**
     * faz o blinky andar
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
